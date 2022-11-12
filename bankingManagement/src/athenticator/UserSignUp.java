package athenticator;

import java.util.Map;
import java.util.Scanner;

import banking.UserInput;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;

import model.User;

public class UserSignUp{
	
	private Scanner sc;
	private Map<String,User> userList;
	private Map<Long,String> AcNoList;	
	private long bankAcNo;
	
	
	public UserSignUp(Map<String,User> userList,Map<Long,String> AcNoList,long bankAcNo){
		this.sc =UserInput.getInstance().sc;
		this.userList = userList;
		this.bankAcNo = bankAcNo;
		this.AcNoList = AcNoList;
	}
	
	public void createNewUser(User currentUser) {
		
		System.out.println("\nPlease fill the details below to complete registration for Zero Bal. A/c");
		
		currentUser.setName(createName());
		
		String dob =getdob();
		byte age=giveAge(dob);
		
		while(age==0) {
			System.out.println("***************Entered Age contadicts with DOB *********************");
			System.out.println("Enter correct Info..........");
			dob = getdob();
			age=giveAge(dob);
		}
		
		currentUser.setDob(dob);
		currentUser.setAge((byte)age);		
		currentUser.setPhone(createPhone());
		currentUser.setGender(getGender());
		currentUser.setIFSC("ZBANK123");
		
		String pan = createPan();
		pan = getEncryptedPass(pan);

		currentUser.setPan(pan);
		currentUser.setEmail(createEmail());
		
		System.out.println("***************** Successfully registered *********************** ");	
		System.out.println("\nProceeding to Login credentials..........");
		
		String username = createUsername();
		String password = createPassword();
		
		password = getEncryptedPass(password);
		
		currentUser.setLoginID(username);
		currentUser.setPassword(password);
		currentUser.setAcNo(++bankAcNo);

		userList.put(username, currentUser);
		AcNoList.put(bankAcNo, username);
		
		System.out.println("\nSuccessfully set username and password \n  --------Happy Banking!!!-------");
	}
	

	private String createName() {
		System.out.print("\nEnter your name :");
		String userName = sc.nextLine();
		
		if(!Validator.isValidName(userName)){
			System.out.println("\n______Username must contain only letters _____ ");
			return createName();
		}
		
		return userName;
		
	}

	private byte giveAge(String dob) {
		
		System.out.print("\nAge      : ");
		while(!sc.hasNextByte()) {
			System.out.println("\nInvalid Age format ");
			System.out.print("\nAge      : ");
			sc.nextLine();
		}
		byte age =sc.nextByte();
		sc.nextLine();
		
		if(age>15&&age<60)  
			return (isCorrectAge(age,dob))? age:0;
		else {
			System.out.println("\nAge should be in the range of 15 to 60" );
			return giveAge(dob);
		}
		
	}

	private boolean isCorrectAge(int age,String dob) {
		
		String chunks[] = dob.split("[/.-]");		
		
		String res = "";
		
		for(int i=chunks.length-1 ; i>=0 ; i--) 
			if(i==0)
				res+=chunks[i];
			else
				res+=chunks[i]+"-";
		
		LocalDate ck = LocalDate.parse(res);
		
		LocalDate currDate = LocalDate.now();
		
		int crctAge = Period.between(ck, currDate).getYears();
		
		if(crctAge-1 <= age && age <= crctAge+1)
			return true;
		
		return false;
	}

	private String createPhone() {
		
		System.out.print("\nPhone    : ");
		String phone = sc.nextLine();
		if(!Validator.isValidPhoneNumber(phone)) {
			System.out.println("\nEnter valid mobile numbers");
			return createPhone();
		}
		return phone;
		
	}

	private String getGender() {
		
		System.out.println("\nGender\n\t1.Male\n\t2.Female\n\t3.Other");
		 
		while(!sc.hasNextInt()) {
			System.out.println("Enter correct option !!!");
			sc.nextLine();
		}
		
		int choice = sc.nextInt();
		sc.nextLine();
		
		if(!Validator.isValidChoice(choice,3)) {
			System.out.println("Entered wrong input!!!");
			return getGender();
		}
		
		if(choice == 1)
			return "Male";
		if(choice == 2)
			return "Female";
		
		return "Other";
		
		
	}
	
	private String getdob() {
		
		System.out.print("\nDate of Birth (DD/MM/YYYY) : ");
		String dob = sc.nextLine();
		if(!Validator.isValidDobReg(dob)) {
			System.out.println("\nEnter in DD/MM/YYYY format");
			return getdob();
		}
		return dob;
		
	}
	
	private String createPan() {
		System.out.print("\nEnter your PAN : ");
		String pan = sc.nextLine();
		
		if(!Validator.isValidPan(pan)){
			System.out.println("\n______ Invalid PAN Number _____ ");
			return createPan();
		}
		
		return pan;
		
	}
	
	private String createEmail() {
		System.out.print("\nEnter your E-mail : ");
		String mail = sc.nextLine();
		
		if(!Validator.isValidEmail(mail)){
			System.out.println("\n______ Invalid Email Format _____ ");
			return createEmail();
		}
		
		return mail;
		
	}
	
	private String createUsername() {
		
		System.out.print("\nCreate new Username : ");
		String username = sc.nextLine();
		
		while(!Validator.isValidUsername(username)){
			System.out.println("\n______ Username can onlt contain letters and numbers _____ ");
			return createUsername();
		
		
		}
		
		if (userList.containsKey(username)) {
			System.out.println("\nUsername Already Exist!! ");
			return createUsername();
		}
		
		return username;
	}
	
	private String createPassword() {
		
		System.out.print("\nCreate password : ");
		String password = sc.nextLine();
		
		if(!Validator.isValidPassword(password)) {
			System.out.println("\nWrong credentials\nMust contain atleast 1(A-Z) 1(a-z) 1(0-9) 1($#%^&@+=) no white spaces");
			return createPassword();
		}
		
		System.out.print("\nVerify password : ");
		String ck =  sc.nextLine();
		
		if(!ck.equals(password) ) {
			System.out.println("\nPassword Mismatch Try again ");
			return createPassword();
		}
		
		
		return password;
	}

	public String getEncryptedPass(String password) {
		
		StringBuilder res = new StringBuilder();
		
		try {
			
			MessageDigest encrpt = MessageDigest.getInstance("MD5");//Message Digest Encryption
			
			encrpt.update(password.getBytes());
			
			byte[] small = encrpt.digest();
			
			
			for(int i=0; i< small.length ;i++)  
				res.append(Integer.toString((small[i] & 0xff) + 0x100, 16).substring(1));  
			
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Password Encrpting error");
		}
		
		return res.toString();
	}

}
