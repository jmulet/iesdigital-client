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
public class GuardZone {
    private final Crud crud;
    private final BeanGuardZone entity;
    public static final String queryForm = "sig_guardies_zones";
     
    public GuardZone(final IClient client)
    {
        entity = new BeanGuardZone();
        crud = new GenericCrud(entity, queryForm, client.getMysql());
    }
    
    public GuardZone(final int id, final IClient client)
    {
        entity = new BeanGuardZone();
        entity.id = id;
        crud = new GenericCrud(entity, queryForm, client.getMysql());
        crud.load();
    }
    
    public GuardZone(final BeanGuardZone bean, final IClient client)
    {
        entity = bean;
        crud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
     public Crud getCrud() {
        return (Crud) crud;
    }
     
     public BeanGuardZone getEntity()
     {
         return entity;
     }
}
