/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.guardies;

import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.database.vscrud.Crud;
import org.iesapp.database.vscrud.GenericCrud;
 

/**
 *
 * @author Josep
 */
public class HorariGuardia {
    protected final GenericCrud genericCrud;
    public static final String fromQuery = "sig_guardies INNER JOIN sig_guardies_zones ON sig_guardies.lloc=sig_guardies_zones.lloc";
    public final BeanHorariGuardia entity;
     
    public HorariGuardia(final IClient client)
    {
        entity = new BeanHorariGuardia();
        genericCrud = new GenericCrud(entity, fromQuery, client.getMysql());
    }
    
    public HorariGuardia(final int id, final IClient client)
    {
        entity = new BeanHorariGuardia();
        entity.id = id;
        genericCrud = new GenericCrud(entity, fromQuery,client.getMysql());
        genericCrud.load();
    }
    
    public HorariGuardia(final BeanHorariGuardia bean, final IClient client)
    { 
         entity = bean;
        genericCrud = new GenericCrud(entity, fromQuery, client.getMysql());
    }
 
    public Crud getCrud() {
        return (Crud) genericCrud;
    }
    
    public BeanHorariGuardia getEntity()
    {
        return entity;
    }
    
}
