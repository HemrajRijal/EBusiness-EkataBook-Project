package com.mvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mvc.dao.EkataDAOImpl;
import com.mvc.model.BookInfo;
import com.mvc.model.UserInfo;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    EkataDAOImpl daoimpl;
    HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        daoimpl=new EkataDAOImpl();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	try
	{
		System.out.println("Inside LoginController GET");
		String view_name="";
	String logindata=request.getParameter("logindata");
	
	if(logindata != null)
	{
		System.out.println("JSON data="+logindata);
		Gson gson=new GsonBuilder().create();
		UserInfo u=gson.fromJson(logindata, UserInfo.class);
		boolean loginstatus=daoimpl.verifyAdmin(u);
		if(loginstatus)
		{
		  
		  response.getWriter().write("true");
		  session=request.getSession(true);
		  session.setAttribute("userid", u.getUserid());
		  session.setAttribute("username", u.getUsername());
		}
		else
		{
			response.getWriter().write("false");
		}
	}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
