package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

	@SerializedName("is_admin")
	private boolean isAdmin;

	@SerializedName("device_id")
	private String deviceId;

	@SerializedName("phone")
	private String phone;

	@SerializedName("date_of_birth")
	private String dateOfBirth;

	@SerializedName("name")
	private String name;

	@SerializedName("language")
	private String language;

	@SerializedName("registered_for_notifications")
	private boolean registeredForNotifications;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	public void setIsAdmin(boolean isAdmin){
		this.isAdmin = isAdmin;
	}

	public boolean isIsAdmin(){
		return isAdmin;
	}

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setDateOfBirth(String dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfBirth(){
		return dateOfBirth;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLanguage(String language){
		this.language = language;
	}

	public String getLanguage(){
		return language;
	}

	public void setRegisteredForNotifications(boolean registeredForNotifications){
		this.registeredForNotifications = registeredForNotifications;
	}

	public boolean isRegisteredForNotifications(){
		return registeredForNotifications;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}