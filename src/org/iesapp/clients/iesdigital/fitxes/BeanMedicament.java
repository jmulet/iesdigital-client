/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;

/**
 *
 * @author Josep
 */
public class BeanMedicament {
    protected int id = 0;
    protected String descripcio = "";
    protected boolean autoritzat = false;
    protected String observacions = "";
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the descripcio
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * @param descripcio the descripcio to set
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * @return the autoritzat
     */
    public boolean isAutoritzat() {
        return autoritzat;
    }

    /**
     * @param autoritzat the autoritzat to set
     */
    public void setAutoritzat(boolean autoritzat) {
        this.autoritzat = autoritzat;
    }

    /**
     * @return the observacions
     */
    public String getObservacions() {
        return observacions;
    }

    /**
     * @param observacions the observacions to set
     */
    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }
    
}
