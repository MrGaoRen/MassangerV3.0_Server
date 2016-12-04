package server;

import model.*;
import java.util.HashMap;

public class MakeResponse extends AllResponse{

	@Override
	public Datagram login(String mesg) {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("result", mesg);

		Datagram res = new Datagram("replylogin",data);
		return res;
		

			
	}

	@Override
	public Datagram register(String mesg) {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("result", mesg);

		Datagram res = new Datagram("replyregister",data);
		return res;
			
		
	}


	@Override
	public Datagram getgroupmember(HashMap<String, String> data) {
		Datagram res = new Datagram("replygetgroupmember",data);
		return res;
		// TODO Auto-generated method stub

	}


//	@Override
//	public Datagram sendfriendmessage(String mesg) {
//		HashMap<String, String> data = new HashMap<String, String>();
//		data.put("result", mesg);
//
//		Datagram res = new Datagram("replylogin",data);
//		return res;
//			
//	
//	}
//
//	@Override
//	public Datagram sendgroupmessage(String mesg) {
//		HashMap<String, String> data = new HashMap<String, String>();
//		data.put("result", mesg);
//
//		Datagram res = new Datagram("replysendgroupmessage",data);
//		return res;
//			
//	
//	}
//
//	@Override
//	public Datagram sendfriendfile(String mesg) {
//		HashMap<String, String> data = new HashMap<String, String>();
//		data.put("result", mesg);
//
//		Datagram res = new Datagram("replysendfriendfile",data);
//		return res;
//
//	}

	@Override
	public Datagram friendmessagerelay(HashMap<String, String> data) {
		Datagram res = new Datagram("friendmessagerelay",data);
		return res;
		// TODO Auto-generated method stub

	}

	@Override
	public Datagram groupmessagerelay(HashMap<String, String> data) {
		Datagram res = new Datagram("groupmessagerelay",data);
		return res;
		// TODO Auto-generated method stub
	}

	@Override
	public Datagram friendfilerelay(HashMap<String, String> data) {
		Datagram res = new Datagram("friendfilerelay",data);
		return res;
		// TODO Auto-generated method stub
	
	}

	@Override
	public Datagram newdisplayname(String mesg) {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("result", mesg);

		Datagram res = new Datagram("replynewdisplayname",data);
		return res;
	}

	

}
