package model.util;

import controllers.ControllerConfiguration;
import model.beans.Configuration;
import model.beans.SubPiece;
import model.jms.JMSProccessor;

/**
 *
 * @author skuarch
 */
public class ThresholdCapturesConnectivity {

    //==========================================================================
    public ThresholdCapturesConnectivity() {
    } // end ThresholdConnectivity

    //==========================================================================
    public boolean requestConnectivity(String host) throws Exception {

        if (host == null || host.length() < 1) {
            throw new NullPointerException("host is empty or null");
        }

        SubPiece subPiece = new SubPiece();
        boolean flag = false;
        Configuration configuration = null;
        int time = 0;
        Object object = null;

        try {

            subPiece.setView("threshold have captures");
            configuration = new ControllerConfiguration().getInitialConfiguration();
            time = configuration.getJmsTimeWaitConnectivity();
            object = new JMSProccessor().sendReceive("threshold have captures", host, "srs", time, new PieceUtilities().subPieceToHashMap(subPiece));

            if (object != null) {
                flag = (Boolean) object;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            configuration = null;
            return flag;
        }

    } // end requestConnectivity
} 