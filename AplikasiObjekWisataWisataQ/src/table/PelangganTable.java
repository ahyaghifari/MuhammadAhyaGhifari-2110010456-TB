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
import model.Pelanggan;
import model.PelangganTerdaftar;

/**
 *
 * @author Ahya Ghifari
 */

// CLASS OBJEK PELANGGAN TABLE
public class PelangganTable {
    
    // inisialisai properti model pelanggan, koneksi, statement dan result set
    Pelanggan pelanggan;
    
    Koneksi koneksi;
    Connection conn;
    
    PreparedStatement ps;
    ResultSet rs;
    
    // konstrukor dan mengisi koneksi
    public PelangganTable(){
        koneksi = new Koneksi();
        conn = koneksi.getConnection();
    }
    
    // tampilPelanggan
    public ArrayList<Pelanggan> tampilPelanggan(String keyword, String limit){
        ArrayList<Pelanggan> listPelanggan = new ArrayList<>();
        
        String limitQuery = "";
        
        if(!limit.equals("Semua")){
            limitQuery = "LIMIT " + limit;
        }else{
            limitQuery = "";
        }
        
        String query = "SELECT pelanggan.*, IFNULL(tr.jml_transaksi, 0) as jml_transaksi FROM pelanggan"
                + " LEFT JOIN (SELECT id_pelanggan, COUNT(id_pelanggan) as jml_transaksi FROM transaksi WHERE status_transaksi='Selesai' GROUP BY id_pelanggan) as tr"
                + " ON pelanggan.id_pelanggan=tr.id_pelanggan ";

        // jika pencarian tidak kosong maka dilakukan pencarian berdasarkan nama, telepon dan email dari pelanggan
        if(!keyword.equals("")){
            query = query + " WHERE nama_pelanggan LIKE '%" + keyword + "%'"
                    + " OR no_telepon LIKE '%" + keyword + "%'"
                    + " OR email LIKE '%" + keyword + "%'"; 
        }
        
        // order atau mengurutkan data
        query += " ORDER BY pelanggan.created_at DESC " + limitQuery;
        
        try{
      
            ps = conn.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                // mengecek dan mengklasifikasikan level dari pelanggan sesuai jumlah transaksi
                // jika transaksi <= 2 maka level classic
                // jika transaksi >=3 maka level silver
                // jika transaksi >= 75 maka level gold
                // jika lebih dari itu makan level platinum
                
                String level;
                int jumlahTransaksi = Integer.parseInt(rs.getString("jml_transaksi"));
                
                if(jumlahTransaksi <= 2){
                    level = "Classic";
                }else if(jumlahTransaksi >= 3){
                    level = "Silver";
                }else if(jumlahTransaksi >= 75){
                    level = "Gold";
                }else{
                    level = "Platinum";
                }
                     
                
                pelanggan = new Pelanggan(
                        Integer.parseInt(rs.getString("id_pelanggan")),
                        rs.getString("nama_pelanggan"),
                        rs.getString("alamat"),
                        rs.getString("no_telepon"),
                        rs.getString("email"),
                        rs.getInt("jml_transaksi"),
                        level,
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
                listPelanggan.add(pelanggan);
            }
        
            
            
        }catch(SQLException | NullPointerException ex){
            System.err.println("ERROR tampilPelanggan : " + ex);
        }
        
        
        
        return listPelanggan;
    }
    
    // tambahPelanggan
    public boolean tambahPelanggan(Pelanggan pelanggan){
        String query = "INSERT INTO pelanggan (nama_pelanggan, alamat, no_telepon, email) VALUES (?, ?, ?, ?)";
        try{

            ps = conn.prepareStatement(query);
            ps.setString(1, pelanggan.getNamaPelanggan());
            ps.setString(2, pelanggan.getAlamat());
            ps.setString(3, pelanggan.getNoTelepon());
            ps.setString(4, pelanggan.getEmail());
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
        }catch(SQLException ex){
            System.err.println("ERROR tambahPelanggan : " + ex);
            return false;
        }
    }

    //ubahPelanggan
    public boolean ubahPelanggan(Pelanggan pelanggan){
        String query = "UPDATE pelanggan SET nama_pelanggan=?, alamat=?, no_telepon=?, email=? WHERE id_pelanggan=?";
        try{

            ps = conn.prepareStatement(query);
            ps.setString(1, pelanggan.getNamaPelanggan());
            ps.setString(2, pelanggan.getAlamat());
            ps.setString(3, pelanggan.getNoTelepon());
            ps.setString(4, pelanggan.getEmail());
            ps.setString(5, String.valueOf(pelanggan.getIdPelanggan()));
            
            ps.executeUpdate();
            
            ps.close();
            conn.close();
            
            return true;
        }catch(SQLException ex){
            System.err.println("ERROR ubahPelanggan : " + ex);
            return false;
        }
    }
    
    public boolean hapusPelanggan(int idPelanggan){
        
        boolean response = false;
        
        boolean exist = false;
        
        String checkTransaksi = "SELECT kode_transaksi FROM transaksi WHERE id_pelanggan='" + idPelanggan + "'";
        
        try{
            
            ps = conn.prepareStatement(checkTransaksi);
            rs = ps.executeQuery();
            
            if(rs.next()){
                exist = true;
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            exist = true;
            System.err.println("ERROR checkTransaksi-hapusPelanggan : " + ex);
        }
        
        if(exist == false){
            
            String query = "DELETE FROM pelanggan WHERE id_pelanggan='" + idPelanggan + "'";
            
            try{
                
                ps = conn.prepareStatement(query);
                ps.executeUpdate();
                
                response = true;
                
            }catch(SQLException ex){
                System.err.println("ERROR hapusPelanggan : " + ex);
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "Pelanggan sudah pernah melakukan transaksi", "Pelanggan", JOptionPane.YES_NO_OPTION);
            response = false;
        }
        
        return response;
    }
    
    public ArrayList<Pelanggan> tampilPelangganTransaksi(String keyword){
        ArrayList<Pelanggan> listPelanggan = new ArrayList<>();
        
        String query = "SELECT pelanggan.id_pelanggan, pelanggan.nama_pelanggan, pelanggan.no_telepon, pelanggan.created_at, IFNULL(tr.jml_transaksi, 0) as jml_transaksi FROM pelanggan"
                + " LEFT JOIN (SELECT id_pelanggan, COUNT(id_pelanggan) as jml_transaksi FROM transaksi WHERE status_transaksi='Selesai' GROUP BY id_pelanggan) as tr"
                + " ON pelanggan.id_pelanggan=tr.id_pelanggan";

        if(!keyword.equals("")){
            query = query + " WHERE nama_pelanggan LIKE '%" + keyword + "%'"
                    + " OR no_telepon LIKE '%" + keyword + "%'"; 
        }    
        
        query += " ORDER BY pelanggan.created_at DESC LIMIT 25";
        
        try{
      
            ps = conn.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                String level;
                int jumlahTransaksi = Integer.parseInt(rs.getString("jml_transaksi"));
                
                if(jumlahTransaksi <= 2){
                    level = "Classic";
                }else if(jumlahTransaksi >= 3){
                    level = "Silver";
                }else if(jumlahTransaksi >= 75){
                    level = "Gold";
                }else{
                    level = "Platinum";
                }
                     
                pelanggan = new Pelanggan();
                pelanggan.setIdPelanggan(Integer.parseInt(rs.getString("id_pelanggan")));
                pelanggan.setNamaPelanggan(rs.getString("nama_pelanggan"));
                pelanggan.setNoTelepon(rs.getString("no_telepon"));
                pelanggan.setLevel(level);
                
                
                listPelanggan.add(pelanggan);
            }
        
            
            
        }catch(SQLException | NullPointerException ex){
            System.err.println("ERROR tampilPelangganTransaksi : " + ex);
        }
        
        
        
        return listPelanggan;
    }
    
    public int dashboardCount(){
        int count = 0;
        try{
            
            ps = conn.prepareStatement("SELECT COUNT(id_pelanggan) as jml_pelanggan FROM pelanggan");
            rs = ps.executeQuery();
            
            while(rs.next()){
                count = Integer.parseInt(rs.getString("jml_pelanggan"));
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            System.err.println("ERROR dashboardCount : " + ex);
        }
        
        return count;
    }
    
    public ArrayList<PelangganTerdaftar> pelangganTerdaftar(String bulan, String tahun){
        ArrayList<PelangganTerdaftar> list = new ArrayList();
        String query = "SELECT DATE(created_at) as tanggal, COUNT(created_at) as jml_pelanggan FROM pelanggan"
                + " WHERE MONTH(created_at)=? AND YEAR(created_at)=? GROUP BY DATE(created_at) ORDER BY created_at ASC";      
        
        try{
            
            ps = conn.prepareStatement(query);
            
            ps.setString(1, bulan);
            ps.setString(2, tahun);
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                PelangganTerdaftar pT = new PelangganTerdaftar();
                
                pT.setTanggal(rs.getString("tanggal"));
                pT.setJmlPelanggan(Integer.parseInt(rs.getString("jml_pelanggan")));  
                
                list.add(pT);
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            System.err.println("ERROR pelangganTerdaftar : " + ex);
        }
        
        return list;
    }
    
}
