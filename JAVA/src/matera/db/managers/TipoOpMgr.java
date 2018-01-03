/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.managers;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.TipooperacionRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class TipoOpMgr {
    
    public static Result<TipooperacionRecord> getTipoOp(){
        return getTipoOp(ActiveDatabase.getDSLContext(),null);
    }
    
    public static Result<TipooperacionRecord> getTipoOp(DSLContext dsl, Condition condition){
        return dsl.select().from(Tables.TIPOOPERACION)
                .where(condition)
                .fetch()
                .into(Tables.TIPOOPERACION);
}
    
    public static void storeOp(TipooperacionRecord tipoOpRecord){
       tipoOpRecord.store();
       }
    
}
