package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.MayorPendienteDetallado;
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
public class MayorPendienteDetalladoDataSource implements JRDataSource{

    private final List<MayorPendienteDetallado> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(MayorPendienteDetallado nuevo){
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
        
        if ("fecha".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFecha();
        }
        
        if ("id_presupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_presupuesto();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("tipoOperacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTipoOperacion();
        }
        
        if ("observaciones".equals(jrf.getName())){
            valor = lista.get(indiceActual).getObservaciones();
        }
        
        if ("saldo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getSaldo();
        }
        
        if ("acumulado".equals(jrf.getName())){
            valor = lista.get(indiceActual).getAcumulado();
        }
        
        return valor;
    }       
    
    public boolean tieneDatos(){
        return this.lista.size() > 0;
    }  
}
