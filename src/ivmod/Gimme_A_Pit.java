/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

/**
 *
 * @author sheldon.chi
 */
public class Gimme_A_Pit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String fvmg = "J:/Jacob Workbook/DATA/Phoscan/year13/phoscan_grid.vmg";
        String newIbound = "J:/Jacob Workbook/DATA/Phoscan/year4/y4_new.vmg";

        model nm = new model(468,424,31);
        nm.readVMG(fvmg);
        nm.makePit("J:/Jacob Workbook/DATA/Phoscan/pit/Y4/java_xyz.dat");
        nm.writeVMG(fvmg, newIbound);
    }
}
