package model.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author skuarch
 */
public class DateUtilities {

    //=========================================================================
    public static String getDate(double timeStamp) throws Exception {        
        
        if(timeStamp < 1){
            throw new Exception("the timeStamp is incorrect");
        }
        
        Timestamp t = new Timestamp((long) timeStamp);
        return t.toString().substring(0, 19);

    } // end getDate

    //=========================================================================
    public static String getCurrentDate() {

        Timestamp timestamp = null;
        timestamp = new java.sql.Timestamp(new Date().getTime());
        return timestamp.toString().substring(0, 19);
        
    } // end getCurrentDate
} // end class
