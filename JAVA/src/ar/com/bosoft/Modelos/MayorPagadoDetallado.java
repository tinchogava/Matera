package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class MayorPagadoDetallado {
    String nombre, paciente, detallePago;
    BigDecimal importe;
    Date fechaLiquidacion;
    int id_presupuesto;

    public MayorPagadoDetallado(String nombre, String paciente, String detallePago, BigDecimal importe, Date fechaLiquidacion, int id_presupuesto) {
        this.nombre = nombre;
        this.paciente = paciente;
        this.detallePago = detallePago;
        this.importe = importe;
        this.fechaLiquidacion = fechaLiquidacion;
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

    public String getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(String detallePago) {
        this.detallePago = detallePago;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }
}
