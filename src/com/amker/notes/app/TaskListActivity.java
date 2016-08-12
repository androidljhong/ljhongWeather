package com.amker.notes.app;

import com.amker.notes.app.TaskList.Tasks;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class TaskListActivity extends ListActivity{
	
	
	//�˵����
    private static final int NEW = 1;
    private static final int DEL = 2;
    
    
    //��ѯ����
    private static final String[] PROJECTION = new String[] {
    	Tasks._ID, //0
    	Tasks.CONTENT, //1
    	Tasks.CREATED, //2
    	Tasks.ALARM, //3
    	Tasks.DATE1, //4
    	Tasks.TIME1, //5
    	Tasks.ON_OFF, //6
    };
    
    @Override
    protected void onCreate(Bundle saveInstanceState){
    	super.onCreate(saveInstanceState);
    	//���Intent
    	final Intent intent = getIntent();
    	//����uri
    	if (intent.getData() == null){
    		intent.setData(Tasks.CONTENT_URI);
    	}
    	//���ListView
    	ListView listView = getListView();
    	//��ѯ���б���¼��Ϣ
    	Cursor cursor = managedQuery(getIntent().getData(),PROJECTION,null,null,Tasks.DEFAULT_SORT_ORDER);
    
    	//����adapter
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,new String[] {Tasks._ID,Tasks.CONTENT },
    			new int[] {android.R.id.text1,android.R.id.text2 });
    	//������¼����Ϣ��ʾ��ListView
    	setListAdapter(adapter);
    	//ΪListView ��ӵ���������
    	listView.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick1(AdapterView<?> av,View v,int position, long id ){
    			//ͨ��ID ��ѯ����¼��Ϣ
    			Uri uri = ContentUris.withAppendedId(Tasks.CONTENT_URI, id);
    			Cursor cursor = managedQuery(uri , PROJECTION, null, null, Tasks.DEFAULT_SORT_ORDER);
    			
    			if(cursor.moveToNext()){
    				int id1 = cursor.getInt(0);
    				String content = cursor.getString(1);
    				String created = cursor.getString(2);
    				int alarm = cursor.getInt(3);
    				String date1 = cursor.getString(4);
    				String time1 = cursor.getString(5);
    				int on_off = cursor.getInt(6);
    				Bundle b = new Bundle();
    				b.putInt("id", id1);
    				b.putString("content", content);
    				b.putString("created", created);
    				
    				b.putInt("alarm", alarm);
    				b.putString("date1", date1);
    				b.putString("time1", time1);
    				
    				b.putInt("on_off", on_off);
    				
    				//������¼��Ϣ��ӵ�Intent
    				intent.putExtra("b", b);
    				//������������ϸ
    				intent.setClass(TaskListActivity.this, TaskDetailActivity.class);
    				startActivity(intent);
    			}
    		}

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
    	
    }
                                   
    //����ѡ��˵�
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0,NEW,0,"�½�");
    	menu.add(0,DEL,0,"ɾ��");
    	return true;
    	
    }
    //����˵���������
    public boolean onOptionItemSelected(MenuItem item) {
    	switch (item.getItemId()){
    	case NEW:
    		//��������¼��ϸ��ϢActivity
    		Intent intent = new Intent();
    		intent.setClass(this, TaskDetailActivity.class);
    		startActivity(intent);
    		return true;
    	case DEL:
    		return true;
    	}
    	return false;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
