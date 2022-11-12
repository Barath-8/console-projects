package view;
import java.sql.SQLException;
import java.util.Scanner;


import controller.DatabaseController;
import model.UserInput;

public class ContactManagerView {
	
	private Scanner sc ;
	private DatabaseController storage;
	private InputChecker input;
	
 	ContactManagerView() {
		sc = UserInput.getInstance().scanner;
		input = new InputChecker();
		}
	
	public void start() {
		

		try {
			storage = new DatabaseController();
		} catch (SQLException e) {
			System.err.println("Database Connection Error!");
			System.exit(0);
		}
		
		System.out.println("===================================================================");
		System.out.println("                         CONTACT MANAGER");
		System.out.println("===================================================================");
	
		while(true) {
			switch(getChoice()) {
				case 1 : storage.viewCallLog("");
						 break;
				case 2 : storage.viewsms("");
						 break;
				case 3 : addContact();
						 break;
				case 4 : showContact();
						 break;
				case 5 : searchContact();
						 break;
				case 6 : deleteContact();
						 break;
				case 7 :System.out.println("_____________________________________________________"); 
						System.exit(0);
				default : System.out.println("Choose correctly !!");
			}
		}
	}
	
	public void searchContact() {

		boolean exit = false;
		
		
		while(!exit) {
			
			String phone="";
		
			switch(searchContactby()) {
				case 1 : phone = storage.searchby("Name",input.createName("Name to be Searched")+"%");
						 break;
				case 2 : phone = storage.searchby("Phone",input.createPhone()+"%");
				 		 break;
				case 3 : phone =storage.searchby("Email",input.createEmail("Email to be searched")+"%");
				 		 break;
				case 4 : exit = true;
				default : System.out.println("Kindly select the given option");
			}
			
			exit = doOperation("Phone",phone);
		}
					
		
	}
	
	private boolean doOperation(String type, String phone) {
		
		while(true) 
		switch(getfeaturechoice()) {
			
			case 1 : String duration = makeCall();
					 storage.addCallLog(phone, duration);
					 break;
			case 2 : String msg = getmsgformat(); 
					 storage.addsms(phone, msg);
					 break;
			case 3 : storage.viewsms(phone);
					 break;
			case 4 : storage.viewCallLog(phone);
					 break;
			case 5 : storage.delete("calling", type, phone);
					 break;
			case 6 : storage.delete("smshistory", type, phone);
					 break;
			case 7 : editcontact(type,phone);
					 break; 
			case 8 : return true; 
			default : System.out.println("Choose correct option");
		}
	
		
	}
	
	private String makeCall() {
		
		System.out.print("\n  Ringing..");
		
		for(int i =0 ; i<13 ; i++) {
			System.out.print("..");
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println("\n\tConnected....\n\n  Started recording");
		
		long start = System.currentTimeMillis();
		
		System.out.println("\n\nEnd Call :\n\t1.YES\n\t2.NO");
		
		runtime();

		long end  = System.currentTimeMillis();
		
		long duration = end-start;
		
		
		long minutes = (duration/1000)/60,
			 seconds = (duration/1000)%60;
		
		String min= String.valueOf(minutes)+" min " +String.valueOf(seconds)+" sec";
		
		System.out.println("Call duration : "+ min);
		
		return min;
	}

	private void runtime() {
		
		while(!sc.hasNextInt()) {
		}
		int yes = sc.nextInt();
		
		
		if(yes !=1) 
			runtime();
		else
			return;
	}

	private String getmsgformat() {
		
		System.out.print("Enter message :");
		String msg = sc.nextLine();
		
		
		if(msg.length()<60)
			return msg;
		
		String[] chunks = msg.split("(?<=\\G.{60})");
		
		msg = "";
		for(String s : chunks)
			msg+=s+"\n";
		
		return msg;
		
	}

	private boolean editcontact(String type, String name) {
		
		while(true) 
		switch(getEditchoice()) {
			case 1 : storage.editBy("Name",input.createName("Name to be Edited") , type, name);
					 break;
			case 2 : storage.editBy("Phone",input.createPhone() , type, name);
					 break;
			case 3 : storage.editBy("Email",input.createEmail("New Email") , type, name);
			 		 break; 
			case 4 : return true; 
			default : System.out.println("Choose correct option");
		}
	
		
	}

	public void deleteContact() {

		boolean exit = false;
		
		
		while(!exit) {
			switch(deleteContactby()) {
				case 1 : storage.delete("contact","Name",input.createName("Name"));
						 break;
				case 2 : storage.delete("contact","Phone",input.createPhone());
				 		 break;
				case 3 : storage.delete("contact","Email", input.createEmail("Eamil"));
						 break;
				case 4 : exit = true;
				default : System.out.println("Enter valid option");
			}
		}
		
	}

	private void showContact() {
		storage.displayAllContacts();
	}

	private void addContact() {
		
		String name = input.createName("Name");
		String phone = input.createPhone();
		String lable= input.createLable();
		String mail = input.createEmail("Email");
		
		boolean isAdded = storage.addContact(name,phone,lable,mail);
		
		if(!isAdded) {
			System.out.println("Contact addition failed");
			return;
		}
		
	}

	private int getChoice() {
		
		byte choice =0;
		System.out.println("\nSelect the following :\n\t[1].Call logs\t\t[4].View All contacts\n\t[2].SMS history\t\t[5].Search Contact \n\t[3].Add contact\t\t[6].Delete Contact\n\t\t\t[7].Exit");	
		
		while(!sc.hasNextByte()) {
			System.out.println("\nKindly select from  the given options!!");
			System.out.println("\nSelect the following :\n\t[1].Call logs\t\t[4].View All contacts\n\t[2].SMS history\t\t[5].Search Contact \n\t[3].Add contact\t\t[6].Delete Contact\n\t\t\t[7].Exit");
			sc.nextLine();
		}
		
		choice = sc.nextByte();
		sc.nextLine();
		
		System.out.println("-----------------------------------------------------");
		
		return choice;
		
	}
	
	private int getfeaturechoice() {
		
		byte choice =0;
		
		System.out.println("\nOptions  : \n\t[1].Call\t\t[3].View SMS\n\t[2].Send SMS\t\t[4].View Call log\n\t[5].Clear call logs\t[6].clear SMS\n\t\t[7].Edit Menu\n\t\t[8].Exit to main");
		
		while(!sc.hasNextByte()) {
			System.out.println("\nKindly select from  the given options!!");
			System.out.println("\nOptions  : \n\t[1].Call\t\t[3].View SMS\n\t[2].Send SMS\t\t[4].View Call log\n\t[5].Clear call logs\t[6].clear SMS\n\t\t[7].Edit Menu\n\t\t[8].Exit to main");
			sc.nextLine();
		}
		
		choice = sc.nextByte();
		sc.nextLine();
		
		System.out.println("-----------------------------------------------------");
		
		
		return choice;
		
	}
	
	
	private int getEditchoice() {
		
		byte choice =0;
		
		System.out.println("\nEdit  : \n\t1.Name\n\t2.Phone\n\t3.Email\n\t4.Exit to main");
		
		while(!sc.hasNextByte()) {
			System.out.println("\nKindly select from  the given options!!");
			System.out.println("Edit : \n\t1.Name\n\t2.Phone\n\t3.Email\n\t4.Exit to main");
			sc.nextLine();
		}
		
		choice = sc.nextByte();
		sc.nextLine();
		
		System.out.println("-----------------------------------------------------");
		
		
		return choice;
		
	}
	
	private int searchContactby() {
		
		byte choice =0;
		
		System.out.println("\nSearch By : \n\t1.Name\n\t2.Phone\n\t3.Email\n\t4.Exit to main");
		
		while(!sc.hasNextByte()) {
			System.out.println("\nKindly select from  the given options!!");
			System.out.println("Search By : \n\t1.Name\n\t2.Phone\n\t3.Email\n\t4.Exit to main");
			sc.nextLine();
		}
		
		choice = sc.nextByte();
		sc.nextLine();
		
		System.out.println("-----------------------------------------------------");
		
		
		return choice;
		
	}

	private int deleteContactby() {
		
		byte choice =0;
		
		System.out.println("\nDelete by : \n\t1.Name\n\t2.Phone\n\t3.Email\n\t4.Exit");
		
		while(!sc.hasNextByte()) {
			System.out.println("\nKindly select from  the given options!!");
			System.out.println("Delete by : \n\t1.Name\n\t2.Phone\n\t3.Email\n\tExit");
			sc.nextLine();
		}
		
		choice = sc.nextByte();
		sc.nextLine();
		
		System.out.println("-----------------------------------------------------");
		
		
		return choice;
		
	}

}
