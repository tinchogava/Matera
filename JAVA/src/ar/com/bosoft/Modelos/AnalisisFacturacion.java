/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.Modelos;

import java.math.BigDecimal;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class AnalisisFacturacion {
    String zona, tipoOperacion, agente;
    int id_presupuesto;
    BigDecimal total, aliPercIIBB, percIIBB, iva21, iva105, netoExento, netoX, neto21, neto105, iibb, rm, importeFlotacion, costoVentas;

    public AnalisisFacturacion(String zona, String agente, String tipoOperacion, int id_presupuesto, BigDecimal total, BigDecimal aliPercIIBB, 
            BigDecimal percIIBB, BigDecimal iva21, BigDecimal iva105, BigDecimal netoExento, BigDecimal netoX, 
            BigDecimal neto21, BigDecimal neto105, BigDecimal iibb, BigDecimal rm, BigDecimal importeFlotacion, 
            BigDecimal costoVentas) {
        this.zona = zona;
        this.tipoOperacion = tipoOperacion;
        this.id_presupuesto = id_presupuesto;
        this.total = total;
        this.aliPercIIBB = aliPercIIBB;
        this.percIIBB = percIIBB;
        this.iva21 = iva21;
        this.iva105 = iva105;
        this.netoExento = netoExento;
        this.netoX = netoX;
        this.neto21 = neto21;
        this.neto105 = neto105;
        this.iibb = iibb;
        this.rm = rm;
        this.importeFlotacion = importeFlotacion;
        this.costoVentas = costoVentas;
        this.agente = agente;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public BigDecimal getCostoVentas() {
        return costoVentas;
    }

    public void setCostoVentas(BigDecimal costoVentas) {
        this.costoVentas = costoVentas;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAliPercIIBB() {
        return aliPercIIBB;
    }

    public void setAliPercIIBB(BigDecimal aliPercIIBB) {
        this.aliPercIIBB = aliPercIIBB;
    }

    public BigDecimal getPercIIBB() {
        return percIIBB;
    }

    public void setPercIIBB(BigDecimal percIIBB) {
        this.percIIBB = percIIBB;
    }

    public BigDecimal getIva21() {
        return iva21;
    }

    public void setIva21(BigDecimal iva21) {
        this.iva21 = iva21;
    }

    public BigDecimal getIva105() {
        return iva105;
    }

    public void setIva105(BigDecimal iva105) {
        this.iva105 = iva105;
    }

    public BigDecimal getNetoExento() {
        return netoExento;
    }

    public void setNetoExento(BigDecimal netoExento) {
        this.netoExento = netoExento;
    }

    public BigDecimal getNetoX() {
        return netoX;
    }

    public void setNetoX(BigDecimal netoX) {
        this.netoX = netoX;
    }

    public BigDecimal getNeto21() {
        return neto21;
    }

    public void setNeto21(BigDecimal neto21) {
        this.neto21 = neto21;
    }

    public BigDecimal getNeto105() {
        return neto105;
    }

    public void setNeto105(BigDecimal neto105) {
        this.neto105 = neto105;
    }

    public BigDecimal getIibb() {
        return iibb;
    }

    public void setIibb(BigDecimal iibb) {
        this.iibb = iibb;
    }

    public BigDecimal getRm() {
        return rm;
    }

    public void setRm(BigDecimal rm) {
        this.rm = rm;
    }

    public BigDecimal getImporteFlotacion() {
        return importeFlotacion;
    }

    public void setImporteFlotacion(BigDecimal importeFlotacion) {
        this.importeFlotacion = importeFlotacion;
    }
}
