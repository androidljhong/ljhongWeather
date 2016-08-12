package com.amker.notes.app;


import java.util.Calendar;

import com.amaker.ch17.app.R;
import com.amker.notes.app.TaskList.Tasks;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;


public class TaskDetailActivity extends ListActivity{

	//����¼��Ϣ�б�
	private ListView listView = null;
	//��������
	private int mYear;
	private int mMonth;
	private int mDay;
	//����ʱ��
	private int mHour;
	private int mMinute;
	//������ʾTextView
	private TextView dateName,dateDesc;
	//ʱ����ʾ
	private TextView timeName,timeDesc;
	//��������TEXTVIEW
	private TextView contentName,contentDesc;
	//�Ƿ�������
	 private int on_off = 0;
	 //�Ƿ���������
	 private int alarm = 0;
	 
	 //��ʾ���ڡ�ʱ��Ի�����
	 static final int DATE_DIALOG_ID = 0;
	 static final int TIME_DIALOG_ID = 1;
	 
	 //�������ݡ����ڡ�ʱ���ַ���
	 private String content ,date1,time1;
	 //����¼ID
	 private int id1;
	 //��ѡ��
	 private CheckedTextView ctv1,ctv2;
	 //���ʲ���ʵ��
	 private LayoutInflater li;
	 
	 //��ʼ������
	 private void init(Intent intent) {
		 Bundle b = intent.getBundleExtra("b");
		 if(b != null){
			 id1 = b.getInt("id");
			 content = b.getString("content");
			 date1 = b.getString("date1");
			 time1 = b.getString("time1");
			 on_off = b.getInt("on_off");
			 alarm = b.getInt("alarm");
			 if (date1 != null && date1.length() >0){
				 String[] strs = date1.split("/");
				 mYear = Integer.parseInt(strs[0]);
				 mMonth = Integer.parseInt(strs[1]);
				 mDay= Integer.parseInt(strs[2]);
			 }
			 
			 if(time1 != null && time1.length() > 0){
				 String [] strs = time1.split(":");
				 mHour = Integer.parseInt(strs[0]);
				 mMinute = Integer.parseInt(strs[1]);
			 }
		 }
	 }
	 
	 @Override
	 public void onCreate(Bundle savedInStanceState){
		 super.onCreate(savedInStanceState);
		 
		 //���ListView
		 listView = getListView();
		 //ʵ����LayoutInflater
		 li = getLayoutInflater();
		 //����listView adapter
		 listView.setAdapter(new ViewAdapter());
		 //�ɶ�ѡ
		 listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		 
		 //���Calendarʵ��
		 final Calendar  c = Calendar.getInstance();
		 //��õ�ǰ���ڡ�ʱ��
		 mYear = c.get(Calendar.YEAR);
		 mMonth = c.get(Calendar.MONTH);
		 mDay = c.get(Calendar.DAY_OF_MONTH);
		 mHour = c.get(Calendar.HOUR_OF_DAY);
		 mMinute = c.get(Calendar.MINUTE);
		 
		 //��Ӧ�б����¼�
		 listView.setOnItemClickListener(new OnItemClickListener(){
			 @Override
			 public void onItemClick(AdapterView<?> av,View v,int position,long id){
				 
				 switch (position){
				 //�����Ƿ�������
				 case 0:
					 ctv1 = (CheckedTextView) v;
					 if(ctv1.isChecked()){
						 on_off = 0;
					 } else {
						 on_off  = 1;
					 }
					 break;
					 //������������
				 case 1:
					 showDialog(DATE_DIALOG_ID);
					 break;
					 //��������ʱ��
				 case 2:
					 showDialog(TIME_DIALOG_ID);
					 break;
					 //������������
				 case 3:
					 showDialog("���������ݣ� ");
					 break;
					 //�����Ƿ�����������
				 case 4:
					 ctv2 = (CheckedTextView) v;
					 if (ctv2.isChecked()){
						 alarm = 0;
						 setAlarm(false);
					 } else {
						 alarm = 1;
						 setAlarm(true);
					 }
					 break;
					 default:
						 break;
					 
				 
				 }
			 }
			 
		 });
		 
	 }
	 
	 @Override
	 protected void onResume(){
		 super.onResume();
		 //��ʼ���б�
		 init(getIntent());
		 }
	 //ListView Adatper,����ʵ�����б��ÿһ��ͨ���Զ�����ͼʵ��
	 @SuppressLint({ "ViewHolder", "InflateParams" })
	class ViewAdapter extends BaseAdapter {
       //�б���ʾ����
		 String[] strs = {"�Ƿ���","����","ʱ��","����","��������"};
		 
		 //����б���ͼ
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return strs.length;
		}
        //�����б���
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}
        //�����б�ID
		@Override
		public long getItemId(int position) {
		
			return position;
		}
        //����б�ǰ�б�����ͼ
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			View v = li.inflate(R.layout.item_row, null);
			switch(position){
			//�Ƿ�����������¼
			case 0:
				ctv1 = (CheckedTextView) li.inflate(android.R.layout.simple_list_item_multiple_choice, null);
				ctv1.setText(strs[position]);
				if(on_off == 0){
					ctv1.setChecked(false);
				} else {
					ctv1.setChecked(true);
				}
				return ctv1;
				//��������
			case 1 :
			dateName = (TextView) v.findViewById(R.id.name);
			dateDesc = (TextView) v.findViewById(R.id.desc);
			dateName.setTag(mYear + "/" + mMonth + "/" + mDay);
			return v;
			// ����ʱ��
			case 2:
				timeName = (TextView) v.findViewById(R.id.name);
				timeDesc = (TextView) v.findViewById(R.id.desc);
				timeName.setText(strs[position]);
				timeDesc.setText(mHour + ":" +mMinute);
				return v;
			case 3:
				contentName = (TextView) v.findViewById(R.id.name);
				contentDesc = (TextView) v.findViewById(R.id.desc);
				contentName.setText(strs[position]);
				contentDesc.setText(content);
				return v;
				//�Ƿ�������ʾ
			case 4 :
				ctv2 = (CheckedTextView) li.inflate(android.R.layout.simple_list_item_multiple_choice,null);
				ctv2.setText(strs[position]);
				if(alarm == 0){
					ctv2.setChecked(false);
				} else {
					ctv2.setChecked(true);
				}
				return ctv2;
						default:
							break;

			}
			return null;
		}
		 
	 }
	  //��ʾ�Ի���
	 @Override
	 protected Dialog onCreateDialog(int id) {
		 switch (id){
		 //��ʾ���ڶԻ���
		 case DATE_DIALOG_ID:
		 return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
		 //��ʾʱ��Ի���
		 case TIME_DIALOG_ID:
	     return new TimePickerDialog(this,mTimeSetListener,mHour,mMinute,false);
		 }
		return null;
		 
	 }
	 //����֪ͨ��ʾ
	 private void setAlarm(boolean flag){
		 final String BC_ACTION = "com.amamker.notes.app.TaskReceiver";
		 //���AlarmManageʵ��
		 final AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		 //ʵ����Intent
		 Intent intent = new Intent();
		 //����intent��ation����
		 intent.setAction(BC_ACTION);
		 intent.putExtra("name", content);
		 //ʵ����PendingIntent
		 final PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
		 //��ȡϵͳʱ��
		 final long time1 = System.currentTimeMillis();
		 Calendar c = Calendar.getInstance();
		 c.set(mYear, mMonth, mDay, mHour, mMinute);
		 long time2 = c.getTimeInMillis();
		 if (flag && (time2-time1)>0 && on_off==1){
			 am.set(AlarmManager.RTC_WAKEUP,time2, pi);
		 } else {
			 am.cancel(pi);
		 }
		 
	 }
	 
	 /*
	  *
	  * ������ʾ���ڶԻ���
	  * */
	 private void showDialog(String msg){
		 View v = li.inflate(R.layout.item_content, null);
		 final EditText contentET = (EditText) v.findViewById(R.id.content);
		 contentET.setText(content);
		 new AlertDialog.Builder(this).setView(v).setMessage(msg).setCancelable(false).setPositiveButton("ȷ��", 
				 new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int id) {
						content = contentET.getText().toString();
						contentDesc.setText(content);
							
					}
				}).show();
	 }
	 //ʱ��ѡ��Ի���
	 private TimePickerDialog.OnTimeSetListener mTimeSetListener = 
			 new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					mHour = hourOfDay;
					mMinute = minute;
					timeDesc.setText(mHour + ":" + mMinute);
					
				}
			};
	 //����ѡ��Ի���
			private DatePickerDialog.OnDateSetListener mDateSetListener = 
					new DatePickerDialog.OnDateSetListener() {
						
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							mYear = year;
							mMonth = monthOfYear;
							mDay = dayOfMonth;
							dateDesc.setText(mYear + "/" + mMonth + "/" + mDay);
							
						}
					};
	 //������޸ı���¼��Ϣ
					protected void onPause(){
						super.onPause();
						saveOrUpdate();
						
					};
    //������޸ı���¼��Ϣ
	private void saveOrUpdate() {
		ContentValues values = new ContentValues();
		 values.clear();
		 values.put(Tasks.CONTENT, contentDesc.getText().toString());
		 values.put(Tasks.DATE1, dateDesc.getText().toString());
		 values.put(Tasks.TIME1, timeDesc.getText().toString());
		 
		 values.put(Tasks.ON_OFF, ctv1.isChecked() ? 1:0);
		 values.put(Tasks.ALARM, ctv2.isChecked() ? 1:0);
		 //�޸�
		 if(id1 !=0){
			 Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, id1);
			 getContentResolver().update(uri, values, null, null);
	     //����
			 
		 } else {
			  Uri uri = TaskList.Tasks.CONTENT_URI;
			  getContentResolver().insert(uri, values);
		 }
	};
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
}
