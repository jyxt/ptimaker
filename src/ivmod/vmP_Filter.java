/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

import java.io.File;

/**
 *
 * @author sheldon.chi
 */


public class vmP_Filter extends javax.swing.filechooser.FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".vmp");
    }
    
    @Override
    public String getDescription() {
        return "vmp files";
    }
}