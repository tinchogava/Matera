/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.math.BigDecimal;
import matera.db.dao.FacturaDao;
import matera.db.dao.MayorprofesionalDao;
import matera.jooq.Tables;
import matera.jooq.tables.records.MayorprofesionalRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

/**
 *
 * @author tinchogava
 */
public class RmService {
    
    public static double getRmPagado(Integer id_presupuesto){
        //try{
            return getRmPagado(id_presupuesto, ActiveDatabase.getDSLContext());
        //} catch (Exception e) {
        //    return 0.00;
        //}
    }
    
    public static double getRMatMayor(Integer id_presupuesto, java.util.Date sinceDate, double rmFacturaAntes){
        FacturaDao facturaDao = new FacturaDao();
        MayorprofesionalDao mayorProfesionalDao = new MayorprofesionalDao();
        BigDecimal rm = BigDecimal.ZERO;
        rm.setScale(2, BigDecimal.ROUND_HALF_UP);
        java.sql.Date since = new java.sql.Date(sinceDate.getTime());
        double returnValue = 0.00;
        
        if(facturaDao.verifyComp(id_presupuesto, 1).equals(0)){
            if(FacturaService.facturacionPrevia(id_presupuesto, since).equals(0)){
                returnValue = mayorProfesionalDao.getRMbyFacturacion(id_presupuesto, 1).doubleValue();
            } 
        } else if(facturaDao.verifyComp(id_presupuesto, 2).equals(0)){
            returnValue = mayorProfesionalDao.getRMbyFacturacion(id_presupuesto, 2).doubleValue();
        } else {
            returnValue = rmFacturaAntes;
        }
        
        return returnValue;
    }
    
    public static BigDecimal getRMbyTotal(Integer id_presupuesto, double totalPresup){
        BigDecimal totalRecibos = new BigDecimal(0);
        BigDecimal totalPresupuesto = new BigDecimal(totalPresup);
        FacturaService.getRecibosByPresupuesto(id_presupuesto).forEach(rcb->{
            totalRecibos.add(rcb.getNeto());
        });
        
        if(!totalPresupuesto.equals(totalRecibos)){
            if(!MayorProfesionalService.getRmByDiferencia(id_presupuesto, totalPresupuesto.compareTo(totalRecibos)).isEmpty()){
            MayorprofesionalRecord mayorprofesionalRecord = MayorProfesionalService
                    .getRmByDiferencia(id_presupuesto, totalPresupuesto.compareTo(totalRecibos)).get(0);
            
            if(mayorprofesionalRecord.getDc().equals("C")){
                return mayorprofesionalRecord.getImporte().negate();
            } else {
                return mayorprofesionalRecord.getImporte();
            }
            } else {
                return BigDecimal.ZERO;
            }    
        } else {
            return BigDecimal.ZERO;  
        }
    }

    private static double getRmPagado(Integer id_presupuesto, DSLContext dsl) {
        return dsl.select(DSL.sum(Tables.MAYORPROFESIONAL.IMPORTE))
                .from(Tables.MAYORPROFESIONAL)
                .where(Tables.MAYORPROFESIONAL.ID_PRESUPUESTO.eq(id_presupuesto))
                .and(Tables.MAYORPROFESIONAL.DC.eq("D"))
                .and(Tables.MAYORPROFESIONAL.LIQUIDACION.greaterThan(0))
                .fetchOneInto(double.class);
        }
}
