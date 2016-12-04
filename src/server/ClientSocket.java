package server;

import model.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;



public class ClientSocket extends Thread{
	private Vector<ClientSocket> onlineUsers ;
	private RunServer server = null;



	private  ObjectInputStream objectInput = null; 
	private  ObjectOutputStream objectOutput = null;
	private  Datagram tmp = null;
	private ServerHandler sh = null;


	String name = null;
	String nickname = null;
	int port;

	public ClientSocket(Socket socket,Vector<ClientSocket> onlineUsers,RunServer server) {
		// TODO Auto-generated constructor stub

		this.onlineUsers = onlineUsers;
		this.server = server;

		sh = new ServerHandler(server,this);
		port = socket.getPort();

		try {
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectInput = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			this.server.setText("client: "+port+" connect fail");
//			e.printStackTrace();
			return;
		}

		this.server.setText("client :  "+port+" success");
		this.start();
	}

	public void run(){

		//onlineUsers.addElement(this);

		try {
			try {

				while ((tmp = (Datagram)objectInput.readObject()) != null) {
					Datagram data; 
					if((data=sh.process(tmp)) != null) senddatagram(data);	
				}

			} catch (ClassNotFoundException e) {
				server.setText(name+"  "+e.getMessage());
			}
		} catch (IOException e) {
			if (name != null){
				server.setText(name+" leave");
			}else{
				server.setText(port+" leave");
			}
		}
		
		
		onlineUsers.removeElement(this);
	}

	public  void senddatagram(Datagram data){
		try {
			objectOutput.writeObject(data);
			objectOutput.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.server.setText("client :  "+port+"sendData fail");
		}
	}
}
