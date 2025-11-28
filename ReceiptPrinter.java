package Tugas3;
import java.io.*;

public class ReceiptPrinter {
	public static void simpanStrukkeFile(String namaFile, String isiStruk) throws IOException {
		try (PrintWriter pw = new PrintWriter(new FileWriter(namaFile, true))) {
			pw.println(isiStruk);
		}
	}
}