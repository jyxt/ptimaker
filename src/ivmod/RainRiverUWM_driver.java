/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import java.util.ArrayList;

/**
 *
 * @author sheldon.chi
 */
public class RainRiverUWM_driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        /*
        String path = "J:/Jacob Workbook/DATA/Rainy River/2013/vmod/Pit/files for mine workings Y12";
        
        String VMG = "RRP_Y11.vmg";
        String VMP = "RRP_Y11.VMP";

        
        ArrayList<Integer> a = GetDimensionFromVMG.go(path+"/"+VMG);
           
        model theModel = new model(a.get(0),a.get(1),a.get(2));                       
        
        theModel.readVMG(path+"/"+VMG); 
        System.out.println(path+"/"+VMG);
        
        theModel.readVMP(path+"/"+VMP);
        
        theModel.readVmodxyz(path+"/boundary.DAT",readVmodXYZOptions.highK);
        
        theModel.readVmodxyz(path+"/inactives.DAT",readVmodXYZOptions.InternalWorkings);

     //   theModel.writeVMG(path+"/NewIboundInside.vmg", path+"/NewIboundInside+umw.vmg");
        theModel.printIbound(path+"/new_ibound.dat");
        
        theModel.writeNewVMG(path+"/"+VMG, path+"/new_ibound.dat", path+"/umw.vmg");
       
        theModel.writeVMP(path+"/"+VMP,path+"/umw.VMP");
        * 
        */
        
        
        // zone budget
        
        /*
        String path = "J:/Jacob Workbook/DATA/Rainy River/2013/vmod/Pit/test/dummie";
 
        String VMG = path+"/dummie.vmg";
        String VMZ = path+"/dummie.vmz";
        
        String vmzOut = path+"/dummie_workings.vmz";
        
        ArrayList<Integer> a = GetDimensionFromVMG.go(VMG);           
        model theModel = new model(a.get(0),a.get(1),a.get(2));      
        
        theModel.readDrainAndPrintVMZ(path+"/Drain.txt", VMZ, vmzOut);
        * 
        */
        
        
        // particles on TMA and mine rock
        
        String path = "J:/Jacob Workbook/DATA/Rainy River/2013/vmod/TMA_deep";
        String VMG = path+"/RR_TMA.vmg";
        String VMP = path+"/RR_TMA.vmp";
        String VMAOUT = path+"/RR_TMA_2.vma";
        //String WaterTableXYZ = path + "/WT.TXT";
        
        String flux = "/flux.TXT";
        String Q = path+"/Q.txt";
        
        ArrayList<Integer> a = GetDimensionFromVMG.go(VMG);           
        model theModel = new model(a.get(0),a.get(1),a.get(2));
        
        /*
         * !! STOP! stillreading flag need to be set to 1!! before running this
         */
        theModel.readVMG(VMG); 
        
        
        theModel.readVMP(VMP);
 //       theModel.readVmodxyz(path+flux, readVmodXYZOptions.flux);
        theModel.printVMAbasedonK(VMAOUT);
     //   theModel.getFluxBasedonK(Q);
        
        
        
    }
}
