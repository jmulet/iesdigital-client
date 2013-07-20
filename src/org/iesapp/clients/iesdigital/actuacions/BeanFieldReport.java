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
public class BeanFieldReport {

    protected String reportPath;
    protected String reportDescription=null;
    protected String includeSubReport="N"; //enum('N','F','A','R') NOT NULL
    protected int limitInc=0;
    protected boolean important=true;
    protected String popupInstructions;
    protected String idReport;
    protected String visibilitat="*"; //enum("P","*") prefectura o prefectura&tutor
    protected String lang="CA";
    
    /**
     * @return the includeSubReport
     */
    public String getIncludeSubReport() {
        return includeSubReport;
    }

    /**
     * @param includeSubReport the includeSubReport to set
     */
    public void setIncludeSubReport(String includeSubReport) {
        this.includeSubReport = includeSubReport;
    }

    /**
     * @return the limitInc
     */
    public int getLimitInc() {
        return limitInc;
    }

    /**
     * @param limitInc the limitInc to set
     */
    public void setLimitInc(int limitInc) {
        this.limitInc = limitInc;
    }

    /**
     * @return the reportPath
     */
    public String getReportPath() {
        return reportPath;
    }

    /**
     * @param reportPath the reportPath to set
     */
    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    /**
     * @return the reportDescription
     */
    public String getReportDescription() {
        return reportDescription;
    }

    /**
     * @param reportDescription the reportDescription to set
     */
    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    /**
     * @return the important
     */
    public boolean isImportant() {
        return important;
    }

    /**
     * @param important the important to set
     */
    public void setImportant(boolean important) {
        this.important = important;
    }

    /**
     * @return the popupInstructions
     */
    public String getPopupInstructions() {
        return popupInstructions;
    }

    /**
     * @param popupInstructions the popupInstructions to set
     */
    public void setPopupInstructions(String popupInstructions) {
        this.popupInstructions = popupInstructions;
    }

    /**
     * @return the idReport
     */
    public String getIdReport() {
        return idReport;
    }

    /**
     * @param idReport the idReport to set
     */
    public void setIdReport(String idReport) {
        this.idReport = idReport;
    }

    /**
     * @return the visibilitat
     */
    public String getVisibilitat() {
        return visibilitat;
    }

    /**
     * @param visibilitat the visibilitat to set
     */
    public void setVisibilitat(String visibilitat) {
        this.visibilitat = visibilitat;
    }

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setAttributes(NamedNodeMap attributes) {
        if(attributes==null) {
            return;
        }
        for(int i=0; i<attributes.getLength(); i++)
        {
            Node item = attributes.item(i);
            if(item.getNodeName().equals("important"))
            {
                this.important = item.getNodeValue().equals("yes");
            }
            else if(item.getNodeName().equals("includeSubreport"))
            {
                this.includeSubReport = item.getNodeValue();
            }
            else if(item.getNodeName().equals("lang"))
            {
                this.lang = item.getNodeValue();
            }
            else if(item.getNodeName().equals("limitInc"))
            {
                this.limitInc = Integer.parseInt( item.getNodeValue() );
            }
            else if(item.getNodeName().equals("popupInstructions"))
            {
                this.popupInstructions = item.getNodeValue();
            }
            else if(item.getNodeName().equals("reportDescription"))
            {
                this.reportDescription = item.getNodeValue();
            }
            else if(item.getNodeName().equals("visibility"))
            {
                this.visibilitat = item.getNodeValue();
            }
            else if(item.getNodeName().equals("reportPath"))
            {
                this.reportPath = item.getNodeValue();
            }
            else if(item.getNodeName().equals("idReport"))
            {
                this.idReport = item.getNodeValue();
            }
        }
    }
}
