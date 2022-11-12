package model;

import java.util.ArrayList;

public class ContactInfo {
	private String name,phone,lable,email;

	private ArrayList<String> sms;
	private ArrayList<String> callLogs;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<String> getSms() {
		return sms;
	}
	public void setSms(ArrayList<String> sms) {
		this.sms = sms;
	}
	public ArrayList<String> getCallLogs() {
		return callLogs;
	}
	public void setCallLogs(ArrayList<String> callLogs) {
		this.callLogs = callLogs;
	}
	
	
	
}
