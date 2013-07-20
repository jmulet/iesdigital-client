/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;

/**
 *
 * @author Josep
 */
public class BeanMedicamentsResum {
    protected int id;
    protected String medicament;
    protected int total;

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
     * @return the medicament
     */
    public String getMedicament() {
        return medicament;
    }

    /**
     * @param medicament the medicament to set
     */
    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
