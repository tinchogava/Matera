package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class MayorDevengadoDetallado {
    String nombre, paciente, tipoOperacion, observaciones;
    BigDecimal debe, haber, acumulado;
    Date fechaCirugia, fechaFactura;
    int id_presupuesto;

    public MayorDevengadoDetallado(String nombre, String paciente, String tipoOperacion, String observaciones, BigDecimal debe, BigDecimal haber, BigDecimal acumulado, Date fechaCirugia, Date fechaFactura, int id_presupuesto) {
        this.nombre = nombre;
        this.paciente = paciente;
        this.tipoOperacion = tipoOperacion;
        this.observaciones = observaciones;
        this.debe = debe;
        this.haber = haber;
        this.acumulado = acumulado;
        this.fechaCirugia = fechaCirugia;
        this.fechaFactura = fechaFactura;
        this.id_presupuesto = id_presupuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public BigDecimal getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(BigDecimal acumulado) {
        this.acumulado = acumulado;
    }

    public Date getFechaCirugia() {
        return fechaCirugia;
    }

    public void setFechaCirugia(Date fechaCirugia) {
        this.fechaCirugia = fechaCirugia;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }
}
