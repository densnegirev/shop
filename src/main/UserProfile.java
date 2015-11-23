package main;

public class UserProfile {
	private Integer id;
	private Integer groupId;
	private String login;
	private String password;
	private String familiya;
	private String imya;
	private String otchestvo;
	private String email;
	private String address;
	private String phone;

	public UserProfile() {}

	public UserProfile(String login, String password, String familiya, String imya, String otchestvo, String email, String address, String phone) {
		this.login = login;
		this.password = password;
		this.familiya = familiya;
		this.imya = imya;
		this.otchestvo = otchestvo;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}

	public void update(String password, String familiya, String imya, String otchestvo, String email, String address, String phone) {
		this.password = password;
		this.familiya = familiya;
		this.imya = imya;
		this.otchestvo = otchestvo;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public Integer getGroupID() {
		return groupId;
	}

	public void setGroupID(Integer id) {
		this.groupId = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
