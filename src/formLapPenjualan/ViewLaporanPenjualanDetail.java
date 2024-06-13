/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package formLapPenjualan;

import _cetak.TableToExcel;
import _cetak.TableToPDF;
import com.mysql.jdbc.CallableStatement;
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
public class ViewLaporanPenjualanDetail extends javax.swing.JInternalFrame {

    
    JasperReport JasRep;
    JasperPrint JasPri;
    Map param = new HashMap(2);
    JasperDesign JasDes;
    private String listCetakLaporan = "CETAK";
    private DefaultTableModel tabmode;
    private TableToPDF templateCetakanPdf;
    private TableToExcel templateCetakanExcel;
    /**
     * Creates new form ViewLaporanPenjualan
     */
    public ViewLaporanPenjualanDetail() {
        templateCetakanPdf = new TableToPDF();
        templateCetakanExcel = new TableToExcel();
        initComponents();
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
    
    public void tampilDataLaporanPenjualanDetailList(Date tanggalAwal, Date tanggalAkhir){
        DefaultTableModel listTableMasterProduk = new DefaultTableModel();
        listTableMasterProduk.addColumn("Nomor Faktur");
        listTableMasterProduk.addColumn("Penjualan Tanggal");
        listTableMasterProduk.addColumn("Produk Nama");
        listTableMasterProduk.addColumn("Satuan Nama");
        listTableMasterProduk.addColumn("Harga");
        listTableMasterProduk.addColumn("Jumlah");
        listTableMasterProduk.addColumn("Diskon (Rp)");
        listTableMasterProduk.addColumn("Diskon (%)");
        listTableMasterProduk.addColumn("Pelanggan");
        listTableMasterProduk.addColumn("Cara Bayar");
        listTableMasterProduk.addColumn("Total Rp");
        listTableMasterProduk.addColumn("Bayar Rp");
        listTableMasterProduk.addColumn("Kembalian");
        listTableMasterProduk.addColumn("Keterangan");
        listTableMasterProduk.addColumn("Sub Total");
        

        try {
           java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
        String sql = "{CALL searchPenjualanDetail(?, ?)}";
        try (CallableStatement cst = (CallableStatement) conn.prepareCall(sql)) {
            cst.setDate(1, new java.sql.Date(tanggalAwal.getTime()));
            cst.setDate(2, new java.sql.Date(tanggalAkhir.getTime()));

            try (ResultSet rs = cst.executeQuery()) {
            

            while (rs.next()) {
                listTableMasterProduk.addRow(new Object[]{
                    rs.getString("penjualan_nomor_faktur"),
                    rs.getString("penjualan_tanggal"),
                    rs.getString("produk_nama"),
                    rs.getString("satuan_nama"),
                    rs.getString("pjual_detail_harga"),
                    rs.getString("pjual_detail_jumlah"),
                    rs.getString("pjual_detail_diskon_rp"),
                    rs.getString("pjual_detail_diskon_persen"),
                    rs.getString("pelanggan_nama"),
                    rs.getString("penjualan_cara_bayar"),
                    rs.getString("penjualan_total_rp"),
                    rs.getString("penjualan_bayar_rp"),
                    rs.getString("penjualan_kembalian_rp"),
                    rs.getString("penjualan_keterangan"),
                    rs.getString("pjual_detail_subtotal")
                
                });
            }
        }
        }
            
            tableGridLaporanPenjualanDetail.setModel(new DefaultTableModel());

            tableGridLaporanPenjualanDetail.setModel(listTableMasterProduk);
           
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
        lbTanggalAwal = new javax.swing.JLabel();
        TanggalAwalField = new com.toedter.calendar.JDateChooser();
        lbTanggalAkhir = new javax.swing.JLabel();
        TanggalAkhirField = new com.toedter.calendar.JDateChooser();
        btnCariLaporanPenjualanRekap = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        btnCetakExcel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGridLaporanPenjualanDetail = new javax.swing.JTable();

        setClosable(true);
        setTitle("Laporan Penjualan Detail");

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        lbTanggalAwal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbTanggalAwal.setForeground(new java.awt.Color(255, 255, 255));
        lbTanggalAwal.setText("Dari Tanggal");

        lbTanggalAkhir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbTanggalAkhir.setForeground(new java.awt.Color(255, 255, 255));
        lbTanggalAkhir.setText("Sampai Tanggal");

        btnCariLaporanPenjualanRekap.setBackground(new java.awt.Color(0, 51, 153));
        btnCariLaporanPenjualanRekap.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCariLaporanPenjualanRekap.setForeground(new java.awt.Color(255, 255, 255));
        btnCariLaporanPenjualanRekap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/47.png"))); // NOI18N
        btnCariLaporanPenjualanRekap.setText("Cari Laporan");
        btnCariLaporanPenjualanRekap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariLaporanPenjualanRekapActionPerformed(evt);
            }
        });

        btnCetak.setBackground(new java.awt.Color(0, 51, 153));
        btnCetak.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCetak.setForeground(new java.awt.Color(255, 255, 255));
        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
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
                "Penjualan Id", "Pelanggan Id", "Nomor faktur", "Penjualan Tanggal", "Produk Nama", "Satuan Nama", "Harga", "Jumlah", "Disk (Rp)", "Disk (%)", "Pelanggan", "Cara Bayar", "Total Rp", "Bayar Rp", "kembalian", "Sub Total"
            }
        ));
        jScrollPane1.setViewportView(tableGridLaporanPenjualanDetail);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(lbTanggalAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TanggalAwalField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbTanggalAkhir)
                .addGap(18, 18, 18)
                .addComponent(TanggalAkhirField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCariLaporanPenjualanRekap)
                .addGap(107, 107, 107)
                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCetakExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(61, 61, 61))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1013, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTanggalAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTanggalAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TanggalAwalField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TanggalAkhirField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCariLaporanPenjualanRekap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCetakExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1026, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariLaporanPenjualanRekapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariLaporanPenjualanRekapActionPerformed
        // TODO add your handling code here:
        Date tanggalAwal = TanggalAwalField.getDate();
        Date tanggalAkhir = TanggalAkhirField.getDate();
        if (tanggalAwal != null && tanggalAkhir !=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String tanggalAwalValue = dateFormat.format(tanggalAwal);
            String tanggalAkhirValue = dateFormat.format(tanggalAkhir);
            tampilDataLaporanPenjualanDetailList(tanggalAwal, tanggalAkhir);
        }else{
            JOptionPane.showMessageDialog(this, "Tanggal Awal dan Akhir Mohon Di isikan Terlebih Dahulu!!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnCariLaporanPenjualanRekapActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
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
    }//GEN-LAST:event_btnCetakActionPerformed

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
    private javax.swing.JButton btnCariLaporanPenjualanRekap;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnCetakExcel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTanggalAkhir;
    private javax.swing.JLabel lbTanggalAwal;
    private javax.swing.JTable tableGridLaporanPenjualanDetail;
    // End of variables declaration//GEN-END:variables
}
