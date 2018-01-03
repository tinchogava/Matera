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
public class TipoCompData {
    private int id_tipoComp;
    private String codigo, denominacion, abreviatura;
    
    public TipoCompData(){
        this.id_tipoComp = 0;
        this.denominacion = "";
        this.abreviatura = "";
    }

    public int getId_tipoComp() {
        return id_tipoComp;
    }

    public void setId_tipoComp(int id_tipoComp) {
        this.id_tipoComp = id_tipoComp;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}
