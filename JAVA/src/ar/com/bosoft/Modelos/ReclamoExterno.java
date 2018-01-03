/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.Modelos;

/**
 *
 * @author tinchogava
 */
public class ReclamoExterno {
    int id_reclamo, id_cirugia;
    String tipoReclamo, paciente, profesional, prestacion, lugarCirugia, 
            destinoReclamo, descripcion, acciones, fechaCirugia, fechaReclamo, fechaNotificacion,
                recibe, direccion, telefono, fechaDevolucion;

    public ReclamoExterno(int id_reclamo, String tipoReclamo, String paciente, String profesional, String prestacion, String lugarCirugia, String destinoReclamo, String descripcion, String acciones, String fechaReclamo, String fechaNotificacion, String recibe, String direccion, String telefono) {
        this.id_reclamo = id_reclamo;
        this.tipoReclamo = tipoReclamo;
        this.paciente = paciente;
        this.profesional = profesional;
        this.prestacion = prestacion;
        this.lugarCirugia = lugarCirugia;
        this.destinoReclamo = destinoReclamo;
        this.descripcion = descripcion;
        this.acciones = acciones;
        this.fechaReclamo = fechaReclamo;
        this.fechaNotificacion = fechaNotificacion;
        this.recibe = recibe;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId_reclamo() {
        return id_reclamo;
    }

    public void setId_reclamo(int id_reclamo) {
        this.id_reclamo = id_reclamo;
    }

    public int getId_cirugia() {
        return id_cirugia;
    }

    public void setId_cirugia(int id_cirugia) {
        this.id_cirugia = id_cirugia;
    }

    public String getTipoReclamo() {
        return tipoReclamo;
    }

    public void setTipoReclamo(String tipoReclamo) {
        this.tipoReclamo = tipoReclamo;
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

    public String getPrestacion() {
        return prestacion;
    }

    public void setPrestacion(String prestacion) {
        this.prestacion = prestacion;
    }

    public String getLugarCirugia() {
        return lugarCirugia;
    }

    public void setLugarCirugia(String lugarCirugia) {
        this.lugarCirugia = lugarCirugia;
    }

    public String getDestinoReclamo() {
        return destinoReclamo;
    }

    public void setDestinoReclamo(String destinoReclamo) {
        this.destinoReclamo = destinoReclamo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAcciones() {
        return acciones;
    }

    public void setAcciones(String acciones) {
        this.acciones = acciones;
    }

    public String getFechaCirugia() {
        return fechaCirugia;
    }

    public void setFechaCirugia(String fechaCirugia) {
        this.fechaCirugia = fechaCirugia;
    }

    public String getFechaReclamo() {
        return fechaReclamo;
    }

    public void setFechaReclamo(String fechaReclamo) {
        this.fechaReclamo = fechaReclamo;
    }

    public String getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(String fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getRecibe() {
        return recibe;
    }

    public void setRecibe(String recibe) {
        this.recibe = recibe;
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

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    
}
