/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.AnalisisCobranza;
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
public class AnalisisCobranzaDataSource implements JRDataSource {
    private final List<AnalisisCobranza> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(AnalisisCobranza nuevo){
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
        
        if ("vendedor".equals(jrf.getName())){
            valor = lista.get(indiceActual).getVendedor();
        }
        
        if ("id_presupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_presupuesto();
        }
        
        if ("fecha".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFecha();
        }
        
        if ("numComp".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNumComp();
        }
        
        if ("importeAplicado".equals(jrf.getName())){
            valor = lista.get(indiceActual).getImporteAplicado();
        }
        
        if ("impuestos".equals(jrf.getName())){
            valor = lista.get(indiceActual).getImpuestos();
        }
        
        if ("rm".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRm();
        }
        
        if ("neto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNeto();
        }
        
        if ("tipoOperacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTipoOperacion();
        }
        
        return valor;
    }       
}
