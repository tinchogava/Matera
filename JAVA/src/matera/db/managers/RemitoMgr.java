/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.managers;

import ar.com.bosoft.Modelos.CostoVentas;
import ar.com.bosoft.clases.Utiles;
import ar.com.bosoft.conexion.ActiveDatabase;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import matera.TableObjects.RemitoTableObject;
import matera.db.dao.RemitoConsumidoDao;
import matera.jooq.Tables;
import matera.jooq.tables.pojos.RemitoConsumido;
import matera.jooq.tables.records.RemitoConsumidoRecord;
import matera.jooq.tables.records.StockdetalleRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

/**
 *
 * @author h2o
 */
public class RemitoMgr {
    public static List<RemitoTableObject> getRemitos(DSLContext dsl, Condition condition){
        List<RemitoTableObject> list = new ArrayList<>();
        dsl.select().from(Tables.REMITO)
                .leftJoin(Tables.PRESUPUESTO).on(Tables.REMITO.ID_PRESUPUESTO.eq(Tables.PRESUPUESTO.ID_PRESUPUESTO))
                .join(Tables.TIPOCOMP).on(Tables.REMITO.ID_TIPOCOMP.eq(Tables.TIPOCOMP.ID_TIPOCOMP))
                .leftJoin(Tables.ENTIDAD).on(Tables.REMITO.ID_ENTIDAD.eq(Tables.ENTIDAD.ID_ENTIDAD))
                .leftJoin(Tables.ENTIDAD.as("lugarCirugia")).on(Tables.PRESUPUESTO.ID_LUGARCIRUGIA.eq(Tables.ENTIDAD.as("lugarCirugia").ID_ENTIDAD))
                .join(Tables.ZONA).on(Tables.REMITO.ID_ZONA.eq(Tables.ZONA.ID_ZONA))
                .leftJoin(Tables.ZONA.as("zonaDestino")).on(Tables.REMITO.ID_ZONADESTINO.eq(Tables.ZONA.as("zonaDestino").ID_ZONA))
                .leftJoin(Tables.PROVEEDOR).on(Tables.REMITO.ID_PROVEEDOR.eq(Tables.PROVEEDOR.ID_PROVEEDOR))
                .leftJoin(Tables.PROFESIONAL.as("profesionalDestino")).on(Tables.REMITO.ID_DESTINO.eq(Tables.PROFESIONAL.as("profesionalDestino").ID_PROFESIONAL))
                .leftJoin(Tables.PROFESIONAL).on(Tables.PRESUPUESTO.ID_PROFESIONAL1.eq(Tables.PROFESIONAL.ID_PROFESIONAL))
                .leftJoin(Tables.LOG_EVENTO_REMITO).on(Tables.LOG_EVENTO_REMITO.ID_REMITO.eq(Tables.REMITO.ID_REMITO))
                .where(condition)//.and(Tables.LOG_EVENTO_REMITO.ID_LOG_EVENTO_TIPO.eq(201))
                .fetch()
                .into((Record r1) -> {
                    list.add(
                            new RemitoTableObject(r1)
                            );
                });
        return list;
    }
    
    public static List<RemitoTableObject> getRemitosByZonaAndFecha(DSLContext dsl, Integer id_zona, Date from, Date to){
        Condition condition = Tables.REMITO.FECHA.greaterOrEqual(from);
        
        if (id_zona > Utiles.TODOS)
            condition = condition.and(Tables.REMITO.ID_ZONA.eq(id_zona));
        
        if (to != null)
            condition = condition.and(Tables.REMITO.FECHA.lessOrEqual(to));        
        
        return getRemitos(dsl, condition);
    }
    
    public static List<RemitoTableObject> getRemitosByPresupuesto(Integer id_presupuesto){
        Condition condition = Tables.REMITO.ID_PRESUPUESTO.eq(id_presupuesto);
        return getRemitos(ActiveDatabase.getDSLContext(), condition);
    }    
    
    public static List<StockdetalleRecord> getConsumido(Result<StockdetalleRecord> records){
        List<StockdetalleRecord> enviado = records.stream().filter(p-> p.getDc().equals(Utiles.CREDITO)).collect(Collectors.toList());
        List<StockdetalleRecord> recibido = records.stream().filter(p-> p.getDc().equals(Utiles.DEBITO)).collect(Collectors.toList());
        enviado.removeIf(p->{
                StockdetalleRecord r = recibido.stream().filter(
                        c->
                        c.getIdProducto().equals(p.getIdProducto()) &&
                        c.getLote().equals(p.getLote()) && 
                        c.getSerie().equals(p.getSerie())
                ).findFirst().orElse(null);
                if (r != null){
                    recibido.remove(r);
                    return true;
                }
                return false;
        });
        return enviado;
    }
    
    public static List<StockdetalleRecord> getConsumidoFromDetalle(Integer id_remito){
        DSLContext dsl = ActiveDatabase.getDSLContext();
        Result<StockdetalleRecord> r = dsl.select(Tables.STOCKDETALLE.fields())
                .from(Tables.STOCKDETALLE)
                .join(Tables.REMITO)
                .on(Tables.REMITO.ID_REMITO.eq(id_remito)
                        .and(Tables.REMITO.RECIBIDO.eq(Utiles.SI)))
                .where(
                        Tables.STOCKDETALLE.ID_REMITO.eq(id_remito)
                        ).fetchInto(Tables.STOCKDETALLE);
        return getConsumido(r);
    }
    
    public static Result<RemitoConsumidoRecord> getConsumido(Integer id_remito){
        DSLContext dsl = ActiveDatabase.getDSLContext();
        return dsl.select(Tables.REMITO_CONSUMIDO.fields())
                .from(Tables.REMITO_CONSUMIDO)
                .join(Tables.REMITO)
                .on(Tables.REMITO.ID_REMITO.eq(id_remito)
                        .and(Tables.REMITO.RECIBIDO.eq(Utiles.SI)))
                .where(
                        Tables.REMITO_CONSUMIDO.ID_REMITO.eq(id_remito)
                        ).fetchInto(Tables.REMITO_CONSUMIDO);
    }
    
    public static void saveConsumidoFromDetalle(Integer id_remito){
        try{
            List<RemitoConsumido> l = new ArrayList<>();
            getConsumidoFromDetalle(id_remito).forEach(c->{
                l.add(new RemitoConsumido(c.getIdStockdetalle(), c.getIdRemito()));
            });
            RemitoConsumidoDao rcdao = new RemitoConsumidoDao();
            rcdao.insert(l);        
        }
        catch(Exception e){
            Utiles.showErrorMessage(e);
        }

    }
}
