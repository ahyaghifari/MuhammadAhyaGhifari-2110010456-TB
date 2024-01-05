/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table;

import db.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Transaksi;
import java.util.Date;
import model.DataTransaksi;
import service.KonversiRupiah;

/**
 *
 * @author Ahya Ghifari
 */
public class TransaksiTable {
    
    Connection conn;
    Koneksi koneksi;
    
    PreparedStatement ps;
    ResultSet rs;
    
    public TransaksiTable(){
        koneksi = new Koneksi();
        conn = koneksi.getConnection();
    }
    
    public ArrayList<Transaksi> tampilTransaksi(String keyword, String limit){
        ArrayList<Transaksi> list = new ArrayList();
    
        String whereQuery = "";
        String limitQuery = "";
        
        if(!limit.equals("Semua")){
            limitQuery = "LIMIT " + limit;
        }
        
        if(!keyword.equals("")){
            whereQuery = " WHERE transaksi.kode_transaksi LIKE '%" + keyword + "%'"
                        + " OR pelanggan.nama_pelanggan LIKE '%" + keyword + "%'"
                        + " OR objek_wisata.nama_objek_wisata LIKE '%" + keyword + "%'"
                        + " OR admin.nama_admin LIKE '%" + keyword + "%'"
                        + " OR transaksi.created_at LIKE '%" + keyword + "%'"; 
        }
        
        String query = "SELECT transaksi.*, " 
                       + "objek_wisata.id_objek_wisata, objek_wisata.nama_objek_wisata, objek_wisata.lokasi,"
                       + " pelanggan.id_pelanggan, pelanggan.nama_pelanggan,"
                       + " admin.id_admin, admin.nama_admin,"
                       + " pembayaran.id_pembayaran, pembayaran.nama_pembayaran"
                       + " FROM transaksi"
                       + " INNER JOIN objek_wisata ON transaksi.id_objek_wisata=objek_wisata.id_objek_wisata"
                       + " INNER JOIN pelanggan ON transaksi.id_pelanggan=pelanggan.id_pelanggan"
                       + " INNER JOIN admin ON transaksi.id_admin=admin.id_admin"
                       + " INNER JOIN pembayaran ON transaksi.id_pembayaran=pembayaran.id_pembayaran"
                       + whereQuery
                       + " ORDER BY transaksi.created_at DESC " + limitQuery;
        
        try{
            ps = conn.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                Transaksi transaksi = new Transaksi(Integer.parseInt(
                        rs.getString("id_transaksi")), 
                        rs.getString("kode_transaksi"),
                        rs.getString("tanggal_wisata"), 
                        Integer.parseInt(rs.getString("jumlah_tiket")), 
                        Double.parseDouble(rs.getString("total_harga")), 
                        rs.getString("status_transaksi"),
                        rs.getString("created_at"),
                        rs.getString("updated_at"), 
                        rs.getString("id_objek_wisata"),
                        rs.getString("nama_objek_wisata"), 
                        rs.getString("lokasi"),
                        Integer.parseInt(rs.getString("id_pelanggan")),
                        rs.getString("nama_pelanggan"), 
                        Integer.parseInt(rs.getString("id_admin")), 
                        rs.getString("nama_admin"), 
                        Integer.parseInt(rs.getString("id_pembayaran")), 
                        rs.getString("nama_pembayaran")
                );
                
                list.add(transaksi);
            }
            
            ps.close();
            rs.close();
            
        }catch(SQLException | NullPointerException ex){
            System.err.println("ERROR tampilTransaksi : " + ex);
        }
        
       
        
        return list;
    }
   
    public boolean tambahTransaksi(Transaksi transaksi){
        String query = "INSERT INTO transaksi (kode_transaksi, tanggal_wisata, jumlah_tiket, "
                + " total_harga, status_transaksi, id_objek_wisata, id_pelanggan, id_admin, id_pembayaran) VALUES"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try{
            
            ps = conn.prepareStatement(query);
            
            ps.setString(1, transaksi.getKodeTransaksi());
            ps.setString(2, transaksi.getTanggalWisata());
            ps.setInt(3, transaksi.getJumlahTiket());
            ps.setDouble(4, transaksi.getTotal());
            ps.setString(5, transaksi.getStatusTransaksi());
            ps.setString(6, transaksi.getObjekWisata().getIdObjekWisata());
            ps.setInt(7, transaksi.getPelanggan().getIdPelanggan());
            ps.setInt(8, transaksi.getAdmin().getIdAdmin());
            ps.setInt(9, transaksi.getPembayaran().getIdPembayaran());
          
            ps.executeUpdate();
            
            return true;
            
        }catch(SQLException ex){
            System.err.println("ERROR tambahTransaksi() : " + ex);
            return false;
        }
        
    }
    
    public boolean ubahTransaksi(Transaksi transaksi){
        String query = "UPDATE transaksi SET tanggal_wisata=?, jumlah_tiket=?, total_harga=?, status_transaksi=?,"
                + " id_objek_wisata=?, id_pelanggan=?, id_admin=?, id_pembayaran=? WHERE kode_transaksi=?";
        
        try{
            
            ps = conn.prepareStatement(query);
            
            
            ps.setString(1, transaksi.getTanggalWisata());
            ps.setInt(2, transaksi.getJumlahTiket());
            ps.setDouble(3, transaksi.getTotal());
            ps.setString(4, transaksi.getStatusTransaksi());
            ps.setString(5, transaksi.getObjekWisata().getIdObjekWisata());
            ps.setInt(6, transaksi.getPelanggan().getIdPelanggan());
            ps.setInt(7, transaksi.getAdmin().getIdAdmin());
            ps.setInt(8, transaksi.getPembayaran().getIdPembayaran());
            ps.setString(9, transaksi.getKodeTransaksi());
          
            ps.executeUpdate();
            
            return true;
            
        }catch(SQLException ex){
            System.err.println("ERROR ubahTransaksi() : " + ex);
            return false;
        }
        
    }
    
    public String generateKodeTransaksi(){

        Date tanggalHariIni = new Date();
        String formattedTanggal = new SimpleDateFormat("yyMMdd").format(tanggalHariIni);
        
        String query = "SELECT kode_transaksi FROM transaksi ORDER BY id_transaksi DESC LIMIT 1";
        String lastKode = "";
        
        try{
           
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
           
            if(rs.next()){
                lastKode = rs.getString("kode_transaksi");
            }
            
        }catch(SQLException ex){
            lastKode = "";
            System.err.println("ERROR generateKodeTransaksi() : " + ex);
        }
        
        String newKodeTransaksi = "";
        
        if(!lastKode.equals("")){
            String tanggalLastKode = lastKode.substring(0,6);
            
            if(tanggalLastKode.equals(formattedTanggal)){
                
                int angkaTerakhir = Integer.parseInt(lastKode.substring(6));
                int incrementAngkaTerakhir = angkaTerakhir + 1;
                String strAngkaTerakhir = String.valueOf(incrementAngkaTerakhir);
                
                if(strAngkaTerakhir.length() == 1){
                    newKodeTransaksi = formattedTanggal + "000" + strAngkaTerakhir;
                }else if(strAngkaTerakhir.length() == 2){
                    newKodeTransaksi = formattedTanggal + "00" + strAngkaTerakhir;
                }else if(strAngkaTerakhir.length() == 3){
                    newKodeTransaksi = formattedTanggal + "0" + strAngkaTerakhir;
                }else{
                    newKodeTransaksi = formattedTanggal + strAngkaTerakhir;
                }
                
            }else{
                newKodeTransaksi = formattedTanggal + "0001";
            }
            
        }

        return newKodeTransaksi;
    }
 
    public int tiketTersedia(String idObjekWisata, int ketersediaanTiket, String tanggal){
        int tiketDiambil = 0;
        
        try{
            String query = "SELECT kode_transaksi FROM transaksi WHERE id_objek_wisata=? AND tanggal_wisata=?";
            
            ps = conn.prepareStatement(query);
            ps.setString(1, idObjekWisata);
            ps.setString(2, tanggal);
            rs = ps.executeQuery();
            
            if(rs.next()){
                while(rs.next()){
                    tiketDiambil++;
                }
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            tiketDiambil = 0;
            System.err.println("ERROR tiketTersedia : " + ex);
        }
        
        
        int tiketTersedia = ketersediaanTiket - tiketDiambil;
        return tiketTersedia;
    }
    
    public int dashboardCount(){
        int count = 0;
        try{
            
            ps = conn.prepareStatement("SELECT COUNT(kode_transaksi) as jml_transaksi FROM transaksi WHERE"
                    + " status_transaksi='Selesai'");
            rs = ps.executeQuery();
            
            while(rs.next()){
                count = Integer.parseInt(rs.getString("jml_transaksi"));
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            System.err.println("ERROR dashboardCount : " + ex);
        }
        
        return count;
    }
  
    public ArrayList<String> getTahunTransaksi(){
        String query = "SELECT DISTINCT YEAR(transaksi.created_at) as tahun_transaksi FROM transaksi ORDER BY transaksi.created_at DESC";
        ArrayList<String> listTahun = new ArrayList();
        
        try{
            
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
            while(rs.next()){
                listTahun.add(rs.getString("tahun_transaksi"));
            }
            
        }catch(SQLException ex){
            System.err.println("ERROR getTahunTransaksi : " +ex);
        }
        
        return listTahun;
    }
    
    public ArrayList<DataTransaksi> getDataTransaksi(String bulan, String tahun){
        
        ArrayList<DataTransaksi> list = new ArrayList();
        
        String query = "SELECT DATE(created_at) as tanggal, COUNT(CASE WHEN status_transaksi='Selesai' THEN 1 END) AS transaksi_selesai, "
                + "COUNT(CASE WHEN status_transaksi='Batal' THEN 1 END) AS transaksi_batal, "
                + "IFNULL(SUM( CASE WHEN status_transaksi='Selesai' THEN  total_harga END), 0) AS total "
                + "FROM `transaksi` WHERE MONTH(created_at)=? AND YEAR(created_at)=? GROUP BY DATE(created_at)";
        
        try{
            
            ps = conn.prepareStatement(query);
            
            ps.setString(1, bulan);
            ps.setString(2, tahun);
            
            rs = ps.executeQuery();
            
            if(rs.isBeforeFirst()){
                while(rs.next()){
                    DataTransaksi dT = new DataTransaksi();
                    dT.setTanggal(rs.getString("tanggal"));
                    dT.setTransaksiSelesai(Integer.parseInt(rs.getString("transaksi_selesai")));
                    dT.setTransaksiBatal(Integer.parseInt(rs.getString("transaksi_batal")));
                    dT.setTotal(Double.parseDouble(rs.getString("total")));
                    dT.setTotalRupiah(KonversiRupiah.konversi(Double.parseDouble(rs.getString("total"))));
                    
                    list.add(dT);
                }
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            System.err.println("ERROR getDataTransaksi : " + ex);
        }
        
        return list;
    }
}
