package com.r2.appservices;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r2.appservices.dao.Address;
import com.r2.appservices.dao.ApiResponse;
import com.r2.appservices.dao.Contact;
import com.r2.appservices.dao.ContactDAO;
import com.r2.appservices.utils.Constants;

@Api(name = "contact")
public class Services {

	

	@ApiMethod(name = "addContact")
	public ApiResponse addContact(Contact contact){
		ContactDAO.INSTANCE.add(contact);
		
		String id = String.valueOf(contact.getId());
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setResponseCode(Constants.CONTACT_ADDED);
		apiResponse.setResponseMessage(Constants.MSG_CONTACT_ADDED);
		apiResponse.setResponseStatus(Constants.SUCCESS);
		apiResponse.setResponseBody(id);
		
		return apiResponse;
	}

	@ApiMethod(
		    name = "getcontacts",
		    path = "getContacts",
		    httpMethod = HttpMethod.GET
		 )
	public ApiResponse getContacts(){
		List<Contact> contacts = ContactDAO.INSTANCE.listContacts();
		
		ApiResponse getcontacts = new ApiResponse();
		getcontacts.setResponseCode(Constants.CONTACT_ADDED);
		getcontacts.setResponseStatus(Constants.SUCCESS);
		
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Contact>>(){}.getType();
		getcontacts.setResponseBody(gson.toJson(contacts, collectionType));
		return getcontacts;
	}	
	
	@ApiMethod(
		    name = "delete",
		    path = "delete",
		    httpMethod = HttpMethod.GET
		 )
	public ApiResponse delete(@Named("id") Long id){
		ContactDAO.INSTANCE.remove(id);
		
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setResponseCode(Constants.CONTACT_ADDED);
		apiResponse.setResponseStatus(Constants.SUCCESS);
		
		
		return apiResponse;
	}	
	
	@ApiMethod(
		    name = "addAddress",
		    path = "addAddress",
		    httpMethod = HttpMethod.POST
		 )
	public ApiResponse addAddress(Address address){
		ContactDAO.INSTANCE.addAddress(address);
		
		String id = String.valueOf(address.getId());
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setResponseCode(Constants.ADDRESS_ADDED);
		apiResponse.setResponseStatus(Constants.SUCCESS);
		apiResponse.setResponseMessage(Constants.MSG_ADDRESS_ADDED);
		apiResponse.setResponseBody(id);
		
		return apiResponse;
	}
	
}
