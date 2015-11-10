package dbservice;

import main.UserProfile;

public interface DBService {
	boolean addUser(String userName, UserProfile userProfile);
	boolean updateUser(String password, String email, String familiya, String imya, String otchestvo, String phone, String address, UserProfile up);
	UserProfile getUser(String userName);
}
