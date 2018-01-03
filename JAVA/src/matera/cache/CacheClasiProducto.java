/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.ClasiproductoRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheClasiProducto implements CacheRecords {
    
    String key = "clasiProducto";
    private static CacheClasiProducto instance = null;
    
    protected CacheClasiProducto() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheClasiProducto instance() {
        if(instance == null) {
           synchronized(CacheClasiProducto.class) { 
              instance = new CacheClasiProducto();
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
                    .select().from(Tables.CLASIPRODUCTO)
                    .fetch());  
    }
    
    public Result<ClasiproductoRecord> getClasiProductos(){
        return get().into(Tables.CLASIPRODUCTO);
    }
}
