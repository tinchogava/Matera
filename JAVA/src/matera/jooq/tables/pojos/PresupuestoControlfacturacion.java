/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "presupuesto_controlfacturacion", schema = "matera")
public class PresupuestoControlfacturacion implements Serializable {

	private static final long serialVersionUID = -1113449184;

	private Integer idPresupuesto;
	private Integer circuito;
	private Integer orden;
	private Integer protocolo;
	private Integer rx;
	private Integer remito;
	private Integer firma;
	private Integer sticker;
	private Integer completo;

	public PresupuestoControlfacturacion() {}

	public PresupuestoControlfacturacion(PresupuestoControlfacturacion value) {
		this.idPresupuesto = value.idPresupuesto;
		this.circuito = value.circuito;
		this.orden = value.orden;
		this.protocolo = value.protocolo;
		this.rx = value.rx;
		this.remito = value.remito;
		this.firma = value.firma;
		this.sticker = value.sticker;
		this.completo = value.completo;
	}

	public PresupuestoControlfacturacion(
		Integer idPresupuesto,
		Integer circuito,
		Integer orden,
		Integer protocolo,
		Integer rx,
		Integer remito,
		Integer firma,
		Integer sticker,
		Integer completo
	) {
		this.idPresupuesto = idPresupuesto;
		this.circuito = circuito;
		this.orden = orden;
		this.protocolo = protocolo;
		this.rx = rx;
		this.remito = remito;
		this.firma = firma;
		this.sticker = sticker;
		this.completo = completo;
	}

	@Id
	@Column(name = "id_presupuesto", unique = true, nullable = false, precision = 10)
	public Integer getIdPresupuesto() {
		return this.idPresupuesto;
	}

	public void setIdPresupuesto(Integer idPresupuesto) {
		this.idPresupuesto = idPresupuesto;
	}

	@Column(name = "circuito", nullable = false, precision = 10)
	public Integer getCircuito() {
		return this.circuito;
	}

	public void setCircuito(Integer circuito) {
		this.circuito = circuito;
	}

	@Column(name = "orden", nullable = false, precision = 10)
	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	@Column(name = "protocolo", nullable = false, precision = 10)
	public Integer getProtocolo() {
		return this.protocolo;
	}

	public void setProtocolo(Integer protocolo) {
		this.protocolo = protocolo;
	}

	@Column(name = "rx", nullable = false, precision = 10)
	public Integer getRx() {
		return this.rx;
	}

	public void setRx(Integer rx) {
		this.rx = rx;
	}

	@Column(name = "remito", nullable = false, precision = 10)
	public Integer getRemito() {
		return this.remito;
	}

	public void setRemito(Integer remito) {
		this.remito = remito;
	}

	@Column(name = "firma", nullable = false, precision = 10)
	public Integer getFirma() {
		return this.firma;
	}

	public void setFirma(Integer firma) {
		this.firma = firma;
	}

	@Column(name = "sticker", nullable = false, precision = 10)
	public Integer getSticker() {
		return this.sticker;
	}

	public void setSticker(Integer sticker) {
		this.sticker = sticker;
	}

	@Column(name = "completo", nullable = false, precision = 10)
	public Integer getCompleto() {
		return this.completo;
	}

	public void setCompleto(Integer completo) {
		this.completo = completo;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("PresupuestoControlfacturacion (");

		sb.append(idPresupuesto);
		sb.append(", ").append(circuito);
		sb.append(", ").append(orden);
		sb.append(", ").append(protocolo);
		sb.append(", ").append(rx);
		sb.append(", ").append(remito);
		sb.append(", ").append(firma);
		sb.append(", ").append(sticker);
		sb.append(", ").append(completo);

		sb.append(")");
		return sb.toString();
	}
}