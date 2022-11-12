package model;

import java.util.Scanner;

public class UserInput {
	
	private static UserInput single_Instance = null;
	
	public  Scanner scanner ;
	
	private UserInput() {
		scanner = new Scanner(System.in);
	}
	
	
	public static UserInput getInstance() {
		
		if(single_Instance == null || single_Instance.scanner==null )
			single_Instance = new UserInput();
		
		return single_Instance;
	}
	
	
}
