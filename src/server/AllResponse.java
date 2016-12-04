package server;

import java.util.HashMap;
import model.*;


public abstract class AllResponse {
	//reply login mesg: success, password incorrect, username not found, unknown error
	public abstract Datagram login(String mesg);
	
	//reply register mesg: success, username existing, unknown error
	public abstract Datagram register(String mesg);
	
	
	//datahashmap:groupmember_1, groupmembername	
	public abstract Datagram getgroupmember(HashMap<String, String> data);	
	
	public abstract Datagram newdisplayname(String mesg);
	
//	//reply sendfriendmessage mesg: success, friend not online, unknown error
//	public abstract Datagram sendfriendmessage(String mesg);	
//	
//	//datahashmap:groupmember_1, groupmembername	
//	public abstract Datagram sendgroupmessage(String mesg);	
//	
//	//reply sendfriendfile mesg: success, unknown error	
//	public abstract Datagram sendfriendfile(String mesg);

	
	
	//friendmessagerelay 
	//data: from previous
	public abstract Datagram friendmessagerelay(HashMap<String, String> data);
	
	//groupmessagerelay
	//data: from previous
	public abstract Datagram groupmessagerelay(HashMap<String, String> data);
	
	//friendfilerelay
	//data: from previous
	public abstract Datagram friendfilerelay(HashMap<String, String> data);	
	

}
