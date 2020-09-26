package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerConnectionImp {

    private static DatagramSocket socket;
    private static boolean isRunning;

    // start the server up
    public static void start(int port) {
        try {
            socket = new DatagramSocket(port);

            isRunning = true;
            listen();
            System.out.println("[Server] started on port " + port);

        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    // send message to every connected clients
    private static void broadcast(String message) {

    };

    // send message to individual connected clients
    private static void send(String message, InetAddress address, int port) 
    {
        try {

        } catch(Exception e) {

        }
    };

    // create a new thread which waits for messages from clients
    private static void listen() {

    }

    // stop the server without closing the program
    public static void stop() {

    };

    // close the server
    private static void close() {

    };

}
