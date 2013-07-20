/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.util.JSEngine;
import org.iesapp.util.StringUtils;

    
/**
 *
 * @author Josep
 */
public final class SGDImporterConfig {
    private LinkedHashMap<String, ArrayList<String>> listSimbols;
    private JSEngine jsEngine;
    private LinkedHashMap<String, ArrayList<Integer>> listSimbolsIds;
    private ArrayList<String> filteredFields;
    private ArrayList<String> listCommentRequired;
    private final IClient client;
    private final int year;
    private final String sgdDB;
     
    //getInstance for this current year
    public SGDImporterConfig(IClient client)
    {
        this.client = client;
        this.year = client.getICoreData().anyAcademic;
        this.sgdDB = (String) ICoreData.configTableMap.get("sgdDBPrefix")+year;
        //initialize(); called lazy...
    }
    
    //getInstance for any year
    public SGDImporterConfig(int year, IClient client)
    {
        this.client = client;
        this.year = year;
        this.sgdDB = (String) ICoreData.configTableMap.get("sgdDBPrefix")+year;
        //initialize(); called lazy...
    }
    
    public HashMap<String, ArrayList<String>> getListSimbols(){
          if(listSimbols==null)
          {
              initialize();
          }
          return listSimbols;
    }
    
    //Problem: these ids may vary among years (importer allows changes of years)
     public HashMap<String, ArrayList<Integer>> getListSimbolsIds(){
          if(listSimbolsIds==null)
          {
              initialize();
          }
          return listSimbolsIds;
    }
    
     public ArrayList<String> getListCommentRequired(){
          if(listCommentRequired==null)
          {
              initialize();
          }
          return listCommentRequired;
    }
    
    
    private void initialize()
    {
        //Initialization is done via configTableMap
        String lists = (String) ICoreData.configTableMap.get("sgdImporterSimbolLists");
        String creq = (String) ICoreData.configTableMap.get("sgdImporterCRequired");
        LinkedHashMap<String,String> tmp = StringUtils.StringToLinkedHash(lists, ";", true);
        listSimbols = new LinkedHashMap<String, ArrayList<String>>();
        for(String id: tmp.keySet())
        {
            listSimbols.put(id, StringUtils.parseStringToArray(tmp.get(id), ",", StringUtils.CASE_INSENSITIVE));
        }
        tmp.clear();
        
        listCommentRequired = StringUtils.parseStringToArray(creq, ";", StringUtils.CASE_INSENSITIVE);
            
          
        //Check if la fitxa de l'alumne conte les columnes adequades per poder desar
        //la informacio d'imporatacio
        //Empram la següent notacio: IDENTIFER_1A, IDENTIFIER_2A, IDENTIFIER_3A  
        ArrayList<String> fields = new ArrayList<String>(); 
        filteredFields = new ArrayList<String>(); 
        String SQL1 = "SHOW COLUMNS FROM `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs";
        try {
            Statement st1 = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st1);
            while(rs1!=null && rs1.next())
            {
                String columnName = rs1.getString("Field").toUpperCase();
                fields.add(columnName);
                if(columnName.endsWith("_1A") || columnName.endsWith("_2A") || columnName.endsWith("_3A"))
                {
                        String identifier = StringUtils.BeforeLast(columnName, "_");
                        if(!filteredFields.contains(identifier))
                        {
                            filteredFields.add(identifier);
                        }
                }
            }
            rs1.close();
            st1.close();
        } catch (SQLException ex) {
            Logger.getLogger(SGDImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(String identifier: listSimbols.keySet())
        {
            String id = identifier.toUpperCase();
            if(!fields.contains(id+"_1A"))
            {
                SQL1 = "ALTER TABLE `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs ADD "+id+"_1A INT(10) UNSIGNED DEFAULT '0' NOT NULL";
                client.getMysql().executeUpdate(SQL1);
            }
            if(!fields.contains(id+"_2A"))
            {
                SQL1 = "ALTER TABLE `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs ADD "+id+"_2A INT(10) UNSIGNED DEFAULT '0' NOT NULL";
                client.getMysql().executeUpdate(SQL1);
            }
            if(!fields.contains(id+"_3A"))
            {
                SQL1 = "ALTER TABLE `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs ADD "+id+"_3A INT(10) UNSIGNED DEFAULT '0' NOT NULL";
                client.getMysql().executeUpdate(SQL1);
            }
        }
          
          //Convert these simbols to ids
          listSimbolsIds = new LinkedHashMap<String, ArrayList<Integer>>();
          for(String simbol: listSimbols.keySet())
          {
              ArrayList<Integer> listInt = new ArrayList<Integer>();
              ArrayList<String> list = listSimbols.get(simbol);
              for(String s: list)
              {
                  listInt.addAll(getIncidenciesIds(s));
              }
              listSimbolsIds.put(simbol, listInt);
          }
           
          
          //Redefine these lists of ids via JSscript
          jsEngine = JSEngine.getJSEngine(SGDImporterConfig.class, ICoreData.contextRoot);
          Bindings bindings = jsEngine.getBindings();
          bindings.put("listSimbolsIds", listSimbolsIds);
          bindings.put("listCommentRequired", listCommentRequired);
          try{
            jsEngine.evalBindings(bindings);
            jsEngine.invokeFunction("redefineListsOfSimbols");
          }
          catch(Exception ex)
          {
            Logger.getLogger(SGDImporter.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    


       private ArrayList<Integer> getIncidenciesIds(String label)
       {

        ArrayList<Integer> aux = new ArrayList<Integer>();

        String SQL1 = "SELECT id FROM "+sgdDB+".tipoincidencias WHERE simbolo='"+label+"'";
        try {
            Statement st = client.getSgd().createStatement();
            ResultSet rs1 = client.getSgd().getResultSet(SQL1,st);
            while (rs1.next()) {
                aux.add(rs1.getInt("id"));
            }
            if(rs1!=null)
            {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SGDImporter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aux;
        }

       
       public ArrayList<String> getColumnFields()
       {
           if(filteredFields==null)
           {
               initialize();
           }
            
           
           return filteredFields;
       }
       
       /**
        * Loads imported SGD data for a given alumno exp and year ids
        * @param exp_FK_ID
        * @param idCurs_FK_ID
        * @return 
        */
    public LinkedHashMap<String, SgdInc> getLocalValuesOf(int exp_FK_ID, String idCurs_FK_ID) {
        java.util.LinkedHashMap<String, SgdInc> map = new java.util.LinkedHashMap<String, SgdInc>();
        String SQL1 = "SELECT * FROM `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs WHERE Exp_FK_ID='"+ exp_FK_ID+"' AND IdCurs_FK_ID='"+idCurs_FK_ID+"'";
        boolean found = false;
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 =client.getMysql().getResultSet(SQL1,st);
            ResultSetMetaData metaData = rs1.getMetaData();
            
            if (rs1!=null && rs1.next()) {
               found = true;
               for(int i=1; i<metaData.getColumnCount()+1; i++)
               {
                    String columnName = metaData.getColumnName(i).toUpperCase();
                    if(columnName.endsWith("_1A") || columnName.endsWith("_2A") || columnName.endsWith("_3A"))
                    {
                        String identifier = StringUtils.BeforeLast(columnName, "_");
                        String aval = StringUtils.AfterLast(columnName, "_");
                        int value = rs1.getInt(i);
                        SgdInc sgdinc;
                        if(map.containsKey(identifier))
                        {
                            sgdinc = map.get(identifier);
                        }
                        else
                        {
                            sgdinc = new SgdInc();
                            sgdinc.setSimbol(identifier);
                            map.put(identifier, sgdinc);
                        }
                        if(aval.startsWith("1"))
                        {
                            sgdinc.setN1a(value);
                        }
                        else if(aval.startsWith("2"))
                        {
                            sgdinc.setN2a(value);
                        }
                        else if(aval.startsWith("3"))
                        {
                            sgdinc.setN3a(value);
                        }

                    }
               }
            }
            if(rs1!=null)
            {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SGDImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        //Return a zero-map if not found
        if(!found)
        {
            for(String id: getColumnFields())
            {
                SgdInc sgdinc = new SgdInc();
                sgdinc.setSimbol(id);
                map.put(id, sgdinc);
            }
                 
        }
        
        return map;
    }

}
