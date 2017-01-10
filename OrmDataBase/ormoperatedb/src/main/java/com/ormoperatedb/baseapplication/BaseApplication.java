package com.ormoperatedb.baseapplication;

import android.app.Application;

import com.ormoperatedb.utils.DBConfig;
import com.ormoperatedb.object.Orm;

/**
 * Created by pdm on 2016/10/3.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 */
public class BaseApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		initOrm();
	}

	public void initOrm(){
		//初始化orm
		//初始化assets目录下面的所有xxx.orm.xml
		try {
			//遍历assets目录，找到根目录所有file
			String[] files = getAssets().list("");
			for (String fileName : files){
				//找到所有以.orm.xml结尾的文件
				if (fileName.endsWith(".orm.xml")){
					Orm orm = DBConfig.parse(getAssets().open(fileName));
					DBConfig.mapping.put(fileName,orm);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
