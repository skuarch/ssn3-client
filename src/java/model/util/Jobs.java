package model.util;

import controllers.ControllerConfiguration;
import model.beans.SubPiece;
import model.jms.JMSProccessor;
import view.notifications.Notifications;

/**
 *
 * @author skuarch
 */
public class Jobs {

    private Notifications notifications = null;

    //==========================================================================
    public Jobs() {
        this.notifications = new Notifications();
    } // end jobs

    //==========================================================================
    public String[] getJobs(String collector) {

        if (collector == null || collector.length() < 1) {
            notifications.error("error collector is null or empty", new Exception("collector is null or empty"));
            return null;
        }

        String[] jobs = null;
        SubPiece subPiece = null;
        int time = 0;

        try {
            
            subPiece = new SubPiece();
            subPiece.setView("getJobs");
            subPiece.setCollector(collector);
            subPiece.setJob("not applicable");
            time = new ControllerConfiguration().getInitialConfiguration().getJmsTimeWaitConnectivity();

            jobs = (String[]) new JMSProccessor().sendReceive("getJobs", collector, "srs", time, new PieceUtilities().subPieceToHashMap(subPiece));

        } catch (Exception e) {
            notifications.error("error getting the jobs", e);
        } finally {
            subPiece = null;
        }

        return jobs;

    } // end getJobs
} // end class

