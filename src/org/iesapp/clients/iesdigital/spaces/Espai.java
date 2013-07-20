/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.spaces;

import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.database.vscrud.Crud;
import org.iesapp.database.vscrud.GenericCrud;
 

/**
 *
 * @author Josep
 */
public class Espai{
    protected final GenericCrud genericCrud;
    private final BeanEspai entity;
    public static final String queryForm = "sig_espais";
     
    public Espai(final IClient client)
    {
        entity = new BeanEspai();
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public Espai(final int id, final IClient client)
    {
        entity = new BeanEspai();
        entity.id = id;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
        genericCrud.load();
    }
    
    public Espai(final BeanEspai bean, final IClient client)
    {
        entity = bean;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }


    public Crud getCrud() {
        return (Crud) genericCrud;
    }
    
    public BeanEspai getEntity(){
        return entity;
    }
    
    
}
