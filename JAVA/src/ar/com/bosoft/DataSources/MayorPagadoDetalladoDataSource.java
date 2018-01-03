package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.MayorPagadoDetallado;
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
public class MayorPagadoDetalladoDataSource implements JRDataSource{

    private final List<MayorPagadoDetallado> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(MayorPagadoDetallado nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("nombre".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombre();
        }
        
        if ("fechaLiquidacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaLiquidacion();
        }
        
        if ("id_presupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_presupuesto();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("detallePago".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDetallePago();
        }
        
        if ("importe".equals(jrf.getName())){
            valor = lista.get(indiceActual).getImporte();
        }
        
        return valor;
    }
    
    public boolean tieneDatos(){
        return this.lista.size() > 0;
    }
}
