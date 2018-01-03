package ar.com.bosoft.conexion;

import ar.com.bosoft.clases.Utiles;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class Conexion {
    public static String user, pass, schema, host, port, prefijo, url, connectionString;
	InputStream input = null;
    Properties properties = new Properties();
    Connection conexion;
    
    public Conexion(){
        try {
        	input = new FileInputStream("config/configuration.properties");
        	properties.load(input);
        } catch (IOException ex) {
            Utiles.enviaError("MyATQ",ex);
        }

        user = properties.getProperty("user") == null ? "" : properties.getProperty("user");
        pass = properties.getProperty("pass") == null ? "" : properties.getProperty("pass");
        schema = properties.getProperty("schema") == null ? "" : properties.getProperty("schema");
        host = properties.getProperty("host") == null ? "" : properties.getProperty("host");
        port = properties.getProperty("port") == null ? "" : properties.getProperty("port");
        prefijo = properties.getProperty("prefijo") == null ? "" : properties.getProperty("prefijo");
        Utiles.emailUsername = properties.getProperty("emailUsername") == null ? "" : properties.getProperty("emailUsername");
        Utiles.emailPassword = properties.getProperty("emailPassword") == null ? "" : properties.getProperty("emailPassword");
        Utiles.emailHost = properties.getProperty("emailHost") == null ? "" : properties.getProperty("emailHost");
        
        url = "jdbc:mysql://" + host + ":" + port + "/" + schema + "?noAccessToProcedureBodies=true";
        try {
            //conexion = DriverManager.getConnection(url, user, pass);
            conexion = ActiveDatabase.getDataSource().getConnection();
            connectionString = "jdbc:mysql://"+host+":"+port+"/"+schema;
            Base.open(ActiveDatabase.getDataSource());
            //Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://"+host+":"+port+"/"+schema, user, pass);
            System.out.println("Conectado a " + host);
        } catch (SQLException ex) {
            Utiles.enviaError("MyATQ",ex);
        }
    }
    
    public Connection getConnection(){
        try {
            if (conexion.isClosed()){
                this.conexion = ActiveDatabase.getDataSource().getConnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return conexion;
    }
    
    public void desconectar(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            Utiles.enviaError("MyATQ",ex);
        }
    }
    
    public ResultSet procAlmacenado(String nombreProc, ArrayList parametros, String empresa, String clase, String metodo){
        ResultSet rs = null;
        try {
            String arma = "{call " + nombreProc + " (" + (parametros.isEmpty() ? ")}" : "");
            Iterator i = parametros.iterator();
            while (i.hasNext()){
                i.next();
                arma = arma + "?" + (i.hasNext() ? "," : ")}");
            }
            /*
            if (conexion.isClosed() || !conexion.isValid(5)) {
//            if (conexion.isClosed()) {
                JOptionPane.showMessageDialog(null, "El servidor a cerrado la conexion por estar inactiva", "Atención", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            */
            CallableStatement cst = getConnection().prepareCall(arma);
            
            i = parametros.iterator();
            int parameterIndex = 1;
            while (i.hasNext()){
                Object obj = i.next();
                if (obj.getClass() == Integer.class){  //Entero
                    cst.setInt(parameterIndex, Integer.parseInt(obj.toString()));
                }else if (obj.getClass() == Double.class){    //Doble o decimal
                    cst.setDouble(parameterIndex, Double.parseDouble(obj.toString()));
                }else if (obj.getClass() == String.class){    //String
                    cst.setString(parameterIndex, obj.toString());
                }else if (obj.getClass() == Character.class){    //Caracter
                    cst.setString(parameterIndex, obj.toString());
                }else if (obj.getClass() == Long.class){      //Fecha (pasar como Long usando Date.getTime()
                    if (Long.parseLong(obj.toString()) > 0){
                        cst.setDate(parameterIndex, new java.sql.Date(Long.parseLong(obj.toString())));
                    }else{
                        cst.setDate(parameterIndex, null);
                    }
                }else if (obj.getClass() == Boolean.class){
                    cst.setBoolean(parameterIndex, Boolean.parseBoolean(obj.toString()));
                }
                parameterIndex ++;
            }
            // Ejecuta el procedimiento almacenado
            cst.execute();
            rs = cst.getResultSet();
        } catch (CommunicationsException ex){
            JOptionPane.showMessageDialog(null, "El servidor no responde", "Atención", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (MySQLNonTransientConnectionException ex){
            JOptionPane.showMessageDialog(null, "El servidor a cerrado la conexion por estar inactiva", "Atención", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (SQLException ex) {
            Utiles.showErrorMessage(ex);
        }
        return rs;
    }
    
    public ResultSet funcAlmacenada(String nombreFunc, ArrayList parametros, String empresa, String clase, String metodo){
        ResultSet rs = null;
        try {
            String arma = "select " + nombreFunc + " (" + (parametros.isEmpty() ? ")" : "");
            Iterator i = parametros.iterator();
            while (i.hasNext()){
                i.next();
                arma = arma + "?" + (i.hasNext() ? "," : ")");
            }
            
            CallableStatement cst = getConnection().prepareCall(arma);
            
            i = parametros.iterator();
            int parameterIndex = 1;
            while (i.hasNext()){
                Object obj = i.next();
                if (obj.getClass() == Integer.class){  //Entero
                    cst.setInt(parameterIndex, Integer.parseInt(obj.toString()));
                }else if (obj.getClass() == Double.class){    //Doble o decimal
                    cst.setDouble(parameterIndex, Double.parseDouble(obj.toString()));
                }else if (obj.getClass() == String.class){    //String
                    cst.setString(parameterIndex, obj.toString());
                }else if (obj.getClass() == Long.class){      //Fecha (pasar como Long usando Date.getTime()
                    if (Long.parseLong(obj.toString()) > 0){
                        cst.setDate(parameterIndex, new java.sql.Date(Long.parseLong(obj.toString())));
                    }else{
                        cst.setDate(parameterIndex, null);
                    }
                }else if (obj.getClass() == Boolean.class){
                    cst.setBoolean(parameterIndex, Boolean.parseBoolean(obj.toString()));
                }
                parameterIndex ++;
            }
            // Ejecuta el procedimiento almacenado
            cst.execute();
            rs = cst.getResultSet();
        } catch (CommunicationsException ex){
            JOptionPane.showMessageDialog(null, "El servidor no responde", "Atención", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (MySQLNonTransientConnectionException ex){
            JOptionPane.showMessageDialog(null, "El servidor a cerrado la conexion por estar inactiva", "Atención", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (SQLException ex) {
            Utiles.enviaError(empresa, ex);
        }
        return rs;
    }
}
