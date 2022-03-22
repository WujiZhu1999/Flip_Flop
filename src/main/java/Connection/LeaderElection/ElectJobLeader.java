package Connection.LeaderElection;

import Connection.Roles.ZookeeperHost;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public interface ElectJobLeader {
    public void volunteerForLeadership(ZooKeeper zooKeeper, String serviceNameSpace);
    public void reeletLeader(ZooKeeper zooKeeper, String serviceNameSpace);
    public void selectZookeeperServer() throws InterruptedException;
}
