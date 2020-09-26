package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ClientWindow {

	private JFrame frame;
	private JTextField messageField;
	private static JTextArea textArea = new JTextArea();
	
	private Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());					
					ClientWindow window = new ClientWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
		// same as the port number that pass to server start()
		client = new Client("localhost", 5477);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 667, 461);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setTitle("Chat Program\n");
		
		textArea.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		messageField = new JTextField();
		panel.add(messageField);
		messageField.setColumns(50);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnSend);			
		
		frame.setLocationRelativeTo(null);

	}
	
	public static void printToConsole(String message) {
		textArea.setText(textArea.getText() + message + "\n");
		
	}

}