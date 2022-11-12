package view;

import java.util.Scanner;

import athenticator.*;
import banking.AdminUser;
import banking.Transaction;
import banking.UserInput;
import model.User;

import java.util.Map;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Zbank {
	private static final String IFSC = "ZBANK123";
	private static long bankAcNo = 12300100;
	private Scanner sc ;
	
	private Map<String,User> userList;
	private Map<Long,String> AcNoList;
	
	User currentUser;
	Validator validator;
	
	public Zbank(){
		this.sc = UserInput.getInstance().sc;
		userList = new LinkedHashMap<String,User>();
		AcNoList = new HashMap<Long,String>();
		validator = new Validator();
	}
	
	public void start() {
		System.out.println("_________________________________________________");
		System.out.println("\n              Zbank WELCOMES You ");
		System.out.println("_________________________________________________");
		
	
		
	while(true) {
		
		switch(getHomechoice()) {			// 1.New User 2.Login 3.Exit
			case 1:
				setNewUser();
				break ;
			case 2:
				login();
				break ;
			case 3:
				System.out.println("\n************************ Thank you for choosing our Bank ****************************** "
								+ "\n\n------------------------------- Have a GREAT DAT -------------------------------------"
								+ "\n________________________________________________________________________________________\n");
				sc.close();
				System.exit(0);
			default :
				System.out.println("Choose correct option");
		}
		System.out.println		("\n-----------------------------------------------------");
			
	}
		
		
	}
	
	private void setNewUser() {
		currentUser = new User();
		UserSignUp userReg = new UserSignUp(userList,AcNoList,bankAcNo++);
		userReg.createNewUser(currentUser);
		}
	
	private void login() {
		
		switch(get_LoginChoice()) {
			case 1 :try {
					adminUser();
				} catch (InterruptedException e) {
					System.err.println("Thread Sleep func Error!!!");
				} break;	
			case 2 : loginUser(); break;
			default : 
				System.out.println("Please select correctly ");
				login();
		}
	}
	
	private void adminUser() throws InterruptedException {
		
		LoginUser user = new LoginUser();
		boolean isloggedin = user.isAdmin();
		
		if(!isloggedin) //entered wrong username or password many times
			return;
		AdminUser admin = new AdminUser(userList,AcNoList);
		admin.adminUserDisplay();
		
	}
	
	private void loginUser() {
		
		if(userList.isEmpty()) {
			System.out.println("No user data Available please proceed to registration");
			start();
		}
		
		LoginUser user = new LoginUser(userList);
		boolean isloggedin = user.loginUser();
		
		if(!isloggedin) //entered wrong username or password many times
			start();
		
		currentUser = user.getCurrentUser();
		
		try {
			getUserAccess();
		} catch (InterruptedException e) {
			System.err.println("Something went wrong with Thread sleep func");
		}
		
	}
	
	private void getUserAccess() throws InterruptedException {


		
		boolean exit = false; //to return to the same method getUserAccess
	
		Transaction user = new Transaction(userList,AcNoList,currentUser);
		
		while(!exit) {
			System.out.println("------------------------------------------------------");
			switch(get_UserChoice()) {		//1.Account Info 2.Deposit 3.Withdraw 4.Last 5 Transaction 5.Transaction history 6.Exit to home 7: Quit
				case 1: userInfo();  break ;
				case 2: user.addAmount(); break;
				case 3: user.withdrawAmount(); break;
				case 4: user.account_Transfer(); break;
				case 5: user.displayLast5Trans(); break;
				case 6: user.displayTransac(); break;
				case 7: exit = true;  break;
				case 8: System.out.println("\n************************ Thank you for choosing our Bank ****************************** "
						+ "\n\n------------------------------- Have a GREAT DAT -------------------------------------"
						+ "\n______________________________________________________________________________________\n");
						sc.close();
						System.exit(0);
				default : 
					System.out.println("Kindly choose the given option ");
			}
			Thread.sleep(500);
		
		}
		
		System.out.println("\nExiting to Home page.............");
		Thread.sleep(1000);

	}
	
	private byte get_LoginChoice() {
		
		byte choice =0;
		
		System.out.println("Select the following : \n\t1.Admin\n\t2.User");
		System.out.print("\n\nEnter your choice : ");
		
		while(!sc.hasNextByte()) {
			System.out.println("\nKindly select from  the given options!!");
			System.out.println("Select the following : \n\t1.Admin\n\t2.User");
			System.out.print("\n\nEnter your choice : ");
			sc.nextLine();
		}
		
		choice = sc.nextByte();
		sc.nextLine();
		
		System.out.println("-----------------------------------------------------");
		
		return choice;
		
	}
	
	private byte get_UserChoice() {
		
		byte choice =0;
		
		System.out.println("\nSelect from the following :\n\n\t[1].Account Info\n\t[2].Deposit\n\t[3].Withdraw\n\t"
					+ "[4].Account Transfer\n\t[5].Last 5 Transaction\n\t[6].Transaction history\n\t[7].Exit to home\n\t[8].Quit");
		System.out.print("\n\nEnter your choice : ");
		
		while(!sc.hasNextByte()) {
			System.out.println("\nKindly select from  the given options!!");
			System.out.println("\nSelect from the following :\n\n\t[1].Account Info\n\t[2].Deposit\n\t[3].Withdraw\n\t"
					+ "[4].Account Transfer\n\t[5].Last 5 Transaction\n\t[6].Transaction history\n\t[7].Exit to home\n\t[8].Quit");
			System.out.print("\n\nEnter your choice : ");
			sc.nextLine();
		}
		
		choice = sc.nextByte();
		sc.nextLine();
		
		System.out.println("-----------------------------------------------------");
		
		return choice;
		
	}

	private byte getHomechoice() {
	
			byte choice =0;
			
			System.out.println("\nSelect from the following :\n\n\t1).New User\n\t2).Login\n\t3).Exit");
			System.out.print("\n\nEnter your choice : ");
			
			while(!sc.hasNextByte()) {
				System.out.println("\nChoose the given options!!");
				System.out.println("\n\t1).New User\n\t2).Login\n\t3).Exit");
				System.out.print("\n\nEnter your choice : ");
				sc.nextLine();
			}
			
			choice = sc.nextByte();
			sc.nextLine();
			
			System.out.println("-----------------------------------------------------");
			
			return choice;
		
	}

	private void userInfo() throws InterruptedException {
		System.out.println("                   Account Information");
		System.out.println("_______________________________________________________");
		System.out.println("\n                Name : " + currentUser.getName());
		System.out.println("          Account No.: "+currentUser.getAcNo());
		System.out.println("          IFSC code  : "+ IFSC);
		System.out.println("   Available Balance : Rs."+NumberFormat.getCurrencyInstance().format(currentUser.getBalance()).substring(1) );
		System.out.println("                 Age : "+currentUser.getAge());
		System.out.println("              Gender : "+currentUser.getGender());
		System.out.println("                 DOB : "+currentUser.getDob());
		System.out.println("              Mobile : " + currentUser.getPhone());
		System.out.println("               Email : "+currentUser.getEmail());
		System.out.println("_______________________________________________________");
		
		Thread.sleep(2000);
	}
	
	
}
