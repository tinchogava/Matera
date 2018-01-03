package ar.com.bosoft.Modelos;

import java.util.Date;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */


public class ListaLiquidacion {
    int id_presupuesto, id_liquidacion;
    String paciente, banco, cheque, profesional;
    Double importe, valor;
    Date vencimiento, fecha;

    public ListaLiquidacion(int id_presupuesto, int id_liquidacion, String paciente, String banco, String cheque, String profesional, Double importe, Double valor, Date vencimiento, Date fecha) {
        this.id_presupuesto = id_presupuesto;
        this.id_liquidacion = id_liquidacion;
        this.paciente = paciente;
        this.banco = banco;
        this.cheque = cheque;
        this.profesional = profesional;
        this.importe = importe;
        this.valor = valor;
        this.vencimiento = vencimiento;
        this.fecha = fecha;
    }

    public int getId_presupuesto() {
        return id_presupuesto;
    }

    public void setId_presupuesto(int id_presupuesto) {
        this.id_presupuesto = id_presupuesto;
    }

    public int getId_liquidacion() {
        return id_liquidacion;
    }

    public void setId_liquidacion(int id_liquidacion) {
        this.id_liquidacion = id_liquidacion;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
