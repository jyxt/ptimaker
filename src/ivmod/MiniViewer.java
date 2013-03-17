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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author sheldon.chi
 */
public class MiniViewer extends JPanel {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        /*
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception unused) {
            ; // Ignore exception because we can't do anything.  Will use default.
        }
        * 
        */
        String fvmg = "J:/Jacob Workbook/DATA/Phoscan/grid_test/phoscan_grid.vmg";
        String fvmp = "J:/Jacob Workbook/DATA/Phoscan/year4/K.vmp";
        getDimension(fvmg);
        model nm = new model(JavaApplication2.j, JavaApplication2.i, JavaApplication2.k);
        
        
        nm.readVMG(fvmg);
        nm.readVMP(fvmp);

//           nm.makePit("J:/Jacob Workbook/DATA/Phoscan/pit/Y4/java_xyz.dat");
        
        System.out.println(nm.k);

        JFrame frame = new JFrame("Ibound");
        frame.setLocationRelativeTo(null);

        //     frame.getContentPane().add(new ib(g,img));
        //int w = nm.j; int h = nm.i;
        int w = 1000;
        int h = 1000;
        frame.setSize(w + 100, 200 + h);

        //frame.getContentPane().add(new MiniViewer(nm));
        
        
        ModelPanel panel = new ModelPanel(nm);
        
        
        JPanel viewer = new Viewer(panel);
        
        frame.getContentPane().add(viewer);
        
        
        frame.setVisible(true);
     //   frame.pack();


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    model nm;
    ib imagePanel;
    JTextField tf;

    /**
     * 
     * @param nm
     */
    public MiniViewer(model nm) {

        imagePanel = new ib(nm);
        JButton upButton = new JButton("Up");

        upButton.addActionListener(
                new ActionListener() {

                    //========================================= listener
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (imagePanel.getLay() > 0) {
                            imagePanel.setLay(imagePanel.getLay() - 1);
                        }
                        tf.setText(Integer.toString(imagePanel.getLay() + 1));

                    }//end listener
                });

        JButton downButton = new JButton("Down");

        downButton.addActionListener(
                new ActionListener() {

                    //========================================= listener
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        imagePanel.setLay(imagePanel.getLay() + 1);
                        tf.setText(Integer.toString(imagePanel.getLay() + 1));
                    }//end listener
                });
        JPanel cp = new JPanel();
        cp.setLayout(new FlowLayout());
        cp.add(upButton);
        cp.add(downButton);

        //    final JTextField tf = new JTextField(4);
        tf = new JTextField(4);
        tf.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
                imagePanel.setLay(Integer.parseInt(tf.getText()) - 1);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //     throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
                //    imagePanel.setLay(Integer.parseInt(tf.getText()));
            }
        });

        cp.add(tf);

        String[] comboboxText = {"IBOUND", "K"};
        JComboBox cb = new JComboBox(comboboxText);

        cb.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JComboBox combo = (JComboBox) e.getSource();
                        String selected = (String) combo.getSelectedItem();
                        if ("K".equals(selected)) {
                            imagePanel.current = "K";
                            imagePanel.setLay(imagePanel.getLay());
                        } else {
                            imagePanel.current = "IBOUND";
                        }
                        imagePanel.setLay(imagePanel.getLay());
                    }
                });


        cp.add(cb);

        JButton zoomIn = new JButton("Zoom in");

        zoomIn.addActionListener(
                new ActionListener() {

                    //========================================= listener
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        imagePanel.ZoomIn();
                        
                    }//end listener
                });


        cp.add(zoomIn);


        JButton zoomOut = new JButton("Zoom out");

        zoomOut.addActionListener(
                new ActionListener() {

                    //========================================= listener
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        imagePanel.ZoomOut();
                        
                    }//end listener
                });


        cp.add(zoomOut);        

        this.setLayout(new BorderLayout());
        this.add(cp, BorderLayout.NORTH);
        this.add(imagePanel, BorderLayout.CENTER);

        
        imagePanel.addMouseWheelListener(
                new MouseWheelListener() {
                @Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                            
                            double zoomAmount = .1 * e.getWheelRotation();
                                
                            imagePanel.mouseZoom(zoomAmount);
                          
			}
		}
	});

    }

    class ib extends JPanel {

        model nm;
        int lay;
        String current;
        double zoom;

        public ib(model m) {
            nm = m;
            lay = 0;
            current = "IBOUND";
            zoom = 1;
        

        }

        public void setLay(int l) {
            lay = l;
            repaint();
        }

        public int getLay() {
            return lay;
        }

        public void ZoomIn()
        {
            zoom = zoom *2;
            repaint();
            System.out.println(zoom);
        }
        ;
        
        public void ZoomOut()
        {
            zoom = zoom /2;
            repaint();
            System.out.println(zoom);
        }
        
        public void mouseZoom(double amount)
        {
            zoom -= amount;
            repaint();
            System.out.println("mouse zoom");
        }
        
        @Override
        public void paintComponent(Graphics gg) {



            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();
            
            int centerX = panelWidth/2;
            int centerY = panelHeight/2;

            int w = nm.j;
            int h = nm.i;

            int x_factor = panelWidth / w;
            int y_factor = panelHeight / h;

            final BufferedImage img = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) img.getGraphics();
            
            for (int jj = 0; jj < w - 1; jj++) {
                for (int ii = 0; ii < h - 1; ii++) {

                    if (lay < 0) {
                        lay = 0;
                    }
                    if (lay > nm.k) {
                        lay = nm.k;
                    }

                    int c;
                    if ("IBOUND".equals(current)) {
                        c = nm.grid[jj][ii][lay].getIbound() * 255;
                    } else {
                        c = nm.grid[jj][ii][lay].getCond() * 255 / 15;
                    }
                    //         int c = (int) nm.grid[jj][ii][k-1].getBottom()/400*255;

                    g.setColor(new Color(c, c, c));
                    //g.fillRect(jj, ii, 1, 1);

                    g.fillRect(jj * x_factor, ii * y_factor, x_factor, y_factor);
                    //    g.drawLine(40, 0, 40, h);
                }
            }

            Graphics2D g2d = (Graphics2D) gg;
        
                 g2d.scale(zoom,zoom);
            g2d.drawImage(img, 0, 0, this);
        }
        
     
        
        
    }

    //utility
    /**
     * 
     * @param fname
     */
    public static void getDimension(String fname) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fname));

            String strRead;
            // x
            JavaApplication2.j = Integer.parseInt(in.readLine());//skip line j
            for (int c = 0; c <= JavaApplication2.j; c++) { // column: length=col+1
                strRead = in.readLine();
            }
            //y
            JavaApplication2.i = Integer.parseInt(in.readLine());//skip line i
            for (int r = 0; r <= JavaApplication2.i; r++) { // row: length=row+1
                strRead = in.readLine();
            }
            //z
            JavaApplication2.k = Integer.parseInt(in.readLine());

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }
    }
    
}
