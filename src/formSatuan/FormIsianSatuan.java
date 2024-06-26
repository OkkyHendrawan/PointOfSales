/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formSatuan;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.CallableStatement;
import javax.swing.ImageIcon;
/**
 *
 * @author okkyh
 */
public class FormIsianSatuan extends javax.swing.JFrame {

    /**
     * Creates new form formIsianSatuan
     */
    Map<String, Integer> kategoriMap = new HashMap<>();
    private String taskFormIsianSatuan = "CREATE";
    private int idUpdateFormIsianSatuan = 0;
    private ViewMasterSatuan parentIsianSatuan;

    public FormIsianSatuan() {
        initComponents();
        ImageIcon icon = new ImageIcon("src/_asset/78.png");
        setIconImage(icon.getImage());
        refreshFormIsian();
        satuanKodeField.setText("");
        satuanNamaField.setText("");
        satuanStatusField.setSelectedItem("Aktif");
    }

protected void refreshFormIsian(){
        this.taskFormIsianSatuan = "CREATE";
        this.idUpdateFormIsianSatuan = 0;
        satuanKodeField.setText("");
        satuanNamaField.setText("");
        satuanStatusField.setSelectedItem("Aktif");
    }

protected void setDataUpdateFormIsianSatuan(String satuan_id, String satuan_kode, String satuan_nama, String satuan_status){      
        this.taskFormIsianSatuan = "UPDATE";
        this.idUpdateFormIsianSatuan = Integer.parseInt(satuan_id);
        // Set Store Data dari Database
        //System.out.print(kategoriNamaProduk);
            
        satuanKodeField.setText(satuan_kode);
        satuanNamaField.setText(satuan_nama);
        satuanStatusField.setSelectedItem(satuan_status);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFormIsianSatuan = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        satuanKodeField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        satuanNamaField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        satuanStatusField = new javax.swing.JComboBox<>();
        btnSimpanSatuanFormIsian = new javax.swing.JButton();
        btnKeluarSatuanFormIsian = new javax.swing.JButton();

        panelFormIsianSatuan.setBackground(new java.awt.Color(15, 60, 89));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Satuan Kode");

        satuanKodeField.setBackground(new java.awt.Color(51, 51, 51));
        satuanKodeField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        satuanKodeField.setForeground(new java.awt.Color(255, 255, 255));
        satuanKodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                satuanKodeFieldActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Satuan Nama");

        satuanNamaField.setBackground(new java.awt.Color(51, 51, 51));
        satuanNamaField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        satuanNamaField.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Satuan Status");

        satuanStatusField.setBackground(new java.awt.Color(51, 51, 51));
        satuanStatusField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        satuanStatusField.setForeground(new java.awt.Color(255, 255, 255));
        satuanStatusField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Tidak Aktif" }));
        satuanStatusField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                satuanStatusFieldActionPerformed(evt);
            }
        });

        btnSimpanSatuanFormIsian.setBackground(new java.awt.Color(0, 102, 51));
        btnSimpanSatuanFormIsian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSimpanSatuanFormIsian.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanSatuanFormIsian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/46_save.png"))); // NOI18N
        btnSimpanSatuanFormIsian.setText("Simpan");
        btnSimpanSatuanFormIsian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanSatuanFormIsianActionPerformed(evt);
            }
        });

        btnKeluarSatuanFormIsian.setBackground(new java.awt.Color(0, 153, 153));
        btnKeluarSatuanFormIsian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnKeluarSatuanFormIsian.setForeground(new java.awt.Color(255, 255, 255));
        btnKeluarSatuanFormIsian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/01.png"))); // NOI18N
        btnKeluarSatuanFormIsian.setText("Keluar");
        btnKeluarSatuanFormIsian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarSatuanFormIsianActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormIsianSatuanLayout = new javax.swing.GroupLayout(panelFormIsianSatuan);
        panelFormIsianSatuan.setLayout(panelFormIsianSatuanLayout);
        panelFormIsianSatuanLayout.setHorizontalGroup(
            panelFormIsianSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormIsianSatuanLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelFormIsianSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(27, 27, 27)
                .addGroup(panelFormIsianSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormIsianSatuanLayout.createSequentialGroup()
                        .addComponent(satuanStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelFormIsianSatuanLayout.createSequentialGroup()
                        .addGroup(panelFormIsianSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(satuanNamaField, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(satuanKodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormIsianSatuanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpanSatuanFormIsian)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnKeluarSatuanFormIsian)
                .addGap(108, 108, 108))
        );
        panelFormIsianSatuanLayout.setVerticalGroup(
            panelFormIsianSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormIsianSatuanLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(panelFormIsianSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(satuanKodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(panelFormIsianSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(satuanNamaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(panelFormIsianSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(satuanStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(panelFormIsianSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanSatuanFormIsian)
                    .addComponent(btnKeluarSatuanFormIsian))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFormIsianSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFormIsianSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void satuanKodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_satuanKodeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_satuanKodeFieldActionPerformed

    private void satuanStatusFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_satuanStatusFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_satuanStatusFieldActionPerformed

    private void btnSimpanSatuanFormIsianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanSatuanFormIsianActionPerformed
        if(taskFormIsianSatuan.equals("UPDATE")){
            updateDataMasterSatuan();
        }else{
            insertDataMasterSatuan();
        }
    }//GEN-LAST:event_btnSimpanSatuanFormIsianActionPerformed

    private void btnKeluarSatuanFormIsianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarSatuanFormIsianActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnKeluarSatuanFormIsianActionPerformed
        
    private void updateDataMasterSatuan() {
        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql = "{CALL UpdateProdukSatuan(?, ?, ?, ?)}";
            try (CallableStatement cst = conn.prepareCall(sql)) {
                cst.setString(1, satuanKodeField.getText());
                cst.setString(2, satuanNamaField.getText());
                cst.setString(3, (String) satuanStatusField.getSelectedItem());
                cst.setInt(4, idUpdateFormIsianSatuan);

                int rowsAffected = cst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Satuan Berhasil Diupdate!");

                    // Call the method to refresh the data after updating
                    refreshFormIsian();
                    parentIsianSatuan.tampilDataList("");
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Satuan Gagal Diupdate!");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Satuan Gagal Diupdate! Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Satuan Gagal Diupdate! Error: " + e.getMessage());
            e.printStackTrace();
        }
    
    }
        public void insertDataMasterSatuan(){
        String satuanKodeValue = satuanKodeField.getText();
        String satuanNamaValue = satuanNamaField.getText();
        String satuanStatusValue = (String) satuanStatusField.getSelectedItem();

        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql = "{CALL InsertProdukSatuan(?, ?, ?)}";
            try (CallableStatement cst = conn.prepareCall(sql)) {
                cst.setString(1, satuanKodeValue);
                cst.setString(2, satuanNamaValue);
                cst.setString(3, satuanStatusValue);

                cst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Satuan Berhasil Disimpan!");
                parentIsianSatuan.tampilDataList("");
                this.setVisible(false);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Satuan Gagal Disimpan! Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Satuan Gagal Disimpan! Error: " + e.getMessage());
            e.printStackTrace();
        }
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
            java.util.logging.Logger.getLogger(FormIsianSatuan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormIsianSatuan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormIsianSatuan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormIsianSatuan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormIsianSatuan().setVisible(true);
            }
        });
    }
public void setParentisianSatuan(ViewMasterSatuan parentForm ){
        this.parentIsianSatuan = parentForm;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluarSatuanFormIsian;
    private javax.swing.JButton btnSimpanSatuanFormIsian;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel panelFormIsianSatuan;
    private javax.swing.JTextField satuanKodeField;
    private javax.swing.JTextField satuanNamaField;
    private javax.swing.JComboBox<String> satuanStatusField;
    // End of variables declaration//GEN-END:variables

}
