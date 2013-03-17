/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

/**
 *
 * @author sheldon.chi
 */
import java.io.*;  


public class TestExec {  
    public static void main(String[] args) throws Exception{  
        try {  
            
            
            String path1 = "C:\\Documents and Settings\\sheldon.chi\\Desktop\\s\\bin\\run.bat";
            
            String[] go1 = {"cmd", "/c", "start","\"DummyTitle\"",path1};
            
            Runtime.getRuntime().exec(go1, null, new File("C:\\Documents and Settings\\sheldon.chi\\Desktop\\s\\bin\\"));
            
            
 } catch (IOException e) { e.printStackTrace(); } } }



/*

public class TestExec {

    public static void main(String[] args) {
        
        File f = new File("./temp/a");
        
        if(!f.mkdirs()) System.out.println("temp directory not created");
        
        File inputFolder = new File("input");        
        if(!inputFolder.mkdirs()) System.out.println("input directory not created");
        
        File bin = new File("bin");
        if(!bin.mkdirs()) System.out.println("bin directory not created");

    }
}
*/
