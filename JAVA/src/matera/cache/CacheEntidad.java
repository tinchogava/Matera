/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.EntidadRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheEntidad implements CacheRecords {
    String key = "entidad";
    private static CacheEntidad instance = null;
    
    protected CacheEntidad() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheEntidad instance() {
        if(instance == null) {
           synchronized(CacheEntidad.class) { 
              instance = new CacheEntidad();
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
        return get().intoGroups(Tables.ENTIDAD.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.ENTIDAD)
                    .fetch());  
    }
    
    public EntidadRecord getEntidad(Integer id){
        return get().into(Tables.ENTIDAD).intoGroups(Tables.ENTIDAD.ID_ENTIDAD).get(id).get(0);
    }
    public EntidadRecord getEntidad(int id){
        return get().into(Tables.ENTIDAD).intoGroups(Tables.ENTIDAD.ID_ENTIDAD).get(id).get(0);
    }
    
    public EntidadRecord getEntidad(String name){
        return get().into(Tables.ENTIDAD).intoGroups(Tables.ENTIDAD.NOMBRE).get(name).get(0);
    }    
    
    public Result<EntidadRecord> getEntidades(){
        return get().into(Tables.ENTIDAD);
    }
    
    public Result<EntidadRecord> getEntidadesHabilitadas(){
        return getHabilitados().into(Tables.ENTIDAD);
    }
    
    public Result<EntidadRecord> getEntidadesForZona(Integer id_zona){
        return getHabilitados().intoGroups(Tables.ENTIDAD.ID_ZONA).get(id_zona).into(Tables.ENTIDAD);
    }
}
