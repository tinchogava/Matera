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
@Table(name = "caja", schema = "matera")
public class Caja implements Serializable {

	private static final long serialVersionUID = 1906525365;

	private Integer idCaja;
	private String  nombre;
	private String  habilita;

	public Caja() {}

	public Caja(Caja value) {
		this.idCaja = value.idCaja;
		this.nombre = value.nombre;
		this.habilita = value.habilita;
	}

	public Caja(
		Integer idCaja,
		String  nombre,
		String  habilita
	) {
		this.idCaja = idCaja;
		this.nombre = nombre;
		this.habilita = habilita;
	}

	@Id
	@Column(name = "id_caja", unique = true, nullable = false, precision = 10)
	public Integer getIdCaja() {
		return this.idCaja;
	}

	public void setIdCaja(Integer idCaja) {
		this.idCaja = idCaja;
	}

	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "habilita", nullable = false, length = 1)
	public String getHabilita() {
		return this.habilita;
	}

	public void setHabilita(String habilita) {
		this.habilita = habilita;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Caja (");

		sb.append(idCaja);
		sb.append(", ").append(nombre);
		sb.append(", ").append(habilita);

		sb.append(")");
		return sb.toString();
	}
}