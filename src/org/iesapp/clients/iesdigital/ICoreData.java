/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital;

import java.util.HashMap;

/**
 *
 * @author Josep
 */
public class ICoreData {
    public static String contextRoot = "";
    public static String core_mysqlDBPrefix = "";
    public static String ip="";
    public static String netbios="";
    public static String core_PRODUCTID="DEFAULT";
    public int anyAcademic = 0;
    public final static HashMap<String, Object> configTableMap = new HashMap<String, Object>();
    public final static HashMap<String, Object> defaultConfigTableMap = new HashMap<String, Object>();
   
    //defaultConfigTableMap is defined and populated in ICoreData
    static
    {
        defaultConfigTableMap.put("anyIniciCurs", new DefaultConfigTableEntry("iesd.core", 2012, "int", "Estableix el curs academic a l'entorn, p.e. 2011 significa el curs academic 2011-2012"));
        defaultConfigTableMap.put("sgdDBPrefix", new DefaultConfigTableEntry("iesd.core", "curso", "String", "Estableix el PREFIX de la base de dades de l'SGD"));
        defaultConfigTableMap.put("sgdDBParams", new DefaultConfigTableEntry("iesd.core", "zeroDateTimeBehavior=convertToNull", "String", "Parametres del connector mysql a la base de dades de l'SGD"));
        defaultConfigTableMap.put("sgdUser", new DefaultConfigTableEntry("iesd.core", "root", "String", "Estableix l'usuari per a la base sgd"));
        defaultConfigTableMap.put("sgdPasswd", new DefaultConfigTableEntry("iesd.core", "", "String", "Estableix la contrasenya de la base sgd"));
        defaultConfigTableMap.put("sgdHost", new DefaultConfigTableEntry("iesd.core", "localhost", "String", "Host per a la base sgd"));
        defaultConfigTableMap.put("versionDB", new DefaultConfigTableEntry("iesd.core", "4.0", "String", "Versio de la base de dades"));
        defaultConfigTableMap.put("minVersion", new DefaultConfigTableEntry("iesd.core", "4.0", "String", "La minima versio del programa requerida per funcionar amb la base"));
        defaultConfigTableMap.put("maxDiesAntelacio", new DefaultConfigTableEntry("iesd.reserves", 15, "int", "Nombre maxim de dies d'antelacio per realitzar reserves"));
        defaultConfigTableMap.put("refreshTime", new DefaultConfigTableEntry("iesd.guardies", 360, "int", "Temps en SEGONS entre refrescs a l'aplicacio de guardies"));
        defaultConfigTableMap.put("adminPwd", new DefaultConfigTableEntry("iesd.core", "1234", "String", "Contrasenya d'administrador de l'entorn iesDigital"));
        defaultConfigTableMap.put("allowWebAdmin", new DefaultConfigTableEntry("iesd.pdaweb", false, "Boolean", "Permet que es puguin dur tasques d'administracio de forma remota des de la web"));
        defaultConfigTableMap.put("guardPwd", new DefaultConfigTableEntry("iesd.core", "1234", "String", "Contrasenya per a l'usuari INTERN de guardies"));
        defaultConfigTableMap.put("pdawebSU", new DefaultConfigTableEntry("iesd.pdaweb", "1234", "String", "Estableix contrasenya d'administrador per a la pdaweb"));
        defaultConfigTableMap.put("idObservAcumulAL", new DefaultConfigTableEntry("iesd.core", 1, "int", "id de la incidencia d'amonestacio greu per acumulacio de 5 lleus"));
        defaultConfigTableMap.put("simbolCastigDimecres", new DefaultConfigTableEntry("iesd.core", "CD", "String", "Simbol per a la incidencia de càstig de dimecres"));
        defaultConfigTableMap.put("sgdEnableLogger", new DefaultConfigTableEntry("iesd.core", true, "Boolean", "Activa el registre en la base sgd"));
        defaultConfigTableMap.put("simbolAmonLLeu", new DefaultConfigTableEntry("iesd.core", "AL", "String", "Simbol amonestacio lleu"));
        defaultConfigTableMap.put("simbolAmonLLeuHist", new DefaultConfigTableEntry("iesd.core", "ALH", "String", "Simbol amonestacio lleu historica"));
        defaultConfigTableMap.put("simbolAmonGreu", new DefaultConfigTableEntry("iesd.core", "AG", "String", "Simbol amonestacio lleu historica"));
        defaultConfigTableMap.put("simbolExpulsio", new DefaultConfigTableEntry("iesd.core", "EX", "String", "Simbol amonestacio lleu historica"));
        defaultConfigTableMap.put("hardJustificacio", new DefaultConfigTableEntry("iesd.fitxes", false, "Boolean", "Justifica faltes de forma drastica (Esborra i la crea de nou)"));
        defaultConfigTableMap.put("enableRegSystemInfo", new DefaultConfigTableEntry("iesd.core", false, "Boolean", "Activa la recopilacio d'informacio dels ordinadors dels usuaris"));
        defaultConfigTableMap.put("refreshKillsig", new DefaultConfigTableEntry("iesd.core", 0, "int", "Periode en minuts per comprovar si hi ha senyals de SIGKILL per l'aplicacio 0=disable"));
        defaultConfigTableMap.put("mysqlLoggerMaxLines", new DefaultConfigTableEntry("iesd.core", 10000, "int", "Nombre màxim de linies en el logger de mysql, 0=desactiva"));
        defaultConfigTableMap.put("cloudBaseURL", new DefaultConfigTableEntry("iesd.pdaweb", "http://localhost:8080/pdaweb/webresources/cloud/", "String", "Ruta url per al servidor de descarregues"));
        defaultConfigTableMap.put("sgdImporterSimbolLists", new DefaultConfigTableEntry("iesd.core", "FA={FA,F};FJ={FJ};RE={RE,R};RJ={RJ};AL={AL,AM};AG={AG}", "String", "Defines lists of simbols to be imported in fitxa alumne curs IDENTIFIER1={SIMBOL1,SIMBOL2,...};IDENTIFIER2={SIMBOL3,SIMBOL4,...}; ..."));
        defaultConfigTableMap.put("sgdImporterCRequired", new DefaultConfigTableEntry("iesd.core", "AL;AG;ALH", "String", "For which identifiers of the sgdImporterSimbolsLists is a comment required to take into account the incidence"));
    }
    
    
}
