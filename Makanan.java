package Tugas3;

public class Makanan extends MenuItem {

    private String jenisMakanan; 

    public Makanan(String nama, double harga, String kategori, String jenisMakanan) {
        super(nama, harga, kategori);
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.printf("%-20s | Kategori: %-10s | Jenis: %-12s | Rp %,10.0f%n",
                getNama(), getKategori(), jenisMakanan, getHarga());
    }

    @Override
    public String toFileFormat() {
        return String.format("MAKANAN;%s;%.2f;%s;%s",
                getNama(), getHarga(), getKategori(), jenisMakanan);
    }
}