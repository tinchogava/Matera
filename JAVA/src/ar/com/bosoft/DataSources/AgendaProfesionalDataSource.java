/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.AgendaProfesional;
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
public class AgendaProfesionalDataSource implements JRDataSource{

    private final List<AgendaProfesional> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(AgendaProfesional nuevo){
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
        
        if ("telParticular".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelParticular();
        }
        
        if ("telMovil".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelMovil();
        }
        
        if ("telConsultorio".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelConsultorio();
        }
        
        if ("telOtro".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelOtro();
        }
        
        if ("email".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmail();
        }
        
        if ("especialidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEspecialidad();
        }
        
        if ("secretaria".equals(jrf.getName())){
            valor = lista.get(indiceActual).getSecretaria();
        }
        
        if ("entidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEntidad();
        }
        
        if ("dni".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDni();
        }
        
        if ("matricula".equals(jrf.getName())){
            valor = lista.get(indiceActual).getMatricula();
        }
        
        if ("vendedor".equals(jrf.getName())){
            valor = lista.get(indiceActual).getVendedor();
        }
        
        if ("zona".equals(jrf.getName())){
            valor = lista.get(indiceActual).getZona();
        }
        
        if ("dirConsultorio".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDirConsultorio();
        }
        
        if ("fechaNac".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaNac();
        }
        
        return valor;
    }       
    
}
