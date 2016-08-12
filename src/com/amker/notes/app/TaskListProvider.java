package com.amker.notes.app;

import java.util.HashMap;

import com.amker.notes.app.TaskList.Tasks;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
/*
 * @author ����־
 * �ṩ����ά���ķ���
 * */

public class TaskListProvider extends ContentProvider {
	//���ݿ����Ƴ���
	private static final String  DATABASE_NAME = "task_list.db";
	//���ݿ�汾����
	private static final int DATABASE_VERSION = 1;
	//�����Ƴ���
	private static final String TASK_LIST_TABLE_NAME = "taskLists";
	//��ѯ�м���
	private static HashMap<String,String> sTaskListProjectionMap;
	//��ѯ����������
	private static final int TASKS = 1 ;
	private static final int TASKS_ID = 2;
	//Uri������
	private static final UriMatcher sUriMatcher;
	//���ݿ⹤����ʵ��
	private DatabaseHelper mOpenHelper;
	//�ڲ������࣬�������ߴ����ݿ⣬������ɾ����
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context){
		super(context , DATABASE_NAME , null , DATABASE_VERSION);	
		}  
		//������
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TASK_LIST_TABLE_NAME + " ("
					+ Tasks._ID + " INTEGER PRIMARY KEY,"
					+ Tasks.DATE1 + " TEXT,"
					+ Tasks.TIME1 + " TEXT,"
					+ Tasks.CONTENT + " TEXT,"
					+ Tasks.ON_OFF + " TEXT,"
					+ Tasks.ALARM + " TEXT,"
					+ Tasks.CREATED + " TEXT," 
					+ ");");
			
		}
		//ɾ����
	

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS taskLists");
			onCreate(db);
		}
	}
	//�������ߴ����ݿ�
	@Override
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}
	
    // ��ѯ
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,String sortOrder){
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		switch(sUriMatcher.match(uri)) {
		//��ѯ����
		case TASKS:
			qb.setTables(TASK_LIST_TABLE_NAME);
			qb.setProjectionMap(sTaskListProjectionMap);
			break;
			//����ID��ѯ
		case TASKS_ID:
			qb.setTables(TASK_LIST_TABLE_NAME);
			qb.setProjectionMap(sTaskListProjectionMap);
			qb.appendWhere(Tasks._ID  + "=" + uri.getPathSegments().get(1) );
			break;
		default: 
			throw new IllegalArgumentException("Uri ����! " + uri);
		
		}
		//ʹ��Ĭ������
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)){
			orderBy = TaskList.Tasks.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}
		//������ݿ�ʵ��
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		//�����α꼯��
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
	//�������
	@Override
	public String getType(Uri uri){
		switch (sUriMatcher.match(uri)) {
		case TASKS:
			return Tasks.CONTENT_TYPE;
		case TASKS_ID:
			return Tasks.CONTENT_ITEM_TYPE;
		
			default:
				throw new IllegalArgumentException("����� URI: " + uri);
		}
	}
	//��������
	@Override 
	public Uri insert(Uri uri ,ContentValues initiaValues){
		if (sUriMatcher.match(uri) != TASKS) {
			throw new IllegalArgumentException("����� URI: " + uri);
		}
		ContentValues values ;
		if (initiaValues != null){
			values = new ContentValues(initiaValues);
		} else {
			values = new ContentValues();
		}
		//������ݿ�ʵ��
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		//�������ݷ�����ID 
		long rowId = db.insert(TASK_LIST_TABLE_NAME, Tasks.CONTENT, values);
		if (rowId > 0){
			Uri taskUri = ContentUris.withAppendedId(TaskList.Tasks.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(taskUri, null);
			return taskUri;
		}
		throw new SQLException("��������ʧ�� " + uri );
	}
	//ɾ������
	@Override
	 public int delete(Uri uri,String where,String[] whereArgs){
		//��ȡ���ݿ�ʵ��
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)){
		//����ָ������ɾ��
		case TASKS:
			count = db.delete(TASK_LIST_TABLE_NAME, where, whereArgs);
			break;
		//����ָ��������IDɾ��
		case TASKS_ID:
			String noteId = uri.getPathSegments().get(1);
			count = db.delete(TASK_LIST_TABLE_NAME, Tasks._ID + "=" + noteId + (!TextUtils.isEmpty(where) ? "AND (" +where+')':""), whereArgs);
			break;
			default:
				throw new IllegalArgumentException("����� URI" +uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
	
	//��������
	@Override
	public int update(Uri uri,ContentValues values,String where, String[] whereArgs){
		//������ݿ�ʵ��
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch(sUriMatcher.match(uri)){
		//����ָ����������
		case TASKS:
			count = db.update(TASK_LIST_TABLE_NAME, values, where, whereArgs);
			break;
			//����ָ��������ID ����
		case TASKS_ID:
			String noteId = uri.getPathSegments().get(1);
			count = db.update(TASK_LIST_TABLE_NAME, values,Tasks._ID + "=" + noteId + (!TextUtils.isEmpty(where) ? "AND (" +where+')':""), whereArgs);
			break;
			default:
				throw new IllegalArgumentException("����� URI" +uri);
				
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
	
	static {
		//Uriƥ�乤����
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(TaskList.AUTHORITY, "taskLists", TASKS);
		sUriMatcher.addURI(TaskList.AUTHORITY, "taskLists/#", TASKS_ID);
		
		//ʵ������ѯ�м���
		sTaskListProjectionMap = new HashMap<String,String>();
		//��Ӳ�ѯ��
		sTaskListProjectionMap.put(Tasks._ID, Tasks._ID);
		sTaskListProjectionMap.put(Tasks.CONTENT, Tasks.CONTENT);
		sTaskListProjectionMap.put(Tasks.CREATED, Tasks.CREATED);
		
		sTaskListProjectionMap.put(Tasks.ALARM, Tasks.ALARM);
		sTaskListProjectionMap.put(Tasks.DATE1, Tasks.DATE1);
		sTaskListProjectionMap.put(Tasks.TIME1, Tasks.TIME1);
		
		
		sTaskListProjectionMap.put(Tasks.ON_OFF, Tasks.ON_OFF);
	}
}
