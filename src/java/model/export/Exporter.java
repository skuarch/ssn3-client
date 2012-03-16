package model.export;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import controllers.ControllerConfiguration;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import model.beans.Configuration;
import model.beans.SubPiece;
import model.jms.JMSProccessor;
import model.util.DateUtilities;
import model.util.PieceUtilities;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author skuarch
 */
public class Exporter {

    //==========================================================================
    public Exporter() {
    } // end Exporter

    //==========================================================================
    public void exportTableToExcel(SubPiece subPiece, String path) throws Exception {

        ArrayList arrayList = null;
        HSSFRow headers = null;
        HSSFRow rows = null;
        String stringTmp = null;
        Double doubleTmp = null;
        Boolean booleanTmp = false;
        int intTmp = 0;
        Float floatTmp = null;
        Date dateTmp = null;
        String nameOfFile = null;
        String[] columnNames = null;
        Object[][] data = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        FileOutputStream fileOut = null;
        int time = 0;
        SubPiece nsg = null;
        Configuration configuration = null;

        try {

            nsg = new PieceUtilities().createSubPieceFromSubPiece(subPiece);
            nsg.isTable(true);
            configuration = new ControllerConfiguration().getInitialConfiguration();

            nameOfFile = subPiece.getJob() + "  " + subPiece.getView() + ".xls";
            path = path + "/" + nameOfFile;

            time = configuration.getJmsTimeWaitMessage();

            if (subPiece.getView().contains("Table")) {
                nsg.setView(subPiece.getView());
            } else {
                nsg.setView(subPiece.getView() + " Table");
            }

            arrayList = (ArrayList) new JMSProccessor().sendReceive(nsg.getView(), nsg.getCollector(), "srs", time, new PieceUtilities().subPieceToHashMap(nsg));

            columnNames = (String[]) arrayList.get(0);
            data = (Object[][]) arrayList.get(1);

            wb = new HSSFWorkbook();
            sheet = wb.createSheet(subPiece.getView()); //sheet name

            // encabezado
            headers = sheet.createRow((short) 0); //columnas

            //poniendo las columnas
            for (int i = 0; i < columnNames.length; i++) {
                headers.createCell((int) i).setCellValue(columnNames[i]);
            }

            // poniendo los datos
            for (int d = 0; d < data.length; d++) {

                rows = sheet.createRow((short) d + 1);

                for (int c = 0; c < columnNames.length; c++) {

                    if (data[d][c] instanceof String) {
                        stringTmp = (String) data[d][c];
                        rows.createCell((int) c).setCellValue(stringTmp);
                    }

                    if (data[d][c] instanceof Double) {
                        doubleTmp = (Double) data[d][c];
                        rows.createCell((int) c).setCellValue(doubleTmp);
                    }

                    if (data[d][c] instanceof Boolean) {
                        booleanTmp = (Boolean) data[d][c];
                        rows.createCell((int) c).setCellValue(booleanTmp);
                    }

                    if (data[d][c] instanceof Integer) {
                        intTmp = (Integer) data[d][c];
                        rows.createCell((int) c).setCellValue(intTmp);
                    }

                    if (data[d][c] instanceof Float) {
                        floatTmp = (Float) data[d][c];
                        rows.createCell((int) c).setCellValue(floatTmp);
                    }

                    if (data[d][c] instanceof Date) {
                        dateTmp = (Date) data[d][c];
                        rows.createCell((int) c).setCellValue(dateTmp);
                    }
                } // nested for
            } // firts for

            fileOut = new FileOutputStream(path);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();

        } catch (Exception e) {
            throw e;
        } finally {
            arrayList = null;
            headers = null;
            rows = null;
            columnNames = null;
            data = null;
            rows = null;
            sheet = null;
            wb = null;
            fileOut = null;
            subPiece = null;
        }

    } // end exportExcel

    //==========================================================================
    public void exportChartToPDF(JFreeChart chart, SubPiece subPiece, String path) throws Exception {

        String fileName = null;
        int height = 500;
        int width = 600;
        PdfWriter writer = null;
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);
        PdfContentByte contentByte = null;
        PdfTemplate template = null;
        Graphics2D graphics2d = null;
        Rectangle2D rectangle2d = null;
       
        try {
            
            fileName = subPiece.getView() + ".pdf";
            path = path + "/" + fileName;                        
            writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            writer.setPageSize(PageSize.LETTER);
            document.open();
            
            document.add(new Paragraph("Sispro Sniffer Network"));
            contentByte = writer.getDirectContent();
            template = contentByte.createTemplate(width, height);
            graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
            rectangle2d = new Rectangle2D.Double(0, 0, width, height);
            chart.draw(graphics2d, rectangle2d);
            graphics2d.dispose();
            contentByte.addTemplate(template, 0, writer.getVerticalPosition(true) - height);

        } catch (Exception ex) {
            throw ex;
        } finally {
            document.close();
        }

    } // end exportPDF
} // end class
