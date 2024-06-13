/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formGudang;

import _cetak.TableToPDF;
import _cetak.TableToExcel;
import formProduk.FormIsianProduk;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Asus
 */
public class ViewMasterGudang extends javax.swing.JInternalFrame {

    /**
     * Creates new form ViewGudang
     */
    
    private FormIsianGudang isianGudang;
    private String taskFormIsianGudang = "CREATE";
    private TableToPDF templateCetakan;
    private TableToExcel templateCetakanExcel;
    
    
    public ViewMasterGudang() {
        isianGudang = new FormIsianGudang();
        templateCetakan = new TableToPDF();
        templateCetakanExcel = new TableToExcel();
        initComponents();
        tampilDataKategoriList("");
    }
    
    private static String generateDynamicFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return now.format(formatter);
    }
    
    public void hideColumnsByName(JTable table, String... columnNames) {
        for (String columnName : columnNames) {
            int columnIndexToHide = -1;

            for (int i = 0; i < table.getColumnCount(); i++) {
                // Case-insensitive comparison
                if (table.getColumnName(i).equalsIgnoreCase(columnName)) {
                    columnIndexToHide = i;
                    break;
                }
            }

            if (columnIndexToHide != -1) {
                // Set the width of the column to 0 to hide it
                table.getColumnModel().getColumn(columnIndexToHide).setMinWidth(0);
                table.getColumnModel().getColumn(columnIndexToHide).setMaxWidth(0);
                table.getColumnModel().getColumn(columnIndexToHide).setWidth(0);
            } else {
                System.out.println("Column not found: " + columnName);
            }
        }
    }
    
    public void tampilDataKategoriList(String cariData) {
        DefaultTableModel listTableKategori = new DefaultTableModel();
        listTableKategori.addColumn("Gudang Id");
        listTableKategori.addColumn("Gudang Nama");
        listTableKategori.addColumn("Gudang Alamat");
        listTableKategori.addColumn("Gudang Status");

    try {
        java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
        String sql;

        if (!cariData.isEmpty()) {
            sql = "CALL searchGudang(?)";
            
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + cariData.toLowerCase() + "%");
            //pst.setString(2, "%" + cariData.toLowerCase() + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                listTableKategori.addRow(new Object[]{
                    rs.getString("gudang_id"),
                    rs.getString("gudang_nama"),
                    rs.getString("gudang_alamat"),
                    rs.getString("gudang_status")
                });
            }
        } else {
            // If no search text, retrieve all records
            sql = "SELECT * FROM gudang WHERE gudang_status = 'Aktif'";
            
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                listTableKategori.addRow(new Object[]{
                    rs.getString("gudang_id"),
                    rs.getString("gudang_nama"),
                    rs.getString("gudang_alamat"),
                    rs.getString("gudang_status")
                });
            }
        }

        tabelMasterKategori.setModel(new DefaultTableModel());
        tabelMasterKategori.setModel(listTableKategori);
        hideColumnsByName(tabelMasterKategori, "Gudang Id");
    } catch (SQLException e) {
        // Handle the exception
        e.printStackTrace();  // or log the exception
    } catch (Exception e) {
        // handle exceptions, at least log them
        e.printStackTrace();
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

        panelGudang = new javax.swing.JPanel();
        Tambah = new javax.swing.JButton();
        btnUbahMasterGudang = new javax.swing.JButton();
        btnCetakMasterGudang = new javax.swing.JButton();
        btnCetakExel = new javax.swing.JButton();
        Pencarian = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        cariTabelGudangField = new javax.swing.JTextField();
        btnRefreshGudang = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelMasterKategori = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Master Gudang");

        panelGudang.setBackground(new java.awt.Color(0, 102, 102));

        Tambah.setBackground(new java.awt.Color(0, 0, 102));
        Tambah.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Tambah.setForeground(new java.awt.Color(255, 255, 255));
        Tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/03.png"))); // NOI18N
        Tambah.setText("Tambah Gudang");
        Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahActionPerformed(evt);
            }
        });

        btnUbahMasterGudang.setBackground(new java.awt.Color(0, 0, 102));
        btnUbahMasterGudang.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnUbahMasterGudang.setForeground(new java.awt.Color(255, 255, 255));
        btnUbahMasterGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/19_edit.png"))); // NOI18N
        btnUbahMasterGudang.setText("Ubah Gudang");
        btnUbahMasterGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahMasterGudangActionPerformed(evt);
            }
        });
        btnUbahMasterGudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUbahMasterGudangKeyPressed(evt);
            }
        });

        btnCetakMasterGudang.setBackground(new java.awt.Color(0, 0, 102));
        btnCetakMasterGudang.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCetakMasterGudang.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakMasterGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        btnCetakMasterGudang.setText("Cetak");
        btnCetakMasterGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakMasterGudangActionPerformed(evt);
            }
        });

        btnCetakExel.setBackground(new java.awt.Color(0, 0, 102));
        btnCetakExel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCetakExel.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakExel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        btnCetakExel.setText("Cetak Exel");
        btnCetakExel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakExelActionPerformed(evt);
            }
        });

        Pencarian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Pencarian.setForeground(new java.awt.Color(255, 255, 255));
        Pencarian.setText("Cari");

        jComboBox1.setBackground(new java.awt.Color(102, 102, 0));
        jComboBox1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilihan Anda", "Gudang Nama", "Gudang Alamat" }));

        cariTabelGudangField.setBackground(new java.awt.Color(51, 51, 51));
        cariTabelGudangField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cariTabelGudangField.setForeground(new java.awt.Color(255, 255, 255));
        cariTabelGudangField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariTabelGudangFieldActionPerformed(evt);
            }
        });
        cariTabelGudangField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cariTabelGudangFieldKeyPressed(evt);
            }
        });

        btnRefreshGudang.setBackground(new java.awt.Color(0, 0, 102));
        btnRefreshGudang.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRefreshGudang.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/22.png"))); // NOI18N
        btnRefreshGudang.setText("Refresh");
        btnRefreshGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshGudangActionPerformed(evt);
            }
        });

        tabelMasterKategori.setBackground(new java.awt.Color(51, 51, 51));
        tabelMasterKategori.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tabelMasterKategori.setForeground(new java.awt.Color(255, 255, 255));
        tabelMasterKategori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelMasterKategori);

        javax.swing.GroupLayout panelGudangLayout = new javax.swing.GroupLayout(panelGudang);
        panelGudang.setLayout(panelGudangLayout);
        panelGudangLayout.setHorizontalGroup(
            panelGudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGudangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panelGudangLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(Pencarian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cariTabelGudangField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefreshGudang, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUbahMasterGudang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCetakMasterGudang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCetakExel)
                        .addGap(0, 73, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelGudangLayout.setVerticalGroup(
            panelGudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGudangLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelGudangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cariTabelGudangField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefreshGudang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbahMasterGudang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetakMasterGudang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetakExel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGudang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGudang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahActionPerformed
        // TODO add your handling code here:
        if( ! ( isianGudang.isVisible() ) ) { // Memeriksa apakah menuProduk sedang tidak tampil
            isianGudang.refreshFormIsianGudang();
            Component parentComponent = null; // Replace with the actual component reference
            isianGudang.setLocationRelativeTo(parentComponent);
            //isianGudang.refreshFormIsian();
            isianGudang.setParentisianGudang(this);
            isianGudang.setVisible(true);
        }
        else {
            isianGudang.requestFocusInWindow();
            isianGudang.setFocusable(true);
            JOptionPane.showMessageDialog(this, "Form Tambah produk Sudah Terbuka, Silahkan Lengkapi data dahulu.");
        }
    }//GEN-LAST:event_TambahActionPerformed

    private void btnUbahMasterGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahMasterGudangActionPerformed
        // TODO add your handling code here:
        int selectedRowIndex = tabelMasterKategori.getSelectedRow();

        if (selectedRowIndex == -1) {
            // No row is selected, show a message
            JOptionPane.showMessageDialog(this, "Tidak Ada Data Yang Dipilih, Mohon Cek Kembali.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            setFormUpdateMasterGudang();
        }
    }//GEN-LAST:event_btnUbahMasterGudangActionPerformed

    private void btnUbahMasterGudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUbahMasterGudangKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnUbahMasterGudangKeyPressed

    private void btnCetakMasterGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakMasterGudangActionPerformed
        // TODO add your handling code here:
        templateCetakan.exportTableToPDF(
            tabelMasterKategori,
            "cetak/gudang/gudang"+generateDynamicFileName()+".pdf",
            "Daftar Master Gudang"
        );
    }//GEN-LAST:event_btnCetakMasterGudangActionPerformed

    private void btnCetakExelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakExelActionPerformed
        // TODO add your handling code here:
        templateCetakanExcel.exportToExcel(
            tabelMasterKategori,
            "cetak/gudang/gudang"+generateDynamicFileName()+".xls",
            "Daftar Master Gudang"
        );
    }//GEN-LAST:event_btnCetakExelActionPerformed

    private void cariTabelGudangFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariTabelGudangFieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cariTabelGudangFieldActionPerformed

    private void cariTabelGudangFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariTabelGudangFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String enteredText = cariTabelGudangField.getText();
            tampilDataKategoriList(enteredText);
        }
    }//GEN-LAST:event_cariTabelGudangFieldKeyPressed

    private void btnRefreshGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshGudangActionPerformed
        // TODO add your handling code here:
        cariTabelGudangField.setText("");
        tampilDataKategoriList("");
    }//GEN-LAST:event_btnRefreshGudangActionPerformed

    //Form Update Master Gudang
    public void setFormUpdateMasterGudang(){
        int selectedRowIndex = tabelMasterKategori.getSelectedRow();
         
        // A row is selected, retrieve data and perform the update
        String gudang_id = (String) tabelMasterKategori.getValueAt(selectedRowIndex, 0);
        String gudang_nama = (String) tabelMasterKategori.getValueAt(selectedRowIndex, 1);
        String gudang_alamat = (String) tabelMasterKategori.getValueAt(selectedRowIndex, 2);
        String gudang_status = (String) tabelMasterKategori.getValueAt(selectedRowIndex, 3);
        
        Component parentComponent = null; // Replace with the actual component reference
        
        isianGudang.setLocationRelativeTo(parentComponent);
        isianGudang.setDataUpdateFormIsianGudang(gudang_id, gudang_nama, gudang_alamat, gudang_status);
        isianGudang.setParentisianGudang(this);
        isianGudang.setVisible(true);
    }
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
            java.util.logging.Logger.getLogger(ViewMasterGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewMasterGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewMasterGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewMasterGudang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewMasterGudang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Pencarian;
    private javax.swing.JButton Tambah;
    private javax.swing.JButton btnCetakExel;
    private javax.swing.JButton btnCetakMasterGudang;
    private javax.swing.JButton btnRefreshGudang;
    private javax.swing.JButton btnUbahMasterGudang;
    private javax.swing.JTextField cariTabelGudangField;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelGudang;
    private javax.swing.JTable tabelMasterKategori;
    // End of variables declaration//GEN-END:variables
}
