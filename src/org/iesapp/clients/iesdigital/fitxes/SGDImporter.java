/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.fitxes;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.clients.iesdigital.dates.Avaluacions;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.DataCtrl;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class SGDImporter extends Thread
{
    private final boolean connect_mysql;
    private final boolean connect_sgd;
    private final boolean programada;
    private final SGDImporterConfig updateSGDConfig;
    private int doExpd = -1;
    
    private String sgdDB="";
    private String appsDB="";
    private String anyAcademic="";

 
    private MyDatabase mysql;
    private MyDatabase sgd;
    private double status=0.0;
    private String logoutput="";
    private boolean running = false;
    private final ArrayList<String> doIdentifiers;
    private final IClient client;
    private final int anyAcademicInt;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }


      /** Any simple, p.e. 2011
      *doExpd <0 updates all students
      * doExpd >0 updates this student
      * doIdentifiers = null (do importation for all defined simbols)
      * doIdentifiers = ArrayList --> (do only importation for simbols in the list)
      **/
      public SGDImporter(String db_sgd, String any, String invoker, int doExpd, ArrayList<String> doIdentifiers, IClient client)
      {
          this.client = client;
          programada = invoker.equals("PROGRAMADA");
          mysql =  client.getMysql(); //new MyDatabase(CoreCfg.core_mysqlHost, CoreCfg.core_mysqlDB, CoreCfg.core_mysqlUser, CoreCfg.core_mysqlPasswd);
          connect_mysql = !mysql.isClosed();
          sgd = client.getSgd(); //new MyDatabase(CoreCfg.coreDB_sgdHost, db_sgd, CoreCfg.coreDB_sgdUser, CoreCfg.coreDB_sgdPasswd);
          connect_sgd = !sgd.isClosed();
          anyAcademic = any;
          anyAcademicInt = Integer.parseInt(any);
          sgdDB = (String) ICoreData.configTableMap.get("sgdDBPrefix")+ any;
          //Gets the single-instance of updateSGDConfig
          //We must create an updateSGDConfig for the selected year
          updateSGDConfig = new SGDImporterConfig(anyAcademicInt, client);
          this.doExpd = doExpd;
          this.doIdentifiers = doIdentifiers;
      }

      /** 
       * 
       * @param expd <0 --> All students, >0 one student 
       */
      public void setDoExpd(int expd)
      {
          this.doExpd = expd;
      }
      
      /**
       * Modify this object
       * @return 
       */
      public SGDImporterConfig getUpdateSGDConfig()
      {
          return updateSGDConfig;
      }
      
//===================================== operations done in background
       @Override
       public void run()
       {
            running = true;
            doUpdate();
            running = false;
            propertyChangeSupport.firePropertyChange("running", true, false);
            
       }
      
//==================================== operations done in foreground       
       public void doUpdate()
       {
            //System.out.println("DO UPDATE");
            //Deixa constància que s'ha engegat la tasca
            int jobid=0;
            if(connect_mysql && programada)
            {
                //Deixa constància que ha començat la tasca
                String SQL1 = "INSERT INTO sig_log (ip, netbios, domain, usua, tasca, inici) "
                        + " VALUES('"+ICoreData.ip+"','"+ICoreData.netbios+"','"+ICoreData.core_PRODUCTID+"',?,?, NOW())";
                 //System.out.println(SQL1);
                Object[] obj = new Object[]{"PROGRAMAT", "IMPORTSGD-FITXES"};
                //System.out.println(obj);
                jobid = mysql.preparedUpdateID(SQL1, obj);
                 //System.out.println(jobid);
            }
            
            //String any = StringUtils.BeforeLast(anyAcademic, "-");
            int numOk = 0;
            int numError = 0;
            String error_msg = "expds: ";
         
            if(!connect_sgd)
            {
                return;
            }
            
             HashMap<Integer,HashMap<String, SgdInc>> map = new HashMap<Integer,HashMap<String, SgdInc>>();
             status = 25.;

               if (doExpd < 0) {
               ArrayList<Avaluacions> allAvaluacions = Avaluacions.getAllAvaluacions(anyAcademicInt, client);

               for (Avaluacions aval : allAvaluacions) {
                   for (String identifier : updateSGDConfig.getListSimbolsIds().keySet()) {
                       if(doIdentifiers==null || doIdentifiers.contains(identifier))
                       {
                       boolean commentRequired = false;
                       if (updateSGDConfig.getListCommentRequired().contains(identifier)) {
                           commentRequired = true;
                       }
                       getCountIncidencies(1, aval, map, identifier, updateSGDConfig.getListSimbolsIds().get(identifier), commentRequired);
                       getCountIncidencies(2, aval, map, identifier, updateSGDConfig.getListSimbolsIds().get(identifier), commentRequired);
                       getCountIncidencies(3, aval, map, identifier, updateSGDConfig.getListSimbolsIds().get(identifier), commentRequired);
                       }
                   }
               }
               status = 75.;
           } else {

               Avaluacions aval = Avaluacions.getAvaluacionsFor(anyAcademicInt, doExpd, client);
               for (String identifier : updateSGDConfig.getListSimbolsIds().keySet()) {
                   if(doIdentifiers==null || doIdentifiers.contains(identifier))
                   {
                   boolean commentRequired = false;
                   if (updateSGDConfig.getListCommentRequired().contains(identifier)) {
                       commentRequired = true;
                   }
                   getCountIncidencies(1, aval, map, identifier, updateSGDConfig.getListSimbolsIds().get(identifier), commentRequired);
                   getCountIncidencies(2, aval, map, identifier, updateSGDConfig.getListSimbolsIds().get(identifier), commentRequired);
                   getCountIncidencies(3, aval, map, identifier, updateSGDConfig.getListSimbolsIds().get(identifier), commentRequired);
                   }
               }


           }

           double inc = 25 / (1. + map.size());

           //Fa l'update a la base de dades
           for (int expd : map.keySet()) {
               HashMap<String, SgdInc> incAlumne = map.get(expd);
               String fieldsUpdate = "";
               String ambit = "";
                for(String identifier: incAlumne.keySet())
                {
                       String id = identifier.toUpperCase();
                       SgdInc sgdInc = incAlumne.get(identifier); 
                       ambit = sgdInc.getAmbit();
                       fieldsUpdate += " "+id+"_1A='"+sgdInc.getN1a()+"', "
                                          +id+"_2A='"+sgdInc.getN2a()+"', "
                                          +id+"_3A='"+sgdInc.getN3a()+"', ";
                }
                fieldsUpdate = StringUtils.BeforeLast(fieldsUpdate, ",");
                String SQL2 = "UPDATE `"+ICoreData.core_mysqlDBPrefix+"`.fitxa_alumne_curs SET " 
                       +  fieldsUpdate + " WHERE IdCurs_FK_ID='"+anyAcademic+"' AND Exp_FK_ID="+expd;
            
                int nup = mysql.executeUpdate(SQL2);
                logoutput +="Nexpd ["+expd+"] "+nup+" :: "+ambit+"  {"+fieldsUpdate+"};\n";
                if(nup>0)
                {
                    numOk +=1;
                }
                else
                {
                    numError +=1;
                    error_msg += expd+"; ";
                }

                status += inc;
                }
            

            status = 100;

            String msg = "Acabat amb "+numOk+" updates i "+numError+" errors. "+error_msg;
            logoutput += "\n\n"+msg;
            
            //System.out.println(logoutput);
            
            if(connect_mysql && programada)
            {
                //Deixa constància que s'acabat la tasca
                String SQL1 = "UPDATE sig_log SET usua='PROGRAMAT', fi=NOW(), resultat=? WHERE id=?";
                if(!connect_sgd){
                    msg = "Error: no hi ha connexió amb SGD";
                }

                Object[] obj = new Object[]{msg, jobid};
                int nup = mysql.preparedUpdate(SQL1, obj);
            }
            
        }


       /***
        * Generic method for counting incidencies
        * supports all students or single student
        * depending on the member value doExpds<0 or >0
        ***/
        private void getCountIncidencies(int aval, Avaluacions avalSystem, 
                HashMap<Integer,HashMap<String,SgdInc>> map, String identifier,
                ArrayList<Integer> listIds, boolean commentRequired)
        {
            
            String desde; 
            String fins;
            desde = new DataCtrl( avalSystem.getFechaInicios().get(""+aval) ).getDataSQL();
            fins  = new DataCtrl( avalSystem.getFechaFins().get(""+aval) ).getDataSQL();
      
            String listAlumnes;
        
            if(doExpd>0)
            {
                listAlumnes = " AND expediente+0 ='"+doExpd+"' ";
             }
            else
            {
                 listAlumnes = " AND expediente+0 IN ("+avalSystem.getListExpds()+") ";
             }
            
            int nlen = listIds.size();
            String condicio = "";
            if(nlen>0)
            {
            condicio = " (idTipoIncidencias="+listIds.get(0)+" ";

            for(int i=1; i<nlen; i++)
            {
                condicio += " OR idTipoIncidencias="+listIds.get(i)+" ";
            }
            condicio += " ) ";
            condicio += listAlumnes;

            //We must decide whether to use this condition or not
             if(commentRequired)
            {
                condicio += " AND NOT ((idTipoObservaciones IS NULL OR idTipoObservaciones=0) AND Comentarios='') ";
            }
             //Nova Query 31/9/2011
             String SQL1 =" SELECT expediente+0, sum(if("+condicio+" , 1 , 0)) AS totalal FROM "+sgdDB+".faltasalumnos AS fal "
                      + "  INNER JOIN "+sgdDB+".alumnos AS alm ON alm.id = fal.idAlumnos "
                      + "  WHERE dia >= '"+desde+"' AND dia <= '"+fins+"' "
                      + "  GROUP BY expediente";

            //System.out.println(SQL1);
            try {
                Statement st = sgd.createStatement();
                ResultSet rs1 = sgd.getResultSet(SQL1,st);
                while (rs1 != null && rs1.next()) {
                    int expd = rs1.getInt(1);
                    int count = rs1.getInt(2);
                    if (count > 0) {
                        boolean q = map.containsKey(expd);
                        HashMap<String, SgdInc> mapInc = null;
                        if (q) {
                            mapInc = map.get(expd);
                        } else {
                            mapInc = new HashMap<String, SgdInc>();
                        }

                        boolean q2 = mapInc.containsKey(identifier);
                        SgdInc sgdinc = null;
                        if (q2) {
                            sgdinc = mapInc.get(identifier);
                        } else {
                            sgdinc = new SgdInc();
                            sgdinc.ambit = avalSystem.getEnsenyament() + " | " + avalSystem.getEstudis();
                            sgdinc.simbol = identifier;
                            mapInc.put(identifier, sgdinc);
                        }

                        if (aval == 1) {
                            sgdinc.n1a = count;
                        } else if (aval == 2) {
                            sgdinc.n2a = count;
                        } else if (aval == 3) {
                            sgdinc.n3a = count;
                        }

                        map.put(expd, mapInc);
                    }
                }
                if(rs1!=null) {
                    rs1.close();
                    st.close();
                }
                } catch (SQLException ex) {
                    Logger.getLogger(SGDImporter.class.getName()).log(Level.SEVERE, null, ex);
                }
         
       
    }
}

    public String getLogoutput() {
        return logoutput;
    }

    public void setLogoutput(String logoutput) {
        this.logoutput = logoutput;
    }


    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public String getAppsDB() {
        return appsDB;
    }

    public void setAppsDB(String appsDB) {
        this.appsDB = appsDB;
    }

    public String getSgdDB() {
        return sgdDB;
    }

    public void setSgdDB(String sgdDB) {
        this.sgdDB = sgdDB;
    }

    public String getAnyAcademic() {
        return anyAcademic;
    }

    public void setAnyAcademic(String anyAcademic) {
        this.anyAcademic = anyAcademic;
    }

}


