/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;

/**
 *
 * @author sheldon.chi
 */
public class project {
    
    model theModel;
    int j,i,k;
    private String path; 
    String vmg;
    String vmb;
    String name;    
    static JFileChooser jFileChooser1;
    static String projectName;
    /*
     * constructor with vmg only
     */
    public project(int jj,int ii,int kk,String ppath,String vmgIn)
    {
        j=jj;
        i=ii;
        k=kk;
        path=ppath;
        
        
        vmg = vmgIn;
        vmb = null;
        
        
        File f = new File(getPath()+"\\temp");
        f.mkdirs();
        File g = new File(getPath()+"\\input");
        g.mkdir();
        File b = new File(getPath()+"\\bin");
        b.mkdir();        
        
        System.out.println(path+"\\temp");
        
        this.copyExe("xyz2mine_pit_vmb_output_drnninterior_noprompt.exe", path+"\\bin\\xyz2mine_pit_vmb_output_drnninterior_noprompt.exe");
        this.copyExe("make_ibound.exe", path+"\\bin\\make_ibound.exe");
   
        //print run.bat
         try {
           
            PrintWriter bat = new PrintWriter(new FileOutputStream(path+"\\bin\\run.bat"));

            bat.println("xyz2mine_pit_vmb_output_drnninterior_noprompt.exe");
            bat.println("make_ibound.exe");
            bat.println("exit");
            

            bat.close();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Exe files not found");
            System.exit(1);
        }
            
    }    
    
    
    
    
    public static void main(String[] args)
    {
        
        
        
        projectName = project.askProjectName();
        project.makeDirectory(projectName); // contains a call to constructor
        
        
        
        
        
    }

    public static String askProjectName()
    {
        
        String projName = JOptionPane.showInputDialog(null, "What's the name of the project? (A project folder will be created using this name");            
        if(projName==null) System.exit(0);
        
        while( projName.length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Name cannot be empty");
            projName = JOptionPane.showInputDialog(null, "What's the name of the project? \n (A project folder will be created using this name)");            
            if(projName==null) System.exit(0);
        }
            
            
        System.out.println(projName);
                
        return projName;
    }
    
    
    public static void makeDirectory(String name)
    {
        
        if(jFileChooser1==null) jFileChooser1 = new JFileChooser();
        jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = jFileChooser1.showDialog(null,"New Project");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            File fileSelected = jFileChooser1.getSelectedFile();
            
            String fileName = jFileChooser1.getSelectedFile().getPath();
            
            fileName = fileName + "\\" + name;
            
            if(new File(fileName).isDirectory())
            {
                JOptionPane.showMessageDialog(null, "Directory\n"+ fileName + "\n already exist,choose another one");
                makeDirectory(name);
               // int a = JOptionPane.showConfirmDialog(null, "Directory already existing, overwrite?", "Ovewrite?",JOptionPane.YES_NO_OPTION);
            }
            project pj = new project(1,1,1,fileName,"test.vmg");
            
            PitGUI UI = new PitGUI(pj.getPath());
            UI.setVisible(true);
            
        }else
        {
            System.exit(0);
        }
                
    }
    
    public void copyExe(String origin, String destination) {
        
        InputStream inStream = null;
        OutputStream outStream = null;

        try {

            File afile = new File(origin);
            File bfile = new File(destination);

            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);

            }

            inStream.close();
            outStream.close();

            System.out.println("File is copied successful!");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Exe files not found");
            System.exit(1);
        }


    }

    
   
    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }
    
}
