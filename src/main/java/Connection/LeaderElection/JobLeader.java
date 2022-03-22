package Connection.LeaderElection;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobLeader implements ElectJobLeader, Watcher {
    private final String serviceNameSpace;
    private static int sessionTimeout = 3000;
    private ZooKeeper zooKeeper;
    private List<String> zooKeeperAddress;

    public static void main(String args[]) throws InterruptedException {
        JobLeader jobLeader = new JobLeader("test");
        jobLeader.addZookeeper("localhost:2181",3000);

    }

    public JobLeader(String serviceNameSpace){
        this.serviceNameSpace = serviceNameSpace;
        this.zooKeeperAddress = new ArrayList<>();
    }

    /**
     * This function should be called when some zooKeeper server is online
     * */
    private void addZookeeperAddress(String zooKeeperAddress) {
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
        //no zookeeper. dead to be finished soon
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
                        this.zooKeeper.notifyAll();
                        this.zooKeeper = null;
                    }
                }
        }
    }
}
