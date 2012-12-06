package ivmod;

import java.io.*;
import java.util.regex.Pattern;
/**
 *
 * @author sheldon.chi
 * 
 * 
 * Array coordinates system used: 
 *    j   i  k 
 * = col row lay
 * =  c   r  l 
 * =  x   y  z 
 * 
 * 
 * j,i,k growing direction is same as MODFLOW 
 * but array index starts from 0 instead of 1
 * 
 * 
 * 
 */
public class model {

    int i, j, k;
    cell[][][] grid;
    double[] x;// grid lines x, size = j+1
    double[] y;// grid lines y, size = i+1
    String rootPath;
    PitGUI UI;
    double[][][]top;

    //constructor
    /**
     * 
     * @param j
     * @param i
     * @param k
     */
    public model(int j, int i, int k) { //col,row,lay

        this.j = j;
        this.i = i;
        this.k = k;
        this.grid = new cell[j][i][k];
        this.x = new double[j + 1];
        this.y = new double[i + 1];
        this.top=new double[j][i][1];

        // initialize x coordinates as 0, read form VMG later
        for (int jj = 0; jj < j; jj++) {
            x[jj] = 0;
        }

        // init y coordinates as 0, read from VMG later
        for (int ii = 0; ii < i; ii++) {
            y[ii] = 0;
        }
        
        
        //initialize top
        for (int jj=0;jj<j;jj++)
        {
            for(int ii=0;ii<i;ii++)
            {
                top[jj][ii][0]=0;
            }
        }
        

        // put right coordinates into grid, j,i,k
        for (int c = 0; c < j; c++) {
            for (int r = 0; r < i; r++) {
                for (int l = 0; l < k; l++) {
                    this.grid[c][r][l] = new cell();
                }
            }//c
        }// r
    }//model
    
    public model(int j, int i, int k, String rootPathIn, PitGUI UIIn) { //col,row,lay

        this.j = j;
        this.i = i;
        this.k = k;
        this.grid = new cell[j][i][k];
        this.x = new double[j + 1];
        this.y = new double[i + 1];
        this.rootPath = rootPathIn;
         this.top=new double[j][i][1];
        
        // initialize x coordinates as 0, read form VMG later
        for (int jj = 0; jj < j; jj++) {
            x[jj] = 0;
        }

        // init y coordinates as 0, read from VMG later
        for (int ii = 0; ii < i; ii++) {
            y[ii] = 0;
        }

        
           //initialize top
        for (int jj=0;jj<j;jj++)
        {
            for(int ii=0;ii<i;ii++)
            {
                top[jj][ii][0]=0;
            }
        }
        

        
        // put right coordinates into grid, j,i,k
        for (int c = 0; c < j; c++) {
            for (int r = 0; r < i; r++) {
                for (int l = 0; l < k; l++) {
                    this.grid[c][r][l] = new cell();
                }
            }//c
        }// r
        
        
        
        
        UI=UIIn;
        
        
        System.out.println(rootPath);
        
    }//model    

    /*
     * read x,y grid coordinates, ibound and bottom of each cell from VMG
     * refer to vmod help "input data formats" section for format
     * 
     */
    /**
     * 
     * @param fname
     */
    public void readVMG(String fname) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fname));//,1024*1024);

            /*
             * read x, y, skip z 
             * 
             * format:
             * j                Line 1
             * x coordinates... j+1 l   ines in total
             * i                
             * y coordinates... i+1 lines in total
             * k
             * z's              k+1 lines in total
             * BLANK LINE
             */

            String strRead;
            
            // x
            in.readLine();//skip line j
            for (int c = 0; c <= this.j; c++) { // column: length=col+1
                strRead = in.readLine();
                this.x[c] = Double.parseDouble(strRead);
            }
            //y
            in.readLine();//skip line i
            for (int r = 0; r <= this.i; r++) { // row: length=row+1
                strRead = in.readLine();
                this.y[r] = Double.parseDouble(strRead);
            }
            //skip z's
            for (int r = 0; r <= this.k + 2; r++) { // 0 to k+2 because k+1 layers + NL + blankline
                in.readLine();
            }



            int keepReading = 1;
            if (keepReading == 1) {



                /* read ibound
                 * 
                 * ibounds are stored by row in normal MODFLOW order
                 * but bottom layer first
                 * 
                 * layer are seperated by BLANK LINE
                 * 
                 */

                for (int l = this.k; l >= 1; l--) { // bottom layer first
                    for (int r = 1; r <= this.i; r++) {
                        strRead = in.readLine();
                        String[] splitarray = strRead.split(" ");
                        for (int c = 1; c <= this.j; c++) {
                            this.grid[(c - 1)][(r - 1)][(l - 1)].setIbound(Integer.parseInt(splitarray[(c - 1)]));
                        }
                    }
                    strRead = in.readLine(); // BLANK LINE
                }


                /*
                 * read elevation
                 * 
                 * format:
                 * 
                 * elevations are stored by column in reverse coordinates
                 * 10 elements per line, bottom layer first
                 * 
                 * e.g. 11 rows, 2 col
                 *            
                 * 
                 * 11 22
                 * 10 21
                 * 9  20
                 * 8  19
                 * 7  18
                 * 6  17
                 * 5  16
                 * 4  15 
                 * 3  14
                 * 2  13
                 * 1  12
                 * 
                 * in vmg:
                 * 
                 * 1 2 3 4 5 6 7 8 9 10
                 * 11
                 * 12 13 14 15 16 17 18 19 20 21
                 * 22
                 * 
                 * numbers are delimited by one or more spaces
                 * used regex to capture the space(s)
                 * 
                 * 
                 */


                /*
                 * skip five lines:
                 * 
                 * BLANK LINE
                 * j
                 * i
                 * k
                 * BLANK LINE
                 * 
                 */
                for (int r = 0; r < 5; r++) {
                    in.readLine();
                }
                int stillReading = 1;
                if (stillReading == 1) {
                    /*
                     * read elevation arrays
                     * 
                     * model has to be larger than 10x10
                     */

                    // i = rowLines*10 + rowLeft
                    int rowLines = this.i / 10; // number of lines used to hold one row
                    int rowLeft = this.i - rowLines * 10; // residual number of entries to hold one row
                    System.out.println(rowLines + " " + rowLeft);


                    Pattern pattern = Pattern.compile("[ ]+");// delimited by one or more space(s)

                    for (int lay = 0; lay < this.k; lay++) {
                        for (int col = 0; col < this.j; col++) {

                            /*
                             * read lines with 10 elements each
                             */

                            // per line
                            for (int ind = 0; ind < rowLines; ind++) {
                                strRead = in.readLine();
                                if (strRead.isEmpty()) {
                                    strRead = in.readLine();
                                }
                                String[] splitarray = pattern.split(strRead.trim());
                                //             System.out.println("Elements per line is: " + splitarray.length);
                                // ten elements per line        
                                for (int iten = 0; iten < 10; iten++) {
                                    int row = ind * 10 + iten; //find row coordinates
                                    //                    System.out.println(splitarray[iten + 1]);

                                    
                                    this.grid[elev2codeJ(col)][elev2codeI(row)][elev2codeK(lay)].setBottom(Double.parseDouble(splitarray[iten]));
                                    //                    System.out.println(col + " " + row + " " + lay + "= " + splitarray[iten + 1]);
                                }
                            }


                            /*
                             * read the single line with < 10 elements
                             */
                            if (this.j > 10) {
                                strRead = in.readLine();
                                String[] splitarray = pattern.split(strRead.trim()); // delimited by one or more space(s)
                   //            System.out.println(splitarray.length);
                                for (int iLeft = 0; iLeft < rowLeft; iLeft++) {
                                    int row = rowLines * 10 + iLeft;
                                    
                              

                                        
                            //                System.out.println(Double.parseDouble(splitarray[iLeft]));
                                           this.grid[elev2codeJ(col)][elev2codeI(row)][elev2codeK(lay)].setBottom(Double.parseDouble(splitarray[iLeft]));
                                    
        //                            
                                    
                                }
                            }
                        }


                    }
                    
                    
                    // read top array
                    
                    
                    for (int col = 0; col < this.j; col++) {

                            /*
                             * read lines with 10 elements each
                             */

                            // per line
                            for (int ind = 0; ind < rowLines; ind++) {
                                strRead = in.readLine();
                                if (strRead.isEmpty()) {
                                    strRead = in.readLine();
                                }
                                String[] splitarray = pattern.split(strRead.trim());
                                //             System.out.println("Elements per line is: " + splitarray.length);
                                // ten elements per line        
                                for (int iten = 0; iten < 10; iten++) {
                                    int row = ind * 10 + iten; //find row coordinates
                                    //                    System.out.println(splitarray[iten + 1]);

                                 //   System.out.println(elev2codeJ(col)+"\t"+elev2codeI(row));
                                    this.top[elev2codeJ(col)][elev2codeI(row)][0] = Double.parseDouble(splitarray[iten]);
                                    //                    System.out.println(col + " " + row + " " + lay + "= " + splitarray[iten + 1]);
                                }
                            }


                            /*
                             * read the single line with < 10 elements
                             */
                            if (this.j > 10) {
                                strRead = in.readLine();
                                String[] splitarray = pattern.split(strRead.trim()); // delimited by one or more space(s)
                                for (int iLeft = 0; iLeft < rowLeft; iLeft++) {
                                    int row = rowLines * 10 + iLeft;

                                     this.top[elev2codeJ(col)][elev2codeI(row)][0] = Double.parseDouble(splitarray[iLeft]);
                    
                                    
                                }
                            }
                        }
                    
                    // end of reading top

                    

                }
                System.out.println("VMG read");
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }
    }

    // convert between world and model coordinates
    /**
     * 
     */
    public void world2Model() {
    }

    // read pit x,y,z from file,
    // use convertCoordinates to convert coordinates
    // uses regex to detect csv, space, or tab delimited
    // consider for improvement: more optimized algorithm for finding pit cells
    /**
     * 
     * @param fname
     */
    public void makePit(String fname) {

        try {
            BufferedReader in = new BufferedReader(new FileReader(fname));

            Pattern pattern = Pattern.compile("[ ,\t]+");

            String strRead;
            int pitx = -1;
            int pity = -1;
            int pitz = -1;
            while ((strRead = in.readLine()) != null) {
                //   strRead = in.readLine(); // don't need this, already reading in while()!
                if(strRead.contains("E")) continue;
                String[] splitarray = pattern.split(strRead);
                double xp = Double.parseDouble(splitarray[0]);
                double yp = Double.parseDouble(splitarray[1]);
                double zp = Double.parseDouble(splitarray[2]);
                //             System.out.println("xp:"+xp+"  yp:"+yp+"  zp:"+zp);


                // find pit cells
                for (int c = 1; c <= this.j; c++) { //consider adding exceptions
                    //              System.out.println("xc: "+x[c]);
                    if (this.x[c] > xp) {
                        pitx = c - 1;// c or c-1
                        break;
                    }

                }
                for (int r = 1; r <= this.i; r++) {
                    if (this.y[r] > yp) {
                        pity = this.i - r;// MODFLOW r coordinates is reverse of physical coordinates
                        break;
                        //                 System.out.println("r: "+r);
                    }
                }
                //            System.out.println("pity: "+pity);

                try {
                    if ((pitx == -1) || (pity == -1)) {
                        //    System.out.println("Pit cell outside model domain");
                        throw new Exception("Pit node outside model domain!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                for (int l = this.k - 2; l >= 0; l--) {
                    if (this.grid[pitx][pity][l].getBottom() > zp) {
              //        if(zp>this.grid[pitx][pity][l].getBottom()&&zp<this.grid[pitx][pity][l].getBottom()+10){
                        pitz = l + 1;
                        //  pitz=l;
                        //                   System.out.println("layer: " + l + " " + this.grid[pitx][pity][l].getBottom());
                        break;
                    } else if (zp < this.grid[pitx][pity][this.k - 1].getBottom()) {
                        System.out.println("Pit node below bottom of model");
                        break;
                    } // have to reconsider whether and how to store ground surface
                    else if (zp >= this.grid[pitx][pity][0].getBottom()) { //above bottoom of layer 1, make top layer pit
                        pitz = 0;
                    }

                }
                //            System.out.println("pitx: " + pitx + " pity: " + pity + " pitz: " + pitz);
                //Pit=number of pits nodes in one model cell
                this.grid[pitx][pity][pitz].setPit(this.grid[pitx][pity][pitz].getPit() + 1);
                //            System.out.println(pitx+" "+pity+" "+pitz+" "+ this.grid[pitx][pity][pitz].getPit());
                //Pit_evel= for now sum of all elevations
                this.grid[pitx][pity][pitz].setPit_elev(zp + this.grid[pitx][pity][pitz].getPit());

                //         System.out.println(xp + " " + yp + " " + zp);


            }//while
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }


        // take average pit elevation for pit cells    
        // there is room for optimization here
        for (int c = 0; c < this.j; c++) {
            for (int r = 0; r < this.i; r++) {
                for (int l = this.k - 2; l >= 0; l--) { // start from bottom
                    if (this.grid[c][r][l].getPit() > 0 && this.grid[c][r][l + 1].getPit() == 0) {//if this is a pit cell and cell below is NOT a pit cell - pit bottom
                        this.grid[c][r][l].setPit_elev(this.grid[c][r][l].getPit_elev() / this.grid[c][r][l].getPit());
                        // printDrain2VMB


                        // everything above drain is inactive, also set Pit=-1
                        for (int p = l - 1; p >= 0; p--) {
                            this.grid[c][r][p].setIbound(0);
                            this.grid[c][r][p].setPit(0);
                        }
                        break; //break layer loop since if bottom is drain, everything above is inactive

                    }//if
                }//l
            }//r
        }//c



    }

    /**
     * 
     * @param fin
     * @param fout
     */
    public void writeVMG(String fin, String fout) {

        try {
            BufferedReader in = new BufferedReader(new FileReader(fin));
            String strRead;
            PrintWriter out = new PrintWriter(new FileOutputStream(fout));

            // print whatever's before ibound
            for (int lines = 0; lines < this.j + this.i + this.k + 7; lines++) {
                strRead = in.readLine();
                out.println(strRead);
            }

            // print new ibound lines
            for (int l = this.k; l >= 1; l--) {
                for (int r = 1; r <= this.i; r++) {
                    for (int c = 1; c <= this.j; c++) {
                        out.print(this.grid[(c - 1)][(r - 1)][(l - 1)].getIbound() + " ");
                    }
                    out.println();
                }
                out.println();
            }

            /*
            // skip original ibound lines
            for(int lines = 0;lines<(nrow+1)*nlay;lines++)
            {
            strRead = in.readLine();        
            }     
            
            out.println();
            // print whatever's left in the vmg
            while ((strRead=in.readLine())!=null){
            strRead = in.readLine(); 
            out.println(strRead);
            }      
            
             */
            in.close();
            out.close();
            System.out.println("VMG Made");

        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }
    }

    /**
     * 
     * @param fname
     */
    public void writeVMP(String fname) {

      try
    {      
      PrintWriter out = new PrintWriter(new FileOutputStream(fname));
      
      for(int l=this.k;l>=1;l--)
      {
        for(int r=1;r<=this.i;r++)
        {
          for(int c=1;c<=this.j;c++)
          {
              
          //  out.print(this.grid[c-1][r-1][l-1].getCond()+" ");
              
              out.print("1 ");
          }
          out.println();          
        }
        out.println();
      } //nlay           
      out.close();
    }//try
    catch(FileNotFoundException e){System.out.println("File not found !");}
    catch(IOException ioe){}

    }
    
    
    // convert elevation array coordinates to PitKit coordinates
    /**
     * 
     * @param elev_i
     * @return
     */
    public int elev2codeI(int elev_i) {
        return this.i - elev_i - 1;
    }

    /**
     * 
     * @param elev_j
     * @return
     */
    public int elev2codeJ(int elev_j) {
        return elev_j;
    }

    /**
     * 
     * @param elev_k
     * @return
     */
    public int elev2codeK(int elev_k) {
        return this.k - elev_k - 1;
    }

    /**
     * 
     * @param fname
     */
    public void readVMP(String fname) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fname));

            String strRead;

            for (int l = this.k; l >= 1; l--) { // bottom layer first
                for (int r = 1; r <= this.i; r++) {
                    strRead = in.readLine();
                    String[] splitarray = strRead.split(" ");
                    for (int c = 1; c <= this.j; c++) {
                        this.grid[(c - 1)][(r - 1)][(l - 1)].setCond(Integer.parseInt(splitarray[(c - 1)]));
                    }
                }
                strRead = in.readLine(); // BLANK LINE
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }

    }

    // for greyfox
    /**
     * 
     */
    public void getBedrockElevation() {
        for (int c = 0; c < j; c++) {
            for (int r = 0; r < i; r++) {
                for (int l = 0; l < k; l++) {
                    if (this.grid[c][r][l].getCond() == 5 && this.grid[c][r][l - 1].getCond() != 5) {
                        double thickness = this.grid[c][r][0].getBottom() - this.grid[c][r][l - 1].getBottom() + 5;//5 is layer 1 thickness
                        System.out.print((x[c] / 2 + x[c + 1] / 2) + "\t" + (y[r] / 2 + y[r + 1] / 2) + "\t" + thickness + "\n");
                    }
                }
            }//c
        }// 
    }

    /// this method doesn't work
    /**
     * 
     * @param fname
     */
    public void readVmodxyz(String fname) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fname));

            String strRead;            
            Pattern pattern = Pattern.compile("[\t ]+");

            while ((strRead = in.readLine()) != null) {
               
                String[] splitarray = pattern.split(strRead.trim()); // trimmed
                int row = Integer.parseInt(splitarray[1]);
                int col = Integer.parseInt(splitarray[2]);
                int lay = Integer.parseInt(splitarray[0]);
               // double elevation = Double.parseDouble(splitarray[3]);
               // this.grid[col - 1][row - 1][lay - 1].setBottom(elevation);
                //this.grid[col-1][row-1][lay-1].setIbound(0);
                
                // for doubling K
                if(this.grid[col-1][row-1][lay-1].getCond()==6)
                {this.grid[col-1][row-1][lay-1].setCond(17);}//shallow
                if(this.grid[col-1][row-1][lay-1].getCond()==7) this.grid[col-1][row-1][lay-1].setCond(18);//deep
                if(this.grid[col-1][row-1][lay-1].getCond()==8) this.grid[col-1][row-1][lay-1].setCond(19);//fa
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }

    }

    /**
     * 
     * @param fname
     */
    public void printBottom(String fname) {


        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(fname));

            for (int c = 0; c < this.j; c++) {
                for (int r = 0; r < this.i; r++) {
                    for (int l = 0; l < 1; l++) {
                        //            System.out.println("k:" + l + "    " + this.grid[c][r][l].getBottom());

                        //print water table 
                    //    out.println(((this.x[c]+this.x[c+1])/2+575640+1060) + "\t" + ((this.y[i-r]+this.y[i-(r+1)])/2+5391875+25) + "\t" + this.grid[c][r][l].getBottom());
                        
                        // print L-till head RR
                        out.println(((this.x[c]+this.x[c+1])/2) + "\t" + ((this.y[i-r]+this.y[i-(r+1)])/2) + "\t" + (this.grid[c][r][l].getBottom()-this.grid[c][r][l].getPit_elev()));
                        
                    }

                }

            }
            out.close();
            System.out.println("WatertableDone");

        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }
    }

    public JI findJI(double xp, double yp) {
        
        JI ji = new JI(0,0);
        
        for (int c = 1; c <= this.j; c++) { //consider adding exceptions
            //              System.out.println("xc: "+x[c]);
            if (this.x[c] > xp) {
                ji.J = c - 1;// c or c-1
                break;
            }

        }
        for (int r = 1; r <= this.i; r++) {
            if (this.y[r] > yp) {
                ji.I = this.i - r;// MODFLOW r coordinates is reverse of physical coordinates
                break;
                //                 System.out.println("r: "+r);
            }
        }
        
        return ji;

    }

    public void findJI_inFile() {
        try {
            
            this.readVMG("C:/0-Modeling projects/9-RainyRiver/vmod/2012-dummie/DUMMIE.VMG");
            BufferedReader in = new BufferedReader(new FileReader("C:/0-Modeling projects/9-RainyRiver/shit.txt"));

            String strRead;
            Pattern pattern = Pattern.compile("[\t ]+");

            while ((strRead = in.readLine()) != null) {
                String[] splitarray = pattern.split(strRead);
                String ID = splitarray[0];
                double e = Double.parseDouble(splitarray[1]);
                double n = Double.parseDouble(splitarray[2]);
                //int lay = Integer.parseInt(splitarray[2]);
                //  double elevation = Double.parseDouble(splitarray[3]);
                JI ji = new JI(0,0);
                ji = this.findJI(e, n);
                
                double ground = this.grid[ji.J][ji.I][0].getBottom();
                double layer2_thickness = this.grid[ji.J][ji.I][0].getBottom() - this.grid[ji.J][ji.I][1].getBottom();
                double layer3_thickness = this.grid[ji.J][ji.I][1].getBottom() - this.grid[ji.J][ji.I][2].getBottom();
                double layer4_thickness = this.grid[ji.J][ji.I][2].getBottom() - this.grid[ji.J][ji.I][3].getBottom();
                
           //     System.out.println(ID);
           //     System.out.println( layer2_thickness);
        //        System.out.println(layer3_thickness);
                System.out.println(layer4_thickness);
                
                
            }

            in.close();
      
      //      m.printBottom("J:/Sheldon backup (large)/0-Modeling projects/4-Hemlo/Hemlo_Sept_newest_Pitdesign/drawdown/big_heads/existing/v3_watertable.txt");

        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }
    }

    final class JI {

        int J;
        int I;

        public JI(int J, int I) {
            this.J = J;
            this.I = I;
        }
    }

    public void readVMB(String fname) {
        
        
        // so far only for steady state with drain cells
        
        
        try {

            BufferedReader in = new BufferedReader(new FileReader(fname));
            String strRead;
          
            
            
            // skip 4 lines 
            for (int skip = 0;skip<=3;skip++)
            {
                in.readLine();
            }
     

            while ((strRead = in.readLine()) != null) {

                int dLay = Integer.parseInt(strRead);
                int dCol = Integer.parseInt(in.readLine());
                int dRow = Integer.parseInt(in.readLine());

                int bcType = Integer.parseInt(in.readLine());
                int group = Integer.parseInt(in.readLine());
                int numOfEntries = Integer.parseInt(in.readLine());

                this.grid[dCol][dRow][dLay].setBc(bcType);
                this.grid[dCol][dRow][dLay].setBcGroup(group);

                if (bcType == 3) {
                    Pattern pattern = Pattern.compile("[\t ]+");

                    String[] splitarray = pattern.split(in.readLine().trim());

                    double startTime = Double.parseDouble(splitarray[0]);
                    double stopTime = Double.parseDouble(splitarray[1]);
                    double drainElev = Double.parseDouble(splitarray[2]);
                    double drainCond = Double.parseDouble(splitarray[3]);
                    int active = Integer.parseInt(splitarray[4]);

        

                    this.grid[dCol][dRow][dLay].setDrain_elev(drainElev);
                    this.grid[dCol][dRow][dLay].setDrain_cond(drainCond);

                }else
                {
                    in.readLine();
                }
            }
            
            System.out.println(this.grid[52][108][8].getDrain_elev());
    

            in.close();

            //      m.printBottom("J:/Sheldon backup (large)/0-Modeling projects/4-Hemlo/Hemlo_Sept_newest_Pitdesign/drawdown/big_heads/existing/v3_watertable.txt");

        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }
    }
    
    public void printVMB(String fname)
    {

        
        // currently only supports drain
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(fname));
            
            out.println(this.k);
            out.println(this.j);
            out.println(this.i);
            out.println();
            

            for (int c = 0; c < this.j; c++) {
                for (int r = 0; r < this.i; r++) {
                    for (int l = 0; l < this.k; l++) {
                        
                        if(this.grid[c][r][l].getBc()==3)
                        {
                            out.println(l);
                            out.println(c);
                            out.println(r);
                            out.println(3); //drain
                            out.println(3); //group 0;
                            out.println(1);
                            out.print("              ");
                            out.print(0);
                            out.print("               ");
                            out.print(1);
                            out.print("   ");
                            out.print(this.grid[c][r][l].getDrain_elev());
                            out.print("   ");
                            out.print(this.grid[c][r][l].getDrain_cond());
                            System.out.println(this.grid[c][r][l].getDrain_cond());
                            out.print(" ");
                            out.println(1+"   0.0000000e+00");
                            
                            
                                    
                        }
                        
                         if(this.grid[c][r][l].getBc()==2) //river
                        {
                            out.println(l);
                            out.println(c);
                            out.println(r);
                            out.println(2); //drain
                            out.println(this.grid[c][r][l].getBcGroup()); //group
                            out.println(1);
                            out.print("              ");
                            out.print(0);
                            out.print("               ");
                            out.print(1);
                            out.print("   ");
                            out.print(this.grid[c][r][l].getDrain_elev());
                            out.print("   ");
                            out.print(this.grid[c][r][l].getBottom()+.1);
                            out.print("   ");                            // river bottoms
                            out.print(this.grid[c][r][l].getDrain_cond());
                   //         System.out.println(this.grid[c][r][l].getDrain_cond());
                            out.print(" ");
                            out.println(1+"   0.0000000e+00");
                            
                            
                                    
                        }
                        
                    }

                }

            }
            out.close();
            System.out.println("done");

        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }        
    }
public void changeBcGroup(String fname, String outName) {
        
        
        // so far only for steady state with drain cells
        
        
        try {

            BufferedReader in = new BufferedReader(new FileReader(fname));            
          
            PrintWriter out = new PrintWriter(new FileOutputStream(outName));
            

                        
            String strRead;
          
            
            
            // skip 4 lines 
            for (int skip = 0;skip<=3;skip++)
            {
                out.println(in.readLine());
            }
     

            while ((strRead = in.readLine()) != null) {

//                int dLay = Integer.parseInt(strRead);
                out.println(strRead.trim());
                int dCol = Integer.parseInt(in.readLine().trim());
                out.println(dCol);
                int dRow = Integer.parseInt(in.readLine().trim());
                out.println(dRow);
                
                in.readLine();
                out.println("1");
                int group = Integer.parseInt(in.readLine().trim());
                out.println(group);
                int numOfEntries = Integer.parseInt(in.readLine().trim());
                out.println(numOfEntries);
            //    this.grid[dCol][dRow][dLay].setBc(bcType);
            //    this.grid[dCol][dRow][dLay].setBcGroup(group);

                out.println(in.readLine());
                /*
                if (bcType == 3) {
                    Pattern pattern = Pattern.compile("[\t ]+");

                    String[] splitarray = pattern.split(in.readLine().trim());

                    double startTime = Double.parseDouble(splitarray[0]);
                    double stopTime = Double.parseDouble(splitarray[1]);
                    double drainElev = Double.parseDouble(splitarray[2]);
                    double drainCond = Double.parseDouble(splitarray[3]);
                    int active = Integer.parseInt(splitarray[4]);

        

                    this.grid[dCol][dRow][dLay].setDrain_elev(drainElev);
                    this.grid[dCol][dRow][dLay].setDrain_cond(drainCond);

                }else
                {
                    in.readLine();
                }
                * 
                */
            }
            
           // System.out.println(this.grid[52][108][8].getDrain_elev());
    

            in.close();
            out.close();

            //      m.printBottom("J:/Sheldon backup (large)/0-Modeling projects/4-Hemlo/Hemlo_Sept_newest_Pitdesign/drawdown/big_heads/existing/v3_watertable.txt");

        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
        } catch (IOException ioe) {
        }
    }


    public void makeModelGridFile()
    {
        
    }
    
    
    public void appendToFile(String fname)
    {
        try{
    		String data = " This content will append to the end of the file";
 
    		File file =new File(fname);
 
    		//if file doesnt exists, then create it
 //   		if(!file.exists()){
   // 			file.createNewFile();
    //		}
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(file.getName(),true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                
                
                
                
    	        bufferWritter.write(data);
                
                
                
                
                
    	        bufferWritter.close();
 
	        System.out.println("Done");
 
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    
public void printModelGridFile(String fname)    
{
       try {

          
  
           PrintWriter out = new PrintWriter(new FileOutputStream(fname));


           UI.updateTextField("Make modelgrid.dat");
           
           out.println("NC");
           
           for (int jj = 0; jj < j; jj++) {
               out.println(x[jj]);
           }

           out.println("NR");
           
           // initialize x coordinates as 0, read form VMG later
           for (int ii = 0; ii < i; ii++) {
               out.println(y[ii]);
           }

           out.println("NL");
           
           
           
            
           // System.out.println(this.grid[52][108][8].getDrain_elev());
    

            out.close();

            //      m.printBottom("J:/Sheldon backup (large)/0-Modeling projects/4-Hemlo/Hemlo_Sept_newest_Pitdesign/drawdown/big_heads/existing/v3_watertable.txt");

        }
         catch (IOException ioe) {
        }    
    
}
    
}
