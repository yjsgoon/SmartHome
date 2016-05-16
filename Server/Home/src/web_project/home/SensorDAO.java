package web_project.home;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// Web Smart Home Sensor DAO �겢�옒�뒪

public class SensorDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	
	void SensorDAO(){
		
	}
	
	void connect() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/ora");
			conn = ds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// �꽱�꽌 �긽�깭 媛깆떊
	public boolean updateDB(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_led=?, s_usw=?, s_hum=?, s_flame=?, " 
				 + "s_vb=?, s_lux=?, s_noise=?, s_temp=?, s_gas=?, s_pwd=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_led());
			pstmt.setInt(2, sensor.getS_usw());
			pstmt.setInt(3, sensor.getS_hum());
			pstmt.setInt(4, sensor.getS_flame());
			pstmt.setInt(5, sensor.getS_vb());
			pstmt.setInt(6, sensor.getS_lux());
			pstmt.setInt(7,  sensor.getS_noise());
			pstmt.setInt(8,  sensor.getS_temp());
			pstmt.setInt(9,  sensor.getS_gas());
			pstmt.setInt(10,  sensor.getS_pwd());
			pstmt.setInt(11,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	public boolean updatePwd(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_pwd=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_pwd());
			pstmt.setInt(2,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	public boolean updateUsw(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_usw=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_usw());
			pstmt.setInt(2,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	public boolean updateFlame(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_flame=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_flame());
			pstmt.setInt(2,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	public boolean updateVibe(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_vb=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_vb());
			pstmt.setInt(2,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	public boolean updateGas(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_gas=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_gas());
			pstmt.setInt(2,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	public boolean updateHum(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_hum=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_hum());
			pstmt.setInt(2,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	public boolean updateTemp(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_temp=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_temp());
			pstmt.setInt(2,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	public boolean updateNoise(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_noise=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_noise());
			pstmt.setInt(2,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	public boolean updateLux(SensorBean sensor) {
		connect();
		
		String sql = "update Sensor set s_lux=? where s_aid=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensor.getS_lux());
			pstmt.setInt(2,  sensor.getS_aid());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	
	// �빐�떦 id�쓽 �꽱�꽌 �긽�깭瑜� 媛��졇�샂
	public SensorBean getDB(int sb_aid) {
		connect();
		
		String sql = "select * from Sensor where s_aid=?";
		SensorBean sensor = new SensorBean();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sb_aid);
			ResultSet rs = pstmt.executeQuery();
			
			// �뜲�씠�꽣媛� �븯�굹留� �엳�쑝誘�濡� rs.next()瑜� �븳踰덈쭔 �떎�뻾�븳�떎.
			if (rs.next()) {
				sensor.setS_aid(rs.getInt("s_aid"));
				sensor.setS_led(rs.getInt("s_led"));
				sensor.setS_usw(rs.getInt("s_usw"));
				sensor.setS_hum(rs.getInt("s_hum"));
				sensor.setS_flame(rs.getInt("s_flame"));
				sensor.setS_vb(rs.getInt("s_vb"));
				sensor.setS_lux(rs.getInt("s_lux"));
				sensor.setS_noise(rs.getInt("s_noise"));
				sensor.setS_temp(rs.getInt("s_temp"));
				sensor.setS_gas(rs.getInt("s_gas"));
				sensor.setS_pwd(rs.getInt("s_pwd"));
				System.out.println(sensor.getS_aid() + " " + rs.getInt("s_vb") + sensor.getS_vb());
			}
			
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return sensor;
	}
	
	// �쟾泥� �꽱�꽌�쓽 �긽�깭瑜� 媛��졇�샂
	public ArrayList<SensorBean> getDBList() {
		connect();
		ArrayList<SensorBean> datas = new ArrayList<SensorBean>();
		
		String sql = "select * from Sensor order by s_aid desc";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				SensorBean sensor = new SensorBean();
				
				sensor.setS_aid(rs.getInt("s_aid"));
				sensor.setS_led(rs.getInt("s_led"));
				sensor.setS_usw(rs.getInt("s_usw"));
				sensor.setS_hum(rs.getInt("s_hum"));
				sensor.setS_flame(rs.getInt("s_flame"));
				sensor.setS_vb(rs.getInt("s_vb"));
				sensor.setS_lux(rs.getInt("s_lux"));
				sensor.setS_noise(rs.getInt("s_noise"));
				sensor.setS_temp(rs.getInt("s_temp"));
				sensor.setS_gas(rs.getInt("s_gas"));
				sensor.setS_pwd(rs.getInt("s_pwd"));
				datas.add(sensor);
			}
			rs.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return datas;
	}
}