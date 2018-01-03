/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.ReclamoexternoRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class CacheReclamoExterno {
    
    String key = "reclamoExterno";
    private static CacheReclamoExterno instance = null;
    
    protected CacheReclamoExterno() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheReclamoExterno instance() {
        if(instance == null) {
           synchronized(CacheReclamoExterno.class) { 
              instance = new CacheReclamoExterno();
           }
        }
        return instance;
    }  
    
    public Result<Record> get() {
        Result<Record> records = CacheMgr.getCache().get(key);
        if (records == null){
            fetch();
        }
        return CacheMgr.getCache().get(key);
    }

    public Result<Record> getHabilitados() {
        return get().intoGroups(Tables.RECLAMOEXTERNO.HABILITA).get("S");
    }

    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.RECLAMOEXTERNO)
                    .fetch());  
    }
    
    public Result<ReclamoexternoRecord> getReclamosExternos(){
        return get().into(Tables.RECLAMOEXTERNO);
    } 
    
    public Result<ReclamoexternoRecord> getReclamosExternosHabilitados(){
        return getHabilitados().into(Tables.RECLAMOEXTERNO);
    }    
    
    public ReclamoexternoRecord getReclamoExterno(Integer id){
        return getReclamosExternos().intoGroups(Tables.RECLAMOEXTERNO.ID_RECLAMOEXTERNO).get(id).get(0);
    }    
}