package web_project.controller;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web_project.home.RoomBean;
import web_project.home.RoomDAO;

@WebServlet("/homeController")

public class homeController extends HttpServlet {
	
    
    public homeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher requestDispatcher;
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html"); 
		
		// DB ���� �������� �о�´�. 
		RoomDAO roomDAO = new RoomDAO();
		RoomBean living_room = new RoomBean();
		RoomBean wash_room = new RoomBean();
		RoomBean main_room = new RoomBean();
		
		living_room = roomDAO.getDB(1);
		main_room = roomDAO.getDB(2);
		wash_room = roomDAO.getDB(3);
		
		// �� ������ setattribute ���ְ� jsp���� ����� �����̴�.
		request.setAttribute("link", living_room);
		request.setAttribute("link", main_room);
		request.setAttribute("link", wash_room); 
		requestDispatcher = request.getRequestDispatcher("/home/home.jsp");
		requestDispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
