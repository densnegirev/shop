package dbservice;

import main.UserGroup;
import main.UserProfile;
import java.sql.*;
import java.util.ArrayList;
import com.hxtt.sql.dbf.DBFDriver;
import shop.Item;

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
}
