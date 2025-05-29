package edu.zsk.terraquest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper {

    private final AppDatabaseHelper dbHelper;

    public DatabaseHelper(Context context) {
        dbHelper = new AppDatabaseHelper(context);
    }

    public SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

}
