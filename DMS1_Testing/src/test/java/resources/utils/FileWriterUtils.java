package resources.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class FileWriterUtils {

	public static void writeDataToFile(String fileName, List<String> data) throws IOException {

		try (	FileOutputStream fos = new FileOutputStream(new File(fileName));
				BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos)); 
				) {		
			for (String line : data) {
				buffer.write(line);
				buffer.newLine();
			}
		}
	}		
}


