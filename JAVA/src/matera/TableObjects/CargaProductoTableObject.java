/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.TableObjects;

import matera.jooq.tables.records.ClasiproductoRecord;
import matera.jooq.tables.records.MonedaRecord;
import matera.jooq.tables.records.ProductoRecord;

/**
 *
 * @author h2o
 */
public class CargaProductoTableObject extends ProductoTableObject {

    Integer cantidad;
    
    public CargaProductoTableObject(ProductoRecord producto, ClasiproductoRecord clasiProducto, MonedaRecord moneda, Integer cantidad) {
        super(producto, clasiProducto, moneda);
        this.cantidad = cantidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
}
