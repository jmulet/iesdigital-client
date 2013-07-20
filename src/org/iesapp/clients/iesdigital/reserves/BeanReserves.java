/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.reserves;

import org.iesapp.database.vscrud.DefaultTableMapping;
import org.iesapp.database.vscrud.PK;
import org.iesapp.database.vscrud.TableMapping;

/**
 *
 * @author Josep
 */
@DefaultTableMapping(tableName="sig_reserves")
public class BeanReserves {
    public static final int AULES=2;
    public static final int MATERIAL=1;

    @PK
    @TableMapping
    protected int id=-1;
    @TableMapping
    protected String abrev = "";
    @TableMapping
    protected String nombreProfesor = "";
    @TableMapping
    protected java.sql.Date dia;
    @TableMapping
    protected int hora = 0;
    @TableMapping
    protected String motiu = "";
    @TableMapping
    protected int tipusConcepte = 0;
    @TableMapping
    protected String idConcepte = "";
    @TableMapping
    protected String concepte = "";
    
    protected String formatedhora="";
    protected boolean caducada=false;

   
    /**
     * @return the abrev
     */
    public String getAbrev() {
        return abrev;
    }

    /**
     * @param abrev the abrev to set
     */
    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    /**
     * @return the dia
     */
    public java.sql.Date getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(java.sql.Date dia) {
        this.dia = dia;
    }

    /**
     * @return the hora
     */
    public int getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

    /**
     * @return the motiu
     */
    public String getMotiu() {
        return motiu;
    }

    /**
     * @param motiu the motiu to set
     */
    public void setMotiu(String motiu) {
        this.motiu = motiu;
    }

  

    /**
     * @return the concepte
     */
    public String getConcepte() {
        return concepte;
    }

    /**
     * @param concepte the concepte to set
     */
    public void setConcepte(String concepte) {
        this.concepte = concepte;
    }

    /**
     * @return the tipusConcepte
     */
    public int getTipusConcepte() {
        return tipusConcepte;
    }

    /**
     * @param tipusConcepte the tipusConcepte to set
     */
    public void setTipusConcepte(int tipusConcepte) {
        this.tipusConcepte = tipusConcepte;
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

    /**
     * @return the idConcepte
     */
    public String getIdConcepte() {
        return idConcepte;
    }

    /**
     * @param idConcepte the idConcepte to set
     */
    public void setIdConcepte(String idConcepte) {
        this.idConcepte = idConcepte;
    }

    /**
     * @return the formatedhora
     */
    public String getFormatedhora() {
        return formatedhora;
    }

    /**
     * @param formatedhora the formatedhora to set
     */
    public void setFormatedhora(String formatedhora) {
        this.formatedhora = formatedhora;
    }

    /**
     * @return the caducada
     */
    public boolean isCaducada() {
        return caducada;
    }

    /**
     * @param caducada the caducada to set
     */
    public void setCaducada(boolean caducada) {
        this.caducada = caducada;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }
}
