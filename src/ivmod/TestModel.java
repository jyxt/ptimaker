/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

/**
 *
 * @author sheldon.chi
 */
public class TestModel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
         model theModel = new model(278,206,57);
        theModel.readVMG("C:/0-Modeling projects/10-GreyFox/apollo-14_2009_09_mint/APOLLO09.VMG");
        System.out.print(theModel.top[0][0][0]);
    }
}
