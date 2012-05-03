package controllers;

import model.beans.Collectors;
import model.util.ModelThresholdsCaptures;

/**
 *
 * @author skuarch
 */
public class ControllerThresholds {

    //==========================================================================
    public ControllerThresholds(){

    } // end ControllerThresholds    
    
    //==========================================================================
    public Collectors[] getCollectorsThresholds() throws Exception{
        return new ModelThresholdsCaptures().getCollectorsThresholdsCaptures();
    } // end getCollectorsThresholds

} // end class
