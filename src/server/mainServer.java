package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class mainServer {
	private final static int portNum = 52273;
	private ServerSocket listener; // initialize main server socket
	Map<Integer, String[]> chatRoom = new HashMap<>(); // Chatroom
	Map<String, Connection> client_all = new HashMap<>(); // manage users

	public Integer roomNum = 1;

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

	private class Connection extends Thread // Thread를 상속받음
	{
		private volatile BufferedReader br;
		private volatile PrintWriter pw;
		private String clientName; // client user ID
//		private String client_ip; // client ip
//		private InetAddress client_inet; // get ip address

		Connection(Socket s) throws IOException {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream(), true);
		}

		public void run() {
			String line = null;

		}
	}
}
