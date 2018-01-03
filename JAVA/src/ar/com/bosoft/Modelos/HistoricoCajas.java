/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.Modelos;

import java.util.Date;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class HistoricoCajas {
    String caja, id, lugarCirugia, zona, profesional, paciente, remito, factura;
    int id_presupuesto;
    Date fechaCirugia;

    public HistoricoCajas(String caja, String id, String lugarCirugia, String zona, String profesional, String paciente, String remito, int id_presupuesto, Date fechaCirugia, String factura) {
        this.caja = caja;
        this.id = id;
        this.lugarCirugia = lugarCirugia;
        this.zona = zona;
        this.profesional = profesional;
        this.paciente = paciente;
        this.remito = remito;
        this.id_presupuesto = id_presupuesto;
        this.fechaCirugia = fechaCirugia;
        this.factura = factura;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLugarCirugia() {
        return lugarCirugia;
    }

    public void setLugarCirugia(String lugarCirugia) {
        this.lugarCirugia = lugarCirugia;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public Date getFechaCirugia() {
        return fechaCirugia;
    }

    public void setFechaCirugia(Date fechaCirugia) {
        this.fechaCirugia = fechaCirugia;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }
}
