package ImageApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FunnyImageInput {

	static void funnyImageMaker(String imageFileName) throws IOException {

		try {
			FileInputStream fis = new FileInputStream(imageFileName);// �̹�������
			FileOutputStream fos = new FileOutputStream("funny.dat");// �������
			DataOutputStream dos = new DataOutputStream(fos);

			dos.writeUTF(imageFileName);
			dos.writeUTF("������..�ʹ� ����..very good..");
			dos.write(fis.readAllBytes());
			System.out.println("���ϻ���");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static void funnyImageReader(String dataFileName) throws IOException {

		FileInputStream fis = new FileInputStream(dataFileName);
		DataInputStream dis = new DataInputStream(fis);

		FileOutputStream funny = new FileOutputStream("funny.png");
		DataOutputStream fun = new DataOutputStream(funny);

		String fileName = "";
		String Imprint = "";
		int data = 0;
		byte b = 0;
		char c;

		try {

			// System.out.println(name);
			fileName = dis.readUTF();// �����̸�
			Imprint = dis.readUTF();// ����

			while (true) {
				fun.write(dis.readByte());
			}

			// end while

		} catch (

		EOFException e) {
			System.out.println();
			System.out.println();
			System.out.println("�����̸�: " + fileName);
			System.out.println("������: " + Imprint);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dis != null)
				try {
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			fis.close();
			dis.close();
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		if (args.length < 2) {
			System.exit(-1);
		}

		if (args[0].equals("make")) {
			funnyImageMaker(args[1]);
		} else if (args[0].equals("read")) {
			funnyImageReader(args[1]);
		} else {
			System.out.println("���� �Ű������� Ʋ�Ƚ��ϴ�.");
		}

	}

}
