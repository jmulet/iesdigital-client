/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;

import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.actuacions.FactoryRules;

/**
 *
 * @author Josep
 */
public class FitxesClient {
    private Medicaments medicaments;
    private final IClient client;
    private FactoryRules factoryRules;
    private FitxesUtils fitxesUtils;
    private SGDImporterConfig sgdImporterConfig;

    public FitxesClient(IClient client) {
        this.client = client;
    }

    //FACADE-METHODS
    
    //SINGLE-INSTANCE UTILITY CLASSES
    public FactoryRules getFactoryRules()
    {
        if(factoryRules==null)
        {
            factoryRules = new FactoryRules(client);
        }
        return factoryRules;
    }
    
    public Medicaments getMedicaments()
    {
        if(medicaments==null)
        {
            medicaments = new Medicaments(client);
        }
        return medicaments;
    }
    
    public FitxesUtils getFitxesUtils()
    {
        if(fitxesUtils==null)
        {
            fitxesUtils = new FitxesUtils(client);
        }
        return fitxesUtils;
    }
    
    public SGDImporterConfig getSGDImporterConfig()
    {
        if(sgdImporterConfig==null)
        {
            sgdImporterConfig = new SGDImporterConfig(client);
        }
        return sgdImporterConfig;
    }
    
    public void dispose() {
        medicaments = null;
        factoryRules = null;
        sgdImporterConfig = null;
    }
    
}
