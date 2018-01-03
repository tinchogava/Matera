/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.helpers;

import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.formularios.Principal;
import static matera.jooq.Tables.LOG_EVENTO_PRESUPUESTO;
import static matera.jooq.Tables.LOG_EVENTO_REMITO;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;

/**
 *
 * @author h2o
 */
public class LogHelper {
    
    public static void logPresupuestoEvent(Integer id, Integer event_type){
        ActiveDatabase.getDSLContext().insertInto(LOG_EVENTO_PRESUPUESTO)
                .set(LOG_EVENTO_PRESUPUESTO.TIMESTAMP, DSL.currentTimestamp())
                .set(LOG_EVENTO_PRESUPUESTO.ID_LOG_EVENTO_TIPO, event_type)
                .set(LOG_EVENTO_PRESUPUESTO.ID_USUARIO, (UInteger.valueOf(Principal.getIdUsuario())))
                .set(LOG_EVENTO_PRESUPUESTO.ID_PRESUPUESTO, id)
                .execute();    
    }
    
    public static void logRemitoEvent(Integer id, Integer event_type){
        ActiveDatabase.getDSLContext().insertInto(LOG_EVENTO_REMITO)
                .set(LOG_EVENTO_REMITO.TIMESTAMP, DSL.currentTimestamp())
                .set(LOG_EVENTO_REMITO.ID_LOG_EVENTO_TIPO, event_type)
                .set(LOG_EVENTO_REMITO.ID_USUARIO, (UInteger.valueOf(Principal.getIdUsuario())))
                .set(LOG_EVENTO_REMITO.ID_REMITO, id)
                .execute();    
    }    
    
}
