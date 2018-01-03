/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.services;

import ar.com.bosoft.DataSources.MayorGeneradoConsolidadoDataSource;
import ar.com.bosoft.DataSources.MayorGeneralConsolidadoDataSource;
import ar.com.bosoft.DataSources.MayorGeneradoDetalladoDataSource;
import ar.com.bosoft.DataSources.MayorGeneralDetalladoDataSource;
import ar.com.bosoft.conexion.ActiveDatabase;
import java.sql.Date;
import java.util.List;
import matera.db.dao.MayorprofesionalDao;
import matera.jooq.Tables;
import matera.jooq.tables.records.MayorprofesionalRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;

/**
 *
 * @author h2o
 */
public class MayorProfesionalService {
    public static MayorGeneradoDetalladoDataSource getMayorGeneradoDetallado(Date d1, Date d2, List<Integer> profesionales){
        return new MayorprofesionalDao().getMayorGeneradoDetallado(d1,d2,profesionales);
    }
    
    public static MayorGeneradoConsolidadoDataSource getMayorGeneradoConsolidado(Date d1, Date d2, List<Integer> profesionales){
        return new MayorprofesionalDao().getMayorGeneradoConsolidado(d1,d2,profesionales);
    }
    
    public static MayorGeneralDetalladoDataSource getMayorGeneralDetallado(Date d1, Date d2, List<Integer> profesionales){
        return new MayorprofesionalDao().getMayorGeneralDetallado(d1,d2,profesionales);
    }
    
    public static MayorGeneralConsolidadoDataSource getMayorGeneralConsolidado(Date d1, Date d2, List<Integer> profesionales){
        return new MayorprofesionalDao().getMayorGeneralConsolidado(d1,d2,profesionales);
    }    
    
    public static Result<MayorprofesionalRecord> getRmByDiferencia(Integer id_presupuesto, Integer rest){
        Condition cndtn = null;
        
        switch(rest){
            case -1:
                cndtn = Tables.MAYORPROFESIONAL.DC.eq("D");
                break;
            case 1:
                cndtn = Tables.MAYORPROFESIONAL.DC.eq("C");
        }
        
        return getRmByDiferencia(id_presupuesto, cndtn, ActiveDatabase.getDSLContext());
    }
    
    private static Result<MayorprofesionalRecord> getRmByDiferencia(Integer id_presupuesto, Condition cndtn, DSLContext dsl){
        
        
        return dsl
                .select()
                .from(Tables.MAYORPROFESIONAL)
                .where(Tables.MAYORPROFESIONAL.ID_PRESUPUESTO.eq(id_presupuesto))
                .and(Tables.MAYORPROFESIONAL.LIQUIDACION.lessOrEqual(0))
                .and(cndtn)
                .fetchInto(Tables.MAYORPROFESIONAL);
    }
    
}
