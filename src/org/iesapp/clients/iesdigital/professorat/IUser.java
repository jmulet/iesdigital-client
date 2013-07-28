/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.professorat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.alumnat.Grup;

/**
 *
 * @author Josep
 */
public class IUser {
    
    public static final int ADMIN = 0;
    public static final int PREF = 1;
    public static final int TUTOR = 2;
    public static final int NOTUTOR = 3;
    public static final int READONLY = 4;
    public static final int NOACCESS = 5;
    public static final int GUARD = 6;
     
    protected String role;
    protected String name;
    protected String abrev;
    protected String carrec;
    protected int grant;
    protected Grup grup;
    protected int idSGD;
    protected String systemUser;
    protected String claveUP;
    protected final IClient client;
    

    public IUser(IClient client)
    {
        this.client = client;
        name = "";
        abrev = "";
        carrec = "";
        grant = 0;
        role = "";
  }

   public IUser(String abrev, IClient client) {
        this.client = client;
        grup = new Grup(client);
       
        abrev = abrev.toUpperCase();

        this.abrev = abrev;
        String carrecs = "";
        
        if(abrev.equals("ADMIN"))
        {
            name = "ADMINISTRADOR";
            grant = IUser.ADMIN;
            carrec = "Administració del sistema";
            role = "ADMIN";
            idSGD = -1;
            grup = null;
        }
        else if(abrev.equals("GUARD"))
        {
            name = "GUARDIES";
            grant = IUser.GUARD;
            carrec = "Usuari de guàrdies";
            role = "GUARD";
            idSGD = -1;
            grup = null;
        }
        else
        {
        
           
        //Determina si el professor és tutor
        //Aquesta condicio nomes funciona si té una assignatura de TUT... dins els horaris de
        //iesDigital.
        //Un cas més general i segur, es utilitzar la condicio de tutor del sistema SGD
            
        String SQL1 = "Select curso, grupo, nivel, nombre, idSGD from sig_horaris as h inner join"
                + " sig_professorat as p on h.prof=p.abrev where h.prof='" + abrev
                + "' and asig LIKE 'TUT%'";

        int idsgd = -1;

        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);

            if (rs1 != null && rs1.next()) {

                int curso = rs1.getInt("curso");
                String grupo = rs1.getString("grupo");
                String nivel = rs1.getString("nivel");
                this.name = rs1.getString("nombre");
                this.grup = new Grup(nivel, curso, grupo, client);
                this.carrec += "Tutor " + curso + " " + nivel + "-" + grupo;

            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Si no és tutor no li troba el nom; tornam a repetir la consulta
        SQL1 = "SELECT nombre, GrupFitxes, prof.idSGD FROM sig_professorat as prof left join "
                + " usu_usuari as usu on prof.abrev=usu.Nom WHERE abrev='" + abrev + "'";
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);

            if (rs1 != null && rs1.next()) {
                name = rs1.getString("nombre");
                role = rs1.getString("GrupFitxes");
                this.idSGD = rs1.getInt("idSGD");
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        //** Aplica nivell de privilegis **/
        if (carrecs.contains("Tutor")) {
            this.grant=IUser.TUTOR;
        } else if (this.role.startsWith("ADMIN")) {
            //name = "ADMINISTRADOR";
            grant = IUser.ADMIN;
            carrec = "Administració del sistema";
        } else if (this.role.startsWith("PREF")) {
            //user.setName("PREFECTURA");
            grant = IUser.PREF;
            carrec = "Coordinació dels usuaris";
        } else if (this.abrev.equals("GUARD")) {
            name = "GUARDIES";
            grant = IUser.GUARD;
            carrec = "Obtenir informació en una guàrdia";
        } else {
            grant = IUser.NOTUTOR;
        }
        }
    }

    public String getSystemUser() {
        return systemUser;
    }

    public String getClaveUP() {
        return claveUP;
    }
    
      
    public int getIdSGD() {
        return idSGD;
    }

    
    public void setIdSGD(int idSGD) {
        this.idSGD = idSGD;
    }

    
    public Grup getGrup() {
        return grup;
    }

    
    public void setGrup(Grup grup) {
        this.grup = grup;
    }


    
    public int getGrant() {
        return grant;
    }

    
    public void setGrant(int grant) {
        this.grant = grant;
    }


    
    public String getCarrec() {
        return carrec;
    }

    
    public void setCarrec(String carrec) {
        this.carrec = carrec;
    }


    
    public String getAbrev() {
        return abrev;
    }

    
    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getRole() {
        return this.role;
    }

    
    public void setRole(String role) {
        this.role=role;
    }
    
}
