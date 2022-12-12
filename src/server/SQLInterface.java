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
		conn.close();
		return tmp;
	}

	public static User getuserbyUID(String UID) throws ClassNotFoundException, SQLException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT * FROM client_list WHERE client_id = ?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, UID);
		ResultSet rs = ps.executeQuery();

		User user = new User();
		rs.next();
		user.setUserID(rs.getString(1));
		user.setUserPassword(rs.getString(2));
		user.setUserName(rs.getString(3));
		user.setUserEmail(rs.getString(4));
		user.setUserPhoneNum(rs.getString(5));
		user.setUserNickname(rs.getString(6));
		user.setUserBitrhDate(rs.getDate(7));
		user.setStatMessage(rs.getString(8));
		user.setUserLastCon(rs.getTimestamp(9));
		user.setSalt(rs.getString(10));
		rs.close();
		ps.close();
		conn.close();
		return user;
	}

	public static void client_logout(String UID) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "UPDATE login_check SET log = FALSE WHERE client_id = 'mike';";

		ps = conn.prepareStatement(sql);
		ps.setString(1, UID);
		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public static Integer validLogin(String id, String pw) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT client_password FROM client_list WHERE client_id = ?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			if (pw.equals(rs.getString(1)))
				return 1;
			else
				return 2;
		}
		return 3;

	}

	public static void initUser(User user) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "INSERT INTO client_list VALUES (?, ?, ?, ?, ?, ?, ? ,? ,? ,?);";
		ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUserID());
		ps.setString(2, user.getUserPassword());
		ps.setString(3, user.getUserName());
		ps.setString(4, user.getUserEmail());
		ps.setString(5, user.getUserPhoneNum());
		ps.setString(6, user.getUserNickname());
		ps.setDate(7, user.getUserBitrhDate());
		ps.setString(8, user.getStatMessage());
		ps.setTimestamp(9, user.getUserLastCon());
		ps.setString(10, user.getSalt());
		ps.executeUpdate();
		ps.close();
		System.out.println("Inserting Successfully!");
	}

	public static Integer validPWChange(String id, String name, String phone, String pw, String salt)
			throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT client_id FROM client_list WHERE client_id = ?, client_name=?, client_phone=?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, name);
		ps.setString(3, phone);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			PreparedStatement ps1 = null;

			String sql1 = "UPDATE client_list SET client_password=?, salt=? WHERE client_id = ?;";
			ps1 = conn.prepareStatement(sql);
			ps1.setString(1, pw);
			ps1.setString(2, salt);
			ps1.setString(3, id);
			ResultSet rs1 = ps1.executeQuery();
			return 1;
		}
		return 4;

	}
}
