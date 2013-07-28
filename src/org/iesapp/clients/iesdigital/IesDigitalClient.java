/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.clients.UClient;
import org.iesapp.clients.iesdigital.anuncis.AnuncisClient;
import org.iesapp.clients.iesdigital.dates.BeanFestiu;
import org.iesapp.clients.iesdigital.dates.DatesCollection;
import org.iesapp.clients.iesdigital.dates.Festiu;
import org.iesapp.clients.iesdigital.fitxes.FitxesClient;
import org.iesapp.clients.iesdigital.guardies.GuardiesClient;
import org.iesapp.clients.iesdigital.missatgeria.MissatgeriaCollection;
import org.iesapp.clients.iesdigital.professorat.IUser;
import org.iesapp.clients.iesdigital.professorat.ProfessoratData;
import org.iesapp.clients.iesdigital.reserves.ReservesClient;
import org.iesapp.clients.iesdigital.spaces.BeanEspai;
import org.iesapp.clients.iesdigital.spaces.Espai;
import org.iesapp.clients.iesdigital.spaces.SpacesCollection;
import org.iesapp.database.Compare;
import org.iesapp.database.CompareIncidence;
import org.iesapp.database.MyDatabase;
import org.iesapp.database.ScriptRunner;
import org.iesapp.util.Version;
 



/**
 *
 * @author Josep
 */
@Version(version="4.5.1")
public class IesDigitalClient implements IClient, UClient {
    private final MyDatabase mysql;
    private final MyDatabase sgd;
    private SpacesCollection spacesCollection;
    private DatesCollection datesCollection;
    protected IUser iuser;
    private final ICoreData coredata;
    private ProfessoratData professoratData;
     
    //Sub-clients
    protected FitxesClient fitxesClient;
    protected GuardiesClient guardiesClient;
    protected ReservesClient reservesClient;
    protected AnuncisClient anuncisClient;
    private MissatgeriaCollection missatgeriaCollection;
    private ArrayList<CompareIncidence> compareStructure2;
    private ArrayList<CompareIncidence> compareStructure1;
    private int checkedYear = 0;
            
     
    public IesDigitalClient(MyDatabase mysql, MyDatabase sgd, ICoreData coredata)
    {
        //Generates a new client facade instance
        this.mysql = mysql;
        this.sgd = sgd;
        this.coredata = coredata;
    }
    
    //SUB-CLIENTS--------------------------------------(Lazy initialization)
    @Override
    public FitxesClient getFitxesClient() {
        if(fitxesClient==null)
        {
            fitxesClient = new FitxesClient(this);
        }
        return fitxesClient;
    }

    @Override
    public GuardiesClient getGuardiesClient() {
        if(guardiesClient==null)
        {
            guardiesClient = new GuardiesClient(this);
        }
        return guardiesClient;
    }

    @Override
    public ReservesClient getReservesClient() {
        if(reservesClient==null)
        {
            reservesClient = new ReservesClient(this);
        }
        return reservesClient;
    }

    @Override
    public AnuncisClient getAnuncisClient() {
        if(anuncisClient==null)
        {
            anuncisClient = new AnuncisClient(this);
        }
        return anuncisClient;
    }
    
    //INTERFACE METHODS------
    
    @Override
    public MyDatabase getMysql() {
        return mysql;
    }

    @Override
    public MyDatabase getSgd() {
        return sgd;
    }
 
    
     public void close()
     {
         if(mysql!=null){
             mysql.close();
         }
         if(sgd!=null){
             sgd.close();     
         }
    }
    
    //FACADE METHODS -----------
     
     public Espai getEspai()
     {
         return new Espai((IClient) this);
     }
     public Espai getEspai(final int id)
     {
         return new Espai(id, (IClient) this);
     }
     public Espai getEspai(final BeanEspai bean)
     {
         return new Espai(bean, (IClient) this);
     }
      
     public Festiu getFestiu()
     {
         return new Festiu((IClient) this);
     }
     public Festiu getFestiu(final int id)
     {
         return new Festiu(id, (IClient) this);
     }
     public Festiu getFestiu(final BeanFestiu bean)
     {
         return new Festiu(bean, (IClient) this);
     }
     
    //SINGLE-INSTANCE UTILITY CLASSES
    public SpacesCollection getSpacesCollection()
    {
        if(spacesCollection==null)
        {
            spacesCollection = new SpacesCollection((IClient) this);
        }
        return spacesCollection;
    }
    
    @Override
    public DatesCollection getDatesCollection()
    {
        if(datesCollection==null)
        {
            datesCollection = new DatesCollection((IClient) this);
        }
        return datesCollection;
    }
  
    @Override
    public ProfessoratData getProfessoratData()
    {
        if(professoratData==null)
        {
            professoratData = new ProfessoratData((IClient) this);
        }
        return professoratData;
    }
    
    @Override
    public MissatgeriaCollection getMissatgeriaCollection()
    {
        if(missatgeriaCollection==null)
        {
            missatgeriaCollection = new MissatgeriaCollection((IClient) this);
        }
        return missatgeriaCollection;
    }
    
   
    @Override
    public IUser getUserInfo() {
        return iuser;
    }
  
    public void setIuser(IUser iuser) {
        this.iuser = iuser;
    }

    @Override
    public ICoreData getICoreData() {
        return coredata;
    }

    public void dispose()
    {
        //dispose sub-clients
        if(guardiesClient!=null)
        {
            guardiesClient.dispose();
        }
        if(fitxesClient!=null)
        {
            fitxesClient.dispose();
        }
        if(reservesClient!=null)
        {
            reservesClient.dispose();
        }
        if(anuncisClient!=null)
        {
            anuncisClient.dispose();
        }
        
        guardiesClient = null;
        fitxesClient = null;
        reservesClient = null;
        anuncisClient = null;
        
        spacesCollection = null;
        datesCollection = null;
        professoratData = null;
        missatgeriaCollection = null;
    }
    
    
    @Override
    public String getClientVersion()
    {
        Version annotation = this.getClass().getAnnotation(Version.class);
        if(annotation!=null)
        {
            return annotation.version();
        }
        
        return "4.5.1";
    }
    
     /**
     * Returns the number of iesdigitalxxxx cursos existing in host
     * iesdigital is the mysqldbprefix specified in the system
     * @return 
     */
    public ArrayList<Integer> getAllYears()
    {
        ArrayList<Integer> list = new  ArrayList<Integer>();
        
         String SQL1  = "SELECT RIGHT(SCHEMA_NAME,4)+0 FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME LIKE '"
                +ICoreData.core_mysqlDBPrefix+"____'";
         try {
            Statement st = getMysql().createStatement();
            ResultSet rs = getMysql().getResultSet(SQL1,st);
            while(rs!=null && rs.next())
            {
               list.add(rs.getInt(1));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MyDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         return list;
    }
    
    
    @Override
    public String checkDatabases(int year) {
        
        //Create a clone of database connection
        MyDatabase mysqlClone = new MyDatabase(mysql.getConBean());
        boolean connect = mysqlClone.connect();
        if(!connect)
        {
            return "ERROR: no database connection";
        }
        
        String result="";
        this.checkedYear = year;
        compareStructure1 = null;
        compareStructure2 = null;
        
        if(ICoreData.core_mysqlDBPrefix!=null && ICoreData.core_mysqlDBPrefix.isEmpty())
        {
        boolean doesSchemaExists = mysqlClone.doesSchemaExists(ICoreData.core_mysqlDBPrefix);
        if(!doesSchemaExists)
        {
            System.out.println("Creating "+ICoreData.core_mysqlDBPrefix);
            mysqlClone.executeUpdate("CREATE DATABASE "+ICoreData.core_mysqlDBPrefix);
            mysqlClone.setCatalog(ICoreData.core_mysqlDBPrefix);
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/iesdigital/sql/iesdigital.sql");
            ScriptRunner srun = new ScriptRunner(mysqlClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
        else
        {
            //Check from temporal script
            String tmpDB = "tmp"+Math.round(Math.random()*1e8);
            mysqlClone.executeUpdate("CREATE DATABASE "+tmpDB);
            System.out.println("Creating "+ tmpDB);
            mysqlClone.setCatalog(tmpDB);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/iesdigital/sql/iesdigital.sql");
            ScriptRunner srun = new ScriptRunner(mysqlClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Compare compare = new Compare(mysqlClone, tmpDB, mysql, ICoreData.core_mysqlDBPrefix);
            compareStructure1 = compare.compareStructure();
            for (CompareIncidence ci: compareStructure1)
            {
                result += ci.toString();
            }
            mysqlClone.executeUpdate("DROP DATABASE "+tmpDB);
        }
       
        
        boolean doesSchemaExists = mysqlClone.doesSchemaExists(ICoreData.core_mysqlDBPrefix+year);
        if(!doesSchemaExists)
        {
            System.out.println("Creating "+ ICoreData.core_mysqlDBPrefix+year);
            mysqlClone.executeUpdate("CREATE DATABASE "+ICoreData.core_mysqlDBPrefix+year);
            mysqlClone.setCatalog(ICoreData.core_mysqlDBPrefix+year);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/iesdigital/sql/iesdigitalxxxx.sql");
            ScriptRunner srun = new ScriptRunner(mysqlClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            //Check it with script tmp backup
            String tmpDB = "tmp"+Math.round(Math.random()*1e8);
            mysqlClone.executeUpdate("CREATE DATABASE "+tmpDB);
            System.out.println("Creating "+ tmpDB);
            mysqlClone.setCatalog(tmpDB);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/iesdigital/sql/iesdigitalxxxx.sql");
            ScriptRunner srun = new ScriptRunner(mysqlClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Compare compare = new Compare(mysqlClone, tmpDB, mysql, ICoreData.core_mysqlDBPrefix+year);
            compareStructure2 = compare.compareStructure();
            for (CompareIncidence ci: compareStructure2)
            {
                result += ci.toString();
            }
            mysqlClone.executeUpdate("DROP DATABASE "+tmpDB);
            
        }
        mysqlClone.close();
        return result;
    }

    @Override
    public String fixDatabases() {
        if (this.compareStructure1 == null && this.compareStructure2==null) {
            return "ERROR: CheckDatabases must be invoked before fixDatabases";
        }
        
        //Create a clone of database connection
        MyDatabase mysqlClone = new MyDatabase(mysql.getConBean());
        boolean connect = mysqlClone.connect();
        if (!connect) {
            return "ERROR: No database connection";
        }
        
        String result = "";

        //Set up temporal database
        //Check it with script tmp backup
        if (compareStructure1 != null && !compareStructure1.isEmpty()) {
            String tmpDB = "tmp" + Math.round(Math.random() * 1e8);
            mysqlClone.executeUpdate("CREATE DATABASE " + tmpDB);
            mysqlClone.setCatalog(tmpDB);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/iesdigital/sql/iesdigital.sql");
            ScriptRunner srun = new ScriptRunner(mysqlClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            Compare compare = new Compare(mysqlClone, tmpDB, mysql, ICoreData.core_mysqlDBPrefix);
            ArrayList<CompareIncidence> notSolved = compare.fixStructure(compareStructure1);

            for (CompareIncidence ci : notSolved) {
                result += ci.toString();
            }
            mysqlClone.executeUpdate("DROP DATABASE " + tmpDB);
        }     
        
        
        //Check it with script tmp backup
        if (compareStructure2 != null && !compareStructure2.isEmpty()) {
            String tmpDB = "tmp" + Math.round(Math.random() * 1e8);
            mysqlClone.executeUpdate("CREATE DATABASE " + tmpDB);
            mysqlClone.setCatalog(tmpDB);
            //Create from script
            InputStream istream = getClass().getResourceAsStream("/org/iesapp/clients/iesdigital/sql/iesdigitalxxxx.sql");
            ScriptRunner srun = new ScriptRunner(mysqlClone.getConnection(), false, false);
            try {
                srun.runScript(new InputStreamReader(istream));
            } catch (IOException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(IesDigitalClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            Compare compare = new Compare(mysqlClone, tmpDB, mysql, ICoreData.core_mysqlDBPrefix+this.checkedYear);
            ArrayList<CompareIncidence> notSolved = compare.fixStructure(compareStructure2);

            for (CompareIncidence ci : notSolved) {
                result += ci.toString();
            }
            mysqlClone.executeUpdate("DROP DATABASE " + tmpDB);
        }
        
        mysqlClone.close();
        this.compareStructure1 = null;
        this.compareStructure2 = null;

        return result;
    }

}
