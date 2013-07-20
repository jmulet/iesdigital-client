/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.actuacions;

import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 *
 * Defineix l'estructura d'una actuacio
 *
 */
public class Actuacio {

    public java.awt.Point position;
    public boolean modal = false;
    public int exp2;
    public int id_actuacio;
    public int id_rule;
    public HashMap<String, Object> map;
    public boolean locked;
    public boolean admin;
    public boolean nova;
    public String ensenyament = "";
    public String estudis = "";
    public BeanRules beanRule;
    public String user;
    public String creador;
    public String resolucio = "";
    public java.util.Date data1;
    public java.util.Date data2;
    public static HashMap<String, Object> resourceMap;
    public ArrayList<Integer> idFaltasAlumnos;
    private final IClient client;

    public Actuacio(final IClient client) {
        this.client = client;
    }

    public Actuacio(String user, Point p, boolean modal, int expedient, int idAct, int idRule,
            boolean locked, boolean admin, String ensenyament, String estudis, HashMap<String, Object> resourceMap,
            final IClient client) {
        this.client = client;
        Actuacio.resourceMap = resourceMap;
        this.user = user;
        this.position = p;
        this.modal = modal;
        this.exp2 = expedient;
        this.id_actuacio = idAct;
        this.id_rule = idRule;
        this.locked = locked;
        this.admin = admin;
        this.nova = idAct <= 0;
        this.estudis = estudis;
        this.beanRule = client.getFitxesClient().getFactoryRules().getRuleXml(idRule, ensenyament, estudis); //A partir d'ara s'obte de l'xml
        this.map = client.getFitxesClient().getFactoryRules().convertToMapDefaultFill(beanRule.getFields());
        this.idFaltasAlumnos = loadIdsFaltasAlumnos();

        this.creador = user;
        //Overwrite with database data if already exists
        if (!nova) {
            String SQL1 = "SELECT data1, data2, resolucio, iniciatper, document FROM tuta_reg_actuacions WHERE id=" + this.id_actuacio;
            try{
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            
                if (rs1 != null && rs1.next()) {
                    creador = rs1.getString("iniciatper");
                    HashMap<String, Object> tmpMap = StringUtils.StringToHash(rs1.getString("document"), ";");
                    for (String key : tmpMap.keySet()) {
                        map.put(key, tmpMap.get(key));
                    }
                    tmpMap.clear();
                    data1 = rs1.getDate("data1");
                    data2 = rs1.getDate("data2");
                    resolucio = rs1.getString("resolucio");
                    //System.out.println("Ini map:" + map);
                }
                if (rs1 != null) {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Actuacio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void save() {
        if (!exists()) {
            doInsert();
        } else {
            doUpdate();
        }
    }

    public boolean exists() {
        if (this.id_actuacio <= 0) {
            return false;
        }
        return true;

    }

    private void doUpdate() {
        String SQL1;
        Object[] values;
        if (data2 == null) {
            SQL1 = "UPDATE tuta_reg_actuacions SET document=?, data2=NULL, resolucio=? WHERE id=?";
            values = new Object[]{StringUtils.HashToString(map, ";"), resolucio, id_actuacio};
        } else {
            SQL1 = "UPDATE tuta_reg_actuacions SET document=?, data2=?, resolucio=? WHERE id=?";
            values = new Object[]{StringUtils.HashToString(map, ";"), data2, resolucio, id_actuacio};
        }

        int nup = client.getMysql().preparedUpdate(SQL1, values);
    }

    private void doInsert() {
        String SQL1;
        Object[] values;
        if (beanRule.autoTancar) //determina si s'auto-tanca quan es crea
        {
            SQL1 = "INSERT INTO tuta_reg_actuacions (iniciatper, exp2, data1, data2, idActuacio, document) "
                    + " VALUES(?,?,CURRENT_DATE(),CURRENT_DATE(),?,?)";
        } else {
            SQL1 = "INSERT INTO tuta_reg_actuacions (iniciatper, exp2, data1, idActuacio, document) "
                    + " VALUES(?,?,CURRENT_DATE(),?,?)";
        }
        values = new Object[]{user, exp2, id_rule, StringUtils.HashToString(map, ";")};
        this.id_actuacio = client.getMysql().preparedUpdateID(SQL1, values);
    }

    public void delete(boolean safeDelete) {
        if (!exists()) {
            return;
        }

        if (safeDelete) {
            //Farem un safe delete que consisteix en moure l'actuacio dins d'una taula "TRASH"
            String SQL1 = "UPDATE tuta_reg_actuacions SET iniciatper='" + creador + "; " + user + "' WHERE id=" + id_actuacio + " LIMIT 1";
            int nup = client.getMysql().executeUpdate(SQL1);

            SQL1 = "INSERT INTO tuta_reg_actuacions_deleted (SELECT * FROM tuta_reg_actuacions "
                    + " WHERE id=" + id_actuacio + ")";
            nup = client.getMysql().executeUpdate(SQL1);
        }

        String SQL1 = "DELETE FROM tuta_reg_actuacions WHERE id=" + id_actuacio + " LIMIT 1";
        int nup = client.getMysql().executeUpdate(SQL1);
        if (nup > 0) {
            id_actuacio = -1;
        }

    }

    public HashMap<String, Object> cloneMap() {
        HashMap<String, Object> cloned = new HashMap<String, Object>();
        for (String ky : map.keySet()) {
            cloned.put(ky, map.get(ky));
        }
        return cloned;
    }

    @Override
    public String toString() {

        StringBuilder tmp = new StringBuilder();
        tmp.append("ESTRUCTURA DE LA MESURA:\n");
        tmp.append("idRule=").append(this.beanRule.getIdRule()).append(", descripcio=").append(this.beanRule.descripcio);
        tmp.append("renderClass=").append(this.beanRule.getClassName());

        tmp.append("MAPA=").append(this.map.toString()).append("\n");
        tmp.append("FIELDS=").append(this.beanRule.fields.toString()).append("\n");

        return tmp.toString();
    }

    
    public static void clearRegisterIesDigital(int id_actuacio, MyDatabase mysql)
    {
        String SQL2 = "DELETE FROM tuta_dies_sancions WHERE idActuacio="+id_actuacio;
        int nup = mysql.executeUpdate(SQL2);
    }

    private ArrayList<Integer> loadIdsFaltasAlumnos() {
        
           ArrayList<Integer> list = new ArrayList<Integer>();
           if(this.nova) {
            return list;
          }
           
           String SQL1 = "SELECT idSgd FROM tuta_incidenciessgd WHERE idCas="+this.id_actuacio;
           try{
           Statement st = client.getMysql().createStatement();
           ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
          
                while (rs1 != null && rs1.next()) {
                    list.add(rs1.getInt(1));
                }
                if (rs1 != null) {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Actuacio.class.getName()).log(Level.SEVERE, null, ex);
            }
        
     return list;
    }
    
   
}
