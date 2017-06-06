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
    private final SqliteDBHandler mDbHandler;

    public MyDatabase(Context context) {
        mDbHandler = new SqliteDBHandler(context);
    }

    public boolean insertATM(MyATM myATM) {
        SQLiteDatabase db = mDbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AtmColumns.ADDRESS_ID, myATM.getAddressId());
        values.put(AtmColumns.ADDRESS_NAME, myATM.getAddressName());
        values.put(AtmColumns.ADDRESS, myATM.getAddress());
        values.put(AtmColumns.DISTRICT_ID, myATM.getDistrictId());
        values.put(AtmColumns.BANK_ID, myATM.getBankId());
        values.put(AtmColumns.LAT, myATM.getLat());
        values.put(AtmColumns.LNG, myATM.getLng());
        db.insert(SqliteDBHandler.TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public int deleteATM(int id) {
        SQLiteDatabase db = mDbHandler.getWritableDatabase();
        return db.delete(SqliteDBHandler.TABLE_NAME, AtmColumns.ADDRESS_ID + " = ? ", new String[]{Integer.toString(id)});
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
                myATM.setAddressId(cursor.getString(1));
                myATM.setAddressName(cursor.getString(2));
                myATM.setAddress(cursor.getString(3));
                myATM.setDistrictId(cursor.getString(4));
                myATM.setBankId(cursor.getString(5));
                myATM.setLat(cursor.getString(6));
                myATM.setLng(cursor.getString(7));

                results.add(myATM);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }
}
