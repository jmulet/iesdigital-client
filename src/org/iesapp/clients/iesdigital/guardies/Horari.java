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
public class Horari {
    protected final GenericCrud genericCrud;
    public static final String queryForm = "sig_horaris INNER JOIN sig_professorat ON sig_horaris.prof=sig_professorat.abrev";
    public final BeanHorari entity;
     
    public Horari(final IClient client)
    {
        entity = new BeanHorari();
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public Horari(final int id, final IClient client)
    {
        entity = new BeanHorari();
        entity.id = id;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
        genericCrud.load();
    }
    
    public Horari(final BeanHorari bean, final IClient client)
    {
        entity = bean;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
 
    public Crud getCrud() {
        return (Crud) genericCrud;
    }
    
    public BeanHorari getEntity()
    {
        return entity;
    }
}
