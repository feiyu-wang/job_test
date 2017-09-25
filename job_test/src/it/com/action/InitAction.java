package it.com.action;

import it.com.dao.PowerControl;
import it.com.dao.UserDao;
import net.sf.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
@org.springframework.stereotype.Controller

public class InitAction{
	@Resource(name="ud")
	UserDao ud;
	@Resource
	PowerControl power;
	
	
public PowerControl getPower() {
		return power;
	}

	public void setPower(PowerControl power) {
		this.power = power;
	}

public UserDao getUd() {
		return ud;
	}

	public void setUd(UserDao ud) {
		this.ud = ud;
	}
/*=====================================================================	��¼ע����������=============================================*/
	@RequestMapping(value="login.action")
	public void handleRequest(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
		System.out.println("����server");
		System.out.println("hhhhh");
		String username=arg0.getParameter("username");
		String userpass1=arg0.getParameter("password");
		  BigInteger bigInteger=null;//�߾�����������
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");//˵����������
		 byte[] inputData = userpass1.getBytes();//�ַ�ת�����ֽ�
		 md.update(inputData); //����
		 bigInteger = new BigInteger(md.digest());//ת���ɸ߾����������� 
		 System.out.println("MD���ܺ�:" + bigInteger.toString()); //���
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userpass=bigInteger.toString();
		System.out.println(username+"-------"+userpass);
		int loginNum=ud.login(username,userpass);
		System.out.println(loginNum);
		PrintWriter out=arg1.getWriter();
		out.println(loginNum);
	}
	@RequestMapping(value="getqusetion.action")
	public void getqusetion(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
		List list=ud.GetQusetion();
		PrintWriter out=arg1.getWriter();
		JSONArray jsonArray=JSONArray.fromObject(list);
		out.println(jsonArray);
	}
	@RequestMapping(value="selectuser.action")
	public void selectuser(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
		String name=arg0.getParameter("username");
		List list=ud.selectuserOn(name);
		PrintWriter out=arg1.getWriter();
		if (list.size()!=0) {
			out.println("1");
		}else {
			out.println("2");
		}
	}
	@RequestMapping(value="insert.action")
	public void insertUser(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
		String name=arg0.getParameter("username");
		PrintWriter out=arg1.getWriter();
			String pass1=arg0.getParameter("password");
			  BigInteger bigInteger=null;//�߾�����������
				try {
					MessageDigest md = MessageDigest.getInstance("MD5");//˵����������
					 byte[] inputData = pass1.getBytes();//�ַ�ת�����ֽ�
				     md.update(inputData); //����
				     bigInteger = new BigInteger(md.digest());//ת���ɸ߾����������� 
				     System.out.println("MD���ܺ�:" + bigInteger.toString()); //���
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String pass=bigInteger.toString();
			int qusetion1=Integer.parseInt(arg0.getParameter("qusetion1"));
			String answer1=arg0.getParameter("answer1");
			int qusetion2=Integer.parseInt(arg0.getParameter("qusetion2"));
			String answer2=arg0.getParameter("answer2");
			int qusetion3=Integer.parseInt(arg0.getParameter("qusetion3"));
			String answer3=arg0.getParameter("answer3");
			String phone=arg0.getParameter("phone");
			String email=arg0.getParameter("email");
			String sfzid=arg0.getParameter("sfzid");
			String logo=arg0.getParameter("logoimg");
			System.out.println();
			ud.InsertUser( name,pass,sfzid,phone,email,logo);
			List<Map> list=ud.FindMsg(name);
			int nameid=(int) ud.FindMsg(name).get(0).get("nameid");
			System.out.println(list.get(0).get("nameid"));
			ud.InsertSecurity(answer1,qusetion1,nameid);
			ud.InsertSecurity(answer2,qusetion2,nameid);
			ud.InsertSecurity(answer3,qusetion3,nameid);

		}

	@RequestMapping(value="forgetpass.action")
	public void forgetpass(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
		String name=arg0.getParameter("username");
		int nameid=(int) ud.FindMsg(name).get(0).get("nameid");
		System.out.println(nameid);
		List<Map> list=ud.FindQusetionByNameid(nameid);
		System.out.println(list.size()+"----=========");
		Random randomNum=new Random();
		int num1,num2;
		while (true) {
			num1=randomNum.nextInt(list.size());
			num2=randomNum.nextInt(list.size());
			if(num1!=num2) {
				break;
			}
		}
		System.out.println(num1+"---"+num2);
		System.out.println(list.toString());
		List<Map> newlist=new ArrayList<Map>();
		System.out.println(list.get(num1));
		System.out.println(list.get(num2));
		newlist.add(list.get(num1));
		newlist.add(list.get(num2));
		
		JSONArray jsonArray=JSONArray.fromObject(newlist);
		PrintWriter out=arg1.getWriter();
		out.println(jsonArray);
	}
	@RequestMapping(value="VerificationSecurity.action")
	public void VerificationSecurity(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
			String name=arg0.getParameter("username");
			PrintWriter out=arg1.getWriter();
			int qusetion1=Integer.parseInt(arg0.getParameter("qusetion1"));
			String answer1=arg0.getParameter("answer1");
			int qusetion2=Integer.parseInt(arg0.getParameter("qusetion2"));
			String answer2=arg0.getParameter("answer2");
			int nameid=(int) ud.FindMsg(name).get(0).get("nameid");
			System.out.println(ud.FindAnswerByNameid(nameid,qusetion1).get(0).get("answer")+"---"+answer1+"---"+ud.FindAnswerByNameid(nameid,qusetion2).get(0).get("answer").equals(answer2));
			if (ud.FindAnswerByNameid(nameid,qusetion1).get(0).get("answer").equals(answer1)&&ud.FindAnswerByNameid(nameid,qusetion2).get(0).get("answer").equals(answer2)) {
				out.println(0);
			}else if (ud.FindAnswerByNameid(nameid,qusetion1).get(0).get("answer").equals(answer1)) {
				out.println(1);
			} else if (ud.FindAnswerByNameid(nameid,qusetion2).get(0).get("answer").equals(answer2)) {
				out.println(2);
			} else {
				out.println(3);
			}

		}

/*====================================ģ���ʼ���û���Ϣ================================*/
	/*��¼��ʼ���û���Ϣ*/
	@RequestMapping(value="initUser.action")
	public void initUser(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
			String name=arg0.getParameter("name");
			PrintWriter out=arg1.getWriter();
			List user=ud.FindMsg(name);

			JSONArray jsonArray=JSONArray.fromObject(user);
			out.println(jsonArray);
		}
	/*��¼��ʼ�� �޸��û���Ϣ*/
	@RequestMapping(value="updateUser.action")
	public void updateUser(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
			String name=arg0.getParameter("username");
			String phone=arg0.getParameter("phone");
			String email=arg0.getParameter("email");
			String logo=arg0.getParameter("logo");
			String sfzid=arg0.getParameter("sfzid");
			PrintWriter out=arg1.getWriter();
			ud.UpdateUserMsgyByYzid(name, sfzid, logo, phone, email);
			out.println("success");
		}

/*==========================================	�û�����  ��ʼ�� �� ��ɾ�� =======================================*/
	
	/*��ʼ��ҳ����������û� ����¼��¼�û���Ϣ*/
	@RequestMapping(value="main.action")
	public void initList(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
			int begin=Integer.parseInt(arg0.getParameter("begin"));
			int size=Integer.parseInt(arg0.getParameter("size"));
			int beginNum=(begin-1)*size;
			String name=arg0.getParameter("name");
			
			PrintWriter out=arg1.getWriter();
			List<Map> list=ud.SelectAllUsersByLimit(beginNum, size);
			//��ǰ����Ա���߱���Ȩ��
			List<Map> nameidList=ud.selectuserOn(name);
			int nameid=(int)nameidList.get(0).get("nameid");
			/*System.out.println(nameidList.get(0).get("nameid"));*/
			List<Map> powerNull = power.findpowerNotHave(nameid);
			boolean flag1=true;
			boolean flag2=true;
			boolean flag3=true;
			boolean delete=true;
			for (Map power : powerNull) {
				if(power.get("powerid").toString().equals("1")) {
					flag1=false;
				}
				if(power.get("powerid").toString().equals("2")) {
					flag2=false;
				}
				if(power.get("powerid").toString().equals("3")) {
					flag3=false;
				}
				if(power.get("powerid").toString().equals("4")) {
					delete=false;
				}
			}
			
			for (Map map : list) {
				if(flag1==false) {
					map.put("phone", "******");
				}
				if(flag2==false) {
					map.put("email", "******");
				}
				if(flag3==false) {
					map.put("sfzid", "******");
				}
			}
			int total=ud.SelectAllUsers().size();
			System.out.println(list);
			JSONArray json1=JSONArray.fromObject(list);
			//תjson��ʱ���ַ��������Ŵ���ת������⣬ע��һ��
			out.print("{\"total\":"+total+",\"users\":");
			out.println(json1);
			out.print(",\"delete\":"+delete);
	        out.print("}"); 
		}
	
	//�ֻ��һ�����
	@RequestMapping(value="findphoneByname.action")
	public void findphoneByname(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String name=request.getParameter("username");
	
		String phone=ud.selectphoneByname(name);
		char[] strs =phone.toCharArray();
		StringBuffer phoneshow = new StringBuffer();
		for (int i = 0; i < phone.length(); i++) {
			if(i>=3) {
				strs[i]='*';
			}
			phoneshow.append(strs[i]);
		}
		PrintWriter out = response.getWriter();
		out.print(phoneshow);
	}
	//�����һ�����
	@RequestMapping(value="findmailByname.action")
	public void findmailByname(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String name=request.getParameter("username");
		
		String email=ud.selectemailByname(name);
		char[] strs =email.toCharArray();
		StringBuffer emailshow = new StringBuffer();
		for (int i = 0; i < email.length(); i++) {
			if(i>=3) {
				strs[i]='*';
			}
			emailshow.append(strs[i]);
		}
		PrintWriter out = response.getWriter();
		out.print(emailshow);
	}
	//�һ������޸�ҳ
	@RequestMapping(value="updatePasswordByname.action")
	public void updatePasswordByname(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String name=request.getParameter("username");
		String password=ud.md5(request.getParameter("password"));
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String sfz=request.getParameter("sfz");
		ud.updatePasswordByname(password, phone, email, sfz, name);
		PrintWriter out = response.getWriter();
		out.println("success");
		
	}
	
	
	
/*================================================ģ��action===========================================================*/
	@RequestMapping(value="testinit.action")
	public void innitxx(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
			String name=arg0.getParameter("username");
			PrintWriter out=arg1.getWriter();
			List<Map> list=ud.GetQusetion();
			JSONArray jsonArray=JSONArray.fromObject(list);
			out.println(jsonArray);
		}
	
	@RequestMapping(value="add.action")
	public void addList(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
			String wenti=arg0.getParameter("wenti");
			String type=arg0.getParameter("type");

			ud.InsertSecurityByAdmin(wenti,type);
			PrintWriter out=arg1.getWriter();
			out.println("success");
		}
	@RequestMapping(value="remove.action")
	public void removeListByYzid(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
			int yzid=Integer.parseInt(arg0.getParameter("yzid"));
			System.out.println(yzid);
			ud.DeleteSecurityByYzid(yzid);
			PrintWriter out=arg1.getWriter();
			out.println("success");
	}
	
	@RequestMapping(value="edit.action")
	public void UpdateByYzid(HttpServletRequest arg0,HttpSession session,HttpServletResponse arg1) throws Exception {
			int yzid=Integer.parseInt(arg0.getParameter("yzid"));
			String security=arg0.getParameter("wenti");
			String type=arg0.getParameter("type");
			System.out.println(yzid);
			ud.UpdateSecurityByYzid(yzid, security, type);
			PrintWriter out=arg1.getWriter();
			out.println("success");
	}

}
/*================================================ģ��action===========================================================*/