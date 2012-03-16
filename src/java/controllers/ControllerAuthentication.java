package controllers;

import model.authentication.Authentication;

/**
 *
 * @author skuarch
 */
public class ControllerAuthentication {

    //==========================================================================
    public ControllerAuthentication() {
    } // end ControllerAuthentication

    //==========================================================================
    public boolean login(String user, String password) throws Exception {
        return new Authentication().validateUser(user, password);
    } // end login
} // end class

