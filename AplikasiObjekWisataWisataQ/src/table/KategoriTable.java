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
import java.util.ArrayList;
import model.Kategori;

/**
 *
 * @author Ahya Ghifari
 */

// CLASS KATEGORI TABLE
public class KategoriTable {
    
    // inisialisai properti model kategori, koneksi, statement dan result set
    Kategori kategori;
    
    Koneksi koneksi;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    // konstruktor dan mempersiapkan koneksi
    public KategoriTable(){
        koneksi = new Koneksi();
        conn = koneksi.getConnection();
    }
    
    // tampilKategori
    public ArrayList<Kategori> tampilKategori(String keyword){
        ArrayList<Kategori> listKategori = new ArrayList<>();
        
        String query = "SELECT * FROM kategori";
        
        if(!keyword.equals("")){
            query = query + " WHERE nama_kategori LIKE '%" + keyword + "%'"; 
        }
        
        try{
      
            ps = conn.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                kategori = new Kategori(rs.getString("id_kategori"), 
                        rs.getString("nama_kategori"), 
                        rs.getString("deskripsi_kategori"), 
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listKategori.add(kategori);
            }
            
        }catch(SQLException | NullPointerException ex){
            System.err.println("ERROR tampilKategori : " + ex);
        }
        
        
        return listKategori;
    }
    
    // tambahKategori
    public boolean tambahKategori(Kategori kategori){
        
        String query = "INSERT INTO kategori (id_kategori, nama_kategori, deskripsi_kategori) VALUES (?, ?, ?)";
        
        try{
            ps = conn.prepareStatement(query);
            
            ps.setString(1, kategori.getIdKategori());
            ps.setString(2, kategori.getNamaKategori());
            ps.setString(3, kategori.getDeskripsiKategori());
            
            ps.executeUpdate();
            
            return true;
            
        }catch(SQLException ex){
            System.err.println("ERROR tambahKategori : " + ex);
            return false;
            
        }
        
    }
   
    // editKategori
    public boolean editKategori(Kategori kategori){
        
        String query = "UPDATE kategori SET nama_kategori=?, deskripsi_kategori=? WHERE id_kategori=?";
        
        try{
            ps = conn.prepareStatement(query);
            
            
            ps.setString(1, kategori.getNamaKategori());
            ps.setString(2, kategori.getDeskripsiKategori());
            ps.setString(3, kategori.getIdKategori());
            
            ps.executeUpdate();
            
            return true;
            
        }catch(SQLException ex){
            System.err.println("ERROR editKategori : " + ex);
            return false;
            
        }
        
    }
    
    // hapusKategori
    public String hapusKategori(String idKategori){
        
        String response = "";
        
        // mengecek apakah kategori sudah pernah digunakan oleh suatu objek wisata
        String checkKategori = "SELECT id_objek_wisata FROM objek_wisata WHERE kategori='" + idKategori + "'";
        
        boolean exist = false;
        try{
            ps = conn.prepareStatement(checkKategori);
            rs = ps.executeQuery();
            
            if(rs.next()){
                exist = true;
                response = "Kategori sedang digunakan";
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            response = "FAILED";
            System.err.println("ERROR hapusKategori-checkKategori : " + ex);
        }
        
        if(exist == false){
            String query = "DELETE FROM kategori WHERE id_kategori='" + idKategori + "'";
            
            try{
                
                ps = conn.prepareStatement(query);
                ps.executeUpdate();
                
                response = "SUCCESS";
                
                rs.close();
                ps.close();
            }catch(SQLException ex){
                response = "FAILED";
                System.err.println("ERROR hapusKategori : " + ex);
            }
        }
        
        return response;
    }
    
    // method untuk membuat id baru untuk kategori
    public String getIdBaru(){
        
        String lastId = "";
        String newId = "";
        
        // mengambil data terakhir pada kategori
        String query = "SELECT id_kategori FROM kategori ORDER BY id_kategori DESC LIMIT 1";
        
        try{
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
           while(rs.next()){
              lastId = rs.getString("id_kategori"); // mengambil id terakhir
           }
        }catch(SQLException ex){
            lastId = "";
            newId = "";
            System.err.println("ERROR getIdBaru() : " + ex);
        }
        
        if(!lastId.equals("")){
            // mengambil 4 huruf terakhir id
            lastId = lastId.substring(4);
            
            // mengubah ke nomor
            int idToNumber = Integer.parseInt(lastId);
            int incrementNumber = idToNumber + 1; // menambahkan nomor terakhir yang sudah dinomori
            String toStringNumber = String.valueOf(incrementNumber); // membalikkan nomor ke string
              
            // menambahkan angka 0 sesuai panjang dari id
            if(toStringNumber.length() == 1){
                newId = "KTGR00" + toStringNumber;
            }else if(toStringNumber.length() == 2){
                newId = "KTGR0" + toStringNumber;
            }else{
                newId = "KTGR" + toStringNumber;
            }  
        }
        
        return newId;
    }
    
    // METHOD untuk menampilkan jumlah kategori pada halaman dashboard
    public int dashboardCount(){
        int count = 0;
        try{
            
            ps = conn.prepareStatement("SELECT COUNT(id_kategori) as jml_kategori FROM kategori");
            rs = ps.executeQuery();
            
            while(rs.next()){
                count = Integer.parseInt(rs.getString("jml_kategori"));
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            System.err.println("ERROR dashboardCount : " + ex);
        }
        
        return count;
    }
    
}
