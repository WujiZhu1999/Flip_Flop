package Connection.LeaderElection;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class JobLeader implements ElectJobLeader, Watcher {

    private ZooKeeper zooKeeper;
    private final int sessionTimeout;
    private final String serviceNameSpace;
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
     * Used to store a bunch of available zookeeper instance
     * */
    private List<ZooKeeperInfo> zooKeeperInfos;
    public void addZookeeperInstance(String key, String address) {
        //ToDo: add verification on address
        ZooKeeperInfo zooKeeperInfo = new ZooKeeperInfo(key, address);
        if (!this.zooKeeperInfos.contains(zooKeeperInfo)) {
            this.zooKeeperInfos.add(zooKeeperInfo);
        }
    }

    /**
     * This function should provide logic to select the zookeeper based on some agreed sorting methods,
     * for same service name, they should be directed to same zookeeper instance
     * */
    private void sortZookeeperInstance(){
        this.zooKeeperInfos.sort(ZooKeeperInfo::compareTo);
    }

    /**
     * Waiting for message from other instance saying that they are available for zookeeper hosting
     * */
    public void waitingForZookeeperHost(int connectionTimeout, int searchNewZkConnectionInterval) {

        while(this.zooKeeperInfos.isEmpty() && Objects.isNull(this.zooKeeper)){
            //ToDo: logic to broadcast over network to find available zookeeper host
            System.out.println("Waiting for zookeeper hosting");
            try {
                TimeUnit.SECONDS.sleep(searchNewZkConnectionInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        try {
            connectToNewZookeeper(connectionTimeout, searchNewZkConnectionInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * Connect New to a zookeeper instance
     * */
    public void connectToNewZookeeper(int connectionTimeout, int searchNewZkConnectionInterval) throws InterruptedException {
        //close connection if old zookeeper connection is not aborted yet
        if (!Objects.isNull(this.zooKeeper)){
            releaseZookeeperConnection();
        }

        ZooKeeper zooKeeper = null;
        while(Objects.isNull(zooKeeper)) {

            if (this.zooKeeperInfos.isEmpty()) {
                Runnable runnable = () -> {waitingForZookeeperHost(connectionTimeout, searchNewZkConnectionInterval);};
                Thread thread = new Thread(runnable);
                thread.start();
                return;
            }

            ZooKeeperInfo zooKeeperInfo = this.zooKeeperInfos.remove(0);
            try {
                zooKeeper = new ZooKeeper(zooKeeperInfo.address, sessionTimeout, this);
            } catch (IOException e) {
                System.out.println("Address not available: " + zooKeeperInfo.address);
            }

            //wait for a time to establish connection, if still not established, considered as temporary unavailable.
            //CONNECTED: success
            //CONNECTING: failed

            TimeUnit.SECONDS.sleep(connectionTimeout);
            System.out.println(zooKeeper.getState());
            if (zooKeeper.getState().equals(ZooKeeper.States.CONNECTED)) {
                this.zooKeeper = zooKeeper;
            } else {
                zooKeeper.close();
                zooKeeper = null;
            }
        }

    }

    private void releaseZookeeperConnection(){

    }



    public static void main(String args[]) throws InterruptedException {
        JobLeader jobLeader = new JobLeader("test", 3000);
        jobLeader.addZookeeperInstance("k1","localhost:2182");
        jobLeader.connectToNewZookeeper(5, 1);
        //jobLeader.addZookeeper("localhost:2181",3000);
        //jobLeader.waitForAddress(5);

        TimeUnit.SECONDS.sleep(15);

        System.out.println("reached this line------------------------------------------------------");
        jobLeader.addZookeeperInstance("k1","localhost:2181");

        System.out.println("reached this line------------------------------------------------------");
        TimeUnit.SECONDS.sleep(15);
    }






    private List<String> zooKeeperAddress;



    public JobLeader(String serviceNameSpace, int sessionTimeout){
        this.serviceNameSpace = serviceNameSpace;
        this.sessionTimeout = sessionTimeout;
        this.zooKeeperInfos = new ArrayList<>();
    }

    /**
     * This function should be called when some zooKeeper server is online
     * */
    public void addZookeeperAddress(String zooKeeperAddress) {
        //ToDo: should add some verification on address
        this.zooKeeperAddress.add(zooKeeperAddress);
    }

    private void startZookeeper(String zooKeeperAddress, int sessionTimeOut) throws InterruptedException {
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(zooKeeperAddress, sessionTimeOut, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Objects.isNull(zooKeeper)) {
            System.out.println("Failed to establish connection with zookeeper server");
        } else {
            System.out.println("Add new zooKeeper");
            this.zooKeeper = zooKeeper;
            synchronized (zooKeeper) {
                zooKeeper.wait();
            }
        }
    }
    @Override
    public void volunteerForLeadership(ZooKeeper zooKeeper, String serviceNameSpace) {

    }

    @Override
    public void reeletLeader(ZooKeeper zooKeeper, String serviceNameSpace) {

    }


    /**
     * If Zookeeper address not available
     * */
    @Override
    public void selectZookeeperServer() throws InterruptedException {
        ZooKeeper zooKeeper = null;
        while(!zooKeeperAddress.isEmpty() && Objects.isNull(zooKeeper)){
            String address = zooKeeperAddress.remove(0);

            try {
                zooKeeper = new ZooKeeper(address, sessionTimeout, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(Objects.isNull(zooKeeper)) {
                System.out.println("Address " + address + " aborted.");
            } else {
                System.out.println("Add new zooKeeper:" + address);
                this.zooKeeper = zooKeeper;
                synchronized (zooKeeper) {
                    zooKeeper.wait();
                }
            }

        }
        //no zookeeper address available. dead to be finished soon
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()){
            case None:
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Successfully connected to Zookeeper");
                }
                if (watchedEvent.getState() == Event.KeeperState.Disconnected) {
                    synchronized (zooKeeper) {
                        //this function will wake up all threads that are in waiting stage
                        //which will make the main thread keep going
                        System.out.println("Disconnected from Zookeeper event");
                        try {
                            zooKeeper.close();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }
}
