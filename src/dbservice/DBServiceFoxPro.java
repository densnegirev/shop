package dbservice;

import main.UserProfile;
import java.sql.*;
import com.hxtt.sql.dbf.DBFDriver;

public class DBServiceFoxPro implements DBService {
	private String url = "jdbc:DBF:///db";
	private Connection con;
	private Driver driver;

	public DBServiceFoxPro() {
		try {
			driver = (DBFDriver)Class.forName("com.hxtt.sql.dbf.DBFDriver").newInstance();
			DriverManager.registerDriver(driver);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new Error("Couldn't create DBService");
		}
	}

	@Override
	public boolean addUser(String userName, UserProfile userProfile) {
		String sql1 = "INSERT INTO users (login, email, password, address, phone, familiya, imya, otchestvo) VALUES ('"
				+ userName.toLowerCase()
				+ "', '" + userProfile.getEmail()
				+ "', '" + userProfile.getPassword()
				+ "', '" + userProfile.getAddress()
				+ "', '" + userProfile.getPhone()
				+ "', '" + userProfile.getFamiliya()
				+ "', '" + userProfile.getImya()
				+ "', '" + userProfile.getOtchestvo() + "')";
		try {
			con = DriverManager.getConnection(url, "", "");

			Statement stmt = con.createStatement();
			int rse = stmt.executeUpdate(sql1);
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public UserProfile getUser(String userName) {
		String sql = "SELECT * FROM users WHERE login='" + userName.toLowerCase() + "'";

		try {
			con = DriverManager.getConnection(url, "", "");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			UserProfile up = new UserProfile();
			boolean found = false;

			if (rs.next()) {
				//(id, login, email, password, address, phone, familiya, imya, otchestvo)
				up.setID((Integer)rs.getObject(1));
				up.setLogin(rs.getObject(2).toString());
				up.setEmail(rs.getObject(3).toString());
				up.setPassword(rs.getObject(4).toString());
				up.setAddress(rs.getObject(5).toString());
				up.setPhone(rs.getObject(6).toString());
				up.setFamiliya(rs.getObject(7).toString());
				up.setImya(rs.getObject(8).toString());
				up.setOtchestvo(rs.getObject(9).toString());

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
	public boolean updateUser(String password, String email, String familiya, String imya, String otchestvo, String phone, String address, UserProfile up) {
		String pass = up.getPassword().equals(password) ? "" : " password='" + password + "'";
		String mail = up.getEmail().equals(email) ? "": " email='" + email + "'";
		String fam = up.getFamiliya().equals(familiya) ? "" : " familiya='" + familiya + "'";
		String name = up.getImya().equals(imya) ? "" : " imya='" + imya + "'";
		String otc = up.getOtchestvo().equals(otchestvo) ? "" : " otchestvo='"+otchestvo+"'";
		String ph = up.getPhone().equals(phone) ? "" : " phone='" + phone + "'";
		String addr = up.getAddress().equals(address) ? "" : " address='" + address + "'";
		//(id, login, email, password, address, phone, familiya, imya, otchestvo)
		StringBuilder sql= new StringBuilder().append("UPDATE users SET");
		
		sql.append(mail).append(pass).append(addr).append(ph).append(fam).append(name).append(otc).append(" WHERE id='").append(up.getId() + "'");
		
		String sqll = sql.toString().replace("' ", "' ,").replace(",W", "W");

		System.out.println(sqll.toString());

		try {
			con = DriverManager.getConnection(url, "", "");
			
			Statement stmt = con.createStatement();
			int rse = stmt.executeUpdate(sqll);

			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}
}
