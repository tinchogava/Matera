/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.DataSources;

import ar.com.bosoft.Modelos.CostoVentas;
import ar.com.bosoft.clases.Utiles;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDataSource;


/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class CostoVentasDataSource extends AbstractDataSource<CostoVentas> implements JRDataSource {
  
    public Integer getPresupuestosCount(){
        Long c = list.stream().filter(Utiles.distinctByKey(l->l.getId_presupuesto())).count();
        return c.intValue();
    }    
    
    public BigDecimal getCostoTotal(){
        return list.stream()
                .map(i->i.getCostoPesos())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
