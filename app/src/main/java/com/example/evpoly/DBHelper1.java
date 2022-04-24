package com.example.evpoly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper1 extends SQLiteOpenHelper{
    public static String NAME="section1.db";
    public static int VERSION=1;
    public static String USER_TABLE_NAME="car";
    public DBHelper1(Context context){
        super(context,NAME,null,VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="CREATE TABLE IF NOT EXISTS car("+
                "car_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "car_num TEXT,"+
                "in_time TEXT,"+
                "left_time TEXT,"+
                "image TEXT )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion >1){
            db.execSQL("DROP TABLE IF EXISTS car");
        }
    }

    public void insertCar(Car car){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("car_num",car.getCar_num());
        values.put("in_time",car.getIn_time());
        values.put("left_time",car.getLeft_time());
        values.put("image", car.getImage());

        db.insert(USER_TABLE_NAME,null,values);
        db.close();
    }

}
