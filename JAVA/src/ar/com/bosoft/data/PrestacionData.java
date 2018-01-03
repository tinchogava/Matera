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


public class PrestacionData {
    int id_prestacion;
    String nombre, habilita;
    Double importe;

    public PrestacionData() {
        this.id_prestacion = 0;
        this.nombre = "";
        this.habilita = "";
        this.importe = 0.00;
    }

    public int getId_prestacion() {
        return id_prestacion;
    }

    public void setId_prestacion(int id_prestacion) {
        this.id_prestacion = id_prestacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHabilita() {
        return habilita;
    }

    public void setHabilita(String habilita) {
        this.habilita = habilita;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }
}
