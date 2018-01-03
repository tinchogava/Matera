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


public class ClasiEntidadData {
    int id_clasiEntidad;
    String nombre, habilita;

    public ClasiEntidadData() {
        this.id_clasiEntidad = 0;
        this.nombre = "";
        this.habilita = "";
    }

    public int getId_clasiEntidad() {
        return id_clasiEntidad;
    }

    public void setId_clasiEntidad(int id_clasiEntidad) {
        this.id_clasiEntidad = id_clasiEntidad;
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
}
