/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.missatgeria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;

/**
 *
 * @author Josep
 */
public class MissatgeriaCollection {
    
    
    public static final String EMPTY_MESSAGE="No s'han trobat notes.";
    public static final int MAXLENGTHNOTES = 500;
    
    public static final String ITEM_ACTITUD = "actitud";
    public static final String ITEM_FEINA = "feina";
    public static final String ITEM_OBSERVACIONS = "observacions";
    public static final String ITEM_NOTES = "notes";

    private int numNous;
    private final IClient client;

    public MissatgeriaCollection(IClient client){
        this.client = client;
    }
    
    
    public int getNumSolPendents()
    {
         //check for new missatges (inbox)
        String SQL1 = "SELECT * FROM sig_missatgeria where destinatari='"+
                client.getUserInfo().getAbrev()+"' AND dataContestat is NULL";
        
        numNous = 0;
        
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                numNous += 1;
            }
            if(rs1!=null) {
                rs1.close();
                st.close();                       
            }
        } catch (SQLException ex) {
            Logger.getLogger(MissatgeriaCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numNous;
    }

     /**
      * Retorna les notes marcades com avaluables de les quals s'ha posat nota
      * 7/7/12 S'ha afegit publicarWEB='S', nomes mostrarà activitats publicades web & avaluables
    **/ 
     public String getAutoNotes(int expedient, int idMateria, int idProfe) {
         return getAutoNotes(client.getICoreData().anyAcademic, expedient, idMateria, idProfe);
    }
     
     
     public String getAutoNotes(int any, int expedient, int idMateria, int idProfe) {
        String txt = "";
        String dbName = (String) ICoreData.configTableMap.get("sgdDBPrefix")+any;
        String SQL1 = "SELECT al.expediente, ac.descripcion, DATE_FORMAT(ac.fecha, '[%d-%m-%y]') as fecha, aa.nota, "
        + " ac.idGrupAsig, ac.idProfesores FROM "+dbName+".actividadesalumno AS aa INNER JOIN "+dbName+".actividades "
        + " AS ac ON ac.id=aa.idActividades INNER JOIN "+dbName+".alumnos AS al ON al.id=aa.idAlumnos "
        + " WHERE (fechaEntrega<>'0000-00-00' OR nota>=0 ) AND evaluable='S' " //AND publicarWeb='S' "
        + " AND idProfesores='"+idProfe+"' AND expediente='"+expedient+"' AND idGrupAsig='"+idMateria+"' ORDER BY ac.fecha";
        
          
         try {
            Statement st = client.getSgd().createStatement();
            ResultSet rs1 = client.getSgd().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                   txt += rs1.getString("fecha")+ " " + rs1.getString("descripcion") + "="+ rs1.getString("nota") +";  ";          
                   
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(MissatgeriaCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //System.out.println("Auto notes reads : "+txt);
        
        if(txt.isEmpty()) {
             txt=EMPTY_MESSAGE;
         }
        
        int length = txt.length();
        if(length>MAXLENGTHNOTES)
        {
            txt = "···"+txt.substring(length-MAXLENGTHNOTES, length-1);
        }
        return txt;
    }
     
    public ArrayList<BeanMissatge> listSolicitudsPendents()
    {
        //check for new missatges (inbox)
        String SQL1 = "SELECT "
                + " prof.nombre, "
                + "  tenv.dia as dataEntrevista, "
                + "  mis.dataContestat, "
                + "  tenv.abrev as remitent, "
                + "  mis.id, "
                + "  mis.actitud, "
                + "  mis.notes, "
                + "  mis.feina, "
                + "  mis.comentaris, "
                + "  mis.materia, "
                + "  mis.idMateria, "
                + "  CONCAT(xes.Llinatge1,' ',xes.Llinatge2,', ', xes.Nom1) AS alumno, "
                + "  CONCAT(xh.Estudis,' ', xh.Grup) AS grupo, "
                + "  xh.Exp2, xd.Foto, ent.observacions "
                + " FROM "
                + " sig_missatgeria AS mis  "
                + " INNER JOIN tuta_entrevistes as tenv "
                + " ON mis.idEntrevista = tenv.id "
                + " LEFT JOIN "
                + " sig_professorat AS prof  "
                + " ON prof.abrev = tenv.abrev  "
                + " INNER JOIN "
                + " tuta_entrevistes AS ent "
                + " ON ent.id = mis.idEntrevista "
                + " INNER JOIN `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne AS xes "
                + " ON xes.Exp2=ent.exp2 "
                + " INNER JOIN `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne_historic AS xh "
                + " ON xh.Exp2=xes.Exp2 AND xh.AnyAcademic='" + client.getICoreData().anyAcademic + "' "
                + " LEFT JOIN`" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne_detall AS xd "
                + " ON xd.Exp_FK_ID=xes.Exp2 "
                + " WHERE destinatari='" + client.getUserInfo().getAbrev() + "'  "
                + " AND dataContestat IS NULL  "
                + " ORDER BY tenv.dia ";

//        Calendar cal0 = Calendar.getInstance();
//        cal0.add(Calendar.DAY_OF_MONTH, -1);

        ArrayList<BeanMissatge> missatges = new ArrayList<BeanMissatge>();
//        int ncaducats = 0;
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);

            while (rs1 != null && rs1.next()) {
                BeanMissatge mis = new BeanMissatge();
                String remite = rs1.getString("nombre");
                java.util.Date data = rs1.getDate("dataEntrevista");
                java.util.Date data2 = rs1.getDate("dataContestat");
                String remite_abrev = rs1.getString("remitent");
                int id = rs1.getInt("id");
                if (remite_abrev.equals("ADMIN")) {
                    remite = "Administrador";
                } else if (remite_abrev.equals("PREF")) {
                    remite = "Prefectura";
                }

                String actitud = rs1.getString("actitud");
                String notes = rs1.getString("notes");
                String feina = rs1.getString("feina");
                String comentaris = rs1.getString("comentaris");

                mis.setDataEntrevista(data);
                mis.setDataContestat(data2);
                mis.setRemitent(remite);
                mis.setRemitent_abrev(remite_abrev);
                mis.setId(id);
                mis.setActitud(actitud);
                mis.setComentari(comentaris);
                mis.setFeina(feina);
                mis.setNotes(notes);
                mis.setNomAlumne(rs1.getString("alumno"));
                mis.setGrupo(rs1.getString("grupo"));
                mis.setMateria(rs1.getString("materia"));
                mis.setIdMateria(rs1.getInt("idMateria"));
                mis.setExpedient(rs1.getInt("Exp2"));
                mis.setPhoto(rs1.getBytes("Foto"));
                mis.setInstruccions(rs1.getString("observacions"));

                missatges.add(mis);
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(MissatgeriaCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return missatges;
      }
    
    
    public ArrayList<BeanMissatge> loadEnviats(String orderQuery)
    {
        ArrayList<BeanMissatge> list = new ArrayList<BeanMissatge>();
        //check for new missatges (outbox)
               String SQL1 = "SELECT "
                + " prof.nombre, "
                + "  tenv.dia as dataEntrevista, "
                + "  mis.dataContestat, "
                + "  tenv.abrev as remitent, "
                + "  mis.id, "
                + "  mis.actitud, "
                + "  mis.notes, "
                + "  mis.feina, "
                + "  mis.comentaris, "
                + "  mis.materia, "
                + "  mis.idMateria, "
                + "  CONCAT(xes.Llinatge1,' ',xes.Llinatge2,', ', xes.Nom1) AS alumno, "
                + "  CONCAT(xh.Estudis,' ', xh.Grup) AS grupo, "
                + "  xh.Exp2 "
                + " FROM "
                + " sig_missatgeria AS mis  "
                + " INNER JOIN tuta_entrevistes as tenv "
                + " ON tenv.id = mis.idEntrevista "
                + " LEFT JOIN "
                + " sig_professorat AS prof  "
                + " ON prof.abrev = tenv.abrev  "
                + " INNER JOIN "
                + " tuta_entrevistes AS ent "
                + " ON ent.id = mis.idEntrevista "
                + " INNER JOIN `"+ICoreData.core_mysqlDBPrefix+"`.xes_alumne AS xes "
                + " ON xes.Exp2=ent.exp2 "
                + " INNER JOIN `"+ICoreData.core_mysqlDBPrefix+"`.xes_alumne_historic AS xh "
                + " ON xh.Exp2=xes.Exp2 AND xh.AnyAcademic='"+ client.getICoreData().anyAcademic +"' "
                + " WHERE destinatari = '"+client.getUserInfo().getAbrev()+"'  "
                + " AND dataContestat IS NOT NULL  "
                + orderQuery;
                //System.out.println("enviats: "+SQL1);
                //System.out.println("@outbox::"+SQL1);
         
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                BeanMissatge mis = new BeanMissatge();
                String remite = rs1.getString("nombre");
                java.util.Date data = rs1.getDate("dataEntrevista");
                java.util.Date data2 = rs1.getDate("dataContestat");
                String remite_abrev = rs1.getString("remitent");
                int id = rs1.getInt("id");
                if(remite_abrev.equals("ADMIN")) {
                    remite = "Administrador";
                }
                else if(remite_abrev.equals("PREF")) {
                    remite = "Prefectura";
                }
                
                String actitud = rs1.getString("actitud");
                String notes = rs1.getString("notes");
                String feina = rs1.getString("feina");
                String comentaris = rs1.getString("comentaris");
                
                mis.setDataEntrevista(data);
                mis.setDataContestat(data2);
                mis.setRemitent(remite);
                mis.setRemitent_abrev(remite_abrev);
                mis.setId(id);
                mis.setActitud(actitud);
                mis.setComentari(comentaris);
                mis.setFeina(feina);
                mis.setNotes(notes);
                mis.setNomAlumne(rs1.getString("alumno"));
                mis.setGrupo(rs1.getString("grupo"));
                mis.setMateria(rs1.getString("materia"));
                mis.setIdMateria(rs1.getInt("idMateria"));
                mis.setExpedient(rs1.getInt("Exp2"));
               
                list.add(mis);   
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MissatgeriaCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

 /**
 * Returns the number of out-of-date instances 
 * @param list
 * @return 
 */
    public int getNumCaducats(final ArrayList<BeanMissatge> list) {
        int ncaducats = 0;

        Calendar cal0 = Calendar.getInstance();
        cal0.add(Calendar.DAY_OF_MONTH, -1);

        for(BeanMissatge bm: list)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(bm.getDataEntrevista());
            if (cal.before(cal0)) {
                ncaducats += 1;
            }
        }
        
       return ncaducats;
    }
    
    

    public int saveBeanMissatge(final BeanMissatge bean) {
        if(bean.getId()<=0)
        {
            return insert(bean);
        }
        else
        {
            return update(bean);
        }
    }

    private int insert(final BeanMissatge bean) {
        
        String SQL1 = "INSERT INTO sig_missatgeria (idEntrevista,destinatari,idMateria,materia,actitud,notes,feina,comentaris,dataContestat,idMensajeProfesor) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";
     
        Object[] objs = new Object[]{bean.idEntrevista, bean.destinatari_abrev, bean.idMateria,
            bean.materia, bean.actitud, bean.notes, bean.feina, bean.comentari, bean.dataContestat, bean.idMensajeProfesor};
        int nup = client.getMysql().preparedUpdateID(SQL1, objs);
        if(nup>0)
        {
            bean.setId(nup);
        }
        return nup;
    }

    private int update(final BeanMissatge bean) {
        String SQL1 = "UPDATE sig_missatgeria SET idEntrevista=?,destinatari=?,"
                + "idMateria=?,materia=?,actitud=?,notes=?,feina=?,comentaris=?,"
                + "dataContestat=?,idMensajeProfesor=? WHERE id="+bean.id;
     
        Object[] objs = new Object[]{bean.idEntrevista, bean.destinatari_abrev, bean.idMateria,
            bean.materia, bean.actitud, bean.notes, bean.feina, bean.comentari, bean.dataContestat, bean.idMensajeProfesor};
        int nup = client.getMysql().preparedUpdate(SQL1, objs);
        
        return nup;
    }
    
    public int deleteBeanMissatge(final BeanMissatge bean)
    {
         String SQL1 = "DELETE FROM sig_missatgeria WHERE id="+bean.id;
         return client.getMysql().executeUpdate(SQL1);
    }
    
    
    ///////
    public ArrayList<String> listMissatgeriaItems(String tipus) {
                       
        ArrayList<String> model = new ArrayList();
        model.add("Triau opcions");
        
        String SQL1 = "SELECT * from sig_missatgeria_items where tipus='"+tipus+"' order by ordre, item";
         try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                model.add(rs1.getString("item"));
            }
            if(rs1!=null) {
                 rs1.close();
                 st.close();
             }
        } catch (SQLException ex) {
            Logger.getLogger(MissatgeriaCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return model;
    }
}
