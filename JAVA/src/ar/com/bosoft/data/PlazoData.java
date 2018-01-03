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


public class PlazoData {
    int id_plazo;
    String nombre, habilita;

    public PlazoData() {
        this.id_plazo = 0;
        this.nombre = "";
        this.habilita = "";
    }

    public int getId_plazo() {
        return id_plazo;
    }

    public void setId_plazo(int id_plazo) {
        this.id_plazo = id_plazo;
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
