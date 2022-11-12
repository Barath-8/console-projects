package athenticator;

public class Validator {
	
	public static boolean isValidPassword(String password) {
		
		return password.matches( "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$");
		
	}
	
	public static boolean isValidPhoneNumber(String phone) {
		
		return phone.matches("^[6-9][0-9]{9}$");
		
	}
	
	public static boolean isValidName(String name) {
		
		return name.matches("^[a-zA-Z]{3,}");
		
	}

	public static boolean isValidUsername(String username) {
		
		return username.matches( "^[a-zA-Z]{3,}[0-9@$_-]*$");
	}
	

	public static boolean isValidDobReg(String dob) {
		
		return dob.matches( "^(3[01]|[12][0-9]|0[1-9])[/.-](1[0-2]|0[1-9])[/.-][0-9]{4}$");
		
	}
	
	public static boolean isValidEmail(String mail) {
		
		return mail.matches("^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9]+.[a-z]+)$");
		
	}
	
	public static boolean isValidPan(String pan) {
		return pan.matches("^[A-Z]{5}[0-9]{4}[A-Z]{1}$");
	}
	
	public static boolean isValidChoice(int choice , int val) {
		return (choice>=1 && choice<=val)? true : false;
	}
		
}
