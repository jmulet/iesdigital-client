/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.guardies;

import org.iesapp.database.vscrud.DefaultTableMapping;
import org.iesapp.database.vscrud.PK;
import org.iesapp.database.vscrud.TableMapping;

/**
 *
 * @author Josep
 */
@DefaultTableMapping(tableName="sig_signatures")
public class BeanSignaturaMati implements BeanSignatura {
    
    @PK
    @TableMapping
    protected int id = 0;
    @TableMapping
    protected String abrev = "";
    @TableMapping
    protected java.util.Date data;
    @TableMapping
    protected int h1 = -1;
    @TableMapping
    protected java.util.Date h1_t ;
    @TableMapping
    protected int h2 = -1;
    @TableMapping
    protected java.util.Date h2_t ;
    @TableMapping
    protected int h3 = -1;
    @TableMapping
    protected java.util.Date h3_t ;
    @TableMapping
    protected int h4 = -1;
    @TableMapping
    protected java.util.Date h4_t ;
    @TableMapping
    protected int h5 = -1;
    @TableMapping
    protected java.util.Date h5_t ;
    @TableMapping
    protected int h6 = -1;
    @TableMapping
    protected java.util.Date h6_t ;
    @TableMapping
    protected int h7 = -1;
    @TableMapping
    protected java.util.Date h7_t; 
    @TableMapping(tableName="sig_professorat")
    protected String nombre=""; 
    
    @Override
    public String toString()
    {
         return "BeanSignatura: id="+this.getId()+"; abrev="+this.getAbrev()+"; nombre="+nombre+" data="+getData()+"; "+
                 "h1="+h1+"; h1_t="+h1_t+";"+
                 "h2="+h2+"; h2_t="+h2_t+";"+
                 "h3="+h3+"; h3_t="+h3_t+";"+
                 "h4="+h4+"; h4_t="+h4_t+";"+
                 "h5="+h5+"; h5_t="+h5_t+";"+
                 "h6="+h6+"; h6_t="+h6_t+";"+
                 "h7="+h7+"; h7_t="+h7_t+";";
    }

    public java.util.Date getH7_t() {
        return h7_t;
    }

    public void setH7_t(java.util.Date h7_t) {
        this.h7_t = h7_t;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public java.util.Date getData() {
        return data;
    }

    public void setData(java.util.Date data) {
        this.data = data;
    }

    public int getH1() {
        return h1;
    }

    public void setH1(int h1) {
        this.h1 = h1;
    }

    public java.util.Date getH1_t() {
        return h1_t;
    }

    public void setH1_t(java.util.Date h1_t) {
        this.h1_t = h1_t;
    }

    public int getH2() {
        return h2;
    }

    public void setH2(int h2) {
        this.h2 = h2;
    }

    public java.util.Date getH2_t() {
        return h2_t;
    }

    public void setH2_t(java.util.Date h2_t) {
        this.h2_t = h2_t;
    }

    public int getH3() {
        return h3;
    }

    public void setH3(int h3) {
        this.h3 = h3;
    }

    public java.util.Date getH3_t() {
        return h3_t;
    }

    public void setH3_t(java.util.Date h3_t) {
        this.h3_t = h3_t;
    }

    public int getH4() {
        return h4;
    }

    public void setH4(int h4) {
        this.h4 = h4;
    }

    public java.util.Date getH4_t() {
        return h4_t;
    }

    public void setH4_t(java.util.Date h4_t) {
        this.h4_t = h4_t;
    }

    public int getH5() {
        return h5;
    }

    public void setH5(int h5) {
        this.h5 = h5;
    }

    public java.util.Date getH5_t() {
        return h5_t;
    }

    public void setH5_t(java.util.Date h5_t) {
        this.h5_t = h5_t;
    }

    public int getH6() {
        return h6;
    }

    public void setH6(int h6) {
        this.h6 = h6;
    }

    public java.util.Date getH6_t() {
        return h6_t;
    }

    public void setH6_t(java.util.Date h6_t) {
        this.h6_t = h6_t;
    }

    public int getH7() {
        return h7;
    }

    public void setH7(int h7) {
        this.h7 = h7;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int[] getH_asArray()
    {
        int[] array = new int[7];
        array[0]=h1;
        array[1]=h2;
        array[2]=h3;
        array[3]=h4;
        array[4]=h5;
        array[5]=h6;
        array[6]=h7;
        return array;
    }
    
    public java.util.Date[] getHt_asArray()
    {
        java.util.Date[] array = new java.util.Date[7];
        array[0]=h1_t;
        array[1]=h2_t;
        array[2]=h3_t;
        array[3]=h4_t;
        array[4]=h5_t;
        array[5]=h6_t;
        array[6]=h7_t;
        return array;
    }

}
