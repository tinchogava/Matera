package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.PresupuestoProd;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Herni
 */
public class PresupuestoProdDataSource implements JRDataSource{

    private final List<PresupuestoProd> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void addPresupuestoProd(PresupuestoProd presupuestoProd){
        this.lista.add(presupuestoProd);
    } 
    
    @Override
    public boolean next() throws JRException {
        return ++indiceActual < lista.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor = null;
        
        if ("cantidad".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCantidad();
        }
        
        if ("codigo".equals(jrf.getName())){
            valor = lista.get(indiceActual).getCodigo();
        }
        
        if ("nombre".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombre();
        }
        
        if ("obsProducto".equals(jrf.getName())){
            valor = lista.get(indiceActual).getObsProducto();
        }
        
        if ("pxUnit".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPxUnit();
        }
        
        return valor;
    }       
    
    public int listaSize(){
        return this.lista.size();
    }
}
