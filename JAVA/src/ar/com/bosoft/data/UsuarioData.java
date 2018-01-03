package ar.com.bosoft.data;

import java.util.Date;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class UsuarioData {
    int id_usuario, id_provincia, id_departamento, id_localidad, id_zona, id_zonaSistema;
    String nombre, documento, cuil, direccion, codPostal,
            usaSistema, contraseña, habilita, email, provincia,
            departamento, localidad;            
    Date fechaNac, fechaIngreso, fechaEgreso;

    public UsuarioData() {
        this.id_usuario = 0;
        this.id_provincia = 0;
        this.id_departamento = 0;
        this.id_localidad = 0;
        this.id_zona = 0;
        this.id_zonaSistema = 0;
        this.nombre = "";
        this.documento = "";
        this.cuil = "";
        this.fechaNac = null;
        this.direccion = "";
        this.codPostal = "";
        this.fechaIngreso = null;
        this.fechaEgreso = null;
        this.usaSistema = "";
        this.contraseña = "";
        this.habilita = "";
        this.email = "";
        this.provincia = "";
        this.departamento = "";
        this.localidad = "";        
    }   

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(int id_provincia) {
        this.id_provincia = id_provincia;
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }

    public int getId_localidad() {
        return id_localidad;
    }

    public void setId_localidad(int id_localidad) {
        this.id_localidad = id_localidad;
    }

    public int getId_zona() {
        return id_zona;
    }

    public void setId_zona(int id_zona) {
        this.id_zona = id_zona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
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

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public String getUsaSistema() {
        return usaSistema;
    }

    public void setUsaSistema(String usaSistema) {
        this.usaSistema = usaSistema;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getHabilita() {
        return habilita;
    }

    public void setHabilita(String habilita) {
        this.habilita = habilita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getId_zonaSistema() {
        return id_zonaSistema;
    }

    public void setId_zonaSistema(int id_zonaSistema) {
        this.id_zonaSistema = id_zonaSistema;
    }
}
