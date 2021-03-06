package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import main.Globals;
import main.UserProfile;
import shop.PurchaseHistory;
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

		String content1 = Globals.TRASH.getContent(up.getId());
		String content2 = new PurchaseHistory().getContent(up.getId());

		pageBuilder.setTitle(Globals.SITE_TITLE + " | Корзина");
		pageBuilder.setContent(content1 + "<br /><br />История заказов<br />" + content2);
		pageBuilder.buildPage();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserProfile up = Globals.ACCOUNT_SERVICE.getSessions(session.getId());
		String action = request.getParameter("action");
		String itemId = request.getParameter("itemid");
		String amount = request.getParameter("amount");

		if (action != null) {
			int userId = up != null ? up.getId() : -1;
			int itemIdNum = itemId != null ? Integer.parseInt(itemId) : -1;

			if (action.equals("add") && amount != null) {
				int amountNum = Integer.parseInt(amount);
				int itemCount = Globals.DB_SERVICE.getItem(itemIdNum).getCount();
				int rem = itemCount - Globals.TRASH.getItemAmount(userId, itemIdNum) - amountNum;

				if (rem >= 0 && rem < itemCount) {
					Globals.TRASH.addItem(userId, itemIdNum, amountNum);

					System.out.println("Added item to trash: " + itemIdNum + " (amount: " + amountNum + ")");
				} else {
					System.out.println("ERROR: Out of stock: " + itemIdNum);
				}
			} else if (action.equals("delete")) {
				Globals.TRASH.deleteItem(userId, itemIdNum);

				response.sendRedirect("/trash");

				return;
			} else if (action.equals("confirm")) {
				Globals.DB_SERVICE.moveItemsFromTrash(userId);
			}
		}

		response.sendRedirect("/index");
	}
}
