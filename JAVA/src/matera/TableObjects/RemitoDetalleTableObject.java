/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.TableObjects;

import ar.com.bosoft.Modelos.Consumido;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import matera.cache.CacheCotizaciones;
import matera.jooq.tables.records.ClasiproductoRecord;
import matera.jooq.tables.records.ProductoRecord;
import matera.jooq.tables.records.StockdetalleRecord;

/**
 *
 * @author h2o
 */
public class RemitoDetalleTableObject {
    Integer cantidad = 1;
    ProductoRecord producto;
    ClasiproductoRecord clasiProducto;
    StockdetalleRecord stockdetalle;
    
    public RemitoDetalleTableObject(ProductoRecord producto, ClasiproductoRecord clasiProducto, StockdetalleRecord stockdetalle){
        this.producto = producto;
        this.clasiProducto = clasiProducto;
        this.stockdetalle = stockdetalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getSerial(){
        return producto.getCodigo() + stockdetalle.getLote() + stockdetalle.getSerie();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RemitoDetalleTableObject other = (RemitoDetalleTableObject) obj;
        Boolean eq = getStockdetalle().getIdProducto().equals(other.getStockdetalle().getIdProducto()) &&
                       getStockdetalle().getLote().equals(other.getStockdetalle().getLote()) && 
                        getStockdetalle().getSerie().equals(other.getStockdetalle().getSerie());
        return eq;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 53 * hash + Objects.hashCode(this.stockdetalle.getIdProducto());
        hash = 53 * hash + Objects.hashCode(this.stockdetalle.getLote());
        hash = 53 * hash + Objects.hashCode(this.stockdetalle.getSerie());
        return hash;
    }
    

    
    
    /**
     *  Constructor inicializando stockdetalle con datos por default
     * @param producto
     * @param clasiProducto
     * @param id_zona
    */
    public RemitoDetalleTableObject(ProductoRecord producto, ClasiproductoRecord clasiProducto, Integer id_zona){
            this.producto = producto;
            this.clasiProducto = clasiProducto;
            this.stockdetalle = new StockdetalleRecord();
            BigDecimal cotizacionActual = CacheCotizaciones.instance().getCotizacionActual();
            stockdetalle.setIdProducto(producto.getIdProducto());
            stockdetalle.setCantidad(1);
            stockdetalle.setDc("C");
            stockdetalle.setLote("");
            stockdetalle.setSerie("");
            stockdetalle.setIdMoneda(producto.getIdMoneda());
            stockdetalle.setIdZona(id_zona);
            stockdetalle.setPm(producto.getPm());
            BigDecimal cotizacion = producto.getIdMoneda() > 1 ? cotizacionActual : new BigDecimal(BigInteger.ONE);
            stockdetalle.setCostopesos(producto.getCosto().multiply(cotizacion));
            stockdetalle.setCotizacion(cotizacionActual);
    }

    public ProductoRecord getProducto() {
        return producto;
    }

    public ClasiproductoRecord getClasiProducto() {
        return clasiProducto;
    }

    public void setClasiProducto(ClasiproductoRecord clasiProducto) {
        this.clasiProducto = clasiProducto;
    }

    public void setProducto(ProductoRecord producto) {
        this.producto = producto;
    }

    public StockdetalleRecord getStockdetalle() {
        return stockdetalle;
    }

    public void setStockdetalle(StockdetalleRecord stockdetalle) {
        this.stockdetalle = stockdetalle;
    }
    
    public Consumido getConsumidoData(){
        return new Consumido(0, getCantidad(), producto.getCodigo(),producto.getGtin(),producto.getNombre(),stockdetalle.getLote(),stockdetalle.getSerie(),producto.getPm());
    }
    
    
}
