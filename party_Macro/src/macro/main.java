package macro;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import macro.ui.main_ui;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new main_ui();
//		System.out.println(
//				decodeUrlDecodeAndBase64("pjT3wfQ4LM+uAVBjSUJ7q230JDRvqcea0kIRnQq0gQlispOOVK2FSFXc/CD2hjlc")
//				);
	
	}
	
	 public static String encodeUrlEncodeAndBase64(String str) {
	        if (str == null || str.equals("")) {
	            return "";
	        } else {
	            try {
	                BASE64Encoder en = new BASE64Encoder();
	                str = URLEncoder.encode(str, "UTF-8");
	                str = en.encode(str.getBytes());
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return str;
	    }

	    /*
	     * Base 64 한글 Decoding
	     */
	    public static String decodeUrlDecodeAndBase64(String str) {
	        if (str == null || str.equals("")) {
	            return "";
	        } else {
	            try {
	                BASE64Decoder de = new BASE64Decoder();
	                byte[] deStr = de.decodeBuffer(str);
//	                str = URLDecoder.decode(deStr.toString(), "UTF-8");
	                str = URLDecoder.decode(new String(deStr), "UTF-8");
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return str;
	    }
}
