package view.charts;

import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.renderer.category.BarRenderer;

/**
 * A custom renderer to provide mouseover highlights.
 */
class MyBarRenderer extends BarRenderer {

    /** The row to highlight (-1 for none). */
    private int highlightRow = -1;
    /** The column to highlight (-1 for none). */
    private int highlightColumn = -1;

    /**
     * Sets the item to be highlighted (use (-1, -1) for no highlight).
     *
     * @param r  the row index.
     * @param c  the column index.
     */
    public void setHighlightedItem(int r, int c) {
        if (this.highlightRow == r && this.highlightColumn == c) {
            return;  // nothing to do
        }
        this.highlightRow = r;
        this.highlightColumn = c;
        notifyListeners(new RendererChangeEvent(this));
    }

    /**
     * Return a special colour for the highlighted item.
     *
     * @param row  the row index.
     * @param column  the column index.
     *
     * @return The outline paint.
     */
    public Paint getItemOutlinePaint(int row, int column) {
        if (row == this.highlightRow && column == this.highlightColumn) {
            return Color.yellow;
        }
        return super.getItemOutlinePaint(row, column);
    }
}