/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;


import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.TipooperacionRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class CacheTipoOperacion implements CacheRecords {
    
    String key = "tipoOperacion";
    private static CacheTipoOperacion instance = null;
    
    protected CacheTipoOperacion() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheTipoOperacion instance() {
        if(instance == null) {
           synchronized(CacheTipoOperacion.class) { 
              instance = new CacheTipoOperacion();
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
        return get().intoGroups(Tables.TIPOOPERACION.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.TIPOOPERACION)
                    .fetch().sortAsc(Tables.TIPOOPERACION.NOMBRE));  
    }
    
    public TipooperacionRecord getTipoOperacion(Integer id){
        return get().into(Tables.TIPOOPERACION).intoGroups(Tables.TIPOOPERACION.ID_TIPOOPERACION).get(id).get(0);
    }
    
    public TipooperacionRecord getTipoOperacion(String name){
        return get().into(Tables.TIPOOPERACION).intoGroups(Tables.TIPOOPERACION.NOMBRE).get(name).get(0);
    }    
    
    public Result<TipooperacionRecord> getTiposOperacion(){
        return get().into(Tables.TIPOOPERACION);
    }
    
    public Result<TipooperacionRecord> getTiposOperacionHabilitados(){
        return getHabilitados().into(Tables.TIPOOPERACION);
    }
}

