package controllers;

import model.mail.SendMail;

/**
 *
 * @author skuarch
 */
public class ControllerMail {

    //==========================================================================
    public ControllerMail(){
    } // end ControllerMail

    //==========================================================================
    public void sendEventViewer(String text) throws Exception{
        new SendMail().sendEventViewer(text);
    } // end sendEventViewer    
    
} // end class
