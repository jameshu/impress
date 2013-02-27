package com.snda.youni.taskweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.snda.iyouni.icommon.context.AppSettings;
import com.snda.iyouni.icommon.spring.BeanLocator;
import com.snda.youni.taskweb.beans.UserObject;
import com.snda.youni.taskweb.daos.UserDAO;
import com.snda.youni.taskweb.managers.UserManager;


@Controller
@RequestMapping("/passport")
public class PassportController {

	@RequestMapping(value="/login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response){
	
		String ticket = request.getParameter("Ticket");
		if(ticket==null || ticket.equals("")){
			return new ModelAndView("login");
		}
		
		String uri = request.getParameter("uri");
		
		String soapRequestData = ""
				+ "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">"
				+ "  <soap:Body>"
				+ "   <Validate xmlns=\"http://uam.sdo.com/\">"
				+ "    <ticket>"+ticket+"</ticket>"
				+ "    <subSystemCode>2097</subSystemCode>"
				+ "   </Validate>" 
				+ "  </soap:Body>"
				+ "</soap:Envelope>";
		
		try{
			URL u = new URL("http://uam.corp.snda.com/Service/Privilege.asmx?WSDL");
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true);
			uc.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
			PrintWriter pw = new PrintWriter(uc.getOutputStream());
			pw.println(soapRequestData);
			pw.close();
			
			DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = bf.newDocumentBuilder();
			Document document = db.parse(uc.getInputStream());
			
			String res = document.getElementsByTagName("ValidateResponse").item(0).getTextContent();
			Node ValidateResultNode = document.getElementsByTagName("ExecuteStatu").item(0);
			
			String user_domainaccount = "";
			String user_name = "";
			Node dataNode = document.getElementsByTagName("Data").item(0);
			if(dataNode!=null){
				NodeList list = dataNode.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					Node tmpNode = list.item(i);
					if(tmpNode.getNodeName().equals("DomainAccount")){
						user_domainaccount = tmpNode.getTextContent();
					}
					if(tmpNode.getNodeName().equals("UserName")){
						user_name = tmpNode.getTextContent();
					}
				}
			}
			
			/*
			//<?xml version="1.0" encoding="utf-8"?>
			<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><soap:Body>
			<ValidateResponse xmlns="http://uam.sdo.com/">
			<ValidateResult><ExecuteStatu>0</ExecuteStatu>
			<Message></Message>
			<Data>
			<DomainAccount>huchangcheng</DomainAccount>
			<UserID>SNDA-huchangcheng</UserID>
			<UserSourceID>011805</UserSourceID>
			<UserName></UserName><IP>10.241.60.19</IP><TimeStamp>2013-02-17T16:32:35.836509+08:00</TimeStamp>
			<UserType>0</UserType><FullDomainAccount>SNDA\huchangcheng</FullDomainAccount>
			</Data></ValidateResult></ValidateResponse></soap:Body></soap:Envelope>
			*/
			//
			//0huchangchengSNDA-huchangcheng01180510.241.60.192013-02-17T16:32:35.836509+08:000SNDA\huchangcheng
			//UAM2-SSO=UserID=huchangcheng&UserType=0&LoginTime=2013-2-17 16:32:36&RememberPwd=1&Password=#shadow#DvCPrtby2Ik06WSm6znmv12OBCeqs+xqbzVmgSS+1EQYqNt8N+JFHg==&ShadowPassword=#********#
			
			//store user info default
			//UserManager um = BeanLocator.getBean("userManager");
			UserDAO userDAO = BeanLocator.getBean("userDAO");
			UserObject user = new UserObject();
			user.setLogin(user_domainaccount);
			user.setName(user_name);
			user.setEmail(user_domainaccount+"@snda.com");
			userDAO.save(user);
			
			UserObject currentUser = userDAO.findUserByLogin(user.getLogin());
			
			//set cookie
			Cookie cookie = new Cookie(AppSettings.getInstance().getString("user.cookie.name", "taskTodo_USER"),currentUser.getId()+":"+user_domainaccount);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			response.addCookie(cookie);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			response.sendRedirect(uri);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
