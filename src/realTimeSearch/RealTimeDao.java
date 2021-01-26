package realTimeSearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RealTimeDao {
	GetConnection getCon = GetConnection.getInstance();
	private Connection con = getCon.getConnection();
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	public void close(Connection con,PreparedStatement pstmt,ResultSet rs) {
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
	public List<RealTimeSearchVo> selectList(){
		List<RealTimeSearchVo> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from realTimeSearch");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RealTimeSearchVo vo = new RealTimeSearchVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setAge(rs.getInt(3));
				vo.setGender(rs.getString(4));
				vo.setAddress(rs.getString(5));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("selectList sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	public List<RealTimeSearchVo> userSearch(String searchField, String searchName) {
		List<RealTimeSearchVo> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from realTimeSearch ");
		sql.append("where "+searchField+" like ? or "+searchField+" like ? or "+searchField+" like ?");
		sql.append("order by "+searchField+" desc");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+searchName);
			pstmt.setString(2, searchName+"%");
			pstmt.setString(3, "%"+searchName+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RealTimeSearchVo vo = new RealTimeSearchVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setAge(rs.getInt(3));
				vo.setGender(rs.getString(4));
				vo.setAddress(rs.getString(5));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("userSearch sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
}
