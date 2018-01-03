/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 */
package ar.com.bosoft.data;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class CajaComposicionData {
    int id_cajaDeposito, id_producto,cantidad;
    String caja, codigo, producto;

    public CajaComposicionData() {
        this.id_cajaDeposito = 0;
        this.id_producto = 0;
        this.cantidad = 0;
        this.caja = "";
        this.codigo = "";
        this.producto = "";
    }

    public int getId_cajaDeposito() {
        return id_cajaDeposito;
    }

    public void setId_cajaDeposito(int id_cajaDeposito) {
        this.id_cajaDeposito = id_cajaDeposito;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
