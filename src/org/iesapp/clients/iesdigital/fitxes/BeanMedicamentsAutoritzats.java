/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;

/**
 *
 * @author Josep
 */
public class BeanMedicamentsAutoritzats {
    protected int expd;
    protected String nomcomplet;
    protected String grup;
    protected String autoritzats;
    protected String observacions;

    /**
     * @return the expd
     */
    public int getExpd() {
        return expd;
    }

    /**
     * @param expd the expd to set
     */
    public void setExpd(int expd) {
        this.expd = expd;
    }

    /**
     * @return the nomcomplet
     */
    public String getNomcomplet() {
        return nomcomplet;
    }

    /**
     * @param nomcomplet the nomcomplet to set
     */
    public void setNomcomplet(String nomcomplet) {
        this.nomcomplet = nomcomplet;
    }

    /**
     * @return the grup
     */
    public String getGrup() {
        return grup;
    }

    /**
     * @param grup the grup to set
     */
    public void setGrup(String grup) {
        this.grup = grup;
    }

    /**
     * @return the autoritzats
     */
    public String getAutoritzats() {
        return autoritzats;
    }

    /**
     * @param autoritzats the autoritzats to set
     */
    public void setAutoritzats(String autoritzats) {
        this.autoritzats = autoritzats;
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
