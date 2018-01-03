/**
 * Derechos de uso otrogados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Informáticos Integrales
 * www.bosoft.com.ar
 */

package ar.com.bosoft.data;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ProveedorData {
    int id_proveedor, id_localidad, id_departamento, id_provincia, id_formaPago;
    String nombre, direccion, codPostal, telefono1, telefono2, email, cuit, gerente, cuentaBanco, 
            observaciones, habilita, localidad, departamento, provincia, formaPago, 
            pagoNombre, pagoTelefono, pagoEmail, clienteNombre, clienteTelefono, clienteEmail, 
            depositoNombre, depositoTelefono, depositoEmail, gln;

    public ProveedorData() {
        this.id_proveedor = 0;
        this.id_localidad = 0;
        this.id_departamento = 0;
        this.id_provincia = 0;
        this.id_formaPago = 0;
        this.nombre = "";
        this.direccion = "";
        this.codPostal = "";
        this.telefono1 = "";
        this.telefono2 = "";
        this.email = "";
        this.cuit = "";
        this.gerente = "";
        this.cuentaBanco = "";
        this.observaciones = "";
        this.habilita = "";
        this.localidad = "";
        this.departamento = "";
        this.provincia = "";
        this.formaPago = "";
        this.pagoNombre = "";
        this.pagoTelefono = "";
        this.pagoEmail = "";
        this.clienteNombre = "";
        this.clienteTelefono = "";
        this.clienteEmail = "";
        this.depositoNombre = "";
        this.depositoTelefono = "";
        this.depositoEmail = "";
        this.gln = "";
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public int getId_formaPago() {
        return id_formaPago;
    }

    public void setId_formaPago(int id_formaPago) {
        this.id_formaPago = id_formaPago;
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

    public String getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
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

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getPagoNombre() {
        return pagoNombre;
    }

    public void setPagoNombre(String pagoNombre) {
        this.pagoNombre = pagoNombre;
    }

    public String getPagoTelefono() {
        return pagoTelefono;
    }

    public void setPagoTelefono(String pagoTelefono) {
        this.pagoTelefono = pagoTelefono;
    }

    public String getPagoEmail() {
        return pagoEmail;
    }

    public void setPagoEmail(String pagoEmail) {
        this.pagoEmail = pagoEmail;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public String getDepositoNombre() {
        return depositoNombre;
    }

    public void setDepositoNombre(String depositoNombre) {
        this.depositoNombre = depositoNombre;
    }

    public String getDepositoTelefono() {
        return depositoTelefono;
    }

    public void setDepositoTelefono(String depositoTelefono) {
        this.depositoTelefono = depositoTelefono;
    }

    public String getDepositoEmail() {
        return depositoEmail;
    }

    public void setDepositoEmail(String depositoEmail) {
        this.depositoEmail = depositoEmail;
    }

    public String getGln() {
        return gln;
    }

    public void setGln(String gln) {
        this.gln = gln;
    }
}
