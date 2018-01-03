/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import ar.com.bosoft.DataSources.CostoVentasDataSource;
import java.sql.Date;
import matera.db.dao.PresupuestoDao;

/**
 *
 * @author h2o
 * Serivicio para obtener los datos de la marcha de negocio de una ZONA entre el primer dia del mes y el dia actual.
 */
public class CostoVentasService {
    public static final int BY_FECHA_CIRUGIA = 0;
    public static final int BY_FECHA_CONSUMO = 1;
    
    
    public static CostoVentasDataSource getCostoVentas(Integer date_type, Date d1, Date d2, Integer id_proveedor, Integer id_zona){
        return new PresupuestoDao().getCostoVentas(date_type, d1, d2, id_proveedor, id_zona);
    }
    
    public static CostoVentasDataSource getCostoVentasByPresupuesto(Integer id_presupuesto){
        return new PresupuestoDao().getCostoVentasByPresupuesto(id_presupuesto);
    }
    
}
