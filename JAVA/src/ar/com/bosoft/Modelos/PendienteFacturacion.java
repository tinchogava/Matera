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
public class PendienteFacturacion {
    String entidad, lugarCx, profesional, paciente,  circuito, orden, protocolo, rx, remito, firma, sticker, observaciones
            , completo;
    int id_presupuesto;
    Date fechaCx;
    BigDecimal montoPresupuesto, montoFacturado;

    public PendienteFacturacion(String entidad, String lugarCx, String profesional, String paciente, Date fechaCx, 
            int id_presupuesto, String  circuito, String orden, String protocolo, String rx, String remito, 
            String firma, String sticker, BigDecimal montoPresupuesto, BigDecimal montoFacturado, String observaciones, String completo) {
        this.entidad = entidad;
        this.lugarCx = lugarCx;
        this.profesional = profesional;
        this.paciente = paciente;
        this.fechaCx = fechaCx;
        this.id_presupuesto = id_presupuesto;
        this.circuito = circuito;
        this.orden = orden;
        this.protocolo = protocolo;
        this.rx = rx;
        this.remito = firma;
        this.firma = firma;
        this.sticker = sticker;
        this.montoPresupuesto = montoPresupuesto;
        this.montoFacturado = montoFacturado;
        this.observaciones = observaciones;
        this.completo = completo;
    }

    public String getCompleto() {
        return completo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getLugarCx() {
        return lugarCx;
    }

    public void setLugarCx(String lugarCx) {
        this.lugarCx = lugarCx;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public Date getFechaCx() {
        return fechaCx;
    }

    public void setFechaCx(Date fechaCx) {
        this.fechaCx = fechaCx;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public String getCircuito() {
        return circuito;
    }

    public void setCircuito(String circuito) {
        this.circuito = circuito;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getRx() {
        return rx;
    }

    public void setRx(String rx) {
        this.rx = rx;
    }

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getSticker() {
        return sticker;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }
    
    public BigDecimal getMontoPresupuesto() {
        return montoPresupuesto;
    }

    public void setMontoPresupuesto(BigDecimal montoPresupuesto) {
        this.montoPresupuesto = montoPresupuesto;
    }

    public BigDecimal getMontoFacturado() {
        return montoFacturado;
    }

    public void setMontoFacturado(BigDecimal montoFacturado) {
        this.montoFacturado = montoFacturado;
    }
}
