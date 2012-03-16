package model.net;

import com.sun.rowset.CachedRowSetImpl;
import java.rmi.UnexpectedException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import model.util.DatabaseUtilities;

/**
 *
 * @author skuarch
 */
public class ConnectPool {

    private int reconnections = 0;

    //==========================================================================
    public ConnectPool() {
    } // end ConnectPool    

    //==========================================================================
    private Connection getConnection() throws Exception {

        Connection connection = null;
        DataSource dataSource = null;

        try {

            dataSource = DatabaseUtilities.getDataSource();

            if (dataSource != null) {
                connection = dataSource.getConnection();
            } else {
                tryReconnect();
            }

        } catch (NullPointerException npe) {
            tryReconnect();
        } catch (Exception e) {
            DatabaseUtilities.closeConnection(connection);
            throw e;
        }

        return connection;

    } // end getConnection

    //=========================================================================    
    public ResultSet executeQuery(String sql) throws Exception {

        if (sql == null || sql.length() < 1) {
            throw new NullPointerException("sql is null or empty");
        }

        ResultSet resultSet = null;
        Connection connection = null;
        Statement statement = null;
        CachedRowSet crs = null;

        try {

            connection = getConnection();

            if (connection == null) {
                return null;
            }

            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);

            //resultset is null ?
            if (resultSet == null) {
                throw new NullPointerException("resultSet is null");
            }

            crs = new CachedRowSetImpl();
            crs.populate(resultSet);

        } catch (Exception e) {
            throw e;
        } finally {
            DatabaseUtilities.closeConnection(connection);
            DatabaseUtilities.closeStatement(statement);
            DatabaseUtilities.closeResultSet(resultSet);
        }

        return crs;
    } //end executeQuery

    //==========================================================================
    public void update(String sql) throws Exception {

        if (sql == null || sql.length() < 1) {
            throw new NullPointerException("sql is null or empty");
        }

        Connection connection = null;
        Statement statement = null;

        try {

            connection = getConnection();

            if (connection == null) {
                return;
            }

            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            throw e;
        } finally {
            DatabaseUtilities.closeConnection(connection);
            DatabaseUtilities.closeStatement(statement);
            sql = null;
        }

    } //end update

    //==========================================================================
    public int getNumRows(ResultSet resultSet) throws Exception {

        if (resultSet == null) {
            throw new NullPointerException("resultSet is null");
        }

        int numRows = 0;

        try {

            resultSet.last();
            numRows = resultSet.getRow();
            resultSet.beforeFirst();

        } catch (Exception e) {
            throw e;
        }

        return numRows;

    } // getNumRows

    //==========================================================================
    private void tryReconnect() throws Exception {

        try {

            if (reconnections >= 3) {
                throw new UnexpectedException("imposible establish connection with dataBase");
            }

            reconnections++;
            Thread.sleep(4000);
            System.out.println("trying reconnect with database " + reconnections);
            getConnection();

        } catch (Exception e) {
            throw e;
        }

    } // end tryReconect
} // end ConnectPool

