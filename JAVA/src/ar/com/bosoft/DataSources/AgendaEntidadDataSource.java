/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.AgendaEntidad;
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
public class AgendaEntidadDataSource implements JRDataSource{

    private final List<AgendaEntidad> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(AgendaEntidad nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("nombre".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombre();
        }
        
        if ("direccion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDireccion();
        }
        
        if ("localidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getLocalidad();
        }
        
        if ("departamento".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDepartamento();
        }
        
        if ("provincia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProvincia();
        }
        
        if ("telefono1".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelefono1();
        }
        
        if ("telefono2".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelefono2();
        }
        
        if ("zona".equals(jrf.getName())){
            valor = lista.get(indiceActual).getZona();
        }
        
        if ("email".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmail();
        }
        
        if ("cuit".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCuit();
        }
        
        if ("posIva".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPosIva();
        }
        
        if ("riesgoCredito".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRiesgoCredito();
        }
        
        if ("formaPago".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFormaPago();
        }
        
        if ("secretaria".equals(jrf.getName())){
            valor = lista.get(indiceActual).getSecretaria();
        }
        
        if ("auditor".equals(jrf.getName())){
            valor = lista.get(indiceActual).getAuditor();
        }
        
        if ("nombreCompras".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombreCompras();
        }
        
        if ("telCompras".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelCompras();
        }
        
        if ("emailCompras".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmailCompras();
        }
        
        if ("nombreTesoreria".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombreTesoreria();
        }
        
        if ("telTesoreria".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelTesoreria();
        }
        
        if ("emailTesoreria".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmailTesoreria();
        }
        
        if ("nombreFarmacia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombreFarmacia();
        }
        
        if ("telFarmacia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelFarmacia();
        }
        
        if ("emailFarmacia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmailFarmacia();
        }
        
        if ("nombreContable".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombreContable();
        }
        
        if ("telContable".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelContable();
        }
        
        if ("emailContable".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmailContable();
        }
        
        if ("gln".equals(jrf.getName())){
            valor = lista.get(indiceActual).getGln();
        }
        
        return valor;
    }       
    
}
