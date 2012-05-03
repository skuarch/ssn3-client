package controllers;

import model.beans.Collectors;
import model.util.ModelCollectors;

/**
 *
 * @author skuarch
 */
public class ControllerCollectors {

    //==========================================================================
    public ControllerCollectors() {
    } // end ControllerCollectors

    //==========================================================================
    public Collectors[] getActiveCollectors() throws Exception {
        return new ModelCollectors().getActivesCollectors();
    }

    //==========================================================================
    public Collectors[] getActivesCollectorsArray() throws Exception {
        return new ModelCollectors().getActivesCollectorsArray();
    }
} // end class
