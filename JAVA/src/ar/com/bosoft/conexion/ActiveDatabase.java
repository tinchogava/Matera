/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.conexion;


import ar.com.bosoft.clases.Utiles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public final class ActiveDatabase {
    
    private static ComboPooledDataSource cpds;

    //private static final ConcurrentHashMap<String, BasicDataSource> dataSources = new ConcurrentHashMap();
    private static DSLContext context;

    private ActiveDatabase() {
        //
    }
    public static Properties getConfigProperties(){
        try {
                InputStream input = new FileInputStream("config/configuration.properties");
                Properties properties = new Properties();
        	properties.load(input);
                properties.setProperty("url", "jdbc:mysql://" +
                        properties.getProperty("host") +
                        ":" +
                        properties.getProperty("port") +
                        "/" +
                        properties.getProperty("schema") +
                        "?rewriteBatchedStatements=true&noAccessToProcedureBodies=true");
                return properties;
        } catch (IOException ex) {
            Utiles.enviaError("MyATQ",ex);
        }
        return null;
    }
   
    
private static void setDataSource() throws IOException, SQLException, PropertyVetoException {
        Properties prop = getConfigProperties();
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl(prop.getProperty("url"));
        cpds.setUser(prop.getProperty("user"));
        cpds.setPassword(prop.getProperty("pass"));

        // the settings below are optional -- c3p0 can work with defaults
        //cpds.setMinPoolSize(1);
        //cpds.setAcquireIncrement(1);
        //cpds.setMaxPoolSize(20);
        //cpds.setMaxStatements(180);

    }

    public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
        if (cpds == null) {
            setDataSource();
            return cpds;
        } else {
            return cpds;
        }
    }

    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }    

    public static DataSource getDataSource(){
        try{
            return getInstance();
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }
        return null;
    }
    
    public static String getConnectionString(){
        return Conexion.connectionString;
    }
    
    public static DSLContext getDSLContext(){
        return getDSLContext(true);
    }
    
    public static DSLContext getDSLContext(Boolean autocommit){
        try {
            if (context != null)
                return context;
            context = DSL.using(getDataSource(), SQLDialect.MYSQL);
            return context;
        } catch (Exception ex) {
            Utiles.showErrorMessage(ex);
            Logger.getLogger(ActiveDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
