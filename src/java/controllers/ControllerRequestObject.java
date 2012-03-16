package controllers;

import model.beans.SubPiece;
import model.util.RequestObject;

/**
 *
 * @author skuarch
 */
public class ControllerRequestObject {

    //==========================================================================
    public ControllerRequestObject(){

    } // end ControllerObject
    
    //==========================================================================
    public Object getObject(SubPiece subPiece) throws Exception{
        return new RequestObject().getObject(subPiece);
    }

} // end class