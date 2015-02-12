package com.r2.appservices.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public enum ContactDAO {
	INSTANCE;

	@SuppressWarnings("unchecked")
	public List<Contact> listContacts() {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Contact.class);
		List<Contact> contacts = (List<Contact>)pm.newQuery(query).execute();
		return contacts;
	}

	public void add(Contact contact) {
		synchronized (this) {
			PersistenceManager pm = getPersistenceManager();
			pm.makePersistent(contact);
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Contact> getContactsFotrUser(String userId) {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Contact.class);
		List<Contact> contacts = (List<Contact>)pm.newQuery(query).execute();
		return contacts;
	}

	public void remove(long id) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Object obj = pm.getObjectById(Contact.class, id);
			pm.deletePersistent(obj);
		} finally {
			pm.close();
		}
	}
	
	public void addAddress(Address address) {
		synchronized (this) {
			PersistenceManager pm = getPersistenceManager();
			pm.makePersistent(address);
			pm.close();
		}
	}
	
	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}
} 
