package dbControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbVo.AccountVo;

public class AccountDao {
	private static Connection con;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	private String url = "jdbc:mysql://nas.monlini.pe.kr:3307/cj200810";
	private String user = "atom", password = "D1HKeiO3UFMxckgW#$";
	
	private static int result;
	
	StringBuffer sql = new StringBuffer();
	
	public AccountDao() {}
	
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
	
	public int createAccount(AccountVo vo) {
		con = this.getConnection();
		sql.append("INSERT INTO account");
		sql.append("(id,pw,nick,birth,email,tel) ");
		sql.append("VALUES(?,?,?,?,?,?)");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getNick());
			pstmt.setTimestamp(4, vo.getBirth());
			pstmt.setString(5, vo.getEmail());
			pstmt.setString(6, vo.getTel());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("createAccount SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}
	
	public int idCheck(String id) {
		
		try {
			con = this.getConnection();
			sql.append("SELECT * FROM account ");
			sql.append("WHERE id=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = 0;				//중복 존재
			}
			else {
				result = 1;
			}
			
		} catch (SQLException e) {
			System.out.println("idCheck SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		return result;
	}
	
	public int nickCheck(String nick) {
		
		try {
			con = this.getConnection();
			sql.append("SELECT * FROM account ");
			sql.append("WHERE nick=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, nick);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = 0;			//중복존재
			}
			else {
				result = 1;
			}
			
		} catch (SQLException e) {
			System.out.println("nickCheck SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		return result;
	}

	public int loginCheck(String id, String pw) {
		con = this.getConnection();
		sql.append("SELECT activation,COUNT(*) FROM account ");
		sql.append("WHERE id=? and pw=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt("COUNT(*)");
			result += rs.getInt("activation");
			
		} catch (SQLException e) {
			System.out.println("loginCheck SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		return result;
	}

	public AccountVo selectByIdPw(String id, String pw) {
		AccountVo vo = new AccountVo();
		con = this.getConnection();
		sql.append("SELECT * FROM account ");
		sql.append("WHERE id=? and pw=?");
		
		try {

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			rs.next();
			
			vo.setIdx(rs.getInt("idx"));
			vo.setId(rs.getString("id"));
			vo.setPw(rs.getString("pw"));
			vo.setNick(rs.getString("nick"));
			vo.setBirth(rs.getTimestamp("birth"));
			vo.setEmail(rs.getString("email"));
			vo.setTel(rs.getString("tel"));

		} catch (SQLException e) {
			System.out.println("selectByIdPw SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return vo;
	}

	public int updateAccountInfo(AccountVo vo) {
		con = this.getConnection();
		sql.append("UPDATE account SET ");
		sql.append("pw=?, birth=?, email=?, tel=? ");
		sql.append("where id=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getPw());
			pstmt.setTimestamp(2, vo.getBirth());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getTel());
			pstmt.setString(5, vo.getId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateAccountInfo SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		
		return result;
	}

	public int deleteAccountForAdmin(String id) {
		con = this.getConnection();
		sql.append("DELETE FROM account ");
		sql.append("WHERE id=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("deleteAccountForAdmin SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		
		return result;
	}

	public int accountDisable(String id, String pw) {
		con = this.getConnection();
		sql.append("UPDATE account SET ");
		sql.append("activation=0 ");
		sql.append("WHERE id=? AND pw=?");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("accountDisable SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

	public List<AccountVo> selectAccount() {
		List<AccountVo> vos = new ArrayList<>();
		con = this.getConnection();
		sql.append("SELECT * FROM account");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AccountVo vo = new AccountVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setId(rs.getString(2));
				vo.setNick(rs.getString(4));
				vo.setStrBirth(rs.getTimestamp(5));
				vo.setEmail(rs.getString(6));
				vo.setTel(rs.getString(7));
				vo.setActivation(rs.getInt(8));
				
				vos.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("selectAccount SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		return vos;
	}

	public int accountEnableForAdmin(String id) {
		con = this.getConnection();
		sql.append("UPDATE account SET ");
		sql.append("activation=1 ");
		sql.append("WHERE id=?");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("accountEnableForAdmin SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

	public int accountDisableForAdmin(String id) {
		con = this.getConnection();
		sql.append("UPDATE account SET ");
		sql.append("activation=0 ");
		sql.append("WHERE id=?");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("accountDisableForAdmin SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

	public List<AccountVo> selectAccountPaging(int startIdx, int lastIdx) {
		List<AccountVo> list = new ArrayList<>();
		con = this.getConnection();
		sql.append("SELECT * FROM account ");
		sql.append("LIMIT ?,?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, startIdx);
			pstmt.setInt(2, lastIdx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AccountVo vo = new AccountVo();
				
				vo.setIdx(rs.getInt(1));
				vo.setId(rs.getString(2));
				vo.setNick(rs.getString(4));
				vo.setStrBirth(rs.getTimestamp(5));
				vo.setEmail(rs.getString(6));
				vo.setTel(rs.getString(7));
				vo.setActivation(rs.getInt(8));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("selectAccountPaging SQL 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
}
