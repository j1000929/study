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
	private DataInputStream reader; // �Է� ��Ʈ��
	private DataOutputStream writer; // ��� ��Ʈ��

	Socket socket; // ��Ŷ

	String title;

	public FileClient(String title) { // ������

		this.title = title;

		try {

			// �����̸��� ������ �����Ѵ�.
			connect();
			System.out.println("�����̸�: " + title + '\n');
			writer = new DataOutputStream(socket.getOutputStream());
			writer.writeUTF(title);
			writer.flush();

			// ������ �Է� ��Ʈ�����κ��� ������ ������ ��´�.

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

	private void connect() { // ������ �����ϴ� �޼ҵ�

		try {

			System.out.println("�������� ������ �õ��մϴ�.\n");

			socket = new Socket("127.0.0.1", 7777);
			System.out.println("�������� ����Ǿ����ϴ�.\n");

			// // �������κ��� ���� �����͸� ����Ѵ�.
			reader = new DataInputStream(socket.getInputStream());

			writer = new DataOutputStream(socket.getOutputStream());

		} catch (Exception e) {

			System.out.println("���� ����..");

		}

	}

	public static void main(String[] args) {

		FileClient client = new FileClient("field.txt");
		// FileClient client2 = new FileClient("field.txt");

		// client.connect(); // ������ �����Ѵ�.

	}

}
