/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.MapaCirugia;
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
public class MapaCirugiaDataSource implements JRDataSource {
    private final List<MapaCirugia> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(MapaCirugia nuevo){
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
        
        if ("caja".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCaja();
        }
        
        if ("profesional".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProfesional();
        }
        
        if ("entidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEntidad();
        }
        
        if ("fechaCirugia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaCirugia();
        }
        
        if ("remito".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRemito();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("lugarCirugia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getLugarCirugia();
        }
        
        if ("importePresupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getImportePresupuesto();
        }
        
        return valor;
    }       
}
