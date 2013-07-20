/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;

/**
 *
 * @author Josep
 */
public class SgdInc {
    protected String simbol="";
    protected String ambit="";
    protected int n1a=0;
    protected int n2a=0;
    protected int n3a=0;

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public void setN1a(int n1a) {
        this.n1a = n1a;
    }

    public void setN2a(int n2a) {
        this.n2a = n2a;
    }

    public void setN3a(int n3a) {
        this.n3a = n3a;
    }
    
    public int getNTotal()
    {
        return getN1a()+getN2a()+getN3a();
    }

    public String getAmbit() {
        return ambit;
    }

    public void setAmbit(String ambit) {
        this.ambit = ambit;
    }

    public int getN1a() {
        return n1a;
    }

    public int getN2a() {
        return n2a;
    }

    public int getN3a() {
        return n3a;
    }
}
