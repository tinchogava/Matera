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


public class ClasiProductoData {
    int id_clasiProducto;
    String nombre, habilita;

    public ClasiProductoData() {
        this.id_clasiProducto = 0;
        this.nombre = "";
        this.habilita = "";
    }

    public int getId_clasiProducto() {
        return id_clasiProducto;
    }

    public void setId_clasiProducto(int id_clasiProducto) {
        this.id_clasiProducto = id_clasiProducto;
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
