/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.spaces;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.database.vscrud.BeanIntrospector;
import org.iesapp.database.vscrud.DefaultTableMapping;
import org.iesapp.database.vscrud.PK;
import org.iesapp.database.vscrud.TableMapping;

/**
 *
 * @author Josep
 */
@DefaultTableMapping(tableName="sig_guardies_zones")
public class BeanGuardZone implements Cloneable{
    @PK
    @TableMapping
    protected int id=0;
    @TableMapping
    protected String lloc="";
    @TableMapping
    protected String descripcio="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLloc() {
        return lloc;
    }

    public void setLloc(String lloc) {
        this.lloc = lloc;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
    
    @Override
    public String toString()
    {
        return "BeanGuardZone: id="+id+"; lloc="+lloc+", descripcio="+descripcio;
    }

    @Override
    public Object clone() {
        try {
            // call clone in Object. 
            return super.clone();
        } catch (CloneNotSupportedException e) {
            //System.out.println("Cloning not allowed.");
            return this;
        }
    }
    
     public void setBeanFromObj(Object obj) {
       
        if(obj instanceof BeanGuardZone)
        {
            try {
                PropertyDescriptor[] propertyDescriptors = BeanIntrospector.getPropertyDescriptors(this.getClass());
                for(PropertyDescriptor pd: propertyDescriptors)
                {
                    String name = pd.getName();
                    PropertyDescriptor pdTo = new PropertyDescriptor(name, obj.getClass());
                    Method readMethod = pdTo.getReadMethod();
                    Method writeMethod = pd.getWriteMethod();
                    writeMethod.invoke(this, readMethod.invoke(obj));
                 }
            } catch (Exception ex) {
                Logger.getLogger(BeanGuardZone.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
