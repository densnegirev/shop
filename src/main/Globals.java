package main;

import dbservice.DBService;
import dbservice.DBServiceFoxPro;

public class Globals {
	public static final String SITE_TITLE = "Магазин ТВ";
	public static final String ENCODING = "UTF-8";
	public static final DBService DB_SERVICE = new DBServiceFoxPro();
}
