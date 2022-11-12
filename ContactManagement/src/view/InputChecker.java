package view;

import java.util.Scanner;

import controller.Validator;
import model.UserInput;

public class InputChecker {
	
	private Scanner sc ;
	
	public InputChecker(){
		sc= UserInput.getInstance().scanner;
	}
	

	public String createName(String str) {
		System.out.print("\nEnter "+str+" : ");
		String userName = sc.nextLine();
		
		if(!Validator.isValidName(userName)){
			System.out.println("______Input must contain only letters _____ ");
			return createName(str);
		}
		
		return userName;
		
	}
	
	public String createLable() {
		System.out.print("\nEnter lable : ");
		String lable = sc.nextLine();
		
		if(!Validator.isValidLable(lable)){
			System.out.println("______Input must contain only letters or be Blank _____ ");
			return createLable();
		}
		
		return lable;
		
	}
	

	public String createPhone() {
		
		System.out.print("\nPhone    : ");
		String phone = sc.nextLine();
		
		if(!Validator.isValidPhoneNumber(phone)) {
			System.out.println("\nEnter valid phone numbers");
			return createPhone();
		}
		
		return phone;
		
	}
	
	public String createEmail(String type) {
		
		System.out.print("\n"+type+"  : ");
		String mail = sc.nextLine();
		
		if(!Validator.isValidEmail(mail)) {
			System.out.println("\nEnter in Email format");
			return createEmail(type);
		}
		
		return mail;
		
	}
	
	


	
}
