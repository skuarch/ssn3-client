package model.util;

import java.util.ArrayList;
import model.beans.Collectors;
import model.beans.SubPiece;

/**
 *
 * @author skuarch
 */
public class ModelThresholdsCaptures {

    //==========================================================================
    public ModelThresholdsCaptures() {
    } // end ModelThresholds

    //==========================================================================
    public Collectors[] getCollectorsThresholdsCaptures() throws Exception {

        Collectors[] activeCollectors = null;
        Collectors[] thresholdCollectors = null;
        ArrayList<Collectors> collectorsList = new ArrayList<Collectors>();
        SubPiece subPiece = new SubPiece();        
        boolean flag = false;        
        ThresholdCapturesConnectivity tc = new ThresholdCapturesConnectivity();

        try {

            subPiece.setView("threshold have captures");
            activeCollectors = new ModelCollectors().getActivesCollectors();                        
            for (int i = 0; i < activeCollectors.length; i++) {
                
                flag = tc.requestConnectivity(activeCollectors[i].getHost());               
                
                if(flag){
                    collectorsList.add(activeCollectors[i]);
                }               

            }
            
            thresholdCollectors = new Collectors[collectorsList.size()];            
            for (int i = 0; i < collectorsList.size(); i++) {
              thresholdCollectors[i] = collectorsList.get(i);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            return thresholdCollectors;
        }

    } //end getCollectorsThresholdsCaptures
} 