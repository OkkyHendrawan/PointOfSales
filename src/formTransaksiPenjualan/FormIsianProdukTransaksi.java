/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formTransaksiPenjualan;

import com.mysql.jdbc.CallableStatement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author HP
 */
public class FormIsianProdukTransaksi extends javax.swing.JFrame {

    /**
     * Creates new form FormIsianProdukTransaksi
     */
    Map<String, Integer> produkCboMap = new HashMap<>();
    Map<String, Integer> satuanCboMap = new HashMap<>();
    private FormIsianTransaksiPenjualan parentProdukTransaksiForm;
    private String taskFormIsianPenjualanDetail = "CREATE";
    private String unixIdPenjualanDetail = "";

    public FormIsianProdukTransaksi() {
        initComponents();
        ImageIcon icon = new ImageIcon("src/_asset/78.png");
        setIconImage(icon.getImage());
        
        storeProdukDpenjualan();
        storeSatuanDpenjualan();
        
        addNumericFilter(jumlahDpenjualanField);
        addNumericFilter(diskPersenDpenjualanField);
        addNumericFilter(diskRpDpenjualanField);
    }
    
    public void setParentForm(FormIsianTransaksiPenjualan parentForm) {
        this.parentProdukTransaksiForm = parentForm;
    }
        
    private void addNumericFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
    
    public void setPerhitunganSubtotal() {
        try {
            int harga = Integer.parseInt(hargaDpenjualanField.getText());
            int diskPersen = Integer.parseInt(diskPersenDpenjualanField.getText());
            int diskRp = Integer.parseInt(diskRpDpenjualanField.getText());
            int jumlah = Integer.parseInt(jumlahDpenjualanField.getText());

            int subtotal = 0;

            if (diskPersen > 0) {
                // If there is a percentage discount
                int diskonAmount = (int) ((diskPersen / 100.0f) * harga);
                subtotal = (jumlah * harga) - diskonAmount;
            } else if (diskRp > 0) {
                // If there is a fixed amount discount
                subtotal = (jumlah * harga) - diskRp;
            } else {
                subtotal = (jumlah * harga);
            }

            // Perform additional calculations or validations as needed

            // Set the result in the field
            subtotalRpDpenjualanField.setText(String.valueOf(subtotal));
        } catch (NumberFormatException e) {
            // Handle the exception if parsing fails
            int subtotal = 0;
            subtotalRpDpenjualanField.setText(String.valueOf(subtotal));
        }
    }

    public void refreshDetailPenjualan(){
        this.unixIdPenjualanDetail = "";
        taskFormIsianPenjualanDetail = "CREATE";
        diskPersenDpenjualanField.setText("0");
        diskRpDpenjualanField.setText("0");
        hargaDpenjualanField.setText("0");
        jumlahDpenjualanField.setText("1");
        produkDpenjualanCboField.setSelectedItem(1);
        satuanDpenjualanCboField.setSelectedItem(-1);
        subtotalRpDpenjualanField.setText("0");
    }
    
    public void setUpdateFormIsianDetail(
        String unixId,
        String produkNama,
        String satuanNama,
        String harga,
        String jumlah,
        String diskRp,
        String diskPersen,
        String subtotalRp
    ){
        taskFormIsianPenjualanDetail = "UPDATE";
        this.unixIdPenjualanDetail = unixId;
        produkDpenjualanCboField.setSelectedItem(produkNama);
        satuanDpenjualanCboField.setSelectedItem(satuanNama);
        hargaDpenjualanField.setText(harga);
        jumlahDpenjualanField.setText(jumlah);
        diskRpDpenjualanField.setText(diskRp);
        diskPersenDpenjualanField.setText(diskPersen);
        subtotalRpDpenjualanField.setText(subtotalRp);
    }
    
        public void storeProdukDpenjualan() {
        // Assuming you have a database connection 'conn'

        try {
            java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();
        
        // Gantilah nama_stored_procedure dengan nama stored procedure yang sesuai
        String storedProcedureCall = "{CALL TransaksiPenjualanStoreProduk()}";
        
        try (CallableStatement callableStatement = (CallableStatement) conn.prepareCall(storedProcedureCall)) {
            ResultSet rs = callableStatement.executeQuery();

            List<String> items = new ArrayList<>();  // Initialize the list

            while (rs.next()) {
                int produkId = rs.getInt("produk_id");
                String produkNama = rs.getString("produk_nama");

                // Add the display value to the ComboBox
                items.add(produkNama);

                // Add the association to the Map
                produkCboMap.put(produkNama, produkId);
            }

            // Populate the ComboBox with the items
            produkDpenjualanCboField.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));

            // Add a listener to get the selected value and use the Map to get the corresponding ID
            produkDpenjualanCboField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedKategoriNama = (String) produkDpenjualanCboField.getSelectedItem();
                    int selectedKategoriId = produkCboMap.get(selectedKategoriNama);
                    // Use the selectedKategoriId as needed
                }
            });
        }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }
    
        public void storeSatuanDpenjualan() {
        // Assuming you have a database connection 'conn'

        try {
            String produkNama = (String) produkDpenjualanCboField.getSelectedItem();
        java.sql.Connection conn = (java.sql.Connection) database.Koneksi.koneksiDB();

        // Pemanggilan prosedur MySQL
        String sql = "CALL TransaksiPenjualanStoreSatuan(?)";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, produkNama);
        ResultSet rs = pst.executeQuery();

            List<String> items = new ArrayList<>();  // Initialize the list

            while (rs.next()) {
                int satuanId = rs.getInt("satuan_id");
                String satuanNama = rs.getString("satuan_nama");
                
                if(rs.getString("pdetail_default").equals("Ya")){
                    String hargaString = rs.getString("pdetail_harga");
                    if (!hargaString.isEmpty()) {
                        hargaDpenjualanField.setText(hargaString);
                    } else {
                        hargaDpenjualanField.setText("0");
                    }
                }

                // Add the display value to the ComboBox
                items.add(satuanNama);

                // Add the association to the Map
                satuanCboMap.put(satuanNama, satuanId);
            }

            // Populate the ComboBox with the items
            satuanDpenjualanCboField.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));
            
            // Add a listener to get the selected value and use the Map to get the corresponding ID
            satuanDpenjualanCboField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedKategoriNama = (String) satuanDpenjualanCboField.getSelectedItem();
                    int selectedKategoriId = satuanCboMap.get(selectedKategoriNama);
                    // Use the selectedKategoriId as needed
                }
            });

            pst.close();

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

        panelFormIsianProduk = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        produkDpenjualanCboField = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        satuanDpenjualanCboField = new javax.swing.JComboBox<>();
        hargaDpenjualanField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jumlahDpenjualanField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        diskRpDpenjualanField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        diskPersenDpenjualanField = new javax.swing.JTextField();
        btnSimpanDpenjualan = new javax.swing.JButton();
        btnBatalPenjualan = new javax.swing.JButton();
        subtotalRpDpenjualanField = new javax.swing.JTextField();

        setTitle("Detail Produk");

        panelFormIsianProduk.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Produk");

        produkDpenjualanCboField.setBackground(new java.awt.Color(51, 51, 51));
        produkDpenjualanCboField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        produkDpenjualanCboField.setForeground(new java.awt.Color(255, 255, 255));
        produkDpenjualanCboField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        produkDpenjualanCboField.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                produkDpenjualanCboFieldItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Satuan");

        satuanDpenjualanCboField.setBackground(new java.awt.Color(51, 51, 51));
        satuanDpenjualanCboField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        satuanDpenjualanCboField.setForeground(new java.awt.Color(255, 255, 255));
        satuanDpenjualanCboField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        hargaDpenjualanField.setEditable(false);
        hargaDpenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        hargaDpenjualanField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        hargaDpenjualanField.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Harga");

        jumlahDpenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        jumlahDpenjualanField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jumlahDpenjualanField.setForeground(new java.awt.Color(255, 255, 255));
        jumlahDpenjualanField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jumlahDpenjualanFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jumlahDpenjualanFieldKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Jumlah");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Diskon (Rp)");

        diskRpDpenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        diskRpDpenjualanField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        diskRpDpenjualanField.setForeground(new java.awt.Color(255, 255, 255));
        diskRpDpenjualanField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diskRpDpenjualanFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                diskRpDpenjualanFieldKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Diskon (%)");

        diskPersenDpenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        diskPersenDpenjualanField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        diskPersenDpenjualanField.setForeground(new java.awt.Color(255, 255, 255));
        diskPersenDpenjualanField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diskPersenDpenjualanFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                diskPersenDpenjualanFieldKeyReleased(evt);
            }
        });

        btnSimpanDpenjualan.setBackground(new java.awt.Color(0, 102, 102));
        btnSimpanDpenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/46_save.png"))); // NOI18N
        btnSimpanDpenjualan.setText("Simpan");
        btnSimpanDpenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanDpenjualanActionPerformed(evt);
            }
        });

        btnBatalPenjualan.setBackground(new java.awt.Color(102, 0, 102));
        btnBatalPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/01.png"))); // NOI18N
        btnBatalPenjualan.setText("Batal");
        btnBatalPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalPenjualanActionPerformed(evt);
            }
        });

        subtotalRpDpenjualanField.setEditable(false);
        subtotalRpDpenjualanField.setBackground(new java.awt.Color(51, 51, 51));
        subtotalRpDpenjualanField.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        subtotalRpDpenjualanField.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelFormIsianProdukLayout = new javax.swing.GroupLayout(panelFormIsianProduk);
        panelFormIsianProduk.setLayout(panelFormIsianProdukLayout);
        panelFormIsianProdukLayout.setHorizontalGroup(
            panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormIsianProdukLayout.createSequentialGroup()
                        .addComponent(subtotalRpDpenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSimpanDpenjualan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatalPenjualan)
                        .addGap(21, 21, 21))
                    .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                        .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hargaDpenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jumlahDpenjualanField, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                    .addComponent(diskRpDpenjualanField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(diskPersenDpenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(108, 108, 108))
                    .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(satuanDpenjualanCboField, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(produkDpenjualanCboField, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelFormIsianProdukLayout.setVerticalGroup(
            panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(produkDpenjualanCboField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(satuanDpenjualanCboField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(11, 11, 11)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hargaDpenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jumlahDpenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFormIsianProdukLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(diskRpDpenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(diskPersenDpenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormIsianProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSimpanDpenjualan)
                        .addComponent(btnBatalPenjualan))
                    .addComponent(subtotalRpDpenjualanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
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

    private void produkDpenjualanCboFieldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_produkDpenjualanCboFieldItemStateChanged
        // TODO add your handling code here:
        storeSatuanDpenjualan();
        setPerhitunganSubtotal();
    }//GEN-LAST:event_produkDpenjualanCboFieldItemStateChanged

    private void jumlahDpenjualanFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahDpenjualanFieldKeyPressed
        setPerhitunganSubtotal();
    }//GEN-LAST:event_jumlahDpenjualanFieldKeyPressed

    private void jumlahDpenjualanFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahDpenjualanFieldKeyReleased
        setPerhitunganSubtotal();
    }//GEN-LAST:event_jumlahDpenjualanFieldKeyReleased

    private void diskRpDpenjualanFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskRpDpenjualanFieldKeyPressed
        diskPersenDpenjualanField.setText("0");
        setPerhitunganSubtotal();
    }//GEN-LAST:event_diskRpDpenjualanFieldKeyPressed

    private void diskRpDpenjualanFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskRpDpenjualanFieldKeyReleased
        setPerhitunganSubtotal();
    }//GEN-LAST:event_diskRpDpenjualanFieldKeyReleased

    private void diskPersenDpenjualanFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskPersenDpenjualanFieldKeyPressed
        diskRpDpenjualanField.setText("0");
        setPerhitunganSubtotal();

    }//GEN-LAST:event_diskPersenDpenjualanFieldKeyPressed

    private void diskPersenDpenjualanFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskPersenDpenjualanFieldKeyReleased
        setPerhitunganSubtotal();
    }//GEN-LAST:event_diskPersenDpenjualanFieldKeyReleased

    private void btnSimpanDpenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanDpenjualanActionPerformed
        if(taskFormIsianPenjualanDetail.equalsIgnoreCase("UPDATE")){
            updateRowPenjualanDetail();
        }else{
            insertRowPenjualanDetail();
        }
    }//GEN-LAST:event_btnSimpanDpenjualanActionPerformed

    private void btnBatalPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalPenjualanActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnBatalPenjualanActionPerformed
    
    public void insertRowPenjualanDetail(){
        String produkNamaValue = (String) produkDpenjualanCboField.getSelectedItem();
        String satuanNamaValue = (String) satuanDpenjualanCboField.getSelectedItem();
        
        String hargaValue = hargaDpenjualanField.getText();
        String jumlahValue = jumlahDpenjualanField.getText();
        String diskRpValue = diskRpDpenjualanField.getText();
        String diskPersenValue = diskPersenDpenjualanField.getText();
        String subtotalValue = subtotalRpDpenjualanField.getText();
        
        // Map<String, Integer> satuanCboMap = new HashMap<>();

        try {
            // Check if the selected value exists in the satuanMap
            if (produkCboMap.containsKey(produkNamaValue)) {
                // Get the kategoriId from the map based on the selected kategoriNama
                int produkId = produkCboMap.get(produkNamaValue);
                int satuanId = satuanCboMap.get(satuanNamaValue);
                
                // Call the method in the parent FormIsianTransaksiPenjualan to add the row
                parentProdukTransaksiForm.addRowDetailProdukPenjualan(
                    produkId, satuanId, produkNamaValue, satuanNamaValue, hargaValue, jumlahValue, diskRpValue, diskPersenValue, subtotalValue
                );
                this.dispose();
                JOptionPane.showMessageDialog(this, "Produk Detail Berhasil Disimpan!");
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Produk not found in the map: " + produkNamaValue);
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
            // handle exceptions, at least log them
            e.printStackTrace();
        }
        // Handle the exception
    }
    
    public void updateRowPenjualanDetail(){
        String produkNamaValue = (String) produkDpenjualanCboField.getSelectedItem();
        String satuanNamaValue = (String) satuanDpenjualanCboField.getSelectedItem();
        
        String hargaValue = hargaDpenjualanField.getText();
        String jumlahValue = jumlahDpenjualanField.getText();
        String diskRpValue = diskRpDpenjualanField.getText();
        String diskPersenValue = diskPersenDpenjualanField.getText();
        String subtotalValue = subtotalRpDpenjualanField.getText();
        
        // Map<String, Integer> satuanCboMap = new HashMap<>();

        try {
            // Check if the selected value exists in the satuanMap
            if (produkCboMap.containsKey(produkNamaValue)) {
                // Get the kategoriId from the map based on the selected kategoriNama
                int produkId = produkCboMap.get(produkNamaValue);
                int satuanId = satuanCboMap.get(satuanNamaValue);
                
                // Call the method in the parent FormIsianTransaksiPenjualan to add the row
                parentProdukTransaksiForm.updateRowDetailProdukPenjualan(
                    this.unixIdPenjualanDetail, produkId, satuanId, produkNamaValue, satuanNamaValue, hargaValue, jumlahValue, diskRpValue, diskPersenValue, subtotalValue
                );
                this.dispose();
                JOptionPane.showMessageDialog(this, "Produk Detail Berhasil Disimpan!");
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Produk not found in the map: " + produkNamaValue);
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
            // handle exceptions, at least log them
            e.printStackTrace();
        }
        // Handle the exception
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
            java.util.logging.Logger.getLogger(FormIsianProdukTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormIsianProdukTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormIsianProdukTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormIsianProdukTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormIsianProdukTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatalPenjualan;
    private javax.swing.JButton btnSimpanDpenjualan;
    private javax.swing.JTextField diskPersenDpenjualanField;
    private javax.swing.JTextField diskRpDpenjualanField;
    private javax.swing.JTextField hargaDpenjualanField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jumlahDpenjualanField;
    private javax.swing.JPanel panelFormIsianProduk;
    private javax.swing.JComboBox<String> produkDpenjualanCboField;
    private javax.swing.JComboBox<String> satuanDpenjualanCboField;
    private javax.swing.JTextField subtotalRpDpenjualanField;
    // End of variables declaration//GEN-END:variables
}
