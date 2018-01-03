/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.AnalisisFacturacion;
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
public class AnalisisFacturacionDataSource implements JRDataSource {
    private final List<AnalisisFacturacion> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(AnalisisFacturacion nuevo){
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
        
        if ("id_presupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_presupuesto();
        }
        
        if ("tipoOperacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTipoOperacion();
        }
        
        if ("total".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTotal();
        }
        
        if ("aliPercIIBB".equals(jrf.getName())){
            valor = lista.get(indiceActual).getAliPercIIBB();
        }
        
        if ("percIIBB".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPercIIBB();
        }
        
        if ("iva21".equals(jrf.getName())){
            valor = lista.get(indiceActual).getIva21();
        }
        
        if ("iva105".equals(jrf.getName())){
            valor = lista.get(indiceActual).getIva105();
        }
        
        if ("netoExento".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNetoExento();
        }
        
        if ("netoX".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNetoX();
        }
        
        if ("neto21".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNeto21();
        }
        
        if ("neto105".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNeto105();
        }
        
        if ("iibb".equals(jrf.getName())){
            valor = lista.get(indiceActual).getIibb();
        }
        
        if ("rm".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRm();
        }
        
        if ("importeFlotacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getImporteFlotacion();
        }
        
        if ("costoVentas".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCostoVentas();
        }
        
        if ("agente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getAgente();
        }
        return valor;
    }       
}
