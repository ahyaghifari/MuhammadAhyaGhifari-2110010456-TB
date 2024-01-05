package frame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Admin;
import model.Pelanggan;
import table.AdminTable;


/**
 *
 * @author Ahya Ghifari
 */
public class AdminFormFrame extends javax.swing.JFrame {
    private AdminFrame adminFrame;
    private ManajerFrame manajerFrame;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private String status = "TAMBAH";
    private int idAdmin;
    private String usernameLama;
    private String jenisKelamin = "";
    private String jabatan = "";
    private boolean isProfil;
    
    /**
     * Creates new form KategoriFormFrame
     * @param manajerFrame
     */
    
    // KONSTRUKTOR TAMBAH ADMIN - Untuk Manajer
    public AdminFormFrame(ManajerFrame manajerFrame) {
        initComponents();
        
        status = "TAMBAH";
        this.manajerFrame = manajerFrame;
        this.setTitle("Admin");
        jabatan = "Admin";
        
        lblTitle.setText("Tambah Admin");
        btnEdit.setVisible(false);
        btnHapus.setVisible(false);
        
    }
    
    // KONSTRUKTOR UNTUK BACA ADMIN ATAU PROFIL - Untuk Manajer
    public AdminFormFrame(ManajerFrame manajerFrame, Admin admin, boolean isProfil) {
        initComponents();
        
        status = "EDIT";
        this.manajerFrame = manajerFrame;
        this.isProfil = isProfil;
        
        // isi properti
        idAdmin = admin.getIdAdmin();
        usernameLama = admin.getUsername();
        jenisKelamin = admin.getJenisKelamin();
        jabatan = admin.getJabatan();
        
        // isi form
        tfUsername.setText(admin.getUsername());
        tfNama.setText(admin.getNamaAdmin());

        try {
            dpTanggalLahir.setSelectedDate(dateFormat.parse(admin.getTanggalLahir()));
        } catch (ParseException ex) {
            Logger.getLogger(AdminFormFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        taAlamat.setText(admin.getAlamat());
        tfNoTelepon.setText(admin.getNoTelepon());
        tfEmail.setText(admin.getEmail());
        
        // set jenis kelamin
        if(jenisKelamin.equals("Laki-Laki")){
            rbLakiLaki.setSelected(true);
        }else{
            rbPerempuan.setSelected(true);
        }
        
        // set nama dan title form
        if(isProfil == true){
            lblTitle.setText("Edit Profil");
            this.setTitle("Profil");
            lblPassword.setVisible(false);
            pfPassword.setVisible(false);
            btnEdit.setVisible(false);
            btnHapus.setVisible(false);
           
        }else{
            changeModeManajer("read");
        } 
    }
    
    // KONSTRUKTOR UNTUK EDIT PROFIL - Untuk Admin
    public AdminFormFrame(AdminFrame adminFrame, Admin admin) {
        initComponents();
        
        status = "EDIT";
        this.adminFrame = adminFrame;
        this.isProfil = true;
        
        lblTitle.setText("Edit Profil");
        this.setTitle("Profil");
        lblPassword.setVisible(false);
        pfPassword.setVisible(false);
        btnEdit.setVisible(false);
        btnHapus.setVisible(false);
        
        idAdmin = admin.getIdAdmin();
        usernameLama = admin.getUsername();
        jenisKelamin = admin.getJenisKelamin();
        jabatan = admin.getJabatan();
        
        tfUsername.setText(admin.getUsername());
        tfNama.setText(admin.getNamaAdmin());

        try {
            dpTanggalLahir.setSelectedDate(dateFormat.parse(admin.getTanggalLahir()));
        } catch (ParseException ex) {
            Logger.getLogger(AdminFormFrame.class.getName()).log(Level.SEVERE, null, ex);
        }


        taAlamat.setText(admin.getAlamat());
        tfNoTelepon.setText(admin.getNoTelepon());
        tfEmail.setText(admin.getEmail());
        
        // set jenis kelamin
        if(jenisKelamin.equals("Laki-Laki")){
            rbLakiLaki.setSelected(true);
        }else{
            rbPerempuan.setSelected(true);
        }
    }
    
    
    // METHOD SETTING FORM UNTUK MODE BACA DAN EDIT - Untuk Manajer
    private void changeModeManajer(String mode){
        if(mode.equals("read")){
            
            lblTitle.setText("Admin");
            this.setTitle("Admin");
            
            btnEdit.setEnabled(true);
            btnBatalTutup.setText("Tutup");
            btnSimpan.setVisible(false);
            
            tfUsername.setEditable(false);
            tfNama.setEditable(false);
           
            dpTanggalLahir.setVisible(false);
            taAlamat.setEditable(false);
            tfNoTelepon.setEditable(false);
            tfEmail.setEditable(false);
            rbLakiLaki.setEnabled(false);
            rbPerempuan.setEnabled(false);
            
            lblPassword.setVisible(false);
            pfPassword.setVisible(false);

        }else if(mode.equals("edit")){

            
            lblTitle.setText("Edit Admin");
            this.setTitle("Admin");
            
            btnEdit.setEnabled(false);
            btnBatalTutup.setText("Batal");
            btnSimpan.setVisible(true);
            
            tfUsername.setEditable(true);
            tfNama.setEditable(true);
            
            dpTanggalLahir.setVisible(true);
            taAlamat.setEditable(true);
            tfNoTelepon.setEditable(true);
            tfEmail.setEditable(true);
            rbLakiLaki.setEnabled(true);
            rbPerempuan.setEnabled(true);
            lblPassword.setVisible(true);
            pfPassword.setVisible(true);
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

        bgJenisKelamin = new javax.swing.ButtonGroup();
        dpTanggalLahir = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        tfUsername = new javax.swing.JTextField();
        lblTitle2 = new javax.swing.JLabel();
        lblTitle3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taAlamat = new javax.swing.JTextArea();
        btnSimpan = new javax.swing.JButton();
        btnBatalTutup = new javax.swing.JButton();
        lblTitle4 = new javax.swing.JLabel();
        tfNoTelepon = new javax.swing.JTextField();
        lblTitle5 = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        lblTitle6 = new javax.swing.JLabel();
        tfNama = new javax.swing.JTextField();
        lblTitle7 = new javax.swing.JLabel();
        lblTitle8 = new javax.swing.JLabel();
        rbLakiLaki = new javax.swing.JRadioButton();
        rbPerempuan = new javax.swing.JRadioButton();
        lblPassword = new javax.swing.JLabel();
        pfPassword = new javax.swing.JPasswordField();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        tfTanggalLahir = new javax.swing.JTextField();

        dpTanggalLahir.setForeground(new java.awt.Color(23, 70, 90));
        dpTanggalLahir.setDateFormat("yyyy-MM-dd");
        dpTanggalLahir.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        dpTanggalLahir.setTextRefernce(tfTanggalLahir);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(23, 70, 90), 1, true));

        lblTitle.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(23, 70, 90));
        lblTitle.setText("Admin");

        tfUsername.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle2.setText("Username : ");

        lblTitle3.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle3.setText("Alamat : ");

        taAlamat.setColumns(20);
        taAlamat.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        taAlamat.setLineWrap(true);
        taAlamat.setRows(5);
        taAlamat.setWrapStyleWord(true);
        jScrollPane1.setViewportView(taAlamat);

        btnSimpan.setBackground(new java.awt.Color(23, 70, 90));
        btnSimpan.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
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

        lblTitle4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle4.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle4.setText("No Telepon : ");

        tfNoTelepon.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle5.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle5.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle5.setText("Email : ");

        tfEmail.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle6.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle6.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle6.setText("Nama : ");

        tfNama.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        lblTitle7.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle7.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle7.setText("Tanggal Lahir : ");

        lblTitle8.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblTitle8.setForeground(new java.awt.Color(28, 28, 39));
        lblTitle8.setText("Jenis Kelamin : ");

        bgJenisKelamin.add(rbLakiLaki);
        rbLakiLaki.setText("Laki-Laki");
        rbLakiLaki.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbLakiLakiMouseClicked(evt);
            }
        });

        bgJenisKelamin.add(rbPerempuan);
        rbPerempuan.setText("Perempuan");
        rbPerempuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbPerempuanMouseClicked(evt);
            }
        });

        lblPassword.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(28, 28, 39));
        lblPassword.setText("Password :");

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

        btnHapus.setBackground(new java.awt.Color(250, 255, 255));
        btnHapus.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        tfTanggalLahir.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTitle2)
                                    .addComponent(lblTitle6)
                                    .addComponent(lblTitle7))
                                .addGap(54, 54, 54)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblTitle3))
                        .addContainerGap(153, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBatalTutup))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnHapus))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblTitle4)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(tfNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45)
                                        .addComponent(lblTitle5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblTitle8)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblPassword)
                                        .addGap(49, 49, 49)))
                                .addComponent(rbLakiLaki)
                                .addGap(18, 18, 18)
                                .addComponent(rbPerempuan)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(btnBatalTutup, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle2)
                    .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle6)
                    .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle7)
                    .addComponent(tfTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(lblTitle3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle4)
                    .addComponent(tfNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle5)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle8)
                    .addComponent(rbLakiLaki)
                    .addComponent(rbPerempuan))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPassword)
                    .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
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

    private void btnBatalTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalTutupActionPerformed
        if(btnBatalTutup.getText().equals("Tutup")){
            if(manajerFrame != null){
                manajerFrame.setEnabled(true);
            }else{
                adminFrame.setEnabled(true);
            }
            dispose();
        }else{
            changeModeManajer("read");
        }
        
    }//GEN-LAST:event_btnBatalTutupActionPerformed

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        
        String context = this.getTitle();
        
        String username = tfUsername.getText();
        String nama = tfNama.getText();
        String tanggalLahir = tfTanggalLahir.getText();
        String alamat = taAlamat.getText();
        String no_telepon = tfNoTelepon.getText();
        String email = tfEmail.getText();
        String password = pfPassword.getText();
        
        if(username.equals("") || nama.equals("") || tanggalLahir.equals("")  || alamat.equals("") || 
                no_telepon.equals("") || email.equals("") || jenisKelamin.equals("")){
            
           JOptionPane.showMessageDialog(rootPane, "Data masih belum lengkap", context, JOptionPane.WARNING_MESSAGE);
       
        }else if(status.equals("TAMBAH") && password.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Password belum diisi", context, JOptionPane.WARNING_MESSAGE);
            
        }else if(no_telepon.length() > 15){
            JOptionPane.showMessageDialog(rootPane, "Isi no telepon dengan benar", context, JOptionPane.WARNING_MESSAGE);
          
        }else{
            
            Admin a = new Admin();
            
            a.setIdAdmin(idAdmin);
            a.setUsername(username);
            a.setNamaAdmin(nama);
            a.setTanggalLahir(tanggalLahir);
            a.setAlamat(alamat);
            a.setNoTelepon(no_telepon);
            a.setEmail(email);
            a.setJenisKelamin(jenisKelamin);
            a.setJabatan(jabatan);
            a.setPassword(password);
            
            AdminTable adminTable = new AdminTable();
            
            if(status.equals("TAMBAH")){
            
                String insert = adminTable.tambahAdmin(a);
                
                if(insert.equals("SUCCESS")){
                    
                    JOptionPane.showMessageDialog(rootPane, context + " berhasil ditambahkan.", context, JOptionPane.INFORMATION_MESSAGE);
                
                    manajerFrame.setEnabled(true);
                    manajerFrame.pilihHalaman("data-admin");
                    
                    dispose();
                    
                }else{
                    if(insert.contains("Username")){
                        JOptionPane.showMessageDialog(rootPane, insert, context, JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(rootPane, context + " gagal ditambahkan", context, JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
                
            }else{
                String update = adminTable.ubahAdmin(a, usernameLama);
                
                if(update.equals("SUCCESS")){
                    
                    JOptionPane.showMessageDialog(rootPane, context + " berhasil diubah.", context, JOptionPane.INFORMATION_MESSAGE);

                    if(manajerFrame != null){
                        manajerFrame.setEnabled(true);
                        if(isProfil == true){    
                            manajerFrame.profil = a;
                            manajerFrame.pilihHalaman("profil");
                        }else{
                            manajerFrame.pilihHalaman("data-admin");
                        }
                        dispose();
                    }else{
                        adminFrame.setEnabled(true);
                        adminFrame.profil = a;
                        adminFrame.pilihHalaman("profil");
                        dispose();
                    }
                   
                }else{
                    if(update.contains("Username")){
                        JOptionPane.showMessageDialog(rootPane, update, context, JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(rootPane, context + " gagal diubah", context, JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
                
            }
            
        }
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void rbLakiLakiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbLakiLakiMouseClicked
        jenisKelamin = "Laki-Laki";
    }//GEN-LAST:event_rbLakiLakiMouseClicked

    private void rbPerempuanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbPerempuanMouseClicked
        jenisKelamin = "Perempuan";
    }//GEN-LAST:event_rbPerempuanMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        changeModeManajer("edit");
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin menghapus admin ini ?", "Admin", JOptionPane.YES_NO_OPTION);
        if(konfirmasi == 0){
            Admin adminHapus = new Admin();
            adminHapus.setIdAdmin(idAdmin);
            
            AdminTable adminTable = new AdminTable();
            
            boolean delete = adminTable.hapusAdmin(adminHapus, manajerFrame.profil.getIdAdmin());
            
            if(delete == true){
                JOptionPane.showMessageDialog(rootPane, "Admin berhasil dihapus", "Admin", JOptionPane.INFORMATION_MESSAGE);
                manajerFrame.setEnabled(true);
                manajerFrame.pilihHalaman("data-admin");
                
                dispose();
            }else{
                JOptionPane.showMessageDialog(rootPane, "Admin gagal dihapus", "Admin", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_btnHapusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgJenisKelamin;
    private javax.swing.JButton btnBatalTutup;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private com.raven.datechooser.DateChooser dpTanggalLahir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblTitle4;
    private javax.swing.JLabel lblTitle5;
    private javax.swing.JLabel lblTitle6;
    private javax.swing.JLabel lblTitle7;
    private javax.swing.JLabel lblTitle8;
    private javax.swing.JPasswordField pfPassword;
    private javax.swing.JRadioButton rbLakiLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JTextArea taAlamat;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfNoTelepon;
    private javax.swing.JTextField tfTanggalLahir;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
}