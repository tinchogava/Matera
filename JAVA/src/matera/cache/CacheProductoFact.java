/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.ProductofactRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheProductoFact implements CacheRecords {
    //static Result<Record> records = null;
    String key = "productoFact";
    private static CacheProductoFact instance = null;
    
    protected CacheProductoFact() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheProductoFact instance() {
        if(instance == null) {
           synchronized(CacheProductoFact.class) { 
              instance = new CacheProductoFact();
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
        return get().intoGroups(Tables.PRODUCTOFACT.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key,
                ActiveDatabase.getDSLContext()
                    .select().from(Tables.PRODUCTOFACT)
                    .fetch().sortAsc(Tables.PRODUCTOFACT.CODIGO)
        );
    }
    
    public Result<ProductofactRecord> getProductoFactHabilitados(){
        return getHabilitados().into(Tables.PRODUCTOFACT);
    }
}
