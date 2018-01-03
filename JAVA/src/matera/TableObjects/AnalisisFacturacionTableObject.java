/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.TableObjects;

import matera.jooq.Tables;
import matera.jooq.tables.records.PresupuestoRecord;
import matera.jooq.tables.records.RemitoRecord;
import matera.jooq.tables.records.TipooperacionRecord;
import matera.jooq.tables.records.VendedorRecord;
import matera.jooq.tables.records.ZonaRecord;
import org.jooq.Record;

/**
 *
 * @author tinchogava
 */
public class AnalisisFacturacionTableObject {
    RemitoRecord remito;
    PresupuestoRecord presupuesto;
    TipooperacionRecord tipoOperacion;
    ZonaRecord zona;
    VendedorRecord vendedor;

    public AnalisisFacturacionTableObject(Record r) {
        this.remito = r.into(Tables.REMITO);
        this.presupuesto = r.into(Tables.PRESUPUESTO);
        this.tipoOperacion = r.into(Tables.TIPOOPERACION);
        this.zona = r.into(Tables.ZONA);
        this.vendedor = r.into(Tables.VENDEDOR);
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

    public TipooperacionRecord getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipooperacionRecord tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public ZonaRecord getZona() {
        return zona;
    }

    public void setZona(ZonaRecord zona) {
        this.zona = zona;
    }

    public VendedorRecord getVendedor() {
        return vendedor;
    }

    public void setVendedor(VendedorRecord vendedor) {
        this.vendedor = vendedor;
    }
    
    
}
