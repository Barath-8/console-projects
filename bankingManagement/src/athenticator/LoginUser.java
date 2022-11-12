package athenticator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Scanner;

import banking.UserInput;
import model.User;

public class LoginUser {
	private Scanner sc;
	private User currentUser;
	
	private String ADMIN = "Admin";
	private String PASS = "Admin@Z1";
	
	private Map<String,User> userList;

	public LoginUser() {
		this.sc = UserInput.getInstance().sc;
	}
	
	public LoginUser( Map<String,User> userList) {
		this.sc =  UserInput.getInstance().sc;
		this.userList=userList;
	}
	
	
	public boolean loginUser( ) {
		
		System.out.print("\nEnter Username : ");
		String username = sc.nextLine();
		byte chance = 5;
		
		while(!userList.containsKey(username)&&chance !=0) {
			System.out.println("\nIncorrect Username  Try again\nYou got only "+chance--+" chances left");
			System.out.print("\nEnter Username : ");
			username = sc.nextLine();
		}
		
		if(chance == 0) {
			System.out.println("You have entered wrong username many times\n-----Returning to main page..........");
			return false ;
		}
		
		currentUser = userList.get(username);
		
		System.out.print("\nEnter Password : ");
		String password = sc.nextLine();
		
		password = getEncryptedPass(password);
		
		String ck = currentUser.getPassword();
		
		chance = 3;
		
		while(!ck.equals(password) && chance !=0) {
			System.out.println("\nIncorrect Password  Try again\nYou got only "+chance--+" chances left");
			System.out.print("\nEnter Password : ");
			password = sc.nextLine();
		}
		
		if(chance == 0) {
			System.out.println("You have entered wrong password many times\n-----Returning to main page..........");
			return false ;
		}
		
		System.out.println("Successfully Logged in ");
		return true;
		
	}
	
	public boolean isAdmin() {
		
		System.out.print("\nEnter admin username : ");
		String username = sc.nextLine();
		byte chance = 5;
		
		while(!getADMIN().equals(username) && chance!=0) {
			System.out.println("\nIncorrect admin Username  Try again\nYou got only "+chance--+" chances left");
			System.out.print("\nEnter admin Username : "); 
			username = sc.nextLine();
		}
		
		if(chance == 0) {
			System.out.println("You have entered wrong username many times\n-----Returning to main page..........");
			return false ;
		}
		
		System.out.print("\nEnter Password : ");
		String password = sc.nextLine();
		
		//password  = getEncryptedPass(password);
		
		chance = 3;
		
		while(!getPASS().equals(password) && chance !=0) {
			System.out.println("\nIncorrect Password  Try again\nYou got only "+chance--+"chances left");
			System.out.print("\nEnter Password : ");
			password = sc.nextLine();
		}
		
		if(chance == 0) {
			System.out.println("You have entered wrong password many times\n-----Returning to main page..........");
			return false ;
		}
		
		System.out.println("Successfully Logged in Admin");
		return true;
	}
	
	private String getEncryptedPass(String password) {
		StringBuilder res = new StringBuilder();
		
		try {
			MessageDigest encrpt = MessageDigest.getInstance("MD5");
			 encrpt.update(password.getBytes());
			 
			 byte[] small = encrpt.digest();
			 
			 for(int i=0; i< small.length ;i++)  
	             	res.append(Integer.toString((small[i] & 0xff) + 0x100, 16).substring(1));  
	             
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Password Encrpting error");
		}
		
		return res.toString();
	}
	
	public User getCurrentUser() {
		return currentUser;
	}

	public String getADMIN() {
		return ADMIN;
	}

	public void setADMIN(String aDMIN) {
		ADMIN = aDMIN;
	}

	public String getPASS() {
		return PASS;
	}

	public void setPASS(String pASS) {
		PASS = pASS;
	}

	
	
	
}
