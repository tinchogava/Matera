/**
 * This class is generated by jOOQ
 */
package matera.jooq.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name = "entidad", schema = "matera")
public class Entidad implements Serializable {

	private static final long serialVersionUID = -1504586517;

	private Integer    idEntidad;
	private String     nombre;
	private String     direccion;
	private Integer    idProvincia;
	private Integer    idDepartamento;
	private Integer    idLocalidad;
	private String     codpostal;
	private String     email;
	private String     telefono1;
	private String     telefono2;
	private String     auditor;
	private String     secretaria;
	private Integer    idZona;
	private String     certimplante;
	private String     recomendaciones;
	private Integer    idFormapago;
	private Integer    idOpcion;
	private String     cuit;
	private Integer    idClasientidad;
	private BigDecimal riesgocredito;
	private String     reqfacturacion;
	private String     observaciones;
	private String     gln;
	private String     habilita;
	private Integer    idEmpresa;

	public Entidad() {}

	public Entidad(Entidad value) {
		this.idEntidad = value.idEntidad;
		this.nombre = value.nombre;
		this.direccion = value.direccion;
		this.idProvincia = value.idProvincia;
		this.idDepartamento = value.idDepartamento;
		this.idLocalidad = value.idLocalidad;
		this.codpostal = value.codpostal;
		this.email = value.email;
		this.telefono1 = value.telefono1;
		this.telefono2 = value.telefono2;
		this.auditor = value.auditor;
		this.secretaria = value.secretaria;
		this.idZona = value.idZona;
		this.certimplante = value.certimplante;
		this.recomendaciones = value.recomendaciones;
		this.idFormapago = value.idFormapago;
		this.idOpcion = value.idOpcion;
		this.cuit = value.cuit;
		this.idClasientidad = value.idClasientidad;
		this.riesgocredito = value.riesgocredito;
		this.reqfacturacion = value.reqfacturacion;
		this.observaciones = value.observaciones;
		this.gln = value.gln;
		this.habilita = value.habilita;
		this.idEmpresa = value.idEmpresa;
	}

	public Entidad(
		Integer    idEntidad,
		String     nombre,
		String     direccion,
		Integer    idProvincia,
		Integer    idDepartamento,
		Integer    idLocalidad,
		String     codpostal,
		String     email,
		String     telefono1,
		String     telefono2,
		String     auditor,
		String     secretaria,
		Integer    idZona,
		String     certimplante,
		String     recomendaciones,
		Integer    idFormapago,
		Integer    idOpcion,
		String     cuit,
		Integer    idClasientidad,
		BigDecimal riesgocredito,
		String     reqfacturacion,
		String     observaciones,
		String     gln,
		String     habilita,
		Integer    idEmpresa
	) {
		this.idEntidad = idEntidad;
		this.nombre = nombre;
		this.direccion = direccion;
		this.idProvincia = idProvincia;
		this.idDepartamento = idDepartamento;
		this.idLocalidad = idLocalidad;
		this.codpostal = codpostal;
		this.email = email;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.auditor = auditor;
		this.secretaria = secretaria;
		this.idZona = idZona;
		this.certimplante = certimplante;
		this.recomendaciones = recomendaciones;
		this.idFormapago = idFormapago;
		this.idOpcion = idOpcion;
		this.cuit = cuit;
		this.idClasientidad = idClasientidad;
		this.riesgocredito = riesgocredito;
		this.reqfacturacion = reqfacturacion;
		this.observaciones = observaciones;
		this.gln = gln;
		this.habilita = habilita;
		this.idEmpresa = idEmpresa;
	}

	@Id
	@Column(name = "id_entidad", unique = true, nullable = false, precision = 10)
	public Integer getIdEntidad() {
		return this.idEntidad;
	}

	public void setIdEntidad(Integer idEntidad) {
		this.idEntidad = idEntidad;
	}

	@Column(name = "nombre", length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "direccion", length = 100)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	@Column(name = "codPostal", length = 10)
	public String getCodpostal() {
		return this.codpostal;
	}

	public void setCodpostal(String codpostal) {
		this.codpostal = codpostal;
	}

	@Column(name = "email", length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telefono1", length = 45)
	public String getTelefono1() {
		return this.telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	@Column(name = "telefono2", length = 45)
	public String getTelefono2() {
		return this.telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	@Column(name = "auditor", length = 45)
	public String getAuditor() {
		return this.auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	@Column(name = "secretaria", length = 45)
	public String getSecretaria() {
		return this.secretaria;
	}

	public void setSecretaria(String secretaria) {
		this.secretaria = secretaria;
	}

	@Column(name = "id_zona", precision = 10)
	public Integer getIdZona() {
		return this.idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	@Column(name = "certImplante", length = 1)
	public String getCertimplante() {
		return this.certimplante;
	}

	public void setCertimplante(String certimplante) {
		this.certimplante = certimplante;
	}

	@Column(name = "recomendaciones", length = 1)
	public String getRecomendaciones() {
		return this.recomendaciones;
	}

	public void setRecomendaciones(String recomendaciones) {
		this.recomendaciones = recomendaciones;
	}

	@Column(name = "id_formaPago", precision = 10)
	public Integer getIdFormapago() {
		return this.idFormapago;
	}

	public void setIdFormapago(Integer idFormapago) {
		this.idFormapago = idFormapago;
	}

	@Column(name = "id_opcion", precision = 10)
	public Integer getIdOpcion() {
		return this.idOpcion;
	}

	public void setIdOpcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}

	@Column(name = "cuit", length = 11)
	public String getCuit() {
		return this.cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	@Column(name = "id_clasiEntidad", precision = 10)
	public Integer getIdClasientidad() {
		return this.idClasientidad;
	}

	public void setIdClasientidad(Integer idClasientidad) {
		this.idClasientidad = idClasientidad;
	}

	@Column(name = "riesgoCredito", precision = 9, scale = 3)
	public BigDecimal getRiesgocredito() {
		return this.riesgocredito;
	}

	public void setRiesgocredito(BigDecimal riesgocredito) {
		this.riesgocredito = riesgocredito;
	}

	@Column(name = "reqFacturacion", length = 65535)
	public String getReqfacturacion() {
		return this.reqfacturacion;
	}

	public void setReqfacturacion(String reqfacturacion) {
		this.reqfacturacion = reqfacturacion;
	}

	@Column(name = "observaciones", length = 65535)
	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Column(name = "gln", length = 14)
	public String getGln() {
		return this.gln;
	}

	public void setGln(String gln) {
		this.gln = gln;
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
		StringBuilder sb = new StringBuilder("Entidad (");

		sb.append(idEntidad);
		sb.append(", ").append(nombre);
		sb.append(", ").append(direccion);
		sb.append(", ").append(idProvincia);
		sb.append(", ").append(idDepartamento);
		sb.append(", ").append(idLocalidad);
		sb.append(", ").append(codpostal);
		sb.append(", ").append(email);
		sb.append(", ").append(telefono1);
		sb.append(", ").append(telefono2);
		sb.append(", ").append(auditor);
		sb.append(", ").append(secretaria);
		sb.append(", ").append(idZona);
		sb.append(", ").append(certimplante);
		sb.append(", ").append(recomendaciones);
		sb.append(", ").append(idFormapago);
		sb.append(", ").append(idOpcion);
		sb.append(", ").append(cuit);
		sb.append(", ").append(idClasientidad);
		sb.append(", ").append(riesgocredito);
		sb.append(", ").append(reqfacturacion);
		sb.append(", ").append(observaciones);
		sb.append(", ").append(gln);
		sb.append(", ").append(habilita);
		sb.append(", ").append(idEmpresa);

		sb.append(")");
		return sb.toString();
	}
}
