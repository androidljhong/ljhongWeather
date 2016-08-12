package com.amker.notes.app;

import android.net.Uri;
import android.provider.BaseColumns;

public final class TaskList {
	//��Ȩ����
	public static final String AUTHORITY = "com.amaker.provider.TaskList";
	
	private TaskList(){ }
		//�ڲ���
		public static final class Tasks implements BaseColumns{
			
			private Tasks(){}
			//����uri
			 public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/taskLists");
			 public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.amaker.taskList";
			 public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.amaker.taskList";
			 
			 //Ĭ��������
			 public static final String DEFAULT_SORT_ORDER = "created DESC";
			 //����
			 public static final String CONTENT = "content";
			 //����ʱ��
			 public static final String CREATED = "created";
			 //����
			 public static final String DATE1 = "date1";
			 //ʱ��
			 public static final String TIME1 = "time1";
			 //�Ƿ���
			 public static final String ON_OFF = "on_off";
			 //����
			 public static final String ALARM = "alarm";
			 
			
		}
	

}
