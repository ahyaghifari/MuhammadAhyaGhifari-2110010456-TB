/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ahya Ghifari
 */
public class Admin {
    
    private int idAdmin;
    private String username;
    private String namaAdmin;
    private String tanggalLahir;
    private String alamat;
    private String noTelepon;
    private String email;
    private String jenisKelamin;
    private String jabatan;
    private String password;
    private String createdAt;
    private String updatedAt;
 
    public Admin(){
        
    }

    public Admin(int idAdmin, String username, String namaAdmin, String tanggalLahir, String alamat, String noTelepon, String email, String jenisKelamin, String jabatan) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.namaAdmin = namaAdmin;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.jabatan = jabatan;
    }

    public Admin(int idAdmin, String username, String namaAdmin, String tanggalLahir, String alamat, String noTelepon, String email, String jenisKelamin, String jabatan, String password) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.namaAdmin = namaAdmin;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.jabatan = jabatan;
        this.password = password;
    }

    public Admin(int idAdmin, String username, String namaAdmin, String tanggalLahir, String alamat, String noTelepon, String email, String jenisKelamin, String jabatan, String password, String createdAt, String updatedAt) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.namaAdmin = namaAdmin;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.jabatan = jabatan;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNamaAdmin() {
        return namaAdmin;
    }

    public void setNamaAdmin(String namaAdmin) {
        this.namaAdmin = namaAdmin;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
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

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
