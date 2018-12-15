package com.hzy.database.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hzy.database.bean.Person;
import com.hzy.database.config.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzy on 2018/12/13
 **/
public class DBManger {

    private static DBHelper helper;

//    private DBManger() {
//
//
//    }


    public static DBHelper getInstance(Context context) {
        if (helper == null) {
//            synchronized (DBManger.class) {
//                if (helper == null) {
                    helper = new DBHelper(context);
//                }
//            }
        }
        return helper;
    }

    /**
     * 根据sql语句在数据库中执行语句
     * @param db  数据库对象
     * @param sql sql语句
     */
    public static void execSQL(SQLiteDatabase db,String sql){
        if(db!=null){
            if(sql!=null&&!"".equals(sql)){
                db.execSQL(sql);
            }

        }
    }

    public static void execSQL(SQLiteDatabase db,String sql, Object[] bindArgs){
        if(db!=null){
            if(sql!=null&&!"".equals(sql)){
                db.execSQL(sql,bindArgs);
            }

        }
    }

    /**
     * 查询语句
     * @param db   数据库对象
     * @param sql  sql语句
     * @param selectionArgs  查询条件占位符
     * @return  查询结果
     */
    public static Cursor execQuery(SQLiteDatabase db, String sql,String[] selectionArgs){
        Cursor cursor=null;
        if(db!=null){
            if(sql!=null&&!"".equals(sql)){
                /**
                 * Cursor rawQuery(String sql, String[] selectionArgs)
                 * String[] selectionArgs 查询条件
                 */
                cursor=db.rawQuery(sql,selectionArgs);
            }

        }
        return cursor;
    }


    public static List<Person> CursorToList(Cursor cursor){
        List<Person> list=new ArrayList<>();
        while (cursor.moveToNext()){//moveToNext() 如果下一条记录存在则返回true
            //int getColumnIndex(String columnName) 根据参数中指定的字段名称获取下标
            int columnIndex=cursor.getColumnIndex(Constant.ID);
            //int getInt(int columnIndex); 根据参数中指定的字段下标，获取对应的int类型的值
            int _id=cursor.getInt(columnIndex);

            String name=cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age=cursor.getInt(cursor.getColumnIndex(Constant.AGE));
            Person person=new Person(_id,name,age);
            list.add(person);
        }
        return list;
    }


}
