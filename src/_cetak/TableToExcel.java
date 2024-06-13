/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package _cetak;

import java.awt.Desktop;
import java.io.File;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author HP
 */
public class TableToExcel {
    
    private static String generateDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return now.format(formatter);
    }
    
    public static void exportToExcel(JTable table, String filePath, String titleExcel) {
        Workbook workbook = new XSSFWorkbook();

        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                throw new IllegalStateException("Couldn't create directory: " + parentDir);
            }

            Sheet sheet = workbook.createSheet("Table Data");

            // Create title row
            int titleRowIndex = 0;
            Row titleRow = sheet.createRow(titleRowIndex);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(titleExcel);
            CellRangeAddress titleCellRange = new CellRangeAddress(titleRowIndex, titleRowIndex, 0, table.getColumnCount() - 1);
            sheet.addMergedRegion(titleCellRange);

            // Apply h3 formatting to the title cell
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 14);
            titleFont.setBold(true);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCell.setCellStyle(titleStyle);

            // Create header row
            int headerRowIndex = titleRowIndex + 1;
            Row headerRow = sheet.createRow(headerRowIndex);
            for (int i = 0; i < table.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(table.getColumnName(i));
            }

            // Create data rows
            for (int i = 0; i < table.getRowCount(); i++) {
                Row row = sheet.createRow(headerRowIndex + i + 1);
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    Object cellValue = table.getValueAt(i, j);
                    String cellText = (cellValue != null) ? cellValue.toString() : "";
                    cell.setCellValue(cellText);
                }
            }

            // Create footer row
            int footerRowIndex = headerRowIndex + table.getRowCount() + 1;
            Row footerRow = sheet.createRow(footerRowIndex);
            Cell footerCell = footerRow.createCell(0);
            footerCell.setCellValue("Dicetak Pada :  " + generateDate());
            CellRangeAddress footerCellRange = new CellRangeAddress(footerRowIndex, footerRowIndex, 0, table.getColumnCount() - 1);
            sheet.addMergedRegion(footerCellRange);

            // Apply h3 formatting to the footer cell
            CellStyle footerStyle = workbook.createCellStyle();
            Font footerFont = workbook.createFont();
            footerFont.setFontHeightInPoints((short) 12);
            footerFont.setItalic(true);
            footerStyle.setFont(footerFont);
            footerStyle.setAlignment(HorizontalAlignment.RIGHT);
            footerCell.setCellStyle(footerStyle);

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null, "Data berhasil diimpor menjadi Excel.");
                openExcelFile(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void openExcelFile(String filePath) {
        try {
            File file = new File(filePath);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
