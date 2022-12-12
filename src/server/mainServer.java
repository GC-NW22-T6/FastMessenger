package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

public class mainServer {
	private final static int portNum = 52273;
	private ServerSocket listener; // initialize main server socket
	Map<Integer, String[]> chatRoom = new HashMap<>(); // Chatroom
	Map<String, Connection> client_all = new HashMap<>(); // manage users

	public Integer roomNum = 1;
	public Object scode;
	public Object scode;

	public mainServer() throws IOException {
		listener = new ServerSocket(portNum); // initialize
	}

	void runServer() throws IOException {
		while (true) // threads
		{
			Socket socket = listener.accept(); // wait for the income....
			System.out.println("accept connection");
			Connection con = new Connection(socket); // thread setting
			con.start(); // start thread
		}
	}

	private class Connection extends Thread // Thread�� ��ӹ���
	{
		private volatile BufferedReader br;
		private volatile PrintWriter pw;
		private String clientName; // client user ID
//		private String client_ip; // client ip
//		private InetAddress client_inet; // get ip address
		private Object scode;

		Connection(Socket s) throws IOException {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream(), true);
		}

		public void run() {
			String line = null;
			try {

				while ((line = br.readLine()) != null) {
					Integer scode = JSONHandle.getSCode(line);
					if (scode == 101) { // Login: get salt to login
						String salt = JSONHandle.getSalt(line);
						pw.println(salt);
						break;
					} else if (scode == 102) { // Login: Login function
						Integer valid = JSONHandle.validIDPW(line);
						if (valid == 1) { // Login success
							String UID = JSONHandle.getUID(line);
							User user = SQLInterface.getuserbyUID(UID);
							pw.println(JSONHandle.makeUsertoJSON(user));
							synchronized (client_all) {
								client_all.put(UID, this);
							}
							break;
						} else if (valid == 2) { // Wrong PW
							pw.println(JSONHandle.getError(02));
						} else { // Wrong ID
							pw.println(JSONHandle.getError(03));
						}
					} else if (scode == 201) { // Join
						Integer valid = JSONHandle.validIDPW(line);
						if (valid == 1 || valid == 2) {
							User user = JSONHandle.makeJSONtoUser(line);
							pw.println(JSONHandle.getError(01));
						} else { // Exist ID
							pw.println(JSONHandle.getError(03));
						}
					}
				}
			} catch (IOException | ParseException e) {
				synchronized (client_all) {
					try {
						SQLInterface.client_logout(this.getClientName());
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					client_all.remove(this.getClientName());
				}
			} finally {
			}
		}

		public String getClientName() {
			return this.clientName;
		}

	}
}