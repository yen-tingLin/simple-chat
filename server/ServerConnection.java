package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerConnection {

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
            // for client's benefit to mark where is the end to the 
            // message from server
            message += "//over";
            byte[] messageData = message.getBytes();
            DatagramPacket dataPacket = new DatagramPacket(
                        messageData, messageData.length, address, port);
            socket.send(dataPacket);
            System.out.println("[Server] send message to " + 
                        address.getHostAddress() + " : " + port);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    };

    // create a new thread which waits for messages from clients
    private static void listen() {
        Thread listener = new Thread("Thread listener") {
            public void run() {
                try {
                    while(isRunning) {
                        // put received data into dataPacket, and dataPacket then write  
                        // data into clientData byte array                        
                        byte[] clientData = new byte[1024];
                        DatagramPacket dataPacket = new DatagramPacket(clientData, clientData.length);
                        socket.receive(dataPacket);

                        // extract message from data
                        String dataToStr = new String(clientData);
                        String message = dataToStr.substring(0, dataToStr.indexOf("//over"));

                        // manage message
                        broadcast(message);
                        
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
        listener.start();
    }

    // stop the server without closing the program
    public static void stop() {

    };

    // close the server
    private static void close() {

    };

}
