package controllers;

import model.util.Jobs;

/**
 *
 * @author skuarch
 */
public class ControllerJobs {

    //==========================================================================
    public ControllerJobs(){
    } // end controllerJobs

    //==========================================================================
    public String[] getJobs(String collector) throws Exception{
        return new Jobs().getJobs(collector);
    } // end getJobs


} // end class
