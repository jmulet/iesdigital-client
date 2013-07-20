/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital;
 
import org.iesapp.clients.iesdigital.anuncis.AnuncisClient;
import org.iesapp.clients.iesdigital.dates.DatesCollection;
import org.iesapp.clients.iesdigital.fitxes.FitxesClient;
import org.iesapp.clients.iesdigital.guardies.GuardiesClient;
import org.iesapp.clients.iesdigital.missatgeria.MissatgeriaCollection;
import org.iesapp.clients.iesdigital.professorat.IUser;
import org.iesapp.clients.iesdigital.professorat.ProfessoratData;
import org.iesapp.clients.iesdigital.reserves.ReservesClient;
import org.iesapp.database.MyDatabase;

/**
 *
 * @author Josep
 */
public interface IClient {
     public MyDatabase getMysql();
     public MyDatabase getSgd();
     public IUser getUserInfo();
     public ICoreData getICoreData();
     
     //Must allow access to utility classes and sub-clients
     public DatesCollection getDatesCollection();
     public ProfessoratData getProfessoratData();
     public GuardiesClient getGuardiesClient();
     public ReservesClient getReservesClient();
     public AnuncisClient getAnuncisClient();
     public FitxesClient getFitxesClient();
     public MissatgeriaCollection getMissatgeriaCollection();
     
}
