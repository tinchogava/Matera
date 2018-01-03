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
@Table(name = "grupoporcentaje", schema = "matera")
public class Grupoporcentaje implements Serializable {

	private static final long serialVersionUID = -961625776;

	private Integer idGrupoporcentaje;
	private String  nombre;
	private String  habilita;
	private Integer idEmpresa;

	public Grupoporcentaje() {}

	public Grupoporcentaje(Grupoporcentaje value) {
		this.idGrupoporcentaje = value.idGrupoporcentaje;
		this.nombre = value.nombre;
		this.habilita = value.habilita;
		this.idEmpresa = value.idEmpresa;
	}

	public Grupoporcentaje(
		Integer idGrupoporcentaje,
		String  nombre,
		String  habilita,
		Integer idEmpresa
	) {
		this.idGrupoporcentaje = idGrupoporcentaje;
		this.nombre = nombre;
		this.habilita = habilita;
		this.idEmpresa = idEmpresa;
	}

	@Id
	@Column(name = "id_grupoPorcentaje", unique = true, nullable = false, precision = 10)
	public Integer getIdGrupoporcentaje() {
		return this.idGrupoporcentaje;
	}

	public void setIdGrupoporcentaje(Integer idGrupoporcentaje) {
		this.idGrupoporcentaje = idGrupoporcentaje;
	}

	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "habilita", length = 1)
	public String getHabilita() {
		return this.habilita;
	}

	public void setHabilita(String habilita) {
		this.habilita = habilita;
	}

	@Column(name = "id_empresa", precision = 10)
	public Integer getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Grupoporcentaje (");

		sb.append(idGrupoporcentaje);
		sb.append(", ").append(nombre);
		sb.append(", ").append(habilita);
		sb.append(", ").append(idEmpresa);

		sb.append(")");
		return sb.toString();
	}
}
