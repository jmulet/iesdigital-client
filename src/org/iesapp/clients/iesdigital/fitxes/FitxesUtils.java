/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.fitxes;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.clients.iesdigital.alumnat.Grup;
import org.iesapp.clients.iesdigital.professorat.IUser;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class FitxesUtils {
    private final IClient client;

  
    public FitxesUtils(IClient client)
    {
        this.client = client;
    }
    
    /**
     * Returns day of interview for a given teacher abrev
     * returns string
     * @param abrev
     * @return 
     */
    public String getDataVisita(String abrev)
    {
        String aux="";

        String SQL1 = "SELECT * FROM tuta_visitestutors WHERE abrev='"+abrev+"'";
        
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1);
            while (rs1 != null && rs1.next()) {

                int dia=  rs1.getInt("dia");
                int hora= rs1.getInt("hora");

                switch(dia)
                {
                    case (1): aux += "Dilluns";break;
                    case (2): aux += "Dimarts";break;
                    case (3): aux += "Dimecres";break;
                    case (4): aux += "Dijous";break;
                    case (5): aux += "Divendres";break;
                }
                aux += " de ";

                aux += StringUtils.formatTime(client.getDatesCollection().getHoresClase()[hora-1]);
                aux += " a ";
                aux += StringUtils.formatTime(client.getDatesCollection().getHoresClase_fi()[hora-1]);
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FitxesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    /**
     * Mes que editable realitza una condició de pertanença de l'alumne nexpd
     * pel professor abrev. Si el professor abrev no es tutor retornà fals, a no ser
     * que s'hagi donat una condicio a ma.
     * L'any que utilitza es l'any del curs definit en el core
     * @param abrev
     * @param nexpd
     * @return 
     */
    
    public boolean belongs(String abrev, int nexpd) {
        boolean result=false;

        if(client.getUserInfo().getGrant()==IUser.ADMIN || client.getUserInfo().getGrant()==IUser.PREF ) {
            return true;
        }
    

        //Comprova si esta especificat manualment com editable
        String perm="";
        String ensenyament = "";
        String grup = "";
        String estudis = "";
        String SQL1 = "Select xes.Permisos, xh.AnyAcademic, xh.Ensenyament, xh.Estudis, xh.Grup FROM `"+
                ICoreData.core_mysqlDBPrefix+"`.xes_alumne AS xes INNER JOIN `"+ICoreData.core_mysqlDBPrefix+"`.xes_alumne_historic AS xh ON xh.Exp2=xes.Exp2 "
                + " where xh.Exp2="+nexpd+""
                + " xh.AnyAcademic='"+client.getICoreData().anyAcademic+"'";
        
        
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {
                perm = rs1.getString("Permisos");
                ensenyament = rs1.getString("Ensenyament");
                grup = rs1.getString("Grup");
                estudis = rs1.getString("Estudis");
            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FitxesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }


        if(!perm.isEmpty())
        {
            if(perm.contains(abrev))
            {
                return true;
            }
            else
            {
                return false;
            }
        }


        //si no, comprova si l'alumne pertany a la seva tutoria
        result = true;


       // result &= bean.getAny_academic().equals(StringUtils.anyAcademic());
        result &= ensenyament.contains((client.getUserInfo().getGrup().getXEnsenyament()));
        result &= estudis.equals(""+client.getUserInfo().getGrup().getXEstudis());
        result &= grup.equals(client.getUserInfo().getGrup().getXGrup());

       
        return result;
    }


/**
 * List of integers n. expds of all students that belong to a given teacher abrev
 * in a given year.
 * @param abrev
 * @param anyAcademicFitxes
 * @return 
 */
    public ArrayList<Integer> belongsList(String abrev, int anyAcademicFitxes) {

        ArrayList<Integer> aux = new ArrayList<Integer>();

        /*
         * Si es un usuari de guardies retorna una llista buida
         */
        if(client.getUserInfo().getGrant()==IUser.GUARD)
        {
            return aux;
        }

        //Si ets administrador o prefectura: tens assignats tots els alumnes
        if(client.getUserInfo().getGrant()==IUser.ADMIN || client.getUserInfo().getGrant()==IUser.PREF )
        {
            String SQL1 = "Select Exp2 from `"+ICoreData.core_mysqlDBPrefix+"`.xes_alumne_historic where AnyAcademic='"+anyAcademicFitxes+"'";
            ResultSet rs1 = client.getMysql().getResultSet(SQL1);

            try {
                while (rs1 != null && rs1.next()) {
                    int exp = rs1.getInt("Exp2");
                    aux.add(exp);
                }
            if(rs1!=null) {
                    rs1.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FitxesUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

            return aux;
        }



         String SQL1 = "Select xes.Permisos, xh.AnyAcademic, xh.Ensenyament, xh.Estudis, xh.Grup, xh.Exp2 FROM `"
                 +ICoreData.core_mysqlDBPrefix+"`.xes_alumne AS xes INNER JOIN `"+ICoreData.core_mysqlDBPrefix+"`.xes_alumne_historic AS xh ON xh.Exp2=xes.Exp2 "
                + " WHERE xh.AnyAcademic='"+anyAcademicFitxes+"'";
       

            try {
                Statement st = client.getMysql().createStatement();
                ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
                while (rs1 != null && rs1.next()) {
                    String perm = rs1.getString("Permisos");
                    String ensenyament = rs1.getString("Ensenyament");
                    String grup = rs1.getString("Grup");
                    String estudis = rs1.getString("Estudis");
                    int exp = rs1.getInt("Exp2");

                    if(!perm.isEmpty())
                    {
                        if(perm.contains(abrev)) {
                        aux.add(exp);
                    }
                    }
                    else
                    {
                        boolean result = true;
                        result &= ensenyament.contains((client.getUserInfo().getGrup().getXEnsenyament()));
                        result &= estudis.equals(""+client.getUserInfo().getGrup().getXEstudis());
                        result &= grup.equals(client.getUserInfo().getGrup().getXGrup());

                        if(result) {
                            aux.add(exp);
                        }

                    }
                }
                if(rs1!=null) {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FitxesUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

         return aux;
        }


   /**
    * returns abrev of a teacher which is the tutor of student nexp i year any
    * @param nexp
    * @param any
    * @return 
    */
    public String getTutor(int nexp, int any)
    {
         Grup grup = new Grup(client).getGrup(nexp, any);
         String abrevtutor = getTutor(grup);
         return abrevtutor;
    }

    /**
     * Returns the tutor for a given grup
     * @param grup
     * @return 
     */
    public String getTutor(Grup grup)
    {
         String abrev = "";
        // String any = StringUtils.anyAcademic();

         String SQL1 = "SELECT DISTINCT prof FROM sig_horaris WHERE curso='" + grup.getKCursInt() + 
                       "' AND grupo='" + grup.getKGrup() + "' AND nivel LIKE '"+grup.getKNivell() + 
                       "%' AND asig LIKE 'TUT%'";

         //System.out.println(SQL1);

         try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            if (rs1 != null && rs1.next()) {
                abrev = StringUtils.noNull(rs1.getString("prof"));
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(FitxesUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

         return abrev;
    }


    public static Image iconToImage(Icon icon) {
          if (icon instanceof ImageIcon) {
              return ((ImageIcon)icon).getImage();
          } else {
              int w = icon.getIconWidth();
              int h = icon.getIconHeight();
              GraphicsEnvironment ge =
              GraphicsEnvironment.getLocalGraphicsEnvironment();
              GraphicsDevice gd = ge.getDefaultScreenDevice();
              GraphicsConfiguration gc = gd.getDefaultConfiguration();
              BufferedImage image = gc.createCompatibleImage(w, h);
              Graphics2D g = image.createGraphics();
              icon.paintIcon(null, g, 0, 0);
              g.dispose();
              return image;
          }
      }
 
    public static byte[] createByteArray(Image image) {

        try {

        int[] pix = new int[image.getWidth(null) * image.getHeight(null)];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, image.getWidth(null), image.getHeight(null), pix, 0, image.getWidth(null));

        pg.grabPixels();

        byte[] pixels = new byte[image.getWidth(null) * image.getHeight(null)];
        for (int j = 0; j <
        pix.length; j++) {
        pixels[j] = new Integer(pix[j]).byteValue();
        }

        return pixels;
        } catch (InterruptedException ex) {
        return null;
        }
    }

    public static byte[] createByteArray(Icon icon) {

        Image img = iconToImage(icon);
        return createByteArray(img);
    }

}
