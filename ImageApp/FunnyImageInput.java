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
			FileInputStream fis = new FileInputStream(imageFileName);// 이미지파일
			FileOutputStream fos = new FileOutputStream("funny.dat");// 출력파일
			DataOutputStream dos = new DataOutputStream(fos);

			dos.writeUTF(imageFileName);
			dos.writeUTF("에구구..너무 예쁨..very good..");
			dos.write(fis.readAllBytes());
			System.out.println("파일생성");

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
			fileName = dis.readUTF();// 파일이름
			Imprint = dis.readUTF();// 리뷰

			while (true) {
				fun.write(dis.readByte());
			}

			// end while

		} catch (

		EOFException e) {
			System.out.println();
			System.out.println();
			System.out.println("파일이름: " + fileName);
			System.out.println("감상평: " + Imprint);

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
			System.out.println("실행 매개변수가 틀렸습니다.");
		}

	}

}
