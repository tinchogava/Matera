/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.CumpleProf;
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
public class CumpleProfDataSource implements JRDataSource{

    private final List<CumpleProf> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(CumpleProf nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("dia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDia();
        }
        
        if ("nombre".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombre();
        }
        
        if ("email".equals(jrf.getName())){
            valor = lista.get(indiceActual).getEmail();
        }
        
        if ("telParticular".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelParticular();
        }
        
        if ("telMovil".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelMovil();
        }
        
        if ("agente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getAgente();
        }
        
        return valor;
    }       
    
}
