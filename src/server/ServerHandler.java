package server;

import model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import db.*;;

public class ServerHandler {

	private MakeResponse responsemaker = new MakeResponse();
	private RunServer server;
	private ClientSocket socket;
	private DateFormat format;
	private Date date;

	public  HashMap<String, String> getinformation(Datagram data){
		return data.getdatagram().get("information");

	}

	public  HashMap<String, String> getdata(Datagram data){
		return data.getdatagram().get("data");

	}
	public  String getname(Datagram data){
		return getinformation(data).get("name");

	}


	public ServerHandler(RunServer server,ClientSocket socket) {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.socket = socket;

		date=new Date();
		format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}


	public Datagram process(Datagram data){
		Datagram out = null;

		switch (getname(data)){
		case "requestlogin":  out = handleLogin(data); break;
		case "requestregister": out = handleRegister(data);break;
		case "requestgetgroupmember": ;	out = handleGetGroup(data);break;
		case "requestsendfriendmessage":out = handleSendFriendMessage(data);break;
		case "requestsendgroupmessage":out = handleSendGroupMessage(data);break;
		case "requestnewdisplayname":out = handleEditNickname(data);break;
		case "requestsendfriendfile": out = handleSendFriendFile(data);
		}
		return out;				
	}


	// write all request handler
	// saisai wang's job


	public  Datagram handleLogin(Datagram indata){
		//		System.out.println(indata.getdatagram().size());

		HashMap<String, String> data = getdata(indata);
		String[] re = Database.getInstance().login(data.get("username"), data.get("password"));
		Datagram  out =  responsemaker.login(re[0]);

		if (re[0].equals("successful")){

			socket.name = data.get("username");
			socket.nickname = re[1];
			server.setText(socket.name+ "   login");
			server.onlineUsers.add(socket);
			
			

			Datagram groupData = handleGetGroup(null);
			
			
			
			for (ClientSocket socket : server.onlineUsers){
				if ( socket.name != null &&  !socket.name.equals(this.socket.name)  )  {
	
					socket.senddatagram( groupData);
				}
			}

		}

		// return the result
		return out;
	}

	
	
	

	public  Datagram handleRegister(Datagram indata){

		HashMap<String, String> data = getdata(indata);
		Datagram  out =  responsemaker.register(Database.getInstance().register(data.get("username"), data.get("password")));



		// return the result
		return out;

	}
	public  Datagram handleEditNickname(Datagram indata){

		HashMap<String, String> data = getdata(indata);
		Datagram  out =  responsemaker.newdisplayname(Database.getInstance().editDisplayname(data.get("username"), data.get("nickname")));

		// return the result
		return out;

	}

	public  Datagram handleSendFriendMessage(Datagram indata){

		HashMap<String, String> data = getdata(indata);

		Datagram  out = null;

		for (ClientSocket socket : server.onlineUsers){
			if (socket.name.equals(data.get("dst"))){
				
//				HashMap<String, String> rdata = new HashMap<String, String>();

//				rdata.put("rname", this.socket.name);
//				rdata.put("msg",data.get("mesg"));
////				rdata.put("rtime", format.format(date));

				out = responsemaker.friendmessagerelay(data);
				socket.senddatagram( out);
//				break;
			}
		}
		

		if (out == null){
//			out = responsemaker.sendfriendmessage("fail for offline");
			server.setText(socket.name + "send message to "+ data.get("dst") + " fail for offline");
//			return out;
		}
		out = null;
		// return the result
		return out;

	}
	
	public  Datagram handleSendFriendFile(Datagram indata){

		HashMap<String, String> data = getdata(indata);

		Datagram  out = null;

		for (ClientSocket socket : server.onlineUsers){
			if (socket.name.equals(data.get("dst"))){
				
//				HashMap<String, String> rdata = new HashMap<String, String>();

//				rdata.put("rname", this.socket.name);
//				rdata.put("msg",data.get("mesg"));
////				rdata.put("rtime", format.format(date));

				out = responsemaker.friendfilerelay(data);
				socket.senddatagram( out);
//				break;
			}
		}
		

		if (out == null){
//			out = responsemaker.sendfriendmessage("fail for offline");
			server.setText(socket.name + "send message to "+ data.get("dst") + " fail for offline");
		}
		out = null;
		// return the result
		return out;

	}
	
	

	public  Datagram handleSendGroupMessage(Datagram indata){

		HashMap<String, String> data = getdata(indata);

		Datagram  out = null;

		for (ClientSocket socket : server.onlineUsers){
			if ( socket.name != null &&  !socket.name.equals(this.socket.name)  )  {
//				out = responsemaker.sendfriendmessage("sucesss");            
				HashMap<String, String> rdata = new HashMap<String, String>();

//				rdata.put("rname", this.socket.name);
//				rdata.put("msg",data.get("mesg"));
//				rdata.put("rtime", format.format(date));

				out = responsemaker.groupmessagerelay(data);
				socket.senddatagram(out);
			}

		}
		out = null;

		//		if (out == null){
		//			out = responsemaker.sendfriendmessage("fail for offline");
		//	        server.setText(socket.name + "send message to "+ data.get("dst") + " fail for offline");
		//		}

		// return the result
		return out;

	}


	public  Datagram handleGetGroup(Datagram indata){

		//		HashMap<String, String> data = getdata(indata);
		Datagram  out =  null;
		HashMap<String, String> rdata = new HashMap<String, String>();

		for (ClientSocket socket : server.onlineUsers){
			if ( socket.name != null) rdata.put(socket.name,socket.nickname);
		}

		out = responsemaker.getgroupmember(rdata);
		// return the result
		return out;

	}


}
