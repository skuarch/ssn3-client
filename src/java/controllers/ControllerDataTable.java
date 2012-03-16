package controllers;

import model.beans.SubPiece;
import model.util.DataTable;

/**
 *
 * @author skuarch
 */
public class ControllerDataTable {

    //==========================================================================
    public ControllerDataTable(){
    
    } // end ControllerDataTable
    
    //==========================================================================
    public Object getData(SubPiece subPiece) throws Exception{
        return new DataTable().getDataTable(subPiece);
    } // end getData
    
    
} // end class
