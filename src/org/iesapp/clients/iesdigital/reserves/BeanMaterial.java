/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.reserves;

import javax.swing.ImageIcon;
import org.iesapp.database.vscrud.DefaultTableMapping;
import org.iesapp.database.vscrud.PK;
import org.iesapp.database.vscrud.TableMapping;

/**
 *
 * @author Josep
 */
@DefaultTableMapping(tableName="sig_reserves_material")
public class BeanMaterial {
    @PK
    @TableMapping
    protected int id=0;
    @TableMapping
    protected String material="";
    @TableMapping
    protected String ubicacio="";
    @TableMapping
    protected String descripcio="";
    @TableMapping
    protected byte[] imatge;
    
    //Linked to imatge in bytes
    protected ImageIcon icon;
 
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
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public byte[] getImatge() {
        return imatge;
    }

    public void setImatge(byte[] imatge) {
        this.imatge = imatge;
    }
     
    @Override
    public String toString()
    {
        return "BeanMaterial: id="+id+"; material="+material+"; ubicacio="+ubicacio+
                "; descripcio="+descripcio+"; imatge="+imatge;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
