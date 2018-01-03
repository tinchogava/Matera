/*
 * Derechos de uso otorgados a MyA Tecnología Quirúrgica S.A.
 * Todos los derechos reservados - 2014 -
 * BOSOFT - Servicios Inform�ticos Integrales
 * www.bosoft.com.ar
 * 
 */
package ar.com.bosoft.data;

/**
 *
 * @author Hernán Estalella - BOSOFT Servicios Informáticos Integrales
 * <hernan_estalella@bosoft.com.ar>
 */
public class ProductoFactData {
    int id_productoFact, id_especialidad, id_subespecialidad, id_forecastGrupo;
    String codigo, nombre, habilita, especialidad, subespecialidad, forecastGrupo, descripcion;
    Double precio;

    public ProductoFactData() {
        this.id_productoFact = 0;
        this.id_especialidad = 0;
        this.id_subespecialidad = 0;
        this.id_forecastGrupo = 0;
        this.codigo = "";
        this.nombre = "";
        this.habilita = "";
        this.especialidad = "";
        this.subespecialidad = "";
        this.forecastGrupo = "";
        this.descripcion = "";
        this.precio = 0.00;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getSubespecialidad() {
        return subespecialidad;
    }

    public void setSubespecialidad(String subespecialidad) {
        this.subespecialidad = subespecialidad;
    }

    public int getId_productoFact() {
        return id_productoFact;
    }

    public void setId_productoFact(int id_productoFact) {
        this.id_productoFact = id_productoFact;
    }

    public int getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
    }

    public int getId_subespecialidad() {
        return id_subespecialidad;
    }

    public void setId_subespecialidad(int id_subespecialidad) {
        this.id_subespecialidad = id_subespecialidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public int getId_forecastGrupo() {
        return id_forecastGrupo;
    }

    public void setId_forecastGrupo(int id_forecastGrupo) {
        this.id_forecastGrupo = id_forecastGrupo;
    }

    public String getForecastGrupo() {
        return forecastGrupo;
    }

    public void setForecastGrupo(String forecastGrupo) {
        this.forecastGrupo = forecastGrupo;
    }
}
