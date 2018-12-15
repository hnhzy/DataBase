package com.hzy.database.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import com.hzy.database.config.Constant;

/**
 * SQLiteOpenHelper
 * 1、提供onCreate()、onUpgrade()等创建数据更新数据的方法
 * 2、提供获取数据库对象的函数
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    public String CREATE_PERSON="create table if not exists "+
            Constant.TABLE_PERSON+"("+Constant.ID+" Integer NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
            ""+Constant.NAME+" TEXT," +
            ""+Constant.AGE+" Integer NOT NULL)";

    public static final String DROP_PERSON="drop table "+Constant.TABLE_PERSON+"";


    public DBHelper(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
        mContext=context;
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        Log.e("TAG","onCreate");
        db.execSQL(CREATE_PERSON);
    }


    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        Log.e("TAG","onUpgrade");
        db.execSQL(DROP_PERSON);
        db.execSQL(CREATE_PERSON);
        Toast.makeText(mContext, "更新数据库", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // TODO 每次成功打开数据库后首先被执行
        Log.e("TAG","onOpen");
    }
}