/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

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
@Table(name = "ajustestock", schema = "matera")
public class Ajustestock implements Serializable {

	private static final long serialVersionUID = 436120757;

	private Integer   idAjustestock;
	private Integer   idZona;
	private Date      fecha;
	private Integer   idEmpresa;
	private String    usuario;
	private Timestamp fechareal;

	public Ajustestock() {}

	public Ajustestock(Ajustestock value) {
		this.idAjustestock = value.idAjustestock;
		this.idZona = value.idZona;
		this.fecha = value.fecha;
		this.idEmpresa = value.idEmpresa;
		this.usuario = value.usuario;
		this.fechareal = value.fechareal;
	}

	public Ajustestock(
		Integer   idAjustestock,
		Integer   idZona,
		Date      fecha,
		Integer   idEmpresa,
		String    usuario,
		Timestamp fechareal
	) {
		this.idAjustestock = idAjustestock;
		this.idZona = idZona;
		this.fecha = fecha;
		this.idEmpresa = idEmpresa;
		this.usuario = usuario;
		this.fechareal = fechareal;
	}

	@Id
	@Column(name = "id_ajusteStock", unique = true, nullable = false, precision = 10)
	public Integer getIdAjustestock() {
		return this.idAjustestock;
	}

	public void setIdAjustestock(Integer idAjustestock) {
		this.idAjustestock = idAjustestock;
	}

	@Column(name = "id_zona", precision = 10)
	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	@Column(name = "fecha")
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "id_empresa", precision = 10)
	public Integer getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	@Column(name = "usuario", length = 45)
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name = "fechaReal")
	public Timestamp getFechareal() {
		return this.fechareal;
	}

	public void setFechareal(Timestamp fechareal) {
		this.fechareal = fechareal;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Ajustestock (");

		sb.append(idAjustestock);
		sb.append(", ").append(idZona);
		sb.append(", ").append(fecha);
		sb.append(", ").append(idEmpresa);
		sb.append(", ").append(usuario);
		sb.append(", ").append(fechareal);

		sb.append(")");
		return sb.toString();
	}
}
