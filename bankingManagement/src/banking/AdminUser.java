package banking;


import java.text.NumberFormat;
import java.util.Map;
import java.util.Scanner;

import model.User;

public class AdminUser {
	
	private Scanner sc;
	private Map<String,User> userList;
	private Map<Long,String> AcNoList;
	
	public AdminUser( Map<String,User> userList,Map<Long,String> AcNoList){
		this.sc =  UserInput.getInstance().sc;
		this.userList=userList;
		this.AcNoList = AcNoList;
	}
	
	public void adminUserDisplay() throws InterruptedException {
		
		System.out.println("Total customers : " + userList.size() );
		
		boolean exit = false;
		while(!exit) {	
			switch(get_AdminChoice()) {	//1.Display All 2.Search user 3.Remove user
				case 1: adminDisplay();
						break;
				case 2: searchUser();
						break;
				case 3: removeUser();
						break;
				case 4: exit=true;
						break;
				default: System.out.println("Enter valid choice !");
				
			}
			
			Thread.sleep(500);
		}
		
	}
	
	private void removeUser() {
		
		if(userList.isEmpty()) {
			System.out.println("\nNothing to Display here.........");
			System.out.println("Get some users first :)");
			return;
		}
		
		long acNo=getSearchAcNo();
		
		if(acNo == 0) {
			System.out.println("<<<<Too Many wrong attempts>>>");
			System.out.println("Returning to Admin screen.....");
			return;
		}
		
		User remove =userList.get(AcNoList.get(acNo)); 
		
		userInfoDisplay(remove);
		
		boolean confirm = isSure(5);
		
		if(!confirm) {
			System.out.println("Returning to Admin Screen......");
			return;
		}
		
		userList.remove(AcNoList.get(acNo));
		AcNoList.remove(acNo);
		
		System.out.println("\nDeletion successfull.......");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}

	private boolean isSure(int chance) {
		
		if(chance == 0) {
			System.out.println("\nToo many wrong attempts");
			return false;
		}
		
		System.out.println("Are you sure you want to delete this user\n\t1.Yes\n\t2.No");
		 
		while(!sc.hasNextByte() && chance-- >0) {
			System.out.println("Enter correct option !!!");
			System.out.println("You have "+chance+" left");
			sc.nextLine();
		}
		
		int choice = sc.nextInt();
		
		if(choice!=2 || choice !=1) {
			System.out.println("Entered wrong input!!!");
			System.out.println("You have "+chance+" left");
			return isSure(chance-1);
		}
		
		return choice==1? true : false;
		
	}

	private void searchUser() {
		
		if(userList.isEmpty()) {
			System.out.println("\nNothing to Display here.........");
			System.out.println("Get some users first :)");
			return;
		}
		
		long acNo=getSearchAcNo();
		
		if(acNo == 0) {
			System.out.println("<<<<Too Many wrong attempts>>>");
			System.out.println("Returning to Admin screen.....");
			return;
		}
		
		User search = userList.get(AcNoList.get(acNo));
		
		
		System.out.println("Displaying user info..... ");
		userInfoDisplay(search);
		
	}

	private void adminDisplay() {
		
		if(userList.isEmpty()) {
			System.out.println("\nNothing to Display here.........");
			System.out.println("Get some users first :)");
			return;
		}
		
		System.out.println("Total customers : " + userList.size() );
		
		int count=1;
		System.out.println("\n_________________________________________________________________________");
		for(User user : userList.values())
			basicInfoDisplay(user,count++);
		System.out.println("\n_________________________________________________________________________");	
	}
	
	private void basicInfoDisplay(User user,int count) {
		System.out.printf("\n%-3d | Ac.No : %-10s | Name : %-14s | Balance : %7.2f |",count,user.getAcNo(),user.getName(),user.getBalance());
	}

	private void userInfoDisplay(User user) {
		System.out.println("                   Account Information");
		System.out.println("_______________________________________________________");
		System.out.println("\n                Name : " + user.getName());
		System.out.println("          Account No.: "+user.getAcNo());
		System.out.println("          IFSC code  : "+ "ZBANK123");
		System.out.println("           Username  : "+ user.getLoginID());
		System.out.println("           Password  : "+ user.getPassword());
		System.out.println("   Available Balance : Rs."+NumberFormat.getCurrencyInstance().format(user.getBalance()).substring(1) );
		System.out.println("                 Age : "+user.getAge());
		System.out.println("              Mobile : "+ user.getPhone());
		System.out.println("               Email : "+user.getEmail());
		System.out.println("_______________________________________________________");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.err.println("Thread Sleep error in UserInfoDisplay");
		}
	}
	
	private byte get_AdminChoice() {
		
		byte choice =0;
		
		System.out.println("\nSelect : \n\t1.Display All\n\t2.Search user\n\t3.Remove user\n\t4.Exit");
		System.out.print("\n\nEnter your choice : ");
		
		while(!sc.hasNextByte()) {
			System.out.println("\nKindly select from  the given options!!");
			System.out.println("Select : \n\t1.Display All\n\t2.Search user\n\t3.Remove user\n\t4.Exit");
			System.out.print("\n\nEnter your choice : ");
			sc.nextLine();
		}
		
		choice = sc.nextByte();
		sc.nextLine();
		
		System.out.println("-----------------------------------------------------");
				
		return choice;
		
	}
	
	private long getSearchAcNo() {
		System.out.println("Enter Account number :");
		
		long recipient =  getValidAcNo(5);
		
		if(recipient == 0)
			return 0;
		
		return recipient;
	}
	
	private long getValidAcNo(int chance) {
		
		
		while(!sc.hasNextLong() && chance-- >0) {
			System.out.println("\nInvalid input!! Account number must be numbers ");
			System.out.println("You have "+(chance+1)+" left");
			System.out.println("Enter Account number :");
			sc.nextLine();
		}
		
		if(chance <= 0  || !sc.hasNextLong()) {
			System.out.println("<<<< Too many wrong attemps >>>>");
			return 0;
		}
		
		long recipient = sc.nextLong();
		sc.nextLine();
		
		boolean valid = isValidAcNo(recipient);
		
		if(!valid) {
			System.out.println("\nYou have "+(chance+1)+" left");
			System.out.println("Enter valid AC.No. : ");
			return getValidAcNo(chance-1);
		}
		
		return recipient;
	}

	private boolean isValidAcNo(long recipient) {
		if(AcNoList.containsKey(recipient) )
			return true;
		return false;
	}

}
