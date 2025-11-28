package Tugas3;

public class Minuman extends MenuItem {

    private String jenisMinuman; 

    public Minuman(String nama, double harga, String kategori, String jenisMinuman) {
        super(nama, harga, kategori);
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.printf("%-20s | Kategori: %-10s | Jenis: %-12s | Rp %,10.0f%n",
                getNama(), getKategori(), jenisMinuman, getHarga());
    }

    @Override
    public String toFileFormat() {
        return String.format("MINUMAN;%s;%.2f;%s;%s",
                getNama(), getHarga(), getKategori(), jenisMinuman);
    }
}