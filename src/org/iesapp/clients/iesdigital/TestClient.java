/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital;

import java.util.ArrayList;
import org.iesapp.clients.iesdigital.dates.Avaluacions;
import org.iesapp.clients.iesdigital.dates.BeanFestiu;
import org.iesapp.clients.iesdigital.dates.BeanHoraClasse;
import org.iesapp.clients.iesdigital.fitxes.BeanMedicamentsAutoritzats;
import org.iesapp.clients.iesdigital.fitxes.Medicaments;
import org.iesapp.clients.iesdigital.guardies.BeanHorari;
import org.iesapp.clients.iesdigital.guardies.BeanHorariGuardia;
import org.iesapp.clients.iesdigital.guardies.HorariGuardia;
import org.iesapp.clients.iesdigital.professorat.BeanProfessor;
import org.iesapp.clients.iesdigital.spaces.BeanEspai;
import org.iesapp.clients.iesdigital.spaces.BeanGuardZone;
import org.iesapp.database.MyDatabase;
import org.iesapp.database.vscrud.Crud;

/**
 *
 * @author Josep
 */
public class TestClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyDatabase mysql = new MyDatabase("localhost","iesDigital2012","root","","zeroDateTimeBehavior=convertToNull");
        System.out.println(mysql.connect());
        MyDatabase sgd = new MyDatabase("localhost","curso2012","root","","zeroDateTimeBehavior=convertToNull");
        System.out.println(sgd.connect());
        
        //Inicia la instància de la base de dades sgd
     
        
       
        ICoreData coredata = new ICoreData();
        coredata.anyAcademic=2012;
        ICoreData.contextRoot="";
        ICoreData.core_mysqlDBPrefix="iesdigital";
        ICoreData.configTableMap.put("sgdDBPrefix", "curso");
        IesDigitalClient client = new IesDigitalClient(mysql,sgd,coredata);
        
        System.out.println("Check "+client.checkDatabases(2012));
        System.out.println("fix "+client.fixDatabases());
        System.exit(0);
        
        Medicaments med = new Medicaments(client);
        ArrayList<BeanMedicamentsAutoritzats> listInformeAutoritzats = med.listInformeAutoritzats();
        for(BeanMedicamentsAutoritzats b: listInformeAutoritzats)
        {
            System.out.println(b.getNomcomplet()+":"
                    
                    +b.getAutoritzats());
        }
        ArrayList<Avaluacions> allAvaluacions = Avaluacions.getAllAvaluacions(2007, client);
        for(Avaluacions aval: allAvaluacions)
        {
            System.out.println(aval.toString());
        }
        
        System.exit(0);
       
        System.out.println("===========================================");
        System.out.println("Exemple d'utilització del paquet iesClient");
        System.out.println("        --Esquema de client--       ");
        System.out.println("per Josep Mulet (c) 2011            ");
        System.out.println("===========================================");
//        ArrayList<BeanMaterial> listMaterials = client.getReservesClient().getReservesCollection().listMaterials();
//        for(BeanMaterial bm: listMaterials)
//        {
//            System.out.println(bm);
//        }
       
        ArrayList list = client.getGuardiesClient().getGuardiesCollection().listSignaturesTarda(" data='2012-09-13' ORDER BY nombre");
        for(Object o: list)
        {
            System.out.println(o.toString());
        }
        
        ArrayList<BeanProfessor> listProfessors = client.getProfessoratData().listProfessors("");
        for(Object bean: listProfessors)
        {
            System.out.println(bean);
        }
        
        System.exit(0);
        
        ArrayList<BeanGuardZone> allGuardZones = client.getSpacesCollection().listGuardZones("");
        for(BeanGuardZone bean: allGuardZones)
        {
            System.out.println(bean);
        }
      
//        guardZone.setDescripcio("TESTing ");
//        guardZone.setLloc("TEST");
//        guardZone.getCrud().save();
//        allGuardZones.get(0).setLloc("MODIFICAT");
//        client.getGuardZone(allGuardZones.get(0)).getCrud().save();
        allGuardZones = client.getSpacesCollection().listGuardZones("");
        client.getGuardiesClient().getGuardZone(5).getCrud().delete();
        for(BeanGuardZone bean: allGuardZones)
        {
            System.out.println(bean);
        }
        
        //Test espais
        for(BeanEspai espai : client.getSpacesCollection().listEspais(""))
        {
            System.out.println(espai);
        }
       
        for(BeanFestiu espai : client.getDatesCollection().listFestius("1=1 ORDER BY DESDE DESC"))
        {
            System.out.println(espai);
        }
        
        for(BeanHorari espai : client.getGuardiesClient().getGuardiesCollection().listHoraris("prof='MA5' and dia='1' order by hora"))
        {
            System.out.println(espai);
        }
        ArrayList<BeanHorariGuardia> allHorariGuardies = client.getGuardiesClient().getGuardiesCollection().listHorariGuardies("dia='2'");
        for(BeanHorariGuardia espai : allHorariGuardies)
        {
            System.out.println(espai);
        }
        BeanHorariGuardia get = allHorariGuardies.get(0);
        get.setDescripcio("NO CANVIARA");
        get.setAbrev("MA5");
        Crud crud = new HorariGuardia(get, client).getCrud();
        crud.save();
        
        
        allHorariGuardies = client.getGuardiesClient().getGuardiesCollection().listHorariGuardies("dia='2'");
        for(BeanHorariGuardia espai : allHorariGuardies)
        {
            System.out.println(espai);
        }
        
        for(Object espai : client.getDatesCollection().listHoraClasses(BeanHoraClasse.ALL))
        {
            System.out.println(espai);
        }
    }
}
