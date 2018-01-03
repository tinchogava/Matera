/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.ControlEntidades;
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
public class ControlEntidadesDataSource implements JRDataSource{
    private final List<ControlEntidades> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(ControlEntidades nuevo){
        this.lista.add(nuevo);
    } 

    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("zona".equals(jrf.getName())){
            valor = lista.get(indiceActual).getZona();
        }
        
        if ("entidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEntidad();
        }
        
        if ("cantPresuEmitidos".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCantPresuEmitidos();
        }
        
        if ("montoPresuEmitidos".equals(jrf.getName())){
            valor = lista.get(indiceActual).getMontoPresuEmitidos();
        }
        
        if ("cantPresuAprobados".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCantPresuAprobados();
        }
        
        if ("montoPresuAprobados".equals(jrf.getName())){
            valor = lista.get(indiceActual).getMontoPresuAprobados();
        }
        
        if ("cantPresuFacturados".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCantPresuFacturados();
        }
        
        if ("montoFactTotal".equals(jrf.getName())){
            valor = lista.get(indiceActual).getMontoFactTotal();
        }
        
        if ("factZona".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFactZona();
        }
        
        return valor;
    }
    
}
