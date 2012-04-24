package model.util;

import com.lowagie.text.*;
import java.awt.Color;
import model.beans.SubPiece;

/**
 *
 * @author skuarch
 */
public class PDFUtilities {

    //==========================================================================
    public static Table tableSubPiece(SubPiece subPiece) throws Exception {

        if (subPiece == null) {
            throw new NullPointerException("subpiece is null");
        }


        Table table = null;
        Cell c1 = null;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0, 0, 0));
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0));

        try {

            table = new Table(2, 3);
            table.setPadding(2);
            table.setSpacing(2);
            table.setSpaceBetweenCells(0);
            c1 = new Cell(new Paragraph("Propertie", headerFont));
            c1.setHeader(true);
            table.addCell(c1);
            c1 = new Cell(new Paragraph("Value", headerFont));
            table.addCell(c1);
            table.endHeaders();

            table.addCell(new Paragraph("collector", cellFont));
            table.addCell(new Paragraph(subPiece.getCollector(), cellFont));
            table.addCell(new Paragraph("job", cellFont));
            table.addCell(new Paragraph(subPiece.getJob(), cellFont));

            if (!subPiece.getDates().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("dates", cellFont));
                table.addCell(new Paragraph(subPiece.getDates(), cellFont));
            }

            if (!subPiece.getIpAddress().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("ip address", cellFont));
                table.addCell(new Paragraph(subPiece.getIpAddress(), cellFont));
            }

            if (!subPiece.getDrillDown().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("drill down", cellFont));
                table.addCell(new Paragraph(subPiece.getDrillDown(), cellFont));
            }

            if (!subPiece.getIPProtocols().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("ip protocols", cellFont));
                table.addCell(new Paragraph(subPiece.getIPProtocols(), cellFont));
            }

            if (!subPiece.getNetworkProtocols().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("network protocols", cellFont));
                table.addCell(new Paragraph(subPiece.getNetworkProtocols(), cellFont));
            }

            if (!subPiece.getTCPProtocols().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("tcp protocols", cellFont));
                table.addCell(new Paragraph(subPiece.getTCPProtocols(), cellFont));
            }

            if (!subPiece.getUDPProtocols().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("udp protocols", cellFont));
                table.addCell(new Paragraph(subPiece.getUDPProtocols(), cellFont));
            }

            if (!subPiece.getTypeService().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("type service", cellFont));
                table.addCell(new Paragraph(subPiece.getTypeService(), cellFont));
            }

            if (!subPiece.getWebsites().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("websites", cellFont));
                table.addCell(new Paragraph(subPiece.getWebsites(), cellFont));
            }

            if (!subPiece.getHostname().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("hostname", cellFont));
                table.addCell(new Paragraph(subPiece.getHostname(), cellFont));
            }

        } catch (Exception e) {
            throw e;
        }

        return table;

    } // end tableSubPiece
} // end class
