package web_project.controller;

//濡쒓렇�씤 而⑦듃濡ㅻ윭
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


@WebServlet("/main_room")

public class mainRoomController extends HttpServlet {
	byte[] sendingData = new byte[]{0,0,0};
	
    public mainRoomController() {
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
		  SensorBean sensor4 = new SensorBean();
		  LedDAO ledDao = new LedDAO();
		  LedBean ledBean = new LedBean();

		  // 由щ튃猷몄씠 1踰�
		  roomBean = roomDao.getDB(1);
		  sensorBean = sensorDao.getDB(roomBean.getR_sensor());
		  sensor4 = sensorDao.getDB(4);
		  ledBean = ledDao.getDB(4);
		  
		  String action = request.getParameter("action");
		  String s_id = request.getParameter("s_id");
		  String sensor = request.getParameter("sensor");
		  String state = request.getParameter("state");
		  
		  if(action != null && action.equals("update")) {
			  // update
			  if(sensor.equals("led")) {
				  // led 蹂�寃�
				  ledBean.setR_state(Integer.parseInt(state));
				  sendingData[0]=2;
				  sendingData[1]=4;
				  sendingData[2]=(byte) ledBean.getR_state();
				  ledBean.setR_aid(sendingData[0]);
				  ledBean.setR_led(sendingData[1]);
				  ledBean.setR_name("큰방");
				  System.out.println(Integer.parseInt(state));
				  ledDao.updateDB(ledBean);
				  Controller.tcp2.sendToClient(sendingData);
			  }/* else if(sensor.equals("vibration")) {
				  // 而ㅽ듉 蹂�寃�
				  sensorBean.setS_vibration(Integer.parseInt(state));
			  } else if(sensor.equals("noise")) {
				  // 而ㅽ듉 蹂�寃�
				  sensorBean.setS_noise(Integer.parseInt(state));
			  }*/ else {
				  //sensorBean.setS_curtain(Integer.parseInt(state));
			  }
			  
			  sensorDao.updateDB(sensorBean);
			  
		  }
		  request.setAttribute("ledBean", ledBean);
		  request.setAttribute("sensorBean", sensorBean);
		  request.setAttribute("sensorBean4", sensor4);
		  RequestDispatcher requestDispatcher = request.getRequestDispatcher("home/mainRoom.jsp"); 
		  requestDispatcher.forward(request, response);
	}
}