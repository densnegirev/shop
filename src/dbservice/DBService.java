package dbservice;

import main.UserProfile;
import product.Item;
import java.util.ArrayList;

public interface DBService {
	boolean addUser(String userName, UserProfile userProfile);
	boolean updateUser(String password, String familiya, String imya, String otchestvo, String email, String address, String phone, UserProfile up);
	UserProfile getUser(String userName);

	ArrayList<Item> getItems(int offset, int count);
}
