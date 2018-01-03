/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.dao;

import ar.com.bosoft.DataSources.CostoVentasDataSource;
import ar.com.bosoft.Modelos.CostoVentas;
import ar.com.bosoft.conexion.ActiveDatabase;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import matera.db.dto.EventoPresupuestoDTO;
import matera.jooq.tables.pojos.LogEventoPresupuesto;
import matera.jooq.tables.pojos.LogEventoTipo;
import matera.jooq.tables.pojos.Usuario;
import matera.jooq.Routines;
import matera.jooq.Tables;
import matera.jooq.tables.records.CotizacionRecord;
import matera.services.CostoVentasService;
import matera.services.RmService;
import org.hibernate.type.IntegerType;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import static org.jooq.impl.DSL.*;



/**
 *
 * @author h2o
 */
public class PresupuestoDao extends matera.jooq.tables.daos.PresupuestoDao {
    Result<CotizacionRecord> cotizacionByFecha;
    CotizacionRecord cr;
    double cotizacion;
    public PresupuestoDao(){
        super(ActiveDatabase.getDSLContext().configuration());
    }
    
    /*
    Monto presupuestado de presupuestos aprobados sin fecha de cirugia 
    de la zona ID_ZONA
    */
    public Double getMontoPresupuestadoAprobadoSinFechaCirugiaForZona(Integer id_zona){
        String[] estados = {"A", "C","S"};
        return DSL.using(configuration())
                .select(
                        DSL.sum(
                                Routines.montopresupuesto(Tables.PRESUPUESTO.ID_PRESUPUESTO)
                                        .sub(Routines.montofacturado(Tables.PRESUPUESTO.ID_PRESUPUESTO))))
                .from(Tables.PRESUPUESTO)
                .where(Tables.PRESUPUESTO.ESTADO.in(estados))
                .and(Tables.PRESUPUESTO.FECHACIRUGIA.isNull())
                .and(Tables.PRESUPUESTO.ID_ZONA.eq(id_zona))
                .fetchOneInto(BigDecimal.class).doubleValue();
    }
    
    /*
    Monto presupuestado de presupuestos confirmados con fecha de cirugia entre las
    fechas d1 y d2 para la zona ID_ZONA
    */
    public Double getMontoPresupuestadoConfirmadoConFechaCirugiaBetweenDatesForZona(Date d1, Date d2, Integer id_zona){
        return DSL.using(configuration())
                .select(
                        DSL.sum(
                                Routines.montopresupuesto(Tables.PRESUPUESTO.ID_PRESUPUESTO)
                                        .sub(Routines.montofacturado(Tables.PRESUPUESTO.ID_PRESUPUESTO))))
                .from(Tables.PRESUPUESTO)
                .where(Tables.PRESUPUESTO.ESTADO.eq("C"))
                .and(Tables.PRESUPUESTO.FECHACIRUGIA.isNotNull())
                .and(Tables.PRESUPUESTO.ID_ZONA.eq(id_zona))
                .fetchOneInto(BigDecimal.class).doubleValue();
    }    
    
    /*
    Monto presupuestado pendiente de presupuestos VIP para la zona id_zona
    */
    public Double getMontoPresupuestadoPendientesVipForZona(Integer id_zona){
        return DSL.using(configuration())
                .select(
                        DSL.sum(
                                Routines.montopresupuesto(Tables.PRESUPUESTO.ID_PRESUPUESTO)))
                .from(Tables.PRESUPUESTO)
                .where(Tables.PRESUPUESTO.ESTADO.eq("P"))
                .and(Tables.PRESUPUESTO.ID_ZONA.eq(id_zona))
                .and(Tables.PRESUPUESTO.FECHACIRUGIA.isNull())
                .and(Tables.PRESUPUESTO.VIP.eq("S"))
                .fetchOneInto(BigDecimal.class).doubleValue();
    }
    
    /*
    Costo de venta entre las fechas d1 y d2 para la zona id_zona
    */
    public Double getCostoVentaBetweenDatesForZona(Date d1, Date d2, Integer id_zona){
        BigDecimal bd = DSL.using(configuration())
                .select(
                        DSL.sum(
                                Tables.STOCKDETALLE.COSTOPESOS))
                .from(Tables.PRESUPUESTO)
                .leftJoin(Tables.REMITO)
                    .on(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(Tables.REMITO.ID_PRESUPUESTO))
                .leftJoin(Tables.REMITO_CONSUMIDO)
                    .on(Tables.REMITO_CONSUMIDO.ID_REMITO.eq(Tables.REMITO.ID_REMITO))
                .leftJoin(Tables.STOCKDETALLE)
                    .on(Tables.REMITO_CONSUMIDO.ID_STOCKDETALLE.eq(Tables.STOCKDETALLE.ID_STOCKDETALLE))
                .where(Tables.PRESUPUESTO.ESTADO.eq("Z"))
                .and(Tables.PRESUPUESTO.ID_ZONA.eq(id_zona))
                .and(Tables.REMITO.FECHACONSUMIDO.greaterOrEqual(d1))
                .and(Tables.REMITO.FECHACONSUMIDO.lessOrEqual(d2))                
                .fetchOneInto(BigDecimal.class);
        if (bd == null)
            return 0.00;
        return bd.doubleValue();
    }
    
    /*
    Monto pendiente de facturacion de presupuestos finalizados
    */
    public Double getPendienteFacturacionFinalizadoBetweenDatesForZona(Date d1, Date d2, Integer id_zona){
        return DSL.using(configuration())
                .select(
                        DSL.sum(
                                Routines.montopresupuesto(Tables.PRESUPUESTO.ID_PRESUPUESTO)
                                        .sub(Routines.montofacturado(Tables.PRESUPUESTO.ID_PRESUPUESTO))))
                .from(Tables.PRESUPUESTO)
                .where(Tables.PRESUPUESTO.ESTADO.eq("Z"))
                .and(Tables.PRESUPUESTO.ID_ZONA.eq(id_zona))
                .and(Tables.PRESUPUESTO.DESACTIVADO.eq("N"))
                .fetchOneInto(BigDecimal.class).doubleValue();
    }
    
    public List<EventoPresupuestoDTO> getEventos(Integer id){
        
        List<EventoPresupuestoDTO> list = new ArrayList<>();
        DSL.using(configuration())
                .select()
                .from(Tables.PRESUPUESTO)
                .join(Tables.LOG_EVENTO_PRESUPUESTO).on(Tables.LOG_EVENTO_PRESUPUESTO.ID_PRESUPUESTO.eq(Tables.PRESUPUESTO.ID_PRESUPUESTO))
                .join(Tables.LOG_EVENTO_TIPO).on(Tables.LOG_EVENTO_TIPO.ID.eq(Tables.LOG_EVENTO_PRESUPUESTO.ID_LOG_EVENTO_TIPO))
                .join(Tables.USUARIO).on(Tables.LOG_EVENTO_PRESUPUESTO.ID_USUARIO.eq(Tables.USUARIO.ID_USUARIO))
                .where(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(id))
                .orderBy(Tables.LOG_EVENTO_PRESUPUESTO.TIMESTAMP)
                .fetch()
                .into((Record r)->{
                    list.add(new EventoPresupuestoDTO(
                            r.into(Tables.LOG_EVENTO_PRESUPUESTO).into(LogEventoPresupuesto.class),
                            r.into(Tables.LOG_EVENTO_TIPO).into(LogEventoTipo.class),
                            r.into(Tables.USUARIO).into(Usuario.class)));
                    
                });
        return list;
    }    

    public CostoVentasDataSource getCostoVentas(Integer date_type, Date d1, Date d2, Integer id_proveedor, Integer id_zona){
        CostoVentasDataSource data = new CostoVentasDataSource(); 
        Calendar calendar = Calendar.getInstance();
        SelectConditionStep step = DSL.using(configuration())
                .select(
                        Tables.PRESUPUESTO.ID_PRESUPUESTO,
                        Tables.PRESUPUESTO.COSTO_VENTA,
                        Tables.STOCKDETALLE.ID_STOCKDETALLE,
                        Tables.STOCKDETALLE.CANTIDAD,
                        Tables.STOCKDETALLE.COSTOPESOS,
                        Tables.VENDEDOR.NOMBRE,
                        Tables.PRODUCTO.CODIGO,
                        Tables.TIPOOPERACION.NOMBRE,
                        Tables.REMITO.FECHACONSUMIDO,
                        Tables.FORMAPAGO.NOMBRE,
                        Tables.PRESUPUESTO.TOTAL,
                        Tables.PRESUPUESTO.RM1,
                        Tables.PRESUPUESTO.RM2,
                        Tables.PRESUPUESTO.RM3,
                        Tables.PRESUPUESTO.RM4,
                        Tables.PRESUPUESTO.RM5
                )
                
                .from(Tables.PRESUPUESTO)
                .join(Tables.REMITO).on(Tables.REMITO.ID_PRESUPUESTO.eq(Tables.PRESUPUESTO.ID_PRESUPUESTO))
                .join(Tables.REMITO_CONSUMIDO).on(Tables.REMITO_CONSUMIDO.ID_REMITO.eq(Tables.REMITO.ID_REMITO))
                .join(Tables.STOCKDETALLE).on(Tables.STOCKDETALLE.ID_STOCKDETALLE.eq(Tables.REMITO_CONSUMIDO.ID_STOCKDETALLE))
                .join(Tables.TIPOOPERACION).on(Tables.TIPOOPERACION.ID_TIPOOPERACION.eq(Tables.PRESUPUESTO.ID_TIPOOPERACION))
                .join(Tables.VENDEDOR).on(Tables.VENDEDOR.ID_VENDEDOR.eq(Tables.PRESUPUESTO.ID_VENDEDOR))
                .join(Tables.PRODUCTO).on(Tables.PRODUCTO.ID_PRODUCTO.eq(Tables.STOCKDETALLE.ID_PRODUCTO))
                .join(Tables.ENTIDAD).on(Tables.ENTIDAD.ID_ENTIDAD.eq(Tables.PRESUPUESTO.ID_ENTIDAD))
                .join(Tables.FORMAPAGO).on(Tables.FORMAPAGO.ID_FORMAPAGO.eq(Tables.ENTIDAD.ID_FORMAPAGO))
                .where(Tables.PRESUPUESTO.ESTADO.eq("Z"));
               
                if (id_zona > IntegerType.ZERO) 
                    step.and(Tables.PRESUPUESTO.ID_ZONA.eq(id_zona));
                
                if (d1 != null){
                    switch(date_type){
                        case CostoVentasService.BY_FECHA_CIRUGIA:
                            step.and(Tables.PRESUPUESTO.FECHACIRUGIA.greaterOrEqual(d1));
                            break;
                        case CostoVentasService.BY_FECHA_CONSUMO:
                            step.and(Tables.REMITO.FECHACONSUMIDO.greaterOrEqual(d1));
                            break;
                            
                    }                
                }

                if (d2 != null){
                    switch(date_type){
                        case CostoVentasService.BY_FECHA_CIRUGIA:
                            step.and(Tables.PRESUPUESTO.FECHACIRUGIA.lessOrEqual(d2));
                            break;
                        case CostoVentasService.BY_FECHA_CONSUMO:
                            step.and(Tables.REMITO.FECHACONSUMIDO.lessOrEqual(d2));
                            break;      
                    }                
                }
                
                if (id_proveedor > 0)
                    step.and(Tables.PRODUCTO.ID_PROVEEDOR.eq(id_proveedor));               
                step.fetch()
                .into(new RecordHandler() {
            @Override
            public void next(Record r1) {
                calendar.setTime(r1.getValue(Tables.REMITO.FECHACONSUMIDO));
                
                Integer mes = ((calendar.get(Calendar.MONTH) - 1 + 1) % 12) + 1;
                Integer año = calendar.get(Calendar.YEAR);
                Condition cotizacionCondition = Tables.COTIZACION.MES.eq(mes);
                cotizacionCondition.and(Tables.COTIZACION.AÑO.eq(año));
                if(((mes.compareTo(6))< 0) && (año.equals(2015))){
                    cotizacionByFecha = DSL.using(configuration()).select()
                            .from(Tables.COTIZACION)
                            .orderBy(Tables.COTIZACION.FECHAREAL.asc())
                            .limit(1)
                            .fetchInto(Tables.COTIZACION);  
                    cotizacion = cotizacionByFecha.get(0).getValor().doubleValue();  
                } else if ((mes.compareTo(3) > 0) &&(año.equals(2016))){
                    cotizacionByFecha = DSL.using(configuration()).select()
                            .from(Tables.COTIZACION)
                            .orderBy(Tables.COTIZACION.FECHAREAL.desc())
                            .limit(1)
                            .fetchInto(Tables.COTIZACION);  
                    cotizacion = cotizacionByFecha.get(0).getValor().doubleValue();  
                } else {
                    cotizacionByFecha = DSL.using(configuration()).select()
                            .from(Tables.COTIZACION)
                            .where(cotizacionCondition)
                            .orderBy(Tables.COTIZACION.FECHAREAL.desc())
                            .limit(1)
                            .fetchInto(Tables.COTIZACION);  
                    cotizacion = cotizacionByFecha.get(0).getValor().doubleValue();  
                }
                try{                 
                    CostoVentas costoVentas = new CostoVentas(
                            r1.getValue(Tables.PRESUPUESTO.ID_PRESUPUESTO),
                            r1.getValue(Tables.STOCKDETALLE.CANTIDAD),
                            r1.getValue(Tables.TIPOOPERACION.NOMBRE),
                            r1.getValue(Tables.VENDEDOR.NOMBRE),
                            r1.getValue(Tables.PRODUCTO.CODIGO),
                            r1.getValue(Tables.STOCKDETALLE.COSTOPESOS),
                            r1.getValue(Tables.STOCKDETALLE.COSTOPESOS),
                            r1.getValue(Tables.PRESUPUESTO.COSTO_VENTA),
                            cotizacion,
                            r1.getValue(Tables.FORMAPAGO.NOMBRE),
                            r1.getValue(Tables.PRESUPUESTO.TOTAL),
                            r1.getValue(Tables.PRESUPUESTO.RM1)
                                    .add(r1.getValue(Tables.PRESUPUESTO.RM2))
                                    .add(r1.getValue(Tables.PRESUPUESTO.RM3))
                                    .add(r1.getValue(Tables.PRESUPUESTO.RM4))
                                    .add(r1.getValue(Tables.PRESUPUESTO.RM5))
                    );
                    data.add(costoVentas);    
                }
                catch(Exception e){}
            }
        });
                
        return data;
    }
    
    public CostoVentasDataSource getCostoVentasByPresupuesto(Integer id_presupuesto){
        CostoVentasDataSource data = new CostoVentasDataSource(); 
        Calendar calendar = Calendar.getInstance();
        SelectConditionStep step = DSL.using(configuration())
                .select(
                        Tables.PRESUPUESTO.ID_PRESUPUESTO,
                        Tables.PRESUPUESTO.COSTO_VENTA,
                        Tables.STOCKDETALLE.CANTIDAD,
                        Tables.STOCKDETALLE.COSTOPESOS,
                        Tables.PRODUCTO.CODIGO,
                        Tables.VENDEDOR.NOMBRE,
                        Tables.REMITO.FECHACONSUMIDO
                )
                .from(Tables.PRESUPUESTO)
                .join(Tables.REMITO).on(Tables.REMITO.ID_PRESUPUESTO.eq(Tables.PRESUPUESTO.ID_PRESUPUESTO))
                .join(Tables.REMITO_CONSUMIDO).on(Tables.REMITO_CONSUMIDO.ID_REMITO.eq(Tables.REMITO.ID_REMITO))
                .join(Tables.STOCKDETALLE).on(Tables.REMITO_CONSUMIDO.ID_STOCKDETALLE.eq(Tables.STOCKDETALLE.ID_STOCKDETALLE))
                .join(Tables.TIPOOPERACION).on(Tables.TIPOOPERACION.ID_TIPOOPERACION.eq(Tables.PRESUPUESTO.ID_TIPOOPERACION))
                .join(Tables.VENDEDOR).on(Tables.VENDEDOR.ID_VENDEDOR.eq(Tables.PRESUPUESTO.ID_VENDEDOR))
                .join(Tables.PRODUCTO).on(Tables.PRODUCTO.ID_PRODUCTO.eq(Tables.STOCKDETALLE.ID_PRODUCTO))
                .where(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(id_presupuesto));
        
                step.fetch()
                .into(new RecordHandler() {
            @Override
            public void next(Record r1) {
                calendar.setTime(r1.getValue(Tables.REMITO.FECHACONSUMIDO));
                
                Integer mes = ((calendar.get(Calendar.MONTH) - 1 + 1) % 12) + 1;
                Integer año = calendar.get(Calendar.YEAR);
                Condition cotizacionCondition = Tables.COTIZACION.MES.eq(mes);
                cotizacionCondition.and(Tables.COTIZACION.AÑO.eq(año));
                if(((mes.compareTo(6))< 0) && (año.equals(2015))){
                    cotizacionByFecha = DSL.using(configuration()).select()
                            .from(Tables.COTIZACION)
                            .orderBy(Tables.COTIZACION.FECHAREAL.asc())
                            .limit(1)
                            .fetchInto(Tables.COTIZACION);  
                    cotizacion = cotizacionByFecha.get(0).getValor().doubleValue();  
                } else if ((mes.compareTo(3) > 0) &&(año.equals(2016))){
                    cotizacionByFecha = DSL.using(configuration()).select()
                            .from(Tables.COTIZACION)
                            .orderBy(Tables.COTIZACION.FECHAREAL.desc())
                            .limit(1)
                            .fetchInto(Tables.COTIZACION);  
                    cotizacion = cotizacionByFecha.get(0).getValor().doubleValue();  
                } else {
                    cotizacionByFecha = DSL.using(configuration()).select()
                            .from(Tables.COTIZACION)
                            .where(cotizacionCondition)
                            .orderBy(Tables.COTIZACION.FECHAREAL.desc())
                            .limit(1)
                            .fetchInto(Tables.COTIZACION);  
                    cotizacion = cotizacionByFecha.get(0).getValor().doubleValue();  
                }
                try{                 
                    CostoVentas costoVentas = new CostoVentas(
                            r1.getValue(Tables.PRESUPUESTO.ID_PRESUPUESTO),
                            r1.getValue(Tables.STOCKDETALLE.CANTIDAD),
                            r1.getValue(Tables.TIPOOPERACION.NOMBRE),
                            r1.getValue(Tables.VENDEDOR.NOMBRE),
                            r1.getValue(Tables.PRODUCTO.CODIGO),
                            r1.getValue(Tables.STOCKDETALLE.COSTOPESOS),
                            r1.getValue(Tables.STOCKDETALLE.COSTOPESOS),
                            r1.getValue(Tables.PRESUPUESTO.COSTO_VENTA),
                            cotizacion                       
                    );
                    data.add(costoVentas);    
                }
                catch(Exception e){}
            }
        });
                
        return data;
    }
}
