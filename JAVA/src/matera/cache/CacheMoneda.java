/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.MonedaRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheMoneda  implements CacheRecords {
    
    String key = "moneda";
    private static CacheMoneda instance = null;
    
    protected CacheMoneda() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheMoneda instance() {
        if(instance == null) {
           synchronized(CacheMoneda.class) { 
              instance = new CacheMoneda();
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
        return get().intoGroups(Tables.ZONA.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.MONEDA)
                    .fetch());  
    }
    
    public Result<MonedaRecord> getMonedas(){
        return get().into(Tables.MONEDA);
    } 
    
    public Result<MonedaRecord> getMonedasHabilitadas(){
        return getHabilitados().into(Tables.MONEDA);
    }    
    
    public MonedaRecord getMoneda(Integer id){
        return getMonedas().intoGroups(Tables.MONEDA.ID_MONEDA).get(id).get(0);
    }    
}