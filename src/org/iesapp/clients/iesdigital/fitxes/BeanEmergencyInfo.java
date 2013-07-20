/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.fitxes;

/**
 *
 * @author Josep
 */
public class BeanEmergencyInfo{


    private String asig;
    private String profesor;
    private String data;
    private String descripcio;
    private float nota;
    
    private int al;     //lleu
    private int alh;    //lleu-historica
    private int ag;     //greu
    private int fa;     //falta
    private int fj;     //falta-justificada
    private int re;     //retard
    private int rj;     //retard-justificat
    private int pa;     //pati
    private int di;     //dimecres
    private int cp;     //comentari-positu
    private int cn;     //comentari-negatiu

    private String alumno="";
    private String grupo="";
    private String tutor="";



    public int getCn() {
        return cn;
    }

    public void setCn(int cn) {
        this.cn = cn;
    }


    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    
    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }


    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }


    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }


    public int getPa() {
        return pa;
    }

    public void setPa(int pa) {
        this.pa = pa;
    }


    public int getRj() {
        return rj;
    }

    public void setRj(int rj) {
        this.rj = rj;
    }

    public int getDi() {
        return di;
    }

    public void setDi(int di) {
        this.di = di;
    }


    public int getAlh() {
        return alh;
    }

    public void setAlh(int alh) {
        this.alh = alh;
    }


    public int getRe() {
        return re;
    }

    public void setRe(int re) {
        this.re = re;
    }


    public int getAg() {
        return ag;
    }

    public void setAg(int ag) {
        this.ag = ag;
    }


    public int getFj() {
        return fj;
    }

    public void setFj(int fj) {
        this.fj = fj;
    }


    public int getFa() {
        return fa;
    }

    public void setFa(int fa) {
        this.fa = fa;
    }


    public int getAl() {
        return al;
    }

    public void setAl(int al) {
        this.al = al;
    }

    

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }


    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }


    public String getAsig() {
        return asig;
    }

    public void setAsig(String asig) {
        this.asig = asig;
    }


}
