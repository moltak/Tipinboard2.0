package com.corping.media;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ConvertToBytes {

	public static byte[] toBytes(String filename) {
		File file = new File(filename);
		byte[] bytes = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			// System.out.println(file.exists() + "!!");
			// InputStream in = resource.openStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];

			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum); // no doubt here is 0
				// Writes len bytes from the specified byte array starting at
				// offset off to this byte array output stream.
				System.out.println("read " + readNum + " bytes,");
			}
			bytes = bos.toByteArray();
		} catch (IOException ex) {
			// Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null,
			// ex);
		}

		return bytes;

	}

}
