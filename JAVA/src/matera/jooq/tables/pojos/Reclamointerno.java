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
@Table(name = "reclamoInterno", schema = "matera")
public class Reclamointerno implements Serializable {

	private static final long serialVersionUID = -1783042183;

	private Integer idReclamointerno;
	private String  nombre;
	private String  habilita;

	public Reclamointerno() {}

	public Reclamointerno(Reclamointerno value) {
		this.idReclamointerno = value.idReclamointerno;
		this.nombre = value.nombre;
		this.habilita = value.habilita;
	}

	public Reclamointerno(
		Integer idReclamointerno,
		String  nombre,
		String  habilita
	) {
		this.idReclamointerno = idReclamointerno;
		this.nombre = nombre;
		this.habilita = habilita;
	}

	@Id
	@Column(name = "id_reclamoInterno", unique = true, nullable = false, precision = 10)
	public Integer getIdReclamointerno() {
		return this.idReclamointerno;
	}

	public void setIdReclamointerno(Integer idReclamointerno) {
		this.idReclamointerno = idReclamointerno;
	}

	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "habilita")
	public String getHabilita() {
		return this.habilita;
	}

	public void setHabilita(String habilita) {
		this.habilita = habilita;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Reclamointerno (");

		sb.append(idReclamointerno);
		sb.append(", ").append(nombre);
		sb.append(", ").append(habilita);

		sb.append(")");
		return sb.toString();
	}
}
