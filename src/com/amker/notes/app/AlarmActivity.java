package com.amker.notes.app;

import com.amaker.ch17.app.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class AlarmActivity extends Activity {
	public static final int ID =1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		//获得Button、textView实例
		Button btn = (Button) findViewById(R.id.cancelButton01);
		TextView tv = (TextView) findViewById(R.id.msgTextView01);
		
		//获得NotificationManager
		String service = Context.NOTIFICATION_SERVICE;
		final NotificationManager nm = (NotificationManager)getSystemService(service);
		//实例化NOtification
		Notification n = new Notification();
		//设置显示提示信息，该信息也会在状态栏显示
		String msg = getIntent().getStringExtra("msg");
		//显示时间
		n.tickerText = msg;
		tv.setText(msg);

		//设置声音提示
		 n.sound = Uri.parse("file:///sdcard/fallbackring.ogg");
		 //发出通知
		 nm.notify(ID,n);
		 //取消通知
		 btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nm.cancel(ID);
				finish();
			}
			 
		 });
	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

}
