package view.charts;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.data.category.CategoryDataset;
import view.notifications.Notifications;

/**
 * A panel for the demo, encapsulating the chart mouse listener.
 */
class MyDemoPanel extends DemoPanel implements ChartMouseListener {

    private Notifications notifications = null;
    private MyBarRenderer renderer;
    private JList list = null;
    private ArrayList<String> clicks = null; // for save clicks

    //==========================================================================
    /**
     * Creates a new demo panel.
     *
     * @param renderer  the renderer.
     */
    public MyDemoPanel(MyBarRenderer renderer, JList list) {
        super(new BorderLayout());
        this.notifications = new Notifications();
        this.renderer = renderer;
        this.list = list;
        list.setComponentPopupMenu(getMenuList());
        this.clicks = new ArrayList<String>();
    }

    //==========================================================================
    /**
     * On a mouse move event, we check what is under the mouse pointer - if
     * it is a data item, we update the renderer so that it highlights this
     * item.
     *
     * @param event  the mouse event.
     */
    public void chartMouseMoved(ChartMouseEvent event) {
        ChartEntity entity = event.getEntity();
        if (!(entity instanceof CategoryItemEntity)) {
            this.renderer.setHighlightedItem(-1, -1);
            return;
        }
        CategoryItemEntity cie = (CategoryItemEntity) entity;
        CategoryDataset dataset = cie.getDataset();
        this.renderer.setHighlightedItem(dataset.getRowIndex(cie.getRowKey()),
                dataset.getColumnIndex(cie.getColumnKey()));

    }

    //==========================================================================
    /**
     * Handles a mouse click by ignoring it.
     *
     * @param event  info about the mouse event.
     */
    public void chartMouseClicked(ChartMouseEvent event) {
        ChartEntity entity = event.getEntity();

        if (!(entity instanceof CategoryItemEntity)) {
            this.renderer.setHighlightedItem(-1, -1);
            return;
        }

        CategoryItemEntity cie = (CategoryItemEntity) entity;
        //CategoryDataset dataset = cie.getDataset();

        if (event.getEntity().toString().contains("CategoryItemEntity")) {
            setArrayList(saveClicks(cie.getColumnKey().toString()));
        }
    }

    //==========================================================================
    private ArrayList<String> saveClicks(String select) {

        boolean found = false;

        try {

            if (clicks.size() == 0) {

                clicks.add(select);

            } else {

                for (int i = 0; i < clicks.size(); i++) {

                    if (clicks.get(i).toString().equalsIgnoreCase(select)) {
                        //si ya esta lo quito
                        clicks.remove(i);
                        found = true;
                        break;
                    }
                }// end for

                if (found == false) {
                    clicks.add(select);
                }

            } // end if else

        } catch (Exception e) {
            notifications.error("error creating chart", e);
        }

        return clicks;
    } // end saveClicks

    //==========================================================================
    private void setArrayList(ArrayList<String> arrayList) {

        Object[] data = null;

        try {

            //clean area
            list.removeAll();

            data = new Object[arrayList.size()];

            //put infomation
            for (int i = 0; i < arrayList.size(); i++) {
                data[i] = arrayList.get(i);
            }


            list.setListData(data);

        } catch (Exception e) {
            notifications.error("error creating chart", e);
        }

    } // end areaSelected

    //==========================================================================
    private JPopupMenu getMenuList() {

        JPopupMenu menu = null;
        JMenuItem removeAll = new javax.swing.JMenuItem();

        try {

            menu = new JPopupMenu();

            // remove all in the list
            removeAll.setText("remove all");
            removeAll.addActionListener(new ActionListener() {

                //==============================================================
                @Override
                public void actionPerformed(ActionEvent ae) {
                    clicks = new ArrayList<String>();
                    list.setListData(new Object[0]);
                }
            });
            menu.add(removeAll);

        } catch (Exception e) {
            notifications.error("error creating chart", e);
        }

        return menu;
    } // end getMenuList
} // end class
