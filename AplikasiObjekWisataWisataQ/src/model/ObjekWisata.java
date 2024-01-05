/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Blob;

/**
 *
 * @author Ahya Ghifari
 */
public class ObjekWisata {
    private String idObjekWisata;
    private String namaObjekWisata;
    private Blob gambarObjekWisata;
    private String lokasi;
    private String deskripsiObjekWisata;
    private int ketersediaanTiket;
    private double hargaTiket;
    private String jamOperasional;
    private Kategori kategori;
    private String created_at;
    private String updated_at;

    private int jmlTransaksi;
    
    public ObjekWisata() {
    }

    public ObjekWisata(String idObjekWisata, String namaObjekWisata, Blob gambarObjekWisata, String lokasi, String deskripsiObjekWisata, int ketersediaanTiket, double hargaTiket, String jamOperasional, String idKategori, String namaKategori, String created_at, String updated_at) {
        this.idObjekWisata = idObjekWisata;
        this.namaObjekWisata = namaObjekWisata;
        this.gambarObjekWisata = gambarObjekWisata;
        this.lokasi = lokasi;
        this.deskripsiObjekWisata = deskripsiObjekWisata;
        this.ketersediaanTiket = ketersediaanTiket;
        this.hargaTiket = hargaTiket;
        this.jamOperasional = jamOperasional;
                
        kategori = new Kategori();
        kategori.setIdKategori(idKategori);
        kategori.setNamaKategori(namaKategori);
        
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getIdObjekWisata() {
        return idObjekWisata;
    }

    public void setIdObjekWisata(String id) {
        this.idObjekWisata = id;
    }

    public String getNamaObjekWisata() {
        return namaObjekWisata;
    }

    public void setNamaObjekWisata(String nama) {
        this.namaObjekWisata = nama;
    }

    public Blob getGambarObjekWisata() {
        return gambarObjekWisata;
    }

    public void setGambarObjekWisata(Blob gambarObjekWisata) {
        this.gambarObjekWisata = gambarObjekWisata;
    }
    
    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsiObjekWisata() {
        return deskripsiObjekWisata;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsiObjekWisata = deskripsi;
    }

    public int getKetersediaanTiket() {
        return ketersediaanTiket;
    }

    public void setKetersediaanTiket(int ketersediaanTiket) {
        this.ketersediaanTiket = ketersediaanTiket;
    }
    
    public double getHargaTiket() {
        return hargaTiket;
    }

    public void setHargaTiket(double hargaTiket) {
        this.hargaTiket = hargaTiket;
    }

    public String getJamOperasional() {
        return jamOperasional;
    }

    public void setJamOperasional(String jamOperasional) {
        this.jamOperasional = jamOperasional;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getJmlTransaksi() {
        return jmlTransaksi;
    }

    public void setJmlTransaksi(int jmlTransaksi) {
        this.jmlTransaksi = jmlTransaksi;
    }
    
    
}
