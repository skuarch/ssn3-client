package model.util;

import java.util.ArrayList;
import java.util.List;
import model.beans.Collectors;
import model.dao.DAO;
import view.notifications.Notifications;

/**
 *
 * @author skuarch
 */
public class ModelCollectors {

    private Notifications notifications = null;

    //==========================================================================
    public ModelCollectors() {
        this.notifications = new Notifications();
    } // end ModelCollectors

    //==========================================================================
    public Collectors[] getCollectors() {

        Collectors[] collectors = null;

        try {

            collectors = doCast(new DAO().getAll("Collectors"));

        } catch (Exception e) {
            notifications.error("error getting collectors", e);
        }

        return collectors;

    } // end getCollectors

    //==========================================================================
    public Collectors[] getActivesCollectors() {

        Collectors[] collectors = null;
        List<Collectors> activeCollectors = null;

        try {

            collectors = getCollectors();
            activeCollectors = new ArrayList<Collectors>();

            for (int i = 0; i < collectors.length; i++) {
                if (collectors[i].getIsActive() == 1) {
                    activeCollectors.add(collectors[i]);
                }
            }

        } catch (Exception e) {
            notifications.error("error getting actives collectors", e);
        }

        return doCast(activeCollectors);

    } // end getActivesCollectors

    //==========================================================================
    public String[] getActivesCollectorsArray() {

        Collectors[] collectors = null;
        String[] collectorsArray = null;

        try {

            collectors = getCollectors();
            collectorsArray = new String[collectors.length];

            for (int i = 0; i < collectors.length; i++) {
                if (collectors[i].getIsActive() == 1) {
                    collectorsArray[i] = collectors[i].getHost();
                }
            }

        } catch (Exception e) {
            notifications.error("error getting actives collectors", e);
        }

        return collectorsArray;

    }

    //==========================================================================
    private Collectors[] doCast(List list) {

        if (list == null) {
            return null;
        }

        Collectors[] collectors = null;

        try {

            collectors = new Collectors[list.size()];

            for (int i = 0; i < list.size(); i++) {
                collectors[i] = (Collectors) list.get(i);
            }

        } catch (Exception e) {
            notifications.error("error casting object to collector", e);
        } finally {
            list = null;
        }

        return collectors;
    } // end doCast
} // end class

