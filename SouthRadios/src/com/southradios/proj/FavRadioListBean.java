package com.southradios.proj;

public class FavRadioListBean {
	
	private int favRadioId;
	private String favRadioName;
	private String favRadioCaption;
	private String favRadioUrl;
	private int favRadioIcon;
	
	


	public FavRadioListBean(int radioId, String radioName, String radioCaption, String radioUrl, int radioIcon) {

		this.favRadioId = radioId;
		this.favRadioName = radioName;
		this.favRadioCaption = radioCaption;
		this.favRadioUrl = radioUrl;
		this.favRadioIcon = radioIcon;
	}




	public int getFavRadioId() {
		return favRadioId;
	}




	public void setFavRadioId(int favRadioId) {
		this.favRadioId = favRadioId;
	}




	public String getFavRadioName() {
		return favRadioName;
	}




	public void setFavRadioName(String favRadioName) {
		this.favRadioName = favRadioName;
	}




	public String getFavRadioCaption() {
		return favRadioCaption;
	}




	public void setFavRadioCaption(String favRadioCaption) {
		this.favRadioCaption = favRadioCaption;
	}




	public String getFavRadioUrl() {
		return favRadioUrl;
	}




	public void setFavRadioUrl(String favRadioUrl) {
		this.favRadioUrl = favRadioUrl;
	}




	public int getFavRadioIcon() {
		return favRadioIcon;
	}




	public void setFavRadioIcon(int favRadioIcon) {
		this.favRadioIcon = favRadioIcon;
	}

	




}
