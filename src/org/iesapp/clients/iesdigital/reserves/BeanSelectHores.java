/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.reserves;

/**
 *
 * @author Josep
 */
public class BeanSelectHores {
    
    protected int idHora=0;
    protected String hora="";
    protected String comment="Disponible";
    protected boolean triat=false;
    protected boolean disponible=true;

    /**
     * @return the idHora
     */
    public int getIdHora() {
        return idHora;
    }

    /**
     * @param idHora the idHora to set
     */
    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the triat
     */
    public boolean isTriat() {
        return triat;
    }

    /**
     * @param triat the triat to set
     */
    public void setTriat(boolean triat) {
        this.triat = triat;
    }

    /**
     * @return the disponible
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * @param disponible the disponible to set
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
            
}
