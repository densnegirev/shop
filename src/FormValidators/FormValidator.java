package formvalidators;

public abstract class FormValidator {
	protected String value;
	protected String errorMsg;

	public enum Types {
		LOGIN,
		PASSWORD
	}

	public abstract boolean validate();

	public static FormValidator create(Types type, String value, String errorMsg) {
		FormValidator result = null;

		switch (type) {
			case LOGIN:
				result = new LoginValidator(value, errorMsg);

				break;

			case PASSWORD:
				result = new PasswordValidator(value, errorMsg);

				break;
		}

		return result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
