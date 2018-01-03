package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.LiqTec;
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
public class LiqTecDataSource implements JRDataSource{

    private final List<LiqTec> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(LiqTec nuevo){
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
        
        if ("fechaCx".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaCx();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("profesional".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProfesional();
        }
        
        if ("prestacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPrestacion();
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
