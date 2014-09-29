package macro;

public class macroInfo {
	public static String URL = "http://drapoker.potluckgames.co.kr";
	public static String KURL = "http://drapoker.potluckgames.co.kr";
	public static String JURL = "http://drapoker.asobism.co.jp";
	
	public static String jsonType(String str){
		int i;
		for (i = 0; str.charAt(i) != '{' ; i++);
		  
		return str.substring(i);
	}
}
