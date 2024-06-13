/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package _appMain;

import formGudang.ViewMasterGudang;
import formKaryawan.ViewMasterKaryawan;
import formKategori.ViewMasterKategori;
import formLapPembelian.ViewLaporanPembelianRekap;
import formLapPembelian.ViewLaporanPembelianDetail;
import formLapPenjualan.ViewLaporanPenjualanRekap;
import formLapPenjualan.ViewLaporanPenjualanDetail;
import formLapStok.ViewLaporanStok;
import formPelanggan.ViewMasterPelanggan;
import formPemasok.ViewMasterPemasok;
import formTransaksiPembelian.ViewTransaksiPembelian;
import formTransaksiPenjualan.ViewTransaksiPenjualan;
import javax.swing.JOptionPane;
import formProduk.ViewMasterProduk;
import formSatuan.ViewMasterSatuan;
import formBantuan.ViewBantuanPanduan;
import formBantuan.ViewBantuanTentang;
import formBantuan.ViewBantuanKontak;
import javax.swing.ImageIcon;

/**
 *
 * @author HP
 */
public class ViewMainMenus extends javax.swing.JFrame {
    
    /**
     * Creates new form ViewMainMenus
     */
    private FormLogin formLoginInstance;
    
    // Inisialisasi Menu Master
    private ViewMasterProduk menuProduk;
    private ViewMasterSatuan menuSatuan;
    private ViewMasterKategori menuKategori;
    private ViewMasterGudang menuGudang;
    private ViewMasterKaryawan menuKaryawan;
    private ViewMasterPelanggan menuPelanggan;
    private ViewMasterPemasok menuPemasok;
    
    // Inisialisasi Menu Transaksi
    private ViewTransaksiPenjualan menuTransaksiPenjualan;
    private ViewTransaksiPembelian menuTransaksiPembelian;
    
    // Inisialisasi Menu Laporan Transaksi
    private ViewLaporanPenjualanRekap menuLapPenjualanRekap;
    private ViewLaporanPenjualanDetail menuLapPenjualanDetail;
    
    private ViewLaporanPembelianRekap menuLapPembelianRekap;
    private ViewLaporanPembelianDetail menuLapPembelianDetail;
    
    private ViewLaporanStok menuLapStok;
   
    public ViewMainMenus(String hakAkses) {
        // Generate Menus sesuai akses
        String aksesMenus = hakAkses;
        
         // Start Component form login
        formLoginInstance = new FormLogin(); 
        
        // Start Component menu master
        menuProduk = new ViewMasterProduk();
        menuSatuan = new ViewMasterSatuan();
        menuKategori = new ViewMasterKategori();
        menuGudang = new ViewMasterGudang();
        menuKaryawan = new ViewMasterKaryawan();
        menuPelanggan = new ViewMasterPelanggan();
        menuPemasok = new ViewMasterPemasok();
        
        // Start Component menu Transaksi
        menuTransaksiPenjualan = new ViewTransaksiPenjualan();
        menuTransaksiPembelian = new ViewTransaksiPembelian();
        
        // Start Component menu Laporan
        menuLapPenjualanRekap = new ViewLaporanPenjualanRekap();
        menuLapPenjualanDetail = new ViewLaporanPenjualanDetail();
        
        menuLapPembelianRekap = new ViewLaporanPembelianRekap();
        menuLapPembelianDetail = new ViewLaporanPembelianDetail();
        
        menuLapStok = new ViewLaporanStok();
        
        // Start Component menu master
        menuProduk = new ViewMasterProduk();
        menuSatuan = new ViewMasterSatuan();
        
              
        // Start init component
        initComponents();
        ImageIcon icon = new ImageIcon("src/_asset/55.png");
        setIconImage(icon.getImage());
        generateMenus(aksesMenus);
    }
    
    public void generateMenus(String aksesMenus) {
        if ("Kasir".equals(aksesMenus)) {
            posMenusMaster.setVisible(false);
            posMenusLaporan.setVisible(false);
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

        panelMainPos = new javax.swing.JPanel();
        posMenus = new javax.swing.JMenuBar();
        posMenusFile = new javax.swing.JMenu();
        menuProfile = new javax.swing.JMenuItem();
        menuKeluar = new javax.swing.JMenuItem();
        posMenusMaster = new javax.swing.JMenu();
        masterProduk = new javax.swing.JMenuItem();
        masterSatuan = new javax.swing.JMenuItem();
        masterKategori = new javax.swing.JMenuItem();
        masterGudang = new javax.swing.JMenuItem();
        masterKaryawan = new javax.swing.JMenuItem();
        masterPelanggan = new javax.swing.JMenuItem();
        masterPemasok = new javax.swing.JMenuItem();
        posMenusTransaksi = new javax.swing.JMenu();
        transaksiPembelian = new javax.swing.JMenuItem();
        transaksiPenjualan = new javax.swing.JMenuItem();
        posMenusLaporan = new javax.swing.JMenu();
        laporanPembelianMenus = new javax.swing.JMenu();
        laporanPembelianRekap = new javax.swing.JMenuItem();
        laporanPembelianDetail = new javax.swing.JMenuItem();
        laporanPenjualanMenus = new javax.swing.JMenu();
        laporanPenjualanRekap = new javax.swing.JMenuItem();
        laporanPenjualanDetail = new javax.swing.JMenuItem();
        laporanStokMenus = new javax.swing.JMenuItem();
        posMenusBantuan = new javax.swing.JMenu();
        bantuanPanduan = new javax.swing.JMenuItem();
        bantuanTentang = new javax.swing.JMenuItem();
        bantuanKontak = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Point Of Sales");

        panelMainPos.setBackground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout panelMainPosLayout = new javax.swing.GroupLayout(panelMainPos);
        panelMainPos.setLayout(panelMainPosLayout);
        panelMainPosLayout.setHorizontalGroup(
            panelMainPosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1038, Short.MAX_VALUE)
        );
        panelMainPosLayout.setVerticalGroup(
            panelMainPosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        posMenusFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/56.png"))); // NOI18N
        posMenusFile.setText("File");
        posMenusFile.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        menuProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/61.png"))); // NOI18N
        menuProfile.setText("Profile");
        menuProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProfileActionPerformed(evt);
            }
        });
        posMenusFile.add(menuProfile);

        menuKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/62.png"))); // NOI18N
        menuKeluar.setText("Keluar");
        menuKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuKeluarActionPerformed(evt);
            }
        });
        posMenusFile.add(menuKeluar);

        posMenus.add(posMenusFile);

        posMenusMaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/57.png"))); // NOI18N
        posMenusMaster.setText("Master");

        masterProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/63.png"))); // NOI18N
        masterProduk.setText("Produk");
        masterProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterProdukActionPerformed(evt);
            }
        });
        posMenusMaster.add(masterProduk);

        masterSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/64.png"))); // NOI18N
        masterSatuan.setText("Satuan");
        masterSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterSatuanActionPerformed(evt);
            }
        });
        posMenusMaster.add(masterSatuan);

        masterKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/65.png"))); // NOI18N
        masterKategori.setText("Kategori");
        masterKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterKategoriActionPerformed(evt);
            }
        });
        posMenusMaster.add(masterKategori);

        masterGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/66.png"))); // NOI18N
        masterGudang.setText("Gudang");
        masterGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterGudangActionPerformed(evt);
            }
        });
        posMenusMaster.add(masterGudang);

        masterKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/67.png"))); // NOI18N
        masterKaryawan.setText("Karyawan");
        masterKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterKaryawanActionPerformed(evt);
            }
        });
        posMenusMaster.add(masterKaryawan);

        masterPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/68.png"))); // NOI18N
        masterPelanggan.setText("Pelanggan");
        masterPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterPelangganActionPerformed(evt);
            }
        });
        posMenusMaster.add(masterPelanggan);

        masterPemasok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/69.png"))); // NOI18N
        masterPemasok.setText("Pemasok");
        masterPemasok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterPemasokActionPerformed(evt);
            }
        });
        posMenusMaster.add(masterPemasok);

        posMenus.add(posMenusMaster);

        posMenusTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/58.png"))); // NOI18N
        posMenusTransaksi.setText("Transaksi");

        transaksiPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/71.png"))); // NOI18N
        transaksiPembelian.setText("Pembelian");
        transaksiPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksiPembelianActionPerformed(evt);
            }
        });
        posMenusTransaksi.add(transaksiPembelian);

        transaksiPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/70.png"))); // NOI18N
        transaksiPenjualan.setText("Penjualan");
        transaksiPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transaksiPenjualanActionPerformed(evt);
            }
        });
        posMenusTransaksi.add(transaksiPenjualan);

        posMenus.add(posMenusTransaksi);

        posMenusLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/59.png"))); // NOI18N
        posMenusLaporan.setText("Laporan");

        laporanPembelianMenus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/72.png"))); // NOI18N
        laporanPembelianMenus.setText("Pembelian");

        laporanPembelianRekap.setText("Rekap");
        laporanPembelianRekap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanPembelianRekapActionPerformed(evt);
            }
        });
        laporanPembelianMenus.add(laporanPembelianRekap);

        laporanPembelianDetail.setText("Detail");
        laporanPembelianDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanPembelianDetailActionPerformed(evt);
            }
        });
        laporanPembelianMenus.add(laporanPembelianDetail);

        posMenusLaporan.add(laporanPembelianMenus);

        laporanPenjualanMenus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/73.png"))); // NOI18N
        laporanPenjualanMenus.setText("Penjualan");

        laporanPenjualanRekap.setText("Rekap");
        laporanPenjualanRekap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanPenjualanRekapActionPerformed(evt);
            }
        });
        laporanPenjualanMenus.add(laporanPenjualanRekap);

        laporanPenjualanDetail.setText("Detail");
        laporanPenjualanDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanPenjualanDetailActionPerformed(evt);
            }
        });
        laporanPenjualanMenus.add(laporanPenjualanDetail);

        posMenusLaporan.add(laporanPenjualanMenus);

        laporanStokMenus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/74.png"))); // NOI18N
        laporanStokMenus.setText("Stok");
        laporanStokMenus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanStokMenusActionPerformed(evt);
            }
        });
        posMenusLaporan.add(laporanStokMenus);

        posMenus.add(posMenusLaporan);

        posMenusBantuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/60.png"))); // NOI18N
        posMenusBantuan.setText("Bantuan");

        bantuanPanduan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/75.png"))); // NOI18N
        bantuanPanduan.setText("Panduan Pengguna");
        bantuanPanduan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bantuanPanduanActionPerformed(evt);
            }
        });
        posMenusBantuan.add(bantuanPanduan);

        bantuanTentang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/76.png"))); // NOI18N
        bantuanTentang.setText("Tentang Kami");
        bantuanTentang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bantuanTentangActionPerformed(evt);
            }
        });
        posMenusBantuan.add(bantuanTentang);

        bantuanKontak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/_asset/77.png"))); // NOI18N
        bantuanKontak.setText("Kontak Dukungan");
        bantuanKontak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bantuanKontakActionPerformed(evt);
            }
        });
        posMenusBantuan.add(bantuanKontak);

        posMenus.add(posMenusBantuan);

        setJMenuBar(posMenus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMainPos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMainPos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProfileActionPerformed
        // TODO add your handling code here:
        if( ! ( menuProduk.isVisible() ) ) { // Memeriksa apakah menuProduk sedang tidak tampil
            menuProduk.setVisible(true); // Display menuProduk
        }
        else {
            JOptionPane.showMessageDialog(this, "Aplikasi 1a sudah dibuka");
        }
    }//GEN-LAST:event_menuProfileActionPerformed

    private void transaksiPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksiPenjualanActionPerformed
        // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuTransaksiPenjualan);

        if (!menuTransaksiPenjualan.isVisible()) {
       menuTransaksiPenjualan.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuTransaksiPenjualan.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
       menuTransaksiPenjualan.toFront();
        }
    }//GEN-LAST:event_transaksiPenjualanActionPerformed

    private void masterProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterProdukActionPerformed
        // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuProduk);

        if (!menuProduk.isVisible()) {
        menuProduk.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuProduk.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuProduk.toFront();
        }
    }//GEN-LAST:event_masterProdukActionPerformed

    private void masterSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterSatuanActionPerformed
       // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuSatuan ke panelMainPos
        this.panelMainPos.add(menuSatuan);

        if (!menuSatuan.isVisible()) {
        menuSatuan.setVisible(true);

        // Set the size of menuSatuan to fill panelMainPos
        menuSatuan.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuSatuan.toFront();
        }
    }//GEN-LAST:event_masterSatuanActionPerformed

    private void menuKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuKeluarActionPerformed
        // TODO add your handling code here:
        dispose();
        JOptionPane.showMessageDialog(this, "Admin Sudah Keluar");
    }//GEN-LAST:event_menuKeluarActionPerformed

    private void laporanPenjualanRekapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanPenjualanRekapActionPerformed
       // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuLapPenjualanRekap);

        if (!menuLapPenjualanRekap.isVisible()) {
        menuLapPenjualanRekap.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuLapPenjualanRekap.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuLapPenjualanRekap.toFront();
        }
    }//GEN-LAST:event_laporanPenjualanRekapActionPerformed

    private void masterKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterKategoriActionPerformed
       // Hapus semua komponen dari panelMainPos
    panelMainPos.removeAll();

    // Tambahkan menuProduk ke panelMainPos
    this.panelMainPos.add(menuKategori);

    if (!menuKategori.isVisible()) {
        menuKategori.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuKategori.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
    } else {
        menuKategori.toFront();
        }
    }//GEN-LAST:event_masterKategoriActionPerformed

    private void masterGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterGudangActionPerformed
        // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuGudang);

        if (!menuGudang.isVisible()) {
        menuGudang.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuGudang.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuGudang.toFront();
        }
    }//GEN-LAST:event_masterGudangActionPerformed

    private void masterKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterKaryawanActionPerformed
        // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuKaryawan);

        if (!menuKaryawan.isVisible()) {
        menuKaryawan.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuKaryawan.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuKaryawan.toFront();
        }
    }//GEN-LAST:event_masterKaryawanActionPerformed

    private void masterPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterPelangganActionPerformed
        // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuPelanggan);

        if (!menuPelanggan.isVisible()) {
        menuPelanggan.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuPelanggan.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuPelanggan.toFront();
        }
    }//GEN-LAST:event_masterPelangganActionPerformed

    private void masterPemasokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterPemasokActionPerformed
        // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuPemasok);

        if (!menuPemasok.isVisible()) {
        menuPemasok.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuPemasok.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuPemasok.toFront();
        }
    }//GEN-LAST:event_masterPemasokActionPerformed

    private void transaksiPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transaksiPembelianActionPerformed
        // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuTransaksiPembelian);

        if (!menuTransaksiPembelian.isVisible()) {
       menuTransaksiPembelian.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuTransaksiPembelian.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
       menuTransaksiPembelian.toFront();
        }
    }//GEN-LAST:event_transaksiPembelianActionPerformed

    private void laporanPembelianRekapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanPembelianRekapActionPerformed
        // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuLapPembelianRekap);

        if (!menuLapPembelianRekap.isVisible()) {
        menuLapPembelianRekap.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuLapPembelianRekap.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuLapPembelianRekap.toFront();
        }
    }//GEN-LAST:event_laporanPembelianRekapActionPerformed

    private void laporanPembelianDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanPembelianDetailActionPerformed
        // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuLapPembelianDetail);

        if (!menuLapPembelianDetail.isVisible()) {
        menuLapPembelianDetail.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuLapPembelianDetail.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuLapPembelianDetail.toFront();
        }
    }//GEN-LAST:event_laporanPembelianDetailActionPerformed

    private void laporanPenjualanDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanPenjualanDetailActionPerformed
         // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuLapPenjualanDetail);

        if (!menuLapPenjualanDetail.isVisible()) {
        menuLapPenjualanDetail.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuLapPenjualanDetail.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuLapPenjualanDetail.toFront();
        }
    }//GEN-LAST:event_laporanPenjualanDetailActionPerformed

    private void laporanStokMenusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanStokMenusActionPerformed
         // Hapus semua komponen dari panelMainPos
        panelMainPos.removeAll();

        // Tambahkan menuProduk ke panelMainPos
        this.panelMainPos.add(menuLapStok);

        if (!menuLapStok.isVisible()) {
        menuLapStok.setVisible(true);

        // Set the size of menuProduk to fill panelMainPos
        menuLapStok.setBounds(0, 0, panelMainPos.getWidth(), panelMainPos.getHeight());
        } else {
        menuLapStok.toFront();
        }
    }//GEN-LAST:event_laporanStokMenusActionPerformed

    private void bantuanPanduanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bantuanPanduanActionPerformed
        new ViewBantuanPanduan(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_bantuanPanduanActionPerformed

    private void bantuanTentangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bantuanTentangActionPerformed
        new ViewBantuanTentang(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_bantuanTentangActionPerformed

    private void bantuanKontakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bantuanKontakActionPerformed
        new ViewBantuanKontak(this, rootPaneCheckingEnabled).show();
    }//GEN-LAST:event_bantuanKontakActionPerformed

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
            java.util.logging.Logger.getLogger(ViewMainMenus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewMainMenus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewMainMenus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewMainMenus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewMainMenus("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem bantuanKontak;
    private javax.swing.JMenuItem bantuanPanduan;
    private javax.swing.JMenuItem bantuanTentang;
    private javax.swing.JMenuItem laporanPembelianDetail;
    private javax.swing.JMenu laporanPembelianMenus;
    private javax.swing.JMenuItem laporanPembelianRekap;
    private javax.swing.JMenuItem laporanPenjualanDetail;
    private javax.swing.JMenu laporanPenjualanMenus;
    private javax.swing.JMenuItem laporanPenjualanRekap;
    private javax.swing.JMenuItem laporanStokMenus;
    private javax.swing.JMenuItem masterGudang;
    private javax.swing.JMenuItem masterKaryawan;
    private javax.swing.JMenuItem masterKategori;
    private javax.swing.JMenuItem masterPelanggan;
    private javax.swing.JMenuItem masterPemasok;
    private javax.swing.JMenuItem masterProduk;
    private javax.swing.JMenuItem masterSatuan;
    private javax.swing.JMenuItem menuKeluar;
    private javax.swing.JMenuItem menuProfile;
    private javax.swing.JPanel panelMainPos;
    private javax.swing.JMenuBar posMenus;
    private javax.swing.JMenu posMenusBantuan;
    private javax.swing.JMenu posMenusFile;
    private javax.swing.JMenu posMenusLaporan;
    private javax.swing.JMenu posMenusMaster;
    private javax.swing.JMenu posMenusTransaksi;
    private javax.swing.JMenuItem transaksiPembelian;
    private javax.swing.JMenuItem transaksiPenjualan;
    // End of variables declaration//GEN-END:variables
}