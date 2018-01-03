/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.AgendaDiaria;
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
public class AgendaDiariaDataSource implements JRDataSource{

    private final List<AgendaDiaria> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(AgendaDiaria nuevo){
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
        
        if ("telefono3".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelefono3();
        }
        
        if ("telefono4".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelefono4();
        }
        
        if ("email".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmail();
        }
        
        if ("zona".equals(jrf.getName())){
            valor = lista.get(indiceActual).getZona();
        }
        
        if ("cuit".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCuit();
        }
        
        if ("contacto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getContacto();
        }
        
        if ("telContacto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelContacto();
        }
        
        if ("movContacto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getMovContacto();
        }
        
        if ("secretaria".equals(jrf.getName())){
            valor = lista.get(indiceActual).getSecretaria();
        }
        
        if ("emailContacto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmailContacto();
        }
        
        return valor;
    }       
    
}
