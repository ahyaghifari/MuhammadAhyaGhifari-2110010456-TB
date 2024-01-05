/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import model.Admin;
import model.ObjekWisata;
import chart.PieChartObjekWisata;
import chart.LineChartPelanggan;
import chart.LineChartTransaksi;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import model.PelangganTerdaftar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.jfree.chart.ChartPanel;
import table.ObjekWisataTable;
import table.PelangganTable;
import table.TransaksiTable;
import model.DataTransaksi;
import service.KonversiRupiah;
import table.AdminTable;

/**
 *
 * @author Ahya Ghifari
 */
public class ManajerFrame extends javax.swing.JFrame {

    // objek profil untuk menyimpan admin yang login
    Admin profil = new Admin();
    
    // inisialisasi card layout untuk panelContent
    CardLayout cl;
    
    // inisialisasi renderer tabel
    DefaultTableCellRenderer centerRenderer;
    
    // inisialisasi ArrayList untuk berbagai macam data untuk tabel
    ArrayList<DataTransaksi> listDataTransaksi = new ArrayList();
    ArrayList<PelangganTerdaftar> listDataPelanggan = new ArrayList();
    ArrayList<ObjekWisata> listObjekWisata = new ArrayList();
    
    ArrayList<Admin> listAdmin = new ArrayList();
    
    // menyiapkan format waktu atau tanggal yang diguanakan untuk pencarian
    SimpleDateFormat tahunFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat bulanFormat = new SimpleDateFormat("MM");
    
    String tahunSekarang = tahunFormat.format(new Date());
    String bulanSekarang = bulanFormat.format(new Date());
    
    /**
     * Creates new form ManajerFrame
     * @param admin
     */
    
    // KONSTRUKTOR
    public ManajerFrame(Admin admin) {
        
        // inisialisasi komponen dan layout serta renderer untuk tabel
        initComponents();
        setLocationRelativeTo(null);
        cl = (CardLayout)(panelContent.getLayout());        
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        
        // mengisi profil dengan admin yang login
        this.profil = admin;
        
        // mengatur label nama dengan profil yang login
        lblHeaderName.setText("Halo, " + profil.getNamaAdmin());
        lblDashboardNama.setText(profil.getUsername() + " (" + profil.getNamaAdmin() + ")");
        
        // mengisi keseluruhan combo box tahun
        setCbTahun();
        
        // mengatur semua combo box bulan
        setSelectedCbBulan(bulanSekarang, cbBulanObjekWisata);
        setSelectedCbBulan(bulanSekarang, cbBulanPelanggan);
        setSelectedCbBulan(bulanSekarang, cbBulanTransaksi);
        
        // memanggil method centerColumnTable mengatur beberapa isi tabel ketengah
        centerColumnTable();
        
        // memilih halaman dashboard untuk konten yang ditampilkan pertama
        pilihHalaman("dashboard");
    
    }
 
    
    // METHOD METHOD UNTUK PENGAMBILAN DATA    
    
    // method dashboard
    final void getDashboardData(){
        
        // inisialisai tabel tabel yang diperlukan
        TransaksiTable transaksiTable = new TransaksiTable();
        PelangganTable pelangganTable = new PelangganTable();
        ObjekWisataTable objekWisataTable = new ObjekWisataTable();
        AdminTable adminTable = new AdminTable();
        
        // mengambil perhitungan data beberapa tabel
        int countTransaksi = transaksiTable.dashboardCount();
        int countPelanggan = pelangganTable.dashboardCount();
        int countObjekWisata = objekWisataTable.dashboardCount();
        int countAdmin = adminTable.dashboardCount();
        
        // menampilkan ke panel panel dashboard
        lblDashboardTransaksi.setText(String.valueOf(countTransaksi));
        lblDashboardPelanggan.setText(String.valueOf(countPelanggan));
        lblDashboardObjekWisata.setText(String.valueOf(countObjekWisata));
        lblDashboardAdmin.setText(String.valueOf(countAdmin));
    }
    
    //method - transaksi    
    final void getTransaksiData(String bulan, String tahun){
        
        // mengambil bulan yang dipilih di combo box transkasi
        String bulanHuruf = cbBulanTransaksi.getSelectedItem().toString();
        
        // inisialisasi 3 data perhitungan keseluruhan data
        int jmlTransaksiSelesai = 0;
        int jmlTransaksiBatal = 0;
        double total = 0;

        // mengatur label keseluruhan data
        lblDataTransaksiPer.setText("Data Transaksi Per " + bulanHuruf + " Tahun " + tahun);
        
        // inisialisasi tabel transaksi dan mengisi arraylist tabeltransaksi
        TransaksiTable modelTableTransaksi = new TransaksiTable();
        listDataTransaksi = modelTableTransaksi.getDataTransaksi(bulan, tahun);
        
        ArrayList<DataTransaksi> list = new ArrayList();
        list = listDataTransaksi;
        
        // mengambil tabel transaksi, membuat isi ketengah dan mengisinya dengan objek
        DefaultTableModel transaksiTabel = (DefaultTableModel)tabelTransaksi.getModel();
        Object[] row = new Object[4];
        
        transaksiTabel.setRowCount(0);
        
        for(int i =0; i < listDataTransaksi.size(); i++){
            row[0] = list.get(i).getTanggal();
            row[1] = list.get(i).getTransaksiSelesai();
            row[2] = list.get(i).getTransaksiBatal();
            row[3] = " " + KonversiRupiah.konversi(list.get(i).getTotal());
        
            transaksiTabel.addRow(row);
            
            // melakukan penambahan untuk total perhitungan
            jmlTransaksiSelesai += list.get(i).getTransaksiSelesai();
            jmlTransaksiBatal += list.get(i).getTransaksiBatal();
            total += list.get(i).getTotal();
        }

        
        // menampilkan hasil perhitungan
        lblDataTransaksiSelesai.setText(": " + jmlTransaksiSelesai);
        lblDataTransaksiBatal.setText(": " + jmlTransaksiBatal);
        lblDataTransaksiTotal.setText(": " + KonversiRupiah.konversi(total));
        
        
        // inisialisasi chart atau statistik dari transaksi
        LineChartTransaksi chartStatus = new LineChartTransaksi(bulan, tahun);
        
        ChartPanel cp = chartStatus.showChart("");
        
        // menampilkan chart transaksi
        panelChartTransaksi.removeAll();
        panelChartTransaksi.add(cp, BorderLayout.CENTER);
        panelChartTransaksi.validate();
    }
    
    // method - data pelanggan
    final void getPelangganData(String bulan, String tahun){
        
        // inisialisasi tabel pelanggan dan mengisi arraylist pelanggan
        PelangganTable modelTablePelanggan = new PelangganTable();
        listDataPelanggan = modelTablePelanggan.pelangganTerdaftar(bulan, tahun);   
        
        ArrayList<PelangganTerdaftar> list = new ArrayList();
        list = listDataPelanggan;
        
        // mebgisi tabel pelanggan dengan list yang sudah didapatkan denganobjek
        DefaultTableModel dataPelangganTabel = (DefaultTableModel)tabelDataPelanggan.getModel();
        
        
        Object[] row = new Object[2];
        
        dataPelangganTabel.setRowCount(0);
        
        
        for(int i =0; i < list.size(); i++){
            row[0] = list.get(i).getTanggal();
            row[1] = list.get(i).getJmlPelanggan();
        
            dataPelangganTabel.addRow(row);
        }

        // inisialisasi chart pelanggan dengan bulan dan tahun dari combo box pelanggan
        LineChartPelanggan lineChartPelanggan = new LineChartPelanggan(bulan, tahun);
            
        ChartPanel ch = lineChartPelanggan.showChart("Data Pelanggan Terdaftar \n " + cbBulanPelanggan.getSelectedItem().toString() + " " + tahun);
        
        // menampilkan chart pelanggan
        panelPelangganTerdaftar.removeAll();
        panelPelangganTerdaftar.add(ch, BorderLayout.CENTER);
        panelPelangganTerdaftar.validate();
        
        
    }
    
    // method - data objek wisata
    final void getObjekWisataData(String bulan, String tahun){
        
        // inisialisasi tabel objek wisata dan mengisi array list objek wisata
        ObjekWisataTable modelObjekWisata = new ObjekWisataTable();
        listObjekWisata = modelObjekWisata.presentaseTransaksi(bulan, tahun, false); 
        
        // mengisi tabel objek wisata dengan list dan objek
        DefaultTableModel objekWisataTabel = (DefaultTableModel)tabelObjekWisata.getModel();
        Object[] row = new Object[2];
        
        objekWisataTabel.setRowCount(0);

            for(int i = 0; i < listObjekWisata.size(); i++){
                row[0] = " " + listObjekWisata.get(i).getNamaObjekWisata();
                row[1] = listObjekWisata.get(i).getJmlTransaksi();

                objekWisataTabel.addRow(row);
            }

            // reset container dari panel chart objek wisata
            panelPresentaseObjekWisata.removeAll();
            panelPresentaseObjekWisata.repaint();

            if(listObjekWisata.get(0).getJmlTransaksi() != 0){ // mengecek apakah data untuk chart tersedia
                
                // inisialisasi pie chart dari objek wisata
                PieChartObjekWisata pieChart = new PieChartObjekWisata("", bulan, tahun);

                ChartPanel ch = pieChart.showChart("Data Transaksi Objek Wisata \n " + cbBulanObjekWisata.getSelectedItem().toString() + " " + tahun);

                // menampilkan pie chart
                panelPresentaseObjekWisata.add(ch, BorderLayout.CENTER);
                panelPresentaseObjekWisata.validate();
            
            }else{
                // jika data pie chart tidak ada maka akan tampil tulisan data tidak ditemukan
                javax.swing.JLabel notfound = new javax.swing.JLabel();
                notfound.setFont(new java.awt.Font("Poppins", 0 ,14));
                notfound.setText("Data tidak ditemukan");
                notfound.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                panelPresentaseObjekWisata.add(notfound, BorderLayout.CENTER);
                panelPresentaseObjekWisata.validate();
                
            }
    }
    
    final void getAdminList(String keyword){
        
        // inisialisai tabel admin dan mengisi arraylist admin
        AdminTable adminTable = new AdminTable();
        listAdmin = adminTable.tampilAdmin(keyword);
        
        // menampilkan list ke tabel dengan objek
        DefaultTableModel dataAdminTabel = (DefaultTableModel)tabelAdmin.getModel();
        Object[] row = new Object[6];
        
        dataAdminTabel.setRowCount(0);
        
        for(int i =0; i < listAdmin.size(); i++){
            row[0] = " " + listAdmin.get(i).getUsername();
            row[1] = " " + listAdmin.get(i).getNamaAdmin();
            row[2] = listAdmin.get(i).getTanggalLahir();
            row[3] = " " + listAdmin.get(i).getNoTelepon();
            row[4] = " " + listAdmin.get(i).getEmail();
            row[5] = listAdmin.get(i).getCreatedAt();
            
            dataAdminTabel.addRow(row);
        }
        
    }
    
    // method- set profil
    public final void setProfil(){
        
        // mengatur label label dari halaman profil sesuai dengan profil manajer yang login
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
        
        // data-transaksi
        }else if(parameter.equals("data-transaksi")){
            
            getTransaksiData(bulanSekarang, tahunSekarang);
            cl.show(panelContent, "panelDataTransaksi");
        
        // data-pelanggan
        }else if(parameter.equals("data-pelanggan")){
            getPelangganData(bulanSekarang, tahunSekarang);
            cl.show(panelContent, "panelDataPelanggan");
           
        // data-objek-wisata
        }else if(parameter.equals("data-objek-wisata")){
            
            getObjekWisataData(bulanSekarang, tahunSekarang);
            cl.show(panelContent, "panelDataObjekWisata");
            
        // data-admin
        }else if(parameter.equals("data-admin")){
            
            tfAdminCari.setText("");
            getAdminList("");
            cl.show(panelContent, "panelDataAdmin");
            
        // profil
        }else if(parameter.equals("profil")){
           
            setProfil();
            cl.show(panelContent, "panelProfil");
        
        // ganti-password
        }else if(parameter.equals("ganti-password")){
            cl.show(panelContent, "panelGantiPassword");
        }
        
        // method navigasi button reset
        navigasiButtonReset(parameter);
    }
    
    // method navigasiButtonReset
    final void navigasiButtonReset(String parameter){
        
        // untuk mengatur tombol tombol navigasi
        // seluruh tombol navigasi akan direset terlebih dahulu kemudian mengubah salah satu tombol sesuai
        // dengan parameter atau konten yang ditampilkan
        
        // reset seluruh tombol navigasi
        btnNavigasiDashboard.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiDashboard.setForeground(Color.white);
       
        btnNavigasiTransaksi.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiTransaksi.setForeground(Color.white);
        btnNavigasiPelanggan.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiPelanggan.setForeground(Color.white);
        
        btnNavigasiObjekWisata.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiObjekWisata.setForeground(Color.white);
        
        btnNavigasiAdmin.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiAdmin.setForeground(Color.white);
        
        btnNavigasiProfil.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiProfil.setForeground(Color.white);
        btnNavigasiGantiPassword.setBackground(new java.awt.Color(23,70,90));
        btnNavigasiGantiPassword.setForeground(Color.white);
        
        // mengatur salah satu tombol navigasi
        if(parameter.equals("dashboard")){
            btnNavigasiDashboard.setBackground(Color.white);
            btnNavigasiDashboard.setForeground(new java.awt.Color(23,70,90));
        
                    
        }else if(parameter.equals("data-transaksi")){
            btnNavigasiTransaksi.setBackground(Color.white);
            btnNavigasiTransaksi.setForeground(new java.awt.Color(23,70,90));
            
        }else if(parameter.equals("data-pelanggan")){
            btnNavigasiPelanggan.setBackground(Color.white);
            btnNavigasiPelanggan.setForeground(new java.awt.Color(23,70,90));

        }else if(parameter.equals("data-objek-wisata")){
           btnNavigasiObjekWisata.setBackground(new java.awt.Color(255,255,255));
           btnNavigasiObjekWisata.setForeground(new java.awt.Color(23,70,90));
            
        }else if(parameter.equals("data-admin")){
           btnNavigasiAdmin.setBackground(new java.awt.Color(255,255,255));
           btnNavigasiAdmin.setForeground(new java.awt.Color(23,70,90));
            
        }else if(parameter.equals("profil")){
           btnNavigasiProfil.setBackground(new java.awt.Color(255,255,255));
           btnNavigasiProfil.setForeground(new java.awt.Color(23,70,90));
            
        }else if(parameter.equals("ganti-password")){
            btnNavigasiGantiPassword.setBackground(Color.white);
            btnNavigasiGantiPassword.setForeground(new java.awt.Color(23,70,90));
        }
    }
    
    // method setCbTahun untuk mengambil daftar tahun yang memiliki transaksi, ditampilkan ke semua combo box tahun
    public final void setCbTahun(){
        TransaksiTable transaksiTable = new TransaksiTable();
        ArrayList<String> listTahun = transaksiTable.getTahunTransaksi();
       
        for(int i =0; i < listTahun.size(); i++){
            cbTahunTransaksi.addItem(listTahun.get(i));
            cbTahunPelanggan.addItem(listTahun.get(i));
            cbTahunObjekWisata.addItem(listTahun.get(i));
        }
    }
    
    // mengambil nilai angka dari bulan yang terpilih pada combo box
    public String getSelectedBulan(String bulan){
        switch (bulan) {
            case "Januari":
                return "01";
            case "Februari":
                return "02";
            case "Maret":
                return "03";
            case "April":
                return "04";
            case "Mei":
                return "05";
            case "Juni":
                return "06";
            case "Juli":
                return "07";
            case "Agustus":
                return "08";
            case "September":
                return "09";
            case "Oktober":
                return "10";
            case "November":
                return "11";
            case "Desember":
                return "12";
            default:
                return "";
        }
    }
    
    // mengatur combo box bulan agar sesuai dengan bulan yang ada pada saat ini
    public final void setSelectedCbBulan(String bulan, JComboBox<String> jcb){
        switch (bulan) {
            case "01":
                jcb.setSelectedItem("Januari");
                break;
            case "02":
                jcb.setSelectedItem("Februari");
                break;
            case "03":
                jcb.setSelectedItem("Maret");
                break;
            case "04":
                jcb.setSelectedItem("April"); 
                break;
            case "05":
                jcb.setSelectedItem("Mei");
                break;
            case "06":
                jcb.setSelectedItem("Juni");
                break;
            case "07":
                jcb.setSelectedItem("Juli");
                break;
            case "08":
                jcb.setSelectedItem("Agustus");
                break;
            case "09":
                jcb.setSelectedItem("September");
                break;
            case "10":
                jcb.setSelectedItem("Oktober");
                break;
            case "11":
                jcb.setSelectedItem("November");
                break;
            case "12":
                jcb.setSelectedItem("Desember");
                break;
            default:
               jcb.setSelectedItem(0);
        }
    }
    
    // mengatur beberapa isi kolom pada beberapa tabel
    public final void centerColumnTable(){
        
        // tabel transaksi
        tabelTransaksi.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelTransaksi.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabelTransaksi.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        // tabel pelanggan
        tabelDataPelanggan.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelDataPelanggan.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        
        // tabel objek wisata
        tabelObjekWisata.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        
        // tabel admin
        tabelAdmin.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabelAdmin.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
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
        panelContainer = new javax.swing.JPanel();
        panelTopBar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblHeaderName = new javax.swing.JLabel();
        panelNavigation = new javax.swing.JPanel();
        btnNavigasiDashboard = new javax.swing.JButton();
        btnNavigasiTransaksi = new javax.swing.JButton();
        btnNavigasiPelanggan = new javax.swing.JButton();
        btnNavigasiProfil = new javax.swing.JButton();
        btnNavigasiGantiPassword = new javax.swing.JButton();
        btnNavigasiKeluar = new javax.swing.JButton();
        btnNavigasiObjekWisata = new javax.swing.JButton();
        btnNavigasiAdmin = new javax.swing.JButton();
        panelContent = new javax.swing.JPanel();
        panelDashboard = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pnlDashboardTransaksi = new javax.swing.JPanel();
        lblDashboardTransaksi = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        pnlDashboardPelanggan = new javax.swing.JPanel();
        lblDashboardPelanggan = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        pnlDashboardObjekWisata = new javax.swing.JPanel();
        lblDashboardObjekWisata = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        pnlDashboardAdmin = new javax.swing.JPanel();
        lblDashboardAdmin = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblDashboardNama = new javax.swing.JLabel();
        panelDataTransaksi = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelTransaksi = new javax.swing.JTable();
        panelChartTransaksi = new javax.swing.JPanel();
        cbBulanTransaksi = new javax.swing.JComboBox<>();
        btnTransaksi = new javax.swing.JButton();
        cbTahunTransaksi = new javax.swing.JComboBox<>();
        lblDataTransaksiPer = new javax.swing.JLabel();
        lblDataTransaksiPer1 = new javax.swing.JLabel();
        lblDataTransaksiPer2 = new javax.swing.JLabel();
        lblDataTransaksiPer3 = new javax.swing.JLabel();
        lblDataTransaksiSelesai = new javax.swing.JLabel();
        lblDataTransaksiBatal = new javax.swing.JLabel();
        lblDataTransaksiTotal = new javax.swing.JLabel();
        panelDataPelanggan = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        cbBulanPelanggan = new javax.swing.JComboBox<>();
        cbTahunPelanggan = new javax.swing.JComboBox<>();
        btnPelanggan = new javax.swing.JButton();
        panelPelangganTerdaftar = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelDataPelanggan = new javax.swing.JTable();
        panelDataObjekWisata = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelObjekWisata = new javax.swing.JTable();
        panelPresentaseObjekWisata = new javax.swing.JPanel();
        cbBulanObjekWisata = new javax.swing.JComboBox<>();
        cbTahunObjekWisata = new javax.swing.JComboBox<>();
        btnObjekWisata = new javax.swing.JButton();
        panelDataAdmin = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelAdmin = new javax.swing.JTable();
        tfAdminCari = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        btnAdminTambah = new javax.swing.JButton();
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

        menuItemEdit.setText("Edit");
        popUpPromoDiskon.add(menuItemEdit);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WisataQ - Manajer");
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
        lblHeaderName.setText("Halo, nemody");
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
        btnNavigasiTransaksi.setText("  Data Transaksi");
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
        btnNavigasiPelanggan.setText("  Data Pelanggan");
        btnNavigasiPelanggan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiPelanggan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiPelangganMouseClicked(evt);
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

        btnNavigasiObjekWisata.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiObjekWisata.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiObjekWisata.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiObjekWisata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/objek-wisata.png"))); // NOI18N
        btnNavigasiObjekWisata.setText("  Data Objek Wisata");
        btnNavigasiObjekWisata.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiObjekWisata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiObjekWisata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiObjekWisataMouseClicked(evt);
            }
        });

        btnNavigasiAdmin.setBackground(new java.awt.Color(23, 70, 90));
        btnNavigasiAdmin.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        btnNavigasiAdmin.setForeground(new java.awt.Color(255, 255, 255));
        btnNavigasiAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/admin.png"))); // NOI18N
        btnNavigasiAdmin.setText("  Data Admin");
        btnNavigasiAdmin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        btnNavigasiAdmin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNavigasiAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNavigasiAdminMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelNavigationLayout = new javax.swing.GroupLayout(panelNavigation);
        panelNavigation.setLayout(panelNavigationLayout);
        panelNavigationLayout.setHorizontalGroup(
            panelNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNavigasiDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
            .addComponent(btnNavigasiTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiKeluar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiGantiPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiProfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiObjekWisata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNavigasiAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(btnNavigasiObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNavigasiAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/transaksi-45.png"))); // NOI18N
        jLabel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardTransaksi.add(jLabel13, java.awt.BorderLayout.LINE_START);

        jLabel15.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Transaksi Selesai");
        jLabel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardTransaksi.add(jLabel15, java.awt.BorderLayout.PAGE_END);

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

        jLabel16.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/pelanggan-45.png"))); // NOI18N
        jLabel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardPelanggan.add(jLabel16, java.awt.BorderLayout.LINE_START);

        jLabel17.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Pelanggan");
        jLabel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardPelanggan.add(jLabel17, java.awt.BorderLayout.PAGE_END);

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

        jLabel20.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/objek-wisata-45.png"))); // NOI18N
        jLabel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardObjekWisata.add(jLabel20, java.awt.BorderLayout.LINE_START);

        jLabel21.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Objek Wisata");
        jLabel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardObjekWisata.add(jLabel21, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(pnlDashboardObjekWisata);

        pnlDashboardAdmin.setBackground(new java.awt.Color(23, 70, 90));
        pnlDashboardAdmin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 10));
        pnlDashboardAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlDashboardAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDashboardAdminMouseClicked(evt);
            }
        });
        pnlDashboardAdmin.setLayout(new java.awt.BorderLayout(15, 5));

        lblDashboardAdmin.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        lblDashboardAdmin.setForeground(new java.awt.Color(255, 255, 255));
        lblDashboardAdmin.setText("20");
        lblDashboardAdmin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardAdmin.add(lblDashboardAdmin, java.awt.BorderLayout.CENTER);

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/admin-45.png"))); // NOI18N
        jLabel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardAdmin.add(jLabel18, java.awt.BorderLayout.LINE_START);

        jLabel19.setFont(new java.awt.Font("Poppins Medium", 0, 28)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Admin");
        jLabel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 0, true));
        pnlDashboardAdmin.add(jLabel19, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(pnlDashboardAdmin);

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
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDashboardLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDashboardNama))
                    .addComponent(jLabel27))
                .addContainerGap(459, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(190, Short.MAX_VALUE))
        );

        panelContent.add(panelDashboard, "panelDashboard");

        panelDataTransaksi.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(23, 70, 90));
        jLabel12.setText("Data Transaksi");

        tabelTransaksi.setAutoCreateRowSorter(true);
        tabelTransaksi.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        tabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tanggal", "Selesai", "Batal", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelTransaksi.setToolTipText("");
        tabelTransaksi.setRowHeight(25);
        tabelTransaksi.setSelectionBackground(new java.awt.Color(23, 70, 90));
        tabelTransaksi.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelTransaksi.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tabelTransaksi);

        panelChartTransaksi.setLayout(new java.awt.BorderLayout());

        cbBulanTransaksi.setBackground(new java.awt.Color(255, 255, 254));
        cbBulanTransaksi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbBulanTransaksi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cbBulanTransaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        cbBulanTransaksi.setKeySelectionManager(null);
        cbBulanTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBulanTransaksiActionPerformed(evt);
            }
        });

        btnTransaksi.setBackground(new java.awt.Color(255, 255, 253));
        btnTransaksi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnTransaksi.setForeground(new java.awt.Color(23, 70, 90));
        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/cetak.png"))); // NOI18N
        btnTransaksi.setText("Cetak");
        btnTransaksi.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));
        btnTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseClicked(evt);
            }
        });

        cbTahunTransaksi.setBackground(new java.awt.Color(255, 255, 254));
        cbTahunTransaksi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbTahunTransaksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        cbTahunTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTahunTransaksiActionPerformed(evt);
            }
        });

        lblDataTransaksiPer.setFont(new java.awt.Font("Poppins SemiBold", 0, 13)); // NOI18N
        lblDataTransaksiPer.setText("Data Transaksi Per (bulan) Tahun (tahun)");

        lblDataTransaksiPer1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblDataTransaksiPer1.setText("Transaksi Batal");

        lblDataTransaksiPer2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblDataTransaksiPer2.setText("Transaksi Selesai");

        lblDataTransaksiPer3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblDataTransaksiPer3.setText("Total Pemasukan");

        lblDataTransaksiSelesai.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblDataTransaksiSelesai.setForeground(new java.awt.Color(23, 70, 90));
        lblDataTransaksiSelesai.setText(": transaksi_selesai");

        lblDataTransaksiBatal.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblDataTransaksiBatal.setForeground(new java.awt.Color(23, 70, 90));
        lblDataTransaksiBatal.setText(": transaksi_batal");

        lblDataTransaksiTotal.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblDataTransaksiTotal.setForeground(new java.awt.Color(23, 70, 90));
        lblDataTransaksiTotal.setText(": transaksi_total");

        javax.swing.GroupLayout panelDataTransaksiLayout = new javax.swing.GroupLayout(panelDataTransaksi);
        panelDataTransaksi.setLayout(panelDataTransaksiLayout);
        panelDataTransaksiLayout.setHorizontalGroup(
            panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataTransaksiLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelChartTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelDataTransaksiLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbBulanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbTahunTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDataTransaksiLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDataTransaksiPer)
                            .addGroup(panelDataTransaksiLayout.createSequentialGroup()
                                .addGroup(panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDataTransaksiPer2)
                                    .addComponent(lblDataTransaksiPer1)
                                    .addComponent(lblDataTransaksiPer3))
                                .addGap(20, 20, 20)
                                .addGroup(panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDataTransaksiTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDataTransaksiBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDataTransaksiSelesai, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        panelDataTransaksiLayout.setVerticalGroup(
            panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataTransaksiLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbBulanTransaksi)
                    .addComponent(btnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTahunTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDataTransaksiLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelChartTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDataTransaksiLayout.createSequentialGroup()
                        .addComponent(lblDataTransaksiPer)
                        .addGap(18, 18, 18)
                        .addGroup(panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDataTransaksiSelesai)
                            .addComponent(lblDataTransaksiPer2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDataTransaksiPer1)
                            .addComponent(lblDataTransaksiBatal))
                        .addGap(18, 18, 18)
                        .addGroup(panelDataTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDataTransaksiPer3)
                            .addComponent(lblDataTransaksiTotal)))))
        );

        panelContent.add(panelDataTransaksi, "panelDataTransaksi");

        panelDataPelanggan.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(23, 70, 90));
        jLabel14.setText("Data Pelanggan");

        cbBulanPelanggan.setBackground(new java.awt.Color(255, 255, 254));
        cbBulanPelanggan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbBulanPelanggan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Bulan", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cbBulanPelanggan.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        cbBulanPelanggan.setKeySelectionManager(null);
        cbBulanPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBulanPelangganActionPerformed(evt);
            }
        });

        cbTahunPelanggan.setBackground(new java.awt.Color(255, 255, 254));
        cbTahunPelanggan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbTahunPelanggan.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        cbTahunPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTahunPelangganActionPerformed(evt);
            }
        });

        btnPelanggan.setBackground(new java.awt.Color(255, 255, 253));
        btnPelanggan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnPelanggan.setForeground(new java.awt.Color(23, 70, 90));
        btnPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/cetak.png"))); // NOI18N
        btnPelanggan.setText("Cetak");
        btnPelanggan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));
        btnPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPelangganActionPerformed(evt);
            }
        });

        panelPelangganTerdaftar.setBackground(new java.awt.Color(255, 255, 255));
        panelPelangganTerdaftar.setLayout(new java.awt.BorderLayout());

        tabelDataPelanggan.setAutoCreateRowSorter(true);
        tabelDataPelanggan.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        tabelDataPelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Tanggal", "Pelanggan Baru"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelDataPelanggan.setToolTipText("");
        tabelDataPelanggan.setRowHeight(25);
        tabelDataPelanggan.setSelectionBackground(new java.awt.Color(23, 70, 90));
        tabelDataPelanggan.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelDataPelanggan.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tabelDataPelanggan);

        javax.swing.GroupLayout panelDataPelangganLayout = new javax.swing.GroupLayout(panelDataPelanggan);
        panelDataPelanggan.setLayout(panelDataPelangganLayout);
        panelDataPelangganLayout.setHorizontalGroup(
            panelDataPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataPelangganLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDataPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(panelDataPelangganLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelPelangganTerdaftar, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDataPelangganLayout.createSequentialGroup()
                        .addComponent(cbBulanPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTahunPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        panelDataPelangganLayout.setVerticalGroup(
            panelDataPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataPelangganLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addGroup(panelDataPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbBulanPelanggan)
                    .addComponent(cbTahunPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDataPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPelangganTerdaftar, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        panelContent.add(panelDataPelanggan, "panelDataPelanggan");

        panelDataObjekWisata.setBackground(new java.awt.Color(255, 255, 255));

        jLabel25.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(23, 70, 90));
        jLabel25.setText("Data Objek Wisata");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 2, 30, 0));

        tabelObjekWisata.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        tabelObjekWisata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nama", "Jumlah Transaksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelObjekWisata.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabelObjekWisata.setRowHeight(25);
        tabelObjekWisata.setSelectionBackground(new java.awt.Color(23, 70, 90));
        tabelObjekWisata.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tabelObjekWisata.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(tabelObjekWisata);

        jPanel2.add(jScrollPane6);

        panelPresentaseObjekWisata.setBackground(new java.awt.Color(255, 255, 255));
        panelPresentaseObjekWisata.setLayout(new java.awt.BorderLayout());
        jPanel2.add(panelPresentaseObjekWisata);

        cbBulanObjekWisata.setBackground(new java.awt.Color(255, 255, 254));
        cbBulanObjekWisata.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbBulanObjekWisata.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Bulan", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        cbBulanObjekWisata.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        cbBulanObjekWisata.setKeySelectionManager(null);
        cbBulanObjekWisata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBulanObjekWisataActionPerformed(evt);
            }
        });

        cbTahunObjekWisata.setBackground(new java.awt.Color(255, 255, 254));
        cbTahunObjekWisata.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbTahunObjekWisata.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        cbTahunObjekWisata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTahunObjekWisataActionPerformed(evt);
            }
        });

        btnObjekWisata.setBackground(new java.awt.Color(255, 255, 253));
        btnObjekWisata.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnObjekWisata.setForeground(new java.awt.Color(23, 70, 90));
        btnObjekWisata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/cetak.png"))); // NOI18N
        btnObjekWisata.setText("Cetak");
        btnObjekWisata.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));
        btnObjekWisata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnObjekWisataMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelDataObjekWisataLayout = new javax.swing.GroupLayout(panelDataObjekWisata);
        panelDataObjekWisata.setLayout(panelDataObjekWisataLayout);
        panelDataObjekWisataLayout.setHorizontalGroup(
            panelDataObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataObjekWisataLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDataObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
                    .addGroup(panelDataObjekWisataLayout.createSequentialGroup()
                        .addGroup(panelDataObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addGroup(panelDataObjekWisataLayout.createSequentialGroup()
                                .addComponent(cbBulanObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbTahunObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDataObjekWisataLayout.setVerticalGroup(
            panelDataObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataObjekWisataLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel25)
                .addGap(18, 18, 18)
                .addGroup(panelDataObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnObjekWisata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbTahunObjekWisata)
                    .addComponent(cbBulanObjekWisata))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelContent.add(panelDataObjekWisata, "panelDataObjekWisata");

        panelDataAdmin.setBackground(new java.awt.Color(255, 255, 255));

        tabelAdmin.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        tabelAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Username", "Nama", "Tanggal Lahir", "No Telepon", "Email", "Dibuat Pada"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelAdmin.setToolTipText("");
        tabelAdmin.setRowHeight(25);
        tabelAdmin.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelAdmin.getTableHeader().setReorderingAllowed(false);
        tabelAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelAdminMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tabelAdmin);

        tfAdminCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfAdminCariKeyPressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(28, 28, 39));
        jLabel22.setText("Cari :");

        jLabel23.setFont(new java.awt.Font("Poppins SemiBold", 0, 28)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(23, 70, 90));
        jLabel23.setText("Admin");

        btnAdminTambah.setBackground(new java.awt.Color(23, 70, 90));
        btnAdminTambah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnAdminTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnAdminTambah.setText("+ Tambah");
        btnAdminTambah.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAdminTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAdminTambahMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelDataAdminLayout = new javax.swing.GroupLayout(panelDataAdmin);
        panelDataAdmin.setLayout(panelDataAdminLayout);
        panelDataAdminLayout.setHorizontalGroup(
            panelDataAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataAdminLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDataAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
                    .addGroup(panelDataAdminLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdminTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDataAdminLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfAdminCari, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDataAdminLayout.setVerticalGroup(
            panelDataAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDataAdminLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelDataAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(btnAdminTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(panelDataAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAdminCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelContent.add(panelDataAdmin, "panelDataAdmin");

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
                                .addGap(207, 572, Short.MAX_VALUE)))
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
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
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
                        .addGap(0, 475, Short.MAX_VALUE))))
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
                .addContainerGap(329, Short.MAX_VALUE))
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

    private void btnNavigasiTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiTransaksiMouseClicked
        pilihHalaman("data-transaksi");
    }//GEN-LAST:event_btnNavigasiTransaksiMouseClicked

    private void btnNavigasiPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiPelangganMouseClicked
        pilihHalaman("data-pelanggan");
    }//GEN-LAST:event_btnNavigasiPelangganMouseClicked

    private void btnNavigasiKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiKeluarMouseClicked
        int konfirmasi = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin keluar dari aplikasi ?", "Keluar", JOptionPane.YES_NO_OPTION);
        if(konfirmasi == 0){
            System.exit(0);
        }
    }//GEN-LAST:event_btnNavigasiKeluarMouseClicked

    private void btnNavigasiGantiPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiGantiPasswordMouseClicked
        pilihHalaman("ganti-password");
    }//GEN-LAST:event_btnNavigasiGantiPasswordMouseClicked

    private void btnNavigasiProfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiProfilMouseClicked
        pilihHalaman("profil");
    }//GEN-LAST:event_btnNavigasiProfilMouseClicked

    private void btnNavigasiDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiDashboardMouseClicked
        pilihHalaman("dashboard");
    }//GEN-LAST:event_btnNavigasiDashboardMouseClicked

    private void btnNavigasiObjekWisataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiObjekWisataMouseClicked
        pilihHalaman("data-objek-wisata");
    }//GEN-LAST:event_btnNavigasiObjekWisataMouseClicked

    private void btnObjekWisataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnObjekWisataMouseClicked
        // cetak laporan untuk objek wisata
        if(listObjekWisata.get(0).getJmlTransaksi() != 0 ){ // mengecek apakah data tersedia
            try{
                
                HashMap<String, Object> parameter = new HashMap<>(); // inisialisasi parameter untuk laporan
                
                // mengisi parameter laporan
                parameter.put("PARAMETER_BULAN_TAHUN", "PER " + 
                        cbBulanObjekWisata.getSelectedItem().toString().toUpperCase() + " " + cbTahunObjekWisata.getSelectedItem().toString());
                parameter.put("PARAMETER_NAMA_MANAJER", profil.getNamaAdmin());
                
                // inisialisasi data untuk laporan menggunakan list objek wisata
                JRBeanArrayDataSource jb = new JRBeanArrayDataSource(listObjekWisata.toArray());

                // mengisi laporan dengan data dan parameter
                JasperPrint jp = JasperFillManager.fillReport("src/report/laporan_objek_wisata_transaksi.jasper",parameter, jb);

                // menampilkan laporan
                JasperViewer viewer = new JasperViewer(jp, false);
                viewer.setVisible(true);

            }catch(JRException e){
                System.err.println("ERROR cetakLaporanObjekWisataTransaksi :" + e);
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Data tidak tersedia", "Data Objek Wisata", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnObjekWisataMouseClicked

    private void cbBulanObjekWisataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBulanObjekWisataActionPerformed
       getObjekWisataData(getSelectedBulan(cbBulanObjekWisata.getSelectedItem().toString()), cbTahunObjekWisata.getSelectedItem().toString());
    }//GEN-LAST:event_cbBulanObjekWisataActionPerformed

    private void cbBulanPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBulanPelangganActionPerformed
        String bulan = cbBulanPelanggan.getSelectedItem().toString();
        getPelangganData(getSelectedBulan(bulan), cbTahunPelanggan.getSelectedItem().toString());
    }//GEN-LAST:event_cbBulanPelangganActionPerformed

    private void btnProfilEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfilEditMouseClicked
        AdminFormFrame adminFormFrame = new AdminFormFrame(this, profil, true);
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

        // validasi untuk form 
        if(passLama.equals("") || passBaru.equals("") || passKonBaru.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Form belum lengkap", "Ganti Password", JOptionPane.WARNING_MESSAGE);
        }else if(!passBaru.equals(passKonBaru)){ // validasii jika pass baru tidak sama dengan konfirmasi pass
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
                
                // jika pada pesan error mengandung password maka akan menampilkan pesan sesuai yang diberikan oleh tabel
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

    private void cbBulanTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBulanTransaksiActionPerformed
        String bulan = cbBulanTransaksi.getSelectedItem().toString();
        getTransaksiData(getSelectedBulan(bulan), cbTahunTransaksi.getSelectedItem().toString());
    }//GEN-LAST:event_cbBulanTransaksiActionPerformed

    private void pnlDashboardTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardTransaksiMouseClicked
        pilihHalaman("data-transaksi");
    }//GEN-LAST:event_pnlDashboardTransaksiMouseClicked

    private void pnlDashboardPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardPelangganMouseClicked
        pilihHalaman("data-pelanggan");
    }//GEN-LAST:event_pnlDashboardPelangganMouseClicked

    private void pnlDashboardAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardAdminMouseClicked
        pilihHalaman("data-admin");
    }//GEN-LAST:event_pnlDashboardAdminMouseClicked

    private void btnNavigasiAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNavigasiAdminMouseClicked
        pilihHalaman("data-admin");
    }//GEN-LAST:event_btnNavigasiAdminMouseClicked

    private void pnlDashboardObjekWisataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardObjekWisataMouseClicked
        pilihHalaman("data-objek-wisata");
    }//GEN-LAST:event_pnlDashboardObjekWisataMouseClicked

    private void cbTahunTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTahunTransaksiActionPerformed
        String bulan = cbBulanTransaksi.getSelectedItem().toString();
        getTransaksiData(getSelectedBulan(bulan), cbTahunTransaksi.getSelectedItem().toString());
    }//GEN-LAST:event_cbTahunTransaksiActionPerformed

    private void btnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseClicked
        
        // cetak transaksi
        if(!listDataTransaksi.isEmpty()){ // memeriksa apakah transaksi ssedang kosong atau tidak
            double total = 0; // inisialisai hasil total keseluruhan transaksi
            for(DataTransaksi dt:listDataTransaksi){
                total += dt.getTotal(); // mengisi variabel total
            }
            try{
                
                // inisialisasi parameter laporan
                HashMap<String, Object> parameter = new HashMap<>();
                
                // mengisi parameter yang dibutuhkan oleh laporan
                parameter.put("PARAMETER_BULAN_TAHUN", "PER " + 
                        cbBulanTransaksi.getSelectedItem().toString().toUpperCase() + " " + cbTahunTransaksi.getSelectedItem().toString());
                parameter.put("PARAMETER_NAMA_MANAJER", profil.getNamaAdmin());
                parameter.put("PARAMETER_TOTAL_KESELURUHAN", KonversiRupiah.konversi(total));
                
                // inisialisasi data laporan dengan array list
                JRBeanArrayDataSource jb = new JRBeanArrayDataSource(listDataTransaksi.toArray());

                // mengisi laporan dengan data dan parameter
                JasperPrint jp = JasperFillManager.fillReport("src/report/laporan_transaksi.jasper",parameter, jb);

                // menampilkan laporan
                JasperViewer viewer = new JasperViewer(jp, false);
                viewer.setVisible(true);

            }catch(JRException e){
                System.err.println("ERROR cetakLaporanTransaksi :" + e);
            }
        }else{
            // jika list transaksi tidak ada atau kosong
            JOptionPane.showMessageDialog(rootPane, "Data tidak tersedia", "Data Transaksi", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnTransaksiMouseClicked

    private void cbTahunPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTahunPelangganActionPerformed
        String bulan = cbBulanPelanggan.getSelectedItem().toString();
        getPelangganData(getSelectedBulan(bulan), cbTahunPelanggan.getSelectedItem().toString());
    }//GEN-LAST:event_cbTahunPelangganActionPerformed

    private void btnPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPelangganActionPerformed
        
        // cetak laporan pelanggan
        if(!listDataPelanggan.isEmpty()){ // memeriksa apakah list data pelanggan tidak kosong
            try{
                
                // inisialisasi parameter laporan
                HashMap<String, Object> parameter = new HashMap<>();
                
                // mengisi parameter laporan
                parameter.put("PARAMETER_BULAN_TAHUN", "PER " + 
                        cbBulanTransaksi.getSelectedItem().toString().toUpperCase() + " " + cbTahunTransaksi.getSelectedItem().toString());
                parameter.put("PARAMETER_NAMA_MANAJER", profil.getNamaAdmin());
          
                // inisialisasi data laporan dari list data pelanggan
                JRBeanArrayDataSource jb = new JRBeanArrayDataSource(listDataPelanggan.toArray());

                // mengisi laporan dengan data dan parameter
                JasperPrint jp = JasperFillManager.fillReport("src/report/laporan_pelanggan.jasper",parameter, jb);

                // menampilkan laporan
                JasperViewer viewer = new JasperViewer(jp, false);
                viewer.setVisible(true);

            }catch(JRException e){
                System.err.println("ERROR cetakLaporanPelanggan :" + e);
            }
        }else{
            // jika data pelanggan tidak ada
            JOptionPane.showMessageDialog(rootPane, "Data tidak tersedia", "Data Pelanggan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnPelangganActionPerformed

    private void cbTahunObjekWisataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTahunObjekWisataActionPerformed
        getObjekWisataData(getSelectedBulan(cbBulanObjekWisata.getSelectedItem().toString()), cbTahunObjekWisata.getSelectedItem().toString());
    }//GEN-LAST:event_cbTahunObjekWisataActionPerformed

    private void tabelAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelAdminMouseClicked
        int row = tabelAdmin.getSelectedRow();
        new AdminFormFrame(this, listAdmin.get(row), false).setVisible(true);
    }//GEN-LAST:event_tabelAdminMouseClicked

    private void tfAdminCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfAdminCariKeyPressed
            getAdminList(tfAdminCari.getText());
    }//GEN-LAST:event_tfAdminCariKeyPressed

    private void btnAdminTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdminTambahMouseClicked
        setEnabled(false);
        AdminFormFrame adminFormFrame = new AdminFormFrame(this);
        adminFormFrame.setVisible(true);
    }//GEN-LAST:event_btnAdminTambahMouseClicked


//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Metal".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ManajerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ManajerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ManajerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ManajerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ManajerFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdminTambah;
    private javax.swing.JButton btnGantiPassword;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnNavigasiAdmin;
    private javax.swing.JButton btnNavigasiDashboard;
    private javax.swing.JButton btnNavigasiGantiPassword;
    private javax.swing.JButton btnNavigasiKeluar;
    private javax.swing.JButton btnNavigasiObjekWisata;
    private javax.swing.JButton btnNavigasiPelanggan;
    private javax.swing.JButton btnNavigasiProfil;
    private javax.swing.JButton btnNavigasiTransaksi;
    private javax.swing.JButton btnObjekWisata;
    private javax.swing.JButton btnPelanggan;
    private javax.swing.JButton btnProfilEdit;
    private javax.swing.JButton btnTransaksi;
    private javax.swing.JComboBox<String> cbBulanObjekWisata;
    private javax.swing.JComboBox<String> cbBulanPelanggan;
    private javax.swing.JComboBox<String> cbBulanTransaksi;
    private javax.swing.JComboBox<String> cbTahunObjekWisata;
    private javax.swing.JComboBox<String> cbTahunPelanggan;
    private javax.swing.JComboBox<String> cbTahunTransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblDashboardAdmin;
    private javax.swing.JLabel lblDashboardNama;
    private javax.swing.JLabel lblDashboardObjekWisata;
    private javax.swing.JLabel lblDashboardPelanggan;
    private javax.swing.JLabel lblDashboardTransaksi;
    private javax.swing.JLabel lblDataTransaksiBatal;
    private javax.swing.JLabel lblDataTransaksiPer;
    private javax.swing.JLabel lblDataTransaksiPer1;
    private javax.swing.JLabel lblDataTransaksiPer2;
    private javax.swing.JLabel lblDataTransaksiPer3;
    private javax.swing.JLabel lblDataTransaksiSelesai;
    private javax.swing.JLabel lblDataTransaksiTotal;
    private javax.swing.JLabel lblHeaderName;
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
    private javax.swing.JPanel panelChartTransaksi;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JPanel panelContent;
    private javax.swing.JPanel panelDashboard;
    private javax.swing.JPanel panelDataAdmin;
    private javax.swing.JPanel panelDataObjekWisata;
    private javax.swing.JPanel panelDataPelanggan;
    private javax.swing.JPanel panelDataTransaksi;
    private javax.swing.JPanel panelGantiPassword;
    private javax.swing.JPanel panelNavigation;
    private javax.swing.JPanel panelPelangganTerdaftar;
    private javax.swing.JPanel panelPresentaseObjekWisata;
    private javax.swing.JPanel panelProfil;
    private javax.swing.JPanel panelTopBar;
    private javax.swing.JPanel pnlDashboardAdmin;
    private javax.swing.JPanel pnlDashboardObjekWisata;
    private javax.swing.JPanel pnlDashboardPelanggan;
    private javax.swing.JPanel pnlDashboardTransaksi;
    private javax.swing.JPopupMenu popUpPromoDiskon;
    private javax.swing.JTextArea taProfilAlamat;
    private javax.swing.JTable tabelAdmin;
    private javax.swing.JTable tabelDataPelanggan;
    private javax.swing.JTable tabelObjekWisata;
    private javax.swing.JTable tabelTransaksi;
    private javax.swing.JTextField tfAdminCari;
    private javax.swing.JPasswordField tfKonfirmasiPasswordBaru;
    private javax.swing.JPasswordField tfPasswordBaru;
    private javax.swing.JPasswordField tfPasswordLama;
    // End of variables declaration//GEN-END:variables
}
