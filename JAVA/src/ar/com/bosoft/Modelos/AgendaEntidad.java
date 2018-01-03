/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.Modelos;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class AgendaEntidad {
    String nombre, direccion, localidad, departamento, provincia,
            telefono1, telefono2, zona, email, cuit, 
            posIva, formaPago, secretaria, auditor, 
            nombreCompras, telCompras, emailCompras, 
            nombreTesoreria, telTesoreria, emailTesoreria, 
            nombreFarmacia, telFarmacia, emailFarmacia, 
            nombreContable, telContable, emailContable, gln;
    
    Double riesgoCredito;    

    public AgendaEntidad(String nombre, String direccion, String localidad, String departamento, String provincia, String telefono1, String telefono2, String zona, String email, String cuit, String posIva, String formaPago, String secretaria, String auditor, String nombreCompras, String telCompras, String emailCompras, String nombreTesoreria, String telTesoreria, String emailTesoreria, String nombreFarmacia, String telFarmacia, String emailFarmacia, String nombreContable, String telContable, String emailContable, Double riesgoCredito, String gln) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.departamento = departamento;
        this.provincia = provincia;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.zona = zona;
        this.email = email;
        this.cuit = cuit;
        this.posIva = posIva;
        this.formaPago = formaPago;
        this.secretaria = secretaria;
        this.auditor = auditor;
        this.nombreCompras = nombreCompras;
        this.telCompras = telCompras;
        this.emailCompras = emailCompras;
        this.nombreTesoreria = nombreTesoreria;
        this.telTesoreria = telTesoreria;
        this.emailTesoreria = emailTesoreria;
        this.nombreFarmacia = nombreFarmacia;
        this.telFarmacia = telFarmacia;
        this.emailFarmacia = emailFarmacia;
        this.nombreContable = nombreContable;
        this.telContable = telContable;
        this.emailContable = emailContable;
        this.riesgoCredito = riesgoCredito;
        this.gln = gln;
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

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getPosIva() {
        return posIva;
    }

    public void setPosIva(String posIva) {
        this.posIva = posIva;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getNombreCompras() {
        return nombreCompras;
    }

    public void setNombreCompras(String nombreCompras) {
        this.nombreCompras = nombreCompras;
    }

    public String getTelCompras() {
        return telCompras;
    }

    public void setTelCompras(String telCompras) {
        this.telCompras = telCompras;
    }

    public String getEmailCompras() {
        return emailCompras;
    }

    public void setEmailCompras(String emailCompras) {
        this.emailCompras = emailCompras;
    }

    public String getNombreTesoreria() {
        return nombreTesoreria;
    }

    public void setNombreTesoreria(String nombreTesoreria) {
        this.nombreTesoreria = nombreTesoreria;
    }

    public String getTelTesoreria() {
        return telTesoreria;
    }

    public void setTelTesoreria(String telTesoreria) {
        this.telTesoreria = telTesoreria;
    }

    public String getEmailTesoreria() {
        return emailTesoreria;
    }

    public void setEmailTesoreria(String emailTesoreria) {
        this.emailTesoreria = emailTesoreria;
    }

    public String getNombreFarmacia() {
        return nombreFarmacia;
    }

    public void setNombreFarmacia(String nombreFarmacia) {
        this.nombreFarmacia = nombreFarmacia;
    }

    public String getTelFarmacia() {
        return telFarmacia;
    }

    public void setTelFarmacia(String telFarmacia) {
        this.telFarmacia = telFarmacia;
    }

    public String getEmailFarmacia() {
        return emailFarmacia;
    }

    public void setEmailFarmacia(String emailFarmacia) {
        this.emailFarmacia = emailFarmacia;
    }

    public String getNombreContable() {
        return nombreContable;
    }

    public void setNombreContable(String nombreContable) {
        this.nombreContable = nombreContable;
    }

    public String getTelContable() {
        return telContable;
    }

    public void setTelContable(String telContable) {
        this.telContable = telContable;
    }

    public String getEmailContable() {
        return emailContable;
    }

    public void setEmailContable(String emailContable) {
        this.emailContable = emailContable;
    }

    public Double getRiesgoCredito() {
        return riesgoCredito;
    }

    public void setRiesgoCredito(Double riesgoCredito) {
        this.riesgoCredito = riesgoCredito;
    }

    public String getGln() {
        return gln;
    }

    public void setGln(String gln) {
        this.gln = gln;
    }
}
