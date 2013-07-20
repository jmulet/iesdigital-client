/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;

/**
 *
 * @author Josep
 */
public class BeanActuacionsPendents {
    
    private String alumne="";
    private String grup="";
    private String actuacio="";
    private String expd="";
    private String obrir="";
    private String tancar="";

    public String getTancar() {
        return tancar;
    }

    public void setTancar(String tancar) {
        this.tancar = tancar;
    }

    public String getObrir() {
        return obrir;
    }

    public void setObrir(String obrir) {
        this.obrir = obrir;
    }


    public String getExpd() {
        return expd;
    }

    public void setExpd(String expd) {
        this.expd = expd;
    }


    public String getActuacio() {
        return actuacio;
    }

    public void setActuacio(String actuacio) {
        this.actuacio = actuacio;
    }


    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }


    public String getAlumne() {
        return alumne;
    }

    public void setAlumne(String alumne) {
        this.alumne = alumne;
    }

}
