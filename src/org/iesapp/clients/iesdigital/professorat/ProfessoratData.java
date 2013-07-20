/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.professorat;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;

/**
 *
 * @author Josep
 */
public class ProfessoratData {

    private LinkedHashMap<String, String> abrev2prof;
    private final IClient client;
    private LinkedHashMap<String, BeanProfessor> mapProfs;
    
    //Full utility class. Please use iesClient facade to prevent multiple instances
    public ProfessoratData(IClient client) {
        this.client = client;
    }

    public String getAbrev(int any, int idProfe) {
        String abrev = "";
        String dbName = ICoreData.core_mysqlDBPrefix+any;
        String SQL1 = "SELECT * FROM "+dbName+".sig_professorat WHERE idSGD=" + idProfe;
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            if (rs1 != null && rs1.next()) {
                abrev = rs1.getString("abrev");
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessoratData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return abrev;
  
    }
    
    /**
     * By default uses current year
     * @param idProfe
     * @return 
     */
    public String getAbrev(int idProfe) {
        return getAbrev(client.getICoreData().anyAcademic, idProfe);
    }
    
    public String getNomByAbrev(String abrev) {
        String nom = "";
        String SQL1 = "Select nombre from sig_professorat where abrev='" + abrev + "'";
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            if (rs1 != null && rs1.next()) {
                nom = rs1.getString("nombre");
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessoratData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nom;
    }

    public int getIdSgd(String abrev) {
        int id = -1;
        String SQL1 = "Select idSGD from sig_professorat where abrev='" + abrev + "'";

        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            if (rs1 != null && rs1.next()) {
                id = rs1.getInt("idSGD");
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessoratData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * Returns a linkedmap which has as keys [ABREV] and values[NAME OF TEACHERS]
     * @return 
     */
    public LinkedHashMap<String, String> getMapAbrev() {
        if (abrev2prof != null) {
            return abrev2prof;
        }
        //create abrev2prof map
        abrev2prof = new LinkedHashMap<String, String>();

        try {

            String SQL1 = "SELECT * FROM sig_professorat ORDER BY NOMBRE";
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            while (rs1 != null && rs1.next()) {
                String nombre = rs1.getString("NOMBRE");
                String abrev = rs1.getString("ABREV");
//                int turno = rs1.getInt("TORN");
//                int sgdID = rs1.getInt("idSGD");

                abrev2prof.put(abrev, nombre);
                //abrev2sgdID.put(abrev, sgdID);
                //torn2prof.put(abrev, turno);
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(ProfessoratData.class.getName()).log(Level.SEVERE, null, ex);
        }

        // abrev2prof = getSortedMap(abrev2prof); //ordena per llinatge

        return abrev2prof;
    }

    //
// Ordena un mapa segons els seus valors
//
    public static HashMap getSortedMap(HashMap hmap) {
        HashMap map = new LinkedHashMap();
        List mapKeys = new ArrayList(hmap.keySet());
        List mapValues = new ArrayList(hmap.values());
        hmap.clear();
        TreeSet sortedSet = new TreeSet(mapValues);
        Object[] sortedArray = sortedSet.toArray();
        int size = sortedArray.length;
        // a) Ascending sort

        for (int i = 0; i < size; i++) {

            map.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), sortedArray[i]);

        }
        return map;
    }
    
    //List all professors
    public ArrayList<BeanProfessor> listProfessors(String condition)
    {
        return new Professor(client).getCrud().list(condition);
    }
    
    //Convert list to HashMap (is a single instance)
    public LinkedHashMap<String, BeanProfessor> getMapProf()
    {
        if(mapProfs == null)
        {
            mapProfs = new LinkedHashMap<String, BeanProfessor> ();
            ArrayList<BeanProfessor> list = new Professor(client).getCrud().list("1=1 ORDER BY nombre"); 
            for(BeanProfessor p: list)
            {
                mapProfs.put(p.getAbrev(), p);
            }
             //Add internal users in order to resolve names
             mapProfs.put("ADMIN", BeanProfessor.ADMINISTRADOR);
             mapProfs.put("GUARD", BeanProfessor.GUARDIES);
            list.clear();
            list = null;
        }
            
        return mapProfs;
    }
   
    
    public void dispose()
    {
        mapProfs = null; 
    }

  
}
