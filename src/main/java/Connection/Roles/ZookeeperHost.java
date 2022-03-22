package Connection.Roles;


import java.io.*;

public class ZookeeperHost{
    private static String targetConfigPath = "./Connection/Zookeeper/apache-zookeeper-3.8.0-bin/conf/zoo.cfg";
    private static String sampleConfigPath = "./Connection/Zookeeper/apache-zookeeper-3.8.0-bin/conf/zoo_sample.cfg";
    private static String zookeeperServerPath = "./Connection/Zookeeper/apache-zookeeper-3.8.0-bin/bin/zkServer.sh";
    private static int ZkPort = 2181;

    /**
     * (Placeholder) Function to allow/disable instance accept zookeeper connection from other instance
     * */
    public static void registerZookeeperAccess(String IP_Address){};

    public static void unregisterZookeeperAccess(String IP_Address){};
    /**
     * Used to start and stop zookeeper server on one instance. Default Port is 2181
     * */
    public static void startZookeeperHost(int syncLimit, int port, int maxClientCnxns) throws IOException {
        writeZooConfigFile(syncLimit, port, maxClientCnxns);
        runZookeeperServer();
    }

    public static void stopZookeeperHost() throws IOException {
        stopZookeeperServer();
    }



    private static void runZookeeperServer() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(zookeeperServerPath + " start");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
    }

    private static void stopZookeeperServer() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(zookeeperServerPath + " stop");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
    }

    public static void main(String args[]) throws IOException {
        startZookeeperHost(5, ZkPort, 60);
        //stopZookeeperHost();
    }

    private static void writeZooConfigFile(int syncLimit, int port, int maxClientCnxns){
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            br = new BufferedReader(new FileReader(sampleConfigPath));
            bw = new BufferedWriter(new FileWriter(targetConfigPath));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("syncLimit")){
                    line = "syncLimit=" + syncLimit;
                } else if (line.startsWith("clientPort")) {
                    line = "clientPort=" + port;
                } else if (line.startsWith("maxClientCnxns")) {
                    line = "maxClientCnxns=" + maxClientCnxns;
                }
                bw.write(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
                //
            }
            try {
                if(bw != null)
                    bw.close();
            } catch (IOException e) {
                //
            }
        }
    }
}
