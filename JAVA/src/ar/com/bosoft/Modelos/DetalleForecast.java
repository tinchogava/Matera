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
public class DetalleForecast {
    int id_presupuesto;
    Date fecha;
    String paciente, direccion, telefono, obsProducto, observaciones, profesional, entidad;
    BigDecimal total;
    int cantidad;

    public DetalleForecast(int id_presupuesto, Date fecha, String paciente, String direccion, String telefono, String obsProducto, String observaciones, BigDecimal total, int cantidad, String profesional, String entidad) {
        this.id_presupuesto = id_presupuesto;
        this.fecha = fecha;
        this.paciente = paciente;
        this.direccion = direccion;
        this.telefono = telefono;
        this.obsProducto = obsProducto;
        this.observaciones = observaciones;
        this.total = total;
        this.cantidad = cantidad;
        this.profesional = profesional;
        this.entidad = entidad;
    }

    /*
    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getObsProducto() {
        return obsProducto;
    }

    public void setObsProducto(String obsProducto) {
        this.obsProducto = obsProducto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    */
}
