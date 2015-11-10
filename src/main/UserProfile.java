package main;

public class UserProfile {
	private String login;
	private String password;
	private String email;
	private String familiya;
	private String imya;
	private String otchestvo;
	private String phone;
	private String address;
	private Integer id;

	public UserProfile() {}

	public UserProfile(String login, String password, String email, String familiya, String imya, String otchestvo, String phone, String address) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.familiya = familiya;
		this.imya = imya;
		this.phone = phone;
		this.otchestvo = otchestvo;
		this.address = address;
	}

	public void update(String password, String email, String familiya, String imya, String otchestvo, String phone, String address) {
		this.password = password;
		this.email = email;
		this.familiya = familiya;
		this.imya = imya;
		this.phone = phone;
		this.otchestvo = otchestvo;
		this.address = address;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public Integer getId() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFamiliya() {
		return familiya;
	}

	public void setFamiliya(String familiya) {
		this.familiya = familiya;
	}

	public String getImya() {
		return imya;
	}

	public void setImya(String imya) {
		this.imya = imya;
	}

	public String getOtchestvo() {
		return otchestvo;
	}

	public void setOtchestvo(String otchestvo) {
		this.otchestvo = otchestvo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
