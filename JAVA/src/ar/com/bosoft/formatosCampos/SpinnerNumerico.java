package ar.com.bosoft.formatosCampos;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *@author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class SpinnerNumerico extends JSpinner{
    private final int defecto;
    private final int minimo;
    private final int maximo;
    private final int incremento;
    
    public SpinnerNumerico(int defecto, int minimo, int maximo, int incremento){
        this.defecto = defecto;
        this.minimo = minimo;
        this.maximo = maximo;
        this.incremento = incremento;
        
        SpinnerNumberModel modelo = new SpinnerNumberModel(defecto, minimo, maximo, incremento);
        this.setModel(modelo);
    }
    
    public SpinnerNumerico(int defecto, int minimo, int maximo, int incremento, boolean moneda){
        this.defecto = defecto;
        this.minimo = minimo;
        this.maximo = maximo;
        this.incremento = incremento;
        
        SpinnerNumberModel modelo = new SpinnerNumberModel(defecto, minimo, maximo, incremento);
        this.setModel(modelo);
        
        if (moneda){
            //La definición del formato aparece en la clase DecimalFormat
            //Le pone signo $ a los números y los valores negativos los pone entre paréntesis
            this.setEditor(new JSpinner.NumberEditor(this, "$00.00;($00.00)"));
        }
    }    
}
