package ar.com.bosoft.formatosCampos;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

/**
 *@author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class SpinnerLista extends JSpinner{
    private final String [] lista;
    
    public SpinnerLista(String [] lista){
        this.lista  = lista;
        
        SpinnerListModel modelo = new SpinnerListModel(lista);
        this.setModel(modelo);
    }
    //Metodos personalizados
    public String obtenerValor(){
        return this.getValue().toString();
    }
    
    public void ponerValor(String valor){
        this.setValue(valor);
    }
}
