package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Client {
	
	private DatagramSocket socket;
    private InetAddress address;
    // server's port
    private int port;	
	
	public Client(String address, int port) {
		try {
			this.address = InetAddress.getByName(address);
			this.port = port;
			
			// client dosen't need a specific port, system will
			// randomly assign one for it
			socket = new DatagramSocket();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		sendFromClient("-conn: test");
	}
	
	public void sendFromClient(String message) 
	{
        try {
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
}
