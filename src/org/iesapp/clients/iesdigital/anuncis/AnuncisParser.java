/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.anuncis;

import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class AnuncisParser {
    private static int type;

    public static AnunciBean getBean(final String doc) {
        AnunciBean bean = new AnunciBean();
        HashMap<String, String> map = StringUtils.StringToHash(doc, ";");

        if (map.containsKey("title")) {
            bean.setTitle(map.get("title"));
        }
        if (map.containsKey("description")) {
            bean.setDescription(map.get("description"));
        }
        if (map.containsKey("body")) {
            bean.setBody(map.get("body"));
        }
        if (map.containsKey("author")) {
            bean.setAuthor(map.get("author"));
        }

        bean.type = AnunciBean.MISC;
        AnuncisDefinition anunciDef = new AnuncisDefinition();
        if (map.containsKey("type")) {
            int bit = Integer.parseInt(map.get("type"));
            anunciDef = AnuncisDefinition.mapDefined.get(bit);
            bean.setType(bit);
            bean.setTypeName(anunciDef.anuncisTypeName);
        }

        bean.setIconType(AnunciBean.RESOURCE_ICON);
        bean.setIconName(anunciDef.getResourceIcon());

        if (map.containsKey("iconType")) {
            bean.setIconType(map.get("iconType"));
        }
        if (map.containsKey("iconName")) {
            bean.setIconName(map.get("iconName"));
        }
        if (map.containsKey("postdate")) {
            String sdate = map.get("postdate");
            bean.setPostdate(parseDateFromString(sdate));
        }
        if (map.containsKey("eventdate")) {
            String sdate = map.get("eventdate");
            bean.setEventdate(parseDateFromString(sdate));
        }
        if (map.containsKey("eventdate2")) {
            String sdate = map.get("eventdate2");
            bean.setEventdate2(parseDateFromString(sdate));
        }
        if (map.containsKey("professorat")) {
            bean.setProfessorat(map.get("professorat"));
        }
        if (map.containsKey("hIndex0")) {
            int intg = (int) Double.parseDouble(map.get("hIndex0"));
            bean.sethIndex0(intg);
        }
        if (map.containsKey("hIndex1")) {
            int intg = (int) Double.parseDouble(map.get("hIndex1"));
            bean.sethIndex1(intg);
        }
        if (map.containsKey("collapse")) {
            byte intg = (byte) Double.parseDouble(map.get("collapse"));
            bean.setCollapse(intg);
        }
        if (map.containsKey("showDateInTitle")) {
            byte intg = (byte) Double.parseDouble(map.get("showDateInTitle"));
            bean.setShowDateInTitle(intg);
        }
        if (map.containsKey("para")) {
            bean.setPara(map.get("para"));
        }

        bean.setNou(false);

        return bean;
    }
    
    
    public static java.util.Date parseDateFromString(String sdate)
    {
        if (sdate==null || sdate.isEmpty() || sdate.equalsIgnoreCase("null") ) {
            return null;
        }
        java.text.DateFormat formatter = null;
    
        if (sdate.contains("/")) {
            formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        } else if (sdate.contains("-")) {
            formatter = new java.text.SimpleDateFormat("dd-MM-yyyy");
        }

        java.util.Date date = null;
        if (!sdate.isEmpty()) {
            try {
                date = (java.util.Date) formatter.parse(sdate);
            } catch (ParseException ex) {
                Logger.getLogger(AnuncisParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return date;
    }
}
