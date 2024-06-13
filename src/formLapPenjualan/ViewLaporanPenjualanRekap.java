/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package formLapPenjualan;

import _cetak.TableToExcel;
import _cetak.TableToPDF;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author HP
 */
public class ViewLaporanPenjualanRekap extends javax.swing.JInternalFrame {

    
    JasperReport JasRep;
    JasperPrint JasPri;
    Map param = new HashMap(2);
    JasperDesign JasDes;
    private String listCetakLaporan = "CETAK";
    private DefaultTableModel tabmode;
    private TableToPDF templateCetakanPdf;
    private TableToExcel templateCetakanExcel;
    
    /**
     * Creates new form ViewLaporanPenjualanDetail
     */
    public ViewLaporanPenjualanRekap() {
        templateCetakanPdf = new TableToPDF();
        templateCetakanExcel = new TableToExcel();
        initComponents();

        //tampilDataLaporanPenjualanList("");
    }
    private static String generateDynamicFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        return now.format(formatter);
    }
    
//    static boolean maximixed = true;
//    int xMouse;
//    int yMouse;
//
//    public void noTable(){
//        int Baris = tabmode.getRowCount();
//        for (int a=0; a<Baris; a++)
//        {
//            String nomor = String.valueOf(a+1);
//            tabmode.setValueAt(nomor +".",a,0);
//        }
//    }
    
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
    
    // start fn tampil data penjualan list
    public void tampilDataLaporanPenjualanList(String tanggalAwal, String tanggalAkhir){
        DefaultTableModel listTableMasterProduk = new DefaultTableModel();
        listTableMasterProduk.addColumn("Penjualan Id");
        listTableMasterProduk.addColumn("Pelanggan Id");
        listTableMasterProduk.addColumn("Nomor Faktur");
        listTableMasterProduk.addColumn("Penjualan Tanggal");
        listTableMasterProduk.addColumn("Pelanggan");
        listTableMasterProduk.addColumn("Cara Bayar");
        listTableMasterProduk.addColumn("Total Rp");
        listTableMasterProduk.addColumn("Bayar Rp");
        listTableMasterProduk.addColumn("Kembalian");
        listTableMasterProduk.addColumn("Keterangan");
        

        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql;
             
            sql = "{CALL searchPenjualanRekap(?, ?)}";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDate(1, java.sql.Date.valueOf(tanggalAwal));
            pst.setDate(2, java.sql.Date.valueOf(tanggalAkhir));
            ResultSet rs = pst.executeQuery();
            

            while (rs.next()) {
                listTableMasterProduk.addRow(new Object[]{
                    rs.getString("penjualan_id"),
                    rs.getString("pelanggan_id"),
                    rs.getString("penjualan_nomor_faktur"),
                    rs.getString("penjualan_tanggal"),
                    rs.getString("pelanggan_nama"),
                    rs.getString("penjualan_cara_bayar"),
                    rs.getString("penjualan_total_rp"),
                    rs.getString("penjualan_bayar_rp"),
                    rs.getString("penjualan_kembalian_rp"),
                    rs.getString("penjualan_keterangan")
                });
            }
            
            tableGridLaporanPenjualanDetail.setModel(new DefaultTableModel());

            tableGridLaporanPenjualanDetail.setModel(listTableMasterProduk);
            hideColumnsByName(tableGridLaporanPenjualanDetail, "Penjualan Id", "Pelanggan Id");
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

        jPanel1 = new javax.swing.JPanel();
        labelTanggalAwal = new javax.swing.JLabel();
        TanggalAwalField = new com.toedter.calendar.JDateChooser();
        labelTanggalAkhir = new javax.swing.JLabel();
        TanggalAkhirField = new com.toedter.calendar.JDateChooser();
        btnCariLaporanPenjualan = new javax.swing.JButton();
        btnCetakPdf = new javax.swing.JButton();
        btnCetakExcel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGridLaporanPenjualanDetail = new javax.swing.JTable();

        setClosable(true);
        setTitle("Laporan Penjualan Rekap");

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        labelTanggalAwal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        labelTanggalAwal.setForeground(new java.awt.Color(255, 255, 255));
        labelTanggalAwal.setText("Dari Tanggal");

        TanggalAwalField.setBackground(new java.awt.Color(204, 204, 0));
        TanggalAwalField.setForeground(new java.awt.Color(255, 255, 255));

        labelTanggalAkhir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        labelTanggalAkhir.setForeground(new java.awt.Color(255, 255, 255));
        labelTanggalAkhir.setText("Sampai Tanggal");

        TanggalAkhirField.setBackground(new java.awt.Color(204, 204, 0));
        TanggalAkhirField.setForeground(new java.awt.Color(255, 255, 255));

        btnCariLaporanPenjualan.setBackground(new java.awt.Color(0, 51, 153));
        btnCariLaporanPenjualan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCariLaporanPenjualan.setForeground(new java.awt.Color(255, 255, 255));
        btnCariLaporanPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/46.png"))); // NOI18N
        btnCariLaporanPenjualan.setText("Cari Laporan");
        btnCariLaporanPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariLaporanPenjualanActionPerformed(evt);
            }
        });

        btnCetakPdf.setBackground(new java.awt.Color(0, 51, 153));
        btnCetakPdf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCetakPdf.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        btnCetakPdf.setText("Cetak");
        btnCetakPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakPdfActionPerformed(evt);
            }
        });

        btnCetakExcel.setBackground(new java.awt.Color(0, 51, 153));
        btnCetakExcel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCetakExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        btnCetakExcel.setText("Cetak Excel");
        btnCetakExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakExcelActionPerformed(evt);
            }
        });

        tableGridLaporanPenjualanDetail.setBackground(new java.awt.Color(51, 51, 51));
        tableGridLaporanPenjualanDetail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tableGridLaporanPenjualanDetail.setForeground(new java.awt.Color(255, 255, 255));
        tableGridLaporanPenjualanDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Penjualan Id", "Pelanggan Id", "Nomor Faktur", "Penjualan Tanggal", "Pelanggan", "Cara Bayar", "Total Rp", "Bayar Rp", "Kembalian", "Keterangan"
            }
        ));
        jScrollPane1.setViewportView(tableGridLaporanPenjualanDetail);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(labelTanggalAwal)
                .addGap(18, 18, 18)
                .addComponent(TanggalAwalField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelTanggalAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TanggalAkhirField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCariLaporanPenjualan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(btnCetakPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCetakExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCariLaporanPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCetakPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCetakExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelTanggalAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTanggalAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TanggalAwalField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TanggalAkhirField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(424, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(74, 74, 74)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addContainerGap()))
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

    private void btnCariLaporanPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariLaporanPenjualanActionPerformed
        // TODO add your handling code here:
        Date tanggalAwal = TanggalAwalField.getDate();
        Date tanggalAkhir = TanggalAkhirField.getDate();
        if (tanggalAwal != null && tanggalAkhir !=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String tanggalAwalValue = dateFormat.format(tanggalAwal);
            String tanggalAkhirValue = dateFormat.format(tanggalAkhir);
            tampilDataLaporanPenjualanList(tanggalAwalValue, tanggalAkhirValue);
        }else{
            JOptionPane.showMessageDialog(this, "Tanggal Awal dan Akhir Mohon Di isikan Terlebih Dahulu!!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_btnCariLaporanPenjualanActionPerformed

    private void btnCetakPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakPdfActionPerformed
        // TODO add your handling code here:
        Date tanggalAwal = TanggalAwalField.getDate();
        Date tanggalAkhir = TanggalAkhirField.getDate();
        if (tanggalAwal != null && tanggalAkhir !=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String tanggalAwalValue = dateFormat.format(tanggalAwal);
            String tanggalAkhirValue = dateFormat.format(tanggalAkhir);
            templateCetakanPdf.exportTableToPDF(
                tableGridLaporanPenjualanDetail,
                "cetak/laporan-penjualan/rekap-penjualan-"+generateDynamicFileName()+".pdf",
                "Daftar Penjualan Periode "+tanggalAwalValue+" s/d "+tanggalAkhirValue
            );
        }else{
            JOptionPane.showMessageDialog(this, "Tanggal Awal dan Akhir Mohon Di isikan Terlebih Dahulu!!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnCetakPdfActionPerformed

    private void btnCetakExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakExcelActionPerformed
        // TODO add your handling code here:
        Date tanggalAwal = TanggalAwalField.getDate();
        Date tanggalAkhir = TanggalAkhirField.getDate();
        if (tanggalAwal != null && tanggalAkhir !=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String tanggalAwalValue = dateFormat.format(tanggalAwal);
            String tanggalAkhirValue = dateFormat.format(tanggalAkhir);
            templateCetakanExcel.exportToExcel(
                tableGridLaporanPenjualanDetail,
                "cetak/laporan-penjualan/rekap-penjualan-"+generateDynamicFileName()+".xls",
                "Daftar Penjualan Periode "+tanggalAwalValue+" s/d "+tanggalAkhirValue
            );
        }else{
            JOptionPane.showMessageDialog(this, "Tanggal Awal dan Akhir Mohon Di isikan Terlebih Dahulu!!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnCetakExcelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser TanggalAkhirField;
    private com.toedter.calendar.JDateChooser TanggalAwalField;
    private javax.swing.JButton btnCariLaporanPenjualan;
    private javax.swing.JButton btnCetakExcel;
    private javax.swing.JButton btnCetakPdf;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTanggalAkhir;
    private javax.swing.JLabel labelTanggalAwal;
    private javax.swing.JTable tableGridLaporanPenjualanDetail;
    // End of variables declaration//GEN-END:variables
}
