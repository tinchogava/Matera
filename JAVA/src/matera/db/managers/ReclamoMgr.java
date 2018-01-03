/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.managers;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import matera.jooq.Tables;

import matera.jooq.tables.records.ReclamoRecord;
import matera.jooq.tables.records.ReclamoexternoRecord;
import matera.jooq.tables.records.ReclamointernoRecord;
import matera.TableObjects.ReclamoTableObject;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;

/**
 *
 * @author tinchogava
 */
public class ReclamoMgr {
    
    public static Result<Record> getReclamo(Integer id_reclamo) {
        return getReclamo(id_reclamo, ActiveDatabase.getDSLContext());
    }
    
    public static Result<ReclamointernoRecord> getReclamosInternos(){
        return getReclamosInternos(ActiveDatabase.getDSLContext(), false);
    }
    
    public static Result<ReclamoexternoRecord> getReclamosExternos(){
        return getReclamosExternos(ActiveDatabase.getDSLContext(), false);
    }
    
    public static Result<ReclamointernoRecord> getReclamosInternosHabilitados(){
        return getReclamosInternos(ActiveDatabase.getDSLContext(), true);
    }
    
    public static Result<ReclamoexternoRecord> getReclamosExternosHabilitados(){
        return getReclamosExternos(ActiveDatabase.getDSLContext(), true);
    }
    
    public static List<ReclamoTableObject> getReclamos(){
        return getReclamos(ActiveDatabase.getDSLContext());
    }
    
    public static List<ReclamoTableObject> getReclamoById(Integer id_reclamo){
        return getReclamoById(id_reclamo, ActiveDatabase.getDSLContext());
    }
    
    public static List<ReclamoTableObject> getReclamosByDate(Date sinceDate, Date untilDate){
        return getReclamosByDate(sinceDate, untilDate, ActiveDatabase.getDSLContext());
        
    }
    
    public static List<ReclamoTableObject> getReclamosByType(boolean sortingInterno, Integer type){
        return getReclamosByType(sortingInterno, type, ActiveDatabase.getDSLContext());
    }

    private static Result<ReclamointernoRecord> getReclamosInternos(DSLContext dsl, boolean habilitados) {
        if(!habilitados){
            return dsl.select().from(Tables.RECLAMOINTERNO)
                .fetch()
                .into(Tables.RECLAMOINTERNO);
        } else {
            return dsl.select().from(Tables.RECLAMOINTERNO)
                .where(Tables.RECLAMOINTERNO.HABILITA.equal("S"))
                .fetch()
                .into(Tables.RECLAMOINTERNO);
        }     
    }

    private static Result<ReclamoexternoRecord> getReclamosExternos(DSLContext dsl, boolean habilitados) {
        if (!habilitados){
            return dsl.select().from(Tables.RECLAMOEXTERNO)
                .fetch()
                .into(Tables.RECLAMOEXTERNO);
        } else {
            return dsl.select().from(Tables.RECLAMOEXTERNO)
                .where(Tables.RECLAMOEXTERNO.HABILITA.equal("S"))
                .fetch()
                .into(Tables.RECLAMOEXTERNO);
        }
        
    }
    
    private static List<ReclamoTableObject> getReclamos(DSLContext dsl) {
        List<ReclamoTableObject> list = new ArrayList<>();
            dsl.select()
                    .from(Tables.RECLAMO)
                    .leftJoin(Tables.RECLAMOINTERNO)
                    .on(Tables.RECLAMOINTERNO.ID_RECLAMOINTERNO.eq(Tables.RECLAMO.ID_RECLAMOINTERNO))
                    .leftJoin(Tables.RECLAMOEXTERNO)
                    .on(Tables.RECLAMOEXTERNO.ID_RECLAMOEXTERNO.eq(Tables.RECLAMO.ID_RECLAMOEXTERNO))
                    .leftJoin(Tables.PRESUPUESTO)
                    .on(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(Tables.RECLAMO.ID_PRESUPUESTO))
                    .leftJoin(Tables.ENTIDAD)
                    .on(Tables.ENTIDAD.ID_ENTIDAD.eq(Tables.PRESUPUESTO.ID_LUGARCIRUGIA))
                    .leftJoin(Tables.PROFESIONAL)
                    .on(Tables.PROFESIONAL.ID_PROFESIONAL.eq(Tables.PRESUPUESTO.ID_PROFESIONAL1))
                    .leftJoin(Tables.PRESTACION)
                    .on(Tables.PRESTACION.ID_PRESTACION.eq(Tables.PRESUPUESTO.ID_PRESTACION))
                    .fetch()
                    .into((Record r1) -> {
                    list.add(
                            new ReclamoTableObject(r1)
                            );
                    });
            return list;
    }
    
    public static int storeReclamo(ReclamoRecord reclamoRecord){
       return reclamoRecord.store();
    }

    public static List<ReclamoTableObject> getReclamoById(Integer id_reclamo, DSLContext dsl) {
        List<ReclamoTableObject> list = new ArrayList<>();
            dsl.select()
                    .from(Tables.RECLAMO)
                    .leftJoin(Tables.RECLAMOINTERNO)
                    .on(Tables.RECLAMOINTERNO.ID_RECLAMOINTERNO.eq(Tables.RECLAMO.ID_RECLAMOINTERNO))
                    .leftJoin(Tables.RECLAMOEXTERNO)
                    .on(Tables.RECLAMOEXTERNO.ID_RECLAMOEXTERNO.eq(Tables.RECLAMO.ID_RECLAMOEXTERNO))
                    .where(Tables.RECLAMO.ID_RECLAMO.eq(id_reclamo))
                    .fetch()
                    .into((Record r1) -> {
                    list.add(
                            new ReclamoTableObject(r1)
                            );
                    });
            return list;
    }

    public static List<ReclamoTableObject> getReclamosByDate(Date sinceDate, Date untilDate, DSLContext dsl) {
        List<ReclamoTableObject> list = new ArrayList<>();
            dsl.select()
                    .from(Tables.RECLAMO)
                    .leftJoin(Tables.RECLAMOINTERNO)
                    .on(Tables.RECLAMOINTERNO.ID_RECLAMOINTERNO.eq(Tables.RECLAMO.ID_RECLAMOINTERNO))
                    .leftJoin(Tables.RECLAMOEXTERNO)
                    .on(Tables.RECLAMOEXTERNO.ID_RECLAMOEXTERNO.eq(Tables.RECLAMO.ID_RECLAMOEXTERNO))
                    .where(Tables.RECLAMO.FECHARECLAMO.between(sinceDate, untilDate))
                    .fetch()
                    .into((Record r1) -> {
                    list.add(
                            new ReclamoTableObject(r1)
                            );
                    });
            return list;
    }

    public static List<ReclamoTableObject> getReclamosByType(boolean sortingInterno, int type, DSLContext dsl) {
        List<ReclamoTableObject> list = new ArrayList<>();
        if(sortingInterno){
             dsl.select()
                    .from(Tables.RECLAMO)
                    .leftJoin(Tables.RECLAMOINTERNO)
                    .on(Tables.RECLAMOINTERNO.ID_RECLAMOINTERNO.eq(Tables.RECLAMO.ID_RECLAMOINTERNO))
                    .where(Tables.RECLAMO.ID_RECLAMOINTERNO.eq(type))
                    .fetch()
                    .into((Record r1) -> {
                    list.add(
                            new ReclamoTableObject(r1)
                            );
                    });
            return list;
        } else {
             dsl.select()
                    .from(Tables.RECLAMO)
                    .leftJoin(Tables.RECLAMOEXTERNO)
                    .on(Tables.RECLAMOEXTERNO.ID_RECLAMOEXTERNO.eq(Tables.RECLAMO.ID_RECLAMOEXTERNO))
                    .where(Tables.RECLAMO.ID_RECLAMOEXTERNO.eq(type))
                    .fetch()
                    .into((Record r1) -> {
                    list.add(
                            new ReclamoTableObject(r1)
                            );
                    });
            return list;
        }
        
    }

    public static Result<Record> getReclamo(Integer id_reclamo, DSLContext dsl) {
        SelectConditionStep step;
        step = dsl
                .select(Tables.RECLAMO.ID_RECLAMO, Tables.RECLAMO.FECHARECLAMO, Tables.RECLAMO.FECHADEVOLUCION, 
                Tables.RECLAMO.FECHANOTIFICACION, Tables.RECLAMO.ACCIONES, Tables.RECLAMO.DESCRIPCION,
                Tables.RECLAMO.DESTINORECLAMO, Tables.RECLAMO.DIRECCION, Tables.RECLAMO.PROCEDIMIENTO,
                Tables.RECLAMO.RECIBE, Tables.RECLAMO.TELEFONO, Tables.RECLAMOINTERNO.NOMBRE, Tables.RECLAMOEXTERNO.NOMBRE,
                Tables.PRESUPUESTO.FECHACIRUGIA, Tables.PRESUPUESTO.PACIENTE, Tables.PROFESIONAL.NOMBRE,
                Tables.ENTIDAD.NOMBRE, Tables.PRESTACION.NOMBRE)
                    .from(Tables.RECLAMO)
                    .leftJoin(Tables.RECLAMOINTERNO)
                    .on(Tables.RECLAMOINTERNO.ID_RECLAMOINTERNO.eq(Tables.RECLAMO.ID_RECLAMOINTERNO))
                    .leftJoin(Tables.RECLAMOEXTERNO)
                    .on(Tables.RECLAMOEXTERNO.ID_RECLAMOEXTERNO.eq(Tables.RECLAMO.ID_RECLAMOEXTERNO))
                    .leftJoin(Tables.PRESUPUESTO)
                    .on(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(Tables.RECLAMO.ID_PRESUPUESTO))
                    .leftJoin(Tables.PRESTACION)
                    .on(Tables.PRESTACION.ID_PRESTACION.eq(Tables.PRESUPUESTO.ID_PRESTACION))
                    .leftJoin(Tables.PROFESIONAL)
                    .on(Tables.PROFESIONAL.ID_PROFESIONAL.eq(Tables.PRESUPUESTO.ID_PROFESIONAL1))
                    .leftJoin(Tables.ENTIDAD)
                    .on(Tables.ENTIDAD.ID_ENTIDAD.eq(Tables.PRESUPUESTO.ID_LUGARCIRUGIA))
                    .where(Tables.RECLAMO.ID_RECLAMO.eq(id_reclamo));
                return step.fetch();
                    
    }
    
}
