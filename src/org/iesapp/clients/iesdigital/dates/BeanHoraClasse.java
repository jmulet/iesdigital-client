/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.dates;
 
import org.iesapp.database.vscrud.DefaultTableMapping;
import org.iesapp.database.vscrud.PK;
import org.iesapp.database.vscrud.TableMapping;

/**
 *
 * @author Josep
 */
@DefaultTableMapping(tableName="sig_hores_classe")
public class BeanHoraClasse {
    public static final int CLASSE=2;
    public static final int PATI=1;
    public static final int ALL=3;
    @PK
    @TableMapping
    protected int id=0;
    @TableMapping
    protected int idTipoHoras=CLASSE;
    @TableMapping
    protected String codigo="";
    @TableMapping
    protected java.sql.Time inicio;
    @TableMapping
    protected java.sql.Time fin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTipoHoras() {
        return idTipoHoras;
    }

    public void setIdTipoHoras(int idTipoHoras) {
        this.idTipoHoras = idTipoHoras;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    
    @Override
    public String toString()
    {
        return "BeanHoraClasse: id="+id+"; idTipoHoras="+idTipoHoras+"; codigo="+codigo+"; "+
               " desde:"+getInicio()+" hasta:"+getFin();
    }

    public java.sql.Time getInicio() {
        return inicio;
    }

    public void setInicio(java.sql.Time inicio) {
        this.inicio = inicio;
    }

    public java.sql.Time getFin() {
        return fin;
    }

    public void setFin(java.sql.Time fin) {
        this.fin = fin;
    }
}
