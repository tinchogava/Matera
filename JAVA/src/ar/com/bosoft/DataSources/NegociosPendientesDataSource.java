/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.NegociosPendientes;
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
public class NegociosPendientesDataSource implements JRDataSource{
    private final List<NegociosPendientes> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(NegociosPendientes nuevo){
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
        
        if ("fecha".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFecha();
        }
        
        if ("entidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEntidad();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("direccion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDireccion();
        }
        
        if ("telefono".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelefono();
        }
        
        if ("observaciones".equals(jrf.getName())){
            valor = lista.get(indiceActual).getObservaciones();
        }
        
        if ("total".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTotal();
        }
        
        if ("producto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProducto();
        }
        
        if ("profesional".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProfesional();
        }
        
        return valor;
    }       
}
