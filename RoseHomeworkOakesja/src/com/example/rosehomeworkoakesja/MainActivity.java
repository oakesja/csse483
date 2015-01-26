package com.example.rosehomeworkoakesja;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private ArrayList<Task> mTasks;
	private ArrayAdapter<Task> mArrayAdapter;
	private TaskDataAdapter mTaskDataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTasks = new ArrayList<Task>();

		mTaskDataAdapter = new TaskDataAdapter(this);
		mTaskDataAdapter.open();
		mTaskDataAdapter.setAllTasks(mTasks);

		mArrayAdapter = new ArrayAdapter<Task>(this,
				android.R.layout.simple_list_item_1, mTasks);
		setListAdapter(mArrayAdapter);

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				deleteTask(mTasks.get(position));
				return false;
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mTaskDataAdapter.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.addTask) {
			addTaskDialog();
		}
		return super.onOptionsItemSelected(item);
	}

	private void addTaskDialog() {
		DialogFragment df = new DialogFragment() {

			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				final View v = inflater.inflate(R.layout.dialog_add, null);
				builder.setView(v);
				builder.setTitle(R.string.add_task);
				builder.setNegativeButton(android.R.string.cancel, null);
				builder.setPositiveButton(R.string.add, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String name = ((EditText) v
								.findViewById(R.id.dialogAddName)).getText()
								.toString();
						String course = ((EditText) v
								.findViewById(R.id.dialogAddCourse)).getText()
								.toString();
						DatePicker tp = (DatePicker) v
								.findViewById(R.id.dialogAddDueDatePicker);
						int day = tp.getDayOfMonth();
						int month = tp.getMonth();
						int year = tp.getYear();
						Task t = new Task(name, course, new GregorianCalendar(
								year, month, day));
						addTask(t);
					}
				});
				return builder.create();
			};
		};
		df.show(getFragmentManager(), "add task");
	}

	private void addTask(Task task) {
		Toast.makeText(this, "Adding task: " + task.toString(),
				Toast.LENGTH_LONG).show();
		mTaskDataAdapter.addTask(task);
		mTaskDataAdapter.setAllTasks(mTasks);
		mArrayAdapter.notifyDataSetChanged();
	}

	private void deleteTask(Task task) {
		Toast.makeText(this, "Deleting task: " + task.toString(),
				Toast.LENGTH_LONG).show();
		mTaskDataAdapter.deleteTask(task);
		mTaskDataAdapter.setAllTasks(mTasks);
		mArrayAdapter.notifyDataSetChanged();
	}
}
