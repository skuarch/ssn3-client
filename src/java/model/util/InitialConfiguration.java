package model.util;

import model.beans.Configuration;
import model.dao.DAO;

/**
 *
 * @author skuarch
 */
public class InitialConfiguration {

    //==========================================================================
    public InitialConfiguration() {
    }

    //==========================================================================
    public Configuration getInicialConfiguration() throws Exception {

        Configuration configuration = null;        

        try {
            
            configuration = (Configuration) new DAO().get(1, Configuration.class);

            if (configuration == null) {
                configuration = createDefaultConfiguration();
                new DAO().create(configuration);
            }

        } catch (Exception e) {
            throw e;
        }

        return configuration;

    } // end getInitialConfiguration

    //==========================================================================
    private Configuration createDefaultConfiguration() throws Exception {

        Configuration configuration = null;

        try {

            System.out.println("creating default configuration");
            configuration = new Configuration();
            configuration.setProjectName("SSN");
            configuration.setJmsTimeWaitConnectivity(5000); // 5 seconds
            configuration.setJmsTimeWaitMessage(60000); // 60 seconds
            configuration.setJWSPath("http://localhost:8080/ssn/ssn-app-client");
            configuration.setHelpPath("http://localhost:8080/ssn-war/help");

        } catch (Exception e) {
            throw e;
        }

        return configuration;

    } // end createInicitialConfiguration
} // end class

