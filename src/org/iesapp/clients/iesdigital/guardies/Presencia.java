/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.guardies;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.dates.DatesControl;
import org.iesapp.util.DataCtrl;

/**
 *
 * @author Josep
 */
public class Presencia {

    private int NH = 8;
    private final IClient client;
    private final DataCtrl dataCtrl;

    public Presencia(IClient client) {
        this.client = client;
        dataCtrl = new DataCtrl();
    }

    public boolean haSignat(String abrev) {
        boolean result = true;

        //No s'ha de signar en festius o dissabtes i diumenges
        DatesControl dc = new DatesControl(client);
        if (dc.getIntDia() > 5 || dc.esFestiu()) {
            return true;
        }

        if (abrev.equalsIgnoreCase("ADMIN") || abrev.equalsIgnoreCase("PREF")  || abrev.equalsIgnoreCase("GUARD")) {
            return true;
        }

        //condicio que un usuari ha signat o no (taula del mati)
        String SQL1 = "SELECT * FROM sig_signatures WHERE ABREV='" + abrev + "' AND "
                + "DATA=CURRENT_DATE() AND ( H1<1 AND H2<1 AND H3<1 AND "
                + " H4<1 AND H5<1 AND H6<1 AND H7<1)";


        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            if (rs1 != null && rs1.next()) {
                result = false;
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Presencia.class.getName()).log(Level.SEVERE, null, ex);
        }


        //condicio que un usuari ha signat o no (taula de la tarda)
        SQL1 = "SELECT * FROM sig_signatures_tarda WHERE ABREV='" + abrev + "' AND "
                + "DATA=CURRENT_DATE() AND ( H1<1 AND H2<1 AND H3<1 AND "
                + " H4<1 AND H5<1 AND H6<1 AND H7<1)";

        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            if (rs1 != null && rs1.next()) {
                result = false;
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Presencia.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }

    
    
    public int getTorn(String abrev) {
        int torn = 0;
        String SQL1 = "SELECT torn FROM sig_professorat WHERE abrev='" + abrev + "'";

        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            while (rs1 != null && rs1.next()) {
                torn = rs1.getInt("torn");
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Presencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return torn;
    }
 
    //Procedeix a signar les hores possibles; que no son guardia i son posteriors a l'hora actual
    public void autoSigna(String abrev) {

        int torn = this.getTorn(abrev);
        if(torn==0 || torn==2)
        {
            autoSignaTorn(abrev, 0);
        }
        if(torn>0)
        {
            autoSignaTorn(abrev, 1);
        }
  
    }
    
    private void autoSignaTorn(final String abrev, final int torn)
    {
        int offset = 0;
        if (torn > 0) {
            offset = 7;
        }
        RowModel rm = client.getGuardiesClient().getGuardiesCollection().getHorari(abrev, dataCtrl.getIntDia(), dataCtrl.getDataSQL(), torn>0);
        Time ara = client.getMysql().getServerTime();
        for (int i = 0; i < 7; i++) {
            double lag = (ara.getTime() - client.getDatesCollection().getHoresClase()[i + offset].getTime()) / (60000.);
            System.out.println("yielding a lag of "+lag);
            CellModel cm = rm.cells[i + 1];
            if (cm.type == CellModel.TYPE_NORMAL) //es classe normal
            {
                    if (lag < 15) //15 minuts per signar
                    {
                        System.out.println(cm.toString());
                        //cm.setStatus(1);
                        client.getGuardiesClient().getGuardiesCollection().updateHorari(abrev, dataCtrl.getIntDia(), dataCtrl.getDataSQL(), i + 1, 1, torn > 0);
                        System.out.println("UPDATED HORARI");
                    }
            }
            else if (cm.type == CellModel.TYPE_GUARDIA) //es classe normal
            {
                
            }
            
        }
    }

    public void writeHorariIfNotExists(String abrev, java.util.Date date, int stat) {
        DataCtrl dc = new DataCtrl(date);
        
        int torn = getTorn(abrev);
        RowModel rm = client.getGuardiesClient().getGuardiesCollection().getHorari(abrev, dc.getIntDia(), dc.getDataSQL(), torn>0);
        boolean forceUpdate = true;

        String taula;
        int offset;
       
        if (torn > 0) {
            taula = "sig_signatures_tarda ";
            offset = 7;
        } else {
            taula = "sig_signatures ";
            offset = 0;
        }


        String campsHores = "";
        String valuesHores = "";
        String updateHores = "";

        for (int i = 1; i < 8; i++) {
            campsHores += ", H" + i;
            int s = rm.cells[i].type < 0 ? -1 : stat;
            valuesHores += ", '" + s + "'";
            updateHores += " H" + i + "=" + s + ", ";
        }

        //28/10/2011: Quan faig un commit d'un horari d'un profe, per seguritat
        //primer comprovam si existeix, si existeix faig un update si no un insert
        String ctrlDia = new DataCtrl(date).getDataSQL();

        String SQL0 = "SELECT * FROM " + taula + " AS sig INNER JOIN "
                + " sig_professorat AS prof ON sig.ABREV ="
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

        String SQL1;

        if (mode_insert) {
            SQL1 = "INSERT INTO " + taula + " (ABREV, DATA " + campsHores + " ) VALUES('"
                    + abrev + "', '" + ctrlDia + "' " + valuesHores + ");";
        } else {
            SQL1 = "UPDATE " + taula + " SET " + updateHores + " DATA='" + ctrlDia + "' WHERE ABREV='" + abrev + "' AND DATA='" + ctrlDia + "'";
        }

        if (mode_insert || forceUpdate) {
            int nup = client.getMysql().executeUpdate(SQL1);
        }

    }
   
    //Alias from GuardiesCollection
   public RowModel getHorari(final String abrev, boolean tarda)
   {
       return client.getGuardiesClient().getGuardiesCollection().getHorari(abrev, dataCtrl.getIntDia(), dataCtrl.getDataSQL(), tarda);
   }

   
         

     
}
