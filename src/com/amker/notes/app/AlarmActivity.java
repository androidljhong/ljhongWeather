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
		//���Button��textViewʵ��
		Button btn = (Button) findViewById(R.id.cancelButton01);
		TextView tv = (TextView) findViewById(R.id.msgTextView01);
		
		//���NotificationManager
		String service = Context.NOTIFICATION_SERVICE;
		final NotificationManager nm = (NotificationManager)getSystemService(service);
		//ʵ����NOtification
		Notification n = new Notification();
		//������ʾ��ʾ��Ϣ������ϢҲ����״̬����ʾ
		String msg = getIntent().getStringExtra("msg");
		//��ʾʱ��
		n.tickerText = msg;
		tv.setText(msg);

		//����������ʾ
		 n.sound = Uri.parse("file:///sdcard/fallbackring.ogg");
		 //����֪ͨ
		 nm.notify(ID,n);
		 //ȡ��֪ͨ
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
