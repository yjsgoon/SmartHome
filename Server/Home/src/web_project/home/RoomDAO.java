package web_project.home;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class RoomDAO {
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
		
		// �빐�떦 id�쓽 Room �젙蹂대�� 媛��졇�샂
		public RoomBean getDB(int r_id) {
			connect();
			
			String sql = "select * from Home where r_id=?";
			RoomBean room = new RoomBean();
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, r_id);
				ResultSet rs = pstmt.executeQuery();
				
				// �뜲�씠�꽣媛� �븯�굹留� �엳�쑝誘�濡� rs.next()瑜� �븳踰덈쭔 �떎�뻾�븳�떎.
				if(rs.next()){
				room.setR_id(rs.getInt("r_id"));
				room.setR_name(rs.getString("r_name"));
				room.setR_cal(rs.getDouble("r_cal"));
				room.setR_row(rs.getDouble("r_row"));
				room.setR_height(rs.getDouble("r_height"));
				}
				rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
			return room;
		}
		
		// �쟾泥� �꽱�꽌�쓽 �긽�깭瑜� 媛��졇�샂
		public ArrayList<RoomBean> getDBList() {
			connect();
			ArrayList<RoomBean> datas = new ArrayList<RoomBean>();
			
			String sql = "select * from Home order by r_id desc";
			try {
				pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					RoomBean room = new RoomBean();
					
					room.setR_id(rs.getInt("r_id"));
					room.setR_name(rs.getString("r_name"));
					room.setR_cal(rs.getDouble("r_cal"));
					room.setR_row(rs.getDouble("r_row"));
					room.setR_height(rs.getDouble("r_height"));
					datas.add(room);
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
