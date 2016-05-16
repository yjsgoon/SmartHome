package web_project.home;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class LedDAO {
	// Web Smart Home Room DAO �겢�옒�뒪
	
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
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
		
		public boolean updateDB(LedBean led) {
			connect();
			
			String sql = "update LED set r_aid=?, r_state=?, r_name=? where r_led=?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, led.getR_aid());
				pstmt.setInt(2, led.getR_state());
				pstmt.setString(3, led.getR_name());
				pstmt.setInt(4, led.getR_led());
				
				System.out.println(led.getR_aid() + led.getR_state() + led.getR_name() + led.getR_led());
				
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
		
		// �빐�떦 id�쓽 Room �젙蹂대�� 媛��졇�샂
		public LedBean getDB(int r_id) {
			connect();
			
			String sql = "select * from LED where r_led=?";
			LedBean led = new LedBean();
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, r_id);
				ResultSet rs = pstmt.executeQuery();
				
				// �뜲�씠�꽣媛� �븯�굹留� �엳�쑝誘�濡� rs.next()瑜� �븳踰덈쭔 �떎�뻾�븳�떎.
				if(rs.next()){
				led.setR_aid(rs.getInt("r_aid"));
				led.setR_led(rs.getInt("r_led"));
				led.setR_state(rs.getInt("r_state"));
				led.setR_name(rs.getString("r_name"));
				}
				rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
			return led;
		}
		
		// �쟾泥� �꽱�꽌�쓽 �긽�깭瑜� 媛��졇�샂
		public ArrayList<LedBean> getDBList() {
			connect();
			ArrayList<LedBean> datas = new ArrayList<LedBean>();
			
			String sql = "select * from LED order by r_led desc";
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					LedBean led = new LedBean();
					
					led.setR_aid(rs.getInt("r_aid"));
					led.setR_led(rs.getInt("r_led"));
					led.setR_state(rs.getInt("r_state"));
					led.setR_name(rs.getString("r_name"));
					datas.add(led);
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
