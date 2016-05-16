package web_project.controller;
/*
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @param args
 */

import java.awt.Robot;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import web_project.home.SensorBean;
import web_project.home.SensorDAO;

class TcpIpServerRunnable implements Runnable {
	Socket socket = null;
	private Robot robot;
	private SocketVO sv = new SocketVO();
	private static byte[] inputDatas = new byte[]{0,0,0};
	private static byte[] sendingDatas = new byte[]{0,0,0};
	private Queue inputQueue = new LinkedList<byte[]>();
	private int port;
	ServerSocket server = null;
	ArduinoDao arduinoDao = new ArduinoDao();
	SensorDAO SensorDao = new SensorDAO();
	SensorBean Sensorbean = new SensorBean();
	SecureVO secureVO = new SecureVO();
	
	public TcpIpServerRunnable(int port) {
		this.port = port;
	}
	public TcpIpServerRunnable() {
		// TODO Auto-generated constructor stub
	}
	

	public void run() {

		String msg = "Hello, Client";

		try {
			robot = new Robot();
			// ???�� ?��?�� 7777?�� �?�? ?���? 객체�? ?��?��
			server = new ServerSocket(port); // 0~1024?�� ?��?��?��?�� ?��?�� ?��?��?��?�� ?�� ?��?��.
			System.out.println("Wait Client......" + port);

			// ?��?��?��?��?��?�� ?��?��?�� 기다�?
			socket = server.accept(); // ?��기서 ?��결되�? ?��?�� ?�� ?��?��?�� ?��?��?���? ?���? ?��?��발생
			System.out.println("Client Connection Success" + port);

			InputStream is = null;
			DataInputStream dis = null;
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			String Msg;
			
			System.out.println(socket.getPort());

			while(true){
			robot.delay(1000);
				if (dis.available() > 0) {
										
					dis.readFully(inputDatas);
					sv.setInputDatas(inputDatas);
				
					System.out.println((int) inputDatas[0] + " " + (int) inputDatas[1] + " " + (int) inputDatas[2]);
					
					switch (inputDatas[0]) {
					case 0:
						if ((inputDatas[1] == 0) && (inputDatas[2] == 0)){ // ?��?��?��
							sendingDatas[0] = inputDatas[0];
							sendingDatas[1] = inputDatas[1];
							sendingDatas[2] = inputDatas[2];
							secureVO.setMode(0);
							Controller.tcp3.sendToClient(sendingDatas);
							System.out.println("보안 모드 : " + sendingDatas[0] + sendingDatas[1] + sendingDatas[2]);
						}
						else if ((inputDatas[1] == 0) && (inputDatas[2] == 1)){
							sendingDatas[0] = inputDatas[0];
							sendingDatas[1] = inputDatas[1];
							sendingDatas[2] = inputDatas[2];
							secureVO.setMode(1);
							Controller.tcp3.sendToClient(sendingDatas);
							System.out.println("보안 모드 : " + sendingDatas[0] + sendingDatas[1] + sendingDatas[2]);
						}
						break;
					case 1:// ?���?
						if ((inputDatas[1] == 1) && (inputDatas[2] == 0)){ // ?��?��?��
							//arduinoDao.LED_OFF("PWD-1");
							Sensorbean.setS_aid(1);
							Sensorbean.setS_pwd(0);
							SensorDao.updatePwd(Sensorbean);
						}
						else if ((inputDatas[1] == 1) && (inputDatas[2] == 1)){
//							arduinoDao.LED_ON("PWD-1");
							Sensorbean.setS_aid(1);
							Sensorbean.setS_pwd(1);
							SensorDao.updatePwd(Sensorbean);
						}
						else if ((inputDatas[1] == 2) && (inputDatas[2] == 0)){ // Tracing
//							arduinoDao.LED_OFF("USW-1");
							Sensorbean.setS_aid(1);
							Sensorbean.setS_usw(0);
							SensorDao.updateUsw(Sensorbean);
						}
						else if ((inputDatas[1] == 2) && (inputDatas[2] == 1)){
//							arduinoDao.LED_ON("USW-1");
							Sensorbean.setS_aid(1);
							Sensorbean.setS_usw(1);
							SensorDao.updateUsw(Sensorbean);
						}
						break;
					case 2:// ?���?
						break;
					case 3:// ?��?��
						if ((inputDatas[1] == 1) && (inputDatas[2] == 0)){ // ?��?��?��?��							
//							arduinoDao.LED_OFF("FLAME-1");
							Sensorbean.setS_aid(3);
							Sensorbean.setS_flame(0);
							SensorDao.updateFlame(Sensorbean);
						}
						else if ((inputDatas[1] == 1) && (inputDatas[2] == 1)){
//							arduinoDao.LED_ON("FLAME-1");
							Sensorbean.setS_aid(3);
							Sensorbean.setS_flame(1);
							SensorDao.updateFlame(Sensorbean);
						}
						else if ((inputDatas[1] == 2) && (inputDatas[2] == 0)){ // 기울기센?��
//							arduinoDao.LED_OFF("VIBE-1");
							Sensorbean.setS_aid(3);
							Sensorbean.setS_vb(0);
							SensorDao.updateVibe(Sensorbean);
						}
						else if ((inputDatas[1] == 2) && (inputDatas[2] == 1)){
//							arduinoDao.LED_ON("VIBE-1");
							Sensorbean.setS_aid(3);
							Sensorbean.setS_vb(1);
							SensorDao.updateVibe(Sensorbean);
						}
						else if ((inputDatas[1] == 3) && (inputDatas[2] == 0)){ // 초음?��+모터
//							arduinoDao.LED_OFF("USW-2");
							Sensorbean.setS_aid(3);
							Sensorbean.setS_usw(0);
							SensorDao.updateUsw(Sensorbean);
						}
						else if ((inputDatas[1] == 3) && (inputDatas[2] == 1)){
//							arduinoDao.LED_ON("USW-2");
							Sensorbean.setS_aid(3);
							Sensorbean.setS_usw(1);
							SensorDao.updateUsw(Sensorbean);
						}
						break;
					case 4:// �?�?
						if ((inputDatas[1] == 1)){ // ?��?��?��?��
//							arduinoDao.SENSOR_ANALOG("GAS-1", inputDatas[2]);
							Sensorbean.setS_aid(4);
							Sensorbean.setS_gas(inputDatas[2]);
							SensorDao.updateGas(Sensorbean);
						}
						else if ((inputDatas[1] == 2)){ // 기울기센?��
//							arduinoDao.SENSOR_ANALOG("HUM-1", inputDatas[2]);
							Sensorbean.setS_aid(4);
							Sensorbean.setS_hum(inputDatas[2]);
							SensorDao.updateHum(Sensorbean);
						}
						else if ((inputDatas[1] == 3)){
//							arduinoDao.SENSOR_ANALOG("TEMP-1", inputDatas[2]);
							Sensorbean.setS_aid(4);
							Sensorbean.setS_temp(inputDatas[2]);
							SensorDao.updateTemp(Sensorbean);
						}
						else if ((inputDatas[1] == 4)){
//							arduinoDao.SENSOR_ANALOG("NOISE-1", inputDatas[2]);
							Sensorbean.setS_aid(4);
							Sensorbean.setS_noise(inputDatas[2]);
							SensorDao.updateNoise(Sensorbean);
						}
						else if ((inputDatas[1] == 5)){
//							arduinoDao.SENSOR_ANALOG("VIBE-1", inputDatas[2]);
//							Sensorbean.setS_aid(4);
//							Sensorbean.setS_vibe(inputDatas[2]);
//							SensorDao.updateVibe(Sensorbean);
							Sensorbean.setS_aid(4);
							Sensorbean.setS_lux(inputDatas[2]);
							SensorDao.updateLux(Sensorbean);
						}
						break;
					default:
						break;
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	public void sendToClient(byte[] sendingBytes) {
		OutputStream out = null;
		DataOutputStream dos = null;
		try {
			System.out.println("?���?");
			out = this.socket.getOutputStream();
			dos = new DataOutputStream(out);
			dos.write(sendingBytes);
			dos.flush();
			System.out.println(sendingBytes + "?��?��?���? ?��?��?��?��?��?��.");
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			/*
			try {
				if(dos != null) {
					dos.close();
				}
				if(out != null) {
					out.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}*/
		}
	}

}