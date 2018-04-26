package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import common.ConsoleColor;

public class ClientReceiver extends Thread {
	String clientName = "";
	DataInputStream input;
	BufferedInputStream Fileinput;

	ClientReceiver(String clientName, InputStream input, InputStream Fileinput) {
		this.clientName = clientName;
		this.input = new DataInputStream(input);
		this.Fileinput = new BufferedInputStream(Fileinput);
	}

	@Override
	public void run() {

		try {

			clientName = input.readUTF();
			while (input != null) {
				System.out.println(ConsoleColor.ANSI_GREEN + input.readUTF() + ConsoleColor.ANSI_RESET);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
				Fileinput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
