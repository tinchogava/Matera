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
@Table(name = "estadosistema", schema = "matera")
public class Estadosistema implements Serializable {

	private static final long serialVersionUID = 11537988;

	private Integer idEstadosistema;
	private String  estado;
	private String  enproceso;

	public Estadosistema() {}

	public Estadosistema(Estadosistema value) {
		this.idEstadosistema = value.idEstadosistema;
		this.estado = value.estado;
		this.enproceso = value.enproceso;
	}

	public Estadosistema(
		Integer idEstadosistema,
		String  estado,
		String  enproceso
	) {
		this.idEstadosistema = idEstadosistema;
		this.estado = estado;
		this.enproceso = enproceso;
	}

	@Id
	@Column(name = "id_estadoSistema", unique = true, nullable = false, precision = 10)
	public Integer getIdEstadosistema() {
		return this.idEstadosistema;
	}

	public void setIdEstadosistema(Integer idEstadosistema) {
		this.idEstadosistema = idEstadosistema;
	}

	@Column(name = "estado", length = 45)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "enProceso", length = 1)
	public String getEnproceso() {
		return this.enproceso;
	}

	public void setEnproceso(String enproceso) {
		this.enproceso = enproceso;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Estadosistema (");

		sb.append(idEstadosistema);
		sb.append(", ").append(estado);
		sb.append(", ").append(enproceso);

		sb.append(")");
		return sb.toString();
	}
}
