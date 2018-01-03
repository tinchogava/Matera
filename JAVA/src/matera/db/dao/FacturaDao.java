/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.dao;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.math.BigDecimal;
import java.sql.Date;
import matera.jooq.Tables;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

/**
 *
 * @author h2o
 */
public class FacturaDao extends matera.jooq.tables.daos.FacturaDao {
    public FacturaDao(){
        super(ActiveDatabase.getDSLContext().configuration());
    }
    
    public BigDecimal getTotalFacturadoBetweenDatesForZona(Date d1, Date d2, Integer id_zona){
        BigDecimal bd =  DSL.using(configuration())
                .select(
                        DSL.sum(
                            DSL.decode()
                                .when(Tables.FACTURA.DC.eq("D"),Tables.FACTURA.NETO.add(Tables.FACTURA.IVA).add(Tables.FACTURA.PERCIIBB))
                                .otherwise(Tables.FACTURA.NETO.add(Tables.FACTURA.IVA).add(Tables.FACTURA.PERCIIBB).neg())
                        )
                ).from(Tables.FACTURA)
                .join(Tables.PRESUPUESTO).on(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(Tables.FACTURA.ID_PRESUPUESTO).and(Tables.PRESUPUESTO.ID_ZONA.eq(id_zona))) 
                .where(Tables.FACTURA.FECHA.greaterOrEqual(d1))
                .and(Tables.FACTURA.FECHA.lessOrEqual(d2))
                .and(Tables.FACTURA.ID_TIPOCOMP.notIn(TipocompDao.getRecibosIdList()))
                .fetchOneInto(BigDecimal.class);
        if(bd == null)
            return BigDecimal.ZERO;
        return bd;
    }
    
    public Integer verifyComp(Integer id_presupuesto, Integer typeComp){
        Integer count = null;
        switch(typeComp) {
            case 1: 
                //Si hay solo Notas de Credito retorna 0
                count = DSL.using(configuration())
                        .fetchCount(DSL.selectFrom(Tables.FACTURA).where(Tables.FACTURA.ID_TIPOCOMP.in(1, 5, 8, 11, 23)));
                break;
            case 2:
                //Si hay solo Facturas retorna 0
                count = DSL.using(configuration())
                        .fetchCount(DSL.selectFrom(Tables.FACTURA).where(Tables.FACTURA.ID_TIPOCOMP.in(3, 7, 10, 13, 21, 25)));
                break;
        }   
        return count;
    }
    
    public BigDecimal analisisFacturacion(Integer id_presupuesto, Date since){
        BigDecimal plus, minus; 
        try{
            plus = DSL.using(configuration())
                    .select(DSL.sum(Tables.FACTURA.NETO))
                    .where(Tables.FACTURA.DC.eq("D"))
                            .and(Tables.FACTURA.FECHA.lessThan(since))
                    .fetchOneInto(BigDecimal.class);

            minus = DSL.using(configuration())
                    .select(DSL.sum(Tables.FACTURA.NETO))
                    .where(Tables.FACTURA.DC.eq("C"))
                            .and(Tables.FACTURA.FECHA.lessThan(since))
                    .fetchOneInto(BigDecimal.class);
            
            return plus.subtract(minus);
        } catch(DataAccessException e){
            return BigDecimal.ZERO;
        }
    }
}
