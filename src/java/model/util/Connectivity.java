package model.util;

import controllers.ControllerConfiguration;
import model.beans.Configuration;
import model.beans.SubPiece;
import model.jms.JMSProccessor;

/**
 *
 * @author skuarch
 */
public class Connectivity {

    private Configuration configuration = null;

    //==========================================================================
    public Connectivity() throws Exception {
        configuration = new ControllerConfiguration().getInitialConfiguration();
    } // end Connectivity    

    //==========================================================================
    public boolean requestConnectivity(String host) throws Exception {

        if (host == null || host.length() < 1) {
            throw new NullPointerException("host is empty or null");
        }

        boolean flag = false;
        int time = 0;
        Object object = null;

        try {

            time = configuration.getJmsTimeWaitConnectivity();
            object = new JMSProccessor().sendReceive("connectivity", host, "srs", time, new PieceUtilities().subPieceToHashMap(new SubPiece()));
            
            if(object !=null){
                flag = (Boolean) object;
            }            

        } catch (Exception e) {
            throw e;
        } finally {
            configuration = null;
        }

        return flag;

    } // end requestConnectivity
} // end class
