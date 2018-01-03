/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.ConsolidadoCobranzaGeneral;
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
public class ConsolidadoCobranzaGeneralDataSource implements JRDataSource {
    private final List<ConsolidadoCobranzaGeneral> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(ConsolidadoCobranzaGeneral nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("year".equals(jrf.getName())){
            valor = lista.get(indiceActual).getYear();
        }
        
        if ("mes".equals(jrf.getName())){
            valor = lista.get(indiceActual).getMes();
        }
        
        if ("importeAplicado".equals(jrf.getName())){
            valor = lista.get(indiceActual).getImporteAplicado();
        }
        
        return valor;
    }       
}
