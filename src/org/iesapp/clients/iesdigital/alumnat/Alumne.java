/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.alumnat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.util.StringUtils;

   
/**
 *
 * @author Josep
 */
public class Alumne { 

    // Splits a string xx yy, zz  as 1r lastname, 2n last name, surname
    private static String[] nameSplitter(String nomComplet) {
        String[] parts = new String[3];
        String tmp = StringUtils.BeforeLast(nomComplet, ",");
        parts[2] = StringUtils.AfterLast(nomComplet, ",");
        parts[0] = StringUtils.BeforeFirst(tmp, " ");
        parts[1] = StringUtils.AfterFirst(tmp, " ");
        return parts;
    }
    protected int expd;
    protected String llinatge1;
    protected String llinatge2;
    protected String nom1;
    protected String estudis;
    protected String lletraGrup;
    protected Grupo grupo;
    protected int nivell;
    
    public Alumne()
    {
       llinatge1 ="";
       llinatge2= "";
       nom1="";
       estudis="";
       lletraGrup="";
       nivell=0;
    }

    public static Alumne getAlumneFromIesDigital(int exp, IClient client) {
        Alumne al = new Alumne();

        String SQL1 = "SELECT * FROM `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne as xes INNER JOIN `"
                + ICoreData.core_mysqlDBPrefix + "`.xes_alumne_historic as xh "
                + " ON xes.Exp2=xh.Exp2 where xh.Exp2= '" + exp + "' AND xh.anyAcademic='"
                + client.getICoreData().anyAcademic + "'";
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);

            al.setExpd(exp);
            if (rs1 != null && rs1.next()) {
                al.setLlinatge1(rs1.getString("llinatge1"));
                al.setLlinatge2(rs1.getString("llinatge2"));
                al.setNom1(rs1.getString("nom1"));
                Grupo alGrupo = new Grupo(rs1.getString("ensenyament"), rs1.getString("estudis"), rs1.getString("grup"), client);
                al.setGrupo(alGrupo);
                al.setLletraGrup( alGrupo.grup );
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }

        } catch (SQLException exep) {
            Logger.getLogger(Alumne.class.getName()).log(Level.SEVERE, null, exep);
        }
        return al;
    }
    
    public static Alumne getAlumneFromSGD(int exp, IClient client) {
              Alumne al = new Alumne();
                 
             //Discard those alumnes that have empty matricula (maxopcion=='0')
            String SQL1 = "SELECT expediente, nombre, al.id AS idAlumnos, grup.grupoGestion, grupo, MAX(aa.opcion) AS maxopcion "
                + "FROM alumnos AS al INNER JOIN gruposalumno AS gal ON al.id=gal.idAlumnos "
                + "INNER JOIN grupos AS grup ON grup.grupoGestion=gal.grupoGestion "
                + "INNER JOIN asignaturasalumno AS aa ON aa.idAlumnos=al.id "
                + " WHERE expediente='" + exp + "'";

                try {
                    Statement st = client.getSgd().createStatement();
                    ResultSet rs1 = client.getSgd().getResultSet(SQL1,st); 
                    al.setExpd(exp);
                    if (rs1 != null && rs1.next()) {
                        String nomComplet = rs1.getString("nombre"); 
                        String[] splitName = nameSplitter(nomComplet);
                        al.setLlinatge1(splitName[0]);
                        al.setLlinatge2(splitName[1]);
                        al.setNom1(splitName[2]);
                        Grupo alGrupo = new Grupo(rs1.getString("grupo").toUpperCase(), client);
                        al.setGrupo( alGrupo );
                        al.setLletraGrup( alGrupo.grup );
                    }
                    if (rs1 != null) {
                        rs1.close();
                        st.close();
                    }

                } catch (SQLException exep) {
                    Logger.getLogger(Alumne.class.getName()).log(Level.SEVERE, null, exep);
                }
                
                return al;
    }

    public int getExpd() {
        return expd;
    }
   
    public void setExpd(int expd) {
        this.expd = expd;
    }

    public String getLlinatge1() {
        return llinatge1;
    }

    public void setLlinatge1(String llinatge1) {
        this.llinatge1 = llinatge1;
    }

    public String getLlinatge2() {
        return llinatge2;
    }

    public void setLlinatge2(String llinatge2) {
        this.llinatge2 = llinatge2;
    }

    public String getNom1() {
        return nom1;
    }

    public void setNom1(String nom1) {
        this.nom1 = nom1;
    }

    public String getEstudis() {
        return estudis;
    }

    public void setEstudis(String estudis) {
        this.estudis = estudis;
    }

    public String getLletraGrup() {
        return lletraGrup;
    }

    public void setLletraGrup(String lletraGrup) {
        this.lletraGrup = lletraGrup;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public int getNivell() {
        return nivell;
    }

    public void setNivell(int nivell) {
        this.nivell = nivell;
    }

    public String getNomComplet() {
        return this.llinatge1+" "+this.llinatge2+", "+this.nom1;
    }
    
}
