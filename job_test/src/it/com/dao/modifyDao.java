package it.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;



/*=============================�������޸�dao========================*/
@Repository
public class modifyDao {
@Resource
SqlSessionFactory sf;

public SqlSessionFactory getSf() {
	return sf;
}

public void setSf(SqlSessionFactory sf) {
	this.sf = sf;
}				
		//��ѯ����רҵ
		public List<Map> FindAllPos() {
		   SqlSession session=sf.openSession();
		   List list=session.selectList("selectAllPos");
		   session.commit();
		   session.close();
		   return list;
		}
		//��ѯ������������
		public List<Map> FindAllQusetionType() {
		   SqlSession session=sf.openSession();
		   List list=session.selectList("selectAlltype");
		   session.commit();
		   session.close();
		   return list;
		}
		//��ѯ������������
		public List<Map> FindAllQusetionCountByType() {
		   SqlSession session=sf.openSession();
		   List list=session.selectList("selectAllCountByType");
		   session.commit();
		   session.close();
		   return list;
		}
}
