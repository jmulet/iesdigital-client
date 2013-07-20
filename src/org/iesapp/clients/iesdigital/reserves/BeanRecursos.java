/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.reserves;

/**
 *
 * @author Josep
 */
public class BeanRecursos {
    protected String id="";
    protected String material="";
    protected String descripcio="";

    public BeanRecursos()
    {
        
    }
    public BeanRecursos(String descripcio, String id)
    {
        this.descripcio = descripcio;
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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
