package edu.zsk.terraquest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UserDatabaseHelper {

    private final AppDatabaseHelper dbHelper;

    public UserDatabaseHelper(Context context) {
        dbHelper = new AppDatabaseHelper(context);
    }

    public SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

}
