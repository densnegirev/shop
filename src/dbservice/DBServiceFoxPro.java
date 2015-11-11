package dbservice;

import main.UserProfile;
import java.sql.*;
import com.hxtt.sql.dbf.DBFDriver;

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
		String sql1 = "INSERT INTO users (login, password, familiya, imya, otchestvo, email, address, phone) VALUES ('"
				+ userName
				+ "', '" + userProfile.getPassword()
				+ "', '" + userProfile.getFamiliya()
				+ "', '" + userProfile.getImya()
				+ "', '" + userProfile.getOtchestvo()
				+ "', '" + userProfile.getEmail()
				+ "', '" + userProfile.getAddress()
				+ "', '" + userProfile.getPhone() + "')";
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
				// (id, login, password, familiya, imya, otchestvo, email, address, phone)
				Object familiyaObj = rs.getObject(4);
				Object imyaObj = rs.getObject(5);
				Object otchestvoObj = rs.getObject(6);
				Object emailObj = rs.getObject(7);
				Object addressObj = rs.getObject(8);
				Object phoneObj = rs.getObject(9);

				up.setID((Integer)rs.getObject(1));
				up.setLogin(rs.getObject(2).toString());
				up.setPassword(rs.getObject(3).toString());
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
}
