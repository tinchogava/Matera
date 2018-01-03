package ar.com.bosoft.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ProductoData {
    int id_producto, id_codConsumo, id_moneda, id_clasiProducto, id_proveedor, stockMinimo;
    String codigo, gtin, nombre, pm, habilita, codConsumo, moneda, clasiProducto, proveedor;
    Double costo, costoPesos;

    public ProductoData() {
        this.id_producto = 0;
        this.id_codConsumo = 0;
        this.id_moneda = 0;
        this.id_clasiProducto = 0;
        this.id_proveedor = 0;
        this.nombre = "";
        this.pm = "";
        this.codigo = "";
        this.gtin = "";
        this.habilita = "";
        this.codConsumo = "";
        this.moneda = "";
        this.clasiProducto = "";
        this.proveedor = "";
        this.costo = 0.000;
        this.costoPesos = 0.00;
        this.stockMinimo = 0;
    }
    
    public ProductoData(ResultSet rs){
        try {
            this.id_producto = rs.getInt("id_producto");
            this.id_codConsumo = rs.getInt("id_codConsumo");
            this.id_moneda = rs.getInt("id_moneda");
            this.id_clasiProducto = rs.getInt("id_clasiProducto");
            this.id_proveedor = rs.getInt("id_proveedor");
            this.nombre = rs.getString("nombre");
            this.pm = rs.getString("pm");
            this.codigo = rs.getString("codigo");
            this.gtin = rs.getString("gtin");
            this.habilita = rs.getString("habilita");
            this.codConsumo = rs.getString("codConsumo");
            this.moneda = rs.getString("moneda");
            this.clasiProducto = rs.getString("clasiProducto");
            this.proveedor = rs.getString("proveedor");
            this.costo = rs.getDouble("costo");
            this.costoPesos = rs.getDouble("costoPesos");
            this.stockMinimo = rs.getInt("stockMinimo");
        } catch (SQLException ex) {
            Logger.getLogger(ProductoData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_codConsumo() {
        return id_codConsumo;
    }

    public void setId_codConsumo(int id_codConsumo) {
        this.id_codConsumo = id_codConsumo;
    }

    public int getId_moneda() {
        return id_moneda;
    }

    public void setId_moneda(int id_moneda) {
        this.id_moneda = id_moneda;
    }

    public int getId_clasiProducto() {
        return id_clasiProducto;
    }

    public void setId_clasiProducto(int id_clasiProducto) {
        this.id_clasiProducto = id_clasiProducto;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getHabilita() {
        return habilita;
    }

    public void setHabilita(String habilita) {
        this.habilita = habilita;
    }

    public String getCodConsumo() {
        return codConsumo;
    }

    public void setCodConsumo(String codConsumo) {
        this.codConsumo = codConsumo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getClasiProducto() {
        return clasiProducto;
    }

    public void setClasiProducto(String clasiProducto) {
        this.clasiProducto = clasiProducto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getCostoPesos() {
        return costoPesos;
    }

    public void setCostoPesos(Double costoPesos) {
        this.costoPesos = costoPesos;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
}
