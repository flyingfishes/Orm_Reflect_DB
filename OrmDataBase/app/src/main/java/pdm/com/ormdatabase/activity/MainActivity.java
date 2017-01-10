package pdm.com.ormdatabase.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ormoperatedb.object.Orm;

import java.util.List;

import pdm.com.ormdatabase.R;
import pdm.com.ormdatabase.adapter.MyAdapter;
import pdm.com.ormdatabase.bean.Student;
import pdm.com.ormdatabase.dao.StudentDao;
import pdm.com.ormdatabase.db.DBHelper;

/**
 * Created by pdm on 2016/10/3.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 * 注:这里model部分不做封装，主要阐述 ORM + Reflect 实现数据操作解耦，同时也方便了数据操作的使用
 * 希望各位多提修改意见：aiyh0202@163.com
 * 如需转载，请注明出处，尊重劳动成果!!!
 */
public class MainActivity  extends AppCompatActivity {
    private Toolbar toolbar;
    private String TAG = "MainActivity";
    private Orm orm;
    private ListView lv_db;
    private List<Student> list;
    private MyAdapter adapter;
    private StudentDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化dao对象
        dao = new StudentDao(DBHelper.getInstance(this));
        Student student = new Student();
        list = null;
        //获取dao对应的对象映射对象
        orm = dao.getOrm();
        try {
            dao.insert(student);
            student = new Student();
            String where = orm.getKey().getColumn() + "=?";
            String[] args = new String[]{"1"};
            dao.update(student, where, args);
            list = dao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        lv_db = (ListView) findViewById(R.id.lv_db);
        adapter = new MyAdapter(this,list);
        lv_db.setAdapter(adapter);
        lv_db.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showpopup(position);
                Log.e(TAG, "编号=   "  +position + "总数为=  "  + list.size());
                return true;
            }
        });

    }
    private void showpopup(final int position){
        new AlertDialog.Builder(this).setTitle("是否删除").setNeutralButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String where = orm.getKey().getColumn() + "=?";
                String[] args = new String[]{list.get(position).getStuId()+""};
                dao.delete(where,args);
                list.remove(position);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(MainActivity.this,list.size()+"条数据",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
    //插入数据
    public void insert(View v){
        Student student = new Student();
        dao.insert(student);
        list.add(student);
        adapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this,list.size()+"条数据",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        //关闭数据库
        if (dao != null){
            dao.dbClose();
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
