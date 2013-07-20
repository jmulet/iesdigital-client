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
public class SignaturaTarda {
    protected final GenericCrud genericCrud;
    public static final String queryForm = "sig_signatures_tarda INNER JOIN sig_professorat ON sig_signatures_tarda.abrev=sig_professorat.abrev";
    public final BeanSignaturaTarda entity;
     
    public SignaturaTarda(final IClient client)
    {
        entity = new BeanSignaturaTarda();
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public SignaturaTarda(final int id, final IClient client)
    {
        entity = new BeanSignaturaTarda();
        entity.setId(id);
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
        genericCrud.load();
    }
    
    public SignaturaTarda(final BeanSignaturaTarda bean, final IClient client)
    {
        entity = bean;
        genericCrud = new GenericCrud(entity, queryForm,client.getMysql());
    }
 
    public Crud getCrud() {
        return (Crud) genericCrud;
    }
    
    public BeanSignaturaTarda getEntity()
    {
        return entity;
    }
}
