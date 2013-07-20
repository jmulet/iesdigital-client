/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.anuncis;

import java.util.HashMap;

/**
 *
 * @author Josep
 */
public class AnuncisDefinition {

    protected final static HashMap<Integer, AnuncisDefinition> mapDefined = new HashMap<Integer, AnuncisDefinition>();
    static{
        AnuncisDefinition.mapDefined.put(0, new AnuncisDefinition(0,"Extraescolar","","sortida.gif"));     
        AnuncisDefinition.mapDefined.put(1, new AnuncisDefinition(1,"Reunió","","reunio.gif"));
        AnuncisDefinition.mapDefined.put(2, new AnuncisDefinition(2,"Event","","event.gif"));
        AnuncisDefinition.mapDefined.put(3, new AnuncisDefinition(3,"Novetats","","new.gif"));
        AnuncisDefinition.mapDefined.put(4, new AnuncisDefinition(4,"TIC","","pda.gif"));
        //Add any other desired type here
    }
    
    public static HashMap<Integer, AnuncisDefinition> getMapDefined() {
        return mapDefined;
    }

    //Carrega tots els templates dels tipus d'anuncis definits
   
    protected String documentTemplate="";
    protected int anuncisTypeId = 0;
    protected String anuncisTypeName = "";
    protected String resourceIcon="";     
    
    public AnuncisDefinition()
    {
    }
    
    public AnuncisDefinition(int i, String name, String template, String icon) {
        anuncisTypeId = i;
        anuncisTypeName = name;
        documentTemplate = template;
        resourceIcon = icon;        
    }

    public String getDocumentTemplate() {
        return documentTemplate;
    }

    public void setDocumentTemplate(String documentTemplate) {
        this.documentTemplate = documentTemplate;
    }

    public int getAnuncisTypeId() {
        return anuncisTypeId;
    }

    public void setAnuncisTypeId(int anuncisTypeId) {
        this.anuncisTypeId = anuncisTypeId;
    }

    public String getAnuncisTypeName() {
        return anuncisTypeName;
    }

    public void setAnuncisTypeName(String anuncisTypeName) {
        this.anuncisTypeName = anuncisTypeName;
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }
    
}
