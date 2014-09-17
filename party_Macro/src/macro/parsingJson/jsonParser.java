package macro.parsingJson;
import java.util.ArrayList;

import macro.method.method;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.makeshop.android.manager.JsonSelector;

public class jsonParser {

	private String ParseString = new String();
	ArrayList<String> TarCardId = new ArrayList<String>();
	protected JsonSelector mSelector;
	protected JsonSelector mArrySelector;
	
	public jsonParser() {}
	
	public void makeArray(String str){
		mSelector = new JsonSelector(str.trim());
		try {
			mArrySelector = mSelector.query("result>tra_card");
		} catch (Exception e) {
			return ;
		}
	}

	public String getSalesJson() {
		for (int i = 0; mArrySelector.query("[" + i + "]").hasResult() ; i++) {
			if ((mArrySelector.query("[" + i + "]").getString("rare").equals("1")
			|| mArrySelector.query("[" + i + "]").getString("rare").equals("3"))
			&& mArrySelector.query("[" + i + "]").getString("level").equals("1")
			&& !isRevolcard(mArrySelector.query("[" + i + "]").getString("cardID"))) {
				
				TarCardId.add(mArrySelector.query("[" + i + "]").getString("traCardID"));
				if (TarCardId.size() == 10) {
					break;
					
				}
			}
		}
		
//		ParseString = "saleID=";
		ParseString = "";
		for (int i = 0; i < TarCardId.size(); i++) {
			ParseString += TarCardId.get(i);
			ParseString +=( i == TarCardId.size()-1 ) ? "":"." ; 
		}
		return ParseString;
	}
	
	public String getEnchanceJson() {
		for (int i = 0; mArrySelector.query("[" + i + "]").hasResult() ; i++) {
			if ((mArrySelector.query("[" + i + "]").getString("rare").equals("1")
			|| mArrySelector.query("[" + i + "]").getString("rare").equals("3"))
			&& mArrySelector.query("[" + i + "]").getString("level").equals("1")
			&& !isRevolcard(mArrySelector.query("[" + i + "]").getString("cardID"))) {
				
				return mArrySelector.query("[" + i + "]").getString("traCardID");
			}
		}
		return ParseString;
	}
	
	public String[] getEvolJson(method evolmethod){
		String TargetEvol = "ERR";
		String BaseId = "";
		for (int i = 0; mArrySelector.query("[" + i + "]").hasResult() ; i++) {
			if( isMP(mArrySelector.query("[" + i + "]").getString("cardID"))) {
				BaseId = mArrySelector.query("[" + i + "]").getString("traCardID");
				 ParseString = evolmethod.evolcheck(BaseId);
				 TargetEvol = EvoljsonParser(ParseString.substring(1));
				 if(TargetEvol.equals("ERR")){
					 continue;
				 } else {
					 break;
				 }
			}
		}
		
		return new String[] {BaseId, TargetEvol};
	}
	
	public String EvoljsonParser(String str) {

		Object obj = JSONValue.parse(str);
		JSONObject oo = (JSONObject) obj;
		JSONObject E_jobj =  (JSONObject)((JSONArray) ((JSONObject) oo.get("result")).get("evolList")).get(0);
		if(E_jobj.get("isEnable").toString().equals("1")){ 
			return (String) ((JSONObject) E_jobj.get("afterCardContents")).get("cardID");
		}
		return "ERR";
	}
	
	boolean isRarecard(Object rare, String... str){
		for (String grade : str) {
			if(rare.equals(grade)){
				return true;
			}
		}
		return false;
	}
	
	boolean isRevolcard(String str){
		if(str.equals("304301") || str.equals("204301") ||str.equals("104301")){ //ring
			return true;
		} else if(str.equals("304201") || str.equals("204201") ||str.equals("104201")){ // maron s:105601 ss:106401
			return true;
		} else if(str.equals("304401") || str.equals("204401") ||str.equals("104401")){ // pariy s:107101 ss:107201
			return true;
		} else if(str.equals("310701") || str.equals("210701") ||str.equals("110701")){ //star  event
			return  true;
		}
		return false;
	}
	
	boolean isMP(String str){
		if(str.equals("304201") || str.equals("204201") ||str.equals("104201")){ // maron s:105601 ss:106401
			return true;
		} else if(str.equals("305601") || str.equals("205601") ||str.equals("105601")){ // maron s:105601 ss:106401
			return true;
		} else if(str.equals("304401") || str.equals("204401") ||str.equals("104401")){ // pariy s:107101 ss:107201
			return true;
		} else if(str.equals("307101") || str.equals("207101") ||str.equals("107101")){ // pariy s:107101 ss:107201
			return true;
		} 
		return false;
	}
}
