/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.ReclamoExterno;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author tinchogava
 */
public class ReclamoExternoDataSource implements JRDataSource{
    private final List<ReclamoExterno> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(ReclamoExterno registro){
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
        
        if ("tipoReclamo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getId_cirugia();
        }
        
        if ("id_cirugia".equals(jrf.getName())){
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
        
        if ("destinoReclamo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDestinoReclamo();
        }
        
        if ("descripcion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDescripcion();
        }
        
        if ("acciones".equals(jrf.getName())){
            valor = lista.get(indiceActual).getAcciones();
        }
        
        if ("fechaReclamo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaReclamo();
        }
        
        if ("fechaNotificacion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaNotificacion();
        }
        
        if ("recibe".equals(jrf.getName())){
            valor = lista.get(indiceActual).getRecibe();
        }
        
        if ("direccion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getDireccion();
        }
        
        if ("telefono".equals(jrf.getName())){
            valor = lista.get(indiceActual).getTelefono();
        }
        
        if ("fechaCirugia".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaCirugia();
        }
        
        if ("fechaDevolucion".equals(jrf.getName())){
            valor = lista.get(indiceActual).getFechaDevolucion();
        }
        
        return valor;
    }

    
}
