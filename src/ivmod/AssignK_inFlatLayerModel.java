/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 *
 * @author sheldon.chi
 */
public class AssignK_inFlatLayerModel {

    String till_file;//ijkTopBottom exported from vmod
    String bedrock_file;
    model m;
    private double[][][] TopNBottom;

    AssignK_inFlatLayerModel(String name, model m) {
        this.till_file = name; // till top and bottom
        this.m = m; //model 
        this.TopNBottom = new double[468][424][2];
    }

    void Doit() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.till_file));

            String strRead;
            Pattern pattern = Pattern.compile("[\t ]+");

            while ((strRead = in.readLine()) != null) {
                String[] splitarray = pattern.split(strRead);
                int i = Integer.parseInt(splitarray[0]) - 1;
                int j = Integer.parseInt(splitarray[1]) - 1;

                double top = Double.parseDouble(splitarray[3]);
                double bottom = Double.parseDouble(splitarray[4]);


                this.TopNBottom[j][i][0] = top;// System.out.println(top);
                this.TopNBottom[j][i][1] = bottom;



            }

            in.close();
            m.readVMG("J:/Jacob Workbook/DATA/Phoscan/grid_test/VM 4.2_18layer/phoscan_grid.vmg");


            for (int c = 90; c < 335; c++) {
                for (int r = 105; r < 350; r++) {
                    for (int l = 2; l < 21; l++) {
                        
     //                   if (m.grid[c][r][l].getBottom() > this.TopNBottom[c][r][1]&&m.grid[c][r][l].getBottom() < this.TopNBottom[c][r][0] ) {
                          if (m.grid[c][r][l].getBottom()+10 < this.TopNBottom[c][r][1] && m.grid[c][r][21].getCond()== 3) { // last layer used as marker for anomaly A
                            m.grid[c][r][l].setCond(3); //bedrock
                            continue;
                        }
                          else if (m.grid[c][r][l].getBottom()+10 > this.TopNBottom[c][r][1]&&m.grid[c][r][l].getBottom() < this.TopNBottom[c][r][0]&& m.grid[c][r][21].getCond()== 3)
                          {
                              m.grid[c][r][l].setCond(2); // till
                              continue;
                          }
                          else if (m.grid[c][r][l].getBottom() > this.TopNBottom[c][r][0]&& m.grid[c][r][21].getCond()== 3)
                          {
                              m.grid[c][r][l].setCond(5);// overburden
                              
                          }
                          
                    }
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
            System.out.println("IO exception");
        }


    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // TODO code application logic here
        model nm = new model(468, 424, 22);
        nm.readVMP("J:/Jacob Workbook/DATA/Phoscan/grid_test/VM 4.2_18layer/K/K_old.txt");
        AssignK_inFlatLayerModel aK = new AssignK_inFlatLayerModel("J:/Jacob Workbook/DATA/Phoscan/grid_test/VM 4.2_18layer/K/Till_TopBottom.TXT", nm);
  
        

        aK.Doit();
        
              nm.writeVMP("J:/Jacob Workbook/DATA/Phoscan/grid_test/VM 4.2_18layer/K/K_old.txt","J:/Jacob Workbook/DATA/Phoscan/grid_test/VM 4.2_18layer/K/K_new.txt");
        
        System.out.println("DONE");

    }
}