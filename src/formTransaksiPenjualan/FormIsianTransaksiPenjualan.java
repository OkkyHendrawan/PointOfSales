/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formTransaksiPenjualan;

import com.mysql.jdbc.CallableStatement;
import database.Koneksi;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author HP
 */
public class FormIsianTransaksiPenjualan extends javax.swing.JFrame {

    JasperReport JasRep;
    JasperPrint JasPri;
    HashMap param = new HashMap();
    JasperDesign JasDes;
    /**
     * Creates new form FormIsianPenjualan
     */
    
    Map<String, Integer> pelangganMap = new HashMap<>();
    Map<String, Integer> gudangMap = new HashMap<>();
    private ViewTransaksiPenjualan parentPenjualanForm;
    private FormIsianProdukTransaksi isianProdukTransaksi;
    private String taskFormIsianPenjualan = "CREATE";
    private String idUpdatePenjualan = "";
    
    public FormIsianTransaksiPenjualan() {
        isianProdukTransaksi = new FormIsianProdukTransaksi();
        initComponents();
        ImageIcon icon = new ImageIcon("src/_asset/78.png");
        setIconImage(icon.getImage());
        addNumericFilter(bayarRpPenjualanField);
    }
    
    public void setParentForm(ViewTransaksiPenjualan parentForm) {
        this.parentPenjualanForm = parentForm;
    }
        
    private void addNumericFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
    
    private int getColumnIndexByName(JTable table, String columnName) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (table.getColumnName(i).equals(columnName)) {
                return i;
            }
        }
        return -1; // Column not found
    }
    
    public void setTotalPembayaran(){
        // Assuming your table model has a column named "Subtotal (Rp)"
        int subtotalColumnIndex = getColumnIndexByName(tableDetailProdukPenjualan, "Subtotal (Rp)");

        int totalPenjualanRp = 0;

        // Iterate over the rows of the table
        for (int row = 0; row < tableDetailProdukPenjualan.getRowCount(); row++) {
            // Get the value in the Subtotal (Rp) column for the current row
            Object subtotalValue = tableDetailProdukPenjualan.getValueAt(row, subtotalColumnIndex);

            // Ensure the value is not null
            if (subtotalValue != null) {
                // Parse the value to double and add it to the total
                totalPenjualanRp += Integer.parseInt(subtotalValue.toString());
            }
        }

        // Set the total in the totalPenjualanRpField
        totalPenjualanRpField.setText(String.valueOf(totalPenjualanRp));
        setTotalKembalian();
    }
    
    public void setTotalKembalian(){
        try{
            int totalKembalian = 0;
            int totalValue = Integer.parseInt(totalPenjualanRpField.getText());
            int bayarValue = Integer.parseInt(bayarRpPenjualanField.getText());
            totalKembalian = bayarValue-totalValue;
            if(totalKembalian > 0){
                kembalianRpPenjualanField.setText(String.valueOf(totalKembalian));
            }else{
                kembalianRpPenjualanField.setText(String.valueOf(0));
            }
        }catch (NumberFormatException e) {
            int subtotal = 0;
            kembalianRpPenjualanField.setText(String.valueOf(subtotal));
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
    
            private void getKodePenjualanOtomatis() {
            try {
                java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
                String sql = "CALL TransaksiPenjualanKodeOtomatis()";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                String produkKodeValueOtomatis = "PJ";
                if (rs.next()) {
                produkKodeValueOtomatis = rs.getString("next_code");
            }

                rs.close();
                pst.close();
                nomorFakturPenjualanField.setText(produkKodeValueOtomatis);
                
            } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
}

    
    public void refreshIsianPenjualan(){
        this.taskFormIsianPenjualan = "CREATE";
        this.idUpdatePenjualan = "";
        getKodePenjualanOtomatis();
        storePelangganPenjualan();
        storeGudangPenjualan();
        bayarRpPenjualanField.setText("0");
        caraBayarPenjualanField.setSelectedIndex(0);
        totalPenjualanRpField.setText("");
        kembalianRpPenjualanField.setText("");
        keteranganPenjualanField.setText("");
        pelangganPenjualanCboField.setSelectedItem(0);
        DefaultTableModel model = (DefaultTableModel) tableDetailProdukPenjualan.getModel();
        model.setRowCount(0);
        tableDetailProdukPenjualan.repaint();
        tanggalPenjualanField.setDate(new Date());
        hideColumnsByName(tableDetailProdukPenjualan, "Unix Id", "Produk Id", "Satuan ID");
    }
    
    public void setUpdateDetailPenjualan(String penjualanId, String pelangganNama, String tanggalFaktur, String nomorFaktur, String caraBayar, String totalRp, String bayarRp, String KembalianRp, String keterangan){
        try {
            this.taskFormIsianPenjualan = "UPDATE";
            this.idUpdatePenjualan = penjualanId;
            getKodePenjualanOtomatis();
            storePelangganPenjualan();
            storeGudangPenjualan();
            pelangganPenjualanCboField.setSelectedItem(pelangganNama);
            nomorFakturPenjualanField.setText(nomorFaktur);
            caraBayarPenjualanField.setSelectedItem(caraBayar);
            totalPenjualanRpField.setText(totalRp);
            bayarRpPenjualanField.setText(bayarRp);
            kembalianRpPenjualanField.setText(KembalianRp);
            keteranganPenjualanField.setText(keterangan);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(tanggalFaktur);
            tanggalPenjualanField.setDate(date);
            setLoadDataDetailPenjualanList(penjualanId);
        } catch (ParseException ex) {
            Logger.getLogger(FormIsianTransaksiPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setLoadDataDetailPenjualanList(String penjualanId){
        DefaultTableModel listTablePenjualanDetail = new DefaultTableModel();
        listTablePenjualanDetail.addColumn("Unix Id");
        listTablePenjualanDetail.addColumn("Produk Id");
        listTablePenjualanDetail.addColumn("Satuan Id");
        listTablePenjualanDetail.addColumn("Produk Nama");
        listTablePenjualanDetail.addColumn("Satuan Nama");
        listTablePenjualanDetail.addColumn("Harga");
        listTablePenjualanDetail.addColumn("Jumlah");
        listTablePenjualanDetail.addColumn("Disk (Rp)");
        listTablePenjualanDetail.addColumn("Disk (%)");
        listTablePenjualanDetail.addColumn("Subtotal (Rp)");
        

        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String sql = "CALL TransaksiPenjualanLoadDataList(?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, penjualanId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                listTablePenjualanDetail.addRow(new Object[]{
                    rs.getString("pjual_detail_id"),
                    rs.getString("pjual_detail_produk_id"),
                    rs.getString("pjual_detail_satuan_id"),
                    rs.getString("produk_nama"),
                    rs.getString("satuan_nama"),
                    rs.getString("pjual_detail_harga"),
                    rs.getString("pjual_detail_jumlah"),
                    rs.getString("pjual_detail_diskon_rp"),
                    rs.getString("pjual_detail_diskon_persen"),
                    rs.getString("pjual_detail_subtotal")
                });
            }
            
            tableDetailProdukPenjualan.setModel(new DefaultTableModel());

            tableDetailProdukPenjualan.setModel(listTablePenjualanDetail);
            hideColumnsByName(tableDetailProdukPenjualan, "Unix Id", "Produk Id", "Satuan ID");
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();  // or log the exception
        } catch (Exception e) {
            // handle exceptions, at least log them
            e.printStackTrace();
        }
    }
    
        public void storePelangganPenjualan() {
        try {
        java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
        
            // Gantilah nama_stored_procedure dengan nama stored procedure yang sesuai
            String storedProcedureCall = "{CALL TransaksiPenjualanStorePelanggan()}";
        
            try (CallableStatement callableStatement = (CallableStatement) conn.prepareCall(storedProcedureCall)) {
            ResultSet rs = callableStatement.executeQuery();
            
            List<String> items = new ArrayList<>();  // Initialize the list

            while (rs.next()) {
                int pelangganId = rs.getInt("pelanggan_id");
                String pelangganNama = rs.getString("pelanggan_nama");

                // Add the display value to the ComboBox
                items.add(pelangganNama);

                // Add the association to the Map
                pelangganMap.put(pelangganNama, pelangganId);
            }

            // Populate the ComboBox with the items
            pelangganPenjualanCboField.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));

            // Add a listener to get the selected value and use the Map to get the corresponding ID
            pelangganPenjualanCboField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedKategoriNama = (String) pelangganPenjualanCboField.getSelectedItem();
                    int selectedKategoriId = pelangganMap.get(selectedKategoriNama);
                    // Use the selectedKategoriId as needed
                }
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exception appropriately
    }
}

    
    public void storeGudangPenjualan() {
    try {
        java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
        
        // Gantilah nama_stored_procedure dengan nama stored procedure yang sesuai
        String storedProcedureCall = "{CALL TransaksiPenjualanStoreGudang()}";
        
        try (CallableStatement callableStatement = (CallableStatement) conn.prepareCall(storedProcedureCall)) {
            ResultSet rs = callableStatement.executeQuery();
            
            List<String> items = new ArrayList<>();  // Initialize the list

            while (rs.next()) {
                int gudangId = rs.getInt("gudang_id");
                String gudangNama = rs.getString("gudang_nama");

                // Add the display value to the ComboBox
                items.add(gudangNama);

                // Add the association to the Map
                gudangMap.put(gudangNama, gudangId);
            }

            // Populate the ComboBox with the items
            gudangPenjualanCboField.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));

            // Add a listener to get the selected value and use the Map to get the corresponding ID
            gudangPenjualanCboField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedKategoriNama = (String) gudangPenjualanCboField.getSelectedItem();
                    int selectedKategoriId = gudangMap.get(selectedKategoriNama);
                    // Use the selectedKategoriId as needed
                }
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exception appropriately
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
        jLabel2 = new javax.swing.JLabel();
        tanggalPenjualanField = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        nomorFakturPenjualanField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        pelangganPenjualanCboField = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        gudangPenjualanCboField = new javax.swing.JComboBox<>();
        keteranganPenjualanField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnTambahDetailJual = new javax.swing.JButton();
        btnUbahDetailJual = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDetailProdukPenjualan = new javax.swing.JTable();
        totalPenjualanRpField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        caraBayarPenjualanField = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        kembalianRpPenjualanField = new javax.swing.JTextField();
        bayarRpPenjualanField = new javax.swing.JTextField();
        btnSimpanDetailPenjualan = new javax.swing.JButton();
        btnBatalPenjualan = new javax.swing.JButton();
        btnCetakPenjualan = new javax.swing.JButton();

        setTitle("Form Isian Penjualan");

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal");

        tanggalPenjualanField.setBackground(new java.awt.Color(204, 204, 0));
        tanggalPenjualanField.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nomor Faktur");

        nomorFakturPenjualanField.setEditable(false);
        nomorFakturPenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        nomorFakturPenjualanField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        nomorFakturPenjualanField.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pelanggan");

        pelangganPenjualanCboField.setBackground(new java.awt.Color(51, 51, 51));
        pelangganPenjualanCboField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        pelangganPenjualanCboField.setForeground(new java.awt.Color(255, 255, 255));
        pelangganPenjualanCboField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Keterangan");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Gudang");

        gudangPenjualanCboField.setBackground(new java.awt.Color(51, 51, 51));
        gudangPenjualanCboField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        gudangPenjualanCboField.setForeground(new java.awt.Color(255, 255, 255));
        gudangPenjualanCboField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        keteranganPenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        keteranganPenjualanField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        keteranganPenjualanField.setForeground(new java.awt.Color(255, 255, 255));

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

        tableDetailProdukPenjualan.setBackground(new java.awt.Color(51, 51, 51));
        tableDetailProdukPenjualan.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tableDetailProdukPenjualan.setForeground(new java.awt.Color(255, 255, 255));
        tableDetailProdukPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Unix Id", "Produk Id", "Satuan Id", "Produk Nama", "Satuan Nama", "Harga", "Jumlah", "Disk (Rp)", "Disk (%)", "Subtotal (Rp)"
            }
        ));
        jScrollPane1.setViewportView(tableDetailProdukPenjualan);

        totalPenjualanRpField.setEditable(false);
        totalPenjualanRpField.setBackground(new java.awt.Color(51, 51, 51));
        totalPenjualanRpField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        totalPenjualanRpField.setForeground(new java.awt.Color(255, 255, 255));
        totalPenjualanRpField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                totalPenjualanRpFieldCaretUpdate(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cara Bayar");

        caraBayarPenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        caraBayarPenjualanField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        caraBayarPenjualanField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tunai", "Kartu", "Transfer" }));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Pembayaran");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Kembalian");

        kembalianRpPenjualanField.setEditable(false);
        kembalianRpPenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        kembalianRpPenjualanField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        kembalianRpPenjualanField.setForeground(new java.awt.Color(255, 255, 255));

        bayarRpPenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        bayarRpPenjualanField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bayarRpPenjualanField.setForeground(new java.awt.Color(255, 255, 255));
        bayarRpPenjualanField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bayarRpPenjualanFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bayarRpPenjualanFieldKeyReleased(evt);
            }
        });

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

        btnCetakPenjualan.setBackground(new java.awt.Color(0, 0, 102));
        btnCetakPenjualan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCetakPenjualan.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/11_print.png"))); // NOI18N
        btnCetakPenjualan.setText("Cetak");
        btnCetakPenjualan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCetakPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakPenjualanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTambahDetailJual)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUbahDetailJual)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nomorFakturPenjualanField)
                            .addComponent(tanggalPenjualanField, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(pelangganPenjualanCboField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(keteranganPenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gudangPenjualanCboField, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 162, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalPenjualanRpField, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(caraBayarPenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bayarRpPenjualanField, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(kembalianRpPenjualanField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCetakPenjualan)
                        .addGap(18, 18, 18)
                        .addComponent(btnSimpanDetailPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBatalPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(tanggalPenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(gudangPenjualanCboField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nomorFakturPenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pelangganPenjualanCboField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambahDetailJual, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUbahDetailJual, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(keteranganPenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalPenjualanRpField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(caraBayarPenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(bayarRpPenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(kembalianRpPenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(35, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSimpanDetailPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBatalPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCetakPenjualan))
                        .addGap(24, 24, 24))))
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

    private void btnTambahDetailJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDetailJualActionPerformed
        // TODO add your handling code here:
        if( ! ( isianProdukTransaksi.isVisible() ) ) { // Memeriksa apakah menuProduk sedang tidak tampil
            Component parentComponent = null; // Replace with the actual component reference
            isianProdukTransaksi.setParentForm(this);
            isianProdukTransaksi.refreshDetailPenjualan();
            isianProdukTransaksi.setLocationRelativeTo(parentComponent);
            isianProdukTransaksi.setVisible(true);
        }
        else {
            isianProdukTransaksi.requestFocusInWindow();
            isianProdukTransaksi.setFocusable(true);
            JOptionPane.showMessageDialog(this, "Form Tambah Detail Sudah Terbuka, Silahkan Lengkapi data dahulu.");
        }
    }//GEN-LAST:event_btnTambahDetailJualActionPerformed

    private void btnUbahDetailJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahDetailJualActionPerformed
        // TODO add your handling code here:
        int selectedRowIndex = tableDetailProdukPenjualan.getSelectedRow();

        if (selectedRowIndex == -1) {
            // No row is selected, show a message
            JOptionPane.showMessageDialog(this, "Tidak Ada Data Yang Dipilih, Mohon Cek Kembali.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            setFormUpdateDetailPenjualan();
        }
    }//GEN-LAST:event_btnUbahDetailJualActionPerformed

    private void totalPenjualanRpFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_totalPenjualanRpFieldCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_totalPenjualanRpFieldCaretUpdate

    private void bayarRpPenjualanFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bayarRpPenjualanFieldKeyPressed
        setTotalKembalian();
    }//GEN-LAST:event_bayarRpPenjualanFieldKeyPressed

    private void bayarRpPenjualanFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bayarRpPenjualanFieldKeyReleased
        setTotalKembalian();
    }//GEN-LAST:event_bayarRpPenjualanFieldKeyReleased

    private void btnSimpanDetailPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanDetailPenjualanActionPerformed

        if(this.taskFormIsianPenjualan.equals("UPDATE")){
            updatePenjualan();
        }else{
            insertPenjualan();
        }
        parentPenjualanForm.tampilDataPenjualanList("");
    }//GEN-LAST:event_btnSimpanDetailPenjualanActionPerformed

    private void btnBatalPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalPenjualanActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnBatalPenjualanActionPerformed

    private void btnCetakPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakPenjualanActionPerformed
        try {
            Connection konn = new Koneksi().koneksiDB();
            File file = new File("src/_cetak/notaPenjualan.jrxml");
            JasDes = JRXmlLoader.load(file);

            // Inisialisasi objek parameter
            Map<String, Object> param = new HashMap<>();
            param.put("penjualan_nomor_Faktur", nomorFakturPenjualanField.getText());

            // Kompilasi dan isi laporan
            JasperReport JasRep = JasperCompileManager.compileReport(JasDes);
            JasperPrint JasPri = JasperFillManager.fillReport(JasRep, param, konn);

            // Menampilkan laporan
            JasperViewer jasperViewer = new JasperViewer(JasPri, false);
            jasperViewer.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal membuka Laporan", "Cetak laporan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCetakPenjualanActionPerformed
        
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
    public void insertPenjualan(){
        // TODO add your handling code here:
        Date selectedDate = tanggalPenjualanField.getDate();

        // Convert the Date to a String or perform other operations
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalPenjualanValue = dateFormat.format(selectedDate);
    
        String nomorFakturPenjualanValue = (String) nomorFakturPenjualanField.getText();
        String bayarPenjualanValue = (String) bayarRpPenjualanField.getText();
        String caraBayarPenjualanValue = (String) caraBayarPenjualanField.getSelectedItem();
        String totalPenjualanValue = (String) totalPenjualanRpField.getText();
        String kembalianPenjualanValue = (String) kembalianRpPenjualanField.getText();
        String keteranganPenjualanValue = (String) keteranganPenjualanField.getText();
        String pelangganNamaPenjualanValue = (String) pelangganPenjualanCboField.getSelectedItem();
        int pelangganIdPenjualanValue  = 0;
        if (pelangganMap.containsKey(pelangganNamaPenjualanValue)) {
            pelangganIdPenjualanValue = pelangganMap.get(pelangganNamaPenjualanValue);
        } else {
            JOptionPane.showMessageDialog(this, "Pelanggan not found in the map: " + pelangganIdPenjualanValue);
        }
        
        String gudangNamaPenjualanValue = (String) gudangPenjualanCboField.getSelectedItem();
        int gudangIdPenjualanValue  = 0;
        if (gudangMap.containsKey(gudangNamaPenjualanValue)) {
            gudangIdPenjualanValue = gudangMap.get(gudangNamaPenjualanValue);
        } else {
            JOptionPane.showMessageDialog(this, "Gudang not found in the map: " + gudangIdPenjualanValue);
        }
        
        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
                String sql = "{CALL TransaksiPenjualanInsert(?,?,?,?,?,?,?,?,?)}";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, nomorFakturPenjualanValue);
            pst.setString(2, tanggalPenjualanValue);
            pst.setInt(3, pelangganIdPenjualanValue);
            pst.setInt(4, gudangIdPenjualanValue);
            pst.setString(5, totalPenjualanValue);
            pst.setString(6, bayarPenjualanValue);
            pst.setString(7, caraBayarPenjualanValue);
            pst.setString(8, kembalianPenjualanValue);
            pst.setString(9, keteranganPenjualanValue);
            pst.executeUpdate();
            // End Insert Header

            //Start Insert Detail produk
            // Get the last inserted ID
            int lastInsertedId = getLastInsertedId(conn);

            // Now insert into the detail table
            DefaultTableModel model = (DefaultTableModel) tableDetailProdukPenjualan.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                int produkId = (int) model.getValueAt(i, model.findColumn("Produk Id"));
                int satuanId = (int) model.getValueAt(i, model.findColumn("Satuan Id"));
                String harga = (String) model.getValueAt(i, model.findColumn("Harga"));
                String jumlah = (String) model.getValueAt(i, model.findColumn("Jumlah"));
                String diskRp = (String) model.getValueAt(i, model.findColumn("Disk (Rp)"));
                String diskPersen = (String) model.getValueAt(i, model.findColumn("Disk (%)"));
                String subtotalRp = (String) model.getValueAt(i, model.findColumn("Subtotal (Rp)"));

                // Insert into detail table
                insertPenjualanDetail(
                    conn,
                    lastInsertedId,
                    produkId,
                    satuanId,
                    harga,
                    jumlah,
                    diskRp,
                    diskPersen,
                    subtotalRp
                );
            }
            //End Insert Detail produk

            JOptionPane.showMessageDialog(this, "Transaksi Berhasil Disimpan!");

            this.setVisible(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Transaksi Gagal Disimpan!");
            // Handle the exception
            e.printStackTrace();  // or log the exception
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Transaksi Gagal Disimpan!");
            // handle exceptions, at least log them
            e.printStackTrace();
        }
    }
    
    public void updatePenjualan(){
        // TODO add your handling code here:
        Date selectedDate = tanggalPenjualanField.getDate();

        // Convert the Date to a String or perform other operations
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalPenjualanValue = dateFormat.format(selectedDate);
    
        String nomorFakturPenjualanValue = (String) nomorFakturPenjualanField.getText();
        String bayarPenjualanValue = (String) bayarRpPenjualanField.getText();
        String caraBayarPenjualanValue = (String) caraBayarPenjualanField.getSelectedItem();
        String totalPenjualanValue = (String) totalPenjualanRpField.getText();
        String kembalianPenjualanValue = (String) kembalianRpPenjualanField.getText();
        String keteranganPenjualanValue = (String) keteranganPenjualanField.getText();
        
        String pelangganNamaPenjualanValue = (String) pelangganPenjualanCboField.getSelectedItem();
        int pelangganIdPenjualanValue  = 0;
        if (pelangganMap.containsKey(pelangganNamaPenjualanValue)) {
            pelangganIdPenjualanValue = pelangganMap.get(pelangganNamaPenjualanValue);
        } else {
            JOptionPane.showMessageDialog(this, "Kategori not found in the map: " + pelangganIdPenjualanValue);
        }
        
        String gudangNamaPenjualanValue = (String) gudangPenjualanCboField.getSelectedItem();
        int gudangIdPenjualanValue  = 0;
        if (gudangMap.containsKey(gudangNamaPenjualanValue)) {
            gudangIdPenjualanValue = gudangMap.get(gudangNamaPenjualanValue);
        } else {
            JOptionPane.showMessageDialog(this, "Gudang not found in the map: " + gudangIdPenjualanValue);
        }
        
        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
            String idUpdateValue = this.idUpdatePenjualan;
            int masterIdPenjualanValue = Integer.parseInt(idUpdateValue);
            
                String sql = "{CALL TransaksiPenjualanUpdate(?,?,?,?,?,?,?,?,?,?)}";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, nomorFakturPenjualanValue);
            pst.setString(2, tanggalPenjualanValue);
            pst.setInt(3, pelangganIdPenjualanValue);
            pst.setInt(4, gudangIdPenjualanValue);
            pst.setString(5, totalPenjualanValue);
            pst.setString(6, bayarPenjualanValue);
            pst.setString(7, caraBayarPenjualanValue);
            pst.setString(8, kembalianPenjualanValue);
            pst.setString(9, keteranganPenjualanValue);
            pst.setInt(10, masterIdPenjualanValue);
            pst.executeUpdate();
            // End Insert Header

            deleteInsertDetailPenjualan(conn, idUpdateValue);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful!");
            } else {
                System.out.println("Update failed. No rows were updated.");
            }
            
            // Now insert into the detail table
            DefaultTableModel model = (DefaultTableModel) tableDetailProdukPenjualan.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                System.out.println(model.findColumn("Produk Id"));
                Object produkIdObj = model.getValueAt(i, model.findColumn("Produk Id"));
                Object satuanIdObj = model.getValueAt(i, model.findColumn("Satuan Id"));

                int produkId = 0; // Default value in case of failure
                int satuanId = 0; // Default value in case of failure

                if (produkIdObj != null) {
                    try {
                        produkId = Integer.parseInt(produkIdObj.toString());
                    } catch (NumberFormatException e) {
                        // Handle the error, for example:
                        System.out.println("Invalid Produk Id value: " + produkIdObj.toString());
                    }
                }

                if (satuanIdObj != null) {
                    try {
                        satuanId = Integer.parseInt(satuanIdObj.toString());
                    } catch (NumberFormatException e) {
                        // Handle the error, for example:
                        System.out.println("Invalid Satuan Id value: " + satuanIdObj.toString());
                    }
                }
                String harga = (String) model.getValueAt(i, model.findColumn("Harga"));
                String jumlah = (String) model.getValueAt(i, model.findColumn("Jumlah"));
                String diskRp = (String) model.getValueAt(i, model.findColumn("Disk (Rp)"));
                String diskPersen = (String) model.getValueAt(i, model.findColumn("Disk (%)"));
                String subtotalRp = (String) model.getValueAt(i, model.findColumn("Subtotal (Rp)"));
                
                // Insert into detail table
                insertPenjualanDetail(
                    conn,
                    masterIdPenjualanValue,
                    produkId,
                    satuanId,
                    harga,
                    jumlah,
                    diskRp,
                    diskPersen,
                    subtotalRp
                );
            }
            //End Insert Detail produk
            JOptionPane.showMessageDialog(this, "Produk Berhasil Disimpan!");
            this.setVisible(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Transaksi Gagal2 Disimpan!");
            // Handle the exception
            e.printStackTrace();  // or log the exception
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Transaksi Gagal3 Disimpan!");
            // handle exceptions, at least log them
            e.printStackTrace();
        }
    }
    
    public void deleteInsertDetailPenjualan(Connection conn, String idUpdateValue) {
    try {
        String sql = "CALL TransaksiPenjualanDetailDelete(?)";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, idUpdateValue);
        pst.executeUpdate();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
        e.printStackTrace();  // or log the exception
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
        e.printStackTrace();
    }
}

    
    private void insertPenjualanDetail(
    Connection conn,
    int lastInsertedId,
    int produkId,
    int satuanId,
    String harga,
    String jumlah,
    String diskRp,
    String diskPersen,
    String subtotalRp
) {
    try {
        String sql = "CALL TransaksiPenjualanDetailInsert(?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, lastInsertedId);
            pst.setInt(2, produkId);
            pst.setInt(3, satuanId);
            pst.setString(4, harga);
            pst.setString(5, jumlah);
            pst.setString(6, diskRp);
            pst.setString(7, diskPersen);
            pst.setString(8, subtotalRp);
            pst.executeUpdate();
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
        e.printStackTrace();  // or log the exception
    }
}

        
    public void setFormUpdateDetailPenjualan(){
        if( ! ( isianProdukTransaksi.isVisible() ) ) {
            int selectedRowIndex = tableDetailProdukPenjualan.getSelectedRow();
            String unixId = (String) tableDetailProdukPenjualan.getValueAt(selectedRowIndex, 0);
            String produkNama = (String) tableDetailProdukPenjualan.getValueAt(selectedRowIndex, 3);
            String satuanNama = (String) tableDetailProdukPenjualan.getValueAt(selectedRowIndex, 4);
            String harga = (String) tableDetailProdukPenjualan.getValueAt(selectedRowIndex, 5);
            String jumlah = (String) tableDetailProdukPenjualan.getValueAt(selectedRowIndex, 6);
            String diskRp = (String) tableDetailProdukPenjualan.getValueAt(selectedRowIndex, 7);
            String diskPersen = (String) tableDetailProdukPenjualan.getValueAt(selectedRowIndex, 8);
            String subtotalRp = (String) tableDetailProdukPenjualan.getValueAt(selectedRowIndex, 9);
        
            Component parentComponent = null;
            isianProdukTransaksi.setLocationRelativeTo(parentComponent);
            isianProdukTransaksi.setParentForm(this);
            isianProdukTransaksi.setUpdateFormIsianDetail(
                unixId,
                produkNama,
                satuanNama,
                harga,
                jumlah,
                diskRp,
                diskPersen,
                subtotalRp
            );
            isianProdukTransaksi.setVisible(true);
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
    
    public void addRowDetailProdukPenjualan(int produkId, int satuanId, String produkNamaValue, String satuanNamaValue, String hargaValue, String jumlahValue, String diskRpValue, String diskPersenValue, String subtotalValue) {
        DefaultTableModel model = (DefaultTableModel) tableDetailProdukPenjualan.getModel();
        String unixId = generateRandomString(5);
        model.addRow(new Object[]{unixId, produkId, satuanId, produkNamaValue, satuanNamaValue, hargaValue, jumlahValue, diskRpValue, diskPersenValue, subtotalValue});
        setTotalPembayaran();
    }
    
    public void updateRowDetailProdukPenjualan(String unixIdUpdate, int produkId, int satuanId, String produkNamaValue, String satuanNamaValue, String hargaValue, String jumlahValue, String diskRpValue, String diskPersenValue, String subtotalValue) {
        DefaultTableModel model = (DefaultTableModel) tableDetailProdukPenjualan.getModel();

        // Iterate through the rows to find the row with the specified unixIdUpdate
        for (int row = 0; row < model.getRowCount(); row++) {
            String unixId = (String) model.getValueAt(row, 0);

            // Check if the current row's unixId matches the specified unixIdUpdate
            if (unixId.equals(unixIdUpdate)) {
                // Update the values for the found row
                model.setValueAt(produkId, row, 1);
                model.setValueAt(satuanId, row, 2);
                model.setValueAt(produkNamaValue, row, 3);
                model.setValueAt(satuanNamaValue, row, 4);
                model.setValueAt(hargaValue, row, 5);
                model.setValueAt(jumlahValue, row, 6);
                model.setValueAt(diskRpValue, row, 7);
                model.setValueAt(diskPersenValue, row, 8);
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
            java.util.logging.Logger.getLogger(FormIsianTransaksiPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormIsianTransaksiPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormIsianTransaksiPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormIsianTransaksiPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormIsianTransaksiPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bayarRpPenjualanField;
    private javax.swing.JButton btnBatalPenjualan;
    private javax.swing.JButton btnCetakPenjualan;
    private javax.swing.JButton btnSimpanDetailPenjualan;
    private javax.swing.JButton btnTambahDetailJual;
    private javax.swing.JButton btnUbahDetailJual;
    private javax.swing.JComboBox<String> caraBayarPenjualanField;
    private javax.swing.JComboBox<String> gudangPenjualanCboField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kembalianRpPenjualanField;
    private javax.swing.JTextField keteranganPenjualanField;
    private javax.swing.JTextField nomorFakturPenjualanField;
    private javax.swing.JComboBox<String> pelangganPenjualanCboField;
    private javax.swing.JTable tableDetailProdukPenjualan;
    private com.toedter.calendar.JDateChooser tanggalPenjualanField;
    private javax.swing.JTextField totalPenjualanRpField;
    // End of variables declaration//GEN-END:variables
}
