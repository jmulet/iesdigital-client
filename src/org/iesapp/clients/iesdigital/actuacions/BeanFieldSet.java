/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.actuacions;

import java.util.ArrayList;
import java.util.HashMap;
import org.iesapp.util.StringUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author Josep
 */
public class BeanFieldSet {
   
    protected int id;
    protected int idset;
    protected String fieldName;
    protected String fieldDescription;
    protected String fieldTooltip;
    protected String fieldIni;
    protected boolean required = true;
    protected boolean visible = true;
    protected boolean addToMap = true;
    protected String editable="S";  //enum('N','T','P','S') NOT NULLEditabilitat del camp N=no, T=nomes tutor, P=nomes prefectua, S=si
    protected String fieldRenderClass;
    protected String selectableField;
    protected HashMap<String, Object> fieldRenderClassParams;
    protected HashMap<String, Object> fieldRenderClassParamsOriginal;
    protected BeanAction action=null;
    protected String category="";

    public static final String TAG_FIELD = "field";
    public static final String TAG_SELECTABLEFIELD = "selectableField";
    public static final String TAG_PARAM = "param";
    public static final String TAG_FIELDGROUP = "fieldGroup";
    public static final String TAG_REGISTER = "register";
    public static final String TAG_REPORT = "report";
    public static final String TAG_CATEGORY = "category";
 
    
    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the fieldDescription
     */
    public String getFieldDescription() {
        return fieldDescription;
    }

    /**
     * @param fieldDescription the fieldDescription to set
     */
    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    /**
     * @return the fieldTooltipvar
     */
    public String getFieldTooltip() {
        return fieldTooltip;
    }

    /**
     * @param fieldTooltipvar the fieldTooltipvar to set
     */
    public void setFieldTooltip(String fieldTooltipvar) {
        this.fieldTooltip = fieldTooltipvar;
    }

    /**
     * @return the fieldIni
     */
    public String getFieldIni() {
        return fieldIni;
    }

    /**
     * @param fieldIni the fieldIni to set
     */
    public void setFieldIni(String fieldIni) {
        this.fieldIni = fieldIni;
    }

    /**
     * @return the editable
     */
    public String getEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(String editable) {
        this.editable = editable;
    }

    /**
     * @return the fieldRenderClass
     */
    public String getFieldRenderClass() {
        return fieldRenderClass;
    }

    /**
     * @param fieldRenderClass the fieldRenderClass to set
     */
    public void setFieldRenderClass(String fieldRenderClass) {
        this.fieldRenderClass = fieldRenderClass;
    }

    /**
     * @return the required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

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
     * @return the idset
     */
    public int getIdset() {
        return idset;
    }

    /**
     * @param idset the idset to set
     */
    public void setIdset(int idset) {
        this.idset = idset;
    }

    /**
     * @param fieldRenderClassParams the fieldRenderClassParams to set
     */
    public void setFieldRenderClassParams(HashMap<String, Object> fieldRenderClassParams) {
        this.fieldRenderClassParams = fieldRenderClassParams;
    }

    /**
     * @return the addToMap
     */
    public boolean isAddToMap() {
        return addToMap;
    }

    /**
     * @param addToMap the addToMap to set
     */
    public void setAddToMap(boolean addToMap) {
        this.addToMap = addToMap;
    }

    /**
     * @return the selectableField
     */
    public String getSelectableField() {
        return selectableField;
    }

    /**
     * @param selectableField the selectableField to set
     */
    public void setSelectableField(String selectableField) {
        this.selectableField = selectableField;
    }

    /**
     * @return the action
     */
    public BeanAction getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(BeanAction action) {
        this.action = action;
    }

    public void addAttributes(NamedNodeMap attributes) {
        if(attributes==null)
        {
            return;
        }
        for(int i=0; i<attributes.getLength(); i++)
        {
            Node item = attributes.item(i);
            if(item.getNodeName().equals("id"))
            {
                this.fieldName = item.getNodeValue();
            }
            else if(item.getNodeName().equals("class"))
            {
                String tmp = item.getNodeValue();
                String className = item.getNodeValue();
                HashMap<String, Object> iniparam = new HashMap<String,Object>();
                this.setFieldRenderClassParamsOriginal((HashMap<String, Object>) iniparam.clone());
                
                if(tmp!=null && tmp.contains("{"))
                {
                     className = StringUtils.BeforeFirst(tmp, "{");
                     String tmp2 = StringUtils.AfterFirst(tmp, "{").replace("}", "");
                     //Convert ini string to a map
                     iniparam = StringUtils.StringToHash(tmp2, "&", false);  
                     
                     //Ha de canviar al valor real
                      for(String key: iniparam.keySet())
                      {
                         String value = (String) iniparam.get(key);
                         if(value.startsWith("$"))
                         {
                             //System.out.println("2.....replace with resource"+Actuacio.resourceMap);
                             //Replace with value from resourceMap
                             if(Actuacio.resourceMap!=null && Actuacio.resourceMap.containsKey(value))
                             {
                                 iniparam.put(key, Actuacio.resourceMap.get(value));
                             }
                         }
                         else if(value.startsWith("("))
                         {
                             //Replace list with arraylist
                             //System.out.println("2.....replace with list");
                             String txt2 = value.replace(")", "").replace("(", "");
                             ArrayList<String> parsed = StringUtils.parseStringToArray(txt2, ",", StringUtils.CASE_INSENSITIVE);
                             iniparam.put(key, parsed);
                         }
                         else
                         {
                             //System.out.println("2.....as it is");
                             iniparam.put(key, value);
                         }
                     }
                   
                }
                this.fieldRenderClass =  className;
                this.setFieldRenderClassParams(iniparam);
            }
            else if(item.getNodeName().equals("description"))
            {
                this.fieldDescription = item.getNodeValue();
            }
            else if(item.getNodeName().equals("iniValue"))
            {
                this.fieldIni = item.getNodeValue();
            }
            else if(item.getNodeName().equals("tooltip"))
            {
                this.fieldTooltip = item.getNodeValue();
            }
            else if(item.getNodeName().equals("visible"))
            {
                this.visible = item.getNodeValue().equalsIgnoreCase("yes");
            }
            else if(item.getNodeName().equals("editable"))
            {
                this.editable = item.getNodeValue();
            }
            else if(item.getNodeName().equals("addToMap"))
            {
                this.addToMap = item.getNodeValue().equalsIgnoreCase("yes");
            }
            else if(item.getNodeName().equals("selection"))
            {
                this.selectableField = item.getNodeValue();
            }
             else if(item.getNodeName().equals("required"))
            {
                this.required = item.getNodeValue().equals("yes");
            }
            
            
        }
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public HashMap<String, Object> getFieldRenderClassParams() {
        return fieldRenderClassParams;
    }

     
    public HashMap<String, Object> getFieldRenderClassParamsOriginal() {
        return fieldRenderClassParamsOriginal;
    }

    public void setFieldRenderClassParamsOriginal(HashMap<String, Object> fieldRenderClassParamsOriginal) {
        this.fieldRenderClassParamsOriginal = fieldRenderClassParamsOriginal;
    }
}
