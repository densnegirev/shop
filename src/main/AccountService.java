package main;

import dbservice.DBService;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
	private DBService dbservice;
	private Map<String, UserProfile> sessions = new HashMap<>();

	public AccountService(DBService dbservice) {
		if (dbservice != null) {
			this.dbservice = dbservice;
		}
	}

	public void updateUser(String sessionId, String password, String familiya, String imya, String otchestvo, String email, String address, String phone) {
		if (dbservice.updateUser(password, familiya, imya, otchestvo, email, address, phone, sessions.get(sessionId))) {
			sessions.get(sessionId).update(password, familiya, imya, otchestvo, email, address, phone);
		}
	}

	public boolean addUser(String userName, UserProfile userProfile) {
		return dbservice.addUser(userName, userProfile);
	}

	public void addSessions(String sessionId, UserProfile userProfile) {
		sessions.put(sessionId, userProfile);
	}

	public UserProfile getUser(String userName) {
		return dbservice.getUser(userName);
	}

	public UserProfile getSessions(String sessionId) {
		return sessions.get(sessionId);
	}

	public void removeSessions(String sessionId) {
		sessions.remove(sessionId);
	}
}
