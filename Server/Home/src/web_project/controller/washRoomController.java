package web_project.controller;

//로그인 컨트롤러
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web_project.home.*;


@WebServlet("/wash_room")

public class washRoomController extends HttpServlet {
	byte[] sendingData = new byte[]{0,0,0};
	
    public washRoomController() {
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
		  LedDAO ledDao = new LedDAO();
		  LedBean ledBean = new LedBean();

		  // 리빙룸이 1번
		  roomBean = roomDao.getDB(1);
		  sensorBean = sensorDao.getDB(roomBean.getR_sensor());
		  ledBean = ledDao.getDB(2);
		  
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
				  sendingData[1]=2;
				  sendingData[2]=(byte) ledBean.getR_state();
				  Controller.tcp2.sendToClient(sendingData);
				  sendingData[2]=(byte) ledBean.getR_state();
				  ledBean.setR_aid(sendingData[0]);
				  ledBean.setR_led(sendingData[1]);
				  ledBean.setR_name("화장실");
				  System.out.println(Integer.parseInt(state));
				  ledDao.updateDB(ledBean);
				  Controller.tcp2.sendToClient(sendingData);
			  } else {
				  // 커튼 변경
				  //sensorBean.setS_ventilator(Integer.parseInt(state));
			  }
			  sensorDao.updateDB(sensorBean);
		  }
		  request.setAttribute("ledBean", ledBean);
		  request.setAttribute("sensorBean", sensorBean);
		  RequestDispatcher requestDispatcher = request.getRequestDispatcher("home/washRoom.jsp"); 
		  requestDispatcher.forward(request, response);
	}
}
