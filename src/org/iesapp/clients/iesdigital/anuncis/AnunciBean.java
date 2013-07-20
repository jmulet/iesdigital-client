/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.anuncis;

/**
 *
 * @author Josep
 */
public class AnunciBean {
    
    public static final int SORTIDA = 0;
    public static final int REUNIO = 1;
    public static final int EVENT = 2;
    public static final int MISC = 3;
    public static final int PDA = 4;
    public static final int WEIB = 5;
    
    public static final String IMPORTED_ICON="I";
    public static final String RESOURCE_ICON="R";
  
    protected String title="";
    protected String description="";
    protected String body="";
    protected String professorat="";
    protected int collapse = 0;
    protected int showDateInTitle = 1;

    protected java.util.Date eventdate = null;
    protected java.util.Date eventdate2 = null;
    protected String author="";
    protected String abrev="";
    protected java.util.Date postdate = new java.util.Date();
    
    protected int type = SORTIDA;
    protected String typeName = "";
    protected String iconName = ""; //Cfg.resourceIcons[0];
    protected String iconType=RESOURCE_ICON;
    protected int globalId=-1;
    protected int dbId=-1;
    
    protected boolean nou=false;
    protected boolean newpost=false;
    protected int hIndex0 = 0;
    protected int hIndex1 = 0;
    
    protected String para="Tothom";
    

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the postdate
     */
    public java.util.Date getPostdate() {
        return postdate;
    }

    /**
     * @param postdate the postdate to set
     */
    public void setPostdate(java.util.Date postdate) {
        this.postdate = postdate;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the nou
     */
    public boolean isNou() {
        return nou;
    }

    /**
     * @param nou the nou to set
     */
    public void setNou(boolean nou) {
        this.nou = nou;
    }

    /**
     * @return the eventdate
     */
    public java.util.Date getEventdate() {
        return eventdate;
    }

    /**
     * @param eventdate the eventdate to set
     */
    public void setEventdate(java.util.Date eventdate) {
        this.eventdate = eventdate;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
        this.iconName = AnuncisDefinition.mapDefined.get(type).getResourceIcon();//;Cfg.resourceIcons[type];
        this.iconType = RESOURCE_ICON;
    }

   
    /**
     * @return the globalId
     */
    public int getGlobalId() {
        return globalId;
    }

    /**
     * @param globalId the globalId to set
     */
    public void setGlobalId(int globalId) {
        this.globalId = globalId;
    }

    /**
     * @return the dbId
     */
    public int getDbId() {
        return dbId;
    }

    /**
     * @param dbId the dbId to set
     */
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    /**
     * @return the newpost
     */
    public boolean isNewpost() {
        return newpost;
    }

    /**
     * @param newpost the newpost to set
     */
    public void setNewpost(boolean newpost) {
        this.newpost = newpost;
    }

    public String getProfessorat() {
        return professorat;
    }

    public void setProfessorat(String professorat) {
        this.professorat = professorat;
    }

    public int gethIndex0() {
        return hIndex0;
    }

    public void sethIndex0(int hIndex0) {
        this.hIndex0 = hIndex0;
    }

    public int gethIndex1() {
        return hIndex1;
    }

    public void sethIndex1(int hIndex1) {
        this.hIndex1 = hIndex1;
    }

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    /**
     * @return the iconName
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * @param iconName the iconName to set
     */
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    /**
     * @return the iconType
     */
    public String getIconType() {
        return iconType;
    }

    /**
     * @param iconType the iconType to set
     */
    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    /**
     * @return the eventdate2
     */
    public java.util.Date getEventdate2() {
        return eventdate2;
    }

    /**
     * @param eventdate2 the eventdate2 to set
     */
    public void setEventdate2(java.util.Date eventdate2) {
        this.eventdate2 = eventdate2;
    }

    /**
     * @return the collapse
     */
    public int getCollapse() {
        return collapse;
    }

    /**
     * @param collapse the collapse to set
     */
    public void setCollapse(int collapse) {
        this.collapse = collapse;
    }

    /**
     * @return the showDateInTitle
     */
    public int getShowDateInTitle() {
        return showDateInTitle;
    }

    /**
     * @param showDateInTitle the showDateInTitle to set
     */
    public void setShowDateInTitle(int showDateInTitle) {
        this.showDateInTitle = showDateInTitle;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }


 
  
}
