/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.managers;

import matera.jooq.Tables;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class RemitoDetalleMgr {
    public static Result<Record> getRemitoDetalle(DSLContext dsl, Condition condition){
        return dsl.select().from(Tables.STOCKDETALLE)
                .join(Tables.REMITO).on(Tables.STOCKDETALLE.ID_REMITO.eq(Tables.REMITO.ID_REMITO))
                .join(Tables.PRODUCTO).on(Tables.STOCKDETALLE.ID_PRODUCTO.eq(Tables.PRODUCTO.ID_PRODUCTO))
                .join(Tables.CLASIPRODUCTO).on(Tables.PRODUCTO.ID_CLASIPRODUCTO.eq(Tables.CLASIPRODUCTO.ID_CLASIPRODUCTO))
                .where(condition)
                .fetch();
    }
    
    public static Result<Record> getEnviado(DSLContext dsl, Integer id_remito){
        
        Condition condition = 
                Tables.STOCKDETALLE.ID_REMITO.eq(id_remito).and(Tables.STOCKDETALLE.DC.eq("C"));
        
        return getRemitoDetalle(dsl, condition);
    }
}
