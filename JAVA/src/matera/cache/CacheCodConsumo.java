/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.CodconsumoRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheCodConsumo implements CacheRecords {
    
    String key = "codConsumo";
    private static CacheCodConsumo instance = null;
    
    protected CacheCodConsumo() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheCodConsumo instance() {
        if(instance == null) {
           synchronized(CacheCodConsumo.class) { 
              instance = new CacheCodConsumo();
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
                    .select().from(Tables.CODCONSUMO)
                    .fetch());  
    }
    
    public Result<CodconsumoRecord> getCodConsumos(){
        return get().into(Tables.CODCONSUMO);
    }
    
    public CodconsumoRecord getCodConsumo(Integer id){
        return getCodConsumos().intoGroups(Tables.CODCONSUMO.ID_CODCONSUMO).get(id).get(0);
    }    
}