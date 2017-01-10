package com.ormoperatedb.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ormoperatedb.db.BaseDatabaseHelper;
import com.ormoperatedb.object.Item;
import com.ormoperatedb.object.Key;
import com.ormoperatedb.object.Orm;
import com.ormoperatedb.utils.DBConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pdm on 2016/10/3.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 */

/**
 * 注：实体类T的构造不能带参数,如果有带，需要自己修改代码
 * 这里封装的方法满足大部分开发者需求，但毕竟不是万能的，如果你有特殊需求，可自己添加封装
 *
 * @param <T>
 */
public class BaseDao<T> {

    private SQLiteDatabase db;
    private Class<?> beanClass;

    //关系映射配置文件
    private Orm orm;

    public Orm getOrm() {
        return orm;
    }

    /**
     * @param helper
     */
    public BaseDao(BaseDatabaseHelper helper, Class<T> beanClass) {
        this.db = helper.getWritableDatabase();
        this.beanClass = beanClass;
        //根据文件名获取对应orm
        this.orm = DBConfig.mapping.get(beanClass.getSimpleName() + ".orm.xml");
    }
    private  Exception e;
    public long insert(List<T> tList){
        long length = -1;
        if (db != null) {
            db.beginTransaction();
            try {
                for (int i = 0;i < tList.size();i++){
                    ContentValues values = putValues(tList.get(i));
                    length = db.insert(orm.getTableName(), null, values);
                    if (length == -1){
                        throw e;
                    }
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
        return length;
    }
    public long insert(T t) {
        long length = -1;
        if (db != null) {
            db.beginTransaction();
            try {
                ContentValues values = putValues(t);
                length = db.insert(orm.getTableName(), null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
        return length;
    }

    /**
     * @return
     * @throws Exception
     */
    public List<T> selectAll() throws Exception {
        List<T> list = null;
        //getSimpleName()返回的是类的简称
        Cursor cursor = db.query(orm.getTableName(), null, null, null, null, null, null);
        list = getCusor(cursor);
        return list;
    }

    /**
     * @param cursor
     * @return
     * @throws Exception
     */
    private List<T> getCusor(Cursor cursor) throws Exception {
        List<T> list = new ArrayList<T>();
        //获取t对应orm的key对象
        Key key = orm.getKey();
        int index;
        Method get;
        Field field;
        Object value;
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //获取T的实例对象
                T t = (T) beanClass.newInstance();
                if (key != null) {
                    //获取字段所在cursor的索引，不返回=-1
                    index = cursor.getColumnIndex(key.getColumn());
                    //给Key字段传值，获取cursor取值的方法
                    get = cursor.getClass().getMethod(DBConfig.methodMaps.get(key.getType()), int.class);
                    //获取实体类的成员变量
                    field = beanClass.getDeclaredField(key.getProperty());
                    field.setAccessible(true);
                    value = get.invoke(cursor, index);
                    //给成员变量赋值
                    field.set(t, value);
                }
                for (Item item : orm.getItems()) {
                    index = cursor.getColumnIndex(item.getColumn());
                    get = cursor.getClass().getMethod(DBConfig.methodMaps.get(item.getType()), int.class);
                    field = beanClass.getDeclaredField(item.getProperty());
                    field.setAccessible(true);
                    value = get.invoke(cursor, index);
                    field.set(t, value);
                }
                list.add(t);
            }
        }
        cursor.close();
        return list;
    }

    /**
     * 复杂条件查询
     *
     * @param colums        所需查询字段列名
     * @param selection     条件字段，这里可以通过and连接多个条件字段,？为占位符 ,+ 比较字符
     * @param selectionArgs 字段条件,一定要相对应
     * @param groupBy       分组字段
     * @param having        聚合函数
     * @param orderBy       排序字段
     * @return
     */
    public List<T> select(String[] colums, String selection, String[] selectionArgs,
                          String groupBy, String having, String orderBy) throws Exception {
        List<T> list = null;
        //获得配置文件信息
        Cursor cursor = db.query(orm.getTableName(), colums, selection, selectionArgs, groupBy, having, orderBy);
        if (cursor != null) {
            list = getCusor(cursor);
        }
        return list;
    }

    /**简单条件查询
     * @param colums
     * @param selection
     * @return
     */
    public List<T> select(String[] colums, String selection, String[] selectionArgs) throws Exception {
        return select(colums, selection, selectionArgs, null, null, null);
    }

    /**
     * 删除数据
     * 这里的用户表名直接从配置文件中获取
     *
     * @param whereClause 条件字段
     * @param whereArgs   字段条件
     * @return
     */
    public int delete(String whereClause, String[] whereArgs) {
        int result = 0;
        db.beginTransaction();
        try {
            result = db.delete(orm.getTableName(), whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return result;
    }

    /**
     * 修改
     *
     * @param t
     * @param whereClause 条件字段
     * @param whereArgs   字段条件
     * @return
     */
    public int update(T t, String whereClause, String[] whereArgs) {
        int result = -1;
        db.beginTransaction();
        try {
            ContentValues values = putValues(t);
            result = db.update(orm.getTableName(), values, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return result;
    }

    private ContentValues putValues(T t) throws Exception {
        //因为无法确定values.put()参数类型，这里用到反射调用values的put方法
        ContentValues values = new ContentValues();
        Key key = orm.getKey();
        //判断key时候为自增长，否才进行赋值
        if (!key.isIdentity()) {
            //通过反射获取ContentValues的put方法
            Method put = values.getClass().getDeclaredMethod("put",
                    new Class[]{String.class, Class.forName(key.getType())});
            //获取实体类的成员变量
            Field field = beanClass.getDeclaredField(key.getProperty());
            //设置不做权限(private、protect等都可以取到值)检查
            field.setAccessible(true);
            //获取成员变量的值
            Object value = field.get(t);
            put.invoke(values, key.getColumn(), value);
        }
        for (Item item : orm.getItems()) {
            Method put = values.getClass().getDeclaredMethod("put",
                    new Class[]{String.class, Class.forName(item.getType())});
            Field field = beanClass.getDeclaredField(item.getProperty());
            field.setAccessible(true);
            Object value = field.get(t);
            put.invoke(values, item.getColumn(), value);
        }
        return values;
    }
    public void execSQL(String sql){
        db.execSQL(sql);
    }
    //关闭数据库
    public void dbClose() {
        if (db != null) {
            if (db.isOpen()) {
                db.close();
            }
        }
    }
}
