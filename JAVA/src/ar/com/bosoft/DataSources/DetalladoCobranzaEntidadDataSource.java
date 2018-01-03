/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.DetalladoCobranzaEntidad;
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
public class DetalladoCobranzaEntidadDataSource implements JRDataSource {
    private final List<DetalladoCobranzaEntidad> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(DetalladoCobranzaEntidad nuevo){
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
        
        if ("fechaRecibo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaRecibo();
        }
        
        if ("numRecibo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNumRecibo();
        }
        
        if ("id_presupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_presupuesto();
        }
        
        if ("fechaFactura".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaFactura();
        }
        
        if ("numFactura".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNumFactura();
        }
        
        if ("importeAplicado".equals(jrf.getName())){
            valor = lista.get(indiceActual).getImporteAplicado();
        }
        
        if ("rm1".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRm1();
        }
        
        if ("rm2".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRm2();
        }
        
        if ("tipoOperacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTipoOperacion();
        }
        
        return valor;
    }       
}
