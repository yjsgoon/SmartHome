package web_project.controller;

//로그인 컨트롤러
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web_project.home.LedBean;
import web_project.home.LedDAO;
import web_project.home.RoomBean;
import web_project.home.RoomDAO;
import web_project.home.SensorBean;
import web_project.home.SensorDAO;


@WebServlet("/kitchen")

public class kitchenController extends HttpServlet {
	byte[] sendingData = new byte[]{0,0,0};
	
	public kitchenController() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  request.setCharacterEncoding("UTF-8");
		  response.setContentType("text/html");
		  

		  RoomDAO roomDao = new RoomDAO();
		  RoomBean roomBean = new RoomBean();
		  SensorDAO sensorDao = new SensorDAO();
		  SensorBean sensorBean = new SensorBean();
		  SensorBean sensor3 = new SensorBean();
		  SensorBean sensor4 = new SensorBean();
		  LedDAO ledDao = new LedDAO();
		  LedBean ledBean = new LedBean();

		  // 리빙룸이 1번
		  roomBean = roomDao.getDB(1);
		  sensorBean = sensorDao.getDB(roomBean.getR_sensor());
		  sensor3 = sensorDao.getDB(3);
		  sensor4 = sensorDao.getDB(4);
		  ledBean = ledDao.getDB(5);
		  
		  String action = request.getParameter("action");
		  String s_id = request.getParameter("s_id");
		  String sensor = request.getParameter("sensor");
		  String state = request.getParameter("state");
		  
		  if(action != null && action.equals("update")) {
			  // update
			  if(sensor.equals("led")) {
				  // led 변경
				  ledBean.setR_state(Integer.parseInt(state));
				  sendingData[0]=2;
				  sendingData[1]=5;
				  sendingData[2]=(byte) ledBean.getR_state();
				  ledBean.setR_aid(sendingData[0]);
				  ledBean.setR_led(sendingData[1]);
				  ledBean.setR_name("부엌");
				  System.out.println(Integer.parseInt(state));
				  ledDao.updateDB(ledBean);
				  Controller.tcp2.sendToClient(sendingData);
			  }
			  
			  /*
			   *  가스센서, 화염센서
			   */
			  
			  sensorDao.updateDB(sensorBean);
		  }
		  request.setAttribute("ledBean", ledBean);
		  request.setAttribute("sensorBean", sensorBean);
		  request.setAttribute("sensorBean3", sensor3);
		  request.setAttribute("sensorBean4", sensor4);
		  RequestDispatcher requestDispatcher = request.getRequestDispatcher("home/kitchen.jsp"); 
		  requestDispatcher.forward(request, response);
	}
}