/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.reserves;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.dates.BeanHoraClasse;
import org.iesapp.util.DataCtrl;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class ReservesCollection {

    private final IClient client;

    public ReservesCollection(IClient client) {
        this.client = client;
    }

    /**
     * lists all reserves of teacher abrev,
     * if filter is true, only no caducades are shown
     * showAules & showMaterial
     * orderSQL
     * 
     * @param abrev
     * @param filter
     * @param showAules
     * @param showMaterial
     * @param orderSQL
     * @return 
     */
    public ArrayList<BeanReserves> getReservesOf(String abrev, boolean filter,
            boolean showAules, boolean showMaterial, String orderSQL) {

        String condition = "";
        String and = "";
        if (showAules && showMaterial) {
            condition += and + " (tipus=1 OR tipus=2) ";
            and = " AND ";
        }
        if (showAules && !showMaterial) {
            condition += and + " tipus=2 ";
            and = " AND ";
        }
        if (!showAules && showMaterial) {
            condition += and + " tipus=1 ";
            and = " AND ";
        }
        if (!abrev.equals("*")) {
            condition += and + " res.abrev='" + abrev + "' ";
            and = " AND ";
        }
        if (!filter) {
            condition += and + " data>=CURRENT_DATE() ";
            and = " AND ";
        }


        if (!condition.isEmpty()) {
            condition = " WHERE " + condition;
        }


        ArrayList<BeanReserves> list = new ArrayList<BeanReserves>();

        String SQL1 = "SELECT  "
                + " res.*, prof.nombre,"
                + " esp.descripcio AS auladesc, "
                + " CONCAT(mat.material, ', ',mat.ubicacio) AS matdesc, "
                + "  CONCAT('(',res.hora,'a) ',TIME_FORMAT(h.inicio,'%H:%i'), '-', TIME_FORMAT(h.fin,'%H:%i')) AS formatedhora, "
                + " IF(res.DATA<CURRENT_DATE,'S','N') AS caducada "
                + " FROM "
                + " sig_reserves AS res  "
                + "   LEFT JOIN "
                + " sig_espais AS esp  "
                + "   ON esp.aula = res.id_recurs  "
                + " LEFT JOIN  "
                + "   sig_reserves_material AS mat "
                + "   ON mat.id = res.id_recurs "
                + " LEFT JOIN "
                + " sig_hores_classe AS h "
                //+ " ON h.codigo = IF(res.hora<10,CONCAT('0',res.hora,'a'),CONCAT(res.hora,'a')) "
                + " ON h.codigo = res.hora "
                + " LEFT JOIN "
                + " sig_professorat AS prof ON prof.abrev=res.abrev"
                + condition
                + orderSQL;


        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            while (rs1 != null && rs1.next()) {
                BeanReserves bean = new BeanReserves();
                bean.id = rs1.getInt("id");
                bean.abrev = rs1.getString("abrev");
                int tipo = rs1.getInt("tipus");
                bean.tipusConcepte = tipo;
                String id_recurs = rs1.getString("id_recurs");
                bean.idConcepte = id_recurs;
                bean.dia = rs1.getDate("data");
                bean.hora = rs1.getInt("hora");
                bean.setFormatedhora(rs1.getString("formatedhora"));
                bean.motiu = rs1.getString("motiu");
                bean.caducada = rs1.getString("caducada").equalsIgnoreCase("S");
                bean.nombreProfesor = StringUtils.noNull(rs1.getString("nombre"));

                if (tipo == 1) {
                    bean.concepte = StringUtils.noNull(rs1.getString("matdesc"));
                } else if (tipo == 2) {
                    bean.concepte = "(" + id_recurs + ") " + StringUtils.noNull(rs1.getString("auladesc"));
                }
                list.add(bean);

            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int delete(int id) {
        String SQL1 = "DELETE FROM sig_reserves where id=" + id;
        int nup = client.getMysql().executeUpdate(SQL1);
        return nup;
    }

    //2=aules 1=material
    public ArrayList<BeanRecursos> getRecursos(int tipusRecurs) {
        ArrayList<BeanRecursos> list = new ArrayList<BeanRecursos>();

        if (tipusRecurs == BeanReserves.AULES) {
            String SQL1 = "SELECT * FROM sig_espais where reservable='1'";


            try {
                Statement st = client.getMysql().createStatement();
                ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
                while (rs1 != null && rs1.next()) {

                    list.add(new BeanRecursos(rs1.getString("descripcio"), rs1.getString("aula")));
                }
                if (rs1 != null) {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReservesCollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String SQL1 = "SELECT id,CONCAT(material,' ',ubicacio) as item FROM sig_reserves_material";

            try {
                Statement st = client.getMysql().createStatement();
                ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
                while (rs1 != null && rs1.next()) {

                    list.add(new BeanRecursos(rs1.getString("item"),
                            rs1.getString("id")));
                }
                if (rs1 != null) {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReservesCollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;
    }

    public ArrayList<BeanSelectHores> listDisponibilitat(java.util.Date dia, String torn, int tipusRecurs, String idRecurs) {
        ArrayList<BeanSelectHores> list = new ArrayList<BeanSelectHores>();


        int lag = torn.equals("2") ? 7 : 0;
        ArrayList<BeanHoraClasse> listHoraClasses = client.getDatesCollection().listHoraClasses(BeanHoraClasse.CLASSE);

        for (int i = 0; i < 7; i++) {
            BeanSelectHores bean = new BeanSelectHores();
            int idHora = i + 1 + lag;
            bean.setIdHora(idHora);
            bean.setHora(StringUtils.formatTime(listHoraClasses.get(idHora - 1).getInicio()) + " - "
                    + StringUtils.formatTime(listHoraClasses.get(idHora - 1).getFin()));
            list.add(bean);
        }


        DataCtrl cd = new DataCtrl(dia);
        int intdiasetmana = cd.getIntDia();
        String diaSQL = cd.getDataSQL();



        if (tipusRecurs == BeanReserves.AULES) //RESERVA D'AULES
        {
            //COMPROVA QUE NO HI TENGUI CLASSE ALGU

            String SQL1 = "SELECT hor.hora, CONCAT('Hi té classe ', prof.nombre, "
                    + "' (',prof.depart,')') AS comentari FROM sig_horaris AS hor "
                    + "LEFT JOIN sig_professorat AS prof ON prof.abrev=hor.prof "
                    + "WHERE aula='" + idRecurs + "' AND dia='" + intdiasetmana + "' ORDER BY hora";

            try {
                Statement st = client.getMysql().createStatement();
                ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
                while (rs1 != null && rs1.next()) {
                    int h = rs1.getInt("hora");
                    int pos = h - 1 - lag;
                    if (pos >= 0 && pos < list.size()) {
                        list.get(pos).setComment(rs1.getString("comentari"));
                        list.get(pos).setDisponible(false);
                    }
                }
                if (rs1 != null) {
                    rs1.close();
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReservesCollection.class.getName()).log(Level.SEVERE, null, ex);
            }


        }

        //COMPROVA SI HA ESTAT RESERVADA PER ALGU

        String SQL1 = "SELECT res.hora, "
                + " CONCAT('Reservat per ',IF(res.abrev='PREF','PREFECTURA',  "
                + " IF(res.abrev='ADMIN','ADMINISTRADOR', IF(res.abrev='GUARD','Professor de guàrdia',prof.nombre))), "
                + " ' (',IF(prof.depart IS NULL,'',prof.depart),')') AS comentari  "
                + " FROM sig_reserves AS res LEFT JOIN sig_professorat AS prof ON prof.abrev=res.abrev WHERE tipus='" + tipusRecurs + "' AND   "
                + " id_recurs='" + idRecurs + "' AND DATA='" + diaSQL + "' ORDER BY hora; ";


        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            while (rs1 != null && rs1.next()) {

                int pos = rs1.getInt("hora") - lag - 1;
                if (pos >= 0 && pos < 8) {
                    list.get(pos).setComment(rs1.getString("comentari"));
                    list.get(pos).setDisponible(false);
                }
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservesCollection.class.getName()).log(Level.SEVERE, null, ex);
        }


        return list;
    }

    public void makeReserva(String abrev, String tipus, String idRecurs, java.util.Date data, ArrayList<Integer> hores, String motiu) {
        //System.out.print("size of hores " + hores.size());
        for (int i = 0; i < hores.size(); i++) {
            String SQL1 = "INSERT INTO sig_reserves (abrev, tipus, id_recurs, data, hora, motiu) VALUES(?,?,?,?,?,?)";
            Object[] obj = new Object[]{abrev, tipus, idRecurs, data, hores.get(i), motiu};
            int nup = client.getMysql().preparedUpdate(SQL1, obj);

        }

    }
    
    /**
     * list table entries of sig_reserves_materials
     * @return 
     */
    public ArrayList<BeanMaterial> listMaterials(String condition)
    {
        return new Material(client).getCrud().list(condition);
    }
            
}
