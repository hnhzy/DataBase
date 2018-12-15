package com.hzy.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.hzy.database.bean.Person;
import com.hzy.database.config.Constant;
import com.hzy.database.util.DBHelper;
import com.hzy.database.util.DBManger;

import java.util.List;

/**
 * Created by hzy on 2018/12/13
 **/
public class MainActivity extends Activity {

    private TextView mTvContext;

    private DBHelper helper;
    private ContentValues cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvContext=findViewById(R.id.tv_context);
        helper = DBManger.getInstance(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createDatabase://创建数据库
                SQLiteDatabase db = helper.getWritableDatabase();
                helper.onCreate(db);
                db.close();
                break;
            case R.id.updateDatabase://更新数据库
                db = helper.getWritableDatabase();
                helper.onUpgrade(db,Constant.DB_VERSION,Constant.DB_NEW_VERSION);
                db.close();
                break;
            case R.id.insert://插入
                db = helper.getWritableDatabase();
                //1、显示开启事务
                db.beginTransaction();
                for (int i = 0; i <20; i++) {
                    String sql = "insert into "+Constant.TABLE_PERSON +" values("+i+",'xiaomi"+i+"',"+i+")";
                    DBManger.execSQL(db,sql);
                }
                //2、提交当前事务
                db.setTransactionSuccessful();
                //3、关闭事务
                db.endTransaction();
                db.close();
                break;
            case R.id.update://修改
                db = helper.getWritableDatabase();
                String updateSql="update "+Constant.TABLE_PERSON+" set "+Constant.NAME+"='xiaoming' where _id=11";
                DBManger.execSQL(db,updateSql);
                db.close();
                break;
            case R.id.query://查询
                db=helper.getWritableDatabase();
//                String selectSql="select * from person where _id=?";
//                String[] selectionArgs=new String[]{"11"};
                String selectSql="select * from person";
                Cursor cursor=DBManger.execQuery(db,selectSql,null);
                List<Person> list=DBManger.CursorToList(cursor);
                if(list!=null){
                    mTvContext.setText(list.toString());
                }
                db.close();
                break;
            case R.id.delete://删除
                db = helper.getWritableDatabase();
                String delSql="delete from "+Constant.TABLE_PERSON+" where "+Constant.ID+"=1";
                DBManger.execSQL(db,delSql);
                db.close();
                break;



            case R.id.insertApi://插入
                db = helper.getWritableDatabase();
                /**
                 * long insert(String table, String nullColumnHack, ContentValues values)
                 * String table           插入数据表的名称
                 * String nullColumnHack,
                 * ContentValues          values键为String类型或者HashMap的集合
                 * 返回值 long 表示插入数据的列
                 */
                cv = new ContentValues();
                cv.put(Constant.ID,21);
                cv.put(Constant.NAME,"charu");
                cv.put(Constant.AGE,111);
                long resInsert=db.insert(Constant.TABLE_PERSON,null, cv);
                if(resInsert>0){
                    Toast.makeText(this, "插入数据成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "插入数据失败", Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.updateApi://修改
                db=helper.getWritableDatabase();
                /**
                 * int update(String table, ContentValues values, String whereClause, String[] whereArgs)
                 * String table          修改数据表的名称
                 * ContentValues values  键为String类型或者HashMap的集合
                 * String whereClause    修改条件
                 * String[] whereArgs    修改条件的占位符
                 * 返回表示修改的条数
                 */
                cv=new ContentValues();
                cv.put(Constant.AGE,222);
                String whereClause=Constant.ID+"=?";
                String[] whereArgs=new String[]{"12"};
                int resUpdate=db.update(Constant.TABLE_PERSON,cv,whereClause,whereArgs);
                if(resUpdate>0){
                    Toast.makeText(this, "修改数据成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "修改数据失败", Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            case R.id.queryApi://查询
                db=helper.getWritableDatabase();
                //select * from person where x group by x having x order by x;
                /**
                 * Cursor query(String table, String[] columns, String selection,
                 *             String[] selectionArgs, String groupBy, String having,
                 *             String orderBy)
                 * String table,      数据表的名称
                 * String[] columns,  查询的字段名称  null 表示查询所有
                 * String selection,  查询条件 where
                 * String[] selectionArgs, 查询条件占位符
                 * String groupBy,    group by    分组
                 * String having,     having      分组后筛选
                 * String orderBy     order by    排序
                 */
                Cursor cursor1=db.query(Constant.TABLE_PERSON,null,null,
                        null,null,null,Constant.ID+" desc");
                List<Person> personList=DBManger.CursorToList(cursor1);
                if(personList!=null){
                    mTvContext.setText(personList.toString());
                }
                db.close();
                break;
            case R.id.deleteApi://删除
                db=helper.getWritableDatabase();
                /**
                 * int delete(String table, String whereClause, String[] whereArgs)
                 * String table        删除数据表的名称
                 * String whereClause  删除条件
                 * String[] whereArgs  删除条件的占位符
                 * 返回表示删除的条数
                 */
                String delWhereClause=Constant.ID+"=?";
                String[] delwhereArgs=new String[]{"13"};
                int delRes=db.delete(Constant.TABLE_PERSON,delWhereClause,delwhereArgs);
                if(delRes>0){
                    Toast.makeText(this, "删除数据成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "删除数据失败", Toast.LENGTH_SHORT).show();
                }
                db.close();
                break;
            default:
                break;
        }
    }
}
