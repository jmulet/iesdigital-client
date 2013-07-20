/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.dates;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.iesapp.clients.iesdigital.IClient;

/**
 *
 * @author Josep
 */
public class DatesControl extends org.iesapp.util.DataCtrl {
    private final IClient client;

    public DatesControl(IClient client)
    {
        super();
        this.client = client;      
    }

    public DatesControl(java.sql.Date date, IClient client)
    {
        super(date);
        this.client = client;
    }

    public DatesControl(java.util.Date date, IClient client)
    {
        super(date);
        this.client = client;
    }

    public DatesControl(Calendar cal, IClient client)
    {
        super(cal);
        this.client = client;
    }
  
    private boolean isHoliday(Calendar cal)
    {
          boolean esfesta = false;
          
          int diaSetmana = 0;
          switch(cal.get(Calendar.DAY_OF_WEEK))
          {
              case Calendar.MONDAY: diaSetmana = 1; break;
              case Calendar.TUESDAY: diaSetmana = 2; break;
              case Calendar.WEDNESDAY: diaSetmana = 3; break;
              case Calendar.THURSDAY: diaSetmana = 4; break;
              case Calendar.FRIDAY: diaSetmana = 5; break;
              case Calendar.SATURDAY: diaSetmana = 6; break;
              case Calendar.SUNDAY: diaSetmana = 7; break;
          }
       
          if(diaSetmana>5) {
              return true; //Considera weekends as holiday
          }  
          
          //Lazy single instance of festius
          ArrayList<BeanFestiu> festius = client.getDatesCollection().getFestius();
       
          //5-11-2011: Bug :: estic comparant sql.Date amb util.Date
          //El problema es que util.Date conserva hores,minuts, etc.
          
          java.util.Date utilDate = cal.getTime();
          Calendar m_cal2 = Calendar.getInstance();
          m_cal2.setTime(utilDate);
          m_cal2.set(Calendar.HOUR_OF_DAY, 0);
          m_cal2.set(Calendar.MINUTE, 0);
          m_cal2.set(Calendar.SECOND, 0);
          m_cal2.set(Calendar.MILLISECOND, 0); 
          java.sql.Date sqlDate = new java.sql.Date(m_cal2.getTime().getTime());
          
          //java.sql.Date mdate = new java.sql.Date( utilDate.getTime() );

          for(BeanFestiu bf: festius){

               if(sqlDate.compareTo(bf.getDesde()) >=0 && sqlDate.compareTo(bf.getFins()) <=0)
               {
                    esfesta = true;
                    break;
              }
          }

        return esfesta;
    }
      
    public boolean esFestiu() //ArrayList<java.sql.Date> festiusFrom, ArrayList<java.sql.Date> festiusTo
    {     
        return isHoliday(m_cal);
    }
    
    public String nextDimecres()
    {
        int nweek = 0;
        java.util.Calendar wend = findNextDimecres(nweek);
        while(isHoliday(wend) && nweek<100)
        {
            nweek +=1;
            wend = findNextDimecres(nweek);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(("dd/MM/yyyy"));
        return sdf.format(wend.getTime());  
       
    }
    
    private java.util.Calendar findNextDimecres(int nweeks)
    {   
          java.util.Date utilDate = m_cal.getTime();
          Calendar m_cal2 = Calendar.getInstance();
          m_cal2.setTime(utilDate);
          m_cal2.set(Calendar.HOUR_OF_DAY, 0);
          m_cal2.set(Calendar.MINUTE, 0);
          m_cal2.set(Calendar.SECOND, 0);
          m_cal2.set(Calendar.MILLISECOND, 0); 
          
          switch(intDiaSetmana)
          {
              case 1:
                  m_cal2.add(Calendar.DAY_OF_MONTH, 2 + nweeks * 7);
                  break;
              case 2:
                  m_cal2.add(Calendar.DAY_OF_MONTH, 7 + nweeks * 7);
                  break;
              case 3:
                  m_cal2.add(Calendar.DAY_OF_MONTH, 7 + nweeks * 7);
                  break;
              case 4:
                  m_cal2.add(Calendar.DAY_OF_MONTH, 6 + nweeks * 7);
                  break;
              case 5:
                  m_cal2.add(Calendar.DAY_OF_MONTH, 5 + nweeks * 7);
                  break;
              case 6:
                  m_cal2.add(Calendar.DAY_OF_MONTH, 4 + nweeks * 7);
                  break;
              case 7:
                  m_cal2.add(Calendar.DAY_OF_MONTH, 3 + nweeks * 7);
                  break;
       
            
          }
              
         return m_cal2;
    }

 
           
}
