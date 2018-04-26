package client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import common.ConsoleColor;

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

		Scanner scanner = new Scanner(System.in);

		try {
			if (output != null) {
				output.writeUTF(clientName);
			}

			while (output != null) {
				String input = scanner.nextLine();
				output.writeUTF("[" + clientName + "]" + ConsoleColor.ANSI_GREEN + input + ConsoleColor.ANSI_RESET);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {//

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