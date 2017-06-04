package com.example.admin.findatm.databases;

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
    public static final String TABLE_NAME = "ATM";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + AtmColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AtmColumns.MA_DIA_DIEM + " INTEGER, " + AtmColumns.TEN_DIA_DIEM
            + " TEXT, " + AtmColumns.DIA_CHI + " TEXT, " + AtmColumns.MA_QUAN + " TEXT, " + AtmColumns.MA_NGAN_HANG + " TEXT, "
            + AtmColumns.LAT + " TEXT, " + AtmColumns.LNG + " TEXT)";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SqliteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public class AtmColumns implements BaseColumns {
        public static final String MA_DIA_DIEM = "madiadiem";
        public static final String TEN_DIA_DIEM = "tendiadiem";
        public static final String DIA_CHI = "diachi";
        public static final String MA_QUAN = "maquan";
        public static final String MA_NGAN_HANG = "manganhang";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
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
