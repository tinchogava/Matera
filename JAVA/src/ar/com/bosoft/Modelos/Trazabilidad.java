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
public class Trazabilidad {
    int id_presupuesto, cantidad;
    Date fechaCx;
    String producto, remito, lote, paciente, profesional, lugarCx, entidad, serie;

    public Trazabilidad(int id_presupuesto, int cantidad, Date fechaCx, String producto, String remito, String lote, String serie, String paciente, String profesional, String lugarCx, String entidad) {
        this.id_presupuesto = id_presupuesto;
        this.cantidad = cantidad;
        this.fechaCx = fechaCx;
        this.producto = producto;
        this.remito = remito;
        this.lote = lote;
        this.serie = serie;
        this.paciente = paciente;
        this.profesional = profesional;
        this.lugarCx = lugarCx;
        this.entidad = entidad;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaCx() {
        return fechaCx;
    }

    public void setFechaCx(Date fechaCx) {
        this.fechaCx = fechaCx;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }     

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getLugarCx() {
        return lugarCx;
    }

    public void setLugarCx(String lugarCx) {
        this.lugarCx = lugarCx;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }
}
