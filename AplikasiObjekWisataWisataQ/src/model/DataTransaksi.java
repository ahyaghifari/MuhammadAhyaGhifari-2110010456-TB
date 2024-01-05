package model;

public class DataTransaksi {
    private String tanggal;
    private int transaksiSelesai;
    private int transaksiBatal;
    private double total;
    private String totalRupiah;
    
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getTransaksiSelesai() {
        return transaksiSelesai;
    }

    public void setTransaksiSelesai(int transaksiSelesai) {
        this.transaksiSelesai = transaksiSelesai;
    }

    public int getTransaksiBatal() {
        return transaksiBatal;
    }

    public void setTransaksiBatal(int transaksiBatal) {
        this.transaksiBatal = transaksiBatal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTotalRupiah() {
        return totalRupiah;
    }

    public void setTotalRupiah(String totalRupiah) {
        this.totalRupiah = totalRupiah;
    }
    
    
    
}
