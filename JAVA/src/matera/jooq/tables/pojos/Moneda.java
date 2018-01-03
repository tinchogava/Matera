/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "moneda", schema = "matera")
public class Moneda implements Serializable {

	private static final long serialVersionUID = -433903220;

	private Integer    idMoneda;
	private String     codigo;
	private String     nombre;
	private String     simbolo;
	private String     habilita;
	private BigDecimal cotizacion;

	public Moneda() {}

	public Moneda(Moneda value) {
		this.idMoneda = value.idMoneda;
		this.codigo = value.codigo;
		this.nombre = value.nombre;
		this.simbolo = value.simbolo;
		this.habilita = value.habilita;
		this.cotizacion = value.cotizacion;
	}

	public Moneda(
		Integer    idMoneda,
		String     codigo,
		String     nombre,
		String     simbolo,
		String     habilita,
		BigDecimal cotizacion
	) {
		this.idMoneda = idMoneda;
		this.codigo = codigo;
		this.nombre = nombre;
		this.simbolo = simbolo;
		this.habilita = habilita;
		this.cotizacion = cotizacion;
	}

	@Id
	@Column(name = "id_moneda", unique = true, nullable = false, precision = 10)
	public Integer getIdMoneda() {
		return this.idMoneda;
	}

	public void setIdMoneda(Integer idMoneda) {
		this.idMoneda = idMoneda;
	}

	@Column(name = "codigo", length = 3)
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "simbolo", length = 3)
	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	@Column(name = "habilita", length = 1)
	public String getHabilita() {
		return this.habilita;
	}

	public void setHabilita(String habilita) {
		this.habilita = habilita;
	}

	@Column(name = "cotizacion", precision = 12, scale = 3)
	public BigDecimal getCotizacion() {
		return this.cotizacion;
	}

	public void setCotizacion(BigDecimal cotizacion) {
		this.cotizacion = cotizacion;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Moneda (");

		sb.append(idMoneda);
		sb.append(", ").append(codigo);
		sb.append(", ").append(nombre);
		sb.append(", ").append(simbolo);
		sb.append(", ").append(habilita);
		sb.append(", ").append(cotizacion);

		sb.append(")");
		return sb.toString();
	}
}
