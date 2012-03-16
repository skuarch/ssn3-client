package model.util;

/**
 * this class is for be using in thread or something that doesn't able throws Exception
 * @author skuarch
 */
public class Thrower extends Exception {

    //==========================================================================
    public Thrower() {
    } // end SSNException

    //==========================================================================
    public void exception(Exception e) {
        
        try {
            throw e;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    //==========================================================================
    public void nullPointerException(String message) {

        try {
            throw new NullPointerException(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
} // end class