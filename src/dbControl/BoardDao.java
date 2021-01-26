package dbControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oracle.nio.BufferSecrets;

import dbVo.BoardVo;
import dbVo.ReplyVo;

public class BoardDao {
	private static BoardDao dao;
	private static Connection con;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	
	private String url = "jdbc:mysql://nas.monlini.pe.kr:3307/cj200810";
	private String user = "atom", password = "D1HKeiO3UFMxckgW#$";
	
	public static int result;
	
	
	// -----싱글톤 기법처리-------
	private BoardDao() {}
	
	public static BoardDao getInstance() {
		if(dao == null) {
			dao = new BoardDao();
		}
		return dao;
	}
	//---싱글톤 기법 처리 --------
	
	public Connection getConnection() {						//DB 연결
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 연결 실패");
		} catch (SQLException e) {
			System.out.println("DB연결 실패");
		}
		return con;
	}
	
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {		//DB연결 해제 메소드
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<BoardVo> selectList(){						//게시판으로 불러오는 메소드
		List<BoardVo> list = new ArrayList<>();
		try {
			con = this.getConnection();
			String sql = "select * from board order by bidx desc;";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				
				vo.setBidx(rs.getInt("bidx"));
				vo.setTag(rs.getString("tag"));
				vo.setTitle(rs.getString("title"));
				vo.setId(rs.getString("id"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setHits(rs.getInt("hits"));
				vo.setRecommend(rs.getInt("recommend"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con,pstmt,rs);
		}
		return list;
	}
	
	public List<BoardVo> selectListLimit(int idx, int pagingLimit){						//게시판으로 불러오는 메소드
		List<BoardVo> list = new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from board ");
		sql.append("order by bidx desc ");
		sql.append("limit ?,?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, idx);
			pstmt.setInt(2, pagingLimit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				
				vo.setBidx(rs.getInt("bidx"));
				vo.setTag(rs.getString("tag"));
				vo.setTitle(rs.getString("title"));
				vo.setId(rs.getString("id"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setHits(rs.getInt("hits"));
				vo.setRecommend(rs.getInt("recommend"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con,pstmt,rs);
		}
		return list;
	}

	public int writeSave(BoardVo vo) {				//글 쓴 것을 DB에 저장하는 메소드
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into board ");
		sql.append("(id,pw,ip,tag,title,content,memberwrite) ");
		sql.append("values (?,?,?,?,?,?,?)");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getIp());
			pstmt.setString(4, vo.getTag());
			pstmt.setString(5, vo.getTitle());
			pstmt.setString(6, vo.getContent());
			pstmt.setInt(7, vo.getMemberwrite());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}
	
	
	public BoardVo selectById(int bidx) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		BoardVo vo = new BoardVo();
		sql.append("select * from board ");
		sql.append("where bidx = ?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo.setTitle(rs.getString("title"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setTag(rs.getString("tag"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setHits(rs.getInt("hits"));
				vo.setRecommend(rs.getInt("recommend"));
				vo.setContent(rs.getString("content"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
			}
		} catch (SQLException e) {
			System.out.println("sql 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return vo;
	}
	
	
	public int hitsUpdate(int bidx, int hits) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("update board set hits = ? ");
		sql.append("where bidx = ?");
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, hits);
			pstmt.setInt(2, bidx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

	
	public int modifyContent(int bidx, String title, String pw, String tag, String content) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("set title=?, tag=?, content=? ");
		sql.append("where bidx=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(1, title);
			pstmt.setString(2, tag);
			pstmt.setString(3, content);
			pstmt.setInt(4, bidx);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

	public int deleteContent(int bidx, String pw) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from board ");
		sql.append("where bidx = ? and pw = ?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			pstmt.setString(2, pw);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		
		return result;
	}

	public int writeReply(ReplyVo vo) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into reply(bidx,id,pw,ip,content,memberwrite) ");
		sql.append("values (?,?,?,?,?,?)");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, vo.getBidx());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getPw());
			pstmt.setString(4, vo.getIp());
			pstmt.setString(5, vo.getContent());
			pstmt.setInt(6, vo.getMemberwrite());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}
	
	public List<ReplyVo> viewReply(int bidx){
		List<ReplyVo> list = new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from reply ");
		sql.append("where bidx = ? order by writedate");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyVo vo = new ReplyVo();
				
				vo.setRidx(rs.getInt("ridx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setContent(rs.getString("content"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("viewReply sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	
	public List<ReplyVo> viewReplyLimit(int bidx,int n, int m){
		List<ReplyVo> list = new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from reply ");
		sql.append("where bidx = ? order by ridx ");
		sql.append("limit ?,?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			pstmt.setInt(2, n);
			pstmt.setInt(3, m);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyVo vo = new ReplyVo();
				
				vo.setRidx(rs.getInt("ridx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setContent(rs.getString("content"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("viewReplyLimit sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	
	public List<ReplyVo> viewReplyDesc(int bidx) {
		List<ReplyVo> list = new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from reply ");
		sql.append("where bidx = ? order by ridx desc");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyVo vo = new ReplyVo();
				
				vo.setRidx(rs.getInt("ridx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setContent(rs.getString("content"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	
	public List<ReplyVo> viewReplyDescLimit(int bidx,int n,int m) {
		List<ReplyVo> list = new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from reply ");
		sql.append("where bidx = ? order by ridx desc limit ?,?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			pstmt.setInt(2, n);
			pstmt.setInt(3, m);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyVo vo = new ReplyVo();
				
				vo.setRidx(rs.getInt("ridx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setContent(rs.getString("content"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	public int countReply(int bidx) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from reply ");
		sql.append("where bidx = ?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return result;
	}

	public ReplyVo selectReplybyRidx(int ridx) {			//댓글 삭제하기 위해 비밀번호 확인하는 메소드
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		ReplyVo vo = new ReplyVo();
		sql.append("select * from reply ");
		sql.append("where ridx = ?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, ridx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo.setBidx(rs.getInt("bidx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setContent(rs.getString("content"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
			}
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		return vo;
	}

	public void deleteReply(int ridx, String pw) {				//댓글 삭제
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from reply ");
		sql.append("where ridx=? and pw=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, ridx);
			pstmt.setString(2, pw);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
	}

	public int recommendUp(int bidx, int recommend) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("update board set recommend=? ");
		sql.append("where bidx=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, recommend);
			pstmt.setInt(2, bidx);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}
	
	
	public List<BoardVo> selectById(String searchBox){
		List<BoardVo> list= new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from board ");
		sql.append("where id=? order by bidx desc");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, searchBox);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				
				vo.setBidx(rs.getInt("bidx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setTitle(rs.getString("title"));
				vo.setTag(rs.getString("tag"));
				vo.setContent(rs.getString("content"));
				vo.setHits(rs.getInt("hits"));
				vo.setRecommend(rs.getInt("recommend"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		
		return list;
	}
	
	public List<BoardVo> selectByIdLimit(String searchBox,int idx,int pagingLimit){
		List<BoardVo> list= new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from board ");
		sql.append("where id=? order by bidx desc ");
		sql.append("limit ?,?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, searchBox);
			pstmt.setInt(2, idx);
			pstmt.setInt(3, pagingLimit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				
				vo.setBidx(rs.getInt("bidx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setTitle(rs.getString("title"));
				vo.setTag(rs.getString("tag"));
				vo.setContent(rs.getString("content"));
				vo.setHits(rs.getInt("hits"));
				vo.setRecommend(rs.getInt("recommend"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		
		
		return list;
	}
	
	public List<BoardVo> selectByTitle(String searchBox){
		List<BoardVo> list= new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from board ");
		sql.append("where title like ? or title like ? or title like ? ");
		sql.append("order by bidx desc");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+searchBox);
			pstmt.setString(2, searchBox+"%");
			pstmt.setString(3, "%"+searchBox+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				
				vo.setBidx(rs.getInt("bidx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setTitle(rs.getString("title"));
				vo.setTag(rs.getString("tag"));
				vo.setContent(rs.getString("content"));
				vo.setHits(rs.getInt("hits"));
				vo.setRecommend(rs.getInt("recommend"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	
	public List<BoardVo> selectByTitleLimit(String searchBox,int idx, int pagingLimit){
		List<BoardVo> list= new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from board ");
		sql.append("where title like ? or title like ? or title like ? ");
		sql.append("order by bidx desc ");
		sql.append("limit ?,?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+searchBox);
			pstmt.setString(2, searchBox+"%");
			pstmt.setString(3, "%"+searchBox+"%");
			pstmt.setInt(4, idx);
			pstmt.setInt(5, pagingLimit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				
				vo.setBidx(rs.getInt("bidx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setTitle(rs.getString("title"));
				vo.setTag(rs.getString("tag"));
				vo.setContent(rs.getString("content"));
				vo.setHits(rs.getInt("hits"));
				vo.setRecommend(rs.getInt("recommend"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	
	public List<BoardVo> selectByContent(String searchBox){
		List<BoardVo> list= new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from board ");
		sql.append("where content like ? or content like ? or content like ? ");
		sql.append("order by bidx desc");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+searchBox);
			pstmt.setString(2, searchBox+"%");
			pstmt.setString(3, "%"+searchBox+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				
				vo.setBidx(rs.getInt("bidx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setTitle(rs.getString("title"));
				vo.setTag(rs.getString("tag"));
				vo.setContent(rs.getString("content"));
				vo.setHits(rs.getInt("hits"));
				vo.setRecommend(rs.getInt("recommend"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}
	
	public List<BoardVo> selectByContentLimit(String searchBox,int idx,int pagingLimit){
		List<BoardVo> list= new ArrayList<>();
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from board ");
		sql.append("where content like ? or content like ? or content like ? ");
		sql.append("order by bidx desc ");
		sql.append("limit ?,?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+searchBox);
			pstmt.setString(2, searchBox+"%");
			pstmt.setString(3, "%"+searchBox+"%");
			pstmt.setInt(4, idx);
			pstmt.setInt(5, pagingLimit);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				
				vo.setBidx(rs.getInt("bidx"));
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setIp(rs.getString("ip"));
				vo.setIpView(rs.getString("ip"));
				vo.setTitle(rs.getString("title"));
				vo.setTag(rs.getString("tag"));
				vo.setContent(rs.getString("content"));
				vo.setHits(rs.getInt("hits"));
				vo.setRecommend(rs.getInt("recommend"));
				vo.setWritedate(rs.getTimestamp("writedate"));
				vo.setWritedateStr(rs.getTimestamp("writedate"));
				vo.setMemberwrite(rs.getInt("memberwrite"));
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return list;
	}

	public void maintainHits(int bidx, int hits) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("update board set hits=? ");
		sql.append("where bidx=?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, hits);
			pstmt.setInt(2, bidx);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("sql오류\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		
	}

	public int deleteContentForAdmin(int bidx) {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from board ");
		sql.append("where bidx = ?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bidx);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, null);
		}
		return result;
	}

	public int selectBidxLatest() {
		con = this.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select bidx from board ");
		sql.append("order by bidx desc limit 1");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt("bidx");
		} catch (SQLException e) {
			System.out.println("sql 에러\n"+e.getMessage());
		}finally {
			close(con, pstmt, rs);
		}
		return result;
	}
	
}
