package view.panels;

import model.beans.SubPiece;

/**
 *
 * @author skuarch
 */
public class BandwidthOverTimeBitsLive extends LineChartPanelLive {

    private SubPiece subPiece = null;

    //==========================================================================
    public BandwidthOverTimeBitsLive(SubPiece subPiece) throws Exception{
        super(subPiece);
        this.subPiece = subPiece;
        onLoad();
    } // end BandwidthOverTimeBitsLive

    //==========================================================================
    private void onLoad() {
        setname();
    }    
    
} // end class