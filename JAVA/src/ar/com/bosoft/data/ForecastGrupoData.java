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


public class ForecastGrupoData {
    int id_forecastGrupo;
    String nombre, habilita;

    public ForecastGrupoData() {
        this.id_forecastGrupo = 0;
        this.nombre = "";
        this.habilita = "";
    }

    public int getId_forecastGrupo() {
        return id_forecastGrupo;
    }

    public void setId_forecastGrupo(int id_forecastGrupo) {
        this.id_forecastGrupo = id_forecastGrupo;
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
