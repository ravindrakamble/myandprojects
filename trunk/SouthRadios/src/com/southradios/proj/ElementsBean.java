package com.southradios.proj;

import org.jsoup.select.Elements;

public class ElementsBean {

	private ElementsBean(){
		
	}
	
	private static ElementsBean elementsBean;

	private Elements elements;

	public Elements getElements() {
		return elements;
	}

	public void setElements(Elements elements) {
		this.elements = elements;
	}
	
	public static ElementsBean getInstance(){
		
		if(elementsBean == null){
			synchronized (ElementsBean.class) {
				if(elementsBean == null){
					elementsBean = new ElementsBean();
				}
			}
		}
		
		return elementsBean;
		
	}
	
}
