package mdp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.UUID;
import java.util.zip.DeflaterInputStream;

public class Util {
	public static String getStringUuid() {
		return UUID.randomUUID().toString();
	}

	public static BigInteger getIntUuid() {
		return convertUuidToInt(UUID.randomUUID());
	}

	public static BigInteger convertUuidToInt(UUID uuid) {
		return new BigInteger(uuid.toString().replace("-", ""), 16);
	}

	private static final int BUFFER_SIZE = 8192;

	public static long copy(InputStream source, OutputStream sink) throws IOException {
		long nread = 0L;
		byte[] buf = new byte[BUFFER_SIZE];
		int n;
		while ((n = source.read(buf)) > 0) {
			sink.write(buf, 0, n);
			nread += n;
		}
		return nread;
	}

	public static byte[] getDeflatedBytes(File file1) throws IOException, FileNotFoundException {
		byte[] file1Bytes;
		try (FileInputStream sourceFileStream = new FileInputStream(file1);
				DeflaterInputStream inDeflaterStream = new DeflaterInputStream(sourceFileStream);
				ByteArrayOutputStream byteStream = new ByteArrayOutputStream();) {
			copy(inDeflaterStream, byteStream);
			file1Bytes = byteStream.toByteArray();
		}
		return file1Bytes;
	}
}
