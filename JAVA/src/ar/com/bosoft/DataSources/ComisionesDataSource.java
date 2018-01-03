package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.Comisiones;
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
public class ComisionesDataSource implements JRDataSource{

    private final List<Comisiones> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(Comisiones nuevo){
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
    
    public boolean tieneDatos(){
        return this.lista.size() > 0;
    }       
}
