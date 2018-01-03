/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.managers;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.util.List;
import matera.jooq.tables.records.PresupuestoControlfacturacionRecord;
import org.jooq.Result;
import matera.jooq.Tables;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;

/**
 *
 * @author h2o
 */
public class PresupuestoMgr {
    public Result<PresupuestoControlfacturacionRecord> getControlFacturacion(Integer id_presupuesto){
        return ActiveDatabase.getDSLContext()
                .select()
                .from(Tables.PRESUPUESTO_CONTROLFACTURACION)
                .where(Tables.PRESUPUESTO_CONTROLFACTURACION.ID_PRESUPUESTO.eq(id_presupuesto))
                .fetchInto(Tables.PRESUPUESTO_CONTROLFACTURACION);
    }
    
    public Result<PresupuestoControlfacturacionRecord> getControlFacturacion(List<Integer> presupuestos){
        return ActiveDatabase.getDSLContext()
                .select()
                .from(Tables.PRESUPUESTO_CONTROLFACTURACION)
                .where(Tables.PRESUPUESTO_CONTROLFACTURACION.ID_PRESUPUESTO.in(presupuestos))
                .fetchInto(Tables.PRESUPUESTO_CONTROLFACTURACION);
    }
    
    public static Result<Record> getPresupuestoById(Integer id_presupuesto){
        DSLContext dsl = ActiveDatabase.getDSLContext();
        SelectConditionStep step;
        step = dsl
                .select(Tables.PRESUPUESTO.FECHACIRUGIA, Tables.ENTIDAD.NOMBRE, Tables.PROFESIONAL.NOMBRE,
                        Tables.PRESUPUESTO.PACIENTE, Tables.PRESTACION.NOMBRE)
                .from(Tables.PRESUPUESTO)
                .leftJoin(Tables.ENTIDAD).on(Tables.ENTIDAD.ID_ENTIDAD.equal(Tables.PRESUPUESTO.ID_LUGARCIRUGIA))
                .leftJoin(Tables.PROFESIONAL).on(Tables.PROFESIONAL.ID_PROFESIONAL.equal(Tables.PRESUPUESTO.ID_PROFESIONAL1))
                .leftJoin(Tables.PRESTACION).on(Tables.PRESTACION.ID_PRESTACION.equal(Tables.PRESUPUESTO.ID_PRESTACION))
                .where(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(id_presupuesto));
        return step.fetch();
    }
}
