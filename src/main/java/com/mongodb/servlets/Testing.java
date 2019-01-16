package com.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.encrypt.MongoAPI;
import com.mongodb.encrypt.MySQLAPI;

/**
 * Servlet implementation class Testing
 */
public class Testing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       static String appID="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Testing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	
		String encColl = request.getParameter("encColl").trim()+"";
	
		if(encColl.trim().equalsIgnoreCase("1")){
			String host = request.getParameter("host").trim();
			int port = Integer.parseInt(request.getParameter("port").trim());
			String db = request.getParameter("db").trim();
			String user = request.getParameter("user").trim();
			String pwd = request.getParameter("pwd").trim();
			String appId = request.getParameter("appid").trim();
			appID=appId ;
		MongoAPI mongoAPI = new MongoAPI();
		String html = mongoAPI.mongoapi(host, port, user, pwd, db, appId);
		response.getWriter().write("success@"+html);
		}
		
		if(encColl.length()>=5){
			int skip = Integer.parseInt(request.getParameter("skip").trim());
			int limit = Integer.parseInt(request.getParameter("limit").trim());
			MongoAPI mongoAPI = new MongoAPI();
			String html = mongoAPI.mongoapi(encColl,skip,limit);
			response.getWriter().write(html);
		}
		
		
		if(encColl.trim().equalsIgnoreCase("1000")){
			String table = request.getParameter("table").trim();
			String fullObj = request.getParameter("findObj").trim();
			
			if(!fullObj.equalsIgnoreCase("") && !fullObj.equalsIgnoreCase(null)){
				MongoAPI mongoAPI = new MongoAPI();
				String html = mongoAPI.findMongoObj(table, fullObj);
				response.getWriter().write(html);
			}
		    
		   
		}
		
	}
	


}
