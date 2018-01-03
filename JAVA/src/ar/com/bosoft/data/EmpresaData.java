package ar.com.bosoft.data;

import java.sql.Date;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class EmpresaData {
    int id_empresa, posicionIva;
    String razonSocial, fantasia, direccion, departamento, provincia, pais, cuit, ingresosBrutos, establecimiento,
           sedeTimbrado, claveAccesos, habilita, usuario, descIva;
    Date inicioActividades;

    public EmpresaData(){
        id_empresa = 0;
        posicionIva = 0;
        razonSocial = "";
        fantasia = "";
        direccion = "";
        departamento = "";
        provincia = "";
        pais = "";
        cuit = "";
        ingresosBrutos = "";
        establecimiento = "";
        sedeTimbrado = "";
        claveAccesos = "";
        habilita = "";
        usuario = "";
        descIva = "";
        inicioActividades = null;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getPosicionIva() {
        return posicionIva;
    }

    public void setPosicionIva(int posicionIva) {
        this.posicionIva = posicionIva;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getIngresosBrutos() {
        return ingresosBrutos;
    }

    public void setIngresosBrutos(String ingresosBrutos) {
        this.ingresosBrutos = ingresosBrutos;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getSedeTimbrado() {
        return sedeTimbrado;
    }

    public void setSedeTimbrado(String sedeTimbrado) {
        this.sedeTimbrado = sedeTimbrado;
    }

    public String getClaveAccesos() {
        return claveAccesos;
    }

    public void setClaveAccesos(String claveAccesos) {
        this.claveAccesos = claveAccesos;
    }

    public String getHabilita() {
        return habilita;
    }

    public void setHabilita(String habilita) {
        this.habilita = habilita;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescIva() {
        return descIva;
    }

    public void setDescIva(String descIva) {
        this.descIva = descIva;
    }

    public Date getInicioActividades() {
        return inicioActividades;
    }

    public void setInicioActividades(Date inicioActividades) {
        this.inicioActividades = inicioActividades;
    }
}
