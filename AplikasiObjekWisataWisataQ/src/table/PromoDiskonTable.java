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
import javax.swing.JOptionPane;
import model.PromoDiskon;

/**
 *
 * @author Ahya Ghifari
 */
public class PromoDiskonTable {

    PromoDiskon promoDiskon;
    
    Koneksi koneksi;
    Connection conn;
    
    PreparedStatement ps;
    ResultSet rs;
    
    public PromoDiskonTable(){
        koneksi = new Koneksi();
        conn = koneksi.getConnection();
    }
    
    public ArrayList<PromoDiskon> tampilPromoDiskon(String level, String tanggal, String limit){
        ArrayList<PromoDiskon> listPromoDiskon = new ArrayList<>();
        
        String query = "SELECT * FROM promo_diskon";
        
        String search = "";
        
        if(!level.equals("")){
            search = " WHERE level='" + level + "'";
        }
        
        if(!tanggal.equals("")){
                search = " WHERE ('" +  tanggal +"' BETWEEN tanggal_tersedia AND tanggal_berakhir)"; 
        }
        
        String limitQuery = "";
        if(!limit.equals("Semua")){
           limitQuery = "LIMIT " + 25;
        }else{
            limitQuery = "";
        }
        
        query += search;
        query += " ORDER BY created_at DESC " + limitQuery;
        
        try{
      
            ps = conn.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                promoDiskon = new PromoDiskon(
                        Integer.parseInt(rs.getString("id_promo_diskon")),
                        Integer.parseInt(rs.getString("presentase_diskon")),
                        rs.getString("level"),
                        rs.getString("tanggal_tersedia"), 
                        rs.getString("tanggal_berakhir"), 
                        rs.getString("created_at"), 
                rs.getString("updated_at"));
                
                listPromoDiskon.add(promoDiskon);
            }
            
        }catch(SQLException | NullPointerException ex){
            System.err.println("ERROR tampilPromoDiskon : " + ex);
        }
        
        
        return listPromoDiskon;
    }
 
    public String tambahPromoDiskon(PromoDiskon pd){
        
        String response = "";
        boolean exist = false;
        
        String checkPromo = "SELECT * FROM promo_diskon"
                + " WHERE ((? BETWEEN tanggal_tersedia AND tanggal_berakhir) OR (? BETWEEN tanggal_tersedia AND tanggal_berakhir)) AND"
                + " (level=? OR level='Semua')";
        
        try{
            ps = conn.prepareStatement(checkPromo);
            
            ps.setString(1, pd.getLevel());
            ps.setString(2, pd.getTanggalTersedia());
            ps.setString(3, pd.getTanggalBerakhir());
            
            rs = ps.executeQuery();
            if(rs.isBeforeFirst()){
                exist = true;
                while(rs.next()){
                    response = "Promo Diskon sudah ada untuk " +  rs.getString("level")  + " pada tanggal " + rs.getString("tanggal_tersedia") + " sampai " + rs.getString("tanggal_berakhir");
                }
            }
            ps.close();
            rs.close();
        }catch(SQLException ex){
            System.err.println("ERROR tambahPromoDiskon-checkPromo : " + ex);
        }
        
        if(exist == false){
            String query = "INSERT INTO promo_diskon (presentase_diskon, level, tanggal_tersedia, tanggal_berakhir) VALUES"
                    + " (?, ?, ?, ?)";
            
            try{

                ps = conn.prepareStatement(query);
                
                ps.setInt(1, pd.getPresentaseDiskon());
                ps.setString(2, pd.getLevel());
                ps.setString(3, pd.getTanggalTersedia());
                ps.setString(4, pd.getTanggalBerakhir());
                
                ps.executeUpdate();
                
                response =  "SUCCESS";
                
                ps.close();
                
            }catch(SQLException ex){
                response = "FAILED";
                System.err.println("ERROR tambahPromoDiskon : " + ex);
            }
            
        }
        
        return response;
    }
    
    public String editPromoDiskon(PromoDiskon pd, int id, String levelLama, String tanggalTersedia, String tanggalBerakhir){
        
        String response = "";
        boolean exist = false;
        
        String checkPromo = "SELECT * FROM promo_diskon";
        
        String where = "";
        
        if(!pd.getTanggalTersedia().equals(tanggalTersedia)){
            where = " WHERE ('" + pd.getTanggalTersedia() + "' BETWEEN tanggal_tersedia AND tanggal_berakhir)";
        }
        
        if(!pd.getTanggalBerakhir().equals(tanggalBerakhir)){
            if(where.contains("WHERE")){
                where = " WHERE ('" + pd.getTanggalTersedia() + "' BETWEEN tanggal_tersedia AND tanggal_berakhir) OR ('" + pd.getTanggalBerakhir() + "' BETWEEN tanggal_tersedia AND tanggal_berakhir)";
            }else{
                where = " WHERE ('" + pd.getTanggalBerakhir() + "' BETWEEN tanggal_tersedia AND tanggal_berakhir)";
            }
        }
        
        
        if(!pd.getLevel().equals(levelLama)){
            if(where.contains("WHERE")){
                where += " AND (level='" + pd.getLevel() + "' OR level='Semua')";
            }else{
                where = " WHERE (level='" + pd.getLevel() + "' OR level='Semua')  AND ('" + tanggalTersedia  +"' BETWEEN tanggal_tersedia AND tanggal_berakhir)";
            }
             
        }
        
        checkPromo += where;         
        
        
        if((!pd.getLevel().equals(levelLama)) ||
                (!pd.getTanggalTersedia().equals(tanggalTersedia)) || (!pd.getTanggalBerakhir().equals(tanggalBerakhir))){
            try{
                ps = conn.prepareStatement(checkPromo);

                rs = ps.executeQuery();
                if(rs.isBeforeFirst()){
                    exist = true;
                    while(rs.next()){
                        response = "Promo Diskon sudah ada untuk " +  rs.getString("level")  + " pada tanggal " + rs.getString("tanggal_tersedia") + " sampai " + rs.getString("tanggal_berakhir");
                    }
                }
                ps.close();
                rs.close();
            }catch(SQLException ex){
                System.err.println("ERROR editPromoDiskon-checkPromo : " + ex);
            }
        }
//        
        if(exist == false){
            String query = "UPDATE promo_diskon SET presentase_diskon=?, level=?, tanggal_tersedia=?, tanggal_berakhir=? WHERE id_promo_diskon=?";
            
            try{

                ps = conn.prepareStatement(query);
                
                ps.setInt(1, pd.getPresentaseDiskon());
                ps.setString(2, pd.getLevel());
                ps.setString(3, pd.getTanggalTersedia());
                ps.setString(4, pd.getTanggalBerakhir());
                ps.setInt(5, id);
                
                ps.executeUpdate();
                
                response =  "SUCCESS";
                
                ps.close();
                
            }catch(SQLException ex){
                response = "FAILED";
                System.err.println("ERROR editPromoDiskon : " + ex);
            }
            
        }

        return response;
    }
    
    public boolean hapusPromoDiskon(PromoDiskon pd){
        
        boolean response = true;
        
        boolean exist = false;
        String tanggalQuery = "(DATE(transaksi.created_at) BETWEEN '" + pd.getTanggalTersedia() + "' AND '" + pd.getTanggalBerakhir() + "')";
        String levelQuery = "('" + pd.getLevel() + "')";
        
        if(pd.getTanggalTersedia().equals(pd.getTanggalBerakhir())){
            tanggalQuery = "DATE(transaksi.created_at)='" + pd.getTanggalTersedia() +"'";
        }
        
        if(pd.getLevel().equals("Semua")){
            levelQuery = "('Classic', 'Silver','Gold', 'Platinum')";
        }
        
        String checkTransaction = "SELECT kode_transaksi, nama_pelanggan,"
                   + " CASE WHEN COUNT(kode_transaksi) <= 2 THEN 'Classic'"
                   + " WHEN COUNT(kode_transaksi) >= 3 THEN 'Silver'"
                   + " WHEN COUNT(kode_transaksi) >= 75 THEN 'Gold'"
                   + " ELSE 'Platinum' END AS levels, DATE(transaksi.created_at)"
                   + " FROM transaksi LEFT JOIN pelanggan ON transaksi.id_pelanggan=pelanggan.id_pelanggan"
                   + " WHERE " + tanggalQuery
                   + " GROUP BY kode_transaksi HAVING levels IN " + levelQuery;
        
        try{
            
            ps = conn.prepareStatement(checkTransaction);
            rs = ps.executeQuery();
            
            if(rs.next()){
                exist = true;
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            exist = true;
            System.err.println("ERROR checkTransaksi-hapusPromoDiskon : " + ex);
        }
        
        if(exist == false){
            String query = "DELETE FROM promo_diskon WHERE id_promo_diskon='" + pd.getIdPromoDiskon() + "'";
            
            try{
                ps = conn.prepareStatement(query);
                ps.executeUpdate();
                
                response = true;
                
                ps.close();
                
            }catch(SQLException ex){
                response = false;
                System.err.println("ERROR hapusPromoDiskon : " + ex);
            }
            
        }else{
            response = false;
            JOptionPane.showMessageDialog(null, "Terdapat transaksi yang sudah menggunakan promo diskon ini\n"
                    + "pada tanggal dan level yang sama", "Promo Diskon", JOptionPane.ERROR_MESSAGE);
        }
        
        return response;
    }
    
    public int cariPromoDiskon(String level, String tanggal){
        
        String date = "CURRENT_DATE";
        
        if(!tanggal.equals("")){
            date = "'" + tanggal + "'";
        }
        
        String query = "SELECT presentase_diskon FROM promo_diskon WHERE ("+ date +" BETWEEN tanggal_tersedia AND tanggal_berakhir) "
                + "AND (level=? OR level='Semua')";
        
        int presentaseDiskon = 0;
        
        try{
            ps = conn.prepareStatement(query);
            
            ps.setString(1, level);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                presentaseDiskon = rs.getInt("presentase_diskon");
            }
            
        }catch(SQLException ex){
            System.err.println("ERROR getPromoDiskon() : " + ex);
        }
        
        return presentaseDiskon;
    }
}
