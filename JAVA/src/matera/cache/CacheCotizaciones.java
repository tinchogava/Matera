/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.math.BigDecimal;
import java.util.Date;
import matera.jooq.Tables;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheCotizaciones implements CacheRecords {
    
    String key = "cotizaciones";
    private static CacheCotizaciones instance = null;
    
    protected CacheCotizaciones() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheCotizaciones instance() {
        if(instance == null) {
           synchronized(CacheCotizaciones.class) { 
              instance = new CacheCotizaciones();
           }
        }
        return instance;
    }    
    
    @Override
    public Result<Record> get() {
        Result<Record> records = CacheMgr.getCache().get(key);
        if (records == null){
            fetch();
        }
        return CacheMgr.getCache().get(key);
    }

    @Override
    public Result<Record> getHabilitados() {
        return get();
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.COTIZACION)
                    .join(Tables.MONEDA).on(Tables.MONEDA.ID_MONEDA.eq(Tables.COTIZACION.ID_MONEDA))
                    .fetch());  
    }
    
    public BigDecimal getCotizacionActual(){
        Date d = new Date();
        return this.get()
                .sortDesc(Tables.COTIZACION.ID_COTIZACION)
                .get(0)
                .into(Tables.COTIZACION)
                .getValor();
    }
}
