/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author sheldon.chi
 */
public class FixingGaps {
    
    //region of interst 
   static int roiRowMin = 130; 
   static int roiRowMax = 320; 
   static int roiColMin = 100; 
   static int roiColMax = 287; 
    
    
   /**
    * 
    * @param args
    */
   public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception unused) {
            ; // Ignore exception because we can't do anything.  Will use default.
        }
        String fvmg = "J:/Jacob Workbook/DATA/Phoscan/year13/phoscan_y13.vmg";
        String fvmp = "J:/Jacob Workbook/DATA/Phoscan/year4/K.vmp";
        JavaApplication2.getDimension(fvmg);
        model nm = new model(JavaApplication2.j, JavaApplication2.i, JavaApplication2.k);
        
        
        nm.readVMG(fvmg);
        nm.readVMP(fvmp);

        //r 320 c 100, r 131 c 287  l 10
        //filter(nm,1,1);
        FixingGaps fg = new FixingGaps();
        for(int l =0;l<15;l++)
        {
            fg.fill(nm,l);
        }
        
        
        nm.writeVMG(fvmg, "J:/Jacob Workbook/DATA/Phoscan/year13/new.vmg");
        
        JFrame frame = new JFrame();
        frame.setLocationRelativeTo(null);

        //     frame.getContentPane().add(new ib(g,img));
        //int w = nm.j; int h = nm.i;
        int w = 500;
        int h = 500;
        frame.setSize(w + 30, 80 + h);

        frame.getContentPane().add(new MiniViewer(nm));
        frame.setVisible(true);
        frame.pack();



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }    

   /**
    * 
    * @param nm
    * @param offsetX
    * @param offsetY
    */
   public static void filter(model nm, int offsetX, int offsetY)
{
          for(int r=roiRowMin;r<320;r++)
        {
            for(int c=100;c<287;c++)
            {
                for(int l=0;l<20;l++)
                {
                    if(nm.grid[c][r][l].getIbound()==1 && nm.grid[c][r-1][l].getIbound()==0  && nm.grid[c][r+offsetY][l].getIbound()==0) nm.grid[c][r][l].setIbound(0);
                    if(nm.grid[c][r][l].getIbound()==1 && nm.grid[c-1][r][l].getIbound()==0  && nm.grid[c+offsetX][r][l].getIbound()==0) nm.grid[c][r][l].setIbound(0);
                }
            }
        }              
        
}
 
   /**
    * 
    * @param nm
    * @param row
    * @param layer
    * @return
    */
   public boolean entireRowActive(model nm, int row,int layer)
{
    
    for(int c=roiColMin;c<=roiColMax;c++)
    {
        if(nm.grid[c][row][layer].getIbound()==0) return false;
    }
    return true;
}


/**
 * 
 * @param nm
 * @param row
 * @param layer
 * @return
 */
public int lowerEnd(model nm,int row,int layer)
{
    for(int c=roiColMin;c<=roiColMax;c++)
    {
        if(nm.grid[c][row][layer].getIbound()==0) return c;
    }
    System.out.println("didn't find");
    return roiColMin;
        
}

/**
 * 
 * @param nm
 * @param row
 * @param layer
 * @return
 */
public int UpperEnd(model nm,int row,int layer)
{
    for(int c=roiColMax;c>=roiColMin;c--)
    {
        if(nm.grid[c][row][layer].getIbound()==0) return c;
    }
    
    System.out.println("didn't find");
    return roiColMin;
        
}

/**
 * 
 * @param nm
 * @param col
 * @param layer
 * @return
 */
public boolean entireColActive(model nm, int col,int layer)
{
    int counter = 0;
    for(int r=roiRowMin;r<=roiRowMax;r++)
    {
        if(nm.grid[col][r][layer].getIbound()==0) counter++;
    }
    
     if(counter<50) return true;
     return false;
}

/**
 * 
 * @param nm
 * @param col
 * @param layer
 * @return
 */
public int colLowerEnd(model nm,int col,int layer)
{
    for(int r=roiRowMin;r<=roiRowMax;r++)
    {
        if(nm.grid[col][r][layer].getIbound()==0) return r;
    }
    System.out.println("didn't find any");
    return 9999;
        
}

/**
 * 
 * @param nm
 * @param col
 * @param layer
 * @return
 */
public int colUpperEnd(model nm,int col,int layer)
{
    for(int r=roiRowMax;r>=roiRowMin;r--)
    {
        if(nm.grid[col][r][layer].getIbound()==0) return r;
    }
    
    System.out.println("didn't find any");
    return 9999;
        
}
/**
 * 
 * @param nm
 * @param layer
 */
public void fill(model nm,int layer)
{
    
    for(int r=200;r<260;r++)
    {
        if (entireRowActive(nm,r,layer))
        {
            int le = lowerEnd(nm,r-1,layer);
            int ue = UpperEnd(nm,r-1,layer);
            if(le==roiColMin || ue==roiColMax) break;
            else{
                for(int c=le;c<=ue;c++)
                {
                    nm.grid[c][r][layer].setIbound(0);

                }
            }
        }
    }
    
    
     for(int c=160;c<230;c++)
    {
        if (entireColActive(nm,c,layer))
        {
            int le = colLowerEnd(nm,c-1,layer);System.out.println(le);
            int ue = colUpperEnd(nm,c-1,layer);System.out.println(ue);
            if(le==9999 || ue==9999) break;
            else{
                for(int r=le;r<=ue;r++)
                {
                    nm.grid[c][r][layer].setIbound(0);

                }
            }
        }
    }
   
}

}
