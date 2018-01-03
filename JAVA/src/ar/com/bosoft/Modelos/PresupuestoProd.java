package ar.com.bosoft.Modelos;

import java.math.BigDecimal;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class PresupuestoProd {
    private int cantidad;
    private BigDecimal pxUnit;
    private String codigo, nombre, obsProducto;

    public PresupuestoProd(int cantidad, String codigo, String nombre, String obsProducto, BigDecimal pxUnit) {
        this.cantidad = cantidad;
        this.pxUnit = pxUnit;
        this.codigo = codigo;
        this.nombre = nombre;
        this.obsProducto = obsProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPxUnit() {
        return pxUnit;
    }

    public void setPxUnit(BigDecimal pxUnit) {
        this.pxUnit = pxUnit;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObsProducto() {
        return obsProducto;
    }

    public void setObsProducto(String obsProducto) {
        this.obsProducto = obsProducto;
    }
}

