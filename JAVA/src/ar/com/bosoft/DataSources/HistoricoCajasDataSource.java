/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.HistoricoCajas;
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
public class HistoricoCajasDataSource implements JRDataSource {
    private final List<HistoricoCajas> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(HistoricoCajas nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("caja".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCaja();
        }
        
        if ("id".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId();
        }
        
        if ("id_presupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_presupuesto();
        }
        
        if ("fechaCirugia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaCirugia();
        }
        
        if ("lugarCirugia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getLugarCirugia();
        }
        
        if ("zona".equals(jrf.getName())){
            valor = lista.get(indiceActual).getZona();
        }
        
        if ("profesional".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProfesional();
        }
        
        if ("remito".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRemito();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("factura".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFactura();
        }
        
        return valor;
    }       
}
