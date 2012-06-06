package view.panels;

import model.beans.SubPiece;

/**
 *
 * @author skuarch
 */
public class EndtoEnd extends BarChartHorizontalPanel {

    public EndtoEnd(SubPiece subPiece) {
        super(subPiece);
        showPagination(false);   
        setTitleChart(subPiece.getCollector() + " ->  " + subPiece.getE2E());
        setEnableTableButtonFooter(false);
        setEnableDropTarget(false);
        setEnableList(false);        
        setEnableExportButtonFooter(false);
        showList(false);        
        execute();        
    }
    
}
