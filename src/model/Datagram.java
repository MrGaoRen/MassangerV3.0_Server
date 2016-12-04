package model;

import java.io.Serializable;
import java.util.HashMap;

public class Datagram implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, HashMap<String, String>> datagram = new HashMap<String, HashMap<String, String>>();
	
	public Datagram(String name, HashMap<String, String> data){
		HashMap<String, String> sigledatagram = new HashMap<String, String>();
		sigledatagram.put("name", name);
		datagram.put("information", sigledatagram);
		datagram.put("data", data);
	}
	
	public HashMap<String, HashMap<String, String>> getdatagram(){
		return datagram;
	}
	
	
	
	


}
