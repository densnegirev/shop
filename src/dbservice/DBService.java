package dbservice;

import main.UserProfile;

public interface DBService {
	boolean addUser(String userName, UserProfile userProfile);
	boolean updateUser(String password, String familiya, String imya, String otchestvo, String email, String address, String phone, UserProfile up);
	UserProfile getUser(String userName);
}
