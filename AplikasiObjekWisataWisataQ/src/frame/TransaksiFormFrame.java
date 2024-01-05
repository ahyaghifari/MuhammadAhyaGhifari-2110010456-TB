/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import model.KeyValueInt;
import model.KeyValueString;
import model.ObjekWisata;
import model.Pelanggan;
import model.Pembayaran;
import table.ObjekWisataTable;
import table.PelangganTable;
import table.PembayaranTable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Admin;
import model.Struk;
import model.Transaksi;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.view.JasperViewer;
import service.KonversiRupiah;
import table.PromoDiskonTable;
import table.TransaksiTable;

/**
 *
 * @author Ahya Ghifari
 */
public final class TransaksiFormFrame extends javax.swing.JFrame {

    private AdminFrame adminFrame;
    
    private String status = "TAMBAH";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private TransaksiTable transaksiTable;
    
    private PelangganTable pelangganTable;
    private ArrayList<Pelanggan> listPelanggan;
    
    private ObjekWisataTable objekWisataTable;
    private ArrayList<ObjekWisata> listObjekWisata;
    
    private PembayaranTable pembayaranTable;
    private ArrayList<Pembayaran> listPembayaran;
    
    private PromoDiskonTable promoDiskonTable;
    
    private double hargaTiket;
    private double totalBiaya;
    int jumlahTiket;
    private int presentaseDiskon;
    private double totalPromoDiskon;
    private double totalKeseluruhan;
    private String dibuatPada;
    
    private int jumlahTiketYangDiambil;
    
    /**
     * Creates new form TransaksiFormFrame
     * @param adminFrame
     */

    public TransaksiFormFrame(AdminFrame adminFrame) {
        initComponents();

        status = "TAMBAH";
        lblTitle.setText("Tambah Transaksi");
        this.adminFrame = adminFrame;
        dibuatPada = "";
        
        btnCetak.setVisible(false);
        btnEdit.setVisible(false);
        lblDibuatPada.setVisible(false);
        lblDibuatPada1.setVisible(false);
        lblTerakhirDiubah.setVisible(false);
        lblTerakhirDiubah2.setVisible(false);
        cbStatus.setVisible(false);
        
        transaksiTable = new TransaksiTable();
        pelangganTable = new PelangganTable();
        objekWisataTable = new ObjekWisataTable();
        pembayaranTable = new PembayaranTable();
        promoDiskonTable = new PromoDiskonTable();
        
        String kodeBaru = transaksiTable.generateKodeTransaksi();
        tfKode.setText(kodeBaru); 
        
        cariPelanggan("");
        setModelCb(getCbPelanggan(), cbPelanggan);
        
        listObjekWisata = objekWisataTable.tampilObjekWisata("");
        setModelCb(getCbObjekWisata(), cbObjekWisata);

        setCbJumlahTiketTersedia();
       
        listPembayaran = pembayaranTable.tampilPembayaran(true);
        setModelCb(getCbPembayaran(), cbPembayaran);
        
        hargaTiket = listObjekWisata.get(0).getHargaTiket();
        
        jumlahTiket = 1;
        
        getPromoDiskon(listPelanggan.get(0).getLevel());
        hitung();
    }
    
    public TransaksiFormFrame(AdminFrame adminFrame, Transaksi ts, String mode) {
        initComponents();
       
        setMode(mode);
        status = "EDIT";
        lblTitle.setText("Transaksi");
        this.adminFrame = adminFrame;
      
        transaksiTable   = new TransaksiTable();
        pelangganTable   = new PelangganTable();
        objekWisataTable = new ObjekWisataTable();
        pembayaranTable  = new PembayaranTable();
        promoDiskonTable = new PromoDiskonTable();
        
        tfKode.setText(ts.getKodeTransaksi()); 
        lblDibuatPada.setText(": " + ts.getCreatedAt());
        lblTerakhirDiubah.setText(": " + ts.getUpdatedAt());
        btnSimpan.setText("Simpan");
        dibuatPada = ts.getCreatedAt();
        cbStatus.setSelectedItem(ts.getStatusTransaksi());
        
        try {
            dpTanggalWisata.setSelectedDate(dateFormat.parse(ts.getTanggalWisata()));
        } catch (ParseException ex) {
            Logger.getLogger(TransaksiFormFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cariPelanggan(ts.getPelanggan().getNamaPelanggan());
        tfCariPelanggan.setText(ts.getPelanggan().getNamaPelanggan());
        setModelCb(getCbPelanggan(), cbPelanggan);
        setCbSelectedPelanggan(ts.getPelanggan().getNamaPelanggan() + "("+ts.getPelanggan().getNoTelepon()+")");
        
        listObjekWisata = objekWisataTable.tampilObjekWisata("");
        setModelCb(getCbObjekWisata(), cbObjekWisata);
        setCbSelectedObjekWisata(ts.getObjekWisata().getNamaObjekWisata() + "(" +  ts.getObjekWisata().getLokasi() +")");
        
        jumlahTiketYangDiambil = ts.getJumlahTiket();
        setCbJumlahTiketTersedia();
        cbJumlahTiketTersedia.setSelectedItem(String.valueOf(ts.getJumlahTiket()));
        
        listPembayaran = pembayaranTable.tampilPembayaran(true);
        setModelCb(getCbPembayaran(), cbPembayaran);
        setCbSelectedPembayaran(ts.getPembayaran().getNamaPembayaran());
        
        hargaTiket = listObjekWisata.get(cbObjekWisata.getSelectedIndex()).getHargaTiket();
        
        jumlahTiket = ts.getJumlahTiket();
        
        getPromoDiskon(listPelanggan.get(0).getLevel());
        hitung();
    }
    
    private void cariPelanggan(String keyword){
        try{
            listPelanggan = pelangganTable.tampilPelanggan(keyword, "25");    
            getPromoDiskon(listPelanggan.get(0).getLevel());
            hitung();
        }catch(Exception ex){
            System.err.println("ERROR cariPelanggan : " + ex);
        }
        
    }
    
    public void refreshPelanggan(){
        cariPelanggan("");
        setModelCb(getCbPelanggan(), cbPelanggan);
        
        getPromoDiskon(listPelanggan.get(0).getLevel());
        hitung();
    }
    
    private Vector getCbPelanggan(){
        Vector v = new Vector();
        
        try{
            ArrayList<Pelanggan> data = listPelanggan;

            for(int i = 0; i < data.size(); i++){
                v.addElement(new KeyValueInt(data.get(i).getIdPelanggan(), data.get(i).getNamaPelanggan() + "(" + data.get(i).getNoTelepon() + ")"));
            }
        }catch(Exception ex){
            System.err.println("ERROR getCbPelanggan : " + ex);
        }
        return v;
    }
    
    private Vector getCbObjekWisata(){
        Vector v = new Vector();
        
        try{
            ArrayList<ObjekWisata> data = listObjekWisata;

            for(int i = 0; i < data.size(); i++){
                v.addElement(new KeyValueString(data.get(i).getIdObjekWisata(), data.get(i).getNamaObjekWisata() + "(" + data.get(i).getLokasi()+ ")"));
            }
        }catch(Exception ex){
            System.err.println("ERROR getCbObjekWisata : " + ex);
        }
        return v;
    }
    
    private Vector getCbPembayaran(){
        Vector v = new Vector();
        
        try{
            ArrayList<Pembayaran> data = listPembayaran;

            v.addElement(new KeyValueInt(0, "Pilih Pembayaran"));
            for(int i = 0; i < data.size(); i++){
                v.addElement(new KeyValueInt(data.get(i).getIdPembayaran(), data.get(i).getNamaPembayaran()));
            }
        }catch(Exception ex){
            System.err.println("ERROR getCbPembayaran : " + ex);
        }
        return v;
    }
    
    private void setModelCb(Vector v, JComboBox<String> jcb){
        DefaultComboBoxModel model;
        model = new DefaultComboBoxModel(v);
        jcb.setModel(model);
    }
    
    private void setCbSelectedPelanggan(String pelanggan){
        KeyValueInt item = new KeyValueInt();
        
        for(int i = 0; i < cbPelanggan.getItemCount(); i++){
           cbPelanggan.setSelectedIndex(i);
           item.setValue(((KeyValueInt)cbPelanggan.getSelectedItem()).getValue());
           if(item.getValue().equals(pelanggan)){
               cbPelanggan.setSelectedIndex(i);
               break;
           }
        }   
    }
    
    private void setCbSelectedObjekWisata(String objekWisata){
        KeyValueString item = new KeyValueString();
        
        for(int i = 0; i < cbObjekWisata.getItemCount(); i++){
           cbObjekWisata.setSelectedIndex(i);
           item.setValue(((KeyValueString)cbObjekWisata.getSelectedItem()).getValue());
           if(item.getValue().equals(objekWisata)){
               cbObjekWisata.setSelectedIndex(i);
               break;
           }
        }
        
    }
    
    private void setCbSelectedPembayaran(String pembayaran){
        KeyValueInt item = new KeyValueInt();
        
        for(int i = 0; i < cbPembayaran.getItemCount(); i++){
           cbPembayaran.setSelectedIndex(i);
           item.setValue(((KeyValueInt)cbPembayaran.getSelectedItem()).getValue());
           if(item.getValue().equals(pembayaran)){
               cbPembayaran.setSelectedIndex(i);
               break;
           }
        }   
    }
 
     private void setCbJumlahTiketTersedia(){
         
        cbJumlahTiketTersedia.removeAllItems();
         
        int selectedObjekWisata = cbObjekWisata.getSelectedIndex();
        int tiketTersedia = transaksiTable.tiketTersedia(
                listObjekWisata.get(selectedObjekWisata).getIdObjekWisata(),
               listObjekWisata.get(selectedObjekWisata).getKetersediaanTiket(),
                       tfTanggalWisata.getText());
        
        if(status.equals("EDIT")){
            if(tfTanggalWisata.getText().equals(dibuatPada.substring(0, 10))){
                tiketTersedia += jumlahTiketYangDiambil;
            }
        }
        
        cbJumlahTiketTersedia.removeAllItems();
        
         for (int i = 1; i <= tiketTersedia; i++) {
             cbJumlahTiketTersedia.addItem(String.valueOf(i));
         }
         
         
    }
 
    private void getPromoDiskon(String level){
        if(dibuatPada.equals("")){
            presentaseDiskon = promoDiskonTable.cariPromoDiskon(level, "");
        }else{
            presentaseDiskon = promoDiskonTable.cariPromoDiskon(level, dibuatPada.substring(0, 10));
        }
        
    }
    
    private void hitung(){
        totalBiaya = hargaTiket * jumlahTiket;
        totalPromoDiskon = (totalBiaya * presentaseDiskon) / 100;
        totalKeseluruhan =  totalBiaya - totalPromoDiskon;
        
        lblTotalBiaya.setText("Total Biaya : " + KonversiRupiah.konversi(totalBiaya));
        lblPromoDiskon.setText("Promo Diskon : " + KonversiRupiah.konversi(totalPromoDiskon) + " (" + presentaseDiskon + "%)");
        lblTotalKeseluruhan.setText("Total Biaya : " + KonversiRupiah.konversi(totalKeseluruhan));
        
    }
    
    private void cetakStruk(Struk struk){
        ArrayList<Struk> strukList =new ArrayList();
        
        strukList.add(struk);
        
        try{
            
            String kodeQr = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + struk.getKodeTransaksi();

            HashMap<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("PARAMETER_QR_CODE", kodeQr);
            
            JRBeanArrayDataSource jb = new JRBeanArrayDataSource(strukList.toArray());
            
            JasperPrint jp = JasperFillManager.fillReport("src/service/struk.jasper",parameter, jb);
            
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setVisible(true);
            
        }catch(JRException e){
            System.err.println("ERROR cetakStruk :" + e);
        }
        
    }
    
    private void setMode(String mode){
        if(mode.equals("read")){
           lblTitle.setText("Transaksi");
           btnCetak.setVisible(true);
           btnEdit.setEnabled(true);
           btnBatalTutup.setText("Tutup");
           cbPelanggan.setEnabled(false);
           tfCariPelanggan.setEditable(false);
           btnTambahPelanggan.setVisible(false);
           cbObjekWisata.setEnabled(false);
           tfTanggalWisata.setEnabled(false);
           dpTanggalWisata.setEnabled(false);
           cbJumlahTiketTersedia.setEnabled(false);
           cbPembayaran.setEnabled(false);
           cbStatus.setEnabled(false);
           btnSimpan.setVisible(false);
           
        }else{
           lblTitle.setText("Edit Transaksi");
           btnCetak.setVisible(false);
           btnEdit.setEnabled(false);
           btnBatalTutup.setText("Batal");
           cbPelanggan.setEnabled(true);
           tfCariPelanggan.setEditable(true);
           btnTambahPelanggan.setVisible(true);
           cbObjekWisata.setEnabled(true);
           tfTanggalWisata.setEnabled(true);
           dpTanggalWisata.setEnabled(true);
           cbJumlahTiketTersedia.setEnabled(true);
           cbPembayaran.setEnabled(true);
           cbStatus.setEnabled(true);
           btnSimpan.setVisible(true);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dpTanggalWisata = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblTitle1 = new javax.swing.JLabel();
        lblTitle2 = new javax.swing.JLabel();
        tfKode = new javax.swing.JTextField();
        lblTitle3 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnBatalTutup = new javax.swing.JButton();
        cbPelanggan = new javax.swing.JComboBox<>();
        tfCariPelanggan = new javax.swing.JTextField();
        btnTambahPelanggan = new javax.swing.JButton();
        lblTitle4 = new javax.swing.JLabel();
        cbObjekWisata = new javax.swing.JComboBox<>();
        lblTitle5 = new javax.swing.JLabel();
        lblTitle6 = new javax.swing.JLabel();
        cbPembayaran = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        lblTotalBiaya = new javax.swing.JLabel();
        lblPromoDiskon = new javax.swing.JLabel();
        lblTotalKeseluruhan = new javax.swing.JLabel();
        btnLihatObjekWisata = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        lblDibuatPada = new javax.swing.JLabel();
        lblDibuatPada1 = new javax.swing.JLabel();
        lblTerakhirDiubah2 = new javax.swing.JLabel();
        lblTerakhirDiubah = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        tfTanggalWisata = new javax.swing.JTextField();
        cbJumlahTiketTersedia = new javax.swing.JComboBox<>();

        dpTanggalWisata.setForeground(new java.awt.Color(23, 70, 90));
        dpTanggalWisata.setDateFormat("yyyy-MM-dd");
        dpTanggalWisata.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        dpTanggalWisata.setTextRefernce(tfTanggalWisata);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transaksi");
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));

        lblTitle.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(23, 70, 90));
        lblTitle.setText("Tambah Transaksi");

        lblTitle1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle1.setText("Kode : ");

        lblTitle2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle2.setText("Tanggal Wisata : ");

        tfKode.setEditable(false);
        tfKode.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle3.setText("Pelanggan : ");

        btnSimpan.setBackground(new java.awt.Color(23, 70, 90));
        btnSimpan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Buat Transaksi");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatalTutup.setBackground(new java.awt.Color(250, 255, 255));
        btnBatalTutup.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnBatalTutup.setText("Tutup");
        btnBatalTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalTutupActionPerformed(evt);
            }
        });

        cbPelanggan.setBackground(new java.awt.Color(255, 255, 250));
        cbPelanggan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbPelanggan.setMaximumRowCount(25);
        cbPelanggan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3" }));
        cbPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPelangganActionPerformed(evt);
            }
        });

        tfCariPelanggan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tfCariPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCariPelangganActionPerformed(evt);
            }
        });

        btnTambahPelanggan.setBackground(new java.awt.Color(23, 70, 90));
        btnTambahPelanggan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnTambahPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/tambah-2.png"))); // NOI18N
        btnTambahPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahPelangganMouseClicked(evt);
            }
        });

        lblTitle4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle4.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle4.setText("Objek Wisata : ");

        cbObjekWisata.setBackground(new java.awt.Color(255, 255, 250));
        cbObjekWisata.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbObjekWisata.setMaximumRowCount(100);
        cbObjekWisata.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3" }));
        cbObjekWisata.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbObjekWisataItemStateChanged(evt);
            }
        });

        lblTitle5.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle5.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle5.setText("Jumlah Tiket : ");

        lblTitle6.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle6.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle6.setText("Pembayaran : ");

        cbPembayaran.setBackground(new java.awt.Color(255, 255, 250));
        cbPembayaran.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbPembayaran.setMaximumRowCount(100);
        cbPembayaran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3" }));

        lblTotalBiaya.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTotalBiaya.setForeground(new java.awt.Color(102, 102, 102));
        lblTotalBiaya.setText("Total Biaya : ");

        lblPromoDiskon.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblPromoDiskon.setForeground(new java.awt.Color(102, 102, 102));
        lblPromoDiskon.setText("Promo Diskon : ");

        lblTotalKeseluruhan.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        lblTotalKeseluruhan.setForeground(new java.awt.Color(28, 28, 39));
        lblTotalKeseluruhan.setText("Total : ");
        lblTotalKeseluruhan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        btnLihatObjekWisata.setBackground(new java.awt.Color(23, 70, 90));
        btnLihatObjekWisata.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnLihatObjekWisata.setForeground(new java.awt.Color(255, 255, 255));
        btnLihatObjekWisata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/lihat.png"))); // NOI18N
        btnLihatObjekWisata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLihatObjekWisataMouseClicked(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(23, 70, 90));
        btnEdit.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });

        btnCetak.setBackground(new java.awt.Color(255, 255, 253));
        btnCetak.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnCetak.setForeground(new java.awt.Color(23, 70, 90));
        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/cetak.png"))); // NOI18N
        btnCetak.setText("Cetak");
        btnCetak.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));
        btnCetak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCetakMouseClicked(evt);
            }
        });

        lblDibuatPada.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        lblDibuatPada.setForeground(new java.awt.Color(102, 102, 102));
        lblDibuatPada.setText(": dibuat_pada");

        lblDibuatPada1.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        lblDibuatPada1.setForeground(new java.awt.Color(102, 102, 102));
        lblDibuatPada1.setText("Dibuat Pada");

        lblTerakhirDiubah2.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        lblTerakhirDiubah2.setForeground(new java.awt.Color(102, 102, 102));
        lblTerakhirDiubah2.setText("Terakhir Diubah ");

        lblTerakhirDiubah.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        lblTerakhirDiubah.setForeground(new java.awt.Color(102, 102, 102));
        lblTerakhirDiubah.setText(": terakhir_diubah");

        cbStatus.setBackground(new java.awt.Color(255, 255, 250));
        cbStatus.setFont(new java.awt.Font("Poppins", 0, 11)); // NOI18N
        cbStatus.setMaximumRowCount(100);
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selesai", "Batal" }));
        cbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbStatusItemStateChanged(evt);
            }
        });

        tfTanggalWisata.setEditable(false);
        tfTanggalWisata.setBackground(new java.awt.Color(255, 255, 255));
        tfTanggalWisata.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tfTanggalWisata.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfTanggalWisataFocusGained(evt);
            }
        });

        cbJumlahTiketTersedia.setBackground(new java.awt.Color(255, 255, 250));
        cbJumlahTiketTersedia.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbJumlahTiketTersedia.setMaximumRowCount(100);
        cbJumlahTiketTersedia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbJumlahTiketTersediaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTitle1)
                                    .addComponent(lblTitle3))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfKode, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfCariPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnTambahPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(lblTotalBiaya)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblTotalKeseluruhan))
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblPromoDiskon)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblTitle4)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnLihatObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblTitle6)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblTitle2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfTanggalWisata, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(88, 88, 88)
                                        .addComponent(lblTitle5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbJumlahTiketTersedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBatalTutup))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblTerakhirDiubah2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblDibuatPada1)
                                .addGap(31, 31, 31)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTerakhirDiubah)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblDibuatPada)
                                .addGap(108, 108, 108)
                                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(btnBatalTutup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle1)
                    .addComponent(tfKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle3)
                    .addComponent(cbPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCariPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTitle4)
                        .addComponent(cbObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnLihatObjekWisata, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle2)
                    .addComponent(lblTitle5)
                    .addComponent(tfTanggalWisata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbJumlahTiketTersedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle6)
                    .addComponent(cbPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotalBiaya)
                            .addComponent(lblTotalKeseluruhan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPromoDiskon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTerakhirDiubah2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDibuatPada)
                            .addComponent(lblDibuatPada1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTerakhirDiubah)))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
       
       if(jumlahTiket == 0 || cbPembayaran.getSelectedIndex() == 0){
           JOptionPane.showMessageDialog(rootPane, "Lengkapi data transaksi", "Transkasi", JOptionPane.WARNING_MESSAGE);
        }else{
            Transaksi transaksi = new Transaksi();
        
            transaksi.setKodeTransaksi(tfKode.getText());
            transaksi.setTanggalWisata(tfTanggalWisata.getText());
            transaksi.setJumlahTiket(Integer.parseInt(cbJumlahTiketTersedia.getSelectedItem().toString()));
            transaksi.setTotal(totalKeseluruhan);
            transaksi.setStatusTransaksi("Selesai");

            Pelanggan pelanggan = new Pelanggan();
            pelanggan.setIdPelanggan(((KeyValueInt)cbPelanggan.getSelectedItem()).getKey());
            transaksi.setPelanggan(pelanggan);

            ObjekWisata objekWisata = new ObjekWisata();
            objekWisata.setIdObjekWisata(((KeyValueString)cbObjekWisata.getSelectedItem()).getKey());
            transaksi.setObjekWisata(objekWisata);

            Pembayaran pembayaran = new Pembayaran();
            pembayaran.setIdPembayaran(((KeyValueInt)cbPembayaran.getSelectedItem()).getKey());
            pembayaran.setNamaPembayaran(cbPembayaran.getSelectedItem().toString());
            transaksi.setPembayaran(pembayaran);

             Admin admin = new Admin();
             admin.setIdAdmin(adminFrame.profil.getIdAdmin());
             transaksi.setAdmin(admin);

             if(status.equals("TAMBAH")){
                boolean insert = transaksiTable.tambahTransaksi(transaksi);

                 if(insert == true){
                     JOptionPane.showMessageDialog(rootPane, "Transaksi Berhasil dibuat", "Transaksi", JOptionPane.INFORMATION_MESSAGE);
                        
                        Struk struk = new Struk();
                        struk.setKodeTransaksi(transaksi.getKodeTransaksi());
                        struk.setPelanggan(((KeyValueInt)cbPelanggan.getSelectedItem()).getValue());
                        struk.setNamaObjekWisata(((KeyValueString)cbObjekWisata.getSelectedItem()).getValue());
                        struk.setJumlahTiket(cbJumlahTiketTersedia.getSelectedItem().toString());
                        struk.setTanggalWisata(tfTanggalWisata.getText());
                        struk.setTotalObjekWisata(KonversiRupiah.konversi(totalBiaya));
                        struk.setTotalBiaya(KonversiRupiah.konversi(totalBiaya));
                        struk.setTotalKeseluruhan(KonversiRupiah.konversi(totalKeseluruhan));
                        struk.setTotalPromoDiskon(KonversiRupiah.konversi(totalPromoDiskon));
                        struk.setPembayaran(transaksi.getPembayaran().getNamaPembayaran());
                        struk.setNamaAdmin(adminFrame.profil.getNamaAdmin());
                        cetakStruk(struk);
                   
                        adminFrame.setEnabled(true);
                        adminFrame.pilihHalaman("transaksi");
                        
                        dispose();
                 }else{
                     JOptionPane.showMessageDialog(rootPane, "Transaksi gagal dibuat", "Transkasi", JOptionPane.ERROR_MESSAGE);
                 }
                 
             }else{
                 transaksi.setStatusTransaksi(cbStatus.getSelectedItem().toString());
                 boolean update = transaksiTable.ubahTransaksi(transaksi);
              
                 if(update == true){
                    JOptionPane.showMessageDialog(rootPane, "Transaksi Berhasil diubah", "Transaksi", JOptionPane.INFORMATION_MESSAGE);
                    
                    adminFrame.setEnabled(true);
                    adminFrame.pilihHalaman("transaksi");
                    
                    dispose();
                 }else{
                    JOptionPane.showMessageDialog(rootPane, "Transaksi gagal diubah", "Transkasi", JOptionPane.ERROR_MESSAGE);
                 }
                    
             }
            
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalTutupActionPerformed
        String text = btnBatalTutup.getText();
        if(text.equals("Tutup")){
            adminFrame.setEnabled(true);
            dispose();
        }else{
            setMode("read");
        }
        
    }//GEN-LAST:event_btnBatalTutupActionPerformed

    private void cbPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPelangganActionPerformed

           int pelangganTerpilih = cbPelanggan.getSelectedIndex();
           String level = listPelanggan.get(pelangganTerpilih).getLevel();
           getPromoDiskon(level);
           hitung();
    }//GEN-LAST:event_cbPelangganActionPerformed

    private void tfCariPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCariPelangganActionPerformed
        cariPelanggan(tfCariPelanggan.getText());
        setModelCb(getCbPelanggan(), cbPelanggan);
    }//GEN-LAST:event_tfCariPelangganActionPerformed

    private void cbObjekWisataItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbObjekWisataItemStateChanged
        int selectedModel = cbObjekWisata.getSelectedIndex();
        hargaTiket = listObjekWisata.get(selectedModel).getHargaTiket();
        setCbJumlahTiketTersedia();
        cbJumlahTiketTersedia.setSelectedIndex(0);
        jumlahTiket = 1;
        hitung();
    }//GEN-LAST:event_cbObjekWisataItemStateChanged

    private void btnTambahPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahPelangganMouseClicked
        dispose();
        PelangganFormFrame pelangganFormFrame = new PelangganFormFrame(this);
        pelangganFormFrame.setVisible(true);
    }//GEN-LAST:event_btnTambahPelangganMouseClicked

    private void btnLihatObjekWisataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLihatObjekWisataMouseClicked
        ObjekWisata ow = listObjekWisata.get(cbObjekWisata.getSelectedIndex());
        new DetailObjekWisataFrame(ow).setVisible(true);
    }//GEN-LAST:event_btnLihatObjekWisataMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        setMode("edit");
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnCetakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakMouseClicked
        Struk struk = new Struk();
        struk.setKodeTransaksi(tfKode.getText());
        struk.setPelanggan(((KeyValueInt)cbPelanggan.getSelectedItem()).getValue());
        struk.setNamaObjekWisata(((KeyValueString)cbObjekWisata.getSelectedItem()).getValue());
        struk.setJumlahTiket(cbJumlahTiketTersedia.getSelectedItem().toString());
        struk.setTanggalWisata(tfTanggalWisata.getText());
        struk.setTotalObjekWisata(KonversiRupiah.konversi(totalBiaya));
        struk.setTotalBiaya(KonversiRupiah.konversi(totalBiaya));
        struk.setTotalKeseluruhan(KonversiRupiah.konversi(totalKeseluruhan));
        struk.setTotalPromoDiskon(KonversiRupiah.konversi(totalPromoDiskon));
        struk.setPembayaran(cbPembayaran.getSelectedItem().toString());
        struk.setNamaAdmin(adminFrame.profil.getNamaAdmin());
        cetakStruk(struk);
    }//GEN-LAST:event_btnCetakMouseClicked

    private void cbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbStatusItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStatusItemStateChanged

    private void tfTanggalWisataFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfTanggalWisataFocusGained
        setCbJumlahTiketTersedia();
    }//GEN-LAST:event_tfTanggalWisataFocusGained

    private void cbJumlahTiketTersediaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJumlahTiketTersediaActionPerformed
        if(cbJumlahTiketTersedia.getItemCount() > 0){
            String jml = cbJumlahTiketTersedia.getSelectedItem().toString();
            jumlahTiket = Integer.parseInt(jml);
            hitung();
        }
    }//GEN-LAST:event_cbJumlahTiketTersediaActionPerformed

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
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(TransaksiFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TransaksiFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TransaksiFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TransaksiFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TransaksiFormFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatalTutup;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnLihatObjekWisata;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambahPelanggan;
    private javax.swing.JComboBox<String> cbJumlahTiketTersedia;
    private javax.swing.JComboBox<String> cbObjekWisata;
    private javax.swing.JComboBox<String> cbPelanggan;
    private javax.swing.JComboBox<String> cbPembayaran;
    private javax.swing.JComboBox<String> cbStatus;
    private com.raven.datechooser.DateChooser dpTanggalWisata;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblDibuatPada;
    private javax.swing.JLabel lblDibuatPada1;
    private javax.swing.JLabel lblPromoDiskon;
    private javax.swing.JLabel lblTerakhirDiubah;
    private javax.swing.JLabel lblTerakhirDiubah2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblTitle4;
    private javax.swing.JLabel lblTitle5;
    private javax.swing.JLabel lblTitle6;
    private javax.swing.JLabel lblTotalBiaya;
    private javax.swing.JLabel lblTotalKeseluruhan;
    private javax.swing.JTextField tfCariPelanggan;
    private javax.swing.JTextField tfKode;
    private javax.swing.JTextField tfTanggalWisata;
    // End of variables declaration//GEN-END:variables

    private void cbSetModel(ArrayList<Pelanggan> tampilPelanggan, String id_pelanggan, String nama_pelanggan, String aint) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
