/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Kategori;
import model.KeyValueString;
import model.ObjekWisata;
import table.KategoriTable;
import table.ObjekWisataTable;

/**
 *
 * @author Ahya Ghifari
 */
public class ObjekWisataFormFrame extends javax.swing.JFrame {

    // inisialisasi properti properti
    private AdminFrame adminFrame;
    private ObjekWisataTable objekWisataTable;
    private String status;
    
    private KategoriTable kategoriTable;
    private ArrayList<Kategori> listKategori;
    
    private BufferedImage gambarObjekWisata;
    private final int TINGGI_GAMBAR = 160; // inisialisasi properti tinggi gambar
    
    
    /**
     * Creates new form KategoriFormFrame
     */
    public ObjekWisataFormFrame() {
        initComponents();
    }

    // KONSTRUKTOR UNTUK TAMBAH
    public ObjekWisataFormFrame(AdminFrame adminFrame) {
        initComponents();
        this.adminFrame = adminFrame;
        
        objekWisataTable = new ObjekWisataTable();
        kategoriTable = new KategoriTable();
        
        listKategori = kategoriTable.tampilKategori("");
        
        tfId.setText(objekWisataTable.getIdBaru());
        getCbKategori();
        
        status = "TAMBAH";
        lblTitle.setText("Tambah Objek Wisata");
        
    }
    
    // KONSTRUKTOR UNTUK EDIT
    public ObjekWisataFormFrame(AdminFrame adminFrame, ObjekWisata objekWisata) {
        initComponents();
        
        this.adminFrame = adminFrame;
        
        objekWisataTable = new ObjekWisataTable();
        kategoriTable = new KategoriTable();
        
        listKategori = kategoriTable.tampilKategori("");
        
        
        // isi form dengan objek wisata yang diterima dari parameter
        tfId.setText(objekWisata.getIdObjekWisata());
        tfNama.setText(objekWisata.getNamaObjekWisata());
        tfLokasi.setText(objekWisata.getLokasi());
        taDeskripsi.setText(objekWisata.getDeskripsiObjekWisata());
        tfKetersediaanTiket.setText(String.valueOf(objekWisata.getKetersediaanTiket()));
        tfHargaTiket.setText(String.valueOf(objekWisata.getHargaTiket()));
        setJamOperasional(objekWisata.getJamOperasional());
        
        gambarObjekWisata = getBufferedImage(objekWisata.getGambarObjekWisata()); // menampilkan gambar
        lblGambar.setIcon(new ImageIcon(gambarObjekWisata));
        
        getCbKategori();
        setCbKategori(objekWisata.getKategori().getNamaKategori());
        
        status = "EDIT";
        lblTitle.setText("Edit Objek Wisata");
    }
    
    // method untuk mennampilkan pilihan kategori dari tabel kategpri
    private Vector getCbKategori(){
        Vector v = new Vector();
        
        try{
            ArrayList<Kategori> data = listKategori;

            v.addElement(new KeyValueString("", "Pilih Kategori"));
            for(int i = 0; i < data.size(); i++){
                v.addElement(new KeyValueString(data.get(i).getIdKategori(), data.get(i).getNamaKategori()));
            }
            
            DefaultComboBoxModel model;
            model = new DefaultComboBoxModel(v);
            cbKategori.setModel(model);
            
        }catch(Exception ex){
            System.err.println("ERROR getCbKategori : " + ex);
        }
        return v;
    }
    
    // method untuk memilih item combo box sesuai dengan objek wisata sebelumnya untuk pengeditan
    private void setCbKategori(String kategori){
        KeyValueString item = new KeyValueString();
        
        for(int i = 0; i < cbKategori.getItemCount(); i++){
            cbKategori.setSelectedIndex(i);
            item.setValue(((KeyValueString)cbKategori.getSelectedItem()).getValue());
            if(item.getValue().equals(kategori)){
                cbKategori.setSelectedIndex(i);
                break;
            }
        }
    }
    
    // method untuk set jam operasional dari objek wisata yang diterima
    private void setJamOperasional(String jam){
        String dari = jam.substring(0, 5);
        String sampai = jam.substring(8, 13);
        
        tfJODari.setText(dari);
        tfJOSampai.setText(sampai);
    }

    // method untuk menampilkan gambar ke form gambar
    public BufferedImage getBufferedImage(Blob imageBlob){
        InputStream binaryStream = null;
        BufferedImage b = null;
        
        try{
            binaryStream = imageBlob.getBinaryStream();
            b = ImageIO.read(binaryStream);
        }catch(SQLException | IOException ex){
            System.err.println("Error getBufferedImage : " + ex );
        }
        
        return b;
    }
    
    // method untuk mengonversi gambar dari blog kee buffered image
    public Blob getBlobImage(BufferedImage bi){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Blob blFile = null;
        
        try{
            ImageIO.write(bi, "png", baos);
            blFile = new javax.sql.rowset.serial.SerialBlob(baos.toByteArray());
        }catch(SQLException | IOException ex){
            Logger.getLogger(ObjekWisataFormFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blFile;
    }
    
    // method untuk mengubah ukuran gambar lebih kecil sesuai dengan keperluan
    private BufferedImage resizeImage(BufferedImage originalImage, int type){
        int lebarGambar = (int) Math.round((double) originalImage.getWidth() / originalImage.getHeight() * TINGGI_GAMBAR);
        BufferedImage resizedImage = new BufferedImage(lebarGambar, TINGGI_GAMBAR, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, lebarGambar, TINGGI_GAMBAR, null);
        g.dispose();
        return resizedImage;
    } 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fChooser = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblTitle1 = new javax.swing.JLabel();
        tfNama = new javax.swing.JTextField();
        lblTitle2 = new javax.swing.JLabel();
        tfId = new javax.swing.JTextField();
        lblTitle3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taDeskripsi = new javax.swing.JTextArea();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        lblTitle4 = new javax.swing.JLabel();
        tfLokasi = new javax.swing.JTextField();
        lblTitle5 = new javax.swing.JLabel();
        tfKetersediaanTiket = new javax.swing.JTextField();
        lblTitle6 = new javax.swing.JLabel();
        tfJODari = new javax.swing.JTextField();
        lblTitle7 = new javax.swing.JLabel();
        tfHargaTiket = new javax.swing.JTextField();
        tfJOSampai = new javax.swing.JTextField();
        lblTitle8 = new javax.swing.JLabel();
        lblTitle9 = new javax.swing.JLabel();
        cbKategori = new javax.swing.JComboBox<>();
        lblTitle10 = new javax.swing.JLabel();
        btnPilihGambar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblGambar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Objek Wisata");
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));

        lblTitle.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(23, 70, 90));
        lblTitle.setText("Tambah Objek Wisata");

        lblTitle1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle1.setText("ID : ");

        tfNama.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle2.setText("Nama : ");

        tfId.setEditable(false);
        tfId.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle3.setText("Deskripsi : ");

        taDeskripsi.setColumns(20);
        taDeskripsi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        taDeskripsi.setLineWrap(true);
        taDeskripsi.setRows(5);
        taDeskripsi.setWrapStyleWord(true);
        taDeskripsi.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        jScrollPane1.setViewportView(taDeskripsi);

        btnSimpan.setBackground(new java.awt.Color(23, 70, 90));
        btnSimpan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(250, 255, 255));
        btnBatal.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        lblTitle4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle4.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle4.setText("Lokasi : ");

        tfLokasi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle5.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle5.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle5.setText("Ketersediaan Tiket : ");

        tfKetersediaanTiket.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle6.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle6.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle6.setText("Harga Tiket : ");

        tfJODari.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle7.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle7.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle7.setText("-");

        tfHargaTiket.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        tfJOSampai.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle8.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle8.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle8.setText("Jam Operasional : ");

        lblTitle9.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle9.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle9.setText("Kategori : ");

        cbKategori.setBackground(new java.awt.Color(255, 255, 254));
        cbKategori.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbKategori.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        lblTitle10.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle10.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle10.setText("Gambar : ");

        btnPilihGambar.setBackground(new java.awt.Color(255, 255, 254));
        btnPilihGambar.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        btnPilihGambar.setForeground(new java.awt.Color(25, 70, 90));
        btnPilihGambar.setText("Pilih Gambar");
        btnPilihGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPilihGambarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblGambar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(lblGambar);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitle))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSimpan)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTitle3)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblTitle1)
                                        .addGap(56, 56, 56)
                                        .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblTitle2)
                                        .addGap(31, 31, 31)
                                        .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblTitle4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnBatal)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTitle6)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblTitle5)
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(tfKetersediaanTiket, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(tfHargaTiket, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(lblTitle10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnPilihGambar)))
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(tfJODari, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblTitle7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tfJOSampai, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(lblTitle8)))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTitle9)
                                    .addComponent(cbKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(btnBatal))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle1)
                    .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle2)
                    .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfLokasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle4))
                .addGap(18, 18, 18)
                .addComponent(lblTitle3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTitle5)
                            .addComponent(tfKetersediaanTiket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTitle6)
                            .addComponent(tfHargaTiket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle8)
                            .addComponent(lblTitle9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfJODari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTitle7)
                            .addComponent(tfJOSampai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle10)
                    .addComponent(btnPilihGambar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSimpan)
                .addContainerGap(17, Short.MAX_VALUE))
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
        
        // mengambil nilai dri form
        String id = tfId.getText();
        String nama = tfNama.getText();
        String lokasi = tfLokasi.getText();
        String deskripsi = taDeskripsi.getText();
        String keterTiket = tfKetersediaanTiket.getText();
        String hargaTiket = tfHargaTiket.getText();
        String joDari = tfJODari.getText();
        String joSampai = tfJOSampai.getText();
        String pilihKategori = cbKategori.getSelectedItem().toString();
        
        // validasi
        if(nama.equals("") || lokasi.equals("") || deskripsi.equals("") || keterTiket.equals("")
                || hargaTiket.equals("") || joDari.equals("") || joSampai.equals("") || pilihKategori.equals("Pilih Kategori")){
            JOptionPane.showMessageDialog(rootPane, "Lengkapi data objek wisata", "Objek Wisata", JOptionPane.WARNING_MESSAGE);
        
        // jika gambar belum dipilih pada saat tambah
        }else if(gambarObjekWisata == null){
            JOptionPane.showMessageDialog(rootPane, "Pilih Gambar objek wisata", "Objek Wisata", JOptionPane.WARNING_MESSAGE);
                
        }else{
            
            // inisialisasi objek objek wisata dan kategori
            ObjekWisata objekWisata = new ObjekWisata();
            
            objekWisata.setIdObjekWisata(id);
            objekWisata.setNamaObjekWisata(nama);
            objekWisata.setGambarObjekWisata(getBlobImage(gambarObjekWisata));
            objekWisata.setLokasi(lokasi);
            objekWisata.setDeskripsi(deskripsi);
            objekWisata.setKetersediaanTiket(Integer.parseInt(keterTiket));
            objekWisata.setHargaTiket(Double.parseDouble(hargaTiket));
            objekWisata.setJamOperasional(joDari + " - " + joSampai);
            
            Kategori kategori = new Kategori();
            kategori.setIdKategori(((KeyValueString)cbKategori.getSelectedItem()).getKey());
            
            objekWisata.setKategori(kategori);
            
            // INSERT OBJEK WISATA 
            if(status.equals("TAMBAH")){
                boolean insert = objekWisataTable.tambahObjekWisata(objekWisata); // tambah objek wisata

                if(insert == true){ // jika proses tambah berhasil
                    JOptionPane.showMessageDialog(rootPane, "Objek Wisata berhasil ditambahkan", "Objek Wisata", JOptionPane.INFORMATION_MESSAGE);
                   
                    adminFrame.setEnabled(true);
                    adminFrame.pilihHalaman("objek-wisata");
                    
                    dispose();
                    
               }else{ // jika proses tambah gagal
                    JOptionPane.showMessageDialog(rootPane, "Objek Wisata gagal ditambah", "Objek Wisata", JOptionPane.ERROR_MESSAGE);
                }
    
            // UPDATE
            }else{ 
                boolean update = objekWisataTable.editObjekWisata(objekWisata); // edit objek wisata

                if(update == true){ // jika proses edit berhasil
                    JOptionPane.showMessageDialog(rootPane, "Objek Wisata berhasil diubah", "Objek Wisata", JOptionPane.INFORMATION_MESSAGE);
                    
                    adminFrame.setEnabled(true);
                    adminFrame.pilihHalaman("objek-wisata");
                    
                    dispose();
                    
               }else{ // jika proses edit gagal
                    JOptionPane.showMessageDialog(rootPane, "Objek Wisata gagal diubah", "Objek Wisata", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        adminFrame.setEnabled(true);
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnPilihGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPilihGambarActionPerformed
        
        // menentukan pilihan file khusus untuk gambar
        FileFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
        
        // menerapkan pilihan 
        fChooser.setFileFilter(filter);
        BufferedImage img = null;
        
        try{
            // mengambil foto dari komputer
            int result =fChooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                File file = fChooser.getSelectedFile();
                img = ImageIO.read(file);
                int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();
                gambarObjekWisata = resizeImage(img, type);
                lblGambar.setIcon(new ImageIcon(gambarObjekWisata));
            }
        }catch(IOException ex){
            System.err.println("Error bPilih : " + ex);
        }
    }//GEN-LAST:event_btnPilihGambarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ObjekWisataFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ObjekWisataFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ObjekWisataFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ObjekWisataFormFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ObjekWisataFormFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnPilihGambar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cbKategori;
    private javax.swing.JFileChooser fChooser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGambar;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle10;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblTitle4;
    private javax.swing.JLabel lblTitle5;
    private javax.swing.JLabel lblTitle6;
    private javax.swing.JLabel lblTitle7;
    private javax.swing.JLabel lblTitle8;
    private javax.swing.JLabel lblTitle9;
    private javax.swing.JTextArea taDeskripsi;
    private javax.swing.JTextField tfHargaTiket;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfJODari;
    private javax.swing.JTextField tfJOSampai;
    private javax.swing.JTextField tfKetersediaanTiket;
    private javax.swing.JTextField tfLokasi;
    private javax.swing.JTextField tfNama;
    // End of variables declaration//GEN-END:variables
}
