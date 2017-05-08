package com.example.admin.findatm.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.findatm.databases.SqliteDBHandler.AtmColumns;
import com.example.admin.findatm.models.MyATM;

import java.util.ArrayList;

/**
 * MyDatabase class
 * Created by naunem on 07/04/2017.
 */

public class MyDatabase {
    private SqliteDBHandler mDbHandler;

    public MyDatabase(Context context) {
        mDbHandler = new SqliteDBHandler(context);
    }

    public boolean insertATM(MyATM myATM) {
        SQLiteDatabase db = mDbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AtmColumns.MA_DIA_DIEM, myATM.getMaDiaDiem());
        values.put(AtmColumns.TEN_DIA_DIEM, myATM.getTenDiaDiem());
        values.put(AtmColumns.DIA_CHI, myATM.getDiaChi());
        values.put(AtmColumns.MA_QUAN, myATM.getMaQuan());
        values.put(AtmColumns.MA_NGAN_HANG, myATM.getMaNganHang());
        values.put(AtmColumns.LAT, myATM.getLat());
        values.put(AtmColumns.LNG, myATM.getLng());
        db.insert(SqliteDBHandler.TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public int deleteATM(int id) {
        SQLiteDatabase db = mDbHandler.getWritableDatabase();
        return db.delete(SqliteDBHandler.TABLE_NAME, AtmColumns.MA_DIA_DIEM + " = ? ", new String[]{Integer.toString(id)});
    }

    public ArrayList<MyATM> getAll() {
        ArrayList<MyATM> results = new ArrayList<>();
        SQLiteDatabase db = mDbHandler.getWritableDatabase();
        String sql = "SELECT * FROM " + SqliteDBHandler.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                MyATM myATM = new MyATM();
                myATM.setId(Integer.parseInt(cursor.getString(0)));
                myATM.setMaDiaDiem(cursor.getString(1));
                myATM.setTenDiaDiem(cursor.getString(2));
                myATM.setDiaChi(cursor.getString(3));
                myATM.setMaQuan(cursor.getString(4));
                myATM.setMaNganHang(cursor.getString(5));
                myATM.setLat(cursor.getString(6));
                myATM.setLng(cursor.getString(7));

                results.add(myATM);
            } while (cursor.moveToNext());
        }
        return results;
    }
}
