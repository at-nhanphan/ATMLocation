package com.example.admin.atmlocation.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * This is SqliteDBHandler class
 * Created by naunem on 07/04/2017.
 */

public class SqliteDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDatabase";
    private static final String TABLE_NAME = "ATM";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (";

    public SqliteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private class AtmColumn implements BaseColumns {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
