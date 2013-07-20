/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.guardies;

/**
 *
 * @author Josep
 */

/* camps:
 *
 * text: el text que apareix en la cel.la
 * codig: l'abreviatura o codig de cada professor p.e. MA4
 * status: 0 = pendent, 1=signat, 2=falta, 3=sortida
 * type: -1=hora buida, 0=normal, 1=guardia, 2=camp que es filtrat
 * 
 */
public class CellModel {

    public static final int STATUS_PENDENT = 0;
    public static final int STATUS_SIGNAT = 1;
    public static final int STATUS_FALTA = 2;
    public static final int STATUS_SORTIDA = 3;
    
    public static final int TYPE_HORABUIDA = -1;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_GUARDIA = 1;
    public static final int TYPE_CAMPFILTRAT = 2;
    
    public String text;
    public String codig;
    public int status;
    public int type;
    public int feina;
    public String comment;

    //Default 
    public CellModel() {
        text = "";
        codig = "";
        status = STATUS_PENDENT;
        type = TYPE_HORABUIDA;
        comment = "";
        feina = 0;
    }

    public CellModel(int g, String t, String c, int s) {
        text = t;
        codig = c;
        status = s;
        type = g;
        comment = "";
        feina = 0;
  }

    public String getText() {
        return text;
    }

    public String getCodig() {
        return codig;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public void setText(String txt) {
        text = noNull(txt);
    }

    public void setCodig(String txt) {
        codig = noNull(txt);
    }

    public void setStatus(int st) {
        status = st;
    }

    public void setType(int b) {
        type = b;
    }

    public void setFeina(int val) {
        feina = val;
    }

    public void setComentari(String val) {
        comment = val;
    }

    public int getFeina() {
        return feina;
    }

    public String getComment() {
        return comment;
    }

    public String noNull(String val) {
        if (val == null) {
            return "";
        } else {
            return val;
        }
    }
    
    
    @Override
    public String toString()
    {
        return "text="+text + ", codig ="+codig+", type="+type+", comment="+
                comment+", feina="+feina+", status="+status;
    }
}
