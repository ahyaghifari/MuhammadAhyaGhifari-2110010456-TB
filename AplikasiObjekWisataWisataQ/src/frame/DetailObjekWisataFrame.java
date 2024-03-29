/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.ObjekWisata;
import service.KonversiRupiah;
import table.ObjekWisataTable;

/**
 *
 * @author Ahya Ghifari
 */
public class DetailObjekWisataFrame extends javax.swing.JFrame {

    // inisialisasi properti
    private final ObjekWisata objekWisata;
    private AdminFrame adminFrame;
    
    /**
     * Creates new form DetailObjekWisata
     * @param af
     * @param ow
     */
    
    /// KONSTRUKTOR UNTUK HALAMAN ADMINFRAME
    public DetailObjekWisataFrame(AdminFrame af, ObjekWisata ow){
        initComponents();
        
        objekWisata = ow;
        adminFrame = af;
        
        setDetail();
        
        btnObjekWisataEdit.setVisible(true);
        btnObjekWisataHapus.setVisible(true);
    }
    
    // KKONSTRUKTOR UNTUK FORM TRANSAKSI
    public DetailObjekWisataFrame(ObjekWisata ow){
        initComponents();
        
        objekWisata = ow;
        
        setDetail();
        
        btnObjekWisataEdit.setVisible(false);
        btnObjekWisataHapus.setVisible(false);
    }
    
    
    // METHOD UNTUK SET DETAIL label dan gambar detail objek wisata
    private void setDetail(){
        
        ObjekWisata ow = objekWisata;
        
        lblObjekWisataNamaLokasi.setText(ow.getNamaObjekWisata() + " (" + ow.getLokasi() + ")");
        
        taObjekWisataDetailDeskripsi.setText(ow.getDeskripsiObjekWisata());
        lblObjekWisataDetailKeterTiket.setText(": " + ow.getKetersediaanTiket());
        lblObjekWisataDetailHargaTiket.setText(KonversiRupiah.konversi(ow.getHargaTiket()));
        lblObjekWisataDetailJamOperasi.setText(ow.getJamOperasional());
        lblObjekWisataDetailKategori.setText(ow.getKategori().getNamaKategori());
        lblObjekWisataDetailDibuatPada.setText(": " + ow.getCreated_at());
        lblObjekWisataDetailTerakhirDiubah.setText(": " + ow.getUpdated_at());
        
        BufferedImage gambarObjekWisata = getBufferedImage(ow.getGambarObjekWisata());
        lblGambar.setIcon(new ImageIcon(gambarObjekWisata));
    }

    // method untuk membaca gambar dari blob gambar
    public final BufferedImage getBufferedImage(Blob imageBlob){
        InputStream binaryStream;
        BufferedImage b = null;
        
        try{
            binaryStream = imageBlob.getBinaryStream();
            b = ImageIO.read(binaryStream);
        }catch(SQLException | IOException ex){
            System.err.println("Error getBufferedImage : " + ex );
        }
        
        return b;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDetailObjekWisata = new javax.swing.JPanel();
        lblObjekWisataNamaLokasi = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        taObjekWisataDetailDeskripsi = new javax.swing.JTextArea();
        jLabel35 = new javax.swing.JLabel();
        lblObjekWisataDetailDibuatPada = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblObjekWisataDetailTerakhirDiubah = new javax.swing.JLabel();
        btnObjekWisataEdit = new javax.swing.JButton();
        btnObjekWisataHapus = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        lblObjekWisataDetailHargaTiket = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblObjekWisataDetailKeterTiket = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lblObjekWisataDetailJamOperasi = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        lblObjekWisataDetailKategori = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblGambar = new javax.swing.JLabel();
        btnTutup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Objek Wisata");
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        panelDetailObjekWisata.setBackground(new java.awt.Color(255, 255, 255));
        panelDetailObjekWisata.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));

        lblObjekWisataNamaLokasi.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        lblObjekWisataNamaLokasi.setForeground(new java.awt.Color(23, 70, 90));
        lblObjekWisataNamaLokasi.setText("nama_lokasi_objek_wisata");

        jLabel34.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(28, 28, 39));
        jLabel34.setText("Deskripsi");

        taObjekWisataDetailDeskripsi.setEditable(false);
        taObjekWisataDetailDeskripsi.setBackground(new java.awt.Color(255, 255, 255));
        taObjekWisataDetailDeskripsi.setColumns(20);
        taObjekWisataDetailDeskripsi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        taObjekWisataDetailDeskripsi.setForeground(new java.awt.Color(28, 28, 39));
        taObjekWisataDetailDeskripsi.setLineWrap(true);
        taObjekWisataDetailDeskripsi.setRows(5);
        taObjekWisataDetailDeskripsi.setWrapStyleWord(true);
        taObjekWisataDetailDeskripsi.setAutoscrolls(false);
        taObjekWisataDetailDeskripsi.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        taObjekWisataDetailDeskripsi.setSelectedTextColor(new java.awt.Color(255, 255, 254));
        taObjekWisataDetailDeskripsi.setSelectionColor(new java.awt.Color(23, 70, 90));
        jScrollPane9.setViewportView(taObjekWisataDetailDeskripsi);

        jLabel35.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 102, 102));
        jLabel35.setText("Dibuat Pada");

        lblObjekWisataDetailDibuatPada.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblObjekWisataDetailDibuatPada.setForeground(new java.awt.Color(102, 102, 102));
        lblObjekWisataDetailDibuatPada.setText(": dibuat_pada");

        jLabel36.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(102, 102, 102));
        jLabel36.setText("Terakhir Diubah :");

        lblObjekWisataDetailTerakhirDiubah.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblObjekWisataDetailTerakhirDiubah.setForeground(new java.awt.Color(102, 102, 102));
        lblObjekWisataDetailTerakhirDiubah.setText(": terakhir_diubah");

        btnObjekWisataEdit.setBackground(new java.awt.Color(23, 70, 90));
        btnObjekWisataEdit.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnObjekWisataEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnObjekWisataEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/edit.png"))); // NOI18N
        btnObjekWisataEdit.setText("Edit");
        btnObjekWisataEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnObjekWisataEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnObjekWisataEditMouseClicked(evt);
            }
        });

        btnObjekWisataHapus.setBackground(new java.awt.Color(255, 255, 250));
        btnObjekWisataHapus.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnObjekWisataHapus.setForeground(new java.awt.Color(28, 28, 39));
        btnObjekWisataHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/hapus.png"))); // NOI18N
        btnObjekWisataHapus.setText("Hapus");
        btnObjekWisataHapus.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(28, 28, 39), 1, true));
        btnObjekWisataHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnObjekWisataHapusMouseClicked(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(28, 28, 39));
        jLabel37.setText("Harga Tiket");

        lblObjekWisataDetailHargaTiket.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblObjekWisataDetailHargaTiket.setForeground(new java.awt.Color(23, 70, 90));
        lblObjekWisataDetailHargaTiket.setText(": harga_tiket");

        jLabel38.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(28, 28, 39));
        jLabel38.setText("Ketersediaan Tiket");

        lblObjekWisataDetailKeterTiket.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblObjekWisataDetailKeterTiket.setForeground(new java.awt.Color(23, 70, 90));
        lblObjekWisataDetailKeterTiket.setText(": 10");

        jLabel39.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(28, 28, 39));
        jLabel39.setText("Jam Operasional");

        lblObjekWisataDetailJamOperasi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblObjekWisataDetailJamOperasi.setForeground(new java.awt.Color(23, 70, 90));
        lblObjekWisataDetailJamOperasi.setText(": 08.00 - 16.00");

        jLabel40.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(28, 28, 39));
        jLabel40.setText("Kategori");

        lblObjekWisataDetailKategori.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblObjekWisataDetailKategori.setForeground(new java.awt.Color(23, 70, 90));
        lblObjekWisataDetailKategori.setText(": kategori");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblGambar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblGambar);

        btnTutup.setBackground(new java.awt.Color(255, 255, 254));
        btnTutup.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnTutup.setText("Tutup");
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDetailObjekWisataLayout = new javax.swing.GroupLayout(panelDetailObjekWisata);
        panelDetailObjekWisata.setLayout(panelDetailObjekWisataLayout);
        panelDetailObjekWisataLayout.setHorizontalGroup(
            panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailObjekWisataLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel34)
                        .addComponent(lblObjekWisataNamaLokasi)
                        .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDetailObjekWisataLayout.createSequentialGroup()
                                .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36))
                                .addGap(31, 31, 31)
                                .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblObjekWisataDetailDibuatPada)
                                    .addGroup(panelDetailObjekWisataLayout.createSequentialGroup()
                                        .addComponent(lblObjekWisataDetailTerakhirDiubah)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnObjekWisataEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnObjekWisataHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDetailObjekWisataLayout.createSequentialGroup()
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblObjekWisataDetailJamOperasi, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel37)
                                    .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnTutup)
                                        .addGroup(panelDetailObjekWisataLayout.createSequentialGroup()
                                            .addComponent(jLabel38)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(lblObjekWisataDetailKeterTiket, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblObjekWisataDetailKategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblObjekWisataDetailHargaTiket, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 30, Short.MAX_VALUE))
        );
        panelDetailObjekWisataLayout.setVerticalGroup(
            panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailObjekWisataLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnTutup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblObjekWisataNamaLokasi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailObjekWisataLayout.createSequentialGroup()
                        .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(lblObjekWisataDetailKeterTiket))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblObjekWisataDetailHargaTiket)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblObjekWisataDetailJamOperasi)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblObjekWisataDetailKategori))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(lblObjekWisataDetailDibuatPada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDetailObjekWisataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(lblObjekWisataDetailTerakhirDiubah)
                    .addComponent(btnObjekWisataHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnObjekWisataEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDetailObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDetailObjekWisata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnObjekWisataEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnObjekWisataEditMouseClicked
        ObjekWisataFormFrame objekWisataFormFrame = new ObjekWisataFormFrame(adminFrame, objekWisata);
        objekWisataFormFrame.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnObjekWisataEditMouseClicked

    private void btnObjekWisataHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnObjekWisataHapusMouseClicked

        // konfirmasi hapus objek wisata
        int konfirmasi = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin menghapus objek wisata ini?", "Objek Wisata", JOptionPane.YES_NO_OPTION);
        if(konfirmasi == 0){
            
            // inisialisasi tabel objek wisata
            ObjekWisataTable objekWisataTable = new ObjekWisataTable();
            String delete = objekWisataTable.hapusObjekWisata(objekWisata.getIdObjekWisata()); // hapus objek wisata

            if(delete.equals("SUCCESS")){ // jika hapus berhasil
              
                JOptionPane.showMessageDialog(rootPane, "Objek Wisata berhasil dihapus", "Objek Wisata", JOptionPane.INFORMATION_MESSAGE);
                
                //mengaktifkan frame admin dan refresh halaman objwk wisata
                adminFrame.setEnabled(true);
                adminFrame.pilihHalaman("objek-wisata");
                dispose();
                
            }else{ // jika hapus gagal
                if(delete.contains("Objek Wisata")){ /// jika respon hapus ada kalimat objek wisata
                    JOptionPane.showMessageDialog(rootPane, delete, "Objek Wisata", JOptionPane.ERROR_MESSAGE);

                }else{ // jika objwk wisata gagal dihapus
                    JOptionPane.showMessageDialog(rootPane, "Objek Wisata gagal dihapus", "Objek Wisata", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }//GEN-LAST:event_btnObjekWisataHapusMouseClicked

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        if(adminFrame != null){
            adminFrame.setEnabled(true);
        }
        dispose();
    }//GEN-LAST:event_btnTutupActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnObjekWisataEdit;
    private javax.swing.JButton btnObjekWisataHapus;
    private javax.swing.JButton btnTutup;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblGambar;
    private javax.swing.JLabel lblObjekWisataDetailDibuatPada;
    private javax.swing.JLabel lblObjekWisataDetailHargaTiket;
    private javax.swing.JLabel lblObjekWisataDetailJamOperasi;
    private javax.swing.JLabel lblObjekWisataDetailKategori;
    private javax.swing.JLabel lblObjekWisataDetailKeterTiket;
    private javax.swing.JLabel lblObjekWisataDetailTerakhirDiubah;
    private javax.swing.JLabel lblObjekWisataNamaLokasi;
    private javax.swing.JPanel panelDetailObjekWisata;
    private javax.swing.JTextArea taObjekWisataDetailDeskripsi;
    // End of variables declaration//GEN-END:variables
}
