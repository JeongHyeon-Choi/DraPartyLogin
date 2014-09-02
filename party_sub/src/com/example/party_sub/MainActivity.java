package com.example.party_sub;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button)findViewById(R.id.add);
		intent = this.getPackageManager().getLaunchIntentForPackage("com.patigames.dragonparty");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(intent);
			}
		});
		File file = this.getFilesDir();
		Log.d("aa", file.toString() );
		Log.d("aa", Environment.getExternalStorageDirectory().toString() );
		File ff = new File(Environment.getExternalStorageDirectory()+"/Android/data/com.patigames.dragonparty/files");
		if(ff.isDirectory()){
			File[] lists = ff.listFiles();
			for (File file2 : lists) {
				if(file2.isFile()){
					Log.d("aa",file2.getName());
				}
			}
		}
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
