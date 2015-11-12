package formvalidators;

public class PasswordValidator extends FormValidator {
	public PasswordValidator(String value, String errorMsg) {
		this.value = value;
		this.errorMsg = errorMsg;
	}

	@Override
	public boolean validate() {
		return value.length() >= 8;
	}
}
