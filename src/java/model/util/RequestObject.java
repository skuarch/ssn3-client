package model.util;

import controllers.ControllerConfiguration;
import model.beans.SubPiece;
import model.jms.JMSProccessor;

/**
 *
 * @author skuarch
 */
public class RequestObject {

    //==========================================================================
    public RequestObject() {
    } // end RequestObject

    //==========================================================================
    public Object getObject(SubPiece subPiece) throws Exception {

        int time = 0;
        JMSProccessor jmsp = null;
        Object object = null;

        try {

            time = new ControllerConfiguration().getInitialConfiguration().getJmsTimeWaitMessage();
            jmsp = new JMSProccessor();
            object = jmsp.sendReceive(subPiece.getView(), subPiece.getCollector(), "srs", time, new PieceUtilities().subPieceToHashMap(subPiece));

        } catch (Exception e) {
            throw e;
        }

        return object;
    }
} // end class
