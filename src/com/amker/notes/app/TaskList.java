package com.amker.notes.app;

import android.net.Uri;
import android.provider.BaseColumns;

public final class TaskList {
	//授权常量
	public static final String AUTHORITY = "com.amaker.provider.TaskList";
	
	private TaskList(){ }
		//内部类
		public static final class Tasks implements BaseColumns{
			
			private Tasks(){}
			//访问uri
			 public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/taskLists");
			 public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.amaker.taskList";
			 public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.amaker.taskList";
			 
			 //默认排序常量
			 public static final String DEFAULT_SORT_ORDER = "created DESC";
			 //内容
			 public static final String CONTENT = "content";
			 //创建时间
			 public static final String CREATED = "created";
			 //日期
			 public static final String DATE1 = "date1";
			 //时间
			 public static final String TIME1 = "time1";
			 //是否开启
			 public static final String ON_OFF = "on_off";
			 //警告
			 public static final String ALARM = "alarm";
			 
			
		}
	

}
