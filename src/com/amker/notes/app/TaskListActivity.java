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
	
	
	//菜单项常量
    private static final int NEW = 1;
    private static final int DEL = 2;
    
    
    //查询数组
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
    	//获得Intent
    	final Intent intent = getIntent();
    	//设置uri
    	if (intent.getData() == null){
    		intent.setData(Tasks.CONTENT_URI);
    	}
    	//获得ListView
    	ListView listView = getListView();
    	//查询所有备忘录信息
    	Cursor cursor = managedQuery(getIntent().getData(),PROJECTION,null,null,Tasks.DEFAULT_SORT_ORDER);
    
    	//创建adapter
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,new String[] {Tasks._ID,Tasks.CONTENT },
    			new int[] {android.R.id.text1,android.R.id.text2 });
    	//将备忘录的信息显示到ListView
    	setListAdapter(adapter);
    	//为ListView 添加单击监听器
    	listView.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick1(AdapterView<?> av,View v,int position, long id ){
    			//通过ID 查询备忘录信息
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
    				
    				//将备忘录信息添加到Intent
    				intent.putExtra("b", b);
    				//启动背完了详细
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
                                   
    //创建选项菜单
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0,NEW,0,"新建");
    	menu.add(0,DEL,0,"删除");
    	return true;
    	
    }
    //现象菜单单击方法
    public boolean onOptionItemSelected(MenuItem item) {
    	switch (item.getItemId()){
    	case NEW:
    		//启动备忘录详细信息Activity
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
