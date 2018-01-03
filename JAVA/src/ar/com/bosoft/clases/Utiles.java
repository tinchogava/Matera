/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.clases;

import ar.com.bosoft.formularios.Principal;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import matera.cache.CacheVendedor;
import matera.db.LogError;
import org.apache.commons.lang3.math.NumberUtils;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

/**
 *@author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class Utiles{
    
    public static final Integer TODOS = 0;
    public static final String TODOS_STRING = "-- TODOS --";
    
    public static final String SI = "S";
    public static final String NO = "N";
    public static final String DEBITO = "D";
    public static final String CREDITO = "C";
    
    public static final String PRESUPUESTO_FINALIZADO = "Z";
    
    public static final String PENDIENTE = "P";
    
    public static final String RECLAMOS_DIR = "img/reclamos";
    
    public static String emailUsername = "";  
    public static String emailPassword = "";
    public static String emailHost = "";
    
    public static enum PRESUPUESTO_ESTADO {
        PENDIENTE("P"), APROBADO("A"), CONFIRMADO("C"), SUSPENDIDO("S"), ANULADO("N"), RECHAZADO("R"), FINALIZADO("Z");

        private final String estado;

        PRESUPUESTO_ESTADO(String estado) {
            this.estado = estado;
        }

        public String getEstado() {
            return estado;
        }
    }
    
    public static class TIPO_COMP {
       
        public static final int FACTURA_A = 1;
        public static final int FACTURA_B = 5;
        public static final int FACTURA_C = 8;
        public static final int FACTURA_EXP = 11;
        public static final int FACTURA_X = 23; 
        public static final int RECIBO_A = 4;
        public static final int RECIBO_B = 16;
        public static final int RECIBO_C = 17;  
        public static final int NOTA_CREDITO_A = 3;
        public static final int NOTA_CREDITO_B = 7;
        public static final int NOTA_CREDITO_C = 10;
        public static final int NOTA_CREDITO_EXP = 13;
        public static final int NOTA_CREDITO_X = 25;
        
    }
    
    public static class INSERT {
        public static final int ERROR = -1;
        public static final int SUCCESS = 1;
    }    
    
    public static class FACTURADO {
        public static final int TODOS = 0;
        public static final int SIN_FACTURAR = 1;
        public static final int FACTURADO = 2;
    }
    
    public static class TRAZABILIDAD {
        public static final int SIN_TRAZABILIDAD = 0;
        public static final int LOTE = 1;
        public static final int LOTE_SERIE = 2;
    }    
    
    public static class LOG_EVENTO {
        public static final int CREA_PRESUPUESTO = 100;
        public static final int ACTUALIZA_PRESUPUESTO = 101; 
        public static final int APRUEBA_PRESUPUESTO = 102;
        public static final int SUSPENDE_PRESUPUESTO = 103;
        public static final int ANULA_PRESUPUESTO = 104;
        public static final int CONFIRMA_PRESUPUESTO = 105;
        public static final int FINALIZA_PRESUPUESTO = 106;
        public static final int RECHAZA_PRESUPUESTO = 107;
        public static final int CREA_REMITO = 200;
        public static final int RECIBE_REMITO = 201;
        public static final int MODIFICA_REMITO = 202;
    } 
    
    public static class ZONA {
        public static final int MENDOZA = 1;
        public static final int ALTO_VALLE = 2;
        public static final int SAN_JUAN = 4;
    }  
    
    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }    
    
    public static void saveError(Exception e){
        try{
            LogError error = new LogError();

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String eString = Utiles.v(sw.toString()); // stack trace as a string        
            
            Integer id_user = 0;
            if (Principal.usuarioData != null) {
                id_user = Principal.usuarioData.getId_usuario();
            }        

            error.set("id_user", id_user);
            error.set("error", eString);
            error.set("status", 1);
            error.set("date", Base.firstCell("Select CURRENT_TIMESTAMP()"));
            error.saveIt();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void showErrorMessage(Exception e){
        JOptionPane.showMessageDialog(null, "Existe un posible error: "+e.getMessage()+" \n\n" +e.toString(), "Información", JOptionPane.ERROR_MESSAGE);
        saveError(e);
    }
    
    public static void showMessage(String msg){
        JOptionPane.showMessageDialog(null, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }    
    
    public static void enviaError(String empresa, Exception ex){
        if (ex.getClass().equals(CommunicationsException.class)) {
            JOptionPane.showMessageDialog(null,"No se ha recibido respuesta desde el servidor."
                    + "\nIntente nuevamente en unos instantes. De persistir el inconveniente, consulte con su asesor informático.", "El servidor no responde",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        saveError(ex);
        
        String usuario = "SIN USUARIO";
        if (Principal.usuarioData != null) {
            usuario = Principal.usuarioData.getNombre();
        }
        String fechaHora = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
        String error = ex.toString();
        String cuerpoMensaje = "Versión del programa: <b>" + Principal.revision + "</b>";
        cuerpoMensaje += "\nError encontrado por <b>" + usuario + "</b> el " + fechaHora + "<br>";
        cuerpoMensaje += "Descripción del error: <b>" + error + "</b><br>";
        cuerpoMensaje += "Traza del error:";
        
        StackTraceElement[] traza = ex.getStackTrace();
        int cant = traza.length;
        String destaca = "ar.com.bosoft";
        boolean negrita;
        for (int i = (cant - 1); i >= 0; i--){
            String linea = traza[i].toString();
            negrita = linea.substring(0, destaca.length()).equals(destaca);
            cuerpoMensaje += "<br>" + (negrita ? "<b>" : "") + linea + (negrita ? "</b>" : "");
            error += "\n" + linea;
        }
        
        JOptionPane.showMessageDialog(null,"Se ha encontrado un error."
                + "\nSe enviará un correo electrónico con el detalle del error al Soporte Técnico de BOSOFT para que se ponga en contacto.\nDisculpe las molestias ocasionadas", "Error interno - El Sistema debe cerrarse",JOptionPane.ERROR_MESSAGE);
        
        String rutaEjecucion = System.getProperty("user.dir");
        String rutaProduccion = "C:" + File.separator + "Bosoft";
        
        if (!rutaEjecucion.equals(rutaProduccion)) {    //Si no esta ejecutando desde el directorio de produccion
            System.out.println(error);
            System.exit(0);
        }
        
        try{
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "mail.bosoft.com.ar");
            props.setProperty("mail.smtp.starttls.enable", "false");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.user", "myatq@bosoft.com.ar");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("MyATQ - Error <myatq@bosoft.com.ar>"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress("pablo.dtorres@gmail.com"));
            message.setSubject(empresa + " - Error de Sistema");
            message.setText(cuerpoMensaje, "ISO-8859-1", "html");

    		Properties properties = new Properties();
    		FileInputStream input;
			try {
				input = new FileInputStream("config/configuration.properties");
	        	properties.load(input);
	            // Lo enviamos.
	        	if(properties.getProperty("sendMail") == "true")
	        	{
	                Transport t = session.getTransport("smtp");
	                t.connect("myatq@bosoft.com.ar", "MaTeRa2684");
	                t.sendMessage(message, message.getAllRecipients());
	                // Cierre.
	                t.close();
	        	}
			} catch (Exception e) {
				System.out.println(e);
			}
            System.exit(0);
        }catch (MessagingException e){
            System.out.println(e);
        }
    }
    
    public static String encode (String texto){
        return Base64.encodeBase64String(texto.getBytes()); 
    }
    
    public static String decode (String texto){
        return new String(Base64.decodeBase64(texto.getBytes())); 
    }
    
    public static String[][] ResultSetToArray(ResultSet rs){
        String obj[][]=null; 
        try{ 
            rs.last(); 
            ResultSetMetaData rsmd = rs.getMetaData(); 
            int numCols = rsmd.getColumnCount(); 
            int numFils =rs.getRow(); 
            obj=new String[numFils][numCols];
            int j = 0; 
            rs.beforeFirst(); 
            while (rs.next()){
                for (int i=0;i<numCols;i++){ 
                    try {
                        obj[j][i]=rs.getObject(i+1).toString().trim();
                    } catch (Exception e) {
                    }
                    //obj[j][i]=rs.getObject(i+1).toString().trim();
                }
                j++; 
            } 
        }catch(SQLException e){
            enviaError("MyATQ", e);
        } 
        return obj;
    }
    
    public static boolean SoloAlfanumerico(String palabra){
        for(int i = 0; i < palabra.length(); i++){
            /*del 48 al 57: Numeros
             * del 65 al 90: Letras Mayusculas
             * del 97 al 122: Letras minusculas
             * 164 y 165: ñ y Ñ
             */
            if(!((palabra.codePointAt(i) > 47 && palabra.codePointAt(i) < 58) || (palabra.codePointAt(i) > 64 && palabra.codePointAt(i) < 91) 
                    || (palabra.codePointAt(i) > 96 && palabra.codePointAt(i) < 123) || (palabra.codePointAt(i) > 163 && palabra.codePointAt(i) < 166))){
                return false;   //Caracteres incorrectos
            }            
        }
        return true;    //Caracteres correctos
    }
    
    public static boolean SoloNumeros(String palabra){
        for(int i = 0; i < palabra.length(); i++){
            /*del 48 al 57: Numeros
             */
            if(!((palabra.codePointAt(i) > 47 && palabra.codePointAt(i) < 58))){
                return false;   //Caracteres incorrectos
            }            
        }
        return true;    //Caracteres correctos
    }
    
    public static boolean esEmail(String correo) {
        Pattern pat;
        Matcher mat;        
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        return mat.find();        
    }
    
    public static void enviaMailBosoft (String nombre, String direccionMail, String passSist, String passAccesos){
        try{
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "mail.bosoft.com.ar");
            props.setProperty("mail.smtp.starttls.enable", "false");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.user", "info@bosoft.com.ar");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("BOSOFT - Sistemas Informáticos Integrales <info@bosoft.com.ar>"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(direccionMail));
            message.setSubject("Bienvenido a BOSOFT - Software de Gestión");
            message.setText(
                    "Éste es un mensaje generado automáticamente, no lo responda. "+                    
                    "\n\nFELICITACIONES " + nombre + ", TU REGISTRO FUE CONFIRMADO EN NUESTRO SITIO!\n" +                    
                    "BIENVENIDO AL MUNDO BOSOFT!\n\n" +
                    "Le recordamos sus claves (Le recomendamos no divulgar sus claves a nadie):\n" +
                    "\t\t Clave de acceso al Sistema: " + passSist +
                    "\n\t\t Clave de Organizador de Accesos: " + passAccesos +
                    "\n\nBOSOFT le agradece su confianza. Ante la menor duda o consulta, le recordamos nuestras formas de contacto:\n" +
                    "- Por mail:\n" +
                    "\t\tsoporte@bosoft.com.ar\n" +
                    "\t\tinfo@bosoft.com.ar\n" +
                    "- Por teléfono:\n" +
                    "\t\t+54 0261 4576277\n" +
                    "- Web: \n" + 
                    "\t\t www.bosoft.com.ar"
                    );

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("info@bosoft.com.ar", "Bosoft010212adm");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }
        catch (MessagingException e)
        {
            enviaError("MyATQ", e);
        }
    }
      
    public static void enviaMailOlvido (String nombre, String direccionMail, String password){
        try{
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "mail.bosoft.com.ar");
            props.setProperty("mail.smtp.starttls.enable", "false");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.user", "claves@bosoft.com.ar");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Servicio de claves - BOSOFT <claves@bosoft.com.ar>"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(direccionMail));
            message.setSubject("Recuperación de clave");
            message.setText(
                    "Éste es un mensaje generado automáticamente, no lo responda. "+                    
                    "\n\nEstimad@ " + nombre + ", por pedido suyo le recordamos su clave (Le recomendamos no divulgar su clave a nadie):\n" +
                    "\t\t Clave de acceso al Sistema: " + password +
                    "\n\nBOSOFT le agradece su confianza. Ante la menor duda o consulta, le recordamos nuestras formas de contacto:\n" +
                    "- Por mail:\n" +
                    "\t\tsoporte@bosoft.com.ar\n" +
                    "\t\tinfo@bosoft.com.ar\n" +
                    "- Por teléfono:\n" +
                    "\t\t+54 0261 4576277\n" +
                    "- Web: \n" + 
                    "\t\t www.bosoft.com.ar"
                    );

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("claves@bosoft.com.ar", "Bosoft010212adm");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }catch (MessagingException ex){
            enviaError("MyATQ", ex);
        }
    }
      
    public static int diaDeLaSemana(String d){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	GregorianCalendar cal = new GregorianCalendar();
        try {
            Date date = formatter.parse(d);
            cal.setTime(date);                
	} catch (ParseException e) {
            enviaError("MyATQ", e);
	}
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    public static String proximoLunes(String fechaVieja){
        int sumaDias = 0;
        int diaViejo = diaDeLaSemana(fechaVieja);
        switch (diaViejo){
            case 7: //Sábado
                sumaDias = 2;
                break;
            case 1: //Domingo
                sumaDias = 1;
                break;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	GregorianCalendar cal = new GregorianCalendar();
        try {
            Date date = formatter.parse(fechaVieja);
            cal.setTime(date);
            cal.add(Calendar.DATE, sumaDias);
	} catch (ParseException e) {
            enviaError("MyATQ", e);
	}
        return formatter.format(cal.getTime());
    }
    
    public static boolean validaCuit(String sCUIT){
        sCUIT = sCUIT.replace("-","");
        String aMult = "5432765432";
//        String [] verificador = aMult.split(""); 

        if (sCUIT.length() == 11){ 
//            String [] aCUIT = sCUIT.split(""); 
            int iResult = 0; 
            for(int i = 0; i <= 9; i++){ 
                iResult += Integer.parseInt(sCUIT.charAt(i)+"") * Integer.parseInt(aMult.charAt(i)+""); 
            } 
            iResult = (iResult % 11); 
            iResult = 11 - iResult; 

            if (iResult == 11){
                iResult = 0;
            } 
            if (iResult == 10){
                iResult = 9;
            }
            if (iResult == Integer.parseInt(sCUIT.charAt(10)+"")){ 
                return true; 
            } 
        }     
        return false; 
    } 
    
    public static boolean validaSistema(){
    	Properties prop = new Properties();
    	InputStream input = null;
        try{
        	input = new FileInputStream("config/configuration.properties");
        	prop.load(input);
        	return true;
        }
        catch(Exception ex)
        {
        	Logger.getLogger(Utiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void imprimeArray (String [][] array, String nombre){
        int col = array[0].length;
        int filas = array.length;
        System.out.println(nombre);
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < col; j++){
                System.out.print(array[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }
    
    public static boolean existeFichero (String path){
        String sFichero = path;
        File fichero = new File(sFichero);
//        System.out.println(path + " " + fichero.exists());
        return fichero.exists();
    }
    
    public static Date FechaModificacionFichero (String path){
                File fichero = new File(path);
                long ms = fichero.lastModified();
                
                Date d = new Date(ms);
                Calendar c = new GregorianCalendar(); 
                c.setTime(d);
                
                String dia, mes, annio, hora, minuto, segundo;
                   
                dia = Integer.toString(c.get(Calendar.DATE));
                mes = Integer.toString(c.get(Calendar.MONTH));
                annio = Integer.toString(c.get(Calendar.YEAR));
                hora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
                minuto = Integer.toString(c.get(Calendar.MINUTE));
                segundo = Integer.toString(c.get(Calendar.SECOND));
                  
                System.out.println (hora + ":" + minuto + ":" + segundo + " " + dia + "/" + mes +"/" + annio);
                
                return d;
    }

    public static String RutaArchivo(){
        String decodedPath = "";
        try {
            String path = Principal.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            decodedPath = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return decodedPath.substring(1); //Para quitar la primer barra.
    }
    
    public static Date primerDiaDelMes(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMinimum(Calendar.DAY_OF_MONTH),
                cal.getMinimum(Calendar.HOUR_OF_DAY),
                cal.getMinimum(Calendar.MINUTE),
                cal.getMinimum(Calendar.SECOND));

        return cal.getTime();
    }
    
    public static Date ultimoDiaDelMes(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMaximum(Calendar.DAY_OF_MONTH),
                cal.getMinimum(Calendar.HOUR_OF_DAY),
                cal.getMinimum(Calendar.MINUTE),
                cal.getMinimum(Calendar.SECOND));

        return cal.getTime();
    }
    
    public static String nombreMes(int mes, boolean mayusculas){
        //Recordar que en Java los meses empiezan por 0, pero en MySQL empiezan por 1
        switch (mes){
            case 0:
                return mayusculas ? "ENERO" : "Enero";
                
            case 1:
                return mayusculas ? "FEBRERO" : "Febrero";
                
            case 2:
                return mayusculas ? "MARZO" : "Marzo";
                
            case 3:
                return mayusculas ? "ABRIL" : "Abril";
                
            case 4:
                return mayusculas ? "MAYO" : "Mayo";
                
            case 5:
                return mayusculas ? "JUNIO" : "Junio";
                
            case 6:
                return mayusculas ? "JULIO" : "Julio";
                
            case 7:
                return mayusculas ? "AGOSTO" : "Agosto";
                
            case 8:
                return mayusculas ? "SEPTIEMBRE" : "Septiembre";
                
            case 9:
                return mayusculas ? "OCTUBRE" : "Octubre";
                
            case 10:
                return mayusculas ? "NOVIEMBRE" : "Noviembre";
                
            case 11:
                return mayusculas ? "DICIEMBRE" : "Diciembre";
        }
        return "";
    }
    
//    public static ResultSet procAlmacenado(String nombreProc, ArrayList parametros, String empresa, String clase, String metodo){
//        Conexion cn = new Conexion();
//        ResultSet rs = null;
//        try {
//            String arma = "{call " + nombreProc + " (";
//            Iterator i = parametros.iterator();
//            while (i.hasNext()){
//                i.next();
//                arma = arma + "?" + (i.hasNext() ? "," : ")}");
//            }
//            
//            CallableStatement cst = cn.getConnection().prepareCall(arma);
//            
//            i = parametros.iterator();
//            int parameterIndex = 1;
//            while (i.hasNext()){
//                Object obj = i.next();
//                if (obj.getClass() == Integer.class){  //Entero
//                    cst.setInt(parameterIndex, Integer.parseInt(obj.toString()));
//                }else if (obj.getClass() == Double.class){    //Doble o decimal
//                    cst.setDouble(parameterIndex, Double.parseDouble(obj.toString()));
//                }else if (obj.getClass() == String.class){    //String
//                    cst.setString(parameterIndex, obj.toString());
//                }else if (obj.getClass() == Long.class){      //Fecha (pasar como Long usando Date.getTime()
//                    if (Long.parseLong(obj.toString()) > 0){
//                        cst.setDate(parameterIndex, new java.sql.Date(Long.parseLong(obj.toString())));
//                    }else{
//                        cst.setDate(parameterIndex, null);
//                    }
//                    
//                }
//                parameterIndex ++;
//            }
//            // Ejecuta el procedimiento almacenado
//            cst.execute();
//            rs = cst.getResultSet();
//        } catch (SQLException ex) {
//            enviaErrorPrograma(empresa,"Utiles", "ResultSetToArray",ex.toString());
//        }finally{
//            cn.desconectar();
//        }
//        return rs;
//    }
    
    public static boolean existeEnModelo(DefaultTableModel modelo, int columnaBusca, Object valorBusca){
        int reg = modelo.getRowCount();
        for (int i = 0; i < reg; i++){
            Object compara = modelo.getValueAt(i, columnaBusca);
            if (compara.equals(valorBusca)){
                return true;
            }
        }
        return false;
    }
    
public static int getRowByValue(DefaultTableModel modelo, int columnaBusca, Object valorBusca){
        int reg = modelo.getRowCount();
        for (int i = 0; i < reg; i++){
            Object compara = modelo.getValueAt(i, columnaBusca);
            if (compara.equals(valorBusca)){
                return i;
            }
        }
        return -1;
    }

public static int getRowByValue(DefaultTableModel modelo, String columnName, Object valorBusca){
    return getRowByValue(modelo, Utiles.getColumnByName(modelo, columnName), valorBusca);
}
    
    public static void borrarDirectorio (File directorio){
        File[] ficheros = directorio.listFiles();
 
        for (File fichero : ficheros) {
            if (fichero.isDirectory()) {
                borrarDirectorio(fichero);
            }
            fichero.delete();
        }
    }
    
    public static String pegarPortapapeles(){
        Clipboard portapapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable datos = portapapeles.getContents(null);
        String textoEnPortapapeles = "";
        if(datos.isDataFlavorSupported(DataFlavor.stringFlavor)){
            try {
                textoEnPortapapeles = datos.getTransferData(DataFlavor.stringFlavor).toString();
            } catch (UnsupportedFlavorException | IOException e) {
            } finally{
            }
        }
        return textoEnPortapapeles;
    }
    
    public static void copiarAlPortapapeles(String textoCopiado){
        Clipboard portapapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection texto=new StringSelection(textoCopiado);
        portapapeles.setContents(texto, texto);
    }
    
    public DefaultTableModel ordenaPorFecha(DefaultTableModel modelo, int columnaFecha, SimpleDateFormat formatter) throws ParseException{
        DefaultTableModel modeloOrdenado = new DefaultTableModel();
        if (modelo.getRowCount() > 0) {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                Object[] fila = (Object[]) modelo.getDataVector().get(i);
                if (modeloOrdenado.getRowCount() > 0) {
                    for (int j = 0; j < modeloOrdenado.getRowCount(); j++) {
                        Object[] filaCompara = (Object[]) modeloOrdenado.getDataVector().get(j);
                        Date fechaCompara = formatter.parse(filaCompara[columnaFecha].toString());
                        Date fechaOrdenada = formatter.parse(fila[columnaFecha].toString());
                        if (fechaCompara.after(fechaOrdenada)) {
                            modeloOrdenado.addRow(fila);
                        }
                    }
                }else{
                    modeloOrdenado.addRow(fila);
                }
            }
        }else{
            return modelo;
        }        
        return modeloOrdenado;   
    }
    
    public static void setEnableContainer(Container c, boolean band) {
        Component [] components = c.getComponents();
        c.setEnabled(band);
        for (Component component : components) {
            component.setEnabled(band);
            if (component instanceof Container) {
                setEnableContainer((Container) component, band);
            }
        }
    }
    
    public static void exportaTablaExcel(JTable table, File file) {
        try {
            WritableWorkbook workbook1 = Workbook.createWorkbook(file);
            WritableSheet sheet1 = workbook1.createSheet("Hoja", 0); 
            TableModel model = table.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                Label column = new Label(i, 0, model.getColumnName(i));
                sheet1.addCell(column);
            }
            
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    String val = (model.getValueAt(i, j) == null ? "" : model.getValueAt(i, j).toString());
                    if (NumberUtils.isNumber(val)){
                        sheet1.addCell(new jxl.write.Number(j, i + 1, Float.parseFloat(val)));
                    }
                    else {
                        sheet1.addCell(new Label(j, i + 1, val));                            
                    }
                }
            }
            workbook1.write();
            workbook1.close();
            JOptionPane.showMessageDialog(null,"Exportación exitosa!", "Información",JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | WriteException ex) {
            JOptionPane.showMessageDialog(null,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
        }            
    }
    
    public static void modeloEnExcel(DefaultTableModel modelo){
        if (modelo.getRowCount() > 0) {
            //Crear un objeto FileChooser
            JFileChooser fc = new JFileChooser();
            //Mostrar la ventana para abrir archivo y recoger la respuesta
            int respuesta = fc.showSaveDialog(null);
            //Comprobar si se ha pulsado Aceptar
            if (respuesta == JFileChooser.APPROVE_OPTION){
                try {
                    //Crear un objeto File con el archivo elegido
                    File file = new File(fc.getSelectedFile().getCanonicalPath() + ".xls");
                    WritableWorkbook workbook1 = Workbook.createWorkbook(file);
                    WritableSheet sheet1 = workbook1.createSheet("Hoja", 0);
                    for (int i = 0; i < modelo.getColumnCount(); i++) {
                        if (i > 13) {
                            break;
                        }
                        Label column = new Label(i, 0, modelo.getColumnName(i));
                        sheet1.addCell(column);
                    }

                    for (int i = 0; i < modelo.getRowCount(); i++) {
                        for (int j = 0; j < modelo.getColumnCount(); j++) {
                            if (j > 13) {
                                break;
                            }

                            String val = (modelo.getValueAt(i, j) == null ? "" : modelo.getValueAt(i, j).toString());
                            Label row = new Label(j, i + 1, val);
                            sheet1.addCell(row);
                        }
                    }
                    workbook1.write();
                    workbook1.close();
                    JOptionPane.showMessageDialog(null,"Exportación exitosa!", "Información",JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | WriteException ex) {
                    JOptionPane.showMessageDialog(null,"No se ha podido crear el archivo", "Información",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay resultados para exportar", "Atención", JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    public static int getColumnByName(JTable table, String name) {
        for (int i = 0; i < table.getColumnCount(); ++i)
            if (StringUtils.stripAccents(table.getColumnName(i).toLowerCase()).equals(StringUtils.stripAccents(name.toLowerCase())))
                return i;
        throw new IllegalArgumentException("INVALID");
        //return -1;
    }
    
    public static int getColumnByName(DefaultTableModel model, String name) {
        for (int i = 0; i < model.getColumnCount(); ++i)
            if (StringUtils.stripAccents(model.getColumnName(i).toLowerCase()).equals(StringUtils.stripAccents(name.toLowerCase())))
                return i;
        
        throw new IllegalArgumentException("INVALID COLUMN NAME: " + name);
        //return -1;
    }    
    
    public static Object getAttr(Object object, String fieldName){
        Field field = null;
        try {
            field = object.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(Utiles.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
          field.setAccessible(true);
          Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Utiles.class.getName()).log(Level.SEVERE, null, ex);
        }
          return value;
    }
    
    public static Vector fillTableObject(int index, DefaultTableModel sourceModel, DefaultTableModel targetModel, HashMap defaults){
        Vector<Object> vec = new Vector<>();
        for (int i = 0; i < targetModel.getColumnCount(); ++i){
            String targetColName = StringUtils.stripAccents(targetModel.getColumnName(i).toLowerCase());
            try{
                int col = Utiles.getColumnByName(sourceModel, targetColName);
                Object o = sourceModel.getValueAt(index, col);
                if (o == null)
                    o = "";
                vec.add(o);
            }
            catch(IllegalArgumentException ex){
                if (defaults.get(targetColName) != null)
                    vec.add(defaults.get(targetColName));
                else{
                    vec.add("");
                    JOptionPane.showMessageDialog(null, "El campo "+targetColName+" no esta disponible en esta tabla y es requerido.", "Atención", JOptionPane.INFORMATION_MESSAGE);
                }
                    
            }
        }
        return vec;
    }
    
    public static Vector fillTableObject(DefaultTableModel sourceModel, ResultSet rs) throws SQLException{
        Vector<Object> vec = new Vector<>();
        for (int i =0; i < sourceModel.getColumnCount();++i){
            String s = rs.getString(StringUtils.stripAccents(sourceModel.getColumnName(i).toLowerCase()));
            vec.add((s == null ? "": s));
        }
        return vec;
    }
    
    public static Vector fillTableObject(DefaultTableModel sourceModel, Map<Object,Object> map){
        Vector<Object> vec = new Vector<>();
        for (int i=0; i < sourceModel.getColumnCount(); ++i){
            vec.add(map.get(StringUtils.stripAccents(sourceModel.getColumnName(i).toLowerCase())));    
        }        
        return vec;
    }
    
    public static Vector fillTableObject(DefaultTableModel sourceModel, Model model){
        Vector<Object> vec = new Vector<>();
        for (int i=0; i < sourceModel.getColumnCount(); ++i){
            //Object o = StringUtils.stripAccents(sourceModel.getColumnName(i).toLowerCase());
            vec.add(model.get(StringUtils.stripAccents(sourceModel.getColumnName(i).toLowerCase())));    
        }        
        return vec;
    }
    
    public static String formattedColumn(String column){
        return StringUtils.stripAccents(column.toLowerCase());
    }
    
    public static ArrayList<Map> resultSetToArrayList(ResultSet rs) throws SQLException{
      ResultSetMetaData md = rs.getMetaData();
      int columns = md.getColumnCount();
      ArrayList list = new ArrayList(50);
      while (rs.next()){
         HashMap row = new HashMap(columns);
         for(int i=1; i<=columns; ++i){           
          row.put(Utiles.formattedColumn(md.getColumnLabel(i)),rs.getObject(i));
         }
          list.add(row);
      }

     return list;
    }
    
    public static HashMap modelRowToMap(Integer row, DefaultTableModel model){
        HashMap map = new HashMap();
        for(int i = 0 ; i < model.getColumnCount(); ++i){
            map.put(Utiles.formattedColumn(model.getColumnName(i)),model.getValueAt(row, i));
        }
        return map;
    }
    
    public static Object valueAt(DefaultTableModel model, Integer row, String column){
        Object val = model.getValueAt(row, Utiles.getColumnByName(model, column));
        if (val == null)
            val = Utiles.v(val);
        return val;
    }
    
    public static Boolean isInModel(DefaultTableModel model, String column, Object value){
        for(int i = 0; i < model.getRowCount(); i++){
            if (Utiles.valueAt(model, i, column).equals(value))
                return true;
        }
        return false;
    }
    
    public static String buildANDQuery(String query, String str){
        if (query == ""){
            query = str;
        }
        else{
            query += " AND " + str; 
        }
            
        return query;
    }
    
    public static HashMap find(ArrayList<HashMap> l, String key, Object value){
        for (HashMap map : l)
        {
            if (map.get(key).equals(value))
                return map;
        }
        return new HashMap();
    }
    
    public static String v(String o){
        return o == null ? "" : o;
    }
    
    public static String v(Object o){
        return o == null ? "" : o.toString();
    }
    
    public static Integer getCount(ArrayList list, HashMap map){
        Integer count = 0;
        while (list.contains(map)){
            count++;
            list.remove(map);
        }
        return count;
    }
    
    public static ArrayList removeDuplicates(ArrayList<Map> list, String filter){

        ArrayList newList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()){
            Map map = (Map) it.next();
            
            Iterator newListIterator = newList.iterator();
            Boolean isUnique = true;
            while (newListIterator.hasNext()){
                HashMap m = (HashMap) newListIterator.next();
                if (map.get(filter).equals(m.get(filter))){
                    isUnique = false;
                    break;
                }
            }
            if (isUnique)
                newList.add(map);
            
        }
        return newList;  
    }
    
    public static String formatDate(Date date){
        if (date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }
    
    public static List<Map> modelToMapList(DefaultTableModel model){
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++){
            list.add(Utiles.modelRowToMap(i, model));
        }
        return list;
    }
    
    public static String formatDateTime(Date date){
        if (date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return format.format(date);
    }
}