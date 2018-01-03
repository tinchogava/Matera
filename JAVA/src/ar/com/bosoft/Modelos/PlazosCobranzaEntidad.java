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
public class PlazosCobranzaEntidad {
    String entidad;
    int dias;
    BigDecimal importeAplicado;

    public PlazosCobranzaEntidad(String entidad, int dias, BigDecimal importeAplicado) {
        this.entidad = entidad;
        this.dias = dias;
        this.importeAplicado = importeAplicado;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public BigDecimal getImporteAplicado() {
        return importeAplicado;
    }

    public void setImporteAplicado(BigDecimal importeAplicado) {
        this.importeAplicado = importeAplicado;
    }
}
