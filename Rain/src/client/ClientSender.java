package client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import common.ConsoleColor;

public class ClientSender extends Thread {//Ŭ���̾�Ʈ �߽� Ŭ����
	
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
				if(input.startsWith("/��������")) {
					String[] token = input.split("[ ]+");
					//+:�� ���ڰ� �ϳ� �̻�
					//[]:������ �����̳� ������ ��Ÿ���� �� ���� ���̴� - ��ȣ�� ������ ��Ÿ����. []������ ^�� �����Ͽ� �����ϸ� not �� ��Ÿ����.
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
				System.out.println("������ �Ϸ�Ǿ����ϴ�.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}