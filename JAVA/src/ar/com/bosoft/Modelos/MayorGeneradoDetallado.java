/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.bosoft.Modelos;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author tinchogava
 */
public class MayorGeneradoDetallado {

int id_presupuesto;
Date fecha;
String paciente, observaciones, dc, medico;
BigDecimal importe,debe, haber;

    public MayorGeneradoDetallado(Date fecha, int id_presupuesto, String medico, String paciente,
            String observaciones, String dc, BigDecimal importe) {
        this.fecha = fecha;
        this.id_presupuesto = id_presupuesto;
        this.paciente = paciente;
        this.observaciones = observaciones;
        this.dc = dc;
        this.importe = importe;
        this.medico = medico;
        
        switch(dc){
            case "C":{
                debe = BigDecimal.ZERO;
                haber = importe;
                break;
            }
                
            case "D":{
                debe = importe;
                haber = BigDecimal.ZERO;
                break;
            }
        }
    }  

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
}
