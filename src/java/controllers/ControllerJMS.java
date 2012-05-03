package controllers;

import model.beans.SubPiece;
import model.util.ModelJMS;

/**
 *
 * @author skuarch
 */
public class ControllerJMS {

    //==========================================================================
    public ControllerJMS(){

    } // end ControllerJMS

    //==========================================================================
    public void send(String select, String sendTo,SubPiece subPiece) throws Exception {
        
        if(select == null || select.length() < 1 || sendTo == null || sendTo.length() < 1 || subPiece == null){
            throw new NullPointerException("some parameter is null");            
        }
        
        new ModelJMS().send(select, sendTo, subPiece);
    } // end send
    
    
} // end class
