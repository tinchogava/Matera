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
@Table(name = "encargadocompras", schema = "matera")
public class Encargadocompras implements Serializable {

	private static final long serialVersionUID = 546013074;

	private Integer idEncargadocompras;
	private Integer idEntidad;
	private String  nombre;
	private String  telefono;
	private String  email;

	public Encargadocompras() {}

	public Encargadocompras(Encargadocompras value) {
		this.idEncargadocompras = value.idEncargadocompras;
		this.idEntidad = value.idEntidad;
		this.nombre = value.nombre;
		this.telefono = value.telefono;
		this.email = value.email;
	}

	public Encargadocompras(
		Integer idEncargadocompras,
		Integer idEntidad,
		String  nombre,
		String  telefono,
		String  email
	) {
		this.idEncargadocompras = idEncargadocompras;
		this.idEntidad = idEntidad;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
	}

	@Id
	@Column(name = "id_encargadoCompras", unique = true, nullable = false, precision = 10)
	public Integer getIdEncargadocompras() {
		return this.idEncargadocompras;
	}

	public void setIdEncargadocompras(Integer idEncargadocompras) {
		this.idEncargadocompras = idEncargadocompras;
	}

	@Column(name = "id_entidad", precision = 10)
	public Integer getIdEntidad() {
		return this.idEntidad;
	}

	public void setIdEntidad(Integer idEntidad) {
		this.idEntidad = idEntidad;
	}

	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "telefono", length = 45)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "email", length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Encargadocompras (");

		sb.append(idEncargadocompras);
		sb.append(", ").append(idEntidad);
		sb.append(", ").append(nombre);
		sb.append(", ").append(telefono);
		sb.append(", ").append(email);

		sb.append(")");
		return sb.toString();
	}
}
