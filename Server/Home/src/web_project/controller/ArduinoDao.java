package web_project.controller;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;


public class ArduinoDao extends HttpServlet{
	
	
	public void LED_ON(String Sensor_id) throws Exception{
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try{
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/ora");
			conn = ds.getConnection();
			
			System.out.println(Sensor_id);
			
			SocketVO.setState((byte)1);
			cs = conn.prepareCall("{call sensor_on(?)}");
			cs.setString(1, Sensor_id);
			cs.executeQuery();
			System.out.println("ON");
			System.out.println(SocketVO.getState());
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs!=null) try{rs.close();}catch(Exception e){};
			if(cs!=null)try{cs.close();}catch(Exception e){};
			if(conn!=null) try{conn.close();}catch(Exception e){};
		}
	}
	
	public void LED_OFF(String Sensor_id) throws Exception{
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try{
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/ora");
			conn = ds.getConnection();
			
			cs = conn.prepareCall("{call sensor_off(?)}");
			cs.setString(1, Sensor_id);
			cs.executeQuery();
			SocketVO.setState((byte)0);
			System.out.println(SocketVO.getState());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs!=null) try{rs.close();}catch(Exception e){};
			if(cs!=null)try{cs.close();}catch(Exception e){};
			if(conn!=null) try{conn.close();}catch(Exception e){};
		}
	}
	
	public void SENSOR_ANALOG(String Sensor_id, int value) throws Exception{
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try{
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/ora");
			conn = ds.getConnection();
			
			cs = conn.prepareCall("{call sensor_analog(?,?)}");
			cs.setString(1, Sensor_id);
			cs.setInt(2, value);
			cs.executeQuery();
			SocketVO.setState((byte)0);
			System.out.println(SocketVO.getState());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs!=null) try{rs.close();}catch(Exception e){};
			if(cs!=null)try{cs.close();}catch(Exception e){};
			if(conn!=null) try{conn.close();}catch(Exception e){};
		}
	}
	
	public List<SensorVO> selectList() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		System.out.println("Off");
		try{
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/ora");
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM SENSOR");
			rs = pstmt.executeQuery();
			
			ArrayList<SensorVO> sensor = new ArrayList<SensorVO>();
			
			while(rs.next()){
				sensor.add(new SensorVO()
										 .setSensor_id(rs.getString("SENSOR_ID"))
										 .setSensor_name(rs.getString("SENSOR_NAME"))
										 .setSensor_info(rs.getString("SENSOR_INFO"))
										 .setSensor_state(rs.getInt("SENSOR_STATE"))
						);
			}
			
			return sensor;
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs!=null) try{rs.close();}catch(Exception e){};
			if(pstmt!=null)try{pstmt.close();}catch(Exception e){};
			if(conn!=null) try{conn.close();}catch(Exception e){};
		}
	}
}
