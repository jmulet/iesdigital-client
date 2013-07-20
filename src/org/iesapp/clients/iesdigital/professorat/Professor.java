/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.professorat;

import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.database.vscrud.Crud;
import org.iesapp.database.vscrud.GenericCrud;

/**
 *
 * @author Josep
 */
public class Professor {
    protected final GenericCrud crud;
    protected final BeanProfessor entity;
    public static final String queryForm = "sig_professorat LEFT JOIN usu_usuari ON sig_professorat.abrev=usu_usuari.Nom";
     
    public Professor(final IClient client)
    {
        entity = new BeanProfessor();
        crud = new GenericCrud(entity, queryForm,client.getMysql());
    }
    
    public Professor(final int id, final IClient client)
    {
        entity = new BeanProfessor();
        entity.setId(id);
        crud = new GenericCrud(entity, queryForm,client.getMysql());
        crud.load();
    }
    
    public Professor(final BeanProfessor bean, final IClient client)
    {
        entity = bean;
        crud = new GenericCrud(entity, queryForm,client.getMysql());
    }

    public Crud getCrud() {
        return (Crud) crud;
    }

    public BeanProfessor getEntity() {
        return entity;
    }

}
