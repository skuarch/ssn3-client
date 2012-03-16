package controllers;

/**
 *
 * @author skuarch
 */
public class ControllerConnectivity {

    //==========================================================================
    public ControllerConnectivity(){

    } // end ControllerConnectivity
    
    //==========================================================================
    public boolean requestConnectivity(String host) throws Exception {
        return new ControllerConnectivity().requestConnectivity(host);
    }

} // end class
