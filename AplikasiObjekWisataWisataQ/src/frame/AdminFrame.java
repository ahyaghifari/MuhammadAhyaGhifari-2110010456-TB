/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import model.Kategori;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Admin;
import model.ObjekWisata;
import model.Pelanggan;
import model.Pembayaran;
import model.PromoDiskon;
import model.Transaksi;
import table.KategoriTable;
import service.KonversiRupiah;
import table.AdminTable;
import table.ObjekWisataTable;
import table.PelangganTable;
import table.PembayaranTable;
import table.PromoDiskonTable;
import table.TransaksiTable;

/**
 *
 * @author Ahya Ghifari
 */
public class AdminFrame extends javax.swing.JFrame {
    Admin profil = new Admin(); // inisialisasii untuk menyimpan admin yang login
    
    CardLayout cl; // inisialisasi layout
    
    // inisialisasi renderer tabel
    DefaultTableCellRenderer centerRenderer;
    
    // inisialisasi array list untuk menyimpan data data dari tabel
    ArrayList<Transaksi> listTransaksi = new ArrayList();
    ArrayList<Pelanggan> listPelanggan = new ArrayList();
    ArrayList<PromoDiskon> listPromoDiskon = new ArrayList();
    
    ArrayList<ObjekWisata> listObjekWisata = new ArrayList();
    ArrayList<Kategori> listKategori = new ArrayList();
    ArrayList<Pembayaran> listPembayaran = new ArrayList();
    
    
    /**
     * Creates new form AdminFrame
     * @param admin
     */
    
    // KONSTRUKTOR
    public AdminFrame(Admin admin) {
        initComponents();
        setLocationRelativeTo(null);
        cl = (CardLayout)(panelContent.getLayout());
        
        this.profil = admin;
        
        // mengisi nilai properti renderer tabel
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        centerColumnTable();
        
        
        lblHeaderName.setText("Halo, " + profil.getNamaAdmin());
        lblDashboardNama.setText(profil.getUsername() + " (" + profil.getNamaAdmin() + ")");
        pilihHalaman("dashboard");
    }
    
    
    // METHOD METHOD UNTUK PENGAMBILAN DATA    
    
    // method dashboard
    final void getDashboardData(){
        
        // inisialisai tabel tabel yang diperlukan
        TransaksiTable transaksiTable = new TransaksiTable();
        PelangganTable pelangganTable = new PelangganTable();
        ObjekWisataTable objekWisataTable = new ObjekWisataTable();
        KategoriTable kategoriTable = new KategoriTable();
        
        // mengambil perhitungan data beberapa tabel
        int countTransaksi = transaksiTable.dashboardCount();
        int countPelanggan = pelangganTable.dashboardCount();
        int countObjekWisata = objekWisataTable.dashboardCount();
        int countKategori = kategoriTable.dashboardCount();
        
        // menampilkan ke panel panel dashboard
        lblDashboardTransaksi.setText(String.valueOf(countTransaksi));
        lblDashboardPelanggan.setText(String.valueOf(countPelanggan));
        lblDashboardObjekWisata.setText(String.valueOf(countObjekWisata));
        lblDashboardKategori.setText(String.valueOf(countKategori));
        
    }
    
    //method - transaksi    
    final void getTransaksiList(String keyword, String limit){
        
        // inisialisasi varaiabel
        TransaksiTable modelTableTransaksi = new TransaksiTable();
        listTransaksi = modelTableTransaksi.tampilTransaksi(keyword, limit);
        
        // inisialisasi array list
        ArrayList<Transaksi> list = new ArrayList();
        list = listTransaksi;
        
        // ambil tabel transaksi
        DefaultTableModel transaksiTabel = (DefaultTableModel)tabelTransaksi.getModel();
        Object[] row = new Object[8];
        
        // hapus semua isi tabel
        transaksiTabel.setRowCount(0);
        
        // isi tabel transaksi
        for(int i =0; i < listTransaksi.size(); i++){
            row[0] = list.get(i).getKodeTransaksi();
            row[1] = list.get(i).getTanggalWisata();
            row[2] = " " + list.get(i).getPelanggan().getNamaPelanggan();
            row[3] = " " + list.get(i).getObjekWisata().getNamaObjekWisata();
            row[4] = list.get(i).getJumlahTiket();
            row[5] = list.get(i).getStatusTransaksi();
            row[6] = " " + list.get(i).getAdmin().getNamaAdmin();
            row[7] = list.get(i).getCreatedAt();
        
            transaksiTabel.addRow(row);
        }
    }
    
    // method - pelanggan
    final void getPelangganList(String keyword, String limit){
        // inisialisasi tabel
        PelangganTable modelTablePelanggan = new PelangganTable();
        listPelanggan = modelTablePelanggan.tampilPelanggan(keyword, limit);   
        
        // inisialisasi array list
        ArrayList<Pelanggan> list = new ArrayList();
        list = listPelanggan;
        
        // ambil tabel pelanggan
        DefaultTableModel pelangganTabel = (DefaultTableModel)tabelPelanggan.getModel();
        Object[] row = new Object[6];
        
        // hapus semua isi tabel
        pelangganTabel.setRowCount(0);
        
        // isi tabel pelanggan
        for(int i =0; i < list.size(); i++){
            row[0] = " " + list.get(i).getNamaPelanggan();
            row[1] = list.get(i).getLevel();
            row[2] = " " + list.get(i).getAlamat();
            row[3] = " " + list.get(i).getNoTelepon();
            row[4] = " " + list.get(i).getEmail();
            row[5] = list.get(i).getCreatedAt();
            
            pelangganTabel.addRow(row);
        }
    }
    
    // method - promo diskon
    final void getPromoDiskonList(String level, String tanggal, String limit){
        
        // inisialisasi tabel promo diskonn
        PromoDiskonTable modelTablePromoDiskon = new PromoDiskonTable();
        listPromoDiskon = modelTablePromoDiskon.tampilPromoDiskon(level, tanggal, limit);   
        
        // inisilisasi array list
        ArrayList<PromoDiskon> list = new ArrayList();
        list = listPromoDiskon;
        
        // ambil tabel promo diskon
        DefaultTableModel promoDiskonTabel = (DefaultTableModel)tabelPromoDiskon.getModel();
        Object[] row = new Object[5];
        
        // hapus semua isi tabel promo diskon
        promoDiskonTabel.setRowCount(0);
        
        // isi tabel dikson dengan data
        for(int i =0; i < list.size(); i++){
            row[0] = list.get(i).getIdPromoDiskon();
            row[1] = list.get(i).getPresentaseDiskon();
            row[2] = list.get(i).getLevel();
            row[3] = list.get(i).getTanggalTersedia();
            row[4] = list.get(i).getTanggalBerakhir();
        
            promoDiskonTabel.addRow(row);
        }
    }
    
    // method - objek wisata
    final void getObjekWisataList(String keyword, String order){
        
        // inisialisasi tabel objek wisata dan mengisi array list
        ObjekWisataTable owTable = new ObjekWisataTable();
        listObjekWisata = owTable.tampilObjekWisata(keyword);
        
        // pengondisia untuk peng urutan data
        if(order.equals("default")){ // berdasarkan id ASC
            Collections.sort(listObjekWisata, Comparator.comparing(ObjekWisata::getIdObjekWisata));
        }else if(order.equals("id-desc")){ // berdasarkan id DESC
            Collections.sort(listObjekWisata, Comparator.comparing(ObjekWisata::getIdObjekWisata));
            Collections.reverse(listObjekWisata);
        }else if(order.equals("nama-asc")){ // berdasarkan nama ASC
            Collections.sort(listObjekWisata, Comparator.comparing(ObjekWisata::getNamaObjekWisata));
        }else if(order.equals("nama-desc")){ // berdasarkan nama DESC
            Collections.sort(listObjekWisata, Comparator.comparing(ObjekWisata::getNamaObjekWisata));
            Collections.reverse(listObjekWisata);
        }else if(order.equals("tiket-asc")){ // berdasarkan tiket ASC
           Collections.sort(listObjekWisata, Comparator.comparingInt(ObjekWisata::getKetersediaanTiket));
        }else if(order.equals("tiket-desc")){ // berdasarkan tiket DESC
            Collections.sort(listObjekWisata, Comparator.comparingInt(ObjekWisata::getKetersediaanTiket));
            Collections.reverse(listObjekWisata);
        }
        
        // inisialisasi array list
        ArrayList<ObjekWisata> list = new ArrayList();
        list = listObjekWisata;
        
        // ambil tabel objek wisata
        DefaultTableModel objekWisataTabel = (DefaultTableModel)tabelObjekWisata.getModel();
        Object[] row = new Object[5];
        
        // hapus semua isi tabel 
        objekWisataTabel.setRowCount(0);
        
        // isi tabel
        for(int i = 0; i < list.size(); i++){
            row[0] = " " +  list.get(i).getNamaObjekWisata() + " (" + list.get(i).getLokasi() + ")";
            row[1] = list.get(i).getKetersediaanTiket();
            row[2] = " " + KonversiRupiah.konversi(list.get(i).getHargaTiket());
            row[3] = list.get(i).getJamOperasional();
            row[4] = " " + list.get(i).getKategori().getNamaKategori();
            
            objekWisataTabel.addRow(row);
        }
    }
    
    // method - kategori
    final void getKategoriList(String where){
        
        // inisialisasti tabel data kategori
        KategoriTable modelKategori = new KategoriTable();
        listKategori = modelKategori.tampilKategori(where);
        
        // inisialisasi array list
        ArrayList<Kategori> list = new ArrayList();
        list = listKategori;
        
        // ambil tabel kategori
        DefaultTableModel kategoriTabel = (DefaultTableModel)tabelKategori.getModel();
        Object[] row = new Object[3];
        
        // hapus semua isi tabel
        kategoriTabel.setRowCount(0);
        
        // isi tabel dengan array list kategori
        for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getIdKategori();
            row[1] = " " + list.get(i).getNamaKategori();
            row[2] = " " + list.get(i).getDeskripsiKategori();
            
            kategoriTabel.addRow(row);
        }
    }
    
    // method - kategori
    final void getPembayaranList(){
        
        // inisialisasi tabel data pembayaran
        PembayaranTable modelPembayaran = new PembayaranTable();
        listPembayaran = modelPembayaran.tampilPembayaran(false);
        
        // inisialiasasi array list
        ArrayList<Pembayaran> list = new ArrayList();
        list = listPembayaran;
        
        // ambil tabel pembayaran
        DefaultTableModel pembayaranTabel = (DefaultTableModel)tabelPembayaran.getModel();
        Object[] row = new Object[4];
        
        // hapus semua isi tabel
        pembayaranTabel.setRowCount(0);
        
        // isi tabel pembayaran
        for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getIdPembayaran();
            row[1] = list.get(i).getNamaPembayaran();
            
            if(list.get(i).isPenggunaan() == true){
                row[2] = "Aktif";
            }else{
                row[2] = "Tidak Aktif";
            }
            
            row[3] = list.get(i).getCreatedAt();
            
            pembayaranTabel.addRow(row);
        }
    }
    
    // method - profil
    public final void setProfil(){
        
        // set semua labe profil sesuai admin yang login
        lblProfilUsername.setText(": " + profil.getUsername());
        lblProfilNama.setText(": " + profil.getNamaAdmin());
        lblProfilTanggalLahir.setText(": " + profil.getTanggalLahir());
        taProfilAlamat.setText(profil.getAlamat());
        lblProfilNoTelepon.setText(": " + profil.getNoTelepon());
        lblProfilEmail.setText(": " + profil.getEmail());
        lblProfilJenisKelamin.setText(": " + profil.getJenisKelamin());
        lblProfilJabatan.setText(": " + profil.getJabatan());
        lblProfilDibuatPada.setText(": " + profil.getCreatedAt());
        lblProfilTerakhirDiubah.setText(": " + profil.getUpdatedAt());
    }
    
    
    // METHOD METHOD UNTUK TAMPILAN FRAME DAN CONTENT
    // method pilih halaman
    final public void pilihHalaman(String parameter){
        // untuk memilih halaman diperlukan parameter untuk mengatur panel konten mana yang akan ditampilkan dan data mana yang akan diinisialisasi
        
        // pilihHalaman untuk dashboard
        if(parameter.equals("dashboard")){
            getDashboardData();
            cl.show(panelContent, "panelDashboard");
            
        // pilihHalaman untuk transaksi
        }else if(parameter.equals("transaksi")){
            
            getTransaksiList(tfTransaksiCari.getText(), cbTransaksiLimit.getSelectedItem().toString());
            cl.show(panelContent, "panelTransaksi");
        
        // pilihHalaman untuk pelanggan
        }else if(parameter.equals("pelanggan")){
            
            tfPelangganCari.setText("");
            cbPelangganLimit.setSelectedIndex(0);
            getPelangganList("", "25");
            cl.show(panelContent, "panelPelanggan");
            
        // pilihHalaman untuk promo diskon
        }else if(parameter.equals("promo-diskon")){
            
//            cbPromoDiskonLevel.setSelectedIndex(0);
//            dpPromoDiskonCari.setDate(new Date());
            getPromoDiskonList("", "", "25");
            cl.show(panelContent, "panelPromoDiskon");
            
        // pilihHalaman untuk objek wisata
        }else if(parameter.equals("objek-wisata")){
            
            tfObjekWisataCari.setText("");
            cbObjekWisataUrutkan.setSelectedIndex(0);
            getObjekWisataList("", "default");
            cl.show(panelContent, "panelObjekWisata");
            
        // pilihHalaman untuk kategori
        }else if(parameter.equals("kategori")){
            tfKategoriCari.setText("");
            getKategoriList("");
            cl.show(panelContent, "panelKategori");
        
        // pilihHalaman untuk pembayaran
        }else if(parameter.equals("pembayaran")){
            getPembayaranList();
            cl.show(panelContent, "panelPembayaran");
               
        // pilihHalaman untuk profil
        }else if(parameter.equals("profil")){
            setProfil();
            cl.show(panelContent, "panelProfil");
            
        // pilihHalaman untuk ganti password
        }else if(parameter.equals("ganti-password")){
            cl.show(panelContent, "panelGantiPassword");
        }
        
        navigasiButtonReset(parameter);
    }
    
    // method navigasiButtonReset
    final void navigasiButtonReset(String parameter){
        
        // untuk mengatur tombol tombol navigasi
        // seluruh tombol navigasi akan direset terlebih dahulu kemudian mengubah salah satu tombol sesuai
        // dengan parameter atau konten yang ditampilkan
        btnNavigasiDashboard.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiDashboard.setForeground(Color.white);
       
        btnNavigasiTransaksi.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiTransaksi.setForeground(Color.white);
        btnNavigasiPelanggan.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiPelanggan.setForeground(Color.white);
        btnNavigasiPromoDiskon.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiPromoDiskon.setForeground(Color.white);
        
        btnNavigasiObjekWisata.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiObjekWisata.setForeground(Color.white);
        btnNavigasiKategori.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiKategori.setForeground(Color.white);
        btnNavigasiPembayaran.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiPembayaran.setForeground(Color.white);
        
        btnNavigasiProfil.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiProfil.setForeground(Color.white);
        btnNavigasiGantiPassword.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiGantiPassword.setForeground(Color.white);
        
        // mengatur salah satu tombol navigasi
        if(parameter.equals("dashboard")){
            btnNavigasiDashboard.setBackground(Color.white);
            btnNavigasiDashboard.setForeground(new java.awt.Color(23,70,90));
        
        }else if(parameter.equals("pelanggan")){
            btnNavigasiPelanggan.setBackground(Color.white);
            btnNavigasiPelanggan.setForeground(new java.awt.Color(23,70,90));
                    
        }else if(parameter.equals("transaksi")){
            btnNavigasiTransaksi.setBackground(Color.white);
            btnNavigasiTransaksi.setForeground(new java.awt.Color(23,70,90));

        }else if(parameter.equals("promo-diskon")){
            btnNavigasiPromoDiskon.setBackground(Color.white);
            btnNavigasiPromoDiskon.setForeground(new java.awt.Color(23,70,90));
            
        }else if(parameter.equals("objek-wisata")){
            btnNavigasiObjekWisata.setBackground(Color.white);
            btnNavigasiObjekWisata.setForeground(new java.awt.Color(23,70,90));
            
        }else if(parameter.equals("kategori")){
            btnNavigasiKategori.setBackground(Color.white);
            btnNavigasiKategori.setForeground(new java.awt.Color(23,70,90));
        
        }else if(parameter.equals("pembayaran")){
            btnNavigasiPembayaran.setBackground(Color.white);
            btnNavigasiPembayaran.setForeground(new java.awt.Color(23,70,90));
        
        }else if(parameter.equals("profil")){
            btnNavigasiProfil.setBackground(Color.white);
            btnNavigasiProfil.setForeground(new java.awt.Color(23,70,90));
            
        }else if(parameter.equals("ganti-password")){
            btnNavigasiGantiPassword.setBackground(Color.white);
            btnNavigasiGantiPassword.setForeground(new java.awt.Color(23,70,90));
            
        } 
    }
    
    // mengatur beberapa isi kolom pada beberapa tabel
    final void centerColumnTable(){
        
        // tabel transaksi
        tabelTransaksi.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelTransaksi.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabelTransaksi.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tabelTransaksi.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tabelTransaksi.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        
        // tabel pelanggan
        tabelPelanggan.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabelPelanggan.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        
        // tabel promo diskon
        tabelPromoDiskon.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelPromoDiskon.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabelPromoDiskon.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabelPromoDiskon.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tabelPromoDiskon.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        
        // tabel objek wisata
        tabelObjekWisata.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabelObjekWisata.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabelObjekWisata.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        // tabel kategori
        tabelKategori.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        
        // tabel pembayaran
        tabelPembayaran.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelPembayaran.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabelPembayaran.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabelPembayaran.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUpPromoDiskon = new javax.swing.JPopupMenu();
        menuItemEdit = new javax.swing.JMenuItem();
        menuItemHapus = new javax.swing.JMenuItem();
        popUpPembayaran = new javax.swing.JPopupMenu();
        menuItemEditPembayaran = new javax.swing.JMenuItem();
        menuItemHapusPembayaran = new javax.swing.JMenuItem();
        popUpTransaksi = new javax.swing.JPopupMenu();
        menuItemLihat = new javax.swing.JMenuItem();
        menuItemEditTransaksi = new javax.swing.JMenuItem();
        panelContainer = new javax.swing.JPanel();
        panelTopBar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblHeaderName = new javax.swing.JLabel();
        panelNavigation = new javax.swing.JPanel();
        btnNavigasiDashboard = new javax.swing.JButton();
        btnNavigasiTransaksi = new javax.swing.JButton();
        btnNavigasiPelanggan = new javax.swing.JButton();
        btnNavigasiPromoDiskon = new javax.swing.JButton();
        btnNavigasiObjekWisata = new javax.swing.JButton();
        btnNavigasiKategori = new javax.swing.JButton();
        btnNavigasiPembayaran = new javax.swing.JButton();
        btnNavigasiProfil = new javax.swing.JButton();
        btnNavigasiGantiPassword = new javax.swing.JButton();
        btnNavigasiKeluar = new javax.swing.JButton();
        panelContent = new javax.swing.JPanel();
        panelDashboard = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pnlDashboardTransaksi = new javax.swing.JPanel();
        lblDashboardTransaksi = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        pnlDashboardPelanggan = new javax.swing.JPanel();
        lblDashboardPelanggan = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        pnlDashboardObjekWisata = new javax.swing.JPanel();
        lblDashboardObjekWisata = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        pnlDashboardKategori = new javax.swing.JPanel();
        lblDashboardKategori = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblDashboardNama = new javax.swing.JLabel();
        panelTransaksi = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelTransaksi = new javax.swing.JTable();
        tfTransaksiCari = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnTransaksiTambah = new javax.swing.JButton();
        cbTransaksiLimit = new javax.swing.JComboBox<>();
        panelPelanggan = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelPelanggan = new javax.swing.JTable();
        tfPelangganCari = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnPelangganTambah = new javax.swing.JButton();
        cbPelangganLimit = new javax.swing.JComboBox<>();
        panelDetailPelanggan = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnPelangganKembali = new javax.swing.JButton();
        lblPelangganDetailNama = new javax.swing.JLabel();
        lblPelangganDetailNama2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblPelangganDetailNoTelepon = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblPelangganDetailEmail = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        taPelangganDetailAlamat = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        lblPelangganDetailDibuatPada = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblPelangganDetailTerakhirDiubah = new javax.swing.JLabel();
        btnPelangganEdit = new javax.swing.JButton();
        btnPelangganHapus = new javax.swing.JButton();
        tfPelangganDetailIndex = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        lblPelangganDetailTransaksiSelesai = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblPelangganDetailLevel = new javax.swing.JLabel();
        panelPromoDiskon = new javax.swing.JPanel();
        btnPromoDiskonReset = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelPromoDiskon = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        btnPromoDiskonTambah = new javax.swing.JButton();
        cbPromoDiskonLevel = new javax.swing.JComboBox<>();
        dpPromoDiskonCari = new org.jdesktop.swingx.JXDatePicker();
        cbPromoDiskonLimit = new javax.swing.JComboBox<>();
        panelObjekWisata = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelObjekWisata = new javax.swing.JTable();
        tfObjekWisataCari = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        btnObjekWisataTambah = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        cbObjekWisataUrutkan = new javax.swing.JComboBox<>();
        panelKategori = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelKategori = new javax.swing.JTable();
        tfKategoriCari = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblKategoriNama = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblKategoriID = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taKategoriDetailDeskripsi = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        lblKategoriDibuatPada = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblKategoriTerakhirDiubah = new javax.swing.JLabel();
        btnKategoriEdit = new javax.swing.JButton();
        btnKategoriTambah = new javax.swing.JButton();
        btnKategoriHapus = new javax.swing.JButton();
        panelPembayaran = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelPembayaran = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        btnPembayaranTambah = new javax.swing.JButton();
        panelProfil = new javax.swing.JPanel();
        btnProfilEdit = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        taProfilAlamat = new javax.swing.JTextArea();
        lblProfilUsername = new javax.swing.JLabel();
        lblProfilNama = new javax.swing.JLabel();
        lblProfilTanggalLahir = new javax.swing.JLabel();
        lblProfilNoTelepon = new javax.swing.JLabel();
        lblProfilEmail = new javax.swing.JLabel();
        lblProfilJenisKelamin = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        lblProfilDibuatPada = new javax.swing.JLabel();
        lblProfilTerakhirDiubah = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        lblProfilJabatan = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        panelGantiPassword = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        tfPasswordLama = new javax.swing.JPasswordField();
        jLabel61 = new javax.swing.JLabel();
        tfPasswordBaru = new javax.swing.JPasswordField();
        jLabel62 = new javax.swing.JLabel();
        tfKonfirmasiPasswordBaru = new javax.swing.JPasswordField();
        btnGantiPassword = new javax.swing.JButton();

        menuItemEdit.setBackground(new java.awt.Color(255, 255, 254));
        menuItemEdit.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        menuItemEdit.setForeground(new java.awt.Color(23, 70, 90));
        menuItemEdit.setText("Edit");
        menuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEditActionPerformed(evt);
            }
        });
        popUpPromoDiskon.add(menuItemEdit);

        menuItemHapus.setBackground(new java.awt.Color(255, 255, 254));
        menuItemHapus.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        menuItemHapus.setForeground(new java.awt.Color(23, 70, 90));
        menuItemHapus.setText("Hapus");
        menuItemHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemHapusActionPerformed(evt);
            }
        });
        popUpPromoDiskon.add(menuItemHapus);

        menuItemEditPembayaran.setBackground(new java.awt.Color(255, 255, 254));
        menuItemEditPembayaran.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        menuItemEditPembayaran.setForeground(new java.awt.Color(23, 70, 90));
        menuItemEditPembayaran.setText("Edit");
        menuItemEditPembayaran.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        menuItemEditPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEditPembayaranActionPerformed(evt);
            }
        });
        popUpPembayaran.add(menuItemEditPembayaran);

        menuItemHapusPembayaran.setBackground(new java.awt.Color(255, 255, 254));
        menuItemHapusPembayaran.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        menuItemHapusPembayaran.setText("Hapus");
        menuItemHapusPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemHapusPembayaranActionPerformed(evt);
            }
        });
        popUpPembayaran.add(menuItemHapusPembayaran);

        menuItemLihat.setBackground(new java.awt.Color(255, 255, 254));
        menuItemLihat.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        menuItemLihat.setForeground(new java.awt.Color(23, 70, 90));
        menuItemLihat.setText("Lihat");
        menuItemLihat.setToolTipText("");
        menuItemLihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLihatActionPerformed(evt);
            }
        });
        popUpTransaksi.add(menuItemLihat);

        menuItemEditTransaksi.setBackground(new java.awt.Color(255, 255, 254));
        menuItemEditTransaksi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        menuItemEditTransaksi.setForeground(new java.awt.Color(28, 28, 90));
        menuItemEditTransaksi.setText("Edit");
        menuItemEditTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEditTransaksiActionPerformed(evt);
            }
        });
        popUpTransaksi.add(menuItemEditTransaksi);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WisataQ - Admin");
        setName("mainFrame"); // NOI18N

        panelContainer.setBackground(new java.awt.Color(255, 255, 255));

        panelTopBar.setBackground(new java.awt.Color(238, 241, 238));
        panelTopBar.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(23, 70, 90));
        jLabel1.setText("WisataQ");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 5));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelTopBar.add(jLabel1, java.awt.BorderLayout.CENTER);

        lblHeaderName.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblHeaderName.setText("Halo, admin");
        lblHeaderName.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 15));
        panelTopBar.add(lblHeaderName, java.awt.BorderLayout.LINE_END);

        panelNavigation.setBackground(new java.awt.Color(23, 70, 90));

        btnNavigasiDashboard.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiDashboard.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/dashboard.png"))); // NOI18N
        btnNavigasiDashboard.setText("  Dashboard");
        btnNavigasiDashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiDashboardMouseClicked(evt);
            }
        });

        btnNavigasiTransaksi.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiTransaksi.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/ticket.png"))); // NOI18N
        btnNavigasiTransaksi.setText("  Transaksi");
        btnNavigasiTransaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiTransaksiMouseClicked(evt);
            }
        });

        btnNavigasiPelanggan.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiPelanggan.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/pelanggan.png"))); // NOI18N
        btnNavigasiPelanggan.setText("  Pelanggan");
        btnNavigasiPelanggan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiPelanggan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiPelangganMouseClicked(evt);
            }
        });

        btnNavigasiPromoDiskon.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiPromoDiskon.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiPromoDiskon.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiPromoDiskon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/promo-diskon.png"))); // NOI18N
        btnNavigasiPromoDiskon.setText("  Promo & Diskon");
        btnNavigasiPromoDiskon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiPromoDiskon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiPromoDiskon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiPromoDiskonMouseClicked(evt);
            }
        });

        btnNavigasiObjekWisata.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiObjekWisata.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiObjekWisata.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiObjekWisata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/objek-wisata.png"))); // NOI18N
        btnNavigasiObjekWisata.setText("  Objek Wisata");
        btnNavigasiObjekWisata.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiObjekWisata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiObjekWisata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiObjekWisataMouseClicked(evt);
            }
        });

        btnNavigasiKategori.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiKategori.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiKategori.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/kategori.png"))); // NOI18N
        btnNavigasiKategori.setText("  Kategori");
        btnNavigasiKategori.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiKategori.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiKategoriMouseClicked(evt);
            }
        });

        btnNavigasiPembayaran.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiPembayaran.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiPembayaran.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiPembayaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/pembayaran.png"))); // NOI18N
        btnNavigasiPembayaran.setText("  Pembayaran");
        btnNavigasiPembayaran.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiPembayaran.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiPembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiPembayaranMouseClicked(evt);
            }
        });

        btnNavigasiProfil.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiProfil.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiProfil.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiProfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/profil.png"))); // NOI18N
        btnNavigasiProfil.setText("  Profil");
        btnNavigasiProfil.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiProfil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiProfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiProfilMouseClicked(evt);
            }
        });

        btnNavigasiGantiPassword.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiGantiPassword.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiGantiPassword.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiGantiPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/ganti-password.png"))); // NOI18N
        btnNavigasiGantiPassword.setText("  Ganti Password");
        btnNavigasiGantiPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiGantiPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiGantiPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiGantiPasswordMouseClicked(evt);
            }
        });

        btnNavigasiKeluar.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiKeluar.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiKeluar.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logout.png"))); // NOI18N
        btnNavigasiKeluar.setText("Keluar");
        btnNavigasiKeluar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiKeluar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiKeluarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelNavigationLayout = new javax.swing.GroupLayout(panelNavigation);
        panelNavigation.setLayout(panelNavigationLayout);
        panelNavigationLayout.setHorizontalGroup(
            panelNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNavigasiDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
            .addComponent(btnNavigasiTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiKategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiObjekWisata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiPromoDiskon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiPembayaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiKeluar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiGantiPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiProfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelNavigationLayout.setVerticalGroup(
            panelNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNavigationLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnNavigasiDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNavigasiTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNavigasiPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNavigasiPromoDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnNavigasiObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNavigasiKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNavigasiPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNavigasiProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnNavigasiGantiPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavigasiKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelContent.setBackground(new java.awt.Color(255, 255, 255));
        panelContent.setLayout(new java.awt.CardLayout());

        panelDashboard.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(23, 70, 90));
        jLabel27.setText("Dashboard");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(2, 2, 20, 20));

        pnlDashboardTransaksi.setBackground(new java.awt.Color(23, 70, 90));
        pnlDashboardTransaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        pnlDashboardTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlDashboardTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDashboardTransaksiMouseClicked(evt);
            }
        });
        pnlDashboardTransaksi.setLayout(new java.awt.BorderLayout(15, 5));

        lblDashboardTransaksi.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        lblDashboardTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        lblDashboardTransaksi.setText("20");
        lblDashboardTransaksi.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardTransaksi.add(lblDashboardTransaksi, java.awt.BorderLayout.CENTER);

        jLabel52.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/transaksi-45.png"))); // NOI18N
        jLabel52.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardTransaksi.add(jLabel52, java.awt.BorderLayout.LINE_START);

        jLabel53.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Transaksi Selesai");
        jLabel53.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardTransaksi.add(jLabel53, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(pnlDashboardTransaksi);

        pnlDashboardPelanggan.setBackground(new java.awt.Color(23, 70, 90));
        pnlDashboardPelanggan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        pnlDashboardPelanggan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlDashboardPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDashboardPelangganMouseClicked(evt);
            }
        });
        pnlDashboardPelanggan.setLayout(new java.awt.BorderLayout(15, 5));

        lblDashboardPelanggan.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        lblDashboardPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        lblDashboardPelanggan.setText("20");
        lblDashboardPelanggan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardPelanggan.add(lblDashboardPelanggan, java.awt.BorderLayout.CENTER);

        jLabel54.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/pelanggan-45.png"))); // NOI18N
        jLabel54.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardPelanggan.add(jLabel54, java.awt.BorderLayout.LINE_START);

        jLabel55.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Pelanggan");
        jLabel55.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardPelanggan.add(jLabel55, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(pnlDashboardPelanggan);

        pnlDashboardObjekWisata.setBackground(new java.awt.Color(23, 70, 90));
        pnlDashboardObjekWisata.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        pnlDashboardObjekWisata.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlDashboardObjekWisata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDashboardObjekWisataMouseClicked(evt);
            }
        });
        pnlDashboardObjekWisata.setLayout(new java.awt.BorderLayout(15, 5));

        lblDashboardObjekWisata.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        lblDashboardObjekWisata.setForeground(new java.awt.Color(255, 255, 255));
        lblDashboardObjekWisata.setText("20");
        lblDashboardObjekWisata.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardObjekWisata.add(lblDashboardObjekWisata, java.awt.BorderLayout.CENTER);

        jLabel56.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/objek-wisata-45.png"))); // NOI18N
        jLabel56.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardObjekWisata.add(jLabel56, java.awt.BorderLayout.LINE_START);

        jLabel57.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Objek Wisata");
        jLabel57.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardObjekWisata.add(jLabel57, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(pnlDashboardObjekWisata);

        pnlDashboardKategori.setBackground(new java.awt.Color(23, 70, 90));
        pnlDashboardKategori.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        pnlDashboardKategori.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlDashboardKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDashboardKategoriMouseClicked(evt);
            }
        });
        pnlDashboardKategori.setLayout(new java.awt.BorderLayout(15, 5));

        lblDashboardKategori.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        lblDashboardKategori.setForeground(new java.awt.Color(255, 255, 255));
        lblDashboardKategori.setText("20");
        lblDashboardKategori.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardKategori.add(lblDashboardKategori, java.awt.BorderLayout.CENTER);

        jLabel58.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/kategori-45.png"))); // NOI18N
        jLabel58.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardKategori.add(jLabel58, java.awt.BorderLayout.LINE_START);

        jLabel59.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Kategori");
        jLabel59.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardKategori.add(jLabel59, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(pnlDashboardKategori);

        jLabel28.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(28, 28, 39));
        jLabel28.setText("Selamat datang,");

        lblDashboardNama.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        lblDashboardNama.setForeground(new java.awt.Color(23, 70, 90));
        lblDashboardNama.setText("nama_admin");

        javax.swing.GroupLayout panelDashboardLayout = new javax.swing.GroupLayout(panelDashboard);
        panelDashboard.setLayout(panelDashboardLayout);
        panelDashboardLayout.setHorizontalGroup(
            panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDashboardLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDashboardLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDashboardNama))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        panelDashboardLayout.setVerticalGroup(
            panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDashboardLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(lblDashboardNama))
                .addGap(43, 43, 43)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(176, Short.MAX_VALUE))
        );

        panelContent.add(panelDashboard, "panelDashboard");

        panelTransaksi.setBackground(new java.awt.Color(255, 255, 255));

        tabelTransaksi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode", "Tanggal", "Pelanggan", "Objek Wisata", "Tiket", "Status", "Admin", "Dibuat Pada"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelTransaksi.setToolTipText("");
        tabelTransaksi.setComponentPopupMenu(popUpTransaksi);
        tabelTransaksi.setRowHeight(25);
        tabelTransaksi.setSelectionBackground(new java.awt.Color(23, 70, 90));
        tabelTransaksi.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelTransaksi.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelTransaksi.getTableHeader().setReorderingAllowed(false);
        tabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTransaksiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelTransaksi);
        if (tabelTransaksi.getColumnModel().getColumnCount() > 0) {
            tabelTransaksi.getColumnModel().getColumn(4).setMinWidth(70);
            tabelTransaksi.getColumnModel().getColumn(4).setPreferredWidth(70);
            tabelTransaksi.getColumnModel().getColumn(4).setMaxWidth(70);
            tabelTransaksi.getColumnModel().getColumn(5).setMinWidth(90);
            tabelTransaksi.getColumnModel().getColumn(5).setPreferredWidth(90);
            tabelTransaksi.getColumnModel().getColumn(5).setMaxWidth(90);
        }

        tfTransaksiCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfTransaksiCariKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(28, 28, 39));
        jLabel11.setText("Cari :");

        jLabel12.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(23, 70, 90));
        jLabel12.setText("Transaksi");

        btnTransaksiTambah.setBackground(new java.awt.Color(23, 70, 90));
        btnTransaksiTambah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnTransaksiTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTransaksiTambah.setText("+ Tambah");
        btnTransaksiTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTransaksiTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTransaksiTambahMouseClicked(evt);
            }
        });

        cbTransaksiLimit.setBackground(new java.awt.Color(255, 255, 254));
        cbTransaksiLimit.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbTransaksiLimit.setForeground(new java.awt.Color(23, 70, 90));
        cbTransaksiLimit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "25", "50", "100", "Semua" }));
        cbTransaksiLimit.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        cbTransaksiLimit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTransaksiLimitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTransaksiLayout = new javax.swing.GroupLayout(panelTransaksi);
        panelTransaksi.setLayout(panelTransaksiLayout);
        panelTransaksiLayout.setHorizontalGroup(
            panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransaksiLayout.createSequentialGroup()
                .addGroup(panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTransaksiLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                            .addGroup(panelTransaksiLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTransaksiTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelTransaksiLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTransaksiCari, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbTransaksiLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelTransaksiLayout.setVerticalGroup(
            panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransaksiLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(btnTransaksiTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfTransaksiCari, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(cbTransaksiLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        panelContent.add(panelTransaksi, "panelTransaksi");

        panelPelanggan.setBackground(new java.awt.Color(255, 255, 255));

        tabelPelanggan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tabelPelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nama", "Level", "Alamat", "No Telepon", "Email", "Dibuat Pada"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelPelanggan.setToolTipText("");
        tabelPelanggan.setRowHeight(25);
        tabelPelanggan.setSelectionBackground(new java.awt.Color(23, 70, 90));
        tabelPelanggan.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelPelanggan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelPelanggan.getTableHeader().setReorderingAllowed(false);
        tabelPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPelangganMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelPelanggan);

        tfPelangganCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPelangganCariActionPerformed(evt);
            }
        });
        tfPelangganCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPelangganCariKeyPressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(28, 28, 39));
        jLabel13.setText("Cari :");

        jLabel14.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(23, 70, 90));
        jLabel14.setText("Pelanggan");

        btnPelangganTambah.setBackground(new java.awt.Color(23, 70, 90));
        btnPelangganTambah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnPelangganTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnPelangganTambah.setText("+ Tambah");
        btnPelangganTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPelangganTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPelangganTambahMouseClicked(evt);
            }
        });

        cbPelangganLimit.setBackground(new java.awt.Color(255, 255, 254));
        cbPelangganLimit.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbPelangganLimit.setForeground(new java.awt.Color(23, 70, 90));
        cbPelangganLimit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "25", "50", "100", "Semua" }));
        cbPelangganLimit.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        cbPelangganLimit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPelangganLimitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPelangganLayout = new javax.swing.GroupLayout(panelPelanggan);
        panelPelanggan.setLayout(panelPelangganLayout);
        panelPelangganLayout.setHorizontalGroup(
            panelPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPelangganLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                    .addGroup(panelPelangganLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPelangganTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPelangganLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfPelangganCari, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbPelangganLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelPelangganLayout.setVerticalGroup(
            panelPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPelangganLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(btnPelangganTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(panelPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPelangganCari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(cbPelangganLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4)
                .addContainerGap())
        );

        panelContent.add(panelPelanggan, "panelPelanggan");

        panelDetailPelanggan.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(28, 28, 39));
        jLabel15.setText("Nama");

        jLabel16.setFont(new java.awt.Font("Poppins", 0, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(28, 28, 39));
        jLabel16.setText("Pelanggan :");

        btnPelangganKembali.setBackground(new java.awt.Color(255, 255, 250));
        btnPelangganKembali.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnPelangganKembali.setForeground(new java.awt.Color(28, 28, 39));
        btnPelangganKembali.setText("Kembali");
        btnPelangganKembali.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPelangganKembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPelangganKembaliMouseClicked(evt);
            }
        });

        lblPelangganDetailNama.setFont(new java.awt.Font("Poppins SemiBold", 0, 20)); // NOI18N
        lblPelangganDetailNama.setForeground(new java.awt.Color(23, 70, 90));
        lblPelangganDetailNama.setText("nama_pelanggan");

        lblPelangganDetailNama2.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        lblPelangganDetailNama2.setForeground(new java.awt.Color(23, 70, 90));
        lblPelangganDetailNama2.setText(": nama_pelanggan");

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(28, 28, 39));
        jLabel18.setText("No Telepon ");

        lblPelangganDetailNoTelepon.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        lblPelangganDetailNoTelepon.setForeground(new java.awt.Color(23, 70, 90));
        lblPelangganDetailNoTelepon.setText(": no_telepon");

        jLabel19.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(28, 28, 39));
        jLabel19.setText("Email");

        lblPelangganDetailEmail.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        lblPelangganDetailEmail.setForeground(new java.awt.Color(23, 70, 90));
        lblPelangganDetailEmail.setText(": email");

        jLabel20.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(28, 28, 39));
        jLabel20.setText("Alamat");

        taPelangganDetailAlamat.setEditable(false);
        taPelangganDetailAlamat.setColumns(20);
        taPelangganDetailAlamat.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        taPelangganDetailAlamat.setForeground(new java.awt.Color(23, 70, 90));
        taPelangganDetailAlamat.setLineWrap(true);
        taPelangganDetailAlamat.setRows(5);
        taPelangganDetailAlamat.setWrapStyleWord(true);
        taPelangganDetailAlamat.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jScrollPane5.setViewportView(taPelangganDetailAlamat);

        jLabel21.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setText("Dibuat Pada");

        lblPelangganDetailDibuatPada.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblPelangganDetailDibuatPada.setForeground(new java.awt.Color(102, 102, 102));
        lblPelangganDetailDibuatPada.setText(": dibuat_pada");

        jLabel22.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("Terakhir Diubah :");

        lblPelangganDetailTerakhirDiubah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblPelangganDetailTerakhirDiubah.setForeground(new java.awt.Color(102, 102, 102));
        lblPelangganDetailTerakhirDiubah.setText(": terakhir_diubah");

        btnPelangganEdit.setBackground(new java.awt.Color(23, 70, 90));
        btnPelangganEdit.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnPelangganEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnPelangganEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/edit.png"))); // NOI18N
        btnPelangganEdit.setText("Edit");
        btnPelangganEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPelangganEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPelangganEditMouseClicked(evt);
            }
        });

        btnPelangganHapus.setBackground(new java.awt.Color(255, 255, 250));
        btnPelangganHapus.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnPelangganHapus.setForeground(new java.awt.Color(28, 28, 39));
        btnPelangganHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hapus.png"))); // NOI18N
        btnPelangganHapus.setText("Hapus");
        btnPelangganHapus.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(28, 28, 39), 1, true));
        btnPelangganHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPelangganHapusMouseClicked(evt);
            }
        });

        tfPelangganDetailIndex.setEditable(false);

        jLabel17.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(28, 28, 39));
        jLabel17.setText("Transaksi Selesai");

        lblPelangganDetailTransaksiSelesai.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        lblPelangganDetailTransaksiSelesai.setForeground(new java.awt.Color(23, 70, 90));
        lblPelangganDetailTransaksiSelesai.setText(": transaksi_selesai");

        jLabel23.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(28, 28, 39));
        jLabel23.setText("Level");

        lblPelangganDetailLevel.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        lblPelangganDetailLevel.setForeground(new java.awt.Color(23, 70, 90));
        lblPelangganDetailLevel.setText(": level");

        javax.swing.GroupLayout panelDetailPelangganLayout = new javax.swing.GroupLayout(panelDetailPelanggan);
        panelDetailPelanggan.setLayout(panelDetailPelangganLayout);
        panelDetailPelangganLayout.setHorizontalGroup(
            panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailPelangganLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetailPelangganLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(lblPelangganDetailNama)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfPelangganDetailIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPelangganKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDetailPelangganLayout.createSequentialGroup()
                        .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDetailPelangganLayout.createSequentialGroup()
                                .addComponent(btnPelangganHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPelangganEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelDetailPelangganLayout.createSequentialGroup()
                                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22))
                                .addGap(31, 31, 31)
                                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPelangganDetailTerakhirDiubah)
                                    .addComponent(lblPelangganDetailDibuatPada)))
                            .addGroup(panelDetailPelangganLayout.createSequentialGroup()
                                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel15))
                                .addGap(18, 18, 18)
                                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPelangganDetailNama2)
                                    .addComponent(lblPelangganDetailEmail)
                                    .addComponent(lblPelangganDetailNoTelepon))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelDetailPelangganLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPelangganDetailLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(lblPelangganDetailTransaksiSelesai, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDetailPelangganLayout.setVerticalGroup(
            panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailPelangganLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(btnPelangganKembali)
                    .addComponent(lblPelangganDetailNama))
                .addGap(12, 12, 12)
                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPelangganHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(btnPelangganEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblPelangganDetailNama2))
                .addGap(18, 18, 18)
                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPelangganDetailNoTelepon)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lblPelangganDetailEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(lblPelangganDetailTransaksiSelesai))
                    .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(lblPelangganDetailLevel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(lblPelangganDetailDibuatPada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDetailPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(lblPelangganDetailTerakhirDiubah))
                .addGap(21, 21, 21)
                .addComponent(tfPelangganDetailIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelContent.add(panelDetailPelanggan, "panelDetailPelanggan");

        panelPromoDiskon.setBackground(new java.awt.Color(255, 255, 255));

        btnPromoDiskonReset.setBackground(new java.awt.Color(255, 255, 253));
        btnPromoDiskonReset.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnPromoDiskonReset.setForeground(new java.awt.Color(23, 70, 90));
        btnPromoDiskonReset.setText("Reset");
        btnPromoDiskonReset.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));
        btnPromoDiskonReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromoDiskonResetMouseClicked(evt);
            }
        });

        tabelPromoDiskon.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tabelPromoDiskon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Presentase Diskon", "Level", "Tanggal Tersedia", "Tanggal Berakhir"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelPromoDiskon.setComponentPopupMenu(popUpPromoDiskon);
        tabelPromoDiskon.setInheritsPopupMenu(true);
        tabelPromoDiskon.setRowHeight(25);
        tabelPromoDiskon.setSelectionBackground(new java.awt.Color(23, 70, 90));
        tabelPromoDiskon.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelPromoDiskon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelPromoDiskon.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tabelPromoDiskon);
        if (tabelPromoDiskon.getColumnModel().getColumnCount() > 0) {
            tabelPromoDiskon.getColumnModel().getColumn(0).setMinWidth(50);
            tabelPromoDiskon.getColumnModel().getColumn(0).setMaxWidth(50);
            tabelPromoDiskon.getColumnModel().getColumn(1).setMinWidth(150);
            tabelPromoDiskon.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabelPromoDiskon.getColumnModel().getColumn(1).setMaxWidth(150);
        }

        jLabel29.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(23, 70, 90));
        jLabel29.setText("Promo Diskon");

        btnPromoDiskonTambah.setBackground(new java.awt.Color(23, 70, 90));
        btnPromoDiskonTambah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnPromoDiskonTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnPromoDiskonTambah.setText("+ Tambah");
        btnPromoDiskonTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPromoDiskonTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromoDiskonTambahMouseClicked(evt);
            }
        });

        cbPromoDiskonLevel.setBackground(new java.awt.Color(255, 255, 254));
        cbPromoDiskonLevel.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbPromoDiskonLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Level", "Classic", "Silver", "Gold", "Platinum", "Semua" }));
        cbPromoDiskonLevel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        cbPromoDiskonLevel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbPromoDiskonLevelItemStateChanged(evt);
            }
        });

        dpPromoDiskonCari.setBackground(new java.awt.Color(255, 255, 254));
        dpPromoDiskonCari.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        dpPromoDiskonCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dpPromoDiskonCariActionPerformed(evt);
            }
        });

        cbPromoDiskonLimit.setBackground(new java.awt.Color(255, 255, 254));
        cbPromoDiskonLimit.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbPromoDiskonLimit.setForeground(new java.awt.Color(23, 70, 90));
        cbPromoDiskonLimit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "25", "50", "100", "Semua" }));
        cbPromoDiskonLimit.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        cbPromoDiskonLimit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPromoDiskonLimitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPromoDiskonLayout = new javax.swing.GroupLayout(panelPromoDiskon);
        panelPromoDiskon.setLayout(panelPromoDiskonLayout);
        panelPromoDiskonLayout.setHorizontalGroup(
            panelPromoDiskonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPromoDiskonLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelPromoDiskonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPromoDiskonLayout.createSequentialGroup()
                        .addComponent(cbPromoDiskonLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dpPromoDiskonCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPromoDiskonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbPromoDiskonLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                    .addGroup(panelPromoDiskonLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPromoDiskonTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelPromoDiskonLayout.setVerticalGroup(
            panelPromoDiskonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPromoDiskonLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelPromoDiskonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(btnPromoDiskonTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelPromoDiskonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPromoDiskonReset)
                    .addGroup(panelPromoDiskonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbPromoDiskonLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dpPromoDiskonCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbPromoDiskonLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelContent.add(panelPromoDiskon, "panelPromoDiskon");

        panelObjekWisata.setBackground(new java.awt.Color(255, 255, 255));

        tabelObjekWisata.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tabelObjekWisata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nama", "Tiket", "Harga", "Jam Operasional", "Kategori"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelObjekWisata.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelObjekWisata.setRowHeight(25);
        tabelObjekWisata.setSelectionBackground(new java.awt.Color(23, 70, 90));
        tabelObjekWisata.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelObjekWisata.getTableHeader().setReorderingAllowed(false);
        tabelObjekWisata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelObjekWisataMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabelObjekWisata);
        if (tabelObjekWisata.getColumnModel().getColumnCount() > 0) {
            tabelObjekWisata.getColumnModel().getColumn(0).setMinWidth(250);
            tabelObjekWisata.getColumnModel().getColumn(0).setPreferredWidth(250);
            tabelObjekWisata.getColumnModel().getColumn(0).setMaxWidth(250);
            tabelObjekWisata.getColumnModel().getColumn(1).setMinWidth(80);
            tabelObjekWisata.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabelObjekWisata.getColumnModel().getColumn(1).setMaxWidth(80);
        }

        tfObjekWisataCari.setBackground(new java.awt.Color(255, 255, 254));
        tfObjekWisataCari.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tfObjekWisataCari.setSelectedTextColor(new java.awt.Color(255, 255, 254));
        tfObjekWisataCari.setSelectionColor(new java.awt.Color(23, 27, 90));
        tfObjekWisataCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfObjekWisataCariKeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(28, 28, 39));
        jLabel24.setText("Cari :");

        jLabel25.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(23, 70, 90));
        jLabel25.setText("Objek Wisata");

        btnObjekWisataTambah.setBackground(new java.awt.Color(23, 70, 90));
        btnObjekWisataTambah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnObjekWisataTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnObjekWisataTambah.setText("+ Tambah");
        btnObjekWisataTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnObjekWisataTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnObjekWisataTambahMouseClicked(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(28, 28, 39));
        jLabel26.setText("Urutkan : ");

        cbObjekWisataUrutkan.setBackground(new java.awt.Color(255, 255, 254));
        cbObjekWisataUrutkan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbObjekWisataUrutkan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID (ASC)", "ID (DESC)", "Nama (ASC)", "Nama (DESC)", "Tiket (ASC)", "Tiket (DESC)" }));
        cbObjekWisataUrutkan.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        cbObjekWisataUrutkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbObjekWisataUrutkanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelObjekWisataLayout = new javax.swing.GroupLayout(panelObjekWisata);
        panelObjekWisata.setLayout(panelObjekWisataLayout);
        panelObjekWisataLayout.setHorizontalGroup(
            panelObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelObjekWisataLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelObjekWisataLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfObjekWisataCari, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbObjekWisataUrutkan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                    .addGroup(panelObjekWisataLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnObjekWisataTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelObjekWisataLayout.setVerticalGroup(
            panelObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelObjekWisataLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(btnObjekWisataTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(tfObjekWisataCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(cbObjekWisataUrutkan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelContent.add(panelObjekWisata, "panelObjekWisata");

        panelKategori.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Poppins SemiBold", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(28, 28, 39));
        jLabel2.setText("Detail");

        tabelKategori.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tabelKategori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nama", "Deskripsi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelKategori.setRowHeight(25);
        tabelKategori.setSelectionBackground(new java.awt.Color(23, 70, 90));
        tabelKategori.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelKategori.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelKategori.getTableHeader().setReorderingAllowed(false);
        tabelKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKategoriMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelKategori);
        if (tabelKategori.getColumnModel().getColumnCount() > 0) {
            tabelKategori.getColumnModel().getColumn(0).setMinWidth(100);
            tabelKategori.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabelKategori.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        tfKategoriCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfKategoriCariKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(28, 28, 39));
        jLabel3.setText("Cari :");

        jLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(23, 70, 90));
        jLabel4.setText("Kategori");

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Nama :");

        lblKategoriNama.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblKategoriNama.setForeground(new java.awt.Color(28, 28, 39));
        lblKategoriNama.setText("NAMA");

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("ID :");

        lblKategoriID.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblKategoriID.setForeground(new java.awt.Color(28, 28, 39));
        lblKategoriID.setText("ID");

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Deskripsi : ");

        taKategoriDetailDeskripsi.setEditable(false);
        taKategoriDetailDeskripsi.setBackground(new java.awt.Color(255, 255, 255));
        taKategoriDetailDeskripsi.setColumns(20);
        taKategoriDetailDeskripsi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        taKategoriDetailDeskripsi.setLineWrap(true);
        taKategoriDetailDeskripsi.setRows(5);
        taKategoriDetailDeskripsi.setWrapStyleWord(true);
        taKategoriDetailDeskripsi.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jScrollPane2.setViewportView(taKategoriDetailDeskripsi);

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Dibuat Pada :");

        lblKategoriDibuatPada.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        lblKategoriDibuatPada.setForeground(new java.awt.Color(102, 102, 102));
        lblKategoriDibuatPada.setText("DIBUAT PADA");

        jLabel9.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Terakhir Diubah :");

        lblKategoriTerakhirDiubah.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        lblKategoriTerakhirDiubah.setForeground(new java.awt.Color(102, 102, 102));
        lblKategoriTerakhirDiubah.setText("TERAKHIR DIUBAH");

        btnKategoriEdit.setBackground(new java.awt.Color(23, 70, 90));
        btnKategoriEdit.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnKategoriEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnKategoriEdit.setText("Edit");
        btnKategoriEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnKategoriEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKategoriEditMouseClicked(evt);
            }
        });

        btnKategoriTambah.setBackground(new java.awt.Color(23, 70, 90));
        btnKategoriTambah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnKategoriTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnKategoriTambah.setText("+ Tambah");
        btnKategoriTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnKategoriTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKategoriTambahMouseClicked(evt);
            }
        });

        btnKategoriHapus.setBackground(new java.awt.Color(250, 255, 255));
        btnKategoriHapus.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnKategoriHapus.setText("Hapus");
        btnKategoriHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelKategoriLayout = new javax.swing.GroupLayout(panelKategori);
        panelKategori.setLayout(panelKategoriLayout);
        panelKategoriLayout.setHorizontalGroup(
            panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKategoriLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelKategoriLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfKategoriCari, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(panelKategoriLayout.createSequentialGroup()
                        .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelKategoriLayout.createSequentialGroup()
                                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(25, 25, 25)
                                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblKategoriID)
                                    .addComponent(lblKategoriNama, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelKategoriLayout.createSequentialGroup()
                                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnKategoriHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblKategoriDibuatPada, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblKategoriTerakhirDiubah, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnKategoriEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                        .addGap(108, 108, 108))
                    .addGroup(panelKategoriLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelKategoriLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnKategoriTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelKategoriLayout.setVerticalGroup(
            panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKategoriLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnKategoriTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfKategoriCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addGap(13, 13, 13)
                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblKategoriID)
                    .addComponent(jLabel7))
                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelKategoriLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(panelKategoriLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblKategoriNama))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnKategoriEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnKategoriHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lblKategoriDibuatPada))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lblKategoriTerakhirDiubah))
                        .addGap(12, 12, 12))))
        );

        panelContent.add(panelKategori, "panelKategori");

        panelPembayaran.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 254));

        tabelPembayaran.setBackground(new java.awt.Color(255, 255, 254));
        tabelPembayaran.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tabelPembayaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Penggunaan", "Ditambahkan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelPembayaran.setComponentPopupMenu(popUpPembayaran);
        tabelPembayaran.setRowHeight(25);
        tabelPembayaran.setSelectionBackground(new java.awt.Color(23, 70, 90));
        tabelPembayaran.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelPembayaran.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelPembayaran.setShowGrid(true);
        tabelPembayaran.getTableHeader().setReorderingAllowed(false);
        tabelPembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPembayaranMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tabelPembayaran);

        jLabel30.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(23, 70, 90));
        jLabel30.setText("Pembayaran");

        btnPembayaranTambah.setBackground(new java.awt.Color(23, 70, 90));
        btnPembayaranTambah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnPembayaranTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnPembayaranTambah.setText("+ Tambah");
        btnPembayaranTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPembayaranTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPembayaranTambahMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelPembayaranLayout = new javax.swing.GroupLayout(panelPembayaran);
        panelPembayaran.setLayout(panelPembayaranLayout);
        panelPembayaranLayout.setHorizontalGroup(
            panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPembayaranLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                    .addGroup(panelPembayaranLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPembayaranTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelPembayaranLayout.setVerticalGroup(
            panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPembayaranLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(btnPembayaranTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE))
        );

        panelContent.add(panelPembayaran, "panelPembayaran");

        panelProfil.setBackground(new java.awt.Color(255, 255, 255));

        btnProfilEdit.setBackground(new java.awt.Color(255, 255, 253));
        btnProfilEdit.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnProfilEdit.setForeground(new java.awt.Color(23, 70, 90));
        btnProfilEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/edit-tosca.png"))); // NOI18N
        btnProfilEdit.setText("Edit");
        btnProfilEdit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));
        btnProfilEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProfilEditMouseClicked(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(23, 70, 90));
        jLabel33.setText("Profil");

        jLabel10.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(28, 28, 39));
        jLabel10.setText("Username");

        jLabel41.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(28, 28, 39));
        jLabel41.setText("Nama");

        jLabel42.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(28, 28, 39));
        jLabel42.setText("Tanggal Lahir");

        jLabel43.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(28, 28, 39));
        jLabel43.setText("Alamat : ");

        jLabel44.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(28, 28, 39));
        jLabel44.setText("No Telepon");

        jLabel45.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(28, 28, 39));
        jLabel45.setText("Email");

        jLabel46.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(28, 28, 39));
        jLabel46.setText("Jenis Kelamin");

        taProfilAlamat.setEditable(false);
        taProfilAlamat.setColumns(20);
        taProfilAlamat.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        taProfilAlamat.setForeground(new java.awt.Color(23, 70, 90));
        taProfilAlamat.setLineWrap(true);
        taProfilAlamat.setRows(5);
        taProfilAlamat.setWrapStyleWord(true);
        taProfilAlamat.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jScrollPane10.setViewportView(taProfilAlamat);

        lblProfilUsername.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblProfilUsername.setForeground(new java.awt.Color(23, 70, 90));
        lblProfilUsername.setText("username");

        lblProfilNama.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblProfilNama.setForeground(new java.awt.Color(23, 70, 90));
        lblProfilNama.setText("nama");

        lblProfilTanggalLahir.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblProfilTanggalLahir.setForeground(new java.awt.Color(23, 70, 90));
        lblProfilTanggalLahir.setText("tanggal_lahir");

        lblProfilNoTelepon.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblProfilNoTelepon.setForeground(new java.awt.Color(23, 70, 90));
        lblProfilNoTelepon.setText("no_telepon");

        lblProfilEmail.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblProfilEmail.setForeground(new java.awt.Color(23, 70, 90));
        lblProfilEmail.setText("email");

        lblProfilJenisKelamin.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblProfilJenisKelamin.setForeground(new java.awt.Color(23, 70, 90));
        lblProfilJenisKelamin.setText("jenis_kelamin");

        jLabel47.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(102, 102, 102));
        jLabel47.setText("Dibuat Pada");

        lblProfilDibuatPada.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblProfilDibuatPada.setForeground(new java.awt.Color(102, 102, 102));
        lblProfilDibuatPada.setText(": dibuat_pada");

        lblProfilTerakhirDiubah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblProfilTerakhirDiubah.setForeground(new java.awt.Color(102, 102, 102));
        lblProfilTerakhirDiubah.setText(": terakhir_diubah");

        jLabel48.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(102, 102, 102));
        jLabel48.setText("Terakhir Diubah :");

        jLabel49.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(28, 28, 39));
        jLabel49.setText("Jabatan");

        lblProfilJabatan.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblProfilJabatan.setForeground(new java.awt.Color(23, 70, 90));
        lblProfilJabatan.setText("jabatan");

        btnLogout.setBackground(new java.awt.Color(255, 255, 253));
        btnLogout.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(23, 70, 90));
        btnLogout.setText("Logout");
        btnLogout.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelProfilLayout = new javax.swing.GroupLayout(panelProfil);
        panelProfil.setLayout(panelProfilLayout);
        panelProfilLayout.setHorizontalGroup(
            panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProfilLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProfilLayout.createSequentialGroup()
                        .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelProfilLayout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addGap(207, 405, Short.MAX_VALUE)))
                        .addGap(386, 386, 386))
                    .addGroup(panelProfilLayout.createSequentialGroup()
                        .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProfilLayout.createSequentialGroup()
                                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel41))
                                .addGap(18, 18, 18)
                                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProfilNama, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblProfilTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblProfilUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelProfilLayout.createSequentialGroup()
                                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel44))
                                .addGap(18, 18, 18)
                                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelProfilLayout.createSequentialGroup()
                                        .addComponent(lblProfilNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel45)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblProfilEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lblProfilJenisKelamin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                        .addComponent(lblProfilJabatan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelProfilLayout.createSequentialGroup()
                        .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addGroup(panelProfilLayout.createSequentialGroup()
                                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel48))
                                .addGap(31, 31, 31)
                                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblProfilDibuatPada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblProfilTerakhirDiubah, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)))
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelProfilLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(30, 30, 30)
                        .addComponent(btnProfilEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        panelProfilLayout.setVerticalGroup(
            panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProfilLayout.createSequentialGroup()
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProfilLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProfilLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProfilEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblProfilUsername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(lblProfilNama))
                .addGap(18, 18, 18)
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(lblProfilTanggalLahir))
                .addGap(27, 27, 27)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane10)
                .addGap(33, 33, 33)
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(lblProfilNoTelepon)
                    .addComponent(jLabel45)
                    .addComponent(lblProfilEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(lblProfilJenisKelamin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(lblProfilJabatan))
                .addGap(30, 30, 30)
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(lblProfilDibuatPada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(lblProfilTerakhirDiubah))
                .addGap(17, 17, 17))
        );

        panelContent.add(panelProfil, "panelProfil");

        panelGantiPassword.setBackground(new java.awt.Color(255, 255, 255));

        jLabel50.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(23, 70, 90));
        jLabel50.setText("Ganti Password");

        jLabel51.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(28, 28, 39));
        jLabel51.setText("Password Lama : ");

        tfPasswordLama.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jLabel61.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(28, 28, 39));
        jLabel61.setText("Password Baru : ");

        tfPasswordBaru.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jLabel62.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(28, 28, 39));
        jLabel62.setText("Konfirmasi Password Baru : ");

        tfKonfirmasiPasswordBaru.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        btnGantiPassword.setBackground(new java.awt.Color(23, 70, 90));
        btnGantiPassword.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnGantiPassword.setForeground(new java.awt.Color(255, 255, 255));
        btnGantiPassword.setText("Ganti Password");
        btnGantiPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGantiPasswordMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelGantiPasswordLayout = new javax.swing.GroupLayout(panelGantiPassword);
        panelGantiPassword.setLayout(panelGantiPasswordLayout);
        panelGantiPasswordLayout.setHorizontalGroup(
            panelGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGantiPasswordLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(panelGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGantiPasswordLayout.createSequentialGroup()
                        .addGroup(panelGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel61)
                            .addComponent(jLabel51))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelGantiPasswordLayout.createSequentialGroup()
                        .addGroup(panelGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGantiPassword)
                            .addGroup(panelGantiPasswordLayout.createSequentialGroup()
                                .addComponent(jLabel62)
                                .addGap(18, 18, 18)
                                .addGroup(panelGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfKonfirmasiPasswordBaru, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                    .addComponent(tfPasswordBaru)
                                    .addComponent(tfPasswordLama))))
                        .addGap(0, 308, Short.MAX_VALUE))))
        );
        panelGantiPasswordLayout.setVerticalGroup(
            panelGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGantiPasswordLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel50)
                .addGap(29, 29, 29)
                .addGroup(panelGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(tfPasswordLama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(panelGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(tfPasswordBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(panelGantiPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(tfKonfirmasiPasswordBaru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(btnGantiPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(316, Short.MAX_VALUE))
        );

        panelContent.add(panelGantiPassword, "panelGantiPassword");

        javax.swing.GroupLayout panelContainerLayout = new javax.swing.GroupLayout(panelContainer);
        panelContainer.setLayout(panelContainerLayout);
        panelContainerLayout.setHorizontalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTopBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addComponent(panelNavigation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelContainerLayout.setVerticalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addComponent(panelTopBar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelNavigation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKategoriTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKategoriTambahMouseClicked
        KategoriTable tableKategori = new KategoriTable();

        setEnabled(false);
        KategoriFormFrame tambahFrame = new KategoriFormFrame(this,tableKategori.getIdBaru());
        tambahFrame.setVisible(true);

    }//GEN-LAST:event_btnKategoriTambahMouseClicked

    private void tfKategoriCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfKategoriCariKeyPressed
        getKategoriList(tfKategoriCari.getText());
    }//GEN-LAST:event_tfKategoriCariKeyPressed

    private void tabelKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelKategoriMouseClicked
        int row = tabelKategori.getSelectedRow();
        if(row >= 0){
            Kategori kategoriTerpilih = listKategori.get(row);
            
            lblKategoriID.setText(kategoriTerpilih.getIdKategori());
            lblKategoriNama.setText(kategoriTerpilih.getNamaKategori());
            taKategoriDetailDeskripsi.setText(kategoriTerpilih.getDeskripsiKategori());
            lblKategoriDibuatPada.setText(kategoriTerpilih.getCreatedAt());
            lblKategoriTerakhirDiubah.setText(kategoriTerpilih.getUpdatedAt());
        }
    }//GEN-LAST:event_tabelKategoriMouseClicked

    private void btnTransaksiTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiTambahMouseClicked
        setEnabled(false);
        TransaksiFormFrame transaksiFormFrame = new TransaksiFormFrame(this);
        transaksiFormFrame.setVisible(true);
    }//GEN-LAST:event_btnTransaksiTambahMouseClicked

    private void tabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTransaksiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelTransaksiMouseClicked

    private void btnNavigasiTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiTransaksiMouseClicked
        pilihHalaman("transaksi");
    }//GEN-LAST:event_btnNavigasiTransaksiMouseClicked

    private void btnNavigasiKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiKategoriMouseClicked
        pilihHalaman("kategori");
    }//GEN-LAST:event_btnNavigasiKategoriMouseClicked

    private void btnNavigasiPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiPelangganMouseClicked
        pilihHalaman("pelanggan");
    }//GEN-LAST:event_btnNavigasiPelangganMouseClicked

    private void tabelPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPelangganMouseClicked
        int row = tabelPelanggan.getSelectedRow();
        if(row >= 0){
            Pelanggan p = listPelanggan.get(row);
            
            tfPelangganDetailIndex.setText(String.valueOf(row));
            
            lblPelangganDetailNama.setText(p.getNamaPelanggan());
            lblPelangganDetailNama2.setText(": " + p.getNamaPelanggan());
            lblPelangganDetailNoTelepon.setText(": " + p.getNoTelepon());
            lblPelangganDetailEmail.setText(": " + p.getEmail());
            taPelangganDetailAlamat.setText( p.getAlamat());
            lblPelangganDetailTransaksiSelesai.setText(": " + String.valueOf(p.getJumlahTransaksi()));
            lblPelangganDetailLevel.setText(": " + p.getLevel());
            lblPelangganDetailDibuatPada.setText(": " + p.getCreatedAt());
            lblPelangganDetailTerakhirDiubah.setText(": " + p.getUpdatedAt());
            
            tfPelangganDetailIndex.setVisible(false);
            cl.show(panelContent, "panelDetailPelanggan");
        }
    }//GEN-LAST:event_tabelPelangganMouseClicked

    private void tfPelangganCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPelangganCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPelangganCariActionPerformed

    private void tfPelangganCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPelangganCariKeyPressed
        getPelangganList(tfPelangganCari.getText(), cbPelangganLimit.getSelectedItem().toString());
    }//GEN-LAST:event_tfPelangganCariKeyPressed

    private void btnPelangganTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPelangganTambahMouseClicked
        setEnabled(false);
        PelangganFormFrame pelangganFormFrame = new PelangganFormFrame(this);
        pelangganFormFrame.setVisible(true);
    }//GEN-LAST:event_btnPelangganTambahMouseClicked

    private void btnPelangganKembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPelangganKembaliMouseClicked
        cl.show(panelContent, "panelPelanggan");
    }//GEN-LAST:event_btnPelangganKembaliMouseClicked

    private void btnPelangganEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPelangganEditMouseClicked
        Pelanggan p = listPelanggan.get(Integer.parseInt(tfPelangganDetailIndex.getText()));
        PelangganFormFrame pelangganFormFrame = new PelangganFormFrame(p,this);
        pelangganFormFrame.setVisible(true);
    }//GEN-LAST:event_btnPelangganEditMouseClicked

    private void btnPelangganHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPelangganHapusMouseClicked
        int konfirmasi = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin menghapus pelanggan ini?", "Pelanggan", JOptionPane.YES_NO_OPTION );
        if(konfirmasi == 0){
            
            PelangganTable pT = new PelangganTable();
            int id = listPelanggan.get(Integer.parseInt(tfPelangganDetailIndex.getText())).getIdPelanggan();
            
            boolean delete = pT.hapusPelanggan(id);
            
            if(delete == true){
               
                JOptionPane.showMessageDialog(rootPane, "Pelanggan berhasil dihapus", "Pelanggan", JOptionPane.INFORMATION_MESSAGE);
                pilihHalaman("pelanggan");
                
            }else{
                JOptionPane.showMessageDialog(rootPane, "Pelanggan gagal dihapus", "Pelanggan", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_btnPelangganHapusMouseClicked

    private void btnNavigasiObjekWisataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiObjekWisataMouseClicked
        pilihHalaman("objek-wisata");
    }//GEN-LAST:event_btnNavigasiObjekWisataMouseClicked

    private void btnNavigasiPromoDiskonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiPromoDiskonMouseClicked
       pilihHalaman("promo-diskon");
    }//GEN-LAST:event_btnNavigasiPromoDiskonMouseClicked

    private void btnNavigasiPembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiPembayaranMouseClicked
        pilihHalaman("pembayaran");
    }//GEN-LAST:event_btnNavigasiPembayaranMouseClicked

    private void btnNavigasiKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiKeluarMouseClicked

        int konfirmasi = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin keluar dari aplikasi ?", "Keluar", JOptionPane.YES_NO_OPTION);
        if(konfirmasi == 0){
            System.exit(0);
        }

    }//GEN-LAST:event_btnNavigasiKeluarMouseClicked

    private void btnNavigasiGantiPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiGantiPasswordMouseClicked
        tfPasswordLama.setText("");
        tfPasswordBaru.setText("");
        tfKonfirmasiPasswordBaru.setText("");
        pilihHalaman("ganti-password");
    }//GEN-LAST:event_btnNavigasiGantiPasswordMouseClicked

    private void btnNavigasiProfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiProfilMouseClicked
        pilihHalaman("profil");
    }//GEN-LAST:event_btnNavigasiProfilMouseClicked

    private void tabelObjekWisataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelObjekWisataMouseClicked
        int row = tabelObjekWisata.getSelectedRow();
              
        if(row >= 0){
            ObjekWisata ow = listObjekWisata.get(row);
            setEnabled(false);
            DetailObjekWisataFrame detailObjekWisata = new DetailObjekWisataFrame(this, ow);
            detailObjekWisata.setVisible(true);
        }
    }//GEN-LAST:event_tabelObjekWisataMouseClicked

    private void tfObjekWisataCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfObjekWisataCariKeyPressed
        cbObjekWisataUrutkan.setSelectedIndex(0);
        getObjekWisataList(tfObjekWisataCari.getText(), "default");        
    }//GEN-LAST:event_tfObjekWisataCariKeyPressed

    private void btnObjekWisataTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnObjekWisataTambahMouseClicked
        setEnabled(false);
        ObjekWisataFormFrame objekWisataFormFrame = new ObjekWisataFormFrame(this);
        objekWisataFormFrame.setVisible(true);
    }//GEN-LAST:event_btnObjekWisataTambahMouseClicked

    private void btnNavigasiDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiDashboardMouseClicked
        pilihHalaman("dashboard");
    }//GEN-LAST:event_btnNavigasiDashboardMouseClicked

    private void btnPromoDiskonTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromoDiskonTambahMouseClicked
        setEnabled(false);
        PromoDiskonFormFrame promoDiskonFormFrame = new PromoDiskonFormFrame(this);
        promoDiskonFormFrame.setVisible(true);
    }//GEN-LAST:event_btnPromoDiskonTambahMouseClicked

    private void menuItemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEditActionPerformed
       int row = tabelPromoDiskon.getSelectedRow();
        
        if(row >= 0){
            PromoDiskon promoDiskon = new PromoDiskon();
           
            promoDiskon.setIdPromoDiskon(Integer.parseInt(tabelPromoDiskon.getValueAt(row, 0).toString()));
            promoDiskon.setPresentaseDiskon(Integer.parseInt(tabelPromoDiskon.getValueAt(row, 1).toString()));
            promoDiskon.setLevel(tabelPromoDiskon.getValueAt(row, 2).toString());
            promoDiskon.setTanggalTersedia(tabelPromoDiskon.getValueAt(row, 3).toString());
            promoDiskon.setTanggalBerakhir(tabelPromoDiskon.getValueAt(row, 4).toString());

            setEnabled(false);
            PromoDiskonFormFrame promoDiskonFormFrame = new PromoDiskonFormFrame(this, promoDiskon);
            promoDiskonFormFrame.setVisible(true);   
        }
    }//GEN-LAST:event_menuItemEditActionPerformed

    private void tabelPembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPembayaranMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelPembayaranMouseClicked

    private void btnPembayaranTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPembayaranTambahMouseClicked
        setEnabled(false);
        PembayaranFormFrame pembayaranFormFrame = new PembayaranFormFrame(this);
        pembayaranFormFrame.setVisible(true);
    }//GEN-LAST:event_btnPembayaranTambahMouseClicked

    private void menuItemEditPembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEditPembayaranActionPerformed
        int row = tabelPembayaran.getSelectedRow(); // mengambil pembayaran yang dipilih
        
        if(row >= 0){ // memeriksa apakah salah satu data sudah dipilih
        
            // melakukan inisialisasi objek pembayaran dan mengisinya sesuai isi tabel
            Pembayaran pembayaran = new Pembayaran();
            
            pembayaran.setIdPembayaran(Integer.parseInt(tabelPembayaran.getValueAt(row, 0).toString()));
            pembayaran.setNamaPembayaran(tabelPembayaran.getValueAt(row, 1).toString());
            
            if(tabelPembayaran.getValueAt(row, 2).toString().equals("Aktif")){
                pembayaran.setPenggunaan(true);
            }else{
                pembayaran.setPenggunaan(false);
            }
            
            // inisialisasi form pembayaran dan menampilkannya
            PembayaranFormFrame pembayaranFormFrame = new PembayaranFormFrame(this, pembayaran);
            pembayaranFormFrame.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(rootPane, "Pilih pembayaran terlebih dahulu", "Pembayaran", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_menuItemEditPembayaranActionPerformed

    private void menuItemHapusPembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemHapusPembayaranActionPerformed
        int row = tabelPembayaran.getSelectedRow(); // mengambil pembayaran yang dipilih
        
        if(row >= 0){ // memeriksa apakah salah satu data sudah dipilih
        
            // menampilkan dialog konfirmasi apakah admin ingin menghapus data
            int konfirmasi = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin hapus pembayaran ? ", "Pembayaran", JOptionPane.YES_NO_OPTION);
            
            if(konfirmasi == 0){ // jika ya
                // inisialisasi tabel
                PembayaranTable pembayaranTable = new PembayaranTable();
                String delete = pembayaranTable.hapusPembayaran(tabelPembayaran.getValueAt(row, 0).toString()); // melakukan penghapusan
            
                if(delete.equals("SUCCESS")){ // jika hapus berhasil
                    JOptionPane.showMessageDialog(rootPane, "Pembayaran berhasil dihapus", "Pembayaran", JOptionPane.INFORMATION_MESSAGE);
                    getPembayaranList();
                }else{  // jika hapus gagal
                    
                    if(delete.contains("Pembayaran")){ // kembalian dari proses penghapus memiliki respon bukan FAILED
                        JOptionPane.showMessageDialog(rootPane, delete, "Pembayaran", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Pembayaran gagal dihapus", "Pembayaran", JOptionPane.ERROR_MESSAGE);
                    }
                }
            
            }
           
        }else{
            JOptionPane.showMessageDialog(rootPane, "Pilih pembayaran terlebih dahulu", "Pembayaran", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_menuItemHapusPembayaranActionPerformed

    private void btnKategoriEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKategoriEditMouseClicked
        int row = tabelKategori.getSelectedRow();
        
        if(row >= 0){
            Kategori kategoriTerpilih = listKategori.get(row);
            
            setEnabled(false);
            KategoriFormFrame kategoriFormFrame = new KategoriFormFrame(this, kategoriTerpilih);
            kategoriFormFrame.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(rootPane, "Pilih kategori telebih dahulu", "Kategori", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnKategoriEditMouseClicked

    private void btnKategoriHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriHapusActionPerformed
        int row = tabelKategori.getSelectedRow(); // mengambil row yang terpilih
        
        if(row >= 0){ // jika ada row yang dipilih
            
            // konfirmasi apakah admin akan benar-benar menghapus kategori
            int konfirmasi = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin menghapus kategori ini ?", "Kategori", JOptionPane.YES_NO_OPTION);

            
            if(konfirmasi == 0 ){ // jika konfirmasi YES
                // inisialisasi tabel kategori
                KategoriTable kategoriTable = new KategoriTable();
                String deleteKategori = kategoriTable.hapusKategori(lblKategoriID.getText()); // hapus kategori
                
                if(deleteKategori.equals("SUCCESS")){ // jika hapus berhasil
                    JOptionPane.showMessageDialog(rootPane, "Kategori berhasil dihapus", "Kategori", JOptionPane.INFORMATION_MESSAGE);
                    
                    lblKategoriID.setText("");
                    lblKategoriNama.setText("");
                    taKategoriDetailDeskripsi.setText("");
                    lblKategoriDibuatPada.setText("");
                    lblKategoriTerakhirDiubah.setText("");
                    
                    getKategoriList(""); // reset tabel kategori
                }else{ // jika hapus gagal
                    if(deleteKategori.contains("Kategori")){
                        JOptionPane.showMessageDialog(rootPane, deleteKategori, "Kategori", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Kategori gagal dihapus", "Kategori", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
        }else{ // jjka tidak ada row yang dipilij
            JOptionPane.showMessageDialog(rootPane, "Pilih kategori terlebih dahulu", "Kategori", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnKategoriHapusActionPerformed

    private void btnProfilEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfilEditMouseClicked
        setEnabled(false);
        AdminFormFrame adminFormFrame = new AdminFormFrame(this, profil);
        adminFormFrame.setVisible(true);
    }//GEN-LAST:event_btnProfilEditMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        dispose();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }//GEN-LAST:event_btnLogoutMouseClicked
    private void btnGantiPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGantiPasswordMouseClicked
        
        // ganti password
        
        // mengambil nilai dari nilai form
        String passLama = tfPasswordLama.getText();
        String passBaru = tfPasswordBaru.getText();
        String passKonBaru = tfKonfirmasiPasswordBaru.getText();
        
        // validasi
        if(passLama.equals("") || passBaru.equals("") || passKonBaru.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Form belum lengkap", "Ganti Password", JOptionPane.WARNING_MESSAGE);
        }else if(!passBaru.equals(passKonBaru)){ // jika password baru tidak sama dengan konfirmasi password
            JOptionPane.showMessageDialog(rootPane, "Password baru tidak sama", "Ganti Password", JOptionPane.WARNING_MESSAGE);
        }else{
            
            // inisialisasi tabel dan melakukan pengubahan password
            AdminTable adminTable = new AdminTable();
            String ubahPassword = adminTable.ubahPassword(profil.getUsername(), passLama, passBaru);
            
            // jika ubah password success
            if(ubahPassword.equals("SUCCESS")){
                JOptionPane.showMessageDialog(rootPane, "Password berhasil diganti","Ganti Password", JOptionPane.INFORMATION_MESSAGE);
            
                // reset form
                tfPasswordLama.setText("");
                tfPasswordBaru.setText("");
                tfKonfirmasiPasswordBaru.setText("");
            }else{
                // jika pada pesan error mengandung password maka akan menampilkan pesan sesuai yang diberikan oleh tab
                if(ubahPassword.contains("Password")){
                    JOptionPane.showMessageDialog(rootPane, ubahPassword,"Ganti Password", JOptionPane.ERROR_MESSAGE);
                
                    tfPasswordLama.setText("");
                    tfPasswordBaru.setText("");
                    tfKonfirmasiPasswordBaru.setText("");
                }else{
                    // jika gagal
                    JOptionPane.showMessageDialog(rootPane, "Password gagal diganti","Ganti Password", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        }
        

    }//GEN-LAST:event_btnGantiPasswordMouseClicked

    private void cbPromoDiskonLevelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbPromoDiskonLevelItemStateChanged

        // mengosongkan pencarian tanggal
        dpPromoDiskonCari.setDate(new Date());
        
        // mencari promo diskon berdasarkan level yang terpilih
        if(cbPromoDiskonLevel.getSelectedIndex() != 0){
            getPromoDiskonList(cbPromoDiskonLevel.getSelectedItem().toString(), "", cbPromoDiskonLimit.getSelectedItem().toString());
        }else{
            getPromoDiskonList("", "", cbPromoDiskonLimit.getSelectedItem().toString());
        }
        
        
    }//GEN-LAST:event_cbPromoDiskonLevelItemStateChanged

    private void btnPromoDiskonResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromoDiskonResetMouseClicked
        // reset pencarian promo diskon
        cbPromoDiskonLevel.setSelectedIndex(0);
        dpPromoDiskonCari.setDate(new Date());
        getPromoDiskonList("", "", "25");
    }//GEN-LAST:event_btnPromoDiskonResetMouseClicked

    private void pnlDashboardTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardTransaksiMouseClicked
        pilihHalaman("transaksi");
    }//GEN-LAST:event_pnlDashboardTransaksiMouseClicked

    private void pnlDashboardPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardPelangganMouseClicked
        pilihHalaman("pelanggan");
    }//GEN-LAST:event_pnlDashboardPelangganMouseClicked

    private void pnlDashboardObjekWisataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardObjekWisataMouseClicked
        pilihHalaman("objek-wisata");
    }//GEN-LAST:event_pnlDashboardObjekWisataMouseClicked

    private void cbObjekWisataUrutkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbObjekWisataUrutkanActionPerformed
        int index = cbObjekWisataUrutkan.getSelectedIndex();
        
        if(index == 0){
             getObjekWisataList(tfObjekWisataCari.getText(), "default");
        }else if(index == 1){
            getObjekWisataList(tfObjekWisataCari.getText(), "id-desc");
        }else if(index == 2){
            getObjekWisataList(tfObjekWisataCari.getText(), "nama-asc");
        }else if(index == 3){
            getObjekWisataList(tfObjekWisataCari.getText(), "nama-desc");
        }else if(index == 4){
            getObjekWisataList(tfObjekWisataCari.getText(), "tiket-asc");
        }else if(index == 5){
            getObjekWisataList(tfObjekWisataCari.getText(), "tiket-desc");
        }
        
    }//GEN-LAST:event_cbObjekWisataUrutkanActionPerformed

    private void dpPromoDiskonCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dpPromoDiskonCariActionPerformed
        cbPromoDiskonLevel.setSelectedIndex(0); // reset combo box level
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        // mencari promo diskon berdasarkan level
        getPromoDiskonList("", df.format(dpPromoDiskonCari.getDate()), cbPromoDiskonLimit.getSelectedItem().toString());
        
    }//GEN-LAST:event_dpPromoDiskonCariActionPerformed

    private void menuItemHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemHapusActionPerformed
        
        int row = tabelPromoDiskon.getSelectedRow();
        
        if(row >= 0){
        int konfirmasi = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin menghapus promo diskon ini ?", "Promo Diskon", JOptionPane.YES_NO_OPTION);
        
        if(konfirmasi == 0){
            PromoDiskonTable promoDiskonTable = new PromoDiskonTable();
            PromoDiskon pD = listPromoDiskon.get(tabelPromoDiskon.getSelectedRow());
            
            boolean delete = promoDiskonTable.hapusPromoDiskon(pD);
            
            if(delete == true){
                
                dpPromoDiskonCari.setDate(new Date());
                cbPromoDiskonLevel.setSelectedIndex(WIDTH);
                getPromoDiskonList("", "", "25");    
                JOptionPane.showMessageDialog(rootPane, "Promo Diskon berhasil dihapus", "Promo Diskon", JOptionPane.INFORMATION_MESSAGE);
            
            }else{
                JOptionPane.showMessageDialog(rootPane, "Promo Diskon gagal dihapus", "Promo Diskon", JOptionPane.ERROR_MESSAGE);
            }
            
            
        }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Pilih promo diskon terlebih dahulu", "Promo Diskon", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_menuItemHapusActionPerformed

    private void cbPelangganLimitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPelangganLimitActionPerformed
        getPelangganList(tfPelangganCari.getText(), cbPelangganLimit.getSelectedItem().toString());
    }//GEN-LAST:event_cbPelangganLimitActionPerformed

    private void cbPromoDiskonLimitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPromoDiskonLimitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPromoDiskonLimitActionPerformed

    private void cbTransaksiLimitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTransaksiLimitActionPerformed
       getTransaksiList(tfTransaksiCari.getText(), cbTransaksiLimit.getSelectedItem().toString());
    }//GEN-LAST:event_cbTransaksiLimitActionPerformed

    private void pnlDashboardKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardKategoriMouseClicked
        pilihHalaman("kategori");
    }//GEN-LAST:event_pnlDashboardKategoriMouseClicked

    private void menuItemEditTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEditTransaksiActionPerformed
        int row = tabelTransaksi.getSelectedRow();
        
        if(row >= 0){
            Transaksi transaksi = listTransaksi.get(row);
            
            setEnabled(false);
            TransaksiFormFrame transaksiFormFrame = new TransaksiFormFrame(this, transaksi, "edit");
            transaksiFormFrame.setVisible(true);
        }
    }//GEN-LAST:event_menuItemEditTransaksiActionPerformed

    private void menuItemLihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLihatActionPerformed
        int row = tabelTransaksi.getSelectedRow();
        
        if(row >= 0){
            Transaksi transaksi = listTransaksi.get(row);
            
            setEnabled(false);
            TransaksiFormFrame transaksiFormFrame = new TransaksiFormFrame(this, transaksi, "read");
            transaksiFormFrame.setVisible(true);
        }
        
    }//GEN-LAST:event_menuItemLihatActionPerformed

    private void tfTransaksiCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTransaksiCariKeyReleased
        getTransaksiList(tfTransaksiCari.getText(), cbTransaksiLimit.getSelectedItem().toString());
    }//GEN-LAST:event_tfTransaksiCariKeyReleased

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AdminFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AdminFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGantiPassword;
    private javax.swing.JButton btnKategoriEdit;
    private javax.swing.JButton btnKategoriHapus;
    private javax.swing.JButton btnKategoriTambah;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnNavigasiDashboard;
    private javax.swing.JButton btnNavigasiGantiPassword;
    private javax.swing.JButton btnNavigasiKategori;
    private javax.swing.JButton btnNavigasiKeluar;
    private javax.swing.JButton btnNavigasiObjekWisata;
    private javax.swing.JButton btnNavigasiPelanggan;
    private javax.swing.JButton btnNavigasiPembayaran;
    private javax.swing.JButton btnNavigasiProfil;
    private javax.swing.JButton btnNavigasiPromoDiskon;
    private javax.swing.JButton btnNavigasiTransaksi;
    private javax.swing.JButton btnObjekWisataTambah;
    private javax.swing.JButton btnPelangganEdit;
    private javax.swing.JButton btnPelangganHapus;
    private javax.swing.JButton btnPelangganKembali;
    private javax.swing.JButton btnPelangganTambah;
    private javax.swing.JButton btnPembayaranTambah;
    private javax.swing.JButton btnProfilEdit;
    private javax.swing.JButton btnPromoDiskonReset;
    private javax.swing.JButton btnPromoDiskonTambah;
    private javax.swing.JButton btnTransaksiTambah;
    private javax.swing.JComboBox<String> cbObjekWisataUrutkan;
    private javax.swing.JComboBox<String> cbPelangganLimit;
    private javax.swing.JComboBox<String> cbPromoDiskonLevel;
    private javax.swing.JComboBox<String> cbPromoDiskonLimit;
    private javax.swing.JComboBox<String> cbTransaksiLimit;
    private org.jdesktop.swingx.JXDatePicker dpPromoDiskonCari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblDashboardKategori;
    private javax.swing.JLabel lblDashboardNama;
    private javax.swing.JLabel lblDashboardObjekWisata;
    private javax.swing.JLabel lblDashboardPelanggan;
    private javax.swing.JLabel lblDashboardTransaksi;
    private javax.swing.JLabel lblHeaderName;
    private javax.swing.JLabel lblKategoriDibuatPada;
    private javax.swing.JLabel lblKategoriID;
    private javax.swing.JLabel lblKategoriNama;
    private javax.swing.JLabel lblKategoriTerakhirDiubah;
    private javax.swing.JLabel lblPelangganDetailDibuatPada;
    private javax.swing.JLabel lblPelangganDetailEmail;
    private javax.swing.JLabel lblPelangganDetailLevel;
    private javax.swing.JLabel lblPelangganDetailNama;
    private javax.swing.JLabel lblPelangganDetailNama2;
    private javax.swing.JLabel lblPelangganDetailNoTelepon;
    private javax.swing.JLabel lblPelangganDetailTerakhirDiubah;
    private javax.swing.JLabel lblPelangganDetailTransaksiSelesai;
    private javax.swing.JLabel lblProfilDibuatPada;
    private javax.swing.JLabel lblProfilEmail;
    private javax.swing.JLabel lblProfilJabatan;
    private javax.swing.JLabel lblProfilJenisKelamin;
    private javax.swing.JLabel lblProfilNama;
    private javax.swing.JLabel lblProfilNoTelepon;
    private javax.swing.JLabel lblProfilTanggalLahir;
    private javax.swing.JLabel lblProfilTerakhirDiubah;
    private javax.swing.JLabel lblProfilUsername;
    private javax.swing.JMenuItem menuItemEdit;
    private javax.swing.JMenuItem menuItemEditPembayaran;
    private javax.swing.JMenuItem menuItemEditTransaksi;
    private javax.swing.JMenuItem menuItemHapus;
    private javax.swing.JMenuItem menuItemHapusPembayaran;
    private javax.swing.JMenuItem menuItemLihat;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JPanel panelContent;
    private javax.swing.JPanel panelDashboard;
    private javax.swing.JPanel panelDetailPelanggan;
    private javax.swing.JPanel panelGantiPassword;
    private javax.swing.JPanel panelKategori;
    private javax.swing.JPanel panelNavigation;
    private javax.swing.JPanel panelObjekWisata;
    private javax.swing.JPanel panelPelanggan;
    private javax.swing.JPanel panelPembayaran;
    private javax.swing.JPanel panelProfil;
    private javax.swing.JPanel panelPromoDiskon;
    private javax.swing.JPanel panelTopBar;
    private javax.swing.JPanel panelTransaksi;
    private javax.swing.JPanel pnlDashboardKategori;
    private javax.swing.JPanel pnlDashboardObjekWisata;
    private javax.swing.JPanel pnlDashboardPelanggan;
    private javax.swing.JPanel pnlDashboardTransaksi;
    private javax.swing.JPopupMenu popUpPembayaran;
    private javax.swing.JPopupMenu popUpPromoDiskon;
    private javax.swing.JPopupMenu popUpTransaksi;
    private javax.swing.JTextArea taKategoriDetailDeskripsi;
    private javax.swing.JTextArea taPelangganDetailAlamat;
    private javax.swing.JTextArea taProfilAlamat;
    private javax.swing.JTable tabelKategori;
    private javax.swing.JTable tabelObjekWisata;
    private javax.swing.JTable tabelPelanggan;
    private javax.swing.JTable tabelPembayaran;
    private javax.swing.JTable tabelPromoDiskon;
    private javax.swing.JTable tabelTransaksi;
    private javax.swing.JTextField tfKategoriCari;
    private javax.swing.JPasswordField tfKonfirmasiPasswordBaru;
    private javax.swing.JTextField tfObjekWisataCari;
    private javax.swing.JPasswordField tfPasswordBaru;
    private javax.swing.JPasswordField tfPasswordLama;
    private javax.swing.JTextField tfPelangganCari;
    private javax.swing.JTextField tfPelangganDetailIndex;
    private javax.swing.JTextField tfTransaksiCari;
    // End of variables declaration//GEN-END:variables
}
