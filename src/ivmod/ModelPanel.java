/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author sheldon.chi
 */
public class ModelPanel extends NavigableImagePanel{
    
    private model theModel;
    private int layer;
    private ModelProperty property;
    
    public ModelPanel(model aModel)
    {
        theModel = aModel;
        this.setLayer(1);
        property = ModelProperty.IBOUND;
        this.setImage(this.model2Image(theModel,layer));
    }

    /**
     * @return the theModel
     */
    public model getTheModel() {
        return theModel;
    }

    /**
     * @param theModel the theModel to set
     */
    public void setTheModel(model theModel) {
        this.theModel = theModel;
    }

    /**
     * @return the layer
     */
    public int getLayer() {
        return layer+1;
    }

    /**
     * @param layer the layer to set
     */
    public void setLayer(int layer) { // offset by 1
        this.layer = layer - 1;
        this.setImage(this.model2Image(theModel,this.layer));
        repaint();
    }

    /**
     * @return the property
     */
    public ModelProperty getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(ModelProperty property) {
        this.property = property;
    }
    
    
    public BufferedImage model2Image(model aModel, int currentLayer)
    {
        int numberOfRows = aModel.i;
        int numberOfColumns = aModel.j;
        
        double ModelWidth= aModel.x[numberOfColumns]-aModel.x[0];
        double ModelHeight = aModel.y[0]-aModel.y[numberOfRows];
                
        int panelWidth = 5000;
        int panelHeight = (int)Math.ceil((panelWidth*(ModelHeight/ModelWidth))); //.height based on relative to width
        
        
        double x_factors[] = new double[numberOfColumns];
        double y_factors[] = new double[numberOfRows];
        int x[] = new int[numberOfColumns];
        int w[]= new int[numberOfColumns];
        int y[] = new int[numberOfRows];
        int h[] = new int[numberOfRows];
        
        for (int c = 0; c < numberOfColumns; c++) {
            x_factors[c] = (aModel.x[c + 1] - aModel.x[c]) / ModelWidth;
            x[c] = (int)((aModel.x[c]-aModel.x[0])/ModelWidth*panelWidth);  
            w[c] = (int)Math.ceil(panelWidth*x_factors[c]);  
        }        
        for(int r=0;r<numberOfRows;r++)
        {
            y_factors[r] = (aModel.y[r]-aModel.y[r+1])/ModelHeight;
            y[r] = (int)((aModel.y[0]-aModel.y[r])/ModelHeight*panelHeight);
            h[r] = (int)Math.ceil(panelHeight*y_factors[r]);
        }        
        
                
        final BufferedImage img = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) img.createGraphics();
        
        System.out.println("about to enter loop");
        for (int jj = 0;jj <numberOfColumns; jj++)
        {
            for (int ii=0;ii<numberOfRows;ii++)
            {                
                int color = aModel.grid[jj][ii][currentLayer].getIbound() * 255;   // get color form ibound, black or white
                g.setColor(new Color(color, color, color));
                
                
                int CellX = x[jj];                
                int CellY = y[ii];
                
                int CellWidth = w[jj];               
                int CellHeight = h[ii];                                                
                g.fillRect(CellX, CellY, CellWidth, CellHeight);

            }            
       //     System.out.println(jj);
        }
        
        return img;
    }
    
}
