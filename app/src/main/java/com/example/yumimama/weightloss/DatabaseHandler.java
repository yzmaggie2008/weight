package com.example.yumimama.weightloss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumimama on 2/5/18.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Test";
    private static final String TABLE_NAME="history";
    private static final int VERSION=1;
    private static final String KEY_ID="id";
    private static final String KEY_DATE="date";
    private static final String KEY_WEIGHT="weight";

    //建表语句
    private static final String CREATE_TABLE="create table "+TABLE_NAME+"("+KEY_ID+
            " integer primary key autoincrement,"+KEY_DATE+" text not null,"+
            KEY_WEIGHT+" text not null);";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addHistory(historyData history){
        SQLiteDatabase db=this.getWritableDatabase();

        //使用ContentValues添加数据
        ContentValues values=new ContentValues();
        values.put(KEY_DATE,history.getDate());
        values.put(KEY_WEIGHT,history.getWeight());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public historyData getHistory(String date){
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor对象返回查询结果
        Cursor cursor=db.query(TABLE_NAME,new String[]{KEY_ID,KEY_DATE,KEY_WEIGHT},
                KEY_DATE+"=?",new String[]{date},null,null,null,null);


        historyData history=null;
        //注意返回结果有可能为空
        if(cursor.moveToFirst()){
            history=new historyData(cursor.getInt(0),cursor.getString(1), cursor.getString(2));
        }
        return history;
    }
    public int getHistoryCounts(){
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.close();

        return cursor.getCount();
    }

    //查找所有student
    public List<historyData> getAllHistory(){
        List<historyData> studentList=new ArrayList<historyData>();

        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                historyData history=new historyData();
                history.setId(Integer.parseInt(cursor.getString(0)));
                history.setDate(cursor.getString(1));
                history.setWeight(cursor.getString(2));
                studentList.add(history);
            }while(cursor.moveToNext());
        }
        return studentList;
    }

    //更新student
    public int updateHitory(historyData history){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_DATE,history.getDate());
        values.put(KEY_WEIGHT,history.getWeight());

        return db.update(TABLE_NAME,values,KEY_ID+"=?",new String[]{String.valueOf(history.getId())});
    }
    public void deleteHistory(historyData history){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(history.getId())});
        db.close();
    }
}
