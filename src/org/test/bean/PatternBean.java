/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.test.bean;
 
/**
*
* @author OR501695
*/
public class PatternBean {
    private String id;
    private int morphType;
    private boolean erosion;
    private int sizeDorE;
    private int cuadrante;
 
    public PatternBean(String patternId){       
        String[] a;
        a = patternId.split(" ");
        id = patternId;
        cuadrante = new Integer(a[0]);
        erosion   = (a[1].equals("T"));
        morphType = new Integer(a[2]);
        sizeDorE  = new Integer(a[3]);  
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
   
    public int getMorphType() {
        return morphType;
    }
 
    public void setMorphType(int morphType) {
        this.morphType = morphType;
    }
 
    public boolean isErosion() {
        return erosion;
    }
 
    public void setErosion(boolean erosion) {
        this.erosion = erosion;
    }
 
    public int getSizeDorE() {
        return sizeDorE;
    }
 
    public void setSizeDorE(int sizeDorE) {
        this.sizeDorE = sizeDorE;
    }
 
    public int getCuadrante() {
        return cuadrante;
    }
 
    public void setCuadrante(int cuadrante) {
        this.cuadrante = cuadrante;
    }
   
    
}