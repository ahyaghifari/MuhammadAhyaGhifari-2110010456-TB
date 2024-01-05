/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ahya Ghifari
 */
public class Pembayaran {
    private int idPembayaran;
    private String namaPembayaran;
    private boolean penggunaan;
    private String createdAt;
    private String updatedAt;
    
   public Pembayaran(){
       
   }

    public Pembayaran(int idPembayaran, String namaPembayaran, boolean penggunaan) {
        this.idPembayaran = idPembayaran;
        this.namaPembayaran = namaPembayaran;
        this.penggunaan = penggunaan;
    }

    public Pembayaran(int idPembayaran, String namaPembayaran, boolean penggunaan, String createdAt, String updatedAt) {
        this.idPembayaran = idPembayaran;
        this.namaPembayaran = namaPembayaran;
        this.penggunaan = penggunaan;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public String getNamaPembayaran() {
        return namaPembayaran;
    }

    public void setNamaPembayaran(String namaPembayaran) {
        this.namaPembayaran = namaPembayaran;
    }

    public boolean isPenggunaan() {
        return penggunaan;
    }

    public void setPenggunaan(boolean penggunaan) {
        this.penggunaan = penggunaan;
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
