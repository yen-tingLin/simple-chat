package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;


public class ServerConnection {

    private static DatagramSocket socket;
    private static boolean isRunning;

    // each client has a unique id
    private static int clientID = 0;
    private static ArrayList<ClientInfo> clients = new ArrayList<>();

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

        for(int i = 0; i < clients.size(); i++) {
            ClientInfo client = clients.get(i);
            send(message, client.getAddress(), client.getPort());
        }

    };

    // send message to individual connected clients
    private static void send(String message, InetAddress address, int port) 
    {
        try {
            // for client's benefit to mark where is the end to the 
            // message from server
            message += "-over";
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
                        String message = dataToStr.substring(0, dataToStr.indexOf("-over"));

                        // manage message
                        if(!isCommand(message, dataPacket)) {
                            broadcast(message);
                        }                      
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
        listener.start();
    }

    /* server command list :
     *   '-conn: [name]' : connect client to server
     *   '-disconn: [id]' : disconnect client from server
     */
    private static boolean isCommand(String message, DatagramPacket packet) {
        
        if(message.startsWith("-conn: ")) {
            // run connection code
            String name = message.substring(message.indexOf(": " + 1));

            ClientInfo newClient = new ClientInfo(
                    name, clientID++, packet.getAddress(), packet.getPort());
            clients.add(newClient);

            broadcast(name + " connected, Welcome !");

            return true;

        } else if(message.startsWith("-disconn: ")) {
            // run disconnection code
            clients.removeIf(client -> client.getId().equals(id));
        }

        return false;
    }

    // stop the server without closing the program
    public static void stop() {
        isRunning = false;
    };

    // close the server
    private static void close() {

    };

}
