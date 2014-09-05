package macro.method;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class test {
	
	public static String[] a = { "uid", "platform", "store", "appVer", "astVer",
			"deviceID", "ymca", "korVer", "User-Agent", "Host",
			"Content-Length", "Proxy-Connection" };
	public static String[] b = {
			"1019975",
			"android",
			"googleplay",
			"v2.0.8",
			"179",
			"000000000000000",
			"1019975",
			"v1.0.10",
			"Dalvik/1.6.0 (Linux; U; Android 4.3; Samsung Galaxy Note 3 - 4.3 - API 18 - 1080x1920 Build/JLS36G) Paros/3.2.13",
			"drapoker.potluckgames.co.kr", "7", "Keep-Alive" };
	
	public final static String NOMAL = "0";
	public final static String ENHANCE = "19";
	
	public static void main(String[] args) {
		
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

		Runnable evolgacha=  new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getGacha(ENHANCE);
					
					jsonParser jp = new jsonParser(getDeckList().substring(1));
					evol(jp.getEvolJson()); 
					
					jp = new jsonParser(getDeckList().substring(1));
					evol(jp.getEvolJson()); 
					
					jp = new jsonParser(getDeckList().substring(1));
					evol(jp.getEvolJson()); 
					
					jp = new jsonParser(getDeckList().substring(1));
					evol(jp.getEvolJson()); 
					
				}
			}
		};
		
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
					getPresentAll();
					
					jsonParser jp = new jsonParser(getDeckList().substring(1));
					evol(jp.getEvolJson()); 
					
					jp = new jsonParser(getDeckList().substring(1));
					evol(jp.getEvolJson()); 
					
					jp = new jsonParser(getDeckList().substring(1));
					evol(jp.getEvolJson()); 
					
					jp = new jsonParser(getDeckList().substring(1));
					evol(jp.getEvolJson()); 
					
				}
			}
		};
		
		
		
		if (args.length > 0){
			for (int i = 0; i < args.length; i++) {
				if(args[i].equals("-a")){
					b[5] = "352316056218161";
					continue;
				} 
				if(args[i].equals("-b")){
					b[0] = "1276399";
					continue;
				} 
			}
			
		}
		
		if (args.length > 0){
			for (int i = 0; i < args.length; i++) {
				if(args[i].equals("-n")){
					new Thread(nomalgacha).start();
					continue;
				}
				if(args[i].equals("-c")){
					new Thread(charge).start();
					continue;
				} 
				if(args[i].equals("-e")){
					new Thread(evolgacha).start();
					continue;
				}
				if(args[i].equals("-p")){
					new Thread(present).start();
					continue;
				}
			}
			
		}
	}
	
	public static void getMissionResult(){
		try {
			// Construct data
//			String data = URLEncoder.encode("key1", "UTF-8") + "="
//					+ URLEncoder.encode("value1", "UTF-8");
//			data += "&" + URLEncoder.encode("key2", "UTF-8") + "="
//					+ URLEncoder.encode("ȫ�浿", "UTF-8");

			// Send data
			URL url = new URL("http://drapoker.potluckgames.co.kr/net/getMissionResult.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);

			
			for (int i = 0, size = a.length; i < size; i++) {
				conn.addRequestProperty(a[i], b[i]);
			}

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
//			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
	
	public static void getPresentAll(){
		try {
			// Construct data
//			String data = URLEncoder.encode("key1", "UTF-8") + "="
//					+ URLEncoder.encode("value1", "UTF-8");
//			data += "&" + URLEncoder.encode("key2", "UTF-8") + "="
//					+ URLEncoder.encode("ȫ�浿", "UTF-8");

			// Send data
			URL url = new URL("http://drapoker.potluckgames.co.kr/net/presentAll.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);

			
			for (int i = 0, size = a.length; i < size; i++) {
				conn.addRequestProperty(a[i], b[i]);
			}

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
//			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
	
	public static void getGacha(String type){
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

			
			for (int i = 0, size = a.length; i < size; i++) {
				conn.addRequestProperty(a[i], b[i]);
			}

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line; 
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
	
	public static String getDeckList(){
		try {
			// Construct data
//			String data = URLEncoder.encode("type", "UTF-8") + "="
//					+ URLEncoder.encode("19", "UTF-8");
//			data += "&" + URLEncoder.encode("count", "UTF-8") + "="
//					+ URLEncoder.encode("10", "UTF-8");

			// Send data
			URL url = new URL("http://drapoker.potluckgames.co.kr/net/getDeckList.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);

			
			for (int i = 0, size = a.length; i < size; i++) {
				conn.addRequestProperty(a[i], b[i]);
			}

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
//			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			String DeckListJson = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				DeckListJson += line;
			}
			wr.close();
			rd.close();
			return DeckListJson;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static void sales(String DeckListJson){
		jsonParser jp = new jsonParser(DeckListJson.substring(1));
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

			
			for (int i = 0, size = a.length; i < size; i++) {
				conn.addRequestProperty(a[i], b[i]);
			}

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
	
	public static String evolcheck(String baseID){
	
		try {
			
			
			// Construct data
			String data = URLEncoder.encode("baseID", "UTF-8") + "="
					+ URLEncoder.encode(baseID, "UTF-8");
			data += "&" + URLEncoder.encode("exec", "UTF-8") + "="
					+ URLEncoder.encode("0", "UTF-8");

			// Send data
			URL url = new URL("http://drapoker.potluckgames.co.kr/net/cardEvol.php");
			URLConnection conn = url.openConnection();
			// If you invoke the method setDoOutput(true) on the URLConnection,
			// it will always use the POST method.
			conn.setDoOutput(true);

			
			for (int i = 0, size = a.length; i < size; i++) {
				conn.addRequestProperty(a[i], b[i]);
			}

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
				System.out.println(line);
				EvolJson += line;
			}
			wr.close();
			rd.close();
			return EvolJson;
		} catch (Exception e) {
		}
		return null;
	}

	public static void evol(String... str){
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

			
			for (int i = 0, size = a.length; i < size; i++) {
				conn.addRequestProperty(a[i], b[i]);
			}

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
		}
	}
//	jsonParser jp = new jsonParser(ta.getText().substring(2));
//	String parsingStr = jp.getSalesJson(); 
//	
//	type=19&count=10
//			
//	saleID=18578025.18578026.18578027.18578028.18578159.18578160.18578161.18578162.18578163.18578164&exec=1
}