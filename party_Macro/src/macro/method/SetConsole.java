package macro.method;

import macro.ui.main_ui;

import com.makeshop.android.manager.ObserverManager;


public class SetConsole {
	private static String log = "";
	
	public static void setSyso(String str){
		String tmp = (str.length() > 100) ? str.substring(0, 100).trim() : str;
		if(log.equals(tmp)) return;
		log = tmp;
		ObserverManager.getInstance().notifyObserver(main_ui.MACRO);
	}
	
	public static String getSyso(){
		return log;
	}
}
