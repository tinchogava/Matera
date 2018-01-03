/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.pojos;


import java.io.Serializable;
import java.sql.Date;

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
@Table(name = "usuario", schema = "matera")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1409585803;

	private UInteger idUsuario;
	private String   nombre;
	private String   documento;
	private String   cuil;
	private Date     fechanac;
	private String   direccion;
	private String   codpostal;
	private Integer  idProvincia;
	private Integer  idDepartamento;
	private Integer  idLocalidad;
	private Date     fechaingreso;
	private Date     fechaegreso;
	private String   usasistema;
	private String   contraseña;
	private String   email;
	private Integer  idZona;
	private Integer  idZonasistema;
	private String   habilita;
	private Integer  idEmpresa;

	public Usuario() {}

	public Usuario(Usuario value) {
		this.idUsuario = value.idUsuario;
		this.nombre = value.nombre;
		this.documento = value.documento;
		this.cuil = value.cuil;
		this.fechanac = value.fechanac;
		this.direccion = value.direccion;
		this.codpostal = value.codpostal;
		this.idProvincia = value.idProvincia;
		this.idDepartamento = value.idDepartamento;
		this.idLocalidad = value.idLocalidad;
		this.fechaingreso = value.fechaingreso;
		this.fechaegreso = value.fechaegreso;
		this.usasistema = value.usasistema;
		this.contraseña = value.contraseña;
		this.email = value.email;
		this.idZona = value.idZona;
		this.idZonasistema = value.idZonasistema;
		this.habilita = value.habilita;
		this.idEmpresa = value.idEmpresa;
	}

	public Usuario(
		UInteger idUsuario,
		String   nombre,
		String   documento,
		String   cuil,
		Date     fechanac,
		String   direccion,
		String   codpostal,
		Integer  idProvincia,
		Integer  idDepartamento,
		Integer  idLocalidad,
		Date     fechaingreso,
		Date     fechaegreso,
		String   usasistema,
		String   contraseña,
		String   email,
		Integer  idZona,
		Integer  idZonasistema,
		String   habilita,
		Integer  idEmpresa
	) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.documento = documento;
		this.cuil = cuil;
		this.fechanac = fechanac;
		this.direccion = direccion;
		this.codpostal = codpostal;
		this.idProvincia = idProvincia;
		this.idDepartamento = idDepartamento;
		this.idLocalidad = idLocalidad;
		this.fechaingreso = fechaingreso;
		this.fechaegreso = fechaegreso;
		this.usasistema = usasistema;
		this.contraseña = contraseña;
		this.email = email;
		this.idZona = idZona;
		this.idZonasistema = idZonasistema;
		this.habilita = habilita;
		this.idEmpresa = idEmpresa;
	}

	@Id
	@Column(name = "id_usuario", unique = true, nullable = false, precision = 10)
	public UInteger getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(UInteger idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "documento", length = 10)
	public String getDocumento() {
		return this.documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Column(name = "cuil", length = 11)
	public String getCuil() {
		return this.cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	@Column(name = "fechaNac")
	public Date getFechanac() {
		return this.fechanac;
	}

	public void setFechanac(Date fechanac) {
		this.fechanac = fechanac;
	}

	@Column(name = "direccion", length = 100)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "codPostal", length = 10)
	public String getCodpostal() {
		return this.codpostal;
	}

	public void setCodpostal(String codpostal) {
		this.codpostal = codpostal;
	}

	@Column(name = "id_provincia", precision = 10)
	public Integer getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	@Column(name = "id_departamento", precision = 10)
	public Integer getIdDepartamento() {
		return this.idDepartamento;
	}

	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	@Column(name = "id_localidad", precision = 10)
	public Integer getIdLocalidad() {
		return this.idLocalidad;
	}

	public void setIdLocalidad(Integer idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	@Column(name = "fechaIngreso")
	public Date getFechaingreso() {
		return this.fechaingreso;
	}

	public void setFechaingreso(Date fechaingreso) {
		this.fechaingreso = fechaingreso;
	}

	@Column(name = "fechaEgreso")
	public Date getFechaegreso() {
		return this.fechaegreso;
	}

	public void setFechaegreso(Date fechaegreso) {
		this.fechaegreso = fechaegreso;
	}

	@Column(name = "usaSistema", length = 1)
	public String getUsasistema() {
		return this.usasistema;
	}

	public void setUsasistema(String usasistema) {
		this.usasistema = usasistema;
	}

	@Column(name = "contraseña", length = 15)
	public String getContraseña() {
		return this.contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	@Column(name = "email", length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "id_zona", precision = 10)
	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	@Column(name = "id_zonaSistema", precision = 10)
	public Integer getIdZonasistema() {
		return this.idZonasistema;
	}

	public void setIdZonasistema(Integer idZonasistema) {
		this.idZonasistema = idZonasistema;
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
		StringBuilder sb = new StringBuilder("Usuario (");

		sb.append(idUsuario);
		sb.append(", ").append(nombre);
		sb.append(", ").append(documento);
		sb.append(", ").append(cuil);
		sb.append(", ").append(fechanac);
		sb.append(", ").append(direccion);
		sb.append(", ").append(codpostal);
		sb.append(", ").append(idProvincia);
		sb.append(", ").append(idDepartamento);
		sb.append(", ").append(idLocalidad);
		sb.append(", ").append(fechaingreso);
		sb.append(", ").append(fechaegreso);
		sb.append(", ").append(usasistema);
		sb.append(", ").append(contraseña);
		sb.append(", ").append(email);
		sb.append(", ").append(idZona);
		sb.append(", ").append(idZonasistema);
		sb.append(", ").append(habilita);
		sb.append(", ").append(idEmpresa);

		sb.append(")");
		return sb.toString();
	}
}
