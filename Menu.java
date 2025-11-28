package Tugas3;

import java.io.*;
import java.util.*;

// Mengelola seluruh item menu.
public class Menu {
    private ArrayList<MenuItem> daftar = new ArrayList<>();

    public void tambah(MenuItem item) {
        daftar.add(item);
    }

    public void hapusMenu(String nama) throws ItemNotFoundException {
        nama = nama.trim(); // penting!

        MenuItem target = null;

        for (MenuItem m : daftar) {
            if (m.getNama().trim().equalsIgnoreCase(nama)) {
                target = m;
                break;
            }
        }

        if (target == null) {
            throw new ItemNotFoundException("Menu '" + nama + "' tidak ditemukan.");
        }

        daftar.remove(target);
        System.out.println("Menu '" + nama + "' berhasil dihapus.");
    }


    public void tampilkanMenu() {
        if (daftar.isEmpty()) {
            System.out.println("Menu kosong.");
            return;
        }

        System.out.println("\n======= DAFTAR MENU =======");
        for (MenuItem m : daftar) m.tampilMenu();
        System.out.println("===========================\n");
    }

    public MenuItem cariByName(String nama) {
        for (MenuItem m : daftar) {
            if (m.getNama().equalsIgnoreCase(nama)) return m;
        }
        return null;
    }

    public void simpanKeFile(String file) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (MenuItem m : daftar) pw.println(m.toFileFormat());
            System.out.println("Menu berhasil disimpan.");
        } catch (Exception e) {
            System.out.println("Gagal simpan: " + e.getMessage());
        }
    }

    public void muatDariFile(String file) {
        File f = new File(file);
        if (!f.exists()) {
            System.out.println("File tidak ditemukan.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");

                if (p.length == 0) continue;

                switch (p[0].toUpperCase()) {

                    case "MAKANAN":
                        // MAKANAN;nama;harga;kategori;jenisMakanan
                        tambah(new Makanan(
                                p[1],
                                Double.parseDouble(p[2]),
                                p[3],
                                p[4]
                        ));
                        break;

                    case "MINUMAN":
                        // MINUMAN;nama;harga;kategori;jenisMinuman
                        tambah(new Minuman(
                                p[1],
                                Double.parseDouble(p[2]),
                                p[3],
                                p[4]
                        ));
                        break;


                    case "DISKON":
                        tambah(new Diskon(p[1], Double.parseDouble(p[2]), Double.parseDouble(p[3])));
                        break;
                }
            }

            System.out.println("Menu berhasil dimuat.");

        } catch (Exception e) {
            System.out.println("Gagal muat: " + e.getMessage());
        }
    }

}