/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.Utilidades;

import ar.com.bosoft.conexion.Conexion;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 *@author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class Probar {
    
    public Probar(){
        Conexion cn = new Conexion();
        String empresa = "Prueba";
        int id_empresa = 1;
        String usuario = "Usuario XYZ";
        int id_usuario = 1;
        String sucursal = "0001";
        String claveAccesos = "123456";
        JFrame v = new JFrame("Prueba JInternalFrame");
        JDesktopPane dp = new JDesktopPane();
        v.getContentPane().add(dp);
        
        ar.com.bosoft.formularios.CargaRecibo internal = new ar.com.bosoft.formularios.CargaRecibo(cn, id_empresa, empresa, usuario);
        dp.add(internal);
        
        // Se visualiza todo.
        v.setSize(1100,800);
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);

        // Se visualiza el JInternalFrame 
        internal.setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(Probar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        
        Probar prueba = new Probar();
    }
}
