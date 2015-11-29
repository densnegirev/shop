package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import main.Globals;
import main.UserProfile;
import templater.PageBuilder;

public class TrashServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserProfile up = Globals.ACCOUNT_SERVICE.getSessions(session.getId());
		PageBuilder pageBuilder = new PageBuilder(request, response);

		if (up == null) {
			response.sendRedirect("/index");

			return;
		}

		pageBuilder.setTitle(Globals.SITE_TITLE + " | Корзина");
		pageBuilder.setContent(Globals.TRASH.getContent(up.getId()));
		pageBuilder.buildPage();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserProfile up = Globals.ACCOUNT_SERVICE.getSessions(session.getId());
		String action = request.getParameter("action");
		String itemId = request.getParameter("itemid");
		String amount = request.getParameter("amount");

		if (up != null && action != null && itemId != null && amount != null) {
			int itemIdNum = Integer.parseInt(itemId);
			int amountNum = Integer.parseInt(amount);

			if (action.equals("add")) {
				Globals.TRASH.addItem(up.getId(), itemIdNum, amountNum);
			} else if (action.equals("delete")) {
				Globals.TRASH.deleteItem(up.getId(), itemIdNum);
			}
		}

		response.sendRedirect("/index");
	}
}
