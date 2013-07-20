/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.reserves;

import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.database.vscrud.Crud;
import org.iesapp.database.vscrud.GenericCrud;
 

/**
 *
 * @author Josep
 */
public class Material {
    protected final GenericCrud genericCrud;
    public static final String queryForm = "sig_reserves_material";
    public final BeanMaterial entity;
     
    public Material(final IClient client)
    {
        entity = new BeanMaterial();
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public Material(final int id, final IClient client)
    {
        entity = new BeanMaterial();
        entity.id = id;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
        genericCrud.load();
    }
    
    public Material(final BeanMaterial bean, final IClient client)
    {
        entity = bean;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
 
    public Crud getCrud() {
        return (Crud) genericCrud;
    }
    
    public BeanMaterial getEntity()
    {
        return entity;
    }
}
