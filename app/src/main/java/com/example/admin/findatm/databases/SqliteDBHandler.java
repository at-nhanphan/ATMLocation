package com.example.admin.findatm.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * This is SqliteDBHandler class
 * Created by naunem on 07/04/2017.
 */

class SqliteDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDatabase";
    static final String TABLE_NAME = "ATM";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + AtmColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AtmColumns.ADDRESS_ID + " INTEGER, " + AtmColumns.ADDRESS_NAME
            + " TEXT, " + AtmColumns.ADDRESS + " TEXT, " + AtmColumns.DISTRICT_ID + " TEXT, " + AtmColumns.BANK_ID + " TEXT, "
            + AtmColumns.LAT + " TEXT, " + AtmColumns.LNG + " TEXT)";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    SqliteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    class AtmColumns implements BaseColumns {
        static final String ADDRESS_ID = "addressId";
        static final String ADDRESS_NAME = "addressName";
        static final String ADDRESS = "address";
        static final String DISTRICT_ID = "districtId";
        static final String BANK_ID = "bankId";
        static final String LAT = "lat";
        static final String LNG = "lng";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
