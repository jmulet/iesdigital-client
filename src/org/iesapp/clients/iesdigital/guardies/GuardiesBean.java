/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.guardies;

/**
 *
 * @author Josep
 */
public class GuardiesBean {
    protected String nombreProfesor;
    protected String idProfesor;
    protected String abrevProfesor;
    protected String zonasGuardia;

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getZonasGuardia() {
        return zonasGuardia;
    }

    public void setZonasGuardia(String zonasGuardia) {
        this.zonasGuardia = zonasGuardia;
    }

    public String getAbrevProfesor() {
        return abrevProfesor;
    }

    public void setAbrevProfesor(String abrevProfesor) {
        this.abrevProfesor = abrevProfesor;
    }
 
}
