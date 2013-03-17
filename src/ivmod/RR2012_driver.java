/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

/**
 *
 * @author sheldon.chi
 */
public class RR2012_driver {

    public static void main(String[] args) {
        // TODO code application logic here
        
        
        int layer = 11;
        int column = 402;
        int row = 349;
        
        
        double kv = 0.0259; //m/d

        double W;
        double h = 1.0;
      
        model nm1 = new model(column, row, layer);
        
        //model nm2 = new model(column, row, layer);

        nm1.readVMB("C:/0-Modeling projects/9-RainyRiver/2012/recharge 10 and 40 clay surrounds CH L-till to bebdrock/a.VMB");
    //    nm2.readVMB("J:/Jacob Workbook/DATA/Rainy River/2012/vmod/existing/constantHeads_removed_K_Ktill_down_ultra_low/STUFF_pinewood.VMB");
       //   nm.readVMB("J:/Jacob Workbook/DATA/Rainy River/2012/vmod/v2.4.3/2.4.3___pinewood.VMB");

        nm1.readVMG("C:/0-Modeling projects/9-RainyRiver/2012/recharge 10 and 40 clay surrounds CH L-till to bebdrock/a.vmg");
      //  nm2.readVMG("J:/Jacob Workbook/DATA/Rainy River/2012/vmod/existing/constantHeads_removed_K_Ktill_down_ultra_low/STUFF.VMG");

       
        
        // calculate conductance
        for (int l = 0; l < layer; l++) {
            for (int c = 0; c < column; c++) {
                for (int r = 0; r < row; r++) {
                    
                    
                   /*
                    // small rivers
                    if (nm1.grid[c][r][l].getBc() == 3) {
                        
                        
                        double dx = nm1.x[c + 1] - nm1.x[c];
                        double dy = nm1.y[r + 1] - nm1.y[r];

                        W = 1; //change W to 4 for PineWood rivers

                        double drainCond = dy * W * kv / h;
                                             
                        
                        
                        nm1.grid[c][r][l].setDrain_cond(drainCond); 
                        
                    }
                    
                    
                    // pinewood
                      if (nm2.grid[c][r][l].getBc() == 3) {
                        
                        
                        double dx = nm2.x[c + 1] - nm2.x[c];
                        double dy = nm2.y[r + 1] - nm2.y[r];

                        W = 4; //change W to 4 for PineWood rivers

                        double drainCond = dx * W * kv / h;
                                             
                        
                        
                        nm2.grid[c][r][l].setDrain_cond(drainCond); 
                     
                     * */
                     
              //      if(nm1.grid[c][r][l].getBc()==3) nm1.grid[c][r][l].setBc(2);
                    
                     
                     
                    }
                    
                }
            }
        
        
        
        
      //  nm1.printVMB("C:/0-Modeling projects/9-RainyRiver/2012/recharge 10 and 40 clay surrounds CH L-till to bebdrock/a_River.VMB");
        //nm2.printVMB("J:/Jacob Workbook/DATA/Rainy River/2012/vmod/existing/constantHeads_removed_K_Ktill_down_ultra_low/STUFF_pinewood_correct.VMB");
        
           nm1.changeBcGroup("C:/0-Modeling projects/9-RainyRiver/2012/recharge 10 and 40 clay surrounds CH L-till to bebdrock/change_group.txt","C:/0-Modeling projects/9-RainyRiver/2012/recharge 10 and 40 clay surrounds CH L-till to bebdrock/a_River.VMB");

    }
}
