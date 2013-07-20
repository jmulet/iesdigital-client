/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.actuacions;

import java.util.ArrayList;
import java.util.HashMap;
import org.iesapp.util.StringUtils;


/**
 *
 * @author Josep
 */
public class BeanRules {
    public static final String POLICY_SINGLE = "SINGLE";
    public static final String POLICY_MULTIPLE = "MULTIPLE";
    public static final String POLICY_MULTIPLE_WAIT = "MULTIPLE_WAIT";
    public static final String POLICY_MULTIPLE_WARNING = "MULTIPLE_WARNING";
    
    protected int idRule;
    protected String descripcio;
    protected String tipus;
    protected boolean nomesAdmin;
    protected String forRoles;
    protected String ambit;
    protected String simbol;
    protected int threshold;
    protected int vrepeticio=0;
    protected int vmax=0;
    protected boolean alertActuacioPendents = false;
    protected ArrayList<Integer> requiredCreated = null;
    protected boolean repetir;
    protected String instruccions;
    protected String reglament;
    protected String className;
    protected String form;
    protected ArrayList<BeanFieldSet> fields;
    protected ArrayList<BeanFieldReport> listReports;
    protected HashMap<String,BeanCategory> listCategories = new HashMap<String,BeanCategory>();
    protected int idFieldSet;
    protected ArrayList<String> selectableGroups;
    protected int minAge=0;
    protected int maxAge=100;
    protected boolean autoTancar = false;
    protected String enviamentCarta = "N";
    protected String enviamentSMS = "N";
    protected String alert;
    protected String descripcioLlarga;
    protected String instancesPolicy;
    protected boolean registerInFitxaAlumne;
    protected ArrayList<Integer> equivalencies = null;
    protected ArrayList<Integer> requiredClosed = null;
    //read from tuta_actuacions-form field, contains further parameters
    protected HashMap<String,String> extensions;
    
    /**
     * @return the descripcio
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * @param descripcio the descripcio to set
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
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
     * @return the nomesAdmin
     */
    public boolean isNomesAdmin() {
        return nomesAdmin;
    }

    /**
     * @param nomesAdmin the nomesAdmin to set
     */
    public void setNomesAdmin(boolean nomesAdmin) {
        this.nomesAdmin = nomesAdmin;
    }

    /**
     * @return the ambit
     */
    public String getAmbit() {
        return ambit;
    }

    /**
     * @param ambit the ambit to set
     */
    public void setAmbit(String ambit) {
        this.ambit = ambit;
    }

    /**
     * @return the threshold
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * @param threshold the threshold to set
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    /**
     * @return the instruccions
     */
    public String getInstruccions() {
        return instruccions;
    }

    /**
     * @param instruccions the instruccions to set
     */
    public void setInstruccions(String instruccions) {
        this.instruccions = instruccions;
    }

    /**
     * @return the reglament
     */
    public String getReglament() {
        return reglament;
    }

    /**
     * @param reglament the reglament to set
     */
    public void setReglament(String reglament) {
        this.reglament = reglament;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the form
     */
    public String getForm() {
        return form;
    }

    /**
     * @param form the form to set
     */
    public void setForm(String form) {
        this.form = form;
    }


    public void print() {
        System.out.println(idRule + " "
                + descripcio + " "
                + tipus + " "
                + nomesAdmin + " "
                + ambit + " "
                + threshold + " "
                + instruccions + " "
                + reglament + " "
                + className + " "
                + form + " ");
            
    }

    /**
     * @return the simbol
     */
    public String getSimbol() {
        return simbol;
    }

    /**
     * @param simbol the simbol to set
     */
    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    /**
     * @return the repetir
     */
    public boolean isRepetir() {
        return repetir;
    }

    /**
     * @param repetir the repetir to set
     */
    public void setRepetir(boolean repetir) {
        this.repetir = repetir;
    }


 
    /**
     * @return the idRule
     */
    public int getIdRule() {
        return idRule;
    }

    /**
     * @param idRule the idRule to set
     */
    public void setIdRule(int idRule) {
        this.idRule = idRule;
    }

    /**
     * @return the fields
     */
    public ArrayList<BeanFieldSet> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(ArrayList<BeanFieldSet> fields) {
        this.fields = fields;
    }

    public boolean isEditable(String field, boolean admin) {
        for(BeanFieldSet bean: this.getFields())
        {
            if(bean.getFieldName().equals(field))
            {
                return (bean.editable.equals("S") || (bean.editable.equals("P") && admin)
                         || (bean.editable.equals("T") && !admin));
            }
        }
        return false;
    }

    /**
     * @return the listReports
     */
    public ArrayList<BeanFieldReport> getListReports() {
        return listReports;
    }

    /**
     * @return the idFieldSet
     */
    public int getIdFieldSet() {
        return idFieldSet;
    }

    /**
     * @param idFieldSet the idFieldSet to set
     */
    public void setIdFieldSet(int idFieldSet) {
        this.idFieldSet = idFieldSet;
    }

    /**
     * @return the minAge
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * @param minAge the minAge to set
     */
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    /**
     * @return the maxAge
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * @param maxAge the maxAge to set
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * @return the autoTancar
     */
    public boolean isAutoTancar() {
        return autoTancar;
    }

    /**
     * @param autoTancar the autoTancar to set
     */
    public void setAutoTancar(boolean autoTancar) {
        this.autoTancar = autoTancar;
    }

    /**
     * @return the enviamentCarta
     */
    public String getEnviamentCarta() {
        return enviamentCarta;
    }

    /**
     * @param enviamentCarta the enviamentCarta to set
     */
    public void setEnviamentCarta(String enviamentCarta) {
        this.enviamentCarta = enviamentCarta;
    }

    /**
     * @return the enviamentSMS
     */
    public String getEnviamentSMS() {
        return enviamentSMS;
    }

    /**
     * @param enviamentSMS the enviamentSMS to set
     */
    public void setEnviamentSMS(String enviamentSMS) {
        this.enviamentSMS = enviamentSMS;
    }

    /**
     * @return the alert
     */
    public String getAlert() {
        return alert;
    }

    /**
     * @param alert the alert to set
     */
    public void setAlert(String alert) {
        this.alert = alert;
    }

    /**
     * @return the descripcioLlarga
     */
    public String getDescripcioLlarga() {
        return descripcioLlarga;
    }

    /**
     * @param descripcioLlarga the descripcioLlarga to set
     */
    public void setDescripcioLlarga(String descripcioLlarga) {
        this.descripcioLlarga = descripcioLlarga;
    }

    /**
     * @return the instancesPolicy
     */
    public String getInstancesPolicy() {
        return instancesPolicy;
    }

    /**
     * @param instancesPolicy the instancesPolicy to set
     */
    public void setInstancesPolicy(String instancesPolicy) {
        this.instancesPolicy = instancesPolicy;
    }

    /**
     * @return the registerInFitxaAlumne
     */
    public boolean isRegisterInFitxaAlumne() {
        return registerInFitxaAlumne;
    }

    /**
     * @param registerInFitxaAlumne the registerInFitxaAlumne to set
     */
    public void setRegisterInFitxaAlumne(boolean registerInFitxaAlumne) {
        this.registerInFitxaAlumne = registerInFitxaAlumne;
    }

    /**
     * @return the equivalencies
     */
    public ArrayList<Integer> getEquivalencies() {
        return equivalencies;
    }

    /**
     * @param equivalencies the equivalencies to set
     */
    public void setEquivalencies(ArrayList<Integer> equivalencies) {
        this.equivalencies = equivalencies;
    }

    /**
     * @return the requiredClosed
     */
    public ArrayList<Integer> getRequiredClosed() {
        return requiredClosed;
    }
    
    /**
     * Obtains the xml representation of the bean
     * @param bean
     * @return 
     */
    public static String toXml(BeanRules bean)
    {
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n");
        for(BeanFieldSet bfs: bean.fields)
        {
            
             if(bfs.fieldRenderClass==null || bfs.fieldRenderClass.isEmpty())
                {
                    String param = "<param id=\""+bfs.getFieldName()+"\" iniValue=\""+
                            bfs.getFieldIni().trim()+"\" addToMap=\""+(bfs.isAddToMap()?"yes":"no")+"\"/>\n";
                    builder.append(param);
                }
                else
                {
                    StringBuilder optionals = new StringBuilder();
                    if(!bfs.isVisible()) {
                        optionals.append("visible=\"no\" ");
                    }
                    if(!bfs.isAddToMap()) {
                        optionals.append("addToMap=\"no\" ");
                    }
                    if(bfs.getFieldTooltip()!=null) {
                        optionals.append("tooltip=\"").append(bfs.getFieldTooltip()).append("\" ");
                    }
                    if(bfs.editable!=null && !bfs.editable.isEmpty()) {
                        optionals.append("editable=\"").append(bfs.editable).append("\" ");
                    }
                    optionals.append("required=\"").append((bfs.isRequired()?"yes":"no")).append("\" ");
                    
                    String className = bfs.getFieldRenderClass();
                    
                    if(bfs.getFieldRenderClassParamsOriginal()!=null && !bfs.fieldRenderClassParamsOriginal.isEmpty())
                    {
                        className += "{";
                        for(String key: bfs.getFieldRenderClassParamsOriginal().keySet())
                        {
                            className += key +"="+bfs.getFieldRenderClassParamsOriginal().get(key)+"&amp;";
                        }
                        className = StringUtils.BeforeLast(className, "&amp;")+"}";
                    }
                    
                    String tagname = BeanFieldSet.TAG_FIELD;
                    String selection = "";
                    if(bfs.selectableField!=null && !bfs.selectableField.isEmpty())
                    {
                        tagname = "selectableField";
                        selection = " selection=\""+bfs.selectableField+"\"";
                    }
                       
                        
                    builder.append("<").append(tagname).append(selection).append(" id=\"").append(bfs.getFieldName()).append("\" class=\"")
                           .append(className).append("\" description=\"").append(bfs.getFieldDescription())
                           .append("\" iniValue=\"").append(bfs.getFieldIni().trim()).append("\" ").append(optionals);
                    //comprova si aquest camp conté accions
                    if(bfs.action==null)
                    {
                        builder.append("/>\n");
                       
                    }
                    else
                    {
                        builder.append(">\n");
                        StringBuilder actions = new StringBuilder();
                        if(bfs.action.isRegisterIesDigital())
                        {
                            actions.append("\t<register target=\"iesdigital\" type=\"").
                                    append(bfs.action.tipus).append("\" dates=\"").append(bfs.action.dates).append("\"/>\n");
                        }
                        if(bfs.action.isRegisterSGD())
                        {
                           actions.append("\t<register target=\"sgd\" simbol=\"").
                                    append(bfs.action.simboloIncidencia).append("\" dates=\"").append(bfs.action.dates).append("\" ").
                                    append("description=\"").append(bfs.action.observaciones.replaceAll("<", "&lt;").replaceAll(">", "&gt;")).append("\" incpersession=\"").
                                    append(bfs.action.incidenciasSesion).append("\" allDay=\"").append(bfs.action.todoElDia?"yes":"no").append("\" />\n");
                      
                        }
                        builder.append(actions).append("</").append(tagname).append(">\n");
                    }
                }
            
                    
        }
        
        //Afegeix (si n'hi ha) groups de camps
        int idx = 1;
        if(bean.getSelectableGroups()!=null)
        {
        for(String s: bean.getSelectableGroups())
        {
            builder.append("<fieldGroup id=\"").append(idx).append("\" elements=\"").append(s).append("\"/>\n");
            idx += 1;
        }
        }
        //Afegeix els reports (si n'hi ha)
        if(bean.listReports!=null)
        {
        for(BeanFieldReport bfr: bean.listReports)
        {
                    
            String options = "";
            if(!bfr.important);
            {
                options += "important=\"no\" ";
            }
            if(bfr.includeSubReport!=null && !bfr.includeSubReport.equals("N"))
            {
                options += "includeSubreport=\""+bfr.includeSubReport+"\" ";
            }
            if(!bfr.lang.equals("CA"))
            {
                options += "lang=\""+bfr.lang+"\" " ;
            }
            if(bfr.limitInc>0)
            {
                options += "limitInc=\""+bfr.limitInc+"\" " ;
            }
            if(bfr.popupInstructions!=null && !bfr.popupInstructions.isEmpty())
            {
                options += "popupInstructions=\""+bfr.popupInstructions+"\" ";
            }
            if(bfr.reportDescription!=null && !bfr.reportDescription.isEmpty())
            {
                options += "reportDescription=\""+bfr.reportDescription+"\" ";
            }
            if(!bfr.visibilitat.equals("*"))
            {
                options += "visibility=\""+bfr.visibilitat+"\" ";
            }
            builder.append("<report idReport=\"").append(bfr.idReport).append("\" reportPath=\"")
                   .append(bfr.reportPath).append("\" ").append(options).append(" />\n");
        }
        }
        builder.append("</root>");
        return builder.toString();
    }

    public String getForRoles() {
        return forRoles;
    }

    public void setForRoles(String forRoles) {
        this.forRoles = forRoles;
    }

    public boolean isAlertActuacioPendents() {
        return alertActuacioPendents;
    }

    public void setAlertActuacioPendents(boolean alertActuacioPendents) {
        this.alertActuacioPendents = alertActuacioPendents;
    }

    public ArrayList<Integer> getRequiredCreated() {
        return requiredCreated;
    }

    public void setRequiredCreated(ArrayList<Integer> requiredCreated) {
        this.requiredCreated = requiredCreated;
    }

    public int getVrepeticio() {
        return vrepeticio;
    }

    public void setVrepeticio(int vrepeticio) {
        this.vrepeticio = vrepeticio;
    }

    public int getVmax() {
        return vmax;
    }

    public void setVmax(int vmax) {
        this.vmax = vmax;
    }

    public ArrayList<String> getSelectableGroups() {
        return selectableGroups;
    }

    public void setSelectableGroups(ArrayList<String> selectableGroups) {
        this.selectableGroups = selectableGroups;
    }

    public HashMap<String,BeanCategory> getListCategories() {
        return listCategories;
    }

    public void setListCategories(HashMap<String,BeanCategory> listCategories) {
        this.listCategories = listCategories;
    }
}
