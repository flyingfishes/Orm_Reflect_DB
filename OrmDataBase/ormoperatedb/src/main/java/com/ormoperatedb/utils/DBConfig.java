package com.ormoperatedb.utils;

import android.util.Xml;

import com.ormoperatedb.object.Item;
import com.ormoperatedb.object.Key;
import com.ormoperatedb.object.Orm;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pdm on 2016/10/3.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 */
public class DBConfig {
	//这里用于存放所有的Orm集合
	public static Map<String,Orm> mapping = new HashMap<String, Orm>();
	//这里用于反射cursor取值的方法
	public static Map<String,String> methodMaps = new HashMap<String,String>();
	static{
		methodMaps.put("java.lang.Integer","getInt");
		methodMaps.put("java.lang.String","getString");
		methodMaps.put("java.lang.Float","getFloat");
		methodMaps.put("java.lang.Double","getDouble");
		methodMaps.put("java.lang.Long","getLong");
		methodMaps.put("java.lang.Short","getShort");
	}

	/**
	 * @param is
	 * @return
	 * @throws Exception
     */
	public static Orm parse(InputStream is) throws Exception {
		Orm orm = null;
		//初始化XML解析器
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is,"UTF-8");
		//获取事件类型
		int eventType = parser.getEventType();
		//判断文档遍历是否结束
		while (eventType!=XmlPullParser.END_DOCUMENT){
			//获取标签名
			String tagName = parser.getName();
			switch (eventType){
				//判断标签头
				case XmlPullParser.START_TAG:
					if ("orm".equals(tagName)){
						orm = new Orm();
						//这里的"tablename"是XML的属性名
						orm.setTableName(parser.getAttributeValue(null,"tablename"));
						orm.setBeanName(parser.getAttributeValue(null,"beanName"));
						orm.setDaoName(parser.getAttributeValue(null,"daoName"));
					}else if ("key".equals(tagName)){
						Key key = new Key();
						key.setColumn(parser.getAttributeValue(null,"column"));
						key.setProperty(parser.getAttributeValue(null,"property"));
						key.setType(parser.getAttributeValue(null,"type"));
						key.setIdentity(Boolean.parseBoolean(parser.getAttributeValue(null,"identity")));
						orm.setKey(key);
					}else if ("item".equals(tagName)){
						Item item = new Item();
						item.setColumn(parser.getAttributeValue(null,"column"));
						item.setProperty(parser.getAttributeValue(null,"property"));
						item.setType(parser.getAttributeValue(null,"type"));
						orm.getItems().add(item);
					}
					break;
			}
			//下移
			eventType = parser.next();
		}
		return orm;
	}

}
