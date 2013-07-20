/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.dates;

import java.text.SimpleDateFormat;
import org.iesapp.database.vscrud.DefaultTableMapping;
import org.iesapp.database.vscrud.PK;
import org.iesapp.database.vscrud.TableMapping;

/**
 *
 * @author Josep
 */
@DefaultTableMapping(tableName="sig_festius")
public class BeanFestiu {
    @PK
    @TableMapping
    protected int id = 0;
    @TableMapping
    protected java.util.Date desde;
    @TableMapping
    protected java.util.Date fins;
    @TableMapping(tableField="commentari")
    protected String comentari = "";
 
    
    @Override
    public String toString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return "BeanFestiu: id="+this.getId()+"; desde="+sdf.format(this.desde)+"; fins="+
                sdf.format(this.fins)+"; comentari="+this.comentari+";";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.util.Date getDesde() {
        return desde;
    }

    public void setDesde(java.util.Date desde) {
        this.desde = desde;
    }

    public java.util.Date getFins() {
        return fins;
    }

    public void setFins(java.util.Date fins) {
        this.fins = fins;
    }

    public String getComentari() {
        return comentari;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }
}
