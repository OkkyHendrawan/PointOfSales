/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package formLapPembelian;

import com.mysql.jdbc.CallableStatement;
import java.awt.Color;
import java.awt.Frame;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import database.Koneksi;
import java.sql.SQLException;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.text.DecimalFormat;

/**
 *
 * @author HP
 */
public class ViewLaporanPembelianRekap extends javax.swing.JInternalFrame  {

    
    JasperReport JasRep;
    JasperPrint JasPri;
    Map param = new HashMap(2);
    JasperDesign JasDes;
    
    private DefaultTableModel tabmode;

 /**
     * Creates new form ViewLaporanPembelian
     */
    public ViewLaporanPembelianRekap() {
        initComponents();
        tanggal();
        tampilDataPembelianList("");
    }
   

    // frame maks dan geser panel
    static boolean maximixed = true;
    int xMouse;
    int yMouse;

    public void noTable(){
        int Baris = tabmode.getRowCount();
        for (int a=0; a<Baris; a++)
        {
            String nomor = String.valueOf(a+1);
            tabmode.setValueAt(nomor +".",a,0);
        }
    }

     public void tanggal() {
        // Mengambil tanggal awal dan tanggal akhir dari JDateChooser
        Date tanggalAwal = tglAwal.getDate();
        Date tanggalAkhir = tglAkhir.getDate();

        if (tanggalAwal != null && tanggalAkhir != null) {
            tampilkanDataPembelianTanggal(tanggalAwal, tanggalAkhir);
        } else {
           
        }
    }
     
     
    public void tampilkanDataPembelianTanggal(Date tanggalAwal, Date tanggalAkhir) {
    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
    DefaultTableModel listTableMasterPembelian = new DefaultTableModel();
    listTableMasterPembelian.addColumn("No");
    listTableMasterPembelian.addColumn("Nomor Faktur");
    listTableMasterPembelian.addColumn("Tanggal");
    listTableMasterPembelian.addColumn("Total Pembelian (Rp)");
    listTableMasterPembelian.addColumn("Nama Pemasok");
    listTableMasterPembelian.addColumn("Alamat");
    listTableMasterPembelian.addColumn("No Tlp");


    try {
        java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
        String sql = "{CALL pembelianRekapTanggal(?, ?)}";
        try (CallableStatement cst = (CallableStatement) conn.prepareCall(sql)) {
            cst.setDate(1, new java.sql.Date(tanggalAwal.getTime()));
            cst.setDate(2, new java.sql.Date(tanggalAkhir.getTime()));

            try (ResultSet rs = cst.executeQuery()) {
                int rowNum = 1; // Initialize the row number counter
                while (rs.next()) {
                    String formattedTotalPembelian = decimalFormat.format(rs.getDouble("pembelian_total_rp"));
                    listTableMasterPembelian.addRow(new Object[]{
                            rowNum,
                            rs.getString("pembelian_nomor_faktur"),
                            rs.getString("pembelian_tanggal"),
                            formattedTotalPembelian,
                            rs.getString("pemasok_nama"),
                            rs.getString("pemasok_alamat"),
                            rs.getString("pemasok_tlp"),
                            
                    });
                    rowNum++; // Increment the row number
                }
            }
        }

        pembelianGridTable.setModel(listTableMasterPembelian);
        hideColumnsByName(pembelianGridTable, "Pembelian Id");

    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    public void pencarian(String sql){
        Object[] Baris = {"No","Nomor Faktur","Tanggal","Total Pembelian (Rp)", "Nama Pemasok", "Alamat", "No Tlp"};
        tabmode = new DefaultTableModel(null, Baris);
        pembelianGridTable.setModel(tabmode);
        int brs = pembelianGridTable.getRowCount();
        for (int i = 0; 1 < brs; i++){
            tabmode.removeRow(1);
        }
        try{
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            try (CallableStatement cst = (CallableStatement) conn.prepareCall(sql)) {
            cst.setString(1, txtCari.getText());
            try (ResultSet hasil = cst.executeQuery()) {
            
            // Format untuk nilai mata uang
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            
            while (hasil.next()){
                String pembelian_nomor_faktur = hasil.getString("pembelian_nomor_faktur");
                String pembelian_tanggal = hasil.getString("pembelian_tanggal");
                 // Memformat nilai pembelian_total_rp dengan koma
                double totalRp = Double.parseDouble(hasil.getString("pembelian_total_rp"));
                String pembelian_total_rp = decimalFormat.format(totalRp);
                String pemasok_nama = hasil.getString("pemasok_nama");
                String pemasok_alamat = hasil.getString("pemasok_alamat");
                String pemasok_tlp = hasil.getString("pemasok_tlp");
                 
                String[] data = {"",pembelian_nomor_faktur, pembelian_tanggal, pembelian_total_rp, pemasok_nama,
                pemasok_alamat, pemasok_tlp};
                tabmode.addRow(data);
                noTable();
                
                    }
                }
            }
        } catch(Exception e){
            
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

        btnLaporan = new javax.swing.ButtonGroup();
        panelPembelianRekap = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        btnCariPembelian = new javax.swing.JButton();
        btnRefreshPembelian = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tglAwal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        tglAkhir = new com.toedter.calendar.JDateChooser();
        btnTampilPembelian = new javax.swing.JButton();
        btnCetakPembelian = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pembelianGridTable = new javax.swing.JTable();

        setClosable(true);
        setTitle("Pembelian Rekap");
        setPreferredSize(new java.awt.Dimension(1182, 685));

        panelPembelianRekap.setBackground(new java.awt.Color(0, 0, 51));
        panelPembelianRekap.setForeground(new java.awt.Color(0, 0, 51));

        txtCari.setBackground(new java.awt.Color(0, 153, 153));
        txtCari.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtCari.setForeground(new java.awt.Color(255, 255, 255));
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });

        btnCariPembelian.setBackground(new java.awt.Color(0, 153, 153));
        btnCariPembelian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCariPembelian.setForeground(new java.awt.Color(255, 255, 255));
        btnCariPembelian.setText("Pencarian");
        btnCariPembelian.setContentAreaFilled(false);
        btnCariPembelian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCariPembelian.setOpaque(true);
        btnCariPembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCariPembelianMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCariPembelianMouseExited(evt);
            }
        });
        btnCariPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPembelianActionPerformed(evt);
            }
        });

        btnRefreshPembelian.setBackground(new java.awt.Color(0, 153, 153));
        btnRefreshPembelian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRefreshPembelian.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/22.png"))); // NOI18N
        btnRefreshPembelian.setText("Refresh");
        btnRefreshPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshPembelianActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dari Tanggal :");
        jLabel1.setOpaque(true);

        tglAwal.setBackground(new java.awt.Color(255, 255, 51));
        tglAwal.setForeground(new java.awt.Color(255, 255, 51));
        tglAwal.setDateFormatString("dd-MM-yyyy");
        tglAwal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setBackground(new java.awt.Color(0, 153, 153));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Sampai Tanggal:");
        jLabel2.setOpaque(true);

        tglAkhir.setBackground(new java.awt.Color(255, 255, 51));
        tglAkhir.setForeground(new java.awt.Color(255, 255, 51));
        tglAkhir.setDateFormatString("dd-MM-yyyy");
        tglAkhir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnTampilPembelian.setBackground(new java.awt.Color(0, 153, 153));
        btnTampilPembelian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnTampilPembelian.setForeground(new java.awt.Color(255, 255, 255));
        btnTampilPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/46.png"))); // NOI18N
        btnTampilPembelian.setText("Tampilkan");
        btnTampilPembelian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTampilPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilPembelianActionPerformed(evt);
            }
        });

        btnCetakPembelian.setBackground(new java.awt.Color(0, 153, 153));
        btnCetakPembelian.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCetakPembelian.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        btnCetakPembelian.setText("Cetak");
        btnCetakPembelian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCetakPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakPembelianActionPerformed(evt);
            }
        });

        pembelianGridTable.setBackground(new java.awt.Color(51, 51, 51));
        pembelianGridTable.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        pembelianGridTable.setForeground(new java.awt.Color(255, 255, 255));
        pembelianGridTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        pembelianGridTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pembelianGridTable.setRowHeight(30);
        pembelianGridTable.setRowMargin(2);
        pembelianGridTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pembelianGridTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(pembelianGridTable);

        javax.swing.GroupLayout panelPembelianRekapLayout = new javax.swing.GroupLayout(panelPembelianRekap);
        panelPembelianRekap.setLayout(panelPembelianRekapLayout);
        panelPembelianRekapLayout.setHorizontalGroup(
            panelPembelianRekapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPembelianRekapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPembelianRekapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPembelianRekapLayout.createSequentialGroup()
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefreshPembelian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTampilPembelian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCetakPembelian)
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        panelPembelianRekapLayout.setVerticalGroup(
            panelPembelianRekapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPembelianRekapLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelPembelianRekapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tglAkhir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tglAwal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTampilPembelian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelPembelianRekapLayout.createSequentialGroup()
                        .addGroup(panelPembelianRekapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCetakPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefreshPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPembelianRekapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCariPembelian, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPembelianRekap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPembelianRekap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariKeyPressed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        String sqlPencarian = "{CALL SearchPembelianRekap(?)}";
        pencarian(sqlPencarian);
    }//GEN-LAST:event_txtCariKeyTyped

    private void btnCariPembelianMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCariPembelianMouseEntered
        btnCariPembelian.setBackground(new Color(0,0,204));
        btnCariPembelian.setForeground(Color.white);
    }//GEN-LAST:event_btnCariPembelianMouseEntered

    private void btnCariPembelianMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCariPembelianMouseExited
        btnCariPembelian.setBackground(new Color(204,204,204));
        btnCariPembelian.setForeground(Color.black);
    }//GEN-LAST:event_btnCariPembelianMouseExited

    private void btnCariPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPembelianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCariPembelianActionPerformed

    private void btnRefreshPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshPembelianActionPerformed
        // TODO add your handling code here:
        txtCari.setText("");
        tampilDataPembelianList("");
    }//GEN-LAST:event_btnRefreshPembelianActionPerformed

    private void btnTampilPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilPembelianActionPerformed
        tanggal();
    }//GEN-LAST:event_btnTampilPembelianActionPerformed

    private void btnCetakPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakPembelianActionPerformed
        try {
            Connection konn = new Koneksi().koneksiDB();
            File file = new File("src/_cetak/laporanPembelianRekap.jrxml");
            JasDes = JRXmlLoader.load(file);
            param.put("pembelian_id",btnCetakPembelian.getText());
            JasRep = JasperCompileManager.compileReport(JasDes);
            JasPri = JasperFillManager.fillReport(JasRep, param, konn);
            //JasperViewer.viewReport(JasPri, false);
            JasperViewer jasperViewer = new JasperViewer(JasPri, false);
            jasperViewer.setExtendedState(jasperViewer.getExtendedState()|javax.swing.JFrame.MAXIMIZED_BOTH);
            jasperViewer.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal membuka Laporan", "Cetak laporan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCetakPembelianActionPerformed

    private void pembelianGridTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pembelianGridTableMouseClicked
       
    }//GEN-LAST:event_pembelianGridTableMouseClicked
    
    private void panelHeaderMouseDragged(java.awt.event.MouseEvent evt) {                                         
        if(maximixed){
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            this.setLocation(x - xMouse, y - yMouse);
        }
    }

    private void panelHeaderMousePressed(java.awt.event.MouseEvent evt) {                                         
        xMouse = evt.getX();
        yMouse = evt.getY();
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
            }
        }
    }
    public void tampilDataPembelianList(String cariData){
        
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        DefaultTableModel listTableMasterPembelian = new DefaultTableModel();
        listTableMasterPembelian.addColumn("No");
        listTableMasterPembelian.addColumn("Nomor Faktur");
        listTableMasterPembelian.addColumn("Tanggal");
        listTableMasterPembelian.addColumn("Total Pembelian (Rp)");
        listTableMasterPembelian.addColumn("Nama Pemasok");
        listTableMasterPembelian.addColumn("Alamat");
        listTableMasterPembelian.addColumn("No Tlp");


        try {
        java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
        String sql = "{CALL pembelianRekap(?)}";
        java.sql.CallableStatement cst = conn.prepareCall(sql);
        cst.setString(1, cariData);
        ResultSet rs = cst.executeQuery();

        int rowNum = 1; // Initialize the row number counter
        while (rs.next()) {
            String formattedTotalPembelian = decimalFormat.format(rs.getDouble("pembelian_total_rp"));
            listTableMasterPembelian.addRow(new Object[]{
                    rowNum,
                    rs.getString("pembelian_nomor_faktur"),
                    rs.getString("pembelian_tanggal"),
                    formattedTotalPembelian,
                    rs.getString("pemasok_nama"),
                    rs.getString("pemasok_alamat"),
                    rs.getString("pemasok_tlp"),
            });
            rowNum++; // Increment the row number
        }

        pembelianGridTable.setModel(new DefaultTableModel());
        pembelianGridTable.setModel(listTableMasterPembelian);
        hideColumnsByName(pembelianGridTable, "Pembelian Id");
    } catch (SQLException e) {
        // Handle the exception
        e.printStackTrace();  // or log the exception
    } catch (Exception e) {
        // handle exceptions, at least log them
        e.printStackTrace();
    }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariPembelian;
    private javax.swing.JButton btnCetakPembelian;
    private javax.swing.ButtonGroup btnLaporan;
    private javax.swing.JButton btnRefreshPembelian;
    private javax.swing.JButton btnTampilPembelian;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPembelianRekap;
    private javax.swing.JTable pembelianGridTable;
    private com.toedter.calendar.JDateChooser tglAkhir;
    private com.toedter.calendar.JDateChooser tglAwal;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
