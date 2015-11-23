package formvalidators;

public class LoginValidator extends FormValidator {
	public LoginValidator(String value) {
		this.value = value;
	}

	@Override
	public boolean validate() {
		return value.length() >= 3;
	}

	@Override
	public String getErrorMsg() {
		return "Логин должен состоять хотя бы из трех символов";
	}
}
