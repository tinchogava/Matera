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
public class PlazosCobranzaGeneral {
    int dias;
    BigDecimal importeAplicado;

    public PlazosCobranzaGeneral(int dias, BigDecimal importeAplicado) {
        this.dias = dias;
        this.importeAplicado = importeAplicado;
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
