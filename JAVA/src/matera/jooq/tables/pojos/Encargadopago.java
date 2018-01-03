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
@Table(name = "encargadopago", schema = "matera")
public class Encargadopago implements Serializable {

	private static final long serialVersionUID = 788023002;

	private Integer idEncargadopago;
	private Integer idProveedor;
	private String  nombre;
	private String  telefono;
	private String  email;

	public Encargadopago() {}

	public Encargadopago(Encargadopago value) {
		this.idEncargadopago = value.idEncargadopago;
		this.idProveedor = value.idProveedor;
		this.nombre = value.nombre;
		this.telefono = value.telefono;
		this.email = value.email;
	}

	public Encargadopago(
		Integer idEncargadopago,
		Integer idProveedor,
		String  nombre,
		String  telefono,
		String  email
	) {
		this.idEncargadopago = idEncargadopago;
		this.idProveedor = idProveedor;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
	}

	@Id
	@Column(name = "id_encargadopago", unique = true, nullable = false, precision = 10)
	public Integer getIdEncargadopago() {
		return this.idEncargadopago;
	}

	public void setIdEncargadopago(Integer idEncargadopago) {
		this.idEncargadopago = idEncargadopago;
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
		StringBuilder sb = new StringBuilder("Encargadopago (");

		sb.append(idEncargadopago);
		sb.append(", ").append(idProveedor);
		sb.append(", ").append(nombre);
		sb.append(", ").append(telefono);
		sb.append(", ").append(email);

		sb.append(")");
		return sb.toString();
	}
}