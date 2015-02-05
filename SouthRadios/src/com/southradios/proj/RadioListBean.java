package com.southradios.proj;

public class RadioListBean {
	
	private int radioId;
	private String radioName;
	private String radioCaption;
	private String radioUrl;
	private int radioIcon;
	
	


	public RadioListBean(int radioId, String radioName, String radioCaption, String radioUrl, int radioIcon) {

		this.radioId = radioId;
		this.radioName = radioName;
		this.radioCaption = radioCaption;
		this.radioUrl = radioUrl;
		this.radioIcon = radioIcon;
	}

	

	public int getRadioId() {
		return radioId;
	}



	public void setRadioId(int radioId) {
		this.radioId = radioId;
	}



	public String getRadioName() {
		return radioName;
	}


	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}


	public String getRadioCaption() {
		return radioCaption;
	}


	public void setRadioCaption(String radioCaption) {
		this.radioCaption = radioCaption;
	}


	public int getRadioIcon() {
		return radioIcon;
	}


	public void setRadioIcon(int radioIcon) {
		this.radioIcon = radioIcon;
	}


	public String getRadioUrl() {
		return radioUrl;
	}


	public void setRadioUrl(String radioUrl) {
		this.radioUrl = radioUrl;
	}
	



}
