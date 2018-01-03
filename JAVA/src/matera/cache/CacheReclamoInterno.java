/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.ReclamointernoRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class CacheReclamoInterno {
     
    String key = "reclamoInterno";
    private static CacheReclamoInterno instance = null;
    
    protected CacheReclamoInterno() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheReclamoInterno instance() {
        if(instance == null) {
           synchronized(CacheReclamoInterno.class) { 
              instance = new CacheReclamoInterno();
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
        return get().intoGroups(Tables.RECLAMOINTERNO.HABILITA).get("S");
    }

    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.RECLAMOINTERNO)
                    .fetch());  
    }
    
    public Result<ReclamointernoRecord> getReclamosInternos(){
        return get().into(Tables.RECLAMOINTERNO);
    } 
    
    public Result<ReclamointernoRecord> getReclamosInternosHabilitados(){
        return getHabilitados().into(Tables.RECLAMOINTERNO);
    }    
    
    public ReclamointernoRecord getReclamoInterno(Integer id){
        return getReclamosInternos().intoGroups(Tables.RECLAMOINTERNO.ID_RECLAMOINTERNO).get(id).get(0);
    }    
}