/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.PrestacionRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class CachePrestaciones implements CacheRecords {
    
    String key = "prestaciones";
    private static CachePrestaciones instance = null;
    
    protected CachePrestaciones() {
       // Exists only to defeat instantiation.
    }
    
    public static CachePrestaciones instance() {
        if(instance == null) {
           synchronized(CachePrestaciones.class) { 
              instance = new CachePrestaciones();
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
        return get().intoGroups(Tables.PRESTACION.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.PRESTACION)
                    .fetch().sortAsc(Tables.PRESTACION.NOMBRE));  
    }
    
    public PrestacionRecord getPrestacion(Integer id){
         return get().into(Tables.PRESTACION).intoGroups(Tables.PRESTACION.ID_PRESTACION).get(id).get(0);
    }
    
    public PrestacionRecord getPrestacion(String name){
        return get().into(Tables.PRESTACION).intoGroups(Tables.PRESTACION.NOMBRE).get(name).get(0);
    }    
    
    public Result<PrestacionRecord> getPrestacion(){
        return get().into(Tables.PRESTACION);
    }
    
    public Result<PrestacionRecord> getPrestacionesHabilitadas(){
        return getHabilitados().into(Tables.PRESTACION);
    }
}
