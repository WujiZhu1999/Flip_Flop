package Connection.Host;

import java.io.*;
import java.util.Scanner;

public class Host {
    private static String targetConfigPath = "./Connection/Host/apache-zookeeper-3.8.0-bin/conf/zoo.cfg";
    private static String sampleConfigPath = "./Connection/Host/apache-zookeeper-3.8.0-bin/conf/zoo_sample.cfg";
    private static String zookeeperServerPath = "./Connection/Host/apache-zookeeper-3.8.0-bin/bin/zkServer.sh";

    public static void main(String args[]) throws IOException {
        writeZooConfigFile(5, 2181, 60);
        runZookeeperServer();
        stopZookeeperServer();
    }

    private Host(int syncLimit, int port, int maxClientCnxns){
        writeZooConfigFile(5, 2181, 60);
    }

    /**
     * Run zookeeper server and stop zookeeper server on command line
     * */
    private static void runZookeeperServer() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(zookeeperServerPath + " start");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String s = null;
         while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
         }
        /**
         *         BufferedReader stdInput = new BufferedReader(new
         *                 InputStreamReader(pr.getInputStream()));
         *
         *         String s = null;
         *         while ((s = stdInput.readLine()) != null) {
         *             System.out.println(s);
         *         }
         * */
    };

    private static void stopZookeeperServer() throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(zookeeperServerPath + " stop");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
        /**
         *         BufferedReader stdInput = new BufferedReader(new
         *                 InputStreamReader(pr.getInputStream()));
         *
         *         String s = null;
         *         while ((s = stdInput.readLine()) != null) {
         *             System.out.println(s);
         *         }
         * */
    }

    /**
     * Define zookeeper config
     * */
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