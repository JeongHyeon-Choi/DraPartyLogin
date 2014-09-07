package macro.method;

import javax.swing.JTextArea;

public class SetConsole {
	public static JTextArea jta;
	
	public static void setSyso(String str){
		jta.setText("");
		jta.setText(str);
	}
	public static void setArea(JTextArea ta){
		jta = ta;
	}
}
