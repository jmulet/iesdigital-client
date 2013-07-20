/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.professorat;

import org.iesapp.database.vscrud.DefaultTableMapping;
import org.iesapp.database.vscrud.PK;
import org.iesapp.database.vscrud.TableMapping;

/**
 *
 * @author Josep
 */
@DefaultTableMapping(tableName="sig_professorat")
public class BeanProfessor {
    public static final BeanProfessor ADMINISTRADOR;
    public static BeanProfessor GUARDIES;
    static{
        ADMINISTRADOR = new BeanProfessor();
        ADMINISTRADOR.abrev = "ADMIN";
        ADMINISTRADOR.nombre = "ADMINISTRADOR";
        GUARDIES = new BeanProfessor();
        GUARDIES.abrev = "GUARD";
        GUARDIES.nombre = "USUARI DE GUARDIES";
    }
    
    @PK
    @TableMapping
    protected int id;
    @TableMapping
    protected String nombre;
    @TableMapping
    protected String abrev;
    @TableMapping
    protected String depart;
    @TableMapping
    protected int torn;
    @TableMapping
    protected int idSGD;
    
    @PK
    @TableMapping(tableName="usu_usuari", tableField="IdUsuari")
    protected long id_usua;
    @TableMapping(tableName="usu_usuari", tableField="Contrasenya")
    protected String contrasenya;
    @TableMapping(tableName="usu_usuari", tableField="GrupFitxes")
    protected String role;
    @TableMapping(tableName="usu_usuari")
    protected boolean bloquejat;
    @TableMapping(tableName="usu_usuari") 
    protected String preferences_pdaweb;
    
    
    @Override
    public String toString()
    {
        return "id="+getId()+"; nombre="+getNombre()+"; abrev="+getAbrev()+"; depart="+getDepart()+"; torn="
                +getTorn()+"; idSGD="+getIdSGD()+"; id_usua="+getId_usua()+"; contrasenya="+getContrasenya()+"; role="
                +getRole()+"; bloquejat="+getBloquejat()+"; preferences_pdaweb="+getPreferences_pdaweb();    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public int getTorn() {
        return torn;
    }

    public void setTorn(int torn) {
        this.torn = torn;
    }

    public int getIdSGD() {
        return idSGD;
    }

    public void setIdSGD(int idSGD) {
        this.idSGD = idSGD;
    }

    public long getId_usua() {
        return id_usua;
    }

    public void setId_usua(long id_usua) {
        this.id_usua = id_usua;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getBloquejat() {
        return bloquejat;
    }

    public void setBloquejat(boolean bloquejat) {
        this.bloquejat = bloquejat;
    }

    public String getPreferences_pdaweb() {
        return preferences_pdaweb;
    }

    public void setPreferences_pdaweb(String preferences_pdaweb) {
        this.preferences_pdaweb = preferences_pdaweb;
    }
    
}
