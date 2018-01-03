/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.TableObjects;

import java.util.Map;
import matera.jooq.tables.records.PresupuestoControlfacturacionRecord;

/**
 *
 * @author h2o
 */
public class PendienteFacturacionTO {
    Map rs;
    PresupuestoControlfacturacionRecord control;

    public Map getRs() {
        return rs;
    }

    public void setRs(Map rs) {
        this.rs = rs;
    }

    public PresupuestoControlfacturacionRecord getControl() {
        return control;
    }

    public void setControl(PresupuestoControlfacturacionRecord control) {
        this.control = control;
    }
    
    public PendienteFacturacionTO(Map rs, PresupuestoControlfacturacionRecord control){
        this.rs = rs;
        this.control = control;
    }
}
