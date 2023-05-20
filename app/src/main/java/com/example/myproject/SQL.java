package com.example.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SQL extends SQLiteOpenHelper {
    private static final String dbName = "PhotoDB.db";
    private static final int version = 1;

    public static final String TABLE_NAME = "Photos";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PHOTO = "PHOTO";
    public static final String COL_TEXT = "text";
    public static final String COL_LOCATION = "loc";
    public static final String COL_DATE = "date";


    private static final String CREATE_TABLE_PHOTOS = "create table if not exists "+ TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL_NAME + " TEXT," +COL_PHOTO+" TEXT NOT NULL,"
            + COL_TEXT + " TEXT, " +COL_LOCATION + " TEXT," +COL_DATE + " TEXT);";


    private Context context;


//    Начало Методов

    public SQL(Context context) {
        super(context, dbName, null, version);
        this.context = context;
    }


//    Создание таблицы
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_PHOTOS);
        } catch (Exception e) {
        }
    }
//  Изменения данных в таблице
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


//  Запись таблицы в List
public List<PhotoItem> listPhoto(){
    String sql = "select * from " + TABLE_NAME;
    SQLiteDatabase db = this.getReadableDatabase();

    List<PhotoItem> storeContacts = new ArrayList<>();
    Cursor cursor = db.rawQuery(sql, null);
    if(cursor.moveToFirst()){
        do{
            int id = Integer.parseInt(cursor.getString(0));
            String Name = cursor.getString(1);
            String Photo = cursor.getString(2);
            String Text = cursor.getString(3);
            String Location = cursor.getString(4);
            String Date = cursor.getString(5);
            storeContacts.add(new PhotoItem(Name,Photo,Text,Location,Date));
        }while (cursor.moveToNext());
    }
    cursor.close();
    return storeContacts;
}

// Добовление информации в таблицу
    public void InsertPhoto(String Name, String Photo, String Date, String Text, String Geoposition) {
//        Создание новой строчки
        SQLiteDatabase db = this.getWritableDatabase();
//        Создание переменной для записи её в таблицу
        ContentValues cv = new ContentValues();
//      Запись в переменную новой информации
        cv.put(COL_NAME,Name);
        cv.put(COL_PHOTO, Photo);
        cv.put(COL_TEXT, Text);
        cv.put(COL_LOCATION, Geoposition);
        cv.put(COL_DATE,Date);

//      Запись информации в новую строку
        long result = db.insert(TABLE_NAME,null,cv);

    }


}
