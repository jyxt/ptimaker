/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author sheldon.chi
 */
public class CatchMeIfYouCan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        diff_match_patch overlord = new diff_match_patch();
    //   System.out.println(overlord.diff_main("anc","and " ));
        
        
        // see if files are same
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:/0-Modeling projects/20-RR-2013-DD/pathline/RR_TMA - Copy.bas"));
            BufferedReader in2 = new BufferedReader(new FileReader("C:/0-Modeling projects/20-RR-2013-DD/pathline/RR_TMA.bas"));          
            String strRead;
            while ((strRead=in.readLine()) != null)
            {
               LinkedList<diff_match_patch.Diff> Diffs = overlord.diff_main(strRead,in2.readLine());
               for (diff_match_patch.Diff d:Diffs)
               {
                   if(d.operation!=diff_match_patch.Operation.EQUAL)
                   {
                       System.out.println(d);
                   }
               } 
            }
            in.close();
            in2.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found !"+e.getMessage());
        } catch (IOException ioe) {
            System.out.println("IO Exception!");
        }      
    }
}
