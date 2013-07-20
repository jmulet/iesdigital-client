/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.guardies;

/**
 *
 * @author Josep
 * 
 * encapsulates both mati and tarda
 */

public interface BeanSignatura {
    
    public static final int STATE_EMPTY = -1;
    public static final int STATE_UNDEFINED = 0;
    public static final int STATE_SIGNED = 1;
    public static final int STATE_ABSENCE = 2;
    public static final int STATE_OUTING = 3;
     
    public String getAbrev();
    public String getNombre();
    public int[] getH_asArray();
    public java.util.Date[] getHt_asArray();
}
