package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.ListaLiquidacion;
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
public class ListaLiquidacionDataSource implements JRDataSource{

    private final List<ListaLiquidacion> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(ListaLiquidacion nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("id_presupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_presupuesto();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("importe".equals(jrf.getName())){
            valor = lista.get(indiceActual).getImporte();
        }
        
        if ("banco".equals(jrf.getName())){
            valor = lista.get(indiceActual).getBanco();
        }
        
        if ("cheque".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCheque();
        }
        
        if ("vencimiento".equals(jrf.getName())){
            valor = lista.get(indiceActual).getVencimiento();
        }
        
        if ("valor".equals(jrf.getName())){
            valor = lista.get(indiceActual).getValor();
        }
        
        if ("id_liquidacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_liquidacion();
        }
        
        if ("profesional".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProfesional();
        }
        
        if ("fecha".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFecha();
        }
        
        return valor;
    }       
}
