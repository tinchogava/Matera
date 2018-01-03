/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matera.db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author lpascuali
 */
@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    @Id
    @Column(name="id_stock", nullable = false)
    private Long id;
    @Column(name="numComp", insertable = false, updatable = false)
    private String comprobante;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipoComp", nullable = false)
    private TipoComprobante tipoComprobante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }
    
    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }
    
}
