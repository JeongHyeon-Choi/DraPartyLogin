package macro.method;

import macro.ui.main_ui;

import com.makeshop.android.manager.ObserverManager;


public class SetConsole {
	private final static int MAX  = 200;
	private static String log = "";
	
	public static void setSyso(String str){
		String tmp = (str.length() > MAX) ? str.substring(0, MAX).trim() : str;
		if(log.equals(tmp)) return;
		log = tmp;
		ObserverManager.getInstance().notifyObserver(main_ui.MACRO);
	}
	
	public static String getSyso(){
		return log;
	}
}
