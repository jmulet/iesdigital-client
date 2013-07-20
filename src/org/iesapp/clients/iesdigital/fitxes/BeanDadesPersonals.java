/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.fitxes;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.clients.iesdigital.alumnat.Grup;
import org.iesapp.util.DataCtrl;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class BeanDadesPersonals {

    private boolean repetidor;
    private boolean repetidorEditable;
    private String nese;
    protected float anysDecimal;
    private final IClient client;

    public BeanDadesPersonals(IClient client) {
        this.client = client;
        telefonsUrgencia = new ArrayList<String>();
        tutorsInfo = new ArrayList<PareTutor>();
        profPermisos = new ArrayList<String>();
        listMedicaments = new ArrayList<BeanMedicament>();
    }

    public void getFromDB(int nexp, int anyAcademicFitxes) {
        String SQL1 = "SELECT *, DATEDIFF(CURRENT_DATE(),xes.DataNaixement)/365 as anys FROM `"
                + ICoreData.core_mysqlDBPrefix + "`.xes_alumne AS xes INNER JOIN `"
                + ICoreData.core_mysqlDBPrefix + "`.xes_alumne_historic"
                + "  AS xh ON xh.Exp2=xes.Exp2 WHERE xh.Exp2='" + nexp
                + "' AND AnyAcademic='" + anyAcademicFitxes + "'";
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            while (rs1 != null && rs1.next()) {

                // CARREGA AQUI LES PROPIETATS DE LA BASE DE DADES

                String permis = StringUtils.noNull(rs1.getString("Permisos"));
                profPermisos = StringUtils.parseStringToArray(permis, ",", StringUtils.CASE_UPPER);
                // //System.out.println(profPermisos);

                llinatge1 = StringUtils.noNull(rs1.getString("Llinatge1"));
                llinatge2 = StringUtils.noNull(rs1.getString("Llinatge2"));
                nom1 = StringUtils.noNull(rs1.getString("Nom1"));
                edat = rs1.getInt("Edat");
                sexe = StringUtils.noNull(rs1.getString("Sexe"));
                nacionalitat = StringUtils.noNull(rs1.getString("Nacionalitat"));
                dataNaixament = rs1.getDate("DataNaixement");
                paisNaixament = StringUtils.noNull(rs1.getString("PaisNaixement"));
                provinciaNaixament = StringUtils.noNull(rs1.getString("ProvinciaNaixement"));
                localitatNaixament = StringUtils.noNull(rs1.getString("LocalitatNaixement"));
                dni = StringUtils.noNull(rs1.getString("DNI"));
                targetaSanitaria = StringUtils.noNull(rs1.getString("TargetaSanitaria"));
                adreca = StringUtils.noNull(rs1.getString("Adreca"));
                municipi = StringUtils.noNull(rs1.getString("Municipi"));
                localitat = StringUtils.noNull(rs1.getString("Localitat"));
                cp = StringUtils.noNull(rs1.getString("CP"));
                numRep = rs1.getInt("NumRep");
                repetidor = rs1.getInt("Repetidor") > 0; //condicio sobre el curs actual
                repetidorEditable = true;
                expedient = rs1.getInt("Exp2");
                sgdUser = "" + expedient;
                sgdPasswd = rs1.getString("pwd");

                grupLletra = rs1.getString("Grup");
                estudis = rs1.getString("Estudis");
                ensenyament = rs1.getString("Ensenyament");
                profTutor = rs1.getString("ProfTutor");
                nese = StringUtils.noNull(rs1.getString("anee"));
                anee = !nese.isEmpty(); //DETERMINES IF IT IS NESE
                setAnysDecimal(rs1.getFloat("anys"));
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }

///DETERMINA SI AQUEST ALUMNE ES REPETIDOR EN FUNCIO DE L'HISTORIC
///si es així el marca com a repetidor i no deixarà editar el camp

        SQL1 = "SELECT xh.Exp2 FROM `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne_historic AS xh INNER JOIN `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne AS xes ON xes.Exp2=xh.Exp2 WHERE "
                + "   (estudis IN (SELECT estudis FROM `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne_historic AS xh2 WHERE xh.Exp2=xh2.Exp2 AND AnyAcademic<"
                + anyAcademicFitxes + " AND AnyAcademic>0 AND xh2.Exp2=" + expedient + ") AND AnyAcademic=" + anyAcademicFitxes + ") "
                + " AND xh.Exp2=" + expedient;
        //System.out.println(SQL1);

        boolean q = false;

        try {

            Statement st7 = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st7);
            while (rs1 != null && rs1.next()) {
                q = true;
            }
            if (rs1 != null) {
                rs1.close();
                st7.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (q) {
            repetidor = true;
            repetidorEditable = false;
        }
////////////////////////////////

        SQL1 = "SELECT * FROM `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne_detall WHERE Exp_FK_ID=" + nexp;

        try {
            Statement st2 = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st2);
            while (rs1 != null && rs1.next()) {
                // CARREGA AQUI LES PROPIETATS DE LA BASE DE DADES
                foto = rs1.getBytes("Foto");
            }
            if (rs1 != null) {
                rs1.close();
                st2.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (foto != null) {
            javax.swing.ImageIcon image = new ImageIcon(foto); //Toolkit.getDefaultToolkit().createImage(foto);
            photo = image.getImage();
        }


////////////////////////////////    
        telefonsUrgencia.clear();
        tutorsInfo.clear();

        SQL1 = "SELECT * from `" + ICoreData.core_mysqlDBPrefix + "`.xes_dades_pares where Exp2=" + nexp;

        try {
            Statement st3 = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st3);

            while (rs1 != null && rs1.next()) {
                // CARREGA AQUI LES PROPIETATS DE LA BASE DE DADES
                String telef = StringUtils.noNull(rs1.getString("Telefon"));
                if (!telef.isEmpty()) {
                    telefonsUrgencia.add(telef);
                }

                PareTutor pt = new PareTutor();
                String relative = StringUtils.noNull(rs1.getString("Relatiu1"));
                String nom = StringUtils.noNull(rs1.getString("Tutor"));
                String telftutor = StringUtils.noNull(rs1.getString("TelefonTutor"));
                String emailtutor = StringUtils.noNull(rs1.getString("EmailTutor"));
                String professio = StringUtils.noNull(rs1.getString("Professio"));
                int edat_tutor = rs1.getInt("Edat");

                pt.setParentesc(relative);
                pt.setNom(nom);
                pt.setTelefons(telftutor);
                pt.setEmails(emailtutor);
                pt.setProfessio(professio);
                pt.setEdat(edat_tutor);

                tutorsInfo.add(pt);
            }
            if (rs1 != null) {
                rs1.close();
                st3.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////////////////////////////////MEDICAMENTS    
        listMedicaments.clear();

        SQL1 = "SELECT observacions FROM `" + ICoreData.core_mysqlDBPrefix + "`.sig_medicaments_observ WHERE exp2=" + nexp;
        String observ = "";
        try {
            Statement st4 = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st4);

            while (rs1 != null && rs1.next()) {
                observ = rs1.getString(1);
            }
            if (rs1 != null) {
                rs1.close();
                st4.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }

        SQL1 = "SELECT DISTINCT "
                + " med.id, "
                + " med.descripcio, "
                + " IF(medal.idMedicament IS NULL,'N','S') AS autoritzat "
                + " FROM  `" + ICoreData.core_mysqlDBPrefix + "`.sig_medicaments AS med  "
                + " LEFT JOIN `" + ICoreData.core_mysqlDBPrefix + "`.sig_medicaments_alumnes AS medal  "
                + " ON medal.exp2 =" + nexp + " AND medal.idMedicament=med.id";

        try {
            Statement st5 = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st5);

            while (rs1 != null && rs1.next()) {
                // CARREGA AQUI LES PROPIETATS DE LA BASE DE DADES
                BeanMedicament bean = new BeanMedicament();
                bean.id = rs1.getInt("id");
                bean.descripcio = rs1.getString("descripcio");
                bean.autoritzat = rs1.getString("autoritzat").equalsIgnoreCase("S");
                bean.observacions = observ;
                listMedicaments.add(bean);
            }
            if (rs1 != null) {
                rs1.close();
                st5.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void commitToDB(int nexp) {

//        String SQL1 = "UPDATE " + IClient.dadesAlumnes +  " SET Llinatge1='"+ llinatge1 +"',  " +
//                                            "Llinatge2='"+ llinatge2 +"',  " +
//                                            "Nom1='"+ nom1 +"',  " +
//                                            "Edat='"+ edat +"',  " +
//                                            "Sexe='"+ sexe +"',  " +
//                                            "Nacionalitat='"+ nacionalitat +"',  " +
//                                            "DataNaixement='"+ dataNaixament +"',  " +
//                                            "PaisNaixement='"+ paisNaixament +"',  " +
//                                            "ProvinciaNaixement='"+ provinciaNaixament +"',  " +
//                                            "LocalitatNaixement='"+ localitatNaixament +"',  " +
//                                            "DNI='"+ dni +"',  " +
//                                            "TargetaSanitaria='"+ targetaSanitaria +"',  " +
//                                            "Adreca='"+ adreca +"',  " +
//                                            "Municipi='"+ municipi +"',  " +
//                                            "Localitat='"+ localitat +"',  " +
//                                            "CP='"+ cp +"'  " +
//                                            " where Exp="+nexp;
//
//        int nup = client.getMysql().executeUpdate(SQL1);
//        //System.out.println( "nup="+nup+" ; "+SQL1);


        String SQL1 = "UPDATE `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne SET Llinatge1 =?, Llinatge2 =?, Nom1 =?, "
                + " Edat=?, Sexe=?, Nacionalitat=?, DataNaixement=?, PaisNaixement=?, "
                + " ProvinciaNaixement=?, LocalitatNaixement=?, DNI=?, TargetaSanitaria=?, "
                + " Adreca=?, Municipi=?, Localitat=?, CP=?, NumRep=?, Repetidor=? WHERE Exp2=" + nexp;

        Object[] updatedValues = new Object[]{llinatge1, llinatge2, nom1,
            edat, sexe, nacionalitat, dataNaixament, paisNaixament,
            provinciaNaixament, localitatNaixament, dni, targetaSanitaria,
            adreca, municipi, localitat, cp, numRep, repetidor};

        int nup = client.getMysql().preparedUpdate(SQL1, updatedValues);

        //   //System.out.println("nup="+nup+"; "+SQL1);

//        String SQL2 = "INSERT INTO " + IClient.dadesAlumnes +  "_detall SET Foto=?,  Exp_FK_ID=?, DadesMediques=?";
//        PreparedStatement ps;
//        try {
//            Connection con = client.getMysql().getConnection();
//            //con.setAutoCommit(false);
//            ps = con.prepareStatement(SQL2);
//
//            File file = new File("foto.png");
//            FileInputStream fis;
//            try {
//                fis = new FileInputStream(file);
//                //ps.setBinaryStream(1, fis, (int) file.length());
//                ps.setInt(2, 2343432);
//                ps.setString(3, "");
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//
//
//            ps.setBytes(1, foto);
//
//            int s = ps.executeUpdate();
//            if(s>0) {
//                    //System.out.println("Image Uploaded successfully !");
//            }
//            else {
//                    //System.out.println("unsucessfull to upload image.");
//            }
//
//            //con.commit();
//            ps.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //System.out.println( "nup="+nup+" ; "+SQL1);

    }

    public boolean isFitxaCursCreated(int nexp, String curs) {
        boolean iscreated = false;
        String any = curs.trim();
        any = StringUtils.BeforeLast(any, "-");
        String SQL1 = "SELECT * from `" + ICoreData.core_mysqlDBPrefix + "`.fitxa_alumne_curs where Exp_FK_ID=" + nexp + " AND "
                + "Any_academic='" + any + "'";
        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            if (rs1 != null && rs1.next()) {
                iscreated = true;
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }

        return iscreated;
    }

//Crea una fitxa pel curs academic
    public void createFitxaCurs(int nexp, String cursAcademic) {
        DataCtrl cd = new DataCtrl();
        String datacreacio = cd.getDataSQL() + " " + cd.getHora();

        //Primer comprova si existeix
        String SQL1 = "Select Exp_FK_ID from `" + ICoreData.core_mysqlDBPrefix + "`.fitxa_alumne_curs where Exp_FK_ID='" + nexp + "' "
                + " AND Any_academic='" + cursAcademic + "'";
        //System.out.println(SQL1);


        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            if (rs1 != null && rs1.next()) {
                return;
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }

        int cursId = Integer.parseInt(StringUtils.BeforeLast(cursAcademic, "-"));

        //Intenta trobar la informació a la taula històric
        String SQL2 = "Select * from `" + ICoreData.core_mysqlDBPrefix + "`.xes_alumne_historic where AnyAcademic='" + cursId + "' AND Exp2=" + nexp;

        SQL1 = "INSERT INTO `" + ICoreData.core_mysqlDBPrefix + "`.fitxa_alumne_curs "
                + "(Exp_FK_ID, "
                + "IdCurs_FK_ID,"
                + " Estudis, "
                + "Grup, "
                + "Any_academic, "
                + "Ensenyament,"
                + " Professor, "
                + "Observacions, "
                + "DerivatORI, "
                + "MotiuDerivacioORI, "
                + "NumMateriesSuspJuny, "
                + "NotaMitjaFinal, "
                + "NumAL_1rTRI, "
                + "NumAL_2nTRI, "
                + "NumAL_3rTRI, "
                + "NumAG_1rTRI,"
                + " NumAG_2nTRI, "
                + "NumAG_3rTRI, "
                + "Sancions, "
                + "Programes, "
                + "DataCreacio,"
                + " Modificat, "
                + "DataModificacio) "
                + " VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


        Object[] values = null;
        try {
            Statement st2 = client.getMysql().createStatement();
            ResultSet rs2 = client.getMysql().getResultSet(SQL2, st2);
            if (rs2 != null && rs2.next()) {
                //ho fa amb les dades importades del SGD

                String lensenyament = rs2.getString("Ensenyament");
                String lestudis = rs2.getString("Estudis");
                String lgrupo = rs2.getString("Grup");
                String lprofe = rs2.getString("ProfTutor");

                Grup grup = new Grup(Grup.XESTIB, lensenyament, lestudis, lgrupo, client);

                values = new Object[]{nexp, cursId, grup.getXEstudis(), grup.getXGrup(), cursAcademic,
                    grup.getXEnsenyament(), lprofe, "", 0, "", 0, 0, 0, 0, 0, 0,
                    0, 0, "", "", datacreacio, "", ""};

                //System.out.println("Dades historiques;");
                //System.out.println("SQL;;"+SQL1);

            } else {
                //ho fa amb les dades del curs actual

                String nivell = "";
                String grup = "";
                String profe = "";
                if (StringUtils.anyAcademic().equals(cursAcademic)) {
                    String abrev = client.getFitxesClient().getFitxesUtils().getTutor(nexp, cursId);

                    profe = StringUtils.noNull(client.getProfessoratData().getMapAbrev().get(abrev));
               }

                Grup grupo = new Grup(client).getGrup(nexp, cursId);
                grupo.print();
                //System.out.println("profe="+profe);

                values = new Object[]{nexp, cursId, grupo.getXEstudis(), grupo.getXGrup(), cursAcademic,
                    grupo.getXEnsenyament(), profe, "", 0, "", 0, 0, 0, 0, 0, 0,
                    0, 0, "", "", datacreacio, "", ""};

                //System.out.println("Dades actuals;");
                //System.out.println("SQL;;"+SQL1);


            }
            if (rs2 != null) {
                rs2.close();
                st2.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Quan crea una fitxa nova, s'ha d'incloure si existeix a la base de dades el grup

        //System.out.println(values);
        int nup = client.getMysql().preparedUpdate(SQL1, values);

        ////System.out.println("Nupdate:"+nup +"; Created fitxa per curs "+cursAcademic+" Exp2= "+ nexp +" : " + SQL1);
    }

    public ArrayList<String> getYears(int nexp) {
        ArrayList<String> llista = new ArrayList<String>();
        String SQL1 = "SELECT * from `" + ICoreData.core_mysqlDBPrefix + "`.fitxa_alumne_curs where Exp_FK_ID=" + nexp + " order by Any_academic";


        try {
            Statement st = client.getMysql().createStatement();
            ResultSet rs1 = client.getMysql().getResultSet(SQL1, st);
            while (rs1 != null && rs1.next()) {
                llista.add(StringUtils.noNull(rs1.getString("Any_academic")));
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeanDadesPersonals.class.getName()).log(Level.SEVERE, null, ex);
        }


        return llista;
    }
    // DEFINIR AQUI LES PROPERTIES
    private String llinatge1 = "";
    private String llinatge2 = "";
    private String nom1 = "";
    private Integer edat = 0;
    private byte[] foto = null;
    private String sexe = "";
    private String nacionalitat = "";
    private Date dataNaixament = new Date(0);
    private String paisNaixament = "";
    private String provinciaNaixament = "";
    private String localitatNaixament = "";
    private String dni = "";
    private String targetaSanitaria = "";
    private String adreca = "";
    private String municipi = "";
    private String localitat = "";
    private String cp = "";
    private String sgdUser = "...";
    private String sgdPasswd;
    private Integer numRep = 0;
    private int expedient = 0;
    private Object photo;
    private ArrayList<String> telefonsUrgencia;
    private ArrayList<PareTutor> tutorsInfo;
    private ArrayList<String> profPermisos;
    private String estudis = "";
    private String grupLletra = "";
    private String ensenyament = "";
    private String profTutor = "";
    private boolean anee = false;
    protected ArrayList<BeanMedicament> listMedicaments;

    public String getProfTutor() {
        return profTutor;
    }

    public void setProfTutor(String profTutor) {
        this.profTutor = profTutor;
    }

    public String getEnsenyament() {
        return ensenyament;
    }

    public void setEnsenyament(String ensenyament) {
        this.ensenyament = ensenyament;
    }

    public String getGrupLletra() {
        return grupLletra;
    }

    public void setGrupLletra(String grupLletra) {
        this.grupLletra = grupLletra;
    }

    public String getEstudis() {
        return estudis;
    }

    public void setEstudis(String estudis) {
        this.estudis = estudis;
    }

    /**
     * Get the value of profPermisos
     *
     * @return the value of profPermisos
     */
    public ArrayList<String> getProfPermisos() {
        return profPermisos;
    }

    /**
     * Set the value of profPermisos
     *
     * @param profPermisos new value of profPermisos
     */
    public void setProfPermisos(ArrayList<String> profPermisos) {
        this.profPermisos = profPermisos;
    }

    public ArrayList<PareTutor> getTutorsInfo() {
        return tutorsInfo;
    }

    public void setTutorsInfo(ArrayList<PareTutor> tutorsInfo) {
        this.tutorsInfo = tutorsInfo;
    }

    public ArrayList<String> getTelefonsUrgencia() {
        return telefonsUrgencia;
    }

    public void setTelefonsUrgencia(ArrayList<String> telefonsUrgencia) {
        this.telefonsUrgencia = telefonsUrgencia;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public int getExpedient() {
        return expedient;
    }

    public void setExpedient(int expedient) {
        this.expedient = expedient;
    }

    public int getNumRep() {
        return numRep;
    }

    public void setNumRep(int numRep) {
        this.numRep = numRep;
    }

    public String getSgdPasswd() {
        return sgdPasswd;
    }

    public void setSgdPasswd(String sgdPasswd) {
        this.sgdPasswd = sgdPasswd;
    }

    public String getSgdUser() {
        return sgdUser;
    }

    public void setSgdUser(String sgdUser) {
        this.sgdUser = sgdUser;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getLocalitat() {
        return localitat;
    }

    public void setLocalitat(String localitat) {
        this.localitat = localitat;
    }

    public String getMunicipi() {
        return municipi;
    }

    public void setMunicipi(String municipi) {
        this.municipi = municipi;
    }

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public String getTargetaSanitaria() {
        return targetaSanitaria;
    }

    public void setTargetaSanitaria(String targetaSanitaria) {
        this.targetaSanitaria = targetaSanitaria;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLocalitatNaixament() {
        return localitatNaixament;
    }

    public void setLocalitatNaixament(String localitatNaixament) {
        this.localitatNaixament = localitatNaixament;
    }

    public String getProvinciaNaixament() {
        return provinciaNaixament;
    }

    public void setProvinciaNaixament(String provinciaNaixament) {
        this.provinciaNaixament = provinciaNaixament;
    }

    public String getPaisNaixament() {
        return paisNaixament;
    }

    public void setPaisNaixament(String paisNaixament) {
        this.paisNaixament = paisNaixament;
    }

    public Date getDataNaixament() {
        return dataNaixament;
    }

    public void setDataNaixament(Date dataNaixament) {
        this.dataNaixament = dataNaixament;
    }

    public String getNacionalitat() {
        return nacionalitat;
    }

    public void setNacionalitat(String nacionalitat) {
        this.nacionalitat = nacionalitat;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public String getNom1() {
        return nom1;
    }

    public void setNom1(String nom1) {
        this.nom1 = nom1;
    }

    public String getLlinatge2() {
        return llinatge2;
    }

    public void setLlinatge2(String llinatge2) {
        this.llinatge2 = llinatge2;
    }

    public String getLlinatge1() {
        return llinatge1;
    }

    public void setLlinatge1(String llinatge1) {
        this.llinatge1 = llinatge1;
    }

    /**
     * @return the listMedicaments
     */
    public ArrayList<BeanMedicament> getListMedicaments() {
        return listMedicaments;
    }

    /**
     * @param listMedicaments the listMedicaments to set
     */
    public void setListMedicaments(ArrayList<BeanMedicament> listMedicaments) {
        this.listMedicaments = listMedicaments;
    }

    public void setAnee(boolean nese) {
        this.anee = nese;
    }

    public boolean isAnee() {
        return this.anee;
    }

    /**
     * @return the repetidor
     */
    public boolean isRepetidor() {
        return repetidor;
    }

    /**
     * @param repetidor the repetidor to set
     */
    public void setRepetidor(boolean repetidor) {
        this.repetidor = repetidor;
    }

    /**
     * @return the repetidorEditable
     */
    public boolean isRepetidorEditable() {
        return repetidorEditable;
    }

    /**
     * @param repetidorEditable the repetidorEditable to set
     */
    public void setRepetidorEditable(boolean repetidorEditable) {
        this.repetidorEditable = repetidorEditable;
    }

    public String getNese() {
        return nese;
    }

    public void setNese(String nese) {
        this.nese = nese;
    }

    /**
     * @return the anysDecimal
     */
    public float getAnysDecimal() {
        return anysDecimal;
    }

    /**
     * @param anysDecimal the anysDecimal to set
     */
    public void setAnysDecimal(float anysDecimal) {
        this.anysDecimal = anysDecimal;
    }
}
