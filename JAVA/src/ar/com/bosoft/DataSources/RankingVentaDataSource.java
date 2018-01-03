/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.RankingVenta;
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
public class RankingVentaDataSource implements JRDataSource {
    private final List<RankingVenta> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(RankingVenta nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("cabecera".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCabecera();
        }
        
        if ("registro".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRegistro();
        }
        
        if ("valor".equals(jrf.getName())){
            valor = lista.get(indiceActual).getValor();
        }
        
        if ("facturado".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFacturado();
        }
        
        if ("efectividad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEfectividad();
        }
        
        return valor;
    }       
}
