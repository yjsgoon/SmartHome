package web_project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Queue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web_project.home.AccountBean;
import web_project.home.AccountDAO;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	public static TcpIpServerRunnable tcp1;
	public static TcpIpServerRunnable tcp2;
	public static TcpIpServerRunnable tcp3;
	public static TcpIpServerRunnable tcp4;
	boolean sw = false;
	ArduinoDao arduinoDao = new ArduinoDao();
	SecureVO secureVO = new SecureVO();

	public void init() {
		if (!sw) {
			tcp1 = new TcpIpServerRunnable(9996); // ����
			tcp2 = new TcpIpServerRunnable(9997); // ����
			tcp3 = new TcpIpServerRunnable(9998); // �糭
			tcp4 = new TcpIpServerRunnable(9999); // ����
			System.out.println("���� ���� ����22");
			Thread t1 = new Thread(tcp1);
			Thread t2 = new Thread(tcp2);
			Thread t3 = new Thread(tcp3);
			Thread t4 = new Thread(tcp4);
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			sw = true;
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ServletContext servletContext = getServletContext();
			RequestDispatcher requestDispatcher;
						
			System.out.println("doGet");
			HttpSession session = request.getSession();
			
			request.setAttribute("secureMode", secureVO);
			
			if ("false".equals(request.getParameter("login_check"))) {
				String a_id = request.getParameter("a_id");
				String a_passwd = request.getParameter("a_passwd");

				AccountDAO dao = new AccountDAO();
				AccountBean user = dao.getDB(a_id);

				if (user.getA_id().equals(a_id) && user.getA_passwd().equals(a_passwd))
					session.setAttribute("login", a_id);
			}
			if (session.getAttribute("login") == null) {
				requestDispatcher = servletContext.getRequestDispatcher("/Login");
			} else if (request.getParameter("link") != null) {
				requestDispatcher = servletContext.getRequestDispatcher("/" + request.getParameter("link"));
			} else {
				requestDispatcher = servletContext.getRequestDispatcher("/homeController");
			}
			requestDispatcher.forward(request, response);
			/*
			request.setAttribute("sensor", arduinoDao.selectList());

			RequestDispatcher rd = request.getRequestDispatcher("/Main.jsp");
			rd.forward(request, response);
			*/
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		/*
		String sensorName;
		byte[] socketData;
		byte[] sendData = new byte[] { 2, 0, 0 };
		Queue socketQueue;
		SocketVO socketServer;
		ArrayList<SensorVO> sensor = new ArrayList<SensorVO>();
		ArrayList<SensorVO> test;
		
		try {
			response.setContentType("text/html;charset=UTF-8");

			sensorName = request.getParameter("led");
			socketServer = new SocketVO();
			socketData = socketServer.getInputDatas();// ����������� �޾ƿ� byte�� �迭 ������
			ArduinoDao arduinoDao = new ArduinoDao();

			PrintWriter out = response.getWriter();

			if (sensorName.equals("led1ON")) {
				arduinoDao.LED_ON("LED-1");
				sendData[1] = 1;
				sendData[2] = 1;
				tcp2.sendToClient(sendData);
			} else if (sensorName.equals("led1OFF")) {
				arduinoDao.LED_OFF("LED-1");
				sendData[1] = 1;
				sendData[2] = 0;
				tcp2.sendToClient(sendData);
			} else if (sensorName.equals("led2ON")) {
				arduinoDao.LED_ON("LED-2");
				sendData[1] = 2;
				sendData[2] = 1;
				tcp2.sendToClient(sendData);
			} else if (sensorName.equals("led2OFF")) {
				arduinoDao.LED_OFF("LED-2");
				sendData[1] = 2;
				sendData[2] = 0;
				tcp2.sendToClient(sendData);
			} else if (sensorName.equals("led3ON")) {
				arduinoDao.LED_ON("LED-3");
				sendData[1] = 3;
				sendData[2] = 1;
				tcp2.sendToClient(sendData);
			} else if (sensorName.equals("led3OFF")) {
				arduinoDao.LED_OFF("LED-3");
				sendData[1] = 3;
				sendData[2] = 0;
				tcp2.sendToClient(sendData);
			} else if (sensorName.equals("led4ON")) {
				arduinoDao.LED_ON("LED-4");
				sendData[1] = 4;
				sendData[2] = 1;
				tcp2.sendToClient(sendData);
			} else if (sensorName.equals("led4OFF")) {
				arduinoDao.LED_OFF("LED-4");
				sendData[1] = 4;
				sendData[2] = 0;
				tcp2.sendToClient(sendData);
			} else if (sensorName.equals("led5ON")) {
				arduinoDao.LED_ON("LED-5");
				sendData[1] = 5;
				sendData[2] = 1;
				tcp2.sendToClient(sendData);
			} else if (sensorName.equals("led5OFF")) {
				arduinoDao.LED_OFF("LED-5");
				sendData[1] = 5;
				sendData[2] = 0;
				tcp2.sendToClient(sendData);
			}

			System.out.println(socketData[0] + " " + socketData[1] + " " + socketData[2]);

			request.setAttribute("sensor", arduinoDao.selectList());
			System.out.println(arduinoDao.selectList());

			test = (ArrayList<SensorVO>) request.getAttribute("sensor");
			System.out.println(test.toString());

			RequestDispatcher rd = request.getRequestDispatcher("/Main.jsp");
			rd.forward(request, response);
	
		} catch (Exception e) {
			throw new ServletException(e);
		}
*/
	}
}