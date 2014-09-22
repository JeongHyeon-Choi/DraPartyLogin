package macro.method;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import macro.parsingJson.jsonParser;

public class method {
	public final static String NOMAL = "0";
	public final static String ENHANCE = "19";
	protected method singleMehod;
	
	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> content = new ArrayList<String>();
	
	private method(){
	}
	
	public method(String str){
		this.singleMehod = this;
		classiy(str);
	}
	
	void classiy(String str){
		String tmp[] = str.split("\n");
		for (String string : tmp) {
			title.add(string.split(":")[0].trim());
			content.add(string.split(":")[1].trim());
		}
	}
	
	void setHeader(URLConnection conn){
		for (int i = 0, cnt = title.size(); i < cnt; i++) {
			conn.addRequestProperty(title.get(i), content.get(i));
		}
	}
	
	public void getMissionResult(){
		try {

			URL url = new URL("http://drapoker.potluckgames.co.kr/net/getMissionResult.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);
			
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				SetConsole.setSyso(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
	
	public void getPresentAll(){
		try {

			URL url = new URL("http://drapoker.potluckgames.co.kr/net/presentAll.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				SetConsole.setSyso(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
	
	public void getGacha(String type){
		try {
			// Construct data
			String data = URLEncoder.encode("type", "UTF-8") + "="
					+ URLEncoder.encode(type, "UTF-8");				//��í ����
			data += "&" + URLEncoder.encode("count", "UTF-8") + "="
					+ URLEncoder.encode("10", "UTF-8");

			// Send data
			URL url = new URL("http://drapoker.potluckgames.co.kr/net/getGacha.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line; 
			while ((line = rd.readLine()) != null) {
				SetConsole.setSyso(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}

	public void sendCard(String strRoomID, String strCardID, String strSkillID, String strURL){
		if(strRoomID.equals("") || strCardID.equals("")) return;
		try {
			//roomID=1758590&traCardID=19748435&turnCount=7&useCardGauge=210001&targetID=1303571
			String data = URLEncoder.encode("roomID", "UTF-8") + "="
					+ URLEncoder.encode(strRoomID, "UTF-8");				
			data += "&" + URLEncoder.encode("traCardID", "UTF-8") + "="
					+ URLEncoder.encode(strCardID, "UTF-8") + "&turnCount=";
			data += strSkillID.equals("") ? "" : "&" + URLEncoder.encode("useCardGauge", "UTF-8") + "="
					+ URLEncoder.encode(strSkillID, "UTF-8");

			// Send data
			URL	url = new URL(strURL);
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line; 
			while ((line = rd.readLine()) != null) {
				SetConsole.setSyso(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}

	public String getCardList(String strParams, String strURL){
		if(strParams.equals("")) return null;
		try {
			String data = strParams;	

			// Send data
			URL	url = new URL(strURL);
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line; 
			String CardListJson = "";
			while ((line = rd.readLine()) != null) {
				CardListJson += line;
			}
			wr.close();
			rd.close();
			SetConsole.setSyso(CardListJson);
			
			jsonParser jp = new jsonParser(); 
			jp.makeCardArray(CardListJson.substring(1));
			String parsingStr = strParams.equals("nop=nop") ? jp.getBCardListJson(): jp.getCCardListJson();
			return parsingStr;
		} catch (Exception e) {
		}
		return null;
	}
	
	public String getDeckList(){
		try {
			URL url = new URL("http://drapoker.potluckgames.co.kr/net/getDeckList.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			String DeckListJson = "";
			while ((line = rd.readLine()) != null) {
				DeckListJson += line;
			}
			wr.close();
			rd.close();
			SetConsole.setSyso(DeckListJson);
			return DeckListJson;
		} catch (Exception e) {
		}
		return null;
	}
	
	public void sales(String DeckListJson){
		jsonParser jp = new jsonParser(); jp.makeDeckArray(DeckListJson.substring(1));
		String parsingStr = jp.getSalesJson(); 
		try {
			// Construct data
			String data = URLEncoder.encode("saleID", "UTF-8") + "="
					+ URLEncoder.encode(parsingStr, "UTF-8");
			data += "&" + URLEncoder.encode("exec", "UTF-8") + "="
					+ URLEncoder.encode("1", "UTF-8");

			// Send data
			URL url = new URL("http://drapoker.potluckgames.co.kr/net/cardSale.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				SetConsole.setSyso(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
	
	public void enchance(String DeckListJson, String baseID){
		jsonParser jp = new jsonParser(); jp.makeDeckArray(DeckListJson.substring(1));
		String parsingStr = jp.getEnchanceJson(); 
		try {
			// Construct data
			String data = URLEncoder.encode("baseID", "UTF-8") + "="
					+ URLEncoder.encode(baseID, "UTF-8");
			data += "&" + URLEncoder.encode("addID", "UTF-8") + "="
					+ URLEncoder.encode(parsingStr, "UTF-8");
			data += "&" + URLEncoder.encode("exec", "UTF-8") + "="
					+ URLEncoder.encode("1", "UTF-8");

			// Send data
			URL url = new URL("http://drapoker.potluckgames.co.kr/net/cardRnfc.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				SetConsole.setSyso(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
	
	public String evolcheck(String baseID){
	
		try {
			String data = URLEncoder.encode("baseID", "UTF-8") + "="
					+ URLEncoder.encode(baseID, "UTF-8");
			data += "&" + URLEncoder.encode("exec", "UTF-8") + "="
					+ URLEncoder.encode("0", "UTF-8");

			URL url = new URL("http://drapoker.potluckgames.co.kr/net/cardEvol.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			String EvolJson = "";
			while ((line = rd.readLine()) != null) {
				EvolJson += line;
			}
			wr.close();
			rd.close();
			SetConsole.setSyso(EvolJson);
			return EvolJson;
		} catch (Exception e) {
		}
		return null;
	}

	public void evol(String... str){
		if(str[1].equals("ERR")) return;
		
		try {
			
			
			// Construct data
			String data = URLEncoder.encode("baseID", "UTF-8") + "="
					+ URLEncoder.encode(str[0], "UTF-8");
			data += "&" + URLEncoder.encode("exec", "UTF-8") + "="
					+ URLEncoder.encode("1", "UTF-8");
			data += "&" + URLEncoder.encode("evolID", "UTF-8") + "="
					+ URLEncoder.encode(str[1], "UTF-8");
			data += "&" + URLEncoder.encode("pushButtonID", "UTF-8") + "="
					+ URLEncoder.encode("1", "UTF-8");
			// Send data
			URL url = new URL("http://drapoker.potluckgames.co.kr/net/cardEvol.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);
			setHeader(conn);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				SetConsole.setSyso(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
	
	public Runnable getNomalgacha() {
		Runnable nomalgacha = new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getGacha(NOMAL);

					sales(getDeckList());
				}
			}
		};
		return nomalgacha;
	}
	
	public Runnable getCharggacha() {
		Runnable charge = new Runnable() {
			public void run() {
				int cnt = 0;
				while (true) {
					if ((cnt++) == 30) {
						try {
							cnt = 0;
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					getMissionResult();

				}

			}
		};
		return charge;
	}

	public Runnable getEvolgacha() {
		Runnable evolgacha = new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getGacha(ENHANCE);
					
					for (int i = 0; i < 4; i++) {
						jsonParser jp = new jsonParser(); jp.makeDeckArray(getDeckList().substring(1));
						evol(jp.getEvolJson(singleMehod)); 
					}
				}
			}
		};
		return evolgacha;
	}
	
	public Runnable delMoney(final String BaseID) {
		Runnable present = new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getGacha(NOMAL);
					
					for (int i = 0; i < 10; i++) {
						enchance(getDeckList(), BaseID);
					}
					
				}
			}
		};
		return present;
	}
	
	public Runnable getPresnet() {
		Runnable present=  new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getPresentAll();
					
					for (int i = 0; i < 4; i++) {
						jsonParser jp = new jsonParser(); jp.makeDeckArray(getDeckList().substring(1));
						evol(jp.getEvolJson(singleMehod)); 
					}
				}
			}
		};
		return present;
	}

	public ArrayList<String> getTitle() {
		return title;
	}

	public ArrayList<String> getContent() {
		return content;
	}
	
}
