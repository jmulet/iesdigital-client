/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.reserves;

import org.iesapp.clients.iesdigital.IClient;

/**
 *
 * @author Josep
 */
public class ReservesClient {
    private final IClient client;
    private ReservesCollection reservesCollection;

    public ReservesClient(IClient client) {
        this.client = client;
    }
    
    public ReservesCollection getReservesCollection()
    {
        if(reservesCollection==null)
        {
            reservesCollection = new ReservesCollection(client);
        }
        return reservesCollection;
    }

    public void dispose() {
        reservesCollection = null;
    }
    
}
