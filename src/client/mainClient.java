package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class mainClient {
	private static String CID; // 사용자 ID

	final static String SERVER_ADDR = "127.0.0.1"; // 자바 채팅 서버 주소
	final static int SERVER_PORT = 52273; // 자바 채팅서버 포트번호

	public static Socket socket; // 서버와 연결하기 위한 포트
	public static PrintWriter pw; // 서버에게 데이터를 쓰기 위한 확장 스트림
	public static volatile BufferedReader br; // 서버로 부터 데이터를 받기 위한 스트림
	public static volatile InputStreamReader isr;
	private static Map<Integer, chatClient> chat_room = new HashMap<>();

	public mainClient() {
		try {
			socket = new Socket(SERVER_ADDR, SERVER_PORT);

			isr = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(isr); // 서버에서 받아오는 스트림
			pw = new PrintWriter(socket.getOutputStream(), true); // 서버로 보내기 위함 스트림
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new LoginFrame();
	}

	static public String getCID() {
		return CID;
	}

	static public void setClientName(String tempName) {
		CID = tempName;
	}
}
