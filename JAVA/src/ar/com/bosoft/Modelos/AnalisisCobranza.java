/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class AnalisisCobranza {
    String zona, vendedor, numComp, tipoOperacion;
    int id_presupuesto;
    Date fecha;
    BigDecimal importeAplicado, impuestos, rm, neto;    

    public AnalisisCobranza(String zona, String vendedor, String numComp, String tipoOperacion, int id_presupuesto, Date fecha, BigDecimal importeAplicado, BigDecimal impuestos, BigDecimal rm, BigDecimal neto) {
        this.zona = zona;
        this.vendedor = vendedor;
        this.numComp = numComp;
        this.tipoOperacion = tipoOperacion;
        this.id_presupuesto = id_presupuesto;
        this.fecha = fecha;
        this.importeAplicado = importeAplicado;
        this.impuestos = impuestos;
        this.rm = rm;
        this.neto = neto;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getNumComp() {
        return numComp;
    }

    public void setNumComp(String numComp) {
        this.numComp = numComp;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getImporteAplicado() {
        return importeAplicado;
    }

    public void setImporteAplicado(BigDecimal importeAplicado) {
        this.importeAplicado = importeAplicado;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getRm() {
        return rm;
    }

    public void setRm(BigDecimal rm) {
        this.rm = rm;
    }

    public BigDecimal getNeto() {
        return neto;
    }

    public void setNeto(BigDecimal neto) {
        this.neto = neto;
    }
}
