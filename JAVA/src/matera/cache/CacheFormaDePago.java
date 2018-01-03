/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.FormapagoRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheFormaDePago implements CacheRecords {
    String key = "Formapago";
    private static CacheFormaDePago instance = null;
    
    protected CacheFormaDePago() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheFormaDePago instance() {
        if(instance == null) {
           synchronized(CacheFormaDePago.class) { 
              instance = new CacheFormaDePago();
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
        return get().intoGroups(Tables.FORMAPAGO.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.FORMAPAGO)
                    .fetch());  
    }
    
    public FormapagoRecord getFormaDePago(Integer id){
        return get().into(Tables.FORMAPAGO).intoGroups(Tables.FORMAPAGO.ID_FORMAPAGO).get(id).get(0);
    }
    
    public FormapagoRecord getFormaDePago(String name){
        return get().into(Tables.FORMAPAGO).intoGroups(Tables.FORMAPAGO.NOMBRE).get(name).get(0);
    }    
    
    public Result<FormapagoRecord> getFormasDePago(){
        return get().into(Tables.FORMAPAGO);
    }
    
    public Result<FormapagoRecord> getFormasDePagoHabilitadas(){
        return getHabilitados().into(Tables.FORMAPAGO);
    }
}
