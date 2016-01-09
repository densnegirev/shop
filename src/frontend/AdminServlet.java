package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import main.Globals;
import main.UserProfile;
import shop.AdminPanel;
import templater.PageBuilder;

public class AdminServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserProfile up = Globals.ACCOUNT_SERVICE.getSessions(session.getId());
		PageBuilder pageBuilder = new PageBuilder(request, response);

		if (up == null || up.getGroupID() != 2) {
			response.sendRedirect("/index");

			return;
		}

		pageBuilder.setTitle(Globals.SITE_TITLE + " | Панель управления");
		pageBuilder.setContent(new AdminPanel().getContent());
		pageBuilder.buildPage();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addPar = request.getParameter("add");
		String editPar = request.getParameter("edit");
		String deletePar = request.getParameter("delete");

		String itemId = request.getParameter("itemid");
		String fabricName = request.getParameter("fabricname");
		String fabricCountry = request.getParameter("fabriccountry");
		String type = request.getParameter("type");
		String hdFormat = request.getParameter("hdformat");
		String resolution = request.getParameter("resolution");
		String model = request.getParameter("model");
		String diagonal = request.getParameter("diagonal");
		String price = request.getParameter("price");
		String count = request.getParameter("count");

		if (addPar != null) {
			Globals.DB_SERVICE.addItem(
					fabricName,
					fabricCountry,
					type,
					hdFormat,
					resolution,
					model,
					diagonal,
					price,
					count);
		} else if (editPar != null) {
			Globals.DB_SERVICE.updateItem(
					itemId,
					fabricName,
					fabricCountry,
					type,
					hdFormat,
					resolution,
					model,
					diagonal,
					price,
					count);
		} else if (deletePar != null) {
			Globals.DB_SERVICE.deleteItem(itemId);
		}

		response.sendRedirect("/admin");
	}
}
