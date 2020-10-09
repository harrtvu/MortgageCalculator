package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.db.Activity;
import com.example.todolist.db.databaseHelper;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private databaseHelper manager;
    private ListView listView;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        manager = new databaseHelper(this);
        listView = (ListView) findViewById(R.id.list_todo);

        updateUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("New Task")
                        .setMessage("Add a new task:")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task  = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db  = manager.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(Activity.ActivityEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(Activity.ActivityEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = db.query(Activity.ActivityEntry.TABLE, new String[] {Activity.ActivityEntry._ID, Activity.ActivityEntry.COL_TASK_TITLE},null, null,null,null,null);

        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(Activity.ActivityEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(index));

            if(adapter == null){
                adapter = new ArrayAdapter<String>(this, R.layout.items, R.id.task_title, taskList );
                listView.setAdapter(adapter);
            }
            else {
                adapter.clear();
                adapter.addAll(taskList);
                adapter.notifyDataSetChanged();
            }
        }
        cursor.close();
        db.close();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView  = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = manager.getWritableDatabase();
        db.delete(Activity.ActivityEntry.TABLE, Activity.ActivityEntry.COL_TASK_TITLE + " = ?", new String[] {task});
        db.close();
        updateUI();

    }


}

