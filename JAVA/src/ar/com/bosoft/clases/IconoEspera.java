/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * BOSOFT - Servicios Informáticos Integrales
 * Todos los derechos reservados -2014-
 * www.bosoft.com.ar
 */
package ar.com.bosoft.clases;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import javax.swing.JFrame;

public class IconoEspera extends Thread {
    ArrayList<String> iconos; 
    JFrame ventana;
    int indice = 0;
    boolean sigue = true;
    Image iconoOriginal;
    String tituloOriginal;

    Runnable miRunnable = new Runnable(){
        @Override
        public void run() {
            while(sigue){
                String pathIcono = iconos.get(indice);
                Image icon = new ImageIcon(this.getClass().getResource(pathIcono)).getImage();
                ventana.setIconImage(icon);
                ventana.setTitle(tituloOriginal + "     --ESPERANDO--");
                if(indice > 0){
                    indice--;
                }else{
                    indice = 11;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }
        }
      };
    
    public IconoEspera(JFrame ventana){
        this.ventana = ventana;
        this.iconoOriginal = ventana.getIconImage();
        this.tituloOriginal = ventana.getTitle();
        
        iconos = new ArrayList();
        for(int i = 1; i <= 12; i++){
            String pathIcono = "/recursos/animacion/cargando" + i + ".png";
            iconos.add(pathIcono);
        }
    }

    public Runnable getRunnable(){
        return miRunnable;
    }
    
    public void devuelveIconoOriginal(){
        ventana.setIconImage(iconoOriginal);
        ventana.setTitle(tituloOriginal);
        sigue = !sigue;        
    }
}
