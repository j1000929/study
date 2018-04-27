package examples;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

	private DataInputStream reader; // 입력 스트림

	private DataOutputStream writer; // 출력 스트림

	private ServerSocket server; // 서버소켓

	private Socket socket; // 소켓

	public FileServer() {
	}

	void startServer() { // 서버를 동작시킨다.

		try {

			server = new ServerSocket(7777);

			System.out.println("서버소켓이 생성되었습니다.");

			while (true) {

				System.out.println("클라이언트를 기다립니다.");

				socket = server.accept();

				System.out.println("클라이언트와 연결되었습니다.");

				// socket의 입력 스트림과 출력 스트림을 얻는다.

				reader = new DataInputStream(socket.getInputStream());

				writer = new DataOutputStream(socket.getOutputStream());

				getCommand(); // 클라이언트와 통신한다.

			}

		} catch (Exception e) {

			System.out.println(e);

		}

	}

	// 클라이언트가 요청한 파일을 전송하는 메소드

	void getCommand() {

		try {

			while (true) {

				String fileName = reader.readUTF(); // 파일이름을 얻는다.
				System.out.println("1." + fileName);

				// 해당 파일의 내용을 클라이언트 측의 소켓으로 전송한다.

				writer.writeUTF(getFileInfo(fileName));
				writer.flush();

				System.out.println(fileName + "의 정보 전송 완료");

			}

		} catch (Exception e) {

		} finally {

			System.out.println("클라이언트와의 접속이 끊겼습니다.");

			try {

				if (reader != null)
					reader.close();

				if (writer != null)
					writer.close();

				if (socket != null)
					socket.close();

				reader = null;
				writer = null;
				socket = null;

			} catch (IOException ie) {
			}

		}

	}

	// fileName에 해당하는 파일의 내용을 반환하는 메소드

	String getFileInfo(String fileName) {

		System.out.println("2." + fileName);
		String fileInfo = "";

		try {

			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			String temp;

			while ((temp = br.readLine()) != null)

				fileInfo += temp + '\n';

			System.out.println(fileInfo);

			br.close();

		} catch (FileNotFoundException fe) {

			fileInfo = "파일이 없습니다.";

		} catch (IOException ie) {
		}

		return fileInfo;

	}

	public static void main(String[] args) {

		FileServer server = new FileServer();

		server.startServer();

	}

}
