package model.util;

import controllers.ControllerConfiguration;
import java.util.ArrayList;
import model.beans.Configuration;
import model.beans.SubPiece;
import model.jms.JMSProccessor;

/**
 *
 * @author skuarch
 */
public class Series {

    //==========================================================================
    public Series() {
    } // end Series

    //==========================================================================
    public ArrayList getSeriesLive(SubPiece subPiece) throws Exception {

        if (subPiece == null) {
            throw new NullPointerException("subpiece is null");
        }

        ArrayList arrayList = null;
        JMSProccessor jmsp = null;
        Configuration configuration = null;

        try {

            configuration = new ControllerConfiguration().getInitialConfiguration();
            jmsp = new JMSProccessor();
            arrayList = (ArrayList) jmsp.sendReceive(subPiece.getView(), subPiece.getCollector(), "srs", configuration.getJmsTimeWaitMessage(), new PieceUtilities().subPieceToHashMap(subPiece));

        } catch (Exception e) {
            throw e;
        } finally {
            configuration = null;
            jmsp = null;
        }

        return arrayList;
    }
} // end class
