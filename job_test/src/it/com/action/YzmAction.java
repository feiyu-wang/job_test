package it.com.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class YzmAction {

	@RequestMapping(value="yzm.action")
	public void Yzm(HttpSession session,HttpServletResponse resp,HttpServletRequest request) throws IOException{
	    // ��֤���ַ�����      
	     int codeCount = 4;         
	    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',      
	            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',      
	            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };      
        // ����һ���������������      
        Random random = new Random();      
        // randomCode���ڱ��������������֤�룬�Ա��û���¼�������֤��      
        StringBuffer randomCode = new StringBuffer();
        // �������codeCount���ֵ���֤�롣      
        for (int i = 0; i < codeCount; i++) {      
            // �õ������������֤�����֡�      
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);      
            // ���������ĸ�����������һ��      
            randomCode.append(strRand);      
        }      
        PrintWriter out=resp.getWriter();
        out.println(randomCode.toString());
	}
}
