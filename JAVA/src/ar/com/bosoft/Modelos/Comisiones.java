package ar.com.bosoft.Modelos;

import java.math.BigDecimal;


/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class Comisiones {
    String zona, nombre, especialidad, beneficio, tipoOperacion, comparte, numComp;
    BigDecimal comision, neto, rm, lineaFlotacion, importeComision;
    int id_presupuesto;

    public Comisiones(String zona, String nombre, String especialidad, String beneficio,
            String tipoOperacion, BigDecimal comision, BigDecimal neto, BigDecimal rm,
            BigDecimal lineaFlotacion, BigDecimal importeComision,
            int id_presupuesto, String comparte, String numComp) {
        this.zona = zona;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.beneficio = beneficio;
        this.tipoOperacion = tipoOperacion;
        this.comision = comision;
        this.neto = neto;
        this.rm = rm;
        this.lineaFlotacion = lineaFlotacion;
        this.importeComision = importeComision;
        this.id_presupuesto = id_presupuesto;
        this.comparte = comparte;
        this.numComp = numComp;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(String beneficio) {
        this.beneficio = beneficio;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getNeto() {
        return neto;
    }

    public void setNeto(BigDecimal neto) {
        this.neto = neto;
    }

    public BigDecimal getRm() {
        return rm;
    }

    public void setRm(BigDecimal rm) {
        this.rm = rm;
    }

    public BigDecimal getLineaFlotacion() {
        return lineaFlotacion;
    }

    public void setLineaFlotacion(BigDecimal lineaFlotacion) {
        this.lineaFlotacion = lineaFlotacion;
    }

    public BigDecimal getImporteComision() {
        return importeComision;
    }

    public void setImporteComision(BigDecimal importeComision) {
        this.importeComision = importeComision;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public String getComparte() {
        return comparte;
    }

    public void setComparte(String comparte) {
        this.comparte = comparte;
    }
}
