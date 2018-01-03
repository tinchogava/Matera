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


public class EntidadData {
    int id_entidad, id_localidad, id_departamento, id_provincia, id_zona, 
            id_opcion, id_formaPago, id_clasiEntidad;
    String nombre, direccion, codPostal, cuit, telefono1, 
            telefono2, email, secretaria, observaciones, habilita, 
            provincia, departamento, localidad, zona, formaPago, 
            posicionIva, auditor, certImplante, recomendaciones, 
            reqFacturacion, clasiEntidad, comprasNombre, comprasTelefono, comprasEmail,  
            tesoreriaNombre, tesoreriaTelefono, tesoreriaEmail, contableNombre, contableTelefono, 
            contableEmail, farmaciaNombre, farmaciaTelefono, farmaciaEmail, gln;
    Double riesgoCredito;

    public EntidadData() {
        this.id_entidad = 0;
        this.id_localidad = 0;
        this.id_departamento = 0;
        this.id_provincia = 0;
        this.id_zona = 0;
        this.id_opcion = 0;
        this.id_formaPago = 0;
        this.id_clasiEntidad = 0;
        this.nombre = "";
        this.direccion = "";
        this.codPostal = "";
        this.cuit = "";
        this.telefono1 = "";
        this.telefono2 = "";
        this.email = "";
        this.secretaria = "";
        this.observaciones = "";
        this.habilita = "";
        this.provincia = "";
        this.departamento = "";
        this.localidad = "";
        this.zona = "";
        this.formaPago = "";
        this.posicionIva = "";
        this.auditor = "";
        this.certImplante = "";
        this.recomendaciones = "";
        this.riesgoCredito = 0.000;
        this.reqFacturacion = "";
        this.clasiEntidad = "";
        this.comprasNombre = "";
        this.comprasTelefono = "";
        this.comprasEmail = "";
        this.tesoreriaNombre = "";
        this.tesoreriaTelefono = "";
        this.tesoreriaEmail = "";
        this.contableNombre = "";
        this.contableTelefono = "";
        this.contableEmail = "";
        this.farmaciaNombre = "";
        this.farmaciaTelefono = "";
        this.farmaciaEmail = "";
        this.gln = "";
    }

    public int getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(int id_entidad) {
        this.id_entidad = id_entidad;
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

    public int getId_opcion() {
        return id_opcion;
    }

    public void setId_opcion(int id_opcion) {
        this.id_opcion = id_opcion;
    }

    public int getId_formaPago() {
        return id_formaPago;
    }

    public void setId_formaPago(int id_formaPago) {
        this.id_formaPago = id_formaPago;
    }

    public int getId_clasiEntidad() {
        return id_clasiEntidad;
    }

    public void setId_clasiEntidad(int id_clasiEntidad) {
        this.id_clasiEntidad = id_clasiEntidad;
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

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
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

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getPosicionIva() {
        return posicionIva;
    }

    public void setPosicionIva(String posicionIva) {
        this.posicionIva = posicionIva;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getCertImplante() {
        return certImplante;
    }

    public void setCertImplante(String certImplante) {
        this.certImplante = certImplante;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public Double getRiesgoCredito() {
        return riesgoCredito;
    }

    public void setRiesgoCredito(Double riesgoCredito) {
        this.riesgoCredito = riesgoCredito;
    }

    public String getReqFacturacion() {
        return reqFacturacion;
    }

    public void setReqFacturacion(String reqFacturacion) {
        this.reqFacturacion = reqFacturacion;
    }

    public String getClasiEntidad() {
        return clasiEntidad;
    }

    public void setClasiEntidad(String clasiEntidad) {
        this.clasiEntidad = clasiEntidad;
    }

    public String getComprasNombre() {
        return comprasNombre;
    }

    public void setComprasNombre(String comprasNombre) {
        this.comprasNombre = comprasNombre;
    }

    public String getComprasTelefono() {
        return comprasTelefono;
    }

    public void setComprasTelefono(String comprasTelefono) {
        this.comprasTelefono = comprasTelefono;
    }

    public String getComprasEmail() {
        return comprasEmail;
    }

    public void setComprasEmail(String comprasEmail) {
        this.comprasEmail = comprasEmail;
    }

    public String getTesoreriaNombre() {
        return tesoreriaNombre;
    }

    public void setTesoreriaNombre(String tesoreriaNombre) {
        this.tesoreriaNombre = tesoreriaNombre;
    }

    public String getTesoreriaTelefono() {
        return tesoreriaTelefono;
    }

    public void setTesoreriaTelefono(String tesoreriaTelefono) {
        this.tesoreriaTelefono = tesoreriaTelefono;
    }

    public String getTesoreriaEmail() {
        return tesoreriaEmail;
    }

    public void setTesoreriaEmail(String tesoreriaEmail) {
        this.tesoreriaEmail = tesoreriaEmail;
    }

    public String getContableNombre() {
        return contableNombre;
    }

    public void setContableNombre(String contableNombre) {
        this.contableNombre = contableNombre;
    }

    public String getContableTelefono() {
        return contableTelefono;
    }

    public void setContableTelefono(String contableTelefono) {
        this.contableTelefono = contableTelefono;
    }

    public String getContableEmail() {
        return contableEmail;
    }

    public void setContableEmail(String contableEmail) {
        this.contableEmail = contableEmail;
    }

    public String getFarmaciaNombre() {
        return farmaciaNombre;
    }

    public void setFarmaciaNombre(String farmaciaNombre) {
        this.farmaciaNombre = farmaciaNombre;
    }

    public String getFarmaciaTelefono() {
        return farmaciaTelefono;
    }

    public void setFarmaciaTelefono(String farmaciaTelefono) {
        this.farmaciaTelefono = farmaciaTelefono;
    }

    public String getFarmaciaEmail() {
        return farmaciaEmail;
    }

    public void setFarmaciaEmail(String farmaciaEmail) {
        this.farmaciaEmail = farmaciaEmail;
    }

    public String getGln() {
        return gln;
    }

    public void setGln(String gln) {
        this.gln = gln;
    }
}
