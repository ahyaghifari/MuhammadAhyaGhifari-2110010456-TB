/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ahya Ghifari
 */
public class PromoDiskon {
    private int idPromoDiskon;
    private int presentaseDiskon;
    private String level;
    private String tanggalTersedia;
    private String tanggalBerakhir;
    private String createdAt;
    private String updatedAt;
    
    public PromoDiskon(){}

    public PromoDiskon(int idPromoDiskon, int presentaseDiskon, String level, String tanggalTersedia, String tanggalBerakhir, String createdAt, String updatedAt) {
        this.idPromoDiskon = idPromoDiskon;
        this.presentaseDiskon = presentaseDiskon;
        this.level = level;
        this.tanggalTersedia = tanggalTersedia;
        this.tanggalBerakhir = tanggalBerakhir;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getIdPromoDiskon() {
        return idPromoDiskon;
    }

    public void setIdPromoDiskon(int idPromoDiskon) {
        this.idPromoDiskon = idPromoDiskon;
    }

    public int getPresentaseDiskon() {
        return presentaseDiskon;
    }

    public void setPresentaseDiskon(int presentaseDiskon) {
        this.presentaseDiskon = presentaseDiskon;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTanggalTersedia() {
        return tanggalTersedia;
    }

    public void setTanggalTersedia(String tanggalTersedia) {
        this.tanggalTersedia = tanggalTersedia;
    }

    public String getTanggalBerakhir() {
        return tanggalBerakhir;
    }

    public void setTanggalBerakhir(String tanggalBerakhir) {
        this.tanggalBerakhir = tanggalBerakhir;
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
