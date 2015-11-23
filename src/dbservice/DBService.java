package dbservice;

import main.UserGroup;
import main.UserProfile;
import shop.Item;
import java.util.ArrayList;

public interface DBService {
	boolean addUser(String userName, UserProfile userProfile);
	boolean updateUser(String password, String familiya, String imya, String otchestvo, String email, String address, String phone, UserProfile up);
	UserProfile getUser(String userName);

	UserGroup getGroup(int groupId);

	ArrayList<Item> getItems(int offset, int count);
	ArrayList<Item> getTrashItems(int offset, int count);
}
