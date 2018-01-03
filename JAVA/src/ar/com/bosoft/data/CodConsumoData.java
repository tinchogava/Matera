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
public class CodConsumoData {
    private int id_codConsumo;
    private String codigo,nombre;
    private String habilita;
    
    public CodConsumoData(){
        this.id_codConsumo = 0;
        this.nombre = "";
        this.habilita = "";
    }

    public int getId_codConsumo() {
        return id_codConsumo;
    }

    public void setId_codConsumo(int id_codConsumo) {
        this.id_codConsumo = id_codConsumo;
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
}
