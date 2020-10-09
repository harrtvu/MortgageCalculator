package com.example.todolist.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {

    public databaseHelper(Context context) {
        super(context, Activity.DB_NAME, null, Activity.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "Create Table " + Activity.ActivityEntry.TABLE + " ( " +
                Activity.ActivityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Activity.ActivityEntry.COL_TASK_TITLE + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Activity.ActivityEntry.TABLE);
        onCreate(db);
    }
}
