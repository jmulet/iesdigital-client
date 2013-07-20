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
@DefaultTableMapping(tableName="sig_horaris")
public class BeanHorari {
    @PK
    @TableMapping
    protected int id = 0;
    @TableMapping
    protected String prof = "";
    @TableMapping
    protected String asig = "";
    @TableMapping
    protected String curso = "0";
    @TableMapping
    protected String nivel = "";
    @TableMapping
    protected String grupo = "";
    @TableMapping
    protected String aula = "";
    @TableMapping
    protected int dia = 0;
    @TableMapping
    protected int hora = 0; 
    @TableMapping(tableName="sig_professorat", mode="r")
    protected String nombre = "";
    
    @Override
    public String toString()
    {
         return "BeanHorari: id="+this.getId()+"; prof="+this.prof+"; nombre="+nombre+"; asig="+
                this.asig+"; curso="+this.getCurso()+"; nivel="+this.nivel+"; grupo="+grupo+"; aula="+
                this.aula+"; dia="+dia+"; hora="+hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getAsig() {
        return asig;
    }

    public void setAsig(String asig) {
        this.asig = asig;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
}
