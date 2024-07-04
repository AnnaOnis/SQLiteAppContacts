package com.sportsprime.sqliteappcontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public DBHelper(@Nullable Context context) {

        super(context,"myDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG,"----- onCreate database");
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "name text,"
                +"email text"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addItem(SQLiteDatabase db, Item item){
        ContentValues cv = new ContentValues();
        cv.put("name", item.getName());
        cv.put("email", item.getEmail());
        Log.d(LOG_TAG,"----- Insert in mytable: ---");
        long rowId = db.insert("mytable",null,cv);
        Log.d(LOG_TAG,"----- row inserted, ID = " + rowId);
    }

    public List<Item> getAll(SQLiteDatabase db){

        List<Item> items = new ArrayList<>();

        Log.d(LOG_TAG,"----- Rows in mutable: ---");
        Cursor c = db.query("mytable",null,null,null,null,null,null);
        if (c.moveToFirst()){

            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int emailColIndex = c.getColumnIndex("email");

            do {
                items.add(new Item(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(emailColIndex)));

                Log.d(LOG_TAG,"ID = " + c.getInt(idColIndex)+
                        ", name = " + c.getString(nameColIndex)+
                        ", email = " + c.getString(emailColIndex));

            }while (c.moveToNext());
        }else
            Log.d(LOG_TAG,"0 rows");
        c.close();

        return items;
    }

    public void clearDB(SQLiteDatabase db){
        Log.d(LOG_TAG,"---- Clear mytable: ---");
        int clearCount = db.delete("mytable",null,null);
        Log.d(LOG_TAG,"deleted rows count = " + clearCount);
    }

    public boolean deleteItem(SQLiteDatabase db, String name, String email){

        int deleteCount = db.delete("mytable", "name=? and email=?", new String[]{name, email});

        Log.d(LOG_TAG,"---- Delete Item ---");
        Log.d(LOG_TAG,"deleted rows count = " + deleteCount);

        return deleteCount > 0;
    }
}
