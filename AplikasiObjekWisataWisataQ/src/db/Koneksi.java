/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ahya Ghifari
 */

// CLASS untuk mengkoneksi kan ke database
public class Koneksi {
    
    // isi properti final untuk database
    private final String URL = "jdbc:mysql://localhost:3306/wisataq_db";
    private final String USER = "root";
    private final String PASS = "";
    
    // method unutk membuat koneksi
    public Connection getConnection(){
        Connection conn;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;
            
        }catch(ClassNotFoundException | SQLException ex){
            System.err.println("Koneksi Gagal");
            return conn=null;
        }
    }
    
    public static void main(String[] args) {
        Koneksi k = new Koneksi();
        k.getConnection();
    }
    
}
