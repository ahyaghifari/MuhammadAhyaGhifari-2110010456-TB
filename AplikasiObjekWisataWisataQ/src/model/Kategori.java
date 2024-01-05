/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ahya Ghifari
 */
public class Kategori {
    private String idKategori;
    private String namaKategori;
    private String deskripsiKategori;
    private String createdAt;
    private String updatedAt;

    public Kategori() {
    }

    public Kategori(String idKategori, String namaKategori, String deskripsiKategori, String createdAt, String updatedAt) {
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.deskripsiKategori = deskripsiKategori;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Kategori(String idKategori, String namaKategori, String deskripsiKategori) {
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.deskripsiKategori = deskripsiKategori;
    }

    
    
    public String getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namKategori) {
        this.namaKategori = namKategori;
    }

    public String getDeskripsiKategori() {
        return deskripsiKategori;
    }

    public void setDeskripsiKategori(String deskripsiKategori) {
        this.deskripsiKategori = deskripsiKategori;
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
