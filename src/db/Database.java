
package db;

import java.sql.*;
import java.util.ArrayList;



public class Database {

	private static Database single = new Database(); 

	String url="jdbc:h2:~/cw"; 
	
	public static Database getInstance() {  
		return single;
	}  

	private Database() {
		try {
			Class.forName("org.h2.Driver");
			System.out.println("h2 run");
			
			Connection conn = DriverManager.getConnection(url, "sa", "sa");
			Statement st = conn.createStatement();
			
//			System.out.println(st.executeUpdate("drop table user"));
			
			String query = "create table IF NOT EXISTS user(id int not null auto_increment primary key,username varchar(255),password varchar(255),displayname varchar(255));";
			int n = st.executeUpdate(query);
			System.out.println(n);
			
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  


	public String register(String username, String password) {
		try {
			Connection conn = DriverManager.getConnection(url, "sa", "sa");
			System.out.println("connect to dababase");
			
			PreparedStatement ps = conn.prepareStatement("select * from user where username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			String res;
			
			if (rs.next()){
				res = "username has been used";
			}else{
				PreparedStatement ps2 = conn.prepareStatement("insert into user (username,password) values(?,?)");
				ps2.setString(1, username);
				ps2.setString(2, password);
				int count = ps2.executeUpdate();
				
				if (count>0) {
					res = "successful";
				}else{
					res = "operation fail";
				}
				ps2.close();
			}
			
			rs.close();
			ps.close();
			conn.close();
			System.out.println("close to dababase");
			
			
			System.out.println(res);
			
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String[] login(String username, String password) {
		
		String[] res = {"",null};
		
		try {
			Connection conn = DriverManager.getConnection(url, "sa", "sa");
			System.out.println("connect to dababase");
			
			PreparedStatement ps = conn.prepareStatement("select * from user where username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			
			
			if (rs.next()){
				
				if (rs.getString(3).equals(password)){
					res[0] = "successful";
					res[1] = rs.getString(4); 
				}else{
					res[0] = "wrong Password";
				}
			}else{
				res[0] = "user does not exist";
			}
			
			rs.close();
			ps.close();
			conn.close();
			System.out.println("close to dababase");
			
			
			System.out.println(res[1]);
			
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public String editDisplayname(String username,String displayName) {
		try {
			
			Connection conn = DriverManager.getConnection(url, "sa", "sa");
			System.out.println("connect to dababase");
			
			PreparedStatement ps = conn.prepareStatement("select * from user where displayname = ?");
			ps.setString(1, displayName);
			ResultSet rs = ps.executeQuery();
			
			String res;
			
			if (rs.next()){
				res = "displayname has been used";
			}else{
				PreparedStatement ps2 = conn.prepareStatement("update user set displayname = '"+displayName+"' where username = ?");
				ps2.setString(1, username);
				int count = ps2.executeUpdate();
				
				if (count>0) {
					res = "successful";
				}else{
					res = "operation fail";
				}
				ps2.close();
			}
			
			rs.close();
			ps.close();
			conn.close();
			System.out.println("close to dababase");
			
			
			System.out.println(res);
			
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	

	public ArrayList<String> allUsers() {
		try {
			
			ArrayList<String> res = new ArrayList<String>();
			
			Connection conn = DriverManager.getConnection(url, "sa", "sa");
			System.out.println("connect to dababase");
			
			PreparedStatement ps = conn.prepareStatement("select * from user");
			ResultSet rs = ps.executeQuery();
		
			
			
			while (rs.next()) {
				res.add(rs.getString(2));
			}
			
			rs.close();
			ps.close();
			conn.close();
			System.out.println("close to dababase");
			
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

    

}
