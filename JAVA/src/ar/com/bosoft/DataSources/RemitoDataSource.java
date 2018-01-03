package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.Remito;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Herni
 */
public class RemitoDataSource implements JRDataSource{

    private final List<Remito> lista = new ArrayList<>();
    private int indiceActual = -1;
    
    public void add(Remito registro){
        this.lista.add(registro);
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
        
        
        if ("gtin".equals(jrf.getName())){
            valor = lista.get(indiceActual).getGtin();
        }
        
        if ("nombre".equals(jrf.getName())){
            valor = lista.get(indiceActual).getNombre();
        }
        
        if ("lote".equals(jrf.getName())){
            valor = lista.get(indiceActual).getLote();
        }
        
        if ("serie".equals(jrf.getName())){
            valor = lista.get(indiceActual).getSerie();
        }        
        
        if ("pm".equals(jrf.getName())){
            valor = lista.get(indiceActual).getPm();
        }
        
        if ("vencimiento".equals(jrf.getName())){
            valor = lista.get(indiceActual).getVencimiento();
        }
        
        return valor;
    }       
    
    public int listaSize(){
        return this.lista.size();
    }
}
