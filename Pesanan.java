package Tugas3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Pesanan {

    private String namaPelanggan;
    private LocalDateTime waktu;
    private List<OrderLine> lines = new ArrayList<>();
    private Diskon diskonDipakai = null;

    public Pesanan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
        this.waktu = LocalDateTime.now();
    }

    // INNER CLASS ORDERLINE
    public static class OrderLine {
        private MenuItem item;
        private int qty;

        public OrderLine(MenuItem item, int qty) {
            this.item = item;
            this.qty = qty;
        }

        public MenuItem getItem() {
            return item;
        }

        public int getQty() {
            return qty;
        }

        public double getSubTotal() {
            return item.getHarga() * qty;
        }
    }

    public void tambahItem(MenuItem item, int qty) {
        lines.add(new OrderLine(item, qty));
    }

    public void terapkanDiskon(Diskon d) {
        this.diskonDipakai = d;
    }

    public double hitungSubTotal() {
        double total = 0;
        for (OrderLine ol : lines) {
            total += ol.getSubTotal();
        }
        return total;
    }

    public double hitungPotongan() {
        if (diskonDipakai == null) return 0;
        double subTotal = hitungSubTotal();

        if (subTotal >= diskonDipakai.getMinimalBelanja()) {
            return subTotal * (diskonDipakai.getPersen() / 100.0);
        }

        return 0;
    }

    public double hitungTotalAkhir() {
        return hitungSubTotal() - hitungPotongan();
    }

    public String generateStruk() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        sb.append("=".repeat(40) + "\n");
        sb.append(String.format("Pelanggan : %s%n", namaPelanggan));
        sb.append(String.format("Waktu     : %s%n", waktu.format(fmt)));
        sb.append("=".repeat(40) + "\n");
        sb.append(String.format("%-25s %5s %15s%n", "Item", "Qty", "Subtotal"));
        sb.append("=".repeat(40) + "\n");

        for (OrderLine ol : lines) {
            sb.append(String.format("%-25s %5d Rp%,15.0f%n",
                    ol.getItem().getNama(), ol.getQty(), ol.getSubTotal()));
        }

        sb.append("=".repeat(40) + "\n");

        if (diskonDipakai != null) {
            sb.append(String.format("%-25s    -Rp %,15.0f%n", "Potongan", hitungPotongan()));
        }

        sb.append(String.format("%-25s    Rp %,15.0f%n", "Total Bayar", hitungTotalAkhir()));
        sb.append("=".repeat(40) + "\n");

        return sb.toString();
    }
}
