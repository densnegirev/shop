package formvalidators;

public class LoginValidator extends FormValidator {
	public LoginValidator(String value, String errorMsg) {
		this.value = value;
		this.errorMsg = errorMsg;
	}

	@Override
	public boolean validate() {
		return value.length() > 0;
	}
}
