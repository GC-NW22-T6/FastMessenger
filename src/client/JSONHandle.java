package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import server.SQLInterface;

public class JSONHandle {

	public static void test(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {
		Socket socket = null;

		// 연결 요청 //
		socket = new Socket(); // 객체 생성
		socket.connect(new InetSocketAddress("IPNUMBER", 50001)); // 소켓 연결 (아이피 및 포트번호 부여)
		System.out.println("Connect Success!"); // 출력문

		// 입출력 스트림 얻기 //
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();

		// 서버 -> 클라이언트 //
		JSONObject jsonRoot = new JSONObject(); // jsonObject 생성
		jsonRoot.put("StatusCode", 01);// 01: 정상, 02: 유저 없음, 03: 비번 틀림 // If문 처리하여 Statcode 따라서 이후 Put, Get 다르게.

		JSONObject jsonData = new JSONObject(); // data 객체 생성
		jsonData.put("ID", "testID");
		jsonData.put("name", "testUserName");
		jsonData.put("PW", "testPassword");
		jsonData.put("statMessage", "메롱:P");
		jsonData.put("lastCon", "2022.12.05 12:03:34");
		// 등등 계속 추가

		jsonRoot.put("data", jsonData); // json 생성

		String json = jsonRoot.toJSONString(); // String 변환

		System.out.println(json); // 출력해보기

		os = socket.getOutputStream(); // outputStream
		PrintWriter pw = new PrintWriter(os); // PrintWriter를 선언
		pw.println(json); // println을 이용하여 \r\n을 같이 보냄
		pw.flush(); // flush
		os.flush();

		// 클라이언트 -> 서버 //
		is = socket.getInputStream(); // inputStream

		Reader reader = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(reader);
		String strJson = br.readLine();

		JSONParser parser = new JSONParser();

		JSONObject jsonResult = (JSONObject) parser.parse(strJson); // json객체 선언 (json 파싱)
		Integer StatusCode = (Integer) jsonResult.get("StatusCode");
		if (StatusCode == 01) {
			JSONObject jsoninner = (JSONObject) jsonResult.get("data");
			String name = (String) jsoninner.get("name");
			String statMessage = (String) jsoninner.get("statMessage");
			String time = (String) jsoninner.get("lastCon");

		}

		System.out.println("StatCode: " + Integer.toString(StatusCode)); // action 출력

		is.close(); // InputStream Close
		os.close();

	}

	public static Integer getSCode(String line) throws org.json.simple.parser.ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonResult = (JSONObject) parser.parse(line); // json객체 선언 (json 파싱)
		Integer StatusCode = (Integer) jsonResult.get("StatusCode");
		return StatusCode;
	}

	public static String makeUsertoJSON(User user) {
		JSONObject jsonRoot = new JSONObject(); // jsonObject 생성
		jsonRoot.put("StatusCode", 01);// 01: 정상, 02: 유저 없음, 03: 비번 틀림 // If문 처리하여 Statcode 따라서 이후 Put, Get 다르게.

		JSONObject jsonData = new JSONObject(); // data 객체 생성
//		jsonData.put("id", user.getUserID());
		jsonData.put("name", user.getUserName());
//		jsonData.put("password", user.getUserPassword());
		jsonData.put("statMessage", user.getStatMessage());
		jsonData.put("lastCon", user.getUserLastCon());
		jsonData.put("phone", user.getUserPhoneNum());
		// 등등 계속 추가

		jsonRoot.put("data", jsonData); // json 생성

		String json = jsonRoot.toJSONString(); // String 변환

		return json;

	}

	public static User makeJSONtoUser(String line) throws org.json.simple.parser.ParseException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonResult = (JSONObject) parser.parse(line); // json객체 선언 (json 파싱)
		JSONObject jsoninner = (JSONObject) jsonResult.get("data");
		User user = new User();
		user.setUserID((String) jsoninner.get("ID"));
		user.setUserPassword((String) jsoninner.get("PW"));
		user.setUserName((String) jsoninner.get("name"));
		user.setStatMessage((String) jsoninner.get("statMessage"));
		user.setUserEmail((String) jsoninner.get("email"));
		user.setUserPhoneNum((String) jsoninner.get("phone"));
		user.setUserNickname((String) jsoninner.get("nickname"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyymmdd");
		java.util.Date date = formatter.parse((String) jsoninner.get("birth"));
		long timeInMilliSeconds = date.getTime();
		java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
		user.setUserBitrhDate(sqlDate);
		user.setUserLastCon(null);
		user.setSalt((String) jsoninner.get("salt"));

		return user;

	}

	public static String getSalt(String UID) throws ClassNotFoundException, SQLException {
		JSONObject jsonRoot = new JSONObject(); // jsonObject 생성
		jsonRoot.put("StatusCode", 01);// 01: 정상, 02: 유저 없음, 03: 비번 틀림 // If문 처리하여 Statcode 따라서 이후 Put, Get 다르게.

		JSONObject jsonData = new JSONObject(); // data 객체 생성
		jsonData.put("salt", SQLInterface.getSaltbyUID(UID));
		jsonRoot.put("data", jsonData); // json 생성

		String json = jsonRoot.toJSONString(); // String 변환

		return json;
	}

	public static String getError(int i) {
		if (i == 2) {
			JSONObject jsonRoot = new JSONObject(); // jsonObject 생성
			jsonRoot.put("StatusCode", 01);
			String json = jsonRoot.toJSONString(); // String 변환
			return json;
		} else if (i == 2) {
			JSONObject jsonRoot = new JSONObject(); // jsonObject 생성
			jsonRoot.put("StatusCode", 02);
			String json = jsonRoot.toJSONString(); // String 변환
			return json;
		} else if (i == 3) {
			JSONObject jsonRoot = new JSONObject(); // jsonObject 생성
			jsonRoot.put("StatusCode", 03);
			String json = jsonRoot.toJSONString(); // String 변환
			return json;
		}
		return null;
	}

	public static Integer validPWChange(String line)
			throws org.json.simple.parser.ParseException, ClassNotFoundException, SQLException {
		JSONParser parser = new JSONParser();
		JSONObject jsonResult = (JSONObject) parser.parse(line); // json객체 선언 (json 파싱)
		Integer StatusCode = (Integer) jsonResult.get("StatusCode");
		JSONObject jsoninner = (JSONObject) jsonResult.get("data");
		String id = (String) jsoninner.get("ID");
		String pw = (String) jsoninner.get("PW");
		String name = (String) jsoninner.get("name");
		String phone = (String) jsoninner.get("phone");
		String salt = (String) jsoninner.get("salt");
		return SQLInterface.validPWChange(id, name, phone, pw, salt);
	}

	public static String make101(String uid) {
		JSONObject jsonRoot = new JSONObject(); // jsonObject 생성
		jsonRoot.put("StatusCode", 101);
		JSONObject jsonData = new JSONObject(); // data 객체 생성
		jsonData.put("ID", uid);
		jsonRoot.put("data", jsonData); // json 생성

		String json = jsonRoot.toJSONString(); // String 변환

		return json;

	}

}
