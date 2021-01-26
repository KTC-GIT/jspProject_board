package webMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WebMessageDao {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private String url = "jdbc:mysql://nas.monlini.pe.kr:3307/cj200810";
	private String user="atom", password="D1HKeiO3UFMxckgW#$";
	
	int result;
	
	public WebMessageDao() {}
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패");
		} catch (Exception e) {
			System.out.println("DB연결 실패");
		}
		
		return con;
	}
	
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<WebMessageVo> selectList(String id) {
		List<WebMessageVo> list = new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from webMessage ");
		sql.append("where receiveId=? order by idx desc");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				WebMessageVo vo = new WebMessageVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setSendId(rs.getString(4));
				vo.setSendSw(rs.getString(5));
				vo.setSendDate(rs.getTimestamp(6));
				vo.setSendDateStr(rs.getTimestamp(6));
				vo.setReceiveId(rs.getString(7));
				vo.setReceiveSw(rs.getString(8));
				vo.setReceiveDate(rs.getTimestamp(9));
				vo.setReceiveDateStr(rs.getTimestamp(9));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("selectList sql 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}

	public WebMessageVo selectByIdx(int idx) {
		WebMessageVo vo = new WebMessageVo();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from webMessage ");
		sql.append("where idx=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			
			rs.next();
			
			vo.setIdx(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setContent(rs.getString(3));
			vo.setSendId(rs.getString(4));
			vo.setSendSw(rs.getString(5));
			vo.setSendDate(rs.getTimestamp(6));
			vo.setSendDateStr(rs.getTimestamp(6));
			vo.setReceiveId(rs.getString(7));
			vo.setReceiveSw(rs.getString(8));
			vo.setReceiveDate(rs.getTimestamp(9));
			vo.setReceiveDateStr(rs.getTimestamp(9));
			
		} catch (SQLException e) {
			System.out.println("selectByIdx sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return vo;
	}

	public int updateReceiveSwR(String receiveSw,Timestamp receiveDate,int idx) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("update webMessage ");
		sql.append("set receiveSw=?,receiveDate=? ");
		sql.append("where idx=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, receiveSw);
			pstmt.setTimestamp(2, receiveDate);
			pstmt.setInt(3, idx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateReceiveSw sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

	public int writeMessage(WebMessageVo vo) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into webMessage ");
		sql.append("(sendId,receiveId,title,content) ");
		sql.append("values(?,?,?,?)");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getSendId());
			pstmt.setString(2, vo.getReceiveId());
			pstmt.setString(3, vo.getTitle());
			pstmt.setString(4, vo.getContent());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("writeMessage sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

	public List<WebMessageVo> selectListBySendId(String id) {
		List<WebMessageVo> list = new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from webMessage ");
		sql.append("where sendId=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				WebMessageVo vo = new WebMessageVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setSendId(rs.getString(4));
				vo.setSendSw(rs.getString(5));
				vo.setSendDate(rs.getTimestamp(6));
				vo.setSendDateStr(rs.getTimestamp(6));
				vo.setReceiveId(rs.getString(7));
				vo.setReceiveSw(rs.getString(8));
				vo.setReceiveDate(rs.getTimestamp(9));
				vo.setReceiveDateStr(rs.getTimestamp(9));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("selectListBySendId sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}

	public int updateReceiveSwG(String receiveSw, int idx) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("update webMessage ");
		sql.append("set receiveSw=? ");
		sql.append("where idx=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, receiveSw);
			pstmt.setInt(2, idx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateReceiveSwG sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

}
