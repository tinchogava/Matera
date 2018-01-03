package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class MayorComposicionDetallado {
    String nombre, paciente, tipoOperacion, observaciones;
    BigDecimal saldoInicial, saldo, acumulado;
    Date fecha;
    int id_presupuesto;

    public MayorComposicionDetallado(String nombre, String paciente, String tipoOperacion, String observaciones, BigDecimal saldoInicial, BigDecimal saldo, BigDecimal acumulado, Date fecha, int id_presupuesto) {
        this.nombre = nombre;
        this.paciente = paciente;
        this.tipoOperacion = tipoOperacion;
        this.observaciones = observaciones;
        this.saldoInicial = saldoInicial;
        this.saldo = saldo;
        this.acumulado = acumulado;
        this.fecha = fecha;
        this.id_presupuesto = id_presupuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(BigDecimal acumulado) {
        this.acumulado = acumulado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }
}