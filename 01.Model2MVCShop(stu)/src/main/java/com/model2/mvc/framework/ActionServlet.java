package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		mapper=RequestMapping.getInstance(resources);
		System.out.println("resources Connection : "+resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		String url = request.getRequestURI();
		System.out.println("ActionServlet�� url : "+url);
		String contextPath = request.getContextPath();
		System.out.println("ActionServlet�� contextPath : "+contextPath);
		String path = url.substring(contextPath.length());
		System.out.println("ActionServlet�� path : "+path);
		
		try{
			
			Action action = mapper.getAction(path);
			System.out.println("ActionServlet__________path : " + path);
			
			action.setServletContext(getServletContext());
			
			String resultPage = action.execute(request, response);
			System.out.println("ActionServlet__________resultPage : " + resultPage);
			String result = resultPage.substring(resultPage.indexOf(":")+1);
			System.out.println("ActionServlet__________result : " +result +"\n");
			
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			System.out.println("ActionServlet : try End.....");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}