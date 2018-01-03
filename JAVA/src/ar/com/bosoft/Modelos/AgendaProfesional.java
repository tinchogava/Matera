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
public class AgendaProfesional {
    String nombre, direccion, localidad, departamento, provincia,
            telParticular, telMovil, telConsultorio, telOtro, email,
            especialidad, secretaria, entidad, dni, matricula,
            vendedor, zona, dirConsultorio;
    Date fechaNac;

    public AgendaProfesional(String nombre, String direccion, String localidad, String departamento, String provincia, String telParticular, String telMovil, String telConsultorio, String telOtro, String email, String especialidad, String secretaria, String entidad, String dni, String matricula, String vendedor, String zona, String dirConsultorio, Date fechaNac) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.departamento = departamento;
        this.provincia = provincia;
        this.telParticular = telParticular;
        this.telMovil = telMovil;
        this.telConsultorio = telConsultorio;
        this.telOtro = telOtro;
        this.email = email;
        this.especialidad = especialidad;
        this.secretaria = secretaria;
        this.entidad = entidad;
        this.dni = dni;
        this.matricula = matricula;
        this.vendedor = vendedor;
        this.zona = zona;
        this.dirConsultorio = dirConsultorio;
        this.fechaNac = fechaNac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelParticular() {
        return telParticular;
    }

    public void setTelParticular(String telParticular) {
        this.telParticular = telParticular;
    }

    public String getTelMovil() {
        return telMovil;
    }

    public void setTelMovil(String telMovil) {
        this.telMovil = telMovil;
    }

    public String getTelConsultorio() {
        return telConsultorio;
    }

    public void setTelConsultorio(String telConsultorio) {
        this.telConsultorio = telConsultorio;
    }

    public String getTelOtro() {
        return telOtro;
    }

    public void setTelOtro(String telOtro) {
        this.telOtro = telOtro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDirConsultorio() {
        return dirConsultorio;
    }

    public void setDirConsultorio(String dirConsultorio) {
        this.dirConsultorio = dirConsultorio;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
}
