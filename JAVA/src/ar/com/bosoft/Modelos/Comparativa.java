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
public class Comparativa {
    String entidad, producto, profesional, paciente, aprobado, competencia, lobby, observaciones;
    Date fecha;
    int id_presupuesto, cantidad;
    BigDecimal montoPresupuesto, montoFacturado;

    public Comparativa(String entidad, String producto, String profesional, String paciente, String aprobado, String competencia, String lobby, String observaciones, Date fecha, int id_presupuesto, BigDecimal montoPresupuesto, BigDecimal montoFacturado, int cantidad) {
        this.entidad = entidad;
        this.producto = producto;
        this.profesional = profesional;
        this.paciente = paciente;
        this.aprobado = aprobado;
        this.competencia = competencia;
        this.lobby = lobby;
        this.observaciones = observaciones;
        this.fecha = fecha;
        this.id_presupuesto = id_presupuesto;
        this.montoPresupuesto = montoPresupuesto;
        this.montoFacturado = montoFacturado;
        this.cantidad = cantidad;
    }
}
