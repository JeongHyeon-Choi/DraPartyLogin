package com.example.party_sub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.party_sub.renameDialog.delListener;
import com.example.party_sub.renameDialog.okListener;

public class MainActivity extends Activity {
	private Intent intent;
	public Button add_btn, del_btn;
	public ListView lv;
	public listAdapter listadp;
	public File dra_root;
	public ArrayList<String> arylist = new ArrayList<String>();
	String rename = new String();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		add_btn = (Button) findViewById(R.id.add);
		del_btn = (Button) findViewById(R.id.del);
		lv = (ListView) findViewById(R.id.list);

		intent = this.getPackageManager().getLaunchIntentForPackage("com.patigames.dragonparty");
		
		File file = this.getFilesDir();
		Log.d("aa", file.toString());
		// File dra_file = new
		// File(file.getPath().replaceFirst("com.example.party_sub",
		// "com.patigames.dragonparty"));
		Log.d("dir", Environment.getExternalStorageDirectory().toString());
		dra_root = new File(Environment.getExternalStorageDirectory()+ "/Android/data/com.patigames.dragonparty/files");
		// File ff = new File(Environment.getExternalStorageDirectory()+"");
//		arylist = get_id();
		get_id();
		listadp = new listAdapter(this, R.layout.list_item, arylist);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setAdapter(listadp);
		add_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dra_root.isDirectory() && dra_root.canRead()) {
					File[] lists = dra_root.listFiles();
					for (File file2 : lists) {
						if (file2.isFile()) {
							Log.d("aa", file2.getName());
							if (file2.getName().equals("accountLog.dat")) {
								try {
									String file_name = check_id() + ".dat";
									FileInputStream fis = new FileInputStream(
											file2);
									while(new File(Environment.getExternalStorageDirectory()
													+ "/Android/data/com.patigames.dragonparty/"
													+ file_name).exists()){
										file_name = "new_" + file_name;
									}
									
									FileOutputStream newfos = new FileOutputStream(
											Environment
													.getExternalStorageDirectory()
													+ "/Android/data/com.patigames.dragonparty/"
													+ file_name);
									int readcount = 0;
									byte[] buffer = new byte[1024];
									while ((readcount = fis.read(buffer, 0,
											1024)) != -1) {
										newfos.write(buffer, 0, readcount);
									}
									listadp.setArray(file_name);
//									arylist.add(file_name);
									listadp.notifyDataSetChanged();
									newfos.close();
									fis.close();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		});
		
		add_btn.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
//				listadp.add("aa");
//				listadp.notifyDataSetChanged();
				ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
				am.restartPackage("com.patigames.dragonparty");
				startActivity(intent);
				return true;
			}
		});
		
		del_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				del_login();
			}
		});


	}

	String check_id() {
		File f_id = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/com.patigames.dragonparty");
		if (f_id.isDirectory() && f_id.canRead() && f_id.canWrite()) {
			File[] id_list = f_id.listFiles();
			return Integer.toString(id_list.length);
		}
		return null;
	}

//	ArrayList<String> get_id() {
//		File f_id = new File(Environment.getExternalStorageDirectory()
//				+ "/Android/data/com.patigames.dragonparty");
//		if (f_id.isDirectory() && f_id.canRead() && f_id.canWrite()) {
//			File[] id_list = f_id.listFiles();
//			int i = id_list.length;
//			ArrayList<String> list = new ArrayList<String>();
//			for (int j = 0; j < i; j++) {
//				list.add(id_list[j].getName());
//			}
//			return list;
//		}
//		return null;
//	}
	void get_id() {
		File f_id = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/com.patigames.dragonparty");
		if (f_id.isDirectory() && f_id.canRead() && f_id.canWrite()) {
			File[] id_list = f_id.listFiles();
			int i = id_list.length;
			arylist.clear();
			for (int j = 1; j < i; j++) {
				arylist.add(id_list[j].getName());
			}
		}
	}
	
	void change_login(String str) {
		del_login();
		File login_file = new File(Environment.getExternalStorageDirectory()+ "/Android/data/com.patigames.dragonparty/"+str);
		if(login_file != null && login_file.isFile() && login_file.canWrite()){
			try {
				FileInputStream fis = new FileInputStream(
						login_file);
				FileOutputStream newfos = new FileOutputStream(
						Environment.getExternalStorageDirectory()
								+ "/Android/data/com.patigames.dragonparty/files/accountLog.dat");
				int readcount = 0;
				byte[] buffer = new byte[1024];
				while ((readcount = fis.read(buffer, 0,
						1024)) != -1) {
					newfos.write(buffer, 0, readcount);
				}
				newfos.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	void del_login(){
		File del_file = new File(Environment.getExternalStorageDirectory()+ "/Android/data/com.patigames.dragonparty/files/accountLog.dat");
		if(del_file != null && del_file.isFile() && del_file.canWrite()){
			del_file.delete();
		}
	}
	void del_login(String arg1,int index){
		File del_file = new File(Environment.getExternalStorageDirectory()+ "/Android/data/com.patigames.dragonparty/"+arg1);
		if(del_file != null && del_file.isFile() && del_file.canWrite()){
			del_file.delete();
//			ArrayList<String> tempArrayList = new ArrayList<String>();
			arylist.remove(index);
			listadp.notifyDataSetChanged();
//			for (String string : arylist) {
//				tempArrayList.add(string);
//			}
//			arylist.clear();
//			arylist = tempArrayList;
			
		}
	}
	
	void rename_login(String arg1, String arg2, int index){
		File target_file = new File(Environment.getExternalStorageDirectory()+ "/Android/data/com.patigames.dragonparty/"+arg1);
		File rename_file = new File(Environment.getExternalStorageDirectory()+ "/Android/data/com.patigames.dragonparty/"+arg2+".dat");
		
		target_file.renameTo(rename_file);
		
		arylist.set(index, arg2+".dat");
	}
	
	public class listAdapter extends ArrayAdapter<String> {

		LayoutInflater inflater;
		ArrayList<String> mcategory;
		Context mcontext;
		int mListLayout;

		public listAdapter(Context context, int listLayout, ArrayList<String> objects) {
			super(context, listLayout, objects);
			mcontext = context;
			mListLayout = listLayout;
			mcategory = objects;
			inflater = (LayoutInflater) mcontext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mcategory.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return mcategory.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView =  LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
//				convertView = inflater.inflate(mListLayout, parent);
			}
			final int positionInt = position;
//			if(positionInt == 0){
//				convertView.setVisibility(View.GONE);
//			}
			Button btn = (Button) convertView.findViewById(R.id.start_btn);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					if (positionInt != 0) {
						Log.d("item", mcategory.get(positionInt));
						change_login(mcategory.get(positionInt));
						ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
						am.restartPackage("com.patigames.dragonparty");
						startActivity(intent);
						finish();
//					}
				}
			});
			TextView tv = (TextView) convertView.findViewById(R.id.item);
			tv.setText(mcategory.get(positionInt));
			convertView.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {

					final renameDialog rd = new renameDialog(getContext(), new okListener() {
						
						@Override
						public void click() {
							rename = renameDialog.getEditStr();
							rename_login(mcategory.get(positionInt), rename, positionInt);
						}
					},
					new delListener() {
						
						@Override
						public void click() {
							del_login(mcategory.get(positionInt), positionInt);					
						}
					});
					rd.show();
//					if (positionInt != 0) {
//						Log.d("item", mcategory.get(positionInt));
//						change_login(mcategory.get(positionInt));
//						startActivity(intent);
//					}
					return false;
				}
			});
			return convertView;
		}
		
		void setArray(String str){
			mcategory.add(str);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		get_id();
		listadp.notifyDataSetChanged();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
