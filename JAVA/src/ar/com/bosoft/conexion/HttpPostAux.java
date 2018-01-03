/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.conexion;

/**
 *
 * @author tinchogava
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/*CLASE AUXILIAR PARA EL ENVIO DE PETICIONES A NUESTRO SISTEMA
 * Y MANEJO DE RESPUESTA.*/
public class HttpPostAux{
    
    InputStream is = null;
    String result = "";
	  

public void httpPostConnect(ArrayList<BasicNameValuePair> parameters, String urlwebserver){
   	
  	//
  	try{
  	        HttpClient httpclient = HttpClients.createDefault();
  	        HttpPost httppost = new HttpPost(urlwebserver);
  	        httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
  	        //ejecuto peticion enviando datos por POST
  	        HttpResponse response = httpclient.execute(httppost); 
  	        HttpEntity entity = response.getEntity();
  	        is = entity.getContent();
  	        System.out.println(is);
  	}catch(Exception e){
  	       // Log.e("log_tag", "Error in http connection "+e.toString());
  	}
  }
  
  public void getPostResponse(){
  
  	try{
  	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
  	        StringBuilder sb = new StringBuilder();
  	        String line = null;
  	        while ((line = reader.readLine()) != null) {
  	                sb.append(line + "\n");
  	        }
  	        is.close();
  	        result=sb.toString();
  	        
  	}catch(Exception e){
  	       
  	}
    }
  	  	
}	
  


	  
	  
