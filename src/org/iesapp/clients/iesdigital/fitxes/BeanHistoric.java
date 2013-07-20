/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;

/**
 *
 * @author Josep
 */
public class BeanHistoric {

    protected int exp2;
    protected String anyAcademic;
    protected String ensenyament;
    protected String estudis;
    protected String grup;
    protected String profTutor;
    private final IClient client;
     
    public BeanHistoric(int exp2, String year, IClient client)
    {
        this.client = client;
        load(exp2,year);
    }
    
    private void load(int exp2, String year)
    {
        this.setExp2(exp2);
        this.setAnyAcademic(year);
        String SQL1 = "SELECT * FROM "+ICoreData.core_mysqlDBPrefix+".xes_alumne_historic WHERE Exp2="+exp2+" AND "+
                " AnyAcademic='"+year+"'";
        try {
        Statement st = client.getMysql().createStatement();
        ResultSet rs1= client.getMysql().getResultSet(SQL1,st);
        
            while(rs1!=null && rs1.next())
            {
                this.setEnsenyament(rs1.getString("Ensenyament"));
                this.setEstudis(rs1.getString("Estudis"));
                this.setGrup(rs1.getString("Grup"));
                this.setProfTutor(rs1.getString("ProfTutor"));                
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanHistoric.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * @return the exp2
     */
    public int getExp2() {
        return exp2;
    }

    /**
     * @param exp2 the exp2 to set
     */
    public void setExp2(int exp2) {
        this.exp2 = exp2;
    }

    /**
     * @return the anyAcademic
     */
    public String getAnyAcademic() {
        return anyAcademic;
    }

    /**
     * @param anyAcademic the anyAcademic to set
     */
    public void setAnyAcademic(String anyAcademic) {
        this.anyAcademic = anyAcademic;
    }

    /**
     * @return the ensenyament
     */
    public String getEnsenyament() {
        return ensenyament;
    }

    /**
     * @param ensenyament the ensenyament to set
     */
    public void setEnsenyament(String ensenyament) {
        this.ensenyament = ensenyament;
    }

    /**
     * @return the estudis
     */
    public String getEstudis() {
        return estudis;
    }

    /**
     * @param estudis the estudis to set
     */
    public void setEstudis(String estudis) {
        this.estudis = estudis;
    }

    /**
     * @return the grup
     */
    public String getGrup() {
        return grup;
    }

    /**
     * @param grup the grup to set
     */
    public void setGrup(String grup) {
        this.grup = grup;
    }

    /**
     * @return the profTutor
     */
    public String getProfTutor() {
        return profTutor;
    }

    /**
     * @param profTutor the profTutor to set
     */
    public void setProfTutor(String profTutor) {
        this.profTutor = profTutor;
    }
}
