package server;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.jar.Attributes.Name;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import db.Database;



public class RunServer {
	
	private JFrame frame;
	private JPanel panelMain;
	private JPanel panelDown;
	private JTextArea ta;
	private JTextField txt;
	private JButton but;
	private JScrollPane jsp;
	private Font font;
	
	
	final static int port = 6666;
	
	public static void main(String[] arg){
		 new RunServer();
	}
	
	Vector<ClientSocket> onlineUsers = new Vector<ClientSocket>();
	
	public RunServer() {
		// TODO Auto-generated constructor stub
		
		serverUI();
		startServer();
		
	}
	
	public void startServer(){
		setText("Server start");
		
		try {
			ServerSocket myServerSocket = new ServerSocket(port);
			while (true){
				
				Socket socket = myServerSocket.accept();
				setText("Find new connection");
//				showConnectivity(socket);
				 
				new ClientSocket(socket, onlineUsers, this);
			}
			
			
		}catch (IOException ex){
			ex.printStackTrace();
		}
		
	}
	
	
	public  void showConnectivity(Socket socket){
		if (socket!=null){
//			setText("local ip:" + socket.getLocalAddress());
//			setText("local port:" + socket.getLocalPort());
//			setText("server ip:" + socket.getInetAddress());
			setText("server port:" + socket.getPort());
		}
		else{
			setText("error");
		}
	}	

	public void setText(String msg) {
		ta.append(msg+"\n");
	}
	public  void serverUI() {
		
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int scrrenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		frame = new JFrame("server");
		panelMain = new JPanel(new BorderLayout());
		panelDown = new JPanel();
		ta = new JTextArea();
		txt = new JTextField(20);
		but = new JButton("seach");
		jsp = new JScrollPane(ta);
		
		panelDown.add(txt);
		panelDown.add(but);
		panelMain.add(jsp, BorderLayout.CENTER);
		panelMain.add(panelDown, BorderLayout.SOUTH);
		font = new Font("Helvetica", Font.BOLD, 18);
		txt.setFont(font);
		ta.setFont(font);
		but.setFont(font);
		ta.setEditable(false);
		
		but.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				switch (txt.getText()) {
				case "clear":
					setText("");
					break;
				case "allUser":
					for (String name : Database.getInstance().allUsers()){
						setText(name);
					}
					break;
				case "allOUser":
					for (ClientSocket socket : onlineUsers ){
						setText(socket.name);
					}
					break;
				default:
					setText(txt.getText());
					break;
				}
			}
		});
		
		
		frame.add(panelMain);
		frame.setBounds(screenWidth/2-300, scrrenHeight/2-300, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		
//		dengJiBiao = new Vector<Waiter>();

		txt.requestFocus();
	}
	



}
