/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.ReclamoInterno;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author tinchogava
 */
public class ReclamoInternoDataSource implements JRDataSource {
    
    private final List<ReclamoInterno> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(ReclamoInterno registro){
        this.lista.add(registro);
    } 

    @Override
    public boolean next() throws JRException {
         return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("id_reclamo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_reclamo();
        }
        
        if ("id_cirugia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_cirugia();
        }
        
        if ("tipoReclamo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTipoReclamo();
        }
        
        if ("paciente".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPaciente();
        }
        
        if ("profesional".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProfesional();
        }
        
        if ("prestacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPrestacion();
        }
        
        if ("lugarCirugia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getLugarCirugia();
        }
        
        if ("sectorReclamo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getSectorReclamo();
        }
        
        if ("descripcion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDescripcion();
        }
        
        if ("procedimientos".equals(jrf.getName())){
            valor = lista.get(indiceActual).getProcedimientos();
        }
        
        if ("acciones".equals(jrf.getName())){
            valor = lista.get(indiceActual).getAcciones();
        }
        
        if ("fechaReclamo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaReclamo();
        }
        
        if ("fechaCirugia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaCirugia();
        }
        
        return valor;
    }
    
}
