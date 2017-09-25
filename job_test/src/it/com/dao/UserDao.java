package it.com.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;





@Repository(value="ud")
public class UserDao {
@Resource
	SqlSessionFactory sf;
	public SqlSessionFactory getSf() {
		return sf;
	}
	public void setSf(SqlSessionFactory sf) {
		this.sf = sf;
	}
	
/*===========================��¼ע�ᣬ��������==================================================*/
		//��½��֤
		public int login(String name, String pass){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.login(name);
			int n=0;//�û���������
			if(list.size()!=0){
					String up=((Map)list.get(0)).get("pass").toString();
				    if(pass.equals(up)){
				    	n=1;//��½�ɹ�
				    }else{
				    	n=2; //�������
				    }
				}
			session.commit();
			session.close();
			return n;
		}
		//��֤�û��Ƿ����
		public List selectuserOn(String name){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.selectuserOn(name);
			session.commit();
			session.close();
			return list;
		}
		//��ȡ�����ܱ�����
		public List GetQusetion(){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.GetQusetion();
			session.commit();
			session.close();
			return list;
		}	
		//ע�����û�
		public void InsertUser( String name,String pass, String sfzid, String phone,String email,String logo){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.InsertUser(name,pass,sfzid,phone,email,logo);
			session.commit();
			session.close();
		}	
		//ע�����û��ܱ�
		public void InsertSecurity( String answer, int yzid,int nameid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.InsertSecurity(answer,yzid,nameid);
			session.commit();
			session.close();
		}	
		//��ѯ�û���Ϣbyname
		public List<Map> FindMsg( String nameid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.FindMsg(nameid);
			session.commit();
			session.close();
			return list;
		}
		//�����û�id��ѯ�û��ܱ�����
		public List<Map> FindQusetionByNameid( int nameid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.FindQusetionByNameid(nameid);
			session.commit();
			session.close();
			return list;
		}
		//�����û�id��ѯ�û��ܱ�����
		public List<Map> FindAnswerByNameid( int nameid,int yzid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.FindAnswerByNameid(nameid,yzid);
			session.commit();
			session.close();
			return list;
		}
		//UpdateUserMssgByYzid�޸��û���Ϣ
		public void UpdateUserMsgyByYzid(String name,String sfzid,String logo,String phone,String email){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.UpdateUserMsgyByYzid(name,sfzid,logo,phone,email);
			session.commit();
			session.close();
		}
		
/*=================================================ģ��==============================================*/
		//�������ܱ�����
		public void InsertSecurityByAdmin( String security,String yzid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.InsertSecurityByAdmin(security,yzid);
			session.commit();
			session.close();
		}
		
		//ɾ���ܱ�����
		public void DeleteSecurityByYzid(int yzid){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.DeleteSecurityByYzid(yzid);
			session.commit();
			session.close();
		}
		//UpdateSecurityByYzid�޸��ܱ���������
		public void UpdateSecurityByYzid(int yzid,String security,String type){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.UpdateSecurityByYzid(yzid,security,type);
			session.commit();
			session.close();
		}
/*=================================================ģ��==============================================*/		
		
/*===========================�û�����==================================================*/
		//���������û���Ϣ
		public  List<Map> SelectAllUsers(){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list=iud.SelectAllUsers();
			session.commit();
			session.close();
			return list;
		}
		//���������û���Ϣbylimit
		public  List<Map> SelectAllUsersByLimit(int begin,int size){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list=iud.SelectAllUsersByLimit(begin,size);
			session.commit();
			session.close();
			return list;
		}
		public List selectuserid(String phone){
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List list = iud.selectuserid(phone);
			session.commit();
			session.close();
			return list;
		}
		//�����û�����ѯ�ֻ�����
		public String selectphoneByname(String name) {
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List<Map> list =iud.selectphoneByname(name);
			String phone =list.get(0).get("phone").toString();
			session.commit();
			session.close();
			return phone;
		}
		//�����û�����ѯ�����˺�
		public String selectemailByname(String name) {
			SqlSession session=sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			List<Map> list =iud.selectemailByname(name);
			String email =list.get(0).get("email").toString();
			session.commit();
			session.close();
			return email;
		}
		//�һ������޸�ҳ
		public void updatePasswordByname(String pass,String phone,String email,String sfzid,String name) {
			SqlSession session =sf.openSession();
			IusersDao iud =session.getMapper(IusersDao.class);
			iud.updatePasswordByname(pass, phone, email, sfzid, name);
			session.commit();
			session.close();
		}
		
		public String md5(String inputStr){
	    	
			  BigInteger bigInteger=null;//�߾�����������
			try {
				MessageDigest md = MessageDigest.getInstance("md5");//˵����������
				 byte[] inputData = inputStr.getBytes();//�ַ�ת�����ֽ�
			     md.update(inputData); //����
			     bigInteger = new BigInteger(md.digest());//ת���ɸ߾����������� 
			    // System.out.println("MD���ܺ�:" + bigInteger.toString()); //���
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    }
			return bigInteger.toString();
	    }
}
