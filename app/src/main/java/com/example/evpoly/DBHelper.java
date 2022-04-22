package com.example.evpoly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper{
    public static String NAME="diary.db";
    public static int VERSION=1;
    public static String USER_TABLE_NAME="user";
    public DBHelper(Context context){
        super(context,NAME,null,VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="CREATE TABLE IF NOT EXISTS user("+
                "name TEXT PRIMARY KEY,"+
                "id TEXT,"+
                "pw TEXT,"+
                "num TEXT )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion >1){
            db.execSQL("DROP TABLE IF EXISTS user");
        }
    }

    public void insertUser(User user){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",user.getName());
        values.put("id",user.getId());
        values.put("pw",user.getPw());
        values.put("num",user.getNum());

        db.insert(USER_TABLE_NAME,null,values);
        db.close();
    }



    public Cursor getData(String id,String name){

        Log.d(getClass().getName(),"헹퍼에서받는값"+id);

        Log.d(getClass().getName(),"헹퍼에서받는값"+name);

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from user where id='"+id+"'",null);

        return cursor;
    }


    public Cursor getlistData(String name){

        SQLiteDatabase database = this.getReadableDatabase();

        Log.d(getClass().getName(),"헹퍼에서받는값"+name);

        Cursor cursor = database.rawQuery("select * from user where name = '"+name+"'",null);

        return cursor;
    }

}
