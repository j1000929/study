package examples;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringReader;
import java.net.Socket;

public class FileClient {

	private InputStream in;
	private DataInputStream reader; // 입력 스트림
	private DataOutputStream writer; // 출력 스트림

	Socket socket; // 소킷

	String title;

	public FileClient(String title) { // 생성자

		this.title = title;

		try {

			// 파일이름을 서버로 전송한다.
			connect();
			System.out.println("파일이름: " + title + '\n');
			writer = new DataOutputStream(socket.getOutputStream());
			writer.writeUTF(title);
			writer.flush();

			// 소켓의 입력 스트림으로부터 파일의 내용을 얻는다.

			String fileInfo = reader.readUTF();
			System.out.println(fileInfo);
			String fileName = title + "__.txt";
			StringBuffer buf = new StringBuffer();

			File file = new File(fileName);
			FileWriter fwriter = new FileWriter(file);

			buf.append(fileInfo);
			StringReader sreader = new StringReader(buf.toString());

			int data = 0;

			while ((data = sreader.read()) != -1) {

				if (data == '\n' || data == '\r') {
					fwriter.write(System.getProperty("line.separator"));
				} else {
					fwriter.write(data);
				}
			}

			sreader.close();
			fwriter.close();

		} catch (Exception ie) {
		}

	}

	private void connect() { // 서버와 연결하는 메소드

		try {

			System.out.println("서버와의 연결을 시도합니다.\n");

			socket = new Socket("127.0.0.1", 7777);
			System.out.println("서버와의 연결되었습니다.\n");

			// // 소켓으로부터 받은 테이터를 출력한다.
			reader = new DataInputStream(socket.getInputStream());

			writer = new DataOutputStream(socket.getOutputStream());

		} catch (Exception e) {

			System.out.println("연결 실패..");

		}

	}

	public static void main(String[] args) {

		FileClient client = new FileClient("field.txt");
		// FileClient client2 = new FileClient("field.txt");

		// client.connect(); // 서버와 연결한다.

	}

}
