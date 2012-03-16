package view.helpers;

import java.awt.Component;
import java.lang.reflect.Constructor;
import model.beans.SubPiece;
import view.notifications.Notifications;
import view.panels.FactoryPanel;

/**
 *
 * @author skuarch
 */
public class HandlerPanels {

    private Notifications notifications = null;

    //==========================================================================
    public HandlerPanels() {
        this.notifications = new Notifications();
    } // end HandlerPanels    

    //==========================================================================
    public Component getComponent(SubPiece subPiece) {

        if (subPiece == null) {
            notifications.error("subPiece is null", new NullPointerException("subPiece is null"));
            return null;
        }

        FactoryPanel factoryPanel = null;
        String stringClass = null;
        Class cls = null;

        try {

            //Your class in string and no spaces
            stringClass = "view.panels." + subPiece.getView().trim();
            stringClass = stringClass.replace(" ", "");

            //Get class definition
            cls = Class.forName(stringClass);

            //Get contructors and run
            Constructor c[] = cls.getConstructors();
            
            //set view in the subPiece
            factoryPanel = (FactoryPanel) c[0].newInstance(subPiece); //run constructor 
            factoryPanel.setName(subPiece.getView());

        } catch (ClassNotFoundException cnfe) {
            notifications.error(subPiece.getView() + ", the panel doesn't exists", cnfe);
        } catch (Exception e) {
            notifications.error("error creating panel", e);
        } finally {
            cls = null;
            stringClass = null;
        }

        return factoryPanel;

    } // end getComponents
} // end class

