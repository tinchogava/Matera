/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.AgendaProveedor;
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
public class AgendaProveedorDataSource implements JRDataSource{

    private final List<AgendaProveedor> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(AgendaProveedor nuevo){
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
        
        if ("codPostal".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCodPostal();
        }
        
        if ("telefono1".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelefono1();
        }
        
        if ("telefono2".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelefono2();
        }
        
        if ("email".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmail();
        }
        
        if ("cuentaBanco".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCuentaBanco();
        }
        
        if ("formaPago".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFormaPago();
        }
        
        if ("cuit".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCuit();
        }
        
        if ("gerente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getGerente();
        }
        
        if ("nombrePagos".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombrePagos();
        }
        
        if ("telPagos".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelPagos();
        }
        
        if ("emailPagos".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmailPagos();
        }
        
        if ("nombreAtencion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombreAtencion();
        }
        
        if ("telAtencion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelAtencion();
        }
        
        if ("emailAtencion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmailAtencion();
        }
        
        if ("nombreDeposito".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombreDeposito();
        }
        
        if ("telDeposito".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelDeposito();
        }
        
        if ("emailDeposito".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmailDeposito();
        }
        
        if ("gln".equals(jrf.getName())){
            valor = lista.get(indiceActual).getGln();
        }
        
        return valor;
    }       
    
}
