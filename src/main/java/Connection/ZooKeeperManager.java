package Connection;

import Connection.LeaderElection.JobLeader;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ZooKeeperManager implements Watcher {

    /**
     * The current zookeeper connection, zkSession timeout, current connected zookeeper info
     * */
    private ZooKeeper zooKeeper;
    private int sessionTimeout;
    private int connectionTimeout;
    private int searchNewZkConnectionInterval;
    private ZooKeeperInfo zooKeeperInfo;

    /**
     * Helper class to store zookeeper instance information
     * */
    private class ZooKeeperInfo implements Comparable<ZooKeeperInfo>{
        public final String key;
        public final String address;

        public ZooKeeperInfo(String key, String address) {
            this.key = key;
            this.address = address;
        }

        @Override
        public int hashCode(){
            return this.address.hashCode();
        }

        @Override
        public boolean equals(Object o){
            if (o == this) {
                return true;
            }

            if (!(o instanceof ZooKeeperInfo)) {
                return false;
            }

            ZooKeeperInfo c = (ZooKeeperInfo) o;
            return (this.address.equals(c.address)) && (this.key.equals(c.key));
        }

        @Override
        public int compareTo(ZooKeeperInfo o) {
            return this.key.compareTo(o.key);
        }
    }

    /**
     * The list used to store sets of possibly available zookeeper info
     * */
    private List<ZooKeeperInfo> zooKeeperInfos;

    /**
     * This function should define the logic to select the zookeeper based on some agreed sorting methods.
     * For instances of similar purpose, they should be directed to same zookeeper address eventually;
     * */
    private void sortZookeeperInstance(){
        this.zooKeeperInfos.sort(ZooKeeperInfo::compareTo);
    }

    /**
     * Automatically search for available host
     * */
    public void searchingForZookeeperHost() throws InterruptedException {
        while(this.zooKeeperInfos.isEmpty() && Objects.isNull(this.zooKeeper)) {
            System.out.println("Looking for new possible zk Connection");
            //ToDo: add implementation that searching for available zk connection within trusted subnets via ping

            TimeUnit.SECONDS.sleep(this.searchNewZkConnectionInterval);
        }
        return;
    }

    /**
     * Functions to connect to zooKeeper, if all stored address is not available will start 'searchingForZookeeperHost'
     * */
    public void connectToNewZookeeper() throws InterruptedException {

        ZooKeeper zooKeeper = null;
        while(Objects.isNull(zooKeeper)) {
            //In case new address added within two while epochs
            sortZookeeperInstance();

            if (this.zooKeeperInfos.isEmpty()) {

                Runnable runnable = () -> {
                    try {
                        searchingForZookeeperHost();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
                return;
            }

            ZooKeeperInfo zooKeeperInfo = this.zooKeeperInfos.remove(0);
            //if the optimal one is current connection, return
            if (zooKeeperInfo.equals(this.zooKeeperInfo)) {
                return;
            }

            try {
                zooKeeper = new ZooKeeper(zooKeeperInfo.address, this.sessionTimeout, this);
            } catch (IOException e) {
                System.out.println("Address not available: " + zooKeeperInfo.address);
            }

            //wait for a time to establish connection, if still not established, considered as temporary unavailable.
            //CONNECTED: success
            //CONNECTING: failed

            TimeUnit.SECONDS.sleep(this.connectionTimeout);
            //System.out.println(zooKeeper.getState());
            if (zooKeeper.getState().equals(ZooKeeper.States.CONNECTED)) {
                //close connection if old zookeeper connection is not aborted yet
                if (!Objects.isNull(this.zooKeeper)){
                    releaseZookeeperConnection();
                }
                this.zooKeeper = zooKeeper;
                this.zooKeeperInfo = zooKeeperInfo;
                System.out.println("Successfully connected--------------------------------AAAAAAAAAAAAAAAAAAAAAAA");
            } else {
                zooKeeper.close();
                zooKeeper = null;
            }
        }

    }

    /**
     * Stop current zookeeper connection
     * */
    public void releaseZookeeperConnection(){
        try {
            this.zooKeeper.close();
            this.zooKeeper = null;
            this.zooKeeperInfo = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.zooKeeper = null;
        }
    };

    /**
     * Add new zkAddressToList,
     * if already included or currently connected to that zookeeper session will do nothing,
     * if current connection are still the optimal connection will do nothing
     * otherwise restart the connection.
     * */
    public void addZookeeperInfo(String key, String address) throws InterruptedException {
        ZooKeeperInfo zooKeeperInfo= new ZooKeeperInfo(key, address);
        if (zooKeeperInfo.equals(this.zooKeeperInfo)) {
            System.out.println("Already connected to this address");
            return;
        }

        if (this.zooKeeperInfos.contains(zooKeeperInfo)) {
            System.out.println("Already stored this address");
            return;
        }

        /**
         * Even if new connection would replace old instance, the old could still be stored as a backup
         * */
        this.zooKeeperInfos.add(zooKeeperInfo);
        checkConnectionUpdate();
    }

    /**
     * Check if current zookeeper connection is still optimal, if not, update connection.
     */
    private void checkConnectionUpdate() throws InterruptedException {
        if(!Objects.isNull(this.zooKeeperInfo)) {
            this.zooKeeperInfos.add(this.zooKeeperInfo);
        }

        if(this.zooKeeperInfos.isEmpty()) {
            return;
        }

        sortZookeeperInstance();
        if (!Objects.isNull(this.zooKeeperInfo) && this.zooKeeperInfo.equals(this.zooKeeperInfos.get(0))) {
            this.zooKeeperInfos.remove(0);
            return;
        } else {
            connectToNewZookeeper();
        }
    }

    /**
     * Constructor
     * */
    public ZooKeeperManager(int sessionTimeout, int connectionTimeout, int searchNewZkConnectionInterval) {
        this.sessionTimeout = sessionTimeout;
        this.connectionTimeout = connectionTimeout;
        this.searchNewZkConnectionInterval = searchNewZkConnectionInterval;
        this.zooKeeperInfos = new ArrayList<>();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()){
            case None:
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Successfully connected to Zookeeper");
                }
                if (watchedEvent.getState() == Event.KeeperState.Disconnected) {
                    System.out.println("Disconnected from server side.");
                    System.out.println("Disconnected from Zookeeper" + this.zooKeeperInfo.address);
                }
                if (watchedEvent.getState() == Event.KeeperState.Closed) {
                    System.out.println("Disconnected from self side. ---------------------------------------------------");
                }
        }
    }

    public static void main(String args[]) throws InterruptedException {
        ZooKeeperManager zooKeeperManager = new ZooKeeperManager(3000, 5, 1);
        zooKeeperManager.connectToNewZookeeper();

        TimeUnit.SECONDS.sleep(15);
        zooKeeperManager.addZookeeperInfo("2", "localhost:2181");
        TimeUnit.SECONDS.sleep(5);
        zooKeeperManager.addZookeeperInfo("3", "localhost:2181");
        System.out.println("--------------------------------------------------------------------------------------");
        TimeUnit.SECONDS.sleep(5);
        zooKeeperManager.releaseZookeeperConnection();

    }
}
