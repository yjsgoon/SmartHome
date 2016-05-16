package web_project.home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// Web Smart Home Account DAO �겢�옒�뒪

public class AccountDAO {
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
	
	// 鍮꾨�踰덊샇 媛깆떊
	public boolean updateDB(AccountBean account) {
		connect();
		
		String sql = "update Account a_passwd=?, where a_id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account.getA_passwd());
			pstmt.setString(2, account.getA_id());
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
	

	// 怨꾩젙 �깮�꽦
	public boolean insertDB(AccountBean account) {
		connect();
		
		String sql = "insert into Account(a_id, a_passwd) values(?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account.getA_id());
			pstmt.setString(2, account.getA_passwd());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
	
	// �빐�떦 怨꾩젙�쓽 �젙蹂대�� 媛��졇�샂
	public AccountBean getDB(String a_id) {
		connect();
		
		String sql = "select * from Account where a_id=?";
		AccountBean account = new AccountBean();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a_id);
			ResultSet rs = pstmt.executeQuery();
			
			// �뜲�씠�꽣媛� �븯�굹留� �엳�쑝誘�濡� rs.next()瑜� �븳踰덈쭔 �떎�뻾�븳�떎.
			rs.next();
			account.setA_id(rs.getString("a_id"));
			account.setA_passwd(rs.getString("a_passwd"));
			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return account;
	}
	
	// �쟾泥� 怨꾩젙�쓽 �젙蹂대�� 媛��졇�샂 (異붽� 蹂댁븞 �닔�젙 �븘�슂)
	public ArrayList<AccountBean> getDBList() {
		connect();
		ArrayList<AccountBean> datas = new ArrayList<AccountBean>();
		
		String sql = "select * from Account order by a_id desc";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				AccountBean account = new AccountBean();
				
				account.setA_id(rs.getString("a_id"));
				account.setA_passwd(rs.getString("a_passwd"));
				datas.add(account);
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