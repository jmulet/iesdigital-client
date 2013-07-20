/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.guardies;

import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.spaces.BeanGuardZone;
import org.iesapp.clients.iesdigital.spaces.GuardZone;

/**
 *
 * @author Josep
 */
public class GuardiesClient {
    private GuardiesCollection guardiesCollection;
    private final IClient client;

    public GuardiesClient(IClient client) {
        this.client = client;
    }

    //Facade methods
     public GuardZone getGuardZone()
     {
         return new GuardZone((IClient) this);
     }
     public GuardZone getGuardZone(final int id)
     {
         return new GuardZone(id, (IClient) this);
     }
     public GuardZone getGuardZone(final BeanGuardZone bean)
     {
         return new GuardZone(bean, (IClient) this);
     }
      
     public Horari getHorari()
     {
         return new Horari((IClient) this);
     }
     public Horari getHorari(final int id)
     {
         return new Horari(id, (IClient) this);
     }
     public Horari getHorari(final BeanHorari bean)
     {
         return new Horari(bean, (IClient) this);
     }
     
     //Collections
    public GuardiesCollection getGuardiesCollection()
    {
        if(guardiesCollection==null)
        {
            guardiesCollection = new GuardiesCollection(client);
        }
        return guardiesCollection;
    }
    
    public void dispose() {
        guardiesCollection = null;
    }
    
}
