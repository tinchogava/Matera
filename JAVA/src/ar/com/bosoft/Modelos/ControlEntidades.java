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
public class ControlEntidades {
    String zona, entidad;
    int cantPresuEmitidos, cantPresuAprobados, cantPresuFacturados;
    BigDecimal montoPresuEmitidos, montoPresuAprobados, montoFactTotal, factZona;

    public ControlEntidades(String zona, String entidad, int cantPresuEmitidos, int cantPresuAprobados, int cantPresuFacturados, BigDecimal montoPresuEmitidos, BigDecimal montoPresuAprobados, BigDecimal montoFactTotal, BigDecimal factZona) {
        this.zona = zona;
        this.entidad = entidad;
        this.cantPresuEmitidos = cantPresuEmitidos;
        this.cantPresuAprobados = cantPresuAprobados;
        this.cantPresuFacturados = cantPresuFacturados;
        this.montoPresuEmitidos = montoPresuEmitidos;
        this.montoPresuAprobados = montoPresuAprobados;
        this.montoFactTotal = montoFactTotal;
        this.factZona = factZona;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public int getCantPresuEmitidos() {
        return cantPresuEmitidos;
    }

    public void setCantPresuEmitidos(int cantPresuEmitidos) {
        this.cantPresuEmitidos = cantPresuEmitidos;
    }

    public int getCantPresuAprobados() {
        return cantPresuAprobados;
    }

    public void setCantPresuAprobados(int cantPresuAprobados) {
        this.cantPresuAprobados = cantPresuAprobados;
    }

    public int getCantPresuFacturados() {
        return cantPresuFacturados;
    }

    public void setCantPresuFacturados(int cantPresuFacturados) {
        this.cantPresuFacturados = cantPresuFacturados;
    }

    public BigDecimal getMontoPresuEmitidos() {
        return montoPresuEmitidos;
    }

    public void setMontoPresuEmitidos(BigDecimal montoPresuEmitidos) {
        this.montoPresuEmitidos = montoPresuEmitidos;
    }

    public BigDecimal getMontoPresuAprobados() {
        return montoPresuAprobados;
    }

    public void setMontoPresuAprobados(BigDecimal montoPresuAprobados) {
        this.montoPresuAprobados = montoPresuAprobados;
    }

    public BigDecimal getMontoFactTotal() {
        return montoFactTotal;
    }

    public void setMontoFactTotal(BigDecimal montoFactTotal) {
        this.montoFactTotal = montoFactTotal;
    }

    public BigDecimal getFactZona() {
        return factZona;
    }

    public void setFactZona(BigDecimal factZona) {
        this.factZona = factZona;
    }
}
