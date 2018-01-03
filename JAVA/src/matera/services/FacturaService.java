/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import ar.com.bosoft.conexion.ActiveDatabase;
import ar.com.bosoft.formularios.Principal;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import matera.jooq.Tables;
import matera.jooq.tables.records.FacturaRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

/**
 *
 * @author tinchogava
 */
public class FacturaService {
    
    public static boolean validForComisiones(Integer id_presupuesto){
        Calendar filterDate = Calendar.getInstance();
        filterDate.set(2017,5,1);
        return validForComisiones(id_presupuesto, filterDate, ActiveDatabase.getDSLContext());
    }
    public static BigDecimal getTotalFacturado(Integer id_presupuesto){
        return getTotalFacturado(id_presupuesto, ActiveDatabase.getDSLContext());
    }
    
    public static Result<FacturaRecord> getRecibosByPresupuesto(Integer id_presupuesto){
        return getRecibosByPresupuesto(id_presupuesto, ActiveDatabase.getDSLContext());
    }
    
    public static boolean isFactured(Integer id_presupuesto, java.util.Date d1, java.util.Date d2){
        java.sql.Date since = new java.sql.Date(d1.getTime());
        java.sql.Date until = new java.sql.Date(d2.getTime());
        return isFactured(id_presupuesto, since, until, ActiveDatabase.getDSLContext());
    }
    public static BigDecimal facturacionPrevia(Integer id_presupuesto, Date since){
       return facturacionPrevia(id_presupuesto, since, ActiveDatabase.getDSLContext());
    }
    
    public static Result<FacturaRecord> getFacturasByNotaDeCreditoAndFecha(Integer id_presupuesto, 
            java.util.Date d1, java.util.Date d2, double importe){
        java.sql.Date sinceDate = new java.sql.Date(d1.getTime());
        java.sql.Date untilDate = new java.sql.Date(d2.getTime());
        
        return getFacturasByNotaDeCreditoAndFecha(id_presupuesto, ActiveDatabase.getDSLContext(), sinceDate, untilDate, 
                new BigDecimal(importe));
    }

    private static Result<FacturaRecord> getRecibosByPresupuesto(Integer id_presupuesto, DSLContext dsl) {
        return dsl.select()
                .from(Tables.FACTURA)
                .where(Tables.FACTURA.ID_TIPOCOMP.in(Principal.TIPO_COMP.RECIBOS))
                .and(Tables.FACTURA.ID_PRESUPUESTO.eq(id_presupuesto))
                .fetchInto(Tables.FACTURA);
        }
    
    private static boolean isFactured(Integer id_presupuesto,Date since, Date until, DSLContext dsl){
        BigDecimal fact;
        BigDecimal notasCred;

        fact =  dsl
                    .select(DSL.sum(Tables.FACTURA.NETO))
                    .from(Tables.FACTURA)
                    .where(Tables.FACTURA.ID_PRESUPUESTO.eq(id_presupuesto)
                            .and(Tables.FACTURA.ID_TIPOCOMP.in(Principal.TIPO_COMP.FACTURAS))
                            .and(Tables.FACTURA.FECHA.greaterThan(since))
                            .and(Tables.FACTURA.FECHA.lessThan(until)))
                    .fetchOneInto(BigDecimal.class);

        notasCred = dsl
                    .select(DSL.sum(Tables.FACTURA.NETO))
                    .from(Tables.FACTURA)
                    .where(Tables.FACTURA.ID_PRESUPUESTO.eq(id_presupuesto)
                            .and(Tables.FACTURA.ID_TIPOCOMP.in(Principal.TIPO_COMP.NOTAS_CREDITO))
                            .and(Tables.FACTURA.FECHA.greaterThan(since))
                            .and(Tables.FACTURA.FECHA.lessThan(until)))
                    .fetchOneInto(BigDecimal.class);
        try{
            return fact.subtract(notasCred).compareTo(BigDecimal.ZERO) > 0;
        } catch (Exception e){
            return false;
        }
    }
    
    private static Result<FacturaRecord> getFacturasByNotaDeCreditoAndFecha(Integer id_presupuesto, DSLContext dsl, 
            java.sql.Date sinceDate, java.sql.Date untilDate, BigDecimal neto){
        
        return dsl.select()
                .from(Tables.FACTURA)
                .where(Tables.FACTURA.ID_PRESUPUESTO.eq(id_presupuesto))
                .and(Tables.FACTURA.ID_TIPOCOMP.in(Principal.TIPO_COMP.NOTAS_CREDITO))
                .and(Tables.FACTURA.FECHA.between(sinceDate, untilDate))
                .and(Tables.FACTURA.NETO.eq(neto))
                .fetchInto(Tables.FACTURA);
    }
    
    private static BigDecimal facturacionPrevia(Integer id_presupuesto, Date since, DSLContext dsl){
        BigDecimal plus, minus; 
        try{
            plus = dsl
                    .select(DSL.sum(Tables.FACTURA.NETO))
                    .where(Tables.FACTURA.ID_PRESUPUESTO.eq(id_presupuesto)
                            .and(Tables.FACTURA.ID_TIPOCOMP.notIn(Principal.TIPO_COMP.NOTAS_CREDITO))
                            .and(Tables.FACTURA.FECHA.lessOrEqual(since)))
                    .fetchOneInto(BigDecimal.class);

            minus = dsl
                    .select(DSL.sum(Tables.FACTURA.NETO))
                    .where(Tables.FACTURA.ID_PRESUPUESTO.eq(id_presupuesto)
                            .and(Tables.FACTURA.ID_TIPOCOMP.in(Principal.TIPO_COMP.NOTAS_CREDITO))
                            .and(Tables.FACTURA.FECHA.lessOrEqual(since)))
                    .fetchOneInto(BigDecimal.class);
            BigDecimal total = plus.subtract(minus);
            return total;
        } catch(DataAccessException e){
            return BigDecimal.valueOf(-1);
        } 
    }
    
    private static BigDecimal getTotalFacturado(Integer id_presupuesto, DSLContext dsl){
        return dsl
                .select(DSL.sum(Tables.FACTURA.NETO))
                .from(Tables.FACTURA)
                .where(Tables.FACTURA.ID_PRESUPUESTO.eq(id_presupuesto)
                        .and(Tables.FACTURA.ID_TIPOCOMP.in(Principal.TIPO_COMP.FACTURAS)))       
                .fetchOneInto(BigDecimal.class);
    }

    private static boolean validForComisiones(Integer id_presupuesto, Calendar filterDate, DSLContext dsl) {
        java.sql.Date comisionesDate = new java.sql.Date(filterDate.getTime().getTime());
        try{
            Date facturaDate = dsl.select(Tables.FACTURA.FECHA).
                                        from(Tables.FACTURA)
                                        .where(Tables.FACTURA.ID_PRESUPUESTO.eq(id_presupuesto))
                                        .and(Tables.FACTURA.ID_TIPOCOMP.in(Principal.TIPO_COMP.FACTURAS))
                                        .orderBy(Tables.FACTURA.FECHA.desc())
                                        .limit(1)
                                        .fetchOne(Tables.FACTURA.FECHA);
            return facturaDate.after(comisionesDate);
        } catch(NullPointerException e){
            return false;
        }
            
    }
}
