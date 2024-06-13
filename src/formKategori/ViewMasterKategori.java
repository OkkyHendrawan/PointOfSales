/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package formKategori;

import com.mysql.jdbc.Connection;
import database.Koneksi;
import com.mysql.jdbc.PreparedStatement;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.text.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author HP
 */
public class ViewMasterKategori extends javax.swing.JInternalFrame {
    
//    JasperReport JasRep;
//    JasperPrint JasPri;
//    Map<String, Object> param = new HashMap<>();
//    JasperDesign JasDes;
    
    /**
     * Creates new form ViewMasterKategori
     * 
     */
    
    private FormIsianKategori isianKategori;
    
    public ViewMasterKategori(){
        isianKategori = new FormIsianKategori(this);
        
        initComponents();
        tampilDatakategoriList("");
    }
    
    protected void tampilDatakategoriList(String pencarianValue) {
        DefaultTableModel listTableMasterKategori = new DefaultTableModel();
        listTableMasterKategori.addColumn("Kategori Id");
        listTableMasterKategori.addColumn("Kategori Kode");
        listTableMasterKategori.addColumn("Kategori Nama");
        listTableMasterKategori.addColumn("Kategori Status");

        
        try {
            Connection conn = (Connection) database.Koneksi.koneksiDB();
            String sql = "";
            if (!pencarianValue.isEmpty()) {
                sql = "call SearchKategori('"+pencarianValue+"')";
            } else {
                sql = "call SearchKategori('')";
            }
            // System.out.print(sql);
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                listTableMasterKategori.addRow(new Object[]{
                    rs.getString("kategori_id"),
                    rs.getString("kategori_kode"),
                    rs.getString("kategori_nama"),
                    rs.getString("kategori_status")
                });
            }
            gridTabelKategori.setModel(new DefaultTableModel());
            gridTabelKategori.setModel(listTableMasterKategori);
            hideColumnsByName(gridTabelKategori, "Kategori Id");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void refreshTabelKategori() {
        tampilDatakategoriList("");
    }
 
    private void centerAlignColumn(JTable table, String columnName, int alignment) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(alignment);

        int columnIndex = findColumnIndex(table, columnName);

        if (columnIndex != -1) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
        } else {
            System.err.println("Column not found: " + columnName);
        }
    }

    private int findColumnIndex(JTable table, String columnName) {
    TableColumnModel columnModel = table.getColumnModel();
    
    for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
            if (columnModel.getColumn(columnIndex).getIdentifier().equals(columnName)) {
                return columnIndex;
            }
        }
    
        return -1; // Column not found
    }
    
    private String getStatusItem (String statusValues) {
        
        if("Aktif".equals(statusValues)) {
            return "Aktif";
        } else {
            return "Tidak Aktif";
        }
        
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelKategori = new javax.swing.JPanel();
        buttonTambah = new javax.swing.JButton();
        buttonRefresh = new javax.swing.JButton();
        buttonUbah = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        PencarianKategoriLabel = new javax.swing.JLabel();
        CetakKategori = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        gridTabelKategori = new javax.swing.JTable();

        setClosable(true);
        setTitle("Master Kategori");

        panelKategori.setBackground(new java.awt.Color(0, 102, 102));

        buttonTambah.setBackground(new java.awt.Color(0, 0, 102));
        buttonTambah.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        buttonTambah.setForeground(new java.awt.Color(255, 255, 255));
        buttonTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/03.png"))); // NOI18N
        buttonTambah.setText("Tambah Kategori");
        buttonTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambahActionPerformed(evt);
            }
        });

        buttonRefresh.setBackground(new java.awt.Color(0, 0, 102));
        buttonRefresh.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        buttonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        buttonRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/22.png"))); // NOI18N
        buttonRefresh.setText("Refresh");
        buttonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRefreshActionPerformed(evt);
            }
        });

        buttonUbah.setBackground(new java.awt.Color(0, 0, 102));
        buttonUbah.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        buttonUbah.setForeground(new java.awt.Color(255, 255, 255));
        buttonUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/19_edit.png"))); // NOI18N
        buttonUbah.setText("Ubah Kategori");
        buttonUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUbahActionPerformed(evt);
            }
        });

        searchField.setBackground(new java.awt.Color(51, 51, 51));
        searchField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        searchField.setForeground(new java.awt.Color(255, 255, 255));
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchFieldKeyPressed(evt);
            }
        });

        PencarianKategoriLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PencarianKategoriLabel.setForeground(new java.awt.Color(255, 255, 255));
        PencarianKategoriLabel.setText("Pencarian");

        CetakKategori.setBackground(new java.awt.Color(0, 0, 102));
        CetakKategori.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        CetakKategori.setForeground(new java.awt.Color(255, 255, 255));
        CetakKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        CetakKategori.setText("Cetak");
        CetakKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CetakKategoriActionPerformed(evt);
            }
        });

        gridTabelKategori.setBackground(new java.awt.Color(51, 51, 51));
        gridTabelKategori.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridTabelKategori.setForeground(new java.awt.Color(255, 255, 255));
        gridTabelKategori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(gridTabelKategori);

        javax.swing.GroupLayout panelKategoriLayout = new javax.swing.GroupLayout(panelKategori);
        panelKategori.setLayout(panelKategoriLayout);
        panelKategoriLayout.setHorizontalGroup(
            panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKategoriLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelKategoriLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(PencarianKategoriLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRefresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonTambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonUbah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CetakKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelKategoriLayout.setVerticalGroup(
            panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKategoriLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CetakKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PencarianKategoriLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelKategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelKategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void buttonUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUbahActionPerformed
        int selectedRowIndex = gridTabelKategori.getSelectedRow();

        if (selectedRowIndex == -1) {
            // No row is selected, show a message
            JOptionPane.showMessageDialog(this, "Tidak Ada Data Yang Dipilih, Mohon Cek Kembali.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            setFormUpdateProduk();
        }
    }//GEN-LAST:event_buttonUbahActionPerformed
    
    public void setFormUpdateProduk(){
        int selectedRowIndex = gridTabelKategori.getSelectedRow();
         
        // A row is selected, retrieve data and perform the update
        String kategoriId = (String) gridTabelKategori.getValueAt(selectedRowIndex, 0);
        String kategoriKode = (String) gridTabelKategori.getValueAt(selectedRowIndex, 1);
        String kategoriNama = (String) gridTabelKategori.getValueAt(selectedRowIndex, 2);
        String kategoriStatus = (String) gridTabelKategori.getValueAt(selectedRowIndex, 3);
        
        // System.out.println(idProduk);
        // System.out.println(kategoriIdProduk);

        Component parentComponent = null; // Replace with the actual component reference
        isianKategori.setLocationRelativeTo(parentComponent);
        isianKategori.setDataUpdateFormIsian(kategoriId, kategoriKode, kategoriNama, kategoriStatus);
        isianKategori.setTitle("Ubah Data");
        isianKategori.setVisible(true);
    }

    private void buttonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRefreshActionPerformed
        refreshTabelKategori();
    }//GEN-LAST:event_buttonRefreshActionPerformed

    private void buttonTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTambahActionPerformed
        if( ! ( isianKategori.isVisible() ) ) { // Memeriksa apakah menuProduk sedang tidak tampil
            Component parentComponent = null; // Replace with the actual component reference
            isianKategori.setLocationRelativeTo(parentComponent);
            isianKategori.refreshFormIsian();
            isianKategori.setTitle("Tambah Data");
            isianKategori.setVisible(true);
        }
        else {
            isianKategori.requestFocusInWindow();
            isianKategori.setFocusable(true);
            JOptionPane.showMessageDialog(this, "Form Tambah produk Sudah Terbuka, Silahkan Lengkapi data dahulu.");
        }
    }//GEN-LAST:event_buttonTambahActionPerformed

    private void searchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // This block will be executed when the Enter key is pressed
            String enteredText = searchField.getText();
            // Perform your desired action with the entered text
            // For example, you might want to process the entered SKU
            tampilDatakategoriList(enteredText);
        }
    }//GEN-LAST:event_searchFieldKeyPressed

    private void CetakKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CetakKategoriActionPerformed
        
//        PreviewFormKategori previewFormKategori = new PreviewFormKategori();
//        previewFormKategori.setVisible(true);
        
        printTable();
        
    }//GEN-LAST:event_CetakKategoriActionPerformed
    
    private boolean cetak() {
        
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        try {
            Connection con = (Connection) database.Koneksi.koneksiDB();
            String sql_cetak = "SELECT * FROM produk_kategori";
            Statement statement = con.createStatement();
            
            ResultSet resultSet = statement.executeQuery(sql_cetak);
            resultSet.last();
            if(resultSet.getRow() == 0) {
                JOptionPane.showMessageDialog(null, "Data tidak Ditemukan");
            }else {
                resultSet.beforeFirst();
                defaultTableModel = tabelModel(resultSet);
                gridTabelKategori.setModel(defaultTableModel);
                gridTabelKategori.setAutoCreateRowSorter(true);
            }
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ViewMasterKategori.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private DefaultTableModel tabelModel(ResultSet rs) {
        
//        String[] header = {"NO", "KODE", "NAMA", "STATUS"};
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        gridTabelKategori.setModel(defaultTableModel);
        try {
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int JumlahKolom = resultSetMetaData.getColumnCount();
            
            String[] namaKolom = new String[JumlahKolom + 1];
//            namaKolom[0] = "No";
            for(int i=0; i<JumlahKolom; i++) {
                namaKolom[i + 1] = resultSetMetaData.getCatalogName(i + 1);
            }
            defaultTableModel.setColumnIdentifiers(namaKolom);
            
            int nomorUrut = 1;
            
            while(rs.next()) {
                String[] dataKategori = new String[JumlahKolom + 1];
                dataKategori[0] = Integer.toString(nomorUrut);
                for(int i = 1; i < JumlahKolom; i++) {
                    dataKategori[i] = rs.getString(i);
                }
                defaultTableModel.addRow(dataKategori);
                nomorUrut++;
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
                
        return defaultTableModel;
    }
    
    private void printTable() {
        MessageFormat header = new MessageFormat("Data Kategori");
        MessageFormat footer = new MessageFormat("TTL");
        try {
            gridTabelKategori.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            System.err.format("Cannot print %s%n", ex.getMessage());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CetakKategori;
    private javax.swing.JLabel PencarianKategoriLabel;
    private javax.swing.JButton buttonRefresh;
    private javax.swing.JButton buttonTambah;
    private javax.swing.JButton buttonUbah;
    private javax.swing.JTable gridTabelKategori;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelKategori;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
    
}
