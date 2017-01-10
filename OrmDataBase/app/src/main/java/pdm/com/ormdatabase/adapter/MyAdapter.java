package pdm.com.ormdatabase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pdm.com.ormdatabase.R;
import pdm.com.ormdatabase.bean.Student;

/**
 * Created by pdm on 2016/10/3 12:03.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Student> list;

    public MyAdapter(Context context, List<Student> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.dblist_item,null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_class = (TextView)convertView.findViewById(R.id.tv_class);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_name.setText(list.get(position).getStuName());
        holder.tv_class.setText(list.get(position).getStuClass());
        return convertView;
    }
    private class ViewHolder{
        private TextView tv_name,tv_class;
    }
}
