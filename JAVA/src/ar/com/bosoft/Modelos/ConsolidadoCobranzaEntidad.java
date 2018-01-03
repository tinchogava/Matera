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
public class ConsolidadoCobranzaEntidad {
    int year;
    String entidad, mes;
    BigDecimal importeAplicado;

    public ConsolidadoCobranzaEntidad(int year, String entidad, String mes, BigDecimal importeAplicado) {
        this.year = year;
        this.entidad = entidad;
        this.mes = mes;
        this.importeAplicado = importeAplicado;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public BigDecimal getImporteAplicado() {
        return importeAplicado;
    }

    public void setImporteAplicado(BigDecimal importeAplicado) {
        this.importeAplicado = importeAplicado;
    }
}
