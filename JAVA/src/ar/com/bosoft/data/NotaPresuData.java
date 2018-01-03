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


public class NotaPresuData {
    int id_notaPresu;
    String nota;

    public NotaPresuData() {
        this.id_notaPresu = 0;
        this.nota = "";
    }

    public int getId_notaPresu() {
        return id_notaPresu;
    }

    public void setId_notaPresu(int id_notaPresu) {
        this.id_notaPresu = id_notaPresu;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
