/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.cache;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.util.ArrayList;
import java.util.List;
import matera.TableObjects.ProductoTableObject;
import matera.jooq.Tables;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class CacheProductos implements CacheRecords {
    //static Result<Record> records = null;
    String key = "productos";
    private static CacheProductos instance = null;
    
    protected CacheProductos() {
       // Exists only to defeat instantiation.
    }
    
    public static CacheProductos instance() {
        if(instance == null) {
           synchronized(CacheProductos.class) { 
              instance = new CacheProductos();
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
        return get().intoGroups(Tables.PRODUCTO.HABILITA).get("S");
    }

    @Override
    public void fetch() {
        CacheMgr.getCache().put(key,
                ActiveDatabase.getDSLContext()
                    .select().from(Tables.PRODUCTO)
                    .join(Tables.PROVEEDOR).on(Tables.PRODUCTO.ID_PROVEEDOR.eq(Tables.PROVEEDOR.ID_PROVEEDOR))
                    .join(Tables.CODCONSUMO).on(Tables.CODCONSUMO.ID_CODCONSUMO.eq(Tables.PRODUCTO.ID_CODCONSUMO))
                    .join(Tables.MONEDA).on(Tables.MONEDA.ID_MONEDA.eq(Tables.PRODUCTO.ID_MONEDA))
                    .join(Tables.CLASIPRODUCTO).on(Tables.CLASIPRODUCTO.ID_CLASIPRODUCTO.eq(Tables.PRODUCTO.ID_CLASIPRODUCTO))
                    .fetch().sortAsc(Tables.PRODUCTO.CODIGO)
        );
    }
     
    public List<ProductoTableObject> getProductoTableObjectList(Boolean habilitadosOnly){
        Result<Record> r;
        List<ProductoTableObject> list = new ArrayList<>();
        if (habilitadosOnly){
            getHabilitados().forEach(p->{
                list.add(new ProductoTableObject(p));
            });
        }else{
            get().forEach(p->{
                list.add(new ProductoTableObject(p));
            });
        }
        return list;
    }
}
