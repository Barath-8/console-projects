package banking;


import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import model.User;

public class Transaction {
	
	private Scanner sc;
	private NumberFormat currencyformat =NumberFormat.getCurrencyInstance(new Locale("en","in"));
	private User currentUser;
	private Map<String,User> userList;
	private Map<Long,String> AcNoList;
	
	public Transaction(User user){
		this.sc= UserInput.getInstance().sc;
		currentUser = user;
	}
	
	public Transaction(Map<String,User> userList,Map<Long,String> AcNoList,User user) {
		this.sc = UserInput.getInstance().sc;
		this.currentUser = user;
		this.userList = userList;
		this.AcNoList = AcNoList;
	}
	
 	public void addAmount()  {
		
		System.out.print("\nEnter Deposit Amount : ");
		int chance=5;
		
		while(!sc.hasNextDouble() && chance-- !=0) {
			System.out.println("\nInvalid Input ");
			System.out.println("You have "+(chance+1)+" left");
			System.out.print("\nEnter Deposit Amount : ");
			sc.nextLine();
		}
		
		if(chance <= 0) {
			System.out.println("\n---------------------  Too many attempts  --------------------- ");
			System.out.println("                    Exiting to user console.......\n");
			sc.nextLine();
			return;
		}
		
		double deposit = sc.nextDouble();
		sc.nextLine();
		
		boolean valid = isValidDeposit(deposit);
		
		if(!valid) {
			System.out.println("\nEnter Amount within 500 to 10000 and must be multiple of hundred's");
			addAmount();
		}else {
			currentUser.deposit(deposit);
			System.out.println("\nDeposit of Rs."+deposit+" Successfull"+"\nCurrent Balance : Rs."+currentUser.getBalance());

			currentUser.addTransaction(String.format("Rs.%-15s credited to your account.       Balance : Rs.%-15s --%s",
										currencyformat.format(deposit).substring(1),currencyformat.format(currentUser.getBalance()).substring(1),getTime()));
			
		}
		
	}
	
	public void withdrawAmount() {
		
		System.out.print("\nEnter Withdraw Amount : ");
		int chance  = 5;
		while(!sc.hasNextDouble() && chance-- !=0) {
			System.out.println("\nInvalid Input ");
			System.out.println("You have "+(chance+1)+" left");
			System.out.print("\nEnter Withdraw Amount : ");
			sc.nextLine();
		}
		
		if(chance <= 0) {
			System.out.println("\n---------------------  Too many attempts  --------------------- ");
			System.out.println("                    Exiting to user console.......\n");
			sc.nextLine();
			return;
		}
		
		double amount = sc.nextDouble();
		sc.nextLine();
		
		boolean valid = isValidWithdraw(amount);
		
		if(!valid) {
			System.out.println("\nEnter Amount within 500 to 20000 ");
			withdrawAmount();
		
		}else if(currentUser.getBalance()-amount < 100) {
			System.out.println("Insufficent amount minimum balance must be Rs.100");
			System.out.println("Current Balance : "+ currentUser.getBalance());
		}else{
			currentUser.withdraw(amount);
			System.out.println("Withdraw of Rs."+amount+" Successfull"+"\nCurrent Balance : Rs."+currentUser.getBalance());
			
			currentUser.addTransaction(String.format("Rs.%-15s debited from your account.      Balance : Rs.%-15s --%s",
											currencyformat.format(amount).substring(1),currencyformat.format(currentUser.getBalance()).substring(1),getTime()));
			
		}	
		
	}
	
	public void account_Transfer() {
		
		long recipient = getRecipientAcNo();
		
		if(recipient == 0) {
			System.out.println("Exiting to user console.......");
			return;
		}
		
		if(recipient == currentUser.getAcNo()) {
			System.out.println("\n++++++++++++++++ Cannot tranfer to self !! ++++++++++++++++++++");
			System.out.println("Exiting to user console.................");
			return;
		}
		
		String username = AcNoList.get(recipient);
		User receiver = userList.get(username);
		String ifsc = receiver.getIFSC();
		
		boolean validIfsc = getIFSC(4,ifsc);
		
		if(!validIfsc) {
			System.out.println("\n++++++++++++++++ Invalid IFSC !! ++++++++++++++++++++");
			System.out.println("Exiting to user console.................");
			return;
		}
		
		double transfer = getTransferAmount(5);
		
		if(transfer == 0) {
			System.out.println("\n++++++++++++++++ Too many wrong Attempts !! ++++++++++++++++++++");
			System.out.println("Exiting to user console.................");
			return;
		}
		
		double balance = currentUser.getBalance();
		
		if(balance-transfer <=100) {
			System.out.println("\nInsufficient Balance... Availabele balance : Rs."+balance);
			System.out.println("Exiting to user console.................");
			return;
		}
		
		double debit = balance - transfer;
		
		double credit = transfer+receiver.getBalance();
		
		currentUser.setBalance(debit);
		
		currentUser.addTransaction(String.format("Rs.%-15s Transferred to Ac.No.*****%-4d  Balance : Rs.%-15s --%s",currencyformat.format(transfer).substring(1),recipient%1000,
				 currencyformat.format(debit).substring(1),getTime()));
		
		receiver.setBalance(credit);
		
		receiver.addTransaction(String.format("Rs.%-15s Creditted from Ac.No.*****%-4d  Balance :  Rs.%-15s --%s",currencyformat.format(transfer).substring(1),currentUser.getAcNo()%1000,
				 currencyformat.format(receiver.getBalance()).substring(1),getTime()));
		
			
		System.out.println("Successfully Transfered Rs"+currencyformat.format(transfer).substring(1)+" Transferred to Ac.No.******"+recipient%1000
							+"\n-----Balance : Rs."+currencyformat.format(debit).substring(1));
		
	}
	
	private boolean getIFSC(int chance,String ifsc) {
		
		if(chance == 0) return false;
		
		System.out.print("\nEnter IFSC code : ");
		
		String ck = sc.nextLine();
		
		if(!ck.equals(ifsc)) {
			System.out.println("Enter valid Account IFSC !!\nYou have only "+(chance-1)+" chance left");
			
			return getIFSC(chance-1,ifsc);
		}
		
		
		return true;
	}

	private double getTransferAmount(int chance) {
		
		System.out.print("\nEnter Transfer Amount :");
		
		while(!sc.hasNextDouble() && chance-- >0 ) {
			System.out.println("\nInvalid Input ");
			System.out.println("You have "+(chance+1)+" left");
			System.out.print("\n\nEnter Deposit Amount :");
			sc.nextLine();
		}
		
		if(chance == 0 ||!sc.hasNextDouble()) {
			sc.nextLine();
			return 0;
		}
		
		double transfer = sc.nextDouble();
		sc.nextLine();
		
		boolean valid = isValidDeposit(transfer);
		
		if(!valid) {
			System.out.println("Enter Amount within 500 to 20000 and must be multiple of hundred's");
			System.out.println("You have "+(chance+1)+" left");
			getTransferAmount(chance-1);
		}
		
		return transfer;
	}

	private long getRecipientAcNo() {
		System.out.print("\nEnter Recipient Account number : ");
		
		long recipient =  getValidAcNo(5);
		
		if(recipient == 0)
			return 0;
		
		return recipient;
	}
	
	private long getValidAcNo(int chance) {
		
		
		while(!sc.hasNextLong() && chance-- >0) {
			System.out.println("\nInvalid input!! Account number must be numbers ");
			System.out.println("You have "+(chance+1)+" left");
			System.out.print("\n\nEnter Account number : ");
			sc.nextLine();
		}
		
		if(chance <= 0  || !sc.hasNextLong()) {
			System.out.println("<<<< Too many wrong attemps >>>>");
			sc.nextLine();
			return 0;
		}
		
		long recipient = sc.nextLong();
		sc.nextLine();
		
		boolean valid = isValidAcNo(recipient);
		
		if(!valid) {
			System.out.println("\nYou have "+(chance+1)+" left");
			System.out.print("\n\nEnter valid AC.No. : ");
			return getValidAcNo(chance-1);
		}
		
		return recipient;
	}
	
	public void displayTransac() {
		if(currentUser.getTransaction().size() == 0) {
			System.out.println("NO Transactions Have been made please feel free to use our Bank ");
			return;
		}
			
		System.out.println("\n*******************************************************************************************************\n");
		for(String transc : currentUser.getTransaction())
			System.out.println(transc);
		System.out.println("\n*******************************************************************************************************");
	}
	
	public void displayLast5Trans() {
		ArrayList<String> trans = currentUser.getTransaction();
		
		if(trans.size() == 0) {
			System.out.println("NO Transactions Have been made please feel free to use our Bank ");
			return;
		}
		
		System.out.println("\n*******************************************************************************************************\n");
		for(int i=1 ; i<=5 && (trans.size()-i)>=0 ; i++) 
			System.out.println(trans.get(trans.size()-i));
		System.out.println("\n*******************************************************************************************************");
		
	}
	
	private  String getTime() {
		
		LocalDateTime myDateObj = LocalDateTime.now();  
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		String dateTime = myDateObj.format(myFormatObj);
		
		return dateTime;
	}
	
	private boolean isValidAcNo(long recipient) {
		if(AcNoList.containsKey(recipient) )
			return true;
		return false;
	}

	private boolean isValidDeposit(double deposit) {
			return (deposit >=500 && deposit <=20000 && deposit%100==0)? true : false;
		}
	private boolean isValidWithdraw(double amount) {
			return (amount >=100 && amount <=10000 && amount%100==0)? true : false;
	}

	public void editPassword() {
		
		
	}

	


	
}
