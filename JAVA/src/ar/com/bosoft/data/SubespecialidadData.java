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


public class SubespecialidadData {
    int id_subespecialidad, id_especialidad;
    String nombre, habilita, especialidad;

    public SubespecialidadData() {
        this.id_subespecialidad = 0;
        this.id_especialidad = 0;
        this.nombre = "";
        this.habilita = "";
        this.especialidad = "";
    }

    public int getId_subespecialidad() {
        return id_subespecialidad;
    }

    public void setId_subespecialidad(int id_subespecialidad) {
        this.id_subespecialidad = id_subespecialidad;
    }

    public int getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
