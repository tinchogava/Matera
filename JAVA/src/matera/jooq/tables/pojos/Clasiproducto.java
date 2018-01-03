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
@Table(name = "clasiproducto", schema = "matera")
public class Clasiproducto implements Serializable {

	private static final long serialVersionUID = -1966577672;

	private Integer idClasiproducto;
	private String  nombre;
	private String  habilita;

	public Clasiproducto() {}

	public Clasiproducto(Clasiproducto value) {
		this.idClasiproducto = value.idClasiproducto;
		this.nombre = value.nombre;
		this.habilita = value.habilita;
	}

	public Clasiproducto(
		Integer idClasiproducto,
		String  nombre,
		String  habilita
	) {
		this.idClasiproducto = idClasiproducto;
		this.nombre = nombre;
		this.habilita = habilita;
	}

	@Id
	@Column(name = "id_clasiProducto", unique = true, nullable = false, precision = 10)
	public Integer getIdClasiproducto() {
		return this.idClasiproducto;
	}

	public void setIdClasiproducto(Integer idClasiproducto) {
		this.idClasiproducto = idClasiproducto;
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
		StringBuilder sb = new StringBuilder("Clasiproducto (");

		sb.append(idClasiproducto);
		sb.append(", ").append(nombre);
		sb.append(", ").append(habilita);

		sb.append(")");
		return sb.toString();
	}
}
