/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital;

/**
 *
 * @author Josep
 */
public class DefaultConfigTableEntry {
    protected String category;
    protected Object value;
    protected String castTo;
    protected String description;

    public DefaultConfigTableEntry(String category,Object value,String castTo,String description)
    {
        this.castTo = castTo;
        this.category = category;
        this.description = description;
        this.value = value;
        
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getCastTo() {
        return castTo;
    }

    public void setCastTo(String castTo) {
        this.castTo = castTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
