package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import server.FileReceiver;

public class ClientReceiver extends Thread {
	Socket socket;
	Socket FileSocket;
	DataInputStream input;
	BufferedInputStream FileInput;

	ClientReceiver(Socket socket, Socket FileSocket) {
	
		try {
			input = new DataInputStream(socket.getInputStream());
			FileInput = new BufferedInputStream(FileSocket.getInputStream());
		} catch (IOException e) {
			
		}
		
	}

	@Override
	public void run() {
		
		while (input != null) {
			try {
				String msg = input.readUTF();
				if(msg.equals("끝")) {//프로그램 종료
					System.exit(0);
				}else if(msg.startsWith("#")){//파일이면
					FileReceiver fr = new FileReceiver(msg.substring(1),FileInput);
					fr.start();
				}else {//채팅메세지이면
					System.out.println(msg);
				}
					
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			input.close();
			FileInput.close();
			socket.close();
			FileSocket.close();
			System.out.println("수신이 종료되었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
