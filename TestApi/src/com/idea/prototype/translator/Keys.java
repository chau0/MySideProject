package com.idea.prototype.translator;

public enum Keys {
	LEFT_CONTROL("Left Control"),
	RIGHT_CONTROL("Right Control"),
	LEFT_SHIFT("Left Shift"),
	RIGHT_SHIFT("Right Shift"),
	Z("Z");
	private String code;
	private Keys(String code){
		this.code = code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	

}
