package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import formvalidators.FormValidator;
import main.Globals;
import main.UserProfile;
import templater.PageBuilder;
import templater.PageGenerator;

public class ProfileServlet extends HttpServlet {
	private String errors;

	public ProfileServlet() {
		errors = "";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageBuilder pageBuilder = new PageBuilder(request, response);
		String content = getContent(request);

		if (content.length() == 0) {
			response.sendRedirect("/index");
		}

		pageBuilder.setTitle(Globals.SITE_TITLE + " | Профиль");
		pageBuilder.setContent(content);
		pageBuilder.buildPage();

		errors = "";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		String passwordAgain = request.getParameter("password_again");
		String familiya = request.getParameter("familiya");
		String imya = request.getParameter("imya");
		String otchestvo = request.getParameter("otchestvo");
		String email =  request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		HttpSession session = request.getSession();
		FormValidator[] validators = new FormValidator[] {
				FormValidator.create(FormValidator.Types.PASSWORD, password)
		};

		errors = "";

		for (int i = 0; i < validators.length; ++i) {
			if (!validators[i].validate()) {
				errors += "<li>" + validators[i].getErrorMsg() + "</li>";
			}
		}

		if (!password.equals(passwordAgain)) {
			errors += "<li>Пароли не совпадают</li>";
		}

		if (errors.length() == 0) {
			Globals.ACCOUNT_SERVICE.updateUser(session.getId(), password, familiya, imya, otchestvo, email, address, phone);
			response.sendRedirect("/index");
		} else {
			errors = "<ul>" + errors + "</ul>";

			response.sendRedirect("/profile");
		}
	}

	private String getContent(HttpServletRequest request) {
		Map<String, Object> pageVariables = new HashMap<>();
		HttpSession session = request.getSession();
		UserProfile up = Globals.ACCOUNT_SERVICE.getSessions(session.getId());

		if (up != null) {
			pageVariables.put("password", up.getPassword());
			pageVariables.put("password_again", up.getPassword());
			pageVariables.put("familiya", up.getFamiliya());
			pageVariables.put("imya", up.getImya());
			pageVariables.put("otchestvo", up.getOtchestvo());
			pageVariables.put("email", up.getEmail());
			pageVariables.put("address", up.getAddress());
			pageVariables.put("phone", up.getPhone());
			pageVariables.put("errors", errors);

			return PageGenerator.getPage("server_tpl/include/profile_panel.inc", pageVariables);
		}

		return "";
	}
}
