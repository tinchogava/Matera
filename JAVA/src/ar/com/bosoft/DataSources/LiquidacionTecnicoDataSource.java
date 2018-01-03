package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.LiquidacionTecnico;
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
public class LiquidacionTecnicoDataSource implements JRDataSource{

    private final List<LiquidacionTecnico> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(LiquidacionTecnico nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("fecha".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFecha();
        }
        
        if ("id_presupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_presupuesto();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("profesional".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProfesional();
        }
        
        if ("tecnico".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTecnico();
        }
        
        if ("importe".equals(jrf.getName())){
            valor = lista.get(indiceActual).getImporte();
        }
        
        return valor;
    }       
}
