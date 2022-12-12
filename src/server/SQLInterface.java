package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLInterface {
	private static String dburl = "jdbc:mysql://localhost/network";
	private static String dbUser = "root";
	private static String dbpasswd = "12345";

	public static String getSaltbyUID(String UID) throws ClassNotFoundException, SQLException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT salt FROM client_list WHERE client_id = ?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, UID);
		ResultSet rs = ps.executeQuery();

		rs.next();
		String tmp = rs.getString(1);
		rs.close();
		ps.close();
		return tmp;
	}

	public static String getuserbyUID(String UID) throws ClassNotFoundException, SQLException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT salt FROM client_list WHERE client_id = ?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, UID);
		ResultSet rs = ps.executeQuery();

		rs.next();
		String tmp = rs.getString(1);
		rs.close();
		ps.close();
		return tmp;
	}

}
