package ar.com.bosoft.formatosCampos;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

/**
 *@author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ConMascara{
    MaskFormatter mascara;
    JFormattedTextField jFmt;

    public JFormattedTextField getjFmt(String mascara, char charEspacio, boolean autoseleccion) {
        // # -> un número   
        // U -> letra mayúscula  
        // L -> letra minúscula
        // * -> cualquier caracter  
        // ' -> caracteres de escape
        // A -> cualquier letra o número   
        // ? -> cualquier caracter
        // H -> cualquier caracter hexagonal (0-9, a-f or A-F)
        try {
            this.mascara = new MaskFormatter(mascara);            
        } catch (ParseException ex) {
            System.out.println("Error al crear la mascara: " + mascara);
        }
        this.mascara.setPlaceholderCharacter(charEspacio); //caracter que muestra en los espacios a rellenar
        this.mascara.setAllowsInvalid(false); //no permite caracteres invalidos
        this.mascara.setValueContainsLiteralCharacters(false);//no se tienen que ingresar los caracteres de la mascara,
                                                      //y getValue() devuelve el contenido sin los caracteres de la mascara
        
        jFmt = new JFormattedTextField(this.mascara);
        
        if (autoseleccion){
            jFmt.addFocusListener(new FocusAdapter(){
                @Override
                public void focusGained (FocusEvent e){
                    SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        jFmt.selectAll();
                    }
                    });
                }
            });
        }
        return jFmt;        
    }
}
