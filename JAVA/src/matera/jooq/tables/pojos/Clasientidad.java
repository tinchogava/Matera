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
@Table(name = "clasientidad", schema = "matera")
public class Clasientidad implements Serializable {

	private static final long serialVersionUID = -1974176645;

	private Integer idClasientidad;
	private String  nombre;
	private String  habilita;

	public Clasientidad() {}

	public Clasientidad(Clasientidad value) {
		this.idClasientidad = value.idClasientidad;
		this.nombre = value.nombre;
		this.habilita = value.habilita;
	}

	public Clasientidad(
		Integer idClasientidad,
		String  nombre,
		String  habilita
	) {
		this.idClasientidad = idClasientidad;
		this.nombre = nombre;
		this.habilita = habilita;
	}

	@Id
	@Column(name = "id_clasiEntidad", unique = true, nullable = false, precision = 10)
	public Integer getIdClasientidad() {
		return this.idClasientidad;
	}

	public void setIdClasientidad(Integer idClasientidad) {
		this.idClasientidad = idClasientidad;
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
		StringBuilder sb = new StringBuilder("Clasientidad (");

		sb.append(idClasientidad);
		sb.append(", ").append(nombre);
		sb.append(", ").append(habilita);

		sb.append(")");
		return sb.toString();
	}
}
