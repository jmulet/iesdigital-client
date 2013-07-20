/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.dates;

import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.database.vscrud.Crud;
import org.iesapp.database.vscrud.GenericCrud;
 

/**
 *
 * @author Josep
 */
public class Festiu{
    protected final GenericCrud crud;
    protected final BeanFestiu entity;
    public static final String queryForm = "sig_festius";
     
    public Festiu(final IClient client)
    {
        entity = new BeanFestiu();
        crud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public Festiu(final int id, final IClient client)
    {
        entity = new BeanFestiu();
        entity.id = id;
        crud = new GenericCrud(entity, queryForm,client.getMysql());
        crud.load();
    }
    
    public Festiu(final BeanFestiu bean, final IClient client)
    {
        entity = bean;
        crud = new GenericCrud(entity, queryForm,client.getMysql());
    }

    public Crud getCrud() {
        return (Crud) crud;
    }

    public BeanFestiu getEntity() {
        return entity;
    }

    
    
}
