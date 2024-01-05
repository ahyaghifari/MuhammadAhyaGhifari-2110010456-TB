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
import model.Admin;

/**
 *
 * @author Ahya Ghifari
 */

// CLASS ADMIN TABLE
public class AdminTable {
    
    // inisialisai properti model admin, koneksi, statement dan result set
    Admin admin;
    
    Koneksi koneksi;
    Connection conn;
    
    PreparedStatement ps;
    ResultSet rs;
    
    // konstruktor dan mengisi properti koneksi
    public AdminTable(){
        koneksi = new Koneksi();
        conn = koneksi.getConnection();
    }
    
    // METHOD tampilAdmin
    public ArrayList<Admin> tampilAdmin(String keyword){
        ArrayList<Admin> listAdmin = new ArrayList<>(); // membuat array list sebagai response
        
        String query = "SELECT * FROM admin WHERE (jabatan='Admin')"; // query mengambil seluruh data admin

        // jika keyword pencarian tidak kosong makan dilakukan pencarian terhadap nama atau telepon atau email admin
        if(!keyword.equals("")){ 
            query = query + " AND (nama_admin LIKE '%" + keyword + "%'"
                    + " OR no_telepon LIKE '%" + keyword + "%'"
                    + " OR email LIKE '%" + keyword + "%')"; 
        }
        
        // query untuk pengurutan admin
        query += " ORDER BY admin.created_at DESC";
        
        try{
      
            ps = conn.prepareStatement(query); // mempersiapkan statement
            
            rs = ps.executeQuery(); // mengambil nilai
            
            // mengisi array list dengan data dari objek admin sesuai data yang diambil
            while(rs.next()){
                
                admin = new Admin();
                admin.setIdAdmin(Integer.parseInt(rs.getString("id_admin")));
                admin.setUsername(rs.getString("username"));
                admin.setNamaAdmin(rs.getString("nama_admin"));
                admin.setTanggalLahir(rs.getString("tanggal_lahir"));
                admin.setAlamat(rs.getString("alamat"));
                admin.setNoTelepon(rs.getString("no_telepon"));
                admin.setEmail(rs.getString("email"));
                admin.setJenisKelamin(rs.getString("jenis_kelamin"));
                admin.setJabatan(rs.getString("jabatan"));
                admin.setCreatedAt(rs.getString("created_at"));
                admin.setCreatedAt(rs.getString("updated_at"));
                
                listAdmin.add(admin);
            }
        
            // menutup reslut set dan koneksi
            rs.close();
            ps.close();
            
        }catch(SQLException | NullPointerException ex){
            System.err.println("ERROR tampilAdmin : " + ex);
        }
        
       
        return listAdmin;
    }

    // METHOD tambahAdmin
    public String tambahAdmin(Admin admin){
        String response = "";
        
        boolean usernameExist = false;
        
            // melakukan pengecekan apakah username sudah digunakan atau belum karena bersifat unik
            String checkUsername = "SELECT username FROM admin WHERE username='" + admin.getUsername() + "'";

            try{

                ps = conn.prepareStatement(checkUsername);
                rs = ps.executeQuery();

                if(rs.next()){
                    usernameExist = true;
                    response = "Username sudah digunakan";
                }

            }catch(SQLException ex){
                System.err.println("ERROR ubahAdmin-checkUsername : " + ex);
                response = "FAILED";
            }
        
        
        // jika ternyata username belum atau tidak ada maka admin ditambahkan
        if(usernameExist == false){
            
            String query = "INSERT INTO admin (username, nama_admin, tanggal_lahir, alamat, no_telepon, email, jenis_kelamin, jabatan, password) VALUES"
                    + " (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try{
                
                ps = conn.prepareStatement(query);
                
                ps.setString(1, admin.getUsername());
                ps.setString(2, admin.getNamaAdmin());
                ps.setString(3, admin.getTanggalLahir());
                ps.setString(4, admin.getAlamat());
                ps.setString(5, admin.getNoTelepon());
                ps.setString(6, admin.getEmail());
                ps.setString(7, admin.getJenisKelamin());
                ps.setString(8, admin.getJabatan());
                ps.setString(9, admin.getPassword());
                
                ps.executeUpdate();
                
                response = "SUCCESS";
                
            }catch(SQLException ex){
                System.err.println("ERROR tambahAdmin : " + ex);
                response =  "FAILED";
            }
            
        }
        
        return response;
    }
    
    // METHOD ubahAdmin
    public String ubahAdmin(Admin admin, String usernameLama){
        String response = "";
        
        boolean usernameExist = false;
        
        // jika username baru dari admin berbeda dari username yang lama maka di cek terlebih dahulu apakah username sudah dipakai atau belum
        if(!admin.getUsername().equals(usernameLama)){
            String checkUsername = "SELECT username FROM admin WHERE username='" + admin.getUsername() + "'";

            try{

                ps = conn.prepareStatement(checkUsername);
                rs = ps.executeQuery();

                if(rs.next()){
                    usernameExist = true;
                    response = "Username sudah digunakan";
                }

            }catch(SQLException ex){
                System.err.println("ERROR ubahAdmin-checkUsername : " + ex);
                response = "FAILED";
            }
        }
        
        // jika username tidak ada maka admin diperbaharui
        if(usernameExist == false){
            
            String query = "UPDATE admin SET username=?, nama_admin=?, tanggal_lahir=?, alamat=?, no_telepon=?, email=?, jenis_kelamin=?, jabatan=?"
                    + " WHERE id_admin=?";
            
            if(!admin.getPassword().equals("")){
                query = "UPDATE admin SET username=?, nama_admin=?, tanggal_lahir=?, alamat=?, no_telepon=?, email=?, jenis_kelamin=?, jabatan=?, password=?"
                    + " WHERE id_admin=?";
            }
            
            try{
                
                ps = conn.prepareStatement(query);
                
                ps.setString(1, admin.getUsername());
                ps.setString(2, admin.getNamaAdmin());
                ps.setString(3, admin.getTanggalLahir());
                ps.setString(4, admin.getAlamat());
                ps.setString(5, admin.getNoTelepon());
                ps.setString(6, admin.getEmail());
                ps.setString(7, admin.getJenisKelamin());
                ps.setString(8, admin.getJabatan());
                
                if(!admin.getPassword().equals("")){
                    ps.setString(9, admin.getPassword());
                    ps.setInt(10, admin.getIdAdmin());
                }else{
                    ps.setInt(9, admin.getIdAdmin());
                }
                
                
                ps.executeUpdate();
                
                response = "SUCCESS";
                
            }catch(SQLException ex){
                System.err.println("ERROR ubahAdmin : " + ex);
                response =  "FAILED";
            }
            
        }
        
        return response;
    }
    
    // METHOD hapusAdmin
    public boolean hapusAdmin(Admin admin, int idManajer){
        
        boolean response = true;
        
        boolean transaksiExist = false;
        
        // mengecek apakah admin sudah pernah melakukan transaksi
        String checkTransaksi = "SELECT id_transaksi FROM transaksi WHERE id_admin='" + admin.getIdAdmin() + "'";
        
        try{
            
            ps = conn.prepareStatement(checkTransaksi);
            rs = ps.executeQuery();
            
            if(rs.next()){
                transaksiExist = true;
                response = false;
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            System.err.println("ERROR checkTransaksiExist-hapusAdmin : " + ex);
        }
        
        // jika tidak ada transaksi maka dilakukan penghapusan
        if(transaksiExist == false){
            
            response = hapusAdminOnDatabase(String.valueOf(admin.getIdAdmin()));
            
        // jika admin sudah melakukan trasaksi
        }else{
            
            boolean updateTransaksi = true;
            
            // melakukan konfirmasi apakah manajer ingin benar-benar melakukan penghapusan
            int konfirmasi = JOptionPane.showConfirmDialog(null, "Admin sudah pernah melakukan transaksi, "
                    + "jika dilanjutkan semua transaksi admin akan diganti dengan manajer saat ini ", "Admin", JOptionPane.YES_NO_OPTION);
            
            // jika ya semua transaksi admin akan digantikan oleh manajer yang login
            if(konfirmasi == 0){
                try{
                    ps = conn.prepareStatement("UPDATE transaksi SET id_admin='" + idManajer + "' WHERE id_admin='" + admin.getIdAdmin() + "'");
                    ps.executeUpdate();
                    
                    ps.close();
                    
                }catch(SQLException ex){
                    updateTransaksi = false;
                    System.err.println("ERROR updateTransaksiIdAdmin : " + ex);
                    response = false;
                }
                
                // jika sudah terupdate
                if(updateTransaksi == true){
                    response = hapusAdminOnDatabase(String.valueOf(admin.getIdAdmin()));
                }
            }
              
        }
      
        return response;
    }
    
    // METHOD hapusAdminOnDatabase 
    private boolean hapusAdminOnDatabase(String idAdmin){
        String query = "DELETE FROM admin WHERE id_admin='" + idAdmin + "'";
            try{
                ps = conn.prepareStatement(query);
                ps.executeUpdate();
                
                ps.close();
                
                return true;
            }catch(SQLException ex){
                System.err.println("ERROR hapusAdmin : " + ex); 
                return false;
            }
    }
    
    // METHOD login
    public Admin login(String username, String password){
        // cek apakah ada admin dengan username dan password yang sama
        String query = "SELECT * FROM admin WHERE username=? AND password=?";
        
        Admin adminLogin = new Admin();
        try{
            
            ps = conn.prepareStatement(query);
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            rs = ps.executeQuery();
            
            if(rs.isBeforeFirst()){
                
                while(rs.next()){
                    adminLogin.setIdAdmin(Integer.parseInt(rs.getString("id_admin")));
                    adminLogin.setUsername(username);
                    adminLogin.setNamaAdmin(rs.getString("nama_admin"));
                    adminLogin.setTanggalLahir(rs.getString("tanggal_lahir"));
                    adminLogin.setAlamat(rs.getString("alamat"));
                    adminLogin.setNoTelepon(rs.getString("no_telepon"));
                    adminLogin.setEmail(rs.getString("email"));
                    adminLogin.setJenisKelamin(rs.getString("jenis_kelamin"));
                    adminLogin.setJabatan(rs.getString("jabatan"));
                    adminLogin.setCreatedAt(rs.getString("created_at"));
                    adminLogin.setUpdatedAt(rs.getString("updated_at"));
                }
                
            }else{
                adminLogin.setIdAdmin(0);
            }
            
        }catch(SQLException ex){
            System.err.println("ERROR login : " +  ex);
        }
        
        return adminLogin;
    }
    
    // METHOD ubahPassword
    public String ubahPassword(String username, String passLama, String passBaru){
        String response = "";
        
        boolean oldPassCorrect = true;
        
        // mengecek apakah password lama yang diinput benar
        String checkPassword = "SELECT password FROM admin WHERE username='" + username + "'";
        
        try{
            ps = conn.prepareStatement(checkPassword);
            rs = ps.executeQuery();
            
            // jika password lama tidak sesuai
            while(rs.next()){
                if(!passLama.equals(rs.getString("password"))){
                    oldPassCorrect = false;
                    response = "Password lama tidak sesuai";
                }
            }
            
        }catch(SQLException ex){
            System.err.println("ERROR ubahPassword-checkOldPassword : " + ex);
            response = "FAILED";
        }
        
        // jika password lama benar maka password diperbarui
        if(oldPassCorrect == true){
            
            String query = "UPDATE admin SET password=? WHERE username=?";
            
            try{
                ps = conn.prepareStatement(query);
                
                ps.setString(1, passBaru);
                ps.setString(2, username);
                
                ps.executeUpdate();
                
                response = "SUCCESS";
                
            }catch(SQLException ex){
                System.err.println("ERROR ubahPassword : " + ex);
                response = "FAILED";
            }
            
        }
        
        return response;
    }
    
    // METHOD untuk menampilkan jumlah admin pada halaman dashboard
    public int dashboardCount(){
        int count = 0;
        try{
            
            ps = conn.prepareStatement("SELECT COUNT(id_admin) as jml_admin FROM admin");
            rs = ps.executeQuery();
            
            while(rs.next()){
                count = Integer.parseInt(rs.getString("jml_admin"));
            }
            
            rs.close();
            ps.close();
            
        }catch(SQLException ex){
            System.err.println("ERROR dashboardCount : " + ex);
        }
        
        return count;
    }
    
}
