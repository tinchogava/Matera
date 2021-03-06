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
@Table(name = "departamento", schema = "matera")
public class Departamento implements Serializable {

	private static final long serialVersionUID = 802342553;

	private Integer idDepartamento;
	private Integer idProvincia;
	private String  nombre;

	public Departamento() {}

	public Departamento(Departamento value) {
		this.idDepartamento = value.idDepartamento;
		this.idProvincia = value.idProvincia;
		this.nombre = value.nombre;
	}

	public Departamento(
		Integer idDepartamento,
		Integer idProvincia,
		String  nombre
	) {
		this.idDepartamento = idDepartamento;
		this.idProvincia = idProvincia;
		this.nombre = nombre;
	}

	@Id
	@Column(name = "id_departamento", unique = true, nullable = false, precision = 10)
	public Integer getIdDepartamento() {
		return this.idDepartamento;
	}

	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	@Column(name = "id_provincia", nullable = false, precision = 10)
	public Integer getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Departamento (");

		sb.append(idDepartamento);
		sb.append(", ").append(idProvincia);
		sb.append(", ").append(nombre);

		sb.append(")");
		return sb.toString();
	}
}
