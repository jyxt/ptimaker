/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sheldon.chi
 */
public class GetDimensionFromVMG {

    public static ArrayList<Integer> go(String fname) {
        ArrayList<Integer> a = new ArrayList<Integer>();

        try {

            BufferedReader in = new BufferedReader(new FileReader(fname));

            String strRead;
            // x
            a.add(Integer.parseInt(in.readLine()));//skip line j
            for (int c = 0; c <= a.get(0); c++) { // column: length=col+1
                strRead = in.readLine();
            }
            //y
            a.add(Integer.parseInt(in.readLine()));//skip line i
            for (int r = 0; r <= a.get(1); r++) { // row: length=row+1
                strRead = in.readLine();
            }
            //z
            a.add(Integer.parseInt(in.readLine()));

            in.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }
        return a;


    }
}
