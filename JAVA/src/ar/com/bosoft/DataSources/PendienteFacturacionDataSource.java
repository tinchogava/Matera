/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.PendienteFacturacion;
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
public class PendienteFacturacionDataSource implements JRDataSource {
    private final List<PendienteFacturacion> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(PendienteFacturacion nuevo){
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
        
        if ("id_presupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_presupuesto();
        }
        
        if ("circuito".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCircuito();
        }
        
        if ("orden".equals(jrf.getName())){
            valor = lista.get(indiceActual).getOrden();
        }
        
        if ("protocolo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProtocolo();
        }
        
        if ("rx".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRx();
        }
        
        if ("remito".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRemito();
        }
        
        if ("firma".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFirma();
        }
        
        if ("sticker".equals(jrf.getName())){
            valor = lista.get(indiceActual).getSticker();
        }
        if ("fechaCx".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaCx();
        }
        
        if ("lugarCx".equals(jrf.getName())){
            valor = lista.get(indiceActual).getLugarCx();
        }
        
        if ("profesional".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProfesional();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("montoPresupuesto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getMontoPresupuesto();
        }
        
        if ("montoFacturado".equals(jrf.getName())){
            valor = lista.get(indiceActual).getMontoFacturado();
        }
        
        if ("observaciones".equals(jrf.getName())){
            valor = lista.get(indiceActual).getObservaciones();
        }
        
        if ("completo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCompleto();
        }
        
        return valor;
    }       
}
