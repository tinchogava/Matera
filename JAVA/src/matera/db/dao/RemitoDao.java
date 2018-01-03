/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.dao;

import ar.com.bosoft.conexion.ActiveDatabase;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import matera.TableObjects.AnalisisFacturacionTableObject;
import matera.TableObjects.RemitoDetalleTableObject;
import matera.db.dto.EventoRemitoDTO;
import matera.jooq.Tables;
import matera.jooq.tables.pojos.LogEventoRemito;
import matera.jooq.tables.pojos.LogEventoTipo;
import matera.jooq.tables.pojos.Remito;
import matera.jooq.tables.pojos.Usuario;
import matera.jooq.tables.records.RemitoRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.impl.DSL;

/**
 *
 * @author h2o
 */
public class RemitoDao extends matera.jooq.tables.daos.RemitoDao {
    
    public RemitoDao(){
        super(ActiveDatabase.getDSLContext().configuration());
    }    
    
    public Map<Integer, Map<String,String>> getObservacionesForPresupuestos(List<Integer> presupuestos){
        Map<Integer, Map<String,String>> obs = new HashMap<>();
        presupuestos.forEach(p->obs.put(p, new HashMap<>()));
        DSL.using(configuration())
                        .select(Tables.REMITO.ID_PRESUPUESTO, Tables.REMITO.NUMCOMP, Tables.REMITO.OBSERVACIONES)
                        .from(Tables.REMITO)
                        .where(Tables.REMITO.ID_PRESUPUESTO.in(presupuestos))
                        .fetch()
                        .into((Record r)->{
                            RemitoRecord rr = r.into(Tables.REMITO);
                            obs.get(rr.getIdPresupuesto())
                                    .put(rr.getNumcomp(), rr.getObservaciones());
                        });
        return obs;
    }
    
    public List<RemitoDetalleTableObject> getConsumidoDetalle(Integer id_remito){
        List<RemitoDetalleTableObject> l = new ArrayList<>();
        DSL.using(configuration())
                .select()
                .from(Tables.REMITO_CONSUMIDO)
                .join(Tables.STOCKDETALLE).on(Tables.STOCKDETALLE.ID_STOCKDETALLE.eq(Tables.REMITO_CONSUMIDO.ID_STOCKDETALLE))
                .join(Tables.PRODUCTO).on(Tables.STOCKDETALLE.ID_PRODUCTO.eq(Tables.PRODUCTO.ID_PRODUCTO))
                .join(Tables.CLASIPRODUCTO).on(Tables.PRODUCTO.ID_CLASIPRODUCTO.eq(Tables.CLASIPRODUCTO.ID_CLASIPRODUCTO))
                .where(Tables.REMITO_CONSUMIDO.ID_REMITO.eq(id_remito))
                .fetch()
                .into((Record r)->{
                    l.add(new RemitoDetalleTableObject(r.into(Tables.PRODUCTO), r.into(Tables.CLASIPRODUCTO), r.into(Tables.STOCKDETALLE)));
                });
        return l;        
    }
    
    public List<EventoRemitoDTO> getEventosForPresupuesto(Integer id){
        List<EventoRemitoDTO> list = new ArrayList<>();
        DSL.using(configuration())
                .select()
                .from(Tables.REMITO)
                .join(Tables.LOG_EVENTO_REMITO).on(Tables.LOG_EVENTO_REMITO.ID_REMITO.eq(Tables.REMITO.ID_REMITO))
                .join(Tables.LOG_EVENTO_TIPO).on(Tables.LOG_EVENTO_TIPO.ID.eq(Tables.LOG_EVENTO_REMITO.ID_LOG_EVENTO_TIPO))
                .join(Tables.USUARIO).on(Tables.LOG_EVENTO_REMITO.ID_USUARIO.eq(Tables.USUARIO.ID_USUARIO))
                .where(Tables.REMITO.ID_PRESUPUESTO.eq(id))
                .orderBy(Tables.LOG_EVENTO_REMITO.TIMESTAMP)
                .fetch()
                .into((Record r)->{
                    list.add(new EventoRemitoDTO(
                            r.into(Tables.LOG_EVENTO_REMITO).into(LogEventoRemito.class),
                            r.into(Tables.LOG_EVENTO_TIPO).into(LogEventoTipo.class),
                            r.into(Tables.USUARIO).into(Usuario.class),
                            r.into(Tables.REMITO).into(Remito.class)));
                });
        return list;
    }    
    
    public Result<RemitoRecord> getRemitosByPresupuesto (Integer id_presupuesto){
        return DSL.using(configuration()).select()
                .from(Tables.REMITO)
                .join(Tables.PRESUPUESTO)
                .on(Tables.PRESUPUESTO.ID_PRESUPUESTO.eq(Tables.REMITO.ID_PRESUPUESTO))
                .where(Tables.REMITO.ID_PRESUPUESTO.eq(id_presupuesto)).fetchInto(Tables.REMITO)
                .sortDesc(Tables.REMITO.FECHACONSUMIDO);
    }
    
    public Result<RemitoRecord> getRemitosByFechaConsumo(Date since, Date until, List<Integer> yaCosteados){
        return DSL.using(configuration())
                .select()
                .from(Tables.REMITO)
                .where(Tables.REMITO.FECHACONSUMIDO.between(since, until))
                .and(Tables.REMITO.ID_PRESUPUESTO.notIn(yaCosteados))
                .fetchInto(Tables.REMITO);
        }
    
}
