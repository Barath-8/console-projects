package controller;


import java.sql.*;

public class DatabaseController {
	
	private Connection sql;   
	
	public DatabaseController() throws SQLException{
	   sql = DriverManager.getConnection("jdbc:mysql://localhost:3306/contacts","root","Barathvini@1");
	}

	
	public boolean addContact(String name,String phone,String lable,String mail) {
		
		try {
			
			PreparedStatement add = sql.prepareStatement("insert into contact values(?,?,?,?)");
			
			add.setString(1, name);
			add.setString(2, phone);
			add.setString(3, lable);
			add.setString(4, mail);
			
			int  res = add.executeUpdate();
			
			if(res==1) {
				System.out.println(" 1 record inserted");
				return true;
			}
			
			
		} catch (SQLException e) {
			
			System.out.println("contact already exists!!!");
			//e.printStackTrace();
		}
		
		return false;

	}
	
	public void displayAllContacts() {
		
		try {
			
			PreparedStatement showAll = sql.prepareStatement("select * from contact order by Name");
			
			ResultSet user = showAll.executeQuery();
			
			if(!user.next()) {
				System.out.println("No one exist ");
				System.out.println("Try creating one !!");
				return ;
			}
			
			System.out.println("\n******************************************************************************************************");
			System.out.printf("\n||       Name       |      Phone       |    lable    |                     Email                     ||");
			System.out.println("\n******************************************************************************************************");
			System.out.printf("\n||  %-15s |  %-13s   |  %-10s  |           %-30s     ||",user.getString(1),user.getString(2),user.getString(3),user.getString(4));
			
			while(user.next()) 
				System.out.printf("\n||  %-15s |  %-13s   |  %-10s  |           %-30s     ||",user.getString(1),user.getString(2),user.getString(3),user.getString(4));
			
			System.out.println("\n********************************************************************************************************");
			
		} catch (SQLException e) {
			System.err.println("Contact display Error!!");
			e.printStackTrace();
		}
		
	}
	
	public String searchby(String type,String val) {
		
			String phone ="";
					
		try {
			PreparedStatement search = sql.prepareStatement(String.format("select * from contact where %s like '%s';",type,val));
			ResultSet user = search.executeQuery();
			

			if(!user.next()) {
				System.out.println("No one exist with this "+type);
				System.out.println("Try creating one !!");
				return "";
			}
			
		 
			phone =user.getString(2);
			
			System.out.println("\n************************************************************************************************");
			System.out.printf("\n||       Name       |      Phone       |    lable    |                  Email                  ||");
			System.out.println("\n_________________________________________________________________________________________________");
			
			System.out.printf("\n||  %-15s |  %-13s   |  %-10s  |          %-25s     ||",user.getString(1),phone,user.getString(3),user.getString(4));
			while(user.next())
				System.out.printf("\n||  %-15s |  %-13s   |  %-10s  |          %-25s     ||",user.getString(1),user.getString(2),user.getString(3),user.getString(4));
			
			System.out.println("\n************************************************************************************************");
			
		} catch (SQLException e) {
			
			System.out.println("\nContact doesn't exists ");
		}
		
		return phone;
	}
	
	public void delete(String table,String type,String val) {
		
		try {
			PreparedStatement delete = sql.prepareStatement(String.format("delete from %s where %s = '%s';",table,type,val));
			
			int res = delete.executeUpdate();

			if(res == 1) 
				System.out.println("Deletion Successfull");
			else
				System.out.println(res+" quantity deleted");
						
			
		} catch (SQLException e) {
			
			System.err.println("Delete Error!!");
		}
		
		
		
	}
	
	public void editBy(String edittype, String set ,String type, String phone) {
		
		try {
			PreparedStatement edit = sql.prepareStatement(String.format("update contact set %s='%s' where %s='%s'",edittype,set,type,phone));
			
			if(edit.executeUpdate() == 1) {
				
				System.out.println("\n*********Succesfully edited contact***********"
									+ "\n_____________Showing changes______________");
				
				searchby(edittype,set);
				
			}else
				System.out.println("Duplicate contact exits filter properly");
			
		
		} catch (SQLException e) {
			System.out.println("Something went wrong in edit");
		}
	}
	
	public boolean addsms(String phone,String msg) {
		
		try {
			
			PreparedStatement sms = sql.prepareStatement("insert into smshistory (phone,sms) values(?,?)");
			
			sms.setString(1, phone);
			sms.setString(2, msg);
			
			int  res = sms.executeUpdate();
			
			if(res==1) {
				System.out.println("SMS sent Successfully");
				return true;
			}
			
			
		} catch (SQLException e) {
			
			System.out.println("contact already exists!!!");
			//e.printStackTrace();
		}
		
		return false;

	}
	
	public void viewsms(String phone) {
		
		try {
			
			PreparedStatement sms;
			if(phone.length()>3) {
			sms= sql.prepareStatement("select contact.name,smshistory.phone,smshistory.sms,smshistory.time from contact right join smshistory on contact.phone=smshistory.phone where smshistory.phone =? order by time desc");
			sms.setString(1, phone);
			}else
				sms= sql.prepareStatement("select contact.name,smshistory.phone,smshistory.sms,smshistory.time from contact right join smshistory on contact.phone=smshistory.phone order by time desc");
				
			ResultSet user = sms.executeQuery();
			

			if(!user.next()) {
				
				System.out.println("You haven't sent any Messages to this "+phone);
				System.out.println("Try sending one !!");
				return ;
			}
			
			System.out.println("\n***********************************************************************");
			System.out.printf("\n||  Name        |  Phone        |  Time             ");
			System.out.println("\n________________________________________________________________________");
			
			System.out.printf("\n\n|| %-12s| %-13s |  %-15s \n\n-%s",user.getString(1),user.getString(2),user.getString(4),user.getString(3));
			while(user.next()) {
				System.out.println("\n____________________________________________________________________");
				
				System.out.printf("\n\n|| %-12s| %-13s  |  %-15s \n\n-%s",user.getString(1),user.getString(2),user.getString(4),user.getString(3));
			}
			
			System.out.println("\n*************************************************************************");
			
			
		} catch (SQLException e) {
			
			System.err.println("SMS ERROR!!!");
			//e.printStackTrace();
		}
		

	}
	
	public boolean addCallLog(String phone,String duration) {
		
		try {
			
			PreparedStatement call = sql.prepareStatement("insert into calling (phone,duration) values(?,?)");
			
			call.setString(1, phone);
			call.setString(2, duration);
			
			int  res = call.executeUpdate();
			
			if(res==1) {
				System.out.println("Call Ended Successfully");
				return true;
			}
			
			
		} catch (SQLException e) {
			
			System.out.println("Calling error!!!");
			//e.printStackTrace();
		}
		
		return false;

	}
	
	public void viewCallLog(String phone) {
		
		try {
			
			PreparedStatement callLog;
			
			if(phone.length()>3) {
				callLog= sql.prepareStatement(
					"select contact.name,calling.phone,calling.call_log,calling.duration from contact right join calling on contact.phone =calling.phone where calling.phone=? order by call_log desc");
				callLog.setString(1, phone);
			}else
				callLog= sql.prepareStatement(
						"select contact.name,calling.phone,calling.call_log,calling.duration from contact right join calling on contact.phone =calling.phone order by call_log desc");
					
				
			
			
			ResultSet user = callLog.executeQuery();
			

			if(!user.next()) {
				if(phone.length()>3) {
				System.out.println("No call Log exist with this "+phone);
				System.out.println("Try calling him/her !!");
				}else {
					System.out.println("You havent call anyone");
				}
				return ;
			}
			
			System.out.println("\n**********************************************************************");
			System.out.printf("\n||  Name        |  Phone        |  duration     | call_Log           ||");
			System.out.println("\n______________________________________________________________________");
			
			System.out.printf("\n\n|| %-13s| %-13s |  %-10s | %s |",user.getString(1),user.getString(2),user.getString(4),user.getString(3));
			while(user.next()) {
				System.out.println("\n_________________________________________________________________");
				
				System.out.printf("\n\n|| %-13s| %-13s |  %-10s | %s |",user.getString(1),user.getString(2),user.getString(4),user.getString(3));
			}
			
			System.out.println("\n**********************************************************************");
			
			
		} catch (SQLException e) {
			
			System.err.println("Call ERROR!!!");
			//e.printStackTrace();
		}
		

	}
}
