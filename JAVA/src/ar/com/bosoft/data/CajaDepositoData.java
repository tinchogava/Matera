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


public class CajaDepositoData {
    int id_cajaDeposito, id_zona, id_caja;
    String codigo, habilita, zona, caja;

    public CajaDepositoData() {
        this.id_cajaDeposito = 0;
        this.id_zona = 0;
        this.id_caja = 0;
        this.codigo = "";
        this.habilita = "";
        this.zona = "";
        this.caja = "";
    }

    public int getId_cajaDeposito() {
        return id_cajaDeposito;
    }

    public void setId_cajaDeposito(int id_cajaDeposito) {
        this.id_cajaDeposito = id_cajaDeposito;
    }

    public int getId_zona() {
        return id_zona;
    }

    public void setId_zona(int id_zona) {
        this.id_zona = id_zona;
    }

    public int getId_caja() {
        return id_caja;
    }

    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getHabilita() {
        return habilita;
    }

    public void setHabilita(String habilita) {
        this.habilita = habilita;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }
}
