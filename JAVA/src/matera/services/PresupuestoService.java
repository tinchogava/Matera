/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import matera.jooq.Tables;
import matera.jooq.tables.records.PresupuestoRecord;
import org.jooq.DSLContext;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class PresupuestoService {
   
    public static PresupuestoRecord getPresupuesto(Integer id_presupuesto){
        return getPresupuesto(id_presupuesto, ActiveDatabase.getDSLContext());
    }
    
    public static Result<PresupuestoRecord> getPresupuestosByFechaCirugia(Calendar d1, Calendar d2, 
            List<Integer> presupuestosPorRevisar){
        java.sql.Date since = new java.sql.Date(d1.getTime().getTime());
        java.sql.Date until = new java.sql.Date(d2.getTime().getTime());
        return getPresupuestosByFechaCirugia(since, until, presupuestosPorRevisar, ActiveDatabase.getDSLContext());
    }
    
    public static Date getFechaCirugia(Integer id_presupuesto){
        return getFechaCirugia(id_presupuesto, ActiveDatabase.getDSLContext());
    }
    
    public static void updateObservaciones(String observaciones, Integer id_presupuesto){
        updateObservaciones(observaciones, id_presupuesto, ActiveDatabase.getDSLContext());
    }
    
    private static PresupuestoRecord getPresupuesto(Integer id_presupuesto, DSLContext dsl){
        return dsl.select()
                .from(Tables.PRESUPUESTO)
                .where(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(id_presupuesto))
                .fetchOneInto(Tables.PRESUPUESTO);
    }
    
    /*private static Result<Record> getAnalisisFacturacionByFechaCirugia(Date since, Date until, List<Integer> yaCosteados, 
            DSLContext dsl) {
        SelectConditionStep step = dsl.select(
                Tables.PRESUPUESTO.ID_PRESUPUESTO,
                Tables.TIPOOPERACION.NOMBRE,
                Tables.VENDEDOR.NOMBRE,
                Tables.ZONA.NOMBRE,
                Tables.PRESUPUESTO.ID_TIPOOPERACION
                )
                .from(Tables.PRESUPUESTO)
                .join(Tables.TIPOOPERACION)
                .on(Tables.TIPOOPERACION.equals(Tables.PRESUPUESTO.ID_TIPOOPERACION))
                .join(Tables.VENDEDOR)
                .on(Tables.VENDEDOR.ID_VENDEDOR.eq(Tables.PRESUPUESTO.ID_VENDEDOR))
                .join(Tables.ZONA)
                .on(Tables.ZONA.ID_ZONA.eq(Tables.PRESUPUESTO.ID_ZONA))
                .where(Tables.PRESUPUESTO.FECHACIRUGIA.between(since, until))
                .and(Tables.PRESUPUESTO.ID_PRESUPUESTO.notIn(yaCosteados));
        return step.fetch();
    }*/

    private static Result<PresupuestoRecord> getPresupuestosByFechaCirugia(Date since, Date until, 
            List<Integer> presupuestosPorRevisar, DSLContext dsl) {
        return dsl.select()
                .from(Tables.PRESUPUESTO)
                .where(Tables.PRESUPUESTO.FECHACIRUGIA.between(since, until))
                .and(Tables.PRESUPUESTO.ID_PRESUPUESTO.in(presupuestosPorRevisar))
                .fetchInto(Tables.PRESUPUESTO);
    }
    
    private static Date getFechaCirugia(Integer id_presupuesto, DSLContext dsl){
        return dsl.select(Tables.PRESUPUESTO.FECHACIRUGIA)
                .from(Tables.PRESUPUESTO)
                .where(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(id_presupuesto))
                .fetchOne(Tables.PRESUPUESTO.FECHACIRUGIA);
    }

    private static void updateObservaciones(String observaciones, Integer id_presupuesto, DSLContext dsl) {
        dsl.update(Tables.PRESUPUESTO)
                .set(Tables.PRESUPUESTO.OBSERVACIONES, observaciones)
                .where(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(id_presupuesto))
                .execute();
    }
    
}
