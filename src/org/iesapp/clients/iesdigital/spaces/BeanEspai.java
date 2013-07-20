/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.spaces;

import org.iesapp.database.vscrud.DefaultTableMapping;
import org.iesapp.database.vscrud.PK;
import org.iesapp.database.vscrud.TableMapping;

/**
 *
 * @author Josep
 */
@DefaultTableMapping(tableName="sig_espais")
public class BeanEspai {
    @PK
    @TableMapping
    protected int id = 0;
    @TableMapping
    protected String aula = "";
    @TableMapping
    protected String descripcio = "";
    @TableMapping
    protected String zona_guardia = "";
    @TableMapping
    protected int utilizable_guardia = 0;
    @TableMapping
    protected int reservable = 0;
  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getZona_guardia() {
        return zona_guardia;
    }

    public void setZona_guardia(String zona_guardia) {
        this.zona_guardia = zona_guardia;
    }

    public int getUtilizable_guardia() {
        return utilizable_guardia;
    }

    public void setUtilizable_guardia(int utilizable_guardia) {
        this.utilizable_guardia = utilizable_guardia;
    }

    public int getReservable() {
        return reservable;
    }

    public void setReservable(int reservable) {
        this.reservable = reservable;
    }
    
    @Override
    public String toString()
    {
        return "BeanEspai: id="+this.id+"; aula="+this.aula+"; descripcio="+this.descripcio+
                "; zona_guardia="+this.zona_guardia+"; reservable="+
                this.reservable+"; utilizable_guardia="+this.utilizable_guardia;
    }
}
