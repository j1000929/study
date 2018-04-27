package examples;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class TestFile {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String fileName = "output.txt";
		StringBuffer buf = new StringBuffer();
		File file = new File(fileName);
		FileWriter writer = new FileWriter(file);

		buf.append("abc" + "rn"); // 요부분 집중
		buf.append("def" + "rn");
		StringReader reader = new StringReader(buf.toString());

		int c;
		while ((c = reader.read()) != -1)
			writer.write(c);

		reader.close();
		writer.close();

	}

}
