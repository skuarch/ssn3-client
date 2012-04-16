package model.util;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author skuarch
 */
public class LoaderProperties {
    
    private InputStream inputStream = null;
    
    //==========================================================================
    public LoaderProperties(InputStream inputStream) {
        this.inputStream = inputStream;
    } // end LoaderProperties

    //==========================================================================
    public Properties getProperties() throws Exception {

        if (inputStream == null) {
            throw new NullPointerException("inputstream is null");
        }

        Properties properties = null;

        try {

            properties = new Properties();
            properties.load(inputStream);

        } catch (Exception e) {
            throw e;
        } finally {
            IOUtilities.closeInputStream(inputStream);            
        }

        return properties;

    } // end getProperties
} // end class
