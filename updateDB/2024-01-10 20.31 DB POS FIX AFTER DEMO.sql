-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.38-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for db_pos_uas
DROP DATABASE IF EXISTS `db_pos_uas`;
CREATE DATABASE IF NOT EXISTS `db_pos_uas` /*!40100 DEFAULT CHARACTER SET armscii8 COLLATE armscii8_bin */;
USE `db_pos_uas`;

-- Dumping structure for procedure db_pos_uas.deleteProdukDetail
DROP PROCEDURE IF EXISTS `deleteProdukDetail`;
DELIMITER //
CREATE PROCEDURE `deleteProdukDetail`(
	IN `pdetailProdukIdValue` INT
)
BEGIN
	
	DELETE FROM produk_detail WHERE pdetail_produk_id = pdetailProdukIdValue;
	
END//
DELIMITER ;

-- Dumping structure for table db_pos_uas.gudang
DROP TABLE IF EXISTS `gudang`;
CREATE TABLE IF NOT EXISTS `gudang` (
  `gudang_id` int(11) NOT NULL AUTO_INCREMENT,
  `gudang_nama` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `gudang_alamat` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `gudang_status` enum('Aktif','Tidak Aktif') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`gudang_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.gudang: ~5 rows (approximately)
INSERT INTO `gudang` (`gudang_id`, `gudang_nama`, `gudang_alamat`, `gudang_status`) VALUES
	(1, 'Gudang  Baru', 'Jl Sukolilo', 'Aktif'),
	(2, 'Gudang Lama', 'Jl. Jember', 'Aktif'),
	(3, 'Gudang Baru', 'Surabaya', 'Aktif'),
	(4, 'Gajah Baru', 'Jember', 'Aktif'),
	(5, 'Rizal', 'sfafa', 'Aktif');

-- Dumping structure for table db_pos_uas.hak_akses
DROP TABLE IF EXISTS `hak_akses`;
CREATE TABLE IF NOT EXISTS `hak_akses` (
  `hak_akses_id` int(11) NOT NULL AUTO_INCREMENT,
  `hak_akses_nama` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `hak_akses_status` enum('Aktif','Tidak Aktif') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`hak_akses_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.hak_akses: ~2 rows (approximately)
INSERT INTO `hak_akses` (`hak_akses_id`, `hak_akses_nama`, `hak_akses_status`) VALUES
	(1, 'Administrator', 'Aktif'),
	(2, 'Kasir', 'Aktif');

-- Dumping structure for procedure db_pos_uas.insertGudang
DROP PROCEDURE IF EXISTS `insertGudang`;
DELIMITER //
CREATE PROCEDURE `insertGudang`(
	IN `p_gudang_nama` VARCHAR(225),
	IN `p_gudang_alamat` VARCHAR(225),
	IN `p_gudang_status` VARCHAR(225)
)
BEGIN
	INSERT INTO gudang (gudang_nama, gudang_alamat, gudang_status)
   VALUES (p_gudang_nama, p_gudang_alamat, p_gudang_status);
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.insertKaryawan
DROP PROCEDURE IF EXISTS `insertKaryawan`;
DELIMITER //
CREATE PROCEDURE `insertKaryawan`(
	IN `karyawanNamaValue` VARCHAR(255),
	IN `karyawanAlamatValue` VARCHAR(255),
	IN `karyawanNotlpValue` VARCHAR(20),
	IN `karyawanJenisValue` VARCHAR(10),
	IN `karyawanStatusValue` VARCHAR(20)
)
BEGIN
    INSERT INTO karyawan (karyawan_nama, karyawan_alamat, karyawan_tlp, karyawan_jenis_kelamin, karyawan_status)
    VALUES (karyawanNamaValue, karyawanAlamatValue, karyawanNotlpValue, karyawanJenisValue, karyawanStatusValue);
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.insertKategori
DROP PROCEDURE IF EXISTS `insertKategori`;
DELIMITER //
CREATE PROCEDURE `insertKategori`(
	IN `kodeValue` VARCHAR(255),
	IN `namaValue` VARCHAR(255),
	IN `statusValue` VARCHAR(255)
)
BEGIN
	INSERT INTO produk_kategori (kategori_kode, kategori_nama, kategori_status)
	VALUES (kodeValue, namaValue, statusValue);
    
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.insertPelanggan
DROP PROCEDURE IF EXISTS `insertPelanggan`;
DELIMITER //
CREATE PROCEDURE `insertPelanggan`(
	IN `pelangganNamaValue` VARCHAR(255),
	IN `pelangganAlamatValue` VARCHAR(255),
	IN `pelangganNotlpValue` VARCHAR(20),
	IN `pelangganJenisValue` VARCHAR(10),
	IN `pelangganStatusValue` VARCHAR(20)
)
BEGIN
    INSERT INTO pelanggan (pelanggan_nama, pelanggan_alamat, pelanggan_tlp, pelanggan_jenis_kelamin, pelanggan_status)
    VALUES (pelangganNamaValue, pelangganAlamatValue, pelangganNotlpValue, pelangganJenisValue, pelangganStatusValue);
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.insertPemasok
DROP PROCEDURE IF EXISTS `insertPemasok`;
DELIMITER //
CREATE PROCEDURE `insertPemasok`(
	IN `p_pemasok_nama` VARCHAR(225),
	IN `p_pemasok_alamat` VARCHAR(225),
	IN `p_pemasok_tlp` VARCHAR(30),
	IN `p_pemasok_status` VARCHAR(225)
)
BEGIN
    INSERT INTO pemasok (pemasok_nama, pemasok_alamat, pemasok_tlp, pemasok_status)
   VALUES (p_pemasok_nama, p_pemasok_alamat, p_pemasok_tlp, p_pemasok_status);
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.insertProduk
DROP PROCEDURE IF EXISTS `insertProduk`;
DELIMITER //
CREATE PROCEDURE `insertProduk`(
	IN `produkKodeValue` VARCHAR(50),
	IN `produkNamaValue` VARCHAR(50),
	IN `produkKeteranganValue` VARCHAR(50),
	IN `produkKategoriIdValue` INT,
	IN `produkStatusValue` VARCHAR(50)
)
BEGIN
	
	INSERT INTO produk (produk_kode, produk_nama, produk_keterangan, produk_kategori_id, produk_status)
         VALUES (produkKodeValue, produkNamaValue, produkKeteranganValue, produkKategoriIdValue, produkStatusValue);
         
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.insertProdukDetail
DROP PROCEDURE IF EXISTS `insertProdukDetail`;
DELIMITER //
CREATE PROCEDURE `insertProdukDetail`(
	IN `pdetailProdukIdValue` INT,
	IN `pdetailSatuanIdValue` INT,
	IN `pdetailHargaValue` INT,
	IN `pdetailNilaiValue` INT,
	IN `pdetailDefaultValue` VARCHAR(50)
)
BEGIN
	
	INSERT INTO produk_detail (pdetail_produk_id, pdetail_satuan_id, pdetail_harga, pdetail_nilai, pdetail_default)
		
		VALUES (pdetailProdukIdValue, pdetailSatuanIdValue, pdetailHargaValue, pdetailNilaiValue, pdetailDefaultValue);       
	
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.insertProdukSatuan
DROP PROCEDURE IF EXISTS `insertProdukSatuan`;
DELIMITER //
CREATE PROCEDURE `insertProdukSatuan`(
	IN `satuanKodeValue` VARCHAR(255),
	IN `satuanNamaValue` VARCHAR(255),
	IN `satuanStatusValue` VARCHAR(20)
)
BEGIN
    INSERT INTO produk_satuan (satuan_kode, satuan_nama, satuan_status)
    VALUES (satuanKodeValue, satuanNamaValue, satuanStatusValue);
END//
DELIMITER ;

-- Dumping structure for table db_pos_uas.kartu_stok
DROP TABLE IF EXISTS `kartu_stok`;
CREATE TABLE IF NOT EXISTS `kartu_stok` (
  `kstok_id` int(11) NOT NULL AUTO_INCREMENT,
  `kstok_jenis` enum('masuk','keluar') COLLATE armscii8_bin DEFAULT NULL,
  `kstok_nomor_faktur` char(50) COLLATE armscii8_bin DEFAULT NULL,
  `kstok_detail_faktur_id` int(11) DEFAULT NULL,
  `kstok_produk_id` int(11) DEFAULT NULL,
  `kstok_satuan_id` int(11) DEFAULT NULL,
  `kstok_jumlah` float DEFAULT NULL,
  `kstok_gudang_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`kstok_id`),
  KEY `penjualan_id` (`kstok_detail_faktur_id`),
  KEY `pembelian_id` (`kstok_produk_id`),
  KEY `kstok_nomor_faktur` (`kstok_nomor_faktur`),
  CONSTRAINT `FK_kartu_stok_produk` FOREIGN KEY (`kstok_produk_id`) REFERENCES `produk` (`produk_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.kartu_stok: ~6 rows (approximately)
INSERT INTO `kartu_stok` (`kstok_id`, `kstok_jenis`, `kstok_nomor_faktur`, `kstok_detail_faktur_id`, `kstok_produk_id`, `kstok_satuan_id`, `kstok_jumlah`, `kstok_gudang_id`) VALUES
	(6, 'masuk', 'PJ0005', 35, 2, 1, 1, 1),
	(8, 'keluar', 'PJ0011', 37, 3, 1, 10, 1),
	(9, 'keluar', 'PJ0012', 38, 5, 1, 10, 4),
	(11, 'masuk', 'PB008', 8, 6, 1, 2, 1),
	(12, 'keluar', 'PJ0012', 39, 5, 1, 11, 1),
	(13, 'keluar', 'PJ0012', 40, 5, 1, 5, 1);

-- Dumping structure for table db_pos_uas.karyawan
DROP TABLE IF EXISTS `karyawan`;
CREATE TABLE IF NOT EXISTS `karyawan` (
  `karyawan_id` int(11) NOT NULL AUTO_INCREMENT,
  `karyawan_nama` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `karyawan_alamat` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `karyawan_tlp` char(50) COLLATE armscii8_bin DEFAULT NULL,
  `karyawan_jenis_kelamin` enum('L','P') COLLATE armscii8_bin DEFAULT NULL,
  `karyawan_status` enum('Aktif','Tidak Aktif') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`karyawan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.karyawan: ~9 rows (approximately)
INSERT INTO `karyawan` (`karyawan_id`, `karyawan_nama`, `karyawan_alamat`, `karyawan_tlp`, `karyawan_jenis_kelamin`, `karyawan_status`) VALUES
	(1, 'Abdul Rohman Masrifan', 'Jombang', '087863975153', 'L', 'Aktif'),
	(2, 'Labib Falah Athallah', 'Surabaya', '087815964509', 'L', 'Aktif'),
	(3, 'Roro', 'Tuban', '088225066808', 'L', 'Aktif'),
	(4, 'Okky Hendrawan', 'Surabaya', '082132269596', 'L', 'Aktif'),
	(5, 'Rifqi', 'Sby', '12345678', 'L', 'Aktif'),
	(6, 'Barli', 'Sby', '1234567', 'L', 'Aktif'),
	(7, 'Claire', 'Malaysia', '12345678', 'P', 'Aktif'),
	(8, 'Labib Gubeng', 'Sby', '123456', 'L', 'Aktif'),
	(9, 'Rizal Asu', 'Tuban', '12345678', 'L', 'Tidak Aktif');

-- Dumping structure for table db_pos_uas.pelanggan
DROP TABLE IF EXISTS `pelanggan`;
CREATE TABLE IF NOT EXISTS `pelanggan` (
  `pelanggan_id` int(10) NOT NULL AUTO_INCREMENT,
  `pelanggan_nama` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `pelanggan_alamat` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `pelanggan_tlp` char(50) COLLATE armscii8_bin DEFAULT NULL,
  `pelanggan_jenis_kelamin` enum('L','P') COLLATE armscii8_bin DEFAULT NULL,
  `pelanggan_status` enum('Aktif','Tidak Aktif') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`pelanggan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.pelanggan: ~6 rows (approximately)
INSERT INTO `pelanggan` (`pelanggan_id`, `pelanggan_nama`, `pelanggan_alamat`, `pelanggan_tlp`, `pelanggan_jenis_kelamin`, `pelanggan_status`) VALUES
	(1, 'Pelanggan Umum', 'Tuban', '088834934', 'L', 'Aktif'),
	(2, 'Labib Surabaya', 'Surabaya', '088834934', 'L', 'Aktif'),
	(3, 'Okky', 'Sby', '12345678', 'L', 'Aktif'),
	(4, 'Barli', 'Sby', '12345678', 'P', 'Tidak Aktif'),
	(5, 'Sohibul', 'Sby', '12345678', 'L', 'Tidak Aktif'),
	(6, 'Jarwo', 'sby', '1234567', 'L', 'Aktif');

-- Dumping structure for table db_pos_uas.pemasok
DROP TABLE IF EXISTS `pemasok`;
CREATE TABLE IF NOT EXISTS `pemasok` (
  `pemasok_id` int(11) NOT NULL AUTO_INCREMENT,
  `pemasok_nama` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `pemasok_alamat` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `pemasok_tlp` char(20) COLLATE armscii8_bin DEFAULT NULL,
  `pemasok_status` enum('Aktif','Tidak Aktif') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`pemasok_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.pemasok: ~4 rows (approximately)
INSERT INTO `pemasok` (`pemasok_id`, `pemasok_nama`, `pemasok_alamat`, `pemasok_tlp`, `pemasok_status`) VALUES
	(1, 'Rizal Tuban', 'Tuban', '0082382343', 'Aktif'),
	(2, 'Okky', 'Surabaya', '02934', 'Aktif'),
	(3, 'Rohman', 'Sby', '12345678', 'Aktif'),
	(4, 'Labib', 'Sby', '12345677', 'Aktif');

-- Dumping structure for table db_pos_uas.pembelian
DROP TABLE IF EXISTS `pembelian`;
CREATE TABLE IF NOT EXISTS `pembelian` (
  `pembelian_id` int(11) NOT NULL AUTO_INCREMENT,
  `pembelian_nomor_faktur` char(50) COLLATE armscii8_bin DEFAULT NULL,
  `pembelian_tanggal` date DEFAULT NULL,
  `pembelian_pemasok_id` int(11) DEFAULT NULL,
  `pembelian_gudang_id` int(11) DEFAULT NULL,
  `pembelian_total_rp` float DEFAULT NULL,
  PRIMARY KEY (`pembelian_id`),
  KEY `pemasok_id` (`pembelian_pemasok_id`),
  KEY `pembelian_gudang_id` (`pembelian_gudang_id`),
  CONSTRAINT `pemasok_id` FOREIGN KEY (`pembelian_pemasok_id`) REFERENCES `pemasok` (`pemasok_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.pembelian: ~8 rows (approximately)
INSERT INTO `pembelian` (`pembelian_id`, `pembelian_nomor_faktur`, `pembelian_tanggal`, `pembelian_pemasok_id`, `pembelian_gudang_id`, `pembelian_total_rp`) VALUES
	(1, 'PB001', '2024-01-07', 1, NULL, 375000),
	(2, 'PB002', '2024-01-07', 2, NULL, 1200000),
	(3, 'PB003', '2024-01-08', 1, NULL, 10000),
	(4, 'PB004', '2024-01-08', 1, NULL, 1200000),
	(5, 'PB005', '2024-01-08', 1, NULL, 500000),
	(6, 'PB006', '2024-01-10', 3, NULL, 24500),
	(7, 'PB007', '2024-01-10', 3, 2, 100000),
	(8, 'PB008', '2024-01-10', 3, 1, 98000);

-- Dumping structure for procedure db_pos_uas.pembelianDetail
DROP PROCEDURE IF EXISTS `pembelianDetail`;
DELIMITER //
CREATE PROCEDURE `pembelianDetail`(IN cariDataParam VARCHAR(255))
BEGIN
    DECLARE formattedHarga DECIMAL(18,2);
    DECLARE formattedTotalPembelian DECIMAL(18,2);
    DECLARE formattedSubTotal DECIMAL(18,2);

    SELECT
        produk.produk_nama,
        produk.produk_kode,
        produk.produk_keterangan,
        pemasok.pemasok_nama,
        pemasok.pemasok_alamat,
        pemasok.pemasok_tlp,
        pembelian_detail.pbeli_detail_harga,
        pembelian_detail.pbeli_detail_jumlah,
        pembelian_detail.pbeli_detail_persen,
        pembelian_detail.pbeli_detail_subtotal,
        pembelian.pembelian_total_rp,
        pembelian.pembelian_nomor_faktur,
        pembelian.pembelian_tanggal
    FROM
        pembelian_detail
        LEFT JOIN produk ON pembelian_detail.pbeli_detail_produk_id = produk.produk_id
        LEFT JOIN pembelian ON pembelian_detail.pbeli_detail_pembelian_id = pembelian.pembelian_id
        LEFT JOIN pemasok ON pembelian.pembelian_pemasok_id = pemasok.pemasok_id
    WHERE
        pembelian.pembelian_id LIKE CONCAT('%', cariDataParam, '%');

    /* You can add more conditions to the WHERE clause if needed */

END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.pembelianDetailTanggal
DROP PROCEDURE IF EXISTS `pembelianDetailTanggal`;
DELIMITER //
CREATE PROCEDURE `pembelianDetailTanggal`(IN tanggalAwalParam DATE, IN tanggalAkhirParam DATE)
BEGIN
    DECLARE formattedHarga DECIMAL(18,2);
    DECLARE formattedTotalPembelian DECIMAL(18,2);
    DECLARE formattedSubTotal DECIMAL(18,2);

    SELECT
        produk.produk_nama,
        produk.produk_kode,
        produk.produk_keterangan,
        pemasok.pemasok_nama,
        pemasok.pemasok_alamat,
        pemasok.pemasok_tlp,
        pembelian_detail.pbeli_detail_harga,
        pembelian_detail.pbeli_detail_jumlah,
        pembelian_detail.pbeli_detail_persen,
        pembelian_detail.pbeli_detail_subtotal,
        pembelian.pembelian_total_rp,
        pembelian.pembelian_nomor_faktur,
        pembelian.pembelian_tanggal
    FROM
        pembelian_detail
        LEFT JOIN produk ON pembelian_detail.pbeli_detail_produk_id = produk.produk_id
        LEFT JOIN pembelian ON pembelian_detail.pbeli_detail_pembelian_id = pembelian.pembelian_id
        LEFT JOIN pemasok ON pembelian.pembelian_pemasok_id = pemasok.pemasok_id
    WHERE
        pembelian.pembelian_tanggal BETWEEN tanggalAwalParam AND tanggalAkhirParam;

    /* You can add more conditions to the WHERE clause if needed */

END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.pembelianRekap
DROP PROCEDURE IF EXISTS `pembelianRekap`;
DELIMITER //
CREATE PROCEDURE `pembelianRekap`(
	IN `cariDataParam` VARCHAR(255)
)
BEGIN
    DECLARE formattedTotalPembelian DECIMAL(18,2);
    
   SELECT
	pembelian.pembelian_nomor_faktur,
	pembelian.pembelian_tanggal,
	pembelian.pembelian_total_rp,
	pemasok.pemasok_nama,
	pemasok.pemasok_alamat,
	pemasok.pemasok_tlp
	FROM pembelian

	LEFT JOIN pemasok ON pembelian.pembelian_pemasok_id = pemasok.pemasok_id
   WHERE pembelian_id LIKE CONCAT('%', cariDataParam, '%');
    
    /* You can add more columns to the SELECT statement if needed */

END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.pembelianRekapTanggal
DROP PROCEDURE IF EXISTS `pembelianRekapTanggal`;
DELIMITER //
CREATE PROCEDURE `pembelianRekapTanggal`(
	IN `tanggalAwalParam` DATE,
	IN `tanggalAkhirParam` DATE
)
BEGIN
    DECLARE formattedTotalPembelian DECIMAL(18,2);
    
      
   SELECT
	pembelian.pembelian_nomor_faktur,
	pembelian.pembelian_tanggal,
	pembelian.pembelian_total_rp,
	pemasok.pemasok_nama,
	pemasok.pemasok_alamat,
	pemasok.pemasok_tlp
	FROM pembelian

	LEFT JOIN pemasok ON pembelian.pembelian_pemasok_id = pemasok.pemasok_id
   WHERE pembelian_tanggal BETWEEN tanggalAwalParam AND tanggalAkhirParam;
    
    /* You can add more columns to the SELECT statement if needed */

END//
DELIMITER ;

-- Dumping structure for table db_pos_uas.pembelian_detail
DROP TABLE IF EXISTS `pembelian_detail`;
CREATE TABLE IF NOT EXISTS `pembelian_detail` (
  `pbeli_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `pbeli_detail_pembelian_id` int(11) DEFAULT NULL,
  `pbeli_detail_produk_id` int(11) DEFAULT NULL,
  `pbeli_detail_satuan_id` int(11) DEFAULT NULL,
  `pbeli_detail_harga` float DEFAULT NULL,
  `pbeli_detail_jumlah` float DEFAULT NULL,
  `pbeli_detail_diskon_rp` float DEFAULT NULL,
  `pbeli_detail_persen` float DEFAULT NULL,
  `pbeli_detail_subtotal` float DEFAULT NULL,
  PRIMARY KEY (`pbeli_detail_id`),
  KEY `produk_id` (`pbeli_detail_produk_id`),
  KEY `satuan_id` (`pbeli_detail_satuan_id`),
  CONSTRAINT `produk_id` FOREIGN KEY (`pbeli_detail_produk_id`) REFERENCES `produk` (`produk_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `satuan_id` FOREIGN KEY (`pbeli_detail_satuan_id`) REFERENCES `produk_satuan` (`satuan_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.pembelian_detail: ~6 rows (approximately)
INSERT INTO `pembelian_detail` (`pbeli_detail_id`, `pbeli_detail_pembelian_id`, `pbeli_detail_produk_id`, `pbeli_detail_satuan_id`, `pbeli_detail_harga`, `pbeli_detail_jumlah`, `pbeli_detail_diskon_rp`, `pbeli_detail_persen`, `pbeli_detail_subtotal`) VALUES
	(1, 1, 3, 1, 25000, 15, 0, 0, 375000),
	(2, 2, 2, 3, 120000, 10, 0, 0, 1200000),
	(3, 3, 5, 1, 1000, 10, 0, 0, 10000),
	(4, 4, 2, 3, 120000, 10, 0, 0, 1200000),
	(5, 5, 6, 1, 50000, 10, 0, 0, 500000),
	(8, 8, 6, 1, 50000, 2, 2000, 0, 98000);

-- Dumping structure for table db_pos_uas.penjualan
DROP TABLE IF EXISTS `penjualan`;
CREATE TABLE IF NOT EXISTS `penjualan` (
  `penjualan_id` int(11) NOT NULL AUTO_INCREMENT,
  `penjualan_nomor_faktur` char(50) COLLATE armscii8_bin DEFAULT NULL,
  `penjualan_tanggal` date DEFAULT NULL,
  `penjualan_pelanggan_id` int(11) DEFAULT NULL,
  `penjualan_gudang_id` int(11) DEFAULT NULL,
  `penjualan_total_rp` float DEFAULT NULL,
  `penjualan_bayar_rp` float DEFAULT NULL,
  `penjualan_cara_bayar` enum('Tunai','Kartu','Transfer') COLLATE armscii8_bin DEFAULT NULL,
  `penjualan_kembalian_rp` float DEFAULT NULL,
  `penjualan_keterangan` varchar(250) COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`penjualan_id`),
  KEY `pelanggan_id` (`penjualan_pelanggan_id`),
  KEY `penjualan_gudang_id` (`penjualan_gudang_id`),
  CONSTRAINT `pelanggan_id` FOREIGN KEY (`penjualan_pelanggan_id`) REFERENCES `pelanggan` (`pelanggan_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.penjualan: ~12 rows (approximately)
INSERT INTO `penjualan` (`penjualan_id`, `penjualan_nomor_faktur`, `penjualan_tanggal`, `penjualan_pelanggan_id`, `penjualan_gudang_id`, `penjualan_total_rp`, `penjualan_bayar_rp`, `penjualan_cara_bayar`, `penjualan_kembalian_rp`, `penjualan_keterangan`) VALUES
	(1, 'PJ0001', '2023-12-07', 1, NULL, 200000, 200000, 'Tunai', 0, 'Tes'),
	(2, 'PJ0002', '2023-12-07', 2, NULL, 25000, 100000, 'Kartu', 50000, 'Teman'),
	(6, 'PJ0003', '2023-12-09', 1, 1, 25000, 200000, 'Tunai', 175000, 'wwww'),
	(7, 'PJ0004', '2024-01-01', 1, 1, 120000, 1000, 'Tunai', 0, ''),
	(8, 'PJ0005', '2024-01-01', 1, 1, 120000, 10, 'Tunai', 0, ''),
	(9, 'PJ0006', '2024-01-01', 1, 1, 120000, 0, 'Tunai', 0, 'www123'),
	(10, 'PJ0007', '2024-01-01', 2, 2, 1000, 0, 'Tunai', 0, 'qweqwe'),
	(11, 'PJ0008', '2024-01-01', 1, 1, 601000, 620000, 'Tunai', 19000, 'qweqwe'),
	(12, 'PJ0009', '2024-01-01', 1, 1, 76000, 80000, 'Tunai', 4000, ''),
	(13, 'PJ0010', '2024-01-01', 2, 1, 300000, 300000, 'Tunai', 0, 'www123'),
	(14, 'PJ0011', '2024-01-02', 2, 1, 250000, 300000, 'Tunai', 50000, ''),
	(15, 'PJ0012', '2024-01-08', 4, 1, 5000, 20000, 'Tunai', 15000, '');

-- Dumping structure for table db_pos_uas.penjualan_detail
DROP TABLE IF EXISTS `penjualan_detail`;
CREATE TABLE IF NOT EXISTS `penjualan_detail` (
  `pjual_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `pjual_detail_penjualan_id` int(11) DEFAULT NULL,
  `pjual_detail_produk_id` int(11) DEFAULT NULL,
  `pjual_detail_satuan_id` int(11) DEFAULT NULL,
  `pjual_detail_harga` float DEFAULT NULL,
  `pjual_detail_jumlah` float DEFAULT NULL,
  `pjual_detail_diskon_rp` float DEFAULT NULL,
  `pjual_detail_diskon_persen` float DEFAULT NULL,
  `pjual_detail_subtotal` float DEFAULT NULL,
  PRIMARY KEY (`pjual_detail_id`),
  KEY `p_detail_id` (`pjual_detail_produk_id`),
  KEY `pjual_detail_pelanggan_id` (`pjual_detail_penjualan_id`) USING BTREE,
  CONSTRAINT `FK_penjualan_detail_produk` FOREIGN KEY (`pjual_detail_produk_id`) REFERENCES `produk` (`produk_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pjual_penjualan_id` FOREIGN KEY (`pjual_detail_penjualan_id`) REFERENCES `penjualan` (`penjualan_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.penjualan_detail: ~11 rows (approximately)
INSERT INTO `penjualan_detail` (`pjual_detail_id`, `pjual_detail_penjualan_id`, `pjual_detail_produk_id`, `pjual_detail_satuan_id`, `pjual_detail_harga`, `pjual_detail_jumlah`, `pjual_detail_diskon_rp`, `pjual_detail_diskon_persen`, `pjual_detail_subtotal`) VALUES
	(1, 1, 1, 1, 200000, 1, 0, 0, 200000),
	(12, 2, 3, 1, 25000, 5, 0, 0, 25000),
	(17, 10, 5, 1, 1000, 1, 0, 0, 1000),
	(18, 11, 2, 1, 120000, 5, 0, 0, 600000),
	(19, 11, 5, 1, 1000, 1, 0, 0, 1000),
	(28, 11, 5, 1, 1000, 1, 0, 0, 1000),
	(30, 13, 3, 1, 25000, 12, 0, 0, 300000),
	(31, 6, 3, 1, 25000, 1, 0, 0, 25000),
	(35, 8, 2, 1, 120000, 1, 0, 0, 120000),
	(37, 14, 3, 1, 25000, 10, 0, 0, 250000),
	(40, 15, 5, 1, 1000, 5, 0, 0, 5000);

-- Dumping structure for table db_pos_uas.produk
DROP TABLE IF EXISTS `produk`;
CREATE TABLE IF NOT EXISTS `produk` (
  `produk_id` int(11) NOT NULL AUTO_INCREMENT,
  `produk_kode` varchar(50) COLLATE armscii8_bin NOT NULL DEFAULT '0',
  `produk_nama` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `produk_keterangan` varchar(100) COLLATE armscii8_bin DEFAULT NULL,
  `produk_kategori_id` int(11) DEFAULT NULL,
  `produk_status` enum('Aktif','Tidak Aktif') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`produk_id`),
  KEY `FK_produk_produk_kategori` (`produk_kategori_id`),
  CONSTRAINT `kategori_id` FOREIGN KEY (`produk_kategori_id`) REFERENCES `produk_kategori` (`kategori_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.produk: ~9 rows (approximately)
INSERT INTO `produk` (`produk_id`, `produk_kode`, `produk_nama`, `produk_keterangan`, `produk_kategori_id`, `produk_status`) VALUES
	(1, 'CT001', 'Cat tembok2 Nip', 'wwq12', 2, 'Aktif'),
	(2, 'RK016', 'Cat tembok', 'wwq12', 3, 'Aktif'),
	(3, 'RK015', 'Surya 12', 'Surya 12 G', 3, 'Aktif'),
	(5, 'BG001', 'Kursi Lipat', 'Ojok ww talah', 1, 'Aktif'),
	(6, 'BG002', 'Besi Muda', 'Keren ', 1, 'Aktif'),
	(7, 'BG003', 'Besi Tua', '', 1, 'Aktif'),
	(8, 'BG004', 'Genteng Alus', 'Keren dan nyaman', 1, 'Aktif'),
	(9, 'CT0003', 'Nama Cat tes', 'tes keterangan data', 1, 'Aktif'),
	(10, 'BG005', 'tes produk BG', 'ASDASDAS', 1, 'Aktif');

-- Dumping structure for table db_pos_uas.produk_detail
DROP TABLE IF EXISTS `produk_detail`;
CREATE TABLE IF NOT EXISTS `produk_detail` (
  `pdetail_id` int(11) NOT NULL AUTO_INCREMENT,
  `pdetail_produk_id` int(11) DEFAULT NULL,
  `pdetail_satuan_id` int(11) DEFAULT NULL,
  `pdetail_harga` float DEFAULT NULL,
  `pdetail_nilai` float DEFAULT NULL,
  `pdetail_default` enum('Ya','Tidak') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`pdetail_id`),
  KEY `satuan_id_fk` (`pdetail_satuan_id`),
  CONSTRAINT `satuan_id_fk` FOREIGN KEY (`pdetail_satuan_id`) REFERENCES `produk_satuan` (`satuan_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.produk_detail: ~8 rows (approximately)
INSERT INTO `produk_detail` (`pdetail_id`, `pdetail_produk_id`, `pdetail_satuan_id`, `pdetail_harga`, `pdetail_nilai`, `pdetail_default`) VALUES
	(1, 1, 1, 1000, 1, 'Ya'),
	(24, 2, 1, 5000, 1, 'Tidak'),
	(25, 2, 3, 120000, 12, 'Ya'),
	(27, 6, 1, 50000, 1, 'Ya'),
	(28, 7, 3, 10000, 1, 'Ya'),
	(29, 8, 10, 10000, 1, 'Ya'),
	(30, 5, 1, 1000, 1, 'Ya'),
	(31, 10, 3, 50000, 1, 'Ya'),
	(32, 3, 1, 25500, 1, 'Ya');

-- Dumping structure for table db_pos_uas.produk_kategori
DROP TABLE IF EXISTS `produk_kategori`;
CREATE TABLE IF NOT EXISTS `produk_kategori` (
  `kategori_id` int(11) NOT NULL AUTO_INCREMENT,
  `kategori_kode` char(5) COLLATE armscii8_bin DEFAULT NULL,
  `kategori_nama` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `kategori_status` enum('Aktif','Tidak Aktif') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`kategori_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.produk_kategori: ~4 rows (approximately)
INSERT INTO `produk_kategori` (`kategori_id`, `kategori_kode`, `kategori_nama`, `kategori_status`) VALUES
	(1, 'BG', 'Bangunan', 'Aktif'),
	(2, 'CT', 'Cat', 'Aktif'),
	(3, 'RK', 'Rokok', 'Aktif'),
	(4, 'ww', 'wempak', 'Aktif'),
	(5, 'ww', 'wem wweaw', 'Aktif');

-- Dumping structure for table db_pos_uas.produk_satuan
DROP TABLE IF EXISTS `produk_satuan`;
CREATE TABLE IF NOT EXISTS `produk_satuan` (
  `satuan_id` int(11) NOT NULL AUTO_INCREMENT,
  `satuan_kode` char(5) COLLATE armscii8_bin DEFAULT NULL,
  `satuan_nama` varchar(100) COLLATE armscii8_bin DEFAULT NULL,
  `satuan_status` enum('Aktif','Tidak Aktif') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`satuan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.produk_satuan: ~10 rows (approximately)
INSERT INTO `produk_satuan` (`satuan_id`, `satuan_kode`, `satuan_nama`, `satuan_status`) VALUES
	(1, 'PCS', 'Pieces', 'Aktif'),
	(2, 'LSN', 'Lusin', 'Aktif'),
	(3, 'Mtr', 'Meter', 'Aktif'),
	(4, 'KG', 'KiloGram', 'Aktif'),
	(5, 'GT', 'Genteng', 'Aktif'),
	(6, 'GLS', 'Gelas', 'Aktif'),
	(7, 'MS', 'Mouse', 'Aktif'),
	(8, 'KL', 'Kursi Lipat', 'Aktif'),
	(9, 'RK', 'Rokok', 'Aktif'),
	(10, 'TBQ', 'Tas Bagusqq', 'Aktif');

-- Dumping structure for procedure db_pos_uas.searchGudang
DROP PROCEDURE IF EXISTS `searchGudang`;
DELIMITER //
CREATE PROCEDURE `searchGudang`(
	IN `p_cariData` VARCHAR(225)
)
BEGIN
	IF p_cariData IS NOT NULL AND p_cariData != '' THEN
        -- If search text is provided, search with conditions
        SELECT 
            gudang_id,
            gudang_nama,
            gudang_alamat,
            gudang_status
        FROM gudang
        WHERE 
            gudang_status = 'Aktif' AND
            (LOWER(gudang_nama) LIKE CONCAT('%', LOWER(p_cariData), '%') OR
             LOWER(gudang_alamat) LIKE CONCAT('%', LOWER(p_cariData), '%'));
    ELSE
        -- If no search text, retrieve all records
        SELECT 
            gudang_id,
            gudang_nama,
            gudang_alamat,
            gudang_status
        FROM gudang
        WHERE gudang_status = 'Aktif';
    END IF;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchKartuStok
DROP PROCEDURE IF EXISTS `searchKartuStok`;
DELIMITER //
CREATE PROCEDURE `searchKartuStok`(
	IN `tglAwal` DATE,
	IN `tglAkhir` DATE
)
BEGIN

	SELECT ks.kstok_id, ks.kstok_jenis, ks.kstok_nomor_faktur, 
			IFNULL(qpj.penjualan_tanggal, qpb.pembelian_tanggal) AS tanggal_stok, ks.kstok_detail_faktur_id, ks.kstok_produk_id, 
			ks.kstok_satuan_id, ks.kstok_jumlah, ks.kstok_gudang_id, p.produk_nama, p.produk_kode, ps.satuan_nama, g.gudang_nama
	FROM kartu_stok ks
	LEFT JOIN (
		SELECT * FROM penjualan_detail
		LEFT JOIN penjualan ON penjualan.penjualan_id = penjualan_detail.pjual_detail_penjualan_id
		LEFT JOIN pelanggan ON pelanggan.pelanggan_id = penjualan.penjualan_pelanggan_id
	)qpj ON qpj.penjualan_nomor_faktur = ks.kstok_nomor_faktur AND qpj.pjual_detail_id = ks.kstok_detail_faktur_id
	LEFT JOIN (
		SELECT * FROM pembelian_detail
		LEFT JOIN pembelian ON pembelian.pembelian_id = pembelian_detail.pbeli_detail_pembelian_id
		LEFT JOIN pemasok ON pemasok.pemasok_id = pembelian.pembelian_pemasok_id
	)qpb ON qpb.pembelian_nomor_faktur = ks.kstok_nomor_faktur AND qpb.pbeli_detail_id = ks.kstok_detail_faktur_id
	LEFT JOIN produk p ON p.produk_id = ks.kstok_produk_id
	LEFT JOIN gudang g ON g.gudang_id = ks.kstok_gudang_id
	LEFT JOIN produk_satuan ps ON ps.satuan_id = ks.kstok_satuan_id
	WHERE (penjualan_tanggal BETWEEN tglAwal AND tglAkhir) || (pembelian_tanggal BETWEEN tglAwal AND tglAkhir);

END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchKaryawan
DROP PROCEDURE IF EXISTS `searchKaryawan`;
DELIMITER //
CREATE PROCEDURE `searchKaryawan`(
	IN `cariData` VARCHAR(255)
)
BEGIN
    IF NOT cariData IS NULL AND NOT cariData = '' THEN
        SELECT 
            karyawan_id,
            karyawan_nama,
            karyawan_alamat,
            karyawan_tlp,
            karyawan_jenis_kelamin,
            karyawan_status
        FROM karyawan
        WHERE karyawan_nama LIKE CONCAT('%', cariData, '%')
           OR karyawan_status = cariData
           OR karyawan_jenis_kelamin = cariData;
    ELSE
        SELECT 
            karyawan_id,
            karyawan_nama,
            karyawan_alamat,
            karyawan_tlp,
            karyawan_jenis_kelamin,
            karyawan_status
        FROM karyawan;
    END IF;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchKategori
DROP PROCEDURE IF EXISTS `searchKategori`;
DELIMITER //
CREATE PROCEDURE `searchKategori`(
	IN `pencarianValue` VARCHAR(255)
)
BEGIN
	IF(pencarianValue != '') THEN
		SELECT *, CONCAT(produk_kategori.kategori_kode, ' / ', produk_kategori.kategori_nama)
		FROM produk_kategori
		WHERE (LOWER(produk_kategori.kategori_kode) LIKE CONCAT('%', LOWER(pencarianValue), '%')
				OR LOWER(produk_kategori.kategori_nama) LIKE CONCAT('%', LOWER(pencarianValue), '%'))
		ORDER BY produk_kategori.kategori_kode;
	ELSE
		SELECT *
		FROM produk_kategori
		ORDER BY produk_kategori.kategori_kode;
	END IF;
    
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchPelanggan
DROP PROCEDURE IF EXISTS `searchPelanggan`;
DELIMITER //
CREATE PROCEDURE `searchPelanggan`(
	IN `cariData` VARCHAR(255)
)
BEGIN
    IF NOT cariData IS NULL AND NOT cariData = '' THEN
        SELECT 
            pelanggan_id,
            pelanggan_nama,
            pelanggan_alamat,
            pelanggan_tlp,
            pelanggan_jenis_kelamin,
            pelanggan_status
        FROM pelanggan
        WHERE pelanggan_nama LIKE CONCAT('%', cariData, '%')
           OR pelanggan_status = cariData
           OR pelanggan_jenis_kelamin = cariData;
    ELSE
        SELECT 
            pelanggan_id,
            pelanggan_nama,
            pelanggan_alamat,
            pelanggan_tlp,
            pelanggan_jenis_kelamin,
            pelanggan_status
        FROM pelanggan;
    END IF;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchPemasok
DROP PROCEDURE IF EXISTS `searchPemasok`;
DELIMITER //
CREATE PROCEDURE `searchPemasok`(
	IN `p_cariData` VARCHAR(225)
)
BEGIN
    IF p_cariData IS NOT NULL AND p_cariData != '' THEN
        -- If search text is provided, search with conditions
        SELECT 
            pemasok_id,
            pemasok_nama,
            pemasok_alamat,
            pemasok_tlp,
            pemasok_status
        FROM pemasok
        WHERE 
            pemasok_status = 'Aktif' AND
            (LOWER(pemasok_nama) LIKE CONCAT('%', LOWER(p_cariData), '%') OR
             LOWER(pemasok_alamat) LIKE CONCAT('%', LOWER(p_cariData), '%') OR
             LOWER(pemasok_tlp) LIKE CONCAT('%', LOWER(p_cariData), '%'));
    ELSE
        -- If no search text, retrieve all records
        SELECT 
            pemasok_id,
            pemasok_nama,
            pemasok_alamat,
            pemasok_tlp,
            pemasok_status
        FROM pemasok
        WHERE pemasok_status = 'Aktif';
    END IF;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchPembelianDetail
DROP PROCEDURE IF EXISTS `searchPembelianDetail`;
DELIMITER //
CREATE PROCEDURE `searchPembelianDetail`(
	IN `searchKeyword` VARCHAR(255)
)
BEGIN
    SELECT
        produk.produk_nama,
        produk.produk_kode,
        produk.produk_keterangan,
        pemasok.pemasok_nama,
        pemasok.pemasok_alamat,
        pemasok.pemasok_tlp,
        pembelian_detail.pbeli_detail_harga,
        pembelian_detail.pbeli_detail_jumlah,
        pembelian_detail.pbeli_detail_persen,
        pembelian_detail.pbeli_detail_subtotal,
        pembelian.pembelian_total_rp,
        pembelian.pembelian_nomor_faktur,
        pembelian.pembelian_tanggal
    FROM
        pembelian_detail
    LEFT JOIN
        produk ON pembelian_detail.pbeli_detail_produk_id = produk.produk_id
    LEFT JOIN
        pembelian ON pembelian_detail.pbeli_detail_pembelian_id = pembelian.pembelian_id
    LEFT JOIN
        pemasok ON pembelian.pembelian_pemasok_id = pemasok.pemasok_id
    WHERE
        produk.produk_nama LIKE CONCAT('%', searchKeyword, '%')
        OR pemasok.pemasok_nama LIKE CONCAT('%', searchKeyword, '%');
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchPembelianRekap
DROP PROCEDURE IF EXISTS `searchPembelianRekap`;
DELIMITER //
CREATE PROCEDURE `searchPembelianRekap`(
	IN `searchKeyword` VARCHAR(255)
)
BEGIN
    SELECT
        pembelian.pembelian_nomor_faktur,
        pembelian.pembelian_tanggal,
        pembelian.pembelian_total_rp,
        pemasok.pemasok_nama,
        pemasok.pemasok_alamat,
        pemasok.pemasok_tlp
    FROM pembelian
    LEFT JOIN pemasok ON pembelian.pembelian_pemasok_id = pemasok.pemasok_id
    WHERE pemasok.pemasok_nama LIKE CONCAT('%', searchKeyword, '%');
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchPenjualanDetail
DROP PROCEDURE IF EXISTS `searchPenjualanDetail`;
DELIMITER //
CREATE PROCEDURE `searchPenjualanDetail`(
	IN `tanggalAwal` DATE,
	IN `tanggalAkhir` DATE
)
BEGIN
    SELECT 
        penjualan.penjualan_nomor_faktur, 
        penjualan.penjualan_tanggal, 
        produk.produk_nama, 
        produk_satuan.satuan_nama, 
        penjualan_detail.pjual_detail_harga, 
        penjualan_detail.pjual_detail_jumlah, 
        penjualan_detail.pjual_detail_diskon_rp, 
        penjualan_detail.pjual_detail_diskon_persen, 
        pelanggan.pelanggan_nama, 
        penjualan.penjualan_cara_bayar, 
        penjualan.penjualan_total_rp, 
        penjualan.penjualan_bayar_rp, 
        penjualan.penjualan_kembalian_rp, 
        penjualan.penjualan_keterangan, 
        penjualan_detail.pjual_detail_subtotal
    FROM penjualan_detail
    LEFT JOIN produk ON penjualan_detail.pjual_detail_produk_id = produk.produk_id
    LEFT JOIN produk_satuan ON penjualan_detail.pjual_detail_satuan_id = produk_satuan.satuan_id
    LEFT JOIN penjualan ON penjualan_detail.pjual_detail_penjualan_id = penjualan.penjualan_id
    LEFT JOIN pelanggan ON penjualan.penjualan_pelanggan_id = pelanggan.pelanggan_id
    WHERE penjualan_detail.penjualan_tanggal BETWEEN tanggalAwal AND tanggalAkhir;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchPenjualanRekap
DROP PROCEDURE IF EXISTS `searchPenjualanRekap`;
DELIMITER //
CREATE PROCEDURE `searchPenjualanRekap`(
	IN `p_tanggalAwal` DATE,
	IN `p_tanggalAkhir` DATE
)
BEGIN
	SELECT 
        pj.penjualan_id,
        p.pelanggan_id,
        pj.penjualan_nomor_faktur,
        pj.penjualan_tanggal,
        p.pelanggan_nama,
        pj.penjualan_cara_bayar,
        pj.penjualan_total_rp,
        pj.penjualan_bayar_rp,
        pj.penjualan_kembalian_rp,
        pj.penjualan_keterangan
   FROM penjualan pj
   LEFT JOIN pelanggan p ON p.pelanggan_id = pj.penjualan_pelanggan_id
   WHERE pj.penjualan_tanggal BETWEEN p_tanggalAwal AND p_tanggalAkhir;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchProduk
DROP PROCEDURE IF EXISTS `searchProduk`;
DELIMITER //
CREATE PROCEDURE `searchProduk`(
	IN `filter` VARCHAR(50)
)
BEGIN
    IF(filter != '') THEN
        SELECT *, CONCAT(pd.pdetail_harga, ' / ', ps.satuan_nama) as harga_display
        FROM produk p
        LEFT JOIN produk_kategori pk ON pk.kategori_id = p.produk_kategori_id
        LEFT JOIN produk_detail pd ON (p.produk_id = pd.pdetail_produk_id AND pd.pdetail_default ='Ya')
        LEFT JOIN produk_satuan ps ON ps.satuan_id = pd.pdetail_satuan_id
		  	WHERE LOWER(p.produk_nama) LIKE CONCAT('%', LOWER(filter), '%') OR LOWER(p.produk_kode) LIKE CONCAT('%', LOWER(filter), '%');
    ELSE
        SELECT *, CONCAT(pd.pdetail_harga, ' / ', ps.satuan_nama) as harga_display
        FROM produk p
        LEFT JOIN produk_kategori pk ON pk.kategori_id = p.produk_kategori_id
        LEFT JOIN produk_detail pd ON (p.produk_id = pd.pdetail_produk_id AND pd.pdetail_default ='Ya')
        LEFT JOIN produk_satuan ps ON ps.satuan_id = pd.pdetail_satuan_id;
    END IF;
    
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchProdukDetail
DROP PROCEDURE IF EXISTS `searchProdukDetail`;
DELIMITER //
CREATE PROCEDURE `searchProdukDetail`(
	IN `pdetailProdukIdValue` INT
)
BEGIN
	
	SELECT *
		FROM produk_detail pd
		LEFT JOIN produk_satuan ps ON pd.pdetail_satuan_id = ps.satuan_id
          where pdetail_produk_id = pdetailProdukIdValue;
                    
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchProdukSatuan
DROP PROCEDURE IF EXISTS `searchProdukSatuan`;
DELIMITER //
CREATE PROCEDURE `searchProdukSatuan`(
	IN `cariDataValue` VARCHAR(255)
)
BEGIN
    IF cariDataValue IS NOT NULL AND cariDataValue != '' THEN
        SELECT * FROM produk_satuan WHERE satuan_nama LIKE CONCAT('%', cariDataValue, '%');
    ELSE
        SELECT * FROM produk_satuan;
    END IF;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.searchTransaksiPenjualan
DROP PROCEDURE IF EXISTS `searchTransaksiPenjualan`;
DELIMITER //
CREATE PROCEDURE `searchTransaksiPenjualan`(
	IN `cariData` VARCHAR(255)
)
BEGIN
    IF cariData IS NOT NULL THEN
        SELECT
            pj.penjualan_id,
            p.pelanggan_id,
            pj.penjualan_tanggal,
            pj.penjualan_nomor_faktur,
            p.pelanggan_nama,
            pj.penjualan_cara_bayar,
            pj.penjualan_total_rp,
            pj.penjualan_bayar_rp,
            pj.penjualan_kembalian_rp,
            pj.penjualan_keterangan
        FROM penjualan pj
        LEFT JOIN pelanggan p ON p.pelanggan_id = pj.penjualan_pelanggan_id
        WHERE pj.penjualan_nomor_faktur LIKE CONCAT('%', cariData, '%')
            OR p.pelanggan_nama LIKE CONCAT('%', cariData, '%')
            OR pj.penjualan_cara_bayar LIKE CONCAT('%', cariData, '%');
    ELSE
        SELECT
            pj.penjualan_id,
            pj.penjualan_tanggal,
            pj.penjualan_nomor_faktur,
            p.pelanggan_id,
            p.pelanggan_nama,
            pj.penjualan_cara_bayar,
            pj.penjualan_total_rp,
            pj.penjualan_bayar_rp,
            pj.penjualan_kembalian_rp,
            pj.penjualan_keterangan
        FROM penjualan pj
        LEFT JOIN pelanggan p ON p.pelanggan_id = pj.penjualan_pelanggan_id;
    END IF;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanDetailDelete
DROP PROCEDURE IF EXISTS `transaksiPenjualanDetailDelete`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanDetailDelete`(IN penjualan_id_value INT)
BEGIN
    DELETE FROM penjualan_detail WHERE pjual_detail_penjualan_id = penjualan_id_value;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanDetailInsert
DROP PROCEDURE IF EXISTS `transaksiPenjualanDetailInsert`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanDetailInsert`(
    IN penjualan_id_value INT,
    IN produk_id_value INT,
    IN satuan_id_value INT,
    IN harga_value VARCHAR(255),
    IN jumlah_value VARCHAR(255),
    IN disk_rp_value VARCHAR(255),
    IN disk_persen_value VARCHAR(255),
    IN subtotal_rp_value VARCHAR(255)
)
BEGIN
    INSERT INTO penjualan_detail (
        pjual_detail_penjualan_id,
        pjual_detail_produk_id,
        pjual_detail_satuan_id,
        pjual_detail_harga,
        pjual_detail_jumlah,
        pjual_detail_diskon_rp,
        pjual_detail_diskon_persen,
        pjual_detail_subtotal
    )
    VALUES (
        penjualan_id_value,
        produk_id_value,
        satuan_id_value,
        harga_value,
        jumlah_value,
        disk_rp_value,
        disk_persen_value,
        subtotal_rp_value
    );
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanInsert
DROP PROCEDURE IF EXISTS `transaksiPenjualanInsert`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanInsert`(
	IN `nomor_faktur` VARCHAR(255),
	IN `tanggal_penjualan` DATE,
	IN `pelanggan_id` INT,
	IN `gudang_id` INT,
	IN `total_rp` VARCHAR(255),
	IN `bayar_rp` VARCHAR(255),
	IN `cara_bayar` VARCHAR(255),
	IN `kembalian_rp` VARCHAR(255),
	IN `keterangan` VARCHAR(255)
)
BEGIN
    INSERT INTO penjualan (
        penjualan_nomor_faktur,
        penjualan_tanggal,
        penjualan_pelanggan_id,
        penjualan_gudang_id,
        penjualan_total_rp,
        penjualan_bayar_rp,
        penjualan_cara_bayar,
        penjualan_kembalian_rp,
        penjualan_keterangan
    ) VALUES (
        nomor_faktur,
        tanggal_penjualan,
        pelanggan_id,
        gudang_id,
        total_rp,
        bayar_rp,
        cara_bayar,
        kembalian_rp,
        keterangan
    );
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanKodeOtomatis
DROP PROCEDURE IF EXISTS `transaksiPenjualanKodeOtomatis`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanKodeOtomatis`()
BEGIN
    DECLARE next_kode VARCHAR(255);
    SET next_kode = (SELECT CONCAT('PJ', LPAD(IFNULL(MAX(CAST(SUBSTRING(penjualan_nomor_faktur, 3) AS UNSIGNED)), 0) + 1, 4, '0')) FROM penjualan);

    IF next_kode IS NULL THEN
        SET next_kode = 'PJ0001';
    END IF;

    SELECT next_kode AS next_code;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanLoadDataList
DROP PROCEDURE IF EXISTS `transaksiPenjualanLoadDataList`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanLoadDataList`(
	IN `penjualanIdParam` VARCHAR(255)
)
BEGIN
    SELECT 
        pd.pjual_detail_id,
        pd.pjual_detail_produk_id,
        pd.pjual_detail_satuan_id,
        p.produk_nama,
        ps.satuan_nama,
        pd.pjual_detail_harga,
        pd.pjual_detail_jumlah,
        pd.pjual_detail_diskon_rp,
        pd.pjual_detail_diskon_persen,
        pd.pjual_detail_subtotal
    FROM penjualan_detail pd
    LEFT JOIN produk p ON p.produk_id = pd.pjual_detail_produk_id
    LEFT JOIN produk_satuan ps ON ps.satuan_id = pd.pjual_detail_satuan_id
    WHERE pd.pjual_detail_penjualan_id = penjualanIdParam;

END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanStoreGudang
DROP PROCEDURE IF EXISTS `transaksiPenjualanStoreGudang`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanStoreGudang`()
BEGIN
    SELECT gudang_id, gudang_nama FROM gudang;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanStorePelanggan
DROP PROCEDURE IF EXISTS `transaksiPenjualanStorePelanggan`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanStorePelanggan`()
BEGIN
    SELECT pelanggan_id, pelanggan_nama FROM pelanggan;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanStoreProduk
DROP PROCEDURE IF EXISTS `transaksiPenjualanStoreProduk`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanStoreProduk`()
BEGIN
    SELECT produk_id, produk_nama FROM produk;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanStoreSatuan
DROP PROCEDURE IF EXISTS `transaksiPenjualanStoreSatuan`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanStoreSatuan`(IN produkNamaParam VARCHAR(255))
BEGIN
    SELECT ps.satuan_id, ps.satuan_nama, pd.pdetail_harga, pd.pdetail_default
    FROM produk_satuan ps
    LEFT JOIN produk_detail pd ON pd.pdetail_satuan_id = ps.satuan_id
    LEFT JOIN produk p ON p.produk_id = pd.pdetail_produk_id
    WHERE p.produk_nama = produkNamaParam
    ORDER BY pd.pdetail_default DESC;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.transaksiPenjualanUpdate
DROP PROCEDURE IF EXISTS `transaksiPenjualanUpdate`;
DELIMITER //
CREATE PROCEDURE `transaksiPenjualanUpdate`(
    IN nomor_faktur VARCHAR(255),
    IN tanggal_penjualan DATE,
    IN pelanggan_id INT,
    IN gudang_id INT,
    IN total_rp VARCHAR(255),
    IN bayar_rp VARCHAR(255),
    IN cara_bayar VARCHAR(255),
    IN kembalian_rp VARCHAR(255),
    IN keterangan VARCHAR(255),
    IN penjualan_id_update INT
)
BEGIN
    UPDATE penjualan
    SET
        penjualan_nomor_faktur = nomor_faktur,
        penjualan_tanggal = tanggal_penjualan,
        penjualan_pelanggan_id = pelanggan_id,
        penjualan_gudang_id = gudang_id,
        penjualan_total_rp = total_rp,
        penjualan_bayar_rp = bayar_rp,
        penjualan_cara_bayar = cara_bayar,
        penjualan_kembalian_rp = kembalian_rp,
        penjualan_keterangan = keterangan
    WHERE
        penjualan_id = penjualan_id_update;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.updateGudang
DROP PROCEDURE IF EXISTS `updateGudang`;
DELIMITER //
CREATE PROCEDURE `updateGudang`(
	IN `p_gudang_id` INT,
	IN `p_gudang_nama` VARCHAR(225),
	IN `p_gudang_alamat` VARCHAR(225),
	IN `p_gudang_status` VARCHAR(225)
)
BEGIN
UPDATE gudang
    SET
        gudang_nama = p_gudang_nama,
        gudang_alamat = p_gudang_alamat,
        gudang_status = p_gudang_status
    WHERE
        gudang_id = p_gudang_id;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.updateKaryawan
DROP PROCEDURE IF EXISTS `updateKaryawan`;
DELIMITER //
CREATE PROCEDURE `updateKaryawan`(
	IN `karyawanNamaValue` VARCHAR(255),
	IN `karyawanAlamatValue` VARCHAR(255),
	IN `karyawanNotlpValue` VARCHAR(20),
	IN `karyawanJenisValue` VARCHAR(10),
	IN `karyawanStatusValue` VARCHAR(20),
	IN `idKaryawan` INT
)
BEGIN
    UPDATE karyawan
    SET
        karyawan_nama = karyawanNamaValue,
        karyawan_alamat = karyawanAlamatValue,
        karyawan_tlp = karyawanNotlpValue,
        karyawan_jenis_kelamin = karyawanJenisValue,
        karyawan_status = karyawanStatusValue
    WHERE karyawan_id = idKaryawan;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.updateKategori
DROP PROCEDURE IF EXISTS `updateKategori`;
DELIMITER //
CREATE PROCEDURE `updateKategori`(
	IN `kodeValue` VARCHAR(255),
	IN `namaValue` VARCHAR(255),
	IN `statusValue` VARCHAR(255),
	IN `idValue` INT
)
BEGIN

	UPDATE produk_kategori 
    SET kategori_kode = kodeValue, kategori_nama = namaValue, kategori_status = statusValue WHERE kategori_id = idValue;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.updatePelanggan
DROP PROCEDURE IF EXISTS `updatePelanggan`;
DELIMITER //
CREATE PROCEDURE `updatePelanggan`(
	IN `pelangganNamaValue` VARCHAR(255),
	IN `pelangganAlamatValue` VARCHAR(255),
	IN `pelangganNotlpValue` VARCHAR(20),
	IN `pelangganJenisValue` VARCHAR(10),
	IN `pelangganStatusValue` VARCHAR(20),
	IN `idPelanggan` INT
)
BEGIN
    UPDATE pelanggan
    SET
        pelanggan_nama = pelangganNamaValue,
        pelanggan_alamat = pelangganAlamatValue,
        pelanggan_tlp = pelangganNotlpValue,
        pelanggan_jenis_kelamin = pelangganJenisValue,
        pelanggan_status = pelangganStatusValue
    WHERE pelanggan_id = idPelanggan;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.updatePemasok
DROP PROCEDURE IF EXISTS `updatePemasok`;
DELIMITER //
CREATE PROCEDURE `updatePemasok`(
	IN `p_pemasok_nama` VARCHAR(225),
	IN `p_pemasok_alamat` VARCHAR(225),
	IN `p_pemasok_tlp` VARCHAR(20),
	IN `p_pemasok_status` VARCHAR(225),
	IN `p_pemasok_id` INT
)
BEGIN
    UPDATE pemasok
    SET
        pemasok_nama = p_pemasok_nama,
        pemasok_alamat = p_pemasok_alamat,
        pemasok_tlp = p_pemasok_tlp,
        pemasok_status = p_pemasok_status
    WHERE
        pemasok_id = p_pemasok_id;
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.updateProduk
DROP PROCEDURE IF EXISTS `updateProduk`;
DELIMITER //
CREATE PROCEDURE `updateProduk`(
	IN `produkKodeValue` VARCHAR(50),
	IN `produkNamaValue` VARCHAR(50),
	IN `produkKeteranganValue` VARCHAR(50),
	IN `produkKategoriIdValue` INT,
	IN `produkStatusValue` VARCHAR(50),
	IN `produkIdValue` INT
)
BEGIN
	
	UPDATE produk SET produk_kode = produkKodeValue, produk_nama = produkNamaValue, produk_keterangan = produkKeteranganValue, produk_kategori_id = produkKategoriIdValue, produk_status = produkStatusValue WHERE produk_id = produkIdValue;
                                
END//
DELIMITER ;

-- Dumping structure for procedure db_pos_uas.updateProdukSatuan
DROP PROCEDURE IF EXISTS `updateProdukSatuan`;
DELIMITER //
CREATE PROCEDURE `updateProdukSatuan`(
	IN `satuanKodeValue` VARCHAR(255),
	IN `satuanNamaValue` VARCHAR(255),
	IN `satuanStatusValue` VARCHAR(20),
	IN `satuanIdValue` INT
)
BEGIN
    UPDATE produk_satuan
    SET satuan_kode = satuanKodeValue,
        satuan_nama = satuanNamaValue,
        satuan_status = satuanStatusValue
    WHERE satuan_id = satuanIdValue;
END//
DELIMITER ;

-- Dumping structure for table db_pos_uas.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_karyawan_id` int(11) DEFAULT NULL,
  `user_username` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `user_password` varchar(50) COLLATE armscii8_bin DEFAULT NULL,
  `user_akses_id` int(11) DEFAULT NULL,
  `user_status` enum('Aktif','Tidak Aktif') COLLATE armscii8_bin DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `karyawan_id` (`user_karyawan_id`),
  KEY `hak_akses_id` (`user_akses_id`),
  CONSTRAINT `hak_akses_id` FOREIGN KEY (`user_akses_id`) REFERENCES `hak_akses` (`hak_akses_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `karyawan_id` FOREIGN KEY (`user_karyawan_id`) REFERENCES `karyawan` (`karyawan_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table db_pos_uas.user: ~5 rows (approximately)
INSERT INTO `user` (`user_id`, `user_karyawan_id`, `user_username`, `user_password`, `user_akses_id`, `user_status`) VALUES
	(1, 1, 'rohman', 'e10adc3949ba59abbe56e057f20f883e', 1, 'Aktif'),
	(2, 2, 'labib', 'e10adc3949ba59abbe56e057f20f883e', 1, 'Aktif'),
	(3, 3, 'rizal', 'e10adc3949ba59abbe56e057f20f883e', 1, 'Aktif'),
	(4, 4, 'okky', 'e10adc3949ba59abbe56e057f20f883e', 1, 'Aktif'),
	(5, 1, 'kasir', 'e10adc3949ba59abbe56e057f20f883e', 2, 'Aktif');

-- Dumping structure for trigger db_pos_uas.pembelian_detail_aft_insert
DROP TRIGGER IF EXISTS `pembelian_detail_aft_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `pembelian_detail_aft_insert` AFTER INSERT ON `pembelian_detail` FOR EACH ROW INSERT INTO kartu_stok

		(kstok_jenis, kstok_nomor_faktur, kstok_detail_faktur_id, kstok_produk_id, kstok_satuan_id, kstok_jumlah, kstok_gudang_id)  

	SELECT 

		'masuk', m.pembelian_nomor_faktur, d.pbeli_detail_id, d.pbeli_detail_produk_id, d.pbeli_detail_satuan_id, d.pbeli_detail_jumlah, m.pembelian_gudang_id

	FROM pembelian_detail d

	LEFT JOIN pembelian m ON (m.pembelian_id = d.pbeli_detail_pembelian_id)

	WHERE 

		d.pbeli_detail_id = NEW.pbeli_detail_id//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger db_pos_uas.pembelian_detail_aft_update
DROP TRIGGER IF EXISTS `pembelian_detail_aft_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `pembelian_detail_aft_update` AFTER UPDATE ON `pembelian_detail` FOR EACH ROW BEGIN
		DELETE FROM kartu_stok WHERE kstok_detail_faktur_id = OLD.pbeli_detail_id;
	
	INSERT INTO kartu_stok

		(kstok_jenis, kstok_nomor_faktur, kstok_detail_faktur_id, kstok_produk_id, kstok_satuan_id, kstok_jumlah, kstok_gudang_id)  

	SELECT 

		'masuk', m.pembelian_nomor_faktur, d.pbeli_detail_id, d.pbeli_detail_produk_id, d.pbeli_detail_satuan_id, d.pbeli_detail_jumlah, m.pembelian_gudang_id

	FROM pembelian_detail d

	LEFT JOIN pembelian m ON (m.pembelian_id = d.pbeli_detail_pembelian_id)

	WHERE 

		d.pbeli_detail_id = NEW.pbeli_detail_id; 	
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger db_pos_uas.penjualan_detail_aft_insert
DROP TRIGGER IF EXISTS `penjualan_detail_aft_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `penjualan_detail_aft_insert` AFTER INSERT ON `penjualan_detail` FOR EACH ROW BEGIN
	
	INSERT INTO kartu_stok

		(kstok_jenis, kstok_nomor_faktur, kstok_detail_faktur_id, kstok_produk_id, kstok_satuan_id, kstok_jumlah, kstok_gudang_id)  

	SELECT 

		'keluar', m.penjualan_nomor_faktur, d.pjual_detail_id, d.pjual_detail_produk_id, d.pjual_detail_satuan_id, d.pjual_detail_jumlah, m.penjualan_gudang_id

	FROM penjualan_detail d

	LEFT JOIN penjualan m ON (m.penjualan_id = d.pjual_detail_penjualan_id)

	WHERE 

		d.pjual_detail_id = NEW.pjual_detail_id; 	

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger db_pos_uas.penjualan_detail_aft_update
DROP TRIGGER IF EXISTS `penjualan_detail_aft_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `penjualan_detail_aft_update` AFTER UPDATE ON `penjualan_detail` FOR EACH ROW BEGIN

	DELETE FROM kartu_stok WHERE kstok_detail_faktur_id = OLD.pjual_detail_id;
	
	INSERT INTO kartu_stok

		(kstok_jenis, kstok_nomor_faktur, kstok_detail_faktur_id, kstok_produk_id, kstok_satuan_id, kstok_jumlah, kstok_gudang_id)  

	SELECT 

		'keluar', m.penjualan_nomor_faktur, d.pjual_detail_id, d.pjual_detail_produk_id, d.pjual_detail_satuan_id, d.pjual_detail_jumlah, m.penjualan_gudang_id

	FROM penjualan_detail d

	LEFT JOIN penjualan m ON (m.penjualan_id = d.pjual_detail_penjualan_id)

	WHERE 

		d.pjual_detail_id = NEW.pjual_detail_id; 	

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

DELIMITER $$
DROP TRIGGER IF EXISTS `insertPembelian`;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertPembelian`(
		IN nomorFakturPembelianValue VARCHAR(255),
        IN tanggalPembelianValue VARCHAR(255),
        IN pemasokIdPembelianValue INT,
        IN gudangIdPembelianValue INT,
        IN totalPembelianValue VARCHAR(255))
BEGIN
		INSERT INTO pembelian(pembelian_nomor_faktur, pembelian_tanggal, pembelian_pemasok_id, pembelian_gudang_id, pembelian_total_rp)
        VALUES (nomorFakturPembelianValue, tanggalPembelianValue, pemasokIdPembelianValue, gudangIdPembelianValue, totalPembelianValue);

END$$

DELIMITER ;
;

DELIMITER $$
DROP TRIGGER IF EXISTS `insertPembelianDetail`;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertPembelianDetail`(
		IN lastInserted INT,
        IN produkId INT,
        IN satuanId INT,
        IN harga VARCHAR(255),
        IN jumlah VARCHAR(255),
        IN diskonRp VARCHAR(255),
        IN diskonPersen VARCHAR(255),
        IN subtotalRp VARCHAR(255)
)
BEGIN
		INSERT INTO pembelian_detail(pbeli_detail_pembelian_id, pbeli_detail_produk_id, pbeli_detail_satuan_id, pbeli_detail_harga,
                     pbeli_detail_jumlah, pbeli_detail_diskon_rp, pbeli_detail_persen, pbeli_detail_subtotal)
		VALUES (lastInserted, produkId, satuanId, harga, jumlah, diskonRp, diskonPersen, subtotalRp);

END$$

DELIMITER ;
;

DELIMITER $$
DROP TRIGGER IF EXISTS `SearchTransaksiPembelian`;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SearchTransaksiPembelian`(IN cariData VARCHAR(255))
BEGIN
	IF (cariData != '') THEN
		SELECT *, CONCAT(pembelian.pembelian_nomor_faktur, ' / ', pemasok.pemasok_nama, ' / ', pembelian.pembelian_tanggal, ' / ', pembelian.pembelian_total_rp)
        FROM pembelian
        LEFT JOIN pemasok ON pemasok.pemasok_id = pembelian.pembelian_pemasok_id
        WHERE (LOWER(pembelian.pembelian_nomor_faktur) LIKE CONCAT('%', LOWER(cariData), '%') 
				OR LOWER(pemasok.pemasok_nama) LIKE CONCAT('%', LOWER(cariData), '%')
                OR LOWER(pembelian.pembelian_tanggal) LIKE CONCAT('%', LOWER(cariData), '%')
                OR LOWER(CAST(pembelian.pembelian_total_rp AS CHAR)) LIKE CONCAT('%', LOWER(cariData), '%'))
		ORDER BY pembelian.pembelian_id DESC;
	ELSE
		SELECT *, CONCAT(pembelian.pembelian_nomor_faktur, ' / ', pemasok.pemasok_nama, ' / ', pembelian.pembelian_tanggal, ' / ', pembelian.pembelian_total_rp)
        FROM pembelian
        LEFT JOIN pemasok ON pemasok.pemasok_id = pembelian.pembelian_pemasok_id
        ORDER BY pembelian.pembelian_id DESC;
	END IF;

END$$

DELIMITER ;
;

DELIMITER $$
DROP TRIGGER IF EXISTS `searchTransaksiPembelianDetail`;
CREATE DEFINER=`root`@`localhost` PROCEDURE `searchTransaksiPembelianDetail`(IN pembelianId VARCHAR(255))
BEGIN
	SELECT 
		pembelian_detail.pbeli_detail_id,
		pembelian_detail.pbeli_detail_produk_id,
		pembelian_detail.pbeli_detail_satuan_id,
		produk.produk_nama,
		produk_satuan.satuan_nama,
		pembelian_detail.pbeli_detail_harga,
		pembelian_detail.pbeli_detail_jumlah,
		pembelian_detail.pbeli_detail_diskon_rp,
		pembelian_detail.pbeli_detail_persen,
		pembelian_detail.pbeli_detail_subtotal
    FROM pembelian_detail
    LEFT JOIN produk ON produk_id = pembelian_detail.pbeli_detail_produk_id
    LEFT JOIN produk_satuan ON satuan_id = pembelian_detail.pbeli_detail_satuan_id
    WHERE pembelian_detail.pbeli_detail_pembelian_id = pembelianId;

END$$

DELIMITER ;
;

DELIMITER $$
DROP TRIGGER IF EXISTS `updatePembelian`;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updatePembelian`(
		IN nomorFakturPembelianValue VARCHAR(255),
        IN tanggalPembelianValue VARCHAR(255),
        IN pemasokIdPembelianValue INT,
        IN gudangIdPembelianValue INT,
        IN totalPembelianValue VARCHAR(255),
        IN masterIdPembelianValue INT)
BEGIN
		UPDATE pembelian
        SET pembelian_nomor_faktur = nomorFakturPembelianValue, pembelian_tanggal = tanggalPembelianValue,
			pembelian_pemasok_id = pemasokIdPembelianValue, pembelian_gudang_id = gudangIdPembelianValue,
            pembelian_total_rp = totalPembelianValue
		WHERE pembelian_id = masterIdPembelianValue;

END$$

DELIMITER ;
;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
