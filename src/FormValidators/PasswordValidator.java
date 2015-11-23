package formvalidators;

public class PasswordValidator extends FormValidator {
	public PasswordValidator(String value) {
		this.value = value;
	}

	@Override
	public boolean validate() {
		return value.length() >= 8;
	}

	@Override
	public String getErrorMsg() {
		return "Пароль должен быть длинной не менее 8 символов";
	}
}
