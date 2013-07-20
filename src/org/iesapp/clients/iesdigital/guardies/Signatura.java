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
public class Signatura {
    protected final GenericCrud genericCrud;
    public static final String queryForm = "sig_signatures INNER JOIN sig_professorat ON sig_signatures.abrev=sig_professorat.abrev";
    public final BeanSignaturaMati entity;
     
    public Signatura(final IClient client)
    {
        entity = new BeanSignaturaMati();
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public Signatura(final int id, final IClient client)
    {
        entity = new BeanSignaturaMati();
        entity.setId(id);
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
        genericCrud.load();
    }
    
    public Signatura(final BeanSignaturaMati bean, final IClient client)
    {
        entity = bean;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
 
    public Crud getCrud() {
        return (Crud) genericCrud;
    }
    
    public BeanSignaturaMati getEntity()
    {
        return entity;
    }
}
