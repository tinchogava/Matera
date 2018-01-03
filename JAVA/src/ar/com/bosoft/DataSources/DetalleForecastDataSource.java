/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.DetalleForecast;
import ar.com.bosoft.clases.Utiles;
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
public class DetalleForecastDataSource implements JRDataSource{
    private final List<DetalleForecast> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(DetalleForecast nuevo){
        this.lista.add(nuevo);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        return Utiles.getAttr(lista.get(indiceActual), jrf.getName());
    }
}
