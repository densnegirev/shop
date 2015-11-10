package dbservice;

import main.UserProfile;
import java.util.HashMap;
import java.util.Map;

public class DBServiceRam implements DBService {
	private Map<String, UserProfile> users = new HashMap<>();

	@Override
	public boolean addUser(String userName, UserProfile userProfile) {
		if (users.containsKey(userName)) {
			return false;
		}

		users.put(userName, userProfile);

		return true;
	}

	@Override
	public UserProfile getUser(String userName) {
		return users.get(userName);
	}

	@Override
	public boolean updateUser(String password, String email, String familiya, String imya, String otchestvo, String phone, String address, UserProfile up) {
		UserProfile u = users.get(up.getLogin());
		if (u == null) {
			return false;
		}

		u.update(password, email, familiya, imya, otchestvo, phone, address);
		
		return true;
	}
}
