package macro.parsingJson;
import java.util.ArrayList;

import macro.macroInfo;
import macro.method.SetConsole;
import macro.method.method;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.makeshop.android.manager.JsonSelector;
import com.sun.xml.internal.fastinfoset.util.StringArray;

public class jsonParser {

	private String ParseString = new String();
	ArrayList<String> TarCardId = new ArrayList<String>();
	protected JsonSelector mSelector;
	protected JsonSelector mArrySelector;
	
	public jsonParser() {}
	
	public void makeDeckArray(String str){
		mSelector = new JsonSelector(str.trim());
		try {
			mArrySelector = mSelector.query("result>tra_card");
		} catch (Exception e) {
			return ;
		}
	}
	
	public void makeCardArray(String str){
		mSelector = new JsonSelector(str.trim());
		try {
			mArrySelector = str.contains("tra_card_deck") ? mSelector.query("result>tra_card_deck>eqID"): mSelector.query("result>cardDeckList");
		} catch (Exception e) {
			return ;
		}
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
	
	public String getBCardListJson() {
		SetConsole.setSyso("===CardList===");
		StringArray colList = new StringArray() ;
		for (int i = 0; i < 13 ; i++) {
			String tmp = mArrySelector.getString( (i+1) + "" );
			for (int j = 0; mSelector.query("result>tra_card>[" + j + "]").hasResult(); j++) {
				if(tmp.equals(mSelector.query("result>tra_card>[" + j + "]").getString("traCardID"))){
					colList.add(mSelector.query("result>tra_card>[" + j + "]").getString("cardName")); 
					break;
				}
			}
			ParseString += colList.get(i) + 
					":" + tmp + "\n";
		}
		return ParseString;
	}
	
	public String getCCardListJson() {
		SetConsole.setSyso("===CardList===");
		String[] colList = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"} ;
		for (int i = 0; mArrySelector.query("[" + i + "]").hasResult() && i < 13 ; i++) {
			ParseString += colList[i] + mArrySelector.query("[" + i + "]").getString("cardName") + 
					":" + mArrySelector.query("[" + i + "]").getString("traCardID") + "\n";
		}
		return ParseString;
	}
	
	public String getSalesJson() {
		SetConsole.setSyso("===Sales===");
		for (int i = 0; mArrySelector.query("[" + i + "]").hasResult() ; i++) {
			if ( isRarecard(mArrySelector.query("[" + i + "]").getString("rare"), "1", "3")
			&& mArrySelector.query("[" + i + "]").getString("level").equals("1")
			&& !isRevolcard(mArrySelector.query("[" + i + "]").getString("cardID"))) {
				
				TarCardId.add(mArrySelector.query("[" + i + "]").getString("traCardID"));
				if (TarCardId.size() == 10) {
					break;
				}
			}
		}
		ParseString = "";
		for (int i = 0; i < TarCardId.size(); i++) {
			ParseString += TarCardId.get(i);
			ParseString +=( i == TarCardId.size()-1 ) ? "":"." ; 
		}
		return ParseString;
	}
	
	public String getEnchanceJson() {
		SetConsole.setSyso("===Enchance===");
		for (int i = 0; mArrySelector.query("[" + i + "]").hasResult() ; i++) {
			if ( isRarecard(mArrySelector.query("[" + i + "]").getString("rare"), "1", "3")
			&& mArrySelector.query("[" + i + "]").getString("level").equals("1")
			&& !isRevolcard(mArrySelector.query("[" + i + "]").getString("cardID"))) {
				
				return mArrySelector.query("[" + i + "]").getString("traCardID");
			}
		}
		return ParseString;
	}
	
	public String[] getEvolJson(method evolmethod){
		SetConsole.setSyso("===Evol===");
		String TargetEvol = "ERR";
		String BaseId = "";
		for (int i = 0; mArrySelector.query("[" + i + "]").hasResult() ; i++) {
			if( isMP(mArrySelector.query("[" + i + "]").getString("cardID"))) {
				BaseId = mArrySelector.query("[" + i + "]").getString("traCardID");
				 ParseString = evolmethod.evolcheck(BaseId);
				 TargetEvol = EvoljsonParser(macroInfo.jsonType(ParseString));
				 if(TargetEvol.equals("ERR")){
					 continue;
				 } else {
					 break;
				 }
			}
		}
		
		return new String[] {BaseId, TargetEvol};
	}
	
	boolean isRarecard(Object rare, String... gradeList){
		for (String grade : gradeList) {
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
