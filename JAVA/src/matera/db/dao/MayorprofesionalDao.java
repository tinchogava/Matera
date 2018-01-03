/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.dao;

import ar.com.bosoft.DataSources.MayorGeneradoConsolidadoDataSource;
import ar.com.bosoft.DataSources.MayorGeneradoDetalladoDataSource;
import ar.com.bosoft.DataSources.MayorGeneralConsolidadoDataSource;
import ar.com.bosoft.DataSources.MayorGeneralDetalladoDataSource;
import ar.com.bosoft.Modelos.MayorGeneradoConsolidado;
import ar.com.bosoft.Modelos.MayorGeneradoDetallado;
import ar.com.bosoft.Modelos.MayorGeneralConsolidado;
import ar.com.bosoft.Modelos.MayorGeneralDetallado;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.ActiveDatabase;
import com.google.common.base.Optional;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import matera.cache.CacheProfesional;
import matera.jooq.Routines;
import matera.jooq.Tables;
import matera.jooq.tables.records.MayorprofesionalRecord;
import matera.jooq.tables.records.PresupuestoRecord;
import matera.jooq.tables.records.ProfesionalRecord;
import matera.jooq.tables.records.TipooperacionRecord;
import org.jooq.Record;
import org.hibernate.type.IntegerType;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

/**
 *
 * @author h2o
 */
public class MayorprofesionalDao extends matera.jooq.tables.daos.MayorprofesionalDao {
    
    public MayorprofesionalDao(){
        super(ActiveDatabase.getDSLContext().configuration());
    }
    
    /*
    RM pendiente entre fechas d1 y d2 para la zona id_zona
    */
    public Double getRmPendienteBetweenDatesForZona(Date d1, Date d2, Integer id_zona){
        return DSL.using(configuration())
                .select(
                        DSL.sum(
                            DSL.decode()
                                .when(Tables.MAYORPROFESIONAL.DC.eq("C"),Tables.MAYORPROFESIONAL.IMPORTE)
                                .otherwise(Tables.MAYORPROFESIONAL.IMPORTE.neg())
                        )
                )
                .from(Tables.MAYORPROFESIONAL)
                .leftJoin(Tables.PRESUPUESTO)
                .on(Tables.MAYORPROFESIONAL.ID_PRESUPUESTO.eq(Tables.PRESUPUESTO.ID_PRESUPUESTO))
                .join(Tables.PROFESIONAL)
                .on(Tables.PROFESIONAL.ID_PROFESIONAL.eq(Tables.MAYORPROFESIONAL.ID_PROFESIONAL))
                .where(Tables.MAYORPROFESIONAL.PRELIQUIDACION.eq(IntegerType.ZERO))
                .and(Tables.PROFESIONAL.ID_ZONA.eq(id_zona))
                .and(Tables.MAYORPROFESIONAL.FECHA.greaterOrEqual(d1))
                .and(Tables.MAYORPROFESIONAL.FECHA.lessOrEqual(d2))
                
                .fetchOneInto(BigDecimal.class).doubleValue();
    }
    
    public MayorGeneradoDetalladoDataSource getMayorGeneradoDetallado(Date d1, Date d2, List<Integer> profesionales){
        MayorGeneradoDetalladoDataSource data = new MayorGeneradoDetalladoDataSource(); 
        DSL.using(configuration())
                .select(Tables.MAYORPROFESIONAL.FECHA,
                        Tables.MAYORPROFESIONAL.ID_PRESUPUESTO, 
                        Tables.PRESUPUESTO.PACIENTE,
                        Tables.MAYORPROFESIONAL.OBSERVACIONES,
                        Tables.MAYORPROFESIONAL.DC,
                        Tables.MAYORPROFESIONAL.IMPORTE,
                        Tables.PROFESIONAL.NOMBRE
                )
                .from(Tables.MAYORPROFESIONAL)
                .leftJoin(Tables.PRESUPUESTO)
                .on(Tables.MAYORPROFESIONAL.ID_PRESUPUESTO.eq(Tables.PRESUPUESTO.ID_PRESUPUESTO))
                .join(Tables.PROFESIONAL)
                .on(Tables.PROFESIONAL.ID_PROFESIONAL.eq(Tables.MAYORPROFESIONAL.ID_PROFESIONAL))
                .where(Tables.MAYORPROFESIONAL.PRELIQUIDACION.eq(IntegerType.ZERO))
                .and(Tables.MAYORPROFESIONAL.ID_PROFESIONAL.in(profesionales))
                .and(Tables.MAYORPROFESIONAL.FECHA.greaterOrEqual(d1))
                .and(Tables.MAYORPROFESIONAL.FECHA.lessOrEqual(d2))
                .orderBy(Tables.PROFESIONAL.NOMBRE, Tables.MAYORPROFESIONAL.FECHA)
                .fetch().into((Record r)->{
                    MayorprofesionalRecord mpr = r.into(Tables.MAYORPROFESIONAL);
                    PresupuestoRecord pr = r.into(Tables.PRESUPUESTO);
                    ProfesionalRecord pfr = r.into(Tables.PROFESIONAL);
                    data.add(new MayorGeneradoDetallado(
                            mpr.getFecha(),
                            mpr.getIdPresupuesto(),
                            pfr.getNombre(),
                            pr.getPaciente(),
                            mpr.getObservaciones(),
                            mpr.getDc(),
                            mpr.getImporte()));
                });
        return data;              
    }
    
    public MayorGeneradoConsolidadoDataSource getMayorGeneradoConsolidado(Date d1, Date d2, List<Integer> profesionales){
        MayorGeneradoConsolidadoDataSource data = new MayorGeneradoConsolidadoDataSource(); 
        DSL.using(configuration())
                .select(
                        Tables.PROFESIONAL.NOMBRE,
                        DSL.sum(
                            DSL.decode()
                                .when(Tables.MAYORPROFESIONAL.DC.eq("C"),Tables.MAYORPROFESIONAL.IMPORTE)
                                .otherwise(Tables.MAYORPROFESIONAL.IMPORTE.neg())
                        ).as("saldo")
                )
                .from(Tables.MAYORPROFESIONAL)
                .join(Tables.PROFESIONAL)
                .on(Tables.PROFESIONAL.ID_PROFESIONAL.eq(Tables.MAYORPROFESIONAL.ID_PROFESIONAL))
                .where(Tables.MAYORPROFESIONAL.PRELIQUIDACION.eq(IntegerType.ZERO))
                .and(Tables.MAYORPROFESIONAL.ID_PROFESIONAL.in(profesionales))
                .and(Tables.MAYORPROFESIONAL.FECHA.greaterOrEqual(d1))
                .and(Tables.MAYORPROFESIONAL.FECHA.lessOrEqual(d2))
                .groupBy(Tables.MAYORPROFESIONAL.ID_PROFESIONAL)
                .orderBy(Tables.PROFESIONAL.NOMBRE)
                .fetch().into((Record r)->{
                    ProfesionalRecord pfr = r.into(Tables.PROFESIONAL);
                    BigDecimal saldo = r.getValue("saldo", BigDecimal.class);
                    
                    data.add(new MayorGeneradoConsolidado(
                            pfr.getNombre(),
                            saldo));
                });
        return data;              
    }
    
    public MayorGeneralDetalladoDataSource getMayorGeneralDetallado(Date d1, Date d2, List<Integer> profesionales){
        MayorGeneralDetalladoDataSource data = new MayorGeneralDetalladoDataSource();
        Map<Integer, BigDecimal> saldoInicialMap = getSaldoInicialForProfesionales(d1, profesionales);
        Set<Integer> profesionalesFound = new HashSet<>();
        profesionalesFound.addAll(profesionales);
        DSL.using(configuration())
                .select(
                        Tables.MAYORPROFESIONAL.ID_PROFESIONAL,
                        Tables.PROFESIONAL.NOMBRE,
                        Tables.MAYORPROFESIONAL.FECHA,
                        Tables.MAYORPROFESIONAL.ID_PRESUPUESTO,
                        Tables.PRESUPUESTO.PACIENTE,
                        Tables.TIPOOPERACION.NOMBRE,
                        DSL.decode()
                                .when(Tables.MAYORPROFESIONAL.LIQUIDACION.lessOrEqual(0),Tables.MAYORPROFESIONAL.OBSERVACIONES)
                                .otherwise(Routines.detalleliquidacion(Tables.MAYORPROFESIONAL.LIQUIDACION))
                                .as("observaciones"),
                        DSL.decode()
                                .when(Tables.MAYORPROFESIONAL.ID_PRESUPUESTO.eq(0), 
                                        Tables.MAYORPROFESIONAL.OBSERVACIONES).as("observacionesMayor"),
                        Tables.MAYORPROFESIONAL.DC,
                        Tables.MAYORPROFESIONAL.IMPORTE
                )
                .from(Tables.MAYORPROFESIONAL)
                .join(Tables.PROFESIONAL).on(Tables.PROFESIONAL.ID_PROFESIONAL.eq(Tables.MAYORPROFESIONAL.ID_PROFESIONAL))
                .leftJoin(Tables.PRESUPUESTO).on(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(Tables.MAYORPROFESIONAL.ID_PRESUPUESTO))
                .leftJoin(Tables.TIPOOPERACION).on(Tables.TIPOOPERACION.ID_TIPOOPERACION.eq(Tables.PRESUPUESTO.ID_TIPOOPERACION))
                .where(Tables.MAYORPROFESIONAL.ID_PROFESIONAL.in(profesionales))
                .and(Tables.MAYORPROFESIONAL.FECHA.greaterOrEqual(d1))
                .and(Tables.MAYORPROFESIONAL.FECHA.lessOrEqual(d2))
                .and(Tables.MAYORPROFESIONAL.PRELIQUIDACION.eq(0).or(Tables.MAYORPROFESIONAL.LIQUIDACION.greaterThan(0)))
                .orderBy(Tables.PROFESIONAL.NOMBRE,Tables.MAYORPROFESIONAL.FECHA)
                .fetch()
                .into((Record r)->{
                    MayorprofesionalRecord mpr = r.into(Tables.MAYORPROFESIONAL);
                    PresupuestoRecord p = r.into(Tables.PRESUPUESTO);
                    ProfesionalRecord prof = r.into(Tables.PROFESIONAL);
                    TipooperacionRecord op = r.into(Tables.TIPOOPERACION);
                    BigDecimal debe = mpr.getDc().equals("D") ? mpr.getImporte() : BigDecimal.ZERO;
                    BigDecimal haber = mpr.getDc().equals("C") ? mpr.getImporte() : BigDecimal.ZERO;
                    
                    profesionalesFound.remove(mpr.getIdProfesional());
                    
                    if (!Utiles.v(r.getValue("observaciones")).contains("Aplicacion manual de anticipos - ")) // HACKY PACKY - Esto es horrible!
                        data.add(
                                new MayorGeneralDetallado( 
                                        prof.getNombre(),
                                        p.getPaciente(),
                                        op.getNombre(),
                                        Utiles.v(r.getValue("observaciones")).concat(" ").concat(Utiles.v(r.getValue("observacionesMayor"))),
                                        Optional.fromNullable(saldoInicialMap.get(mpr.getIdProfesional())).or(BigDecimal.ZERO),
                                        debe,
                                        haber,
                                        mpr.getFecha(),
                                        mpr.getIdPresupuesto()
                                )
                        );
                });
        Map<Integer, ProfesionalRecord> map = CacheProfesional.instance().getProfesionales().intoMap(Tables.PROFESIONAL.ID_PROFESIONAL);
        profesionalesFound.forEach(r->{
            try{
                if (saldoInicialMap.get(r) != null && saldoInicialMap.get(r).compareTo(BigDecimal.ZERO) != 0){
                    data.add(
                            new MayorGeneralDetallado( 
                                    map.get(r).getNombre(),
                                    "",
                                    "",
                                    "",
                                    Optional.fromNullable(saldoInicialMap.get(r)).or(BigDecimal.ZERO),
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    d1,
                                    -1
                            )
                    );            
                } 
            }
            catch(Exception e){
                Utiles.showErrorMessage(e);
            }
        });
        return data;
    }
    
    public Map<Integer, BigDecimal> getSaldoInicialForProfesionales(Date d, List<Integer> profesionales){
        Map<Integer, BigDecimal> map = new HashMap<>();
        DSL.using(configuration())
                .select(
                        Tables.MAYORPROFESIONAL.ID_PROFESIONAL,
                        DSL.sum(
                            DSL.decode()
                                .when(Tables.MAYORPROFESIONAL.DC.eq("D"),Tables.MAYORPROFESIONAL.IMPORTE)
                                .otherwise(Tables.MAYORPROFESIONAL.IMPORTE.neg())
                        ).as("saldo"))
                .from(Tables.MAYORPROFESIONAL)
                .where(Tables.MAYORPROFESIONAL.ID_PROFESIONAL.in(profesionales))
                .and(Tables.MAYORPROFESIONAL.FECHA.lessThan(d))
                .and(Tables.MAYORPROFESIONAL.PRELIQUIDACION.eq(0).or(Tables.MAYORPROFESIONAL.LIQUIDACION.greaterThan(0)))
                .groupBy(Tables.MAYORPROFESIONAL.ID_PROFESIONAL)
                .fetch()
                .into((Record r)->{
                    map.put(r.into(Tables.MAYORPROFESIONAL).getIdProfesional(), 
                            Optional.fromNullable(r.getValue("saldo", BigDecimal.class))
                                    .or(BigDecimal.ZERO));
                });
        return map;
    }
    
    public MayorGeneralConsolidadoDataSource getMayorGeneralConsolidado(Date d1, Date d2, List<Integer> profesionales){
        
        MayorGeneralConsolidadoDataSource data = new MayorGeneralConsolidadoDataSource();
        try{
        Map<Integer, BigDecimal> saldoInicialMap = getSaldoInicialForProfesionales(d1, profesionales);
        Set<Integer> profesionalesFound = new HashSet<>();
        profesionalesFound.addAll(profesionales);
        DSL.using(configuration())
                .select(
                        Tables.PROFESIONAL.ID_PROFESIONAL,
                        Tables.PROFESIONAL.NOMBRE,
                        DSL.sum(DSL.decode()
                                .when(Tables.MAYORPROFESIONAL.DC.eq("D"),Tables.MAYORPROFESIONAL.IMPORTE)
                        ).as("debe"),
                        DSL.sum(DSL.decode()
                                .when(Tables.MAYORPROFESIONAL.DC.eq("C"),Tables.MAYORPROFESIONAL.IMPORTE)
                        ).as("haber")
                )
                .from(Tables.MAYORPROFESIONAL)
                .join(Tables.PROFESIONAL).on(Tables.PROFESIONAL.ID_PROFESIONAL.eq(Tables.MAYORPROFESIONAL.ID_PROFESIONAL))
                .where(Tables.MAYORPROFESIONAL.ID_PROFESIONAL.in(profesionales))
                .and(Tables.MAYORPROFESIONAL.FECHA.greaterOrEqual(d1))
                .and(Tables.MAYORPROFESIONAL.FECHA.lessOrEqual(d2))
                .and(Tables.MAYORPROFESIONAL.PRELIQUIDACION.eq(0).or(Tables.MAYORPROFESIONAL.LIQUIDACION.greaterThan(0)))
                .groupBy(Tables.MAYORPROFESIONAL.ID_PROFESIONAL)
                .fetch()
                .into((Record r)->{
                    ProfesionalRecord prof = r.into(Tables.PROFESIONAL);
                    BigDecimal debe = Optional.fromNullable(r.getValue("debe", BigDecimal.class))
                                                    .or(BigDecimal.ZERO);
                    BigDecimal haber = Optional.fromNullable(r.getValue("haber", BigDecimal.class))
                                                    .or(BigDecimal.ZERO);
                    
                    profesionalesFound.remove(prof.getIdProfesional());
                    data.add(
                            new MayorGeneralConsolidado( 
                                    prof.getNombre(),
                                    Optional.fromNullable(saldoInicialMap.get(prof.getIdProfesional()))
                                                    .or(BigDecimal.ZERO),
                                    debe,
                                    haber
                            )
                    );
                        
                });
        
        Map<Integer, ProfesionalRecord> map = CacheProfesional.instance().getProfesionales().intoMap(Tables.PROFESIONAL.ID_PROFESIONAL);
        profesionalesFound.forEach(r->{
            try{
                if (saldoInicialMap.get(r) != null && saldoInicialMap.get(r).compareTo(BigDecimal.ZERO) != 0){
                    data.add(
                            new MayorGeneralConsolidado( 
                                    map.get(r).getNombre(),
                                    saldoInicialMap.get(r),
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO
                            )
                    );            
                } 
            }
            catch(Exception e){
                Utiles.showErrorMessage(e);
            }
        });
        }
        catch(DataAccessException | IllegalArgumentException e){
            Utiles.showErrorMessage(e);
        }        
        return data;
    }
    
    public BigDecimal getRMbyFacturacion(Integer id_presupuesto, Integer typeComp){
        BigDecimal rm = null;
        switch(typeComp){
            case 1:
                //suma solo las entradas en MayorProfesional donde hay C en campo DC
                rm = DSL.using(configuration())
                        .select(DSL.sum(Tables.MAYORPROFESIONAL.IMPORTE))
                        .where(Tables.MAYORPROFESIONAL.DC.eq("C"))
                        .and(Tables.MAYORPROFESIONAL.ID_PRESUPUESTO.eq(id_presupuesto))
                        .fetchOneInto(BigDecimal.class);
                break;
            case 2:
                //suma solo las entradas en MayorProfesional donde hay D en campo DC y 0 en liquidacion(Ajustes por menor consumo)
                rm = DSL.using(configuration())
                        .select(DSL.sum(Tables.MAYORPROFESIONAL.IMPORTE))
                        .where(Tables.MAYORPROFESIONAL.DC.eq("D"))
                        .and(Tables.MAYORPROFESIONAL.LIQUIDACION.eq(0))
                        .and(Tables.MAYORPROFESIONAL.ID_PRESUPUESTO.eq(id_presupuesto))
                        .fetchOneInto(BigDecimal.class);
        }
        return rm;
    }
}
