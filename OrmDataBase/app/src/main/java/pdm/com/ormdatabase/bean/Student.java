package pdm.com.ormdatabase.bean;

import java.util.Random;

/**
 * Created by pdm on 2016/10/3.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 */
public class Student {
	private Integer stuId;
	private String stuName;
	private String stuClass;
	
	public Student() {
		super();
		stuName = "name_"+ new Random().nextInt(10000);//随机取数
		stuClass="class_"+new Random().nextInt(10000);
		// TODO Auto-generated constructor stub
	}
	public Student(String stuName, String stuClass) {
		super();
		this.stuName = stuName;
		this.stuClass = stuClass;
	}
	public Integer getStuId() {
		return stuId;
	}
	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getStuClass() {
		return stuClass;
	}
	public void setStuClass(String stuClass) {
		this.stuClass = stuClass;
	}
	@Override
	public String toString() {
		return "Student [stuId=" + stuId + ", stuName=" + stuName
				+ ", stuClass=" + stuClass + "]";
	}
	
	
}
