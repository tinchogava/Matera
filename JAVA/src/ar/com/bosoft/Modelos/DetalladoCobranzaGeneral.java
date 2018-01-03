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
public class DetalladoCobranzaGeneral {
    Date fechaRecibo, fechaFactura;
    String numRecibo, numFactura, tipoOperacion;
    int id_presupuesto;
    BigDecimal importeAplicado, rm1, rm2;

    public DetalladoCobranzaGeneral(Date fechaRecibo, Date fechaFactura, String numRecibo, String numFactura, String tipoOperacion, int id_presupuesto, BigDecimal importeAplicado, BigDecimal rm1, BigDecimal rm2) {
        this.fechaRecibo = fechaRecibo;
        this.fechaFactura = fechaFactura;
        this.numRecibo = numRecibo;
        this.numFactura = numFactura;
        this.tipoOperacion = tipoOperacion;
        this.id_presupuesto = id_presupuesto;
        this.importeAplicado = importeAplicado;
        this.rm1 = rm1;
        this.rm2 = rm2;
    }

    public Date getFechaRecibo() {
        return fechaRecibo;
    }

    public void setFechaRecibo(Date fechaRecibo) {
        this.fechaRecibo = fechaRecibo;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getNumRecibo() {
        return numRecibo;
    }

    public void setNumRecibo(String numRecibo) {
        this.numRecibo = numRecibo;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
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

    public BigDecimal getImporteAplicado() {
        return importeAplicado;
    }

    public void setImporteAplicado(BigDecimal importeAplicado) {
        this.importeAplicado = importeAplicado;
    }

    public BigDecimal getRm1() {
        return rm1;
    }

    public void setRm1(BigDecimal rm1) {
        this.rm1 = rm1;
    }

    public BigDecimal getRm2() {
        return rm2;
    }

    public void setRm2(BigDecimal rm2) {
        this.rm2 = rm2;
    }
}
