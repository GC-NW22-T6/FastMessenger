package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class mainClient {
	private static String CID; // 사용자 ID
	public static User thisUser = null;

	public static String IP;
	public static int portNum;
	public static Socket socket; // 서버와 연결하기 위한 포트
	public static PrintWriter pw; // 서버에게 데이터를 쓰기 위한 확장 스트림
	public static volatile BufferedReader br; // 서버로 부터 데이터를 받기 위한 스트림
	public static volatile InputStreamReader isr;
//	private static Map<Integer, chatClient> chat_room = new HashMap<>();

	public mainClient() throws UnknownHostException, IOException {
		try {
			String server_ip;
			String server_port;
			File file = new File("server_info.dat"); // 파일 객체 생성
			if (file.exists() == true) { // server_info.dat이 있다면
				FileReader filereader = new FileReader(file); // 입력 스트림 생성
				BufferedReader buffReader = new BufferedReader(filereader); // 입력 버퍼 생성
				String line = "";
				ArrayList<String> ServerInfo = new ArrayList<>(); // array 생성
				while ((line = buffReader.readLine()) != null) {
					ServerInfo.add(line); // readLine()을 통해 array에 한줄씩 저장
				}
				server_ip = ServerInfo.get(0); // 첫째 줄
				server_port = ServerInfo.get(1); // 둘째 줄
				buffReader.close();

				IP = server_ip;
				portNum = Integer.parseInt(server_port);
			}

			socket = new Socket(IP, portNum);

			isr = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(isr); // 서버에서 받아오는 스트림
			pw = new PrintWriter(socket.getOutputStream(), true); // 서버로 보내기 위함 스트림
		}

		catch (FileNotFoundException e) {
			System.out.println("Server Not Found");
		}
		new LoginFrame();
	}

	static public String getCID() {
		return CID;
	}

	static public void setClientName(String tempName) {
		CID = tempName;
	}

	public static void main(String[] args) throws IOException {
		new mainClient();
	}
}
