package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.ControlPrecios;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ControlPreciosDataSource implements JRDataSource{

    private final List<ControlPrecios> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(ControlPrecios nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("cantidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCantidad();
        }
        
        if ("codigo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCodigo();
        }
        
         if ("gtin".equals(jrf.getName())){
            valor = lista.get(indiceActual).getGtin();
        }       
        
        if ("descripcion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDescripcion();
        }
        
        if ("clasificacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getClasificacion();
        }
        
        if ("precio".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPrecio();
        }
        
        return valor;
    }       
}
