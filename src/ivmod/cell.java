/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivmod;

/**
 *
 * @author sheldon.chi
 */
public class cell {
    
//    private int j;
//    private int i;
//    private int k;
    private int pit;
    private int bc;//boundary condition; 3 is drain
    private int ibound;
//    private double top;
    private double bottom; 
    private double pit_elev;
    private int cond;
    
    // drain conductance 
    private double drain_cond;
    
    private double drain_elev;
    
    private int bcGroup;
    
    private int zonebudget;
    
    private int drain_code;//group code
   

    
    /**
     * 
     */
    public cell() {
  //      this.i = i;
   //     this.j = j;
   //     this.k = k;
        this.pit = 0;
        this.bc = 0;
        this.ibound = 0;
 //       this.top = 0.0;
        this.bottom = 0.0;
        this.pit_elev = 0.0;
        this.cond=0;
        this.drain_cond = 0.0;
        this.drain_elev = 0.0;
        this.zonebudget=0;
        
        this.drain_code=0;
        
        
    }
    

    /**
     * @return the pit
     */
    public int getPit() {
        return pit;
    }

    /**
     * @param pit the pit to set
     */
    public void setPit(int pit) {
        this.pit = pit;
    }

    /**
     * @return the bc
     */
    public int getBc() {
        return bc;
    }

    /**
     * @param bc the bc to set
     */
    public void setBc(int bc) {
        this.bc = bc;
    }

    /**
     * @return the ibound
     */
    public int getIbound() {
        return ibound;
    }

    /**
     * @param ibound the ibound to set
     */
    public void setIbound(int ibound) {
        this.ibound = ibound;
    }

    /**
     * @return the bottom
     */
    public double getBottom() {
        return bottom;
    }

    /**
     * @param bottom the bottom to set
     */
    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    /**
     * @return the pit_elev
     */
    public double getPit_elev() {
        return pit_elev;
    }

    /**
     * @param pit_elev the pit_elev to set
     */
    public void setPit_elev(double pit_elev) {
        this.pit_elev = pit_elev;
    }
     
    /**
     * 
     * @return
     */
    public int getCond() {
        return cond;
    }

    /**
     * 
     * @param cond
     */
    public void setCond(int cond) {
        this.cond = cond;
    }

    /**
     * @return the drain_cond
     */
    public double getDrain_cond() {
        return drain_cond;
    }

    /**
     * @param drain_cond the drain_cond to set
     */
    public void setDrain_cond(double drain_cond) {
        this.drain_cond = drain_cond;
    }

    /**
     * @return the drain_elev
     */
    public double getDrain_elev() {
        return drain_elev;
    }

    /**
     * @param drain_elev the drain_elev to set
     */
    public void setDrain_elev(double drain_elev) {
        this.drain_elev = drain_elev;
    }

    /**
     * @return the bcGroup
     */
    public int getBcGroup() {
        return bcGroup;
    }

    /**
     * @param bcGroup the bcGroup to set
     */
    public void setBcGroup(int bcGroup) {
        this.bcGroup = bcGroup;
    }

    /**
     * @return the zonebudget
     */
    public int getZonebudget() {
        return zonebudget;
    }

    /**
     * @param zonebudget the zonebudget to set
     */
    public void setZonebudget(int zonebudget) {
        this.zonebudget = zonebudget;
    }

    /**
     * @return the drain_code
     */
    public int getDrain_code() {
        return drain_code;
    }

    /**
     * @param drain_code the drain_code to set
     */
    public void setDrain_code(int drain_code) {
        this.drain_code = drain_code;
    }


}
