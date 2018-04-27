package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClientReceiver extends Thread {
	String clientName;
	DataInputStream input;
	BufferedInputStream fileInput;

	ClientReceiver(String clientName, InputStream input, InputStream fileInput) {
		this.input = new DataInputStream(input);
		this.fileInput = new BufferedInputStream(fileInput);
		this.clientName = clientName;
	}

	@Override
	public void run() {
		while (input != null) {
			try {
				System.out.println(input.readUTF());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			input.close();
			fileInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
