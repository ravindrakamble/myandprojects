package com.southradios.proj;

public class PaadalRadioListBean {

	private int paadalRadioId;
	private String paadalRadioName;
	private String paadalRadioCaption;
	private String paadalRadioUrl;
	private int paadalRadioIcon;

	public PaadalRadioListBean(int paadalRadioId ,String paadalRadioName, String paadalRadioCaption, String paadalRadioUrl, int paadalRadioIcon) {
		
		this.paadalRadioId = paadalRadioId;
		this.paadalRadioName = paadalRadioName;
		this.paadalRadioCaption = paadalRadioCaption;
		this.paadalRadioUrl = paadalRadioUrl;
		this.paadalRadioIcon = paadalRadioIcon;
	}
	
	

	public int getPaadalRadioId() {
		return paadalRadioId;
	}



	public void setPaadalRadioId(int paadalRadioId) {
		this.paadalRadioId = paadalRadioId;
	}



	public String getPaadalRadioName() {
		return paadalRadioName;
	}

	public void setPaadalRadioName(String paadalRadioName) {
		this.paadalRadioName = paadalRadioName;
	}

	public String getPaadalRadioCaption() {
		return paadalRadioCaption;
	}

	public void setPaadalRadioCaption(String paadalRadioCaption) {
		this.paadalRadioCaption = paadalRadioCaption;
	}

	public String getPaadalRadioUrl() {
		return paadalRadioUrl;
	}

	public void setPaadalRadioUrl(String paadalRadioUrl) {
		this.paadalRadioUrl = paadalRadioUrl;
	}

	public int getPaadalRadioIcon() {
		return paadalRadioIcon;
	}

	public void setPaadalRadioIcon(int paadalRadioIcon) {
		this.paadalRadioIcon = paadalRadioIcon;
	}
	
	

}
