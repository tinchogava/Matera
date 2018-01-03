/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author lpascuali
 */
@Entity
@Table(name = "tipocomp")
public class TipoComprobante implements Serializable {
    
    @Id
    @Column(name="id_tipoComp", nullable = false)
    private Long id;
    
    @Column(name="abreviatura", insertable = false, updatable = false)
    private String abreviatura;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}
