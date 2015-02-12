package com.r2.appservices.dao;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@SuppressWarnings("unchecked")
public enum TrackerDAO {
	INSTANCE;

	public List<User> listUsers() {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(User.class);
		List<User> users = (List<User>)pm.newQuery(query).execute();
		return users;
	}

	public List<Device> listDevices() {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Device.class);
		List<Device> devices = (List<Device>)pm.newQuery(query).execute();
		return devices;
	}
	public List<UserLocation> listCurrentLocations() {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(UserLocation.class);
		List<UserLocation> userLocations = (List<UserLocation>)pm.newQuery(query).execute();
		pm.close();
		return userLocations;
	}


	public boolean removeAllLocations() {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(UserLocation.class);
		List<UserLocation> userLocations = (List<UserLocation>)pm.newQuery(query).execute();
		for(UserLocation loc : userLocations){
			pm.deletePersistent(loc);
		}
		pm.close();
		return true;
	}

	public void add(User user) {
		synchronized (this) {
			PersistenceManager pm = getPersistenceManager();
			pm.makePersistent(user);
			pm.close();
		}
	}

	public void add(UserDetails user) {
		synchronized (this) {
			PersistenceManager pm = getPersistenceManager();
			pm.makePersistent(user);
			pm.close();
		}
	}
	
	public void returnDevice(UserDetails user) {
		synchronized (this) {
			PersistenceManager pm = getPersistenceManager();
			pm.makePersistent(user);
			pm.close();
		}
	}
	
	public void add(Device device) {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Device.class);
		query.setFilter("imei =='" + device.getImei() +"'");

		List<Device> devices = (List<Device>)pm.newQuery(query).execute();
		if(devices.size() == 0){
			synchronized (this) {
				pm.makePersistent(device);
				pm.close();
			}
		}
	}

	public boolean updateRingStatus(String imei, int status) {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Device.class);
		query.setFilter("imei =='" + imei +"'");

		List<Device> devices = (List<Device>)pm.newQuery(query).execute();
		if(devices.size() > 0){
			Device device = devices.get(0);
			device.setRingStatus(status);
		}
		pm.close();
		return true;
	}

	public int getDevice(String imei) {
		int status = 0;
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Device.class);
		query.setFilter("imei =='" + imei +"'");
		Device device = null;
		List<Device> devices = (List<Device>)pm.newQuery(query).execute();
		if(devices.size() > 0){
			device = devices.get(0);
			status = device.getRingStatus();
			if(device.getRingStatus() != 0){
				device.setRingStatus(0);
			}
		}
		pm.close();
		return status;
	}

	public boolean removeAllDevices() {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Device.class);
		List<Device> devices = (List<Device>)pm.newQuery(query).execute();
		for(Device loc : devices){
			pm.deletePersistent(loc);
		}
		pm.close();
		return true;
	}

	public User login(User user) {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(User.class);
		query.setFilter("username =='" + user.getUsername() +"' && password=='" + user.getPassword() + "'");
		List<User> users = (List<User>)pm.newQuery(query).execute();

		if(users.size() > 0){
			return users.get(0);
		}else{
			return null;
		}
	}

	public void remove(long id) {
		PersistenceManager pm = getPersistenceManager();
		try {
			Object obj = pm.getObjectById(User.class, id);
			pm.deletePersistent(obj);
		} finally {
			pm.close();
		}
	}

	public UserLocation addOrUpdate(UserLocation userLocation) {
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(UserLocation.class);
		query.setFilter("userId ==" + userLocation.getUserId());

		List<UserLocation> userLocations = (List<UserLocation>)pm.newQuery(query).execute();
		if(userLocations.size() == 0){
			synchronized (this) {
				if(userLocation.getUpdatedAt() == null)
					userLocation.setUpdatedAt(new Date());
				pm.makePersistent(userLocation);
				pm.close();
			}
		}else{
			UserLocation fromDB = userLocations.get(0);
			fromDB.setLatitude(userLocation.getLatitude());
			fromDB.setLongitude(userLocation.getLongitude());
			fromDB.setAltitude(userLocation.getAltitude());
				fromDB.setProjectName(userLocation.getProjectName());
			fromDB.setStartDate(userLocation.getStartDate());
			fromDB.setReturnDate(userLocation.getReturnDate());
			fromDB.setUserEmail(userLocation.getUserEmail());
			fromDB.setFirstame(userLocation.getFirstame());
			fromDB.setUpdatedAt(new Date());

			fromDB.setDescription(userLocation.getDescription());
			pm.close();
		}

		return userLocation;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}
} 

