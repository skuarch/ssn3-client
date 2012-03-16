package model.dataset;

import controllers.ControllerConfiguration;
import model.beans.Configuration;
import model.beans.SubPiece;
import model.jms.JMSProccessor;
import model.util.PieceUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author skuarch
 */
public class Dataset {

    //==========================================================================
    public Dataset() {
    } // end Dataset

    //==========================================================================
    public CategoryDataset getCategoryDataset(SubPiece subPiece) throws Exception {

        if (subPiece == null) {
            throw new NullPointerException("subPiece is empty");
        }

        CategoryDataset categoryDataset = null;
        Configuration configuration = null;

        try {

            configuration = new ControllerConfiguration().getInitialConfiguration();
            categoryDataset = (CategoryDataset) new JMSProccessor().sendReceive(subPiece.getView(), subPiece.getCollector(), "srs", configuration.getJmsTimeWaitMessage(), new PieceUtilities().subPieceToHashMap(subPiece));            

        } catch (Exception e) {
            throw e;
        }

        return categoryDataset;

    } // end getCategoryDataset

    //==========================================================================
    public XYDataset getXYDataset(SubPiece subPiece) throws Exception {

        if (subPiece == null) {
            throw new NullPointerException("subPiece is empty");
        }

        XYDataset xyDataset = null;
        Configuration configuration = null;

        try {

            configuration = new ControllerConfiguration().getInitialConfiguration();            
            xyDataset = (XYDataset) new JMSProccessor().sendReceive(subPiece.getView(), subPiece.getCollector(), "srs", configuration.getJmsTimeWaitMessage(), new PieceUtilities().subPieceToHashMap(subPiece));


        } catch (Exception e) {
            throw e;
        }

        return xyDataset;

    }
} // end class
