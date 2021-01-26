package dbControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbVo.FileVo;

public class FileDao {
	private static Connection con;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	private String url = "jdbc:mysql://nas.monlini.pe.kr:3307/cj200810";
	private String user="atom", password="D1HKeiO3UFMxckgW#$";
	
	private static int result;
	
	
	public FileDao() {} 
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패");
		} catch (SQLException e) {
			System.out.println("DB연결 실패");
		}
		
		return con;
	}
	
	public void close(Connection con,PreparedStatement pstmt, ResultSet rs) {
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

	public int tmpUpload(FileVo vo) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into file ");
		sql.append("(filePath,fileName,orifileName) ");
		sql.append("values(?,?,?)");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getFilePath());
			pstmt.setString(2, vo.getFileName());
			pstmt.setString(3, vo.getOrifileName());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("tmpUpload sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}
	
	public int selectNumberOfFile(int bidx) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as n from file ");
		sql.append("where bidx=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt("n");
		} catch (SQLException e) {
			System.out.println("selectNumberOfFile sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		return result;
	}

	public List<FileVo> tmpSelect() {
		List<FileVo> list = new ArrayList<>();
		
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from file ");
		sql.append("where bidx=0");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FileVo vo = new FileVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setBidx(rs.getInt(2));
				vo.setFilePath(rs.getString(3));
				vo.setFileName(rs.getString(4));
				vo.setOrifileName(rs.getString(5));
				vo.setHashCode(rs.getString(6));
				vo.setUploadDate(rs.getTimestamp(7));
				vo.setUploadDateStr(rs.getTimestamp(7));
				vo.setDownloadCount(rs.getInt(8));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("tmpSelect sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		return list;
	}

	public int checkName(String fileName) {
		
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from file ");
		sql.append("where fileName=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, fileName);
			rs = pstmt.executeQuery();
			
			rs.next();
			
			result = rs.getInt("count(*)");
			
		} catch (SQLException e) {
			System.out.println("selectByName sql error\n"+e.getMessage());
		}finally{
			close(con, pstmt, rs);
		}
		return result;
	}

	public List<FileVo> selectByBidx(int bidx) {
		List<FileVo> list = new ArrayList<>();
		
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from file ");
		sql.append("where bidx = ? ");
		sql.append("order by bidx");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FileVo vo = new FileVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setBidx(rs.getInt(2));
				vo.setFilePath(rs.getString(3));
				vo.setFileName(rs.getString(4));
				vo.setOrifileName(rs.getString(5));
				vo.setHashCode(rs.getString(6));
				vo.setUploadDate(rs.getTimestamp(7));
				vo.setUploadDateStr(rs.getTimestamp(7));
				vo.setDownloadCount(rs.getInt(8));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("selectByBidx sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}

	public void uploadServer(int bidx, String hashCode, String fileName) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("update file set bidx=?, hashCode=? ");
		sql.append("where fileName=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			pstmt.setString(2, hashCode);
			pstmt.setString(3, fileName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("uploadServer sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
	}
	public List<FileVo> selectTempFile() {
		List<FileVo> list = new ArrayList<>();
		
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from file ");
		sql.append("where bidx=0");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FileVo vo = new FileVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setBidx(rs.getInt(2));
				vo.setFilePath(rs.getString(3));
				vo.setFileName(rs.getString(4));
				vo.setOrifileName(rs.getString(5));
				vo.setHashCode(rs.getString(6));
				vo.setUploadDate(rs.getTimestamp(7));
				vo.setUploadDateStr(rs.getTimestamp(7));
				vo.setDownloadCount(rs.getInt(8));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("selectTempFile sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		return list;
	}
	
	public int deleteTempFile() {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from file ");
		sql.append("where bidx=0");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("deleteTempFile sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		
		return result;
	}

	public int deleteFileByFileName(int bidx,String fileName) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from file ");
		sql.append("where bidx=? and fileName=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			pstmt.setString(2, fileName);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("deleteFileByFileName sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}
	
	public int deleteFileByBidx(int bidx) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from file ");
		sql.append("where bidx=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("deleteFileByBidx sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

	public List<FileVo> selectByFileName(int bidx, String fileName) {
		List<FileVo> list = new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from file ");
		sql.append("where bidx=? and fileName=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			pstmt.setString(2, fileName);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FileVo vo = new FileVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setBidx(rs.getInt(2));
				vo.setFilePath(rs.getString(3));
				vo.setFileName(rs.getString(4));
				vo.setOrifileName(rs.getString(5));
				vo.setHashCode(rs.getString(6));
				vo.setUploadDate(rs.getTimestamp(7));
				vo.setUploadDateStr(rs.getTimestamp(7));
				vo.setDownloadCount(rs.getInt(8));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("selectByFileName sql error\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	
}
