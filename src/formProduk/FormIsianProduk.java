/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package formProduk;

import java.awt.Component;
import java.sql.*;
import java.text.Format;
import javax.swing.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 *
 * @author HP
 */
public class FormIsianProduk extends javax.swing.JFrame {

    /**
     * Creates new form FormIsianProduk
     */
    
    Map<String, Integer> kategoriMap = new HashMap<>();
    private ViewMasterProduk parentIsianForm;
    private FormIsianProdukDetail isianProdukDetail;
    private String taskFormIsian = "CREATE";
    private int idUpdateFormIsian = 0;

    public FormIsianProduk() {
        isianProdukDetail = new FormIsianProdukDetail();
        initComponents();
        ImageIcon icon = new ImageIcon("src/_asset/78.png");
        setIconImage(icon.getImage());
    }
    
    public void setIsianParentForm(ViewMasterProduk parentForm) {
        this.parentIsianForm = parentForm;
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
    
    protected void refreshFormIsian(){
        this.taskFormIsian = "CREATE";
        this.idUpdateFormIsian = 0;
        hideColumnsByName(tableProdukDetail, "Pdetail Id", "Pdetail Produk Id", "Satuan ID");
        produkKategoriField.setSelectedItem(1);
        produkNamaField.setText("");
        produkKeteranganField.setText("");
        produkStatusField.setSelectedItem("Aktif");
        DefaultTableModel model = (DefaultTableModel) tableProdukDetail.getModel();
        model.setRowCount(0);
        tableProdukDetail.repaint();
        
        // Set Store Data dari Database
        storeKategoriProduk();
        generateKodeProdukOtomatis();
    }
    
    protected void setDataUpdateFormIsian(String idProduk, String kodeProduk, String namaProduk, String kategoriNamaProduk, String statusProduk, String keteranganProduk){      
        this.taskFormIsian = "UPDATE";
        this.idUpdateFormIsian = Integer.parseInt(idProduk);
        // Set Store Data dari Database
        storeKategoriProduk();
        // System.out.print(kodeProduk);
        
        produkKategoriField.setSelectedItem(kategoriNamaProduk);
        produkKodeField.setText(kodeProduk);
        produkNamaField.setText(namaProduk);
        produkKeteranganField.setText(keteranganProduk);
        produkStatusField.setSelectedItem(statusProduk);
        getProdukDetailById(idProduk);
    }
    
    protected void getProdukDetailById(String idProduk){
        DefaultTableModel listDataTableProdukDetailUpdate = new DefaultTableModel();
        listDataTableProdukDetailUpdate.addColumn("Pdetail Id");
        listDataTableProdukDetailUpdate.addColumn("Pdetail Produk Id");
        listDataTableProdukDetailUpdate.addColumn("Satuan Id");
        listDataTableProdukDetailUpdate.addColumn("Satuan Kode");
        listDataTableProdukDetailUpdate.addColumn("Satuan Harga");
        listDataTableProdukDetailUpdate.addColumn("Satuan Nilai");
        listDataTableProdukDetailUpdate.addColumn("Satuan Default");
        
        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql;
            sql = "call SearchProdukDetail ('"+idProduk+"')";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            // System.out.print(sql);
            
            while (rs.next()) {
                listDataTableProdukDetailUpdate.addRow(new Object[]{
                    rs.getString("pdetail_id"),
                    rs.getString("pdetail_produk_id"),
                    rs.getInt("pdetail_satuan_id"),
                    rs.getString("satuan_kode"),
                    rs.getString("pdetail_harga"),
                    rs.getString("pdetail_nilai"),
                    rs.getString("pdetail_default")
                });
            }
            
            tableProdukDetail.setModel(new DefaultTableModel());
            tableProdukDetail.setModel(listDataTableProdukDetailUpdate);
            hideColumnsByName(tableProdukDetail, "Pdetail Id", "Pdetail Produk Id", "Satuan ID");  
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

        editorSatuanStatusCellField = new javax.swing.JComboBox<>();
        panelFormIsianProduk = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        produkKategoriField = new javax.swing.JComboBox<>();
        produkKodeField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        produkNamaField = new javax.swing.JTextField();
        produkStatusField = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        produkKeteranganField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProdukDetail = new javax.swing.JTable();
        btnProdukDetailAdd = new javax.swing.JButton();
        btnUbahSatuan = new javax.swing.JButton();
        btnSimpanProdukFormIsian = new javax.swing.JButton();
        btnKeluarProdukFormIsian = new javax.swing.JButton();

        editorSatuanStatusCellField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setTitle("Tambah Produk");

        panelFormIsianProduk.setBackground(new java.awt.Color(15, 60, 89));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Produk Kategori");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Produk Kode");

        produkKategoriField.setBackground(new java.awt.Color(51, 51, 51));
        produkKategoriField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        produkKategoriField.setForeground(new java.awt.Color(255, 255, 255));
        produkKategoriField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        produkKategoriField.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                produkKategoriFieldItemStateChanged(evt);
            }
        });
        produkKategoriField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produkKategoriFieldActionPerformed(evt);
            }
        });

        produkKodeField.setEditable(false);
        produkKodeField.setBackground(new java.awt.Color(51, 51, 51));
        produkKodeField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        produkKodeField.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Produk Nama");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Produk Status");

        produkNamaField.setBackground(new java.awt.Color(51, 51, 51));
        produkNamaField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        produkNamaField.setForeground(new java.awt.Color(255, 255, 255));

        produkStatusField.setBackground(new java.awt.Color(51, 51, 51));
        produkStatusField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        produkStatusField.setForeground(new java.awt.Color(255, 255, 255));
        produkStatusField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Tidak Aktif" }));
        produkStatusField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produkStatusFieldActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Produk Keterangan");

        produkKeteranganField.setBackground(new java.awt.Color(51, 51, 51));
        produkKeteranganField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        produkKeteranganField.setForeground(new java.awt.Color(255, 255, 255));

        tableProdukDetail.setBackground(new java.awt.Color(51, 51, 51));
        tableProdukDetail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tableProdukDetail.setForeground(new java.awt.Color(255, 255, 255));
        tableProdukDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pdetail Id", "Pdetail Produk Id", "Satuan Id", "Satuan Nama", "Satuan Harga", "Satuan Nilai", "Satuan Default"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableProdukDetail);

        btnProdukDetailAdd.setBackground(new java.awt.Color(0, 102, 102));
        btnProdukDetailAdd.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnProdukDetailAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnProdukDetailAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/03.png"))); // NOI18N
        btnProdukDetailAdd.setText("Tambah Satuan");
        btnProdukDetailAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdukDetailAddActionPerformed(evt);
            }
        });

        btnUbahSatuan.setBackground(new java.awt.Color(0, 102, 102));
        btnUbahSatuan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnUbahSatuan.setForeground(new java.awt.Color(255, 255, 255));
        btnUbahSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/19_edit.png"))); // NOI18N
        btnUbahSatuan.setText("Ubah Satuan");
        btnUbahSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahSatuanActionPerformed(evt);
            }
        });

        btnSimpanProdukFormIsian.setBackground(new java.awt.Color(0, 102, 51));
        btnSimpanProdukFormIsian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSimpanProdukFormIsian.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanProdukFormIsian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/46_save.png"))); // NOI18N
        btnSimpanProdukFormIsian.setText("Simpan");
        btnSimpanProdukFormIsian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanProdukFormIsianActionPerformed(evt);
            }
        });

        btnKeluarProdukFormIsian.setBackground(new java.awt.Color(0, 153, 153));
        btnKeluarProdukFormIsian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnKeluarProdukFormIsian.setForeground(new java.awt.Color(255, 255, 255));
        btnKeluarProdukFormIsian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/01.png"))); // NOI18N
        btnKeluarProdukFormIsian.setText("Keluar");
        btnKeluarProdukFormIsian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarProdukFormIsianActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormIsianProdukLayout = new javax.swing.GroupLayout(panelFormIsianProduk);
        panelFormIsianProduk.setLayout(panelFormIsianProdukLayout);
        panelFormIsianProdukLayout.setHorizontalGroup(
            panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormIsianProdukLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(30, 30, 30)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(produkNamaField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                        .addComponent(produkKodeField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(produkStatusField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(produkKeteranganField, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(produkKategoriField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                                .addComponent(btnProdukDetailAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUbahSatuan))))
                    .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSimpanProdukFormIsian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKeluarProdukFormIsian)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panelFormIsianProdukLayout.setVerticalGroup(
            panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(produkKategoriField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProdukDetailAdd)
                    .addComponent(btnUbahSatuan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                        .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(produkKodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(produkNamaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(produkStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(produkKeteranganField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanProdukFormIsian)
                    .addComponent(btnKeluarProdukFormIsian))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFormIsianProduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFormIsianProduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void produkKategoriFieldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_produkKategoriFieldItemStateChanged
        // TODO add your handling code here:
        generateKodeProdukOtomatis();
    }//GEN-LAST:event_produkKategoriFieldItemStateChanged

    private void produkKategoriFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produkKategoriFieldActionPerformed

    }//GEN-LAST:event_produkKategoriFieldActionPerformed

    private void produkStatusFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produkStatusFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_produkStatusFieldActionPerformed

    private void btnProdukDetailAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdukDetailAddActionPerformed
        // TODO add your handling code here:
        if( ! ( isianProdukDetail.isVisible() ) ) { // Memeriksa apakah menuProduk sedang tidak tampil
            Component parentComponent = null; // Replace with the actual component reference
            isianProdukDetail.setLocationRelativeTo(parentComponent);
            isianProdukDetail.setParentForm(this);  // Set the parent form reference
            isianProdukDetail.refreshFormIsianDetail();
            isianProdukDetail.setVisible(true);

        }
        else {
            JOptionPane.showMessageDialog(this, "Form Tambah produk Sudah Terbuka, Silahkan Lengkapi data dahulu.");
        }
    }//GEN-LAST:event_btnProdukDetailAddActionPerformed

    private void btnUbahSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahSatuanActionPerformed
        // TODO add your handling code here:
        int selectedRowIndex = tableProdukDetail.getSelectedRow();

        if (selectedRowIndex == -1) {
            // No row is selected, show a message
            JOptionPane.showMessageDialog(this, "Tidak Ada Data Yang Dipilih, Mohon Cek Kembali.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            setFormUpdateDetailSatuan();
        }
    }//GEN-LAST:event_btnUbahSatuanActionPerformed

    private void btnSimpanProdukFormIsianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanProdukFormIsianActionPerformed
        DefaultTableModel model = (DefaultTableModel) tableProdukDetail.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Data Tidak Bisa Disimpan, Mohon Isi satuan Terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Do not proceed with the save operation
        }

        if(taskFormIsian.equalsIgnoreCase("UPDATE")){
            updateDataMasterProduk();
        }else{
            insertDataMasterProduk();
        }
    }//GEN-LAST:event_btnSimpanProdukFormIsianActionPerformed

    private void btnKeluarProdukFormIsianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarProdukFormIsianActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnKeluarProdukFormIsianActionPerformed
    
    public void storeKategoriProduk() {
        // Assuming you have a database connection 'conn'

        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql = "SELECT kategori_id, kategori_nama FROM produk_kategori";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            List<String> items = new ArrayList<>();  // Initialize the list

            while (rs.next()) {
                int kategoriId = rs.getInt("kategori_id");
                String kategoriNama = rs.getString("kategori_nama");

                // Add the display value to the ComboBox
                items.add(kategoriNama);

                // Add the association to the Map
                kategoriMap.put(kategoriNama, kategoriId);
            }

            // Populate the ComboBox with the items
            produkKategoriField.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));

            // Add a listener to get the selected value and use the Map to get the corresponding ID
            produkKategoriField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedKategoriNama = (String) produkKategoriField.getSelectedItem();
                    int selectedKategoriId = kategoriMap.get(selectedKategoriNama);
                    // Use the selectedKategoriId as needed
                }
            });

            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }
    
    private String getKodeKategoriById(){
        String produkKategoriValue = (String) produkKategoriField.getSelectedItem();
        int kategoriId = 0;
        
        try {
            if (kategoriMap.containsKey(produkKategoriValue)) {
                kategoriId = kategoriMap.get(produkKategoriValue);
            } else {
                JOptionPane.showMessageDialog(this, "Kategori not found in the map: " + produkKategoriValue);
            }
            
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql = "SELECT kategori_kode FROM produk_kategori WHERE kategori_id = "+kategoriId+" ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            String kategoriKode = "XX";
            if (rs.next()) {
                kategoriKode = rs.getString("kategori_kode");
            }
            rs.close();
            pst.close();
            return kategoriKode;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }return null;
    }
    
    // Generate kode sesuai kategori yang dipilih
    public void generateKodeProdukOtomatis() {
        
        String kategoriKodeSelect = getKodeKategoriById();
        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            // Mencari Last Code by data, dibuat otomatis
            String sql = "SELECT CONCAT('"+kategoriKodeSelect+"', LPAD(SUBSTRING(produk_kode, 3) + 1, 3, '0')) AS next_produk_kode" 
                           + " FROM produk" 
                           + " WHERE produk_kode LIKE '%"+kategoriKodeSelect+"%'" 
                           + " ORDER BY produk_id DESC" 
                           + " LIMIT 1";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            String produkKodeValueOtomatis = "XX";
            if (rs.next()) {
                produkKodeValueOtomatis = rs.getString("next_produk_kode");
            }else{
                produkKodeValueOtomatis = kategoriKodeSelect+"001";
            }
            produkKodeField.setText(produkKodeValueOtomatis);
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }
    
   
    private void updateDataMasterProduk(){
        String updatedProdukKodeValue = produkKodeField.getText();
        String updateProdukNamaValue = produkNamaField.getText();
        String updateProdukKategoriValue = (String) produkKategoriField.getSelectedItem();
        String updateProdukStatusValue = (String) produkStatusField.getSelectedItem();
        String updateProdukKeteranganValue = produkKeteranganField.getText();

        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql;
            // System.out.print(kategoriMap);
            // Check if the selected value exists in the kategoriMap
            if (kategoriMap.containsKey(updateProdukKategoriValue)) {
                // Get the kategoriId from the map based on the selected kategoriNama
                int updateKategoriId = kategoriMap.get(updateProdukKategoriValue);
                // System.out.print(kategoriMap);
//              Start Insert Header
                sql = "call UpdateProduk (?, ?, ?, ?, ?, ?)";

                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, updatedProdukKodeValue);
                pst.setString(2, updateProdukNamaValue);
                pst.setString(3, updateProdukKeteranganValue);
                pst.setInt(4, updateKategoriId);
                pst.setString(5, updateProdukStatusValue);

                // Assuming you have the ID of the row you want to update
                pst.setInt(6, this.idUpdateFormIsian);
                // delete detail
                deleteInsertDetailSatuan(this.idUpdateFormIsian);

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Update successful!");
                } else {
                    System.out.println("Update failed. No rows were updated.");
                }
                JOptionPane.showMessageDialog(this, "Produk Berhasil Disimpan!");
                parentIsianForm.tampilDataList("");
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Kategori not found in the map: " + updateProdukKategoriValue);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
            // Handle the exception
            e.printStackTrace();  // or log the exception
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
            // handle exceptions, at least log them
            e.printStackTrace();
        }
    }
    
    private void deleteInsertDetailSatuan(int idUpdated){
        String produkKodeValue = produkKodeField.getText();
        String produkNamaValue = produkNamaField.getText();
        String produkKategoriValue = (String) produkKategoriField.getSelectedItem();
        String produkStatusValue = (String) produkStatusField.getSelectedItem();
        String produkKeteranganValue = produkKeteranganField.getText();

        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql;
            // System.out.print(kategoriMap);
            sql = "call DeleteProdukDetail('"+idUpdated+"')";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            
            // Now insert into the detail table
            DefaultTableModel model = (DefaultTableModel) tableProdukDetail.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                int satuanId = (int) model.getValueAt(i, model.findColumn("Satuan Id"));
                String satuanHargaString = (String) model.getValueAt(i, model.findColumn("Satuan Harga"));
                double satuanHarga = Double.parseDouble(satuanHargaString);
                String satuanNilaiString = (String) model.getValueAt(i, model.findColumn("Satuan Nilai"));
                int satuanNilai = Integer.parseInt(satuanNilaiString);
                String satuanDefaultString = (String) model.getValueAt(i, model.findColumn("Satuan Default"));

                // Insert into detail table
                insertProdukDetail(conn, idUpdated, satuanId, satuanHarga, satuanNilai, satuanDefaultString);
            }
            //End Insert Detail produk
            JOptionPane.showMessageDialog(this, "Produk Detail Berhasil Disimpan!");
            parentIsianForm.tampilDataList("");
            this.setVisible(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
            // Handle the exception
            e.printStackTrace();  // or log the exception
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
            // handle exceptions, at least log them
            e.printStackTrace();
        }
    }
    
    private void insertDataMasterProduk(){
        String produkKodeValue = produkKodeField.getText();
        String produkNamaValue = produkNamaField.getText();
        String produkKategoriValue = (String) produkKategoriField.getSelectedItem();
        String produkStatusValue = (String) produkStatusField.getSelectedItem();
        String produkKeteranganValue = produkKeteranganField.getText();

        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql;
            // System.out.print(kategoriMap);
            // Check if the selected value exists in the kategoriMap
            if (kategoriMap.containsKey(produkKategoriValue)) {
                // Get the kategoriId from the map based on the selected kategoriNama
                int kategoriId = kategoriMap.get(produkKategoriValue);
                // System.out.print(kategoriMap);
//              Start Insert Header
                sql = "call InsertProduk(?, ?, ?, ?, ?)";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);

                pst.setString(1, produkKodeValue);
                pst.setString(2, produkNamaValue);
                pst.setString(3, produkKeteranganValue);
                pst.setInt(4, kategoriId);  // Set the kategoriId in the SQL query
                pst.setString(5, produkStatusValue);
                pst.executeUpdate();
                // End Insert Header

                //Start Insert Detail produk
                // Get the last inserted ID
                int lastInsertedId = getLastInsertedId(conn);

                // Now insert into the detail table
                DefaultTableModel model = (DefaultTableModel) tableProdukDetail.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    int satuanId = (int) model.getValueAt(i, model.findColumn("Satuan Id"));
                    String satuanHargaString = (String) model.getValueAt(i, model.findColumn("Satuan Harga"));
                    double satuanHarga = Double.parseDouble(satuanHargaString);
                    String satuanNilaiString = (String) model.getValueAt(i, model.findColumn("Satuan Nilai"));
                    int satuanNilai = Integer.parseInt(satuanNilaiString);
                    String satuanDefaultString = (String) model.getValueAt(i, model.findColumn("Satuan Default"));

                    // Insert into detail table
                    insertProdukDetail(conn, lastInsertedId, satuanId, satuanHarga, satuanNilai, satuanDefaultString);
                }
                //End Insert Detail produk
        
                JOptionPane.showMessageDialog(this, "Produk Berhasil Disimpan!");
                
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Kategori not found in the map: " + produkKategoriValue);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
            // Handle the exception
            e.printStackTrace();  // or log the exception
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
            // handle exceptions, at least log them
            e.printStackTrace();
        }
    }
    
    private int getLastInsertedId(Connection conn) throws SQLException {
        int lastInsertedId = -1;
        String sql = "SELECT LAST_INSERT_ID() as last_id";
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                lastInsertedId = rs.getInt("last_id");
            }
        }
        return lastInsertedId;
    }

    private void insertProdukDetail(Connection conn, int produkId, int satuanId, double satuanHarga, int satuanNilai, String satuanDefaultString) throws SQLException {
        String sql = "call InsertProdukDetail (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, produkId);
            pst.setInt(2, satuanId);
            pst.setDouble(3, satuanHarga);
            pst.setInt(4, satuanNilai);
            pst.setString(5, satuanDefaultString);
            pst.executeUpdate();
        }
    }
    
    public void setFormUpdateDetailSatuan(){
        if( ! ( isianProdukDetail.isVisible() ) ) {
            int selectedRowIndex = tableProdukDetail.getSelectedRow();
            
            String pdetailId = (String) tableProdukDetail.getValueAt(selectedRowIndex, 0);
            String satuanNama = (String) tableProdukDetail.getValueAt(selectedRowIndex, 3);
            String satuanHarga = (String) tableProdukDetail.getValueAt(selectedRowIndex, 4);
            String satuanNilai = (String) tableProdukDetail.getValueAt(selectedRowIndex, 5);
            String satuanDefault = (String) tableProdukDetail.getValueAt(selectedRowIndex, 6);
        
            Component parentComponent = null;
            isianProdukDetail.setLocationRelativeTo(parentComponent);
            isianProdukDetail.setParentForm(this);
            isianProdukDetail.setUpdateFormIsianDetail(pdetailId, satuanNama, satuanHarga, satuanNilai, satuanDefault);
            isianProdukDetail.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(this, "Form Tambah produk Sudah Terbuka, Silahkan Lengkapi data dahulu.");
        }
    }
    
    public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomStringBuilder = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomStringBuilder.append(randomChar);
        }

        return randomStringBuilder.toString();
    }
    
    // START ADD ROW PRODUK DETAIL SATUAN
    public void addRowTableSatuan(int pdetailSatuanId, String pdetailSatuanNamaValue, String pdetailHargaValue, String pdetailNilaiValue, String pdetailDefaultValue) {
        DefaultTableModel model = (DefaultTableModel) tableProdukDetail.getModel();
        String unixId = generateRandomString(5);
        model.addRow(new Object[]{unixId, "", pdetailSatuanId, pdetailSatuanNamaValue, pdetailHargaValue, pdetailNilaiValue, pdetailDefaultValue});
    }
    // END ADD ROW PRODUK DETAIL SATUAN
    
    // START UPDATE ROW PRODUK DETAIL SATUAN
    public void updateRowTableSatuan(String unixIdUpdate, int pdetailSatuanId, String pdetailSatuanNamaValue, String pdetailHargaValue, String pdetailNilaiValue, String pdetailDefaultValue) {
        DefaultTableModel model = (DefaultTableModel) tableProdukDetail.getModel();

        // Iterate through the rows to find the row with the specified unixIdUpdate
        for (int row = 0; row < model.getRowCount(); row++) {
            String unixId = (String) model.getValueAt(row, 0);

            // Check if the current row's unixId matches the specified unixIdUpdate
            if (unixId.equals(unixIdUpdate)) {
                // Update the values for the found row
                model.setValueAt(pdetailSatuanId, row, 2);
                model.setValueAt(pdetailSatuanNamaValue, row, 3);
                model.setValueAt(pdetailHargaValue, row, 4);
                model.setValueAt(pdetailNilaiValue, row, 5);
                model.setValueAt(pdetailDefaultValue, row, 6);
                break; // Break out of the loop since the update is done
            }
        }
    }
    // END UPDATE ROW PRODUK DETAIL SATUAN

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKeluarProdukFormIsian;
    private javax.swing.JButton btnProdukDetailAdd;
    private javax.swing.JButton btnSimpanProdukFormIsian;
    private javax.swing.JButton btnUbahSatuan;
    private javax.swing.JComboBox<String> editorSatuanStatusCellField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelFormIsianProduk;
    private javax.swing.JComboBox<String> produkKategoriField;
    private javax.swing.JTextField produkKeteranganField;
    private javax.swing.JTextField produkKodeField;
    private javax.swing.JTextField produkNamaField;
    private javax.swing.JComboBox<String> produkStatusField;
    private javax.swing.JTable tableProdukDetail;
    // End of variables declaration//GEN-END:variables
}
