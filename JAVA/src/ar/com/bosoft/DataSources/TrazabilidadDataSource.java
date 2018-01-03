/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.Trazabilidad;
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
public class TrazabilidadDataSource  implements JRDataSource{

    private final List<Trazabilidad> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(Trazabilidad nuevo){
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
        
        if ("producto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProducto();
        }
        
        if ("remito".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRemito();
        }
        
        if ("lote".equals(jrf.getName())){
            valor = lista.get(indiceActual).getLote();
        }
        
        if ("serie".equals(jrf.getName())){
            valor = lista.get(indiceActual).getSerie();
        }        
        
        if ("cantidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCantidad();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("profesional".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProfesional();
        }
        
        if ("lugarCx".equals(jrf.getName())){
            valor = lista.get(indiceActual).getLugarCx();
        }
        
        if ("entidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEntidad();
        }
        
        return valor;
    }
}
