package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class Client {
	
	private DatagramSocket socket;
    private InetAddress address;
    // server's port
    private int port;
    private boolean isRunning;
    private String name;
	
	public Client(String name, String address, int port) {
		try {
			this.address = InetAddress.getByName(address);
			this.port = port;
			this.name = name;
			
			// client dosen't need a specific port, system will
			// randomly assign one for it
			socket = new DatagramSocket();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		isRunning = true;
		receiveByClient();
		sendFromClient("-conn: " + name);
	}
	
	public void sendFromClient(String message) 
	{
        try {
        	if(!message.startsWith("-")) {
        		message = name + " says : " + message;
        	}
        	
            // for client's benefit to mark where is the end to the 
            // message from server
            message += "-over";
            byte[] messageData = message.getBytes();
            DatagramPacket dataPacket = new DatagramPacket(
                        messageData, messageData.length, address, port);
            socket.send(dataPacket);
            System.out.println("[Client] send message from " + 
                        address.getHostAddress() + ", to server port: " + port);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        		
	}
	
    // create a new thread which waits for messages from clients	
	public void receiveByClient() {
        Thread listener = new Thread("Client listener") {
            public void run() {
                try {
                    while(isRunning) {
                        // put received data into dataPacket, and dataPacket then write  
                        // data into messageData byte array                        
                        byte[] messageData = new byte[1024];
                        DatagramPacket dataPacket = new DatagramPacket(messageData, messageData.length);
                        socket.receive(dataPacket);

                        // extract message from data
                        String dataToStr = new String(messageData);
                        String message = dataToStr.substring(0, dataToStr.indexOf("-over"));

                        // manage message
                        if(!isCommand(message, dataPacket)) {
                            ClientWindow.printToConsole("receive message: <" + message + "> from " + 
                            			dataPacket.getAddress() + ": " + dataPacket.getPort());
                            
                        } 
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
        listener.start();
	}
	
    private static boolean isCommand(String message, DatagramPacket packet) {
        
        if(message.startsWith("-conn: ")) {
            // run connection code
        	

        } else if(message.startsWith("-disconn: ")) {
            // run disconnection code
            // clients.removeIf(client -> client.getId().equals(id));
        }

        return false;
    }
	
	
}
