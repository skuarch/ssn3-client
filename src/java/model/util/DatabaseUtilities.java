package model.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import model.ssn.Main;

/**
 *
 * @author skuarch
 */
public class DatabaseUtilities {

    //==========================================================================
    public static DataSource getDataSource() {
        return Main.getDataSource();
    }

    //==========================================================================
    public static void closeConnection(Connection connection) {
        try {

            if (connection != null) {
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection = null;
        }
    } // end closeConnection

    //==========================================================================
    public static void closeStatement(Statement statement) {
        try {

            if (statement != null) {
                statement.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
        }
    } // end closeStatement

    //==========================================================================
    public static void closeResultSet(ResultSet resultSet) {
        try {

            if (resultSet != null) {
                resultSet.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resultSet = null;
        }
    } // end closeStatement
    
} // end class

