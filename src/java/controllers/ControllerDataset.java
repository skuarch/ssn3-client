package controllers;

import java.util.ArrayList;
import model.beans.SubPiece;
import model.dataset.Dataset;
import model.util.Series;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author skuarch
 */
public class ControllerDataset {

    //==========================================================================
    public ControllerDataset() {
    } // end ControllerDataset

    //==========================================================================
    public CategoryDataset categoryDataset(SubPiece subPiece) throws Exception {
        return (CategoryDataset) new Dataset().getCategoryDataset(subPiece);
    } // end categoryDataset

    //==========================================================================
    public XYDataset xyDataset(SubPiece subPiece) throws Exception {
        return (XYDataset) new Dataset().getXYDataset(subPiece);
    } // end xyDataset

    //==========================================================================
    public ArrayList getSeries(SubPiece subPiece) throws Exception{
        return new Series().getSeriesLive(subPiece);
    }
} // end ControllerDataset
