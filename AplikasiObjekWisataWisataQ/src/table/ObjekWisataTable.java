/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table;

import model.ObjekWisata;
import db.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Kategori;

/**
 *
 * @author Ahya Ghifari
 */

// CLASS OBJEK WISATA TABLE
public class ObjekWisataTable {
    
    // inisialisai properti model objek wisata, koneksi, statement dan result set
    ObjekWisata objekWisata;
    
    Koneksi koneksi;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    // konstuktor dan mngisi koneksi
    public ObjekWisataTable(){
        koneksi = new Koneksi();
        conn = koneksi.getConnection();
    }
     
    
    // tampilObjekWisata
    public ArrayList<ObjekWisata> tampilObjekWisata(String keyword){
        ArrayList<ObjekWisata> listObjekWisata = new ArrayList<>();    
        
        String query = "SELECT * FROM objek_wisata INNER JOIN kategori ON objek_wisata.kategori=kategori.id_kategori";
        
        if(!keyword.equals("")){
            query += " WHERE nama_objek_wisata LIKE '%" + keyword + "%'"
                    + " OR lokasi LIKE '%" + keyword + "%' "
                    + " OR nama_kategori LIKE '%" + keyword +"%'";
        }
        
        try{
      
            ps = conn.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                objekWisata = new ObjekWisata(
                        rs.getString("id_objek_wisata"), 
                        rs.getString("nama_objek_wisata"), 
                        rs.getBlob("gambar_objek_wisata"),
                        rs.getString("lokasi"),
                        rs.getString("deskripsi_objek_wisata"),
                        Integer.parseInt(rs.getString("ketersediaan_tiket")),
                        rs.getDouble("harga_tiket"),
                        rs.getString("jam_operasional"),
                        rs.getString("id_kategori"),
                        rs.getString("nama_kategori"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listObjekWisata.add(objekWisata);
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException | NullPointerException ex){
            System.err.println("ERROR tampilObjekWisata : " + ex);
        }
        
        return listObjekWisata;
    }
    
    // tambahObjekWisata
    public boolean tambahObjekWisata(ObjekWisata objekWisata){
        String query = "INSERT INTO objek_wisata (id_objek_wisata, nama_objek_wisata, gambar_objek_wisata, lokasi, deskripsi_objek_wisata,"
                + " ketersediaan_tiket, harga_tiket, jam_operasional, kategori) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try{
            ps = conn.prepareStatement(query);
            
            ps.setString(1, objekWisata.getIdObjekWisata());
            ps.setString(2, objekWisata.getNamaObjekWisata());
            ps.setBlob(3, objekWisata.getGambarObjekWisata());
            ps.setString(4, objekWisata.getLokasi());
            ps.setString(5, objekWisata.getDeskripsiObjekWisata());
            ps.setInt(6, objekWisata.getKetersediaanTiket());
            ps.setDouble(7, objekWisata.getHargaTiket());
            ps.setString(8, objekWisata.getJamOperasional());
            ps.setString(9, objekWisata.getKategori().getIdKategori());
            
            ps.executeUpdate();
            
            return true;
            
        }catch(SQLException ex){
            System.err.println("ERROR tambahKategori : " + ex);
            return false;
            
        }
        
    }
    
    // editObjekWisata
    public boolean editObjekWisata(ObjekWisata objekWisata){
        
        String query = "UPDATE objek_wisata SET nama_objek_wisata=?, gambar_objek_wisata=?, lokasi=?, deskripsi_objek_wisata=?, ketersediaan_tiket=?,"
                + " harga_tiket=?, jam_operasional=?, kategori=? WHERE id_objek_wisata=?";
        
        try{
            ps = conn.prepareStatement(query);
           
            ps.setString(1, objekWisata.getNamaObjekWisata());
            ps.setBlob(2, objekWisata.getGambarObjekWisata());
            ps.setString(3, objekWisata.getLokasi());
            ps.setString(4, objekWisata.getDeskripsiObjekWisata());
            ps.setInt(5, objekWisata.getKetersediaanTiket());
            ps.setDouble(6, objekWisata.getHargaTiket());
            ps.setString(7, objekWisata.getJamOperasional());
            ps.setString(8, objekWisata.getKategori().getIdKategori());
            ps.setString(9, objekWisata.getIdObjekWisata());
            
            ps.executeUpdate();
            
            return true;
            
        }catch(SQLException ex){
            System.err.println("ERROR editObjekWisata : " + ex);
            return false;
            
        }
        
    }
    
    // haousObjekWisata
    public String hapusObjekWisata(String id){
        String response = "";
        
        // mengecek apakah objek wisata sudah ada ditransaksi
        String checkObjekWisata = "SELECT id_objek_wisata FROM transaksi WHERE id_objek_wisata='" + id + "'";
        
        boolean exist = false;
        try{
            ps = conn.prepareStatement(checkObjekWisata);
            rs = ps.executeQuery();
            
            if(rs.next()){
                exist = true;
                // jika objek wisata sudah ada di transaksi maka objek wisata tidak bisa dihapus
                response = "Objek Wisata sudah pernah di transaksi kan";
            }
            
        }catch(SQLException ex){
            response = "FAILED";
            System.err.println("ERROR hapusObjekWisata-checkObjekWisata : " + ex);
        }
        
        // jika belum ada maka lanjut hapus
        if(exist == false){
            String query = "DELETE FROM objek_wisata WHERE id_objek_wisata='" + id + "'";
            
            try{
                
                ps = conn.prepareStatement(query);
                ps.executeUpdate();
                
                response = "SUCCESS";
                
                rs.close();
            }catch(SQLException ex){
                response = "FAILED";
                System.err.println("ERROR hapusObjekWisata : " + ex);
            }
        }
        
        return response;
    }
    
    // method untuk membuat id baru untuk kategori
    public String getIdBaru(){
        
        String lastId = "";
        String newId = "";
        
        // mengambil data terakhir pada objek wisata
        String query = "SELECT id_objek_wisata FROM objek_wisata ORDER BY id_objek_wisata DESC LIMIT 1";
        
        try{
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
           while(rs.next()){
              lastId = rs.getString("id_objek_wisata"); // mengambil id dari data terakhir
           }
        }catch(SQLException ex){
            lastId = "";
            newId = "";
            System.err.println("ERROR getIdBaru() : " + ex);
        }
        
        if(!lastId.equals("")){
            lastId = lastId.substring(3); // mengambil 3 huruf terakhir dari id
            
            // mengubah ke nomor
            int idToNumber = Integer.parseInt(lastId);
            int incrementNumber = idToNumber + 1; // menambah nomor id
            String toStringNumber = String.valueOf(incrementNumber); // mengubah kembali ke string
              
            // menambahkan angka 0 sesuai panjang dari id
            if(toStringNumber.length() == 1){
                newId = "OBJ00" + toStringNumber;
            }else if(toStringNumber.length() == 2){
                newId = "OBJ0" + toStringNumber;
            }else{
                newId = "OBJ" + toStringNumber;
            }  
        }
        
        return newId;
    }
    
    // METHOD untuk menampilkan jumlah objek wisata pada halaman dashboard
    public int dashboardCount(){
        int count = 0;
        try{
            
            ps = conn.prepareStatement("SELECT COUNT(id_objek_wisata) as jml_objek_wisata FROM objek_wisata");
            rs = ps.executeQuery();
            
            while(rs.next()){
                count = Integer.parseInt(rs.getString("jml_objek_wisata"));
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            System.err.println("ERROR dashboardCount : " + ex);
        }
        
        return count;
    }
    
    // METHOD khusus untuk manajer melihat presentase transaksi dari objek wisata perbulannya
    public ArrayList<ObjekWisata> presentaseTransaksi(String bulan, String tahun, boolean existOnly){
        
        String joinTransaksi = "INNER JOIN";
        if(existOnly == false)
            joinTransaksi = "LEFT JOIN";
        
        String whereTransaksi = "WHERE MONTH(created_at)='" + bulan +"' AND YEAR(created_at)='"+ tahun +"'";

        if(bulan.equals("")){
            whereTransaksi = "WHERE YEAR(created_at)='"+ tahun +"'";
        }
        
        String query = "SELECT objek_wisata.*, kategori.*, t.jml_transaksi, t.transaksi_created FROM objek_wisata INNER JOIN kategori ON objek_wisata.kategori=kategori.id_kategori "
                + joinTransaksi + " (SELECT id_objek_wisata, COUNT(transaksi.kode_transaksi) as jml_transaksi, created_at as transaksi_created "
                + "FROM transaksi " + whereTransaksi +" GROUP BY id_objek_wisata) as t ON objek_wisata.id_objek_wisata=t.id_objek_wisata ORDER BY t.jml_transaksi DESC ";
        
        ArrayList<ObjekWisata> list = new ArrayList();
        
        try{
            
           ps = conn.prepareStatement(query);
           rs = ps.executeQuery();
           
           while(rs.next()){
               ObjekWisata ow = new ObjekWisata(
                       rs.getString("id_objek_wisata"), 
                        rs.getString("nama_objek_wisata"), 
                        rs.getBlob("gambar_objek_wisata"),
                        rs.getString("lokasi"),
                        rs.getString("deskripsi_objek_wisata"),
                        Integer.parseInt(rs.getString("ketersediaan_tiket")),
                        rs.getDouble("harga_tiket"),
                        rs.getString("jam_operasional"),
                        rs.getString("id_kategori"),
                        rs.getString("nama_kategori"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
               );
               ow.setJmlTransaksi(rs.getInt("jml_transaksi"));
               
               
               list.add(ow);
           }
           
        }catch(SQLException ex){
            System.err.println("ERROR presentaseTransaksi : " + ex);  
        }
        
        return list;
    }
    
}
