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
public class Remito extends matera.jooq.tables.pojos.Remito {
    Set<Stockdetalle> remitoDetalle;
    Set<RemitoConsumido> consumido;
}