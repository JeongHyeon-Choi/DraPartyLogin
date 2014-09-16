package macro.method;

import javax.swing.JTextArea;

public class SetConsole {
	public JTextArea jta;
	
	public void setSyso(String str){
		jta.setText("");
		jta.setText(str);
	}
	public void setArea(JTextArea ta){
		jta = ta;
	}
}
