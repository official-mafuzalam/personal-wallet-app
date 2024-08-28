package com.octosync.personalwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "personal_wallet";
    public static final String Income = "income";
    public static final String Expense = "expense";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE expense(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "category TEXT, " +
                        "amount FLOAT," +
                        "time DOUBLE" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE income(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "category TEXT, " +
                        "amount FLOAT," +
                        "time DOUBLE" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists expense");
        db.execSQL("drop table if exists income");
    }

    //Income Section

    public void addIncome(String category, float amount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("category", category);
        contentValues.put("amount", amount);
        contentValues.put("time", System.currentTimeMillis());
        db.insert(Income, null, contentValues);
    }

    public float totalIncome() {
        float total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from income", null);

        if (cursor != null && cursor.getCount() >= 0) {
            while (cursor.moveToNext()) {
                float income = cursor.getFloat(2);
                total = total + income;
            }
        }
        return total;
    }

    //Expense Section

    public void addExpense(String category, float amount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("category", category);
        contentValues.put("amount", amount);
        contentValues.put("time", System.currentTimeMillis());
        db.insert(Expense, null, contentValues);
    }

    public float totalExpense() {
        float total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from expense", null);

        if (cursor != null && cursor.getCount() >= 0) {
            while (cursor.moveToNext()) {
                float expense = cursor.getFloat(2);
                total = total + expense;
            }
        }
        return total;
    }

    public Cursor showData(int type) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (type == 1) {
            cursor = db.rawQuery("select * from income", null);
        } else {
            cursor = db.rawQuery("select * from expense", null);
        }
        return cursor;
    }

//    public void deleteData(int id, int type) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String tableName = (type == 1) ? "income" : "expense";
//        db.execSQL("DELETE FROM " + tableName + " WHERE id = ?", new Object[]{id});
//    }

    public void deleteExpense(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from expense where id = " + id);
    }
    public void deleteIncome(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from income where id = " + id);
    }


}