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
public class BeanCategory {
    protected String category="";
    protected String title="";
    protected String parent="";
    protected boolean collapsable=true;
    protected boolean collapsed=false;  
    

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
     * @return the collapsable
     */
    public boolean isCollapsable() {
        return collapsable;
    }

    /**
     * @param collapsable the collapsable to set
     */
    public void setCollapsable(boolean collapsable) {
        this.collapsable = collapsable;
    }

    /**
     * @return the collapsed
     */
    public boolean isCollapsed() {
        return collapsed;
    }

    /**
     * @param collapsed the collapsed to set
     */
    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public void setAttributes(NamedNodeMap attributes) {
        for(int i=0; i<attributes.getLength(); i++)
        {
            Node item = attributes.item(i);
            if(item.getNodeName().equals("id"))
            {
                this.category = item.getNodeValue();
            }
            else if(item.getNodeName().equals("title"))
            {
                this.title = item.getNodeValue();
            }
            else if(item.getNodeName().equals("collapsable"))
            {
                this.collapsable = item.getNodeValue().equals("yes");
            }
            else if(item.getNodeName().equals("collapsed"))
            {
                this.collapsed = item.getNodeValue().equals("yes");
            }
        }
    }

    /**
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(String parent) {
        this.parent = parent;
    }
}
