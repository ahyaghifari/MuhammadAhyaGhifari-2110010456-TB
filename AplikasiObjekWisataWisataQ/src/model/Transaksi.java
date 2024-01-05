/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ahya Ghifari
 */
public class Transaksi {
    private int idTransaksi;
    private String kodeTransaksi;
    private String tanggalWisata;
    private int jumlahTiket;
    private double total;
    private String statusTransaksi;
    private ObjekWisata objekWisata;
    private Admin admin;
    private Pelanggan pelanggan;
    private Pembayaran pembayaran;
    private String createdAt;
    private String updatedAt;
    
    public Transaksi(){
        
    }

    public Transaksi(int idTransaksi, String kodeTransaksi, String tanggalWisata, int jumlahTiket, double total, String statusTransaksi, String createdAt, String updatedAt, 
            String idObjekWisata, String namaObjekWisata, String lokasi, int idPelanggan, String namaPelanggan, int idAdmin, String namaAdmin, int idPembayaran, String namaPembayaran) {
        this.idTransaksi = idTransaksi;
        this.kodeTransaksi = kodeTransaksi;
        this.tanggalWisata = tanggalWisata;
        this.jumlahTiket = jumlahTiket;
        this.total = total;
        this.statusTransaksi = statusTransaksi;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        
        objekWisata = new ObjekWisata();
        objekWisata.setIdObjekWisata(idObjekWisata);
        objekWisata.setNamaObjekWisata(namaObjekWisata);
        objekWisata.setLokasi(lokasi);
        
        pelanggan = new Pelanggan();
        pelanggan.setIdPelanggan(idPelanggan);
        pelanggan.setNamaPelanggan(namaPelanggan);
        
        admin = new Admin();
        admin.setIdAdmin(idAdmin);
        admin.setNamaAdmin(namaAdmin);
        
        pembayaran = new Pembayaran();
        pembayaran.setIdPembayaran(idPembayaran);
        pembayaran.setNamaPembayaran(namaPembayaran);
        
    }    
    

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getTanggalWisata() {
        return tanggalWisata;
    }

    public void setTanggalWisata(String tanggalWisata) {
        this.tanggalWisata = tanggalWisata;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatusTransaksi() {
        return statusTransaksi;
    }

    public void setStatusTransaksi(String statusTransaksi) {
        this.statusTransaksi = statusTransaksi;
    }

    public ObjekWisata getObjekWisata() {
        return objekWisata;
    }

    public void setObjekWisata(ObjekWisata objekWisata) {
        this.objekWisata = objekWisata;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Pembayaran pembayaran) {
        this.pembayaran = pembayaran;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    
    
}
