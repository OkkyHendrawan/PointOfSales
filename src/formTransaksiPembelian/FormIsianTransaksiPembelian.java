/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formTransaksiPembelian;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import formTransaksiPenjualan.FormIsianTransaksiPenjualan;
import static formTransaksiPenjualan.FormIsianTransaksiPenjualan.generateRandomString;

import java.sql.ResultSet;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author okkyh
 */
public class FormIsianTransaksiPembelian extends javax.swing.JFrame {

    /**
     * Creates new form FormIsianTransaksiPembelian
     */
    
    Map<String, Integer> pemasokMap = new HashMap<>();
    Map<String, Integer> gudangMap = new HashMap<>();
    private ViewTransaksiPembelian parentPembelianForm;
    private FormIsianPemasokTransaksi isianPemasokTransaksi;
    
    private String taskFormIsianPembelian = "CREATE";
    private String idUpdatePembelian = "";
    
    public FormIsianTransaksiPembelian() {
        
        isianPemasokTransaksi = new FormIsianPemasokTransaksi();
          
        initComponents();
        ImageIcon icon = new ImageIcon("src/_asset/78.png");
        setIconImage(icon.getImage());
        
        setLoadDataPembelianList("");
        refreshIsianPembelian();
        
        setTitle("Form Transaksi Pembelian");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
    
    public void setParentForm(ViewTransaksiPembelian parentForm) {
        this.parentPembelianForm = parentForm;
    }
    
    // Get kolom index.
    private int getColumnIndexByName(JTable table, String columnName) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (table.getColumnName(i).equals(columnName)) {
                return i;
            }
        }
        return -1; // Column not found
    }
    
    // Set total pembayaran.
    public void setTotalPembayaran() {
        
        int subtotalColumnIndex = getColumnIndexByName(tabelDetailProdukPembelian, "Subtotal (Rp)");
        
        if(subtotalColumnIndex != -1) {
            
            int totalPembelianRp = 0;
            
            for (int i = 0; i < tabelDetailProdukPembelian.getRowCount(); i++) {
            
                Object subtotalValue = tabelDetailProdukPembelian.getValueAt(i, subtotalColumnIndex);
                
                if(subtotalValue != null) {
                
                    totalPembelianRp += Integer.parseInt(subtotalValue.toString());
                    
                }
            
            }
            
            totalPembelianRpField.setText(String.valueOf(totalPembelianRp));
            
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
       
    private void getKodePembelianOtomatis() {
        
        try {
            Connection con = (Connection) database.Koneksi.koneksiDB();
            String sql = """
                         select concat('PB', LPAD(substring(pembelian_nomor_faktur, 3) + 1, 3, '0')) as next_kode
                         from pembelian
                         order by pembelian_nomor_faktur desc
                         limit 1;""";
            
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            String produkKodeValueOtomatis = "PB";
            
            if(rs.next()) {
                
                produkKodeValueOtomatis = rs.getString("next_kode");
                
            }else {
                produkKodeValueOtomatis = "PB001";
            }
            nomorFakturPembelianField.setText(produkKodeValueOtomatis);
            rs.close();
            ps.close();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        };
    }

    public void refreshIsianPembelian() {
        this.taskFormIsianPembelian = "CREATE";
        this.idUpdatePembelian = "";     
        getKodePembelianOtomatis();
        storePemasokPembelian();  
        storeGudangPembelian();
        pemasokPembelianCboField.setSelectedItem(0);
        totalPembelianRpField.setText("");
        DefaultTableModel tableModel = (DefaultTableModel) tabelDetailProdukPembelian.getModel();
        tableModel.setRowCount(0);
        tabelDetailProdukPembelian.repaint();
        tanggalPembelianField.setDate(new Date());
        hideColumnsByName(tabelDetailProdukPembelian, "Unix Id", "Produk Id", "Satuan Id");
    }
    
    public void setUpdateDetailPembelian(String pembelianId, String tanggalFaktur, String nomorFaktur, String pemasokNama, String totalRp) {
        try {
            this.taskFormIsianPembelian = "UPDATE";
            this.idUpdatePembelian = pembelianId;     
            getKodePembelianOtomatis();
            storePemasokPembelian();
            storeGudangPembelian();
            pemasokPembelianCboField.setSelectedItem(pemasokNama);
            nomorFakturPembelianField.setText(nomorFaktur);      
            totalPembelianRpField.setText(totalRp);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(tanggalFaktur);
            tanggalPembelianField.setDate(date);
            setLoadDataPembelianList(pembelianId);          
        } catch (ParseException ex) {
            Logger.getLogger(FormIsianTransaksiPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setLoadDataPembelianList(String pembelianId) {
        
        DefaultTableModel listTabelDetailPembelian = new DefaultTableModel();
        listTabelDetailPembelian.addColumn("Unix Id");
        listTabelDetailPembelian.addColumn("Produk Id");
        listTabelDetailPembelian.addColumn("Satuan Id");
        listTabelDetailPembelian.addColumn("Produk Nama");
        listTabelDetailPembelian.addColumn("Satuan Nama");
        listTabelDetailPembelian.addColumn("Harga");
        listTabelDetailPembelian.addColumn("Jumlah");
        listTabelDetailPembelian.addColumn("Diskon (Rp)");
        listTabelDetailPembelian.addColumn("Diskon (%)");
        listTabelDetailPembelian.addColumn("Subtotal (Rp)");
        
        try {
            Connection con = (Connection) database.Koneksi.koneksiDB();
            String sql = "call `searchTransaksiPembelianDetail`('"+pembelianId+"')";
            
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                listTabelDetailPembelian.addRow(new Object[] {
                    rs.getString("pbeli_detail_id"),
                    rs.getString("pbeli_detail_produk_id"),
                    rs.getString("pbeli_detail_satuan_id"),
                    rs.getString("produk_nama"),
                    rs.getString("satuan_nama"),
                    rs.getString("pbeli_detail_harga"),
                    rs.getString("pbeli_detail_jumlah"),
                    rs.getString("pbeli_detail_diskon_rp"),
                    rs.getString("pbeli_detail_persen"),
                    rs.getString("pbeli_detail_subtotal")
                });
            }
            
            tabelDetailProdukPembelian.setModel(new DefaultTableModel());
            
            tabelDetailProdukPembelian.setModel(listTabelDetailPembelian);
            hideColumnsByName(tabelDetailProdukPembelian, "Unix Id", "Produk Id", "Satuan Id");
            
        } catch (SQLException ex) {
        
            ex.printStackTrace();
        
        } catch (Exception e) {
            
            e.printStackTrace();
        
        }
    }
    
    public void storePemasokPembelian() {
        
        try {
            Connection con = (Connection) database.Koneksi.koneksiDB();
            String sql = "call `searchPemasok`('')";
            
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            List<String> items = new ArrayList<>();
            
            while(rs.next()) {
                
                int pemasokId = rs.getInt("pemasok_id");
                String pemasokNama = rs.getString("pemasok_nama");
                
                items.add(pemasokNama);
                
                pemasokMap.put(pemasokNama, pemasokId);
            }
            
            pemasokPembelianCboField.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));

            ps.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void storeGudangPembelian() {
        
        try {
            Connection con = (Connection) database.Koneksi.koneksiDB();
            String sql = "call `searchGudang`('')";
            
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            List<String> items = new ArrayList<>();
            
            while(rs.next()) {
                
                int gudangId = rs.getInt("gudang_id");
                String gudangNama = rs.getString("gudang_nama");
                
                items.add(gudangNama);
                
                gudangMap.put(gudangNama, gudangId);
                
            }
            
            gudangPembelianCboField.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));
            ps.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
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

        panelFormTransaksiPembelian = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tanggalPembelianField = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        nomorFakturPembelianField = new javax.swing.JTextField();
        PenjualanLabel = new javax.swing.JLabel();
        pemasokPembelianCboField = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnTambahDetailJual = new javax.swing.JButton();
        btnUbahDetailJual = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDetailProdukPembelian = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        totalPembelianRpField = new javax.swing.JTextField();
        gudangLabel = new javax.swing.JLabel();
        gudangPembelianCboField = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        caraBayarComboBox = new javax.swing.JComboBox<>();
        btnSimpanDetailPenjualan = new javax.swing.JButton();
        btnBatalPenjualan = new javax.swing.JButton();

        panelFormTransaksiPembelian.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal");

        tanggalPembelianField.setBackground(new java.awt.Color(204, 204, 0));
        tanggalPembelianField.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nomor Faktur");

        nomorFakturPembelianField.setEditable(false);
        nomorFakturPembelianField.setBackground(new java.awt.Color(51, 51, 51));
        nomorFakturPembelianField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        nomorFakturPembelianField.setForeground(new java.awt.Color(255, 255, 255));

        PenjualanLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        PenjualanLabel.setForeground(new java.awt.Color(255, 255, 255));
        PenjualanLabel.setText("Pemasok");

        pemasokPembelianCboField.setBackground(new java.awt.Color(51, 51, 51));
        pemasokPembelianCboField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        pemasokPembelianCboField.setForeground(new java.awt.Color(255, 255, 255));
        pemasokPembelianCboField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilihan Pemasok" }));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Detail Produk");

        btnTambahDetailJual.setBackground(new java.awt.Color(0, 0, 153));
        btnTambahDetailJual.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnTambahDetailJual.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahDetailJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/03.png"))); // NOI18N
        btnTambahDetailJual.setText("Tambah");
        btnTambahDetailJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahDetailJualActionPerformed(evt);
            }
        });

        btnUbahDetailJual.setBackground(new java.awt.Color(0, 102, 51));
        btnUbahDetailJual.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnUbahDetailJual.setForeground(new java.awt.Color(255, 255, 255));
        btnUbahDetailJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/19_edit.png"))); // NOI18N
        btnUbahDetailJual.setText("Ubah");
        btnUbahDetailJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahDetailJualActionPerformed(evt);
            }
        });

        tabelDetailProdukPembelian.setBackground(new java.awt.Color(51, 51, 51));
        tabelDetailProdukPembelian.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tabelDetailProdukPembelian.setForeground(new java.awt.Color(255, 255, 255));
        tabelDetailProdukPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ));
        jScrollPane1.setViewportView(tabelDetailProdukPembelian);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total");

        totalPembelianRpField.setEditable(false);
        totalPembelianRpField.setBackground(new java.awt.Color(51, 51, 51));
        totalPembelianRpField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        totalPembelianRpField.setForeground(new java.awt.Color(255, 255, 255));
        totalPembelianRpField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                totalPembelianRpFieldCaretUpdate(evt);
            }
        });

        gudangLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        gudangLabel.setForeground(new java.awt.Color(255, 255, 255));
        gudangLabel.setText("Gudang");

        gudangPembelianCboField.setBackground(new java.awt.Color(51, 51, 51));
        gudangPembelianCboField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        gudangPembelianCboField.setForeground(new java.awt.Color(255, 255, 255));
        gudangPembelianCboField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Gudang" }));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cara Bayar");

        caraBayarComboBox.setBackground(new java.awt.Color(51, 51, 51));
        caraBayarComboBox.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        caraBayarComboBox.setForeground(new java.awt.Color(255, 255, 255));
        caraBayarComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tunai", "Debit", "Transfer" }));

        btnSimpanDetailPenjualan.setBackground(new java.awt.Color(0, 0, 153));
        btnSimpanDetailPenjualan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSimpanDetailPenjualan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanDetailPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/46_save.png"))); // NOI18N
        btnSimpanDetailPenjualan.setText("Simpan");
        btnSimpanDetailPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanDetailPenjualanActionPerformed(evt);
            }
        });

        btnBatalPenjualan.setBackground(new java.awt.Color(0, 102, 0));
        btnBatalPenjualan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnBatalPenjualan.setForeground(new java.awt.Color(255, 255, 255));
        btnBatalPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/01.png"))); // NOI18N
        btnBatalPenjualan.setText("Batal");
        btnBatalPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalPenjualanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormTransaksiPembelianLayout = new javax.swing.GroupLayout(panelFormTransaksiPembelian);
        panelFormTransaksiPembelian.setLayout(panelFormTransaksiPembelianLayout);
        panelFormTransaksiPembelianLayout.setHorizontalGroup(
            panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormTransaksiPembelianLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormTransaksiPembelianLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(totalPembelianRpField, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFormTransaksiPembelianLayout.createSequentialGroup()
                        .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PenjualanLabel)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pemasokPembelianCboField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nomorFakturPembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tanggalPembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(172, 172, 172)
                        .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(gudangLabel))
                        .addGap(18, 18, 18)
                        .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gudangPembelianCboField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(caraBayarComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormTransaksiPembelianLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFormTransaksiPembelianLayout.createSequentialGroup()
                        .addComponent(btnSimpanDetailPenjualan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBatalPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelFormTransaksiPembelianLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(btnTambahDetailJual)
                            .addGap(18, 18, 18)
                            .addComponent(btnUbahDetailJual))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        panelFormTransaksiPembelianLayout.setVerticalGroup(
            panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormTransaksiPembelianLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggalPembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gudangLabel)
                        .addComponent(gudangPembelianCboField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomorFakturPembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(caraBayarComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pemasokPembelianCboField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PenjualanLabel))
                .addGap(30, 30, 30)
                .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahDetailJual, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbahDetailJual, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalPembelianRpField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(panelFormTransaksiPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatalPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpanDetailPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFormTransaksiPembelian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFormTransaksiPembelian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahDetailJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDetailJualActionPerformed
        // TODO add your handling code here:
        if( ! ( isianPemasokTransaksi.isVisible() ) ) { // Memeriksa apakah menuProduk sedang tidak tampil

            Component parentComponent = null; // Replace with the actual component reference
            isianPemasokTransaksi.setParentForm(this);
            isianPemasokTransaksi.refreshDetailPembelian();
            isianPemasokTransaksi.setLocationRelativeTo(parentComponent);
            isianPemasokTransaksi.setVisible(true);

        }
        else {

            isianPemasokTransaksi.requestFocusInWindow();
            isianPemasokTransaksi.setFocusable(true);
            JOptionPane.showMessageDialog(this, "Form Tambah Detail Sudah Terbuka, Silahkan Lengkapi data dahulu.");

        }
    }//GEN-LAST:event_btnTambahDetailJualActionPerformed

    private void btnUbahDetailJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahDetailJualActionPerformed
        int selectedRowIndex = tabelDetailProdukPembelian.getSelectedRow();

        if(selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Tidak Ada Data Yang Dipilih, Mohon Cek Kembali.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }else {
            setFormUpdateDetailPembelian();
        }
    }//GEN-LAST:event_btnUbahDetailJualActionPerformed

    private void totalPembelianRpFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_totalPembelianRpFieldCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_totalPembelianRpFieldCaretUpdate

    private void btnSimpanDetailPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanDetailPenjualanActionPerformed
        if(this.taskFormIsianPembelian.equals("UPDATE")){
            updatePembelian();
        }else{
            insertPembelian();
        }
    }//GEN-LAST:event_btnSimpanDetailPenjualanActionPerformed

    private void btnBatalPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalPenjualanActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnBatalPenjualanActionPerformed
        
    private int getLastedInserted(Connection con) throws SQLException {
        int lastInserted = -1;
        String sql = "SELECT LAST_INSERT_ID() as last_id";
        
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()) {
            lastInserted = rs.getInt("last_id");
        }
        return lastInserted;
    }
    
    public void insertPembelian() {
        
        Date selectedDate = tanggalPembelianField.getDate();      
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalPembelianValue = dateFormat.format(selectedDate);
        
        String nomorFakturPembelianValue = nomorFakturPembelianField.getText();
        String totalPembelianValue = totalPembelianRpField.getText();
        
        // Pemasok.
        String pemasokNamaPembelianValue = (String) pemasokPembelianCboField.getSelectedItem();
        
        int pemasokIdPembelianValue = 0;
        
        if(pemasokMap.containsKey(pemasokNamaPembelianValue)) {
        
            pemasokIdPembelianValue = pemasokMap.get(pemasokNamaPembelianValue);
        
        }else {
        
            JOptionPane.showMessageDialog(this, "Kategori not found in the map: " + pemasokNamaPembelianValue);
        
        }
        
        // Gudang.
        String gudangNamaPembelianValue = (String) gudangPembelianCboField.getSelectedItem();
        
        int gudangIdPembelianValue = 0;
        
        if(gudangMap.containsKey(gudangNamaPembelianValue)) {
            
            gudangIdPembelianValue = gudangMap.get(gudangNamaPembelianValue);
            
        }else {
        
            JOptionPane.showMessageDialog(this, "Kategori not found in the map: " + gudangNamaPembelianValue);
            
        }
        
        try {
            Connection con = (Connection) database.Koneksi.koneksiDB();
            String sql_pembelian = "call insertPembelian(?, ?, ?, ?, ?);";
            
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql_pembelian);
            
            ps.setString(1, nomorFakturPembelianValue);
            ps.setString(2, tanggalPembelianValue);
            ps.setInt(3, pemasokIdPembelianValue);
            ps.setInt(4, gudangIdPembelianValue);
            ps.setString(5, totalPembelianValue);     
            ps.executeUpdate();
            
            int lastInserted = getLastedInserted(con);
            
            DefaultTableModel tableModel = (DefaultTableModel) tabelDetailProdukPembelian.getModel();
            for(int i = 0; i < tableModel.getRowCount(); i++) {
                int produkId = (int) tableModel.getValueAt(i, tableModel.findColumn("Produk Id"));
                int satuanId = (int) tableModel.getValueAt(i, tableModel.findColumn("Satuan Id"));
                String harga = (String) tableModel.getValueAt(i, tableModel.findColumn("Harga"));
                String jumlah = (String) tableModel.getValueAt(i, tableModel.findColumn("Jumlah"));
                String diskonRp = (String) tableModel.getValueAt(i, tableModel.findColumn("Diskon (Rp)"));
                String diskonPersen = (String) tableModel.getValueAt(i, tableModel.findColumn("Diskon (%)"));
                String subtotalRp = (String) tableModel.getValueAt(i, tableModel.findColumn("Subtotal (Rp)"));
                
                insertDetailPembelian(
                        con, 
                        lastInserted,
                        produkId, 
                        satuanId, 
                        harga, 
                        jumlah, 
                        diskonRp, 
                        diskonPersen, 
                        subtotalRp
                );
            }
            
            JOptionPane.showMessageDialog(this, "Transaksi Berhasil Disimpan!");

            this.setVisible(false);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
    }
    
    public void updatePembelian() {
        
        Date selectedDate = tanggalPembelianField.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalPembelianValue = dateFormat.format(selectedDate);
        
        String nomorFakturPembelianValue = nomorFakturPembelianField.getText();
        String totalPembelianValue = totalPembelianRpField.getText();
        
        String pemasokNamaPembelianValue = (String) pemasokPembelianCboField.getSelectedItem();
        
        int pemasokIdPembelianValue = 0;
        
        if(pemasokMap.containsKey(pemasokNamaPembelianValue)) {
        
            pemasokIdPembelianValue = pemasokMap.get(pemasokNamaPembelianValue);
        
        }else {
        
            JOptionPane.showMessageDialog(this, "Kategori not found in the map: " + pemasokIdPembelianValue);
        
        }
        
        // Gudang.
        String gudangNamaPembelianValue = (String) gudangPembelianCboField.getSelectedItem();
        
        int gudangIdPembelianValue = 0;
        
        if(gudangMap.containsKey(gudangNamaPembelianValue)) {
            
            gudangIdPembelianValue = gudangMap.get(gudangNamaPembelianValue);
            
        }else {
        
            JOptionPane.showMessageDialog(this, "Kategori not found in the map: " + gudangNamaPembelianValue);
            
        }
        
        try {
            Connection con = (Connection) database.Koneksi.koneksiDB();
            String idUpdateValue = this.idUpdatePembelian;
            int masterIdPembelianValue = Integer.parseInt(idUpdateValue);
            

            
            String sql = "call `updatePembelian`(?, ?, ?, ?, ?, ?);";
            
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setString(1, nomorFakturPembelianValue);
            ps.setString(2, tanggalPembelianValue);          
            ps.setInt(3, pemasokIdPembelianValue);
            ps.setInt(4, gudangIdPembelianValue);
            ps.setString(5, totalPembelianValue);     
            ps.setInt(6, masterIdPembelianValue);
            ps.executeUpdate();
            
            deleteInsertDetailPembelian(con, idUpdateValue);
            
//            int lastInserted = getLastedInserted(con);
            
            int rowsUpdated = ps.executeUpdate();
            
            if(rowsUpdated > 0) {
            
                System.out.println("Update successful!");
            
            }else {
            
                System.out.println("Update failed. No rows were updated.");
            
            }
            
            DefaultTableModel tableModel = (DefaultTableModel) tabelDetailProdukPembelian.getModel();
            for(int i = 0; i < tableModel.getRowCount(); i++) {
//                int produkId = (int) tableModel.getValueAt(i, tableModel.findColumn("Produk Id"));
//                int satuanId = (int) tableModel.getValueAt(i, tableModel.findColumn("Satuan Id"));
                
                Object produkIdObj = tableModel.getValueAt(i, tableModel.findColumn("Produk Id"));
                Object satuanIdObj = tableModel.getValueAt(i, tableModel.findColumn("Satuan Id"));

                int produkId = 0;
                int satuanId = 0;
                
                if(produkIdObj != null) {
                    try{
                        produkId = Integer.parseInt(produkIdObj.toString());
                    }catch (NumberFormatException e) {
                            
                        System.out.println("Invalid Produk Id value: " + produkIdObj.toString());
                            
                    }
                }
                
                if(satuanIdObj != null) {
                    try{
                    satuanId = Integer.parseInt(satuanIdObj.toString());
                    }catch (NumberFormatException e) {
                        System.out.println("Invalid Satuan Id value: " + satuanIdObj.toString());
                    }
                }
                
                String harga = (String) tableModel.getValueAt(i, tableModel.findColumn("Harga"));
                String jumlah = (String) tableModel.getValueAt(i, tableModel.findColumn("Jumlah"));
                String diskonRp = (String) tableModel.getValueAt(i, tableModel.findColumn("Diskon (Rp)"));
                String diskonPersen = (String) tableModel.getValueAt(i, tableModel.findColumn("Diskon (%)"));
                String subtotalRp = (String) tableModel.getValueAt(i, tableModel.findColumn("Subtotal (Rp)"));
                
                insertDetailPembelian(
                    con,
                    masterIdPembelianValue,
                    produkId,
                    satuanId,
                    harga,
                    jumlah,
                    diskonRp,
                    diskonPersen,
                    subtotalRp
                );
            }
            
            JOptionPane.showMessageDialog(this, "Produk Berhasil Disimpan!");
            this.setVisible(false);
        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void deleteInsertDetailPembelian(Connection con, String idUpdateValue){
        try {
            String sql;
            // System.out.print(kategoriMap);
            sql = "DELETE FROM pembelian_detail WHERE pbeli_detail_pembelian_id='"+idUpdateValue+"'";
            
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.executeUpdate();
        
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
    
    private void insertDetailPembelian(
            Connection con, 
            int lastInserted,
            int produkId, 
            int satuanId, 
            String harga, 
            String jumlah, 
            String diskonRp, 
            String diskonPersen, 
            String subtotalRp
    ) throws SQLException {
        
        String sql = "call `insertPembelianDetail`(?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
        
        ps.setInt(1, lastInserted);
        ps.setInt(2, produkId);
        ps.setInt(3, satuanId);
        ps.setString(4, harga);
        ps.setString(5, jumlah);
        ps.setString(6, diskonRp);
        ps.setString(7, diskonPersen);
        ps.setString(8, subtotalRp);
        ps.executeUpdate();
    
    }   
    
    private void updateDetailPembelian(
            Connection con, 
            int masterIdPembelianValue,
            int gudangId,
            int produkId, 
            int satuanId, 
            String harga, 
            String jumlah, 
            String diskonRp, 
            String diskonPersen, 
            String subtotalRp
            
    ) throws SQLException {
        
        String sql = """
                     update pembelian_detail set pbeli_detail_pembelian_id = ?, pbeli_detail_produk_id = ?, pbeli_detail_satuan_id = ?, pbeli_detail_harga = ?,
                     pbeli_detail_jumlah = ?, pbeli_detail_diskon_rp = ?, pbeli_detail_persen = ?, pbeli_detail_subtotal = ?
                     where pbeli_detail_id = ? and pbeli_detail_produk_id = ?""";
        
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql); 
        ps.setInt(1, masterIdPembelianValue);
        ps.setInt(2, produkId);
        ps.setInt(3, satuanId);
        ps.setString(4, harga);
        ps.setString(5, jumlah);
        ps.setString(6, diskonRp);
        ps.setString(7, diskonPersen);
        ps.setString(8, subtotalRp);
        
        ps.executeUpdate();
        
    }
        
    public void setFormUpdateDetailPembelian() {
        if(!(isianPemasokTransaksi.isVisible())) {
            int selectedRowIndex = tabelDetailProdukPembelian.getSelectedRow();
            String unixId = (String) tabelDetailProdukPembelian.getValueAt(selectedRowIndex, 0);
            String produkNama = (String) tabelDetailProdukPembelian.getValueAt(selectedRowIndex, 3);
            String satuanNama = (String) tabelDetailProdukPembelian.getValueAt(selectedRowIndex, 4);
            String harga = (String) tabelDetailProdukPembelian.getValueAt(selectedRowIndex, 5);
            String jumlah = (String) tabelDetailProdukPembelian.getValueAt(selectedRowIndex, 6);
            String diskonRp = (String) tabelDetailProdukPembelian.getValueAt(selectedRowIndex, 7);
            String diskonPersen = (String) tabelDetailProdukPembelian.getValueAt(selectedRowIndex, 8);
            String subtotal = (String) tabelDetailProdukPembelian.getValueAt(selectedRowIndex, 9);
            
            Component parentComponent = null;
            isianPemasokTransaksi.setLocationRelativeTo(parentComponent);
            isianPemasokTransaksi.setParentForm(this);
            isianPemasokTransaksi.setUpdateFormIsianDetail(
                    unixId, 
                    produkNama, 
                    satuanNama, 
                    harga, 
                    jumlah, 
                    diskonRp, 
                    diskonPersen, 
                    subtotal
            );
            isianPemasokTransaksi.setVisible(true);
        }else {
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
    
    public void addRowDetailProdukPembelian(int produkId, int satuanId, String produkNamaValue, String satuanNamaValue, String hargaValue, String jumlahValue, String diskonRpValue, String diskonPersenValue, String subtotalValue) {
        DefaultTableModel model = (DefaultTableModel) tabelDetailProdukPembelian.getModel();
        String unixId = generateRandomString(5);
        model.addRow(new Object[]{unixId, produkId, satuanId, produkNamaValue, satuanNamaValue, hargaValue, jumlahValue, diskonRpValue, diskonPersenValue, subtotalValue});
        setTotalPembayaran();
    }

    public void updateRowDetailProdukPembelian(String unixIdPembelianDetail, int produkId, int satuanId, String produkNamaValue, String satuanNamaValue, String hargaValue, String jumlahValue, String diskonRpValue, String diskonPersenValue, String subtotalValue) {
        DefaultTableModel model = (DefaultTableModel) tabelDetailProdukPembelian.getModel();

        // Iterate through the rows to find the row with the specified unixIdUpdate
        for (int row = 0; row < model.getRowCount(); row++) {
            String unixId = (String) model.getValueAt(row, 0);

            // Check if the current row's unixId matches the specified unixIdUpdate
            if (unixId.equals(unixIdPembelianDetail)) {
                // Update the values for the found row
                model.setValueAt(produkId, row, 1);
                model.setValueAt(satuanId, row, 2);
                model.setValueAt(produkNamaValue, row, 3);
                model.setValueAt(satuanNamaValue, row, 4);
                model.setValueAt(hargaValue, row, 5);
                model.setValueAt(jumlahValue, row, 6);
                model.setValueAt(diskonRpValue, row, 7);
                model.setValueAt(diskonPersenValue, row, 8);
                model.setValueAt(subtotalValue, row, 9);
                
                break; // Break out of the loop since the update is done
            }
        }
        setTotalPembayaran();
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
            java.util.logging.Logger.getLogger(FormIsianTransaksiPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormIsianTransaksiPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormIsianTransaksiPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormIsianTransaksiPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormIsianTransaksiPembelian().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PenjualanLabel;
    private javax.swing.JButton btnBatalPenjualan;
    private javax.swing.JButton btnSimpanDetailPenjualan;
    private javax.swing.JButton btnTambahDetailJual;
    private javax.swing.JButton btnUbahDetailJual;
    private javax.swing.JComboBox<String> caraBayarComboBox;
    private javax.swing.JLabel gudangLabel;
    private javax.swing.JComboBox<String> gudangPembelianCboField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nomorFakturPembelianField;
    private javax.swing.JPanel panelFormTransaksiPembelian;
    private javax.swing.JComboBox<String> pemasokPembelianCboField;
    private javax.swing.JTable tabelDetailProdukPembelian;
    private com.toedter.calendar.JDateChooser tanggalPembelianField;
    private javax.swing.JTextField totalPembelianRpField;
    // End of variables declaration//GEN-END:variables

    

//    public void addRowDetailPembelian(int produkId, int satuanId, String produkNamaValue, String satuanNamaValue, String hargaValue, String jumlahValue, String diskonRpValue, String diskonPersenValue, String subtotalValue) {
//        DefaultTableModel tableModel = (DefaultTableModel) tabelDetailProdukPembelian.getModel();
//        String unixId = generateRandomString(5);
//        tableModel.addRow(new Object [] {unixId, produkId, satuanId, produkNamaValue, satuanNamaValue, hargaValue, jumlahValue, diskonRpValue, diskonPersenValue, subtotalValue});
//        setTotalPembayaran();
//    }
//    
//    public void updateRowDetailPembelian(String unixIdPembelianDetail, int produkId, int satuanId, String produkNamaValue, String satuanNamaValue, String hargaValue, String jumlahValue, String diskonRpValue, String diskonPersenValue, String subtotalValue) {
//        DefaultTableModel tableModel = (DefaultTableModel) tabelDetailProdukPembelian.getModel();
//        
//        for(int i = 0; i < tableModel.getRowCount(); i++) {
//            String unixId = (String) tableModel.getValueAt(i, 0);
//            
//            if(unixId.equals(unixIdPembelianDetail)) {
//                tableModel.setValueAt(produkId, i, 1);
//                tableModel.setValueAt(satuanId, i, 2);
//                tableModel.setValueAt(produkNamaValue, i, 3);
//                tableModel.setValueAt(satuanNamaValue, i, 4);
//                tableModel.setValueAt(hargaValue, i, 5);
//                tableModel.setValueAt(jumlahValue, i, 6);
//                tableModel.setValueAt(diskonRpValue, i, 7);
//                tableModel.setValueAt(diskonPersenValue, i, 8);
//                tableModel.setValueAt(subtotalValue, i, 9);
//                
//                break;
//            }
//        }
//        setTotalPembayaran();
//    }
    
//            int masterIdPembelianValue = 0; // Default value if parsing fails.
//            
//            try {
//                
//                System.out.println("Debug: idUpdateValue before parsing: '" + idUpdateValue + "'");
//                
//                if(!idUpdateValue.isEmpty()) {
//                
//                    masterIdPembelianValue = Integer.parseInt(idUpdateValue);
//                    
//                }else {
//                
//                    System.out.println("Error: idUpdateValue is Empty!");
//                    
//                    JOptionPane.showMessageDialog(this, "Error: ID is Empty!", "Error", JOptionPane.ERROR_MESSAGE);
//                    return; // Exit the method since the ID is not valid.
//                    
//                }
//                
//            } catch (NumberFormatException e) {
//                
//                System.out.println("Error: Invalid integer format for idUpdateValue!");
//                
//                JOptionPane.showMessageDialog(this, "Error: Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
//                
//                e.printStackTrace();
//                return;
//                
//            }   

    
    
    

    
    

}