/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.missatgeria;

import java.util.Date;
import org.iesapp.database.MyDatabase;

/**
 *
 * @author Josep
 */
public class BeanMissatge{

    protected int id;
    protected String remitent;
    protected String remitent_abrev;
    protected String destinatari;
    protected String destinatari_abrev;
    protected String nomAlumne;
    protected Date dataEntrevista;
    protected Date dataContestat;
    protected String actitud;
    protected String notes;
    protected String feina;
    protected String comentari;
    protected String grupo;
    protected String materia;
    protected int expedient;
    protected int idMateria;
    protected int idEntrevista;
    protected int idMensajeProfesor;
    protected byte[] photo;
    protected String instruccions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestinatari_abrev() {
        return destinatari_abrev;
    }

    public void setDestinatari_abrev(String destinatari_abrev) {
        this.destinatari_abrev = destinatari_abrev;
    }

    public String getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(String destinatari) {
        this.destinatari = destinatari;
    }

    public String getRemitent_abrev() {
        return remitent_abrev;
    }

    public void setRemitent_abrev(String remitent_abrev) {
        this.remitent_abrev = remitent_abrev;
    }

    public String getRemitent() {
        return remitent;
    }

    public void setRemitent(String remitent) {
        this.remitent = remitent;
    }

    public void setRebut(MyDatabase mysql) {
        String SQL1 = "Update sig_missatgeria SET dataRebut=NOW() where id=" + this.id;
        int nup = mysql.executeUpdate(SQL1);
    }

    /**
     * @return the actitud
     */
    public String getActitud() {
        return actitud;
    }

    /**
     * @param actitud the actitud to set
     */
    public void setActitud(String actitud) {
        this.actitud = actitud;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the feina
     */
    public String getFeina() {
        return feina;
    }

    /**
     * @param feina the feina to set
     */
    public void setFeina(String feina) {
        this.feina = feina;
    }

    /**
     * @return the comentari
     */
    public String getComentari() {
        return comentari;
    }

    /**
     * @param comentari the comentari to set
     */
    public void setComentari(String comentari) {
        this.comentari = comentari;
    }

    /**
     * @return the nomAlumne
     */
    public String getNomAlumne() {
        return nomAlumne;
    }

    /**
     * @param nomAlumne the nomAlumne to set
     */
    public void setNomAlumne(String nomAlumne) {
        this.nomAlumne = nomAlumne;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the materia
     */
    public String getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(String materia) {
        this.materia = materia;
    }

    /**
     * @return the expedient
     */
    public int getExpedient() {
        return expedient;
    }

    /**
     * @param expedient the expedient to set
     */
    public void setExpedient(int expedient) {
        this.expedient = expedient;
    }

    /**
     * @return the idMateria
     */
    public int getIdMateria() {
        return idMateria;
    }

    /**
     * @param idMateria the idMateria to set
     */
    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    /**
     * @return the dataEntrevista
     */
    public Date getDataEntrevista() {
        return dataEntrevista;
    }

    /**
     * @param dataEntrevista the dataEntrevista to set
     */
    public void setDataEntrevista(Date dataEntrevista) {
        this.dataEntrevista = dataEntrevista;
    }

    /**
     * @return the dataContestat
     */
    public Date getDataContestat() {
        return dataContestat;
    }

    /**
     * @param dataContestat the dataContestat to set
     */
    public void setDataContestat(Date dataContestat) {
        this.dataContestat = dataContestat;
    }

    /**
     * @return the photo
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(String instruccions) {
        this.instruccions = instruccions;
    }

    public int getIdEntrevista() {
        return idEntrevista;
    }

    public void setIdEntrevista(int idEntrevista) {
        this.idEntrevista = idEntrevista;
    }

    public int getIdMensajeProfesor() {
        return idMensajeProfesor;
    }

    public void setIdMensajeProfesor(int idMensajeProfesor) {
        this.idMensajeProfesor = idMensajeProfesor;
    }
 
    public BeanMissatge clone() {
        BeanMissatge bean = new BeanMissatge();
        bean.remitent = this.remitent;
        bean.remitent_abrev = this.remitent_abrev;
        bean.destinatari = this.destinatari;
        bean.destinatari_abrev = this.destinatari_abrev;
        bean.nomAlumne = this.nomAlumne;
        bean.dataEntrevista = this.dataEntrevista;
        bean.dataContestat = this.dataContestat;
        bean.actitud = this.actitud;
        bean.notes = this.notes;
        bean.feina = this.feina;
        bean.comentari = this.comentari;
        bean.grupo = this.grupo;
        bean.materia = this.materia;
        bean.expedient = this.expedient;
        bean.idMateria = this.idMateria;
        bean.idEntrevista = this.idEntrevista;
        bean.idMensajeProfesor = this.idMensajeProfesor;
        bean.photo = this.photo;
        bean.instruccions = this.instruccions;
        return bean;
    }
    
}
