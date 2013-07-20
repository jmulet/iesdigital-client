/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.anuncis;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;

/**
 *
 * @author Josep
 */
public class AnuncisClient {
    private final IClient client;
    
    public AnuncisClient(IClient client)
    {
        this.client = client;
    }

    
     //Carrega els anuncis
     //mode=* tots, M mes S setmana A avui
     //type=-1 tots, 1=EXTRAESCOLARS, 2=REUNIONS, 3=EVENTS, 4=NOVETATS, 5=TIC
    
     public ArrayList<AnunciBean> loadAnuncis(int type, int order, String limit, String txt)
     {
        ArrayList<AnunciBean> listAnuncis = new ArrayList<AnunciBean>();
       
        String SQL1;
        java.util.Date avui = new java.util.Date();
        String cond = "";
        if(type>=0)
        {
            cond = " AND mis.missatge LIKE '%type={"+type+"}%' ";
        }
       
        
        String orderCond = " ORDER by data DESC, id DESC"; //Order by post date
        
   
        if(order==1)
        {
            //Order by first event date
            orderCond = " ORDER BY STR_TO_DATE(REPLACE(LEFT(RIGHT(missatge,LENGTH(missatge)-10-LOCATE('eventDate={',missatge)),10),'-','.'), GET_FORMAT(DATE, 'EUR')) DESC, id DESC";
        }
        
         if(!limit.equals("*") && txt.isEmpty())
        {
            orderCond += " LIMIT "+limit;
        }
       
       
        
        if(client.getUserInfo()==null)
        {
            //Les ordena per data de post, no per data de l'event
            SQL1 = "SELECT mis.id, mis.data, mis.missatge, mis.de as abrev FROM "
                + " sig_missatges as mis LEFT JOIN sig_professorat as prof ON mis.de=prof.abrev "
                + " WHERE instantani=2 AND "
                + " para LIKE '%*%' "+cond+"  "+orderCond;
        }
        else
        {
             SQL1 = "SELECT mis.id, mis.data, mis.missatge, mis.de as abrev FROM "
                + " sig_missatges as mis LEFT JOIN sig_professorat as prof ON mis.de=prof.abrev "
                + " WHERE instantani=2 AND "
                + " (para LIKE '%*%' OR para LIKE '%"+client.getUserInfo().getAbrev()+"%' ) "+cond+" "+
                     orderCond;
        }
         
        int k = 0;
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                String doc = rs1.getString("missatge");
                AnunciBean bean = AnuncisParser.getBean(doc);
                bean.setAbrev(rs1.getString("abrev"));
               // bean.postdate = rs1.getDate("data");
                bean.newpost = false;
                bean.globalId = k;
                bean.dbId = rs1.getInt("id");
                bean.setNou(false);
                
                if(txt.isEmpty() || (!txt.isEmpty() && bean.getTitle().toUpperCase().contains(txt.toUpperCase()) ||
                        bean.getBody().toUpperCase().contains(txt.toUpperCase())))
                {
                    listAnuncis.add(bean);
                }   
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnuncisClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listAnuncis;
     }

    public void dispose() {
        
    }

    public int getAnuncisVigents(String abrev) {
        int vigents = 0;
        String SQL1 = "SELECT STR_TO_DATE(SUBSTRING(mis.missatge, LOCATE(';eventdate={',mis.missatge)+12,10),'%d-%m-%Y') AS eventDate, "+
                     "STR_TO_DATE(SUBSTRING(mis.missatge, LOCATE(';eventdate2={',mis.missatge)+13, 10) , '%d-%m-%Y') AS eventDate2  FROM "+
                     "sig_missatges AS mis LEFT JOIN sig_professorat AS prof ON mis.de=prof.abrev "+
                     "WHERE mis.para LIKE '%*%' OR mis.para LIKE '%"+abrev+"%'";
        
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar calnow = Calendar.getInstance();
        calnow.set(Calendar.HOUR_OF_DAY, 0);
        calnow.set(Calendar.MINUTE, 0);
        calnow.set(Calendar.SECOND, 0);
        calnow.set(Calendar.MILLISECOND, 0);
      
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs = client.getMysql().getResultSet(SQL1);
            while(rs!=null && rs.next())
            {
                Date date1 = rs.getDate(1);
                Date date2 = rs.getDate(2);
                if(date1!=null)
                {
                    cal1.setTime(date1);
                    if(date2!=null)
                    {
                        cal2.setTime(date2);
                        if(cal1.compareTo(calnow)<=0 && calnow.compareTo(cal2)<=0)
                        {
                            vigents += 1;
                        }
                    }
                    else
                    {
                        if(cal1.compareTo(calnow)>=0)
                        {
                            vigents += 1;
                        }
                    }
                }
                
            }
            if(rs!=null)
            {
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnuncisClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vigents;
    }
}
