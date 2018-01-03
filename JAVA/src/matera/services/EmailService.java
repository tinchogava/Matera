/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import ar.com.bosoft.clases.Utiles;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import matera.db.managers.EmailreciptersMgr;
/**
 *
 * @author tinchogava
 */
public class EmailService {
    
    private static Session session;
    
    private static final Properties properties = new Properties();
    /*
    private static final String urlWebServer = "";
    private static String emailUsername = "";
    private static String emailPassword = "";
    private static String emailHost = "";
    
    public static void sendEmailViaHTTP(Integer id_presupuesto, String paciente, String obraSocial, String contenido){
        ArrayList<BasicNameValuePair> parameters = new ArrayList<>();

        parameters.add(new BasicNameValuePair("idPresupuesto", id_presupuesto.toString()));
        parameters.add(new BasicNameValuePair("paciente", paciente));
        parameters.add(new BasicNameValuePair("obraSocial", obraSocial));
        parameters.add(new BasicNameValuePair("contenido", contenido));
        HttpPostAux httpPostAux = new HttpPostAux();
        httpPostAux.httpPostConnect(parameters,urlWebServer);
    }
*/
         
    public static void sendEmail(Integer id_presupuesto, Integer id_zona, String paciente, String obraSocial, 
            String profesional, String contenido) throws UnsupportedEncodingException{

        properties.setProperty("mail.smtp.host", Utiles.emailHost);
	properties.setProperty("mail.smtp.starttls.enable", "true");
	properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.from", "aprobacion.presupuestos@myasa.com.ar");
	properties.setProperty("mail.from.alias", "aprobacion.presupuestos@myasa.com.ar");
	properties.setProperty("mail.smtp.user", Utiles.emailUsername);
	properties.setProperty("mail.smtp.auth", "true");
        
        session = Session.getDefaultInstance(properties);
        
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aprobacion@presupuestos.myasa.com.ar"));
            EmailreciptersMgr.getEmailRecipters(id_zona).forEach(eR->{
                
           try {
               message.addRecipient(Message.RecipientType.TO, new InternetAddress(eR.getEmailaddress()));
            } catch (MessagingException ex) {
                }
            });
            message.setSubject("Aprobación Presupuesto Nº" + id_presupuesto);
            message.setText("Turno: " + id_presupuesto + "\r\n\r\n Paciente: " + paciente + "\r\n\r\n Obra Social: "
                    + obraSocial + "\r\n\r\n Profesional: "
                    + profesional + "\r\n\r\n Productos: \r\n\r\n" + contenido);
            Transport transport = session.getTransport("smtp");
            transport.connect((String)properties.getProperty("mail.smtp.user"), Utiles.emailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch (MessagingException me){
            System.out.println("Error al enviar");
        }
    }
    
}
