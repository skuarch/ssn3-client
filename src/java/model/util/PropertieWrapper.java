package model.util;

import java.io.InputStream;

/**
 *
 * @author skuarch
 */
public class PropertieWrapper {

    //==========================================================================
    public static String getStringValue(String propertieName) throws Exception {

        InputStream inputStream = null;
        LoaderProperties loaderProperties = null;
        String propertie = null;

        try {
            
            inputStream = PropertieWrapper.class.getResourceAsStream("/model/resources/configuration.properties");
            loaderProperties = new LoaderProperties(inputStream);
            propertie = loaderProperties.getProperties().getProperty(propertieName);
            
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtilities.closeInputStream(inputStream);
        }

        return propertie;

    } // end getStringValue
} // end class
