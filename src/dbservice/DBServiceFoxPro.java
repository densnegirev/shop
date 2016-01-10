package dbservice;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import com.hxtt.sql.dbf.DBFDriver;
import shop.Item;
import shop.TrashItem;
import main.Globals;
import main.UserGroup;
import main.UserProfile;

public class DBServiceFoxPro implements DBService {
	private String url = "jdbc:DBF:///db";
	private Connection con;

	public DBServiceFoxPro() {
		try {
			Driver driver = (DBFDriver)Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
			DriverManager.registerDriver(driver);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new Error("Couldn't create DBService");
		}
	}

	@Override
	public boolean addUser(String userName, UserProfile userProfile) {
		String sql1 = "INSERT INTO users (group_id, login, password, familiya, imya, otchestvo, email, address, phone) VALUES ("
				+ "1, '" + userName
				+ "', '" + userProfile.getPassword()
				+ "', '" + userProfile.getFamiliya()
				+ "', '" + userProfile.getImya()
				+ "', '" + userProfile.getOtchestvo()
				+ "', '" + userProfile.getEmail()
				+ "', '" + userProfile.getAddress()
				+ "', '" + userProfile.getPhone() + "')";

		System.out.println("SQL: " + sql1);

		try {
			con = DriverManager.getConnection(url, "", "");

			Statement stmt = con.createStatement();

			stmt.executeUpdate(sql1);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public UserProfile getUser(String userName) {
		String sql = "SELECT * FROM users WHERE login = '" + userName + "'";

		try {
			con = DriverManager.getConnection(url, "", "");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			UserProfile up = new UserProfile();
			boolean found = false;

			if (rs.next()) {
				// (id, group, login, password, familiya, imya, otchestvo, email, address, phone)
				Object familiyaObj = rs.getObject(5);
				Object imyaObj = rs.getObject(6);
				Object otchestvoObj = rs.getObject(7);
				Object emailObj = rs.getObject(8);
				Object addressObj = rs.getObject(9);
				Object phoneObj = rs.getObject(10);

				up.setID((Integer)rs.getObject(1));
				up.setGroupID((Integer)rs.getObject(2));
				up.setLogin(rs.getObject(3).toString());
				up.setPassword(rs.getObject(4).toString());
				up.setFamiliya(familiyaObj == null ? "" : familiyaObj.toString());
				up.setImya(imyaObj == null ? "" : imyaObj.toString());
				up.setOtchestvo(otchestvoObj == null ? "" : otchestvoObj.toString());
				up.setEmail(emailObj == null ? "" : emailObj.toString());
				up.setAddress(addressObj == null ? "" : addressObj.toString());
				up.setPhone(phoneObj == null ? "" : phoneObj.toString());

				found = true;
			}

			rs.close();
			stmt.close();
			con.close();

			return found ? up : null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean updateUser(String password, String familiya, String imya, String otchestvo, String email, String address, String phone, UserProfile up) {
		String passwordNew = up.getPassword().equals(password) ? "" : " password = '" + password + "'";
		String familiyaNew = up.getFamiliya().equals(familiya) ? "" : " familiya = '" + familiya + "'";
		String imyaNew = up.getImya().equals(imya) ? "" : " imya = '" + imya + "'";
		String otchestvoNew = up.getOtchestvo().equals(otchestvo) ? "" : " otchestvo = '" + otchestvo + "'";
		String emailNew = up.getEmail().equals(email) ? "": " email = '" + email + "'";
		String addressNew = up.getAddress().equals(address) ? "" : " address = '" + address + "'";
		String phoneNew = up.getPhone().equals(phone) ? "" : " phone = '" + phone + "'";
		StringBuilder sql = new StringBuilder().append("UPDATE users SET");
		StringBuilder params = new StringBuilder();
		params.append(passwordNew).append(familiyaNew).append(imyaNew).append(otchestvoNew).append(emailNew).append(addressNew).append(phoneNew);

		if (params.length() == 0) {
			return true;
		}

		params.append(" WHERE user_id = ").append(up.getId());
		sql.append(params);

		String sqlFix = sql.toString().replace("' ", "', ").replace(", W", " W");

		System.out.println(sqlFix);

		try {
			con = DriverManager.getConnection(url, "", "");
			
			Statement stmt = con.createStatement();

			stmt.executeUpdate(sqlFix);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	@Override
	public UserGroup getGroup(int groupId) {
		UserGroup ug = new UserGroup();

		try {
			con = DriverManager.getConnection(url, "", "");

			String sql = "SELECT groups.name, groups.color FROM groups WHERE groups.group_id = " + groupId;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				ug.setName(rs.getObject(1).toString());
				ug.setColor(rs.getObject(2).toString());
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ug;
	}

	@Override
	public ArrayList<Item> getItems() {
		ArrayList<Item> result = new ArrayList<>();

		try {
			con = DriverManager.getConnection(url, "", "");

			String sql = "SELECT items.item_id, fabricators.name, fabricators.country, types.type, hdformats.hd_format, resolutions.resolution, items.model, items.diagonal, items.price, items.count FROM items, fabricators, types, hdformats, resolutions WHERE items.fabric_id = fabricators.fabric_id AND items.type_id = types.type_id AND items.format_id = hdformats.format_id AND items.resolution_id = resolutions.resolution_id";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = (int)rs.getObject(1);
				String fabricName = rs.getObject(2).toString();
				String fabricCountry = rs.getObject(3).toString();
				String type = rs.getObject(4).toString();
				String format = rs.getObject(5).toString();
				String resolution = rs.getObject(6).toString();
				String model = rs.getObject(7).toString();
				int diagonal = (int)rs.getObject(8);
				int price = (int)rs.getObject(9);
				int count = (int)rs.getObject(10);

				result.add(new Item(id, fabricName, fabricCountry, type, format, resolution, model, diagonal, price, count));
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Item getItem(int itemId) {
		Item item = null;

		try {
			con = DriverManager.getConnection(url, "", "");

			String sql = "SELECT items.item_id, fabricators.name, fabricators.country, types.type, hdformats.hd_format, resolutions.resolution, items.model, items.diagonal, items.price, items.count FROM items, fabricators, types, hdformats, resolutions WHERE items.fabric_id = fabricators.fabric_id AND items.type_id = types.type_id AND items.format_id = hdformats.format_id AND items.resolution_id = resolutions.resolution_id AND items.item_id = " + itemId;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				int id = (int)rs.getObject(1);
				String fabricName = rs.getObject(2).toString();
				String fabricCountry = rs.getObject(3).toString();
				String type = rs.getObject(4).toString();
				String format = rs.getObject(5).toString();
				String resolution = rs.getObject(6).toString();
				String model = rs.getObject(7).toString();
				int diagonal = (int)rs.getObject(8);
				int price = (int)rs.getObject(9);
				int count = (int)rs.getObject(10);

				item = new Item(id, fabricName, fabricCountry, type, format, resolution, model, diagonal, price, count);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return item;
	}

	@Override
	public void addItem(String fabricName, String fabricCountry, String type, String hdFormat, String resolution, String model, String diagonal, String price, String count) {
		int fabricId = getFabricId(fabricName, fabricCountry);
		int typeId = getTypeId(type);
		int formatId = getFormatId(hdFormat);
		int resolutionId = getResolutionId(resolution);

		String sql = "INSERT INTO items (fabric_id, type_id, format_id, resolution_id, model, diagonal, price, count) VALUES (" +
				fabricId + ", " +
				typeId + ", " +
				formatId + ", " +
				resolutionId + ", " +
				"'" + model + "', " +
				diagonal + ", " +
				price + ", " +
				count + ")";

		try {
			con = DriverManager.getConnection(url, "", "");

			Statement stmt = con.createStatement();

			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Catalog ADD SQL: " + sql);
	}
	/*
	@Override
	public void updateItem(String itemId, String fabricName, String fabricCountry, String type, String hdFormat, String resolution, String model, String diagonal, String price, String count) {

	}
	*/
	@Override
	public void deleteItem(String itemId) {
		String sql = "DELETE FROM items WHERE items.item_id = " + itemId;

		try {
			con = DriverManager.getConnection(url, "", "");

			Statement stmt = con.createStatement();

			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void moveItemsFromTrash(int userId) {
		LinkedList<TrashItem> userItems = Globals.TRASH.getUserItems(userId);
		DateFormat dateFormat = new SimpleDateFormat("{^yyyy-MM-dd}");
		Calendar cal = Calendar.getInstance();
		String orderDate = dateFormat.format(cal.getTime());

		System.out.println(orderDate);

		try {
			String sql = "INSERT INTO ordersdbf (user_id, order_date) VALUES (" +
					userId + ", " +
					"'" + orderDate + "')";

			con = DriverManager.getConnection(url, "", "");

			Statement stmt = con.createStatement();

			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (TrashItem trashItem : userItems) {
			try {
				int orderDateId = getOrderDateId(userId, orderDate);
				String sql = "INSERT INTO orderdata (orderdate_id, item_id, amount) VALUES (" +
						orderDate + ", " +
						trashItem.getItemId() + ", " +
						trashItem.getAmount() + ")";

				con = DriverManager.getConnection(url, "", "");

				Statement stmt = con.createStatement();

				stmt.executeUpdate(sql);
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private int getOrderDateId(int userId, String orderDate) {
		int res = -1;

		try {
			con = DriverManager.getConnection(url, "", "");

			String sql = "SELECT ordersdbf.orderdate_id FROM ordersdbf WHERE ordersdbf.user_id = " + userId + " AND ordersdbf.order_date = " + orderDate;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				res = (int)rs.getObject(1);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	private int getFabricId(String name, String country) {
		int res = getFabricIdHelper(name, country);

		if (res == -1) {
			String sql = "INSERT INTO fabricators (name, country) VALUES ('" + name + "', '" + country + "')";

			try {
				con = DriverManager.getConnection(url, "", "");

				Statement stmt = con.createStatement();

				stmt.executeUpdate(sql);
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			res = getFabricIdHelper(name, country);
		}

		return res;
	}

	private int getFabricIdHelper(String name, String country) {
		int res = -1;

		try {
			con = DriverManager.getConnection(url, "", "");

			String sql = "SELECT fabricators.fabric_id FROM fabricators WHERE fabricators.name = '" + name + "' AND fabricators.country = '" + country + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				res = (int)rs.getObject(1);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	private int getTypeId(String value) {
		int res = getTypeIdHelper(value);

		if (res == -1) {
			String sql = "INSERT INTO types (type) VALUES ('" + value + "')";

			try {
				con = DriverManager.getConnection(url, "", "");

				Statement stmt = con.createStatement();

				stmt.executeUpdate(sql);
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			res = getTypeIdHelper(value);
		}

		return res;
	}

	private int getTypeIdHelper(String value) {
		int res = -1;

		try {
			con = DriverManager.getConnection(url, "", "");

			String sql = "SELECT types.type_id FROM types WHERE types.type = '" + value + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				res = (int)rs.getObject(1);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	private int getFormatId(String value) {
		int res = getFormatIdHelper(value);

		if (res == -1) {
			String sql = "INSERT INTO hdformats (hd_format) VALUES ('" + value + "')";

			try {
				con = DriverManager.getConnection(url, "", "");

				Statement stmt = con.createStatement();

				stmt.executeUpdate(sql);
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			res = getFormatIdHelper(value);
		}

		return res;
	}

	private int getFormatIdHelper(String value) {
		int res = -1;

		try {
			con = DriverManager.getConnection(url, "", "");

			String sql = "SELECT hdformats.format_id FROM hdformats WHERE hdformats.hd_format = '" + value + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				res = (int)rs.getObject(1);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	private int getResolutionId(String value) {
		int res = getResolutionIdHelper(value);

		if (res == -1) {
			String sql = "INSERT INTO resolutions (resolution) VALUES ('" + value + "')";

			try {
				con = DriverManager.getConnection(url, "", "");

				Statement stmt = con.createStatement();

				stmt.executeUpdate(sql);
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			res = getResolutionIdHelper(value);
		}

		return res;
	}

	private int getResolutionIdHelper(String value) {
		int res = -1;

		try {
			con = DriverManager.getConnection(url, "", "");

			String sql = "SELECT resolutions.resolution_id FROM resolutions WHERE resolutions.resolution = '" + value + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				res = (int)rs.getObject(1);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
}
