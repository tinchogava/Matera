/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.TableObjects;

import matera.jooq.Tables;
import matera.jooq.tables.records.LiquidacionOlpRecord;
import matera.jooq.tables.records.LiquidacionRecord;
import matera.jooq.tables.records.TrackingolpRecord;
import org.jooq.Record;

/**
 *
 * @author tinchogava
 */
public class TrackingOLPTableObject {
    TrackingolpRecord trackingolp;
    LiquidacionOlpRecord liquidacionolp;
    LiquidacionRecord liquidacion;

    public TrackingOLPTableObject(TrackingolpRecord trackingolp, LiquidacionOlpRecord liquidacionolp,
            LiquidacionRecord liquidacion) {
        this.trackingolp = trackingolp;
        this.liquidacionolp = liquidacionolp;
        this.liquidacion = liquidacion;
    }

    public TrackingOLPTableObject(Record r) {
        this.trackingolp = r.into(Tables.TRACKINGOLP);
        this.liquidacionolp = r.into(Tables.LIQUIDACION_OLP);
        this.liquidacion = r.into(Tables.LIQUIDACION);
    }
    
    public TrackingolpRecord getTrackingOLP() {
        return trackingolp;
    }

    public void setTrackingOLP(TrackingolpRecord trackingolp) {
        this.trackingolp = trackingolp;
    }

    public LiquidacionOlpRecord getLiquidacionolp() {
        return liquidacionolp;
    }

    public void setLiquidacionolp(LiquidacionOlpRecord liquidacionolp) {
        this.liquidacionolp = liquidacionolp;
    }

    public LiquidacionRecord getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(LiquidacionRecord liquidacion) {
        this.liquidacion = liquidacion;
    }
}

    

