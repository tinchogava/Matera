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
@Table(name = "tecnico", schema = "matera")
public class Tecnico implements Serializable {

	private static final long serialVersionUID = 865496528;

	private Integer idTecnico;
	private String  nombre;
	private String  habilita;
	private Integer idEmpresa;

	public Tecnico() {}

	public Tecnico(Tecnico value) {
		this.idTecnico = value.idTecnico;
		this.nombre = value.nombre;
		this.habilita = value.habilita;
		this.idEmpresa = value.idEmpresa;
	}

	public Tecnico(
		Integer idTecnico,
		String  nombre,
		String  habilita,
		Integer idEmpresa
	) {
		this.idTecnico = idTecnico;
		this.nombre = nombre;
		this.habilita = habilita;
		this.idEmpresa = idEmpresa;
	}

	@Id
	@Column(name = "id_tecnico", unique = true, nullable = false, precision = 10)
	public Integer getIdTecnico() {
		return this.idTecnico;
	}

	public void setIdTecnico(Integer idTecnico) {
		this.idTecnico = idTecnico;
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

	@Column(name = "id_empresa", nullable = false, precision = 10)
	public Integer getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Tecnico (");

		sb.append(idTecnico);
		sb.append(", ").append(nombre);
		sb.append(", ").append(habilita);
		sb.append(", ").append(idEmpresa);

		sb.append(")");
		return sb.toString();
	}
}
