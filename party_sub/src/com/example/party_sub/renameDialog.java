package com.example.party_sub;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class renameDialog extends Dialog{
	EditText edit;
	Button	cancle;
	Button	submit;
	public static String EditStr= "";
	okListener ook;
	
	public renameDialog(Context context , okListener ok) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_rename);
		
		ook = ok;
		
		edit = (EditText)findViewById(R.id.edit);
		submit = (Button)findViewById(R.id.submit);
		cancle = (Button)findViewById(R.id.cancle);
		
		submit.setOnClickListener(new submitClick());
		cancle.setOnClickListener(new cancleClick());
		
//		AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
		
	}
	
	class submitClick implements android.view.View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str = edit.getText().toString();
			if(str != null){
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
	
	public interface okListener{
		void click();
	}

	public static String getEditStr() {
		return EditStr;
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
