/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package formProduk;

import _appMain.FormLogin;
import _cetak.TableToExcel;
import _cetak.TableToPDF;
import java.awt.Component;
import java.sql.*;
import java.text.Format;
import javax.swing.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author HP
 */
public class ViewMasterProduk extends javax.swing.JInternalFrame {

    /**
     * Creates new form ViewMasterProduk
     */
    
    private FormIsianProduk isianProduk;
    private TableToPDF templatePdf;
    private TableToExcel templateExcel;
                
    public ViewMasterProduk() {
        isianProduk = new FormIsianProduk();
        templatePdf = new TableToPDF();
        templateExcel = new TableToExcel();
        
        initComponents();
        tampilDataList("");
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
    
    private void centerAlignColumn(JTable table, String columnName, int alignment) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(alignment);

        table.getColumnModel().getColumn(table.getColumn(columnName).getModelIndex())
                .setCellRenderer(centerRenderer);
    }
    
    public void tampilDataList(String cariData) {
        DefaultTableModel listTableMasterProduk = new DefaultTableModel();
        listTableMasterProduk.addColumn("Produk Id");
        listTableMasterProduk.addColumn("Kategori Id");
        listTableMasterProduk.addColumn("Produk Kode");
        listTableMasterProduk.addColumn("Produk Nama");
        listTableMasterProduk.addColumn("Produk Kategori");
        listTableMasterProduk.addColumn("Produk Harga Satuan");
        listTableMasterProduk.addColumn("Produk Status");
        listTableMasterProduk.addColumn("Produk Keterangan");

        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql;
            
            if (!cariData.isEmpty()) {
                sql = "CALL SearchProduk('" + cariData + "')";
            } else {
                sql = "CALL SearchProduk('')";
            }
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                listTableMasterProduk.addRow(new Object[]{
                    rs.getString("produk_id"),
                    rs.getString("kategori_id"),
                    rs.getString("produk_kode"),
                    rs.getString("produk_nama"),
                    rs.getString("kategori_nama"),
                    rs.getString("harga_display"),
                    rs.getString("produk_status"),
                    rs.getString("produk_keterangan"),
                });
            }
            
            produkGridTable.setModel(new DefaultTableModel());

            produkGridTable.setModel(listTableMasterProduk);
            centerAlignColumn(produkGridTable, "Produk Status", SwingConstants.CENTER);
            hideColumnsByName(produkGridTable, "Produk ID", "Kategori ID");
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

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        btnTambahProduk = new javax.swing.JButton();
        btnUbahProduk = new javax.swing.JButton();
        btnCetakProduk = new javax.swing.JButton();
        btnCetakXls = new javax.swing.JButton();
        pencarianLabel = new javax.swing.JLabel();
        cariProdukField = new javax.swing.JTextField();
        btnRefreshProduk = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        produkGridTable = new javax.swing.JTable();

        jMenuItem1.setText("jMenuItem1");

        setBorder(null);
        setClosable(true);
        setTitle("Master Produk");

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        btnTambahProduk.setBackground(new java.awt.Color(0, 0, 102));
        btnTambahProduk.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnTambahProduk.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/03.png"))); // NOI18N
        btnTambahProduk.setText("Tambah Produk");
        btnTambahProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahProdukActionPerformed(evt);
            }
        });

        btnUbahProduk.setBackground(new java.awt.Color(0, 0, 102));
        btnUbahProduk.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnUbahProduk.setForeground(new java.awt.Color(255, 255, 255));
        btnUbahProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/19_edit.png"))); // NOI18N
        btnUbahProduk.setText("Ubah Produk");
        btnUbahProduk.setPreferredSize(new java.awt.Dimension(77, 27));
        btnUbahProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahProdukActionPerformed(evt);
            }
        });

        btnCetakProduk.setBackground(new java.awt.Color(0, 0, 102));
        btnCetakProduk.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCetakProduk.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        btnCetakProduk.setText("Cetak");
        btnCetakProduk.setPreferredSize(new java.awt.Dimension(77, 27));
        btnCetakProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakProdukActionPerformed(evt);
            }
        });

        btnCetakXls.setBackground(new java.awt.Color(0, 0, 102));
        btnCetakXls.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCetakXls.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakXls.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        btnCetakXls.setText("Cetak XLS");
        btnCetakXls.setPreferredSize(new java.awt.Dimension(77, 27));
        btnCetakXls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakXlsActionPerformed(evt);
            }
        });

        pencarianLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        pencarianLabel.setForeground(new java.awt.Color(255, 255, 255));
        pencarianLabel.setText("Pencarian");

        cariProdukField.setBackground(new java.awt.Color(51, 51, 51));
        cariProdukField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cariProdukField.setForeground(new java.awt.Color(255, 255, 255));
        cariProdukField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariProdukFieldActionPerformed(evt);
            }
        });
        cariProdukField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cariProdukFieldKeyPressed(evt);
            }
        });

        btnRefreshProduk.setBackground(new java.awt.Color(0, 0, 102));
        btnRefreshProduk.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRefreshProduk.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/22.png"))); // NOI18N
        btnRefreshProduk.setText("Refresh");
        btnRefreshProduk.setPreferredSize(new java.awt.Dimension(77, 27));
        btnRefreshProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshProdukActionPerformed(evt);
            }
        });

        produkGridTable.setBackground(new java.awt.Color(51, 51, 51));
        produkGridTable.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        produkGridTable.setForeground(new java.awt.Color(255, 255, 255));
        produkGridTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(produkGridTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addComponent(btnTambahProduk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUbahProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pencarianLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cariProdukField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefreshProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCetakProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCetakXls, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUbahProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cariProdukField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pencarianLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefreshProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCetakXls, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCetakProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTambahProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahProdukActionPerformed
        if( ! ( isianProduk.isVisible() ) ) { // Memeriksa apakah menuProduk sedang tidak tampil
            Component parentComponent = null; // Replace with the actual component reference
            isianProduk.setLocationRelativeTo(parentComponent);
            isianProduk.refreshFormIsian();
            isianProduk.setIsianParentForm(this);
            isianProduk.setVisible(true);
        }
        else {
            isianProduk.requestFocusInWindow();
            isianProduk.setFocusable(true);
            JOptionPane.showMessageDialog(this, "Form Tambah produk Sudah Terbuka, Silahkan Lengkapi data dahulu.");
        }
    }//GEN-LAST:event_btnTambahProdukActionPerformed

    private void btnUbahProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahProdukActionPerformed
        // TODO add your handling code here:
        int selectedRowIndex = produkGridTable.getSelectedRow();

        if (selectedRowIndex == -1) {
            // No row is selected, show a message
            JOptionPane.showMessageDialog(this, "Tidak Ada Data Yang Dipilih, Mohon Cek Kembali.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            setFormUpdateProduk();
        }
    }//GEN-LAST:event_btnUbahProdukActionPerformed

    private void btnCetakProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakProdukActionPerformed
        // TODO add your handling code here:
        templatePdf.exportTableToPDF(
            produkGridTable,
            "cetak/produk/produk"+generateDynamicFileName()+".pdf",
            "Daftar Produk"
        );
    }//GEN-LAST:event_btnCetakProdukActionPerformed

    private void btnCetakXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakXlsActionPerformed
        // TODO add your handling code here:
        templateExcel.exportToExcel(
            produkGridTable,
            "cetak/produk/produk"+generateDynamicFileName()+".xls",
            "Daftar Produk"
        );
    }//GEN-LAST:event_btnCetakXlsActionPerformed

    private void cariProdukFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariProdukFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariProdukFieldActionPerformed

    private void cariProdukFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariProdukFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // This block will be executed when the Enter key is pressed
            String enteredText = cariProdukField.getText();
            // Perform your desired action with the entered text
            // For example, you might want to process the entered SKU
            tampilDataList(enteredText);
        }
    }//GEN-LAST:event_cariProdukFieldKeyPressed

    private void btnRefreshProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshProdukActionPerformed
        // TODO add your handling code here:
        cariProdukField.setText("");
        tampilDataList("");
    }//GEN-LAST:event_btnRefreshProdukActionPerformed
    
    public void setFormUpdateProduk(){
        int selectedRowIndex = produkGridTable.getSelectedRow();
         
        // A row is selected, retrieve data and perform the update
        String idProduk = (String) produkGridTable.getValueAt(selectedRowIndex, 0);
        String kategoriIdProduk = (String) produkGridTable.getValueAt(selectedRowIndex, 1);
        String kodeProduk = (String) produkGridTable.getValueAt(selectedRowIndex, 2);
        String namaProduk = (String) produkGridTable.getValueAt(selectedRowIndex, 3);
        String kategoriNamaProduk = (String) produkGridTable.getValueAt(selectedRowIndex, 4);
        String hargaSatuan = (String) produkGridTable.getValueAt(selectedRowIndex, 5);
        String statusProduk = (String) produkGridTable.getValueAt(selectedRowIndex, 6);
        String keteranganProduk = (String) produkGridTable.getValueAt(selectedRowIndex, 7);
        
        // System.out.println(idProduk);
        // System.out.println(kategoriIdProduk);

        Component parentComponent = null; // Replace with the actual component reference
        isianProduk.setLocationRelativeTo(parentComponent);
        isianProduk.setDataUpdateFormIsian(idProduk, kodeProduk, namaProduk, kategoriNamaProduk, statusProduk, keteranganProduk);
        isianProduk.setIsianParentForm(this);
        isianProduk.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetakProduk;
    private javax.swing.JButton btnCetakXls;
    private javax.swing.JButton btnRefreshProduk;
    private javax.swing.JButton btnTambahProduk;
    private javax.swing.JButton btnUbahProduk;
    private javax.swing.JTextField cariProdukField;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel pencarianLabel;
    private javax.swing.JTable produkGridTable;
    // End of variables declaration//GEN-END:variables
}
