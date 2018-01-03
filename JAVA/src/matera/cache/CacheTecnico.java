/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;


import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.TecnicoRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author tinchogava
 */
public class CacheTecnico implements CacheRecords {
    
    String key = "tecnico";
    private static CacheTecnico instance = null;
    
    protected CacheTecnico() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheTecnico instance() {
        if(instance == null) {
           synchronized(CacheTecnico.class) { 
              instance = new CacheTecnico();
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
        return get().intoGroups(Tables.TECNICO.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.TECNICO)
                    //.where(Tables.TECNICO.HABILITA.eq("S"))
                    .fetch().sortAsc(Tables.TECNICO.NOMBRE));  
    }
    
    public TecnicoRecord getTecnico(Integer id){
        return get().into(Tables.TECNICO).intoGroups(Tables.TECNICO.ID_TECNICO).get(id).get(0);
    }
    
    public TecnicoRecord getTecnico(String name){
        return get().into(Tables.TECNICO).intoGroups(Tables.TECNICO.NOMBRE).get(name).get(0);
    }    
    
    public Result<TecnicoRecord> getTecnicos(){
        return get().into(Tables.TECNICO);
    }
    
    public Result<TecnicoRecord> getTecnicosHabilitados(){
        return getHabilitados().into(Tables.TECNICO);
    }
}

