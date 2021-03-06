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
@Table(name = "log_evento_tipo", schema = "matera")
public class LogEventoTipo implements Serializable {

	private static final long serialVersionUID = -988535181;

	private Integer id;
	private String  nombre;

	public LogEventoTipo() {}

	public LogEventoTipo(LogEventoTipo value) {
		this.id = value.id;
		this.nombre = value.nombre;
	}

	public LogEventoTipo(
		Integer id,
		String  nombre
	) {
		this.id = id;
		this.nombre = nombre;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nombre", nullable = false, length = 255)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("LogEventoTipo (");

		sb.append(id);
		sb.append(", ").append(nombre);

		sb.append(")");
		return sb.toString();
	}
}
