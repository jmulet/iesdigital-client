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
@DefaultTableMapping(tableName="sig_guardies")
public class BeanHorariGuardia {
    @PK 
    @TableMapping
    protected int id = 0;
    @TableMapping
    protected String abrev = "";
    @TableMapping
    protected String lloc = "";
    @TableMapping(tableName="sig_guardies_zones", mode="r")
    protected String descripcio = "";
    @TableMapping
    protected int dia = 0;
    @TableMapping
    protected int hora = 0;  
    
    @Override
    public String toString()
    {
         return "BeanHorariGuardia: id="+this.getId()+"; abrev="+this.abrev+"; lloc="+
                this.lloc+"; descripcio="+descripcio+"; dia="+getDia()+"; hora="+getHora();
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

    public String getLloc() {
        return lloc;
    }

    public void setLloc(String lloc) {
        this.lloc = lloc;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    
 
}
