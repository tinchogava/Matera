/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class LiqTec {
    int id_presupuesto;
    Date fechaCx;
    String paciente, profesional, prestacion, tecnico;
    BigDecimal importe;

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public Date getFechaCx() {
        return fechaCx;
    }

    public void setFechaCx(Date fechaCx) {
        this.fechaCx = fechaCx;
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

    public String getPrestacion() {
        return prestacion;
    }

    public void setPrestacion(String prestacion) {
        this.prestacion = prestacion;
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

    public LiqTec(int id_presupuesto, Date fechaCx, String paciente, String profesional, String prestacion, String tecnico, BigDecimal importe) {
        this.id_presupuesto = id_presupuesto;
        this.fechaCx = fechaCx;
        this.paciente = paciente;
        this.profesional = profesional;
        this.prestacion = prestacion;
        this.tecnico = tecnico;
        this.importe = importe;
    }
}
