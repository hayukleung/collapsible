package com.hayukleung.collapsible.demo;

import java.util.List;

import com.hayukleung.collapsible.Element;
import com.hayukleung.collapsible.IOrgDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 组织架构及联系人公共数据库
 * 
 */
public class OrgDBImpl implements IOrgDB {

    protected static final String DB_NAME = "org.db";
    protected static final String TB_NAME = "_table_org";
    protected static final int DB_VERSION = 1;
    protected DatabaseHelper mHelper = null;
    protected SQLiteDatabase mDatabase = null;
    protected StringBuilder mSqlCreate;

    /**
     * 构造函数
     * 
     * @param context
     */
    public OrgDBImpl(Context context) {
        mHelper = new DatabaseHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    /**
     * 数据库关闭
     */
    public void close() {
        try {
            mDatabase.close();
            mHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(mSqlCreate.toString());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // FIXME use alter table instead of drop table
            db.execSQL("drop table if exists " + TB_NAME);
            db.execSQL(mSqlCreate.toString());
        }
    }

    @Override
    public List<Element> queryAll() {
        // TODO Auto-generated method stub
        return null;
    }
}
