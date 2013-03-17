/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import java.io.*;
import java.util.regex.Pattern;

/**
 *
 * @author sheldon.chi
 */
public class GetLTillHead {

    /**
     * @param args the command line arguments
     */
    String fname;
    model m;
    
    GetLTillHead(String name, model m) {
        this.fname = name;
        this.m = m;
    }

    void Doit() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.fname));
            BufferedReader ground_surface = new BufferedReader(new FileReader("J:/Jacob Workbook/DATA/Rainy River/2012/artesian zone/ground surface.TXT"));            

            String strRead;
            Pattern pattern = Pattern.compile("[\t ]+");

            
            m.readVMG("C:/0-Modeling projects/9-RainyRiver/vmod/2012_v2.2/2.2.VMG");
            
            while ((strRead = in.readLine()) != null) {
                String[] splitarray = pattern.split(strRead);
                int row = Integer.parseInt(splitarray[0]);
                int col = Integer.parseInt(splitarray[1]);
                //int lay = Integer.parseInt(splitarray[2]);
              //  double elevation = Double.parseDouble(splitarray[3]);
                
                if(!splitarray[3].contains("E")) m.grid[col - 1][row - 1][0].setBottom(Double.parseDouble(splitarray[3]));
            }

            while ((strRead = ground_surface.readLine()) != null) {
                String[] splitarray = pattern.split(strRead);
                int row = Integer.parseInt(splitarray[0]);
                int col = Integer.parseInt(splitarray[1]);
                //int lay = Integer.parseInt(splitarray[2]);
              //  double elevation = Double.parseDouble(splitarray[3]);
                
                if(!splitarray[3].contains("E")) m.grid[col - 1][row - 1][0].setPit_elev(Double.parseDouble(splitarray[3])); //store ground surface in pit elev
            }            
            
            in.close();
            ground_surface.close();
            
     
            m.printBottom("J:/Jacob Workbook/DATA/Rainy River/2012/artesian zone/LTill_Head.txt");
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }


    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here
        model nm = new model(402,349,9);
        GetLTillHead gw = new GetLTillHead("J:/Jacob Workbook/DATA/Rainy River/2012/artesian zone/head 3 4 5.TXT",nm);
        System.out.println(gw.fname);
        
        gw.Doit();
        //System.out.println("DONE");
        
    }
}
