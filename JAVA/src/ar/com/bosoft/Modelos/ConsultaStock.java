package ar.com.bosoft.Modelos;

import java.util.Date;


/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ConsultaStock {
    String zona, codigo, gtin, nombre, lote, serie, pm, observaciones, dc, proveedor, remito;
    int cantidad, inicial, acumulado;
    Date fecha, vencimiento;
    Double costo;

    public ConsultaStock(String zona, String codigo, String gtin, String nombre, String lote, String serie, String pm, String observaciones, String dc, int cantidad, int inicial, Date fecha, Date vencimiento, int acumulado, Double costo, String proveedor, String remito) {
        this.zona = zona;
        this.codigo = codigo;
        this.gtin = gtin;
        this.nombre = nombre;
        this.lote = lote;
        this.pm = pm;
        this.observaciones = observaciones;
        this.dc = dc;
        this.cantidad = cantidad;
        this.inicial = inicial;
        this.fecha = fecha;
        this.vencimiento = vencimiento;
        this.acumulado = acumulado;
        this.costo = costo;
        this.serie = serie;
        this.proveedor = proveedor;
        this.remito = remito;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }    

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getInicial() {
        return inicial;
    }

    public void setInicial(int inicial) {
        this.inicial = inicial;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(int acumulado) {
        this.acumulado = acumulado;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }
}
