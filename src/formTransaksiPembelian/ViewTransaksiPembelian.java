/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package formTransaksiPembelian;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author HP
 */
public class ViewTransaksiPembelian extends javax.swing.JInternalFrame {

    /**
     * Creates new form ViewTransaksiPembelian
     */
    private FormIsianTransaksiPembelian isianTransaksiPembelian;
    
    public ViewTransaksiPembelian() {
        isianTransaksiPembelian = new FormIsianTransaksiPembelian();
        initComponents();
              
        tampilDataPembelianList("");
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
    
    public void tampilDataPembelianList(String cariData) {
        
        DefaultTableModel listTableTransaksiPembelian = new DefaultTableModel();
        listTableTransaksiPembelian.addColumn("Pembelian Id");
        listTableTransaksiPembelian.addColumn("Pemasok Id");
        listTableTransaksiPembelian.addColumn("Tanggal");
        listTableTransaksiPembelian.addColumn("Nomor Faktur");
        listTableTransaksiPembelian.addColumn("Pemasok");
        listTableTransaksiPembelian.addColumn("Total Rp");
        
        try {
            Connection con = (Connection) database.Koneksi.koneksiDB();
            String sql;
            
            if (!(cariData.isEmpty())) {
                sql = "call `SearchTransaksiPembelian`('"+cariData+"')";
            } else {
                sql = "call `SearchTransaksiPembelian`('');";
            }
            
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                listTableTransaksiPembelian.addRow(new Object[] {
                    rs.getString("pembelian_id"),
                    rs.getString("pemasok_id"),
                    rs.getString("pembelian_tanggal"),
                    rs.getString("pembelian_nomor_faktur"),
                    rs.getString("pemasok_nama"),
                    rs.getString("pembelian_total_rp")
                });              
            }
            
            tabelGridPembelian.setModel(new DefaultTableModel());
            
            tabelGridPembelian.setModel(listTableTransaksiPembelian);
            hideColumnsByName(tabelGridPembelian, "Pembelian Id", "Pemasok Id");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setFormUpdatePembelian() {
        int selectedRowIndex = tabelGridPembelian.getSelectedRow();
        
        String pembelianId = (String) tabelGridPembelian.getValueAt(selectedRowIndex, 0);
        String pembelianTanggal = (String) tabelGridPembelian.getValueAt(selectedRowIndex, 2);
        String pembelianNomorFaktur = (String) tabelGridPembelian.getValueAt(selectedRowIndex, 3);
        String pemasokNama = (String) tabelGridPembelian.getValueAt(selectedRowIndex, 4);
        String pembelianTotalRp = (String) tabelGridPembelian.getValueAt(selectedRowIndex, 5);
        
        Component parentComponent = null;
        isianTransaksiPembelian.setLocationRelativeTo(parentComponent);
        isianTransaksiPembelian.setParentForm(this);
        isianTransaksiPembelian.setUpdateDetailPembelian(
                pembelianId, pembelianTanggal, pembelianNomorFaktur, pemasokNama, pembelianTotalRp
        );
        isianTransaksiPembelian.setVisible(true);        
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTransaksiPembelian = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelGridPembelian = new javax.swing.JTable();
        btnTambahPembelian = new javax.swing.JButton();
        btnUbahPembelian = new javax.swing.JButton();
        btnRefreshPembelian = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cariPembelianField = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Transaksi Pembelian");

        panelTransaksiPembelian.setBackground(new java.awt.Color(0, 102, 51));

        tabelGridPembelian.setBackground(new java.awt.Color(51, 51, 51));
        tabelGridPembelian.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tabelGridPembelian.setForeground(new java.awt.Color(255, 255, 255));
        tabelGridPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane1.setViewportView(tabelGridPembelian);

        btnTambahPembelian.setBackground(new java.awt.Color(0, 0, 153));
        btnTambahPembelian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnTambahPembelian.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/03.png"))); // NOI18N
        btnTambahPembelian.setText("Tambah Transaksi Pembelian");
        btnTambahPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPembelianActionPerformed(evt);
            }
        });

        btnUbahPembelian.setBackground(new java.awt.Color(0, 0, 153));
        btnUbahPembelian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnUbahPembelian.setForeground(new java.awt.Color(255, 255, 255));
        btnUbahPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/19_edit.png"))); // NOI18N
        btnUbahPembelian.setText("Ubah Transaksi Pembelian");
        btnUbahPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahPembelianActionPerformed(evt);
            }
        });

        btnRefreshPembelian.setBackground(new java.awt.Color(0, 0, 153));
        btnRefreshPembelian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRefreshPembelian.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/22.png"))); // NOI18N
        btnRefreshPembelian.setText("Refresh");
        btnRefreshPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshPembelianActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pencarian");

        cariPembelianField.setBackground(new java.awt.Color(51, 51, 51));
        cariPembelianField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cariPembelianField.setForeground(new java.awt.Color(255, 255, 255));
        cariPembelianField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cariPembelianFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelTransaksiPembelianLayout = new javax.swing.GroupLayout(panelTransaksiPembelian);
        panelTransaksiPembelian.setLayout(panelTransaksiPembelianLayout);
        panelTransaksiPembelianLayout.setHorizontalGroup(
            panelTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransaksiPembelianLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTransaksiPembelianLayout.createSequentialGroup()
                        .addComponent(btnTambahPembelian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUbahPembelian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefreshPembelian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cariPembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelTransaksiPembelianLayout.setVerticalGroup(
            panelTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransaksiPembelianLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(panelTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cariPembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambahPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUbahPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefreshPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTransaksiPembelian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTransaksiPembelian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPembelianActionPerformed
        // TODO add your handling code here:
        if( ! ( isianTransaksiPembelian.isVisible() ) ) { // Memeriksa apakah menuProduk sedang tidak tampil
            Component parentComponent = null; // Replace with the actual component reference
            isianTransaksiPembelian.setLocationRelativeTo(parentComponent);
            isianTransaksiPembelian.refreshIsianPembelian();
            isianTransaksiPembelian.setVisible(true);
        }
        else {
            isianTransaksiPembelian.requestFocusInWindow();
            isianTransaksiPembelian.setFocusable(true);
            JOptionPane.showMessageDialog(this, "Form Tambah Detail Sudah Terbuka, Silahkan Lengkapi data dahulu.");
        }
    }//GEN-LAST:event_btnTambahPembelianActionPerformed

    private void btnUbahPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahPembelianActionPerformed
        int selectedRowIndex = tabelGridPembelian.getSelectedRow();

        if(selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Tidak Ada Data Yang Dipilih, Mohon Cek Kembali.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }else {
            setFormUpdatePembelian();
        }
    }//GEN-LAST:event_btnUbahPembelianActionPerformed

    private void btnRefreshPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshPembelianActionPerformed
        tampilDataPembelianList("");
    }//GEN-LAST:event_btnRefreshPembelianActionPerformed

    private void cariPembelianFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariPembelianFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String enteredText = cariPembelianField.getText();
            tampilDataPembelianList(enteredText);
        }
    }//GEN-LAST:event_cariPembelianFieldKeyPressed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefreshPembelian;
    private javax.swing.JButton btnTambahPembelian;
    private javax.swing.JButton btnUbahPembelian;
    private javax.swing.JTextField cariPembelianField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelTransaksiPembelian;
    private javax.swing.JTable tabelGridPembelian;
    // End of variables declaration//GEN-END:variables
}