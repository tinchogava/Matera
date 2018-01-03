package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class LiquidacionTecnico {
    Date fecha;
    int id_presupuesto;
    String paciente, profesional, tecnico;
    BigDecimal importe;

    public LiquidacionTecnico(Date fecha, int id_presupuesto, String paciente, String profesional, String tecnico, BigDecimal importe) {
        this.fecha = fecha;
        this.id_presupuesto = id_presupuesto;
        this.paciente = paciente;
        this.profesional = profesional;
        this.tecnico = tecnico;
        this.importe = importe;
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

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
}
