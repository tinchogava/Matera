/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.pojos;

import java.util.Set;

/**
 *
 * @author h2o
 */
public class Presupuesto extends matera.jooq.tables.pojos.Presupuesto {
    Set <Remito> remitos;
    Tipooperacion tipooperacion;
    Vendedor vendedor;
}
