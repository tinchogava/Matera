package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.EstrategiaEntidad;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Herni
 */
public class EstrategiaEntidadDataSource implements JRDataSource{

    private final List<EstrategiaEntidad> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(EstrategiaEntidad nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("entidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEntidad();
        }
        
        if ("fechaInicio".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaInicio();
        }
        
        if ("fechaAgenda".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaAgenda();
        }
        
        if ("estrategia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEstrategia();
        }
        
        return valor;
    }
}
