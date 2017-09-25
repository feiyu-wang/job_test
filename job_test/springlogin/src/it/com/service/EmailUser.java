package it.com.service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Repository;



@Repository
public class EmailUser {
	 static
	    {
	    	//��ȡ�����ļ��Ĳ���
	    	Properties prop = new Properties();
	    	try {
				prop.load(EmailUser.class.getClassLoader().getResourceAsStream("email.properties"));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	myEmailAccount = prop.getProperty("myEmailAccount");
	    	myEmailPassword = prop.getProperty("myEmailPassword");
	    	myEmailSMTPHost = prop.getProperty("myEmailSMTPHost");
	    }
	    public static String myEmailAccount ;
	    public static String myEmailPassword;
//	  public static String myEmailAccount = "279656813@qq.com";
//	    public static String myEmailPassword = "gofbgrxommkxbhaj";
	    // fqtnexapzqscbbhg	    
	    //imap  irikmebioiocbfbc
	    // ����������� SMTP ��������ַ, ����׼ȷ, ��ͬ�ʼ���������ַ��ͬ, һ��(ֻ��һ��, ���Ǿ���)��ʽΪ: smtp.xxx.com
	    // ����163����� SMTP ��������ַΪ: smtp.163.com
	    public static String myEmailSMTPHost;
	    // �ռ������䣨�滻Ϊ�Լ�֪������Ч���䣩
	    public static String  key=null;
	    

	    public String getKey() {
			return key;
		}


		public void setKey(String key) {
			this.key = key;
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

		public String  activationCode(){
	    	System.out.println(md5("ab45c9"));
	    	return "aaaaaaaaa";
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    public String  smtpToEmail(String receiveMailAccount){
	        // 1. ������������, ���������ʼ��������Ĳ�������
	        Properties props = new Properties();                    // ��������
	        props.setProperty("mail.transport.protocol", "smtp");   // ʹ�õ�Э�飨JavaMail�淶Ҫ��
	        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // �����˵������ SMTP ��������ַ
	        props.setProperty("mail.smtp.auth", "true");            // ��Ҫ������֤
	        
	        // PS: ĳЩ���������Ҫ�� SMTP ������Ҫʹ�� SSL ��ȫ��֤ (Ϊ����߰�ȫ��, ����֧��SSL����, Ҳ�����Լ�����),
	        //     ����޷������ʼ�������, ��ϸ�鿴����̨��ӡ�� log, ����������� ������ʧ��, Ҫ�� SSL ��ȫ���ӡ� �ȴ���,
	        //     ������ /* ... */ ֮���ע�ʹ���, ���� SSL ��ȫ���ӡ�
	        /*
	        // SMTP �������Ķ˿� (�� SSL ���ӵĶ˿�һ��Ĭ��Ϊ 25, ���Բ����, ��������� SSL ����,
	        //                  ��Ҫ��Ϊ��Ӧ����� SMTP �������Ķ˿�, ����ɲ鿴��Ӧ�������İ���,
	        //                  QQ�����SMTP(SLL)�˿�Ϊ465��587, ������������ȥ�鿴)
	         * 
	         */
	        final String smtpPort = "465";
	        props.setProperty("mail.smtp.port", smtpPort);
	        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
	        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
	        
             System.out.println(myEmailAccount);
             System.out.println(myEmailPassword);
             System.out.println(myEmailSMTPHost);
	        // 2. �������ô����Ự����, ���ں��ʼ�����������
	        Session session = Session.getDefaultInstance(props);
	        session.setDebug(true);                                 // ����Ϊdebugģʽ, ���Բ鿴��ϸ�ķ��� log
	        // 3. ����һ���ʼ�
	        MimeMessage message=null;
			try {
				message = createMimeMessage(session, myEmailAccount, receiveMailAccount);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // 4. ���� Session ��ȡ�ʼ��������
	        Transport transport = null;
			try {
				transport = session.getTransport();
			} catch (NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // 5. ʹ�� �����˺� �� ���� �����ʼ�������, ������֤����������� message �еķ���������һ��, ���򱨴�
	        // 
	        //    PS_01: �ɰܵ��жϹؼ��ڴ�һ��, ������ӷ�����ʧ��, �����ڿ���̨�����Ӧʧ��ԭ��� log,
	        //           ��ϸ�鿴ʧ��ԭ��, ��Щ����������᷵�ش������鿴�������͵�����, ���ݸ����Ĵ���
	        //           ���͵���Ӧ�ʼ��������İ�����վ�ϲ鿴����ʧ��ԭ��
	        //
	        //    PS_02: ����ʧ�ܵ�ԭ��ͨ��Ϊ���¼���, ��ϸ������:
	        //           (1) ����û�п��� SMTP ����;
	        //           (2) �����������, ����ĳЩ���俪���˶�������;
	        //           (3) ���������Ҫ�����Ҫʹ�� SSL ��ȫ����;
	        //           (4) �������Ƶ��������ԭ��, ���ʼ��������ܾ�����;
	        //           (5) ������ϼ��㶼ȷ������, ���ʼ���������վ���Ұ�����
	        //
	        //    PS_03: ��ϸ��log, ���濴log, ����log, ����ԭ����log��˵����
			
				try {
					transport.connect(myEmailAccount, myEmailPassword);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

	        // 6. �����ʼ�, �������е��ռ���ַ, message.getAllRecipients() ��ȡ�������ڴ����ʼ�����ʱ��ӵ������ռ���, ������, ������
	        try {
				transport.sendMessage(message, message.getAllRecipients());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // 7. �ر�����
	        try {
				transport.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return  key;
	    }

	    /**
	     * ����һ��ֻ�����ı��ļ��ʼ�
	     *
	     * @param session �ͷ����������ĻỰ
	     * @param sendMail ����������
	     * @param receiveMail �ռ�������
	     * @return
	     * @throws Exception
	     */
	    public  MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
	        // 1. ����һ���ʼ�
	        MimeMessage message = new MimeMessage(session);

	        // 2. From: ������
	        message.setFrom(new InternetAddress(sendMail, "ĳ����", "UTF-8"));

	        // 3. To: �ռ��ˣ��������Ӷ���ռ��ˡ����͡����ͣ�
	        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX�û�", "UTF-8"));

	        // 4. Subject: �ʼ�����
	        message.setSubject("������ԭ", "UTF-8");
	        //�õ�������
	        key=getKey();
	        System.out.println("=====1="+key);
	        // 5. Content: �ʼ����ģ�����ʹ��html��ǩ��
	        message.setContent("<a href='http://localhost:8080/springlogin/emailyanzheng.action?key="+key+"'>�������</a>", "text/html;charset=UTF-8");

	        // 6. ���÷���ʱ��
	        message.setSentDate(new Date());

	        // 7. ��������
	        message.saveChanges();

	        return message;
	    }
	    public static void main(String[] args) {
	    	EmailUser m=new EmailUser();
	    	//m.activationCode();
	    	System.out.println("*"+m.smtpToEmail("994415585@qq.com"));
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//m.activationCode();
		}
	  
}
