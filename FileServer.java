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

	private DataInputStream reader; // �Է� ��Ʈ��

	private DataOutputStream writer; // ��� ��Ʈ��

	private ServerSocket server; // ��������

	private Socket socket; // ����

	public FileServer() {
	}

	void startServer() { // ������ ���۽�Ų��.

		try {

			server = new ServerSocket(7777);

			System.out.println("���������� �����Ǿ����ϴ�.");

			while (true) {

				System.out.println("Ŭ���̾�Ʈ�� ��ٸ��ϴ�.");

				socket = server.accept();

				System.out.println("Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.");

				// socket�� �Է� ��Ʈ���� ��� ��Ʈ���� ��´�.

				reader = new DataInputStream(socket.getInputStream());

				writer = new DataOutputStream(socket.getOutputStream());

				getCommand(); // Ŭ���̾�Ʈ�� ����Ѵ�.

			}

		} catch (Exception e) {

			System.out.println(e);

		}

	}

	// Ŭ���̾�Ʈ�� ��û�� ������ �����ϴ� �޼ҵ�

	void getCommand() {

		try {

			while (true) {

				String fileName = reader.readUTF(); // �����̸��� ��´�.
				System.out.println("1." + fileName);

				// �ش� ������ ������ Ŭ���̾�Ʈ ���� �������� �����Ѵ�.

				writer.writeUTF(getFileInfo(fileName));
				writer.flush();

				System.out.println(fileName + "�� ���� ���� �Ϸ�");

			}

		} catch (Exception e) {

		} finally {

			System.out.println("Ŭ���̾�Ʈ���� ������ ������ϴ�.");

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

	// fileName�� �ش��ϴ� ������ ������ ��ȯ�ϴ� �޼ҵ�

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

			fileInfo = "������ �����ϴ�.";

		} catch (IOException ie) {
		}

		return fileInfo;

	}

	public static void main(String[] args) {

		FileServer server = new FileServer();

		server.startServer();

	}

}
