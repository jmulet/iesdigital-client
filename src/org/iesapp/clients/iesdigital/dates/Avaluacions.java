/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.dates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class Avaluacions {

    protected int idAval = -1;
    protected String estudis = "*";
    protected String ensenyament = "*";
    private HashMap<String,java.util.Date> fechaInicios = new HashMap<String,java.util.Date>();
    private HashMap<String,java.util.Date> fechaFins = new HashMap<String,java.util.Date>();
    protected HashMap<String,Integer> ids = new HashMap<String,Integer>();
    private final IClient client;
    private String dbName_ies;
    private String dbName_sgd;
    private final int any;
     
    public Avaluacions(IClient client)
    {
        this.client = client;
        this.any = client.getICoreData().anyAcademic;
        initialize(any);
    }
    public Avaluacions(int any, IClient client)
    {
        this.client = client;
        this.any = any;
        initialize(any);
    }
  
    private void initialize(int any)
    {
        ids.put("1", -1);
        ids.put("2", -1);
        ids.put("3", -1);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, any);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH,1);
        fechaInicios.put("1", cal.getTime());
        
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH,15);
        fechaFins.put("1", cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        fechaInicios.put("2", cal.getTime());
        
        cal.set(Calendar.YEAR, any+1);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH,10);
        fechaFins.put("2", cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        fechaInicios.put("3", cal.getTime());
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH,30);       
        fechaFins.put("3", cal.getTime()); 
        
        //Diferent cases
        dbName_ies = ICoreData.core_mysqlDBPrefix+any;
        dbName_sgd = (String) ICoreData.configTableMap.get("sgdDBPrefix") +any;       
    }
    
    public String getWhereClause()
    {
        StringBuilder sbuilder = new StringBuilder();
        if(!ensenyament.contains("*") && !ensenyament.isEmpty())
        {
            String str = getEnsenyament().replaceAll(",","','");
            sbuilder.append(" ensenyament IN ('").append(str).append("') ");
        }
        if(!estudis.contains("*") && !estudis.isEmpty())
        {
            if(sbuilder.length()>0)
            {
                sbuilder.append(" AND ");
            }
            String str = getEstudis().replaceAll(",","','");
            sbuilder.append(" estudis IN ('").append(str).append("') ");
        }
        return sbuilder.toString();
    }
    
    public String getListExpds()
    {
        String whereClause = this.getWhereClause();
        
        if(!whereClause.trim().isEmpty())
        {
            whereClause = " AND "+whereClause;
        }
        String SQL1= "SELECT exp2 FROM "+ICoreData.core_mysqlDBPrefix+
                ".xes_alumne_historic WHERE AnyAcademic='"+
                any+"' "+whereClause;
        String result = "'";
         try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                 result += rs1.getInt(1)+"','";
            }
            if(rs1!=null)
            {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Avaluacions.class.getName()).log(Level.SEVERE, null, ex);
        }
        result += "'";
        return result;
    }
    
    
    //Get avaluacions scheme for a given student, acording to its enseyment / estudis
    public static Avaluacions getAvaluacionsFor(int any, int nexpd, IClient client) {
        
        //Returns all in case it fails
        String ens="*";
        String est="*";
        
        String SQL1 = "SELECT * FROM "+ICoreData.core_mysqlDBPrefix+".xes_alumne_historic WHERE Exp2="+nexpd+" AND "
                + " AnyAcademic='"+client.getICoreData().anyAcademic+"'";
      
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 =client.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                ens = rs1.getString("Ensenyament");
                est = rs1.getString("Estudis");
            }
            if(rs1!=null)
            {
                rs1.close();
                st.close();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Avaluacions.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        return getAvaluacionsFor(any, ens, est, client);
    }
 
    
    public void getAvaluacionsForId(int idAvaluacio)
    {
        
        this.idAval = idAvaluacio;
        String SQL1 = "SELECT * FROM "+this.dbName_ies+".avaluacionsdetall WHERE idAvaluacions="+idAvaluacio+" ORDER BY fechaInicio ASC";
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);

            while (rs1 != null && rs1.next()) {
                java.sql.Date ini = rs1.getDate("fechaInicio");
                java.sql.Date fin = rs1.getDate("fechaFin");
                String quina = StringUtils.noNull(rs1.getString("valorExportable")).toUpperCase();
                int id = rs1.getInt("id");

                if(quina.startsWith("1",0))
                {
                    fechaInicios.put("1", ini);
                    fechaFins.put("1", fin);
                    getIds().put("1", id);
                }
                else if(quina.startsWith("2",0))
                {
                    fechaInicios.put("2", ini);
                    fechaFins.put("2", fin);
                    getIds().put("2", id);
                }
                else if(quina.startsWith("3",0) || quina.startsWith("ORD",0) )
                {
                    fechaInicios.put("3", ini);
                    fechaFins.put("3", fin);
                    getIds().put("3", id);
                }
                else
                {
                    fechaInicios.put(quina, ini);
                    fechaFins.put(quina, fin);
                }

            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Avaluacions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * At the end all are delegated to this method
     * @param ensenyament
     * @param estudis
     * @param client
     * @return 
     */
    public static Avaluacions getAvaluacionsFor(int any, String ensenyament, String estudis, IClient client)
    {
        Avaluacions aval = new Avaluacions(any, client);
        aval.setEnsenyament(ensenyament);
        aval.setEstudis(estudis);
        
        
        //Obtains from iesdigital
        boolean exists_table = false;
        
            
            String SQL1 = "SELECT * FROM "+aval.dbName_ies+".avaluacionsdetall as ad INNER JOIN "
                    + aval.dbName_ies+".avaluacions as a"
                    + " ON ad.idAvaluacions=a.id WHERE (ensenyament LIKE '%*%' OR "
                    + " ensenyament LIKE '%"+ensenyament+"%') AND (estudis LIKE '%*%' OR "
                    + " estudis LIKE '%"+estudis+"%') ORDER BY fechaInicio ASC";
            try {
                Statement st = client.getMysql().createStatement();
                ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);

                while (rs1 != null && rs1.next()) {
                    exists_table = true;
                    java.sql.Date ini = rs1.getDate("fechaInicio");
                    java.sql.Date fin = rs1.getDate("fechaFin");
                    String quina = StringUtils.noNull(rs1.getString("valorExportable")).toUpperCase();
                    int id = rs1.getInt("id");
                    aval.idAval = rs1.getInt("idAvaluacions");

                    if(quina.startsWith("1",0))
                    {
                        aval.fechaInicios.put("1", ini);
                        aval.fechaFins.put("1", fin);
                        aval.getIds().put("1", id);
                    }
                    else if(quina.startsWith("2",0))
                    {
                        aval.fechaInicios.put("2", ini);
                        aval.fechaFins.put("2", fin);
                        aval.getIds().put("2", id);
                    }
                    else if(quina.startsWith("3",0) || quina.startsWith("ORD",0) )
                    {
                        aval.fechaInicios.put("3", ini);
                        aval.fechaFins.put("3", fin);
                        aval.getIds().put("3", id);
                    }
                    else
                    {
                        aval.fechaInicios.put(quina, ini);
                        aval.fechaFins.put(quina, fin);
                    }

                }
                if(rs1!=null)
                {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Avaluacions.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        if(!exists_table)
        {
            //Obtain from sgd
            SQL1 = "SELECT DISTINCT fechaInicio, fechaFin, valorExportable, id, idEvaluaciones FROM "
                  + aval.dbName_sgd+".evaluacionesdetalle GROUP BY valorExportable ORDER BY fechaInicio, valorExportable ";
           
             try {
                Statement st = client.getSgd().createStatement();
                ResultSet rs1 = client.getSgd().getResultSet(SQL1,st);

                while (rs1 != null && rs1.next()) {
                    java.sql.Date ini = rs1.getDate("fechaInicio");
                    java.sql.Date fin = rs1.getDate("fechaFin");
                    String quina = StringUtils.noNull(rs1.getString("valorExportable")).toUpperCase();
                    int id = rs1.getInt("id");
                    aval.idAval = rs1.getInt("idEvaluaciones");

                    if(quina.startsWith("1",0))
                    {
                        aval.fechaInicios.put("1", ini);
                        aval.fechaFins.put("1", fin);
                        aval.getIds().put("1", id);
                    }
                    else if(quina.startsWith("2",0))
                    {
                        aval.fechaInicios.put("2", ini);
                        aval.fechaFins.put("2", fin);
                        aval.getIds().put("2", id);
                    }
                    else if(quina.startsWith("3",0) || quina.startsWith("ORD",0) )
                    {
                        aval.fechaInicios.put("3", ini);
                        aval.fechaFins.put("3", fin);
                        aval.getIds().put("3", id);
                    }
                    else
                    {
                        aval.fechaInicios.put(quina, ini);
                        aval.fechaFins.put(quina, fin);
                    }

                }
                if(rs1!=null)
                {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Avaluacions.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return aval;
    }
    
    /**
     * Get all avaluacions for a given curs academic "any"
     * @param any
     * @param client
     * @return 
     */
    public static ArrayList<Avaluacions> getAllAvaluacions(int any, IClient client)
    {
        
        //Try to get avaluacions from iesdigital (new scheme)
        ArrayList<Avaluacions> list = new ArrayList<Avaluacions>();
        String dbName = ICoreData.core_mysqlDBPrefix+any;
       
        if(client.getMysql().doesTableExists(dbName,"avaluacions"))
        {
            //checkTablesExistence(any, client);
       
            
         String SQL1 = "SELECT id, ensenyament, estudis FROM "+dbName+".avaluacions";
         try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {
               Avaluacions aval = new Avaluacions(any, client);
               aval.setIdAval(rs1.getInt("id") );
               aval.setEnsenyament(rs1.getString("ensenyament"));
               aval.setEstudis(rs1.getString("estudis"));
               aval.getAvaluacionsForId( rs1.getInt("id") );
               list.add(aval);
            }
            if(rs1!=null)
            {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Avaluacions.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        }
        else
        {
            //Return
//            Avaluacions aval = new Avaluacions(any, client);
//            aval.setIdAval(-1);
//            aval.setEnsenyament("*");
//            aval.setEstudis("*");
            list.add(Avaluacions.getAvaluacionsFor(any, "*", "*", client));
        }
    
        return list;
    }

    
    public String getEstudis() {
        return estudis;
    }

    public String getEnsenyament() {
        return ensenyament;
    }

    public HashMap<String,java.util.Date> getFechaInicios() {
        return fechaInicios;
    }

    public HashMap<String,java.util.Date> getFechaFins() {
        return fechaFins;
    }

    public void setEstudis(String estudis) {
        this.estudis = estudis;
    }

    public void setEnsenyament(String ensenyament) {
        this.ensenyament = ensenyament;
    }

    public void save() {
        
        if(this.idAval<=0)
        {            
            String SQL0 = "INSERT INTO avaluacions SET ensenyament=?, estudis=?";
            this.idAval = client.getMysql().preparedUpdateID(SQL0, new Object[]{ensenyament,estudis});
        }
        else
        {
            String SQL0 = "UPDATE avaluacions SET ensenyament=?, estudis=? WHERE id="+idAval;
            client.getMysql().preparedUpdate(SQL0, new Object[]{ensenyament,estudis});
        }
        
        for(String naval: getIds().keySet())
        {
            if(getIds().get(naval)>0)
            {
                update(naval);
            }
            else
            {
                insert(naval);
            }
        }
    }

    private void update(String naval) {
        int id = getIds().get(naval);
      
        String SQL1 = "UPDATE avaluacionsdetall SET fechaInicio=?,fechaFin=? WHERE id="+id;
        client.getMysql().preparedUpdate(SQL1, new Object[]{fechaInicios.get(naval),fechaFins.get(naval)});
        
    }

    private void insert(String naval) {
         
        String SQL1 = "INSERT INTO avaluacionsdetall (idAvaluacions,fechaInicio,fechaFin,valorExportable) VALUES(?,?,?,?)";
        int id = client.getMysql().preparedUpdateID(SQL1, new Object[]{idAval,fechaInicios.get(naval),
        fechaFins.get(naval),naval+"a Avaluació"});
        getIds().put(naval, id);
    }

    public void delete()
    {
        String SQL0 = "DELETE FROM avaluacions WHERE id="+idAval;
        client.getMysql().executeUpdate(SQL0);
        for(Integer id: getIds().values())
        {
            if(id>0)
            {
                String SQL1 = "DELETE FROM avaluacionsdetall WHERE id="+id;
                client.getMysql().executeUpdate(SQL1);
            }
        }
        
        getIds().put("1",-1);
        getIds().put("2",-1);
        getIds().put("3",-1);
    }

    public int getIdAval() {
        return idAval;
    }

    public void setIdAval(int idAval) {
        this.idAval = idAval;
    }

    public HashMap<String,Integer> getIds() {
        return ids;
    }
            
            
    @Override
    public String toString()
    {
        String str = getEnsenyament()+" "+ getEstudis()+"\n";
        str +="inicio: "+fechaInicios+"\n";
        str +="fin: "+fechaFins+"\n";
        str += "sql: "+getWhereClause();
        return str;
    }

    public void duplicate() {
       Avaluacions nova = new Avaluacions(client);
       nova.setEnsenyament(ensenyament);
       nova.setEstudis(estudis);
       nova.setIdAval(-1);
       nova.getFechaInicios().put("1", fechaInicios.get("1"));
       nova.getFechaInicios().put("2", fechaInicios.get("2"));
       nova.getFechaInicios().put("3", fechaInicios.get("3"));
       nova.getFechaFins().put("1", fechaFins.get("1"));
       nova.getFechaFins().put("2", fechaFins.get("2"));
       nova.getFechaFins().put("3", fechaFins.get("3"));
       nova.getIds().put("1", -1);
       nova.getIds().put("2", -1);
       nova.getIds().put("3", -1);
       nova.save();
    }
   
    
    public static void checkTablesExistence(int any, IClient client) {
        String dbName = ICoreData.core_mysqlDBPrefix+any;
        //Check if table exists
        boolean doesTableExists = client.getMysql().doesTableExists(dbName, "avaluacions");
        if(!doesTableExists)
        {
            String SQL = "CREATE TABLE " + dbName + ".`avaluacions` ("
                    + "`id` int(11) NOT NULL AUTO_INCREMENT, "
                    + "`ensenyament` varchar(255) NOT NULL, "
                    + "`estudis` varchar(255) NOT NULL, "
                    + "PRIMARY KEY (`id`) "
                    + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1";
            client.getMysql().executeUpdate(SQL);
            SQL = "INSERT INTO "+ dbName + ".`avaluacions` (ensenyament, estudis) VALUES('*','*')";
            int id = client.getMysql().executeUpdateID(SQL);
            
            doesTableExists = client.getMysql().doesTableExists(dbName, "avaluacionsdetall");
            if(!doesTableExists && id>0)
            {
                SQL = "CREATE TABLE " + dbName + ".`avaluacionsdetall` ("
                        + "`id` int(11) NOT NULL AUTO_INCREMENT, "
                        + "`idAvaluacions` int(11) DEFAULT NULL, "
                        + "`fechaInicio` date DEFAULT NULL, "
                        + "`fechaFin` date DEFAULT NULL, "
                        + "`valorExportable` varchar(255) NOT NULL DEFAULT '', "
                        + "PRIMARY KEY (`id`) "
                        + ") ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1";
                
                client.getMysql().executeUpdate(SQL);
                //IMPORT AVALUACIONS FROM SGD-TABLE evaluacionesdetalle
                importAvaluacionsFromSGD(any, client);
            }
        }
       
    }

    /**
     * imports from cursoxxxx.evaluacionesdetalle --> iesdigitalxxxx.avaluacionsdetall
     * where xxxx stands for any curs academic.
     * @param any
     * @param client 
     */
    public static void importAvaluacionsFromSGD(int any, IClient client) {
        String dbName_ies = ICoreData.core_mysqlDBPrefix+any;
        String dbName_sgd =(String) ICoreData.configTableMap.get("sgdDBPrefix") + any;
        //Retrieve dates from sgd
        String SQL="SELECT fechaInicio, fechaFin FROM "+ dbName_sgd+".evaluacionesdetalle ORDER BY fechaInicio ASC LIMIT 3";
        
        java.util.Date[] iniDates = new java.util.Date[3];
        java.util.Date[] endDates = new java.util.Date[3];
        try {
            Statement st = client.getSgd().createStatement();
            ResultSet rs = client.getSgd().getResultSet(SQL,st);
            int i = 0;
            while(rs!=null && rs.next())
            {
                iniDates[i] = rs.getDate(1);
                endDates[i] = rs.getDate(2);
                i += 1;
            }
            if(rs!=null)
            {
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Avaluacions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Retrieve existing avaluacions from iesdigital
         ArrayList<Integer> idAvals = new ArrayList<Integer>();
         SQL="SELECT id FROM "+ dbName_ies+".avaluacions";
         try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs = client.getMysql().getResultSet(SQL,st);
            while(rs!=null && rs.next())
            {
                idAvals.add(rs.getInt(1));
            }
            if(rs!=null)
            {
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Avaluacions.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        //Put the same dates for each avaluacio
        for(int idAval : idAvals)
        {
            for(int k=0; k<3; k++)
            {
                SQL = "UPDATE "+dbName_ies+".avaluacionsdetall SET fechaInicio=?, fechaFin=? "
                        + " WHERE valorExportable LIKE '%"+(k+1)+"a%' AND idAvaluacions="+idAval;
                //System.out.println(SQL);
                int nup = client.getMysql().preparedUpdate(SQL, new Object[]{iniDates[k], endDates[k]});
                if(nup<=0)
                {
                    SQL = "INSERT INTO "+dbName_ies+".avaluacionsdetall (idAvaluacions, fechaInicio, fechaFin) "
                        + " VALUES(?,?,?) WHERE valorExportable LIKE '%"+(k+1)+"a%'";
                    //System.out.println(SQL);
                    client.getMysql().preparedUpdate(SQL, new Object[]{idAval, iniDates[k], endDates[k]});
                }
            }
            
        }
    }

}
