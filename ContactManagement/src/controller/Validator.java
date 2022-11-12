package controller;

public class Validator {
	
	public static boolean isValidPassword(String password) {
		
		return password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$");
		
	}
	
	public static boolean isValidPhoneNumber(String phone) {
		
		return phone.matches( "^[6-9][0-9]{9}$");
	}
	
	public static boolean isValidName(String name) {
		
		return name.matches("^[a-zA-Z]{2,}");
		
	}
	
	public static boolean isValidLable(String lable) {
		
		return lable.matches( "^[a-zA-Z]*|\s$");
		
	}
	
	public static boolean isValidEmail(String mail) {
		
		return mail.matches("^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9]+.[a-z]+)|\s$");
		
	}
	
	public static boolean isValidChoice(byte choice,int val) {
		return (choice >= 1 && choice <=val)? true : false;
	}

	
}
