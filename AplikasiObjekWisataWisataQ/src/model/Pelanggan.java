/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ahya Ghifari
 */
public class Pelanggan {
    private int idPelanggan;
    private String namaPelanggan;
    private String alamat;
    private String noTelepon;
    private String email;
    private String createdAt;
    private String updatedAt;
    private int jumlahTransaksi;
    private String level;
    
    public Pelanggan(){
        
    }

    public Pelanggan(int idPelanggan, String namaPelanggan, String alamat, String noTelepon, String email) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.email = email;
    }

    public Pelanggan(int idPelanggan, String namaPelanggan, String alamat, String noTelepon, String email, int jumlahTransaksi, String level, String createdAt, String updatedAt) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.email = email;
        this.jumlahTransaksi = jumlahTransaksi;
        this.level = level;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    

    public int getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(int jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }

    
    
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
    
}
