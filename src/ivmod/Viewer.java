/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author sheldon.chi
 */
public class Viewer extends JPanel {

    Graphics g;
    
    ModelPanel panel;
    JTextField tf;
    /**
     *
     * @param imagePanel
     */
    public Viewer(ModelPanel imagePanel) {
        

        this.panel = imagePanel;
        
        JButton upButton = new JButton("Up");
        
        
        
        JButton downButton = new JButton("Down");
        
        JPanel cp = new JPanel();
        cp.setLayout(new FlowLayout());
        cp.add(upButton);
        cp.add(downButton);

        tf = new JTextField(4);
        
        cp.add(tf);
        
        String[] comboboxText = {"IBOUND", "K"};
        JComboBox cb = new JComboBox(comboboxText);
        
        downButton.addActionListener(
                new ActionListener() {

                    //========================================= listener
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        panel.setLayer(panel.getLayer() + 1);                        
                        tf.setText(Integer.toString(panel.getLayer()));
                        
                    }//end listener
                });
        //   cp.add(cb);

        //   JButton zoomIn = new JButton("Zoom in");



    //    cp.add(zoomIn);


      //  JButton zoomOut = new JButton("Zoom out");


    //    cp.add(zoomOut);

        this.setLayout(new BorderLayout());
        this.add(cp, BorderLayout.NORTH);
        this.add(imagePanel, BorderLayout.CENTER);



    }
}
