/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formTransaksiPembelian;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import formTransaksiPenjualan.FormIsianTransaksiPenjualan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author okkyh
 */
public class FormIsianPemasokTransaksi extends javax.swing.JFrame {

    /**
     * Creates new form FormIsianPemasokTransaksi
     */
    
    private FormIsianTransaksiPembelian parentPemasokTransaksiForm;
    Map<String, Integer> produkCboMap = new HashMap<>();
    Map<String, Integer> satuanCboMap = new HashMap<>();
    private String taskFormIsianPembelianDetail = "CREATE";
    private String unixIdPembelianDetail = "";
    
    public FormIsianPemasokTransaksi() {
        initComponents();
        ImageIcon icon = new ImageIcon("src/_asset/78.png");
        setIconImage(icon.getImage());
        
        storeProdukDpembelian();
        storeSatuanDpembelian();
        
        addNumericFilter(jumlahDpembelianField);
        addNumericFilter(diskonPersenDpembelianField);
        addNumericFilter(diskonRpDpembelianField);
        
        setTitle("Form Pemasok Produk");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void setParentForm(FormIsianTransaksiPembelian parentForm) {
        this.parentPemasokTransaksiForm = parentForm;
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
    
    // Hitung subtotal.
    public void setPerhitunganSubtotal() {
        try {
            int harga = Integer.parseInt(hargaDpembelianField.getText());
            int diskPersen = Integer.parseInt(diskonPersenDpembelianField.getText());
            int diskRp = Integer.parseInt(diskonRpDpembelianField.getText());
            int jumlah = Integer.parseInt(jumlahDpembelianField.getText());

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
            subtotalRpDpembelianField.setText(String.valueOf(subtotal));
        } catch (NumberFormatException e) {
            // Handle the exception if parsing fails
            int subtotal = 0;
            subtotalRpDpembelianField.setText(String.valueOf(subtotal));
        }
    }
    
    // Refresh detail pembelian.
    public void refreshDetailPembelian() {
        this.unixIdPembelianDetail = "";
        taskFormIsianPembelianDetail = "CREATE";
        produkDpembelianCboField.setSelectedItem(1);
        satuanDpembelianField.setSelectedItem(-1);
        hargaDpembelianField.setText("0");
        jumlahDpembelianField.setText("1");
        diskonRpDpembelianField.setText("0");
        diskonPersenDpembelianField.setText("0");
        subtotalRpDpembelianField.setText("0");
    }
    
    // Set update detail.
    public void setUpdateFormIsianDetail(
            String unixId, 
            String produkNama, 
            String satuanNama, 
            String harga, 
            String jumlah, 
            String diskonRp, 
            String diskonPersen, 
            String subtotalRp
    ) {
        taskFormIsianPembelianDetail = "UPDATE";
        this.unixIdPembelianDetail = unixId;
        produkDpembelianCboField.setSelectedItem(produkNama);
        satuanDpembelianField.setSelectedItem(satuanNama);
        hargaDpembelianField.setText(harga);
        jumlahDpembelianField.setText(jumlah);
        diskonRpDpembelianField.setText(diskonRp);
        diskonPersenDpembelianField.setText(diskonPersen);
        subtotalRpDpembelianField.setText(subtotalRp);
    }
    
    // Detail produk pembelian.
    public void storeProdukDpembelian() {
        
        try {
            Connection con = (Connection) database.Koneksi.koneksiDB();
            String sql = "select produk_id, produk_nama from produk;";
            
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            List<String> items = new ArrayList<>();
            
            while(rs.next()) {
                int produkId = rs.getInt("produk_id");
                String produkNama = rs.getString("produk_nama"); 
                
                items.add(produkNama);
                
                produkCboMap.put(produkNama, produkId);
            }           
            
            produkDpembelianCboField.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));
            
            ps.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Detail pemasok pembelian.
    public void storeSatuanDpembelian() {
        
        try {
            String produkNama = (String) produkDpembelianCboField.getSelectedItem();
            Connection con = (Connection) database.Koneksi.koneksiDB();
            
            String sql = """
                         select satuan_id, satuan_nama, pdetail_harga, pdetail_default
                         from produk_satuan
                         left join produk_detail on produk_detail.pdetail_satuan_id = produk_satuan.satuan_id
                         left join produk on produk.produk_id = produk_detail.pdetail_produk_id
                         where produk.produk_nama = '"""+produkNama+"'\n" +
                            "order by produk_detail.pdetail_default DESC;";
            
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            List<String> items = new ArrayList<>(); 
            
            while(rs.next()) {
                int satuanId = rs.getInt("satuan_id");
                String satuanNama = rs.getString("satuan_nama");
                
                if(rs.getString("pdetail_default").equals("Ya")) {
                    String hargaString = rs.getString("pdetail_harga");
                    
                    if(!hargaString.isEmpty()) {
                        hargaDpembelianField.setText(hargaString);
                    }else {
                        hargaDpembelianField.setText("0");
                    }
                    
                }
                
                items.add(satuanNama);
                
                satuanCboMap.put(satuanNama, satuanId);
                
            }            
            
            satuanDpembelianField.setModel(new DefaultComboBoxModel<>(items.toArray(new String[0])));      
            
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

        panelFormIsianPemasok = new javax.swing.JPanel();
        produkLabel = new javax.swing.JLabel();
        produkDpembelianCboField = new javax.swing.JComboBox<>();
        satuanDpembelianField = new javax.swing.JComboBox<>();
        satuanLabel = new javax.swing.JLabel();
        hargaLabel = new javax.swing.JLabel();
        hargaDpembelianField = new javax.swing.JTextField();
        jumlahLabel = new javax.swing.JLabel();
        jumlahDpembelianField = new javax.swing.JTextField();
        diskonRpLabel = new javax.swing.JLabel();
        diskonRpDpembelianField = new javax.swing.JTextField();
        subtotalRpDpembelianField = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        diskonPersenDpembelianField = new javax.swing.JTextField();
        diskonPersenLabel = new javax.swing.JLabel();

        panelFormIsianPemasok.setBackground(new java.awt.Color(0, 0, 102));

        produkLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        produkLabel.setForeground(new java.awt.Color(255, 255, 255));
        produkLabel.setText("Produk");

        produkDpembelianCboField.setBackground(new java.awt.Color(51, 51, 51));
        produkDpembelianCboField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        produkDpembelianCboField.setForeground(new java.awt.Color(255, 255, 255));
        produkDpembelianCboField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilihan Anda" }));
        produkDpembelianCboField.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                produkDpembelianCboFieldItemStateChanged(evt);
            }
        });

        satuanDpembelianField.setBackground(new java.awt.Color(51, 51, 51));
        satuanDpembelianField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        satuanDpembelianField.setForeground(new java.awt.Color(255, 255, 255));
        satuanDpembelianField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        satuanLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        satuanLabel.setForeground(new java.awt.Color(255, 255, 255));
        satuanLabel.setText("Satuan");

        hargaLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        hargaLabel.setForeground(new java.awt.Color(255, 255, 255));
        hargaLabel.setText("Harga");

        hargaDpembelianField.setEditable(false);
        hargaDpembelianField.setBackground(new java.awt.Color(51, 51, 51));
        hargaDpembelianField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        hargaDpembelianField.setForeground(new java.awt.Color(255, 255, 255));

        jumlahLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jumlahLabel.setForeground(new java.awt.Color(255, 255, 255));
        jumlahLabel.setText("Jumlah");

        jumlahDpembelianField.setBackground(new java.awt.Color(51, 51, 51));
        jumlahDpembelianField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jumlahDpembelianField.setForeground(new java.awt.Color(255, 255, 255));
        jumlahDpembelianField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jumlahDpembelianFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jumlahDpembelianFieldKeyReleased(evt);
            }
        });

        diskonRpLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        diskonRpLabel.setForeground(new java.awt.Color(255, 255, 255));
        diskonRpLabel.setText("Diskon (Rp)");

        diskonRpDpembelianField.setBackground(new java.awt.Color(51, 51, 51));
        diskonRpDpembelianField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        diskonRpDpembelianField.setForeground(new java.awt.Color(255, 255, 255));
        diskonRpDpembelianField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diskonRpDpembelianFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                diskonRpDpembelianFieldKeyReleased(evt);
            }
        });

        subtotalRpDpembelianField.setBackground(new java.awt.Color(51, 51, 51));
        subtotalRpDpembelianField.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        subtotalRpDpembelianField.setForeground(new java.awt.Color(255, 255, 255));

        btnSimpan.setBackground(new java.awt.Color(0, 102, 102));
        btnSimpan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/46_save.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(102, 0, 102));
        btnBatal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/01.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        diskonPersenDpembelianField.setBackground(new java.awt.Color(51, 51, 51));
        diskonPersenDpembelianField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        diskonPersenDpembelianField.setForeground(new java.awt.Color(255, 255, 255));
        diskonPersenDpembelianField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diskonPersenDpembelianFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                diskonPersenDpembelianFieldKeyReleased(evt);
            }
        });

        diskonPersenLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        diskonPersenLabel.setForeground(new java.awt.Color(255, 255, 255));
        diskonPersenLabel.setText("Diskon (%)");

        javax.swing.GroupLayout panelFormIsianPemasokLayout = new javax.swing.GroupLayout(panelFormIsianPemasok);
        panelFormIsianPemasok.setLayout(panelFormIsianPemasokLayout);
        panelFormIsianPemasokLayout.setHorizontalGroup(
            panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormIsianPemasokLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subtotalRpDpembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFormIsianPemasokLayout.createSequentialGroup()
                        .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(produkLabel)
                            .addComponent(satuanLabel)
                            .addComponent(hargaLabel)
                            .addComponent(jumlahLabel)
                            .addComponent(diskonRpLabel))
                        .addGap(24, 24, 24)
                        .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(hargaDpembelianField)
                                .addComponent(produkDpembelianCboField, 0, 160, Short.MAX_VALUE)
                                .addComponent(satuanDpembelianField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jumlahDpembelianField))
                            .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelFormIsianPemasokLayout.createSequentialGroup()
                                    .addComponent(diskonRpDpembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(diskonPersenLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(diskonPersenDpembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelFormIsianPemasokLayout.createSequentialGroup()
                                    .addGap(103, 103, 103)
                                    .addComponent(btnSimpan)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnBatal))))))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        panelFormIsianPemasokLayout.setVerticalGroup(
            panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormIsianPemasokLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(produkLabel)
                    .addComponent(produkDpembelianCboField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(satuanLabel)
                    .addComponent(satuanDpembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hargaLabel)
                    .addComponent(hargaDpembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jumlahLabel)
                    .addComponent(jumlahDpembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(diskonRpLabel)
                    .addComponent(diskonRpDpembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diskonPersenLabel)
                    .addComponent(diskonPersenDpembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormIsianPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subtotalRpDpembelianField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFormIsianPemasok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFormIsianPemasok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void produkDpembelianCboFieldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_produkDpembelianCboFieldItemStateChanged
        storeSatuanDpembelian();
        setPerhitunganSubtotal();
    }//GEN-LAST:event_produkDpembelianCboFieldItemStateChanged

    private void jumlahDpembelianFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahDpembelianFieldKeyPressed
        setPerhitunganSubtotal();
    }//GEN-LAST:event_jumlahDpembelianFieldKeyPressed

    private void jumlahDpembelianFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahDpembelianFieldKeyReleased
        setPerhitunganSubtotal();
    }//GEN-LAST:event_jumlahDpembelianFieldKeyReleased

    private void diskonRpDpembelianFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskonRpDpembelianFieldKeyPressed
        diskonPersenDpembelianField.setText("0");
        setPerhitunganSubtotal();
    }//GEN-LAST:event_diskonRpDpembelianFieldKeyPressed

    private void diskonRpDpembelianFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskonRpDpembelianFieldKeyReleased
        setPerhitunganSubtotal();
    }//GEN-LAST:event_diskonRpDpembelianFieldKeyReleased

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(taskFormIsianPembelianDetail.equalsIgnoreCase("UPDATE")){
            updateRowPembelianDetail();
        }else{
            insertRowPembelianDetail();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void diskonPersenDpembelianFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskonPersenDpembelianFieldKeyPressed
        diskonRpDpembelianField.setText("0");
        setPerhitunganSubtotal();
    }//GEN-LAST:event_diskonPersenDpembelianFieldKeyPressed

    private void diskonPersenDpembelianFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diskonPersenDpembelianFieldKeyReleased
        setPerhitunganSubtotal();
    }//GEN-LAST:event_diskonPersenDpembelianFieldKeyReleased
    
    // Insert tabel detail pembelian.
    public void insertRowPembelianDetail() {
        String produkNamaValue = (String) produkDpembelianCboField.getSelectedItem();
        String satuanNamaValue = (String) satuanDpembelianField.getSelectedItem().toString();        
        
        String hargaValue = hargaDpembelianField.getText();
        String jumlahValue = jumlahDpembelianField.getText();
        String diskonRpValue = diskonRpDpembelianField.getText();
        String diskonPersenValue = diskonPersenDpembelianField.getText();
        String subtotalValue = subtotalRpDpembelianField.getText();

        try{
            if(produkCboMap.containsKey(produkNamaValue)) {
                
                int produkId = produkCboMap.get(produkNamaValue);
                int satuanId = satuanCboMap.get(satuanNamaValue);
            
                parentPemasokTransaksiForm.addRowDetailProdukPembelian(
                        produkId, satuanId, produkNamaValue, satuanNamaValue, hargaValue, jumlahValue, diskonRpValue, diskonPersenValue, subtotalValue
                );
                
                this.dispose();
                JOptionPane.showMessageDialog(this, "Berhasil disimpan");
                this.setVisible(false);
                
            }else {
                JOptionPane.showMessageDialog(this, "Produk not found in the map: " + produkNamaValue);
            }       
        
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Produk Gagal Disimpan!");
            e.printStackTrace();
        }
    }
    
    // Update tabel detail pembelian.
    public void updateRowPembelianDetail() {
        String produkNamaValue = (String) produkDpembelianCboField.getSelectedItem();
        String satuanNamaValue = (String) satuanDpembelianField.getSelectedItem().toString();
        
        String hargaValue = hargaDpembelianField.getText();
        String jumlahValue = jumlahDpembelianField.getText();
        String diskonRpValue = diskonRpDpembelianField.getText();
        String diskonPersenValue = diskonPersenDpembelianField.getText();
        String subtotalValue = subtotalRpDpembelianField.getText();
        
        try{
            
            if(produkCboMap.containsKey(produkNamaValue)) {
                
                int produkId = produkCboMap.get(produkNamaValue);
                int satuanId = satuanCboMap.get(satuanNamaValue);
                
                parentPemasokTransaksiForm.updateRowDetailProdukPembelian(
                        this.unixIdPembelianDetail, produkId, satuanId, produkNamaValue, satuanNamaValue, hargaValue, jumlahValue, diskonRpValue, diskonPersenValue, subtotalValue
                );
                
                this.dispose();
                JOptionPane.showMessageDialog(this, "Berhasil di update");
                this.setVisible(false);
                
            }else {
                JOptionPane.showMessageDialog(this, "Produk not found in the map: " + produkNamaValue);
            }
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Produk gagal disimpan");
            e.printStackTrace();
        }
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
            java.util.logging.Logger.getLogger(FormIsianPemasokTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormIsianPemasokTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormIsianPemasokTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormIsianPemasokTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormIsianPemasokTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField diskonPersenDpembelianField;
    private javax.swing.JLabel diskonPersenLabel;
    private javax.swing.JTextField diskonRpDpembelianField;
    private javax.swing.JLabel diskonRpLabel;
    private javax.swing.JTextField hargaDpembelianField;
    private javax.swing.JLabel hargaLabel;
    private javax.swing.JTextField jumlahDpembelianField;
    private javax.swing.JLabel jumlahLabel;
    private javax.swing.JPanel panelFormIsianPemasok;
    private javax.swing.JComboBox<String> produkDpembelianCboField;
    private javax.swing.JLabel produkLabel;
    private javax.swing.JComboBox<String> satuanDpembelianField;
    private javax.swing.JLabel satuanLabel;
    private javax.swing.JTextField subtotalRpDpembelianField;
    // End of variables declaration//GEN-END:variables
}
