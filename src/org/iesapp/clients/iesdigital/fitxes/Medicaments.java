/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.util.StringUtils;
 
/**
 *
 * @author Josep
 * 
 */
public class Medicaments {
    private final IClient client;
    
    public Medicaments(final IClient client){
        this.client = client;
    }
    
    public ArrayList<BeanMedicamentsSubministrats> listSubministraments() {

        StringBuilder SQL1 = new StringBuilder();
        SQL1.append("SELECT mreg.exp2, CONCAT(xes.Llinatge1,' ',xes.Llinatge2,', ',xes.Nom1) AS nom, ");
        SQL1.append("CONCAT(xh.Estudis, ' ', xh.Grup) AS grup, TIME(mreg.DATA) AS hora, DATE_FORMAT(DATE(mreg.DATA),'%d-%m-%Y') AS dia,");
        SQL1.append(" mreg.idAutoritzat, med.descripcio AS presa, mreg.observacions FROM ");
        SQL1.append(" sig_medicaments_reg AS mreg INNER JOIN `").append(ICoreData.core_mysqlDBPrefix).append("`.`xes_alumne` AS xes ");
        SQL1.append(" ON xes.Exp2 = mreg.exp2 INNER JOIN `").append(ICoreData.core_mysqlDBPrefix).append("`.`xes_alumne_historic` AS xh ");
        SQL1.append(" ON xh.Exp2 = mreg.exp2 AND xh.AnyAcademic = '").append(client.getICoreData().anyAcademic).append("' INNER JOIN ");
        SQL1.append(" `").append(ICoreData.core_mysqlDBPrefix).append("`.`sig_medicaments` AS med ON med.id = mreg.idMedicament ");
        SQL1.append(" ORDER BY grup, nom, mreg.DATA ");

        
        ArrayList<BeanMedicamentsSubministrats> list = new ArrayList<BeanMedicamentsSubministrats>();

        int row = 2;

        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1.toString(), st);
            while (rs1 != null && rs1.next()) {
                BeanMedicamentsSubministrats bean = new BeanMedicamentsSubministrats();
                bean.setExpd(rs1.getInt("exp2"));
                bean.setNomcomplet(rs1.getString("nom"));
                bean.setGrup(rs1.getString("grup"));
                bean.setAutoritzats(rs1.getString("idAutoritzat"));
                bean.setObservacions(StringUtils.noNull(rs1.getString("observacions")));
                bean.setPresa(rs1.getString("presa"));
                bean.setHora(rs1.getString("hora"));
                bean.setDia(rs1.getString("dia"));

                list.add(bean);
            }

            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medicaments.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    
        
      public ArrayList<BeanMedicamentsResum> listSubministramentsResum() {

        ArrayList<BeanMedicamentsResum> list2 = new ArrayList<BeanMedicamentsResum>();
        //Construeix el subreport resum
        String SQL2 = "SELECT med.id, med.descripcio, COUNT(reg.id) AS total FROM "
                + "`" + ICoreData.core_mysqlDBPrefix + "`.sig_medicaments AS med LEFT JOIN "
                + " sig_medicaments_reg AS reg ON reg.idMedicament=med.id GROUP BY descripcio";


        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs2 = client.getMysql().getResultSet(SQL2, st);
            while (rs2 != null && rs2.next()) {
                BeanMedicamentsResum bean = new BeanMedicamentsResum();
                bean.id = rs2.getInt(1);
                bean.medicament = rs2.getString(2);
                bean.total = rs2.getInt(3);
                list2.add(bean);
            }
            if (rs2 != null) {
                rs2.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medicaments.class.getName()).log(Level.SEVERE, null, ex);
        }


        return list2;
    }
      
      
      

    public ArrayList<BeanMedicamentsAutoritzats> listInformeAutoritzats() {
        StringBuilder SQL1 = new StringBuilder("SELECT xh.exp2, xh.AnyAcademic, CONCAT(llinatge1,' ',llinatge2,', ',nom1) AS nom, CONCAT(xh.Estudis,' ', xh.Grup) AS grup,");
        SQL1.append(" GROUP_CONCAT(IF(med.descripcio IS NULL,'', med.descripcio) SEPARATOR ',') AS medicaments, IF(obv.observacions IS NULL, '', obv.observacions) as observ FROM ");
        SQL1.append(ICoreData.core_mysqlDBPrefix).append(".xes_alumne_historic AS xh INNER JOIN ").append(ICoreData.core_mysqlDBPrefix ).append(".xes_alumne AS xes ");
        SQL1.append("ON xes.exp2=xh.Exp2 AND xh.AnyAcademic='").append(client.getICoreData().anyAcademic).append("' ");
        SQL1.append("LEFT JOIN ").append(ICoreData.core_mysqlDBPrefix).append(".sig_medicaments_alumnes AS ma ON ma.exp2=xh.Exp2 ");
        SQL1.append("LEFT JOIN ").append(ICoreData.core_mysqlDBPrefix).append(".sig_medicaments AS med ON med.id=ma.idMedicament ");
        SQL1.append("LEFT JOIN ").append(ICoreData.core_mysqlDBPrefix).append(".sig_medicaments_observ AS obv ON obv.exp2=xh.Exp2 ");
        SQL1.append("GROUP BY xh.exp2 ORDER BY grup, nom");
        
        
        //System.out.println("SQL1-->"+SQL1.toString());

        ArrayList<BeanMedicamentsAutoritzats> list = new ArrayList<BeanMedicamentsAutoritzats>();

        int row = 2;
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1.toString(), st);
            while (rs1 != null && rs1.next()) {
                BeanMedicamentsAutoritzats bean = new BeanMedicamentsAutoritzats();
                bean.setExpd(rs1.getInt("exp2"));
                bean.setNomcomplet(rs1.getString("nom"));
                bean.setGrup(rs1.getString("grup"));
                bean.setAutoritzats(rs1.getString("medicaments"));
                bean.setObservacions(rs1.getString("observ"));

                list.add(bean);
            }

            if (rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medicaments.class.getName()).log(Level.SEVERE, null, ex);
        }     
 
        return list;
      
    }
    
    
}
