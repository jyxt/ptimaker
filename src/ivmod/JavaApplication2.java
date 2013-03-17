/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author sheldon.chi
 */
public class JavaApplication2 {

    static int j, i, k;

    /**
     */
    public static void go() {
        // TODO code application logic here

        //    System.out.println("Go!");


        // greyfox-apollo      
//    model nm = new model(278,206,57); //col, row, lay        
        //  nm.readVMG("APOLLO-14.VMG");
        // nm.readVMP("APOLLO-14.VMP");
        // nm.getBedrockElevation();

        /*
        if (gui.vmg!=null){
        getDimension(gui.vmg);
        model nm = new model(JavaApplication2.j,JavaApplication2.i,JavaApplication2.k);
        System.out.println("gui.vmg!=null");
        nm.readVMG(gui.vmg);
        //          nm.readVmodxyz("C:/ivmod_test/pit/gridinfo.TXT");
        nm.makePit(gui.vmp);
        nm.writeVMG(gui.vmg, "C:/ivmod_test/new.vmg");
        
        
        }
        else{ System.out.println("vm=null");
        
        }
        
         */

        //   String fvmg = "J:/Sheldon backup (large)/0-Modeling projects/9-RainyRiver/vmod/RR-pit/experiement/RR-PIT.VMG";
        String fvmg = "J:/Sheldon backup (large)/0-Modeling projects/10-GreyFox/greyfox_FP/Greyfox_FP.vmg";
     //  String fvmg = "APOLLO-14.VMG";
       String fpit = "J:/Sheldon backup (large)/0-Modeling projects/9-RainyRiver/vmod/RR-pit/experiement/out.dat";
        getDimension(fvmg);
        model nm = new model(JavaApplication2.j, JavaApplication2.i, JavaApplication2.k);
        nm.readVMG(fvmg);
        //        nm.readVmodxyz("C:/ivmod_test/pit/gridinfo.TXT");
        //        nm.makePit(fpit);
        //           nm.printBottom();
        //       nm.writeVMG(fvmg, "J:/Sheldon backup (large)/0-Modeling projects/9-RainyRiver/vmod/RR-pit/experiement/new.vmg");





        // model nm = new model(874,636,9); //col, row, lay



        //       nm.readVMG("ct.vmg");
        //     nm.makePit("xyz.dat");
        //      nm.writeVMG("ct.vmg", "C_NEW.vmg");
        int WIDTH = nm.j;
        int HEIGHT = nm.i;
               
        final BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)img.getGraphics();
        for(int jj = 0; jj < WIDTH-1; jj++) {
            for(int ii = 0; ii < HEIGHT-1; ii++) {
                int c = nm.grid[jj][ii][0].getIbound()*255;
   
          //      int c = (int) nm.grid[jj][ii][k-1].getBottom()/400*255;
                g.setColor(new Color(c, c, c));
                g.fillRect(jj,ii, 1, 1);       
            }
        }
        
        //TestGUI frame = new TestGUI();
        
        
        
        
        JFrame frame = new JFrame();
                frame.setLocationRelativeTo(null);

   //     frame.getContentPane().add(new ib(g,img));
        frame.setSize(WIDTH+30, 80+HEIGHT);

//        frame.getContentPane().add(new Viewer(g,img));
        frame.setVisible(true);

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
     //   TestGUI nt = new TestGUI();


        /*
        
        model nm = new model(3,2,4); //col, row, lay
        nm.readVMG("a.vmg");
        for (int c = 0; c < 3; c++) {
        for (int r = 0; r < 2; r++) {
        for (int l = 0; l < 4; l++) {
        //                     System.out.println(nm.x[c]+" "+nm.y[r]);
        nm.grid[c][r][l].setBottom((4-l)*100);
        System.out.println(nm.grid[c][r][l].getBottom());
        //                   System.out.println(nm.grid[c][r][l].getIbound());
        }
        }
        }
        nm.makePit("b.pit");
        
        for (int l = 0; l < 4; l++)  {
        for (int r = 0; r < 2; r++) {
        for (int c = 0; c < 3; c++){
        //          System.out.print(nm.grid[c][r][l].getPit_elev());
        System.out.print(nm.grid[c][r][l].getIbound());
        }
        System.out.print("\n");
        }
        System.out.println();
        }
         */

    }
    
   
    
    
    
    

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
