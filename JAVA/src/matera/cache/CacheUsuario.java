/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;


import ar.com.bosoft.conexion.ActiveDatabase;
import matera.jooq.Tables;
import matera.jooq.tables.records.UsuarioRecord;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.types.UInteger;

/**
 *
 * @author h2o
 */
public class CacheUsuario implements CacheRecords {
    
    String key = "usuario";
    private static CacheUsuario instance = null;
    
    protected CacheUsuario() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheUsuario instance() {
        if(instance == null) {
           synchronized(CacheUsuario.class) { 
              instance = new CacheUsuario();
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
        return get().intoGroups(Tables.USUARIO.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key, ActiveDatabase.getDSLContext()
                    .select().from(Tables.USUARIO)
                    .fetch().sortAsc(Tables.USUARIO.NOMBRE));  
    }
    
    public UsuarioRecord getUsuario(UInteger id){
        return get().into(Tables.USUARIO).intoGroups(Tables.USUARIO.ID_USUARIO).get(id).get(0);
    }
    
    public UsuarioRecord getUsuario(Integer id){
        return get().into(Tables.USUARIO).intoGroups(Tables.USUARIO.ID_USUARIO).get(UInteger.valueOf(id)).get(0);
    }
    
    public UsuarioRecord getUsuario(String name){
        return get().into(Tables.USUARIO).intoGroups(Tables.USUARIO.NOMBRE).get(name).get(0);
    }    
    
    public Result<UsuarioRecord> getUsuarios(){
        return get().into(Tables.USUARIO);
    }
    
    public Result<UsuarioRecord> getUsuariosHabilitados(){
        return getHabilitados().into(Tables.USUARIO);
    }    
    
    public Result<UsuarioRecord> getUsuariosForZona(Integer id_zona){
        return getHabilitados().into(Tables.USUARIO).intoGroups(Tables.USUARIO.ID_ZONA).get(id_zona);
    }    
}


