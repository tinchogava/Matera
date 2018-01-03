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
@Table(name = "atencioncliente", schema = "matera")
public class Atencioncliente implements Serializable {

	private static final long serialVersionUID = -516000822;

	private Integer idAtencioncliente;
	private Integer idProveedor;
	private String  nombre;
	private String  telefono;
	private String  email;

	public Atencioncliente() {}

	public Atencioncliente(Atencioncliente value) {
		this.idAtencioncliente = value.idAtencioncliente;
		this.idProveedor = value.idProveedor;
		this.nombre = value.nombre;
		this.telefono = value.telefono;
		this.email = value.email;
	}

	public Atencioncliente(
		Integer idAtencioncliente,
		Integer idProveedor,
		String  nombre,
		String  telefono,
		String  email
	) {
		this.idAtencioncliente = idAtencioncliente;
		this.idProveedor = idProveedor;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
	}

	@Id
	@Column(name = "id_atencionCliente", unique = true, nullable = false, precision = 10)
	public Integer getIdAtencioncliente() {
		return this.idAtencioncliente;
	}

	public void setIdAtencioncliente(Integer idAtencioncliente) {
		this.idAtencioncliente = idAtencioncliente;
	}

	@Column(name = "id_proveedor", precision = 10)
	public Integer getIdProveedor() {
		return this.idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
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
		StringBuilder sb = new StringBuilder("Atencioncliente (");

		sb.append(idAtencioncliente);
		sb.append(", ").append(idProveedor);
		sb.append(", ").append(nombre);
		sb.append(", ").append(telefono);
		sb.append(", ").append(email);

		sb.append(")");
		return sb.toString();
	}
}
