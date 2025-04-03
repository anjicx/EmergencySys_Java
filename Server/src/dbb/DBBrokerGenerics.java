package dbb;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import domain.AbstractDO;
import exception.ServerException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBBrokerGenerics {
    //CONNECTION POOL .JAR
   
    private static HikariDataSource dataSource;
    private static DBBrokerGenerics instance;

    private DBBrokerGenerics() {
    }

    // GETINSTANCE ZA DBB
    public static DBBrokerGenerics getInstance() {
        if (instance == null) {
            instance = new DBBrokerGenerics();
            try {
                HikariConfig config = new HikariConfig();
                DBProperties dbp = new DBProperties();
                config.setJdbcUrl(dbp.getDBURL());
                config.setUsername(dbp.getDBUser());
                config.setPassword(dbp.getDBPassword());
                config.setMaximumPoolSize(2000);
                config.setAutoCommit(false);
                dataSource = new HikariDataSource(config);
            } catch (IOException ex) {
                Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, "Greška kod inicijalizacije baze.", ex);
            }
        }
        return instance;
    }

    //konekcija iz bazena
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // potvrdjuje konekciju izbacuje iz thread-a
public void commitTransaction() throws SQLException, ServerException {
    Connection connection = null;
    try {      
        connection = ConnectionManager.getConnection();
        if (connection != null && !connection.isClosed()) {
            connection.commit();
            System.out.println("TRANSAKCIJA POTVRĐENA");
        } else {
            System.out.println("KONEKCIJA ZATVORENA");
        }
    } catch (SQLException | ServerException e) {
        e.printStackTrace();
    } finally {
        ConnectionManager.removeConnection(); // Uklanja konekciju iz ThreadLocal nakon commit-a
    }
}

// Rollback transakcije i sklanja iz thread pool-a
public void rollbackTransaction() throws SQLException, ServerException {
    Connection connection = null;
    try {
        connection = ConnectionManager.getConnection();
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            System.out.println("TRANSAKCIJA PONIŠTENA");
        } else {
            System.out.println("KONEKCIJA ZATVORENA");
        }
    } catch (SQLException | ServerException e) {
        e.printStackTrace();
    } finally {
        ConnectionManager.removeConnection(); // Uklanja konekciju iz ThreadLocal nakon rollback-a
        
    }
}


    
  //POSTAVLJA STATUS LOGOVANJA
 public boolean izmeniZapis(AbstractDO odo) {
    String query = "UPDATE " + odo.getTableName()+ " SET " + odo.setAttrValueUpdate() + " WHERE " + odo.getWhereCondition();
     System.out.println(query);
    return izvrsiUpit(query);

}
    

//UPDATE---ovo moze da se izmeniZapis 
public boolean updateRecordById(AbstractDO odo)throws Exception{
String query="UPDATE "+odo.getTableName()+" SET "+odo.setAttrValue()+" WHERE id="+odo.getPrimaryKey();
System.out.println("Update query is "+query);
 return izvrsiUpit(query);
}

public boolean izvrsiUpit(String query) {
        boolean signal = false;
        try {Connection connection = ConnectionManager.getConnection();
             Statement st = connection.createStatement() ;
            int rowcount = st.executeUpdate(query);
            signal = rowcount > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServerException ex) {
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signal;
    }

    //prima Report
   
//UCITAVANJE FAJLOVA
    
    

public boolean insertRecords(List<AbstractDO> records) {
        if (records == null || records.isEmpty()) {
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.WARNING, "Lista zapisa je prazna ili null.");
            return false;
        }
        try {Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();//kreiranje 1 statement i onda doadvanja
            for (AbstractDO odo : records) {
                String query = "INSERT INTO " + odo.getTableName() +
                        odo.getColumnsInsert()+
                        " VALUES (" + odo.getValuesInsert()+ ")";
                System.out.println(query);
                statement.addBatch(query);
            }
            statement.executeBatch();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, "Greška prilikom izvršavanja batch operacije.", ex);
        } catch (ServerException ex) {
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
 
 //select 1 tbl
 //SELEKTOVANJE

/*SELECT * FROM TABELA1 JOIN TABELA2 WHERE USLOV ZA 2 TABELE-DOCTOR IMA USLOV PRETR KADA JE JOIN 1 I JOIN2 */
/*LOGOVANJE DEPARTMENT+DOCTOR CONDITION JOIN KAD JE->USERNAME,PASSWORD PROVERA*/
//VRATI CELE DVE POVEZANE TBL S USLOVOM


//bolje logovanje greski
public AbstractDO getByConditionWithJoin(AbstractDO odo){
        AbstractDO obj=null;
        String query="select * from "+ odo.getTwoJoinTbl()+" where " +odo.getWhereCondition();
        System.out.println(query);
      try {Connection connection = ConnectionManager.getConnection();
             Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(query); 

        obj=odo.rsInTblObj(rs);
        } 
     catch (SQLException ex) {
 // Logovanje greške sa detaljima
        Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, "SQL greška: " + ex.getMessage(), ex);
        throw new RuntimeException("Došlo je do greške u upitu. Pokušajte ponovo kasnije.");     
   
}       catch (ServerException ex) {
 Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, "Server greška: " + ex.getMessage(), ex);
        throw new RuntimeException("Server greška. Molimo pokušajte kasnije."); 
}
return obj;
}
public AbstractDO getByCondition(AbstractDO odo){//sve s uslovom
AbstractDO obj=null;
/*OVO CE SELECT 0 UVEK NPR SELECT + +odo.vratiPrimarniKljuc()*/
        String query="select * "+ " from "+ odo.getTableName()+" where " +odo.getWhereCondition();
        System.out.println(query);

         try {Connection connection = ConnectionManager.getConnection();
             Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(query); 
        obj=odo.rsInTblObj(rs);
        
        
    } catch (SQLException ex) {
        Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
}       catch (ServerException ex) {
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
        }
   
return obj;



}
//nema join-a samo 1 tabela
//public List<Procedure> getProceduresByDepartment(Department department) 
/*OVO*/
/*vraca procedure u zavisnosti od odelenja*/
public List<AbstractDO> getListByCondition(AbstractDO odo){

List <AbstractDO>listObjects=new ArrayList<>();
String query="select "+odo.getSelectValues()+ 
                " from "+ odo.getTableName()+" where " +odo.getWhereCondition();
 System.out.println(query);
        try {Connection connection = ConnectionManager.getConnection();
             Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(query);
        listObjects = odo.rsInTblList(rs); 
        } 
  catch (SQLException ex) {
        Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
  
}       catch (ServerException ex) {
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
        }
return listObjects;


}

//popunjavanje cmb(select*)
//kad vraca listu abstractdo moze null jer lista obj al samo abstrdo ne!
//nemoj try with jer on automatski zatvara konekciju!!
public List<AbstractDO> getListAll(AbstractDO odo) {//vrati celu tabelu
        List<AbstractDO> listObjects = new ArrayList<>();
        String query = "SELECT * FROM " + odo.getTableName();
        try {Connection connection = ConnectionManager.getConnection();
             Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(query); 
            listObjects = odo.rsInTblList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServerException ex) {
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listObjects;
    }

//povezane


/*getDiagnosis(String search)*/
/*reportitem*/
public List <AbstractDO> getListByConditionWithJoin(AbstractDO odo){
        List <AbstractDO>listObjects=new ArrayList<>();
        String query="select "+odo.getSelectValues()+ 
                " from "+ odo.getTwoJoinTbl()+" where " +odo.getWhereCondition();//TBLJOIN
        System.out.println(query);
   try {Connection connection;
            try {
                connection = ConnectionManager.getConnection();
                Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(query) ;

        listObjects = odo.rsInTblList(rs); 
            } catch (ServerException ex) {
                Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 


     catch (SQLException ex) {
        Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
  
}return listObjects;
}



//IZVESTAJ 4JOINA
public List<AbstractDO> getListByConditionMultiJoin(AbstractDO odo){

List <AbstractDO>listObjects=new ArrayList<>();
String query="select "+odo.getSelectValues()+ 
                " from "+ odo.getFourJoinTbl()+" where " +odo.getWhereCondition();
 System.out.println(query);
try {  Connection connection;
    try {
        connection = ConnectionManager.getConnection();
         Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(query) ;

        listObjects = odo.rsInTblList(rs); 
    } catch (ServerException ex) {
        Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
    }
        } 
catch (SQLException ex) {
        Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
  
}return listObjects;



}


//INSERTOVANJE



public boolean insertRecord(AbstractDO odo)throws Exception{
        String query = "INSERT INTO " + odo.getTableName() + odo.getColumnsInsert() + 
                       " VALUES (" + odo.getValuesInsert() + ")";
        System.out.println("Upit za insert je " + query);
        
        boolean success = izvrsiUpit(query);
        if (!success) {
            System.err.println("Insert nije uspeo za upit: " + query);
        }  
        return success;

}

 
 public AbstractDO insertRecordPrepared(AbstractDO odo) throws Exception{
        String query = "INSERT INTO " + odo.getTableName() + " " + odo.getColumnsInsert()
                + " VALUES (" + odo.getValuesInsert()+ ")";
        System.out.println("Upit za insert prepared je: " + query);
PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

       
        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Nijedan red nije ubačen. Greška!");
        }
//stavis int da vrati samo slobodan id
        // Dohvat generisanog ključa
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long generatedId = generatedKeys.getLong(1);
                System.out.println(generatedId);

                odo.setID(generatedId); // Postavi generisani ID u entitet
                System.out.println("Generisani ID je: " + generatedId);
            } else {
                throw new SQLException("Nije moguće dobiti generisani ključ. Greška!");
            }
        }

        return odo;
}
 


 
 
 
//KOD UCITAVANJA DIJAGNOZA I PROCEDURA
public int isDataLoaded(AbstractDO ado) throws SQLException {
    String query = "SELECT EXISTS (SELECT 1 FROM " + ado.getTableName() + ")";
    System.out.println(query);
    
    try (Connection connection = ConnectionManager.getConnection();
             Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(query)) {

        if (rs.next()) {
            int i = rs.getInt(1);  
            if (i == 1) {
                System.out.println("dbb.DBBrokerGenerics.isDataLoaded()");
                return 1; // Tabela nije prazna
            } else if(i == 0) {
                return 0; // Tabela je prazna
            }
        }
    } catch (SQLException ex) {
        // Pravilno bacanje greške ako nastane problem u bazi
        throw new SQLException("Greška u bazi podataka prilikom provere tabele: " + ex.getMessage(), ex);
    }   catch (ServerException ex) { 
            Logger.getLogger(DBBrokerGenerics.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    return -1;  // Ako nije pronađeno (nema podataka u RS-u ili došlo do greške)
}


 
 
 

}
     