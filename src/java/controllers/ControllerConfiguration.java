package controllers;

import model.beans.Configuration;
import model.dao.DAO;
import model.util.InitialConfiguration;

/**
 *
 * @author skuarch
 */
public class ControllerConfiguration {

    //==========================================================================
    public ControllerConfiguration() {
    } // end ControllerConfiguration

    //==========================================================================
    public Configuration getInitialConfiguration() throws Exception {
        return new InitialConfiguration().getInicialConfiguration();
    } // end getInitialConfiguration

    //==========================================================================
    public void deleteConfiguration(Configuration configuration) throws Exception {
        new DAO().delete(configuration);
    } // end deleteConfiguration

    //==========================================================================
    public long saveConfiguration(Configuration configuration) throws Exception {
        return new DAO().create(configuration);
    }

    //==========================================================================
    public void truncateConfiguration() throws Exception {
        new DAO().deleteAll("configuration");
    } // end truncateConfiguration
} // end class
