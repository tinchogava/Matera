/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.managers;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.util.ArrayList;
import java.util.List;
import matera.TableObjects.TrackingOLPTableObject;
import matera.jooq.Tables;
import org.jooq.DSLContext;
import org.jooq.Record;

/**
 *
 * @author tinchogava
 */
public class TrackingOLPMgr {
    public static List<TrackingOLPTableObject> getTableTrackingOLP(){
        return getTrackingOLP(ActiveDatabase.getDSLContext());
    }

    private static List<TrackingOLPTableObject> getTrackingOLP(DSLContext dsl) {
        List<TrackingOLPTableObject> list = new ArrayList<>();
        dsl.select()
                .from(Tables.TRACKINGOLP)
                .leftJoin(Tables.LIQUIDACION_OLP)
                .on(Tables.LIQUIDACION_OLP.ID.eq(Tables.TRACKINGOLP.ID_LIQUIDACION))
                .leftJoin(Tables.LIQUIDACION)
                .on(Tables.LIQUIDACION.ID_LIQUIDACION.eq(Tables.LIQUIDACION_OLP.ID))
                .fetch()
                    .into((Record r1) -> {
                    list.add(
                            new TrackingOLPTableObject(r1)
                            );
                    });
        return list;    
    }
    
}
