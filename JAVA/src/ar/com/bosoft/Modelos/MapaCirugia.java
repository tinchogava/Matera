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
public class MapaCirugia {
    int id_presupuesto;
    String caja, profesional, entidad, remito, paciente, lugarCirugia;
    Date fechaCirugia;
    BigDecimal importePresupuesto;

    public MapaCirugia(int id_presupuesto, String caja, String profesional, String entidad, String remito, String paciente, String lugarCirugia, Date fechaCirugia, BigDecimal importePresupuesto) {
        this.id_presupuesto = id_presupuesto;
        this.caja = caja;
        this.profesional = profesional;
        this.entidad = entidad;
        this.remito = remito;
        this.paciente = paciente;
        this.lugarCirugia = lugarCirugia;
        this.fechaCirugia = fechaCirugia;
        this.importePresupuesto = importePresupuesto;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getLugarCirugia() {
        return lugarCirugia;
    }

    public void setLugarCirugia(String lugarCirugia) {
        this.lugarCirugia = lugarCirugia;
    }

    public Date getFechaCirugia() {
        return fechaCirugia;
    }

    public void setFechaCirugia(Date fechaCirugia) {
        this.fechaCirugia = fechaCirugia;
    }

    public BigDecimal getImportePresupuesto() {
        return importePresupuesto;
    }

    public void setImportePresupuesto(BigDecimal importePresupuesto) {
        this.importePresupuesto = importePresupuesto;
    }
}
