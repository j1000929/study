package examples;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class FileUploadClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			String serverIp = "127.0.0.1";
			// 소켓을 생성하여 연결을 요청한다.
			Socket socket = new Socket(serverIp, 7777);

			System.out.println("서버에 연결 되었습니다.");

			Sender sender = new Sender(socket);
			Receiver receiver = new Receiver(socket);

			sender.start();
			receiver.start();

		} catch (ConnectException ce) {
			// TODO Auto-generated catch block
			ce.printStackTrace();
		} catch (IOException ie) {
			// TODO Auto-generated catch block
			ie.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
