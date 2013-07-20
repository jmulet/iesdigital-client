/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.dates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.database.MyDatabase;
import org.iesapp.database.vscrud.GenericCrud;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class DatesCollection {
    private final IClient client;
    private java.sql.Time[] horesClasse;
    private java.sql.Time[] horesClasse_fi;
    private Avaluacions avaluacions;
    private ArrayList festius;
    
    public DatesCollection(IClient client)
    {
        this.client = client;
    }
    
    private void lazyLoadingHoresClasse()
    {
        ArrayList<BeanHoraClasse> listHoraClasses = listHoraClasses(BeanHoraClasse.CLASSE);
        horesClasse = new java.sql.Time[listHoraClasses.size()];
        horesClasse_fi = new java.sql.Time[listHoraClasses.size()];
        int i = 0;
        for (BeanHoraClasse bhc : listHoraClasses) {
            horesClasse[i] = bhc.getInicio();
            horesClasse_fi[i]= bhc.getFin();
            i += 1;
        }
        listHoraClasses.clear();
    }
    
    //Lazy - single instance method
    public java.sql.Time[] getHoresClase()
    {
        if(horesClasse==null || horesClasse_fi==null)
        {
          lazyLoadingHoresClasse();
        }
        return horesClasse;
    }
    
    //Lazy - single instance method
    public java.sql.Time[] getHoresClase_fi()
    {
        if(horesClasse==null || horesClasse_fi==null)
        {
          lazyLoadingHoresClasse();
        }
        return horesClasse_fi;
    }
    
    public ArrayList<BeanFestiu> listFestius(String order)
    {
        return new GenericCrud(new BeanFestiu(), Festiu.queryForm, client.getMysql()).list(order);
    }
    
    public ArrayList<BeanFestiu> getFestius()
    {
        if(festius ==null)
        {
            festius = new GenericCrud(new BeanFestiu(), Festiu.queryForm, client.getMysql()).list("");
        }
        return festius;
    }
    

    public ArrayList<BeanHoraClasse> listHoraClasses(int tipus)
    {
        String order="";
        if(tipus==BeanHoraClasse.CLASSE)
        {
            order = "idTipoHoras="+BeanHoraClasse.CLASSE;
        }
        else if(tipus==BeanHoraClasse.PATI)
        {
            order = "idTipoHoras="+BeanHoraClasse.PATI;
        }
        else  
        {
            order = "1=1";
        }
        order +=" ORDER BY inicio ASC";
        return new GenericCrud(new BeanHoraClasse(), HoraClasse.queryForm,  client.getMysql()).list(order);
    }

    public Avaluacions getAvaluacions()
    {
        if(avaluacions==null)
        {
            avaluacions = new Avaluacions(client);
        }
        return avaluacions;
    }
    
    //
    //Dispose single instance fields
    //
    public void dispose()
    {
        avaluacions = null;
        horesClasse = null;
        horesClasse_fi = null;
        festius = null;
    }

}


