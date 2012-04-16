package view.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import model.beans.SubPiece;
import view.panels.FactoryPanel;
import view.splits.Navigator;
import view.splits.SubNavigator;

/**
 *
 * @author skuarch
 */
public class FloatFrame extends JFrame {

    private FloatFrame floatFrame = null;
    private Component component = null;
    private javax.swing.JButton jButtonClose = new JButton();
    private javax.swing.JButton jButtonOnTop = new JButton();
    private javax.swing.JButton jButtonSwap = new JButton();
    private javax.swing.JToolBar jToolBar = new JToolBar();

    //==========================================================================
    public FloatFrame(Component component) {
        this.floatFrame = (FloatFrame) this;
        this.component = component;
        setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/view/images/ssnIcon.png")));
        setPreferredSize(new Dimension(630, 500));
        setTitle(component.getName());
        setLayout(new BorderLayout());
        pack();
        setLocationRelativeTo(getContentPane());
        onLoad();
    } // end FloatFrame

    //==========================================================================
    private void onLoad() {

        jToolBar.setFloatable(false);
        jToolBar.setRollover(true);

        jButtonOnTop.setIcon(new ImageIcon(getClass().getResource("/view/images/onTop.png"))); // NOI18N
        jButtonOnTop.setFocusable(false);
        jButtonOnTop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonOnTop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar.add(jButtonOnTop);

        jButtonSwap.setIcon(new ImageIcon(getClass().getResource("/view/images/swap.png"))); // NOI18N
        jButtonSwap.setFocusable(false);
        jButtonSwap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSwap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar.add(jButtonSwap);

        jButtonClose.setIcon(new ImageIcon(getClass().getResource("/view/images/shutdown.png"))); // NOI18N
        jButtonClose.setFocusable(false);
        jButtonClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar.add(jButtonClose);

        add(jToolBar, BorderLayout.PAGE_START);
        add(component, BorderLayout.CENTER);
        addListeners();

    }

    //==========================================================================
    private void addListeners() {
        jButtonSwap.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                FactoryPanel newComponent = (FactoryPanel) component;
                SubPiece subPiece = newComponent.getSubPiece();
                SubNavigator subNavigator = (SubNavigator) Navigator.getInstance().getComponent(subPiece.getJob());

                if (subNavigator == null) {
                    subNavigator = new SubNavigator(subPiece);
                    subNavigator.addTab(component.getName(), component);
                    Navigator.getInstance().addTab(subNavigator.getName(), subNavigator);
                    closeMe();
                } else {
                    subNavigator.addTab(component.getName(), component);
                    Navigator.getInstance().addTab(subNavigator.getName(), subNavigator);
                    closeMe();
                }

            }
        });

        jButtonOnTop.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                if (floatFrame.isAlwaysOnTop()) {
                    floatFrame.setAlwaysOnTop(false);
                } else {
                    floatFrame.setAlwaysOnTop(true);
                }
            }
        });

        jButtonClose.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ev) {
                closeMe();
            }
        });

    } // end onLoad()

    //==========================================================================
    private void closeMe() {
        setVisible(false);
        FactoryPanel fp = (FactoryPanel) component;
        fp.destroy();
    } // end closeMe

    //==========================================================================
    @Override
    protected void finalize() throws Throwable {

        try {
            floatFrame = null;
            component = null;
            jButtonClose = null;
            jButtonOnTop = null;
            jButtonSwap = null;
            jToolBar = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.finalize();
        }

    } // end finalize
} // end class

