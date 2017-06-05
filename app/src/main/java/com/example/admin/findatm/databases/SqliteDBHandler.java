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
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AtmColumns.MA_DIA_DIEM + " INTEGER, " + AtmColumns.TEN_DIA_DIEM
            + " TEXT, " + AtmColumns.DIA_CHI + " TEXT, " + AtmColumns.MA_QUAN + " TEXT, " + AtmColumns.MA_NGAN_HANG + " TEXT, "
            + AtmColumns.LAT + " TEXT, " + AtmColumns.LNG + " TEXT)";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    SqliteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    class AtmColumns implements BaseColumns {
        static final String MA_DIA_DIEM = "madiadiem";
        static final String TEN_DIA_DIEM = "tendiadiem";
        static final String DIA_CHI = "diachi";
        static final String MA_QUAN = "maquan";
        static final String MA_NGAN_HANG = "manganhang";
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
