package client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import common.ConsoleColor;

public class ClientSender extends Thread {//클라이언트 발신 클래스
	
	DataOutputStream output;
	BufferedOutputStream fileOutput;
	private Scanner sc;
	FileSender filesender;

	public ClientSender(Socket socket, Socket FileSocket) {

		try {
			this.output = new DataOutputStream(socket.getOutputStream());
			this.fileOutput = new BufferedOutputStream(FileSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	@Override
	public void run() {

		try {
			
			sc = new Scanner(System.in);
			
			while (output != null) {
				String input = sc.nextLine();
				if(input.startsWith("/파일전송")) {
					String[] token = input.split("[ ]+");
					//+:앞 문자가 하나 이상
					//[]:문자의 집합이나 범위를 나타내며 두 문자 사이는 - 기호로 범위를 나타낸다. []내에서 ^가 선행하여 존재하면 not 을 나타낸다.
				filesender = new FileSender(token[1],output, fileOutput);
					//FileSender(String filename, DataOutputStream out, BufferedOutputStream Fileout)
					filesender.start();
					
				}else {
					output.writeUTF(input);
				}
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {//

			try {
				output.close();
				fileOutput.close();
				System.out.println("전송이 완료되었습니다.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}