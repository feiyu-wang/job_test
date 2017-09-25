package it.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.SessionScope;

@Repository
public class StaticticDao {
@Resource
SqlSessionFactory sf;

public SqlSessionFactory getSf() {
	return sf;
}

public void setSf(SqlSessionFactory sf) {
	this.sf = sf;
}
	//��ʼ��������ҳtotal���ܸ���
	public List<Map> findtotal() {
		SqlSession session=sf.openSession();
		List<Map> list=null;
		list=session.selectList("Statictic.findtotal");
		session.commit();
		session.close();
		return list;
	}
	/*
	 * ���������ύ�ɼ�����������Ϣ
	 * ����score��Ϊnull
	 */
	public List<Map> selectInterview(int begin,int size){
		SqlSession session = sf.openSession();
		List<Map> list=null;
		Map map = new HashMap();
		map.put("begin", begin);
		map.put("size", size);
		list=session.selectList("Statictic.selectInterview",map);
		session.commit();
		session.close();
		return list;
	}
	 /*
	  * ����������������Ϣ,���ڵ���鿴
	  */
	public List<Map> selectAllInterview() {
		SqlSession session=sf.openSession();
		List<Map> list = null;
		list=session.selectList("Statictic.selectAllInterview");
		session.commit();
		session.close();
		return list;
	}
	
	/*
	 * ��̬sql����һ���԰�
	 * ��ѯ score��position��usersource��ѡ��
	 * ����ѡ��ĳһ�����������null����
	 */
	public List<Map> selectAll(int score,String position,String usersource,String username){
		SqlSession session=sf.openSession();
		List<Map> list = null;
		Map map = new HashMap();
		map.put("score", score);
		map.put("position", position);
		map.put("usersource", usersource);
		map.put("username", username);
		list=session.selectList("Statictic.selectAll",map);
		session.commit();
		session.close();
		return list;
	}
	/*
	 * �������������е���Դ,ȥ�غ�
	 */
	public List<Map> findAllSource(){
		SqlSession session=sf.openSession();
		List<Map> list=null;
		list=session.selectList("Statictic.selectBySource");
		session.commit();
		session.close();
		return list;
	}
	/*
	 * �������е�רҵ��ȥ�غ�
	 */
	public List<Map> findAllPosition(){
		SqlSession session = sf.openSession();
		List<Map> list = null;
		list=session.selectList("Statictic.selectAllPosition");
		session.commit();
		session.close();
		return list;
	}

}
