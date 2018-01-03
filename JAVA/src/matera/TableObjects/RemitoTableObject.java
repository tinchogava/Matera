/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.TableObjects;

import matera.jooq.Tables;
import matera.jooq.tables.records.EntidadRecord;
import matera.jooq.tables.records.LogEventoRemitoRecord;
import matera.jooq.tables.records.PresupuestoRecord;
import matera.jooq.tables.records.ProfesionalRecord;
import matera.jooq.tables.records.ProveedorRecord;
import matera.jooq.tables.records.RemitoRecord;
import matera.jooq.tables.records.ZonaRecord;
import org.jooq.Record;

/**
 *
 * @author h2o
 */
public class RemitoTableObject {
    RemitoRecord remito;
    PresupuestoRecord presupuesto;
    ZonaRecord zona;
    ZonaRecord zonaDestino;
    ProfesionalRecord profesional;
    ProfesionalRecord profesionalDestino;
    ProveedorRecord proveedor;
    EntidadRecord entidad;
    EntidadRecord lugarCirugia;
    LogEventoRemitoRecord eventoRemito;

    public ProfesionalRecord getProfesionalDestino() {
        return profesionalDestino;
    }

    public void setProfesionalDestino(ProfesionalRecord profesionalDestino) {
        this.profesionalDestino = profesionalDestino;
    }
    
    public RemitoTableObject(Record r){
        remito = r.into(Tables.REMITO);
        presupuesto = r.into(Tables.PRESUPUESTO);
        zona = r.into(Tables.ZONA);        
        profesionalDestino = r.into(Tables.PROFESIONAL.as("profesionalDestino"));
        profesional = r.into(Tables.PROFESIONAL);
        proveedor = r.into(Tables.PROVEEDOR);
        entidad = r.into(Tables.ENTIDAD);
        lugarCirugia = r.into(Tables.ENTIDAD.as("lugarCirugia"));
        zonaDestino = r.into(Tables.ZONA.as("zonaDestino"));
        eventoRemito = r.into(Tables.LOG_EVENTO_REMITO);
    }

    public LogEventoRemitoRecord getEventoRemito() {
        return eventoRemito;
    }

    public void setEventoRemito(LogEventoRemitoRecord eventoRemito) {
        this.eventoRemito = eventoRemito;
    }

    public ZonaRecord getZonaDestino() {
        return zonaDestino;
    }

    public void setZonaDestino(ZonaRecord zonaDestino) {
        this.zonaDestino = zonaDestino;
    }

    public EntidadRecord getLugarCirugia() {
        return lugarCirugia;
    }

    public void setLugarCirugia(EntidadRecord lugarCirugia) {
        this.lugarCirugia = lugarCirugia;
    }

    public RemitoRecord getRemito() {
        return remito;
    }

    public void setRemito(RemitoRecord remito) {
        this.remito = remito;
    }

    public PresupuestoRecord getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(PresupuestoRecord presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ZonaRecord getZona() {
        return zona;
    }

    public void setZona(ZonaRecord zona) {
        this.zona = zona;
    }

    public ProfesionalRecord getProfesional() {
        return profesional;
    }

    public void setProfesional(ProfesionalRecord profesional) {
        this.profesional = profesional;
    }

    public ProveedorRecord getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorRecord proveedor) {
        this.proveedor = proveedor;
    }

    public EntidadRecord getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadRecord entidad) {
        this.entidad = entidad;
    }
    
    public String getDestino(){
        RemitoRecord r = getRemito();
        if (r.getIdPresupuesto() > 0)
            return getLugarCirugia().getNombre();
        if (r.getIdProveedor() > 0)
            return getProveedor().getNombre();
        if (r.getIdDestino() > 0)
            return getProfesionalDestino().getNombre();
        if (r.getIdZonadestino() > 0)
            return getZonaDestino().getNombre();
        return "";
    }    
}
