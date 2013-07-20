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
public class HoraClasse{
    protected final GenericCrud crud;
    protected final BeanHoraClasse entity;
    public static final String queryForm = "sig_hores_classe";
     
    public HoraClasse(final IClient client)
    {
        entity = new BeanHoraClasse();
        crud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public HoraClasse(final int id, final IClient client)
    {
        entity = new BeanHoraClasse();
        entity.id = id;
        crud = new GenericCrud(entity, queryForm,client.getMysql());
        crud.load();
    }
    
    public HoraClasse(final BeanHoraClasse bean, final IClient client)
    {
        entity = bean;
        crud = new GenericCrud(entity, queryForm,client.getMysql());
    }

     
    public Crud getCrud() {
        return (Crud) crud;
    }
    
    public BeanHoraClasse getEntity()
    {
        return entity;
    }
    
}
