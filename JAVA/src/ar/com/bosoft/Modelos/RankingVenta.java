/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class RankingVenta {
    String cabecera, registro;
    BigDecimal valor, facturado, efectividad;
    BigDecimal oneHundred = new BigDecimal(100);

    public RankingVenta(String cabecera, String registro, BigDecimal cantidad, BigDecimal facturado) {
        this.cabecera = cabecera;
        this.registro = registro;
        this.valor = cantidad;
        this.facturado = facturado;
        this.efectividad = new BigDecimal(facturado.doubleValue()/valor.doubleValue());
    }

    public BigDecimal getEfectividad() {
        return efectividad;
    }

    public void setEfectividad(BigDecimal efectividad) {
        this.efectividad = efectividad;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
    
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getFacturado() {
        return facturado;
    }

    public void setFacturado(BigDecimal facturado) {
        this.facturado = facturado;
    }
}
