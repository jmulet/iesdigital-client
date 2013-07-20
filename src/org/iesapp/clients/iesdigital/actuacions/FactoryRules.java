/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.actuacions;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 *
 * @author Josep
 */
public class FactoryRules {
    public static final String ASSISTENCIA="ASSISTENCIA";
    public static  final String CONVIVENCIA="CONVIVENCIA";
    public static  final String SMS="SMS";
    public static  final String CARTA="CARTA";
    public static  final byte ALL=0;
    public static  final byte PENDENTS=1;
    private static HTMLDocument logger;
    private final IClient client;
  
    //Please use iesClient Facade to ensure a single instance per session
    public FactoryRules(IClient client)
    {
        this.client = client;
    }
    
    public BeanRules getRuleXml(int idRule, String ambit, String estudis)
    {
       BeanRules bean = new BeanRules();
       int idSubRule = 0;
       String SQL1 = "SELECT *, taf.id as idSubRule FROM tuta_actuacions as ta INNER JOIN tuta_actuacions_fields as taf "
               + " ON ta.id = taf.idRule WHERE  (ambits LIKE '%"+ambit+"%' OR ambits='*') "
               + " AND (estudis LIKE '%"+estudis+"%' OR estudis='*') AND ta.id="+idRule;
        
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);    
            if(rs1!=null && rs1.next())
            {
              bean.ambit = ambit;
              bean.idRule = idRule;
              idSubRule = rs1.getInt("idSubRule");
              bean.tipus = rs1.getString("tipus");
              bean.descripcio = rs1.getString("actuacio");
              bean.nomesAdmin = rs1.getInt("nomesAdmin")>0;
              bean.forRoles = rs1.getString("roles");
              bean.simbol = rs1.getString("simbol");
              bean.threshold = rs1.getInt("threshold");
              bean.vrepeticio = rs1.getInt("vrepeticio");
              bean.vmax = rs1.getInt("vmax");
              bean.alertActuacioPendents = rs1.getString("alertActuacionsPendents").equalsIgnoreCase("S");              
              bean.repetir = rs1.getString("repetir").equalsIgnoreCase("S");
              bean.instruccions = rs1.getString("instruccions");
              bean.reglament = rs1.getString("reglament");
              String txt = rs1.getString("class");
              bean.className = (txt!=null && !txt.isEmpty() && !txt.contains("NULL"))?txt:null;
              bean.form = StringUtils.noNull(rs1.getString("xmlForm"));
              bean.setIdFieldSet(rs1.getInt("idFieldSet"));
              bean.minAge = rs1.getInt("minAge");
              bean.maxAge = rs1.getInt("maxAge");
              bean.autoTancar = rs1.getString("autoTancar").equalsIgnoreCase("S");
              bean.enviamentCarta = rs1.getString("enviamentCarta");
              bean.setEnviamentSMS(rs1.getString("enviamentSMS"));
              bean.alert = rs1.getString("alert");
              bean.setDescripcioLlarga(rs1.getString("descripcio"));
              if(bean.descripcioLlarga==null || bean.descripcioLlarga.isEmpty())
              {
                  bean.descripcioLlarga = bean.descripcio;
              }
              bean.instancesPolicy = rs1.getString("instancesPolicy");
              bean.registerInFitxaAlumne = rs1.getString("registerInFitxaAlumne").equalsIgnoreCase("S");
           
              String tmp = rs1.getString("equivalencies");
              if(tmp!=null && !tmp.isEmpty())
              {
                bean.equivalencies = new ArrayList<Integer>();
                ArrayList<String> parsed = StringUtils.parseStringToArray(tmp, ",", StringUtils.CASE_INSENSITIVE);
                for(String s: parsed)
                {
                    bean.equivalencies.add( Integer.parseInt(s));
                }
              }
                          
              tmp = rs1.getString("requireClosed");
              if(tmp!=null && !tmp.isEmpty())
              {
                bean.requiredClosed = new ArrayList<Integer>();
                ArrayList<String> parsed = StringUtils.parseStringToArray(tmp, ",", StringUtils.CASE_INSENSITIVE);
                for(String s: parsed)
                {
                    bean.requiredClosed.add( Integer.parseInt(s) );
                }
              }
              
              String tmp2 = rs1.getString("requireCreated");
               
              if(tmp2!=null && !tmp2.isEmpty())
              {
                bean.requiredCreated = new ArrayList<Integer>();
                ArrayList<String> parsed = StringUtils.parseStringToArray(tmp2, ",", StringUtils.CASE_INSENSITIVE);
                for(String s: parsed)
                {
                    bean.requiredCreated.add( Integer.parseInt(s) );
                }
              }
              //bean.form = rs1.getString("xmlForm"); //already up
              
              //Extension of beanRule with hashmap
              //This map must be parsed
              String formExt = StringUtils.noNull(rs1.getString("form"));
              bean.extensions = StringUtils.StringToHash(formExt, ";");
             // //System.out.println("more parameters:: "+bean.extensions);
            }
            else
            {
                //System.out.println("EMPTY RS SQL->"+SQL1);
            }
            
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bean.fields = new ArrayList<BeanFieldSet>();
        bean.setSelectableGroups(new ArrayList<String>());
        
        //Parseja el formulari xml
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	DocumentBuilder b;
        try {
            b = f.newDocumentBuilder();
            InputSource is = new InputSource();
            String str = StringUtils.noNull(bean.form);
            if(str.isEmpty())
            {
                //System.out.println( "EMPTY form from "+bean.idRule+" "+ bean.descripcio);
            }
            is.setCharacterStream(new StringReader(str));
            Document doc = b.parse(is);
            doc.normalize();
            
            NodeList root = doc.getElementsByTagName("root");
            NodeList elements = root.item(0).getChildNodes();
            
            for (int i = 0; i < elements.getLength(); i++) {
                Node item = elements.item(i);
                if(item.getNodeName().startsWith("#"))
                {
                    continue;
                }
                String fieldType = item.getNodeName();
                
                if(fieldType.equals(BeanFieldSet.TAG_CATEGORY))
                {
                    BeanCategory category = new BeanCategory();
                    category.setAttributes(item.getAttributes());
                    category.setParent("");
                    bean.getListCategories().put(category.category, category);
                    NodeList childNodes = item.getChildNodes();                    
                    for(int k=0; k<childNodes.getLength(); k++ )
                    {
                        checkFIELD(childNodes.item(k), bean, category.category);
                    }
                    
                }
                else
                {
                    checkFIELD(item, bean, "");
                }
            }
         Element element = getLogger().getElement("body");
        getLogger().insertBeforeEnd(element, "<div class='date'>"+new java.util.Date() +"</div><div class='select'>Form parsed with success.</div><br>");
        } catch (Exception ex) {
            Element element = getLogger().getElement("body");
            try {
                getLogger().insertBeforeEnd(element, "<div class='date'>"+new java.util.Date() +"</div><div class='error'>"+ex+"</div><br>");
            } catch (Exception ex1) {
                Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
        }
	
        //Cerca si te reports
        //bean.listReports = loadReports(idRule, ambit, estudis);
       //La cerca de reports es fa ara segons la idSubRule o la id de la taula tuta_actuacions_fields
        bean.listReports = loadReports(idSubRule);
        
        return bean;
        
    }
    
//    public BeanRules getRule(int idRule, String ambit, String estudis)
//    {
//       BeanRules bean = new BeanRules();
//       int idSubRule = 0;
//       String SQL1 = "SELECT *, taf.id as idSubRule FROM tuta_actuacions as ta INNER JOIN tuta_actuacions_fields as taf "
//               + " ON ta.id = taf.idRule WHERE  (ambits LIKE '%"+ambit+"%' OR ambits='*') "
//               + " AND (estudis LIKE '%"+estudis+"%' OR estudis='*') AND ta.id="+idRule;
//          try {
//            Statement st = client.getMysql().createStatement();
//            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st); 
//            if(rs1!=null && rs1.next())
//            {
//              bean.ambit = ambit;
//              bean.idRule = idRule;
//              idSubRule = rs1.getInt("idSubRule");
//              bean.tipus = rs1.getString("tipus");
//              bean.descripcio = rs1.getString("actuacio");
//              bean.nomesAdmin = rs1.getString("nomesAdmin").equalsIgnoreCase("S");
//              bean.simbol = rs1.getString("simbol");
//              bean.threshold = rs1.getInt("threshold");
//              bean.repetir = rs1.getString("repetir").equalsIgnoreCase("S");
//              bean.instruccions = rs1.getString("instruccions");
//              bean.reglament = rs1.getString("reglament");
//              bean.className = rs1.getString("class");
//              bean.form = rs1.getString("xmlForm");
//              bean.setIdFieldSet(rs1.getInt("idFieldSet"));
//              bean.minAge = rs1.getInt("minAge");
//              bean.maxAge = rs1.getInt("maxAge");
//              bean.autoTancar = rs1.getString("autoTancar").equalsIgnoreCase("S");
//              bean.enviamentCarta = rs1.getString("enviamentCarta");
//              bean.setEnviamentSMS(rs1.getString("enviamentSMS"));
//              bean.alert = rs1.getString("alert");
//              bean.setDescripcioLlarga(rs1.getString("descripcio"));
//              if(bean.descripcioLlarga==null || bean.descripcioLlarga.isEmpty())
//              {
//                  bean.descripcioLlarga = bean.descripcio;
//              }
//              bean.instancesPolicy = rs1.getString("instancesPolicy");
//              bean.registerInFitxaAlumne = rs1.getString("registerInFitxaAlumne").equalsIgnoreCase("S");
//           
//              String tmp = rs1.getString("equivalencies");
//              if(tmp!=null && !tmp.isEmpty())
//              {
//                bean.equivalencies = new ArrayList<Integer>();
//                ArrayList<String> parsed = StringUtils.parseStringToArray(tmp, ",", StringUtils.CASE_INSENSITIVE);
//                for(String s: parsed)
//                {
//                    bean.equivalencies.add( Integer.parseInt(s));
//                }
//              }
//              
//                
//              tmp = rs1.getString("requireClosed");
//              if(tmp!=null && !tmp.isEmpty())
//              {
//                bean.requiredClosed = new ArrayList<Integer>();
//                ArrayList<String> parsed = StringUtils.parseStringToArray(tmp, ",", StringUtils.CASE_INSENSITIVE);
//                for(String s: parsed)
//                {
//                    bean.requiredClosed.add( Integer.parseInt(s) );
//                }
//              }
//            }
//            
//            if(rs1!=null) {
//                rs1.close();
//                st.close();
//            }
//            
//            
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //Cerca si té camps selectables en group
//        bean.selectableGroups = new ArrayList<String>();
//        SQL1 = "SELECT GROUP_CONCAT(taf.fieldName) AS selectableGroup FROM tuta_actuacions_fieldsgroup AS fg INNER JOIN tuta_actuacions_fieldsets AS taf ON "+
//               "taf.idFieldSet=fg.idFieldSets AND taf.id=fg.idField WHERE idFieldSets="+bean.idFieldSet+"  GROUP BY fg.idGroup ";
//        //System.out.println(SQL1);
//         try {
//            Statement st2 = client.getMysql().createStatement();
//            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st2); 
//            while(rs1!=null && rs1.next())
//            {
//                bean.selectableGroups.add(rs1.getString(1));
//            }
//            if(rs1!=null) {
//                rs1.close();
//                st2.close();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        //Cerca si te reports
//       // bean.listReports = loadReports(idRule, ambit, estudis);
//         bean.listReports = loadReports(idSubRule);
//         
//        //Load the fieldSet
//        bean.setFields( FactoryRules.getFields(idRule, ambit, estudis, bean.descripcio) );
//        
//        return bean;
//       
//      
//    }
//            
    
    /**
     * 
     * unsused
     * @param idRule
     * @param ambit
     * @param estudis
     * @param descriptionRule
     * @return 
     */
//     public ArrayList<BeanFieldSet> getFields(final int idRule, final String ambit, final String estudis, final String descriptionRule)
//     {
//        ArrayList<BeanFieldSet> list = new ArrayList<BeanFieldSet>();
//        
//        String SQL1 = "SELECT taf.idRule, fs.* FROM tuta_actuacions_fields AS taf INNER JOIN "
//                + " tuta_actuacions_fieldsets AS fs ON fs.idFieldset=taf.idFieldSet WHERE  "
//                + " (taf.ambits LIKE '%"+ambit+"%' OR taf.ambits='*') "
//                + " AND (taf.estudis LIKE '%"+estudis+"%' OR taf.estudis='*') AND taf.idRule="+idRule+" ORDER BY fs.ordre";
//         try {
//            Statement st = client.getMysql().createStatement();
//            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st); 
//            while(rs1!=null && rs1.next())
//            {
//                BeanFieldSet bean = new BeanFieldSet();
//                bean.editable = rs1.getString("editable");
//                bean.setVisible(rs1.getString("visible").equalsIgnoreCase("S"));
//                bean.fieldDescription = rs1.getString("fieldDescription");
//                bean.fieldIni = rs1.getString("fieldIni");
//                bean.fieldName = rs1.getString("fieldName");
//                String tmp = rs1.getString("fieldRenderClass");
//                String className = tmp;
//                HashMap<String,Object> iniparam = null;
//                
//                //Gestiona passar parametres a la classe renderClass
//                if(tmp!=null && tmp.contains("{"))
//                {
//                     className = StringUtils.BeforeFirst(tmp, "{");
//                     String tmp2 = StringUtils.AfterFirst(tmp, "{").replace("}", "");
//                     //Convert ini string to a map
//                     iniparam = StringUtils.StringToHash(tmp2, "&", false);
//                     //System.out.println("1....."+iniparam);
//                     //Convert map values to correct type
//                     for(String key: iniparam.keySet())
//                     {
//                         String value = (String) iniparam.get(key);
//                         if(value.startsWith("$"))
//                         {
//                             //System.out.println("2.....replace with resource"+Actuacio.resourceMap);
//                             //Replace with value from resourceMap
//                             if(Actuacio.resourceMap!=null && Actuacio.resourceMap.containsKey(value))
//                             {
//                                 iniparam.put(key, Actuacio.resourceMap.get(value));
//                             }
//                         }
//                         else if(value.startsWith("("))
//                         {
//                             //Replace list with arraylist
//                             //System.out.println("2.....replace with list");
//                             String txt2 = value.replace("(", "").replace(")","");
//                             ArrayList<String> parsed = StringUtils.parseStringToArray(txt2, ",", StringUtils.CASE_INSENSITIVE);
//                             iniparam.put(key, parsed);
//                         }
//                         else
//                         {
//                             //System.out.println("2.....as it is");
//                             iniparam.put(key, value);
//                         }
//                     }
//                     
//                    
//                }
//                //System.out.println("3.....result"+iniparam);
//                bean.fieldRenderClass = className;
//                bean.fieldRenderClassParams=iniparam;
//                bean.fieldTooltip = rs1.getString("fieldTooltip");
//                bean.required = rs1.getString("required").equalsIgnoreCase("S");
//                bean.id = rs1.getInt("id");
//                bean.idset = rs1.getInt("idFieldset");
//                bean.selectableField = StringUtils.noNull(rs1.getString("selectableField")).trim();;
//                bean.addToMap = rs1.getString("addToMap").equalsIgnoreCase("S");
//                
//                //Aquest field té associada una accio
//                int idAction = rs1.getInt("actionId");
//                bean.action = loadActionBean(idAction);
//                if(bean.action!=null && (bean.action.observaciones==null || bean.action.observaciones.isEmpty()) )
//                {
//                    bean.action.observaciones = "<Fitxes> "+descriptionRule;
//                }
//                
//              
//                list.add(bean);
//            }
//            if(rs1!=null) {
//                rs1.close();
//                st.close();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }
    
    public HashMap<String,Object> convertToMap(final ArrayList<BeanFieldSet> list)
    {
        HashMap<String,Object> map = new HashMap<String,Object>();
        for(BeanFieldSet bean: list)
        {
            map.put(bean.fieldName, bean.fieldIni);
        }
        return map;
    }
    
    public HashMap<String,Object> convertToMapDefaultFill(final ArrayList<BeanFieldSet> list)
    {
        HashMap<String,Object> map = new HashMap<String,Object>();
        for(BeanFieldSet bean: list)
        {
            if(bean.fieldName==null || bean.fieldName.isEmpty()) {
                return map;
            }
            
            bean.fieldIni = bean.fieldIni==null?"":bean.fieldIni;
            
            if(bean.fieldIni.startsWith("$"))
            {
                if(Actuacio.resourceMap!=null && Actuacio.resourceMap.containsKey(bean.fieldIni))
                {
                    Object obj= Actuacio.resourceMap.get(bean.fieldIni);
                    if(obj.getClass().equals(String.class)){
                        bean.fieldIni = (String) obj ;
                    }   
                }
                else
                {
                    bean.fieldIni="";
                }
            }
            map.put(bean.fieldName, bean.fieldIni);
        }
        return map;
    }

    public void getAllRules(DefaultComboBoxModel comboModel1total, ArrayList<Integer> listIds1total,
            DefaultComboBoxModel comboModel1, ArrayList<Integer> listIds1, String tipus, String ambit, String estudis, boolean admin, boolean tutor) {
      
        String SQL1 = "SELECT * from tuta_actuacions as ta INNER JOIN tuta_actuacions_fields as taf "
                + " ON ta.id=taf.idRule WHERE tipus='"+tipus+"' AND (taf.ambits LIKE '%"+ambit+"%'"
                + " OR taf.ambits='*') AND (taf.estudis LIKE '%"+estudis+"%' OR taf.estudis='*')";
       
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st); 

            while (rs1 != null && rs1.next()) {
                int id = rs1.getInt("id");
                String action = rs1.getString("actuacio");
                boolean oa = rs1.getInt("nomesAdmin")>0;
                String forRoles = rs1.getString("roles");
                ArrayList listRoles = StringUtils.parseStringToArray(forRoles, ",", StringUtils.CASE_UPPER);
                 

                comboModel1total.addElement(action);
                listIds1total.add(id);
                
                if(admin || ((!admin && !oa) && 
                        (listRoles.contains(client.getUserInfo().getRole()) ||  
                         listRoles.contains("*"))   ) )
                {
                    comboModel1.addElement(action);
                    listIds1.add(id);                    
                }
            }

        if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private BeanAction loadActionBean(int idAction) {
        
        BeanAction bean = null;
        
        if(idAction<=0) {
            return bean;
        }
                
        String SQL1 = "SELECT * FROM tuta_actuacions_actions WHERE id="+idAction;
        try {
        Statement st = client.getMysql().getConnection().createStatement(); 
        ResultSet rs1 = st.executeQuery(SQL1);
        

            if (rs1 != null && rs1.next()) {
                bean = new BeanAction();
                bean.id = idAction;
               bean.dates = rs1.getString("dates");
               bean.incidenciasSesion = rs1.getInt("incidenciasSesion");
               bean.observaciones = rs1.getString("observaciones");
               bean.registerIesDigital = rs1.getString("registerIesDigital").equalsIgnoreCase("S");
               bean.registerSGD = rs1.getString("registerSGD").equalsIgnoreCase("S");
               bean.simboloIncidencia = rs1.getString("simboloIncidencia");
               bean.tipus = rs1.getString("tipus");
               bean.todoElDia = rs1.getString("todoElDia").equalsIgnoreCase("S");
            }

        if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bean;
    }

    public ComboBoxModel getComboModel(String tipus, boolean admin, byte mode) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        String SQL1 = "";
        if(mode==ALL) {
            SQL1 = "SELECT * from tuta_actuacions WHERE tipus='"+tipus+"'";
        }
        else if(mode==PENDENTS) {
            SQL1 = "SELECT * from tuta_actuacions WHERE tipus='"+tipus+"' AND simbol!='' and threshold>0";
        }
        else {
            return model;
        }
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st); 
            while (rs1 != null && rs1.next()) {
                int id = rs1.getInt("id");
                String action = rs1.getString("actuacio");
                boolean oa = rs1.getInt("nomesAdmin")>0;
                String forRoles = rs1.getString("roles"); 
                ArrayList listRoles = StringUtils.parseStringToArray(forRoles, ",", StringUtils.CASE_UPPER);
             
                if(admin || ((!admin && !oa) && 
                        (listRoles.contains(client.getUserInfo().getRole()) ||  
                         listRoles.contains("*"))   ) )
                {
                    model.addElement(action+ "  ["+id+"]");       
                }
            }

        if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return model;
    }
    
    
    public String getConditionEnviament(String tipus)
    {
        String SQL1 = "";
        if(tipus.equals("SMS"))
        {
            SQL1 = "SELECT ta.id,taf.enviamentSMS as enviament FROM tuta_actuacions AS ta INNER JOIN tuta_actuacions_fields AS taf ON ta.id=taf.idRule WHERE enviamentSMS!='N'";
        }
        else
        {
            SQL1 = "SELECT ta.id,taf.enviamentCarta as enviament FROM tuta_actuacions AS ta INNER JOIN tuta_actuacions_fields AS taf ON ta.id=taf.idRule WHERE enviamentCarta!='N'";            
        }
       
        String query = "";
        String or = "";
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st); 

            while (rs1 != null && rs1.next()) {
                int id = rs1.getInt("id");
                String enviament = rs1.getString("enviament");
                if(enviament.equals("S"))
                {
                    query += or + " idActuacio="+id+" ";
                    or = "OR";
                }
                else
                {
                    query += or + " (idActuacio="+id+" AND document LIKE '%"+enviament.replaceAll("$","").trim()+"={X}%')";
                    or = "OR";
                }
            }

        if(rs1!=null) {
                rs1.close();
                st.close();
         }
        } catch (SQLException ex) {
            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       
        return query;
    }
    
    public ArrayList<Integer> getListConditionEnviament(String tipus)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        String SQL1 = "";
        if(tipus.equals("SMS"))
        {
            SQL1 = "SELECT ta.id,taf.enviamentSMS as enviament FROM tuta_actuacions AS ta INNER JOIN tuta_actuacions_fields AS taf ON ta.id=taf.idRule WHERE enviamentSMS!='N'";
        }
        else
        {
            SQL1 = "SELECT ta.id,taf.enviamentCarta as enviament FROM tuta_actuacions AS ta INNER JOIN tuta_actuacions_fields AS taf ON ta.id=taf.idRule WHERE enviamentCarta!='N'";            
        }
       
        String query = "";
        String or = "";
         try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st); 

            while (rs1 != null && rs1.next()) {
                int id = rs1.getInt("id");
                String enviament = rs1.getString("enviament");
                if(enviament.equals("S"))
                {
                    list.add(id);
                }
            }

        if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       
        return list;
    }
    /**
     * Obté un llistat amb tots els tipus de registres que es fan a iesDigital
     * a partir de les rules
     * @return 
     */
    
    public ArrayList<String> getIesDigitalRegisterNames()
    {
        ArrayList<String> list = new ArrayList<String>();
        
        String SQL1 = "SELECT DISTINCT tipus FROM tuta_dies_sancions WHERE tipus!='' order by tipus";
         try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st); 

            while (rs1 != null && rs1.next()) {
               list.add(rs1.getString(1));
            }

        if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return list;
    }

    /**
     * CHECKS ONLY ONE -> FIELD, SELECTABLEFIELD AND PARAM
     * @param item
     * @param bean 
     */
    private void checkFIELD(Node item, BeanRules bean, String idCategory) {
         
         String fieldType = item.getNodeName();       
         NamedNodeMap attributes = item.getAttributes();
         BeanFieldSet bfs = new BeanFieldSet();
         bfs.addAttributes(attributes);
         bfs.category = idCategory;
       
         //Should find actions included in child elements
         NodeList childNodes = item.getChildNodes();
         int numValid = 0;
         BeanAction ba = null;
                  
       
         if(fieldType.equals(BeanFieldSet.TAG_FIELD) || fieldType.equals(BeanFieldSet.TAG_SELECTABLEFIELD))
         {
         
            if(childNodes!=null)
            {
                ba = new BeanAction();
                for(int k=0; k<childNodes.getLength(); k++ )
                {
                    Node item1 = childNodes.item(k);
                    if(item1.getNodeName().equals(BeanFieldSet.TAG_REGISTER))
                    {
                        NamedNodeMap attributes1 = item1.getAttributes();
                        if(attributes1!=null)
                        {
                            ba.setAttributes(attributes1);
                        }
                        numValid += 1;
                    }

                }
            }
        
            if(numValid==0) 
            {
                ba = null;
            }

            bfs.action = ba;                   
            bean.fields.add(bfs);
        }
        else if(fieldType.equals(BeanFieldSet.TAG_CATEGORY))
        {
             BeanCategory category = new BeanCategory();
             category.setAttributes(attributes);
             category.setParent(idCategory);
             bean.getListCategories().put(category.category, category);
             NodeList childNodes2 = item.getChildNodes();
             for (int k = 0; k < childNodes2.getLength(); k++) {
                 checkFIELD(childNodes2.item(k), bean, category.category);             //RECURSIVE
             }
        }
        else if(fieldType.equals(BeanFieldSet.TAG_PARAM))
        {
            bfs.setVisible(false);
            bfs.setFieldRenderClass(null);
            bfs.setSelectableField(null);
            bean.fields.add(bfs);                    
        }
        else if(fieldType.equals(BeanFieldSet.TAG_FIELDGROUP))
        {
            Node namedItem = attributes.getNamedItem("elements");
            if(namedItem.getNodeValue()!=null)
            {
                bean.getSelectableGroups().add(namedItem.getNodeValue());
            }

        }
//        else if(fieldType.equals(BeanFieldSet.TAG_REPORT))
//        {
//            BeanFieldReport bfr = new BeanFieldReport();
//            bfr.setAttributes(attributes);
//            bean.listReports.add(bfr);
//        }
        
    
                
    }

    //private ArrayList<BeanFieldReport> loadReports(int idRule, String ambit, String estudis) {
    private ArrayList<BeanFieldReport> loadReports(int idSubRule) {
        ArrayList<BeanFieldReport> list = new ArrayList<BeanFieldReport>();
//        String SQL1 = "SELECT * FROM tuta_actuacions_reports WHERE (ambit LIKE '%"+ambit+"%'"
//                + " OR ambit='*') AND (estudis LIKE '%"+estudis+"%' OR estudis='*') AND idRule="+idRule+" ORDER by important desc";
        

         String SQL1 = "SELECT * FROM tuta_actuacions_reports WHERE idSubRule="+idSubRule+" ORDER by important desc";

        //System.out.println(SQL1);
         try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st); 
            while(rs1!=null && rs1.next())
            {
                BeanFieldReport bfr = new BeanFieldReport();
                bfr.idReport = rs1.getString("id");
                bfr.important = rs1.getString("important").equalsIgnoreCase("S");
                bfr.reportPath = rs1.getString("reportPath");
                bfr.popupInstructions = rs1.getString("popupInstructions");
                bfr.reportDescription = rs1.getString("reportDescription");
               
                if(bfr.reportDescription==null)
                {
                    bfr.reportDescription = bfr.reportPath;
                }
                bfr.visibilitat = rs1.getString("visibilitat");
                bfr.setIncludeSubReport(rs1.getString("includeSubReport"));
                bfr.limitInc = rs1.getInt("limitInc");
                bfr.lang = rs1.getString("lang");
                
                if(bfr.reportPath!=null && !bfr.reportPath.isEmpty())
                {
                     list.add(bfr);
                }
                
                
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FactoryRules.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;       
    }

    
    public static HTMLDocument getLogger() {
        if (logger != null) {
            return logger;
        }

        HTMLEditorKit editorkit = new HTMLEditorKit();
        logger = (HTMLDocument) editorkit.createDefaultDocument();
        Element root = logger.getDefaultRootElement();
        try {
            logger.insertAfterStart(root, " <html> "
                    + "<head>"
                    + "<title>An example HTMLDocument</title>"
                    + "<style type=\"text/css\">"
                    + "  div {text-align:left; text-indent:2px; font-family:arial; font-size:10px}"
                    + "  ul { color: grey; }"
                    + "  .especial{ text-align:left; max-width: 140px;min-width:140;}"
                    + "  .select{ text-indent:12px; text-align:left; color:black; font-family:arial; font-size:10px}"
                    + "  .update{ text-indent:12px; text-align:left; color:blue; font-family:arial; font-size:10px}"
                    + "  .insert{ text-indent:12px;text-align:left; color:rgb(0,100,255); font-family:arial; font-size:10px}"
                    + "  .delete{ text-indent:12px;text-align:left; color:rgb(200,100,0); font-family:arial; font-size:10px}"
                    + "  .error{ text-indent:2px;text-align:left; color:red; font-family:arial; font-size:10px}"
                    + "  .date{text-align:left; color:grey; font-family:courier; font-size:8px; max-width: 140px;min-width:140;}"
                    + "  .title{text-align:left; color:blue; font-family:arial; font-weight:bold; font-size:14px}"
                    + "  .comment{text-align:left; color:grey; font-family:arial;  font-style:italic; font-size:10px}"
                    + "  .summary{text-align:left; color:green; font-family:arial; font-size:10px}"
                    + "  .warning{text-align:left; color:black; background-color:yellow;font-weight:bold; font-family: Courier New,Courier,monospace; font-size:10px}"
                    + "</style>"
                    + "</head>"
                    + "<body id='body'>"
                    + "</body>"
                    + "</html>");
             
        } catch (BadLocationException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logger;
    }
}
