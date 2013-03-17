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
public class vmG_Filter extends javax.swing.filechooser.FileFilter {
  @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".vmg");
    }
    
    @Override
    public String getDescription() {
        return "vmg files";
    }    
}

