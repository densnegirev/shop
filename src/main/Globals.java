package main;

import dbservice.DBService;
import dbservice.DBServiceFoxPro;
import shop.Trash;

public class Globals {
	public static final String SITE_TITLE = "Магазин ТВ";
	public static final String ENCODING = "UTF-8";
	public static final DBService DB_SERVICE = new DBServiceFoxPro();
	public static final AccountService ACCOUNT_SERVICE = new AccountService(DB_SERVICE);
	public static final Trash TRASH = new Trash();
}
