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
import model.Pembayaran;

/**
 *
 * @author Ahya Ghifari
 */
public class PembayaranTable {

    Pembayaran pembayaran;
    
    Koneksi koneksi;
    Connection conn;
    
    PreparedStatement ps;
    ResultSet rs;
    
    public PembayaranTable(){
        koneksi = new Koneksi();
        conn = koneksi.getConnection();
    }
    
    public ArrayList<Pembayaran> tampilPembayaran(boolean activeOnly){
        ArrayList<Pembayaran> listPembayaran = new ArrayList<>();
        
        String query = "SELECT * FROM pembayaran";
        
        if(activeOnly == true){
            query = query + " WHERE penggunaan='1'"; 
        }
        
        try{
      
            ps = conn.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                pembayaran = new Pembayaran(Integer.parseInt(rs.getString("id_pembayaran")), 
                        rs.getString("nama_pembayaran"), 
                        rs.getBoolean("penggunaan"),
                        rs.getString("created_at"),
                        rs.getString("updated_at"));
                
                listPembayaran.add(pembayaran);
            }
            
        }catch(SQLException | NullPointerException ex){
            System.err.println("ERROR tampilPembayaran : " + ex);
        }
        
        
        return listPembayaran;
    }
    
    public boolean tambahPembayaran(Pembayaran pembayaran){
        
        boolean response;
        String query = "INSERT INTO pembayaran (nama_pembayaran, penggunaan) VALUES (?, ?)";
        
        try{
            
            ps = conn.prepareStatement(query);
            ps.setString(1, pembayaran.getNamaPembayaran());
            ps.setBoolean(2, pembayaran.isPenggunaan());
            
            ps.executeUpdate();
            
            response = true;
            
        }catch(SQLException ex){
            response = true;
            System.err.println("ERROR tambahPembayaran : " + ex);
        }
        
        return response;
    }
    
    public boolean editPembayaran(Pembayaran pembayaran){
        
        boolean response;
        String query = "UPDATE pembayaran SET nama_pembayaran=?, penggunaan=? WHERE id_pembayaran=?";
        
        try{
            
            ps = conn.prepareStatement(query);
            ps.setString(1, pembayaran.getNamaPembayaran());
            ps.setBoolean(2, pembayaran.isPenggunaan());
            ps.setInt(3, pembayaran.getIdPembayaran());
            
            ps.executeUpdate();
            
            response = true;
            
        }catch(SQLException ex){
            response = true;
            System.err.println("ERROR editPembayaran : " + ex);
        }
        
        return response;
    }

    public String hapusPembayaran(String id){
        
        String response = "";
        
        String checkPembayaran = "SELECT id_pembayaran FROM transaksi WHERE id_pembayaran='" + id + "'";
        
        boolean exist = false;
        try{
            ps = conn.prepareStatement(checkPembayaran);
            rs = ps.executeQuery();
            
            if(rs.next()){
                exist = true;
                response = "Pembayaran sudah pernah dipakai";
            }
            
        }catch(SQLException ex){
            response = "FAILED";
            System.err.println("ERROR hapusPembayaran-checkPembayaran : " + ex);
        }
        
        if(exist == false){
            String query = "DELETE FROM pembayaran WHERE id_pembayaran='" + id + "'";
            
            try{
                
                ps = conn.prepareStatement(query);
                ps.executeUpdate();
                
                response = "SUCCESS";
                
                rs.close();
            }catch(SQLException ex){
                response = "FAILED";
                System.err.println("ERROR hapusPembayaran : " + ex);
            }
        }
        
        return response;
    }
    
}
