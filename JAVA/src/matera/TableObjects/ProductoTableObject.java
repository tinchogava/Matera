/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.TableObjects;

import matera.jooq.Tables;
import matera.jooq.tables.records.ClasiproductoRecord;
import matera.jooq.tables.records.CodconsumoRecord;
import matera.jooq.tables.records.MonedaRecord;
import matera.jooq.tables.records.ProductoRecord;
import matera.jooq.tables.records.ProveedorRecord;
import org.jooq.Record;

/**
 *
 * @author h2o
 */
public class ProductoTableObject {
    ProductoRecord producto;
    ClasiproductoRecord clasiProducto;
    MonedaRecord moneda;
    CodconsumoRecord codConsumo;

    public ProveedorRecord getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorRecord proveedor) {
        this.proveedor = proveedor;
    }
    ProveedorRecord proveedor;
    
    public ProductoTableObject(Record r){
        this.producto = r.into(Tables.PRODUCTO);
        this.clasiProducto = r.into(Tables.CLASIPRODUCTO);
        this.moneda = r.into(Tables.MONEDA);
        this.codConsumo = r.into(Tables.CODCONSUMO);
        this.proveedor = r.into(Tables.PROVEEDOR);
    }

    public CodconsumoRecord getCodConsumo() {
        return codConsumo;
    }

    public void setCodConsumo(CodconsumoRecord codConsumo) {
        this.codConsumo = codConsumo;
    }

    public ProductoTableObject(ProductoRecord producto, ClasiproductoRecord clasiProducto, MonedaRecord moneda) {
        this.producto = producto;
        this.clasiProducto = clasiProducto;
        this.moneda = moneda;
    }

    public MonedaRecord getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedaRecord moneda) {
        this.moneda = moneda;
    }

    public ProductoRecord getProducto() {
        return producto;
    }

    public void setProducto(ProductoRecord producto) {
        this.producto = producto;
    }

    public ClasiproductoRecord getClasiProducto() {
        return clasiProducto;
    }

    public void setClasiProducto(ClasiproductoRecord clasiProducto) {
        this.clasiProducto = clasiProducto;
    }
}
