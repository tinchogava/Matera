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


public class TipoOperacionData {
    int id_tipoOperacion;
    String nombre;

    public TipoOperacionData() {
        this.id_tipoOperacion = 0;
        this.nombre = "";
    }

    public int getId_tipoOperacion() {
        return id_tipoOperacion;
    }

    public void setId_tipoOperacion(int id_tipooperacion) {
        this.id_tipoOperacion = id_tipooperacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
