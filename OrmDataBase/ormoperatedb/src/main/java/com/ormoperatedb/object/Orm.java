package com.ormoperatedb.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pdm on 2016/10/3.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 */
public class Orm {
	private String tableName;
	private String beanName;
	private String daoName;
	private Key key;
	private List<Item> items = new ArrayList<Item>();
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getDaoName() {
		return daoName;
	}
	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "Orm [tableName=" + tableName + ", beanName=" + beanName
				+ ", daoName=" + daoName + ", key=" + key + ", items=" + items
				+ "]";
	}
	
	
}
