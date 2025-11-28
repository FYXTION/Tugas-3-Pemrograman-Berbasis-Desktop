package Tugas3;
import java.io.*;
import java.util.Scanner;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);
	private static final Menu menu = new Menu();
	private static Pesanan pesananTerakhir = null;
	
	public static void main(String[] args) {
		menu.muatDariFile("menu.txt");
		
		while (true) {
			System.out.println("\n=== APLIKASI RESTORAN ===");
			System.out.println("1. Tambah item menu");
			System.out.println("2. Tampilkan menu");
			System.out.println("3. Pesan menu");
			System.out.println("4. Tampilkan struk");
			System.out.println("5. Cetak (simpan) struk");
			System.out.println("6. Import struk dari file");
			System.out.println("7. Import daftar menu dari file");
			System.out.println("8. Simpan daftar menu ke file");
			System.out.println("9. Hapus menu");
			System.out.println("0. Keluar menu");
			
			System.out.println("\nMasukan menu yang ingin dipilih:");
			String pilih = scanner.nextLine().trim();
			
			switch (pilih) {
			case "1" -> tambahMenuInteractive();
			case "2" -> menu.tampilkanMenu();
			case "3" -> lakukanPemesanan();
			case "4" -> tampilkanStrukTerakhir();
			case "5" -> simpanStrukkeFile();
			case "6" -> importStrukdariFile();
			case "7" -> importMenudariFile();
			case "8" -> menu.simpanKeFile("menu.txt");
			case "9" -> hapusMenu();
			case "0" -> {
				System.out.println("Keluar. Sampai jumpa!");
				return;
			}
			default -> System.out.println("Pilihan tidak valid");
			}
		}
	}
	
	// Fitur import struk dari file 
	
	private static void importStrukdariFile() {
		System.out.print("Masukan nama file struk (misal: struk.txt): ");
		String file = scanner.nextLine().trim();
		File f = new File(file);
		if (!f.exists()) {
			System.out.println("File tidak ditemukan!");
			return;
		}
		
		System.out.println("\n=== STRUK DARI FILE ===");
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println("Gagal membaca: " + e.getMessage());
		}
		System.out.println("=".repeat(40) + "\n");
	}
	
	// Fitur tambah menu
	
	private static void tambahMenuInteractive() {
		System.out.println("Pilih tipe: 1 = Makanan, 2 = Minuman, 3 = Diskon");
		System.out.print("Tipe: ");
		String t = scanner.nextLine().trim();
		
		if (t.equals("1")) {
			System.out.print("Nama makanan: ");
			String nama = scanner.nextLine().trim();
			
			System.out.print("Harga: ");
			double harga = readDoubleSafe();
			
			System.out.print("Jenis makanan (misal: Main dish, Side dish, Dessert): ");
			String jenis = scanner.nextLine().trim();
			
			System.out.print("Kategori (Makanan/Minuman): ");
			String kategori = scanner.nextLine().trim();
			
			menu.tambah(new Makanan(nama, harga, kategori, jenis));
			System.out.println("Menu telah ditambahkan");
		}
		else if (t.equals("2")) {
			System.out.print("Nama minuman: ");
			String nama = scanner.nextLine().trim();
			
			System.out.print("Harga: ");
			double harga = readDoubleSafe();
			
			System.out.print("Jenis minuman (misal: Hot, Cold, Juice): ");
			String jenis = scanner.nextLine().trim();
			
			System.out.print("Kategori (Makanan/Minuman): ");
			String kategori = scanner.nextLine().trim();
			
			menu.tambah(new Minuman(nama, harga, kategori, jenis));
			System.out.println("Menu telah ditambahkan");
		}
		else if (t.equals("3")) {
			System.out.print("Nama diskon: ");
			String nama = scanner.nextLine().trim();
			
			System.out.print("Masukan persentase diskon (%):");
			double persen = readDoubleSafe();
			
			System.out.print("Masukan minimal belanja: ");
			double minimal = readDoubleSafe();
			
			menu.tambah(new Diskon(nama, persen, minimal));
			System.out.println("Diskon telah ditambahkan.");
		} else {
			System.out.println("Tipe tidak dikenali.");
		}
	}
	
	// pemesanan
	private static void lakukanPemesanan() {
		System.out.print("Nama pelanggan: ");
		String namaPelanggan = scanner.nextLine().trim();
		Pesanan pesanan = new Pesanan(namaPelanggan);
		
		System.out.println("Ketik nama item. Ketik 'selesai' untuk selesai");
		while (true) {
			System.out.print("Pesan: ");
			String input = scanner.nextLine().trim();
			if (input.equalsIgnoreCase("selesai")) break;
			if (input.isEmpty()) continue;
			
			MenuItem item = menu.cariByName(input);
			if (item == null) {
				System.out.println("Item '" + input + "' tidak ditemukan.");
				continue;
			}
			
			if (item instanceof Diskon) {
				pesanan.terapkanDiskon((Diskon) item);
				System.out.println("Diskon diterapkan jika memenuhi syarat.");
			}
			else {
			    System.out.print("Jumlah: ");
			    int qty = readIntSafe();
			    pesanan.tambahItem(item, qty);
			    System.out.println(item.getNama() + " " + qty + "x telah ditambahkan.\n");
			}

		}
		
		String struk = pesanan.generateStruk();
		System.out.println("\n" + struk);
		
		try {
			ReceiptPrinter.simpanStrukkeFile("struk.txt", struk);
		} catch (Exception ignored) {}
		
		pesananTerakhir = pesanan;
	}
	
	// Fungsi Tampilkan Struk
	private static void tampilkanStrukTerakhir() {
		if (pesananTerakhir == null) {
			System.out.println("Belum ada pesanan.");
			return;
		}
		System.out.println(pesananTerakhir.generateStruk());
	}
	
	// Fungsi Simpan struk
	private static void simpanStrukkeFile() {
		if (pesananTerakhir == null) {
			System.out.println("Belum ada pesanan.");
			return;
		}
		
		try {
			ReceiptPrinter.simpanStrukkeFile("struk.txt", pesananTerakhir.generateStruk());
			System.out.println("Struk disimpan.");
		} catch (Exception e) {
			System.out.println("Gagal: " + e.getMessage());
		}
	}
	//Fungsi import menu
	private static void importMenudariFile() {
		System.out.print("Masukan nama file menu: ");
		String f = scanner.nextLine().trim();
		menu.muatDariFile(f);
		menu.tampilkanMenu();
	}
	
	// Fungsi hapus menu
	
	private static void hapusMenu() {
		System.out.print("Masukan nama menu yang akan dihapus: ");
		String nama = scanner.nextLine().trim();
		
		try {
            menu.hapusMenu(nama);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
	
	// Helper input
	
	private static int readIntSafe() {
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.print("Masukan angka valid: ");
			}
		}
	}
	
	private static double readDoubleSafe() {
		while (true) {
			try {
				return Double.parseDouble(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.print("Masukan angka valid: ");
			}
		}
	}

}