package banking;

import java.util.Scanner;

public class UserInput {
	
	private static UserInput single_Instance = null;
	
	public  Scanner sc ;
	
	private UserInput() {
		sc = new Scanner(System.in);
	}
	
	
	public static UserInput getInstance() {
		
		if(single_Instance == null || single_Instance.sc==null )
			single_Instance = new UserInput();
		
		return single_Instance;
	}
	
	
}
