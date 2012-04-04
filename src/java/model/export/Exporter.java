package model.export;

import com.lowagie.text.*;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import controllers.ControllerConfiguration;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import model.beans.Configuration;
import model.beans.SubPiece;
import model.beans.User;
import model.jms.JMSProccessor;
import model.util.PDFUtilities;
import model.util.PieceUtilities;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;
import view.dialogs.ChooserFile;
import view.frames.MainFrame;
import view.notifications.Notifications;
import view.panels.FactoryPanel;
import view.splits.Navigator;
import view.splits.SubNavigator;

/**
 *
 * @author skuarch
 */
public class Exporter {

    private Notifications notifications = null;

    //==========================================================================
    public Exporter() {
        notifications = new Notifications();
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

    //==========================================================================
    public void createPdfReport(Component[] subs, String path) {

        if (subs == null || subs.length < 1) {
            notifications.error("imposible create report", new NullPointerException());
        }

        if (path == null || path.length() < 1) {
            return;
        }

        SubPiece subPiece = null;
        SubNavigator subNavigator = null;
        Component[] tabs = null;
        FactoryPanel factoryPanel = null;
        ChooserFile chooserFile = null;
        Document document = null;
        PdfWriter pdfWriter = null;
        HeaderFooter headerFooter = null;
        Image image = null;
        Watermark watermark = null;
        String stringHeader = null;
        Paragraph paragraph = null;
        PdfContentByte pdfContentByte = null;
        PdfTemplate pdfTemplate = null;
        Graphics2D graphics2d = null;
        Rectangle2D rectangle2d = null;
        int height = 400;
        int width = 525;
        JFreeChart chart = null;
        Image imageChart = null;
        Table table = null;
        ArrayList arrayList = null;
        String columnNames[] = null;
        Object[][] data = null;
        Cell cell = null;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0, 0, 0));
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0));

        try {

            //jobs
            subs = Navigator.getInstance().getComponents();

            if (subs != null) {

                document = new Document(PageSize.LETTER, 50, 50, 50, 50);
                pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(path + "/pdf.pdf"));
                image = Image.getInstance(MainFrame.class.getResource("/view/images/watermark.png"));
                image.setAbsolutePosition(270, 60);
                watermark = new Watermark(image, 250, 350);
                stringHeader = "Sispro Sniffer Network   [ " + new ControllerConfiguration().getInitialConfiguration().getProjectName() + " ]      page: ";

                //firts page                
                Phrase pHeader = new Phrase(stringHeader, FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new Color(175, 175, 175)));
                headerFooter = new HeaderFooter(pHeader, true);
                document.setHeader(headerFooter);
                document.add(watermark);

                document.open();

                document.add(new Paragraph("Report information", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, new Color(0, 0, 0))));
                document.add(new Paragraph("Report created on " + new Date(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(175, 175, 175))));
                document.add(new Paragraph("Client information", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new Color(0, 0, 0))));
                document.add(new Paragraph(new ControllerConfiguration().getInitialConfiguration().getProjectName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(175, 175, 175))));
                document.add(new Paragraph("Analyst information", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new Color(0, 0, 0))));
                document.add(new Paragraph("Report created by: " + User.getInstance().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(175, 175, 175))));

                for (int i = 0; i < subs.length; i++) {

                    if (subs[i] instanceof SubNavigator) {

                        document.newPage();

                        //document.add(new Paragraph(subs[i].getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new Color(175, 175, 175))));

                        subNavigator = (SubNavigator) subs[i];

                        //tabs
                        tabs = subNavigator.getComponents();

                        for (int p = 0; p < tabs.length; p++) {

                            if (tabs[p] instanceof FactoryPanel) {

                                System.out.println(" components: " + tabs[p].getName());
                                factoryPanel = (FactoryPanel) tabs[p];
                                subPiece = factoryPanel.getSubPiece();
                                //information
                                document.add(PDFUtilities.tableSubPiece(subPiece));

                                //table or chart
                                if (subPiece.isTable()) {

                                    //creating table****************************
                                    System.out.println("is table");
                                    arrayList = (ArrayList) factoryPanel.getData();
                                    columnNames = (String[]) arrayList.get(0);
                                    data = (Object[][]) arrayList.get(1);
                                    table = new Table(columnNames.length, data.length);
                                    table.setPadding(2);
                                    table.setSpacing(2);
                                    table.setSpaceBetweenCells(0);

                                    //columns
                                    for (int j = 0; j < columnNames.length; j++) {
                                        cell = new Cell(new Paragraph(columnNames[j], headerFont));
                                        cell.setHeader(true);
                                        table.addCell(cell);
                                    }
                                    table.endHeaders();

                                    //data
                                    for (int d = 0; d < data.length; d++) {

                                        for (int c = 0; c < columnNames.length; c++) {
                                            cell = new Cell(new Paragraph(data[d][c] + "", cellFont));
                                            table.addCell(cell);
                                        }

                                    }

                                    document.add(table);
                                    document.newPage();

                                } else {

                                    //creating chart****************************
                                    chart = (JFreeChart) factoryPanel.getData();
                                    chart.setBackgroundPaint(new Color(255, 255, 255, 0));
                                    pdfContentByte = pdfWriter.getDirectContent();
                                    pdfTemplate = pdfContentByte.createTemplate(width, height);
                                    graphics2d = pdfTemplate.createGraphics(width, height, new DefaultFontMapper());
                                    rectangle2d = new Rectangle2D.Double(0, 0, width, height);
                                    chart.draw(graphics2d, rectangle2d);
                                    graphics2d.dispose();
                                    imageChart = Image.getInstance(pdfTemplate);
                                    document.add(imageChart);
                                    document.newPage();
                                }
                            }

                        } // end for tabs

                    }

                } // end for subs


            } // end if subs


        } catch (Exception e) {
            notifications.error("error creating pdf", e);
        } finally {
            document.close();
        }

    } // end createPdfReport    
} // end class
