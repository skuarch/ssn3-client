package model.util;

import java.io.Serializable;

/**
 *
 * @author skuarch
 */
public class SerializableObject {

    //==========================================================================
    public SerializableObject(){        
    } // end SerializableObject
    
    //==========================================================================
    public Object getSerializableObject(Object object){        
        return (Serializable) object;
    }    

} // end class
