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
public class GetWatertable {

    /**
     * @param args the command line arguments
     */
    String fname;
    model m;
    
    GetWatertable(String name, model m) {
        this.fname = name;
        this.m = m;
    }

    void Doit() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.fname));

            String strRead;
            Pattern pattern = Pattern.compile("[\t ]+");

            while ((strRead = in.readLine()) != null) {
                String[] splitarray = pattern.split(strRead);
                int row = Integer.parseInt(splitarray[0]);
                int col = Integer.parseInt(splitarray[1]);
                //int lay = Integer.parseInt(splitarray[2]);
              //  double elevation = Double.parseDouble(splitarray[3]);
                
                if(!splitarray[3].contains("E")) m.grid[col - 1][row - 1][0].setBottom(Double.parseDouble(splitarray[3]));
            }

            in.close();
            m.readVMG("J:/Sheldon backup (large)/0-Modeling projects/4-Hemlo/Hemlo_Sept_newest_Pitdesign/v1_1e-7/V1.VMG");
            m.printBottom("J:/Sheldon backup (large)/0-Modeling projects/4-Hemlo/Hemlo_Sept_newest_Pitdesign/drawdown/big_heads/existing/v3_watertable.txt");
            
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
        model nm = new model(534,292,49);
        GetWatertable gw = new GetWatertable("J:/Sheldon backup (large)/0-Modeling projects/4-Hemlo/Hemlo_Sept_newest_Pitdesign/drawdown/big_heads/existing/v3_ijk.TXT",nm);
        System.out.println(gw.fname);
        
        gw.Doit();
        //System.out.println("DONE");
        
    }
}
