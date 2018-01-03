/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.ProfesionalRecord;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheProfesional implements CacheRecords {

    //static Result<Record> records = null;
    String key = "profesional";
    private static CacheProfesional instance = null;
    
    protected CacheProfesional() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheProfesional instance() {
        if(instance == null) {
           synchronized(CacheProfesional.class) { 
              instance = new CacheProfesional();
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
        return get().intoGroups(Tables.PROFESIONAL.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key,ActiveDatabase.getDSLContext()
                    .select().from(Tables.PROFESIONAL)
                    .fetch().sortAsc(Tables.PROFESIONAL.NOMBRE));
    }
    
    public ProfesionalRecord getProfesional(Integer id){
        return get().into(Tables.PROFESIONAL).intoGroups(Tables.PROFESIONAL.ID_PROFESIONAL).get(id).get(0);
    }
    
    public ProfesionalRecord getProfesional(String name){
        return get().into(Tables.PROFESIONAL).intoGroups(Tables.PROFESIONAL.NOMBRE).get(name).get(0);
    }    
    
    public Result<ProfesionalRecord> getProfesionales(){
        return get().into(Tables.PROFESIONAL);
    }
    
    public Result<ProfesionalRecord> getProfesionalesHabilitados(){
        return getHabilitados().into(Tables.PROFESIONAL);
    }
    
    public Result<ProfesionalRecord> getProfesionalesForZona(Integer id_zona){
        return getHabilitados().into(Tables.PROFESIONAL).intoGroups(Tables.PROFESIONAL.ID_ZONA).get(id_zona);
    } 
    
}
