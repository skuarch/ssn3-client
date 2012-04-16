package model.util;

import controllers.ControllerConfiguration;
import model.beans.Configuration;
import model.beans.SubPiece;
import model.jms.JMSProccessor;

/**
 *
 * @author skuarch
 */
public class DataTable {

    //==========================================================================
    public DataTable() {
    } // end DataTable

    //==========================================================================
    public Object getDataTable(SubPiece subPiece) throws Exception {

        if (subPiece == null) {
            throw new NullPointerException("subpiece is null");
        }

        Object object = null;
        Configuration configuration = null;

        try {

            configuration = new ControllerConfiguration().getInitialConfiguration();
            object = new JMSProccessor().sendReceive(subPiece.getView(), subPiece.getCollector(), "srs", configuration.getJmsTimeWaitMessage(), new PieceUtilities().subPieceToHashMap(subPiece));

        } catch (Exception e) {
            throw e;
        } finally {
            return object;
        }

    } // end getDataTable
} // end class
