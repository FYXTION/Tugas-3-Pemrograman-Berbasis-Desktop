package Tugas3;

/**
 * Representasi item diskon — juga turunan MenuItem supaya dapat ditampilkan di daftar menu.
 * Untuk diskon, harga tidak relevan (set ke 0), tetapi memiliki persen dan syarat minimal belanja.
 */
public class Diskon extends MenuItem {
    private double persen;         // misal 30 untuk 30%
    private double minimalBelanja; // syarat minimal (Rp)

    public Diskon(String nama, double persen, double minimalBelanja) {
        super(nama, 0.0, "Diskon");
        this.persen = persen;
        this.minimalBelanja = minimalBelanja;
    }

    public double getPersen() { return persen; }
    public double getMinimalBelanja() { return minimalBelanja; }

    public void setPersen(double persen) { this.persen = persen; }
    public void setMinimalBelanja(double minimalBelanja) { this.minimalBelanja = minimalBelanja; }

    @Override
    public void tampilMenu() {
        System.out.printf("Diskon  : %-25s | %5.0f%% off | Minimal: Rp %,10.0f%n",
                getNama(), persen, minimalBelanja);
    }

    @Override
    public String toFileFormat() {
        // DISKON;nama;persen;minimal
        return String.format("DISKON;%s;%.2f;%.2f", escape(getNama()), persen, minimalBelanja);
    }

    private String escape(String s) {
        return s.replace(";", ",");
    }
}