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
@DefaultTableMapping(tableName="sig_senseguardia")
public class BeanSenseGuardia {
    @PK 
    @TableMapping
    protected int id = 0;
    @TableMapping
    protected String item = "";
    @TableMapping
    protected String descripcio = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
     
    @Override
    public String toString()
    {
         return "BeanSenseGuardia: id="+this.getId()+"; item="+this.item+"; descripcio="+descripcio+";";
    }
}
