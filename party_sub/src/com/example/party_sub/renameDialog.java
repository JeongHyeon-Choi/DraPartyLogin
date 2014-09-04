package com.example.party_sub;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class renameDialog extends Dialog{
	EditText edit;
	Button cancle;
	Button submit;
	Button delete;
	
	Context mContext;
	
	public static String EditStr = "";
	okListener ook;
	delListener ddl;

	public renameDialog(Context context , okListener ok, delListener del) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_rename);
		mContext = context;
		ook = ok;
		ddl = del;
		
		edit = (EditText)findViewById(R.id.dia_edit);
		submit = (Button)findViewById(R.id.dia_submit);
		cancle = (Button)findViewById(R.id.dia_cancle);
		delete = (Button)findViewById(R.id.dia_del);
		
		submit.setOnClickListener(new submitClick());
		cancle.setOnClickListener(new cancleClick());
		delete.setOnClickListener(new delClick());
		
//		AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
		
	}
	
	class submitClick implements android.view.View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str = edit.getText().toString();
			if(str != null && checkMkf(str)){
				EditStr = str;
				ook.click();
				dismiss();
			}
		}
		
	}
	
	class cancleClick implements android.view.View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dismiss();
		}
		
	}
	
	class delClick implements android.view.View.OnClickListener{

		@Override
		public void onClick(View v) {
			ddl.click();
			dismiss();
		}
		
	}
	
	public interface okListener{
		void click();
	}
	
	public interface delListener{
		void click();
	}

	public static String getEditStr() {
		return EditStr;
	}
	
	boolean checkMkf(String str){
		if(str.length() < 15){
			String errStr = new String("\\\"\'/:*?<>,|.");
			for (int i = 0, cnt = errStr.length(); i < cnt; i++) {
				if(str.contains(new String(errStr.charAt(i) + ""))){
					Toast.makeText(mContext, "특수문자를 쓰지 마욧!",Toast.LENGTH_SHORT).show();
					return false;
				}
			}
			 return true;
		} else {
			Toast.makeText(mContext, "15자 이내로 입력하세요.",Toast.LENGTH_SHORT).show();
		}
		return false;
		
	}
	
	
	
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		
//		AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//		LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
//		mBuilder.setView(mLayoutInflater.inflate(R.layout.dialog_rename, null));
//		mBuilder.setTitle("ReName");
//		mBuilder.setMessage("Input String");
//		return mBuilder.create();
//	}
//	@Override
//	public void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//	}

}
