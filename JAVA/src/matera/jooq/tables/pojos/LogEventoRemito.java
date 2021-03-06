/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jooq.types.UInteger;


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
@Table(name = "log_evento_remito", schema = "matera")
public class LogEventoRemito implements Serializable {

	private static final long serialVersionUID = 979901898;

	private Integer   id;
	private Timestamp timestamp;
	private Integer   idLogEventoTipo;
	private UInteger  idUsuario;
	private Integer   idRemito;

	public LogEventoRemito() {}

	public LogEventoRemito(LogEventoRemito value) {
		this.id = value.id;
		this.timestamp = value.timestamp;
		this.idLogEventoTipo = value.idLogEventoTipo;
		this.idUsuario = value.idUsuario;
		this.idRemito = value.idRemito;
	}

	public LogEventoRemito(
		Integer   id,
		Timestamp timestamp,
		Integer   idLogEventoTipo,
		UInteger  idUsuario,
		Integer   idRemito
	) {
		this.id = id;
		this.timestamp = timestamp;
		this.idLogEventoTipo = idLogEventoTipo;
		this.idUsuario = idUsuario;
		this.idRemito = idRemito;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "timestamp", nullable = false)
	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name = "id_log_evento_tipo", nullable = false, precision = 10)
	public Integer getIdLogEventoTipo() {
		return this.idLogEventoTipo;
	}

	public void setIdLogEventoTipo(Integer idLogEventoTipo) {
		this.idLogEventoTipo = idLogEventoTipo;
	}

	@Column(name = "id_usuario", nullable = false, precision = 10)
	public UInteger getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(UInteger idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "id_remito", nullable = false, precision = 10)
	public Integer getIdRemito() {
		return this.idRemito;
	}

	public void setIdRemito(Integer idRemito) {
		this.idRemito = idRemito;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("LogEventoRemito (");

		sb.append(id);
		sb.append(", ").append(timestamp);
		sb.append(", ").append(idLogEventoTipo);
		sb.append(", ").append(idUsuario);
		sb.append(", ").append(idRemito);

		sb.append(")");
		return sb.toString();
	}
}
