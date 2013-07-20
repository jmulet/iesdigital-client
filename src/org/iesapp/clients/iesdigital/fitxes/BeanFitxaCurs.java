/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.fitxes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class BeanFitxaCurs {

    private int exp_FK_ID=0;
    private String idCurs_FK_ID="";
    private String grup="";
    private String any_academic="";
    private String nivell="";
    private String professor="";
    private String observacions="";
    private int deriva_ORI=0;
    private String motiuDerivacioORI="";
    private int numMateriesSuspesesJuny=0;
    private double notaMitjanaFinal=0;
    private String curs="";
    private int NExpDisciplina=0;
    private String sancions="";

    private String programes="";
    private String dataCreacio="";
    private String dataModificacio="";
    private String modificat="";

    //nou parametre per a camps importats
    protected HashMap<String, SgdInc> importacioSGD = new HashMap<String, SgdInc>();
    private final IClient client;

    public static int getChanges(BeanFitxaCurs bean, BeanFitxaCurs bean2) {
        int nchanges = 0;

        if( !(bean.curs).equals(bean2.curs) ) {
            nchanges += 1;
        }
        if( !(bean.grup).equals(bean2.grup) ) {
            nchanges += 1;
        }
        if( !(bean.any_academic).equals(bean2.any_academic) ) {
            nchanges += 1;
        }
        if( !(bean.nivell).equals(bean2.nivell) ) {
            nchanges += 1;
        }
        if( !(bean.professor).equals(bean2.professor) ) {
            nchanges += 1;
        }
        if( !(bean.observacions).equals(bean2.observacions) ) {
            nchanges += 1;
        }
        if( !(bean.deriva_ORI==bean2.deriva_ORI) ) {
            nchanges += 1;
        }
        if( !(bean.motiuDerivacioORI).equals(bean2.motiuDerivacioORI) ) {
            nchanges += 1;
        }
        if( !(bean.numMateriesSuspesesJuny==bean2.numMateriesSuspesesJuny) ) {
            nchanges += 1;
        }
        if( Math.abs(bean.notaMitjanaFinal-bean2.notaMitjanaFinal)>0.1 ) {
            nchanges += 1;
        }
        
        
        //if( !(bean.NExpDisciplina==bean2.NExpDisciplina) ) nchanges += 1;

        if( !(bean.sancions).equals(bean2.sancions) ) {
            nchanges += 1;
        }
        if( !(bean.programes).equals(bean2.programes) ) {
            nchanges += 1;
        }

         
        return nchanges;
    }
   

    public BeanFitxaCurs(IClient client)
    {
        this.client = client;
    }

 
    public void getFromDB(int nexp, String anyAcademic)
    {

        ResultSet rs1;
        String SQL1 = "select * from `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs where Exp_FK_ID="+nexp+
                " AND Any_academic='"+anyAcademic.trim()+"'";
        
        try {
            Statement st = client.getMysql().createStatement();
            rs1 = client.getMysql().getResultSet(SQL1,st);
            while (rs1.next()) {

                // CARREGA AQUI LES PROPIETATS DE LA BASE DE DADES

            exp_FK_ID = rs1.getInt("Exp_FK_ID");
            idCurs_FK_ID = rs1.getString("IdCurs_FK_ID");
            curs = rs1.getString("Estudis");
            grup=rs1.getString("Grup");
            any_academic=rs1.getString("Any_academic");
            nivell=rs1.getString("Ensenyament");
            professor=rs1.getString("Professor");
            observacions=rs1.getString("Observacions");
            //cal llevar els \n
            observacions = observacions.replaceAll("\n", "\\\n");
            deriva_ORI=rs1.getInt("DerivatORI");
            motiuDerivacioORI=rs1.getString("MotiuDerivacioORI");
            motiuDerivacioORI = motiuDerivacioORI.replaceAll("\n", "\\\n");

            numMateriesSuspesesJuny=rs1.getInt("NumMateriesSuspJuny");
            notaMitjanaFinal=rs1.getFloat("NotaMitjaFinal");
            notaMitjanaFinal = (double)Math.round(notaMitjanaFinal * 10) / 10;

            sancions = rs1.getString("Sancions");
            NExpDisciplina = rs1.getInt("NExpDisciplina");
            programes = rs1.getString("Programes");

            dataCreacio = StringUtils.noNull(rs1.getString("DataCreacio"));
            modificat = StringUtils.noNull(rs1.getString("Modificat"));
            dataModificacio = StringUtils.noNull(rs1.getString("DataModificacio"));

            }
            if(rs1!=null)
            {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Load imported data
        importacioSGD = client.getFitxesClient().getSGDImporterConfig().getLocalValuesOf(exp_FK_ID, idCurs_FK_ID);

    }


    public void commitToDB(int nexp)
    {
        String SQL1 = "UPDATE `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs SET "
                    + "Estudis=?,  "
                    + "Grup=?,  "
                    + "Any_academic=?,  "
                    + "Ensenyament=?,  "
                    + "Professor=?,  "
                    + "Observacions=?,  "
                    + "DerivatORI=?,  "
                    + "MotiuDerivacioORI=?,  "
                    + "NumMateriesSuspJuny=?,  "
                    + "NotaMitjaFinal=?,  "
                    + "Sancions=?,   "
                    + "NExpDisciplina=?, "
                    + "Programes=?, "
                    + "Modificat=?, "
                    + "DataModificacio=? "
                    + " WHERE Exp_FK_ID=" + nexp
                    + " AND Any_academic='"+ any_academic +"'";

        Object[] updatedValues = new Object[]{curs, grup, any_academic, nivell,
                professor, observacions, deriva_ORI, motiuDerivacioORI,
                numMateriesSuspesesJuny, notaMitjanaFinal, 
                sancions, NExpDisciplina, programes, modificat, dataModificacio};

        ////System.out.println(programes+ modificat+ dataModificacio);

        int nup = client.getMysql().preparedUpdate(SQL1, updatedValues);
        //////System.out.println( "nup="+nup+" ; "+SQL1);

    }


    public void deleteFromDB()
    {
        //Farem un safe delete que consisteix en moure la fitxa dins d'una taula "TRASH"
        String SQL1 = "INSERT INTO `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_deleted (SELECT * FROM `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs "
                + " WHERE Exp_FK_ID='"+exp_FK_ID+"' "
                +" AND Any_academic='"+ any_academic +"')";
        int nup = client.getMysql().executeUpdate(SQL1);


        //si s'ha copiat, podem esborrar
        if(nup>0)
        {
                SQL1 = "DELETE FROM `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs WHERE Exp_FK_ID='"+exp_FK_ID+"' "
                        +" AND Any_academic='"+ any_academic +"'";
                nup = client.getMysql().executeUpdate(SQL1);
        }


    }

    public static ArrayList<Integer> getAnys(int nexp, MyDatabase mysql)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        ResultSet rs1 = null;
        String SQL1 = "SELECT IdCurs_FK_ID FROM `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs where Exp_FK_ID="+nexp;
        
        try {
            Statement st = mysql.createStatement();
            rs1 = mysql.getResultSet(SQL1,st);
            while (rs1!=null && rs1.next()) {
               list.add( rs1.getInt(1) );
            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(BeanFitxaCurs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    
    public String getModificat() {
        return modificat;
    }

    public void setModificat(String modificat) {
        this.modificat = modificat;
    }


    public String getDataModificacio() {
        return dataModificacio;
    }

    public void setDataModificacio(String dataModificacio) {
        this.dataModificacio = dataModificacio;
    }


    public String getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(String dataCreacio) {
        this.dataCreacio = dataCreacio;
    }



    public String getProgrames() {
        return programes;
    }

    public void setProgrames(String programes) {
        this.programes = programes;
    }

    public String getSancions() {
        return sancions;
    }

    public void setSancions(String sancions) {
        this.sancions = sancions;
    }

    public int getNExpDisciplina() {
        return NExpDisciplina;
    }

    public void setNExpDisciplina(int NExpDisciplina) {
        this.NExpDisciplina = NExpDisciplina;
    }



    public String getCurs() {
        return curs;
    }

    public void setCurs(String curs) {
        this.curs = curs;
    }


    public double getNotaMitjanaFinal() {
        return notaMitjanaFinal;
    }

    public void setNotaMitjanaFinal(double notaMitjanaFinal) {
        this.notaMitjanaFinal = notaMitjanaFinal;
    }


    public int getNumMateriesSuspesesJuny() {
        return numMateriesSuspesesJuny;
    }

    public void setNumMateriesSuspesesJuny(int numMateriesSuspesesJuny) {
        this.numMateriesSuspesesJuny = numMateriesSuspesesJuny;
    }
    public String getMotiuDerivacioORI() {
        return motiuDerivacioORI;
    }

    public void setMotiuDerivacioORI(String motiuDerivacioORI) {
        this.motiuDerivacioORI = motiuDerivacioORI;
    }


    public int getDeriva_ORI() {
        return deriva_ORI;
    }

    public void setDeriva_ORI(int deriva_ORI) {
        this.deriva_ORI = deriva_ORI;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }


    public String getNivell() {
        return nivell;
    }

    public void setNivell(String nivell) {
        this.nivell = nivell;
    }

    public String getAny_academic() {
        return any_academic;
    }

    public void setAny_academic(String any_academic) {
        this.any_academic = any_academic;
    }



    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }

 
    public String getIdCurs_FK_ID() {
        return idCurs_FK_ID;
    }

    public void setIdCurs_FK_ID(String idCurs_FK_ID) {
        this.idCurs_FK_ID = idCurs_FK_ID;
    }

    public int getExp_FK_ID() {
        return exp_FK_ID;
    }

    public void setExp_FK_ID(int exp_FK_ID) {
        this.exp_FK_ID = exp_FK_ID;
    }

    public HashMap<String, SgdInc> getImportacioSGD() {
        return importacioSGD;
    }

}
