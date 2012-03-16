package model.util;

import java.net.InetAddress;
import java.util.Date;


/**
 * create a string key.
 * @author skuarch
 */
public class KeyGenerator {

    //=========================================================================
    /**
     * constructor without parameters.
     */
    public KeyGenerator() {
    } //end KeyGenerator
    
    //=========================================================================
    /**
     * this create a key, with hostname, num random, timestamp, separated by comma.
     * this key is for send one request and wait the response with the same key.
     * @return String
     */
    public String generateKey() throws Exception {

        //hostname, num random, timestamp
        String key = null;
        String myHostname = null;
        double random = 0;
        long timeStamp = 0;

        try {

            myHostname = InetAddress.getLocalHost().getHostName();
            random = Math.random();
            timeStamp = new Date().getTime();

            key = myHostname + "," + random +"," +timeStamp;

        } catch (Exception e) {
            throw e;
        } finally {
            random = 0;
            timeStamp = 0;
        }

        if (key == null) {
            throw new NullPointerException("key is null");
        }

        return key;

    } // end generateKey
} //end class