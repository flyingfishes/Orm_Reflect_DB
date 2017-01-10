package pdm.com.ormdatabase.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ormoperatedb.db.BaseDatabaseHelper;

/**
 * Author:pdm on 2016/10/7 8:43
 * Email:aiyh0202@163.com
 */

public class DBHelper extends BaseDatabaseHelper{
    private static final String DATABASENAME = "user";
    private static final int DATABASE_VERSION = 1;
    //单例模式
    private DBHelper(Context context){
        super(context, DATABASENAME, null, DATABASE_VERSION);
    }

    private static BaseDatabaseHelper helper;

    public static BaseDatabaseHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (BaseDatabaseHelper.class) {
                if (helper == null) {
                    helper = new DBHelper(context);
                }
            }
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("create table students(_sid integer primary key autoincrement," +
                    "stu_name varchar(20),stu_classname varchar(20))");

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            db.execSQL("create table product(_pid integer primary key autoincrement," +
                    "product_name varchar(20),product_price float)");

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


}
