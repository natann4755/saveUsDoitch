package com.example.model;

import com.google.gson.annotations.SerializedName;

public class Login{

	@SerializedName("deviceId")
	private String deviceId;

	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceId(){
		return deviceId;
	}
}