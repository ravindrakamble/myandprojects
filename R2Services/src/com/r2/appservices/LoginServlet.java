package com.r2.appservices;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r2.appservices.dao.TrackerDAO;
import com.r2.appservices.dao.User;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger("Tracker");
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		
		logger.info("Login request received");
		
		String userName = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    logger.info("Username:" + userName + " Password:" + password);
	    
	    User user = new User();
	    user.setUsername(userName);
	    user.setPassword(password);
	    
	    User loginSuccess = TrackerDAO.INSTANCE.login(user);
	    
	    if(loginSuccess != null){
	    	resp.sendRedirect("/pages/home.jsp");
	    }else{
	    	String message = "Username/Password incorrect, Please try again...";
	    	request.setAttribute("message", message);
	    	request.getRequestDispatcher("/index.jsp").forward(request, resp);
	    }
	}
}
