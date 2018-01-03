/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.data;

import java.util.Date;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ProfesionalData {
    int id_profesional, id_localidad, id_departamento, id_provincia, id_zona, 
            id_especialidad, id_subespecialidad, id_entidad, id_vendedor;
    String nombre, direccion, codPostal, dni, matricula, contacto, perfil, email, telParticular, telMovil, telOtros, telConsultorio,
            secretaria, dirConsultorio, observaciones, habilita, provincia, departamento, localidad, especialidad, subespecialidad,
            zona, vendedor, entidad;
    Date fechaNac;

    public ProfesionalData() {
        this.id_profesional = 0;
        this.id_localidad = 0;
        this.id_departamento = 0;
        this.id_provincia = 0;
        this.id_zona = 0;
        this.id_especialidad = 0;
        this.id_subespecialidad = 0;
        this.id_entidad = 0;
        this.id_vendedor = 0;
        this.nombre = "";
        this.direccion = "";
        this.codPostal = "";
        this.dni = "";
        this.matricula = "";
        this.contacto = "";
        this.perfil = "";
        this.email = "";
        this.telParticular = "";
        this.telMovil = "";
        this.telOtros = "";
        this.telConsultorio = "";
        this.secretaria = "";
        this.dirConsultorio = "";
        this.observaciones = "";
        this.habilita = "";
        this.provincia = "";
        this.departamento = "";
        this.localidad = "";
        this.especialidad = "";
        this.subespecialidad = "";
        this.zona = "";
        this.vendedor = "";
        this.entidad = "";
        this.fechaNac = null;
    }

    public int getId_profesional() {
        return id_profesional;
    }

    public void setId_profesional(int id_profesional) {
        this.id_profesional = id_profesional;
    }

    public int getId_localidad() {
        return id_localidad;
    }

    public void setId_localidad(int id_localidad) {
        this.id_localidad = id_localidad;
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }

    public int getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(int id_provincia) {
        this.id_provincia = id_provincia;
    }

    public int getId_zona() {
        return id_zona;
    }

    public void setId_zona(int id_zona) {
        this.id_zona = id_zona;
    }

    public int getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
    }

    public int getId_subespecialidad() {
        return id_subespecialidad;
    }

    public void setId_subespecialidad(int id_subespecialidad) {
        this.id_subespecialidad = id_subespecialidad;
    }

    public int getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(int id_entidad) {
        this.id_entidad = id_entidad;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
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

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTelOtros() {
        return telOtros;
    }

    public void setTelOtros(String telOtros) {
        this.telOtros = telOtros;
    }

    public String getTelConsultorio() {
        return telConsultorio;
    }

    public void setTelConsultorio(String telConsultorio) {
        this.telConsultorio = telConsultorio;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getDirConsultorio() {
        return dirConsultorio;
    }

    public void setDirConsultorio(String dirConsultorio) {
        this.dirConsultorio = dirConsultorio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getHabilita() {
        return habilita;
    }

    public void setHabilita(String habilita) {
        this.habilita = habilita;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getSubespecialidad() {
        return subespecialidad;
    }

    public void setSubespecialidad(String subespecialidad) {
        this.subespecialidad = subespecialidad;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
}
