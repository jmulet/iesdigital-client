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
public class SignaturaComentari {
    protected final GenericCrud genericCrud;
    public static final String queryForm = "sig_signatures_comentaris";
    public final BeanSignaturaComentari entity;
     
    public SignaturaComentari(final IClient client)
    {
        entity = new BeanSignaturaComentari();
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public SignaturaComentari(final int id, final IClient client)
    {
        entity = new BeanSignaturaComentari();
        entity.setId(id);
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
        genericCrud.load();
    }
    
    public SignaturaComentari(final BeanSignaturaComentari bean, final IClient client)
    {
        entity = bean;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
 
    public Crud getCrud() {
        return (Crud) genericCrud;
    }
    
    public BeanSignaturaComentari getEntity()
    {
        return entity;
    }
}
