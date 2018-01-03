/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.TableObjects;

import matera.jooq.Tables;
import matera.jooq.tables.records.EntidadRecord;
import matera.jooq.tables.records.PrestacionRecord;
import matera.jooq.tables.records.PresupuestoRecord;
import matera.jooq.tables.records.ProfesionalRecord;
import matera.jooq.tables.records.ReclamoRecord;
import matera.jooq.tables.records.ReclamoexternoRecord;
import matera.jooq.tables.records.ReclamointernoRecord;
import org.jooq.Record;

/**
 *
 * @author tinchogava
 */
public class ReclamoTableObject {
    ReclamoRecord reclamo;
    ReclamointernoRecord reclamoInterno;
    ReclamoexternoRecord reclamoExterno;
    PresupuestoRecord presupuesto;
    ProfesionalRecord profesional;
    EntidadRecord entidad;
    PrestacionRecord prestacion;

    public ReclamoTableObject(Record r) {
        this.reclamo = r.into(Tables.RECLAMO);
        this.reclamoInterno = r.into(Tables.RECLAMOINTERNO);
        this.reclamoExterno = r.into(Tables.RECLAMOEXTERNO);
        this.presupuesto = r.into(Tables.PRESUPUESTO);
        this.profesional = r.into(Tables.PROFESIONAL);
        this.entidad = r.into(Tables.ENTIDAD);
        this.prestacion = r.into(Tables.PRESTACION);
    }

    public ReclamoRecord getReclamo() {
        return reclamo;
    }

    public void setReclamo(ReclamoRecord reclamo) {
        this.reclamo = reclamo;
    }

    public ReclamointernoRecord getReclamoInterno() {
        return reclamoInterno;
    }

    public void setReclamoInterno(ReclamointernoRecord reclamoInterno) {
        this.reclamoInterno = reclamoInterno;
    }

    public ReclamoexternoRecord getReclamoExterno() {
        return reclamoExterno;
    }

    public void setReclamoExterno(ReclamoexternoRecord reclamoExterno) {
        this.reclamoExterno = reclamoExterno;
    }

    public PresupuestoRecord getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(PresupuestoRecord presupuesto) {
        this.presupuesto = presupuesto;
    }

    public ProfesionalRecord getProfesional() {
        return profesional;
    }

    public void setProfesional(ProfesionalRecord profesional) {
        this.profesional = profesional;
    }

    public EntidadRecord getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadRecord entidad) {
        this.entidad = entidad;
    }

    public PrestacionRecord getPrestacion() {
        return prestacion;
    }

    public void setPrestacion(PrestacionRecord prestacion) {
        this.prestacion = prestacion;
    }
    
    
    
     public ReclamoTableObject(ReclamoRecord reclamo, ReclamointernoRecord reclamoInterno, ReclamoexternoRecord reclamoExterno, PresupuestoRecord presupuesto, ProfesionalRecord profesional, EntidadRecord entidad, PrestacionRecord prestacion) {
        this.reclamo = reclamo;
        this.reclamoInterno = reclamoInterno;
        this.reclamoExterno = reclamoExterno;
        this.presupuesto = presupuesto;
        this.profesional = profesional;
        this.entidad = entidad;
        this.prestacion = prestacion;
    }

    
}
