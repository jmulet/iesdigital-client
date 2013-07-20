/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.spaces;

import java.util.ArrayList;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.database.vscrud.GenericCrud;

/**
 *
 * @author Josep
 */
public class SpacesCollection {
    private final IClient client;
    
    public SpacesCollection(IClient client)
    {
        this.client = client;
    }
    
    public ArrayList<BeanGuardZone> listGuardZones(String orderCondition)
    {
        return new GenericCrud(new BeanGuardZone(), GuardZone.queryForm, client.getMysql()).list(orderCondition); 
    }
    
    
    public ArrayList<BeanEspai> listEspais(String orderCondition)
    {
        return new GenericCrud(new BeanEspai(), Espai.queryForm, client.getMysql()).list(orderCondition);
    }
}
