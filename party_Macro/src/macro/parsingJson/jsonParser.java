package macro.parsingJson;
import java.util.ArrayList;

import macro.method.method;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class jsonParser {

	private String ParseString = new String();
	JSONArray jar = new JSONArray();
	ArrayList<String> TarCardId = new ArrayList<>();

	public jsonParser(String str) {
		System.out.println("=======decode=======");

		Object obj = JSONValue.parse(str);
		JSONObject oo = (JSONObject) obj;
		jar = (JSONArray) ((JSONObject) oo.get("result")).get("tra_card");
	}

	public String getSalesJson() {
		for (int i = 0; i < jar.size(); i++) {
			if ((((JSONObject) jar.get(i)).get("rare").equals("1")
			|| ((JSONObject) jar.get(i)).get("rare").equals("3"))
			&& ((JSONObject) jar.get(i)).get("level").equals("1")
			&& !isRevolcard((String) ((JSONObject) jar.get(i)).get("cardID"))) {
				
				TarCardId.add((String) ((JSONObject) jar.get(i))
						.get("traCardID"));
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
	
	public String[] getEvolJson(method evolmethod){
		String TargetEvol = "ERR";
		String BaseId = "";
		for (int i = 0; i < jar.size(); i++) {
			if( isMP((String) ((JSONObject) jar.get(i)).get("cardID"))) {
				BaseId = (String) ((JSONObject) jar.get(i)).get("traCardID");
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
		System.out.println("=======EVOL=======");

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
