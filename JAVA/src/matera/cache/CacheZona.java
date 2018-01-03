/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.ZonaRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheZona implements CacheRecords {
    
    String key = "zona";
    private static CacheZona instance = null;
    
    protected CacheZona() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheZona instance() {
        if(instance == null) {
           synchronized(CacheZona.class) { 
              instance = new CacheZona();
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
                    .select().from(Tables.ZONA)
                    .fetch());  
    }
    
    public ZonaRecord getZona(Integer id){
        return get().into(Tables.ZONA).intoGroups(Tables.ZONA.ID_ZONA).get(id).get(0);
    }
    
    public ZonaRecord getZona(String name){
        return get().into(Tables.ZONA).intoGroups(Tables.ZONA.NOMBRE).get(name).get(0);
    }    
    
    public Result<ZonaRecord> getZonas(){
        return get().into(Tables.ZONA);
    }
    
    public Result<ZonaRecord> getZonasHabilitadas(){
        return getHabilitados().into(Tables.ZONA);
    }
}
