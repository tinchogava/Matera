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
public class AgendaProveedor {
    String nombre, direccion, localidad, departamento, provincia,
            codPostal, telefono1, telefono2, email, cuentaBanco, 
            formaPago, cuit, gerente, 
            nombrePagos, telPagos, emailPagos, 
            nombreAtencion, telAtencion, emailAtencion, 
            nombreDeposito, telDeposito, emailDeposito,
            gln;

    public AgendaProveedor(String nombre, String direccion, String localidad, String departamento, String provincia, String codPostal, String telefono1, String telefono2, String email, String cuentaBanco, String formaPago, String cuit, String gerente, String nombrePagos, String telPagos, String emailPagos, String nombreAtencion, String telAtencion, String emailAtencion, String nombreDeposito, String telDeposito, String emailDeposito, String gln) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.departamento = departamento;
        this.provincia = provincia;
        this.codPostal = codPostal;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.email = email;
        this.cuentaBanco = cuentaBanco;
        this.formaPago = formaPago;
        this.cuit = cuit;
        this.gerente = gerente;
        this.nombrePagos = nombrePagos;
        this.telPagos = telPagos;
        this.emailPagos = emailPagos;
        this.nombreAtencion = nombreAtencion;
        this.telAtencion = telAtencion;
        this.emailAtencion = emailAtencion;
        this.nombreDeposito = nombreDeposito;
        this.telDeposito = telDeposito;
        this.emailDeposito = emailDeposito;
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

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public String getNombrePagos() {
        return nombrePagos;
    }

    public void setNombrePagos(String nombrePagos) {
        this.nombrePagos = nombrePagos;
    }

    public String getTelPagos() {
        return telPagos;
    }

    public void setTelPagos(String telPagos) {
        this.telPagos = telPagos;
    }

    public String getEmailPagos() {
        return emailPagos;
    }

    public void setEmailPagos(String emailPagos) {
        this.emailPagos = emailPagos;
    }

    public String getNombreAtencion() {
        return nombreAtencion;
    }

    public void setNombreAtencion(String nombreAtencion) {
        this.nombreAtencion = nombreAtencion;
    }

    public String getTelAtencion() {
        return telAtencion;
    }

    public void setTelAtencion(String telAtencion) {
        this.telAtencion = telAtencion;
    }

    public String getEmailAtencion() {
        return emailAtencion;
    }

    public void setEmailAtencion(String emailAtencion) {
        this.emailAtencion = emailAtencion;
    }

    public String getNombreDeposito() {
        return nombreDeposito;
    }

    public void setNombreDeposito(String nombreDeposito) {
        this.nombreDeposito = nombreDeposito;
    }

    public String getTelDeposito() {
        return telDeposito;
    }

    public void setTelDeposito(String telDeposito) {
        this.telDeposito = telDeposito;
    }

    public String getEmailDeposito() {
        return emailDeposito;
    }

    public void setEmailDeposito(String emailDeposito) {
        this.emailDeposito = emailDeposito;
    }

    public String getGln() {
        return gln;
    }

    public void setGln(String gln) {
        this.gln = gln;
    }
}
