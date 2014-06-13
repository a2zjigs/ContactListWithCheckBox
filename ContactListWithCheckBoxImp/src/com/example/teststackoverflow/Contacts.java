package com.example.teststackoverflow;

public class Contacts {

	String PhoneNumber = null;
	String Name = null;
	boolean selected = false;

	public Contacts(String code, String name, boolean selected) {
		super();
		this.PhoneNumber = code;
		this.Name = name;
		this.selected = selected;
	}

	public String getCode() {
		return PhoneNumber;
	}

	public void setCode(String code) {
		this.PhoneNumber = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}