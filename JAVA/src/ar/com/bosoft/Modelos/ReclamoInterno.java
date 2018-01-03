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
public class ReclamoInterno {
    int id_reclamo, id_cirugia;
    String tipoReclamo, paciente, profesional, prestacion, lugarCirugia, sectorReclamo, descripcion, procedimientos, acciones, fechaCirugia, fechaReclamo;
    //Date fechaReclamo;//, fechaCirugia;

    public ReclamoInterno(int id_reclamo, int id_cirugia, String tipoReclamo, String paciente, String profesional, String prestacion, String lugarCirugia, String sectorReclamo, String descripcion, String procedimientos, String acciones, String fechaReclamo, String fechaCirugia) {
        this.id_reclamo = id_reclamo;
        this.id_cirugia = id_cirugia;
        this.tipoReclamo = tipoReclamo;
        this.paciente = paciente;
        this.profesional = profesional;
        this.prestacion = prestacion;
        this.lugarCirugia = lugarCirugia;
        this.sectorReclamo = sectorReclamo;
        this.descripcion = descripcion;
        this.procedimientos = procedimientos;
        this.acciones = acciones;
        this.fechaReclamo = fechaReclamo;
        this.fechaCirugia = fechaCirugia;
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

    public String getSectorReclamo() {
        return sectorReclamo;
    }

    public void setSectorReclamo(String sectorReclamo) {
        this.sectorReclamo = sectorReclamo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProcedimientos() {
        return procedimientos;
    }

    public void setProcedimientos(String procedimientos) {
        this.procedimientos = procedimientos;
    }

    public String getAcciones() {
        return acciones;
    }

    public void setAcciones(String acciones) {
        this.acciones = acciones;
    }

    public String getFechaReclamo() {
        return fechaReclamo;
    }

    public void setFechaReclamo(String fechaReclamo) {
        this.fechaReclamo = fechaReclamo;
    }

    public String getFechaCirugia() {
        return fechaCirugia;
    }

    public void setFechaCirugia(String fechaCirugia) {
        this.fechaCirugia = fechaCirugia;
    }
    
    
    
}
