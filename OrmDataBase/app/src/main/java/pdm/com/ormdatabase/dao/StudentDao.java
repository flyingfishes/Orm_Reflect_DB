package pdm.com.ormdatabase.dao;

import com.ormoperatedb.dao.BaseDao;
import com.ormoperatedb.db.BaseDatabaseHelper;
import com.ormoperatedb.object.Orm;

import pdm.com.ormdatabase.bean.Student;

/**
 * Created by pdm on 2016/10/3.
 * CSDN :http://write.blog.csdn.net/postlist
 * GitHub :https://github.com/flyingfishes
 */
public class StudentDao extends BaseDao<Student> {

	public StudentDao(BaseDatabaseHelper helper) {
		super(helper,Student.class);
		// TODO Auto-generated constructor stub
	}
	public Orm getOrm(){
		return super.getOrm();
	}

}
