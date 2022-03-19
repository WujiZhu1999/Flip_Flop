package Connection.Host;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ConnectToHost implements Watcher {
    private static final String ZOOKEEPER_ADDRESS = "13.239.121.161:2181";
    private static final int SESSION_TIMEOUT = 3000;
    private ZooKeeper zooKeeper;

    public static void main(String args[]) throws IOException, InterruptedException {
        ConnectToHost connectToHost = new ConnectToHost();
        connectToHost.connectToZookeeper();
        connectToHost.run();
        connectToHost.close();
    }

    public void connectToZookeeper() throws IOException {
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
    }

    public void run() throws InterruptedException {
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    public void close() throws InterruptedException {
        //this will close the zooKeeper which still have event thread and io thread running
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Successfully connected to Zookeeper");
                } else {
                    synchronized (zooKeeper) {
                        //this function will wake up all threads that are in waiting stage
                        //which will make the main thread keep going
                        System.out.println("Disconnected from Zookeeper event");
                        zooKeeper.notifyAll();
                    }
                }

        }
    }
}
