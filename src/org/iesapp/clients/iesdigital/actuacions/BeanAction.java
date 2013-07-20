/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.actuacions;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Josep
 */
public class BeanAction {

    protected int id;
    protected boolean registerIesDigital=false; //enum('N','S') NOT NULL
    protected String tipus; //varchar(255) NOT NULLtipus d'incidencia per la cerca a iesdigital
    protected String dates; //enum('$VALUE','$TODAY','$ANYACADEMIC','$AVALUACIO') NOT NULLdates o intervals de dates
    protected String actualDates;
    protected boolean registerSGD=false; //enum('N','S') NOT NULL
    protected String simboloIncidencia; //varchar(255) NOT NULLSimbol de la incidencia en el sistema SGD
    protected String observaciones; //varchar(255) NOT NULLobservacions
    protected int incidenciasSesion=1; //tinyint(11) NOT NULLNombre d'incidencies del tipus simbol que s'ha de posar cada dia
    protected boolean todoElDia=false; //enum('N','S') NOT NULLSi la incidencia s'ha de possar a totes les hores de cada dia, p.e., EXPULSIO   

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the registerIesDigital
     */
    public boolean isRegisterIesDigital() {
        return registerIesDigital;
    }

    /**
     * @param registerIesDigital the registerIesDigital to set
     */
    public void setRegisterIesDigital(boolean registerIesDigital) {
        this.registerIesDigital = registerIesDigital;
    }

    /**
     * @return the tipus
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * @param tipus the tipus to set
     */
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    /**
     * @return the dates
     */
    public String getDates() {
        return dates;
    }

    /**
     * @param dates the dates to set
     */
    public void setDates(String dates) {
        this.dates = dates;
    }

    /**
     * @return the registerSGD
     */
    public boolean isRegisterSGD() {
        return registerSGD;
    }

    /**
     * @param registerSGD the registerSGD to set
     */
    public void setRegisterSGD(boolean registerSGD) {
        this.registerSGD = registerSGD;
    }

    /**
     * @return the simboloIncidencia
     */
    public String getSimboloIncidencia() {
        return simboloIncidencia;
    }

    /**
     * @param simboloIncidencia the simboloIncidencia to set
     */
    public void setSimboloIncidencia(String simboloIncidencia) {
        this.simboloIncidencia = simboloIncidencia;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the incidenciasDia
     */
    public int getIncidenciasSesion() {
        return incidenciasSesion;
    }

    /**
     * @param incidenciasDia the incidenciasDia to set
     */
    public void setIncidenciasDia(int incidenciasSesion) {
        this.incidenciasSesion = incidenciasSesion;
    }

    /**
     * @return the todoElDia
     */
    public boolean isTodoElDia() {
        return todoElDia;
    }

    /**
     * @param todoElDia the todoElDia to set
     */
    public void setTodoElDia(boolean todoElDia) {
        this.todoElDia = todoElDia;
    }

    /**
     * @return the actualDates
     */
    public String getActualDates() {
        return actualDates;
    }

    /**
     * @param actualDates the actualDates to set
     */
    public void setActualDates(String actualDates) {
        this.actualDates = actualDates;
    }

    public void setAttributes(NamedNodeMap attributes) {
        if(attributes==null)
        {
            return;
        }
        String target = attributes.getNamedItem("target").getNodeValue();
        if(target==null || target.isEmpty())
        {
            return;
        }
        
        if(target.equalsIgnoreCase("iesdigital")) {
            this.registerIesDigital =true;
        }
        if(target.equalsIgnoreCase("sgd")) {
            this.registerSGD = true;
        }
        
        for(int i=0; i<attributes.getLength(); i++)
        {
            Node item = attributes.item(i);
            if(item.getNodeName().equals("dates"))
            {
                this.dates = item.getNodeValue();
            }
            else if(item.getNodeName().equals("dates"))
            {
                this.dates = item.getNodeValue();
            }
            else if(item.getNodeName().equals("type"))
            {
                this.tipus = item.getNodeValue();
            }
            else if(item.getNodeName().equals("simbol"))
            {
                this.simboloIncidencia = item.getNodeValue();
            }
            else if(item.getNodeName().equals("description"))
            {
                this.observaciones = item.getNodeValue().replaceAll("<", "(").replaceAll(">", ")");
            }
            else if(item.getNodeName().equals("incpersession"))
            {
                this.incidenciasSesion = Integer.parseInt( item.getNodeValue() );
            }
            else if(item.getNodeName().equals("allDay"))
            {
                this.todoElDia = item.getNodeValue().equals("yes");
            }
                      
           
        }
    }
}
