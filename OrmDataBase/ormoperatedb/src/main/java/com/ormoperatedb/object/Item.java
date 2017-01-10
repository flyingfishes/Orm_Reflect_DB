package com.ormoperatedb.object;

/**
 * Created by pdm on 2016/10/3.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 */
public class Item {
	/**
	 * <item column="stu_classname" 
	 * property="stuClass" 
	 * type="java.lang.String"></item>
	 */

	private String column;
	private String property;
	private String type;
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Item [column=" + column + ", property=" + property + ", type="
				+ type + "]";
	}
	
	
}
