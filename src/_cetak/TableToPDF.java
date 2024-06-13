package _cetak;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class TableToPDF {
    
    private static String generateDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return now.format(formatter);
    }
    
    public static void exportTableToPDF(JTable table, String outputPath, String titleTable) {
        Document document = new Document(PageSize.A4);

        File file = new File(outputPath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new IllegalStateException("Couldn't create directory: " + parentDir);
        }
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // Add centered title with larger font size
            Paragraph title = new Paragraph(titleTable, FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());

            // Add table header
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(new PdfPCell(new Phrase(table.getColumnName(i))));
            }

            // Add table rows
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Object cellValue = table.getValueAt(i, j);
                    String cellText = (cellValue != null) ? cellValue.toString() : "";
                    pdfTable.addCell(cellText);
                }
            }

            document.add(pdfTable);
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Paragraph footer = new Paragraph("Dicetak Pada" + generateDate(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC));
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

            document.close();

            JOptionPane.showMessageDialog(null, "Data berhasil diimpor menjadi PDF..");
            openPdfFile(outputPath);

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static void openPdfFile(String filePath) {
        try {
            File file = new File(filePath);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
