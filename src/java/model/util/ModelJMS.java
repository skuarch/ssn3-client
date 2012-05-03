package model.util;

import model.beans.SubPiece;
import model.jms.JMSProccessor;

/**
 *
 * @author skuarch
 */
public class ModelJMS {

    //==========================================================================
    public ModelJMS() {
    } // end ModeJMS

    //==========================================================================
    public void send(String select, String sendTo, SubPiece subPiece) throws Exception {
        new JMSProccessor().send(select, sendTo, "srs", "request", new KeyGenerator().generateKey(), new PieceUtilities().subPieceToHashMap(subPiece));
    } // end send
} 