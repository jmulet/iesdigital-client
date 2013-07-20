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
public class SenseGuardia {
    protected final GenericCrud genericCrud;
    public static final String queryForm = "sig_senseguardia";
    public final BeanSenseGuardia entity;
     
    public SenseGuardia(final IClient client)
    {
        entity = new BeanSenseGuardia();
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public SenseGuardia(final int id, final IClient client)
    {
        entity = new BeanSenseGuardia();
        entity.id = id;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
        genericCrud.load();
    }
    
    public SenseGuardia(final BeanSenseGuardia bean, final IClient client)
    {
        entity = bean;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
 
    public Crud getCrud() {
        return (Crud) genericCrud;
    }
    
    public BeanSenseGuardia getEntity()
    {
        return entity;
    }
}
