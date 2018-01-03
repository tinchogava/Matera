package ar.com.bosoft.Utilidades;

import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class Banco {
    public static void main(String[] args) throws IOException, InterruptedException, Exception{
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostName());
        System.out.println(localHost.getHostAddress());
    }
}

