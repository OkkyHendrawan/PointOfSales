/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package formBantuan;

/**
 *
 * @author okkyh
 */
public class ViewBantuanPanduan extends javax.swing.JDialog {

    /**
     * Creates new form ViewBantuanPanduan
     */
    public ViewBantuanPanduan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        labelNama = new javax.swing.JLabel();
        btnKeluarIsianPelanggan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        panelHeader.setBackground(new java.awt.Color(51, 51, 51));
        panelHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelHeaderMouseDragged(evt);
            }
        });
        panelHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelHeaderMousePressed(evt);
            }
        });

        labelNama.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        labelNama.setForeground(new java.awt.Color(255, 255, 255));
        labelNama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNama.setText("Panduan Pengguna");

        btnKeluarIsianPelanggan.setBackground(new java.awt.Color(0, 153, 153));
        btnKeluarIsianPelanggan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnKeluarIsianPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        btnKeluarIsianPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/01.png"))); // NOI18N
        btnKeluarIsianPelanggan.setText("Keluar");
        btnKeluarIsianPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarIsianPelangganActionPerformed(evt);
            }
        });

        jTextArea1.setBackground(new java.awt.Color(51, 51, 51));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jTextArea1.setText("1. Pengenalan Sistem Inventaris\n\n1.1 Apa itu Sistem Inventaris?\nSistem Inventaris kami dirancang untuk membantu Anda mengelola dan melacak persediaan\nbarang secara efisien. Dengan fitur-fitur yang intuitif, Anda dapat dengan mudah menambahkan,\nmengedit, dan menghapus entri inventaris.\n\n1.2 Tujuan Penggunaan\n\nSistem ini dapat digunakan untuk:\nMencatat barang masuk dan keluar.\nMelacak tingkat persediaan dan mencegah kekurangan barang.\nMenghasilkan laporan inventaris secara berkala.\n\n2. Menjalankan Aplikasi\n\n2.1 Persyaratan Sistem\n\nPastikan perangkat Anda memenuhi persyaratan sistem minimum yang diperlukan untuk \nmenjalankan aplikasi ini.\n\n2.2 Instalasi Aplikasi\n\nIkuti panduan instalasi langkah-demi-langkah untuk memasang aplikasi di perangkat Anda.\n\n3. Memulai Penggunaan\n\n3.1 Masuk ke Sistem\n\nGunakan akun pengguna Anda untuk masuk ke sistem inventaris.\n\n3.2 Antarmuka Pengguna\n\nJelajahi antarmuka pengguna yang ramah pengguna dan pahami elemen-elemen kunci \nseperti menu utama, toolbar, dan panel navigasi.\n\n4. Manajemen Barang\n\n4.1 Menambahkan Barang Baru\n\nPelajari cara menambahkan barang baru ke dalam inventaris, termasuk informasi yang \nharus dimasukkan seperti nama barang, jumlah, dan kategori.\n\n4.2 Mengedit dan Menghapus Barang\n\nPahami cara mengubah atau menghapus entri barang yang sudah ada.\n\n5. Melacak Persediaan\n\n5.1 Memeriksa Tingkat Persediaan\n\nGunakan fitur pencarian atau tampilan laporan untuk memeriksa tingkat persediaan\nbarang secara keseluruhan.\n\n5.2 Pencatatan Barang Masuk dan Keluar\n\nCatat barang masuk dan keluar dari inventaris dengan mengikuti prosedur yang telah ditentukan.\n\n6. Laporan Inventaris\n\n6.1 Membuat Laporan\n\nGunakan alat pembuatan laporan untuk menghasilkan laporan inventaris yang dapat dicetak \natau disimpan.\n7. Dukungan Pelanggan\n\n7.1 Layanan Dukungan\n\nTemukan informasi kontak dan panduan langkah-demi-langkah untuk mendapatkan \ndukungan teknis jika diperlukan.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHeaderLayout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(btnKeluarIsianPelanggan))
                    .addGroup(panelHeaderLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(labelNama, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(135, Short.MAX_VALUE))
            .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(labelNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 459, Short.MAX_VALUE)
                .addComponent(btnKeluarIsianPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHeaderLayout.createSequentialGroup()
                    .addContainerGap(51, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(79, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        getAccessibleContext().setAccessibleParent(jPanel1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMouseDragged
      
    }//GEN-LAST:event_panelHeaderMouseDragged

    private void panelHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMousePressed
      
    }//GEN-LAST:event_panelHeaderMousePressed

    private void btnKeluarIsianPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarIsianPelangganActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarIsianPelangganActionPerformed

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
            java.util.logging.Logger.getLogger(ViewBantuanPanduan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewBantuanPanduan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewBantuanPanduan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewBantuanPanduan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ViewBantuanPanduan dialog = new ViewBantuanPanduan(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluarIsianPelanggan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelNama;
    private javax.swing.JPanel panelHeader;
    // End of variables declaration//GEN-END:variables
}
