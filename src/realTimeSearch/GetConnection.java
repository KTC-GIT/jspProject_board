package realTimeSearch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
	private static GetConnection getCon;
	private Connection con;
	private String url = "jdbc:mysql://nas.monlini.pe.kr:3307/cj200810";
	private String user="atom", password="D1HKeiO3UFMxckgW#$";
	
	private GetConnection() {}
	
	public static GetConnection getInstance() {
		if(getCon==null) {
			getCon = new GetConnection();
		}
		return getCon;
	}
	
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
}
