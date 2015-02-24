package com.example.rosehomeworkoakesja;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskDataAdapter {
	private static final String DATABASE_NAME = "tasks.db";
	private static final String TABLE_NAME = "tasks";
	private static final int DATABASE_VERSION = 1;

	public static final String DEBUG = "SQL";
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_COURSE = "course";
	public static final String KEY_YEAR = "year";
	public static final String KEY_MONTH = "month";
	public static final String KEY_DAY = "day";

	private SQLiteDatabase mDatabase;
	private SQLiteOpenHelper mHelper;

	public TaskDataAdapter(Context context) {
		mHelper = new TaskDbHelper(context);
	}

	public void open() {
		mDatabase = mHelper.getReadableDatabase();
	}

	public void close() {
		mDatabase.close();
	}

	private ContentValues getContentValuesFromTask(Task score) {
		ContentValues row = new ContentValues();
		row.put(KEY_NAME, score.getName());
		row.put(KEY_COURSE, score.getCourse());
		row.put(KEY_YEAR, score.getYearDue());
		row.put(KEY_MONTH, score.getMonthDue());
		row.put(KEY_DAY, score.getDayDue());
		return row;
	}

	public void addTask(Task task) {
		ContentValues row = getContentValuesFromTask(task);
		long rowId = mDatabase.insert(TABLE_NAME, null, row);
		task.setId(rowId);
	}

	public void deleteTask(Task task) {
		mDatabase.delete(TABLE_NAME, KEY_ID + " = " + task.getId(), null);
	}

	public void setAllTasks(ArrayList<Task> tasks) {
		String[] columns = null;
		Cursor cursor = mDatabase.query(TABLE_NAME, columns, null, null, null,
				null, null);
		if (cursor == null || !cursor.moveToFirst()) {
			return;
		}
		tasks.clear();
		do {
			tasks.add(getTaskFromCursor(cursor));
		} while ((cursor.moveToNext()));
		Collections.sort(tasks);
		Log.d(DEBUG, "all tasks size: " + tasks.size());
	}

	public Task getTaskFromCursor(Cursor cursor) {
		String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
		String course = cursor.getString(cursor
				.getColumnIndexOrThrow(KEY_COURSE));
		int year = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_YEAR));
		int month = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MONTH));
		int date = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_DAY));
		long id = cursor.getLong(cursor.getColumnIndexOrThrow(KEY_ID));
		Task task = new Task(name, course, new GregorianCalendar(year, month,
				date));
		task.setId(id);
		Log.d(DEBUG, "get from cursor: " + task.toString());
		return task;
	}

	private static class TaskDbHelper extends SQLiteOpenHelper {
		private static final String DROP_STATEMENT = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
		private static final String CREATE_STATEMENT;
		static {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE " + TABLE_NAME + "(");
			sb.append(KEY_ID + " integer primary key autoincrement, ");
			sb.append(KEY_NAME + " text, ");
			sb.append(KEY_COURSE + " text, ");
			sb.append(KEY_YEAR + " integer, ");
			sb.append(KEY_MONTH + " integer, ");
			sb.append(KEY_DAY + " integer ");
			sb.append(")");
			CREATE_STATEMENT = sb.toString();
		}

		public TaskDbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("EXAM", "constructor");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("EXAM", "create");
			db.execSQL(CREATE_STATEMENT);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_STATEMENT);
			db.execSQL(CREATE_STATEMENT);
		}

	}
}
