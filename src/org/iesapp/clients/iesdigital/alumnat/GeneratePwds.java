/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.alumnat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.database.MyDatabase;
import org.iesapp.util.JSEngine;

/**
 *
 * @author Josep
 */
public class GeneratePwds {
    
    /**
     * Mètode per generar la contrasenya d'un sol alumne 
     * @param exp2
     * @param mysql
     * @return 
     */
    public static String genContrasenya(int exp2, MyDatabase mysql)
    {
        JSEngine jsEngine = JSEngine.getJSEngine(GeneratePwds.class, ICoreData.contextRoot);
        Bindings bindings = jsEngine.getBindings();
        String pwd = "";
        int nup = 0;   
        try {
            bindings.put("mysql", mysql);
            bindings.put("mysqlDBPrefix", ICoreData.core_mysqlDBPrefix);
            jsEngine.evalBindings(bindings);
            nup = ((Number) jsEngine.invokeFunction("genContrasenyaAlumne", exp2, true)).intValue();
//            if(jTextArea2!=null)
//            {
//                    jTextArea2.append("\nContrasenyes generades a partir del fitxer script "+
//                    jsEngine.getScriptFile().getAbsolutePath()+"\n");
//            }
        } catch (Exception ex) {
            //ErrorDisplay.showMsg(ex.toString());
        }
        
        if (nup<2) {
            String cmd = "UPDATE `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne SET ultim8 = RIGHT (MD5(Exp2),8) WHERE Exp2=" + exp2;
            nup = mysql.executeUpdate(cmd);
            cmd = "UPDATE `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne SET pwd = LEFT(CONV(ultim8,16,10),8) WHERE Exp2=" + exp2;
            nup += mysql.executeUpdate(cmd);
//            if(jTextArea2!=null)
//            {
//                    jTextArea2.append("Contrasenyes generades a partir de la definició per defecte.\n");
//            }
        }
        
        if (nup < 2) {
            pwd = "Error: No s'ha pogut crear la contrasenya";
        } else {
            String cmd = "SELECT pwd FROM `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne WHERE Exp2=" + exp2;
            try {
                Statement st = mysql.createStatement();
                ResultSet rs1 = mysql.getResultSet(cmd, st);
                while (rs1 != null && rs1.next()) {
                    pwd = "S'ha creat la contrasenya " + rs1.getString("pwd") + " per a l'usuari " + exp2;
                }
                if (rs1 != null) {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GeneratePwds.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return pwd;
    }

    
    /**
     * Genera les contrasenyes de tots els alumnes
     * @param update
     * @param mysql
     * @return 
     */
    public static int generaContrasenyes(boolean update, MyDatabase mysql)
    {
        int nup = 0;
        JSEngine jsEngine = JSEngine.getJSEngine(GeneratePwds.class, ICoreData.contextRoot);
        Bindings bindings = jsEngine.getBindings();
         try {
            bindings.put("mysql", mysql);
            bindings.put("mysqlDBPrefix", ICoreData.core_mysqlDBPrefix);
            jsEngine.evalBindings(bindings); 
            nup = ((Number) jsEngine.invokeFunction("genContrasenyaAlumne", -1, update)).intValue();
//            if(jTextArea2!=null)
//            {
//                    jTextArea2.append("\nContrasenyes generades a partir del fitxer script "+
//                    jsEngine.getScriptFile().getAbsolutePath()+"\n");
//            }
        } catch (Exception ex) {
//            ErrorDisplay.showMsg(ex.toString());
        }
        
        if (nup<2 && update) {
            if (!update) {
                String SQL1 = "UPDATE `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne SET ultim8 = RIGHT (MD5(Exp2),8) WHERE pwd='' OR pwd IS NULL";
                nup += mysql.executeUpdate(SQL1);
                SQL1 = "UPDATE `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne SET pwd = LEFT(CONV(ultim8,16,10),8) WHERE pwd='' OR pwd IS NULL";
                nup += mysql.executeUpdate(SQL1);
            } else {
                String SQL1 = "UPDATE `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne SET ultim8 = RIGHT (MD5(Exp2),8)";
                nup += mysql.executeUpdate(SQL1);
                SQL1 = "UPDATE `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne SET pwd = LEFT(CONV(ultim8,16,10),8)";
                nup += mysql.executeUpdate(SQL1);
            }
//            if(jTextArea2!=null)
//            {
//                    jTextArea2.append("Contrasenyes generades a partir de la definició per defecte\n");
//            }
        }
        return nup;
    }
    
}
