package web_project.controller;

//濡쒓렇�씤 而⑦듃濡ㅻ윭
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Login")

public class loginController extends HttpServlet {
    public loginController() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  request.setCharacterEncoding("UTF-8");
		  response.setContentType("text/html");
		  System.out.println("login");
		  RequestDispatcher requestDispatcher = request.getRequestDispatcher("login/login.jsp"); 
		  requestDispatcher.forward(request, response);
	}
}
