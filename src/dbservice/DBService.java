package dbservice;

import main.UserGroup;
import main.UserProfile;
import shop.Item;
import shop.Order;

import java.util.ArrayList;

public interface DBService {
	boolean addUser(String userName, UserProfile userProfile);
	boolean updateUser(String password, String familiya, String imya, String otchestvo, String email, String address, String phone, UserProfile up);
	UserProfile getUser(String userName);

	UserGroup getGroup(int groupId);

	ArrayList<Item> getItems();
	Item getItem(int itemId);

	void addItem(String fabricName, String fabricCountry, String type, String hdFormat, String resolution, String model, String diagonal, String price, String count);
	//void updateItem(String itemId, String fabricName, String fabricCountry, String type, String hdFormat, String resolution, String model, String diagonal, String price, String count);
	void deleteItem(String itemId);

	void moveItemsFromTrash(int userId);

	ArrayList<Order> getUserOrders(int userId);
}
