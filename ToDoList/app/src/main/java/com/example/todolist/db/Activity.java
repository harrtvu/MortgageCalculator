package com.example.todolist.db;
import android.provider.BaseColumns;


public class Activity {
    public static final String DB_NAME ="com.example.myapplication.db";
    public static final int DB_VERSION = 1;
    public class ActivityEntry implements BaseColumns {
        public static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE = "title";
    }
}

