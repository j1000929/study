package client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class ClientSender extends Thread {
	String clientName;
	DataOutputStream output;
	BufferedOutputStream fileOutput;

	public ClientSender(String clientName, OutputStream output, OutputStream fileOutput) {
		this.output = new DataOutputStream(output);
		this.fileOutput = new BufferedOutputStream(fileOutput);
		this.clientName = clientName;
	}

	@Override
	public void run() {
		try {
			output.writeUTF(clientName);

			Scanner scanner = new Scanner(System.in);

			while (output != null) {
				String input = scanner.nextLine();
				output.writeUTF("[" + clientName + "]" + input);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				output.close();
				fileOutput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
