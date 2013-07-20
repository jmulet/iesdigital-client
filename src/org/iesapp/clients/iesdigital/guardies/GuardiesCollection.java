/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.guardies;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.professorat.BeanProfessor;
import org.iesapp.database.vscrud.GenericCrud;
import org.iesapp.util.DataCtrl;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class GuardiesCollection {
    private final IClient client;
    
    public GuardiesCollection(IClient client)
    {
        this.client = client;
    }
    
    public ArrayList<BeanHorari> listHoraris(String order)
    {
        return new GenericCrud(new BeanHorari(), Horari.queryForm, client.getMysql()).list(order);
    }
    
    public ArrayList<BeanHorariGuardia> listHorariGuardies(String order)
    {
        return new GenericCrud(new BeanHorariGuardia(), HorariGuardia.fromQuery, client.getMysql()).list(order);
    }
    
    public ArrayList<BeanSignatura> listSignaturesMati(String order)
    {
        return new GenericCrud(new BeanSignaturaMati(), Signatura.queryForm, client.getMysql()).list(order);
    }
    
    public ArrayList<BeanSignatura> listSignaturesTarda(String order)
    {
        return new GenericCrud(new BeanSignaturaTarda(), SignaturaTarda.queryForm, client.getMysql()).list(order);
    }
    
    public ArrayList<BeanHorariGuardia> listSignaturaComentaris(String order)
    {
        return new GenericCrud(new BeanSignaturaComentari(), SignaturaComentari.queryForm, client.getMysql()).list(order);
    }
    
     
    public ArrayList<GuardiesBean> getProfesGuardia(int idDia, int idHoraiesd)
    {
         ArrayList<GuardiesBean> list = new  ArrayList<GuardiesBean>();
         
         String SQL1 = "SELECT prof.nombre, prof.idSGD, zg.descripcio, prof.abrev FROM sig_guardies AS ga INNER JOIN "
                 + " sig_professorat AS prof ON prof.abrev=ga.abrev INNER JOIN sig_guardies_zones "
                 + " AS zg ON zg.lloc= ga.lloc WHERE dia="+idDia+" AND hora="+idHoraiesd+" ORDER BY descripcio";
         
         
         try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                GuardiesBean bean = new GuardiesBean();
                bean.setAbrevProfesor(rs1.getString("abrev"));
                bean.setIdProfesor(rs1.getString("idSGD"));
                bean.setNombreProfesor(rs1.getString("nombre"));
                bean.setZonasGuardia(rs1.getString("descripcio"));
                list.add(bean);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuardiesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return list;
    }

    public  ArrayList<GuardiesBean> getProfesGuardia(int idDiaSetmana, Time hora) {
          
          String SQL0 ="SELECT codigo FROM sig_hores_classe WHERE inicio='"+hora+"'";
          int codigo = -1;
       
            try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL0,st);
            while(rs1!=null && rs1.next())
            {
                codigo = rs1.getInt("codigo");
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
          } catch (SQLException ex) {
            Logger.getLogger(GuardiesCollection.class.getName()).log(Level.SEVERE, null, ex);
          }
               
        return getProfesGuardia(idDiaSetmana, codigo);
    }
   
     
    /**
     * Determines if signatures for a given day (sqlDate in SQL format yyyy-mm-dd) 
     * are already created or not
     * @param ctrlDia
     * @return 
     */
    public boolean areSignaturesForDate(String ctrlDia) {
        boolean created = false;
        try {

            String SQL1 = "SELECT * FROM sig_data where data='" + ctrlDia + "'";
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            if (rs1 != null && rs1.next()) {
                created = true;
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuardiesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return created;                
    }
 
    /**Guardies table is filled according to this linked map
    *Step 1: include all professorat (orderer by lastname)
    *Step 2: include horari to map
    *Step 3: include guardies to map
    *Step 4: include status to map
    *Step 5: include comments (homework)
    *All these steps are implemented in the iesdigital client
    *Returns LinkedHashMap<String, RowModel> hm = new LinkedHashMap<String, RowModel>();
    * **/
    public LinkedHashMap<String, RowModel> getRowModelMap(int intDiaSetmana, String ctrlDia, boolean tornTarda)
    {
        return constructHorari(null, intDiaSetmana, ctrlDia, tornTarda);
    }
  
    /**
     * construeix l'horari del professor "abrev" el n. dia de la setmana "dia" 
     * @param abrev
     * @param dia
     * @param torn
     * @return 
     */
   public RowModel getHorari(String abrev, int intDiaSetmana, String ctrlDia, boolean tornTarda)
   {
        //Use above implementation passing abrev!=null
        LinkedHashMap<String, RowModel> constructHorari = constructHorari(abrev, intDiaSetmana, ctrlDia, tornTarda);
        return constructHorari.get(abrev);
   }

   
   //construeix l'horari del professor "abrev" el n. dia de la setmana "dia" i el guarda a la base de dades
   public void commitHorari(String abrev, int intDiaSetmana, String ctrlDia, 
           boolean torn, int stat, String commentg, int feina, boolean forceUpdate)
    {
        RowModel rm =this.getHorari(abrev, intDiaSetmana, ctrlDia, torn);

        String taula;
        int offset =0;
        if(torn)
        {
            taula = "sig_signatures_tarda ";
            offset = 7;
        }
        else
        {
            taula = "sig_signatures ";
            offset = 0;
        }
            

        String campsHores = "";
        String valuesHores = "";
        String updateHores = "";

        for (int i = 1; i < RowModel.NH; i++) {
            campsHores += ", H" + i;
            int s = rm.cells[i].type < 0 ? -1 : stat;
            valuesHores += ", '" + s + "'";
            updateHores += " H" + i + "=" + s + ", ";
        }

        //28/10/2011: Quan faig un commit d'un horari d'un profe, per seguritat
        //primer comprovam si existeix, si existeix faig un update si no un insert

        String SQL0 = "SELECT * FROM " + taula + " AS sig INNER JOIN "
                + "sig_professorat AS prof ON sig.ABREV ="
                + "prof.ABREV WHERE sig.DATA='" + ctrlDia
                + "' AND sig.ABREV='" + abrev + "'";

        boolean mode_insert = true; //insert
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL0, st);

            if (rs1 != null && rs1.next()) {
                mode_insert = false; //update
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (java.sql.SQLException e) {
            //
        }

        String SQL1 = "";
        
        if(mode_insert)
        {
            SQL1 = "INSERT INTO " + taula + " (ABREV, DATA " + campsHores +" ) VALUES('"
                + abrev + "', '"+ ctrlDia + "'" +valuesHores + ");";
        }
        else
        {
            SQL1 = "UPDATE " + taula + " SET "+updateHores+" DATA='"+ctrlDia+"' WHERE ABREV='"+abrev+"' AND DATA='"+ctrlDia+"'";
        }

        if(mode_insert || forceUpdate)
        {
         
            int nup = client.getMysql().executeUpdate(SQL1);
          //  //System.out.println(nup+":"+SQL1);
        }
        
       //si es tracta d'una falta prevista a més ha de marcar feina i comentaris de guardia
       if(stat == 2)
       {
          for(int i=0; i<RowModel.NH; i++)
          {
             String txt = rm.cells[i].text;
             int hora = i + offset;
             if(rm.cells[i].type == 0 ) //nomes crea feina per les hores normals
             {
                 String condition = " WHERE "+
                  " data='"+ ctrlDia +"' AND dia_setmana='"+intDiaSetmana+"' AND "+
                  " hora='"+hora+"' AND profe_falta='"+abrev+"'";
                 SQL1 = "SELECT * FROM sig_diari_guardies " + condition;
                 
                 try {
                      Statement st = client.getMysql().createStatement();
                      ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
                        if (!rs1.next()) {
                            SQL1 = "INSERT INTO sig_diari_guardies (data, dia_setmana, hora, " +
                                    "profe_falta, grup, feina, comentaris) VALUES('" + ctrlDia +
                                    "', '" + intDiaSetmana + "', '" + hora + "', '" + abrev + "', " + "'" + txt + "', '"
                                    + feina + "', '" + commentg + "' )";
                             int nup = client.getMysql().executeUpdate(SQL1);
                        }
                        else {
                             SQL1 = "UPDATE sig_diari_guardies SET feina='"+feina+"', "+
                                    "comentaris='"+ commentg + "' " + condition;
                             int nup = client.getMysql().executeUpdate(SQL1);
                        }
                        if(rs1!=null)
                        {
                            rs1.close();
                            st.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(GuardiesCollection.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
              }
          }
      }

  }

   
   public int updateHorari(String abrev, int intDiaSetmana, String ctrlDia, int h, int code, boolean torn)
   {
       //First of all check if this horari is already written in db
       String taula;
       int offset;
       if (torn) {
           taula = "sig_signatures_tarda ";
           offset = 7;
       } else {
           taula = "sig_signatures ";
           offset = 0;
       }

       String SQL1 = "SELECT * FROM " + taula + " AS sig INNER JOIN "
               + "sig_professorat AS prof ON sig.ABREV ="
               + "prof.ABREV WHERE sig.DATA='" + ctrlDia
               + "' AND sig.ABREV='" + abrev + "'";

       boolean exists = false;

       try {
           Statement st = client.getMysql().createStatement();
           ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);

           if (rs1 != null && rs1.next()) {
               exists = true;
           }
       } catch (SQLException ex) {
           Logger.getLogger(GuardiesCollection.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       if(!exists)
       {
           this.commitHorari(abrev, intDiaSetmana, ctrlDia, torn, 0, "", 0, false);
       }
       
       SQL1 = "UPDATE " + taula + " SET H" + h + "=" + code
               + ", H" + h + "_T=NOW() "
               + " WHERE ABREV='" + abrev
               + "' AND DATA='" + ctrlDia + "'";
       int nup = client.getMysql().executeUpdate(SQL1);

       return nup;
    }

   
    public boolean promptDate(String ctrlDia)
    {
        String SQL1 = "";
        ResultSet rs1 = null;

        SQL1 = "SELECT * FROM sig_data WHERE data='"+ctrlDia+"'";
         try {
             Statement st = client.getMysql().createStatement();
             rs1 = client.getMysql().getResultSet(SQL1,st);
            if (rs1!=null && rs1.next())
            {
              //do nothing:: the date is already there
              return true;
            }
            else
            {
               SQL1 = "INSERT INTO sig_data (data) VALUES('"+ctrlDia+"')";
               int nup = client.getMysql().executeUpdate(SQL1);
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }

        } catch (SQLException ex) {
            Logger.getLogger(GuardiesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
  }

   
   private LinkedHashMap<String,RowModel> constructHorari(String mabrev, int intDiaSetmana, String ctrlDia, boolean tornTarda)
   {
        LinkedHashMap<String, RowModel> hm = new LinkedHashMap<String, RowModel>();
        
       
        if(mabrev==null)
        {   //consulta de tot el professorat (use a single-instance map)
            LinkedHashMap<String, BeanProfessor> mapProf = client.getProfessoratData().getMapProf();
            for (String abrev : mapProf.keySet()) {
                RowModel rmodel = new RowModel();
                rmodel.cells[0].setText(mapProf.get(abrev).getNombre());
                rmodel.cells[0].setCodig(abrev);
                hm.put(abrev, rmodel);
            }
        }
        else
        {
            //Only one professor must be done
            String nombre = client.getProfessoratData().getNomByAbrev(mabrev);
            RowModel rmodel = new RowModel();
            rmodel.cells[0].setText(nombre);
            rmodel.cells[0].setCodig(mabrev);
            hm.put(mabrev, rmodel);
        }
      
        int iIni;
        int iEnd;
        String taula;

        //MATI O TARDA (Choose correct table name and set table headers)
        if(tornTarda)
        {
            iIni = 8;
            iEnd = 14;
            taula = "sig_signatures_tarda ";
        }
        else
        {
            iIni = 1;
            iEnd = 8;
            taula = "sig_signatures ";
        }

        

        for(int i=iIni; i<iEnd; i++){
            try {
                //Aixo llista les hores de classe
                StringBuilder SQL1 = new StringBuilder();
                String abrevCondition = "";
                if(mabrev!=null)
                {
                   abrevCondition = " AND hor.prof='"+mabrev+"' ";
                }
                SQL1.append("SELECT ABREV, NOMBRE, ASIG, NIVEL, CURSO, GRUPO, AULA, IF(item IS NULL,0,1) AS filtrat "); 
                SQL1.append("FROM sig_horaris AS hor INNER JOIN sig_professorat AS prof ON hor.PROF = ");
                SQL1.append("prof.ABREV LEFT JOIN sig_senseguardia AS sg ON hor.asig = sg.item WHERE dia=");
                SQL1.append(intDiaSetmana).append(" AND hora=").append(i).append(abrevCondition).append(" ORDER BY nombre");
                        
                
                Statement st2 = client.getMysql().createStatement();
                ResultSet rs1 = client.getMysql().getResultSet(SQL1.toString(),st2);
           
                while (rs1!=null && rs1.next()) {
                     String abrev = rs1.getString("ABREV");
                     RowModel rmodel = hm.get(abrev);
                     String assig = rs1.getString("ASIG");
                
                    //comprova si aquest camp esta filtrat
                    boolean isfiltered = rs1.getInt("filtrat")>0;
                    if(isfiltered)
                    {
                        rmodel.cells[i-iIni+1].setType(CellModel.TYPE_CAMPFILTRAT);
                    }
                    else
                    {
                        rmodel.cells[i-iIni+1].setType(CellModel.TYPE_NORMAL);
                    }

                    rmodel.cells[i-iIni+1].text = StringUtils.noNull(assig) + ", " +
                          StringUtils.noNull(rs1.getString("CURSO")) + " " +
                          StringUtils.noNull(rs1.getString("NIVEL")) + "-" +
                          StringUtils.noNull(rs1.getString("GRUPO")) +
                          " " + "[" + StringUtils.noNull(rs1.getString("AULA")) + "]";
        
                    //hm.put(abrev, rmodel);
                }
                if(rs1!=null) {
                    rs1.close();
                    st2.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GuardiesCollection.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
       
      

        /////////////////////////////////ARA LLEGEIX LES GUARDIES
        String abrevCondition = "";
        if(mabrev!=null)
        {
             abrevCondition = " AND sig_guardies.abrev='"+mabrev+"' ";
        }
        String condition = "dia="+intDiaSetmana + abrevCondition;
        ArrayList<BeanHorariGuardia> listHorariGuardies = client.getGuardiesClient().getGuardiesCollection().listHorariGuardies(condition);
        for(BeanHorariGuardia bhg : listHorariGuardies)
        {
            RowModel rmodel = hm.get(bhg.getAbrev());
            //ABANS HORA>=0
            int hora = bhg.getHora() - iIni + 1;
            if(rmodel != null && hora >=1 && hora<9)  //necessit aixo sino hi ha problemes
            {
                rmodel.cells[hora].text = bhg.getLloc();
                rmodel.cells[hora].type = CellModel.TYPE_GUARDIA;
            }
        }
        //listHorariGuardies.clear();
       
        
        ///////////////////////////////NOW CHECK THE STATUS OF EACH HOUR
         String avui = new DataCtrl().getDataSQL();
         abrevCondition = "";
         if(mabrev!=null)
         {
                   abrevCondition = " AND sig_professorat.abrev='"+mabrev+"' ";
         }
         
         condition = "DATA='"+ctrlDia+"'" + abrevCondition;
         ArrayList<BeanSignatura> listSignatures;
         if(tornTarda)
         {
             listSignatures = client.getGuardiesClient().getGuardiesCollection().listSignaturesTarda(condition);
         }
         else
         {
             listSignatures = client.getGuardiesClient().getGuardiesCollection().listSignaturesMati(condition);
         }
    
        for (BeanSignatura bs : listSignatures) {
            String abrev = bs.getAbrev();
            RowModel rmodel = hm.get(abrev);
            if (rmodel != null) {
                for (int i = 1; i < 8; i++) {
                    int stat = bs.getH_asArray()[i - 1];  //H1 --> [0] in array
                    rmodel.cells[i].status = stat;

                    //28/10/11: Bug1 :: Només les hores buides poden contenir un stat=-1
                    //Si es canvia l'horari dinàmicament, i les signatures
                    //ja han estat creades això fa que tengui hores amb
                    //estat -1 que requereixen esser signades.
                    //Arregla inconsitencia #1
                    if (avui.equals(ctrlDia) && stat == -1 && !rmodel.cells[i].text.isEmpty()) {
                        //System.out.println("he trobat inconstencia tipus 1");
                        rmodel.cells[i].status = 0;
                        //Cal fer un update de la base
                        int nup = this.updateHorari(abrev, intDiaSetmana, ctrlDia, i, 0, tornTarda);

                        //System.out.println("he trobat inconstencia tipus 2 :"+nup);
                    }

                    //28/10/11: Bug2 :: Pel mateix motiu anterior, quedarà sense
                    // signar una hora buida, es a dir un camp "" amb stat=0
                    //Arregla inconsitencia #2
                    if (avui.equals(ctrlDia) && stat == 0 && rmodel.cells[i].text.isEmpty()) {
                        rmodel.cells[i].status = -1;
                        //Cal fer un update de la base
                        int nup = this.updateHorari(abrev, intDiaSetmana, ctrlDia, i, -1, tornTarda);
                        //System.out.println("he trobat inconstencia tipus 2 :"+nup);
                    }

                }
                hm.put(abrev, rmodel);
            }
        }
        //listSignatures.clear();

        /////////////////////////////////ARA IMPORTA LES FEINES I COMENTARIS
        String SQL1 = "SELECT * FROM sig_diari_guardies WHERE DATA='" + ctrlDia
                + "' AND HORA>=" + iIni + " AND HORA<" + iEnd;
        if (mabrev != null) {
            SQL1 += " AND profe_falta='" + mabrev + "'";
        }

        try {
            Statement st5 = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st5);


            while (rs1 != null && rs1.next()) {
                //si no troba cap element vol dir que nigu ha deixat cap
                //comentari o feina
                String who = rs1.getString("PROFE_FALTA");
                RowModel rmodel = hm.get(who);
                if (rmodel != null) {
                    int h = rs1.getInt("HORA");
                    rmodel.cells[h - iIni + 1].feina = rs1.getInt("FEINA");
                    rmodel.cells[h - iIni + 1].comment = rs1.getString("COMENTARIS").replaceAll(";", "\n");
                }
            }
            if (rs1 != null) {
                rs1.close();
                st5.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuardiesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }


        return hm;
    }

   
    
}
