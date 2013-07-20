/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.alumnat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import org.iesapp.clients.iesdigital.IClient;
import org.iesapp.clients.iesdigital.ICoreData;
import org.iesapp.util.JSEngine;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 * 
 * Compatibilitat entre els grups de SGD i els del XESTIB
 * Converteix els noms dels grups SGD al sistema XESTIB
 * 
 */
public final class Grupo {
        public String estudis="";
        public String ensenyament="";
        public String grup="";
//  
//        public static void main(String[] args)
//        {
//            CoreCfg.debug=true;
//            CoreCfg.preInitialize();
//            
//            Grupo grupo = new Grupo("1r Batx. A");
//            //System.out.println("Grupo--->"+grupo.toString());
//        }
    private final IClient client;
        
        public Grupo(IClient client)
        {
            //default constructor
            this.client = client;
        }
        
      //Construeix el grup atraves d'una cadena de text del sgd       
      public Grupo(String sgdInputText, IClient client) {
        this.client = client;
        JSEngine jsEngine = JSEngine.getJSEngine(getClass(), ICoreData.contextRoot);
        Bindings bindings = jsEngine.getBindings();
        bindings.put("grupo", this);
        boolean success = false;
        try {
            jsEngine.evalBindings(bindings);
            success =(Boolean) jsEngine.invokeFunction("parseSgdGroup2Xestib",sgdInputText);
       } catch (Exception ex) {
            Logger.getLogger(Grupo.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        if(!success)    //using build-in, script failed
        {
        String gruporaw = sgdInputText.toUpperCase();
        gruporaw = gruporaw.replace("BATXILLERAT", "BAT");
        gruporaw = gruporaw.replace("BATX.", "BAT");
        gruporaw = gruporaw.replace("BATX", "BAT");

        gruporaw = gruporaw.replace("*", "");
        gruporaw = gruporaw.replace("-1", "");
        gruporaw = gruporaw.replace("-2", "");


        if (gruporaw.contains("ESO") || gruporaw.contains("HOT") || gruporaw.contains("PQPI")) {
            ensenyament = "ESO";
        } else if (gruporaw.contains("BAT")) {
            ensenyament = "BAT";
        }

        if (gruporaw.contains("ESO")) {
            gruporaw = gruporaw.replaceAll("D'", "");

            estudis = StringUtils.BeforeLast(gruporaw, "ESO").trim().toUpperCase() + " ESO";
            String tmp = StringUtils.AfterLast(gruporaw, "O").trim();
            grup = tmp.substring(0, 1);

        } else if (gruporaw.contains("BAT")) {
            estudis = StringUtils.BeforeLast(gruporaw, "BAT").trim().toUpperCase() + " BATX";
            String tmp = StringUtils.AfterLast(gruporaw, "T").trim();
            grup = tmp.substring(0, 1);
        } else if (gruporaw.contains("HOT")) {

            if (gruporaw.contains("HOT31")) {
                ensenyament = "GS";
                gruporaw = gruporaw.replaceAll(" ", "");
                estudis = gruporaw.substring(0, gruporaw.length() - 1);
                grup = gruporaw.substring(gruporaw.length() - 1, gruporaw.length());
            } else if (gruporaw.contains("HOT21")) {
                ensenyament = "GM";
                gruporaw = gruporaw.replaceAll(" ", "");
                estudis = gruporaw.substring(0, gruporaw.length() - 1);
                grup = gruporaw.substring(gruporaw.length() - 1, gruporaw.length());
            } else if (gruporaw.contains("HOT22")) {
                ensenyament = "GM";
                gruporaw = gruporaw.replaceAll(" ", "");
                estudis = gruporaw.substring(0, gruporaw.length() - 1);
                grup = gruporaw.substring(gruporaw.length() - 1, gruporaw.length());
            } else {
                String kk = gruporaw.replace("-LOGSE", " ");
                kk = kk.replace("*", "");
                kk = kk.replace("-1", " ");
                kk = kk.replace("- LOGSE", " ").trim();
                estudis = StringUtils.BeforeLast(kk, " ").trim();
                grup = StringUtils.AfterLast(kk, " ").trim();
            }
        } else if (gruporaw.contains("PQPI")) {
            estudis = "2- PQPI";
            String kk = gruporaw.replaceAll("\\*", "");
            grup = StringUtils.AfterLast(kk, " ").trim();
        } else if (gruporaw.contains("AUX.CUINA")) {
            estudis = "HOT11";
            String kk = gruporaw.replaceAll("AUX.CUINA", "");
            grup = kk.trim();
        } else if (gruporaw.contains("AUX.SERV.RESTAURACIÓ")) {
            estudis = "HOT12";
            String kk = gruporaw.replaceAll("AUX.SERV.RESTAURACIÓ", "");
            grup = kk.trim();
        } else if (gruporaw.contains("VOLUNTARIS")) {
            ensenyament = "ESO";
            estudis = "MÒD. VOLUNTARIS";
            grup = StringUtils.AfterLast(gruporaw, "VOLUNTARIS").trim();
        } else if (gruporaw.contains("ADM31")) {
            ensenyament = "GS";
            estudis = "ADM31";
            grup = StringUtils.AfterLast(gruporaw, "ADM31").trim();
        } else if (gruporaw.contains("ADM21")) {

            ensenyament = "GM";
            gruporaw = gruporaw.replaceAll(" ", "");
            estudis = gruporaw.substring(0, gruporaw.length() - 1);
            grup = gruporaw.substring(gruporaw.length() - 1, gruporaw.length());
        } else if (gruporaw.contains("ADG21")) {
            ensenyament = "GM";
            estudis = "ADG21";
            grup = StringUtils.AfterLast(gruporaw, "ADG21").trim();
        } else if (gruporaw.contains("ADG32")) {
            ensenyament = "GS";
            estudis = "ADG32";
            grup = StringUtils.AfterLast(gruporaw, "ADG32").trim();
        } else {
            throw (new java.lang.UnsupportedOperationException("ATENCIO: impossible fer un parsing del grup raw " + gruporaw));
        }
 
        ensenyament = ensenyament.trim();
        estudis = estudis.trim();
        grup = grup.trim();
        }


    }

    //Constructor a partir del xestib (no fa res mes que guardar la informacio)
    public Grupo(String ensenyament, String estudis, String grup, IClient client) {
        this.client = client;
        this.ensenyament = ensenyament;
        this.estudis = estudis;
        this.grup = grup;
    }

    public boolean equals(Grupo g1) {
        //Refina la condicio d'ésser igual a
        String thisestudis = this.getEstudis().replaceAll("1R", "1").replaceAll("2N", "2")
                .replaceAll("3R", "3").replaceAll("4T", "4").replaceAll(" ", "");

        String g1estudis = g1.getEstudis().replaceAll("1R", "1").replaceAll("2N", "2")
                .replaceAll("3R", "3").replaceAll("4T", "4").replaceAll(" ", "");

        String thisensenyament = this.getEnsenyament().replaceAll("PQPI", "ESO");
        String g1ensenyament = g1.getEnsenyament().replaceAll("PQPI", "ESO");

        boolean cond = true;
        cond &= (thisensenyament.replaceAll(" ", "")).equals(g1ensenyament.replaceAll(" ", ""));
        //if(!cond) //System.out.println("1. "+cond+" "+this.ensenyament+" ; "+g1.ensenyament);
        cond &= (thisestudis.replaceAll(" ", "")).equals(g1estudis.replaceAll(" ", ""));
        //if(!cond)  //System.out.println("2. "+cond+" "+this.estudis+" ; "+g1.estudis);
        cond &= (this.getGrup().replaceAll(" ", "")).equals(g1.getGrup().replaceAll(" ", ""));
        //if(!cond) //System.out.println("3. "+cond+" "+this.grup+" ; "+g1.grup);
        return cond;
    }

    public String print() {
        return this.getEnsenyament() + " " + this.getEstudis() + " " + this.getGrup();
    }
    
    @Override
    public String toString()
    {
        return print();
    }

    public String getEstudis() {
        return estudis;
    }

    public void setEstudis(String estudis) {
        this.estudis = estudis;
    }

    public String getEnsenyament() {
        return ensenyament;
    }

    public void setEnsenyament(String ensenyament) {
        this.ensenyament = ensenyament;
    }

    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }
}
