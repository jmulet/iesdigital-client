/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.actuacions;

import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 *
 * @author Josep
 **/
public interface IFormulari {
  
    public void setActuacio(Actuacio act);

    public byte getExitCode();
    
    /**
     * 
     * Set the form visible=true or hide=false
     * 
     * @param visible 
     */
    public void setVisible(boolean visible);
    
    /**
     * gets the Updated Map
     * 
     */    
    
    public void dispose();
    
    
    public HashMap<String,Object> getMap();
    
     /**
     * Forces the updated map to be written to database
     * 
     */
    public void updateDocDatabase();
         
     /**
     * Before accept, checks if the form is right
     * returns null if everything is right, explicative errors in any other case
     */
    public String doCheck();
    
 /**
  * check if the limitador dels reports esta activat
  * @return 
  */
    public boolean isLimitador();
    
    /*
     * Retorna la id del report seleccionat
     */
    public BeanFieldReport getSelectedReport();
    
    public void addListenerDocButton(ActionListener listener);

    public void addListenerCloseButtons(ActionListener actionListener);

    public void addListenerAcceptButton(ActionListener actionListener);
 
    public boolean isMainReportGenerated();
    
    public void setMainReportGenerated(boolean generated);
    
}
